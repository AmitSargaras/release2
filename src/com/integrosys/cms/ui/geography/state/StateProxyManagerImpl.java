package com.integrosys.cms.ui.geography.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateBusManager;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;


/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class StateProxyManagerImpl implements IStateProxyManager{

	private IStateBusManager stateBusManager;
	
	private IStateBusManager stagingStateBusManager;
	
    private ITrxControllerFactory trxControllerFactory;

    public IStateBusManager getStateBusManager() {
		return stateBusManager;
	}

	public void setStateBusManager(IStateBusManager stateBusManager) {
		this.stateBusManager = stateBusManager;
	}

	public IStateBusManager getStagingStateBusManager() {
		return stagingStateBusManager;
	}

	public void setStagingStateBusManager(IStateBusManager stagingStateBusManager) {
		this.stagingStateBusManager = stagingStateBusManager;
	}


	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}


	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IStateTrxValue checkerApproveState(ITrxContext anITrxContext,
			IStateTrxValue anIStateTrxValue)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anIStateTrxValue == null) {
            throw new NoSuchGeographyException
                    ("The IStateTrxValue to be updated is null!!!");
        }
        anIStateTrxValue = formulateTrxValue(anITrxContext, anIStateTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_STATE);
        return operate(anIStateTrxValue, param);
	}


	public IStateTrxValue checkerRejectState(ITrxContext anITrxContext,IStateTrxValue anIStateTrxValue)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anIStateTrxValue == null) {
            throw new NoSuchGeographyException("The IStateTrxValue to be updated is null!!!");
        }
        anIStateTrxValue = formulateTrxValue(anITrxContext, anIStateTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_STATE);
        return operate(anIStateTrxValue, param);
	}


	public IState createState(IState State)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getStateBusManager().createState(State);
	}
	
	public IStateTrxValue getStateById(long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
	           throw new NoSuchGeographyException("Invalid StateId");
	       }
		   IStateTrxValue trxValue = new OBStateTrxValue();
	       trxValue.setReferenceID(String.valueOf(id));
	       trxValue.setTransactionType(ICMSConstant.INSTANCE_STATE);
	       OBCMSTrxParameter param = new OBCMSTrxParameter();
	       param.setAction(ICMSConstant.ACTION_READ_STATE);
	       return operate(trxValue, param);
	}


	public IStateTrxValue getStateByTrxID(String aTrxID)throws NoSuchGeographyException, TransactionException,CommandProcessingException {
		IStateTrxValue trxValue = new OBStateTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_STATE);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_STATE_ID);
        return operate(trxValue, param);
	}


	public IStateTrxValue getStateTrxValue(long aStateId)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		if (aStateId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
	           throw new NoSuchGeographyException("Invalid StateId");
	       }
		IStateTrxValue trxValue = new OBStateTrxValue();
        trxValue.setReferenceID(String.valueOf(aStateId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_STATE);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_STATE);
        return operate(trxValue, param);
	}


	public IStateTrxValue makerCloseRejectedState(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anIStateTrxValue == null) {
            throw new NoSuchGeographyException("The IStateTrxValue to be updated is null!!!");
        }
        anIStateTrxValue = formulateTrxValue(anITrxContext, anIStateTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STATE);
        return operate(anIStateTrxValue, param);
	}

	public IStateTrxValue makerDeleteState(ITrxContext anITrxContext,IStateTrxValue anIStateTrxValue, IState anIState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anIState == null) {
            throw new NoSuchGeographyException("The IState to be updated is null !!!");
        }
        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anIStateTrxValue, anIState);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_STATE);
        return operate(trxValue, param);
	}


	public IStateTrxValue makerEditRejectedState(ITrxContext anITrxContext,IStateTrxValue anIStateTrxValue, IState anState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (anIStateTrxValue == null) {
	            throw new NoSuchGeographyException("The IStateTrxValue to be updated is null!!!");
	        }
	        if (anState == null) {
	            throw new NoSuchGeographyException("The IState to be updated is null !!!");
	        }
	        anIStateTrxValue = formulateTrxValue(anITrxContext, anIStateTrxValue, anState);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_STATE);
	        return operate(anIStateTrxValue, param);
	}


	public IStateTrxValue makerUpdateState(ITrxContext anITrxContext,IStateTrxValue anICCStateTrxValue, IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (anICCState == null) {
	            throw new NoSuchGeographyException("The IStateTrxValue to be updated is null !!!");
	        }
	        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anICCStateTrxValue, anICCState);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_STATE);
	        return operate(trxValue, param);		
	}


	public IState updateState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		if(state!=null){
			return (IState)getStateBusManager().updateState(state);
		}else{
			 throw new NoSuchGeographyException("Other Bank Object is null.");
		 }	
		
	}

	public SearchResult listState(String type,String text)throws NoSuchGeographyException {
		try {
			return getStateBusManager().listState(type, text);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing State");
		}
	}
	
	public IStateTrxValue makerCreateState(ITrxContext anITrxContext,IStateTrxValue anIStateTrxValue, IState anState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anState == null) {
            throw new NoSuchGeographyException("The IStateTrxValue to be created is null !!!");
        }
        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anIStateTrxValue, anState);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_STATE);
        return operate(trxValue, param);		
	}

	
	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anIState
	 * @return
	 */
	private IStateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IState anIState) {
        IStateTrxValue stateTrxValue = null;
        if (anICMSTrxValue != null) {
            stateTrxValue = new OBStateTrxValue(anICMSTrxValue);
        } else {
            stateTrxValue = new OBStateTrxValue();
        }
        stateTrxValue = formulateTrxValue(anITrxContext, (IStateTrxValue) stateTrxValue);
        stateTrxValue.setStagingState(anIState);
        return stateTrxValue;
    }
	
	public IStateTrxValue makerUpdateSaveUpdateState(	ITrxContext anITrxContext,IStateTrxValue anICCStateTrxValue,IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (anICCState == null) {
	            throw new NoSuchGeographyException("The ICCState to be updated is null !!!");
	        }
	        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anICCStateTrxValue, anICCState);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_STATE);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create State  .
	 */

	public IStateTrxValue makerUpdateSaveCreateState(ITrxContext anITrxContext,IStateTrxValue anICCStateTrxValue,IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		 if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (anICCState == null) {
	            throw new NoSuchGeographyException("The ICCState to be updated is null !!!");
	        }
	        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anICCStateTrxValue, anICCState);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_STATE);
	        return operate(trxValue, param);
	}
	
	public IStateTrxValue makerSaveState(ITrxContext anITrxContext, IState anICCState) throws NoSuchGeographyException,TrxParameterException,TransactionException {
        if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anICCState == null) {
            throw new NoSuchGeographyException("The ICCState to be updated is null !!!");
        }

        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCState);
        trxValue.setFromState("DRAFT");
        trxValue.setStatus("PENDING_PERFECTION");
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_STATE);
        return operate(trxValue, param);
    }
	
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIStateTrxValue
	  * @return IStateTrxValue
	  */
	 private IStateTrxValue formulateTrxValue(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) {
	        anIStateTrxValue.setTrxContext(anITrxContext);
	        anIStateTrxValue.setTransactionType(ICMSConstant.INSTANCE_STATE);
	        return anIStateTrxValue;
	    }
	
	 private IStateTrxValue operate(IStateTrxValue anIStateTrxValueTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws NoSuchGeographyException,TrxParameterException,TransactionException {
	     ICMSTrxResult result = operateForResult(anIStateTrxValueTrxValue, anOBCMSTrxParameter);
	     return (IStateTrxValue) result.getTrxValue();
	 }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws NoSuchGeographyException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");
	
			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
	
		 catch (NoSuchGeographyException ex) {
			 throw new NoSuchGeographyException(ex.toString());
		 }
	 }

	public List  getStateList(long stateId) throws NoSuchGeographyException {
		return getStateBusManager().getStateList(stateId);
	}

	public List  getCountryList(long countryId) throws NoSuchGeographyException {
		return getStateBusManager().getCountryList(countryId);
	}
	
	public List getRegionList(long countryId) throws NoSuchGeographyException {
		return getStateBusManager().getRegionList(countryId);
	}

	public boolean checkActiveCities(IState state) {
		return getStateBusManager().checkActiveCities(state);
	}
	
	public boolean checkInActiveRegions(IState state) {
		return getStateBusManager().checkInActiveRegions(state);
	}

	public IStateTrxValue makerActivateState(ITrxContext anITrxContext,IStateTrxValue anICCStateTrxValue, IState anICCState)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		if (anITrxContext == null) {
            throw new NoSuchGeographyException("The ITrxContext is null!!!");
        }
        if (anICCState == null) {
            throw new NoSuchGeographyException("The ICity to be updated is null !!!");
        }
        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, anICCStateTrxValue, anICCState);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_STATE);
        return operate(trxValue, param);
	}

	public boolean isStateCodeUnique(String stateCode) {
		return getStateBusManager().isStateCodeUnique(stateCode);
	}
	
	public boolean isStateNameUnique(String stateName,long countryId) {
		return getStateBusManager().isStateNameUnique(stateName,countryId);
	}
	
	//------------------------------------File Upload-----------------------------------------------------
	
	 private IStateTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 IStateTrxValue ccStateTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccStateTrxValue = new OBStateTrxValue(anICMSTrxValue);
	        } else {
	            ccStateTrxValue = new OBStateTrxValue();
	        }
	        ccStateTrxValue = formulateTrxValueID(anITrxContext, (IStateTrxValue) ccStateTrxValue);
	        ccStateTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccStateTrxValue;
	    }
	 
	 private IStateTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 IStateTrxValue ccStateTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccStateTrxValue = new OBStateTrxValue(anICMSTrxValue);
	        } else {
	            ccStateTrxValue = new OBStateTrxValue();
	        }
	        ccStateTrxValue = formulateTrxValueID(anITrxContext, (IStateTrxValue) ccStateTrxValue);
	        ccStateTrxValue.setStagingFileMapperID(fileId);
	        return ccStateTrxValue;
	    }
	 private IStateTrxValue formulateTrxValueID(ITrxContext anITrxContext, IStateTrxValue anIStateTrxValue) {
	        anIStateTrxValue.setTrxContext(anITrxContext);
	        anIStateTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_STATE);
	        return anIStateTrxValue;
	    }
 
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IStateTrxValue makerInsertMapperState(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws NoSuchGeographyException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new NoSuchGeographyException("The OBFileMapperID to be updated is null !!!");
	        }

	        IStateTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getStateBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null State object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertState(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getStateBusManager().insertState(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null State object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IStateTrxValue getInsertFileByTrxID(String trxID)
		throws NoSuchGeographyException, TransactionException,
		CommandProcessingException {
		 	IStateTrxValue trxValue = new OBStateTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_STATE);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in State.
		 */
	 public List getAllStage(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getStateBusManager().getAllStageState( searchBy, login);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IStateTrxValue checkerApproveInsertState(
	 		ITrxContext anITrxContext,
	 		IStateTrxValue anIStateTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new NoSuchGeographyException("The ITrxContext is null!!!");
	    }
	    if (anIStateTrxValue == null) {
	        throw new NoSuchGeographyException
	                ("The IStateTrxValue to be updated is null!!!");
	    }
	    anIStateTrxValue = formulateTrxValueID(anITrxContext, anIStateTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIStateTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of State.
		 */
	 public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getStateBusManager().getFileMasterList( searchBy);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public IState insertActualState(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getStateBusManager().insertActualState(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new NoSuchGeographyException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new NoSuchGeographyException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in State.
		 */
	 
	 public IStateTrxValue checkerCreateState(ITrxContext anITrxContext, IState anICCState, String refStage) throws NoSuchGeographyException,TrxParameterException,
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anICCState == null) {
	         throw new NoSuchGeographyException("The ICCState to be updated is null !!!");
	     }

	     IStateTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCState);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCState.getIdState()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files State.
		 */

	 public IStateTrxValue checkerRejectInsertState(
	 	ITrxContext anITrxContext,
	 	IStateTrxValue anIStateTrxValue)
	 	throws NoSuchGeographyException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new NoSuchGeographyException("The ITrxContext is null!!!");
	 	}
	 	if (anIStateTrxValue == null) {
	 	  throw new NoSuchGeographyException
	 	          ("The IStateTrxValue to be updated is null!!!");
	 	}
	 		anIStateTrxValue = formulateTrxValueID(anITrxContext, anIStateTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIStateTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files State.
		 */

	 public IStateTrxValue makerInsertCloseRejectedState(
	 		ITrxContext anITrxContext,
	 		IStateTrxValue anIStateTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anIStateTrxValue == null) {
	         throw new NoSuchGeographyException("The IStateTrxValue to be updated is null!!!");
	     }
	     anIStateTrxValue = formulateTrxValue(anITrxContext, anIStateTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIStateTrxValue, param);
	 }

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getStateBusManager().deleteTransaction(obFileMapperMaster); 		
	}

	public IRegion getRegionByRegionCode(String regionCode) {
		return getStateBusManager().getRegionByRegionCode(regionCode);
	}
}
