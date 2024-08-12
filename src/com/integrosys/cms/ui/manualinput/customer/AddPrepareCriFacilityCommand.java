package com.integrosys.cms.ui.manualinput.customer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterDAOFactory;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class AddPrepareCriFacilityCommand extends AbstractCommand{
	
	private IDirectorMasterProxyManager directorMasterProxy;
	
	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(
			IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
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
				{ "trxID", "java.lang.String", REQUEST_SCOPE },		
				{ "facList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "legalId", "java.lang.String", REQUEST_SCOPE },
				{ "legalSource", "java.lang.String", REQUEST_SCOPE },
				{ "ManualInputCustomerInfoForm", "com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
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
				/*{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },*/
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
//				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE },
				{ "facilityList", "java.util.List", SERVICE_SCOPE },
				{ "directorMasterList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{ "facList", "java.util.List", SERVICE_SCOPE },
				{ "serialNoList", "java.util.List", SERVICE_SCOPE },
				{ "ManualInputCustomerInfoForm", "com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm", FORM_SCOPE },
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
		String event = (String) map.get("event");
		List directorCountryList = (List) map.get("directorCountryList");
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
//		if (directorCityList != null) {
//			resultMap.put("directorCityList", directorCityList);
//		} else {
//			resultMap.put("directorCityList", getFacilityList());
//		}
		
		try {
			//SHIV  -----------------
			List lbValList = new ArrayList();
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			
			resultMap.put("facilityList", new ArrayList());
			resultMap.put("serialNoList", new ArrayList());
			resultMap.put("directorMasterList", getDirectorMasterProxy().getAllDirectorMaster());
		} catch (DirectorMasterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TrxParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		DefaultLogger.debug(this, "Inside doExecute() ManualInputCreateCustomerCommand "+event);
		List list = (List)map.get("directorList");
		
		resultMap.put("ManualInputCustomerInfoForm", map.get("ManualInputCustomerInfoForm"));
		resultMap.put("facList", map.get("facList"));
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("event",event);
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
	
//	public SearchResult getFacilityList() throws Exception {
//		try {
//			return  FacilityNewMasterDAOFactory.getFacilityNewMasterJDBC().getAllFacilityNewMaster();
//		}
//		catch (Exception ex) {
//			throw new Exception();
//		}
//	}
//	
	
	
//	private List getFacility(SearchResult srcLst) {
//		List lbValList = new ArrayList();
//		List lst = (List) srcLst.getResultList();
//	try {
//			
//		for (int i = 0; i < lst.size(); i++) {
//			IFacilityNewMaster newMaster=(IFacilityNewMaster) lst.get(i);
//			String val = newMaster.getNewFacilityCode();
//			String label = newMaster.getNewFacilityName();
//				LabelValueBean lvBean = new LabelValueBean(label,val );
//				lbValList.add(lvBean);
//		}
//	} catch (Exception ex) {
//	}
//	return CommonUtil.sortDropdown(lbValList);
//}
	
}
