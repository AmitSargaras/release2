/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CompareUtil.java,v 1.3 2005/09/23 05:22:12 whuang Exp $
 */

package com.integrosys.cms.ui.limit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/23 05:22:12 $ Tag: $Name: $
 */

public class CompareUtil {
	/**
	 * check object changes
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean isChanged(Object obj1, Object obj2) {
		if (((obj1 == null) && (obj2 != null)) || ((obj1 != null) && (obj2 == null))) {
			return true;
		}
		else if ((obj1 != null) && (obj2 != null)) {
			if (!obj1.equals(obj2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check boolean changes
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean isChanged(boolean value1, boolean value2) {
		if (value1 != value2) {
			return true;
		}
		return false;
	}

	/**
	 * check float changes
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean isChanged(float value1, float value2) {
		if (value1 != value2) {
			return true;
		}
		return false;
	}

	/**
	 * check maker in same team
	 * @param team
	 * @param userID
	 * @return
	 */
	public static boolean haveSameCountryCode(ITeam currentTeam, long teamID) throws CommandProcessingException {
		boolean haveSameCountryCode = false;
		try {
			ITeam team = new CMSTeamProxy().getTeam(teamID);
			String[] countryCodes = team.getCountryCodes();
			String[] currentCountryCodes = currentTeam.getCountryCodes();
			if ((currentCountryCodes != null) && (countryCodes != null)) {
				for (int x = 0; x < currentCountryCodes.length; x++) {
					for (int y = 0; y < countryCodes.length; y++) {
						if (currentCountryCodes[x].equals(countryCodes[y])) {
							haveSameCountryCode = true;
							break;
						}
					}
				}
			}
			return haveSameCountryCode;
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.limit.CompareUtil", e);
			throw new CommandProcessingException();
		}
	}
}
