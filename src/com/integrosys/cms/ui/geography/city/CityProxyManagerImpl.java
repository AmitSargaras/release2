package com.integrosys.cms.ui.geography.city;

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
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityBusManager;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class CityProxyManagerImpl implements ICityProxyManager {

	private ICityBusManager cityBusManager;

	private ICityBusManager stagingCityBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public ICityBusManager getCityBusManager() {
		return cityBusManager;
	}

	public void setCityBusManager(ICityBusManager cityBusManager) {
		this.cityBusManager = cityBusManager;
	}

	public ICityBusManager getStagingCityBusManager() {
		return stagingCityBusManager;
	}

	public void setStagingCityBusManager(ICityBusManager stagingCityBusManager) {
		this.stagingCityBusManager = stagingCityBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ICityTrxValue checkerApproveCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICityTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be updated is null!!!");
		}
		anICityTrxValue = formulateTrxValue(anITrxContext, anICityTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CITY);
		return operate(anICityTrxValue, param);
	}

	public ICityTrxValue checkerRejectCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICityTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be updated is null!!!");
		}
		anICityTrxValue = formulateTrxValue(anITrxContext, anICityTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CITY);
		return operate(anICityTrxValue, param);
	}

	public ICity createCity(ICity City) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getCityBusManager().createCity(City);
	}

	public ICityTrxValue getCityById(long id) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid CityId");
		}
		ICityTrxValue trxValue = new OBCityTrxValue();
		trxValue.setReferenceID(String.valueOf(id));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CITY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue getCityByTrxID(String aTrxID)
			throws NoSuchGeographyException, TransactionException,
			CommandProcessingException {
		ICityTrxValue trxValue = new OBCityTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CITY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CITY_ID);
		return operate(trxValue, param);
	}

	public ICityTrxValue getCityTrxValue(long aCityId)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (aCityId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid CityId");
		}
		ICityTrxValue trxValue = new OBCityTrxValue();
		trxValue.setReferenceID(String.valueOf(aCityId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CITY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue makerCloseRejectedCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICityTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be updated is null!!!");
		}
		anICityTrxValue = formulateTrxValue(anITrxContext, anICityTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CITY);
		return operate(anICityTrxValue, param);
	}

	public ICityTrxValue makerDeleteCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue, ICity anICity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICity == null) {
			throw new NoSuchGeographyException(
					"The ICity to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICityTrxValue, anICity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue makerActivateCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue, ICity anICity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICity == null) {
			throw new NoSuchGeographyException(
					"The ICity to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICityTrxValue, anICity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue makerEditRejectedCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue, ICity anCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICityTrxValue == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be updated is null!!!");
		}
		if (anCity == null) {
			throw new NoSuchGeographyException(
					"The ICity to be updated is null !!!");
		}
		anICityTrxValue = formulateTrxValue(anITrxContext, anICityTrxValue,
				anCity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CITY);
		return operate(anICityTrxValue, param);
	}

	public ICityTrxValue makerUpdateCity(ITrxContext anITrxContext,
			ICityTrxValue anICCCityTrxValue, ICity anICCCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCity == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCityTrxValue, anICCCity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CITY);
		return operate(trxValue, param);
	}

	public ICity updateCity(ICity city) throws NoSuchGeographyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		if (city != null) {
			return (ICity) getCityBusManager().updateCity(city);
		} else {
			throw new NoSuchGeographyException("Other Bank Object is null.");
		}
	}

	public SearchResult listCity(String type, String text)
			throws NoSuchGeographyException {
		try {
			return getCityBusManager().listCity(type, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing City");
		}
	}

	public ICityTrxValue makerCreateCity(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue, ICity anCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anCity == null) {
			throw new NoSuchGeographyException(
					"The ICityTrxValue to be created is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICityTrxValue, anCity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue makerUpdateSaveUpdateCity(ITrxContext anITrxContext,
			ICityTrxValue anICCCityTrxValue, ICity anICCCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCity == null) {
			throw new NoSuchGeographyException(
					"The ICCCity to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCityTrxValue, anICCCity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_CITY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update Draft for create City .
	 */

	public ICityTrxValue makerUpdateSaveCreateCity(ITrxContext anITrxContext,
			ICityTrxValue anICCCityTrxValue, ICity anICCCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCity == null) {
			throw new NoSuchGeographyException(
					"The ICCCity to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCCityTrxValue, anICCCity);
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CITY);
		return operate(trxValue, param);
	}

	public ICityTrxValue makerSaveCity(ITrxContext anITrxContext,
			ICity anICCCity) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCCity == null) {
			throw new NoSuchGeographyException(
					"The ICCCity to be updated is null !!!");
		}
		ICityTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCCity);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CITY);
		return operate(trxValue, param);
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anICity
	 * @return
	 */

	private ICityTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, ICity anICity) {
		ICityTrxValue cityTrxValue = null;
		if (anICMSTrxValue != null) {
			cityTrxValue = new OBCityTrxValue(anICMSTrxValue);
		} else {
			cityTrxValue = new OBCityTrxValue();
		}
		cityTrxValue = formulateTrxValue(anITrxContext,
				(ICityTrxValue) cityTrxValue);
		cityTrxValue.setStagingCity(anICity);
		return cityTrxValue;
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICityTrxValue
	 * @return ICityTrxValue
	 */

	private ICityTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICityTrxValue anICityTrxValue) {
		anICityTrxValue.setTrxContext(anITrxContext);
		anICityTrxValue.setTransactionType(ICMSConstant.INSTANCE_CITY);
		return anICityTrxValue;
	}

	private ICityTrxValue operate(ICityTrxValue anICityTrxValueTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		ICMSTrxResult result = operateForResult(anICityTrxValueTrxValue,
				anOBCMSTrxParameter);
		return (ICityTrxValue) result.getTrxValue();
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

	public List getCountryList(long countryId) throws NoSuchGeographyException {
		return getCityBusManager().getCountryList(countryId);
	}

	public List getRegionList(long stateId) throws NoSuchGeographyException {
		return getCityBusManager().getRegionList(stateId);
	}

	public List getStateList(long stateId) throws NoSuchGeographyException {
		return getCityBusManager().getStateList(stateId);
	}

	public List getCityList(long stateId) throws NoSuchGeographyException {
		return getCityBusManager().getCityList(stateId);
	}

	public boolean checkInActiveStates(ICity city) {
		return getCityBusManager().checkInActiveStates(city);
	}

	public boolean isCityCodeUnique(String cityCode) {
		return getCityBusManager().isCityCodeUnique(cityCode);
	}
	
	public boolean isCityNameUnique(String cityName,long stateId) {
		return getCityBusManager().isCityNameUnique(cityName,stateId);
	}
	
	//------------------------------------File Upload-----------------------------------------------------
	
	 private ICityTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 ICityTrxValue ccCityTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCityTrxValue = new OBCityTrxValue(anICMSTrxValue);
	        } else {
	            ccCityTrxValue = new OBCityTrxValue();
	        }
	        ccCityTrxValue = formulateTrxValueID(anITrxContext, (ICityTrxValue) ccCityTrxValue);
	        ccCityTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccCityTrxValue;
	    }
	 
	 private ICityTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 ICityTrxValue ccCityTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCityTrxValue = new OBCityTrxValue(anICMSTrxValue);
	        } else {
	            ccCityTrxValue = new OBCityTrxValue();
	        }
	        ccCityTrxValue = formulateTrxValueID(anITrxContext, (ICityTrxValue) ccCityTrxValue);
	        ccCityTrxValue.setStagingFileMapperID(fileId);
	        return ccCityTrxValue;
	    }
	 private ICityTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICityTrxValue anICityTrxValue) {
	        anICityTrxValue.setTrxContext(anITrxContext);
	        anICityTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_CITY);
	        return anICityTrxValue;
	    }

	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public ICityTrxValue makerInsertMapperCity(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws NoSuchGeographyException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new NoSuchGeographyException("The OBFileMapperID to be updated is null !!!");
	        }

	        ICityTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
			try {
				return getCityBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null City object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertCity(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getCityBusManager().insertCity(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null City object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public ICityTrxValue getInsertFileByTrxID(String trxID)
		throws NoSuchGeographyException, TransactionException,
		CommandProcessingException {
		 	ICityTrxValue trxValue = new OBCityTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_CITY);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in City.
		 */
	 public List getAllStage(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCityBusManager().getAllStageCity( searchBy, login);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public ICityTrxValue checkerApproveInsertCity(
	 		ITrxContext anITrxContext,
	 		ICityTrxValue anICityTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new NoSuchGeographyException("The ITrxContext is null!!!");
	    }
	    if (anICityTrxValue == null) {
	        throw new NoSuchGeographyException
	                ("The ICityTrxValue to be updated is null!!!");
	    }
	    anICityTrxValue = formulateTrxValueID(anITrxContext, anICityTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anICityTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of City.
		 */
	 public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCityBusManager().getFileMasterList( searchBy);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public ICity insertActualCity(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getCityBusManager().insertActualCity(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new NoSuchGeographyException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new NoSuchGeographyException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in City.
		 */
	 
	 public ICityTrxValue checkerCreateCity(ITrxContext anITrxContext, ICity anICCCity, String refStage) throws NoSuchGeographyException,TrxParameterException,
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anICCCity == null) {
	         throw new NoSuchGeographyException("The ICCCity to be updated is null !!!");
	     }

	     ICityTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCity);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCCity.getIdCity()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files City.
		 */

	 public ICityTrxValue checkerRejectInsertCity(
	 	ITrxContext anITrxContext,
	 	ICityTrxValue anICityTrxValue)
	 	throws NoSuchGeographyException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new NoSuchGeographyException("The ITrxContext is null!!!");
	 	}
	 	if (anICityTrxValue == null) {
	 	  throw new NoSuchGeographyException
	 	          ("The ICityTrxValue to be updated is null!!!");
	 	}
	 		anICityTrxValue = formulateTrxValueID(anITrxContext, anICityTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anICityTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files City.
		 */

	 public ICityTrxValue makerInsertCloseRejectedCity(
	 		ITrxContext anITrxContext,
	 		ICityTrxValue anICityTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anICityTrxValue == null) {
	         throw new NoSuchGeographyException("The ICityTrxValue to be updated is null!!!");
	     }
	     anICityTrxValue = formulateTrxValue(anITrxContext, anICityTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anICityTrxValue, param);
	 }
	 
	 /**
		 * @return Maker deleteTransaction.  //A shiv 170811
		 */
	 public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				getCityBusManager().deleteTransaction(obFileMapperMaster);
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null City object cannot update.");
			}
	 }
	 
	public List getCityByCountryCode(String countryCode)
			throws NoSuchGeographyException {
		return getCityBusManager().getCityByCountryCode(countryCode);
	}
}
