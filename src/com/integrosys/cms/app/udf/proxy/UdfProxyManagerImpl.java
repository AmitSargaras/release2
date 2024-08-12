package com.integrosys.cms.app.udf.proxy;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfBusManager;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;

public class UdfProxyManagerImpl implements IUdfProxyManager {

	// Spring wired.
	IUdfBusManager udfBusManager;
	
	private IUdfBusManager stagingUdfBusManager;
	
	private ITrxControllerFactory trxControllerFactory;

	public IUdfBusManager getUdfBusManager() {
		return udfBusManager;
	}

	public void setUdfBusManager(IUdfBusManager udfBusManager) {
		this.udfBusManager = udfBusManager;
	}

	// Implemented methods.
	public void deleteUdf(IUdf udf) throws UdfException {
		getUdfBusManager().deleteUdf(udf);
	}

	public List findAllUdfs() throws UdfException {
		return getUdfBusManager().findAllUdfs();
	}

	public IUdf findUdfById(String entityName, long id) throws UdfException {
		return getUdfBusManager().findUdfById(entityName, id);
	}

	public IUdf insertUdf(IUdf udf) throws UdfException {
		return getUdfBusManager().insertUdf(udf);
	}

	public IUdf updateUdf(IUdf udf) throws UdfException, TrxParameterException, TransactionException {
		return getUdfBusManager().updateUdf(udf);
	}

	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException {
		return getUdfBusManager().getUdfSequencesByModuleId(moduleId);
	}
	
	public void freezeUdf(IUdf udf) throws UdfException {
		getUdfBusManager().freezeUdf(udf);
	}
	
	public List findUdfByStatus(String entityName, String status) throws UdfException {
		return getUdfBusManager().findUdfByStatus(entityName, status);
	}
	
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException {
		return getUdfBusManager().getUdfByModuleIdAndStatus(moduleId, status);
	}
	

	public IUdfBusManager getStagingUdfBusManager() {
		return stagingUdfBusManager;
	}

	public void setStagingUdfBusManager(IUdfBusManager stagingUdfBusManager) {
		this.stagingUdfBusManager = stagingUdfBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	
	
	/**
	 * @return Maker Create Udf
	 */
	public IUdfTrxValue makerCreateUdf(ITrxContext anITrxContext,
			IUdf anICCUdf)
			throws UdfException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new UdfException("The ITrxContext is null!!!");
		}
		if (anICCUdf == null) {
			throw new UdfException("The ICCUdf to be updated is null !!!");
		}

		IUdfTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCUdf);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_UDF);
		return operate(trxValue, param);
	}
	
	
	
	private IUdfTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IUdf anIUdf) {
		IUdfTrxValue ccUdfTrxValue = null;
		if (anICMSTrxValue != null) {
			ccUdfTrxValue = new OBUdfTrxValue(anICMSTrxValue);
		} else {
			ccUdfTrxValue = new OBUdfTrxValue();
		}
		ccUdfTrxValue = formulateTrxValue(anITrxContext,
				(IUdfTrxValue) ccUdfTrxValue);
		ccUdfTrxValue.setStagingUdf(anIUdf);
		return ccUdfTrxValue;
	}

	private IUdfTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IUdfTrxValue anIUdfTrxValue) {
		anIUdfTrxValue.setTrxContext(anITrxContext);
		anIUdfTrxValue.setTransactionType(ICMSConstant.INSTANCE_UDF);
		return anIUdfTrxValue;
	}

	private IUdfTrxValue operate(IUdfTrxValue anIUdfTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws UdfException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIUdfTrxValue, anOBCMSTrxParameter);
		return (IUdfTrxValue) result.getTrxValue();
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws UdfException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (UdfException ex) {
			throw new UdfException("ERROR--Cannot Get the Udf Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new UdfException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new UdfException("ERROR--Cannot Get the Udf Controller.");
		}
	}
	
	
	/**
	 * @return List of all Udf
	 */
	
	public SearchResult getAllActualUdf()throws UdfException,TrxParameterException,TransactionException {
		try{
			return getUdfBusManager().getAllUdf( );
		}catch (Exception e) {
			throw new UdfException("ERROR- Cannot retrive list from database.");
			
		}
    }
	
	/**
	 * @return Udf TRX value according to trxId .
	 */

	public IUdfTrxValue getUdfByTrxID(String aTrxID)
			throws UdfException, TransactionException, CommandProcessingException {
		IUdfTrxValue trxValue = new OBUdfTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_UDF);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_UDF_ID);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Checker Approve Udf according to criteria .
	 */

	public IUdfTrxValue checkerApproveUdf(ITrxContext anITrxContext,
			IUdfTrxValue anIUdfTrxValue)
			throws UdfException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new UdfException("The ITrxContext is null!!!");
		}
		if (anIUdfTrxValue == null) {
			throw new UdfException("The IUdfTrxValue to be updated is null!!!");
		}
		anIUdfTrxValue = formulateTrxValue(anITrxContext, anIUdfTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UDF);
		return operate(anIUdfTrxValue, param);
	}
	
//	public boolean isProductCodeUnique(String ProductCode) {
//		return getUdfBusManager().isProductCodeUnique(ProductCode);
//	}
	
	/**
	 * @return Maker Update Udf
	 */

	public IUdfTrxValue makerUpdateSaveUpdateUdf(ITrxContext anITrxContext,
			IUdfTrxValue anICCUdfTrxValue, IUdf anICCUdf)
			throws UdfException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new UdfException("The ITrxContext is null!!!");
		}
		if (anICCUdf == null) {
			throw new UdfException("The ICCUdf to be updated is null !!!");
		}
		IUdfTrxValue trxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue,
				anICCUdf);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_UDF);
		return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Save Udf
	 */

	public IUdfTrxValue makerSaveUdf(ITrxContext anITrxContext,
			IUdf anICCUdf)
			throws UdfException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new UdfException("The ITrxContext is null!!!");
		}
		if (anICCUdf == null) {
			throw new UdfException("The ICCUdf to be updated is null !!!");
		}

		IUdfTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCUdf);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UDF);
		return operate(trxValue, param);
	}
	
	//added by santosh
	 /**
	 * @return  Product Master TRX value  .
	 */
	public IUdfTrxValue getUdfTrxValue(long id) throws UdfException, TrxParameterException, TransactionException {
		if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new FCCBranchException("Invalid udf Id");
       }
		IUdfTrxValue trxValue = new OBUdfTrxValue();
       trxValue.setReferenceID(String.valueOf(id));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_UDF);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_UDF);
       return operate(trxValue, param);
	}
	
	/**
	 * @return Maker Update Udf
	 */

	public IUdfTrxValue makerUpdateUdf(OBTrxContext anITrxContext,
			IUdfTrxValue anICCUdfTrxValue, OBUdf anICCUdf)
			throws UdfException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new UdfException("The ITrxContext is null!!!");
		}
		if (anICCUdf == null) {
			throw new UdfException("The ICCUdf to be updated is null !!!");
		}
		IUdfTrxValue trxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue,anICCUdf);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_UDF);
		return operate(trxValue, param);
	}
	
	 /**
	 * @return Checker Reject  FCCBranch according to criteria .
	 */
	
	
	public IUdfTrxValue checkerRejectUdf(ITrxContext anITrxContext,
			IUdfTrxValue anICCUdfTrxValue)
			throws UdfException, TrxParameterException, TransactionException {
		 if (anITrxContext == null) {
	            throw new UdfException("The ITrxContext is null!!!");
	     }
	     if (anICCUdfTrxValue == null) {
	    	 throw new UdfException("The IUdfTrxValue to be updated is null!!!");
	     }
	     anICCUdfTrxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UDF);
	     return operate(anICCUdfTrxValue, param);
	}
	
	 /**
		 * @return Maker Edit FCCBranch
		 */
		public IUdfTrxValue makerEditRejectedUdf(
				ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue, IUdf anIUdf)
						throws UdfException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new UdfException("The ITrxContext is null!!!");
	        }
	        if (anICCUdfTrxValue == null) {
	            throw new UdfException("The IUdfTrxValue to be updated is null!!!");
	        }
	        if (anIUdf == null) {
	            throw new UdfException("The IUdf to be updated is null !!!");
	        }
	        anICCUdfTrxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue, anIUdf);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UDF);
	        return operate(anICCUdfTrxValue, param);
		}
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IUdfTrxValue makerCloseRejectedUdf(
				ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue)
				throws UdfException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCUdfTrxValue == null) {
	            throw new FCCBranchException("The ICCUdfTrxValue to be updated is null!!!");
	        }
	        anICCUdfTrxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UDF);
	        return operate(anICCUdfTrxValue, param);
		}	
		/**
		 * @return Maker Update Draft for create Udf
		 */

		public IUdfTrxValue makerUpdateSaveCreateUdf(ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue, IUdf anICCUdf)
				throws UdfException, TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new UdfException("The ITrxContext is null!!!");
			}
			if (anICCUdf == null) {
				throw new UdfException("The ICCUdf to be updated is null !!!");
			}
			IUdfTrxValue trxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue,anICCUdf);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_UDF);
			return operate(trxValue, param);
		}
		
		 /**
		 * @return Maker Close FCCBranch.
		 */
		
		public IUdfTrxValue makerCloseDraftUdf(
				ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue)
				throws UdfException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new FCCBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCUdfTrxValue == null) {
	            throw new FCCBranchException("The ICCUdfTrxValue to be updated is null!!!");
	        }
	        anICCUdfTrxValue = formulateTrxValue(anITrxContext, anICCUdfTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_UDF);
	        return operate(anICCUdfTrxValue, param);
		}
		
		
		public IUdfTrxValue makerDeleteUdf(ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue,
				IUdf anICCUdf) throws UdfException,
				TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new UdfException("The ITrxContext is null!!!");
			}
			if (anICCUdf == null) {
				throw new UdfException(
						"The ICCPropertyIdx to be updated is null !!!");
			}
			IUdfTrxValue trxValue = formulateTrxValue(anITrxContext,
					anICCUdfTrxValue, anICCUdf);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_DELETE_UDF);
			return operate(trxValue, param);
		}

		public IUdfTrxValue makerActivateUdf(
				ITrxContext anITrxContext,
				IUdfTrxValue anICCUdfTrxValue,
				IUdf anICCUdf) throws UdfException,
				TrxParameterException, TransactionException {
			if (anITrxContext == null) {
				throw new UdfException("The ITrxContext is null!!!");
			}
			if (anICCUdf == null) {
				throw new UdfException(
						"The ICCPropertyIdx to be updated is null !!!");
			}
			IUdfTrxValue trxValue = formulateTrxValue(anITrxContext,
					anICCUdfTrxValue, anICCUdf);
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_UDF);
			return operate(trxValue, param);
		}
		
		/**
		 * @return List of all Udf
		 */
		
//		public SearchResult getAllFilteredActualUdf(String code,String name)throws UdfException,TrxParameterException,TransactionException {
//			try{
//				return getUdfBusManager().getAllFilteredUdf(code,name);
//			}catch (Exception e) {
//				throw new UdfException("ERROR- Cannot retrive list from database.");
//			}
//	    }

}
