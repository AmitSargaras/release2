package com.integrosys.cms.ui.newtatmaster;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.proxy.ITatmasterProxyManager;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;

public class CheckerReadTatMaster extends AbstractCommand implements	ICommonEventConstant{
	
	private ITatmasterProxyManager tatMasterProxy;

	public ITatmasterProxyManager getTatMasterProxy() {
		return tatMasterProxy;
	}

	public void setTatMasterProxy(ITatmasterProxyManager tatMasterProxy) {
		this.tatMasterProxy = tatMasterProxy;
	}
	
	public CheckerReadTatMaster() {
	}

	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "tatEventObj", "com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster", FORM_SCOPE },
				{"ITatMasterTrxValue", "com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ComponentException {
		

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			INewTatMaster tatEventObj;
			 INewTatMaster tatMaster;
			ITatMasterTrxValue trxValue=null;
			String tatCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get Component Trx value
			trxValue = (OBTatMasterTrxValue) getTatMasterProxy().getTatMasterByTrxID(tatCode);
			if(!trxValue.getStatus().equalsIgnoreCase("ACTIVE")){	
				 tatMaster=(OBNewTatMaster) trxValue.getTatMaster();
				 tatEventObj = (OBNewTatMaster) trxValue.getStagingtatMaster();
				 if(null!=tatMaster && !"checker_process_edit".equals(event)){
					 tatEventObj.setCreatedBy(tatMaster.getCreatedBy());
					 tatEventObj.setCreatedOn(tatMaster.getCreatedOn());
					 tatEventObj.setLastUpdatedBy(tatMaster.getLastUpdatedBy());
					 tatEventObj.setLastUpdatedOn(tatMaster.getLastUpdatedOn());
				 }
			}else{
				tatEventObj = (OBNewTatMaster) trxValue.getTatMaster();
			}
			
			resultMap.put("ITatMasterTrxValue", trxValue);
			resultMap.put("tatEventObj", tatEventObj);
			resultMap.put("event", event);
		} catch (TatMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	
		
	}
}
