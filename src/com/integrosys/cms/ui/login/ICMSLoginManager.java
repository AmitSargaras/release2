package com.integrosys.cms.ui.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.integrosys.base.businfra.login.ICredentials;
import com.integrosys.base.businfra.login.ILoginInfo;
import com.integrosys.component.login.ui.ILoginManager;
import com.integrosys.component.login.ui.LoginProcessException;

public interface ICMSLoginManager extends ILoginManager {

	public ILoginInfo executeLoginProcess(ICredentials credentials, HttpServletRequest request,
			HttpServletResponse response, String teamMemberShipID, String membershipID) throws LoginProcessException;

	/**
	 * @return whether cms login manager require verifying force password change
	 *         after login
	 */
	public boolean requiredVerifyForcePasswordChange();

}
