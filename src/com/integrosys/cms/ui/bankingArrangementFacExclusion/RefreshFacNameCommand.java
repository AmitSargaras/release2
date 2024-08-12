package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

public class RefreshFacNameCommand extends AbstractCommand {
	
	public RefreshFacNameCommand() {}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "facCat", String.class.getName(), REQUEST_SCOPE },
			{ "system", String.class.getName(), REQUEST_SCOPE }
			});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {{ "facNameList", List.class.getName(), SERVICE_SCOPE }});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String facCat = (String) (map.get("facCat"));
		String system = (String) (map.get("system"));
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		if ((facCat != null) && !facCat.trim().equals("") && system != null && !system.trim().equals("")) {
			try {
				ILimitDAO dao = LimitDAOFactory.getDAO();
				result.put("facNameList", getFacName(dao.getFacNameList(facCat, system)));
			}catch (Exception e) {
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private List getFacName(List lst) {
		List lbValList = new ArrayList();	
		OBLimit obLimit = new OBLimit();	
		for(int i=0; i<lst.size(); i++){
			obLimit = (OBLimit)lst.get(i);
			String label = obLimit.getFacilityCode();
			String value = obLimit.getFacilityName();
				LabelValueBean lvBean = new LabelValueBean(value,value );
				lbValList.add(lvBean);	
		}
				
		return CommonUtil.sortDropdown(lbValList);
	}
}