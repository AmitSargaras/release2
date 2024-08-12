package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.proxy.IProxyManager;
import com.integrosys.cms.app.excLineforstpsrm.trx.IExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.excLineforstpsrm.trx.OBExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteCommand extends AbstractCommand implements ICommonEventConstant,
IExcLineForSTPSRMConstant {

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
			{ POJO_TRX, IExcLineForSTPSRMTrxValue.class.getName(), SERVICE_SCOPE },
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ POJO_OBJECT, IExcLineForSTPSRM.class.getName(), FORM_SCOPE },
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
			IExcLineForSTPSRMTrxValue trxValueIn = (IExcLineForSTPSRMTrxValue) map.get(POJO_TRX);
			IExcLineForSTPSRM obj = (IExcLineForSTPSRM) map.get(POJO_OBJECT);
			String event = (String) map.get("event");
			String remarks = (String) map.get("remarks");
			IExcLineForSTPSRMTrxValue trxValueOut = new OBExcLineForSTPSRMTrxValue();
	
			ctx.setRemarks(remarks);
			
			trxValueOut = getProxyManager().makerDelete(ctx, trxValueIn, obj);
	
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (ExcLineForSTPSRMException ex) {
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