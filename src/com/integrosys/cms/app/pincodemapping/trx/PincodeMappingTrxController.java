package com.integrosys.cms.app.pincodemapping.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class PincodeMappingTrxController extends CMSTrxController{

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
	public PincodeMappingTrxController() {
		super();
	}
	
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_PINCODE_MAPPING;
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
				if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_PINCODE_MAPPING)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerUpdatePincodeMappingOperation");
				} else if (action
						.equals(ICMSConstant.ACTION_MAKER_CREATE_PINCODE_MAPPING)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCreateSavedPincodeMappingOperation");
				} else if (action
						.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PINCODE_MAPPING)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseDraftPincodeMappingOperation");
				}
				
				throw new TrxParameterException("Unknown Action: " + action
						+ " with toState: " + toState);

			}
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PINCODE_MAPPING)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerSaveUpdatePincodeMappingOperation");
		}
		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_PINCODE_MAPPING)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerSavePincodeMappingOperation");
		}
		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerCreatePincodeMappingOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveCreatePincodeMappingOperation");
			}

			if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPincodeMappingOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerUpdatePincodeMappingOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_DELETE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerDeletePincodeMappingOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivePincodeMappingOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdatePincodeMappingOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPincodeMappingOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveDeletePincodeMappingOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectPincodeMappingOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PINCODE_MAPPING)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedCreatePincodeMappingOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedUpdatePincodeMappingOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedDeletePincodeMappingOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedEnablePincodeMappingOperation");
				}
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PINCODE_MAPPING)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedCreatePincodeMappingOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedUpdatePincodeMappingOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)
						|| fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedDisablePincodeMappingOperation");
				}
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_DELETED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_PINCODE_MAPPING)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivatePincodeMappingOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		}/* else if (toState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
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
		} */else if (action
				.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PINCODE_MAPPING)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerCloseDraftPincodeMappingOperation");
		}
		throw new TrxParameterException(
				"To State does not match presets! No operations found!");
	}

	
}
