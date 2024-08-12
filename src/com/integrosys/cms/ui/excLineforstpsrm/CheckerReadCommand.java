package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
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

public class CheckerReadCommand extends AbstractCommand implements ICommonEventConstant,
IExcLineForSTPSRMConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public CheckerReadCommand() {}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ "TrxId", String.class.getName(), REQUEST_SCOPE },
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
			String trxId = (String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			IExcLineForSTPSRMTrxValue trxValue = (IExcLineForSTPSRMTrxValue) getProxyManager().getByTrxID(trxId);
			IExcLineForSTPSRM excLineForSTPSRMObj = (IExcLineForSTPSRM) trxValue.getStaging();
			
			IFacilityNewMasterJdbc jdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
			resultMap.put("lineNoList", jdbc.getAllActiveLineNumbers());
			
			resultMap.put(POJO_TRX, trxValue);
			resultMap.put(POJO_OBJECT, excLineForSTPSRMObj);
			resultMap.put("event", event);
			if(trxValue.getActual() == null)
				resultMap.put("full_edit", "Y");
			else {
				if("REJECTED".equals(trxValue.getToState()) && "PENDING_DELETE".equals(trxValue.getFromState())) {
					resultMap.put("full_edit", "D");
				}else {
					resultMap.put("full_edit", "N");
				}
			}	
		} catch (ExcLineForSTPSRMException e) {
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