/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.holiday.proxy;



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
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayBusManager;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the Holiday modules
 * 
 * @author $Author:  Abhijit R. $<br>
 * @version $Revision: 1.6 $
 * 
 */
public class HolidayProxyManagerImpl implements IHolidayProxyManager {

	
	private IHolidayBusManager holidayBusManager;
	
	
	private IHolidayBusManager stagingHolidayBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private IHolidayBusManager stagingHolidayFileMapperIdBusManager;
	
	private IHolidayBusManager holidayFileMapperIdBusManager;



	public IHolidayBusManager getStagingHolidayBusManager() {
		return stagingHolidayBusManager;
	}

	public void setStagingHolidayBusManager(
			IHolidayBusManager stagingHolidayBusManager) {
		this.stagingHolidayBusManager = stagingHolidayBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IHolidayBusManager getHolidayBusManager() {
		return holidayBusManager;
	}

	public void setHolidayBusManager(IHolidayBusManager holidayBusManager) {
		this.holidayBusManager = holidayBusManager;
	}

	public IHolidayBusManager getStagingHolidayFileMapperIdBusManager() {
		return stagingHolidayFileMapperIdBusManager;
	}

	public void setStagingHolidayFileMapperIdBusManager(
			IHolidayBusManager stagingHolidayFileMapperIdBusManager) {
		this.stagingHolidayFileMapperIdBusManager = stagingHolidayFileMapperIdBusManager;
	}

	public IHolidayBusManager getHolidayFileMapperIdBusManager() {
		return holidayFileMapperIdBusManager;
	}

	public void setHolidayFileMapperIdBusManager(
			IHolidayBusManager holidayFileMapperIdBusManager) {
		this.holidayFileMapperIdBusManager = holidayFileMapperIdBusManager;
	}

	
	/**
	 * @return List of all Holiday
	 */
	
	public SearchResult getAllActualHoliday()throws HolidayException,TrxParameterException,TransactionException {
		try{


			return getHolidayBusManager().getAllHoliday( );
		}catch (Exception e) {
			throw new HolidayException("ERROR- Cannot retrive list from database.");
		}
    }
	



	/**
	 * @return List of all Holiday according to criteria .
	 */
	
	
	public List searchHoliday(String login) throws HolidayException,TrxParameterException,TransactionException {
	 	return getHolidayBusManager().searchHoliday(login);

    }
	/**
	 * @return List of all Holiday according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws HolidayException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getHolidayBusManager().getAllHoliday( searchBy, searchText);
		}else{
			throw new HolidayException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return Holiday according to id .
		 */
		
	 public IHoliday getHolidayById(long id) throws HolidayException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getHolidayBusManager().getHolidayById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new HolidayException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new HolidayException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update Holiday according to criteria .
		 */
		
	 
	 
	 public IHoliday updateHoliday(IHoliday holiday) throws HolidayException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 IHoliday item = (IHoliday) holiday;
			try {
				return getHolidayBusManager().updateHoliday(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new HolidayException("ERROR-- Due to null Holiday object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete Holiday according to criteria .
		 */
		
	 public IHoliday deleteHoliday(IHoliday holiday) throws HolidayException, TrxParameterException,
		TransactionException {
		 if(!(holiday==null)){
		 IHoliday item = (IHoliday) holiday;
			try {
				return getHolidayBusManager().deleteHoliday(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new HolidayException("ERROR-- Transaction for the Holiday object is null.");
			}
		 }else{
			 throw new HolidayException("ERROR-- Cannot delete due to null Holiday object.");
		 }
		}
	 /**
		 * @return Checker Approve  Holiday according to criteria .
		 */
		
	
	public IHolidayTrxValue checkerApproveHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anIHolidayTrxValue)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new HolidayException("The ITrxContext is null!!!");
        }
        if (anIHolidayTrxValue == null) {
            throw new HolidayException
                    ("The IHolidayTrxValue to be updated is null!!!");
        }
        anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_HOLIDAY);
        return operate(anIHolidayTrxValue, param);
	}
	 /**
	 * @return Checker Reject  Holiday according to criteria .
	 */
	
	
	public IHolidayTrxValue checkerRejectHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anIHolidayTrxValue)
			throws HolidayException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anIHolidayTrxValue == null) {
	            throw new HolidayException("The IHolidayTrxValue to be updated is null!!!");
	        }
	        anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_HOLIDAY);
	        return operate(anIHolidayTrxValue, param);
	}
	
	 /**
	 * @return  Holiday TRX value according to trxId  .
	 */
	

	
	public IHolidayTrxValue getHolidayByTrxID(String aTrxID)
			throws HolidayException, TransactionException,
			CommandProcessingException {
		IHolidayTrxValue trxValue = new OBHolidayTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_HOLIDAY);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_HOLIDAY_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  Holiday TRX value  .
	 */
	

	public IHolidayTrxValue getHolidayTrxValue(
			long aHolidayId) throws HolidayException,
			TrxParameterException, TransactionException {
		if (aHolidayId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new HolidayException("Invalid HolidayId");
        }
        IHolidayTrxValue trxValue = new OBHolidayTrxValue();
        trxValue.setReferenceID(String.valueOf(aHolidayId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_HOLIDAY);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_HOLIDAY);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close Holiday.
	 */
	
	public IHolidayTrxValue makerCloseRejectedHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anIHolidayTrxValue)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new HolidayException("The ITrxContext is null!!!");
        }
        if (anIHolidayTrxValue == null) {
            throw new HolidayException("The IHolidayTrxValue to be updated is null!!!");
        }
        anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_HOLIDAY);
        return operate(anIHolidayTrxValue, param);
	}

	 /**
	 * @return Maker Close draft Holiday
	 */
	
	public IHolidayTrxValue makerCloseDraftHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anIHolidayTrxValue)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new HolidayException("The ITrxContext is null!!!");
        }
        if (anIHolidayTrxValue == null) {
            throw new HolidayException("The IHolidayTrxValue to be updated is null!!!");
        }
        anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_HOLIDAY);
        return operate(anIHolidayTrxValue, param);
	}
	 /**
	 * @return Maker Edit Holiday
	 */
	public IHolidayTrxValue makerEditRejectedHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anIHolidayTrxValue, IHoliday anHoliday)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new HolidayException("The ITrxContext is null!!!");
        }
        if (anIHolidayTrxValue == null) {
            throw new HolidayException("The IHolidayTrxValue to be updated is null!!!");
        }
        if (anHoliday == null) {
            throw new HolidayException("The IHoliday to be updated is null !!!");
        }
        anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue, anHoliday);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_HOLIDAY);
        return operate(anIHolidayTrxValue, param);
	}
	 /**
	 * @return Maker Update Holiday
	 */

	public IHolidayTrxValue makerUpdateHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anICCHolidayTrxValue,
			IHoliday anICCHoliday)
			throws HolidayException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCHoliday to be updated is null !!!");
	        }
	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, anICCHolidayTrxValue, anICCHoliday);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_HOLIDAY);
	        return operate(trxValue, param);
	}
	
	 /**
	 * @return Maker Update Holiday
	 */

	public IHolidayTrxValue makerUpdateSaveUpdateHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anICCHolidayTrxValue,
			IHoliday anICCHoliday)
			throws HolidayException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCHoliday to be updated is null !!!");
	        }
	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, anICCHolidayTrxValue, anICCHoliday);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_HOLIDAY);
	        return operate(trxValue, param);
	}
	
	
	/**
	 * @return Maker Update Draft for create Holiday
	 */

	public IHolidayTrxValue makerUpdateSaveCreateHoliday(
			ITrxContext anITrxContext,
			IHolidayTrxValue anICCHolidayTrxValue,
			IHoliday anICCHoliday)
			throws HolidayException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCHoliday to be updated is null !!!");
	        }
	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, anICCHolidayTrxValue, anICCHoliday);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_HOLIDAY);
	        return operate(trxValue, param);
	}
	 private IHolidayTrxValue operate(IHolidayTrxValue anIHolidayTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws HolidayException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIHolidayTrxValue, anOBCMSTrxParameter);
	        return (IHolidayTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws HolidayException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (HolidayException ex) {
			 throw new HolidayException("ERROR--Cannot Get the Holiday Controller.");
		 }
		 catch (Exception ex) {
			 System.out.println("!!!!!!!!!!!!!!Exception"+ex.getMessage());
			 ex.printStackTrace();
			 throw new HolidayException("ERROR--Cannot Get the Holiday Controller.");
		 }
	 }
	 
	 private IHolidayTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IHoliday anIHoliday) {
	        IHolidayTrxValue ccHolidayTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccHolidayTrxValue = new OBHolidayTrxValue(anICMSTrxValue);
	        } else {
	            ccHolidayTrxValue = new OBHolidayTrxValue();
	        }
	        ccHolidayTrxValue = formulateTrxValue(anITrxContext, (IHolidayTrxValue) ccHolidayTrxValue);
	        ccHolidayTrxValue.setStagingHoliday(anIHoliday);
	        return ccHolidayTrxValue;
	    }
	 private IHolidayTrxValue formulateTrxValue(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) {
	        anIHolidayTrxValue.setTrxContext(anITrxContext);
	        anIHolidayTrxValue.setTransactionType(ICMSConstant.INSTANCE_HOLIDAY);
	        return anIHolidayTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete Holiday
		 */

	 public IHolidayTrxValue makerDeleteHoliday(ITrxContext anITrxContext, IHolidayTrxValue anICCHolidayTrxValue, IHoliday anICCHoliday) throws HolidayException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, anICCHolidayTrxValue, anICCHoliday);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_HOLIDAY);
	        return operate(trxValue, param);
	    }

	 /**
		 * @return Maker Create Holiday
		 */
	 public IHolidayTrxValue makerCreateHoliday(ITrxContext anITrxContext, IHoliday anICCHoliday) throws HolidayException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCHoliday to be updated is null !!!");
	        }

	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCHoliday);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_HOLIDAY);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker Save Holiday
		 */
	 
	 public IHolidayTrxValue makerSaveHoliday(ITrxContext anITrxContext, IHoliday anICCHoliday) throws HolidayException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new HolidayException("The ITrxContext is null!!!");
	        }
	        if (anICCHoliday == null) {
	            throw new HolidayException("The ICCHoliday to be updated is null !!!");
	        }

	        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCHoliday);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_HOLIDAY);
	        return operate(trxValue, param);
	    }

	
	//------------------------------------File Upload-----------------------------------------------------
		
		 private IHolidayTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 IHolidayTrxValue ccHolidayTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccHolidayTrxValue = new OBHolidayTrxValue(anICMSTrxValue);
		        } else {
		            ccHolidayTrxValue = new OBHolidayTrxValue();
		        }
		        ccHolidayTrxValue = formulateTrxValueID(anITrxContext, (IHolidayTrxValue) ccHolidayTrxValue);
		        ccHolidayTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccHolidayTrxValue;
		    }
		 
		 private IHolidayTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
			 IHolidayTrxValue ccHolidayTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccHolidayTrxValue = new OBHolidayTrxValue(anICMSTrxValue);
		        } else {
		            ccHolidayTrxValue = new OBHolidayTrxValue();
		        }
		        ccHolidayTrxValue = formulateTrxValueID(anITrxContext, (IHolidayTrxValue) ccHolidayTrxValue);
		        ccHolidayTrxValue.setStagingFileMapperID(fileId);
		        return ccHolidayTrxValue;
		    }
		 private IHolidayTrxValue formulateTrxValueID(ITrxContext anITrxContext, IHolidayTrxValue anIHolidayTrxValue) {
		        anIHolidayTrxValue.setTrxContext(anITrxContext);
		        anIHolidayTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_HOLIDAY);
		        return anIHolidayTrxValue;
		    }
	    
		 
		 /**
			 * @return Maker insert a fileID to generate a transation.
			 */
		 public IHolidayTrxValue makerInsertMapperHoliday(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws HolidayException, TrxParameterException,
					TransactionException {
				// TODO Auto-generated method stub
				if (anITrxContext == null) {
		            throw new HolidayException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new HolidayException("The OBFileMapperID to be updated is null !!!");
		        }

		        IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 /**
			 * @return Maker check if previous upload is pending.
			 */
		 public boolean isPrevFileUploadPending() throws HolidayException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getHolidayBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new HolidayException("ERROR-- Due to null Holiday object cannot update.");
				}
			}
		 
		 /**
			 * @return Maker insert uploaded files in Staging table.
			 */
		 
		 public int insertHoliday(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws HolidayException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getHolidayBusManager().insertHoliday(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new HolidayException("ERROR-- Due to null Holiday object cannot update.");
				}
			}
		 
		 /**
			 * @return create record with TransID.
			 */
		 
		 public IHolidayTrxValue getInsertFileByTrxID(String trxID)
			throws HolidayException, TransactionException,
			CommandProcessingException {
			 	IHolidayTrxValue trxValue = new OBHolidayTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 /**
			 * @return Pagination for uploaded files in Holiday.
			 */
		 public List getAllStage(String searchBy, String login)throws HolidayException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getHolidayBusManager().getAllStageHoliday( searchBy, login);
			}else{
				throw new HolidayException("ERROR- Search criteria is null.");
			}
		  }
		
		 /**
			 * @return Checker approval for uploaded files.
			 */
		 
		 public IHolidayTrxValue checkerApproveInsertHoliday(
		 		ITrxContext anITrxContext,
		 		IHolidayTrxValue anIHolidayTrxValue)
		 		throws HolidayException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		        throw new HolidayException("The ITrxContext is null!!!");
		    }
		    if (anIHolidayTrxValue == null) {
		        throw new HolidayException
		                ("The IHolidayTrxValue to be updated is null!!!");
		    }
		    anIHolidayTrxValue = formulateTrxValueID(anITrxContext, anIHolidayTrxValue);
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		    return operate(anIHolidayTrxValue, param);
		 }
		 
		 /**
			 * @return list of files uploaded in staging table of Holiday.
			 */
		 public List getFileMasterList(String searchBy)throws HolidayException,TrxParameterException,TransactionException {
			if(searchBy!=null){
		
			 return getHolidayBusManager().getFileMasterList( searchBy);
			}else{
				throw new HolidayException("ERROR- Search criteria is null.");
			}
		  }
		 
		 /**
			 * @return Maker insert upload files.
			 */
		 public IHoliday insertActualHoliday(String sysId) throws HolidayException,TrxParameterException,TransactionException

		 {
		  if(sysId != null){
		 	 try {
		 		 return getHolidayBusManager().insertActualHoliday(sysId);
		 	 } catch (Exception e) {		 		
		 		 e.printStackTrace();
		 		 throw new HolidayException("ERROR- Transaction for the Id is invalid.");
		 	 }
		  }else{
		 	 throw new HolidayException("ERROR- Id for retrival is null.");
		  }
		 }
		 
		 /**
			 * @return Checker create file master in Holiday.
			 */
		 
		 public IHolidayTrxValue checkerCreateHoliday(ITrxContext anITrxContext, IHoliday anICCHoliday, String refStage) throws HolidayException,TrxParameterException,
		 TransactionException {
		     if (anITrxContext == null) {
		         throw new HolidayException("The ITrxContext is null!!!");
		     }
		     if (anICCHoliday == null) {
		         throw new HolidayException("The ICCHoliday to be updated is null !!!");
		     }

		     IHolidayTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCHoliday);
		     trxValue.setFromState("PENDING_CREATE");
		     trxValue.setReferenceID(String.valueOf(anICCHoliday.getId()));
		     trxValue.setStagingReferenceID(refStage);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
		     return operate(trxValue, param);
		 }
		 
		 /**
			 * @return Checker Reject for upload files Holiday.
			 */

		 public IHolidayTrxValue checkerRejectInsertHoliday(
		 	ITrxContext anITrxContext,
		 	IHolidayTrxValue anIHolidayTrxValue)
		 	throws HolidayException, TrxParameterException,
		 	TransactionException {
		 	if (anITrxContext == null) {
		 	  throw new HolidayException("The ITrxContext is null!!!");
		 	}
		 	if (anIHolidayTrxValue == null) {
		 	  throw new HolidayException
		 	          ("The IHolidayTrxValue to be updated is null!!!");
		 	}
		 		anIHolidayTrxValue = formulateTrxValueID(anITrxContext, anIHolidayTrxValue);
		 		OBCMSTrxParameter param = new OBCMSTrxParameter();
		 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		 	return operate(anIHolidayTrxValue, param);
		 }
		 
		 /**
			 * @return Maker Close rejected files Holiday.
			 */

		 public IHolidayTrxValue makerInsertCloseRejectedHoliday(
		 		ITrxContext anITrxContext,
		 		IHolidayTrxValue anIHolidayTrxValue)
		 		throws HolidayException, TrxParameterException,
		 		TransactionException {
		 	if (anITrxContext == null) {
		         throw new HolidayException("The ITrxContext is null!!!");
		     }
		     if (anIHolidayTrxValue == null) {
		         throw new HolidayException("The IHolidayTrxValue to be updated is null!!!");
		     }
		     anIHolidayTrxValue = formulateTrxValue(anITrxContext, anIHolidayTrxValue);
		     OBCMSTrxParameter param = new OBCMSTrxParameter();
		     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
		     return operate(anIHolidayTrxValue, param);
		 }

		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
			getHolidayBusManager().deleteTransaction(obFileMapperMaster);			
		}
}
