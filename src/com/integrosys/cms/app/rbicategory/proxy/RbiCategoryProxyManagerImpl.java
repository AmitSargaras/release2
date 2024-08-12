package com.integrosys.cms.app.rbicategory.proxy;

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
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategoryBusManager;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.rbicategory.trx.OBRbiCategoryTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the diary item modules
 * 
 * @author $Author:  Govind.Sahu $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2011/02 10:03:55 $ Tag: $Name: $
 */
public class RbiCategoryProxyManagerImpl implements IRbiCategoryProxyManager {

	
	private IRbiCategoryBusManager rbiCategoryBusManager;
	
	
	private IRbiCategoryBusManager stagingRbiCategoryBusManager;

    private ITrxControllerFactory trxControllerFactory;



	

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}




	/**
	 * @return List of all RBI Category .
	 */
	
	public List getAllRbiCategoryList()throws RbiCategoryException,TrxParameterException,TransactionException {
		try{


			return getRbiCategoryBusManager().getAllRbiCategoryList( );
		}catch (Exception e) {
			throw new RbiCategoryException("ERROR- Cannot retrive list from database.");
		}
    }
	public List getRbiIndCodeByNameList(String indname)throws RbiCategoryException,TrxParameterException,TransactionException {
		try{


			return getRbiCategoryBusManager().getRbiIndCodeByNameList(indname);
		}catch (Exception e) {
			throw new RbiCategoryException("ERROR- Cannot retrive list from database.");
		}
    }
	/**
	 * @return List of all RBI Category .
	 */
	
	public List searchRbiCategory(String srAlph)throws RbiCategoryException,TrxParameterException,TransactionException {
		try{


			return getRbiCategoryBusManager().searchRbiCategory(srAlph );
		}catch (Exception e) {
			throw new RbiCategoryException("ERROR- Cannot retrive list from database.");
		}
    }
	


	 /**
		 * @return RBI Category according to id .
		 */
		
	 public IRbiCategory getRbiCategoryById(long id) throws RbiCategoryException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getRbiCategoryBusManager().getRbiCategoryById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new RbiCategoryException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new RbiCategoryException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update RBI Category according to criteria .
		 */
		
	 
	 
	 public IRbiCategory updateRbiCategory(IRbiCategory systemBankBranch) throws RbiCategoryException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 IRbiCategory item = (IRbiCategory) systemBankBranch;
			try {
				return getRbiCategoryBusManager().updateRbiCategory(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new RbiCategoryException("ERROR-- Due to null RBI Category object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete RBI Category according to criteria .
		 */
		
	 public IRbiCategory deleteRbiCategory(IRbiCategory systemBankBranch) throws RbiCategoryException, TrxParameterException,
		TransactionException {
		 if(!(systemBankBranch==null)){
			 IRbiCategory item = (IRbiCategory) systemBankBranch;
			try {
				return getRbiCategoryBusManager().deleteRbiCategory(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new RbiCategoryException("ERROR-- Transaction for the RbiCategory object is null.");
			}
		 }else{
			 throw new RbiCategoryException("ERROR-- Cannot delete due to null RbiCategory object.");
		 }
		}
	 /**
		 * @return Checker Approve  RBI Category according to criteria .
		 */
		
	
	public IRbiCategoryTrxValue checkerApproveRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anIRbiCategoryTrxValue)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new RbiCategoryException("The ITrxContext is null!!!");
        }
        if (anIRbiCategoryTrxValue == null) {
            throw new RbiCategoryException
                    ("The IRbiCategoryTrxValue to be updated is null!!!");
        }
        anIRbiCategoryTrxValue = formulateTrxValue(anITrxContext, anIRbiCategoryTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_RBI_CATEGORY);
        return operate(anIRbiCategoryTrxValue, param);
	}
	 /**
	 * @return Checker Reject  RBI Category according to criteria .
	 */
	
	
	public IRbiCategoryTrxValue checkerRejectRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anIRbiCategoryTrxValue)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anIRbiCategoryTrxValue == null) {
	            throw new RbiCategoryException("The IRbiCategoryTrxValue to be updated is null!!!");
	        }
	        anIRbiCategoryTrxValue = formulateTrxValue(anITrxContext, anIRbiCategoryTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_RBI_CATEGORY);
	        return operate(anIRbiCategoryTrxValue, param);
	}
	
	 /**
	 * @return  RBI Category TRX value according to trxId  .
	 */
	

	
	public IRbiCategoryTrxValue getRbiCategoryByTrxID(String aTrxID)
			throws RbiCategoryException, TransactionException,
			CommandProcessingException {
		IRbiCategoryTrxValue trxValue = new OBRbiCategoryTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_RBI_CATEGORY);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_RBI_CATEGORY_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  RBI Category TRX value  .
	 */
	

	public IRbiCategoryTrxValue getRbiCategoryTrxValue(
			long RbiCategoryId) throws RbiCategoryException,
			TrxParameterException, TransactionException {
		if (RbiCategoryId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new RbiCategoryException("Invalid RbiCategoryId");
        }
        IRbiCategoryTrxValue trxValue = new OBRbiCategoryTrxValue();
        trxValue.setReferenceID(String.valueOf(RbiCategoryId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_RBI_CATEGORY);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_RBI_CATEGORY);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close RBI Category  .
	 */
	
	public IRbiCategoryTrxValue makerCloseRejectedRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anIRbiCategoryTrxValue)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new RbiCategoryException("The ITrxContext is null!!!");
        }
        if (anIRbiCategoryTrxValue == null) {
            throw new RbiCategoryException("The IRbiCategoryTrxValue to be updated is null!!!");
        }
        anIRbiCategoryTrxValue = formulateTrxValue(anITrxContext, anIRbiCategoryTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_RBI_CATEGORY);
        return operate(anIRbiCategoryTrxValue, param);
	}

	 /**
	 * @return Maker Close draft RBI Category  .
	 */
	
	public IRbiCategoryTrxValue makerCloseDraftRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anIRbiCategoryTrxValue)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new RbiCategoryException("The ITrxContext is null!!!");
        }
        if (anIRbiCategoryTrxValue == null) {
            throw new RbiCategoryException("The IRbiCategoryTrxValue to be updated is null!!!");
        }
        anIRbiCategoryTrxValue = formulateTrxValue(anITrxContext, anIRbiCategoryTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_RBI_CATEGORY);
        return operate(anIRbiCategoryTrxValue, param);
	}
	 /**
	 * @return Maker Edit RBI Category  .
	 */
	public IRbiCategoryTrxValue makerEditRejectedRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anIRbiCategoryTrxValue, IRbiCategory anRbiCategory)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new RbiCategoryException("The ITrxContext is null!!!");
        }
        if (anIRbiCategoryTrxValue == null) {
            throw new RbiCategoryException("The IRbiCategoryTrxValue to be updated is null!!!");
        }
        if (anRbiCategory == null) {
            throw new RbiCategoryException("The IRbiCategory to be updated is null !!!");
        }
        anIRbiCategoryTrxValue = formulateTrxValue(anITrxContext, anIRbiCategoryTrxValue, anRbiCategory);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_RBI_CATEGORY);
        return operate(anIRbiCategoryTrxValue, param);
	}
	 /**
	 * @return Maker Update RBI Category  .
	 */

	public IRbiCategoryTrxValue makerUpdateRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anICCRbiCategoryTrxValue,
			IRbiCategory anICCRbiCategory)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCRbiCategory to be updated is null !!!");
	        }
	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRbiCategoryTrxValue, anICCRbiCategory);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_RBI_CATEGORY);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update RBI Category  .
	 */

	public IRbiCategoryTrxValue makerUpdateSaveUpdateRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anICCRbiCategoryTrxValue,
			IRbiCategory anICCRbiCategory)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCRbiCategory to be updated is null !!!");
	        }
	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRbiCategoryTrxValue, anICCRbiCategory);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_RBI_CATEGORY);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create RBI Category  .
	 */

	public IRbiCategoryTrxValue makerUpdateSaveCreateRbiCategory(
			ITrxContext anITrxContext,
			IRbiCategoryTrxValue anICCRbiCategoryTrxValue,
			IRbiCategory anICCRbiCategory)
			throws RbiCategoryException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCRbiCategory to be updated is null !!!");
	        }
	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRbiCategoryTrxValue, anICCRbiCategory);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RBI_CATEGORY);
	        return operate(trxValue, param);
	}
	 private IRbiCategoryTrxValue operate(IRbiCategoryTrxValue anIRbiCategoryTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws RbiCategoryException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIRbiCategoryTrxValue, anOBCMSTrxParameter);
	        return (IRbiCategoryTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws RbiCategoryException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (RbiCategoryException ex) {
			 ex.printStackTrace();
			 throw new RbiCategoryException("ERROR--Cannot Get the RBI Category Controller.");
		 }
		 catch (Exception ex) {
			 ex.printStackTrace();
			 throw new RbiCategoryException("ERROR--Cannot Get the RBI Category Controller.");
		 }
	 }
	 
	 private IRbiCategoryTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IRbiCategory anIRbiCategory) {
	        IRbiCategoryTrxValue ccRbiCategoryTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccRbiCategoryTrxValue = new OBRbiCategoryTrxValue(anICMSTrxValue);
	        } else {
	            ccRbiCategoryTrxValue = new OBRbiCategoryTrxValue();
	        }
	        ccRbiCategoryTrxValue = formulateTrxValue(anITrxContext, (IRbiCategoryTrxValue) ccRbiCategoryTrxValue);
	        ccRbiCategoryTrxValue.setStagingRbiCategory(anIRbiCategory);
	        return ccRbiCategoryTrxValue;
	    }
	 private IRbiCategoryTrxValue formulateTrxValue(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue) {
	        anIRbiCategoryTrxValue.setTrxContext(anITrxContext);
	        anIRbiCategoryTrxValue.setTransactionType(ICMSConstant.INSTANCE_RBI_CATEGORY);
	        return anIRbiCategoryTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete RBI Category  .
		 */

	 public IRbiCategoryTrxValue makerDeleteRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anICCRbiCategoryTrxValue, IRbiCategory anICCRbiCategory) throws RbiCategoryException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, anICCRbiCategoryTrxValue, anICCRbiCategory);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_RBI_CATEGORY);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create RBI Category  .
		 */
	 public IRbiCategoryTrxValue makerCreateRbiCategory(ITrxContext anITrxContext, IRbiCategory anICCRbiCategory) throws RbiCategoryException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCRbiCategory to be updated is null !!!");
	        }

	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRbiCategory);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_RBI_CATEGORY);
	        return operate(trxValue, param);
	    }
	 
	 
	 public IRbiCategoryTrxValue makerSaveRbiCategory(ITrxContext anITrxContext, IRbiCategory anICCRbiCategory) throws RbiCategoryException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new RbiCategoryException("The ITrxContext is null!!!");
	        }
	        if (anICCRbiCategory == null) {
	            throw new RbiCategoryException("The ICCRbiCategory to be updated is null !!!");
	        }

	        IRbiCategoryTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRbiCategory);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_RBI_CATEGORY);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
		 * @return List of all RBI Category .
		 */
		
		public boolean getCheckIndustryName(IRbiCategory rbiCategory)throws RbiCategoryException,TrxParameterException,TransactionException {
			try{


				return getRbiCategoryBusManager().getActualRbiCategory(rbiCategory);
			}catch (Exception e) {
				throw new RbiCategoryException("ERROR- Cannot retrive list from database.");
			}
	    }
	 
		/*
		 *  This method return true if Industry Name already approve else return false.
		 */
		public boolean isIndustryNameApprove(String industryNameId) {
			return getRbiCategoryBusManager().isIndustryNameApprove(industryNameId);
			
		}
		
		/*
		 *  This method return true if Rbi Code Category already assign to Industry else return false.
		 */
		public List isRbiCodeCategoryApprove(OBRbiCategory stgObRbiCategory, boolean isEdit, OBRbiCategory actObRbiCategory) {
			return getRbiCategoryBusManager().isRbiCodeCategoryApprove(stgObRbiCategory, isEdit, actObRbiCategory);
		}
		

	/**
	 * @return the rbiCategoryBusManager
	 */
	public IRbiCategoryBusManager getRbiCategoryBusManager() {
		return rbiCategoryBusManager;
	}

	/**
	 * @param rbiCategoryBusManager the rbiCategoryBusManager to set
	 */
	public void setRbiCategoryBusManager(
			IRbiCategoryBusManager rbiCategoryBusManager) {
		this.rbiCategoryBusManager = rbiCategoryBusManager;
	}

	/**
	 * @return the stagingRbiCategoryBusManager
	 */
	public IRbiCategoryBusManager getStagingRbiCategoryBusManager() {
		return stagingRbiCategoryBusManager;
	}

	/**
	 * @param stagingRbiCategoryBusManager the stagingRbiCategoryBusManager to set
	 */
	public void setStagingRbiCategoryBusManager(
			IRbiCategoryBusManager stagingRbiCategoryBusManager) {
		this.stagingRbiCategoryBusManager = stagingRbiCategoryBusManager;
	}

   
	
}
