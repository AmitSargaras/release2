package com.integrosys.cms.app.udf.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class UdfTrxController extends CMSTrxController{

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
    public UdfTrxController() {
    	super();
	}
    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     * Not implemented.
     *
     * @return String
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_UDF;
    }
    
    /**
     * Returns an ITrxOperation object
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException
     *          on error
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        ITrxOperation op = factoryOperation(value, param);
        DefaultLogger.debug(this, "Returning Operation: " + op);
        return op;
    }
    
    /**
     * Helper method to factory the operations
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws TrxParameterException on error
     */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        String action = param.getAction();
        if (action==null) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }
        if (value==null) {
            throw new TrxParameterException("Value is null in ITrxParameter!");
        }
        
        DefaultLogger.debug(this, "Action: " + action);

        String toState = value.getToState();
        String fromState = value.getFromState();
        DefaultLogger.debug(this, "toState: " + value.getToState());
        if(toState!=null){
        	 if(toState.equals(ICMSConstant.STATE_DRAFT)){
        		 if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_UDF)) {
        			 return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateUdfOperation");
        		 }
        		 if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_UDF)) {
             		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateUdfOperation");
             	}
        	}
        }
           if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUdfOperation");
            }
            if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateUdfOperation");
            }
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateUdfOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } 
        else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateUdfOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUdfOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } 
        else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
        	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_UDF)) {
        		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateUdfOperation");
        	}
        	else if (action
					.equals(ICMSConstant.ACTION_MAKER_DELETE_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerDeleteUdfOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivateUdfOperation");
			}
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        } 
        else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateUdfOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_UDF)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectUdfOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveDeleteUdfOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectUdfOperation");
			}
			throw new TrxParameterException("Unknown Action: " + action
					+ " with toState: " + toState);
		}
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UDF)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateUdfOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateUdfOperation");
                }else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedDeleteUdfOperation");
				} else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerEditRejectedEnableUdfOperation");
				}
            } else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UDF)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateUdfOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateUdfOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)
						|| fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					return (ITrxOperation) getNameTrxOperationMap().get(
							"MakerCloseRejectedDisableUdfOperation");
				}
            }
        }  else if (toState.equals(ICMSConstant.STATE_DELETED)) {
			if (action.equals(ICMSConstant.ACTION_MAKER_ACTIVATE_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"MakerActivateUdfOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		} else if (toState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
			if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerApproveEnableUdfOperation");
			} else if (action
					.equals(ICMSConstant.ACTION_CHECKER_REJECT_UDF)) {
				return (ITrxOperation) getNameTrxOperationMap().get(
						"CheckerRejectActivateUdfOperation");
			}
			throw new TrxParameterException("Unknow Action: " + action
					+ " with toState: " + toState);
		}    
       
        else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_UDF)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftUdfOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }
}
