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
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ReadTatMasterEvent extends AbstractCommand{
	
	
	private ITatmasterProxyManager tatMasterProxy;
	
	
	
	public ITatmasterProxyManager getTatMasterProxy() {
		return tatMasterProxy;
	}

	public void setTatMasterProxy(ITatmasterProxyManager tatMasterProxy) {
		this.tatMasterProxy = tatMasterProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				 {"tatCode", "java.lang.String", REQUEST_SCOPE},
				 {"tatCode.session", "java.lang.String", SERVICE_SCOPE},
				 { "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	        		{ "tatEventObj", "com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster", FORM_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{"tatCode.session", "java.lang.String", SERVICE_SCOPE},
					{"ITatMasterTrxValue", "com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue", SERVICE_SCOPE},
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
	        });
	    }
	   
	   public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			try {
				INewTatMaster tatEvent;
				ITatMasterTrxValue trxValue=null;
			
				String event = (String) map.get("event");
				String tatCode=(String) (map.get("tatCode"));
				if(tatCode==null||"".equals(tatCode)){
					tatCode=(String) (map.get("tatCode.session"));
				}
			
				
				resultMap.put("tatCode.session", tatCode);
				
				
				
				trxValue = (OBTatMasterTrxValue) getTatMasterProxy().getTatMasterTrxValue(Long.parseLong(tatCode));
				tatEvent = (OBNewTatMaster) trxValue.getTatMaster();
				
				
				
				if(!(trxValue.getStatus().equals("ACTIVE")))
				{
					resultMap.put("wip", "wip");
				}
				
				resultMap.put("event", event);
					
			
				resultMap.put("ITatMasterTrxValue", trxValue);
				resultMap.put("tatEventObj", tatEvent);
			}catch (TatMasterException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
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
