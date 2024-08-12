package com.integrosys.cms.ui.userrole;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.userrole.proxy.ICMSUserRoleProxy;

/**
 * Abstract implementation of {@link AbstractCommand} provided for the team
 * maintenance, providing the service bean (which to be injected) used by the
 * subclasses.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractUserRoleCommand extends AbstractCommand {

	private ICMSUserRoleProxy cmsUserRoleProxy;

	public final void setCmsUserRoleProxy(ICMSUserRoleProxy cmsUserRoleProxy) {
		this.cmsUserRoleProxy = cmsUserRoleProxy;
	}

	public ICMSUserRoleProxy getCmsUserRoleProxy() {
		return cmsUserRoleProxy;
	}

	public abstract String[][] getParameterDescriptor();

	public abstract String[][] getResultDescriptor();

}
