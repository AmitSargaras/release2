/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBCommonCodeEntriesManagerBean.java
 *
 * Created on February 6, 2007, 11:43 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntriesException;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.trx.CommonCodeEntriesTrxControllerFactory;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;

/**
 * 
 * @author eric
 */
public class SBCommonCodeEntriesProxyManagerBean implements SessionBean, ICommonCodeEntriesProxy {

	SessionContext session;

	/** Creates a new instance of SBCommonCodeEntriesManagerBean */
	public SBCommonCodeEntriesProxyManagerBean() {
	}

	public void setSessionContext(SessionContext session) throws EJBException, RemoteException {
		this.session = session;
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbCreate() throws CreateException {

	}

	public void ejbPostCreate() {

	}

	public ICommonCodeEntriesTrxValue getCategoryTrxId(String transactionId) throws CommonCodeEntriesException {
		if (transactionId == null) {
			throw new CommonCodeEntriesException("The TrxID is null!!!");
		}

		DefaultLogger.debug(this, "Now retreiving entry info for transaction id : " + transactionId);

		ICommonCodeEntriesTrxValue trxValue = new OBCommonCodeEntriesTrxValue();
		trxValue.setTransactionID(transactionId);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_READ);

		return operate(trxValue, param);
	}

	public ICommonCodeEntriesTrxValue makerCancelUpdate(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		if (anITrxContext == null) {
			throw new CommonCodeEntriesException("The anITrxContext is null!!!");
		}
		if (commonCodeTypeTrxVal == null) {
			throw new CommonCodeEntriesException("The ICommonCodeEntriesTrxValue to be updated is null!!!");
		}

		ICommonCodeEntriesTrxValue trxValue = formulateTrxValue(anITrxContext, commonCodeTypeTrxVal);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_CLOSE);

		return operate(trxValue, param);
	}

	public ICommonCodeEntriesTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		if (anITrxContext == null) {
			throw new CommonCodeEntriesException("The anITrxContext is null!!!");
		}
		if (commonCodeTypeTrxVal == null) {
			throw new CommonCodeEntriesException("The ICommonCodeEntriesTrxValue to be updated is null!!!");
		}

		ICommonCodeEntriesTrxValue trxValue = formulateTrxValue(anITrxContext, commonCodeTypeTrxVal);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_REJECT);

		return operate(trxValue, param);
	}

	public ICommonCodeEntriesTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal) throws CommonCodeEntriesException {
		if (anITrxContext == null) {
			throw new CommonCodeEntriesException("The anITrxContext is null!!!");
		}
		if (commonCodeTypeTrxVal == null) {
			throw new CommonCodeEntriesException("The ICommonCodeEntriesTrxValue to be updated is null!!!");
		}

		ICommonCodeEntriesTrxValue trxValue = formulateTrxValue(anITrxContext, commonCodeTypeTrxVal);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_APPROVE);

		return operate(trxValue, param);
	}

	public ICommonCodeEntriesTrxValue getCategoryType(int categoryType) throws CommonCodeEntriesException {
		return null;
	}

	public SearchResult getCategoryList(SearchCriteria aCriteria) throws CommonCodeEntriesException, SearchDAOException {
		return null;
	}

	public ICommonCodeEntriesTrxValue makerUpdateCategory(ITrxContext ctx,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException {
		if (ctx == null) {
			throw new CommonCodeEntriesException("The anITrxContext is null!!!");
		}
		if (commonCodeTypeTrxVal == null) {
			throw new CommonCodeEntriesException("The ICommonCodeEntriesTrxValue to be updated is null!!!");
		}
		if (obCommonCodeType == null) {
			throw new CommonCodeEntriesException("The ICommonCodeEntries to be created is null!!!");
		}

		DefaultLogger.debug(this, "");

		ICommonCodeEntriesTrxValue trxValue = formulateTrxValue(ctx, commonCodeTypeTrxVal, obCommonCodeType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_UPDATE);

		return operate(trxValue, param);
	}

	public ICommonCodeEntriesTrxValue makerEditRejectedTrx(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue commonCodeTypeTrxVal, ICommonCodeEntries obCommonCodeType)
			throws CommonCodeEntriesException {
		// not implemented here
		// check the CommonCodeEntriesProxyManagerImpl

		return null;
	}

	private ICommonCodeEntriesTrxValue formulateTrxValue(ITrxContext anITrxContext, ICommonCodeEntries anCommonCodeType) {
		return formulateTrxValue(anITrxContext, null, anCommonCodeType);
	}

	private ICommonCodeEntriesTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICommonCodeEntries anCommonCodeType) {
		ICommonCodeEntriesTrxValue commonCodeTypeTrxValue = null;

		if (anICMSTrxValue != null) {
			commonCodeTypeTrxValue = new OBCommonCodeEntriesTrxValue(anICMSTrxValue);
		}
		else {
			commonCodeTypeTrxValue = new OBCommonCodeEntriesTrxValue();
		}

		commonCodeTypeTrxValue.setStagingCommonCodeEntries(anCommonCodeType);
		commonCodeTypeTrxValue = formulateTrxValue(anITrxContext, commonCodeTypeTrxValue);
		return commonCodeTypeTrxValue;
	}

	private ICommonCodeEntriesTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICommonCodeEntriesTrxValue anICommonCodeTypeTrxValue) {
		anICommonCodeTypeTrxValue.setTrxContext(anITrxContext);
		anICommonCodeTypeTrxValue.setTransactionType(ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME);
		return anICommonCodeTypeTrxValue;
	}

	public ICommonCodeEntriesTrxValue getCategoryId(long categoryId) throws CommonCodeEntriesException {
		if (categoryId == ICMSConstant.LONG_INVALID_VALUE) {
			throw new CommonCodeEntriesException("The categoryId is invalid!!!");
		}

		DefaultLogger.debug(this, "Now retreiving entry info for category code id : " + categoryId);

		ICommonCodeEntriesTrxValue trxValue = new OBCommonCodeEntriesTrxValue();
		trxValue.setReferenceID(String.valueOf(categoryId));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_READ_BY_REF);

		return operate(trxValue, param);		
	}
	
	public ICommonCodeEntriesTrxValue getEntryValues(long categoryId,String desc,String value) throws CommonCodeEntriesException {
		if (categoryId == ICMSConstant.LONG_INVALID_VALUE) {
			throw new CommonCodeEntriesException("The categoryId is invalid!!!");
		}

		ICommonCodeEntriesTrxValue trxValue = new OBCommonCodeEntriesTrxValue();
		trxValue.setCodeDescription(desc);
		trxValue.setCodeValue(value);
		trxValue.setReferenceID(String.valueOf(categoryId));
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.COMMON_CODE_ENTRY_READ_BY_REF);

		return operate(trxValue, param);		
	}

	private ICommonCodeEntriesTrxValue operate(ICommonCodeEntriesTrxValue anICommonCodeTypeTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CommonCodeEntriesException {
		ICMSTrxResult result = operateForResult(anICommonCodeTypeTrxValue, anOBCMSTrxParameter);
		return (ICommonCodeEntriesTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CommonCodeEntriesException {
		try {
			DefaultLogger.debug(this, "Trying to get controller");
			ITrxController controller = (new CommonCodeEntriesTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);

			if (controller == null) {
				DefaultLogger.debug(this, "Controller is null");
				throw new CommonDataManagerException("ITrxController is null!!!");
			}

			DefaultLogger.debug(this, "Going to operate Trx");
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			DefaultLogger.debug(this, "Done operation");
			return (ICMSTrxResult) result;
		}
		catch (Exception ex) {
			ex.printStackTrace();

			rollback();
			throw new CommonCodeEntriesException(ex.toString());
		}
	}

	protected void rollback() {
		session.setRollbackOnly();
	}

}
