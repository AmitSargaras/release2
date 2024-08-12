package com.integrosys.cms.ui.bizstructure;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;

/**
 * Abstract implementation of {@link AbstractCommand} provided for the team
 * maintenance, providing the service bean (which to be injected) used by the
 * subclasses.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractTeamCommand extends AbstractCommand {

	private ICMSTeamProxy cmsTeamProxy;

	public final void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}

	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}

	public abstract String[][] getParameterDescriptor();

	public abstract String[][] getResultDescriptor();

}
