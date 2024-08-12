/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.collateralNewMaster.proxy;



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
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterBusManager;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the CollateralNewMaster modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class CollateralNewMasterProxyManagerImpl implements ICollateralNewMasterProxyManager {

	
	private ICollateralNewMasterBusManager collateralNewMasterBusManager;
	
	
	private ICollateralNewMasterBusManager stagingCollateralNewMasterBusManager;

    private ITrxControllerFactory trxControllerFactory;



	public ICollateralNewMasterBusManager getStagingCollateralNewMasterBusManager() {
		return stagingCollateralNewMasterBusManager;
	}

	public void setStagingCollateralNewMasterBusManager(
			ICollateralNewMasterBusManager stagingCollateralNewMasterBusManager) {
		this.stagingCollateralNewMasterBusManager = stagingCollateralNewMasterBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ICollateralNewMasterBusManager getCollateralNewMasterBusManager() {
		return collateralNewMasterBusManager;
	}

	public void setCollateralNewMasterBusManager(ICollateralNewMasterBusManager collateralNewMasterBusManager) {
		this.collateralNewMasterBusManager = collateralNewMasterBusManager;
	}


	/**
	 * @return List of all CollateralNewMaster
	 */
	
	public SearchResult getAllActualCollateralNewMaster()throws CollateralNewMasterException,TrxParameterException,TransactionException {
		try{


			return getCollateralNewMasterBusManager().getAllCollateralNewMaster();
		}catch (Exception e) {
			throw new CollateralNewMasterException("ERROR- Cannot retrive list from database.");
		}
    }
	
	/*
	 * getFilteredCollateral(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * generates query for collateral master based on input search criteria .
	 */
	public SearchResult getFilteredCollateral(String collateralCode,
			String collateralDescription, String collateralMainType,
			String collateralSubType)throws CollateralNewMasterException,TrxParameterException,TransactionException {
		try{
			return getCollateralNewMasterBusManager().getFilteredCollateral(collateralCode,collateralDescription,collateralMainType,collateralSubType);
		}catch (Exception e) {
			throw new CollateralNewMasterException("ERROR- Cannot retrive list from database.");
		}
    }
	



	/**
	 * @return List of all CollateralNewMaster according to criteria .
	 */
	
	
	public List searchCollateralNewMaster(String login) throws CollateralNewMasterException,TrxParameterException,TransactionException {
	 	return getCollateralNewMasterBusManager().searchCollateralNewMaster(login);

    }
	/**
	 * @return List of all CollateralNewMaster according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws CollateralNewMasterException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getCollateralNewMasterBusManager().getAllCollateralNewMaster( searchBy, searchText);
		}else{
			throw new CollateralNewMasterException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return CollateralNewMaster according to id .
		 */
		
	 public ICollateralNewMaster getCollateralNewMasterById(long id) throws CollateralNewMasterException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getCollateralNewMasterBusManager().getCollateralNewMasterById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new CollateralNewMasterException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new CollateralNewMasterException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update CollateralNewMaster according to criteria .
		 */
		
	 
	 
	 public ICollateralNewMaster updateCollateralNewMaster(ICollateralNewMaster collateralNewMaster) throws CollateralNewMasterException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 ICollateralNewMaster item = (ICollateralNewMaster) collateralNewMaster;
			try {
				return getCollateralNewMasterBusManager().updateCollateralNewMaster(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CollateralNewMasterException("ERROR-- Due to null CollateralNewMaster object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete CollateralNewMaster according to criteria .
		 */
		
	 public ICollateralNewMaster deleteCollateralNewMaster(ICollateralNewMaster collateralNewMaster) throws CollateralNewMasterException, TrxParameterException,
		TransactionException {
		 if(!(collateralNewMaster==null)){
		 ICollateralNewMaster item = (ICollateralNewMaster) collateralNewMaster;
			try {
				return getCollateralNewMasterBusManager().deleteCollateralNewMaster(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new CollateralNewMasterException("ERROR-- Transaction for the CollateralNewMaster object is null.");
			}
		 }else{
			 throw new CollateralNewMasterException("ERROR-- Cannot delete due to null CollateralNewMaster object.");
		 }
		}
	 /**
		 * @return Checker Approve  CollateralNewMaster according to criteria .
		 */
		
	
	public ICollateralNewMasterTrxValue checkerApproveCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralNewMasterException("The ITrxContext is null!!!");
        }
        if (anICollateralNewMasterTrxValue == null) {
            throw new CollateralNewMasterException
                    ("The ICollateralNewMasterTrxValue to be updated is null!!!");
        }
        anICollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, anICollateralNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COLLATERAL_NEW_MASTER);
        return operate(anICollateralNewMasterTrxValue, param);
	}
	 /**
	 * @return Checker Reject  CollateralNewMaster according to criteria .
	 */
	
	
	public ICollateralNewMasterTrxValue checkerRejectCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICollateralNewMasterTrxValue == null) {
	            throw new CollateralNewMasterException("The ICollateralNewMasterTrxValue to be updated is null!!!");
	        }
	        anICollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, anICollateralNewMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COLLATERAL_NEW_MASTER);
	        return operate(anICollateralNewMasterTrxValue, param);
	}
	
	 /**
	 * @return  CollateralNewMaster TRX value according to trxId  .
	 */
	

	
	public ICollateralNewMasterTrxValue getCollateralNewMasterByTrxID(String aTrxID)
			throws CollateralNewMasterException, TransactionException,
			CommandProcessingException {
		ICollateralNewMasterTrxValue trxValue = new OBCollateralNewMasterTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_NEW_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_COLLATERAL_NEW_MASTER_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  CollateralNewMaster TRX value  .
	 */
	

	public ICollateralNewMasterTrxValue getCollateralNewMasterTrxValue(
			long aCollateralNewMasterId) throws CollateralNewMasterException,
			TrxParameterException, TransactionException {
		if (aCollateralNewMasterId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new CollateralNewMasterException("Invalid CollateralNewMasterId");
        }
        ICollateralNewMasterTrxValue trxValue = new OBCollateralNewMasterTrxValue();
        trxValue.setReferenceID(String.valueOf(aCollateralNewMasterId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL_NEW_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_COLLATERAL_NEW_MASTER);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close CollateralNewMaster.
	 */
	
	public ICollateralNewMasterTrxValue makerCloseRejectedCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralNewMasterException("The ITrxContext is null!!!");
        }
        if (anICollateralNewMasterTrxValue == null) {
            throw new CollateralNewMasterException("The ICollateralNewMasterTrxValue to be updated is null!!!");
        }
        anICollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, anICollateralNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_NEW_MASTER);
        return operate(anICollateralNewMasterTrxValue, param);
	}

	 /**
	 * @return Maker Close draft CollateralNewMaster
	 */
	
	public ICollateralNewMasterTrxValue makerCloseDraftCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralNewMasterException("The ITrxContext is null!!!");
        }
        if (anICollateralNewMasterTrxValue == null) {
            throw new CollateralNewMasterException("The ICollateralNewMasterTrxValue to be updated is null!!!");
        }
        anICollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, anICollateralNewMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_NEW_MASTER);
        return operate(anICollateralNewMasterTrxValue, param);
	}
	 /**
	 * @return Maker Edit CollateralNewMaster
	 */
	public ICollateralNewMasterTrxValue makerEditRejectedCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue, ICollateralNewMaster anCollateralNewMaster)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CollateralNewMasterException("The ITrxContext is null!!!");
        }
        if (anICollateralNewMasterTrxValue == null) {
            throw new CollateralNewMasterException("The ICollateralNewMasterTrxValue to be updated is null!!!");
        }
        if (anCollateralNewMaster == null) {
            throw new CollateralNewMasterException("The ICollateralNewMaster to be updated is null !!!");
        }
        anICollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, anICollateralNewMasterTrxValue, anCollateralNewMaster);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COLLATERAL_NEW_MASTER);
        return operate(anICollateralNewMasterTrxValue, param);
	}
	 /**
	 * @return Maker Update CollateralNewMaster
	 */

	public ICollateralNewMasterTrxValue makerUpdateCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue,
			ICollateralNewMaster anICCCollateralNewMaster)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCCollateralNewMaster to be updated is null !!!");
	        }
	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralNewMasterTrxValue, anICCCollateralNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update CollateralNewMaster
	 */

	public ICollateralNewMasterTrxValue makerUpdateSaveUpdateCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue,
			ICollateralNewMaster anICCCollateralNewMaster)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCCollateralNewMaster to be updated is null !!!");
	        }
	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralNewMasterTrxValue, anICCCollateralNewMaster);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create CollateralNewMaster
	 */

	public ICollateralNewMasterTrxValue makerUpdateSaveCreateCollateralNewMaster(
			ITrxContext anITrxContext,
			ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue,
			ICollateralNewMaster anICCCollateralNewMaster)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCCollateralNewMaster to be updated is null !!!");
	        }
	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralNewMasterTrxValue, anICCCollateralNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	}
	 private ICollateralNewMasterTrxValue operate(ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws CollateralNewMasterException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anICollateralNewMasterTrxValue, anOBCMSTrxParameter);
	        return (ICollateralNewMasterTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws CollateralNewMasterException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (CollateralNewMasterException ex) {
			 throw new CollateralNewMasterException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new CollateralNewMasterException("ERROR--Cannot update already deleted record");
		 }catch (Exception ex) {
			 ex.printStackTrace();
			 throw new CollateralNewMasterException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }
	 }
	 
	 private ICollateralNewMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ICollateralNewMaster anICollateralNewMaster) {
	        ICollateralNewMasterTrxValue ccCollateralNewMasterTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCollateralNewMasterTrxValue = new OBCollateralNewMasterTrxValue(anICMSTrxValue);
	        } else {
	            ccCollateralNewMasterTrxValue = new OBCollateralNewMasterTrxValue();
	        }
	        ccCollateralNewMasterTrxValue = formulateTrxValue(anITrxContext, (ICollateralNewMasterTrxValue) ccCollateralNewMasterTrxValue);
	        ccCollateralNewMasterTrxValue.setStagingCollateralNewMaster(anICollateralNewMaster);
	        return ccCollateralNewMasterTrxValue;
	    }
	 private ICollateralNewMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICollateralNewMasterTrxValue anICollateralNewMasterTrxValue) {
	        anICollateralNewMasterTrxValue.setTrxContext(anITrxContext);
	        anICollateralNewMasterTrxValue.setTransactionType("COLLATERAL_MASTER");
	        return anICollateralNewMasterTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete CollateralNewMaster
		 */

	 public ICollateralNewMasterTrxValue makerDeleteCollateralNewMaster(ITrxContext anITrxContext, ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue, ICollateralNewMaster anICCCollateralNewMaster) throws CollateralNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCollateralNewMasterTrxValue, anICCCollateralNewMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create CollateralNewMaster
		 */
	 public ICollateralNewMasterTrxValue makerCreateCollateralNewMaster(ITrxContext anITrxContext, ICollateralNewMaster anICCCollateralNewMaster) throws CollateralNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCCollateralNewMaster to be updated is null !!!");
	        }

	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCollateralNewMaster);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save CollateralNewMaster
		 */
	 
	 public ICollateralNewMasterTrxValue makerSaveCollateralNewMaster(ITrxContext anITrxContext, ICollateralNewMaster anICCCollateralNewMaster) throws CollateralNewMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CollateralNewMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCCollateralNewMaster == null) {
	            throw new CollateralNewMasterException("The ICCCollateralNewMaster to be updated is null !!!");
	        }

	        ICollateralNewMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCollateralNewMaster);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COLLATERAL_NEW_MASTER);
	        return operate(trxValue, param);
	    }

	public boolean isCollateraNameUnique(String collateralName) {
		return getCollateralNewMasterBusManager().isCollateraNameUnique(collateralName);
	}
	public boolean isDuplicateRecord(String cpsId) {
		return getCollateralNewMasterBusManager().isDuplicateRecord(cpsId);
	}

	
	    
	
}
