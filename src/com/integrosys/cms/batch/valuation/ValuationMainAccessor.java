package com.integrosys.cms.batch.valuation;

import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationHandler;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingletonListener;
import com.integrosys.cms.batch.factory.BatchParameterValidator;

/**
 * Provide service for valuation main.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class ValuationMainAccessor {
	private IValuationMainDAO valuationMainDao;

	private IValuationHandler valuationHandler;

	private TransactionTemplate transactionTemplate;

	private BatchParameterValidator valuationParameterValidator;

	private ValuationProfileSingletonListener valuationProfileSingletonListener;

	/**
	 * @return the transactionTemplate
	 */
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	/**
	 * @return the valuationHandler
	 */
	public IValuationHandler getValuationHandler() {
		return valuationHandler;
	}

	/**
	 * @return the valuationMainDao
	 */
	public IValuationMainDAO getValuationMainDao() {
		return valuationMainDao;
	}

	/**
	 * @return the valuationParameterValidator
	 */
	public BatchParameterValidator getValuationParameterValidator() {
		return valuationParameterValidator;
	}

	public ValuationProfileSingletonListener getValuationProfileSingletonListener() {
		return valuationProfileSingletonListener;
	}

	/**
	 * @param transactionTemplate the transactionTemplate to set
	 */
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	/**
	 * @param valuationHandler the valuationHandler to set
	 */
	public void setValuationHandler(IValuationHandler valuationHandler) {
		this.valuationHandler = valuationHandler;
	}

	/**
	 * @param valuationMainDao the valuationMainDao to set
	 */
	public void setValuationMainDao(IValuationMainDAO valuationMainDao) {
		this.valuationMainDao = valuationMainDao;
	}

	/**
	 * @param valuationParameterValidator the valuationParameterValidator to set
	 */
	public void setValuationParameterValidator(BatchParameterValidator valuationParameterValidator) {
		this.valuationParameterValidator = valuationParameterValidator;
	}

	public void setValuationProfileSingletonListener(ValuationProfileSingletonListener valuationProfileSingletonListener) {
		this.valuationProfileSingletonListener = valuationProfileSingletonListener;
	}

}
