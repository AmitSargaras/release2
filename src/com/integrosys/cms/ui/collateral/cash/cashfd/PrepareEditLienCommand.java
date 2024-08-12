package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class PrepareEditLienCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				//{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				//{ "trxID", "java.lang.String", REQUEST_SCOPE },		
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "baselSerial", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				{ "radioSelect", "java.lang.String", REQUEST_SCOPE },

	            { "facilityName", "java.lang.String", REQUEST_SCOPE},
	            { "facilityId", "java.lang.String", REQUEST_SCOPE},
	            { "lcnNo", "java.lang.String", REQUEST_SCOPE},	 
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },				
				//{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", GLOBAL_SCOPE }
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
				//{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
				//{ "system", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
			//	{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },	
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "baselSerial", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				{ "radioSelect", "java.lang.String", REQUEST_SCOPE },

	            { "facilityName", "java.lang.String", REQUEST_SCOPE},
	            { "facilityId", "java.lang.String", REQUEST_SCOPE},
	            { "lcnNo", "java.lang.String", REQUEST_SCOPE},	 
				
				//{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
		
		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String indexID = (String) map.get("indexID");
		String event = (String) map.get("event");
		String lienNo = (String) map.get("lienNo");
		String lienAmount = (String) map.get("lienAmount");
		String serialNo = (String) map.get("serialNo");
		String baselSerial = (String) map.get("baselSerial");
		String remark = (String) map.get("remark");
	//	 DecimalFormat dft = new DecimalFormat("#0.00");
	//	 double lAmount = Double.parseDouble(lienAmount);
	//	 lienAmount = dft.format(lAmount);
		String index = (String) map.get("index");
		String facilityName = (String) map.get("facilityName");
		String facilityId = (String) map.get("facilityId");
		String lcnNo = (String) map.get("lcnNo");
		
		//int ind = Integer.parseInt(index)-1;
		//String systemCustomerId = (String) map.get("systemCustomerId");
		DefaultLogger.debug(this, "Inside doExecute() PrepareEditLienCommand "+event);

		//String lienNumber = (String) map.get("lienNo");
		//String lienAmount = (String) map.get("lienAmount");
		
		
		List list = (List)map.get("lienList");		
		
		
/*		OBLien lien = new OBLien();
		lien= (OBLien)list.get(ind);
		 lienNo =Long.toString(lien.getLienNumber());
		 lienAmount =Long.toString(lien.getLienAmount());
		long a = lien.getLienAmount();
		if( lienNo != null && ! lienNo.equals(""))
		lien.setLienNumber(Long.parseLong(lienNo));
		if( lienAmount != null && ! lienAmount.equals("") )
			lien.setLienAmount(Long.parseLong(lienAmount));
		
		
		list.add(ind,lien);*/
		//list.remove(ind);
		//ILienMethod obLien = (OBLien)map.get("OBLien");
		//resultMap.put("OBLien", obLien);
		resultMap.put("index", index);
		resultMap.put("indexID", indexID);
		resultMap.put("lienNo", lienNo);
		resultMap.put("lienAmount", UIUtil.formatWithCommaAndDecimal(lienAmount));
		resultMap.put("serialNo", serialNo);
		resultMap.put("baselSerial", baselSerial);
		resultMap.put("remark", remark);
		resultMap.put("event",event);
		resultMap.put("lienList", list);
		resultMap.put("facilityName",facilityName);
		resultMap.put("facilityId", facilityId);
		resultMap.put("lcnNo",lcnNo);
		resultMap.put("radioSelect", (String) map.get("radioSelect"));
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
		
		
		
	}
	
	
}
