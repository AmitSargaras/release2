package com.integrosys.cms.app.function.proxy;

public class TeamFunctionGrpProxyFactory {
	public static ITeamFunctionGrpProxy getProxy() {
		return new TeamFunctionGrpProxyImpl();
	}
}
