/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.LmtColSearchCriteria;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchSecDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "totalSecCount", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "securityId", "java.lang.String", REQUEST_SCOPE }, { "sourceId", "java.lang.String", REQUEST_SCOPE },
				{ "CustName", "java.lang.String", REQUEST_SCOPE }, { "PropId", "java.lang.String", REQUEST_SCOPE },
				{ "securitySubtype", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "securityList", "java.util.Collection", SERVICE_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "totalSecCount", "java.lang.String", REQUEST_SCOPE },
				{ "displayList", "java.lang.String", REQUEST_SCOPE },
				{ "securityList", "java.util.Collection", SERVICE_SCOPE },
				{ "CustName", "java.lang.String", REQUEST_SCOPE }, { "PropId", "java.lang.String", REQUEST_SCOPE },
				{ "collateral", "java.util.Collection", SERVICE_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			String totalCount = (String) (map.get("totalSecCount"));
			String securityId = (String) (map.get("securityId"));
			String securitySubtype = (String) (map.get("securitySubtype"));
			String sourceId = (String) (map.get("sourceId"));
			String event = (String) (map.get("event"));
			LmtColSearchCriteria crit = new LmtColSearchCriteria();
			
			String custName = (String) (map.get("CustName"));
			String propId = (String) (map.get("PropId"));
			
			// MBB-911 - space after security ID
			if (securityId != null) {
				securityId = securityId.trim();
			}

			crit.setSciSecId(securityId);
			crit.setSecSubtype(securitySubtype);
			crit.setSourceId(sourceId);
			crit.setLimitProfId(limit.getLimitProfileID());
			if (!AbstractCommonMapper.isEmptyOrNull(totalCount)) {
				crit.setNItems(Integer.parseInt(totalCount));
			}
			else {
				// first time -1 indicate that we need to retrieve count from
				// backend
				crit.setNItems(-1);
			}
			String startInd = (String) (map.get("startIndex"));
			if (startInd != null) {
				crit.setStartIndex(Integer.parseInt(startInd));
			}
			else {
				crit.setStartIndex(0);
			}
			List prevSecList = (List) (map.get("securityList"));
			if (prevSecList != null) {
				prevSecList.clear();
			}
			MILimitUIHelper helper = new MILimitUIHelper();
			SearchResult pgRes = null;  
			
				crit.setCustName(custName);
				crit.setPropSearchId(propId);
				pgRes = helper.getSBMILmtProxy().searchCollateralByIdSubtype(crit);
			
			
			boolean flag = false;
			
			if(event.equals("read_security")||event.equals("read_security_rejected")) {
				
				if(lmtTrxObj.getLimit() != null && lmtTrxObj.getLimit().getCollateralAllocations() != null){
					for(int i=0; i < lmtTrxObj.getLimit().getCollateralAllocations().length; i++){
						if(lmtTrxObj.getLimit().getCollateralAllocations()[i].getCollateral() != null) {
							if(lmtTrxObj.getLimit().getCollateralAllocations()[i].getCollateral().getSCISecurityID().equals(securityId)){
								result.put("collateral",lmtTrxObj.getLimit().getCollateralAllocations()[i].getCollateral());
								flag = true;
							}
						}
					}
				}
				
				if(lmtTrxObj.getStagingLimit() != null && lmtTrxObj.getStagingLimit().getCollateralAllocations() != null){
					for(int i=0; i < lmtTrxObj.getStagingLimit().getCollateralAllocations().length; i++){
						if(lmtTrxObj.getStagingLimit().getCollateralAllocations()[i].getCollateral() != null) {
							if(Long.toString(lmtTrxObj.getStagingLimit().getCollateralAllocations()[i].getCollateral().getCollateralID()).equals(securityId)){
								if(!flag) {
									result.put("collateral",lmtTrxObj.getStagingLimit().getCollateralAllocations()[i].getCollateral());
								}
							}
						}
					}
				}
			}

			result.put("CustName", custName);
			result.put("PropId", propId);
			result.put("session.startIndex", startInd);
			result.put("securityList", pgRes.getResultList());
			result.put("displayList", "Y");
			result.put("totalSecCount", String.valueOf(pgRes.getNItems()));

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
