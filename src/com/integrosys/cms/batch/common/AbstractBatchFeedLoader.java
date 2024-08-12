package com.integrosys.cms.batch.common;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.cms.batch.BatchFeedLoadException;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.factory.TransactionControlledBatchJob;

/**
 * <p>
 * Abstract batch feed loader which itself also a batch job.
 * 
 * <p>
 * This loader facilitate the transaction environment by persist feeds in batch,
 * batch size controlled by {@link #setFeedBatchSize(int)}.
 * 
 * <p>
 * Subclass to implement {@link #doPersistFeedList(List)} to do the actual
 * persistent. Subclass should avoid persist one feed in a transaction context
 * which in turn will slow down the performance.
 * 
 * <p>
 * Subclass to implement {@link #getFlatFileItemReader()} which provided the
 * actual flat file reader used inside {@link #doReadFeeds(Map)}. If there are
 * multiple flat file item reader, consider to use some logic to control
 * returning this flat file reader. Such as return a private properties, this
 * properties get changed before calling {@link #doReadFeeds(Map)}.
 * 
 * @author Chong Jun Yong
 * @since 17.10.2008
 * @see #doReadFeeds(Map)
 */
public abstract class AbstractBatchFeedLoader implements TransactionControlledBatchJob {

	/** logger available for the sub classes */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private TransactionTemplate transactionTemplate;

	private BatchFeedErrorLogger batchFeedErrorLogger;

	/**
	 * feed batch size which indicate the size a list of feeds to be persisted
	 * before to continue next batch of feeds
	 */
	private int feedBatchSize;

	private int feedPersistentBatchSize = 0;

	public void setBatchFeedErrorLogger(BatchFeedErrorLogger batchFeedErrorLogger) {
		this.batchFeedErrorLogger = batchFeedErrorLogger;
	}

	public BatchFeedErrorLogger getBatchFeedErrorLogger() {
		return batchFeedErrorLogger;
	}

	public final int getFeedBatchSize() {
		return feedBatchSize;
	}

	public final void setFeedBatchSize(int feedBatchSize) {
		this.feedBatchSize = feedBatchSize;
	}

	/**
	 * The batch size for persistency, if this value is not set, it will follow
	 * the one of <tt>feedBatchSize</tt>
	 * @param feedPersistentBatchSize batch size for persistency, default is 0
	 */
	public final void setFeedPersistentBatchSize(int feedPersistentBatchSize) {
		this.feedPersistentBatchSize = feedPersistentBatchSize;
	}

	public final void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		Validate.notNull(transactionTemplate,
				"'transactionTemplate' to be used to control transaction must not be null.");
		Validate.notNull(transactionTemplate.getTransactionManager(), "'transactionManager' must not be null.");

		this.transactionTemplate = transactionTemplate;
	}

	protected TransactionTemplate getTransactionTemplate() {
		return this.transactionTemplate;
	}

	public abstract void execute(Map context) throws BatchJobException;

	/**
	 * <p>
	 * execute the actual workload in a bulk insertion fashion, preparing a list
	 * of feed which match the {@link #feedBatchSize} then do the persistence
	 * work after that. {@link #persistFeedList(List)} will be invoked after the
	 * list is prepared.
	 * 
	 * <p>
	 * the feed is getting from {@link #getFlatFileItemReader()}, subclass to
	 * implements this method.
	 * 
	 * <p>
	 * If some business logic need to be work on feed before persistent, use
	 * {@link #preprocess(Object)} to manipulate the feed.
	 * 
	 * @param context the key-value pair of parameters to be used in the batch
	 *        job, basically not used in the feed loader batch job, unless
	 *        stated otherwise.
	 * @throws BatchJobException if there is any error encountered in the
	 *         loading.
	 */
	protected void doReadFeeds(Map context) throws BatchJobException {
		ExecutionContext executionContext = new ExecutionContext();
		try {
			getFlatFileItemReader().open(executionContext);

			List feedList = new ArrayList(feedBatchSize);
			List batchFeedErrorList = new ArrayList();

			Object feed = getNextItem(batchFeedErrorList);
			while (feed != null) {
				preprocess(feed);
				feedList.add(feed);

				if (feedList.size() == feedBatchSize) {
					SoftReference ref = new SoftReference(feedList);
					persistFeedList((List) ref.get());
					feedList.clear();
					ref.clear();
					ref = null;
				}

				feed = getNextItem(batchFeedErrorList);
			}

			persistFeedList(feedList);
			feedList.clear();

			if (!batchFeedErrorList.isEmpty()) {
				logBatchFeedErrors(batchFeedErrorList);
			}
		}
		finally {
			getFlatFileItemReader().close(executionContext);
		}
	}

	/**
	 * Get next item which parsed successfully, it will automatically get the
	 * next parsable item if hit {@link FlatFileParseException}. If others
	 * exception are caught, whole batch job will stop, this could be due to I/O
	 * error, etc.
	 * 
	 * @param batchFeedErrorList list of batch feed errors to be added into it
	 *        for furthur logging purpose in caller
	 * @return the parsable item
	 */
	protected Object getNextItem(List batchFeedErrorList) {
		Object feed = null;

		boolean continueReading = true;
		while (continueReading) {
			try {
				feed = getFlatFileItemReader().read();
				continueReading = false;
			}
			catch (FlatFileParseException ex) {
				BatchFeedError error = new BatchFeedError(ex.getLineNumber(), ex.getInput(), ex.getCause().getMessage());
				batchFeedErrorList.add(error);
			}
			catch (Exception ex) {
				throw new BatchFeedLoadException(
						"failed to load flat file, not caused by parsing error, check log for more information", ex);
			}
		}

		return feed;
	}

	/**
	 * Process the feed before passed to persistence storage, eg can be set the
	 * id field in the feed.
	 * 
	 * @param feed feed going to be persisted
	 */
	protected void preprocess(Object feed) {
	}

	/**
	 * Persist the feed list in a bulk insertion fashion. actual work done in
	 * {@link #doPersistFeedList(List)}
	 * 
	 * @param feedList list of feed to be persisted.
	 */
	public final void persistFeedList(final List feedList) {
		if (feedList.isEmpty()) {
			return;
		}

		if (this.feedPersistentBatchSize > 0) {
			while (feedPersistentBatchSize <= feedList.size()) {
				SoftReference ref = new SoftReference(feedList.subList(0, feedPersistentBatchSize));
				final List persistentSubList = (List) ref.get();

				this.transactionTemplate.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						doPersistFeedList(persistentSubList);
						return null;
					}
				});

				persistentSubList.clear();
				ref.clear();
				ref = null;
			}

			if (feedList.size() > 0) {
				this.transactionTemplate.execute(new TransactionCallback() {
					public Object doInTransaction(TransactionStatus status) {
						doPersistFeedList(feedList);
						return null;
					}
				});
			}
		}
		else {
			transactionTemplate.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus status) {
					doPersistFeedList(feedList);
					return null;
				}
			});
		}

	}

	/**
	 * Actual persistent of the list of feed into storage. subclass should
	 * implement in the way that all feeds are transact in single transaction
	 * context.
	 * 
	 * @param feedList list of feed to be persisted.
	 */
	protected abstract void doPersistFeedList(List feedList);

	/**
	 * Log the batch feed errors into using logger provided.
	 * 
	 * @param batchFeedErrorList
	 */
	protected void logBatchFeedErrors(List batchFeedErrorList) {
		getBatchFeedErrorLogger().log(batchFeedErrorList);
	}

	/**
	 * Retrieve the flat file item reader to be used in
	 * {@link #doReadFeeds(Map)}
	 * 
	 * @return flat file item reader instance which is configured good-for-use
	 *         at the first place.
	 */
	protected abstract ResourceAwareItemReaderItemStream getFlatFileItemReader();

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Batch Job: ").append(Thread.currentThread().getName()).append("; ");
		buf.append("Batch Feed Size: ").append(feedBatchSize).append("; ");
		buf.append("Transaction Manager used: ").append(transactionTemplate.getTransactionManager());

		return buf.toString();
	}
}