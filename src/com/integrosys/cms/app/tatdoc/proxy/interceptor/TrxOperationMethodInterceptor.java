package com.integrosys.cms.app.tatdoc.proxy.interceptor;

import java.rmi.RemoteException;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocBusManager;
import com.integrosys.cms.app.tatdoc.bus.OBTatDoc;
import com.integrosys.cms.app.tatdoc.bus.TatDocException;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatdoc.trx.OBTatDocTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.TrxOperationHelper;

/**
 * <p>
 * Interceptor to intercept facility and collateral transaction operation, to
 * check for the complete status of STP. then to update the TAT completion date
 * for the Limit Profile.
 * <p>
 * TAT document will be either created (no TAT found) or updated (only if
 * completion date is not input)
 * <p>
 * This interceptor suppose to check against which Application Types of AA are
 * valid for auto create/update of the TAT records, via setting values inside
 * {@link #setValidAutoCreateTatDocApplicationTypes(String[])}
 * 
 * @author Chong Jun Yong
 * 
 */
public class TrxOperationMethodInterceptor implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(TrxOperationMethodInterceptor.class);

	private static final String OPS_DESC_SYSTEM_CREATE_TAT = "SYSTEM_CREATE_TAT";

	private ILimitDAO limitJdbcDao;

	private ITatDocBusManager actualTatDocBusManager;

	private ITatDocBusManager stagingTatDocBusManager;

	private SBCMSTrxManager workflowManager;

	private TransactionTemplate transactionTemplate;

	private HibernateTemplate hibernateTemplate;

	private String[] validAutoCreateTatDocApplicationTypes;

	public void setActualTatDocBusManager(ITatDocBusManager actualTatDocBusManager) {
		this.actualTatDocBusManager = actualTatDocBusManager;
	}

	public void setStagingTatDocBusManager(ITatDocBusManager stagingTatDocBusManager) {
		this.stagingTatDocBusManager = stagingTatDocBusManager;
	}

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setLimitJdbcDao(ILimitDAO limitJdbcDao) {
		this.limitJdbcDao = limitJdbcDao;
	}

	/**
	 * To set the valid application types which is avalaible for auto
	 * create/update TAT doc completion date.
	 * @param validAutoCreateTatDocApplicationTypes list of valid application
	 *        types.
	 */
	public void setValidAutoCreateTatDocApplicationTypes(String[] validAutoCreateTatDocApplicationTypes) {
		this.validAutoCreateTatDocApplicationTypes = validAutoCreateTatDocApplicationTypes;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (invocation.getMethod().getName().equals("performProcess")) {
			ICMSTrxValue trxValue = (ICMSTrxValue) invocation.getArguments()[0];

			final ITrxContext trxContext = trxValue.getTrxContext();
			final ILimitProfile limitProfile = this.limitJdbcDao.retrieveLimitProfileByCmsLimitProfileId(new Long(
					trxContext.getLimitProfile().getLimitProfileID()));

			ITatDoc actualTatDoc = this.actualTatDocBusManager.getTatDocByLimitProfileID(limitProfile
					.getLimitProfileID());
			if (actualTatDoc != null && actualTatDoc.getDocCompletionDate() != null) {
				return invocation.proceed();
			}

			if (!ArrayUtils.contains(this.validAutoCreateTatDocApplicationTypes, limitProfile.getApplicationType())) {
				return invocation.proceed();
			}

			this.transactionTemplate.execute(new TransactionCallback() {

				public Object doInTransaction(TransactionStatus status) {
					// to syncrhonize for jdbc call
					hibernateTemplate.flush();

					if (limitJdbcDao.checkLimitProfileStpComplete(limitProfile)) {
						logger.info("Going to update/insert TAT doc for Limit Profile, Id ["
								+ limitProfile.getLimitProfileID() + "], LOS AA Number ["
								+ limitProfile.getLosLimitProfileReference() + "].");
						updateOrInsertTatDoc(limitProfile, trxContext);
					}

					return null;
				}
			});

			return invocation.proceed();
		}

		return invocation.proceed();
	}

	private void updateOrInsertTatDoc(final ILimitProfile limitProfile, final ITrxContext trxContext) {
		final ITatDoc actualTatDoc = this.actualTatDocBusManager.getTatDocByLimitProfileID(limitProfile
				.getLimitProfileID());
		if (actualTatDoc != null) {
			actualTatDoc.setDocCompletionDate(new Date());

			this.transactionTemplate.execute(new TransactionCallback() {

				public Object doInTransaction(TransactionStatus status) {
					actualTatDocBusManager.update(actualTatDoc);

					try {
						ICMSTrxValue cmsTrxValue = workflowManager.findTrxByRefIDAndTrxType(String.valueOf(actualTatDoc
								.getTatDocID()), ICMSConstant.INSTANCE_TAT_DOC);
						if (cmsTrxValue != null) {
							ITatDoc stagingTatDoc = stagingTatDocBusManager.getTatDocByID(Long.parseLong(cmsTrxValue
									.getStagingReferenceID()));
							if (stagingTatDoc != null) {
								stagingTatDoc.setDocCompletionDate(actualTatDoc.getDocCompletionDate());
								stagingTatDocBusManager.update(stagingTatDoc);
							}
						}
					}
					catch (TransactionException e) {
						throw new TatDocException("failed to retrieve workflow values for reference id ["
								+ actualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC + "]", e);
					}
					catch (RemoteException e) {
						throw new TatDocException("failed to retrieve workflow values for reference id ["
								+ actualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC + "]", e
								.getCause());
					}

					actualTatDocBusManager.insertOrRemovePendingPerfectionCreditFolder(actualTatDoc, limitProfile);
					return null;
				}
			});
		}
		else {
			this.transactionTemplate.execute(new TransactionCallback() {

				public Object doInTransaction(TransactionStatus status) {
					ITatDoc stagingTatDoc = new OBTatDoc();
					stagingTatDoc.setDocCompletionDate(new Date());
					stagingTatDoc.setLimitProfileID(limitProfile.getLimitProfileID());

					ITatDoc createdStagingTatDoc = stagingTatDocBusManager.create(stagingTatDoc);

					ITatDoc actualTatDoc = new OBTatDoc();
					actualTatDoc.setDocCompletionDate(new Date());
					actualTatDoc.setLimitProfileID(limitProfile.getLimitProfileID());

					ITatDoc createdActualTatDoc = actualTatDocBusManager.create(actualTatDoc);

					ITatDocTrxValue trxValue = new OBTatDocTrxValue();
					trxValue.setInstanceName(ICMSConstant.INSTANCE_TAT_DOC);
					trxValue.setReferenceID(String.valueOf(createdActualTatDoc.getTatDocID()));
					trxValue.setStagingReferenceID(String.valueOf(createdStagingTatDoc.getTatDocID()));
					trxValue.setCreateDate(new Date());
					trxValue.setFromState(ICMSConstant.STATE_ND);
					trxValue.setToState(ICMSConstant.STATE_ACTIVE);
					trxValue.setOpDesc(OPS_DESC_SYSTEM_CREATE_TAT);
					trxValue.setTrxContext(trxContext);
					trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_DOC);

					try {
						TrxOperationHelper.mapTrxContext(trxContext, trxValue);
					}
					catch (TransactionException ex) {
						throw new TatDocException("failed to map trx context to trx value", ex);
					}

					try {
						workflowManager.createTransaction(trxValue);
					}
					catch (TransactionException e) {
						throw new TatDocException("failed to create workflow values for reference id ["
								+ createdActualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC
								+ "]", e);
					}
					catch (RemoteException e) {
						throw new TatDocException("failed to create workflow values for reference id ["
								+ createdActualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC
								+ "]", e);
					}

					actualTatDocBusManager.insertOrRemovePendingPerfectionCreditFolder(actualTatDoc, limitProfile);
					return null;
				}
			});
		}
	}

}
