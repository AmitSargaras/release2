package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.ComponentException;

public class AbstractComponentTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	 	private IComponentBusManager componentBusManager;

	    private IComponentBusManager stagingComponentBusManager;

	    private IComponentBusManager stagingComponentFileMapperIdBusManager;
	    
	    private IComponentBusManager componentFileMapperIdBusManager;

	   
	
		public IComponentBusManager getComponentBusManager() {
			return componentBusManager;
		}
		public void setComponentBusManager(IComponentBusManager componentBusManager) {
			this.componentBusManager = componentBusManager;
		}
		public IComponentBusManager getStagingComponentBusManager() {
			return stagingComponentBusManager;
		}
		public void setStagingComponentBusManager(
				IComponentBusManager stagingComponentBusManager) {
			this.stagingComponentBusManager = stagingComponentBusManager;
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
		protected IComponentTrxValue prepareTrxValue(IComponentTrxValue componentTrxValue)throws TrxOperationException {
	        if (componentTrxValue != null) {
	            IComponent actual = componentTrxValue.getComponent();
	            IComponent staging = componentTrxValue.getStagingComponent();
	            if (actual != null) {
	            	componentTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            } else {
	            	componentTrxValue.setReferenceID(null);
	            }
	            if (staging != null) {
	            	componentTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
	            } else {
	            	componentTrxValue.setStagingReferenceID(null);
	            }
	            return componentTrxValue;
	        }
	        else{
	        	throw new  TrxOperationException("ERROR-- component is null");
	        }
	    }
		/**
		 * 
		 * @param holidayTrxValue
		 * @return IHolidayTrxValue
		 * @throws TrxOperationException
		 */

	    protected IComponentTrxValue updateComponentTrx(IComponentTrxValue componentTrxValue) throws TrxOperationException {
	    	if(componentTrxValue!=null){
	    	try {
	        	componentTrxValue = prepareTrxValue(componentTrxValue);
	            ICMSTrxValue tempValue = super.updateTransaction(componentTrxValue);
	            OBComponentTrxValue newValue = new OBComponentTrxValue(tempValue);
	            newValue.setComponent(componentTrxValue.getComponent());
	            newValue.setStagingComponent(componentTrxValue.getStagingComponent());
	            return newValue;
	        }
	        
	        catch (ComponentException ex) {
	            throw new ComponentException("General Exception: in update component  " );
	        }
	    	}else{
	        	throw new ComponentException("Error : Error  while preparing result component in abstract trx operation");
	        }
	    }
	    /**
	     * 
	     * @param componentTrxValue
	     * @return IHolidayTrxValue
	     * @throws TrxOperationException
	     */

	    protected IComponentTrxValue createStagingComponent(IComponentTrxValue componentTrxValue) throws TrxOperationException {
	    	if(componentTrxValue!=null){
	    	try {
	            IComponent component = getStagingComponentBusManager().createComponent(componentTrxValue.getStagingComponent());
	            componentTrxValue.setStagingComponent(component);
	            componentTrxValue.setStagingReferenceID(String.valueOf(component.getId()));
	            return componentTrxValue;
	        }
	        catch (ComponentException e) {
	            throw new ComponentException("Error : Error  while creating component in abstract trx operation");
	        }catch (Exception ex) {
	            throw new ComponentException("Error : Error  while creating component in abstract trx operation");
	        }
	    	}else{
	        	throw new ComponentException("Error : Error  while preparing result component in abstract trx operation");
	        }
	    }
	    /**
	     * 
	     * @param anITrxValue
	     * @return IComponentTrxValue
	     * @throws TrxOperationException
	     */

	    protected IComponentTrxValue getComponentTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
	    	if(anITrxValue!=null){
	    	try {
	            return (IComponentTrxValue) anITrxValue;
	        }
	        catch (ComponentException e) {
	            throw new ComponentException("The ITrxValue is not of type OBCComponentTrxValue: ");
	        }
	        catch (ClassCastException ex) {
	            throw new ComponentException("The ITrxValue is not of type OBCComponentTrxValue: " + ex.toString());
	        }
	    	}else{
	        	throw new ComponentException("Error : Error  while preparing result component in abstract trx operation");
	        }
	    }
	    
	    /**
	     * 
	     * @param value
	     * @return ITrxResult
	     */

	    protected ITrxResult prepareResult(IComponentTrxValue value) {
	    	if(value!=null){
	        OBCMSTrxResult result = new OBCMSTrxResult();
	        result.setTrxValue(value);
	        return result;
	        }else{
	        	throw new ComponentException("Error : Error  while preparing result component in abstract trx operation");
	        }
	    }
	    
	    /**
	     * 
	     * @param componentTrxValue
	     * @return IComponentTrxValue
	     * @throws TrxOperationException
	     */
	    //------------------------------------File Insert---------------------------------------------
	    
	    protected IComponentTrxValue prepareInsertTrxValue(IComponentTrxValue componentTrxValue)throws TrxOperationException {
	        if (componentTrxValue != null) {
	            IFileMapperId actual = componentTrxValue.getFileMapperID();
	            IFileMapperId staging = componentTrxValue.getStagingFileMapperID();
	            if (actual != null) {
	            	componentTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            } else {
	            	componentTrxValue.setReferenceID(null);
	            }
	            if (staging != null) {
	            	componentTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
	            } else {
	            	componentTrxValue.setStagingReferenceID(null);
	            }
	            return componentTrxValue;
	        }
	        else{
	        	throw new  TrxOperationException("ERROR-- Component is null");
	        }
	    }
		
	    
	    
	    protected IComponentTrxValue updateMasterInsertTrx(IComponentTrxValue componentTrxValue) throws TrxOperationException {
	        try {
	        	componentTrxValue = prepareInsertTrxValue(componentTrxValue);
	            ICMSTrxValue tempValue = super.updateTransaction(componentTrxValue);
	            OBComponentTrxValue newValue = new OBComponentTrxValue(tempValue);
	            newValue.setFileMapperID(componentTrxValue.getFileMapperID());
	            newValue.setStagingFileMapperID(componentTrxValue.getStagingFileMapperID());
	            return newValue;
	        }
	        
	        catch (TrxOperationException ex) {
	            throw new TrxOperationException("General Exception: " + ex.toString());
	        }
	    }
	    
	    
	    
	    
	    protected IComponentTrxValue createStagingFileId(IComponentTrxValue componentTrxValue) throws TrxOperationException {
	        try {
	        	IFileMapperId fileMapperID = getStagingComponentFileMapperIdBusManager().createFileId(componentTrxValue.getStagingFileMapperID());
	        	componentTrxValue.setStagingFileMapperID(fileMapperID);
	        	componentTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
	            return componentTrxValue;
	        }
	        catch (Exception ex) {
	            throw new TrxOperationException(ex);
	        }
	    }
	    

	    protected IComponentTrxValue insertActualComponent(IComponentTrxValue componentTrxValue) throws TrxOperationException {
	        try {
	            IComponent component = getStagingComponentFileMapperIdBusManager().insertComponent(componentTrxValue.getStagingComponent());
	            componentTrxValue.setComponent(component);
	            componentTrxValue.setReferenceID(String.valueOf(component.getId()));
	            return componentTrxValue;
	        }
	        catch (Exception ex) {
	            throw new TrxOperationException(ex);
	        }
	    }
		
		public String getOperationName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	    
}
