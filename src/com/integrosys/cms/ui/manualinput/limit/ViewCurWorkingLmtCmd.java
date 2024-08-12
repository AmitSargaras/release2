/*
 * Created on 2007-2-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ViewCurWorkingLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE }, 
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] {{ "lmtId", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "fccBranchList", "java.util.List", SERVICE_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE }, 
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String lmtID = (String) (map.get("limitId")); //Shiv 300911
		String indexID = (String) (map.get("indexID"));
		String fromEvent = (String) (map.get("fromEvent")); 
		try {
			// just map from form to staging limit and save in trxValue object
			ILimit lmt = (ILimit) (map.get("lmtDetailForm"));
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
			
			IFCCBranchDao fccBranchDao = (IFCCBranchDao) BeanHouse.get("fccBranchDao");
			List fccBranchList = fccBranchDao.getFccBranchList();
					List fccBranchLbValList = new ArrayList();
					try {

						for (int i = 0; i < fccBranchList.size(); i++) {
							IFCCBranch fccBranch = (IFCCBranch) fccBranchList.get(i);
							if (fccBranch.getStatus().equals("ACTIVE")) {

								String id1 = Long.toString(fccBranch.getId());
								String val1 = fccBranch.getBranchCode();

								LabelValueBean lvBean1 = new LabelValueBean(val1, id1);
								fccBranchLbValList.add(lvBean1);
							}
						}
					} catch (Exception ex) {
					}
					fccBranchList = CommonUtil.sortDropdown(fccBranchLbValList);
					
					result.put("fccBranchList", fccBranchList);
					result.put("sessionCriteria",map.get("sessionCriteria"));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		if(map.get("lmtId") != null){
			lmtID = (String) (map.get("lmtId")); //Shiv 231111
		}
		result.put("sessionCriteria",map.get("sessionCriteria"));
		result.put("fundedAmount", map.get("fundedAmount"));
		result.put("nonFundedAmount", map.get("nonFundedAmount"));
		result.put("memoExposer", map.get("memoExposer"));
		result.put("sanctionedLimit", map.get("sanctionedLimit"));
		result.put("inrValue", map.get("inrValue"));
		result.put("lmtId", lmtID); //Shiv 300911
		result.put("indexID", indexID); 
		result.put("fromEvent", fromEvent);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
