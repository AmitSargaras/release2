/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.facilityNewMaster.proxy;



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
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterBusManager;
import com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the FacilityNewMaster modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class FacilityNewMasterProxyManagerImpl implements IFacilityNewMasterProxyManager {

	
	private IFacilityNewMasterBusManager facilityNewMasterBusManager;
	
	
	private IFacilityNewMasterBusManager stagingFacilityNewMasterBusManager;

    private ITrxControllerFactory trxControllerFactory;



	public IFacilityNewMasterBusManager getStagingFacilityNewMasterBusManager() {
		return stagingFacilityNewMasterBusManager;
	}

	public void setStagingFacilityNewMasterBusManager(
			IFacilityNewMasterBusManager stagingFacilityNewMasterBusManager) {
		this.stagingFacilityNewMasterBusManager = stagingFacilityNewMasterBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IFacilityNewMasterBusManager getFacilityNewMasterBusManager() {
		return facilityNewMasterBusManager;
	}

	public void setFacilityNewMasterBusManager(IFacilityNewMasterBusManager facilityNewMasterBusManager) {
		this.facilityNewMasterBusManager = facilityNewMasterBusManager;
	}


	/**
	 * @return List of all FacilityNewMaster
	 */
	
	public SearchResult getAllActualFacilityNewMaster()throws FacilityNewMasterException,TrxParameterException,TransactionException {
		try{


			return getFacilityNewMasterBusManager().getAllFacilityNewMaster( );
		}catch (Exception e) {
			throw new FacilityNewMasterException("ERROR- Cannot retrive list from database.");
		}
    }
	/**
	 * getFilteredActualFacilityNewMaster - List of all FacilityNewMaster according to input criteria .
	 */
	
	public SearchResult getFilteredActualFacilityNewMaster(String code,String name,String category,String type,String system,String line)
			throws FacilityNewMasterException,TrxParameterException,TransactionException {
		try{
			return getFacilityNewMasterBusManager().getFilteredActualFacilityNewMaster(code, name, category, type, system, line);
		}catch (Exception e) {
			throw new FacilityNewMasterException("ERROR- Cannot retrive list from database.");
		}
    }

	/**
	 * @return List of all FacilityNewMaster according to criteria .
	 */
	
	
	public List searchFacilityNewMaster(String login) throws FacilityNewMasterException,TrxParameterException,TransactionException {
	 	return getFacilityNewMasterBusManager().searchFacilityNewMaster(login);

    }
	/**
	 * @return List of all FacilityNewMaster according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws FacilityNewMasterException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getFacilityNewMasterBusManager().getAllFacilityNewMaster( searchBy, searchText);
		}else{
			throw new FacilityNewMasterException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return FacilityNewMaster according to id .
		 */
		
	 /**
		 * @return Validate  Line Number Of Facility.
		 */
	 public boolean isUniqueCode(String branchCode,String system) throws FacilityNewMasterException,TrxParameterException,
		TransactionException {
	     return  getFacilityNewMasterBusManager().isUniqueCode(branchCode,system);
	    }
	 
	 public boolean isUniqueFacilityCode(String facilityCode) throws FacilityNewMasterException,TrxParameterException,
		TransactionException {
	     return  getFacilityNewMasterBusManager().isUniqueFacilityCode(facilityCode);
	    }
	 
	 
	 
	 public IFacilityNewMaster getFacilityNewMasterById(long id) throws FacilityNewMasterException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getFacilityNewMasterBusManager().getFacilityNewMasterById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new FacilityNewMasterException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new FacilityNewMasterException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update FacilityNewMaster according to criteria .
		 */
		
	 
	 
	 public IFacilityNewMaster updateFacilityNewMaster(IFacilityNewMaster facilityNewMaster) throws FacilityNewMasterException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 IFacilityNewMaster item = (IFacilityNewMaster) facilityNewMaster;
			try {
				return getFacilityNewMasterBusManager().updateFacilityNewMaster(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new FacilityNewMasterException("ERROR-- Due to null FacilityNewMaster object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete FacilityNewMaster according to criteria .
		 */
		
	 public IFacilityNewMaster deleteFacilityNewMaster(IFacilityNewMaster facilityNewMaster) throws FacilityNewMasterException, TrxParameterException,
		TransactionException {
		 if(!(facilityNewMaster==null)){
		 IFacilityNewMaster item = (IFacilityNewMaster) facilityNewMaster;
			try {
				return getFacilityNewMasterBusManager().deleteFacilityNewMaster(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new FacilityNewMasterException("ERROR-- Transaction for the FacilityNewMaster object is null.");
			}
		 }else{
			 throw new FacilityNewMasterException("ERROR-- Cannot delete due to null FacilityNewMaster object.");
		 }
		}
	 /**
		 * @return Checker Approve  FacilityNewMaster according to criteria .
		 */
		
	
	public IFacilityNewMasterTrxValue checkerApproveFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FacilityNewMasterException("The ITrxContext is null!!!");
        }
        if (anIFacilityNewMasterTrxValue == null) {
            throw new FacilityNewMasterException
                    ("The IFacilityNewMasterTrxValue to be updated is null!!!");
        }
        anIFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, anIFacilityNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_FACILITY_NEW_MASTER);
        return operate(anIFacilityNewMasterTrxValue, param);
	}
	 /**
	 * @return Checker Reject  FacilityNewMaster according to criteria .
	 */
	
	
	public IFacilityNewMasterTrxValue checkerRejectFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anIFacilityNewMasterTrxValue == null) {
	            throw new FacilityNewMasterException("The IFacilityNewMasterTrxValue to be updated is null!!!");
	        }
	        anIFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, anIFacilityNewMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_FACILITY_NEW_MASTER);
	        return operate(anIFacilityNewMasterTrxValue, param);
	}
	
	 /**
	 * @return  FacilityNewMaster TRX value according to trxId  .
	 */
	

	
	public IFacilityNewMasterTrxValue getFacilityNewMasterByTrxID(String aTrxID)
			throws FacilityNewMasterException, TransactionException,
			CommandProcessingException {
		IFacilityNewMasterTrxValue trxValue = new OBFacilityNewMasterTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_FACILITY_NEW_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_FACILITY_NEW_MASTER_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  FacilityNewMaster TRX value  .
	 */
	

	public IFacilityNewMasterTrxValue getFacilityNewMasterTrxValue(
			long aFacilityNewMasterId) throws FacilityNewMasterException,
			TrxParameterException, TransactionException {
		if (aFacilityNewMasterId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new FacilityNewMasterException("Invalid FacilityNewMasterId");
        }
        IFacilityNewMasterTrxValue trxValue = new OBFacilityNewMasterTrxValue();
        trxValue.setReferenceID(String.valueOf(aFacilityNewMasterId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_FACILITY_NEW_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_FACILITY_NEW_MASTER);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close FacilityNewMaster.
	 */
	
	public IFacilityNewMasterTrxValue makerCloseRejectedFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FacilityNewMasterException("The ITrxContext is null!!!");
        }
        if (anIFacilityNewMasterTrxValue == null) {
            throw new FacilityNewMasterException("The IFacilityNewMasterTrxValue to be updated is null!!!");
        }
        anIFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, anIFacilityNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FACILITY_NEW_MASTER);
        return operate(anIFacilityNewMasterTrxValue, param);
	}

	 /**
	 * @return Maker Close draft FacilityNewMaster
	 */
	
	public IFacilityNewMasterTrxValue makerCloseDraftFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FacilityNewMasterException("The ITrxContext is null!!!");
        }
        if (anIFacilityNewMasterTrxValue == null) {
            throw new FacilityNewMasterException("The IFacilityNewMasterTrxValue to be updated is null!!!");
        }
        anIFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, anIFacilityNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_FACILITY_NEW_MASTER);
        return operate(anIFacilityNewMasterTrxValue, param);
	}
	 /**
	 * @return Maker Edit FacilityNewMaster
	 */
	public IFacilityNewMasterTrxValue makerEditRejectedFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue, IFacilityNewMaster anFacilityNewMaster)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FacilityNewMasterException("The ITrxContext is null!!!");
        }
        if (anIFacilityNewMasterTrxValue == null) {
            throw new FacilityNewMasterException("The IFacilityNewMasterTrxValue to be updated is null!!!");
        }
        if (anFacilityNewMaster == null) {
            throw new FacilityNewMasterException("The IFacilityNewMaster to be updated is null !!!");
        }
        anIFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, anIFacilityNewMasterTrxValue, anFacilityNewMaster);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_FACILITY_NEW_MASTER);
        return operate(anIFacilityNewMasterTrxValue, param);
	}
	 /**
	 * @return Maker Update FacilityNewMaster
	 */

	public IFacilityNewMasterTrxValue makerUpdateFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anICCFacilityNewMasterTrxValue,
			IFacilityNewMaster anICCFacilityNewMaster)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCFacilityNewMaster to be updated is null !!!");
	        }
	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFacilityNewMasterTrxValue, anICCFacilityNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update FacilityNewMaster
	 */

	public IFacilityNewMasterTrxValue makerUpdateSaveUpdateFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anICCFacilityNewMasterTrxValue,
			IFacilityNewMaster anICCFacilityNewMaster)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCFacilityNewMaster to be updated is null !!!");
	        }
	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFacilityNewMasterTrxValue, anICCFacilityNewMaster);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create FacilityNewMaster
	 */

	public IFacilityNewMasterTrxValue makerUpdateSaveCreateFacilityNewMaster(
			ITrxContext anITrxContext,
			IFacilityNewMasterTrxValue anICCFacilityNewMasterTrxValue,
			IFacilityNewMaster anICCFacilityNewMaster)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCFacilityNewMaster to be updated is null !!!");
	        }
	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFacilityNewMasterTrxValue, anICCFacilityNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	}
	 private IFacilityNewMasterTrxValue operate(IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws FacilityNewMasterException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIFacilityNewMasterTrxValue, anOBCMSTrxParameter);
	        return (IFacilityNewMasterTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws FacilityNewMasterException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (FacilityNewMasterException ex) {
			 throw new FacilityNewMasterException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new FacilityNewMasterException("ERROR--Cannot update already deleted record");
		 }
		 catch (Exception ex) {
			 throw new FacilityNewMasterException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }
	 }
	 
	 private IFacilityNewMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFacilityNewMaster anIFacilityNewMaster) {
	        IFacilityNewMasterTrxValue ccFacilityNewMasterTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccFacilityNewMasterTrxValue = new OBFacilityNewMasterTrxValue(anICMSTrxValue);
	        } else {
	            ccFacilityNewMasterTrxValue = new OBFacilityNewMasterTrxValue();
	        }
	        ccFacilityNewMasterTrxValue = formulateTrxValue(anITrxContext, (IFacilityNewMasterTrxValue) ccFacilityNewMasterTrxValue);
	        ccFacilityNewMasterTrxValue.setStagingFacilityNewMaster(anIFacilityNewMaster);
	        return ccFacilityNewMasterTrxValue;
	    }
	 private IFacilityNewMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, IFacilityNewMasterTrxValue anIFacilityNewMasterTrxValue) {
	        anIFacilityNewMasterTrxValue.setTrxContext(anITrxContext);
	        anIFacilityNewMasterTrxValue.setTransactionType(ICMSConstant.INSTANCE_FACILITY_NEW_MASTER);
	        return anIFacilityNewMasterTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete FacilityNewMaster
		 */

	 public IFacilityNewMasterTrxValue makerDeleteFacilityNewMaster(ITrxContext anITrxContext, IFacilityNewMasterTrxValue anICCFacilityNewMasterTrxValue, IFacilityNewMaster anICCFacilityNewMaster) throws FacilityNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFacilityNewMasterTrxValue, anICCFacilityNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create FacilityNewMaster
		 */
	 public IFacilityNewMasterTrxValue makerCreateFacilityNewMaster(ITrxContext anITrxContext, IFacilityNewMaster anICCFacilityNewMaster) throws FacilityNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCFacilityNewMaster to be updated is null !!!");
	        }

	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCFacilityNewMaster);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save FacilityNewMaster
		 */
	 
	 public IFacilityNewMasterTrxValue makerSaveFacilityNewMaster(ITrxContext anITrxContext, IFacilityNewMaster anICCFacilityNewMaster) throws FacilityNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FacilityNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCFacilityNewMaster == null) {
	            throw new FacilityNewMasterException("The ICCFacilityNewMaster to be updated is null !!!");
	        }

	        IFacilityNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCFacilityNewMaster);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_FACILITY_NEW_MASTER);
	        return operate(trxValue, param);
	    }

	public boolean isFacilityNameUnique(String facilityName) {
		return getFacilityNewMasterBusManager().isFacilityNameUnique(facilityName);
	}
	
	public boolean isFacilityCpsIdUnique(String cpsId) {
		return getFacilityNewMasterBusManager().isFacilityCpsIdUnique(cpsId);
	}

	
	    
	
}
