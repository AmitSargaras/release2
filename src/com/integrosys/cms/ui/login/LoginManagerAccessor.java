package com.integrosys.cms.ui.login;

import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.base.businfra.authentication.AuthenticationManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * Provide service bean to be used by concrete Login Manager.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class LoginManagerAccessor {
	private JdbcTemplate authenticationJdbcTemplate;

	private AuthenticationManager authenticationManager;

	private ICMSTeamProxy cmsTeamProxy;

	private ICommonUserProxy userProxy;

	private ITeamFunctionGrpProxy teamFunctionGrpProxy;

	private boolean requiredVerifyForcePasswordChange;

	/**
	 * @return the authenticationJdbcTemplate
	 */
	public JdbcTemplate getAuthenticationJdbcTemplate() {
		return authenticationJdbcTemplate;
	}

	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @return the cmsTeamProxy
	 */
	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}

	public ITeamFunctionGrpProxy getTeamFunctionGrpProxy() {
		return teamFunctionGrpProxy;
	}

	/**
	 * @return the userProxy
	 */
	public ICommonUserProxy getUserProxy() {
		return userProxy;
	}

	public boolean isRequiredVerifyForcePasswordChange() {
		return requiredVerifyForcePasswordChange;
	}

	/**
	 * @param authenticationJdbcTemplate the authenticationJdbcTemplate to set
	 */
	public void setAuthenticationJdbcTemplate(JdbcTemplate authenticationJdbcTemplate) {
		this.authenticationJdbcTemplate = authenticationJdbcTemplate;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @param cmsTeamProxy the cmsTeamProxy to set
	 */
	public void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}

	public void setRequiredVerifyForcePasswordChange(boolean requiredVerifyForcePasswordChange) {
		this.requiredVerifyForcePasswordChange = requiredVerifyForcePasswordChange;
	}

	public void setTeamFunctionGrpProxy(ITeamFunctionGrpProxy teamFunctionGrpProxy) {
		this.teamFunctionGrpProxy = teamFunctionGrpProxy;
	}

	/**
	 * @param userProxy the userProxy to set
	 */
	public void setUserProxy(ICommonUserProxy userProxy) {
		this.userProxy = userProxy;
	}
}
