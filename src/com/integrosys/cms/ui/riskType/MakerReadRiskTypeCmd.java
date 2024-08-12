package com.integrosys.cms.ui.riskType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeDaoImpl;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class MakerReadRiskTypeCmd extends AbstractCommand implements ICommonEventConstant{

	private IRiskTypeProxyManager riskTypeProxy;

	public IRiskTypeProxyManager getRiskTypeProxy() {
		return riskTypeProxy;
	}

	public void setRiskTypeProxy(IRiskTypeProxyManager riskTypeProxy) {
		this.riskTypeProxy = riskTypeProxy;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadRiskTypeCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			 {"productCode", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 { "TrxId", "java.lang.String", REQUEST_SCOPE },
			 {"event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "riskTypeObj", "com.integrosys.cms.app.riskType.bus.OBRiskType", FORM_SCOPE },
				{"IRiskTypeTrxValue", "com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "riskGrade", "java.util.Set", SERVICE_SCOPE },
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IRiskType riskType;
			IRiskTypeTrxValue trxValue=null;
			String productCode=(String) (map.get("productCode"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			trxValue = (OBRiskTypeTrxValue) getRiskTypeProxy().getRiskTypeTrxValue(Long.parseLong(productCode));
			riskType = (OBRiskType) trxValue.getStagingRiskType();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			Set<String> riskGrade=new HashSet<String>();
			RiskTypeDaoImpl daoValuation = new RiskTypeDaoImpl();
			riskGrade = daoValuation.getRiskGrade();
			/*String num = riskType.getValuationAmount();
			num = UIUtil.formatWithCommaAndDecimalNew(num);
			riskType.setValuationAmount(num);*/
			
			resultMap.put("riskGrade", riskGrade);
			
			resultMap.put("IRiskTypeTrxValue", trxValue);
			resultMap.put("riskTypeObj", riskType);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (RiskTypeException e) {
		
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
