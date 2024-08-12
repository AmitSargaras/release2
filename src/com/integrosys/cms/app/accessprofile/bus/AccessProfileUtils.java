/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileUtils.java,v 1.3 2005/10/27 06:30:11 lyng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

/**
 * Provides utility methods for function access profile.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/27 06:30:11 $ Tag: $Name: $
 */
public class AccessProfileUtils {

	/**
	 * Gets possible combinations for evaluating against the database. Not quite
	 * right to be here, but just a quick and dirty placement of the logic here,
	 * consolation: in one place, easier refactoring next time.
	 * 
	 * @return
	 */
	public static Object[][] getEvaluations(String action, String event, long roleType) {

		Object[][] arr = new Object[5][3];

		arr[0][0] = action;
		arr[0][1] = event;
		arr[0][2] = new Long(roleType);

		arr[1][0] = action;
		arr[1][1] = event;
		arr[1][2] = new Long(AccessProfileConstants.ANY_ROLE_TYPE);

		arr[2][0] = action;
		arr[2][1] = AccessProfileConstants.ANY_EVENT;
		arr[2][2] = new Long(roleType);

		arr[3][0] = action;
		arr[3][1] = AccessProfileConstants.ANY_EVENT;
		arr[3][2] = new Long(AccessProfileConstants.ANY_ROLE_TYPE);

		arr[4][0] = AccessProfileConstants.ANY_ACTION;
		arr[4][1] = AccessProfileConstants.ANY_EVENT;
		arr[4][2] = new Long(roleType);

		return arr;
	}

	/**
	 * Check if the given action, event, and roleType can access a function.
	 * 
	 * @param fap of type IAccessProfile
	 * @param action of type String
	 * @param event of type String
	 * @param roleType of type long
	 * @return true if function access is allowed, otherwise false
	 */
	public static boolean isAccessAllowed(IAccessProfile fap, String action, String event, long roleType) {
		Object[][] eval = getEvaluations(action, event, roleType);
		
		for (int i = 0; i < eval.length; i++) {
			if (fap.getAction().equals(eval[i][0]) && fap.getEvent().equals(eval[i][1])
					&& (fap.getRoleType() == ((Long) eval[i][2]).longValue())) {
				return true;
			}
		}
		return false;
	}
}
