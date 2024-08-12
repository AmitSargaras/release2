package com.integrosys.cms.ui.geography.region;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionBusManager;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RegionProxyManagerImpl implements IRegionProxyManager {

	private IRegionBusManager regionBusManager;

	private IRegionBusManager stagingRegionBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public IRegionBusManager getRegionBusManager() {
		return regionBusManager;
	}

	public void setRegionBusManager(IRegionBusManager regionBusManager) {
		this.regionBusManager = regionBusManager;
	}

	public IRegionBusManager getStagingRegionBusManager() {
		return stagingRegionBusManager;
	}

	public void setStagingRegionBusManager(
			IRegionBusManager stagingRegionBusManager) {
		this.stagingRegionBusManager = stagingRegionBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IRegionTrxValue checkerApproveRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anIRegionTrxValue == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be updated is null!!!");
		}
		anIRegionTrxValue = formulateTrxValue(anITrxContext, anIRegionTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_REGION);
		return operate(anIRegionTrxValue, param);
	}

	public IRegionTrxValue checkerRejectRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anIRegionTrxValue == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be updated is null!!!");
		}
		anIRegionTrxValue = formulateTrxValue(anITrxContext, anIRegionTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_REGION);
		return operate(anIRegionTrxValue, param);
	}

	public IRegion createRegion(IRegion Region)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getRegionBusManager().createRegion(Region);
	}

	public IRegionTrxValue getRegionById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid RegionId");
		}
		IRegionTrxValue trxValue = new OBRegionTrxValue();
		trxValue.setReferenceID(String.valueOf(id));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_REGION);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_REGION);
		return operate(trxValue, param);
	}

	public IRegionTrxValue getRegionByTrxID(String aTrxID)
			throws NoSuchGeographyException, TransactionException,
			CommandProcessingException {
		IRegionTrxValue trxValue = new OBRegionTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_REGION);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_REGION_ID);
		return operate(trxValue, param);
	}

	public IRegionTrxValue getRegionTrxValue(long aRegionId)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (aRegionId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new NoSuchGeographyException("Invalid RegionId");
		}
		IRegionTrxValue trxValue = new OBRegionTrxValue();
		trxValue.setReferenceID(String.valueOf(aRegionId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_REGION);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_REGION);
		return operate(trxValue, param);
	}

	public IRegionTrxValue makerCloseRejectedRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anIRegionTrxValue == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be updated is null!!!");
		}
		anIRegionTrxValue = formulateTrxValue(anITrxContext, anIRegionTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_REGION);
		return operate(anIRegionTrxValue, param);
	}

	public IRegionTrxValue makerDeleteRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue, IRegion anIRegion)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anIRegion == null) {
			throw new NoSuchGeographyException(
					"The IRegion to be updated is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anIRegionTrxValue, anIRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_REGION);
		return operate(trxValue, param);
	}

	public IRegionTrxValue makerEditRejectedRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue, IRegion anRegion)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anIRegionTrxValue == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be updated is null!!!");
		}
		if (anRegion == null) {
			throw new NoSuchGeographyException(
					"The IRegion to be updated is null !!!");
		}
		anIRegionTrxValue = formulateTrxValue(anITrxContext, anIRegionTrxValue,
				anRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_REGION);
		return operate(anIRegionTrxValue, param);
	}

	public IRegionTrxValue makerUpdateRegion(ITrxContext anITrxContext,
			IRegionTrxValue anICCRegionTrxValue, IRegion anICCRegion)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCRegion == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be updated is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCRegionTrxValue, anICCRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REGION);
		return operate(trxValue, param);
	}

	public IRegion updateRegion(IRegion region)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		if (region != null) {
			return (IRegion) getRegionBusManager().updateRegion(region);
		} else {
			throw new NoSuchGeographyException("Other Bank Object is null.");
		}
	}

	public SearchResult listRegion(String type, String text)
			throws NoSuchGeographyException {
		try {
			return getRegionBusManager().getRegionList(type, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing Region");
		}
	}

	public IRegionTrxValue makerCreateRegion(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue, IRegion anRegion)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anRegion == null) {
			throw new NoSuchGeographyException(
					"The IRegionTrxValue to be created is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anIRegionTrxValue, anRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_REGION);
		return operate(trxValue, param);
	}

	public IRegionTrxValue makerUpdateSaveUpdateRegion(
			ITrxContext anITrxContext, IRegionTrxValue anICCRegionTrxValue,
			IRegion anICCRegion) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCRegion == null) {
			throw new NoSuchGeographyException(
					"The ICCRegion to be updated is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCRegionTrxValue, anICCRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_REGION);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Update Draft for create Region .
	 */

	public IRegionTrxValue makerUpdateSaveCreateRegion(
			ITrxContext anITrxContext, IRegionTrxValue anICCRegionTrxValue,
			IRegion anICCRegion) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCRegion == null) {
			throw new NoSuchGeographyException(
					"The ICCRegion to be updated is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCRegionTrxValue, anICCRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_REGION);
		return operate(trxValue, param);
	}

	public IRegionTrxValue makerSaveRegion(ITrxContext anITrxContext,
			IRegion anICCRegion) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCRegion == null) {
			throw new NoSuchGeographyException(
					"The ICCRegion to be updated is null !!!");
		}

		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCRegion);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_REGION);
		return operate(trxValue, param);
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anICMSTrxValue
	 * @param anIRegion
	 * @return
	 */
	private IRegionTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, IRegion anIRegion) {
		IRegionTrxValue regionTrxValue = null;
		if (anICMSTrxValue != null) {
			regionTrxValue = new OBRegionTrxValue(anICMSTrxValue);
		} else {
			regionTrxValue = new OBRegionTrxValue();
		}
		regionTrxValue = formulateTrxValue(anITrxContext,
				(IRegionTrxValue) regionTrxValue);
		regionTrxValue.setStagingRegion(anIRegion);
		return regionTrxValue;
	}

	/**
	 * 
	 * @param anITrxContext
	 * @param anIRegionTrxValue
	 * @return IRegionTrxValue
	 */
	private IRegionTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IRegionTrxValue anIRegionTrxValue) {
		anIRegionTrxValue.setTrxContext(anITrxContext);
		anIRegionTrxValue.setTransactionType(ICMSConstant.INSTANCE_REGION);
		return anIRegionTrxValue;
	}

	private IRegionTrxValue operate(IRegionTrxValue anIRegionTrxValueTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		ICMSTrxResult result = operateForResult(anIRegionTrxValueTrxValue,
				anOBCMSTrxParameter);
		return (IRegionTrxValue) result.getTrxValue();
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
		return getRegionBusManager().getCountryList(countryId);
	}

	public boolean checkActiveState(IRegion region) {
		return getRegionBusManager().checkActiveState(region);
	}

	public boolean checkInActiveCountries(IRegion region) {
		return getRegionBusManager().checkInActiveCountries(region);
	}

	public IRegionTrxValue makerActivateRegion(ITrxContext anITrxContext,
			IRegionTrxValue anICCRegionTrxValue, IRegion anICCRegion)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new NoSuchGeographyException("The ITrxContext is null!!!");
		}
		if (anICCRegion == null) {
			throw new NoSuchGeographyException(
					"The IRegion to be updated is null !!!");
		}
		IRegionTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCRegionTrxValue, anICCRegion);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_REGION);
		return operate(trxValue, param);
	}
	
	//------------------------------------File Upload-----------------------------------------------------
	
	 private IRegionTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 IRegionTrxValue ccRegionTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccRegionTrxValue = new OBRegionTrxValue(anICMSTrxValue);
	        } else {
	            ccRegionTrxValue = new OBRegionTrxValue();
	        }
	        ccRegionTrxValue = formulateTrxValueID(anITrxContext, (IRegionTrxValue) ccRegionTrxValue);
	        ccRegionTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccRegionTrxValue;
	    }
	 
	 private IRegionTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 IRegionTrxValue ccRegionTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccRegionTrxValue = new OBRegionTrxValue(anICMSTrxValue);
	        } else {
	            ccRegionTrxValue = new OBRegionTrxValue();
	        }
	        ccRegionTrxValue = formulateTrxValueID(anITrxContext, (IRegionTrxValue) ccRegionTrxValue);
	        ccRegionTrxValue.setStagingFileMapperID(fileId);
	        return ccRegionTrxValue;
	    }
	 private IRegionTrxValue formulateTrxValueID(ITrxContext anITrxContext, IRegionTrxValue anIRegionTrxValue) {
	        anIRegionTrxValue.setTrxContext(anITrxContext);
	        anIRegionTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_REGION);
	        return anIRegionTrxValue;
	    }
  
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IRegionTrxValue makerInsertMapperRegion(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws NoSuchGeographyException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new NoSuchGeographyException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new NoSuchGeographyException("The OBFileMapperID to be updated is null !!!");
	        }

	        IRegionTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
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
				return getRegionBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null Region object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertRegion(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getRegionBusManager().insertRegion(fileMapperMaster, userName, resultlList);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null Region object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IRegionTrxValue getInsertFileByTrxID(String trxID)
		throws NoSuchGeographyException, TransactionException,
		CommandProcessingException {
		 	IRegionTrxValue trxValue = new OBRegionTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_REGION);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in Region.
		 */
	 public List getAllStage(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getRegionBusManager().getAllStageRegion( searchBy, login);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IRegionTrxValue checkerApproveInsertRegion(
	 		ITrxContext anITrxContext,
	 		IRegionTrxValue anIRegionTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new NoSuchGeographyException("The ITrxContext is null!!!");
	    }
	    if (anIRegionTrxValue == null) {
	        throw new NoSuchGeographyException
	                ("The IRegionTrxValue to be updated is null!!!");
	    }
	    anIRegionTrxValue = formulateTrxValueID(anITrxContext, anIRegionTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIRegionTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of Region.
		 */
	 public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getRegionBusManager().getFileMasterList( searchBy);
		}else{
			throw new NoSuchGeographyException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public IRegion insertActualRegion(String sysId) throws NoSuchGeographyException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getRegionBusManager().insertActualRegion(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new NoSuchGeographyException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new NoSuchGeographyException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in Region.
		 */
	 
	 public IRegionTrxValue checkerCreateRegion(ITrxContext anITrxContext, IRegion anICCRegion, String refStage) throws NoSuchGeographyException,TrxParameterException,
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anICCRegion == null) {
	         throw new NoSuchGeographyException("The ICCRegion to be updated is null !!!");
	     }

	     IRegionTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCRegion);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCRegion.getIdRegion()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files Region.
		 */

	 public IRegionTrxValue checkerRejectInsertRegion(
	 	ITrxContext anITrxContext,
	 	IRegionTrxValue anIRegionTrxValue)
	 	throws NoSuchGeographyException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new NoSuchGeographyException("The ITrxContext is null!!!");
	 	}
	 	if (anIRegionTrxValue == null) {
	 	  throw new NoSuchGeographyException
	 	          ("The IRegionTrxValue to be updated is null!!!");
	 	}
	 		anIRegionTrxValue = formulateTrxValueID(anITrxContext, anIRegionTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIRegionTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files Region.
		 */

	 public IRegionTrxValue makerInsertCloseRejectedRegion(
	 		ITrxContext anITrxContext,
	 		IRegionTrxValue anIRegionTrxValue)
	 		throws NoSuchGeographyException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new NoSuchGeographyException("The ITrxContext is null!!!");
	     }
	     if (anIRegionTrxValue == null) {
	         throw new NoSuchGeographyException("The IRegionTrxValue to be updated is null!!!");
	     }
	     anIRegionTrxValue = formulateTrxValue(anITrxContext, anIRegionTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIRegionTrxValue, param);
	 }

	public boolean isRegionCodeUnique(String regionCode) {
		return getRegionBusManager().isRegionCodeUnique(regionCode);
	}
	
	public boolean isRegionNameUnique(String regionName,long countryId) {
		return getRegionBusManager().isRegionNameUnique(regionName,countryId);
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getRegionBusManager().deleteTransaction(obFileMapperMaster);	
	}

	public ICountry getCountryByCountryCode(String countryCode) {
		return getRegionBusManager().getCountryByCountryCode(countryCode);
	}
}
