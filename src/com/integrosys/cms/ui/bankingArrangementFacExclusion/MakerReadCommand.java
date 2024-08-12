package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.BankingArrangementFacExclusionException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.proxy.IProxyManager;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.IBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadCommand extends AbstractCommand implements ICommonEventConstant,
IBankingArrangementFacExclusionConstant{

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
			{ POJO_OBJECT, IBankingArrangementFacExclusion.class.getName(), FORM_SCOPE },
			{ POJO_TRX, IBankingArrangementFacExclusionTrxValue.class.getName(), SERVICE_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ "facCat", String.class.getName(), REQUEST_SCOPE },
			{ "system", String.class.getName(), REQUEST_SCOPE }
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String id=(String) map.get("id");
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			
			IBankingArrangementFacExclusionTrxValue trxValue = getProxyManager().getTrxValue(Long.valueOf(id));
			IBankingArrangementFacExclusion obj = trxValue.getActual() != null ? trxValue.getActual() : trxValue.getStaging();
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put(POJO_TRX, trxValue);
			resultMap.put(POJO_OBJECT, obj);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
			resultMap.put("facCat", obj.getFacCategory());
			resultMap.put("system", obj.getSystem());
		} catch (BankingArrangementFacExclusionException e) {
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