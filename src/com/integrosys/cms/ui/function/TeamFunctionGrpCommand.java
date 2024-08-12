package com.integrosys.cms.ui.function;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;

public class TeamFunctionGrpCommand extends AbstractCommand {
	private ITeamFunctionGrpProxy teamFunctionGrpProxy;

	public ITeamFunctionGrpProxy getTeamFunctionGrpProxy() {
		return teamFunctionGrpProxy;
	}

	public void setTeamFunctionGrpProxy(ITeamFunctionGrpProxy teamFunctionGrpProxy) {
		this.teamFunctionGrpProxy = teamFunctionGrpProxy;
	}
}
