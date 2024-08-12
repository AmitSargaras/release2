/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralTrxController.java,v 1.23 2004/08/16 02:43:28 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This trx controller is to be used in Collateral. It provides factory for trx
 * operations that are specific to collateral.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @author Andy Wong
 * @since 2004/08/16 02:43:28
 */
public class CollateralTrxController extends CMSTrxController {

	private static final long serialVersionUID = 8763429554568027794L;

	private Map nameTrxOperationMap;

	private String[] sourceIdsStpAllowedUponApproval;

	private String[] exceptionSourceIdsStpAllowedForEmptyHostCollateralId;

	/**
	 * to indicate whether this controller has the access to stp system, default
	 * is <code>false</code>
	 */
	private boolean accessStpSystem = false;

	/**
	 * Indicate whether the transaction controller should have the access to the
	 * stp system, so even if the collateral source id appear in the list of
	 * source id which is set into
	 * {@link #setSourceIdsStpAllowedUponApproval(List)}, the stp wont taken
	 * place. Rather only normal flow is invoked
	 * 
	 * @param accessStpSystem indicate whether this controller have the access
	 *        to stp system
	 */
	public void setAccessStpSystem(boolean accessStpSystem) {
		this.accessStpSystem = accessStpSystem;
	}

	/**
	 * @return to indicate whether this controller has the access to stp system
	 */
	public boolean isAccessStpSystem() {
		return accessStpSystem;
	}

	/**
	 * To set a list of source id which to match the source id of the
	 * collateral, to determine whether the collateral should be stp to host
	 * when approval action is taken.
	 * 
	 * @param sourceIdsStpAllowedUponApproval list of source id of collateral
	 *        that allowed stp upon approval
	 */
	public void setSourceIdsStpAllowedUponApproval(String[] sourceIdsStpAllowedUponApproval) {
		this.sourceIdsStpAllowedUponApproval = sourceIdsStpAllowedUponApproval;
	}

	/**
	 * <p>
	 * List of source ids for exception case that collateral's host collateral
	 * is empty which required to be stp.
	 * <p>
	 * This would mean that, for collateral having host collateral id, and fall
	 * under {@link #setSourceIdsStpAllowedUponApproval}, would not get stp.
	 * 
	 * @param exceptionSourceIdsStpAllowedForEmptyHostCollateralId list of
	 *        source ids.
	 */
	public void setExceptionSourceIdsStpAllowedForEmptyHostCollateralId(
			String[] exceptionSourceIdsStpAllowedForEmptyHostCollateralId) {
		this.exceptionSourceIdsStpAllowedForEmptyHostCollateralId = exceptionSourceIdsStpAllowedForEmptyHostCollateralId;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	protected ITrxOperation getTrxOperation(String name) throws TrxParameterException {
		ITrxOperation op = (ITrxOperation) getNameTrxOperationMap().get(name);

		if (op == null) {
			throw new TrxParameterException("trx operation retrieved is null for given name [" + name + "]");
		}

		return op;
	}

	/**
	 * Default Constructor
	 */
	public CollateralTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COLLATERAL;
	}

	/**
	 * Get transaction operation given the transaction value and parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
	 *         transaction parameter is invalid
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to get the operation given the transaction value and
	 * transaction parameter.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return transaction operation
	 * @throws TrxParameterException if the transaction parameter is invalid
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String fromState = value.getStatus();
		DefaultLogger.debug(this, "FromState: " + fromState);

		if (fromState == null) {
			throw new TrxParameterException("From State is null!");
		}
		String action = param.getAction();
		DefaultLogger.debug(this, "Action: " + action);

		if (action == null) {
			throw new TrxParameterException("Action must not be null");
		}

		if (ICMSConstant.ACTION_SUBSCRIBE_CREATE_COL.equals(action)) {
			return getTrxOperation("SubscribeCreateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_MAKER_SAVE_COL.equals(action)) {
			return getTrxOperation("MakerSaveCollateralOperation");
		}
		else if (ICMSConstant.ACTION_MAKER_UPDATE_COL.equals(action)) {
			return getTrxOperation("MakerUpdateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_FULL_DELETE_COL.equals(action)) {
			return getTrxOperation("FullDeleteCollateralOperation");
		}
		else if (ICMSConstant.ACTION_PART_DELETE_COL.equals(action)) {
			return getTrxOperation("PartDeleteCollateralOperation");
		}
		else if (ICMSConstant.ACTION_SYSTEM_UPDATE_COL.equals(action)) {
			return getTrxOperation("SystemUpdateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_HOST_UPDATE_COL.equals(action)) {
			return getTrxOperation("HostUpdateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_MAKER_DELETE_COL.equals(action)) {
			return getTrxOperation("MakerDeleteCollateralOperation");
		}
		else if (ICMSConstant.ACTION_CREATE_LIEN.equals(action)) {
			return getTrxOperation("MakerCreateLienOperation");
		}
		else if (ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COL.equals(action)) {
			
			//Commented for HDFC (STP is in not used)
			/*ICollateralTrxValue trxValue = (ICollateralTrxValue) value;
			ICollateral stagingCollateral = trxValue.getStagingCollateral();
			ITrxContext trxContext = trxValue.getTrxContext();

			String sourceId = stagingCollateral.getSourceId();
			if ((ArrayUtils.contains(this.sourceIdsStpAllowedUponApproval, sourceId) || (ArrayUtils.contains(
					this.exceptionSourceIdsStpAllowedForEmptyHostCollateralId, sourceId))
					&& stagingCollateral.getSCISecurityID() == null)
					&& isAccessStpSystem()) {
				if (trxContext.getStpAllowed()) {
					return getTrxOperation("CheckerApproveUpdateCollateralStpPassedOperation");
				}
				else {
					return getTrxOperation("CheckerApproveUpdateCollateralStpFailedOperation");
				}
			}*/

			return getTrxOperation("CheckerApproveUpdateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_COL.equals(action)) {
			return getTrxOperation("CheckerRejectUpdateCollateralOperation");
		}
		else if (ICMSConstant.ACTION_SYSTEM_CANCEL_COL.equals(action)) {
			return getTrxOperation("SystemCancelCollateralOperation");
		}
		else if (ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_COL.equals(action)) {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) value;
			ICollateral stagingCollateral = trxValue.getStagingCollateral();

			String sourceId = stagingCollateral.getSourceId();
			if (ArrayUtils.contains(this.sourceIdsStpAllowedUponApproval, sourceId) && isAccessStpSystem()) {
				return getTrxOperation("CheckerApproveDeleteCollateralStpPassedOperation");
			}

			return getTrxOperation("CheckerApproveDeleteCollateralOperation");
		}
		else if (ICMSConstant.ACTION_CHECKER_REJECT_DELETE_COL.equals(action)) {
			return getTrxOperation("CheckerRejectDeleteCollateralOperation");
		}
		else if (ICMSConstant.ACTION_MAKER_CANCEL_COL.equals(action)) {
			return getTrxOperation("MakerCancelCollateralOperation");
		}
		else if (ICMSConstant.ACTION_SYSTEM_REJECT_COL.equals(action)) {
			return getTrxOperation("SystemRejectCollateralOperation");
		}
		else if (ICMSConstant.ACTION_SYSTEM_APPROVE_COL.equals(action)) {
			return getTrxOperation("SystemApproveCollateralOperation");
		}
		else if (ICMSConstant.ACTION_MAKER_CREATE_COL.equals(action)) {
			return getTrxOperation("MakerCreateCollateralOperation");
		}

		throw new TrxParameterException("No operation found for action name [" + action + "] for 'COL' instance");
	}
}