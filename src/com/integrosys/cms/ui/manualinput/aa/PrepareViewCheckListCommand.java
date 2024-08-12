package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class PrepareViewCheckListCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },			
				{ "aaID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.aaID", "java.lang.String", SERVICE_SCOPE }
				
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
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "aaID", "java.lang.String", REQUEST_SCOPE },
				{ "camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE }
				
								
		});
		}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		String event = (String) map.get("event");
		String aaID = (String) map.get("aaID");
		String limitProfileID="";
		if("view".equals(event)||"maker_edit_aadetail".equals(event)){
		
		if(aaID==null ||"".equals(aaID)){
			aaID=(String) map.get("session.aaID");
			limitProfileID=aaID;
		}
		if(aaID!=null){
			limitProfileID=aaID;
		}
		}
		ILimitProfileTrxValue limitProfileTrxVal =(ILimitProfileTrxValue)map.get("limitProfileTrxVal");
		if(limitProfileTrxVal!=null && limitProfileTrxVal.getLimitProfile()!=null){
		if("checker_edit_aadetail".equals(event)||"maker_edit_aadetail_reject".equals(event)
				||"maker_close_aadetail".equals(event)||"return_maker_edit_aadetail_reject".equals(event)){
			limitProfileID=Long.toString(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
		}
		if("to_track".equals(event)){
			limitProfileID=Long.toString(limitProfileTrxVal.getLimitProfile().getLimitProfileID());
		}
		//DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", LimitProfileID=" + aaID);
		try {
			
			HashMap camCheckListMap=new HashMap();
			ArrayList camChecklistArray=new ArrayList();
			 camCheckListMap= CheckListDAOFactory.getCheckListDAO().getBulkCAMCheckListByCategoryAndProfileID("CAM",Long.parseLong(limitProfileID));
			  camChecklistArray =(ArrayList) camCheckListMap.get("NORMAL_LIST");
			  resultMap.put("camChecklistArray", camChecklistArray);
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
		} 
		}
		else{
		resultMap.put("camChecklistArray", new ArrayList());
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
