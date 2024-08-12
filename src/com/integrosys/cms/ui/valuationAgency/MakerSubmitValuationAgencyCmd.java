package com.integrosys.cms.ui.valuationAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.ICity;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */
public class MakerSubmitValuationAgencyCmd extends AbstractCommand implements
		ICommonEventConstant {

	
private  static boolean isVACodeUnique = true;
	
	/**
	 * @return the isVACodeUnique
	 */
	public static boolean isVACodeUnique() {
		return isVACodeUnique;
	}

	/**
	 * @param isVACodeUnique the isVACodeUnique to set
	 */
	public static void setVACodeUnique(boolean isVACodeUnique) {
		MakerSubmitValuationAgencyCmd.isVACodeUnique = isVACodeUnique;
	}

	
	
	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	private IValuationAgencyProxyManager valuationAgencyProxy;

	/**
	 * Default Constructor
	 */

	public MakerSubmitValuationAgencyCmd() {
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
				{ "valuationAgencyCode", "java.lang.String", REQUEST_SCOPE },
				{ "IValuationAgencyTrxValue","com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "valuationObj","com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency",FORM_SCOPE }
			});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{ "countryList", "java.util.List", REQUEST_SCOPE },
				{ "regionList", "java.util.List", REQUEST_SCOPE },
				{ "cityList", "java.util.List", REQUEST_SCOPE },
				{ "stateList", "java.util.List", REQUEST_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			
			OBValuationAgency valuationAgency = (OBValuationAgency) map.get("valuationObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IValuationAgencyTrxValue trxValueOut = new OBValuationAgencyTrxValue();
			trxValueOut = getValuationAgencyProxy().makerCreateValuationAgency(ctx, valuationAgency);
		
			boolean isValuationNameUnique = false;			
			String valuationName =  valuationAgency.getValuationAgencyName().trim();
			
			if( valuationName != null )
				isValuationNameUnique = getValuationAgencyProxy().isValuationNameUnique(valuationName);

			if( isValuationNameUnique )
				exceptionMap.put("valuationAgencyNameError", new ActionMessage("error.string.exist","ValuationAgency Name"));
			
			if( isValuationNameUnique ){
				IValuationAgencyTrxValue valuationAgencyTrxValue = null;
				long stateId = 0, countryId = 0;
				resultMap.put("stateList", getStateList());
				resultMap.put("countryList", getCountryList(countryId));
				resultMap.put("regionList", getRegionList(stateId));
				resultMap.put("cityList", getCityList(stateId));
				resultMap.put("request.ITrxValue", valuationAgencyTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;		
			} 
			
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (ValuationAgencyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List)getValuationAgencyProxy().getCountryList(countryId);
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry) idList.get(i);
				if (country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getRegionList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = "";
				String val = "";
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getStateList() {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				String id = Long.toString(state.getIdState());
				String val = state.getStateName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCityList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();//(List) getValuationAgencyProxy().getCityList(stateId);	 	
			for (int i = 0; i < idList.size(); i++) {
					ICity city = (ICity)idList.get(i);
					if( city.getStatus().equals("ACTIVE")) {
						String id = "";
						String val = "";
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
