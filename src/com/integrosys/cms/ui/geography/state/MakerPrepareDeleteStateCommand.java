/**
 * 
 */
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
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 * @author sandiip.shinde
 *
 */
public class MakerPrepareDeleteStateCommand extends AbstractCommand{
	
	private IStateProxyManager stateProxy;
	
	private ICityProxyManager cityProxy;
	
	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerPrepareDeleteStateCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"stateId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "startIndex", "java.lang.String", REQUEST_SCOPE },
	            {"countryId", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"stateObj", "com.integrosys.cms.app.geography.state.bus.IState", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"flag", "java.lang.String", REQUEST_SCOPE},
	            { "startIndex", "java.lang.String", REQUEST_SCOPE },
	            { "countryList", "java.util.List",SERVICE_SCOPE },
				{ "regionList", "java.util.List",SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IStateTrxValue", "com.integrosys.cms.app.geography.country.trx.IStateTrxValue", SERVICE_SCOPE}
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
	        String id = (String) map.get("stateId");
	        try {
	        	
	        	String event = (String) map.get("event");
	        	String startIdx = (String) map.get("startIndex");
	        	
	        	IStateTrxValue stateTrxValue = null;
	        	IState state = new OBState();
	        	long stateId =0;
	        	boolean yesNo = false;
	        	String flag ="";
	        	stateTrxValue = getStateProxy().getStateTrxValue(Long.parseLong(id));
	        	state = stateTrxValue.getActualState();
	        	
	        	if( stateTrxValue.getStatus().equals("PENDING_CREATE") ||
	        		stateTrxValue.getStatus().equals("PENDING_UPDATE") || 
	        		stateTrxValue.getStatus().equals("PENDING_DELETE") ||
	        		stateTrxValue.getStatus().equals("REJECTED") 	   ||
	        		stateTrxValue.getStatus().equals("DRAFT")){
					resultMap.put("wip", "wip");
				}
	        	
	        	if( event.equals("maker_delete_state_read") )
	        		yesNo = getStateProxy().checkActiveCities(state);	        	
	        	else if( event.equals("maker_prepare_activate_state") )
	        		yesNo = getStateProxy().checkInActiveRegions(state);
	        	
	        	if( yesNo == true )
	        		flag = "true";
	        	else
	        		flag = "false";
	        		
	        	resultMap.put("flag", flag);
	        	resultMap.put("event", event);
	            resultMap.put("stateObj", state);
	            resultMap.put("startIndex", startIdx);
	            resultMap.put("IStateTrxValue", stateTrxValue);
	            resultMap.put("countryList", getCountryList(state.getRegionId().getCountryId().getIdCountry()));
				resultMap.put("regionList", getRegionList(stateId));
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
			 status = limitDao.getCAMMigreted("CMS_STATE",Long.parseLong(id),"ID");
			
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
				List idList = (List) getCityProxy().getCountryList(countryId);		
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
		
		private List getRegionList(long stateId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getCityProxy().getRegionList(stateId);		
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
