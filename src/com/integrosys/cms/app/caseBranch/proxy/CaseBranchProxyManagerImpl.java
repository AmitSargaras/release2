/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.caseBranch.proxy;



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
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranchBusManager;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the CaseBranch modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class CaseBranchProxyManagerImpl implements ICaseBranchProxyManager {

	
	private ICaseBranchBusManager caseBranchBusManager;
	
	
	private ICaseBranchBusManager stagingCaseBranchBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private ICaseBranchBusManager stagingCaseBranchFileMapperIdBusManager;
	
	private ICaseBranchBusManager caseBranchFileMapperIdBusManager;



	public ICaseBranchBusManager getStagingCaseBranchBusManager() {
		return stagingCaseBranchBusManager;
	}

	public void setStagingCaseBranchBusManager(
			ICaseBranchBusManager stagingCaseBranchBusManager) {
		this.stagingCaseBranchBusManager = stagingCaseBranchBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ICaseBranchBusManager getCaseBranchBusManager() {
		return caseBranchBusManager;
	}

	public void setCaseBranchBusManager(ICaseBranchBusManager caseBranchBusManager) {
		this.caseBranchBusManager = caseBranchBusManager;
	}

	public ICaseBranchBusManager getStagingCaseBranchFileMapperIdBusManager() {
		return stagingCaseBranchFileMapperIdBusManager;
	}

	public void setStagingCaseBranchFileMapperIdBusManager(
			ICaseBranchBusManager stagingCaseBranchFileMapperIdBusManager) {
		this.stagingCaseBranchFileMapperIdBusManager = stagingCaseBranchFileMapperIdBusManager;
	}

	public ICaseBranchBusManager getCaseBranchFileMapperIdBusManager() {
		return caseBranchFileMapperIdBusManager;
	}

	public void setCaseBranchFileMapperIdBusManager(
			ICaseBranchBusManager caseBranchFileMapperIdBusManager) {
		this.caseBranchFileMapperIdBusManager = caseBranchFileMapperIdBusManager;
	}

	
	/**
	 * @return List of all CaseBranch
	 */
	
	public SearchResult getAllActualCaseBranch()throws CaseBranchException,TrxParameterException,TransactionException {
		try{


			return getCaseBranchBusManager().getAllCaseBranch( );
		}catch (Exception e) {
			throw new CaseBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	

	/**
	 * @return List of all CaseBranch
	 */
	
	public SearchResult getAllFilteredActualCaseBranch(String code,String name)throws CaseBranchException,TrxParameterException,TransactionException {
		try{


			return getCaseBranchBusManager().getAllFilteredCaseBranch(code,name );
		}catch (Exception e) {
			throw new CaseBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	

	/**
	 * @return List of all CaseBranch according to criteria .
	 */
	
	
	public List searchCaseBranch(String login) throws CaseBranchException,TrxParameterException,TransactionException {
	 	return getCaseBranchBusManager().searchCaseBranch(login);

    }
	/**
	 * @return List of all CaseBranch according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws CaseBranchException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getCaseBranchBusManager().getAllCaseBranch( searchBy, searchText);
		}else{
			throw new CaseBranchException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return CaseBranch according to id .
		 */
		
	 public ICaseBranch getCaseBranchById(long id) throws CaseBranchException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getCaseBranchBusManager().getCaseBranchById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new CaseBranchException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new CaseBranchException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update CaseBranch according to criteria .
		 */
		
	 
	 
	 public ICaseBranch updateCaseBranch(ICaseBranch caseBranch) throws CaseBranchException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 ICaseBranch item = (ICaseBranch) caseBranch;
			try {
				return getCaseBranchBusManager().updateCaseBranch(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CaseBranchException("ERROR-- Due to null CaseBranch object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete CaseBranch according to criteria .
		 */
		
	 public ICaseBranch deleteCaseBranch(ICaseBranch caseBranch) throws CaseBranchException, TrxParameterException,
		TransactionException {
		 if(!(caseBranch==null)){
		 ICaseBranch item = (ICaseBranch) caseBranch;
			try {
				return getCaseBranchBusManager().deleteCaseBranch(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new CaseBranchException("ERROR-- Transaction for the CaseBranch object is null.");
			}
		 }else{
			 throw new CaseBranchException("ERROR-- Cannot delete due to null CaseBranch object.");
		 }
		}
	 /**
		 * @return Checker Approve  CaseBranch according to criteria .
		 */
		
	
	public ICaseBranchTrxValue checkerApproveCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICaseBranchTrxValue)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseBranchException("The ITrxContext is null!!!");
        }
        if (anICaseBranchTrxValue == null) {
            throw new CaseBranchException
                    ("The ICaseBranchTrxValue to be updated is null!!!");
        }
        anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CASEBRANCH);
        return operate(anICaseBranchTrxValue, param);
	}
	 /**
	 * @return Checker Reject  CaseBranch according to criteria .
	 */
	
	
	public ICaseBranchTrxValue checkerRejectCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICaseBranchTrxValue)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICaseBranchTrxValue == null) {
	            throw new CaseBranchException("The ICaseBranchTrxValue to be updated is null!!!");
	        }
	        anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CASEBRANCH);
	        return operate(anICaseBranchTrxValue, param);
	}
	
	 /**
	 * @return  CaseBranch TRX value according to trxId  .
	 */
	

	
	public ICaseBranchTrxValue getCaseBranchByTrxID(String aTrxID)
			throws CaseBranchException, TransactionException,
			CommandProcessingException {
		ICaseBranchTrxValue trxValue = new OBCaseBranchTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_CASEBRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CASEBRANCH_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  CaseBranch TRX value  .
	 */
	

	public ICaseBranchTrxValue getCaseBranchTrxValue(
			long aCaseBranchId) throws CaseBranchException,
			TrxParameterException, TransactionException {
		if (aCaseBranchId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new CaseBranchException("Invalid CaseBranchId");
        }
        ICaseBranchTrxValue trxValue = new OBCaseBranchTrxValue();
        trxValue.setReferenceID(String.valueOf(aCaseBranchId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_CASEBRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CASEBRANCH);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close CaseBranch.
	 */
	
	public ICaseBranchTrxValue makerCloseRejectedCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICaseBranchTrxValue)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseBranchException("The ITrxContext is null!!!");
        }
        if (anICaseBranchTrxValue == null) {
            throw new CaseBranchException("The ICaseBranchTrxValue to be updated is null!!!");
        }
        anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CASEBRANCH);
        return operate(anICaseBranchTrxValue, param);
	}

	 /**
	 * @return Maker Close draft CaseBranch
	 */
	
	public ICaseBranchTrxValue makerCloseDraftCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICaseBranchTrxValue)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseBranchException("The ITrxContext is null!!!");
        }
        if (anICaseBranchTrxValue == null) {
            throw new CaseBranchException("The ICaseBranchTrxValue to be updated is null!!!");
        }
        anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CASEBRANCH);
        return operate(anICaseBranchTrxValue, param);
	}
	 /**
	 * @return Maker Edit CaseBranch
	 */
	public ICaseBranchTrxValue makerEditRejectedCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICaseBranchTrxValue, ICaseBranch anCaseBranch)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CaseBranchException("The ITrxContext is null!!!");
        }
        if (anICaseBranchTrxValue == null) {
            throw new CaseBranchException("The ICaseBranchTrxValue to be updated is null!!!");
        }
        if (anCaseBranch == null) {
            throw new CaseBranchException("The ICaseBranch to be updated is null !!!");
        }
        anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue, anCaseBranch);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CASEBRANCH);
        return operate(anICaseBranchTrxValue, param);
	}
	 /**
	 * @return Maker Update CaseBranch
	 */

	public ICaseBranchTrxValue makerUpdateCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICCCaseBranchTrxValue,
			ICaseBranch anICCCaseBranch)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
	        }
	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseBranchTrxValue, anICCCaseBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CASEBRANCH);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update CaseBranch
	 */

	public ICaseBranchTrxValue makerUpdateSaveUpdateCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICCCaseBranchTrxValue,
			ICaseBranch anICCCaseBranch)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
	        }
	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseBranchTrxValue, anICCCaseBranch);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CASEBRANCH);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create CaseBranch
	 */

	public ICaseBranchTrxValue makerUpdateSaveCreateCaseBranch(
			ITrxContext anITrxContext,
			ICaseBranchTrxValue anICCCaseBranchTrxValue,
			ICaseBranch anICCCaseBranch)
			throws CaseBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
	        }
	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseBranchTrxValue, anICCCaseBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CASEBRANCH);
	        return operate(trxValue, param);
	}
	 private ICaseBranchTrxValue operate(ICaseBranchTrxValue anICaseBranchTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws CaseBranchException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anICaseBranchTrxValue, anOBCMSTrxParameter);
	        return (ICaseBranchTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws CaseBranchException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (CaseBranchException ex) {
			 throw new CaseBranchException("ERROR--Cannot Get the CaseBranch Controller.");
		 }
		 catch (Exception ex) {
			 throw new CaseBranchException("ERROR--Cannot Get the CaseBranch Controller.");
		 }
	 }
	 
	 private ICaseBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ICaseBranch anICaseBranch) {
	        ICaseBranchTrxValue ccCaseBranchTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCaseBranchTrxValue = new OBCaseBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccCaseBranchTrxValue = new OBCaseBranchTrxValue();
	        }
	        ccCaseBranchTrxValue = formulateTrxValue(anITrxContext, (ICaseBranchTrxValue) ccCaseBranchTrxValue);
	        ccCaseBranchTrxValue.setStagingCaseBranch(anICaseBranch);
	        return ccCaseBranchTrxValue;
	    }
	 private ICaseBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) {
	        anICaseBranchTrxValue.setTrxContext(anITrxContext);
	        anICaseBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_CASEBRANCH);
	        return anICaseBranchTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete CaseBranch
		 */

	 public ICaseBranchTrxValue makerDeleteCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICCCaseBranchTrxValue, ICaseBranch anICCCaseBranch) throws CaseBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCaseBranchTrxValue, anICCCaseBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CASEBRANCH);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create CaseBranch
		 */
	 public ICaseBranchTrxValue makerCreateCaseBranch(ITrxContext anITrxContext, ICaseBranch anICCCaseBranch) throws CaseBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
	        }

	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseBranch);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CASEBRANCH);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save CaseBranch
		 */
	 
	 public ICaseBranchTrxValue makerSaveCaseBranch(ITrxContext anITrxContext, ICaseBranch anICCCaseBranch) throws CaseBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CaseBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCCaseBranch == null) {
	            throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
	        }

	        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseBranch);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CASEBRANCH);
	        return operate(trxValue, param);
	    }

	
	//------------------------------------File Upload-----------------------------------------------------
		
		 private ICaseBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 ICaseBranchTrxValue ccCaseBranchTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccCaseBranchTrxValue = new OBCaseBranchTrxValue(anICMSTrxValue);
		        } else {
		            ccCaseBranchTrxValue = new OBCaseBranchTrxValue();
		        }
		        ccCaseBranchTrxValue = formulateTrxValueID(anITrxContext, (ICaseBranchTrxValue) ccCaseBranchTrxValue);
		        ccCaseBranchTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccCaseBranchTrxValue;
		    }
		 
		 private ICaseBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
			 ICaseBranchTrxValue ccCaseBranchTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccCaseBranchTrxValue = new OBCaseBranchTrxValue(anICMSTrxValue);
		        } else {
		            ccCaseBranchTrxValue = new OBCaseBranchTrxValue();
		        }
		        ccCaseBranchTrxValue = formulateTrxValueID(anITrxContext, (ICaseBranchTrxValue) ccCaseBranchTrxValue);
		        ccCaseBranchTrxValue.setStagingFileMapperID(fileId);
		        return ccCaseBranchTrxValue;
		    }
		 private ICaseBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) {
		        anICaseBranchTrxValue.setTrxContext(anITrxContext);
		        anICaseBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_CASEBRANCH);
		        return anICaseBranchTrxValue;
		    }
	    
		 
		 /**
			 * @return Maker insert a fileID to generate a transation.
			 */
		 public ICaseBranchTrxValue makerInsertMapperCaseBranch(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws CaseBranchException, TrxParameterException,
					TransactionException {
				// TODO Auto-generated method stub
				if (anITrxContext == null) {
		            throw new CaseBranchException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new CaseBranchException("The OBFileMapperID to be updated is null !!!");
		        }

		        ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 /**
			 * @return Maker check if previous upload is pending.
			 */
		 public boolean isPrevFileUploadPending() throws CaseBranchException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getCaseBranchBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new CaseBranchException("ERROR-- Due to null CaseBranch object cannot update.");
				}
			}
		 
		 /**
			 * @return Maker insert uploaded files in Staging table.
			 */
		 
		 public int insertCaseBranch(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws CaseBranchException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getCaseBranchBusManager().insertCaseBranch(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new CaseBranchException("ERROR-- Due to null CaseBranch object cannot update.");
				}
			}
		 
		 /**
			 * @return create record with TransID.
			 */
		 
		 public ICaseBranchTrxValue getInsertFileByTrxID(String trxID)
			throws CaseBranchException, TransactionException,
			CommandProcessingException {
			 	ICaseBranchTrxValue trxValue = new OBCaseBranchTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 /**
			 * @return Pagination for uploaded files in CaseBranch.
			 */
		 public List getAllStage(String searchBy, String login)throws CaseBranchException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getCaseBranchBusManager().getAllStageCaseBranch( searchBy, login);
			}else{
				throw new CaseBranchException("ERROR- Search criteria is null.");
			}
		  }
		
		 /**
			 * @return Checker approval for uploaded files.
			 */
		 
		 public ICaseBranchTrxValue checkerApproveInsertCaseBranch(
		 		ITrxContext anITrxContext,
		 		ICaseBranchTrxValue anICaseBranchTrxValue)
		 		throws CaseBranchException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		        throw new CaseBranchException("The ITrxContext is null!!!");
		    }
		    if (anICaseBranchTrxValue == null) {
		        throw new CaseBranchException
		                ("The ICaseBranchTrxValue to be updated is null!!!");
		    }
		    anICaseBranchTrxValue = formulateTrxValueID(anITrxContext, anICaseBranchTrxValue);
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		    return operate(anICaseBranchTrxValue, param);
		 }
		 
		 /**
			 * @return list of files uploaded in staging table of CaseBranch.
			 */
		 public List getFileMasterList(String searchBy)throws CaseBranchException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getCaseBranchBusManager().getFileMasterList( searchBy);
			}else{
				throw new CaseBranchException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return Maker insert upload files.
			 */
		 public ICaseBranch insertActualCaseBranch(String sysId) throws CaseBranchException,TrxParameterException,TransactionException

		 {
		  if(sysId != null){
		 	 try {
		 		 return getCaseBranchBusManager().insertActualCaseBranch(sysId);
		 	 } catch (Exception e) {		 		
		 		 e.printStackTrace();
		 		 throw new CaseBranchException("ERROR- Transaction for the Id is invalid.");
		 	 }
		  }else{
		 	 throw new CaseBranchException("ERROR- Id for retrival is null.");
		  }
		 }
		 
		 /**
			 * @return Checker create file master in CaseBranch.
			 */
		 
		 public ICaseBranchTrxValue checkerCreateCaseBranch(ITrxContext anITrxContext, ICaseBranch anICCCaseBranch, String refStage) throws CaseBranchException,TrxParameterException,
		 TransactionException {
		     if (anITrxContext == null) {
		         throw new CaseBranchException("The ITrxContext is null!!!");
		     }
		     if (anICCCaseBranch == null) {
		         throw new CaseBranchException("The ICCCaseBranch to be updated is null !!!");
		     }

		     ICaseBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCaseBranch);
		     trxValue.setFromState("PENDING_CREATE");
		     trxValue.setReferenceID(String.valueOf(anICCCaseBranch.getId()));
		     trxValue.setStagingReferenceID(refStage);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
		     return operate(trxValue, param);
		 }
		 
		 /**
			 * @return Checker Reject for upload files CaseBranch.
			 */

		 public ICaseBranchTrxValue checkerRejectInsertCaseBranch(
		 	ITrxContext anITrxContext,
		 	ICaseBranchTrxValue anICaseBranchTrxValue)
		 	throws CaseBranchException, TrxParameterException,
		 	TransactionException {
		 	if (anITrxContext == null) {
		 	  throw new CaseBranchException("The ITrxContext is null!!!");
		 	}
		 	if (anICaseBranchTrxValue == null) {
		 	  throw new CaseBranchException
		 	          ("The ICaseBranchTrxValue to be updated is null!!!");
		 	}
		 		anICaseBranchTrxValue = formulateTrxValueID(anITrxContext, anICaseBranchTrxValue);
		 		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		 	return operate(anICaseBranchTrxValue, param);
		 }
		 
		 /**
			 * @return Maker Close rejected files CaseBranch.
			 */

		 public ICaseBranchTrxValue makerInsertCloseRejectedCaseBranch(
		 		ITrxContext anITrxContext,
		 		ICaseBranchTrxValue anICaseBranchTrxValue)
		 		throws CaseBranchException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		         throw new CaseBranchException("The ITrxContext is null!!!");
		     }
		     if (anICaseBranchTrxValue == null) {
		         throw new CaseBranchException("The ICaseBranchTrxValue to be updated is null!!!");
		     }
		     anICaseBranchTrxValue = formulateTrxValue(anITrxContext, anICaseBranchTrxValue);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
		     return operate(anICaseBranchTrxValue, param);
		 }

		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
			getCaseBranchBusManager().deleteTransaction(obFileMapperMaster);			
		}
}
