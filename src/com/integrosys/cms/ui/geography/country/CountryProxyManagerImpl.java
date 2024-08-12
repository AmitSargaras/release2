package com.integrosys.cms.ui.geography.country;

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
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryBusManager;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class CountryProxyManagerImpl implements ICountryProxyManager {

	private ICountryBusManager countryBusManager;

	private ICountryBusManager stagingCountryBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public ICountryBusManager getCountryBusManager() {
		return countryBusManager;
	}

	public void setCountryBusManager(ICountryBusManager countryBusManager) {
		this.countryBusManager = countryBusManager;
	}

	public ICountryBusManager getStagingCountryBusManager() {
		return stagingCountryBusManager;
	}

	public void setStagingCountryBusManager(
			ICountryBusManager stagingCountryBusManager) {
		this.stagingCountryBusManager = stagingCountryBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ICountryTrxValue checkerApproveCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICountryTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be updated is null!!!");
		}
		anICountryTrxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COUNTRY);
		return operate(anICountryTrxValue, param);
	}

	public ICountryTrxValue checkerRejectCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICountryTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be updated is null!!!");
		}
		anICountryTrxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COUNTRY);
		return operate(anICountryTrxValue, param);
	}

	public ICountry createCountry(ICountry Country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryBusManager().createCountry(Country);
	}

	public ICountry deleteCountry(ICountry Country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {

		return null;
	}

	public ICountryTrxValue getCountryById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid CountryId");
		}
		ICountryTrxValue trxValue = new OBCountryTrxValue();
		trxValue.setReferenceID(String.valueOf(id));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COUNTRY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue getCountryByTrxID(String aTrxID)
			throws NoSuchGeographyException, TransactionException,
			CommandProcessingException {
		ICountryTrxValue trxValue = new OBCountryTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COUNTRY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COUNTRY_ID);
		return operate(trxValue, param);
	}

	public ICountryTrxValue getCountryTrxValue(long aCountryId)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (aCountryId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid CountryId");
		}
		ICountryTrxValue trxValue = new OBCountryTrxValue();
		trxValue.setReferenceID(String.valueOf(aCountryId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COUNTRY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue makerCloseRejectedCountry(
			ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICountryTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be updated is null!!!");
		}
		anICountryTrxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COUNTRY);
		return operate(anICountryTrxValue, param);
	}

	public ICountryTrxValue makerDeleteCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue, ICountry anICountry)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICountry == null) {
			throw new NoSuchGeographyException(
					"The ICountry to be updated is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue, anICountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue makerEditRejectedCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue, ICountry anCountry)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICountryTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be updated is null!!!");
		}
		if (anCountry == null) {
			throw new NoSuchGeographyException(
					"The ICountry to be updated is null !!!");
		}
		anICountryTrxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue, anCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COUNTRY);
		return operate(anICountryTrxValue, param);
	}

	public ICountryTrxValue makerUpdateCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICCCountryTrxValue, ICountry anICCCountry)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCountry == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be updated is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCountryTrxValue, anICCCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountry updateCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		if (country != null) {
			return (ICountry) getCountryBusManager().updateCountry(country);
		} else {
			throw new NoSuchGeographyException("Other Bank Object is null.");
		}
	}

	public SearchResult listCountry(String type, String text)
			throws NoSuchGeographyException {
		try {
			return getCountryBusManager().getCountryList(type, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing Country");
		}
	}

	public ICountryTrxValue makerCreateCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue, ICountry anCountry)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anCountry == null) {
			throw new NoSuchGeographyException(
					"The ICountryTrxValue to be created is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICountryTrxValue, anCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue makerUpdateSaveUpdateCountry(
			ITrxContext anITrxContext, ICountryTrxValue anICCCountryTrxValue,
			ICountry anICCCountry) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCountry == null) {
			throw new NoSuchGeographyException(
					"The ICCCountry to be updated is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCountryTrxValue, anICCCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_COUNTRY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update Draft for create Country .
	 */

	public ICountryTrxValue makerUpdateSaveCreateCountry(
			ITrxContext anITrxContext, ICountryTrxValue anICCCountryTrxValue,
			ICountry anICCCountry) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCountry == null) {
			throw new NoSuchGeographyException(
					"The ICCCountry to be updated is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCountryTrxValue, anICCCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue makerSaveCountry(ITrxContext anITrxContext,
			ICountry anICCCountry) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCountry == null) {
			throw new NoSuchGeographyException(
					"The ICCCountry to be updated is null !!!");
		}

		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCCountry);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COUNTRY);
		return operate(trxValue, param);
	}

	public ICountryTrxValue makerActivateCountry(ITrxContext anITrxContext,
			ICountryTrxValue anICCCountryTrxValue, ICountry anICCCountry)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCountry == null) {
			throw new NoSuchGeographyException(
					"The ICountry to be updated is null !!!");
		}
		ICountryTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCountryTrxValue, anICCCountry);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_COUNTRY);
		return operate(trxValue, param);
	}

	public boolean checkActiveRegion(ICountry country) {
		return getCountryBusManager().checkActiveRegion(country);
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anICountry
	 * @return
	 */
	private ICountryTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, ICountry anICountry) {
		ICountryTrxValue countryTrxValue = null;
		if (anICMSTrxValue != null) {
			countryTrxValue = new OBCountryTrxValue(anICMSTrxValue);
		} else {
			countryTrxValue = new OBCountryTrxValue();
		}
		countryTrxValue = formulateTrxValue(anITrxContext,
				(ICountryTrxValue) countryTrxValue);
		countryTrxValue.setStagingCountry(anICountry);
		return countryTrxValue;
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICountryTrxValue
	 * @return ICountryTrxValue
	 */
	private ICountryTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICountryTrxValue anICountryTrxValue) {
		anICountryTrxValue.setTrxContext(anITrxContext);
		anICountryTrxValue.setTransactionType(ICMSConstant.INSTANCE_COUNTRY);
		return anICountryTrxValue;
	}

	private ICountryTrxValue operate(
			ICountryTrxValue anICountryTrxValueTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		ICMSTrxResult result = operateForResult(anICountryTrxValueTrxValue,
				anOBCMSTrxParameter);
		return (ICountryTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory()
					.getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate
					.notNull(controller,
							"'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue,
					anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}

		catch (NoSuchGeographyException ex) {
			throw new NoSuchGeographyException(ex.toString());
		}
	}
	
	
	//------------------------------------File Upload-----------------------------------------------------
	
	 private ICountryTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 ICountryTrxValue ccCountryTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCountryTrxValue = new OBCountryTrxValue(anICMSTrxValue);
	        } else {
	            ccCountryTrxValue = new OBCountryTrxValue();
	        }
	        ccCountryTrxValue = formulateTrxValueID(anITrxContext, (ICountryTrxValue) ccCountryTrxValue);
	        ccCountryTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccCountryTrxValue;
	    }
	 
	 private ICountryTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 ICountryTrxValue ccCountryTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCountryTrxValue = new OBCountryTrxValue(anICMSTrxValue);
	        } else {
	            ccCountryTrxValue = new OBCountryTrxValue();
	        }
	        ccCountryTrxValue = formulateTrxValueID(anITrxContext, (ICountryTrxValue) ccCountryTrxValue);
	        ccCountryTrxValue.setStagingFileMapperID(fileId);
	        return ccCountryTrxValue;
	    }
	 private ICountryTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICountryTrxValue anICountryTrxValue) {
	        anICountryTrxValue.setTrxContext(anITrxContext);
	        anICountryTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_COUNTRY);
	        return anICountryTrxValue;
	    }
   
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public ICountryTrxValue makerInsertMapperCountry(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws CountryException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new CountryException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new CountryException("The OBFileMapperID to be updated is null !!!");
	        }

	        ICountryTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws CountryException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getCountryBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CountryException("ERROR-- Due to null Country object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertCountry(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws CountryException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getCountryBusManager().insertCountry(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CountryException("ERROR-- Due to null Country object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public ICountryTrxValue getInsertFileByTrxID(String trxID)
		throws CountryException, TransactionException,
		CommandProcessingException {
		 	ICountryTrxValue trxValue = new OBCountryTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_COUNTRY);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in Country.
		 */
	 public List getAllStage(String searchBy, String login)throws CountryException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCountryBusManager().getAllStageCountry( searchBy, login);
		}else{
			throw new CountryException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public ICountryTrxValue checkerApproveInsertCountry(
	 		ITrxContext anITrxContext,
	 		ICountryTrxValue anICountryTrxValue)
	 		throws CountryException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new CountryException("The ITrxContext is null!!!");
	    }
	    if (anICountryTrxValue == null) {
	        throw new CountryException
	                ("The ICountryTrxValue to be updated is null!!!");
	    }
	    anICountryTrxValue = formulateTrxValueID(anITrxContext, anICountryTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anICountryTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of Country.
		 */
	 public List getFileMasterList(String searchBy)throws CountryException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCountryBusManager().getFileMasterList( searchBy);
		}else{
			throw new CountryException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public ICountry insertActualCountry(String sysId) throws CountryException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getCountryBusManager().insertActualCountry(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new CountryException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new CountryException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in Country.
		 */
	 
	 public ICountryTrxValue checkerCreateCountry(ITrxContext anITrxContext, ICountry anICCCountry, String refStage) throws CountryException,TrxParameterException,
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new CountryException("The ITrxContext is null!!!");
	     }
	     if (anICCCountry == null) {
	         throw new CountryException("The ICCCountry to be updated is null !!!");
	     }

	     ICountryTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCountry);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCCountry.getIdCountry()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files Country.
		 */

	 public ICountryTrxValue checkerRejectInsertCountry(
	 	ITrxContext anITrxContext,
	 	ICountryTrxValue anICountryTrxValue)
	 	throws CountryException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new CountryException("The ITrxContext is null!!!");
	 	}
	 	if (anICountryTrxValue == null) {
	 	  throw new CountryException
	 	          ("The ICountryTrxValue to be updated is null!!!");
	 	}
	 		anICountryTrxValue = formulateTrxValueID(anITrxContext, anICountryTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anICountryTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files Country.
		 */

	 public ICountryTrxValue makerInsertCloseRejectedCountry(
	 		ITrxContext anITrxContext,
	 		ICountryTrxValue anICountryTrxValue)
	 		throws CountryException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new CountryException("The ITrxContext is null!!!");
	     }
	     if (anICountryTrxValue == null) {
	         throw new CountryException("The ICountryTrxValue to be updated is null!!!");
	     }
	     anICountryTrxValue = formulateTrxValue(anITrxContext, anICountryTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anICountryTrxValue, param);
	 }

	public boolean isCountryCodeUnique(String countryCode) {
		return getCountryBusManager().isCountryCodeUnique(countryCode);
	}
	
	public boolean isCountryNameUnique(String countryName) {
		return getCountryBusManager().isCountryNameUnique(countryName);
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCountryBusManager().deleteTransaction(obFileMapperMaster);
	}
}
