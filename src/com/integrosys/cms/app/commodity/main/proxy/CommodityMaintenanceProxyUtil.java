/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/CommodityMaintenanceProxyUtil.java,v 1.2 2004/06/04 04:53:42 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import java.util.ArrayList;

import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 17, 2004 Time:
 * 11:22:20 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMaintenanceProxyUtil {

	public static final IProfile[] filterProfilesByCommodityCategory(IProfile[] inProfiles, String inCategory) {
		IProfile[] filteredProfiles = null;
		if (inProfiles != null) {
			ArrayList listOfFilteredProfiles = new ArrayList();
			for (int i = 0; i < inProfiles.length; i++) {
				IProfile iProfile = inProfiles[i];
				if (iProfile.getCategory().equals(inCategory)) {
					listOfFilteredProfiles.add(iProfile);
				}
			}

			filteredProfiles = new IProfile[listOfFilteredProfiles.size()];
			filteredProfiles = (IProfile[]) listOfFilteredProfiles.toArray(filteredProfiles);
		}
		return filteredProfiles;
	}

}
