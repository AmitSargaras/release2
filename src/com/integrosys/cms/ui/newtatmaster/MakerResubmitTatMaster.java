package com.integrosys.cms.ui.newtatmaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.proxy.ITatmasterProxyManager;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerResubmitTatMaster extends AbstractCommand implements	ICommonEventConstant{
	
	private ITatmasterProxyManager tatMasterProxy;

	public ITatmasterProxyManager getTatMasterProxy() {
		return tatMasterProxy;
	}

	public void setTatMasterProxy(ITatmasterProxyManager tatMasterProxy) {
		this.tatMasterProxy = tatMasterProxy;
	}

	public MakerResubmitTatMaster() {
	}
	
	
	
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"ITatMasterTrxValue", "com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                { "tatEventObj", "com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
					   });
		}
	
	
	   public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

	    	HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			HashMap exceptionMap = new HashMap();
			boolean execute = false;
			try {
				OBNewTatMaster tatMaster = (OBNewTatMaster) map.get("tatEventObj");
				Date toDate=new Date();
				//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
				
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ITatMasterTrxValue trxValueIn = (OBTatMasterTrxValue) map.get("ITatMasterTrxValue");
				ITatMasterTrxValue trxValueOut = new OBTatMasterTrxValue();

				
				tatMaster.setCreatedOn(toDate);
				tatMaster.setCreatedBy(ctx.getUser().getLoginID());
				trxValueOut = getTatMasterProxy().makerEditRejectedTatMaster(ctx, trxValueIn, tatMaster);
			 

					resultMap.put("request.ITrxValue", trxValueOut);
	   	}
			catch (TatMasterException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
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
