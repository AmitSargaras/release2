/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RemoveCoBorrowerXRefDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
		//	 { "xrefDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "indexIDD", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },

	//			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, 

				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{"coBorrowerId", "java.lang.String", REQUEST_SCOPE },
				{ "coBorrowerName", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", REQUEST_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		//	{"xrefDetailForm", "java.lang.Object", FORM_SCOPE },
			{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
			{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
			{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			
			String event = (String) map.get("event");
			String index = (String) map.get("indexIDD");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			
	
		
			
		//Delete CoBorrower	
		if(EventConstant.EVENT_DELETE_CO_BORROWER.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER_1.equals(event)) {

				System.out.println("Inside Delete command"+event);
				
				List list = (List)map.get("restCoBorrowerList");
				
				String coBorrowerId = (String)map.get("coBorrowerId");
				String coBorrowerName = (String)map.get("coBorrowerName");
				
				list.remove((Integer.parseInt(index))-1);
				
				
				result.put("restCoBorrowerList", list);
				
				List	idlist = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					OBLimitXRefCoBorrower coborrObj = (OBLimitXRefCoBorrower) list.get(i);
					idlist.add(coborrObj.getCoBorrowerId());
					
				}
				String lineCoBorrowerIds = UIUtil.getDelimitedStringFromList(idlist, ",");
				   lineCoBorrowerIds = lineCoBorrowerIds==null ? "" : lineCoBorrowerIds ;
				 //  System.out.println("************ lineCoBorrowerIds"+lineCoBorrowerIds);
					result.put("restCoBorrowerIds",lineCoBorrowerIds);

			}
		
			result.put("lmtTrxObj", lmtTrxObj);
			String facCoBorrowerLiabIds =  (String) map.get("facCoBorrowerLiabIds");
			System.out.println("facCoBorrowerLiabIds in JAVA CMD:::::"+facCoBorrowerLiabIds);
			result.put("facCoBorrowerLiabIds", facCoBorrowerLiabIds);
			
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return temp;		
						
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
	}
}
