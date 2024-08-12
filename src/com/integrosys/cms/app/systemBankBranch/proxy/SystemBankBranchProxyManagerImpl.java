/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.systemBankBranch.proxy;



import java.util.ArrayList;
import java.util.HashMap;
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
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchBusManager;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.bhavcopy.OBBhavcopy;

/**
 * This class act as a facade to the services offered by the diary item modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2011/02 10:03:55 $ Tag: $Name: $
 */
public class SystemBankBranchProxyManagerImpl implements ISystemBankBranchProxyManager {

	
	private ISystemBankBranchBusManager systemBankBranchBusManager;
	
	
	private ISystemBankBranchBusManager stagingSystemBankBranchBusManager;
	
	private ISystemBankBranchBusManager stagingFileMapperIdSysBusManager;
	
	private ISystemBankBranchBusManager fileMapperIdSysBusManager;

    private ITrxControllerFactory trxControllerFactory;



	public ISystemBankBranchBusManager getStagingSystemBankBranchBusManager() {
		return stagingSystemBankBranchBusManager;
	}

	public void setStagingSystemBankBranchBusManager(
			ISystemBankBranchBusManager stagingSystemBankBranchBusManager) {
		this.stagingSystemBankBranchBusManager = stagingSystemBankBranchBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ISystemBankBranchBusManager getSystemBankBranchBusManager() {
		return systemBankBranchBusManager;
	}

	public void setSystemBankBranchBusManager(ISystemBankBranchBusManager systemBankBranchBusManager) {
		this.systemBankBranchBusManager = systemBankBranchBusManager;
	}
	
	public ISystemBankBranchBusManager getStagingFileMapperIdSysBusManager() {
		return stagingFileMapperIdSysBusManager;
	}

	public void setStagingFileMapperIdSysBusManager(
			ISystemBankBranchBusManager stagingFileMapperIdSysBusManager) {
		this.stagingFileMapperIdSysBusManager = stagingFileMapperIdSysBusManager;
	}

	public ISystemBankBranchBusManager getFileMapperIdSysBusManager() {
		return fileMapperIdSysBusManager;
	}

	public void setFileMapperIdSysBusManager(
			ISystemBankBranchBusManager fileMapperIdSysBusManager) {
		this.fileMapperIdSysBusManager = fileMapperIdSysBusManager;
	}
	
	public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) {
		return (SearchResult)getSystemBankBranchBusManager().getSystemBranchList(branchCode,branchName,state,city);
	}

	/**
	 * @return List of all System Bank Branch .
	 */
	
	public SearchResult getAllActualBranch()throws SystemBankBranchException,TrxParameterException,TransactionException {
		try{


			return getSystemBankBranchBusManager().getAllSystemBankBranch( );
		}catch (Exception e) {
			throw new SystemBankBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	
	
	public SearchResult getAllActualBranchForHUB()throws SystemBankBranchException,TrxParameterException,TransactionException {
		try{


			return getSystemBankBranchBusManager().getAllSystemBankBranchForHUB();
		}catch (Exception e) {
			throw new SystemBankBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	
	/**
	 * @return List of all System Bank Branch .
	 */
	
	public List getAllHUBBranchId()throws SystemBankBranchException,TrxParameterException,TransactionException {
		try{


			return getSystemBankBranchBusManager().getAllHUBBranchId( );
		}catch (Exception e) {
			throw new SystemBankBranchException("ERROR- Cannot retrive list from database.");
		}
    }


	/**
	 * @return List of all System Bank Branch .
	 */
	
	public List getAllHUBBranchValue()throws SystemBankBranchException,TrxParameterException,TransactionException {
		try{


			return getSystemBankBranchBusManager().getAllHUBBranchValue( );
		}catch (Exception e) {
			throw new SystemBankBranchException("ERROR- Cannot retrive list from database.");
		}
    }
	
	

	/**
	 * @return List of all System Bank Branch according to criteria .
	 */
	
	
	public SearchResult searchBranch(String login) throws SystemBankBranchException,TrxParameterException,TransactionException {
	 	return getSystemBankBranchBusManager().searchBranch(login);

    }
	/**
	 * @return List of all System Bank Branch according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws SystemBankBranchException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getSystemBankBranchBusManager().getAllSystemBankBranch( searchBy, searchText);
		}else{
			throw new SystemBankBranchException("ERROR- Search criteria is null.");
		}
	    }
	 
	 /**
		 * @return List of all System Bank Branch according to criteria .
		 */
		
		
		 public List getAllStage(String searchBy, String login)throws SystemBankBranchException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getSystemBankBranchBusManager().getAllStageSystemBankBranch( searchBy,login);
			}else{
				throw new SystemBankBranchException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return List of all System Bank Branch according to criteria .
			 */
			
			
			 public List getFileMasterList(String searchBy)throws SystemBankBranchException,TrxParameterException,TransactionException {
				if(searchBy!=null){
			
				 return getSystemBankBranchBusManager().getFileMasterList( searchBy);
				}else{
					throw new SystemBankBranchException("ERROR- Search criteria is null.");
				}
			  }
			
	 /**
		 * @return System Bank Branch according to id .
		 */
		
	 public ISystemBankBranch getSystemBankBranchById(long id) throws SystemBankBranchException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getSystemBankBranchBusManager().getSystemBankBranchById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new SystemBankBranchException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new SystemBankBranchException("ERROR- Id for retrival is null.");
		 }

	 }
	 	 
	 /**
		 * @return Update System Bank Branch according to criteria .
		 */
		
	 
	 
	 public ISystemBankBranch updateSystemBankBranch(ISystemBankBranch systemBankBranch) throws SystemBankBranchException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 ISystemBankBranch item = (ISystemBankBranch) systemBankBranch;
			try {
				return getSystemBankBranchBusManager().updateSystemBankBranch(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new SystemBankBranchException("ERROR-- Due to null System Bank Branch object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete System Bank Branch according to criteria .
		 */
		
	 public ISystemBankBranch deleteSystemBankBranch(ISystemBankBranch systemBankBranch) throws SystemBankBranchException, TrxParameterException,
		TransactionException {
		 if(!(systemBankBranch==null)){
		 ISystemBankBranch item = (ISystemBankBranch) systemBankBranch;
			try {
				return getSystemBankBranchBusManager().deleteSystemBankBranch(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new SystemBankBranchException("ERROR-- Transaction for the SystemBankBranch object is null.");
			}
		 }else{
			 throw new SystemBankBranchException("ERROR-- Cannot delete due to null SystemBankBranch object.");
		 }
		}
	 /**
		 * @return Checker Approve  System Bank Branch according to criteria .
		 */
		
	
	public ISystemBankBranchTrxValue checkerApproveSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new SystemBankBranchException("The ITrxContext is null!!!");
        }
        if (anISystemBankBranchTrxValue == null) {
            throw new SystemBankBranchException
                    ("The ISystemBankBranchTrxValue to be updated is null!!!");
        }
        anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK_BRANCH);
        return operate(anISystemBankBranchTrxValue, param);
	}
	
	 /**
	 * @return Checker Reject  System Bank Branch according to criteria .
	 */
	
	
	public ISystemBankBranchTrxValue checkerRejectSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankBranchTrxValue == null) {
	            throw new SystemBankBranchException("The ISystemBankBranchTrxValue to be updated is null!!!");
	        }
	        anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH);
	        return operate(anISystemBankBranchTrxValue, param);
	}
		
	 /**
	 * @return  System Bank Branch TRX value according to trxId  .
	 */
	

	
	public ISystemBankBranchTrxValue getSystemBankBranchByTrxID(String aTrxID)
			throws SystemBankBranchException, TransactionException,
			CommandProcessingException {
		ISystemBankBranchTrxValue trxValue = new OBSystemBankBranchTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK_BRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SYSTEM_BANK_BRANCH_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  System Bank Branch TRX value  .
	 */
	

	public ISystemBankBranchTrxValue getSystemBankBranchTrxValue(
			long aSystemBankBranchId) throws SystemBankBranchException,
			TrxParameterException, TransactionException {
		if (aSystemBankBranchId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new SystemBankBranchException("Invalid SystemBankBranchId");
        }
        ISystemBankBranchTrxValue trxValue = new OBSystemBankBranchTrxValue();
        trxValue.setReferenceID(String.valueOf(aSystemBankBranchId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK_BRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SYSTEM_BANK_BRANCH);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close System Bank Branch  .
	 */
	
	public ISystemBankBranchTrxValue makerCloseRejectedSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new SystemBankBranchException("The ITrxContext is null!!!");
        }
        if (anISystemBankBranchTrxValue == null) {
            throw new SystemBankBranchException("The ISystemBankBranchTrxValue to be updated is null!!!");
        }
        anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK_BRANCH);
        return operate(anISystemBankBranchTrxValue, param);
	}

	 /**
	 * @return Maker Close draft System Bank Branch  .
	 */
	
	public ISystemBankBranchTrxValue makerCloseDraftSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new SystemBankBranchException("The ITrxContext is null!!!");
        }
        if (anISystemBankBranchTrxValue == null) {
            throw new SystemBankBranchException("The ISystemBankBranchTrxValue to be updated is null!!!");
        }
        anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK_BRANCH);
        return operate(anISystemBankBranchTrxValue, param);
	}
	 /**
	 * @return Maker Edit System Bank Branch  .
	 */
	public ISystemBankBranchTrxValue makerEditRejectedSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anISystemBankBranchTrxValue, ISystemBankBranch anSystemBankBranch)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new SystemBankBranchException("The ITrxContext is null!!!");
        }
        if (anISystemBankBranchTrxValue == null) {
            throw new SystemBankBranchException("The ISystemBankBranchTrxValue to be updated is null!!!");
        }
        if (anSystemBankBranch == null) {
            throw new SystemBankBranchException("The ISystemBankBranch to be updated is null !!!");
        }
        anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue, anSystemBankBranch);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK_BRANCH);
        return operate(anISystemBankBranchTrxValue, param);
	}
	 /**
	 * @return Maker Update System Bank Branch  .
	 */

	public ISystemBankBranchTrxValue makerUpdateSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anICCSystemBankBranchTrxValue,
			ISystemBankBranch anICCSystemBankBranch)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
	        }
	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankBranchTrxValue, anICCSystemBankBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update System Bank Branch  .
	 */

	public ISystemBankBranchTrxValue makerUpdateSaveUpdateSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anICCSystemBankBranchTrxValue,
			ISystemBankBranch anICCSystemBankBranch)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
	        }
	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankBranchTrxValue, anICCSystemBankBranch);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create System Bank Branch  .
	 */

	public ISystemBankBranchTrxValue makerUpdateSaveCreateSystemBankBranch(
			ITrxContext anITrxContext,
			ISystemBankBranchTrxValue anICCSystemBankBranchTrxValue,
			ISystemBankBranch anICCSystemBankBranch)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
	        }
	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankBranchTrxValue, anICCSystemBankBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	}
	 private ISystemBankBranchTrxValue operate(ISystemBankBranchTrxValue anISystemBankBranchTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws SystemBankBranchException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anISystemBankBranchTrxValue, anOBCMSTrxParameter);
	        return (ISystemBankBranchTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws SystemBankBranchException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (SystemBankBranchException ex) {
			 throw new SystemBankBranchException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }
		 catch (Exception ex) {
			 throw new SystemBankBranchException("ERROR--Cannot Get the System Bank Branch Controller.");
		 }
	 }
	 
	 private ISystemBankBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ISystemBankBranch anISystemBankBranch) {
	        ISystemBankBranchTrxValue ccSystemBankBranchTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue();
	        }
	        ccSystemBankBranchTrxValue = formulateTrxValue(anITrxContext, (ISystemBankBranchTrxValue) ccSystemBankBranchTrxValue);
	        ccSystemBankBranchTrxValue.setStagingSystemBankBranch(anISystemBankBranch);
	        return ccSystemBankBranchTrxValue;
	    }
	 private ISystemBankBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ISystemBankBranchTrxValue anISystemBankBranchTrxValue) {
	        anISystemBankBranchTrxValue.setTrxContext(anITrxContext);
	        anISystemBankBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK_BRANCH);
	        return anISystemBankBranchTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete System Bank Branch  .
		 */

	 public ISystemBankBranchTrxValue makerDeleteSystemBankBranch(ITrxContext anITrxContext, ISystemBankBranchTrxValue anICCSystemBankBranchTrxValue, ISystemBankBranch anICCSystemBankBranch) throws SystemBankBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankBranchTrxValue, anICCSystemBankBranch);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create System Bank Branch  .
		 */
	 public ISystemBankBranchTrxValue makerCreateSystemBankBranch(ITrxContext anITrxContext, ISystemBankBranch anICCSystemBankBranch) throws SystemBankBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
	        }

	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSystemBankBranch);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	    }
	 
	 public ISystemBankBranchTrxValue makerSaveSystemBankBranch(ITrxContext anITrxContext, ISystemBankBranch anICCSystemBankBranch) throws SystemBankBranchException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBankBranch == null) {
	            throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
	        }

	        ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSystemBankBranch);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_SYSTEM_BANK_BRANCH);
	        return operate(trxValue, param);
	    }
	  
	 /**
		 * @return Validate  Create System Bank Branch  .
		 */
	 public boolean isUniqueCode(String coloumn ,String branchCode) throws SystemBankBranchException,TrxParameterException,
		TransactionException {
	     return  getSystemBankBranchBusManager().isUniqueCode(coloumn,branchCode);
	    }
	
	
//------------------------------------File Upload-----------------------------------------------------
	 
	 
	 private ISystemBankBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
	        ISystemBankBranchTrxValue ccSystemBankBranchTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue();
	        }
	        ccSystemBankBranchTrxValue = formulateTrxValueID(anITrxContext, (ISystemBankBranchTrxValue) ccSystemBankBranchTrxValue);
	        ccSystemBankBranchTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccSystemBankBranchTrxValue;
	    }
	 
	 private ISystemBankBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
	        ISystemBankBranchTrxValue ccSystemBankBranchTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccSystemBankBranchTrxValue = new OBSystemBankBranchTrxValue();
	        }
	        ccSystemBankBranchTrxValue = formulateTrxValueID(anITrxContext, (ISystemBankBranchTrxValue) ccSystemBankBranchTrxValue);
	        ccSystemBankBranchTrxValue.setStagingFileMapperID(fileId);
	        return ccSystemBankBranchTrxValue;
	    }
	 private ISystemBankBranchTrxValue formulateTrxValueID(ITrxContext anITrxContext, ISystemBankBranchTrxValue anISystemBankBranchTrxValue) {
	        anISystemBankBranchTrxValue.setTrxContext(anITrxContext);
	        anISystemBankBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
	        return anISystemBankBranchTrxValue;
	    }
	 
	 /**
		 * @return create id in File Mapper id table .
		 */
	public ISystemBankBranchTrxValue makerInsertMapperSystemBankBranch(
			ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
			throws SystemBankBranchException, TrxParameterException,
			TransactionException {
		// TODO Auto-generated method stub
		if (anITrxContext == null) {
         throw new SystemBankBranchException("The ITrxContext is null!!!");
     }
     if (obFileMapperID == null) {
         throw new SystemBankBranchException("The OBFileMapperID to be updated is null !!!");
     }

     ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
     trxValue.setFromState("PENDING_INSERT");
     OBCMSTrxParameter param = new OBCMSTrxParameter();
     param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
     return operate(trxValue, param);
	}
	
	 
	 /**
		 * @return Create records in Mapper table with Transaction key value .
		 */
	
	 public int insertSystemBankBranch(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws SystemBankBranchException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getSystemBankBranchBusManager().insertSystemBankBranch(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new SystemBankBranchException("ERROR-- Due to null System Bank Branch object cannot update.");
			}
		}  
	 
	 /**
		 * @return search operation files upload for System Bank Branch.
		 */

	public ISystemBankBranchTrxValue getInsertFileByTrxID(String trxID)
			throws SystemBankBranchException, TransactionException,
			CommandProcessingException {
		ISystemBankBranchTrxValue trxValue = new OBSystemBankBranchTrxValue();
        trxValue.setTransactionID(String.valueOf(trxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
        return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Approve files upload for System Bank Branch according to criteria .
	 */
	

public ISystemBankBranchTrxValue checkerApproveInsertSystemBankBranch(
		ITrxContext anITrxContext,
		ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
		throws SystemBankBranchException, TrxParameterException,
		TransactionException {
	if (anITrxContext == null) {
       throw new SystemBankBranchException("The ITrxContext is null!!!");
   }
   if (anISystemBankBranchTrxValue == null) {
       throw new SystemBankBranchException
               ("The ISystemBankBranchTrxValue to be updated is null!!!");
   }
   anISystemBankBranchTrxValue = formulateTrxValueID(anITrxContext, anISystemBankBranchTrxValue);
   OBCMSTrxParameter param = new OBCMSTrxParameter();
   param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
   return operate(anISystemBankBranchTrxValue, param);
}


/**
* @return Checker Reject for uploaded  System Bank Branch according to criteria .
*/


public ISystemBankBranchTrxValue checkerRejectInsertSystemBankBranch(
	ITrxContext anITrxContext,
	ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
	throws SystemBankBranchException, TrxParameterException,
	TransactionException {
	if (anITrxContext == null) {
	  throw new SystemBankBranchException("The ITrxContext is null!!!");
	}
	if (anISystemBankBranchTrxValue == null) {
	  throw new SystemBankBranchException
	          ("The ISystemBankBranchTrxValue to be updated is null!!!");
	}
		anISystemBankBranchTrxValue = formulateTrxValueID(anITrxContext, anISystemBankBranchTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	return operate(anISystemBankBranchTrxValue, param);
}

/**
 * @return Maker Close upload files for System Bank Branch  .
 */

public ISystemBankBranchTrxValue makerInsertCloseRejectedSystemBankBranch(
		ITrxContext anITrxContext,
		ISystemBankBranchTrxValue anISystemBankBranchTrxValue)
		throws SystemBankBranchException, TrxParameterException,
		TransactionException {
	if (anITrxContext == null) {
        throw new SystemBankBranchException("The ITrxContext is null!!!");
    }
    if (anISystemBankBranchTrxValue == null) {
        throw new SystemBankBranchException("The ISystemBankBranchTrxValue to be updated is null!!!");
    }
    anISystemBankBranchTrxValue = formulateTrxValue(anITrxContext, anISystemBankBranchTrxValue);
    OBCMSTrxParameter param = new OBCMSTrxParameter();
    param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
    return operate(anISystemBankBranchTrxValue, param);
}

/**
 * @return System Bank Branch according to id .
 */

public ISystemBankBranch insertActualSystemBankBranch(String sysId) throws SystemBankBranchException,TrxParameterException,TransactionException

{
 if(sysId != null){
	 try {
		 return getSystemBankBranchBusManager().insertActualSystemBankBranch(sysId);
	 } catch (Exception e) {
		
		 e.printStackTrace();
		 throw new SystemBankBranchException("ERROR- Transaction for the Id is invalid.");
	 }
 }else{
	 throw new SystemBankBranchException("ERROR- Id for retrival is null.");
 }
}

/**
 * @return Maker Create System Bank Branch  .
 */
public ISystemBankBranchTrxValue checkerCreateSystemBankBranch(ITrxContext anITrxContext, ISystemBankBranch anICCSystemBankBranch, String refStage) throws SystemBankBranchException,TrxParameterException,
TransactionException {
    if (anITrxContext == null) {
        throw new SystemBankBranchException("The ITrxContext is null!!!");
    }
    if (anICCSystemBankBranch == null) {
        throw new SystemBankBranchException("The ICCSystemBankBranch to be updated is null !!!");
    }

    ISystemBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSystemBankBranch);
    trxValue.setFromState("PENDING_CREATE");
    trxValue.setReferenceID(String.valueOf(anICCSystemBankBranch.getId()));
    trxValue.setStagingReferenceID(refStage);
    OBCMSTrxParameter param = new OBCMSTrxParameter();
    param.setAction(ICMSConstant.ACTION_CHECKER_CREATE_SYSTEM_BANK_BRANCH);
    return operate(trxValue, param);
}

/**
* @return boolean value is status in Transation table pending for approval.
*/

public boolean isPrevFileUploadPending() throws SystemBankBranchException, TrxParameterException,
TransactionException  {
 
	try {
		return getSystemBankBranchBusManager().isPrevFileUploadPending();
	} catch (Exception e) {
		
		e.printStackTrace();
		throw new HolidayException("ERROR-- Due to null Holiday object cannot update.");
	}
}


	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getSystemBankBranchBusManager().deleteTransaction(obFileMapperMaster);
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode)throws SystemBankBranchException, TrxParameterException,TransactionException {
		return getFileMapperIdSysBusManager().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}

	public boolean isHubValid(String linkedHub) {
		return getFileMapperIdSysBusManager().isHubValid(linkedHub);
	}
}
