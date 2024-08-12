package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.proxy.IProxyManager;
import com.integrosys.cms.app.excLineforstpsrm.trx.IExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadCommand extends AbstractCommand implements ICommonEventConstant,
IExcLineForSTPSRMConstant{

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public MakerReadCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			 { "startIndex", String.class.getName(), REQUEST_SCOPE },
			 { "id", String.class.getName(), REQUEST_SCOPE },
			 { "event", String.class.getName(), REQUEST_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ POJO_OBJECT, IExcLineForSTPSRM.class.getName(), FORM_SCOPE },
			{ POJO_TRX, IExcLineForSTPSRMTrxValue.class.getName(), SERVICE_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ "lineNoList", List.class.getName(), SERVICE_SCOPE }
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String id=(String) map.get("id");
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			
			IExcLineForSTPSRMTrxValue trxValue = getProxyManager().getTrxValue(Long.valueOf(id));
			IExcLineForSTPSRM obj = trxValue.getActual() != null ? trxValue.getActual() : trxValue.getStaging();
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			IFacilityNewMasterJdbc jdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
			resultMap.put("lineNoList", jdbc.getAllActiveLineNumbers());
			
			resultMap.put(POJO_TRX, trxValue);
			resultMap.put(POJO_OBJECT, obj);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (ExcLineForSTPSRMException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}