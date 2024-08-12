package com.integrosys.cms.ui.geography.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerPrepareEditStateCommand extends AbstractCommand{
	
	private IStateProxyManager stateProxy;

	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	public MakerPrepareEditStateCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"stateId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "startIndex", "java.lang.String", REQUEST_SCOPE }
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{ "stateObj", "com.integrosys.cms.app.geography.state.bus.IState", FORM_SCOPE },
				{"IStateTrxValue", "com.integrosys.cms.app.geography.state.trx.IStateTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "startIndex", "java.lang.String", REQUEST_SCOPE },
	            { "countryList", "java.util.List",SERVICE_SCOPE },
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            { "regionList", "java.util.List",SERVICE_SCOPE },	            
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
			});
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        long id = Long.parseLong((String) map.get("stateId"));
	        try {
	        	
	        	
	        	String startIdx = (String) map.get("startIndex");
	        	String event = (String) map.get("event");
	        	
	        	IStateTrxValue stateTrxValue =(IStateTrxValue) getStateProxy().getStateById(id);
	        	IState state = stateTrxValue.getActualState();
	        	
	        	long idCountry = 0;
	        	if(	stateTrxValue.getStatus().equals("PENDING_CREATE") ||
	        		stateTrxValue.getStatus().equals("PENDING_UPDATE") || 
	        		stateTrxValue.getStatus().equals("PENDING_DELETE") || 
	        		stateTrxValue.getStatus().equals("REJECTED") 	   ||
	        		stateTrxValue.getStatus().equals("DRAFT"))
					resultMap.put("wip", "wip");
	        	
	            resultMap.put("event", event);
	            resultMap.put("stateObj", state);
	            resultMap.put("startIndex", startIdx);
	            resultMap.put("countryList", getCountryList(idCountry));
	            resultMap.put("regionList", getRegionList(state.getRegionId().getCountryId().getIdCountry()));
	            resultMap.put("IStateTrxValue", stateTrxValue);
	        } catch (NoSuchGeographyException nsge) {
	        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
				cpe.initCause(nsge);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_STATE",id,"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					if( country.getStatus().equals("ACTIVE")) {
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
		
		private List getRegionList(long countryId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getStateProxy().getRegionList(countryId);				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
					if( region.getStatus().equals("ACTIVE")) {
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		} 

}
