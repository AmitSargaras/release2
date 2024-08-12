package com.integrosys.cms.app.directorMaster.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
  * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 *  Director Master Trx controller to manage trx operations
 */
public class DirectorMasterTrxController extends CMSTrxController {

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
    public DirectorMasterTrxController() {
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
        return ICMSConstant.INSTANCE_DIRECTOR_MASTER;
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
            	if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DIRECTOR_MASTER)) {
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDraftCreateDirectorMasterOperation");
            		
            	}
            	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER)) {
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");
            	}
            	if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIRECTOR_MASTER)) {
                	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftDirectorMasterOperation");
            	       	}  
            	if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER)) {
            		if(fromState.equals(ICMSConstant.STATE_PENDING_PERFECTION))
            				{
           		 return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateDirectorMasterOperation");
            				}
            		else
            		{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");
            			
            		}
           	}
            	}
            }
        if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_DIRECTOR_MASTER)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveDirectorMasterOperation");
        }
        if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER)) {
            return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateDirectorMasterOperation");
        }
        
        if(toState!=null){
            if(toState.equals(ICMSConstant.STATE_PENDING_ENABLE)){
            	if (action.equals(ICMSConstant.ACTION_MAKER_APPROVE_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateDirectorMasterOperation");
            	
            	}
            	if (action.equals(ICMSConstant.ACTION_MAKER_ENABLE_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerEnableDirectorMasterOperation");
                	
            	}
            	if (action.equals(ICMSConstant.ACTION_MAKER_ENABLE_REJECT_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedEnableDirectorMasterOperation");
                	
            	}
            	
            	}
            }
        if(toState!=null){
            if(toState.equals(ICMSConstant.STATE_CLOSED)){
            	if (action.equals(ICMSConstant.ACTION_MAKER_DISABLE_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerDisableDirectorMasterOperation");	
            	}
            	if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");	
            	}
            	
            	}
        }
                        
        if ((toState == null) || (toState.equals(ICMSConstant.STATE_ND))) {
            if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerCreateDirectorMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DIRECTOR_MASTER)) {
            	
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveCreateDirectorMasterOperation");
            }

            if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDirectorMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_ACTIVE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_DISABLE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDisableDirectorMasterOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_ENABLE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerEnableDirectorMasterOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");
            }
            throw new TrxParameterException("Unknow Action: " + action + " with toState: " + toState);
        }    
        else if (toState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDirectorMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        } else if (toState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
            if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerDisableRejectDirectorMasterOperation");
            }
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        //added by venkat for enable process.
        else if (toState.equals(ICMSConstant.STATE_DISABLE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_ENABLE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerEnableDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDirectorMasterOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDirectorMasterOperation");
            }           
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }  
        else if (toState.equals(ICMSConstant.STATE_REJECTED)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedCreateDirectorMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedUpdateDirectorMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEditRejectedDeleteDirectorMasterOperation");
                }else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerEnableDirectorMasterOperation");
                }else if (action.equals(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER))
            	{
                	return (ITrxOperation) getNameTrxOperationMap().get("MakerDisableDirectorMasterOperation");	
            	}else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER))
            	{
            		return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");	
            	}
                
                
            }
            else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIRECTOR_MASTER)) {
                if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedCreateDirectorMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedUpdateDirectorMasterOperation");
                } else if (fromState.equals(ICMSConstant.STATE_PENDING_DISABLE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedDisableDirectorMasterOperation");
                }
                else if (fromState.equals(ICMSConstant.STATE_PENDING_ENABLE)) {
                    return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedEnableDirectorMasterOperation");
                }
            
            } 
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }
        else if (toState.equals(ICMSConstant.STATE_ENABLE)) {
            if (action.equals(ICMSConstant.ACTION_MAKER_DISABLE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerDisableDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectDirectorMasterOperation");
            }else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateDirectorMasterOperation");
            } else if (action.equals(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER)) {
                return (ITrxOperation) getNameTrxOperationMap().get("MakerSaveUpdateDirectorMasterOperation");
            }          
            
            throw new TrxParameterException("Unknown Action: " + action + " with toState: " + toState);
        }  
               
        
        else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_DIRECTOR_MASTER)) {
        	return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftDirectorMasterOperation");
        }
        throw new TrxParameterException("To State does not match presets! No operations found!");
    }

}
