package com.integrosys.cms.app.baselmaster.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.baselmaster.bus.BaselMasterException;
import com.integrosys.cms.app.baselmaster.bus.IBaselBusManager;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.baselmaster.trx.OBBaselMasterTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class BaselProxyManagerImpl implements IBaselProxyManager{
	
	private IBaselBusManager baselBusManager;
	
	private ITrxControllerFactory trxControllerFactory;
	
	private IBaselBusManager stagingBaselBusManager;
    
    private IBaselBusManager stagingBaselFileMapperIdBusManager;
	
	private IBaselBusManager baselFileMapperIdBusManager;
	
	public IBaselBusManager getStagingBaselBusManager() {
		return stagingBaselBusManager;
	}


	public void setStagingBaselBusManager(IBaselBusManager stagingBaselBusManager) {
		this.stagingBaselBusManager = stagingBaselBusManager;
	}


	public IBaselBusManager getStagingBaselFileMapperIdBusManager() {
		return stagingBaselFileMapperIdBusManager;
	}


	public void setStagingBaselFileMapperIdBusManager(
			IBaselBusManager stagingBaselFileMapperIdBusManager) {
		this.stagingBaselFileMapperIdBusManager = stagingBaselFileMapperIdBusManager;
	}


	public IBaselBusManager getBaselFileMapperIdBusManager() {
		return baselFileMapperIdBusManager;
	}


	public void setBaselFileMapperIdBusManager(
			IBaselBusManager baselFileMapperIdBusManager) {
		this.baselFileMapperIdBusManager = baselFileMapperIdBusManager;
	}


	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}


	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}


	public IBaselBusManager getBaselBusManager() {
		return baselBusManager;
	}


	public void setBaselBusManager(IBaselBusManager baselBusManager) {
		this.baselBusManager = baselBusManager;
	}


	public SearchResult getAllActualBasel() throws BaselMasterException,TrxParameterException, TransactionException {
		try{


			return getBaselBusManager().getAllActualBasel();
		}catch (Exception e) {
			throw new BaselMasterException("ERROR- Cannot retrive list from database.");
		}
		}
	
	public SearchResult getAllActualCommon() throws BaselMasterException,TrxParameterException, TransactionException {
		try{


			return getBaselBusManager().getAllActualCommon();
		}catch (Exception e) {
			throw new BaselMasterException("ERROR- Cannot retrive list from database.");
		}
		}
	
	public IBaselMasterTrxValue makerCreateBasel(ITrxContext anITrxContext,IBaselMaster anICCBasel) throws BaselMasterException,
	TrxParameterException, TransactionException {
			if (anITrxContext == null) {
		        throw new ComponentException("The ITrxContext is null!!!");
		    }
		    if (anICCBasel == null) {
		        throw new BaselMasterException("The anICCBasel to be updated is null !!!");
		    }
		
		    IBaselMasterTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCBasel);
		    trxValue.setFromState("PENDING_CREATE");
		    OBCMSTrxParameter param = new OBCMSTrxParameter();
		    param.setAction(ICMSConstant.ACTION_MAKER_CREATE_BASEL);
		    return operate(trxValue, param);
}
	private IBaselMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IBaselMaster anIBasel) {
		IBaselMasterTrxValue ccBaselTrxValue = null;
        if (anICMSTrxValue != null) {
        	ccBaselTrxValue = new OBBaselMasterTrxValue(anICMSTrxValue);
        } else {
        	ccBaselTrxValue = new OBBaselMasterTrxValue();
        }
        ccBaselTrxValue = formulateTrxValue(anITrxContext, (IBaselMasterTrxValue) ccBaselTrxValue);
        ccBaselTrxValue.setStagingBaselMaster(anIBasel);
        return ccBaselTrxValue;
    }
	
	private IBaselMasterTrxValue formulateTrxValue(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue) {
		anIBaselTrxValue.setTrxContext(anITrxContext);
		anIBaselTrxValue.setTransactionType(ICMSConstant.INSTANCE_BASEL);
        return anIBaselTrxValue;
    }
	
	private IBaselMasterTrxValue operate(IBaselMasterTrxValue anIBaselTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	throws BaselMasterException,TrxParameterException,TransactionException {
        ICMSTrxResult result = operateForResult(anIBaselTrxValue, anOBCMSTrxParameter);
        return (IBaselMasterTrxValue) result.getTrxValue();
    }
 
 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
 throws BaselMasterException,TrxParameterException,TransactionException {
	 try {
		 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
		 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

		 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
		 return (ICMSTrxResult) result;
	 }
	 catch (BaselMasterException ex) {
		 throw new BaselMasterException("ERROR--Cannot Get the Basel Controller.");
	 }
	 catch (Exception ex) {
		 throw new BaselMasterException("ERROR--Cannot Get the Basel Controller.");
	 }
 }
 
	 public IBaselMasterTrxValue getBaselByTrxID(String aTrxID)throws BaselMasterException, TransactionException,
			CommandProcessingException {
		 IBaselMasterTrxValue trxValue = new OBBaselMasterTrxValue();
		trxValue.setTransactionID(String.valueOf(aTrxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_BASEL);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_BASEL_ID);
		return operate(trxValue, param);
		}
	 
	 public IBaselMasterTrxValue checkerApproveBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue)
		throws BaselMasterException, TrxParameterException,TransactionException {
				if (anITrxContext == null) {
			     throw new ComponentException("The ITrxContext is null!!!");
			 }
			 if (anIBaselTrxValue == null) {
			     throw new ComponentException
			             ("The IComponentTrxValue to be updated is null!!!");
			 }
			 anIBaselTrxValue = formulateTrxValue(anITrxContext, anIBaselTrxValue);
			 OBCMSTrxParameter param = new OBCMSTrxParameter();
			 param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_BASEL);
			 return operate(anIBaselTrxValue, param);
	 }
	 
	 public IBaselMasterTrxValue getBaselTrxValue(long aBaselId)throws BaselMasterException, TrxParameterException,
					TransactionException {
				if (aBaselId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			     throw new BaselMasterException("Invalid BaselId");
			 }
				IBaselMasterTrxValue trxValue = new OBBaselMasterTrxValue();
			 trxValue.setReferenceID(String.valueOf(aBaselId));
			 trxValue.setTransactionType(ICMSConstant.INSTANCE_BASEL);
			 OBCMSTrxParameter param = new OBCMSTrxParameter();
			 param.setAction(ICMSConstant.ACTION_READ_BASEL);
			 return operate(trxValue, param);
	 }
	 
	 public IBaselMasterTrxValue checkerRejectBasel(ITrxContext anITrxContext,IBaselMasterTrxValue anIBaselTrxValue) throws BaselMasterException,
					TrxParameterException, TransactionException {
				if (anITrxContext == null) {
			     throw new BaselMasterException("The ITrxContext is null!!!");
			 }
			 if (anIBaselTrxValue == null) {
			     throw new BaselMasterException("The IComponentTrxValue to be updated is null!!!");
			 }
			 anIBaselTrxValue = formulateTrxValue(anITrxContext, anIBaselTrxValue);
			 OBCMSTrxParameter param = new OBCMSTrxParameter();
			 param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_BASEL);
			 return operate(anIBaselTrxValue, param);
	 }
	 
	 public IBaselMasterTrxValue makerEditRejectedBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue,
			 IBaselMaster anBasel) throws BaselMasterException,TrxParameterException, TransactionException {
			if (anITrxContext == null) {
	            throw new ComponentException("The ITrxContext is null!!!");
	        }
	        if (anIBaselTrxValue == null) {
	            throw new BaselMasterException("The IComponentTrxValue to be updated is null!!!");
	        }
	        if (anBasel == null) {
	            throw new ComponentException("The IComponent to be updated is null !!!");
	        }
	        anIBaselTrxValue = formulateTrxValue(anITrxContext, anIBaselTrxValue, anBasel);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_BASEL);
	        return operate(anIBaselTrxValue, param);
		}
	 
	 public IBaselMasterTrxValue makerCloseRejectedBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anIBaselTrxValue)
				throws BaselMasterException, TrxParameterException,	TransactionException {
			if (anITrxContext == null) {
	            throw new BaselMasterException("The ITrxContext is null!!!");
	        }
	        if (anIBaselTrxValue == null) {
	            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
	        }
	        anIBaselTrxValue = formulateTrxValue(anITrxContext, anIBaselTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BASEL);
	        return operate(anIBaselTrxValue, param);
		}
	 
	 public IBaselMasterTrxValue makerUpdateBasel(ITrxContext anITrxContext, IBaselMasterTrxValue anICCBaselTrxValue, IBaselMaster anICCBasel)
				throws BaselMasterException, TrxParameterException,
				TransactionException {
			 if (anITrxContext == null) {
		            throw new BaselMasterException("The ITrxContext is null!!!");
		        }
		        if (anICCBasel == null) {
		            throw new BaselMasterException("The anICCBasel to be updated is null !!!");
		        }
		        IBaselMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCBaselTrxValue, anICCBasel);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_BASEL);
		        return operate(trxValue, param);
		}
	 
	 public IBaselMasterTrxValue makerDeleteBasel(ITrxContext anITrxContext,IBaselMasterTrxValue anICCBaselTrxValue, IBaselMaster anICCBasel)
		throws BaselMasterException, TrxParameterException,TransactionException {
				if (anITrxContext == null) {
			     throw new BaselMasterException("The ITrxContext is null!!!");
			 }
			 if (anICCBasel == null) {
			     throw new ComponentException("The ICCPropertyIdx to be updated is null !!!");
			 }
			 IBaselMasterTrxValue trxValue = formulateTrxValue(anITrxContext, anICCBaselTrxValue, anICCBasel);
			 OBCMSTrxParameter param = new OBCMSTrxParameter();
			 param.setAction(ICMSConstant.ACTION_MAKER_DELETE_BASEL);
			 return operate(trxValue, param);
}
	 
	 public SearchResult getSearchBasel(String baselName)
		throws BaselMasterException {
	return getBaselBusManager().getSearchBaselList(baselName);
}
	

}
