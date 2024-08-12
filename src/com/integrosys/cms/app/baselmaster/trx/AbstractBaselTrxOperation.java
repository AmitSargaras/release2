package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.baselmaster.bus.BaselMasterException;
import com.integrosys.cms.app.baselmaster.bus.IBaselBusManager;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentBusManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public class AbstractBaselTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{
	
	private IBaselBusManager baselBusManager;
	
	private IBaselBusManager stagingBaselBusManager;
	
	private IBaselBusManager stagingBaselFileMapperIdBusManager;
    
    private IBaselBusManager baselFileMapperIdBusManager;

	public IBaselBusManager getBaselBusManager() {
		return baselBusManager;
	}

	public void setBaselBusManager(IBaselBusManager baselBusManager) {
		this.baselBusManager = baselBusManager;
	}

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
    
    
	protected IBaselMasterTrxValue prepareTrxValue(IBaselMasterTrxValue baselTrxValue)throws TrxOperationException {
        if (baselTrxValue != null) {
        	IBaselMaster actual = baselTrxValue.getBaselMaster();
        	IBaselMaster staging = baselTrxValue.getStagingBaselMaster();
            if (actual != null) {
            	baselTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	baselTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	baselTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	baselTrxValue.setStagingReferenceID(null);
            }
            return baselTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- component is null");
        }
    }
	

    protected IBaselMasterTrxValue updateBaselTrx(IBaselMasterTrxValue baselTrxValue) throws TrxOperationException {
    	if(baselTrxValue!=null){
    	try {
    		baselTrxValue = prepareTrxValue(baselTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(baselTrxValue);
            OBBaselMasterTrxValue newValue = new OBBaselMasterTrxValue(tempValue);
            newValue.setBaselMaster(baselTrxValue.getBaselMaster());
            newValue.setStagingBaselMaster(baselTrxValue.getStagingBaselMaster());
            return newValue;
        }
        
        catch (BaselMasterException ex) {
            throw new BaselMasterException("General Exception: in update component  " );
        }
    	}else{
        	throw new BaselMasterException("Error : Error  while preparing result component in abstract trx operation");
        }
    }
    /**
     * 
     * @param componentTrxValue
     * @return IHolidayTrxValue
     * @throws TrxOperationException
     */

    protected IBaselMasterTrxValue createStagingBasel(IBaselMasterTrxValue baselTrxValue) throws TrxOperationException {
    	if(baselTrxValue!=null){
    	try {
    		IBaselMaster basel = getStagingBaselBusManager().createBasel(baselTrxValue.getStagingBaselMaster());
    		baselTrxValue.setStagingBaselMaster(basel);
    		baselTrxValue.setStagingReferenceID(String.valueOf(basel.getId()));
            return baselTrxValue;
        }
        catch (BaselMasterException e) {
            throw new BaselMasterException("Error : Error  while creating component in abstract trx operation");
        }catch (Exception ex) {
            throw new BaselMasterException("Error : Error  while creating component in abstract trx operation");
        }
    	}else{
        	throw new BaselMasterException("Error : Error  while preparing result component in abstract trx operation");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IComponentTrxValue
     * @throws TrxOperationException
     */

    protected IBaselMasterTrxValue getBaselTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (IBaselMasterTrxValue) anITrxValue;
        }
        catch (BaselMasterException e) {
            throw new BaselMasterException("The ITrxValue is not of type OBCComponentTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new BaselMasterException("The ITrxValue is not of type OBCComponentTrxValue: " + ex.toString());
        }
    	}else{
        	throw new BaselMasterException("Error : Error  while preparing result component in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IBaselMasterTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new BaselMasterException("Error : Error  while preparing result component in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param componentTrxValue
     * @return IComponentTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IBaselMasterTrxValue prepareInsertTrxValue(IBaselMasterTrxValue baselTrxValue)throws TrxOperationException {
        if (baselTrxValue != null) {
            IFileMapperId actual = baselTrxValue.getFileMapperID();
            IFileMapperId staging = baselTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	baselTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	baselTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	baselTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	baselTrxValue.setStagingReferenceID(null);
            }
            return baselTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Basel is null");
        }
    }
	
    
    
    protected IBaselMasterTrxValue updateMasterInsertTrx(IBaselMasterTrxValue baselTrxValue) throws TrxOperationException {
        try {
        	baselTrxValue = prepareInsertTrxValue(baselTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(baselTrxValue);
            OBBaselMasterTrxValue newValue = new OBBaselMasterTrxValue(tempValue);
            newValue.setFileMapperID(baselTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(baselTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IBaselMasterTrxValue createStagingFileId(IBaselMasterTrxValue baselTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingBaselFileMapperIdBusManager().createFileId(baselTrxValue.getStagingFileMapperID());
        	baselTrxValue.setStagingFileMapperID(fileMapperID);
        	baselTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return baselTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IBaselMasterTrxValue insertActualBasel(IBaselMasterTrxValue baselTrxValue) throws TrxOperationException {
        try {
        	IBaselMaster basel = getStagingBaselFileMapperIdBusManager().insertBasel(baselTrxValue.getStagingBaselMaster());
        	baselTrxValue.setBaselMaster(basel);
        	baselTrxValue.setReferenceID(String.valueOf(basel.getId()));
            return baselTrxValue;
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
