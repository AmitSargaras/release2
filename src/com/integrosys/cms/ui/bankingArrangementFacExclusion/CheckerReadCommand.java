package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
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

public class CheckerReadCommand extends AbstractCommand implements ICommonEventConstant,
IBankingArrangementFacExclusionConstant {

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
			String trxId = (String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			IBankingArrangementFacExclusionTrxValue trxValue = (IBankingArrangementFacExclusionTrxValue) getProxyManager().getByTrxID(trxId);
			IBankingArrangementFacExclusion bankingArrFacExcl = (IBankingArrangementFacExclusion) trxValue.getStaging();
			
			resultMap.put(POJO_TRX, trxValue);
			resultMap.put(POJO_OBJECT, bankingArrFacExcl);
			resultMap.put("facCat", bankingArrFacExcl.getFacCategory());
			resultMap.put("system", bankingArrFacExcl.getSystem());
			resultMap.put("event", event);
			if(trxValue.getActual() == null)
				resultMap.put("full_edit", "Y");
		} catch (BankingArrangementFacExclusionException e) {
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