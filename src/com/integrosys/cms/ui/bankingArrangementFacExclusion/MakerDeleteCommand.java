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
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.OBBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteCommand extends AbstractCommand implements ICommonEventConstant,
IBankingArrangementFacExclusionConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public MakerDeleteCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{ POJO_TRX, IBankingArrangementFacExclusionTrxValue.class.getName(), SERVICE_SCOPE },
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ POJO_OBJECT, IBankingArrangementFacExclusion.class.getName(), FORM_SCOPE },
			{"remarks", String.class.getName(), REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{ "request.ITrxValue", ICMSTrxValue.class.getName(), REQUEST_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try{
			OBTrxContext ctx = (OBTrxContext) map.get(TRX_CONTEXT);
			IBankingArrangementFacExclusionTrxValue trxValueIn = (IBankingArrangementFacExclusionTrxValue) map.get(POJO_TRX);
			IBankingArrangementFacExclusion obj = (IBankingArrangementFacExclusion) map.get(POJO_OBJECT);
			String event = (String) map.get("event");
			String remarks = (String) map.get("remarks");
			IBankingArrangementFacExclusionTrxValue trxValueOut = new OBBankingArrangementFacExclusionTrxValue();
	
			ctx.setRemarks(remarks);
			
			trxValueOut = getProxyManager().makerDelete(ctx, trxValueIn, obj);
	
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (BankingArrangementFacExclusionException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e); 
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