/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.fccBranch.proxy;



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
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchBusManager;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fccBranch.trx.OBFCCBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the FCCBranch modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class FCCBranchProxyManagerImpl implements IFCCBranchProxyManager {

	
	private IFCCBranchBusManager fccBranchBusManager;
	
	
	private IFCCBranchBusManager stagingFCCBranchBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private IFCCBranchBusManager stagingFCCBranchFileMapperIdBusManager;
	
	private IFCCBranchBusManager fccBranchFileMapperIdBusManager;




	
	/**
	 * @return the fccBranchBusManager
	 */
	public IFCCBranchBusManager getFccBranchBusManager() {
		return fccBranchBusManager;
	}


	/**
	 * @param fccBranchBusManager the fccBranchBusManager to set
	 */
	public void setFccBranchBusManager(IFCCBranchBusManager fccBranchBusManager) {
		this.fccBranchBusManager = fccBranchBusManager;
	}


	/**
	 * @return the stagingFCCBranchBusManager
	 */
	public IFCCBranchBusManager getStagingFCCBranchBusManager() {
		return stagingFCCBranchBusManager;
	}


	/**
	 * @param stagingFCCBranchBusManager the stagingFCCBranchBusManager to set
	 */
	public void setStagingFCCBranchBusManager(
			IFCCBranchBusManager stagingFCCBranchBusManager) {
		this.stagingFCCBranchBusManager = stagingFCCBranchBusManager;
	}


	/**
	 * @return the trxControllerFactory
	 */
	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}


	/**
	 * @param trxControllerFactory the trxControllerFactory to set
	 */
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}


	/**
	 * @return the stagingFCCBranchFileMapperIdBusManager
	 */
	public IFCCBranchBusManager getStagingFCCBranchFileMapperIdBusManager() {
		return stagingFCCBranchFileMapperIdBusManager;
	}


	/**
	 * @param stagingFCCBranchFileMapperIdBusManager the stagingFCCBranchFileMapperIdBusManager to set
	 */
	public void setStagingFCCBranchFileMapperIdBusManager(
			IFCCBranchBusManager stagingFCCBranchFileMapperIdBusManager) {
		this.stagingFCCBranchFileMapperIdBusManager = stagingFCCBranchFileMapperIdBusManager;
	}


	/**
	 * @return the fccBranchFileMapperIdBusManager
	 */
	public IFCCBranchBusManager getFccBranchFileMapperIdBusManager() {
		return fccBranchFileMapperIdBusManager;
	}


	/**
	 * @param fccBranchFileMapperIdBusManager the fccBranchFileMapperIdBusManager to set
	 */
	public void setFccBranchFileMapperIdBusManager(
			IFCCBranchBusManager fccBranchFileMapperIdBusManager) {
		this.fccBranchFileMapperIdBusManager = fccBranchFileMapperIdBusManager;
	}


	/**
	 * @return List of all FCCBranch
	 */
	
	public SearchResult getAllActualFCCBranch()throws FCCBranchException,TrxParameterException,TransactionException {
		try{


			return getFccBranchBusManager().getAllFCCBranch( );
		}catch (Exception e) {
			throw new FCCBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	

	/**
	 * @return List of all FCCBranch
	 */
	
	public SearchResult getAllFilteredActualFCCBranch(String code,String name)throws FCCBranchException,TrxParameterException,TransactionException {
		try{


			return getFccBranchBusManager().getAllFilteredFCCBranch(code,name );
		}catch (Exception e) {
			throw new FCCBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	

	
	/**
	 * @return List of all FCCBranch according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws FCCBranchException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getFccBranchBusManager().getAllFCCBranch( searchBy, searchText);
		}else{
			throw new FCCBranchException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return FCCBranch according to id .
		 */
		
	 public IFCCBranch getFCCBranchById(long id) throws FCCBranchException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getFccBranchBusManager().getFCCBranchById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new FCCBranchException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new FCCBranchException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update FCCBranch according to criteria .
		 */
		
	 
	 
	 public IFCCBranch updateFCCBranch(IFCCBranch fccBranch) throws FCCBranchException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 IFCCBranch item = (IFCCBranch) fccBranch;
			try {
				return getFccBranchBusManager().updateFCCBranch(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new FCCBranchException("ERROR-- Due to null FCCBranch object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete FCCBranch according to criteria .
		 */
		
	 public IFCCBranch deleteFCCBranch(IFCCBranch fccBranch) throws FCCBranchException, TrxParameterException,
		TransactionException {
		 if(!(fccBranch==null)){
		 IFCCBranch item = (IFCCBranch) fccBranch;
			try {
				return getFccBranchBusManager().deleteFCCBranch(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new FCCBranchException("ERROR-- Transaction for the FCCBranch object is null.");
			}
		 }else{
			 throw new FCCBranchException("ERROR-- Cannot delete due to null FCCBranch object.");
		 }
		}
	 /**
		 * @return Checker Approve  FCCBranch according to criteria .
		 */
		
	
	public IFCCBranchTrxValue checkerApproveFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anIFCCBranchTrxValue)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FCCBranchException("The ITrxContext is null!!!");
        }
        if (anIFCCBranchTrxValue == null) {
            throw new FCCBranchException
                    ("The IFCCBranchTrxValue to be updated is null!!!");
        }
        anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_FCCBRANCH);
        return operate(anIFCCBranchTrxValue, param);
	}
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IFCCBranchTrxValue checkerRejectFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anIFCCBranchTrxValue)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anIFCCBranchTrxValue == null) {
	            throw new FCCBranchException("The IFCCBranchTrxValue to be updated is null!!!");
	        }
	        anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_FCCBRANCH);
	        return operate(anIFCCBranchTrxValue, param);
	}
	
	 /**
	 * @return  FCCBranch TRX value according to trxId  .
	 */
	

	
	public IFCCBranchTrxValue getFCCBranchByTrxID(String aTrxID)
			throws FCCBranchException, TransactionException,
			CommandProcessingException {
		IFCCBranchTrxValue trxValue = new OBFCCBranchTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_FCCBRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_FCCBRANCH_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  FCCBranch TRX value  .
	 */
	

	public IFCCBranchTrxValue getFCCBranchTrxValue(
			long aFCCBranchId) throws FCCBranchException,
			TrxParameterException, TransactionException {
		if (aFCCBranchId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new FCCBranchException("Invalid FCCBranchId");
        }
        IFCCBranchTrxValue trxValue = new OBFCCBranchTrxValue();
        trxValue.setReferenceID(String.valueOf(aFCCBranchId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_FCCBRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_FCCBRANCH);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close FCCBranch.
	 */
	
	public IFCCBranchTrxValue makerCloseRejectedFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anIFCCBranchTrxValue)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FCCBranchException("The ITrxContext is null!!!");
        }
        if (anIFCCBranchTrxValue == null) {
            throw new FCCBranchException("The IFCCBranchTrxValue to be updated is null!!!");
        }
        anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FCCBRANCH);
        return operate(anIFCCBranchTrxValue, param);
	}

	 /**
	 * @return Maker Close draft FCCBranch
	 */
	
	public IFCCBranchTrxValue makerCloseDraftFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anIFCCBranchTrxValue)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FCCBranchException("The ITrxContext is null!!!");
        }
        if (anIFCCBranchTrxValue == null) {
            throw new FCCBranchException("The IFCCBranchTrxValue to be updated is null!!!");
        }
        anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_FCCBRANCH);
        return operate(anIFCCBranchTrxValue, param);
	}
	 /**
	 * @return Maker Edit FCCBranch
	 */
	public IFCCBranchTrxValue makerEditRejectedFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anIFCCBranchTrxValue, IFCCBranch anFCCBranch)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new FCCBranchException("The ITrxContext is null!!!");
        }
        if (anIFCCBranchTrxValue == null) {
            throw new FCCBranchException("The IFCCBranchTrxValue to be updated is null!!!");
        }
        if (anFCCBranch == null) {
            throw new FCCBranchException("The IFCCBranch to be updated is null !!!");
        }
        anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue, anFCCBranch);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_FCCBRANCH);
        return operate(anIFCCBranchTrxValue, param);
	}
	 /**
	 * @return Maker Update FCCBranch
	 */

	public IFCCBranchTrxValue makerUpdateFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anICCFCCBranchTrxValue,
			IFCCBranch anICCFCCBranch)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
	        }
	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFCCBranchTrxValue, anICCFCCBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_FCCBRANCH);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update FCCBranch
	 */

	public IFCCBranchTrxValue makerUpdateSaveUpdateFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anICCFCCBranchTrxValue,
			IFCCBranch anICCFCCBranch)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
	        }
	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFCCBranchTrxValue, anICCFCCBranch);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_FCCBRANCH);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create FCCBranch
	 */

	public IFCCBranchTrxValue makerUpdateSaveCreateFCCBranch(
			ITrxContext anITrxContext,
			IFCCBranchTrxValue anICCFCCBranchTrxValue,
			IFCCBranch anICCFCCBranch)
			throws FCCBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
	        }
	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFCCBranchTrxValue, anICCFCCBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_FCCBRANCH);
	        return operate(trxValue, param);
	}
	 private IFCCBranchTrxValue operate(IFCCBranchTrxValue anIFCCBranchTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws FCCBranchException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIFCCBranchTrxValue, anOBCMSTrxParameter);
	        return (IFCCBranchTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws FCCBranchException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (FCCBranchException ex) {
			 throw new FCCBranchException("ERROR--Cannot Get the FCCBranch Controller.");
		 }
		 catch (Exception ex) {
			 throw new FCCBranchException("ERROR--Cannot Get the FCCBranch Controller.");
		 }
	 }
	 
	 private IFCCBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFCCBranch anIFCCBranch) {
	        IFCCBranchTrxValue ccFCCBranchTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccFCCBranchTrxValue = new OBFCCBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccFCCBranchTrxValue = new OBFCCBranchTrxValue();
	        }
	        ccFCCBranchTrxValue = formulateTrxValue(anITrxContext, (IFCCBranchTrxValue) ccFCCBranchTrxValue);
	        ccFCCBranchTrxValue.setStagingFCCBranch(anIFCCBranch);
	        return ccFCCBranchTrxValue;
	    }
	 private IFCCBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) {
	        anIFCCBranchTrxValue.setTrxContext(anITrxContext);
	        anIFCCBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_FCCBRANCH);
	        return anIFCCBranchTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete FCCBranch
		 */

	 public IFCCBranchTrxValue makerDeleteFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anICCFCCBranchTrxValue, IFCCBranch anICCFCCBranch) throws FCCBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCFCCBranchTrxValue, anICCFCCBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_FCCBRANCH);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create FCCBranch
		 */
	 public IFCCBranchTrxValue makerCreateFCCBranch(ITrxContext anITrxContext, IFCCBranch anICCFCCBranch) throws FCCBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
	        }

	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCFCCBranch);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_FCCBRANCH);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save FCCBranch
		 */
	 
	 public IFCCBranchTrxValue makerSaveFCCBranch(ITrxContext anITrxContext, IFCCBranch anICCFCCBranch) throws FCCBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCFCCBranch == null) {
	            throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
	        }

	        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCFCCBranch);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_FCCBRANCH);
	        return operate(trxValue, param);
	    }

	
	//------------------------------------File Upload-----------------------------------------------------
		
		 private IFCCBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 IFCCBranchTrxValue ccFCCBranchTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccFCCBranchTrxValue = new OBFCCBranchTrxValue(anICMSTrxValue);
		        } else {
		            ccFCCBranchTrxValue = new OBFCCBranchTrxValue();
		        }
		        ccFCCBranchTrxValue = formulateTrxValueID(anITrxContext, (IFCCBranchTrxValue) ccFCCBranchTrxValue);
		        ccFCCBranchTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccFCCBranchTrxValue;
		    }
		 
		 private IFCCBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
			 IFCCBranchTrxValue ccFCCBranchTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccFCCBranchTrxValue = new OBFCCBranchTrxValue(anICMSTrxValue);
		        } else {
		            ccFCCBranchTrxValue = new OBFCCBranchTrxValue();
		        }
		        ccFCCBranchTrxValue = formulateTrxValueID(anITrxContext, (IFCCBranchTrxValue) ccFCCBranchTrxValue);
		        ccFCCBranchTrxValue.setStagingFileMapperID(fileId);
		        return ccFCCBranchTrxValue;
		    }
		 private IFCCBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) {
		        anIFCCBranchTrxValue.setTrxContext(anITrxContext);
		        anIFCCBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_FCCBRANCH);
		        return anIFCCBranchTrxValue;
		    }
	    
		 
		 /**
			 * @return Maker insert a fileID to generate a transation.
			 */
		 public IFCCBranchTrxValue makerInsertMapperFCCBranch(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws FCCBranchException, TrxParameterException,
					TransactionException {
				// TODO Auto-generated method stub
				if (anITrxContext == null) {
		            throw new FCCBranchException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new FCCBranchException("The OBFileMapperID to be updated is null !!!");
		        }

		        IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 /**
			 * @return Maker check if previous upload is pending.
			 */
		 public boolean isPrevFileUploadPending() throws FCCBranchException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getFccBranchBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new FCCBranchException("ERROR-- Due to null FCCBranch object cannot update.");
				}
			}
		 
		 /**
			 * @return Maker insert uploaded files in Staging table.
			 */
		 
		 public int insertFCCBranch(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws FCCBranchException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getFccBranchBusManager().insertFCCBranch(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new FCCBranchException("ERROR-- Due to null FCCBranch object cannot update.");
				}
			}
		 
		 /**
			 * @return create record with TransID.
			 */
		 
		 public IFCCBranchTrxValue getInsertFileByTrxID(String trxID)
			throws FCCBranchException, TransactionException,
			CommandProcessingException {
			 	IFCCBranchTrxValue trxValue = new OBFCCBranchTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 /**
			 * @return Pagination for uploaded files in FCCBranch.
			 */
		 public List getAllStage(String searchBy, String login)throws FCCBranchException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getFccBranchBusManager().getAllStageFCCBranch( searchBy, login);
			}else{
				throw new FCCBranchException("ERROR- Search criteria is null.");
			}
		  }
		
		 /**
			 * @return Checker approval for uploaded files.
			 */
		 
		 public IFCCBranchTrxValue checkerApproveInsertFCCBranch(
		 		ITrxContext anITrxContext,
		 		IFCCBranchTrxValue anIFCCBranchTrxValue)
		 		throws FCCBranchException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		        throw new FCCBranchException("The ITrxContext is null!!!");
		    }
		    if (anIFCCBranchTrxValue == null) {
		        throw new FCCBranchException
		                ("The IFCCBranchTrxValue to be updated is null!!!");
		    }
		    anIFCCBranchTrxValue = formulateTrxValueID(anITrxContext, anIFCCBranchTrxValue);
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		    return operate(anIFCCBranchTrxValue, param);
		 }
		 
		 /**
			 * @return list of files uploaded in staging table of FCCBranch.
			 */
		 public List getFileMasterList(String searchBy)throws FCCBranchException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getFccBranchBusManager().getFileMasterList( searchBy);
			}else{
				throw new FCCBranchException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return Maker insert upload files.
			 */
		 public IFCCBranch insertActualFCCBranch(String sysId) throws FCCBranchException,TrxParameterException,TransactionException

		 {
		  if(sysId != null){
		 	 try {
		 		 return getFccBranchBusManager().insertActualFCCBranch(sysId);
		 	 } catch (Exception e) {		 		
		 		 e.printStackTrace();
		 		 throw new FCCBranchException("ERROR- Transaction for the Id is invalid.");
		 	 }
		  }else{
		 	 throw new FCCBranchException("ERROR- Id for retrival is null.");
		  }
		 }
		 
		 /**
			 * @return Checker create file master in FCCBranch.
			 */
		 
		 public IFCCBranchTrxValue checkerCreateFCCBranch(ITrxContext anITrxContext, IFCCBranch anICCFCCBranch, String refStage) throws FCCBranchException,TrxParameterException,
		 TransactionException {
		     if (anITrxContext == null) {
		         throw new FCCBranchException("The ITrxContext is null!!!");
		     }
		     if (anICCFCCBranch == null) {
		         throw new FCCBranchException("The ICCFCCBranch to be updated is null !!!");
		     }

		     IFCCBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCFCCBranch);
		     trxValue.setFromState("PENDING_CREATE");
		     trxValue.setReferenceID(String.valueOf(anICCFCCBranch.getId()));
		     trxValue.setStagingReferenceID(refStage);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
		     return operate(trxValue, param);
		 }
		 
		 /**
			 * @return Checker Reject for upload files FCCBranch.
			 */

		 public IFCCBranchTrxValue checkerRejectInsertFCCBranch(
		 	ITrxContext anITrxContext,
		 	IFCCBranchTrxValue anIFCCBranchTrxValue)
		 	throws FCCBranchException, TrxParameterException,
		 	TransactionException {
		 	if (anITrxContext == null) {
		 	  throw new FCCBranchException("The ITrxContext is null!!!");
		 	}
		 	if (anIFCCBranchTrxValue == null) {
		 	  throw new FCCBranchException
		 	          ("The IFCCBranchTrxValue to be updated is null!!!");
		 	}
		 		anIFCCBranchTrxValue = formulateTrxValueID(anITrxContext, anIFCCBranchTrxValue);
		 		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		 	return operate(anIFCCBranchTrxValue, param);
		 }
		 
		 /**
			 * @return Maker Close rejected files FCCBranch.
			 */

		 public IFCCBranchTrxValue makerInsertCloseRejectedFCCBranch(
		 		ITrxContext anITrxContext,
		 		IFCCBranchTrxValue anIFCCBranchTrxValue)
		 		throws FCCBranchException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		         throw new FCCBranchException("The ITrxContext is null!!!");
		     }
		     if (anIFCCBranchTrxValue == null) {
		         throw new FCCBranchException("The IFCCBranchTrxValue to be updated is null!!!");
		     }
		     anIFCCBranchTrxValue = formulateTrxValue(anITrxContext, anIFCCBranchTrxValue);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
		     return operate(anIFCCBranchTrxValue, param);
		 }

		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
			getFccBranchBusManager().deleteTransaction(obFileMapperMaster);			
		}
		
		
		public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException{
			return getFccBranchBusManager().fccBranchUniqueCombination(branchCode,aliasBranchCode,id);
		}
}
