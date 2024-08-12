package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.HashMap;

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

public class MakerSubmitEditCommand extends AbstractCommand implements ICommonEventConstant,
IExcLineForSTPSRMConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public MakerSubmitEditCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
			{ POJO_TRX, IExcLineForSTPSRMTrxValue.class.getName(), SERVICE_SCOPE },
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
            { "remarks", String.class.getName(), REQUEST_SCOPE },
            { "event", String.class.getName(), REQUEST_SCOPE },
            { POJO_OBJECT, IExcLineForSTPSRM.class.getName(), FORM_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() { 
	
		return (new String[][]{
	            { "request.ITrxValue", ICMSTrxValue.class.getName(), REQUEST_SCOPE }
		});
	}

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
        	IExcLineForSTPSRM obj = (IExcLineForSTPSRM) map.get(POJO_OBJECT);
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get(TRX_CONTEXT);
			IExcLineForSTPSRMTrxValue trxValueIn = (IExcLineForSTPSRMTrxValue) map.get(POJO_TRX);
			IExcLineForSTPSRMTrxValue trxValueOut = new OBExcLineForSTPSRMTrxValue();
			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			trxValueOut = getProxyManager().makerEditRejected(ctx, trxValueIn, obj);
			
			resultMap.put("request.ITrxValue", trxValueOut);
        } catch (ExcLineForSTPSRMException obe) {
        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
			cpe.initCause(obe);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
	    
}