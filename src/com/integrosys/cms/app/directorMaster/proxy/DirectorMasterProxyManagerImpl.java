
package com.integrosys.cms.app.directorMaster.proxy;



import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMasterBusManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class act as a facade to the services offered by the diary item modules
  *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 04 May 2011) $
 * Tag : $Name$
 */

public class DirectorMasterProxyManagerImpl implements IDirectorMasterProxyManager {

	
	private IDirectorMasterBusManager directorMasterBusManager;
	
	
	private IDirectorMasterBusManager stagingDirectorMasterBusManager;

    private ITrxControllerFactory trxControllerFactory;



	public IDirectorMasterBusManager getStagingDirectorMasterBusManager() {
		return stagingDirectorMasterBusManager;
	}

	public void setStagingDirectorMasterBusManager(
			IDirectorMasterBusManager stagingDirectorMasterBusManager) {
		this.stagingDirectorMasterBusManager = stagingDirectorMasterBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IDirectorMasterBusManager getDirectorMasterBusManager() {
		return directorMasterBusManager;
	}

	public void setDirectorMasterBusManager(IDirectorMasterBusManager directorMasterBusManager) {
		this.directorMasterBusManager = directorMasterBusManager;
	}


	/**
	 * @return List of all Director Master .
	 */
	
	public SearchResult getAllDirectorMaster()throws DirectorMasterException {
		try{
			return getDirectorMasterBusManager().getAllDirectorMaster();
		}catch (Exception e) {
			throw new DirectorMasterException("ERROR- Cannot retrive list from database.");
		}
    }

	/**
	 * @return List of all Director Master according to criteria .
	 */
	
	
	
	/**
	 * @return List of all Director Master according to criteria .
	 */
	
	
	 public SearchResult getAllActual(String searchBy,String searchText)throws DirectorMasterException,TrxParameterException,TransactionException {
		if(searchBy!=null&& searchText!=null){
	
		 return getDirectorMasterBusManager().getAllDirectorMaster( searchBy, searchText);
		}else{
			throw new DirectorMasterException("ERROR- Search criteria is null.");
		}
	    }
	 /**
		 * @return Director Master according to id .
		 */
		
	 public IDirectorMaster getDirectorMasterById(long id) throws DirectorMasterException,TrxParameterException,TransactionException

	 {
		 if(id!=0){
			 try {
				 return getDirectorMasterBusManager().getDirectorMasterById(id);
			 } catch (Exception e) {
				
				 e.printStackTrace();
				 throw new DirectorMasterException("ERROR- Transaction for the Id is invalid.");
			 }
		 }else{
			 throw new DirectorMasterException("ERROR- Id for retrival is null.");
		 }

	 }
	 
	 /**
		 * @return Update Director Master according to criteria .
		 */
		
	 
	 
	 public IDirectorMaster updateDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException, TrxParameterException,
		TransactionException, ConcurrentUpdateException  {
		 IDirectorMaster item = (IDirectorMaster) directorMaster;
			try {
				return getDirectorMasterBusManager().updateDirectorMaster(item);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new DirectorMasterException("ERROR-- Due to null Director Master object cannot update.");
			}
		}
	 
	 /**
		 * @return Delete Director Master according to criteria .
		 */
		
	 public IDirectorMaster disableDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException, TrxParameterException,
		TransactionException {
		 if(!(directorMaster==null)){
		 IDirectorMaster item = (IDirectorMaster) directorMaster;
			try {
				return getDirectorMasterBusManager().disableDirectorMaster(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new DirectorMasterException("ERROR-- Transaction for the DirectorMaster object is null.");
			}
		 }else{
			 throw new DirectorMasterException("ERROR-- Cannot delete due to null DirectorMaster object.");
		 }
		}
	 
	 /**
		 * @return Delete Director Master according to criteria .
		 */
		
	 public IDirectorMaster enableDirectorMaster(IDirectorMaster directorMaster) throws DirectorMasterException, TrxParameterException,
		TransactionException {
		 if(!(directorMaster==null)){
		 IDirectorMaster item = (IDirectorMaster) directorMaster;
			try {
				return getDirectorMasterBusManager().enableDirectorMaster(item);
			} catch (ConcurrentUpdateException e) {
				
				e.printStackTrace();
				throw new DirectorMasterException("ERROR-- Transaction for the DirectorMaster object is null.");
			}
		 }else{
			 throw new DirectorMasterException("ERROR-- Cannot delete due to null DirectorMaster object.");
		 }
		}
	 
	 
	 /**
		 * @return Checker Approve  Director Master according to criteria .
		 */
		
	
	public IDirectorMasterTrxValue checkerApproveDirectorMaster(
			ITrxContext anITrxContext,
			IDirectorMasterTrxValue anIDirectorMasterTrxValue)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new DirectorMasterException("The ITrxContext is null!!!");
        }
        if (anIDirectorMasterTrxValue == null) {
            throw new DirectorMasterException
                    ("The IDirectorMasterTrxValue to be updated is null!!!");
        }
        anIDirectorMasterTrxValue = formulateTrxValue(anITrxContext, anIDirectorMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_DIRECTOR_MASTER);
        return operate(anIDirectorMasterTrxValue, param);
	}
	 /**
	 * @return Checker Reject  Director Master according to criteria .
	 */
	
	
	public IDirectorMasterTrxValue checkerRejectDirectorMaster(
			ITrxContext anITrxContext,
			IDirectorMasterTrxValue anIDirectorMasterTrxValue)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anIDirectorMasterTrxValue == null) {
	            throw new DirectorMasterException("The IDirectorMasterTrxValue to be updated is null!!!");
	        }
	        anIDirectorMasterTrxValue = formulateTrxValue(anITrxContext, anIDirectorMasterTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER);
	        return operate(anIDirectorMasterTrxValue, param);
	}
	
	 /**
	 * @return  Director Master TRX value according to trxId  .
	 */
	

	
	public IDirectorMasterTrxValue getDirectorMasterByTrxID(String aTrxID)
			throws DirectorMasterException, TransactionException,
			CommandProcessingException {
		IDirectorMasterTrxValue trxValue = new OBDirectorMasterTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_DIRECTOR_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_DIRECTOR_MASTER_ID);
        return operate(trxValue, param);
	}
	 /**
	 * @return  Director Master TRX value  .
	 */
	

	public IDirectorMasterTrxValue getDirectorMasterTrxValue(
			long aDirectorMasterId) throws DirectorMasterException,
			TrxParameterException, TransactionException {
		if (aDirectorMasterId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new DirectorMasterException("Invalid DirectorMasterId");
        }
        IDirectorMasterTrxValue trxValue = new OBDirectorMasterTrxValue();
        trxValue.setReferenceID(String.valueOf(aDirectorMasterId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_DIRECTOR_MASTER);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_DIRECTOR_MASTER);
        return operate(trxValue, param);
	}

	 /**
	 * @return Maker Close Director Master  .
	 */
	
	public IDirectorMasterTrxValue makerCloseRejectedDirectorMaster(
			ITrxContext anITrxContext,
			IDirectorMasterTrxValue anIDirectorMasterTrxValue)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new DirectorMasterException("The ITrxContext is null!!!");
        }
        if (anIDirectorMasterTrxValue == null) {
            throw new DirectorMasterException("The IDirectorMasterTrxValue to be updated is null!!!");
        }
        anIDirectorMasterTrxValue = formulateTrxValue(anITrxContext, anIDirectorMasterTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DIRECTOR_MASTER);
        return operate(anIDirectorMasterTrxValue, param);
	}

	 /**
	 * @return Maker Edit Director Master  .
	 */
	public IDirectorMasterTrxValue makerEditRejectedDirectorMaster(
			ITrxContext anITrxContext,
			IDirectorMasterTrxValue anIDirectorMasterTrxValue, IDirectorMaster anDirectorMaster)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new DirectorMasterException("The ITrxContext is null!!!");
        }
        if (anIDirectorMasterTrxValue == null) {
            throw new DirectorMasterException("The IDirectorMasterTrxValue to be updated is null!!!");
        }
        if (anDirectorMaster == null) {
            throw new DirectorMasterException("The IDirectorMaster to be updated is null !!!");
        }
        anIDirectorMasterTrxValue = formulateTrxValue(anITrxContext, anIDirectorMasterTrxValue, anDirectorMaster);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER);
        return operate(anIDirectorMasterTrxValue, param);
	}
	 /**
	 * @return Maker Update Director Master  .
	 */

	public IDirectorMasterTrxValue makerUpdateDirectorMaster(
			ITrxContext anITrxContext,
			IDirectorMasterTrxValue anICCDirectorMasterTrxValue,
			IDirectorMaster anICCDirectorMaster)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCDirectorMaster == null) {
	            throw new DirectorMasterException("The ICCDirectorMaster to be updated is null !!!");
	        }
	        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDirectorMasterTrxValue, anICCDirectorMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DIRECTOR_MASTER);
	        return operate(trxValue, param);
	}
	 /**
	 * @return Maker Director Master  .
	 */
	 private IDirectorMasterTrxValue operate(IDirectorMasterTrxValue anIDirectorMasterTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws DirectorMasterException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIDirectorMasterTrxValue, anOBCMSTrxParameter);
	        return (IDirectorMasterTrxValue) result.getTrxValue();
	    }
	 /**
		 * @return Maker Director Master  .
		 */

	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws DirectorMasterException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);


			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (DirectorMasterException ex) {
			 throw new DirectorMasterException(ex.getMessage());
		 }
		 catch (Exception ex) {
			 ex.printStackTrace();
			 throw new DirectorMasterException("ERROR--Cannot Get the Director Master Controller.");
		 }
	 }
	 /**
		 * @return Maker Delete Director Master  .
		 */

	 private IDirectorMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IDirectorMaster anIDirectorMaster) {
	        IDirectorMasterTrxValue ccDirectorMasterTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccDirectorMasterTrxValue = new OBDirectorMasterTrxValue(anICMSTrxValue);
	        } else {
	            ccDirectorMasterTrxValue = new OBDirectorMasterTrxValue();
	        }
	        ccDirectorMasterTrxValue = formulateTrxValue(anITrxContext, (IDirectorMasterTrxValue) ccDirectorMasterTrxValue);
	        ccDirectorMasterTrxValue.setStagingDirectorMaster(anIDirectorMaster);
	        return ccDirectorMasterTrxValue;
	    }
	 /**
		 * @return Maker Director Master  .
		 */
	 private IDirectorMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, IDirectorMasterTrxValue anIDirectorMasterTrxValue) {
	        anIDirectorMasterTrxValue.setTrxContext(anITrxContext);
	        anIDirectorMasterTrxValue.setTransactionType(ICMSConstant.INSTANCE_DIRECTOR_MASTER);
	        return anIDirectorMasterTrxValue;
	    }
	 
	 /**
		 * @return Maker Delete Director Master  .
		 */

	 public IDirectorMasterTrxValue makerDisableDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster) throws DirectorMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCDirectorMaster == null) {
	            throw new DirectorMasterException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDirectorMasterTrxValue, anICCDirectorMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DISABLE_DIRECTOR_MASTER);
	        return operate(trxValue, param);
	    }
	 
	 /**
		 * @return Maker Delete Director Master  .
		 */

	 public IDirectorMasterTrxValue makerEnableDirectorMaster(ITrxContext anITrxContext, IDirectorMasterTrxValue anICCDirectorMasterTrxValue, IDirectorMaster anICCDirectorMaster) throws DirectorMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCDirectorMaster == null) {
	            throw new DirectorMasterException("The ICCPropertyIdx to be updated is null !!!");
	        }
	        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDirectorMasterTrxValue, anICCDirectorMaster);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_ENABLE_DIRECTOR_MASTER);
	        return operate(trxValue, param);
	    }
	 
	 

	 /**
		 * @return Maker Create Director Master  .
		 */
	 public IDirectorMasterTrxValue makerCreateDirectorMaster(ITrxContext anITrxContext, IDirectorMaster anICCDirectorMaster) throws DirectorMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCDirectorMaster == null) {
	            throw new DirectorMasterException("The ICCDirectorMaster to be updated is null !!!");
	        }

	        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCDirectorMaster);
	        trxValue.setFromState("PENDING_CREATE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DIRECTOR_MASTER);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker search Director Master  .
		 */
	
	 public List searchDirector(String login) throws DirectorMasterException,TrxParameterException,TransactionException {
		 	return getDirectorMasterBusManager().searchDirector(login);

	    }
	 /**
		 * @return Maker save Director Master  .
		 */
	
	
	 public IDirectorMasterTrxValue makerSaveDirectorMaster(ITrxContext anITrxContext, IDirectorMaster anICCDirectorMaster) throws DirectorMasterException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new DirectorMasterException("The ITrxContext is null!!!");
	        }
	        if (anICCDirectorMaster == null) {
	            throw new DirectorMasterException("The ICCDirectorMaster to be updated is null !!!");
	        }

	        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCDirectorMaster);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_DIRECTOR_MASTER);
	        return operate(trxValue, param);
	    }
	 /**
		 * @return Maker updated Director Master  .
		 */
	
	 public IDirectorMasterTrxValue makerUpdateSaveUpdateDirectorMaster(
				ITrxContext anITrxContext,
				IDirectorMasterTrxValue anICCDirectorMasterTrxValue,
				IDirectorMaster anICCDirectorMaster)
				throws DirectorMasterException, TrxParameterException,
				TransactionException {
			 if (anITrxContext == null) {
		            throw new DirectorMasterException("The ITrxContext is null!!!");
		        }
		        if (anICCDirectorMaster == null) {
		            throw new DirectorMasterException("The ICCDirectorMaster to be updated is null !!!");
		        }
		        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDirectorMasterTrxValue, anICCDirectorMaster);
		        trxValue.setFromState("DRAFT");
		       trxValue.setStatus("ACTIVE");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER);
		        return operate(trxValue, param);
		}
	 /**
		 * @return Maker update Director Master  .
		 */
	
	 public IDirectorMasterTrxValue makerUpdateSaveCreateDirectorMaster(
				ITrxContext anITrxContext,
				IDirectorMasterTrxValue anICCDirectorMasterTrxValue,
				IDirectorMaster anICCDirectorMaster)
				throws DirectorMasterException, TrxParameterException,
				TransactionException {
			 if (anITrxContext == null) {
		            throw new DirectorMasterException("The ITrxContext is null!!!");
		        }
		        if (anICCDirectorMaster == null) {
		            throw new DirectorMasterException("The ICCDirectorMaster to be updated is null !!!");
		        }
		        IDirectorMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCDirectorMasterTrxValue, anICCDirectorMaster);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DIRECTOR_MASTER);
		        return operate(trxValue, param);
		}

	public boolean isDinNumberUnique(String dinNumber) {
		return getDirectorMasterBusManager().isDinNumberUnique(dinNumber);
	}

	public boolean isDirectorNameUnique(String directorName) {
		return getDirectorMasterBusManager().isDirectorNameUnique(directorName);
	}
	 
}
