package com.integrosys.cms.app.login;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import com.integrosys.base.businfra.login.AuthenticationException;
import com.integrosys.base.businfra.login.ConditionList;
import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.IPersistentBroker;
import com.integrosys.base.businfra.login.InvalidCredentialsException;
import com.integrosys.base.businfra.login.PersistentEntityException;
import com.integrosys.base.businfra.login.PersistentFactory;
import com.integrosys.base.businfra.login.PersistentStorageException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.login.ADLoginHelper;
import com.integrosys.component.common.constant.ICompConstant;
import com.integrosys.component.login.app.LoginConstant;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.app.OBLOSCustomer;
import com.integrosys.component.login.app.PersistentEntityType;
import com.integrosys.component.login.app.SBLoginHistoryLog;
import com.integrosys.component.login.app.SMEUserLoginModule;
import com.integrosys.component.login.app.StatusModificationHandler;
import com.integrosys.component.user.app.constant.UserConstant;

public class ADAuthenticator extends SMEUserLoginModule {

	public boolean authenticate(ICredentials credentials)
			throws InvalidCredentialsException, AuthenticationException {
		boolean isNew = true;
		int attempts = 0;
		PersistentFactory factory = PersistentFactory.getInstance();
		IPersistentBroker broker = null;
		try {
			broker = factory.getBroker();
		}
		catch (Exception ex) {
			throwAuthException("couldn't get persistent broker", LoginErrorCodes.BROKER_ERROR);
		}
		OBLOSCustomer smeCust = new OBLOSCustomer();
		smeCust.setLoginId(credentials.getLoginId());
		smeCust.setRole(credentials.getRole());
		try {
			isNew = broker.isNew(smeCust, PersistentEntityType.LOS_USER);
			if (isNew) {
				throw new InvalidCredentialsException("SME user Login ID not found");
			}
			OBLOSCustomer cust = (OBLOSCustomer) broker.retrieve(smeCust, PersistentEntityType.LOS_USER);
			attempts = Integer.parseInt(cust.getLoginAttempts());
			if (!cust.getStatus().equals(LoginConstant.ACTIVE)) {
				// Setting default message as Locked State
				String strErrMesg = "Account is not ACTIVE";
				String strErrCode = LoginErrorCodes.ACCT_LOCK;
				if (LoginConstant.NEW.equals(cust.getStatus())) {
					strErrMesg = "Account is New";
					strErrCode = LoginErrorCodes.ACCT_NEW;
				}
				else if (LoginConstant.DORMANT.equals(cust.getStatus())) {
					strErrMesg = "Account has Dormant";
					strErrCode = LoginErrorCodes.ACCT_DORMANT;
				}
				else if (LoginConstant.EXPIRED.equals(cust.getStatus())) {
					strErrMesg = "Account has Expired";
					strErrCode = LoginErrorCodes.ACCT_EXPIRED;
				}

				DefaultLogger.debug(this,"ErrCode = " + strErrCode + " ErrMesg = " + strErrMesg);

				throwAuthException(strErrMesg, strErrCode);
				if (PropertyManager.getBoolean("log.logininfo.needed", false)) {
					SBLoginHistoryLog logger = (SBLoginHistoryLog) BeanController.getEJB("SBLoginHistoryLog",
							"com.integrosys.component.login.app.SBLoginHistoryLogHome");
					try {
						logger.logUnsuccessfulLogin(credentials, "ACCOUNT_LOCKED", attempts);
					}
					catch (RemoteException e) {
						throw new AuthenticationException(
								"an error occured while loging in the login info in SMEUserLoginMOdule logout method");
					}
				}
			}
			ConditionList condList = new ConditionList();
			condList.addCondition(OBLOSCustomer.LOGIN_ID, ConditionList.EQUAL, credentials.getLoginId(),
					ConditionList.STRING_TYPE, ConditionList.AND);
			condList.addCondition(OBLOSCustomer.PASSWORD, ConditionList.EQUAL, credentials.getPassword(),
					ConditionList.STRING_TYPE, ConditionList.AND);
			condList.addCondition(OBLOSCustomer.STATUS, ConditionList.EQUAL, LoginConstant.ACTIVE,
					ConditionList.STRING_TYPE);
			//Vector searchResult = broker.retrieve(condList, PersistentEntityType.LOS_USER);
			Vector searchResult = ADLoginHelper.authenticateAndLogin(credentials);
			System.out.println("In SME User Login Module, search result - " + searchResult);

			if (searchResult == null) {
				String excludeSuperUsers = PropertyManager.getValue(UserConstant.SUPER_USERS);

				// Change on May 11, 2007
				// Exclude superusers from getting LOCKED. i.e SA_MAKER,
				// SA_CHECKER users as listed in property will not be locked

				if ((excludeSuperUsers == null)
						|| ((excludeSuperUsers != null) && (excludeSuperUsers.toUpperCase().indexOf(
								smeCust.getLoginId().toUpperCase()) < 0))) {
					attempts++;
				}
				//By Abhijit R geting vamues of max attempts from general param.
				IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
				Date dateApplication=new Date();
				int maxAttemptsIndicator=0;
				try{
				for(int i=0;i<generalParamEntries.length;i++){
					if(generalParamEntries[i].getParamCode().equals("MAX_LOGIN_ATTEMPTS")){
						maxAttemptsIndicator=Integer.parseInt((generalParamEntries[i].getParamValue()));
					} 
				}
				}catch (NumberFormatException e) {
					maxAttemptsIndicator=PropertyManager.getInt(LoginConstant.MAX_PIN_RETRY_SUFFIX + credentials.getRealm(), 3);
				}
				//int propertyMaxAttempts=PropertyManager.getInt(LoginConstant.MAX_PIN_RETRY_SUFFIX + credentials.getRealm(), 3);
				
				/*int finalMaxAttemptIndc=0;
				if(maxAttemptsIndicator!=null){
					finalMaxAttemptIndc=maxAttemptsIndicator;
				}else{
					finalMaxAttemptIndc=propertyMaxAttempts;
				}*/
				if (attempts >= maxAttemptsIndicator) {
					smeCust.setStatus(LoginConstant.LOCKED);
					StatusModificationHandler smHandler = new StatusModificationHandler();
					smHandler.handleEvent(credentials, LoginConstant.LOCKED);
				}
				smeCust.setLoginAttempts(new Integer(attempts).toString());
				smeCust.setLastLoginIp(credentials.getLastLoginIp());
				broker.update(smeCust, PersistentEntityType.LOS_USER);
				//if (attempts >= PropertyManager.getInt(LoginConstant.MAX_PIN_RETRY_SUFFIX + credentials.getRealm(), 3)) {
				if (attempts >= maxAttemptsIndicator) {
					if (PropertyManager.getBoolean("log.logininfo.needed", false)) {
						SBLoginHistoryLog logger = (SBLoginHistoryLog) BeanController.getEJB("SBLoginHistoryLog",
								"com.integrosys.component.login.app.SBLoginHistoryLogHome");
						try {
							logger.logUnsuccessfulLogin(credentials, ICompConstant.PASSWORD_MISMATCH, attempts);
						}
						catch (RemoteException e) {
							throw new AuthenticationException(
									"an error occured while loging in the login info in SMEUserLoginMOdule logout method");
						}
					}
					throwAuthException("Max attempts reached", LoginErrorCodes.MAX_ATTEMPTS);
				}
				else {
					if (PropertyManager.getBoolean("log.logininfo.needed", false)) {
						SBLoginHistoryLog logger = (SBLoginHistoryLog) BeanController.getEJB("SBLoginHistoryLog",
								"com.integrosys.component.login.app.SBLoginHistoryLogHome");
						try {
							logger.logUnsuccessfulLogin(credentials, ICompConstant.PASSWORD_MISMATCH, attempts);
						}
						catch (RemoteException e) {
							throw new AuthenticationException(
									"an error occured while loging in the login info in SMEUserLoginMOdule logout method");
						}
					}
					throw new InvalidCredentialsException("Failed login. Attempt count" + attempts);
				}
			}
			else {
				System.out.println("Resetting Login Attempts");
				smeCust.setLoginAttempts(new Integer(LoginConstant.SME_PIN_TYPE1_RESET).toString());
				smeCust.setLastLoginIp(credentials.getLastLoginIp());
				broker.update(smeCust, PersistentEntityType.LOS_USER);
			}
			return true;
		}
		catch (PersistentEntityException pex) {
			AuthenticationException aex1 = new AuthenticationException("Object cannot be retrieved.");
			aex1.setErrorCode(LoginErrorCodes.RETRIEVAL_ERROR);
			throw aex1;
		}
		catch (PersistentStorageException pse) {
			AuthenticationException aex2 = new AuthenticationException("LDAP connection failure.");
			aex2.setErrorCode(LoginErrorCodes.CONNECTION_FAILURE);
			throw aex2;
		}
	}
	
	private void throwAuthException(String msg, String errorCode) throws AuthenticationException {
		AuthenticationException aex = new AuthenticationException(msg);
		aex.setErrorCode(errorCode);
		throw aex;
	}
}
