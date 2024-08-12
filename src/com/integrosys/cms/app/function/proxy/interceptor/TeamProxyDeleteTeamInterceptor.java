package com.integrosys.cms.app.function.proxy.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.function.proxy.ITeamFunctionGrpProxy;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;

/**
 * <p>
 * Method interceptor to intercept team proxy action on the checker approve
 * delete team, so that, team function transaction can be closed correctly.
 * 
 * @author Chong Jun Yong
 * 
 */
public class TeamProxyDeleteTeamInterceptor implements MethodInterceptor {

	private ITeamFunctionGrpProxy teamFunctionProxy;

	public void setTeamFunctionProxy(ITeamFunctionGrpProxy teamFunctionProxy) {
		this.teamFunctionProxy = teamFunctionProxy;
	}

	public ITeamFunctionGrpProxy getTeamFunctionProxy() {
		return teamFunctionProxy;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();

		if (method.getName().equals("checkerApproveDeleteTeam")) {
			ITrxContext trxContext = (ITrxContext) arguments[0];
			ITeamTrxValue teamTrxValue = (ITeamTrxValue) arguments[1];

			ITeam actualTeam = teamTrxValue.getTeam();
			long teamId = actualTeam.getTeamID();

			ITeamFunctionGrpTrxValue teamFunctionGroupTrxValue = getTeamFunctionProxy().getTeamFunctionGrpByTeamId(
					trxContext, teamId);

			Object retValue = null;
			try {
				retValue = invocation.proceed();
			}
			catch (InvocationTargetException ex) {
				throw ex.getCause();
			}

			if (teamFunctionGroupTrxValue != null) {
				DefaultLogger.info(this,
						"closing team function group initiate from team proxy check approve delete, workflow value ["
								+ teamFunctionGroupTrxValue + "]");
				getTeamFunctionProxy().systemCloseTeamFunctionGroupTransaction(trxContext, teamFunctionGroupTrxValue);
			}

			return retValue;
		}

		try {
			return invocation.proceed();
		}
		catch (InvocationTargetException ex) {
			throw ex.getCause();
		}

	}
}
