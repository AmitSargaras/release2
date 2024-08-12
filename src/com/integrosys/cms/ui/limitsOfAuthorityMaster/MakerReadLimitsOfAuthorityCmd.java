package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.LimitsOfAuthorityMasterException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.proxy.IProxyManager;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trx.ILimitsOfAuthorityMasterTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadLimitsOfAuthorityCmd extends AbstractCommand implements ICommonEventConstant,
ILimitsOfAuthorityMasterConstant{

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public MakerReadLimitsOfAuthorityCmd() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			 { "startIndex", String.class.getName(), REQUEST_SCOPE },
			 { "id", String.class.getName(), REQUEST_SCOPE },
			 { "event", String.class.getName(), REQUEST_SCOPE },
			 { SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			 { SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE }
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ LIMITS_OF_AUTHORITY_OBJ, ILimitsOfAuthorityMaster.class.getName(), FORM_SCOPE },
			{ LIMITS_OF_AUTHORITY_TRX_VAL, ILimitsOfAuthorityMasterTrxValue.class.getName(), SERVICE_SCOPE },
			{ SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE }
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String id=(String) map.get("id");
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			
			ILimitsOfAuthorityMasterTrxValue trxValue = getProxyManager().getTrxValue(Long.valueOf(id));
			ILimitsOfAuthorityMaster obj = trxValue.getActual() != null ? trxValue.getActual() : trxValue.getStaging();
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			if(map.get(SESSION_RANKING_SEQUENCE_LIST) == null) {
				resultMap.put(SESSION_RANKING_SEQUENCE_LIST, LimitsOfAuthorityMasterHelper.populateRankingOfSequence());
			}
			if(map.get(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE) == null) {
				resultMap.put(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, LimitsOfAuthorityMasterHelper.populateRankingOfSequenceMap());
			}
			
			resultMap.put(LIMITS_OF_AUTHORITY_TRX_VAL, trxValue);
			resultMap.put(LIMITS_OF_AUTHORITY_OBJ, obj);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (LimitsOfAuthorityMasterException e) {
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