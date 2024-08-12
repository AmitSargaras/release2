package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.BankingArrangementFacExclusionException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusionJdbc;
import com.integrosys.cms.app.bankingArrangementFacExclusion.proxy.IProxyManager;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.IBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditCommand extends AbstractCommand implements ICommonEventConstant,
IBankingArrangementFacExclusionConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public CheckerApproveEditCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{ POJO_TRX, IBankingArrangementFacExclusionTrxValue.class.getName(), SERVICE_SCOPE },
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{ "remarks", String.class.getName(), REQUEST_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE }
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
		HashMap exceptionMap = new HashMap();
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get(TRX_CONTEXT);
			IBankingArrangementFacExclusionTrxValue trxValueIn = (IBankingArrangementFacExclusionTrxValue) map.get(POJO_TRX);
			
			IBankingArrangementFacExclusion staging = trxValueIn.getStaging();
			if(ICMSConstant.STATE_PENDING_CREATE.equals(trxValueIn.getStatus())) {
				IBankingArrangementFacExclusionJdbc exclusionJdbc = (IBankingArrangementFacExclusionJdbc) BeanHouse.get("bankingArrangementFacExclusionJdbc");
				boolean isDuplicate = exclusionJdbc.isExcluded(staging.getSystem(),
						staging.getFacCategory(), staging.getFacName(), false);
				
				if(isDuplicate) {
					exceptionMap.put("master", new ActionMessage("error.banking.arr.exc.master.duplicate"));
					resultMap.put("trxId", trxValueIn.getTransactionID());
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}
			}
			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			IBankingArrangementFacExclusionTrxValue trxValueOut = getProxyManager().checkerApprove(ctx, trxValueIn);
			
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (BankingArrangementFacExclusionException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}