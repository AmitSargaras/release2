package com.integrosys.cms.app.limit.trx;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.CMSTrxController;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Transaction controller to be used for workflow purpose. Mainly used by
 * Facility.
 * 
 * @author Chong Jun Yong
 * @author Andy Wong
 * @since 03.09.2008
 */
public class FacilityTrxController extends CMSTrxController {

	private static final long serialVersionUID = -3005285752346245737L;

	/** instance or transaction type of this trx controller */
	static final String INSTANCE_FACILITY = "FACILITY";

	public static final String ACTION_MAKER_SAVE = "MAKER_SAVE";

	public static final String ACTION_MAKER_CREATE = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_REJECT = "CHECKER_REJECT";

	public static final String ACTION_CHECKER_APPROVE = "CHECKER_APPROVE";

	public static final String ACTION_SYSTEM_APPROVE = "SYSTEM_APPROVE";

	public static final String ACTION_SYSTEM_UPDATE = "SYSTEM_UPDATE";

	public static final String ACTION_SYSTEM_REJECT = "SYSTEM_REJECT";

	public static final String ACTION_CHECKER_APPROVE_PASS = "CHECKER_APPROVE_PASS";

	public static final String ACTION_CHECKER_APPROVE_FAIL = "CHECKER_APPROVE_FAIL";

	private Map nameTrxOperationMap;

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
	 * list of application type of the facilities that is allowed to be stp to
	 * host
	 */
	private List applicationTypesStpAllowedList;

	public void setApplicationTypesStpAllowedList(List applicationTypesStpAllowedList) {
		this.applicationTypesStpAllowedList = applicationTypesStpAllowedList;
	}

	public List getApplicationTypesStpAllowedList() {
		return applicationTypesStpAllowedList;
	}

	/**
	 * @return the nameTrxOperationMap
	 */
	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	/**
	 * @param nameTrxOperationMap the nameTrxOperationMap to set
	 */
	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		Validate.notNull(value, "'value' must not be null.");
		Validate.notNull(param, "'param' must not be null.");

		String action = param.getAction();

		if (ACTION_MAKER_SAVE.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveFacilityOperation");
		}

		if (ACTION_MAKER_CREATE.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateFacilityOperation");
		}

		if (ACTION_MAKER_CLOSE.equals(action)) {
			if (ICMSConstant.STATE_REJECTED_CREATE.equals(value.getFromState())
					|| ICMSConstant.STATE_ND.equals(value.getFromState())) {
				return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseNewFacilityOperation");
			}

			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseFacilityOperation");
		}

		if (ACTION_MAKER_UPDATE.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateFacilityOperation");
		}

		if (ACTION_CHECKER_REJECT.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectFacilityOperation");
		}

		if (ACTION_CHECKER_APPROVE.equals(action)) {
			IFacilityTrxValue trxValue = (IFacilityTrxValue) value;

			ITrxContext trxContext = trxValue.getTrxContext();
			ILimitProfile limitProfile = trxContext.getLimitProfile();

			String applicationType = limitProfile.getApplicationType();
			if (getApplicationTypesStpAllowedList().contains(applicationType) && isAccessStpSystem()) {
				if (trxContext.getStpAllowed()) {
					return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveFacilityStpPassedOperation");
				}
				else {
					return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveFacilityStpFailedOperation");
				}
			}

			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveFacilityOperation");
		}

		if (ACTION_SYSTEM_APPROVE.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemApproveFacilityOperation");
		}

		if (ACTION_SYSTEM_UPDATE.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemUpdateFacilityOperation");
		}

		if (ACTION_SYSTEM_REJECT.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemRejectFacilityOperation");
		}

		throw new IllegalArgumentException("cannot find operation using action [" + action + "]");
	}

	public String getInstanceName() {
		return INSTANCE_FACILITY;
	}

}
