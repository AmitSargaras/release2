package com.integrosys.cms.app.relationshipmgr.proxy;

/**
 * This RelationshipMgrProxyManagerImpl implements the methods that will be available to the
 * operating on a Relationship Manager Master
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 10:03:55 
 */

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
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrBusManager;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class RelationshipMgrProxyManagerImpl implements IRelationshipMgrProxyManager {

	private IRelationshipMgrBusManager relationshipMgrBusManager;
	
	private IRelationshipMgrBusManager stagingRelationshipMgrBusManager;
	
    private ITrxControllerFactory trxControllerFactory;

	/**
	 * @return the relationshipMgrBusManager
	 */
	public IRelationshipMgrBusManager getRelationshipMgrBusManager() {
		return relationshipMgrBusManager;
	}


	/**
	 * @param relationshipMgrBusManager the relationshipMgrBusManager to set
	 */
	public void setRelationshipMgrBusManager(
			IRelationshipMgrBusManager relationshipMgrBusManager) {
		this.relationshipMgrBusManager = relationshipMgrBusManager;
	}


	/**
	 * @return the stagingRelationshipMgrBusManager
	 */
	public IRelationshipMgrBusManager getStagingRelationshipMgrBusManager() {
		return stagingRelationshipMgrBusManager;
	}


	/**
	 * @param stagingRelationshipMgrBusManager the stagingRelationshipMgrBusManager to set
	 */
	public void setStagingRelationshipMgrBusManager(
			IRelationshipMgrBusManager stagingRelationshipMgrBusManager) {
		this.stagingRelationshipMgrBusManager = stagingRelationshipMgrBusManager;
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
	 * @return the SearchResult
	 */
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrBusManager().getRelationshipMgrList(rmCode,rmName);
	}
	
	public SearchResult getRelationshipMgrList(String regionId) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrBusManager().getRelationshipMgrList(regionId);
	}
	
	public IRelationshipMgr getRelationshipMgrById(long id) throws RelationshipMgrException,TrxParameterException,TransactionException 
    {
	 if(id!=0){
		 return getRelationshipMgrBusManager().getRelationshipMgrById(id);
	 }else{
		 throw new OtherBankException("ERROR-- Key for RelationshipMgr is null.");
	 }
	 
    }
	
	/**
	 * @return the OtherBank details
	 * @throws ConcurrentUpdateException 
	 * @throws TransactionException 
	 * @throws TrxParameterException 
	 */
	 public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException, TrxParameterException, TransactionException,ConcurrentUpdateException {
		if(relationshipMgr!=null){
			return (IRelationshipMgr)getRelationshipMgrBusManager().updateRelationshipMgr(relationshipMgr);
		}else{
			 throw new RelationshipMgrException("Relationship Manager Object is null.");
		 }	
	}
    
	/**
	 * @return the RelationshipMgr details
	 */
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException , TrxParameterException, TransactionException{
		if(relationshipMgr!=null){
			return (IRelationshipMgr)getRelationshipMgrBusManager().deleteRelationshipMgr(relationshipMgr);
		}else{
			 throw new RelationshipMgrException("Relationship Manager Object is null.");
		}		
	}
	
	
	/**
	 * @return the RelationshipMgr details
	 */
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException {
		return (IRelationshipMgr)getRelationshipMgrBusManager().createRelationshipMgr(relationshipMgr);
	}
	
	/**
	  * @return RelationshipMgrTrx Value
	  * @param RelationshipMgr Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	public IRelationshipMgrTrxValue getRelationshipMgrTrxValue(long aRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
       if (aRelationshipMgr == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new RelationshipMgrException("Invalid RelationshipMgrId");
       }
       IRelationshipMgrTrxValue trxValue = new OBRelationshipMgrTrxValue();
       trxValue.setReferenceID(String.valueOf(aRelationshipMgr));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_RELATIONSHIP_MGR);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_RELATIONSHIP_MGR);
       return operate(trxValue, param);
   }
	
	/**
	  * 
	  * @param anIRelationshipMgrTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IRelationshipMgrTrxValue
	  * @throws RelationshipMgrException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IRelationshipMgrTrxValue operate(IRelationshipMgrTrxValue anIRelationshipMgrTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIRelationshipMgrTrxValue, anOBCMSTrxParameter);
	        return (IRelationshipMgrTrxValue) result.getTrxValue();
	    }
	 
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws RelationshipMgrException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws RelationshipMgrException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new RelationshipMgrException("ERROR--Cannot update already deleted record");
		 }
		 catch (RelationshipMgrException ex) {
			 throw new RelationshipMgrException(ex.toString());
		 }
	 }
	 
	 /**
	  * @return updated Relationship Manager Trx value Object
	  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
	  * 
	  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
	  */
	 
	 public IRelationshipMgrTrxValue makerUpdateRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anICCRelationshipMgr == null) {
	            throw new RelationshipMgrException("The ICCRelationshipMgr to be updated is null !!!");
	        }
	        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_RELATIONSHIP_MGR);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
	  * @return updated Relationship Manager Trx value Object
	  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
	  * 
	  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
	  */
	 
	 public IRelationshipMgrTrxValue makerDeleteRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) 
	 	throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anICCRelationshipMgr == null) {
	            throw new RelationshipMgrException("The ICCRelationshipMgr to be updated is null !!!");
	        }
	        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_RELATIONSHIP_MGR);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated Relationship Manager Trx value Object
	  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
	  */
	 public IRelationshipMgrTrxValue makerEditRejectedRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue, IRelationshipMgr anIRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anIRelationshipMgrTrxValue == null) {
	            throw new RelationshipMgrException("The IRelationshipMgrTrxValue to be updated is null!!!");
	        }
	        if (anIRelationshipMgr == null) {
	            throw new RelationshipMgrException("The IRelationshipMgr to be updated is null !!!");
	        }
	        anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue, anIRelationshipMgr);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RELATIONSHIP_MGR);
	        return operate(anIRelationshipMgrTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIRelationshipMgr
	  * @return IRelationshipMgrTrxValue 
	  * 
	  * 
	  */
	 
	 private IRelationshipMgrTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IRelationshipMgr anIRelationshipMgr) {
	        IRelationshipMgrTrxValue ccRelationshipMgrTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue(anICMSTrxValue);
	        } else {
	            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue();
	        }
	        ccRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, (IRelationshipMgrTrxValue) ccRelationshipMgrTrxValue);
	        ccRelationshipMgrTrxValue.setStagingRelationshipMgr(anIRelationshipMgr);
	        return ccRelationshipMgrTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIRelationshipMgrTrxValue
	  * @return IRelationshipMgrTrxValue
	  */
	 private IRelationshipMgrTrxValue formulateTrxValue(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) {
	        anIRelationshipMgrTrxValue.setTrxContext(anITrxContext);
	        anIRelationshipMgrTrxValue.setTransactionType(ICMSConstant.INSTANCE_RELATIONSHIP_MGR);
	        return anIRelationshipMgrTrxValue;
	    }

	 /**
		  * @return RelationshipMgrTrx Value
		  * @param RelationshipMgr Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public IRelationshipMgrTrxValue getRelationshipMgrByTrxID(String trxID) throws RelationshipMgrException,TransactionException,CommandProcessingException{
		  IRelationshipMgrTrxValue trxValue = new OBRelationshipMgrTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_RELATIONSHIP_MGR);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_RELATIONSHIP_MGR_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return Relationship Manager Trx Value
	   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public IRelationshipMgrTrxValue checkerApproveRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) throws RelationshipMgrException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anIRelationshipMgrTrxValue == null) {
	            throw new RelationshipMgrException
	                    ("The IRelationshipMgrTrxValue to be updated is null!!!");
	        }
	        anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_RELATIONSHIP_MGR);
	        return operate(anIRelationshipMgrTrxValue, param);
	    }
	  /**
	   * @return Relationship Manager Trx Value
	   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public IRelationshipMgrTrxValue checkerRejectRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anIRelationshipMgrTrxValue == null) {
	            throw new RelationshipMgrException("The IRelationshipMgrTrxValue to be updated is null!!!");
	        }
	        anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_RELATIONSHIP_MGR);
	        return operate(anIRelationshipMgrTrxValue, param);
	    }
	  /**
	   * @return Relationship Manager Trx Value
	   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IRelationshipMgrTrxValue makerCloseRejectedRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anIRelationshipMgrTrxValue == null) {
	            throw new RelationshipMgrException("The IRelationshipMgrTrxValue to be updated is null!!!");
	        }
	        anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RELATIONSHIP_MGR);
	        return operate(anIRelationshipMgrTrxValue, param);
	    }
	  
	  /**
	   * @return Relationship Manager Trx Value
	   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IRelationshipMgrTrxValue makerCloseDraftRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) throws RelationshipMgrException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new RelationshipMgrException("The ITrxContext is null!!!");
	        }
	        if (anIRelationshipMgrTrxValue == null) {
	            throw new RelationshipMgrException("The IRelationshipMgrTrxValue to be updated is null!!!");
	        }
	        anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_RELATIONSHIP_MGR);
	        return operate(anIRelationshipMgrTrxValue, param);
	    }
	  
	  /**
		  * @return updated Relationship Manager Trx value Object
		  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
		  * 
		  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
		  */
		 
		 public IRelationshipMgrTrxValue makerCreateRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new RelationshipMgrException("The ITrxContext is null!!!");
		        }
		        if (anICCRelationshipMgr == null) {
		            throw new RelationshipMgrException("The IRelationshipMgr to be created is null !!!");
		        }
		        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RELATIONSHIP_MGR);
		        return operate(trxValue, param);
		    }
		 
		 /**
		  * @return updated Relationship Manager Trx value Object
		  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
		  * 
		  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
		  */
		 
		 public IRelationshipMgrTrxValue makerSaveRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new RelationshipMgrException("The ITrxContext is null!!!");
		        }
		        if (anICCRelationshipMgr == null) {
		            throw new RelationshipMgrException("The IRelationshipMgr to be created is null !!!");
		        }
		        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
		        trxValue.setStatus(ICMSConstant.STATE_PENDING_PERFECTION);
		        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREATE_RELATIONSHIP_MGR);
		        return operate(trxValue, param);
		    }
		 
		 public boolean isRMCodeUnique(String rmCode){
			 return getRelationshipMgrBusManager().isRMCodeUnique(rmCode);
		 }
		 
		 /**
		  * @return updated Relationship Manager Trx value Object
		  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
		  * 
		  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
		  */
		 
		 public IRelationshipMgrTrxValue makerUpdateCreateRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new RelationshipMgrException("The ITrxContext is null!!!");
		        }
		        if (anICCRelationshipMgr == null) {
		            throw new RelationshipMgrException("The ICCRelationshipMgr to be updated is null !!!");
		        }
		        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RELATIONSHIP_MGR);
		        return operate(trxValue, param);
		    }
		 
		 /**
		  * @return updated Relationship Manager Trx value Object
		  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
		  * 
		  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
		  */
		 
		 public IRelationshipMgrTrxValue makerUpdateSaveRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgrTrxValue anICCRelationshipMgrTrxValue, IRelationshipMgr anICCRelationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new RelationshipMgrException("The ITrxContext is null!!!");
		        }
		        if (anICCRelationshipMgr == null) {
		            throw new RelationshipMgrException("The ICCRelationshipMgr to be updated is null !!!");
		        }
		        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRelationshipMgrTrxValue, anICCRelationshipMgr);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_RELATIONSHIP_MGR);
		        return operate(trxValue, param);
		    }
		 
		 
		//------------------------------------File Upload-----------------------------------------------------
			
		 private IRelationshipMgrTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 IRelationshipMgrTrxValue ccRelationshipMgrTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue(anICMSTrxValue);
		        } else {
		            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue();
		        }
		        ccRelationshipMgrTrxValue = formulateTrxValueID(anITrxContext, (IRelationshipMgrTrxValue) ccRelationshipMgrTrxValue);
		        ccRelationshipMgrTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccRelationshipMgrTrxValue;
		    }
		 
		 private IRelationshipMgrTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
			 IRelationshipMgrTrxValue ccRelationshipMgrTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue(anICMSTrxValue);
		        } else {
		            ccRelationshipMgrTrxValue = new OBRelationshipMgrTrxValue();
		        }
		        ccRelationshipMgrTrxValue = formulateTrxValueID(anITrxContext, (IRelationshipMgrTrxValue) ccRelationshipMgrTrxValue);
		        ccRelationshipMgrTrxValue.setStagingFileMapperID(fileId);
		        return ccRelationshipMgrTrxValue;
		    }
		 private IRelationshipMgrTrxValue formulateTrxValueID(ITrxContext anITrxContext, IRelationshipMgrTrxValue anIRelationshipMgrTrxValue) {
		        anIRelationshipMgrTrxValue.setTrxContext(anITrxContext);
		        anIRelationshipMgrTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_RELATIONSHIP_MGR);
		        return anIRelationshipMgrTrxValue;
		    }
	    
		 
		 /**
			 * @return Maker insert a fileID to generate a transation.
			 */
		 public IRelationshipMgrTrxValue makerInsertMapperRelationshipMgr(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws RelationshipMgrException, TrxParameterException,
					TransactionException {
				if (anITrxContext == null) {
		            throw new RelationshipMgrException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new RelationshipMgrException("The OBFileMapperID to be updated is null !!!");
		        }

		        IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 /**
			 * @return Maker check if previous upload is pending.
			 */
		 public boolean isPrevFileUploadPending() throws RelationshipMgrException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getRelationshipMgrBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RelationshipMgrException("ERROR-- Due to null RelationshipMgr object cannot update.");
				}
			}
		 
		 /**
			 * @return Maker insert uploaded files in Staging table.
			 */
		 
		 public int insertRelationshipMgr(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws RelationshipMgrException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getRelationshipMgrBusManager().insertRelationshipMgr(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new RelationshipMgrException("ERROR-- Due to null RelationshipMgr object cannot update.");
				}
			}
		 
		 /**
			 * @return create record with TransID.
			 */
		 
		 public IRelationshipMgrTrxValue getInsertFileByTrxID(String trxID)
			throws RelationshipMgrException, TransactionException,
			CommandProcessingException {
			 	IRelationshipMgrTrxValue trxValue = new OBRelationshipMgrTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_RELATIONSHIP_MGR);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 /**
			 * @return Pagination for uploaded files in RelationshipMgr.
			 */
		 public List getAllStage(String searchBy, String login)throws RelationshipMgrException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getRelationshipMgrBusManager().getAllStageRelationshipMgr( searchBy, login);
			}else{
				throw new RelationshipMgrException("ERROR- Search criteria is null.");
			}
		  }
		
		 /**
			 * @return Checker approval for uploaded files.
			 */
		 
		 public IRelationshipMgrTrxValue checkerApproveInsertRelationshipMgr(
		 		ITrxContext anITrxContext,
		 		IRelationshipMgrTrxValue anIRelationshipMgrTrxValue)
		 		throws RelationshipMgrException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		        throw new RelationshipMgrException("The ITrxContext is null!!!");
		    }
		    if (anIRelationshipMgrTrxValue == null) {
		        throw new RelationshipMgrException
		                ("The IRelationshipMgrTrxValue to be updated is null!!!");
		    }
		    anIRelationshipMgrTrxValue = formulateTrxValueID(anITrxContext, anIRelationshipMgrTrxValue);
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		    return operate(anIRelationshipMgrTrxValue, param);
		 }
		 
		 /**
			 * @return list of files uploaded in staging table of RelationshipMgr.
			 */
		 public List getFileMasterList(String searchBy)throws RelationshipMgrException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getRelationshipMgrBusManager().getFileMasterList( searchBy);
			}else{
				throw new RelationshipMgrException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return Maker insert upload files.
			 */
		 public IRelationshipMgr insertActualRelationshipMgr(String sysId) throws RelationshipMgrException,TrxParameterException,TransactionException

		 {
		  if(sysId != null){
		 	 try {
		 		 return getRelationshipMgrBusManager().insertActualRelationshipMgr(sysId);
		 	 } catch (Exception e) {		 		
		 		 e.printStackTrace();
		 		 throw new RelationshipMgrException("ERROR- Transaction for the Id is invalid.");
		 	 }
		  }else{
		 	 throw new RelationshipMgrException("ERROR- Id for retrival is null.");
		  }
		 }
		 
		 /**
			 * @return Checker create file master in RelationshipMgr.
			 */
		 
		 public IRelationshipMgrTrxValue checkerCreateRelationshipMgr(ITrxContext anITrxContext, IRelationshipMgr anICCRelationshipMgr, String refStage) throws RelationshipMgrException,TrxParameterException,
		 TransactionException {
		     if (anITrxContext == null) {
		         throw new RelationshipMgrException("The ITrxContext is null!!!");
		     }
		     if (anICCRelationshipMgr == null) {
		         throw new RelationshipMgrException("The ICCRelationshipMgr to be updated is null !!!");
		     }

		     IRelationshipMgrTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRelationshipMgr);
		     trxValue.setFromState("PENDING_CREATE");
		     trxValue.setReferenceID(String.valueOf(anICCRelationshipMgr.getId()));
		     trxValue.setStagingReferenceID(refStage);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
		     return operate(trxValue, param);
		 }
		 
		 /**
			 * @return Checker Reject for upload files RelationshipMgr.
			 */

		 public IRelationshipMgrTrxValue checkerRejectInsertRelationshipMgr(
		 	ITrxContext anITrxContext,
		 	IRelationshipMgrTrxValue anIRelationshipMgrTrxValue)
		 	throws RelationshipMgrException, TrxParameterException,
		 	TransactionException {
		 	if (anITrxContext == null) {
		 	  throw new RelationshipMgrException("The ITrxContext is null!!!");
		 	}
		 	if (anIRelationshipMgrTrxValue == null) {
		 	  throw new RelationshipMgrException
		 	          ("The IRelationshipMgrTrxValue to be updated is null!!!");
		 	}
		 		anIRelationshipMgrTrxValue = formulateTrxValueID(anITrxContext, anIRelationshipMgrTrxValue);
		 		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		 	return operate(anIRelationshipMgrTrxValue, param);
		 }
		 
		 /**
			 * @return Maker Close rejected files RelationshipMgr.
			 */

		 public IRelationshipMgrTrxValue makerInsertCloseRejectedRelationshipMgr(
		 		ITrxContext anITrxContext,
		 		IRelationshipMgrTrxValue anIRelationshipMgrTrxValue)
		 		throws RelationshipMgrException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		         throw new RelationshipMgrException("The ITrxContext is null!!!");
		     }
		     if (anIRelationshipMgrTrxValue == null) {
		         throw new RelationshipMgrException("The IRelationshipMgrTrxValue to be updated is null!!!");
		     }
		     anIRelationshipMgrTrxValue = formulateTrxValue(anITrxContext, anIRelationshipMgrTrxValue);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
		     return operate(anIRelationshipMgrTrxValue, param);
		 }
		 
		 /**
			 * @return the ArrayList
			 */
			public List getRegionList(String countryId) throws OtherBankException{
				return (List)getRelationshipMgrBusManager().getRegionList(countryId);
			}

			public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
				getRelationshipMgrBusManager().deleteTransaction(obFileMapperMaster);					
			}


			public boolean isValidRegionCode(String regionCode) {
				return getRelationshipMgrBusManager().isValidRegionCode(regionCode);
			}


			public boolean isRelationshipMgrNameUnique(String relationshipMgrName) {
				return getRelationshipMgrBusManager().isRelationshipMgrNameUnique(relationshipMgrName);
			}
			
			
			public boolean isEmployeeIdUnique(String employeeId) {
				return getRelationshipMgrBusManager().isEmployeeIdUnique(employeeId);
			}
			
			public IHRMSData getHRMSEmpDetails(String rmEmpID) {
				return getRelationshipMgrBusManager().getHRMSEmpDetails(rmEmpID);
			}

			public IRelationshipMgr getRMDetails(String rmID) {
				return getRelationshipMgrBusManager().getRMDetails(rmID);
			}
			
			public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode) {
				return getRelationshipMgrBusManager().getLocalCAD(cadEmployeeCode,cadBranchCode);
			}
			
			public void insertHRMSData(String[] data) {
				getRelationshipMgrBusManager().insertHRMSData(data);
			}
			public void updateHRMSData(IHRMSData  ihrmsData,String[] data) {
				getRelationshipMgrBusManager().updateHRMSData(ihrmsData,data);
			}
}
