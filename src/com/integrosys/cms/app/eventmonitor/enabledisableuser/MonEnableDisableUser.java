package com.integrosys.cms.app.eventmonitor.enabledisableuser;

import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.user.bus.CMS_USER_Helper;
import com.integrosys.component.user.app.constant.UserConstant;
import com.integrosys.component.user.app.proxy.CommonUserProxyFactory;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * @author lini
 * 
 *         Batch job to enable users based on Valid From Date and Valid To Date
 *         And disable users based on Valid To Date
 */
public class MonEnableDisableUser extends AbstractMonCommon {

	protected IMonitorDAO[] getDAOArray() {
		return null;
	}

	protected IMonRule getTriggerRule() {
		return null;
	}

	public IRuleParam constructRuleParam(int ruleNum) throws EventMonitorException {
		return null;
	}

	public String getEventName() {
		return "";
	}

	public void start(String countryCode, SessionContext context) throws EventMonitorException {

		String excludeSuperUsers = PropertyManager.getValue(UserConstant.SUPER_USERS);
	//	String strDaysDormant = PropertyManager.getValue(ICMSConstant.DAYS_DORMANT);
		String exclSuperUsers = getSuperUsersString(excludeSuperUsers);
		String strDaysDormant="60";
		String newStrDaysDormant="60";
		int daysDormant = 0;
		int daysDormantNew = 0;
		//Setting Max Dormant days  through general param	-- Abhijit Rudrakshawar
		IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
		IGeneralParamEntry generalParamEntry = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.EXISTING_USER_DORMANCY_DAYS);
		if(generalParamEntry!=null){
			 strDaysDormant =generalParamEntry.getParamValue();	
		}
		
		try {
			try {
				daysDormant = Integer.parseInt(strDaysDormant);
			}
			catch (Exception e) {
				daysDormant = 30;
				DefaultLogger.debug(this,"Setting Days Dormant to 30. Please set property integrosys.login.days.dormant in ofa.properties. ");
//				System.out.println("Setting Days Dormant to 30. Please set property integrosys.login.days.dormant in ofa.properties. ");
			}

		//	enableUsers(exclSuperUsers, new Date());
		//	disableUsers(exclSuperUsers, new Date());
			dormantUsers(exclSuperUsers, daysDormant);
		}
		catch (Exception ex) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ");
			ex.printStackTrace();
		}
		
		// by Shiv
		IGeneralParamEntry generalParamEntryNew = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.DORMANCY_DAYS);
		if(generalParamEntry!=null){
			 newStrDaysDormant = generalParamEntryNew.getParamValue();
		}
		
		try {
			try {
				daysDormantNew = Integer.parseInt(newStrDaysDormant);
			}
			catch (Exception e) {
				daysDormantNew = 30;
				DefaultLogger.debug(this,"Setting Days Dormant to 30. Please set value in CMS_GENERAL_PARAM TABLE. ");
//				System.out.println("Setting Days Dormant to 30. Please set value in CMS_GENERAL_PARAM TABLE. ");
			}

		//	enableUsers(exclSuperUsers, new Date());
		//	disableUsers(exclSuperUsers, new Date());
			newDormantUsers(exclSuperUsers, daysDormantNew);
		}
		catch (Exception ex) {
			DefaultLogger.debug(this, "Batch Job Error !!!! ");
			ex.printStackTrace();
		}
		
	}

	private String getSuperUsersString(String excludeSuperUsers) {
		String exclSuperUsers = " ";
		StringTokenizer token = new StringTokenizer(excludeSuperUsers, ",");
		while (token.hasMoreTokens()) {
			exclSuperUsers += " '" + token.nextElement() + "' " + ",";
		}
		DefaultLogger.debug(this, exclSuperUsers);
		DefaultLogger.debug(this, exclSuperUsers.substring(0, exclSuperUsers.lastIndexOf(",") - 1));
		if ((exclSuperUsers != null) && (exclSuperUsers.length() > 2)) {
			return exclSuperUsers.substring(0, exclSuperUsers.lastIndexOf(",") - 1);
		}
		return null;
	}

	private void disableUsers(String exclSuperUsers, Date validToDate) throws Exception {
		try {
			ICommonUserProxy proxy = CommonUserProxyFactory.getUserProxy();
			proxy.disableUserAccounts(exclSuperUsers, validToDate);
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "", ex);
			throw ex;
		}
	}

	private void enableUsers(String exclSuperUsers, Date validFromDate) throws Exception {
		try {
			ICommonUserProxy proxy = CommonUserProxyFactory.getUserProxy();
			proxy.enableUserAccounts(exclSuperUsers, validFromDate);
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "", ex);
			throw ex;
		}
	}

	private void dormantUsers(String exclSuperUsers, int daysDormant) throws Exception {
		new CMS_USER_Helper().dormantUserAccounts(exclSuperUsers, daysDormant);
		/*try {
			ICommonUserProxy proxy = CommonUserProxyFactory.getUserProxy();
			proxy.dormantUserAccounts(exclSuperUsers, daysDormant);
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "", ex);
			throw ex;
		}*/
	}
	
	private void newDormantUsers(String exclSuperUsers, int daysDormantNew) throws Exception {
		new CMS_USER_Helper().newDormantUserAccounts(exclSuperUsers, daysDormantNew);		
	}

	
}
