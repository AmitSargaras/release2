package com.integrosys.cms.ui.geography.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerReadStateCommand extends AbstractCommand implements ICommonEventConstant {
	
	private IStateProxyManager stateProxy;

	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadStateCommand() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, 
				{"countryId", "java.lang.String", REQUEST_SCOPE}
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
				{ "stateObj", "com.integrosys.cms.app.geography.bus.OBGeography", FORM_SCOPE },
				{ "countryList", "java.util.List",REQUEST_SCOPE },
				{ "regionList", "java.util.List",REQUEST_SCOPE },
				{"IStateTrxValue", "com.integrosys.cms.app.geography.state.trx.IStateTrxValue", SERVICE_SCOPE},
		});
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {

			String trxId	=(String) (map.get("TrxId"));
			/*long countryId =0;
			String id =(String) map.get("countryId");
			if ( id != null && id != "") {
				countryId = Long.parseLong(id);
			}*/	
			IStateTrxValue trxValue = (OBStateTrxValue) getStateProxy().getStateTrxValue((Long.parseLong(trxId)));
			IState state = (OBState) trxValue.getActualState();
			long countryId = state.getRegionId().getCountryId().getIdCountry();
			
			resultMap.put("IStateTrxValue", trxValue);
			resultMap.put("stateObj", state);
			resultMap.put("countryList", getCountryList(countryId));
			resultMap.put("regionList", getRegionList(countryId));
		}catch (NoSuchGeographyException ex) {
	       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
	         ex.printStackTrace();
	         throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
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
				List idList = (List) getStateProxy().getCountryList(countryId);		
				for (int i = 0; i < idList.size(); i++) {
						ICountry country = (ICountry)idList.get(i);
						String id = Long.toString(country.getIdCountry());
						String val = country.getCountryName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		private List getRegionList(long countryId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getStateProxy().getRegionList(countryId);		
				for (int i = 0; i < idList.size(); i++) {
						IRegion region = (IRegion)idList.get(i);
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}

}
