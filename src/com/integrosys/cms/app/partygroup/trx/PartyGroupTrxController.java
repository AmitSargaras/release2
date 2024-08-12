package com.integrosys.cms.app.partygroup.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author bharat waghela Party Group Trx controller to manage trx operations
 */
public class PartyGroupTrxController extends CMSTrxController {

	private Map nameTrxOperationMap;

	/**
	 * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
	 *         ITrxOperation name will be the same.
	 */
	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	/**
	 * Default Constructor
	 */
	public PartyGroupTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table. Not
	 * implemented.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_PARTY_GROUP;
	}

	/**
	 * Returns an ITrxOperation object
	 * 
	 * @param value
	 *            - ITrxValue
	 * @param param
	 *            - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException
	 *             on error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to factory the operations
	 * 
	 * @param value
	 *            - ITrxValue
	 * @param param
	 *            - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException
	 *             on error
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param)
			throws TrxParameterException {
		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
		if (toState != null) {
			if (toState.equals(ICMSConstant.STATE_DRAFT)) {
				if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_PARTY_GROUP)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerUpdatePartyGroupOperation");
				} else if (action
						.equals(ICMSConstant.ACTION_MAKER_CREATE_PARTY_GROUP)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCreateSavedPartyGroupOperation");
				} else if (action
						.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PARTY_GROUP)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftPartyGroupOperation");
				}
				throw new TrxParameterException("Unknown Action: " + action
						+ " with toState: " + toState);

			}
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PARTY_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerSaveUpdatePartyGroupOperation");
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_PARTY_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerSavePartyGroupOperation");
		}
		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCreatePartyGroupOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveCreatePartyGroupOperation");
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPartyGroupOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerUpdatePartyGroupOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_DELETE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerDeletePartyGroupOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivatePartyGroupOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdatePartyGroupOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPartyGroupOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveDeletePartyGroupOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPartyGroupOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PARTY_GROUP)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedCreatePartyGroupOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedUpdatePartyGroupOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedDeletePartyGroupOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedEnablePartyGroupOperation");
				}
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PARTY_GROUP)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedCreatePartyGroupOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedUpdatePartyGroupOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)
						|| fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedDisablePartyGroupOperation");
				}
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_DELETED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivatePartyGroupOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveEnablePartyGroupOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_PARTY_GROUP)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectActivatePartyGroupOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (action
				.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PARTY_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerCloseDraftPartyGroupOperation");
		}
		throw new TrxParameterException(
				"To State does not match presets! No operations found!");
	}

}
