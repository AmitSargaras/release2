/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OfficeCheckListTrxController.java,v 1.4 2005/07/08 11:40:37 hshii Exp $
 */

package com.integrosys.cms.app.checklist.trx;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.transaction.CMSTrxForwardOperation;
import com.integrosys.cms.app.transaction.CMSTrxForwardOperationBase;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/08 11:40:37 $ Tag: $Name: $
 */
public class OfficeCheckListTrxController extends CMSTrxController {
	/**
           * 
           */
	public OfficeCheckListTrxController() {
		super();
		initOperationRegistry();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperationFactory#getOperation
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		// can lookup TR_STATE_MATRIX by instance, fromstate, operation
		ITrxOperation extOp = new CMSTrxForwardOperation();// Default operation
		ICMSTrxValue extValue = (ICMSTrxValue) value;
		// String fromState = extValue.getFromState(); current fromState should
		// be last toState!!!!! or getStatus
		String fromState = extValue.getToState();
		String strOp = extValue.getOpDesc();

		if (strOp == null) {
			return extOp;
		}

		String classNameOfOp = null;
		DefaultLogger.debug(this, "-----fromState=:" + fromState);
		DefaultLogger.debug(this, "-----operation=:" + strOp);
		classNameOfOp = getOperationClassName(fromState, strOp);
		DefaultLogger.debug(this, "-----Operation get:" + classNameOfOp);
		if (classNameOfOp != null) {
			try {
				Object obj = Class.forName(classNameOfOp).newInstance();
				ITrxOperation gotOp = (ITrxOperation) obj;
				extOp = gotOp;
			}
			catch (ClassNotFoundException e) {
				DefaultLogger.debug(this, "No  Impletation Operation Class Found");
			}
			catch (InstantiationException e) {
				DefaultLogger.debug(this, "Failed on Instantiate Operation");
			}
			catch (IllegalAccessException e) {
				DefaultLogger.debug(this, "Failed on Access to Operation");
			}
			catch (ClassCastException e) {
				DefaultLogger.debug(this, "Imcopatible ExtCMSTrxOperation");
			}

		}

		// Operation to be done
		if (CMSTrxForwardOperationBase.class.isInstance(extOp)) {
			((CMSTrxForwardOperationBase) extOp).setOperationName(strOp);
		}

		return extOp;
	}

	public String getInstanceName() {
		return InstanceName;
	}

	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	private String InstanceName = "";

	/**
	 * Register operation class
	 * @author heju
	 * 
	 */
	private void initOperationRegistry() {
		/*
		 * operations.put(ICMSConstant.STATE_PENDING_UPDATE + DLT +
		 * ICMSConstant.ACTION_FORWARD,
		 * "com.integrosys.cms.app.transaction.CMSTrxForwardOperation");
		 */
		operations.put(ICMSConstant.STATE_PENDING_UPDATE + DLT + ICMSConstant.ACTION_FORWARD,
				"com.integrosys.cms.app.checklist.trx.CheckerForwardCheckListReceiptOperation");

		operations.put(ICMSConstant.STATE_PENDING_AUTH + DLT + ICMSConstant.ACTION_REJECT,
				"com.integrosys.cms.app.checklist.trx.OfficeCheckListRejectOperation");
		operations.put(ICMSConstant.STATE_PENDING_AUTH + DLT + ICMSConstant.ACTION_APPROVE,
				"com.integrosys.cms.app.checklist.trx.OfficeCheckListApproveOperation");
		operations.put(ICMSConstant.STATE_PENDING_AUTH + DLT + ICMSConstant.ACTION_FORWARD,
				"com.integrosys.cms.app.transaction.CMSTrxForwardOperation");

		operations.put(ICMSConstant.STATE_PENDING_OFFICE + DLT + ICMSConstant.ACTION_APPROVE,
				"com.integrosys.cms.app.checklist.trx.OfficeCheckListApproveOperation");
		operations.put(ICMSConstant.STATE_PENDING_OFFICE + DLT + ICMSConstant.ACTION_REJECT,
				"com.integrosys.cms.app.checklist.trx.CMSTrxForwardOperation");
		operations.put(ICMSConstant.STATE_PENDING_OFFICE + DLT + ICMSConstant.ACTION_FORWARD,
				"com.integrosys.cms.app.transaction.CMSTrxForwardOperation");
		operations.put(ICMSConstant.STATE_PENDING_OFFICE + DLT + ICMSConstant.ACTION_BACKWARD,
				"com.integrosys.cms.app.transaction.CMSTrxForwardOperation");

		operations.put(ICMSConstant.STATE_PENDING_VERIFY + DLT + ICMSConstant.ACTION_REJECT,
				"com.integrosys.cms.app.checklist.trx.OfficeCheckListRejectOperation");

	}

	private String getOperationClassName(String from, String op) {
		return (String) operations.get(from + DLT + op);
	}

	private HashMap operations = new HashMap();

	private final static String DLT = "_";
}
