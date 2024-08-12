package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
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

public class CheckerReadLimitsOfAuthorityCmd extends AbstractCommand implements ICommonEventConstant,
ILimitsOfAuthorityMasterConstant {

	private IProxyManager proxyManager;
	
	public IProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(IProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public CheckerReadLimitsOfAuthorityCmd() {}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ "TrxId", String.class.getName(), REQUEST_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE }
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ LIMITS_OF_AUTHORITY_OBJ, ILimitsOfAuthorityMaster.class.getName(), FORM_SCOPE },
			{ LIMITS_OF_AUTHORITY_TRX_VAL, ILimitsOfAuthorityMasterTrxValue.class.getName(), SERVICE_SCOPE },
			{ "event", String.class.getName(), REQUEST_SCOPE },
			{ SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE }
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String trxId = (String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			ILimitsOfAuthorityMasterTrxValue trxValue = (ILimitsOfAuthorityMasterTrxValue) getProxyManager().getByTrxID(trxId);
			ILimitsOfAuthorityMaster limitsOfAuthorityMaster = (ILimitsOfAuthorityMaster) trxValue.getStaging();
			
			resultMap.put(LIMITS_OF_AUTHORITY_TRX_VAL, trxValue);
			resultMap.put(LIMITS_OF_AUTHORITY_OBJ, limitsOfAuthorityMaster);
			resultMap.put("event", event);
			if(map.get(SESSION_RANKING_SEQUENCE_LIST) == null) {
				resultMap.put(SESSION_RANKING_SEQUENCE_LIST, LimitsOfAuthorityMasterHelper.populateRankingOfSequence());
			}
			if(map.get(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE) == null) {
				resultMap.put(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, LimitsOfAuthorityMasterHelper.populateRankingOfSequenceMap());
			}
			if(trxValue.getActual() == null)
				resultMap.put("full_edit", "Y");
		} catch (LimitsOfAuthorityMasterException e) {
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