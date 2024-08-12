package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.ITatMasterBusManager;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public class AbstractTatMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{
	


 	private ITatMasterBusManager tatMasterBusManager;

    private ITatMasterBusManager stagingtatMasterBusManager;

    private ITatMasterBusManager stagingtatFileMapperIdBusManager;
    
    private ITatMasterBusManager tatFileMapperIdBusManager;
    
    


	public ITatMasterBusManager getTatMasterBusManager() {
		return tatMasterBusManager;
	}
	public void setTatMasterBusManager(ITatMasterBusManager tatMasterBusManager) {
		this.tatMasterBusManager = tatMasterBusManager;
	}
	
	public ITatMasterBusManager getStagingtatMasterBusManager() {
		return stagingtatMasterBusManager;
	}
	public void setStagingtatMasterBusManager(
			ITatMasterBusManager stagingtatMasterBusManager) {
		this.stagingtatMasterBusManager = stagingtatMasterBusManager;
	}
	
	
	public ITatMasterBusManager getStagingtatFileMapperIdBusManager() {
		return stagingtatFileMapperIdBusManager;
	}
	public void setStagingtatFileMapperIdBusManager(
			ITatMasterBusManager stagingtatFileMapperIdBusManager) {
		this.stagingtatFileMapperIdBusManager = stagingtatFileMapperIdBusManager;
	}
	public ITatMasterBusManager getTatFileMapperIdBusManager() {
		return tatFileMapperIdBusManager;
	}
	public void setTatFileMapperIdBusManager(
			ITatMasterBusManager tatFileMapperIdBusManager) {
		this.tatFileMapperIdBusManager = tatFileMapperIdBusManager;
	}
	
	protected ITatMasterTrxValue prepareTrxValue(ITatMasterTrxValue tatMasterTrxValue)throws TrxOperationException {
        if (tatMasterTrxValue != null) {
        	INewTatMaster actual = tatMasterTrxValue.getTatMaster();
        	INewTatMaster staging = tatMasterTrxValue.getStagingtatMaster();
            if (actual != null) {
            	tatMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	tatMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	tatMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	tatMasterTrxValue.setStagingReferenceID(null);
            }
            return tatMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- tat master is null");
        }
    }
	/**
	 * 
	 * @param holidayTrxValue
	 * @return IHolidayTrxValue
	 * @throws TrxOperationException
	 */

    protected ITatMasterTrxValue updateTatMasterTrx(ITatMasterTrxValue tatMasterTrxValue) throws TrxOperationException {
    	if(tatMasterTrxValue!=null){
    	try {
    		tatMasterTrxValue = prepareTrxValue(tatMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(tatMasterTrxValue);
            OBTatMasterTrxValue newValue = new OBTatMasterTrxValue(tempValue);
            newValue.setTatMaster(tatMasterTrxValue.getTatMaster());
            newValue.setStagingtatMaster(tatMasterTrxValue.getStagingtatMaster());
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

   
    /**
     * 
     * @param anITrxValue
     * @return IComponentTrxValue
     * @throws TrxOperationException
     */

    protected ITatMasterTrxValue getTatMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (ITatMasterTrxValue) anITrxValue;
        }
        catch (TatMasterException e) {
            throw new ComponentException("The ITrxValue is not of type OBTatMasterTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new ComponentException("The ITrxValue is not of type OBTatMasterTrxValue: " + ex.toString());
        }
    	}else{
        	throw new ComponentException("Error : Error  while preparing result tat master in abstract trx operation");
        }
    }
    
    protected ITatMasterTrxValue createStagingTatMaster(ITatMasterTrxValue tatMasterTrxValue) throws TrxOperationException {
    	if(tatMasterTrxValue!=null){
    	try {
    		INewTatMaster tatMaster = getStagingtatMasterBusManager().createTatMaster(tatMasterTrxValue.getStagingtatMaster());
            tatMasterTrxValue.setStagingtatMaster(tatMaster);
            tatMasterTrxValue.setStagingReferenceID(String.valueOf(tatMaster.getId()));
            return tatMasterTrxValue;
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
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ITatMasterTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new ComponentException("Error : Error  while preparing result Tat Master in abstract trx operation");
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
    
    
    
    
   
    

    
	
	public String getOperationName() {
		// TODO Auto-generated method stub
		return null;
	}
	
    


}
