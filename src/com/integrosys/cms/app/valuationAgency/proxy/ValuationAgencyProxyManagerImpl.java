package com.integrosys.cms.app.valuationAgency.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgencyBusManager;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

/**
 * This class act as a facade to the services offered by the diary item modules
 * 
 * @author $Author:  Rajib Aich $<br>
 * @version $Revision: 1.6 $
 * 
 */

public class ValuationAgencyProxyManagerImpl implements
		IValuationAgencyProxyManager {

	private ITrxControllerFactory trxControllerFactory;

	private IValuationAgencyBusManager stagingValuationAgencyBusManager;

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(
			ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IValuationAgencyBusManager getStagingValuationAgencyBusManager() {
		return stagingValuationAgencyBusManager;
	}

	public void setStagingValuationAgencyBusManager(
			IValuationAgencyBusManager stagingValuationAgencyBusManager) {
		this.stagingValuationAgencyBusManager = stagingValuationAgencyBusManager;
	}

	public void setValuationAgencyBusManager(
			IValuationAgencyBusManager valuationAgencyBusManager) {
		this.valuationAgencyBusManager = valuationAgencyBusManager;
	}

	public IValuationAgencyBusManager getValuationAgencyBusManager() {
		return valuationAgencyBusManager;
	}

	private IValuationAgencyBusManager valuationAgencyBusManager;

	/**
	 * @return List of  Valuation Agency
	 * 
	 *         This method access the Database through jdbc and fetch data.
	 */

	public List getAllActual() {
		return getValuationAgencyBusManager().getAllValuationAgency();
	}
	
	public List getFilteredActual(String code,String name) {
		return getValuationAgencyBusManager().getFilteredValuationAgency(code,name);
	}

	/**
	 * @return Maker Create  ValuationAgency
	 */
	public IValuationAgencyTrxValue makerCreateValuationAgency(
			ITrxContext anITrxContext, IValuationAgency anICCvaluationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCvaluationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCValuationAgency to be updated is null !!!");
		}

		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				null, anICCvaluationAgency);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	private IValuationAgencyTrxValue formulateTrxValue(
			ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IValuationAgency anIvaluationAgency) {
		IValuationAgencyTrxValue ccValuationAgencyTrxValue = null;
		if (anICMSTrxValue != null) {
			ccValuationAgencyTrxValue = new OBValuationAgencyTrxValue(
					anICMSTrxValue);
		} else {
			ccValuationAgencyTrxValue = new OBValuationAgencyTrxValue();
		}
		ccValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				(IValuationAgencyTrxValue) ccValuationAgencyTrxValue);
		ccValuationAgencyTrxValue.setStagingValuationAgency(anIvaluationAgency);
		return ccValuationAgencyTrxValue;
	}

	private IValuationAgencyTrxValue formulateTrxValue(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIvaluationAgencyTrxValue) {
		anIvaluationAgencyTrxValue.setTrxContext(anITrxContext);
		anIvaluationAgencyTrxValue
				.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AGENCY);
		return anIvaluationAgencyTrxValue;
	}

	private IValuationAgencyTrxValue operate(
			IValuationAgencyTrxValue anIValuationAgencyTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		ICMSTrxResult result = operateForResult(anIValuationAgencyTrxValue,
				anOBCMSTrxParameter);
		return (IValuationAgencyTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller,"'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue,anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (ValuationAgencyException ex) {
			throw new ValuationAgencyException(
					"ERROR--Cannot Get the Valuation Agency Controller.");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ValuationAgencyException(
					"ERROR--Cannot Get the Valuation Agency Controller.");
		}
	}

	public IValuationAgencyTrxValue getValuationAgencyByTrxID(String aTrxID)
			throws ValuationAgencyException, TransactionException,
			CommandProcessingException {
		IValuationAgencyTrxValue trxValue = new OBValuationAgencyTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AGENCY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_VALUATION_AGENCY_ID);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue checkerApproveValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anIValuationAgencyTrxValue == null) {
			throw new ValuationAgencyException(
					"The IValuationAgencyTrxValue to be updated is null!!!");
		}
		anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				anIValuationAgencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_VALUATION_AGENCY);
		return operate(anIValuationAgencyTrxValue, param);
	}

	public IValuationAgencyTrxValue checkerRejectValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anIValuationAgencyTrxValue == null) {
			throw new ValuationAgencyException(
					"The IValuationAgencyTrxValue to be updated is null!!!");
		}
		anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				anIValuationAgencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AGENCY);
		return operate(anIValuationAgencyTrxValue, param);
	}

	public IValuationAgencyTrxValue makerCloseRejectedValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anIValuationAgencyTrxValue == null) {
			throw new ValuationAgencyException(
					"The IValuationAgencyTrxValue to be updated is null!!!");
		}
		anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				anIValuationAgencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param
				.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_VALUATION_AGENCY);
		return operate(anIValuationAgencyTrxValue, param);
	}

	/**
	 * @return Maker Close draft  Valuation Agency .
	 */

	public IValuationAgencyTrxValue makerCloseDraftValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anIValuationAgencyTrxValue == null) {
			throw new ValuationAgencyException(
					"The IValuationAgencyTrxValue to be updated is null!!!");
		}
		anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				anIValuationAgencyTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_VALUATION_AGENCY);
		return operate(anIValuationAgencyTrxValue, param);
	}

	public IValuationAgencyTrxValue makerEditRejectedValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anIValuationAgencyTrxValue,
			IValuationAgency anValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anIValuationAgencyTrxValue == null) {
			throw new ValuationAgencyException(
					"The IValuationAgencyTrxValue to be updated is null!!!");
		}
		if (anValuationAgency == null) {
			throw new ValuationAgencyException(
					"The IValuationAgency to be updated is null !!!");
		}
		anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext,
				anIValuationAgencyTrxValue, anValuationAgency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param
				.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_VALUATION_AGENCY);
		return operate(anIValuationAgencyTrxValue, param);
	}

	public IValuationAgencyTrxValue getValuationAgencyTrxValue(
			long aValuationAgencyId) throws ValuationAgencyException,
			TrxParameterException, TransactionException {
		if (aValuationAgencyId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new ValuationAgencyException("Invalid ValuationAgencyId");
		}
		IValuationAgencyTrxValue trxValue = new OBValuationAgencyTrxValue();
		trxValue.setReferenceID(String.valueOf(aValuationAgencyId));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_VALUATION_AGENCY);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue makerUpdateSaveCreateValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue,
			IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCValuationAgency to be updated is null !!!");
		}
		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCValuationAgencyTrxValue, anICCValuationAgency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue makerDisableValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue,
			IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCValuationAgencyTrxValue, anICCValuationAgency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DISABLE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Delete  Valuation Agency Master .
	 */

	public IValuationAgencyTrxValue makerEnableValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue,
			IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCValuationAgencyTrxValue, anICCValuationAgency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ENABLE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue makerUpdateSaveUpdateValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue,
			IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCValuationAgency to be updated is null !!!");
		}
		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCValuationAgencyTrxValue, anICCValuationAgency);
		// trxValue.setFromState("DRAFT");
		// trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue makerSaveValuationAgency(
			ITrxContext anITrxContext, IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The ICCValuationAgency to be updated is null !!!");
		}

		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				null, anICCValuationAgency);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}

	public IValuationAgencyTrxValue makerCreateValuationAgency(
			ITrxContext anITrxContext,
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue,
			IValuationAgency anICCValuationAgency)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new ValuationAgencyException("The ITrxContext is null!!!");
		}
		if (anICCValuationAgency == null) {
			throw new ValuationAgencyException(
					"The IValuationAgency to be created is null !!!");
		}
		IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCValuationAgencyTrxValue, anICCValuationAgency);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_VALUATION_AGENCY);
		return operate(trxValue, param);
	}
	
	 public boolean isVACodeUnique(String vaCode){
		 return getValuationAgencyBusManager().isVACodeUnique(vaCode); 
	 }
	 
		public List getCountryList(long countryId) throws ValuationAgencyException {
			return getValuationAgencyBusManager().getCountryList(countryId);
		}
		public List getCityList(long stateId) throws ValuationAgencyException {
			return getValuationAgencyBusManager().getCityList(stateId);
		}
		
		 public boolean isPrevFileUploadPending() throws ValuationAgencyException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getValuationAgencyBusManager().isPrevFileUploadPending();
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new ValuationAgencyException("ERROR-- Due to null  object cannot update.");
				}
			}
		 
		 
		 public IValuationAgencyTrxValue makerInsertMapperValuationAgency(
					ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
					throws ValuationAgencyException, TrxParameterException,
					TransactionException {
				// TODO Auto-generated method stub
				if (anITrxContext == null) {
		            throw new ValuationAgencyException("The ITrxContext is null!!!");
		        }
		        if (obFileMapperID == null) {
		            throw new ValuationAgencyException("The OBFileMapperID to be updated is null !!!");
		        }

		        IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
		        trxValue.setFromState("PENDING_INSERT");
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		        return operate(trxValue, param);
			} 
		 
		 private IValuationAgencyTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
			 IValuationAgencyTrxValue ccValuationAgencyTrxValue = null;
		        if (anICMSTrxValue != null) {
		            ccValuationAgencyTrxValue = new OBValuationAgencyTrxValue(anICMSTrxValue);
		        } else {
		            ccValuationAgencyTrxValue = new OBValuationAgencyTrxValue();
		        }
		        ccValuationAgencyTrxValue = formulateTrxValueID(anITrxContext, (IValuationAgencyTrxValue) ccValuationAgencyTrxValue);
		        ccValuationAgencyTrxValue.setStagingFileMapperID(obFileMapperID);
		        return ccValuationAgencyTrxValue;
		    }
		 
		 private IValuationAgencyTrxValue formulateTrxValueID(ITrxContext anITrxContext, IValuationAgencyTrxValue anIValuationAgencyTrxValue) {
		        anIValuationAgencyTrxValue.setTrxContext(anITrxContext);
		        anIValuationAgencyTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_VALUATION_AGENCY);
		        return anIValuationAgencyTrxValue;
		    }
		 
		 public int insertValuationAgency(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws ValuationAgencyException, TrxParameterException,
			TransactionException  {
			 
				try {
					return getValuationAgencyBusManager().insertValuationAgency(fileMapperMaster, userName, resultlList);
				} catch (Exception e) {
					
					e.printStackTrace();
					throw new ValuationAgencyException("ERROR-- Due to null ValuationAgency object cannot update.");
				}
			}
		 
		 
		 public IValuationAgencyTrxValue getInsertFileByTrxID(String trxID)
			throws ValuationAgencyException, TransactionException,
			CommandProcessingException {
			 IValuationAgencyTrxValue trxValue = new OBValuationAgencyTrxValue();
			 	trxValue.setTransactionID(String.valueOf(trxID));
			 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_VALUATION_AGENCY);
			 	OBCMSTrxParameter param = new OBCMSTrxParameter();
			 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
			 return operate(trxValue, param);
	}
		 
		 public List getAllStage(String searchBy, String login)throws ValuationAgencyException,TrxParameterException,TransactionException {
				if(searchBy!=null){
			
				 return getValuationAgencyBusManager().getAllStageValuationAgency( searchBy, login); 
				}else{
					throw new ValuationAgencyException("ERROR- Search criteria is null.");
				}
			  }
			
		 public IValuationAgencyTrxValue checkerApproveInsertValuationAgency(
			 		ITrxContext anITrxContext,
			 		IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			 		throws ValuationAgencyException, TrxParameterException,
			 		TransactionException {
			 	if (anITrxContext == null) {
			        throw new ValuationAgencyException("The ITrxContext is null!!!");
			    }
			    if (anIValuationAgencyTrxValue == null) {
			        throw new ValuationAgencyException
			                ("The IValuationAgencyTrxValue to be updated is null!!!");
			    }
			    anIValuationAgencyTrxValue = formulateTrxValueID(anITrxContext, anIValuationAgencyTrxValue);
			    OBCMSTrxParameter param = new OBCMSTrxParameter();
			    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
			    return operate(anIValuationAgencyTrxValue, param);
			 }
			 
			 /**
				 * @return list of files uploaded in staging table of ValuationAgency.
				 */
			 public List getFileMasterList(String searchBy)throws ValuationAgencyException,TrxParameterException,TransactionException {
				if(searchBy!=null){
			
				 return getValuationAgencyBusManager().getFileMasterList( searchBy);   
				}else{
					throw new ValuationAgencyException("ERROR- Search criteria is null.");
				}
			  }
			 
			 /**
				 * @return Maker insert upload files.
				 */
			 public IValuationAgency insertActualValuationAgency(String sysId) throws ValuationAgencyException,TrxParameterException,TransactionException

			 {
			  if(sysId != null){
			 	 try {
			 		 return getValuationAgencyBusManager().insertActualValuationAgency(sysId);
			 	 } catch (Exception e) {		 		
			 		 e.printStackTrace();
			 		 throw new ValuationAgencyException("ERROR- Transaction for the Id is invalid.");
			 	 }
			  }else{
			 	 throw new ValuationAgencyException("ERROR- Id for retrival is null.");
			  }
			 }
			 
			 /**
				 * @return Checker create file master in ValuationAgency.
				 */
			 
			 public IValuationAgencyTrxValue checkerCreateValuationAgency(ITrxContext anITrxContext, IValuationAgency anICCValuationAgency, String refStage) throws ValuationAgencyException,TrxParameterException,
			 TransactionException {
			     if (anITrxContext == null) {
			         throw new ValuationAgencyException("The ITrxContext is null!!!");
			     }
			     if (anICCValuationAgency == null) {
			         throw new ValuationAgencyException("The ICCValuationAgency to be updated is null !!!");
			     }

			     IValuationAgencyTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCValuationAgency);
			     trxValue.setFromState("PENDING_CREATE");
			     trxValue.setReferenceID(String.valueOf(anICCValuationAgency.getId()));
			     trxValue.setStagingReferenceID(refStage);
			     OBCMSTrxParameter param = new OBCMSTrxParameter();
			     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
			     return operate(trxValue, param);
			 }
			 
			 /**
				 * @return Checker Reject for upload files ValuationAgency.
				 */

			 public IValuationAgencyTrxValue checkerRejectInsertValuationAgency(
			 	ITrxContext anITrxContext,
			 	IValuationAgencyTrxValue anIValuationAgencyTrxValue)
			 	throws ValuationAgencyException, TrxParameterException,
			 	TransactionException {
			 	if (anITrxContext == null) {
			 	  throw new ValuationAgencyException("The ITrxContext is null!!!");
			 	}
			 	if (anIValuationAgencyTrxValue == null) {
			 	  throw new ValuationAgencyException
			 	          ("The IValuationAgencyTrxValue to be updated is null!!!");
			 	}
			 		anIValuationAgencyTrxValue = formulateTrxValueID(anITrxContext, anIValuationAgencyTrxValue);
			 		OBCMSTrxParameter param = new OBCMSTrxParameter();
			 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
			 	return operate(anIValuationAgencyTrxValue, param);
			 }
			 
			 public IValuationAgencyTrxValue makerInsertCloseRejectedValuationAgency(
				 		ITrxContext anITrxContext,
				 		IValuationAgencyTrxValue anIValuationAgencyTrxValue)
				 		throws ValuationAgencyException, TrxParameterException,
				 		TransactionException {
				 	if (anITrxContext == null) {
				         throw new ValuationAgencyException("The ITrxContext is null!!!");
				     }
				     if (anIValuationAgencyTrxValue == null) {
				         throw new ValuationAgencyException("The IValuationAgencyTrxValue to be updated is null!!!");
				     }
				     anIValuationAgencyTrxValue = formulateTrxValue(anITrxContext, anIValuationAgencyTrxValue);
				     OBCMSTrxParameter param = new OBCMSTrxParameter();
				     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_VALUATION_AGENCY);
				     return operate(anIValuationAgencyTrxValue, param);
				 }
			 
			 public boolean isUniqueCode(String branchCode) throws ValuationAgencyException,TrxParameterException,
				TransactionException {
			     return  getValuationAgencyBusManager().isUniqueCode(branchCode);  
			    }

		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException, TrxParameterException,TransactionException {
			getValuationAgencyBusManager().deleteTransaction(obFileMapperMaster);			
		}

		public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException, TrxParameterException,TransactionException {
			return getValuationAgencyBusManager().isValuationNameUnique(valuationName);
		}

		public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode)throws ValuationAgencyException, TrxParameterException,TransactionException {
			return getValuationAgencyBusManager().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
		}
		
		public String getValuationAgencyName(String companyId) {
			return getValuationAgencyBusManager().getValuationAgencyName(companyId);
		}
}
