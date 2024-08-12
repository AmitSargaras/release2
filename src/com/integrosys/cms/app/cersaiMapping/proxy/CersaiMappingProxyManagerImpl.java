package com.integrosys.cms.app.cersaiMapping.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingBusManager;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CersaiMappingProxyManagerImpl implements ICersaiMappingProxyManager{
	
	private ICersaiMappingBusManager cersaiMappingBusManager;

	private ICersaiMappingBusManager stagingCersaiMappingBusManager;
	
	private ITrxControllerFactory trxControllerFactory;

	

	public ICersaiMappingBusManager getCersaiMappingBusManager() {
		return cersaiMappingBusManager;
	}

	public void setCersaiMappingBusManager(ICersaiMappingBusManager cersaiMappingBusManager) {
		this.cersaiMappingBusManager = cersaiMappingBusManager;
	}

	public ICersaiMappingBusManager getStagingCersaiMappingBusManager() {
		return stagingCersaiMappingBusManager;
	}

	public void setStagingCersaiMappingBusManager(ICersaiMappingBusManager stagingCersaiMappingBusManager) {
		this.stagingCersaiMappingBusManager = stagingCersaiMappingBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	@Override
	public ICersaiMappingTrxValue makerSaveCersaiMapping(ITrxContext ctx, ICersaiMapping cersaiMapping) throws CersaiMappingException, TrxParameterException, TransactionException {
		if (ctx == null) {
			throw new CersaiMappingException("The ITrxContext is null!!!");
		}
		if (cersaiMapping == null) {
			throw new CersaiMappingException("The CersaiMapping to be updated is null !!!");
		}

		ICersaiMappingTrxValue trxValue = formulateTrxValue(ctx, null, cersaiMapping);
		trxValue.setFromState("ND");
		trxValue.setStatus("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CERSAI_MAPPING);
		return operate(trxValue, param);
	}
	
	private ICersaiMappingTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICersaiMapping cersaiMapping) {
		ICersaiMappingTrxValue trxValue = null;
		if (anICMSTrxValue != null) {
			trxValue = new OBCersaiMappingTrxValue(anICMSTrxValue);
		} else {
			trxValue = new OBCersaiMappingTrxValue();
		}
		trxValue = formulateTrxValue(anITrxContext,
				(ICersaiMappingTrxValue) trxValue);
		trxValue.setStagingCersaiMapping(cersaiMapping);	
		return trxValue;
	}
	
	private ICersaiMappingTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICersaiMappingTrxValue anICersaiMappingTrxValue) {
		anICersaiMappingTrxValue.setTrxContext(anITrxContext);
		anICersaiMappingTrxValue.setTransactionType(ICMSConstant.INSTANCE_CERSAI_MAPPING);
		return anICersaiMappingTrxValue;
	}
	
	private ICersaiMappingTrxValue operate(ICersaiMappingTrxValue anICersaiMappingTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anICersaiMappingTrxValue, anOBCMSTrxParameter);
		return (ICersaiMappingTrxValue) result.getTrxValue();
	}
	
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		} catch (CersaiMappingException ex) {
			throw new CersaiMappingException("ERROR--Cannot Get the CersaiMapping Controller.");
		} catch (TrxParameterException te) {
			te.printStackTrace();
			throw new CersaiMappingException("ERROR--Cannot update already deleted record");
		} catch (Exception ex) {
			throw new CersaiMappingException("ERROR--Cannot Get the CersaiMapping Controller.");
		}
	}

	@Override
	public SearchResult getAllActualCersaiMapping()
			throws CersaiMappingException, TrxParameterException, TransactionException {
		try{
			return getCersaiMappingBusManager().getAllCersaiMapping();
		}catch (Exception e) {
			throw new CersaiMappingException("ERROR- Cannot retrive list from database.");
			
		}
	}
	
	public SearchResult getAllActualCersaiMapping(String mastername)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		try{
			return getCersaiMappingBusManager().getAllCersaiMapping(mastername);
		}catch (Exception e) {
			throw new CersaiMappingException("ERROR- Cannot retrive list from database.");
		}
	}

	@Override
	public ICersaiMappingTrxValue makerCreateCersaiMapping(ITrxContext anITrxContext, ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CersaiMappingException("The ITrxContext is null!!!");
		}
		if (anICCCersaiMapping == null) {
			throw new CersaiMappingException("The ICCCersaiMapping to be updated is null !!!");
		}

		ICersaiMappingTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCersaiMapping);
		trxValue.setFromState("PENDING_CREATE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CERSAI_MAPPING);
		return operate(trxValue, param);
	}

	@Override
	public ICersaiMappingTrxValue getCersaiMappingByTrxID(String aTrxID)
			throws CersaiMappingException, TransactionException, CommandProcessingException {
		ICersaiMappingTrxValue trxValue = new OBCersaiMappingTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_CERSAI_MAPPING);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CERSAI_MAPPING_ID);
		return operate(trxValue, param);
	}

	@Override
	public ICersaiMappingTrxValue checkerApproveCersaiMapping(ITrxContext anITrxContext,
			ICersaiMappingTrxValue anICersaiMappingTrxValue)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new CersaiMappingException("The ITrxContext is null!!!");
		}
		if (anICersaiMappingTrxValue == null) {
			throw new CersaiMappingException("The ICersaiMappingTrxValue to be updated is null!!!");
		}
		anICersaiMappingTrxValue = formulateTrxValue(anITrxContext, anICersaiMappingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CERSAI_MAPPING);
		return operate(anICersaiMappingTrxValue, param);
	}

	@Override
	public boolean isCersaiCodeUnique(String cersaiCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ICersaiMappingTrxValue makerUpdateSaveUpdateCersaiMapping(ITrxContext anITrxContext,
			ICersaiMappingTrxValue anICCCersaiMappingTrxValue, ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	@Override
	public ICersaiMappingTrxValue getCersaiMappingTrxValue(long cersaiCode)
			throws CersaiMappingException, TransactionException, CommandProcessingException {
			ICersaiMappingTrxValue trxValue = new OBCersaiMappingTrxValue();
	       trxValue.setReferenceID(String.valueOf(cersaiCode));
	       trxValue.setTransactionType(ICMSConstant.INSTANCE_CERSAI_MAPPING);
	       OBCMSTrxParameter param = new OBCMSTrxParameter();
	       param.setAction(ICMSConstant.ACTION_READ_CERSAI_MAPPING);
	       return operate(trxValue, param);
	}

	@Override
	public ICersaiMappingTrxValue makerUpdateCersaiMapping(OBTrxContext ctx, ICersaiMappingTrxValue trxValueIn,
			OBCersaiMapping cersaiMapping) throws CersaiMappingException, TrxParameterException, TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICersaiMappingTrxValue checkerRejectCersaiMapping(ITrxContext anITrxContext, ICersaiMappingTrxValue anICCCersaiMappingTrxValue)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new CersaiMappingException("The ITrxContext is null!!!");
     }
     if (anICCCersaiMappingTrxValue == null) {
    	 throw new CersaiMappingException("The ICersaiMappingTrxValue to be updated is null!!!");
     }
     anICCCersaiMappingTrxValue = formulateTrxValue(anITrxContext, anICCCersaiMappingTrxValue);
     OBCMSTrxParameter param = new OBCMSTrxParameter();
     param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CERSAI_MAPPING);
     return operate(anICCCersaiMappingTrxValue, param);
	}

	@Override
	public ICersaiMappingTrxValue makerEditRejectedCersaiMapping(ITrxContext anITrxContext, ICersaiMappingTrxValue anICCCersaiMappingTrxValue,
			ICersaiMapping anICersaiMapping)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new CersaiMappingException("The ITrxContext is null!!!");
        }
        if (anICCCersaiMappingTrxValue == null) {
            throw new CersaiMappingException("The ICersaiMappingTrxValue to be updated is null!!!");
        }
        if (anICersaiMapping == null) {
            throw new CersaiMappingException("The ICersaiMapping to be updated is null !!!");
        }
        anICCCersaiMappingTrxValue = formulateTrxValue(anITrxContext, anICCCersaiMappingTrxValue, anICersaiMapping);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CERSAI_MAPPING);
        return operate(anICCCersaiMappingTrxValue, param);
	}

	@Override
	public ICersaiMappingTrxValue makerCloseRejectedCersaiMapping(ITrxContext anITrxContext, ICersaiMappingTrxValue anICCCersaiMappingTrxValue)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new FCCBranchException("The ITrxContext is null!!!");
        }
        if (anICCCersaiMappingTrxValue == null) {
            throw new FCCBranchException("The ICCCersaiMappingTrxValue to be updated is null!!!");
        }
        anICCCersaiMappingTrxValue = formulateTrxValue(anITrxContext, anICCCersaiMappingTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CERSAI_MAPPING);
        return operate(anICCCersaiMappingTrxValue, param);
	}	
	

	@Override
	public ICersaiMappingTrxValue makerUpdateSaveCreateCersaiMapping(ITrxContext ctx, ICersaiMappingTrxValue trxValueIn,
			ICersaiMapping anICCCersaiMapping)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult getAllFilteredActualCersaiMapping(String code, String name)
			throws CersaiMappingException, TrxParameterException, TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public ICersaiMappingTrxValue makerSaveCersaiMapping(ITrxContext anITrxContext, ICersaiMapping anICCCersaiMapping)
//			throws CersaiMappingException, TrxParameterException, TransactionException {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
