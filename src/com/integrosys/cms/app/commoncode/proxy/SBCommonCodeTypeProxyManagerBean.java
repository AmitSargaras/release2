package com.integrosys.cms.app.commoncode.proxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.trx.CommonCodeTypeTrxControllerFactory;
import com.integrosys.cms.app.commoncode.trx.ICommonCodeTypeTrxValue;
import com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class SBCommonCodeTypeProxyManagerBean implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCommonCodeTypeProxyManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	public ICommonCodeTypeTrxValue getCategoryByTrxId(String transactionId) throws CommonCodeTypeException {

		if (transactionId == null) {
			throw new CommonCodeTypeException("The TrxID is null!!!");
		}
		ICommonCodeTypeTrxValue trxValue = new OBCommonCodeTypeTrxValue();
		trxValue.setTransactionID(transactionId);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COMMON_CODE_TYPE);
		return operate(trxValue, param);
	}

	/**
	 * Maker creation of a common code category
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anCommonCodeType - ICommonCodeType
	 * @return ICommonCodeTypeTrxValue - the interface representing the common
	 *         category
	 * @throws CommonCodeTypeException on errors
	 */
	public ICommonCodeTypeTrxValue makerCreateCategory(ITrxContext anITrxContext, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException {

		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (anCommonCodeType == null) {
			throw new CommonCodeTypeException("The ICommonCodeType to be created is null!!!");
		}
		ICommonCodeTypeTrxValue trxValue = formulateTrxValue(anITrxContext, anCommonCodeType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COMMON_CODE_TYPE);
		return operate(trxValue, param);
	}

	public ICommonCodeTypeTrxValue makerUpdateCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue aCommonCodeTypeTrxValue, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException {
		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (aCommonCodeTypeTrxValue == null) {
			throw new CommonCodeTypeException("The aCommonCodeTypeTrxValue to be updated is null!!!");
		}
		if (anCommonCodeType == null) {
			throw new CommonCodeTypeException("The ICommonCodeType to be created is null!!!");
		}
		ICommonCodeTypeTrxValue trxValue = formulateTrxValue(anITrxContext, aCommonCodeTypeTrxValue, anCommonCodeType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COMMON_CODE_TYPE);
		return operate(trxValue, param);

	}

	/**
	 * Checker approve the common code type
	 * @param anITrxContext - ITrxContext
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws CommonCodeTypeException on errors
	 */
	public ICommonCodeTypeTrxValue checkerApproveCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException {
		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (anICommonCodeTypeTrxValue == null) {
			throw new CommonCodeTypeException("The anIDocumentItemTrxValue to be approved is null!!!");
		}
		anICommonCodeTypeTrxValue = formulateTrxValue(anITrxContext, anICommonCodeTypeTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COMMON_CODE_TYPE);
		return operate(anICommonCodeTypeTrxValue, param);
	}

	/**
	 * Checker approve the common code type
	 * @param anITrxContext - ITrxContext
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws CommonCodeTypeException on errors
	 */
	public ICommonCodeTypeTrxValue checkerRejectCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException {
		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (anICommonCodeTypeTrxValue == null) {
			throw new CommonCodeTypeException("The anIDocumentItemTrxValue to be approved is null!!!");
		}
		anICommonCodeTypeTrxValue = formulateTrxValue(anITrxContext, anICommonCodeTypeTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COMMON_CODE_TYPE);
		return operate(anICommonCodeTypeTrxValue, param);
	}

	/**
	 * Checker approve the common code type
	 * @param anITrxContext - ITrxContext
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the interface representing the document
	 *         item trx obj
	 * @throws CommonCodeTypeException on errors
	 */

	public ICommonCodeTypeTrxValue makerEditRejectedCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue, ICommonCodeType anCommonCodeType)
			throws CommonCodeTypeException {

		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (anICommonCodeTypeTrxValue == null) {
			throw new CommonCodeTypeException("The anICommonCodeTypeTrxValue to be edited is null!!!");
		}
		anICommonCodeTypeTrxValue = formulateTrxValue(anITrxContext, anICommonCodeTypeTrxValue, anCommonCodeType);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COMMON_CODE_TYPE);
		return operate(anICommonCodeTypeTrxValue, param);

	}

	public ICommonCodeTypeTrxValue makerCancelUpdateCategory(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) throws CommonCodeTypeException {
		if (anITrxContext == null) {
			throw new CommonCodeTypeException("The anITrxContext is null!!!");
		}
		if (anICommonCodeTypeTrxValue == null) {
			throw new CommonCodeTypeException("The anICommonCodeTypeTrxValue to be edited is null!!!");
		}
		anICommonCodeTypeTrxValue = formulateTrxValue(anITrxContext, anICommonCodeTypeTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COMMON_CODE_TYPE);
		return operate(anICommonCodeTypeTrxValue, param);

	}

	/**
	 * Formulate the document item Trx Object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anCommonCodeType - ICommonCodeType
	 * @return ICommonCodeTypeTrxValue - the common category trx interface
	 *         formulated
	 */
	private ICommonCodeTypeTrxValue formulateTrxValue(ITrxContext anITrxContext, ICommonCodeType anCommonCodeType) {
		return formulateTrxValue(anITrxContext, null, anCommonCodeType);
	}

	/**
	 * Formulate the document item Trx Object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param anCommonCodeType - ICommonCodeType
	 * @return ICommonCodeTypeTrxValue - the common category trx interface
	 *         formulated
	 */
	private ICommonCodeTypeTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICommonCodeType anCommonCodeType) {
		ICommonCodeTypeTrxValue commonCodeTypeTrxValue = null;
		if (anICMSTrxValue != null) {
			commonCodeTypeTrxValue = new OBCommonCodeTypeTrxValue(anICMSTrxValue);
		}
		else {
			commonCodeTypeTrxValue = new OBCommonCodeTypeTrxValue();
		}
		commonCodeTypeTrxValue.setStagingCommonCodeType(anCommonCodeType);
		commonCodeTypeTrxValue = formulateTrxValue(anITrxContext, commonCodeTypeTrxValue);
		return commonCodeTypeTrxValue;
	}

	/**
	 * Formulate the document item trx object
	 * 
	 * @param anITrxContext - ITrxContext
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @return ICommonCodeTypeTrxValue - the common category trx interface
	 *         formulated
	 */
	private ICommonCodeTypeTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue) {
		anICommonCodeTypeTrxValue.setTrxContext(anITrxContext);
		anICommonCodeTypeTrxValue.setTransactionType(ICMSConstant.INSTANCE_COMMON_CODE_TYPE_LIST);
		return anICommonCodeTypeTrxValue;
	}

	/**
	 * Helper method to perform the document item transactions.
	 * 
	 * @param anICommonCodeTypeTrxValue - ICommonCodeTypeTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICommonCodeTypeTrxValue - the trx interface
	 */
	private ICommonCodeTypeTrxValue operate(ICommonCodeTypeTrxValue anICommonCodeTypeTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws CommonCodeTypeException {
		ICMSTrxResult result = operateForResult(anICommonCodeTypeTrxValue, anOBCMSTrxParameter);
		return (ICommonCodeTypeTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the document item transactions.
	 * 
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	private ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CommonCodeTypeException {
		try {
			ITrxController controller = (new CommonCodeTypeTrxControllerFactory()).getController(anICMSTrxValue,
					anOBCMSTrxParameter);
			if (controller == null) {
				throw new CommonDataManagerException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new CommonCodeTypeException("Failed to operate common code type workflow", e);
		}
		catch (Exception ex) {
			rollback();
			throw new CommonCodeTypeException(ex.toString());
		}

	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}
}
