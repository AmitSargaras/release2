package com.integrosys.cms.app.valuationAgency.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * @author rajib.aich Valuation Agency Trx controller to manage trx operations
 */

public class ValuationAgencyTrxController extends CMSTrxController {

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
	public ValuationAgencyTrxController() {
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
		return ICMSConstant.INSTANCE_VALUATION_AGENCY;
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
		if (action == null) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		if (value == null) {
			throw new TrxParameterException("Value is null in ITrxParameter!");
		}

		DefaultLogger.debug(this, "Action: " + action);

		String toState = value.getToState();
		String fromState = value.getFromState();
		DefaultLogger.debug(this, "toState: " + value.getToState());
		if (toState != null) {
			if (toState.equals(ICMSConstant.STATE_DRAFT)) {
				if (action
						.equals(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerUpdateDraftCreateValuationAgencyOperation");

				}
				if (action.equals(ICMSConstant.ACTION_MAKER_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerUpdateValuationAgencyOperation");
				}

			}
		}

		if (toState != null) {
			if (toState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
				if (action
						.equals(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"CheckerApproveUpdateValuationAgencyOperation");

				}
				if (action
						.equals(ICMSConstant.ACTION_MAKER_ENABLE_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEnableValuationAgencyOperation");

				}
				if (action
						.equals(ICMSConstant.ACTION_CHECKER_REJECT_DISABLE_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedEnableValuationAgencyOperation");

				}

			}
		}

		if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_VALUATION_AGENCY)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerSaveValuationAgencyOperation");
		}

		if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"ValuationAgencyOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
			if (action
					.equals(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveCreateValuationAgencyOperation");
			}

			if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectValuationAgencyOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
			if (action
					.equals(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdateValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectValuationAgencyOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		}

		else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerUpdateValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_DISABLE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerDisableValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_ENABLE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerEnableValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerSaveUpdateValuationAgencyOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_VALUATION_AGENCY)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedCreateValuationAgencyOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedUpdateValuationAgencyOperation");
				} 
				
			//	else if (fromState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
			//		return (ITrxOperation) getNameTrxOperationMap().get(
			//				"MakerEditRejectedDeleteValuationAgencyOperation");
			//	}

				else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEnableValuationAgencyOperation");
				}
				// else if
				// (fromState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
				// return (ITrxOperation)
				// getNameTrxOperationMap().get("MakerCloseRejectedDisableValuationAgencyOperation");
				// }

				else if (action
						.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_VALUATION_AGENCY)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerDisableValuationAgencyOperation");
				}

			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_VALUATION_AGENCY)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedCreateValuationAgencyOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedUpdateValuationAgencyOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedDeleteValuationAgencyOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedEnableValuationAgencyOperation");
				}

				else if (fromState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
					return (ITrxOperation) getNameTrxOperationMap()
							.get(
									"MakerCloseRejectedDisableValuationAgencyOperation");
				}
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_DISABLE)) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_ENABLE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerEnableValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectDirectorMasterOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectDirectorMasterOperation");
			}

			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		}

		else if (toState.equals(ICMSConstant.STATE_ENABLE)) {
			if (action
					.equals(ICMSConstant.ACTION_MAKER_DISABLE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerDisableValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectDirectorMasterOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerUpdateDirectorMasterOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerSaveUpdateDirectorMasterOperation");
			}else if (action
					.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerSaveUpdateValuationAgencyOperation");
			}

			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		}

		else if (toState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
			if (action
					.equals(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveUpdateValuationAgencyOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AGENCY)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerDisableRejectValuationAgencyOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		}

		else if (action
				.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_VALUATION_AGENCY)) {
			return (ITrxOperation) getNameTrxOperationMap().get(
					"MakerCloseDraftValuationAgencyOperation");
		}

		throw new TrxParameterException(
				"To State does not match presets! No operations found!");
	}
}
