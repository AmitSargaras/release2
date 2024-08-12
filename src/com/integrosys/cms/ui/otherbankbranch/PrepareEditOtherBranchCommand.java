package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * This command Prepares the other bank branch before editing
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23
 */
public class PrepareEditOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBranchProxyManager otherBranchProxyManager;
	
	private IOtherBankProxyManager otherBankProxyManager;
	

	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	/**
	 * @return the otherBranchProxyManager
	 */
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	/**
	 * @param otherBranchProxyManager the otherBranchProxyManager to set
	 */
	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	public PrepareEditOtherBranchCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BranchId", "java.lang.String", REQUEST_SCOPE},
	            {"branchCountryId","java.lang.String",REQUEST_SCOPE},
				{"branchRegionId","java.lang.String",REQUEST_SCOPE},
				{"branchStateId","java.lang.String",REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBranchObj", "com.integrosys.cms.app.otherbranch.bus.OBOtherBranch", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
	            {"countryList","java.util.List",SERVICE_SCOPE},
	            {"regionList","java.util.List",SERVICE_SCOPE},
	            {"stateList","java.util.List",SERVICE_SCOPE},
	            {"cityList","java.util.List",SERVICE_SCOPE}
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
	        try {
	        	String id = (String) map.get("BranchId");
	        	String event = (String) map.get("event");
	        	
	        	String countryId = (String) map.get("countryId");
	        	String regionId = (String) map.get("regionId");
	        	String stateId = (String) map.get("stateId");
	        	
	        	List regionList = new ArrayList();
	        	List stateList = new ArrayList();
	        	List cityList = new ArrayList();
	            IOtherBranch OtherBranch = new OBOtherBranch();
	            IOtherBankBranchTrxValue otherBankBranchTrxValue = null;
	        	
	        	otherBankBranchTrxValue = getOtherBranchProxyManager().getOtherBranchTrxValue(Long.parseLong(id));
	        	OtherBranch = otherBankBranchTrxValue.getOtherBranch();
	        	
	        	if(otherBankBranchTrxValue.getStatus().equals("PENDING_UPDATE") || otherBankBranchTrxValue.getStatus().equals("PENDING_DELETE") 
	        			|| otherBankBranchTrxValue.getStatus().equals("DRAFT")
	        			|| otherBankBranchTrxValue.getStatus().equals("REJECTED") ){
					resultMap.put("wip", "wip");
				}
	        	
	        	if(OtherBranch.getCountry()!=null){
	        		regionList = getRegionList(Long.toString(OtherBranch.getCountry().getIdCountry()));
	        		stateList = getStateList(Long.toString(OtherBranch.getRegion().getIdRegion()));
	        		cityList = getCityList(Long.toString(OtherBranch.getState().getIdState()));
	        	}
	        	LimitDAO limitDao = new LimitDAO();
	    		try {
	    		String migratedFlag = "N";	
	    		boolean status = false;	
	    		 status = limitDao.getCAMMigreted("CMS_OTHER_BANK_BRANCH",OtherBranch.getId(),"ID");
	    		
	    		if(status)
	    		{
	    			migratedFlag= "Y";
	    		}
	    		resultMap.put("migratedFlag", migratedFlag);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	
	        	resultMap.put("countryList",getCountryList());
	        	resultMap.put("regionList",regionList);
	        	resultMap.put("stateList",stateList);
	        	resultMap.put("cityList",cityList);
	        	
	            resultMap.put("event", event);
	            resultMap.put("OtherBranchObj", OtherBranch);
	            resultMap.put("IOtherBankBranchTrxValue", otherBankBranchTrxValue);
	            
	        } catch (OtherBranchException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	    
	    private List getCountryList() {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getOtherBankProxyManager().getCountryList();				
			
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
	    
	    private List getRegionList(String countryId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getOtherBankProxyManager().getRegionList(countryId);				
			
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
		
		private List getStateList(String regionId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getOtherBankProxyManager().getStateList(regionId);				
			
				for (int i = 0; i < idList.size(); i++) {
					IState state = (IState)idList.get(i);
					if( state.getStatus().equals("ACTIVE")) {
						String id = Long.toString(state.getIdState());
						String val = state.getStateName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		private List getCityList(String stateId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getOtherBankProxyManager().getCityList(stateId);				
			
				for (int i = 0; i < idList.size(); i++) {
					ICity city = (ICity)idList.get(i);
					if( city.getStatus().equals("ACTIVE")) {
						String id = Long.toString(city.getIdCity());
						String val = city.getCityName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	}



