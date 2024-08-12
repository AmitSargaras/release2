package com.integrosys.cms.app.component.proxy;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.IHolidayBusManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;

public class ComponentProxyManagerImpl implements IComponentProxyManager {
	
	
	private IComponentBusManager componentBusManager;
	private IComponentBusManager stagingComponentBusManager;

    private ITrxControllerFactory trxControllerFactory;
    
    private IComponentBusManager stagingComponentFileMapperIdBusManager;
	
	private IComponentBusManager componentFileMapperIdBusManager;

	
	
	public IComponentBusManager getStagingComponentBusManager() {
		return stagingComponentBusManager;
	}


	public void setStagingComponentBusManager(
			IComponentBusManager stagingComponentBusManager) {
		this.stagingComponentBusManager = stagingComponentBusManager;
	}


	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}


	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}


	public IComponentBusManager getStagingComponentFileMapperIdBusManager() {
		return stagingComponentFileMapperIdBusManager;
	}


	public void setStagingComponentFileMapperIdBusManager(
			IComponentBusManager stagingComponentFileMapperIdBusManager) {
		this.stagingComponentFileMapperIdBusManager = stagingComponentFileMapperIdBusManager;
	}


	public IComponentBusManager getComponentFileMapperIdBusManager() {
		return componentFileMapperIdBusManager;
	}


	public void setComponentFileMapperIdBusManager(
			IComponentBusManager componentFileMapperIdBusManager) {
		this.componentFileMapperIdBusManager = componentFileMapperIdBusManager;
	}


	public IComponentBusManager getComponentBusManager() {
		return componentBusManager;
	}


	public void setComponentBusManager(IComponentBusManager componentBusManager) {
		this.componentBusManager = componentBusManager;
	}

	
	public SearchResult getAllActualComponent() throws ComponentException,TrxParameterException, TransactionException {
		try{


			return getComponentBusManager().getAllComponent();
		}catch (Exception e) {
			throw new ComponentException("ERROR- Cannot retrive list from database.");
		}
		
	}


	
	public IComponentTrxValue makerCreateComponent(ITrxContext anITrxContext,IComponent anICCComponent) throws ComponentException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anICCComponent == null) {
            throw new ComponentException("The ICCComponent to be updated is null !!!");
        }

        IComponentTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCComponent);
        trxValue.setFromState("PENDING_CREATE");
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COMPONENT);
        return operate(trxValue, param);
	}
	
	 private IComponentTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IComponent anIComponent) {
	        IComponentTrxValue ccComponentTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccComponentTrxValue = new OBComponentTrxValue(anICMSTrxValue);
	        } else {
	            ccComponentTrxValue = new OBComponentTrxValue();
	        }
	        ccComponentTrxValue = formulateTrxValue(anITrxContext, (IComponentTrxValue) ccComponentTrxValue);
	        ccComponentTrxValue.setStagingComponent(anIComponent);
	        return ccComponentTrxValue;
	    }
	 
	 private IComponentTrxValue formulateTrxValue(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue) {
	        anIComponentTrxValue.setTrxContext(anITrxContext);
	        anIComponentTrxValue.setTransactionType(ICMSConstant.INSTANCE_COMPONENT);
	        return anIComponentTrxValue;
	    }

	 private IComponentTrxValue operate(IComponentTrxValue anIComponentTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws ComponentException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIComponentTrxValue, anOBCMSTrxParameter);
	        return (IComponentTrxValue) result.getTrxValue();
	    }
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws ComponentException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }
		 catch (ComponentException ex) {
			 throw new ComponentException("ERROR--Cannot Get the Component Controller.");
		 }
		 catch (Exception ex) {
			 throw new ComponentException("ERROR--Cannot Get the Component Controller.");
		 }
	 }


	
	public IComponentTrxValue getComponentTrxValue(long aComponentId)throws ComponentException, TrxParameterException,
			TransactionException {
		if (aComponentId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new ComponentException("Invalid ComponentId");
        }
        IComponentTrxValue trxValue = new OBComponentTrxValue();
        trxValue.setReferenceID(String.valueOf(aComponentId));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_COMPONENT);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_COMPONENT);
        return operate(trxValue, param);
	}


	
	public IComponentTrxValue getComponentByTrxID(String aTrxID)throws ComponentException, TransactionException,
			CommandProcessingException {
		IComponentTrxValue trxValue = new OBComponentTrxValue();
        trxValue.setTransactionID(String.valueOf(aTrxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_COMPONENT);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_COMPONENT_ID);
        return operate(trxValue, param);
	}



	public IComponentTrxValue checkerApproveComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue)
			throws ComponentException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anIComponentTrxValue == null) {
            throw new ComponentException
                    ("The IComponentTrxValue to be updated is null!!!");
        }
        anIComponentTrxValue = formulateTrxValue(anITrxContext, anIComponentTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COMPONENT);
        return operate(anIComponentTrxValue, param);
	}


	
	public IComponentTrxValue checkerRejectComponent(ITrxContext anITrxContext,IComponentTrxValue anIComponentTrxValue) throws ComponentException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anIComponentTrxValue == null) {
            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
        }
        anIComponentTrxValue = formulateTrxValue(anITrxContext, anIComponentTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COMPONENT);
        return operate(anIComponentTrxValue, param);
	}


	
	public IComponentTrxValue makerDeleteComponent(ITrxContext anITrxContext,IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)
			throws ComponentException, TrxParameterException,			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anICCComponent == null) {
            throw new ComponentException("The ICCPropertyIdx to be updated is null !!!");
        }
        IComponentTrxValue trxValue = formulateTrxValue(anITrxContext, anICCComponentTrxValue, anICCComponent);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_COMPONENT);
        return operate(trxValue, param);
	}


	
	public IComponentTrxValue makerEditRejectedComponent(ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue,
			IComponent anComponent) throws ComponentException,TrxParameterException, TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anIComponentTrxValue == null) {
            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
        }
        if (anComponent == null) {
            throw new ComponentException("The IComponent to be updated is null !!!");
        }
        anIComponentTrxValue = formulateTrxValue(anITrxContext, anIComponentTrxValue, anComponent);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_COMPONENT);
        return operate(anIComponentTrxValue, param);
	}


	public IComponentTrxValue makerUpdateComponent(ITrxContext anITrxContext,
			IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)
			throws ComponentException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new ComponentException("The ITrxContext is null!!!");
	        }
	        if (anICCComponent == null) {
	            throw new ComponentException("The ICCComponent to be updated is null !!!");
	        }
	        IComponentTrxValue trxValue = formulateTrxValue(anITrxContext, anICCComponentTrxValue, anICCComponent);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COMPONENT);
	        return operate(trxValue, param);
	}


	public IComponentTrxValue makerUpdateSaveCreateComponent(
			ITrxContext anITrxContext,
			IComponentTrxValue anICCComponentTrxValue, IComponent anICCComponent)
			throws ComponentException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anICCComponent == null) {
            throw new ComponentException("The ICCComponent to be updated is null !!!");
        }
        IComponentTrxValue trxValue = formulateTrxValue(anITrxContext, anICCComponentTrxValue, anICCComponent);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COMPONENT);
        return operate(trxValue, param);
	}


	
	public IComponentTrxValue makerCloseDraftComponent(
			ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue)
			throws ComponentException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anIComponentTrxValue == null) {
            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
        }
        anIComponentTrxValue = formulateTrxValue(anITrxContext, anIComponentTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_COMPONENT);
        return operate(anIComponentTrxValue, param);
	}


	
	public IComponentTrxValue makerCloseRejectedComponent(
			ITrxContext anITrxContext, IComponentTrxValue anIComponentTrxValue)
			throws ComponentException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new ComponentException("The ITrxContext is null!!!");
        }
        if (anIComponentTrxValue == null) {
            throw new ComponentException("The IComponentTrxValue to be updated is null!!!");
        }
        anIComponentTrxValue = formulateTrxValue(anITrxContext, anIComponentTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_COMPONENT);
        return operate(anIComponentTrxValue, param);
	}


	
	public boolean isUniqueCode(String componentName)throws ComponentException, TrxParameterException,
	TransactionException {
		return  getComponentBusManager().isUniqueCode(componentName);
		
	}


	
	public SearchResult getSearchComponentList(String componentName)
			throws ComponentException {
		return getComponentBusManager().getSearchComponentList(componentName);
	}


	
	
	

}
