package com.integrosys.cms.batch.factory;

import org.springframework.transaction.support.TransactionTemplate;


/**
 * <p>
 * Batch Job that required to control transaction. Such as for long transaction,
 * begin and commit for a chunk of records.
 * 
 * <p>
 * Transaction will be be using the help of {@link TransactionTemplate}. Caller
 * to deal with
 * {@link TransactionTemplate#execute(org.springframework.transaction.support.TransactionCallback)}
 * , and supply
 * {@link org.springframework.transaction.PlatformTransactionManager}
 * 
 * @author Chong Jun Yong
 * 
 */
public interface TransactionControlledBatchJob extends BatchJob {

	public void setTransactionTemplate(TransactionTemplate transactionTemplate);
}
