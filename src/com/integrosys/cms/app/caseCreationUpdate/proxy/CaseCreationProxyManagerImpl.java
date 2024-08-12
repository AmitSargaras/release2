/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.caseCreationUpdate.proxy;



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
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationBusManager;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the CaseCreation modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class CaseCreationProxyManagerImpl implements ICaseCreationProxyManager {

	
	private ICaseCreationBusManager caseCreationBusManager;
	
	
	private ICaseCreationBusManager stagingCaseCreationBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private ICaseCreationBusManager stagingCaseCreationFileMapperIdBusManager;
	
	private ICaseCreationBusManager caseCreationUpdateFileMapperIdBusManager;



	public ICaseCreationBusManager getStagingCaseCreationBusManager() {
		return stagingCaseCreationBusManager;
	}

	public void setStagingCaseCreationBusManager(
			ICaseCreationBusManager stagingCaseCreationBusManager) {
		this.stagingCaseCreationBusManager = stagingCaseCreationBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	
	public ICaseCreationBusManager getCaseCreationBusManager() {
		return caseCreationBusManager;
	}

	public void setCaseCreationBusManager(
			ICaseCreationBusManager caseCreationBusManager) {
		this.caseCreationBusManager = caseCreationBusManager;
	}

	public ICaseCreationBusManager getStagingCaseCreationFileMapperIdBusManager() {
		return stagingCaseCreationFileMapperIdBusManager;
	}

	public void setStagingCaseCreationFileMapperIdBusManager(
			ICaseCreationBusManager stagingCaseCreationFileMapperIdBusManager) {
		this.stagingCaseCreationFileMapperIdBusManager = stagingCaseCreationFileMapperIdBusManager;
	}

	public ICaseCreationBusManager getCaseCreationFileMapperIdBusManager() {
		return caseCreationUpdateFileMapperIdBusManager;
	}

	public void setCaseCreationFileMapperIdBusManager(
			ICaseCreationBusManager caseCreationUpdateFileMapperIdBusManager) {
		this.caseCreationUpdateFileMapperIdBusManager = caseCreationUpdateFileMapperIdBusManager;
	}

	
	/**
	 * @return List of all CaseCreation
	 */
	
	public SearchResult getAllActualCaseCreation(long id)throws CaseCreationException,TrxParameterException,TransactionException {
		try{


			return getCaseCreationBusManager().getAllCaseCreation( id);
		}catch (Exception e) {
			throw new CaseCreationException("ERROR- Cannot retrive list from database.");
		}
    }
	
	public SearchResult getAllActualCaseCreation()throws CaseCreationException,TrxParameterException,TransactionException {
		try{


			return getCaseCreationBusManager().getAllCaseCreation();
		}catch (Exception e) {
			throw new CaseCreationException("ERROR- Cannot retrive list from database.");
		}
    }
	
	public SearchResult getAllActualCaseCreationBranchMenu(String branchCode)throws CaseCreationException,TrxParameterException,TransactionException {
		try{


			return getCaseCreationBusManager().getAllCaseCreationBranchMenu(branchCode);
		}catch (Exception e) {
			throw new CaseCreationException("ERROR- Cannot retrive list from database.");
		}
    }
	


	/**
	 * @return List of all CaseCreation according to criteria .
	 */
	
	
	public List searchCaseCreation(String login) throws CaseCreationException,TrxParameterException,TransactionException {
	 	return getCaseCreationBusManager().searchCaseCreation(login);

    }
	/**
	 * @return List of all CaseCreation according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws CaseCreationException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getCaseCreationBusManager().getAllCaseCreation( searchBy, searchText);
		}else{
			throw new CaseCreationException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return CaseCreation according to id .
		 */
		
	 public ICaseCreation getCaseCreationById(long id) throws CaseCreationException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getCaseCreationBusManager().getCaseCreationById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new CaseCreationException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new CaseCreationException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update CaseCreation according to criteria .
		 */
		
	 
	 
	 public ICaseCreation updateCaseCreation(ICaseCreation caseCreationUpdate) throws CaseCreationException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 ICaseCreation item = (ICaseCreation) caseCreationUpdate;
			try {
				return getCaseCreationBusManager().updateCaseCreation(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CaseCreationException("ERROR-- Due to null CaseCreation object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete CaseCreation according to criteria .
		 */
		
	 public ICaseCreation deleteCaseCreation(ICaseCreation caseCreationUpdate) throws CaseCreationException, TrxParameterException,
		TransactionException {
		 if(!(caseCreationUpdate==null)){
		 ICaseCreation item = (ICaseCreation) caseCreationUpdate;
			try {
				return getCaseCreationBusManager().deleteCaseCreation(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new CaseCreationException("ERROR-- Transaction for the CaseCreation object is null.");
			}
		 }else{
			 throw new CaseCreationException("ERROR-- Cannot delete due to null CaseCreation object.");
		 }
		}
	 /**
		 * @return Checker Approve  CaseCreation according to criteria .
		 */
		
	
	public ICaseCreationTrxValue checkerApproveCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseCreationException("The ITrxContext is null!!!");
        }
        if (anICaseCreationTrxValue == null) {
            throw new CaseCreationException
                    ("The ICaseCreationTrxValue to be updated is null!!!");
        }
        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION);
        return operate(anICaseCreationTrxValue, param);
	}
	 /**
	 * @return Checker Reject  CaseCreation according to criteria .
	 */
	
	
	public ICaseCreationTrxValue checkerRejectCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICaseCreationTrxValue == null) {
	            throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
	        }
	        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CASECREATION);
	        return operate(anICaseCreationTrxValue, param);
	}
	
	 /**
	 * @return  CaseCreation TRX value according to trxId  .
	 */
	

	
	public ICaseCreationTrxValue getCaseCreationByTrxID(String aTrxID)
			throws CaseCreationException, TransactionException,
			CommandProcessingException {
		ICaseCreationTrxValue trxValue = new OBCaseCreationTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_CASECREATION);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CASECREATION_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  CaseCreation TRX value  .
	 */
	

	public ICaseCreationTrxValue getCaseCreationTrxValue(
			long aCaseCreationId) throws CaseCreationException,
			TrxParameterException, TransactionException {
		if (aCaseCreationId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new CaseCreationException("Invalid CaseCreationId");
        }
        ICaseCreationTrxValue trxValue = new OBCaseCreationTrxValue();
        trxValue.setReferenceID(String.valueOf(aCaseCreationId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_CASECREATION);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CASECREATION);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close CaseCreation.
	 */
	
	public ICaseCreationTrxValue makerCloseRejectedCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseCreationException("The ITrxContext is null!!!");
        }
        if (anICaseCreationTrxValue == null) {
            throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
        }
        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CASECREATION);
        return operate(anICaseCreationTrxValue, param);
	}

	 /**
	 * @return Maker Close draft CaseCreation
	 */
	
	public ICaseCreationTrxValue makerCloseDraftCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseCreationException("The ITrxContext is null!!!");
        }
        if (anICaseCreationTrxValue == null) {
            throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
        }
        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CASECREATION);
        return operate(anICaseCreationTrxValue, param);
	}
	 /**
	 * @return Maker Edit CaseCreation
	 */
	public ICaseCreationTrxValue makerEditRejectedCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue, ICaseCreation anCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseCreationException("The ITrxContext is null!!!");
        }
        if (anICaseCreationTrxValue == null) {
            throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
        }
        if (anCaseCreation == null) {
            throw new CaseCreationException("The ICaseCreation to be updated is null !!!");
        }
        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue, anCaseCreation);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CASECREATION);
        return operate(anICaseCreationTrxValue, param);
	}
	
	public ICaseCreationTrxValue makerEditRejectedCaseCreationBranch(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICaseCreationTrxValue, ICaseCreation anCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseCreationException("The ITrxContext is null!!!");
        }
        if (anICaseCreationTrxValue == null) {
            throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
        }
        if (anCaseCreation == null) {
            throw new CaseCreationException("The ICaseCreation to be updated is null !!!");
        }
        anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue, anCaseCreation);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CASECREATION_BRANCH);
        return operate(anICaseCreationTrxValue, param);
	}
	 /**
	 * @return Maker Update CaseCreation
	 */

	public ICaseCreationTrxValue makerUpdateCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICCCaseCreationTrxValue,
			ICaseCreation anICCCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }
	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseCreationTrxValue, anICCCaseCreation);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION);
	        return operate(trxValue, param);
	}
	
	
	
	public ICaseCreationTrxValue makerUpdateCaseCreationBranch(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICCCaseCreationTrxValue,
			ICaseCreation anICCCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }
	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseCreationTrxValue, anICCCaseCreation);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CASECREATION_BRANCH);
	        return operate(trxValue, param);
	}
	 /**
	 * @return Maker Update CaseCreation
	 */

	public ICaseCreationTrxValue makerUpdateSaveUpdateCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICCCaseCreationTrxValue,
			ICaseCreation anICCCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }
	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseCreationTrxValue, anICCCaseCreation);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CASECREATION);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create CaseCreation
	 */

	public ICaseCreationTrxValue makerUpdateSaveCreateCaseCreation(
			ITrxContext anITrxContext,
			ICaseCreationTrxValue anICCCaseCreationTrxValue,
			ICaseCreation anICCCaseCreation)
			throws CaseCreationException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }
	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseCreationTrxValue, anICCCaseCreation);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CASECREATION);
	        return operate(trxValue, param);
	}
	 private ICaseCreationTrxValue operate(ICaseCreationTrxValue anICaseCreationTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws CaseCreationException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anICaseCreationTrxValue, anOBCMSTrxParameter);
	        return (ICaseCreationTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws CaseCreationException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (CaseCreationException ex) {
			 throw new CaseCreationException("ERROR--Cannot Get the CaseCreation Controller.");
		 }
		 catch (Exception ex) {
			 throw new CaseCreationException("ERROR--Cannot Get the CaseCreation Controller.");
		 }
	 }
	 
	 private ICaseCreationTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ICaseCreation anICaseCreation) {
	        ICaseCreationTrxValue ccCaseCreationTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCaseCreationTrxValue = new OBCaseCreationTrxValue(anICMSTrxValue);
	        } else {
	            ccCaseCreationTrxValue = new OBCaseCreationTrxValue();
	        }
	        ccCaseCreationTrxValue = formulateTrxValue(anITrxContext, (ICaseCreationTrxValue) ccCaseCreationTrxValue);
	        ccCaseCreationTrxValue.setStagingCaseCreation(anICaseCreation);
	        return ccCaseCreationTrxValue;
	    }
	 private ICaseCreationTrxValue formulateTrxValue(ITrxContext anITrxContext, ICaseCreationTrxValue anICaseCreationTrxValue) {
	        anICaseCreationTrxValue.setTrxContext(anITrxContext);
	        anICaseCreationTrxValue.setTransactionType(ICMSConstant.INSTANCE_CASECREATION);
	        return anICaseCreationTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete CaseCreation
		 */

	 public ICaseCreationTrxValue makerDeleteCaseCreation(ITrxContext anITrxContext, ICaseCreationTrxValue anICCCaseCreationTrxValue, ICaseCreation anICCCaseCreation) throws CaseCreationException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseCreationTrxValue, anICCCaseCreation);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CASECREATION);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create CaseCreation
		 */
	 public ICaseCreationTrxValue makerCreateCaseCreation(ITrxContext anITrxContext, ICaseCreation anICCCaseCreation) throws CaseCreationException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }

	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseCreation);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CASECREATION);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save CaseCreation
		 */
	 
	 public ICaseCreationTrxValue makerSaveCaseCreation(ITrxContext anITrxContext, ICaseCreation anICCCaseCreation) throws CaseCreationException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseCreationException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseCreation == null) {
	            throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
	        }

	        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseCreation);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CASECREATION);
	        return operate(trxValue, param);
	    }

	
	//------------------------------------File Upload-----------------------------------------------------
		
		 private ICaseCreationTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 ICaseCreationTrxValue ccCaseCreationTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccCaseCreationTrxValue = new OBCaseCreationTrxValue(anICMSTrxValue);
		        } else {
		            ccCaseCreationTrxValue = new OBCaseCreationTrxValue();
		        }
		        ccCaseCreationTrxValue = formulateTrxValueID(anITrxContext, (ICaseCreationTrxValue) ccCaseCreationTrxValue);
		        ccCaseCreationTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccCaseCreationTrxValue;
		    }
		 
		 private ICaseCreationTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
			 ICaseCreationTrxValue ccCaseCreationTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccCaseCreationTrxValue = new OBCaseCreationTrxValue(anICMSTrxValue);
		        } else {
		            ccCaseCreationTrxValue = new OBCaseCreationTrxValue();
		        }
		        ccCaseCreationTrxValue = formulateTrxValueID(anITrxContext, (ICaseCreationTrxValue) ccCaseCreationTrxValue);
		        ccCaseCreationTrxValue.setStagingFileMapperID(fileId);
		        return ccCaseCreationTrxValue;
		    }
		 private ICaseCreationTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICaseCreationTrxValue anICaseCreationTrxValue) {
		        anICaseCreationTrxValue.setTrxContext(anITrxContext);
		        anICaseCreationTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_CASECREATION);
		        return anICaseCreationTrxValue;
		    }
	    
		 
		 /**
			 * @return Maker insert a fileID to generate a transation.
			 */
		 public ICaseCreationTrxValue makerInsertMapperCaseCreation(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws CaseCreationException, TrxParameterException,
					TransactionException {
				// TODO Auto-generated method stub
				if (anITrxContext == null) {
		            throw new CaseCreationException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new CaseCreationException("The OBFileMapperID to be updated is null !!!");
		        }

		        ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 /**
			 * @return Maker check if previous upload is pending.
			 */
		 public boolean isPrevFileUploadPending() throws CaseCreationException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getCaseCreationBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new CaseCreationException("ERROR-- Due to null CaseCreation object cannot update.");
				}
			}
		 
		 /**
			 * @return Maker insert uploaded files in Staging table.
			 */
		 
		 public int insertCaseCreation(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws CaseCreationException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getCaseCreationBusManager().insertCaseCreation(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new CaseCreationException("ERROR-- Due to null CaseCreation object cannot update.");
				}
			}
		 
		 /**
			 * @return create record with TransID.
			 */
		 
		 public ICaseCreationTrxValue getInsertFileByTrxID(String trxID)
			throws CaseCreationException, TransactionException,
			CommandProcessingException {
			 	ICaseCreationTrxValue trxValue = new OBCaseCreationTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 /**
			 * @return Pagination for uploaded files in CaseCreation.
			 */
		 public List getAllStage(String searchBy, String login)throws CaseCreationException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getCaseCreationBusManager().getAllStageCaseCreation( searchBy, login);
			}else{
				throw new CaseCreationException("ERROR- Search criteria is null.");
			}
		  }
		
		 /**
			 * @return Checker approval for uploaded files.
			 */
		 
		 public ICaseCreationTrxValue checkerApproveInsertCaseCreation(
		 		ITrxContext anITrxContext,
		 		ICaseCreationTrxValue anICaseCreationTrxValue)
		 		throws CaseCreationException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		        throw new CaseCreationException("The ITrxContext is null!!!");
		    }
		    if (anICaseCreationTrxValue == null) {
		        throw new CaseCreationException
		                ("The ICaseCreationTrxValue to be updated is null!!!");
		    }
		    anICaseCreationTrxValue = formulateTrxValueID(anITrxContext, anICaseCreationTrxValue);
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		    return operate(anICaseCreationTrxValue, param);
		 }
		 
		 /**
			 * @return list of files uploaded in staging table of CaseCreation.
			 */
		 public List getFileMasterList(String searchBy)throws CaseCreationException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getCaseCreationBusManager().getFileMasterList( searchBy);
			}else{
				throw new CaseCreationException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return Maker insert upload files.
			 */
		 public ICaseCreation insertActualCaseCreation(String sysId) throws CaseCreationException,TrxParameterException,TransactionException

		 {
		  if(sysId != null){
		 	 try {
		 		 return getCaseCreationBusManager().insertActualCaseCreation(sysId);
		 	 } catch (Exception e) {		 		
		 		 e.printStackTrace();
		 		 throw new CaseCreationException("ERROR- Transaction for the Id is invalid.");
		 	 }
		  }else{
		 	 throw new CaseCreationException("ERROR- Id for retrival is null.");
		  }
		 }
		 
		 /**
			 * @return Checker create file master in CaseCreation.
			 */
		 
		 public ICaseCreationTrxValue checkerCreateCaseCreation(ITrxContext anITrxContext, ICaseCreation anICCCaseCreation, String refStage) throws CaseCreationException,TrxParameterException,
		 TransactionException {
		     if (anITrxContext == null) {
		         throw new CaseCreationException("The ITrxContext is null!!!");
		     }
		     if (anICCCaseCreation == null) {
		         throw new CaseCreationException("The ICCCaseCreation to be updated is null !!!");
		     }

		     ICaseCreationTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseCreation);
		     trxValue.setFromState("PENDING_CREATE");
		     trxValue.setReferenceID(String.valueOf(anICCCaseCreation.getId()));
		     trxValue.setStagingReferenceID(refStage);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
		     return operate(trxValue, param);
		 }
		 
		 /**
			 * @return Checker Reject for upload files CaseCreation.
			 */

		 public ICaseCreationTrxValue checkerRejectInsertCaseCreation(
		 	ITrxContext anITrxContext,
		 	ICaseCreationTrxValue anICaseCreationTrxValue)
		 	throws CaseCreationException, TrxParameterException,
		 	TransactionException {
		 	if (anITrxContext == null) {
		 	  throw new CaseCreationException("The ITrxContext is null!!!");
		 	}
		 	if (anICaseCreationTrxValue == null) {
		 	  throw new CaseCreationException
		 	          ("The ICaseCreationTrxValue to be updated is null!!!");
		 	}
		 		anICaseCreationTrxValue = formulateTrxValueID(anITrxContext, anICaseCreationTrxValue);
		 		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		 	return operate(anICaseCreationTrxValue, param);
		 }
		 
		 /**
			 * @return Maker Close rejected files CaseCreation.
			 */

		 public ICaseCreationTrxValue makerInsertCloseRejectedCaseCreation(
		 		ITrxContext anITrxContext,
		 		ICaseCreationTrxValue anICaseCreationTrxValue)
		 		throws CaseCreationException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		         throw new CaseCreationException("The ITrxContext is null!!!");
		     }
		     if (anICaseCreationTrxValue == null) {
		         throw new CaseCreationException("The ICaseCreationTrxValue to be updated is null!!!");
		     }
		     anICaseCreationTrxValue = formulateTrxValue(anITrxContext, anICaseCreationTrxValue);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
		     return operate(anICaseCreationTrxValue, param);
		 }

		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
			getCaseCreationBusManager().deleteTransaction(obFileMapperMaster);			
		}

		
		public List getCaseCreationByBranchCode(String branchCode) {
			
			return getCaseCreationBusManager().getCaseCreationByBranchCode(branchCode); 
		}
}
