package com.integrosys.cms.batch.common;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;

/**
 * A default implementation of {@link AbstractDomainAwareBatchFeedLoader} using
 * <ol>
 * <li><tt>FlatFileItemReader</tt> to read a flat file consist of feeds (via
 * {@link #setActualFlatFileItemReader(FlatFileItemReader)}
 * <li>then use <tt>ListRecordsDao</tt> (via
 * {@link #setListRecordsJdbcDao(ListRecordsDao)}to persist the feeds into the
 * temp table (via {@link #setTempTableName(String)}.
 * <li>Then finally rebind the stored procedure and run it (via
 * {@link #setValidationProcedureName(String)} and
 * {@link #setRunProcedureName(String)}) to update the domain table using the
 * feeds inside the temp table
 * </ol>
 * <p>
 * See the super class on what to set into this loader to run properly, such as
 * <tt>TransactionTemplate</tt>, <tt>JdbcTemplate</tt>, etc.
 * @author Chong Jun Yong
 * 
 */
public class DefaultDomainAwareBatchFeedLoader extends AbstractDomainAwareBatchFeedLoader {

	private FlatFileItemReader actualFlatFileItemReader;

	private ListRecordsDao listRecordsJdbcDao;

	public final void setActualFlatFileItemReader(FlatFileItemReader actualFlatFileItemReader) {
		this.actualFlatFileItemReader = actualFlatFileItemReader;
	}

	public final void setListRecordsJdbcDao(ListRecordsDao listRecordsJdbcDao) {
		this.listRecordsJdbcDao = listRecordsJdbcDao;
	}

	protected final void doExecute(Map context) throws BatchJobException {
		// first step, read feeds
		if (context.get(PARAM_KEY_SKIP_READ_FEEDS) == null) {
			doReadFeeds(context);
		}

		// second step, bind package
		if (context.get(PARAM_KEY_SKIP_BIND_PACKAGE) == null) {
			doRebindPackage();
		}

		// last step, run validate and actual stored proc
		if (context.get(PARAM_KEY_SKIP_RUN_PROC) == null) {
			doRunStoredProcedure();
		}
	}

	protected final void doPersistFeedList(List feedList) {
		this.listRecordsJdbcDao.persist(feedList);
	}

	protected final ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return this.actualFlatFileItemReader;
	}

}
