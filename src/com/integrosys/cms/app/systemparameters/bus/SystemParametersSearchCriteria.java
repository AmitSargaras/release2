/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/systemparameters/bus/SystemParametersSearchCriteria.java,v 1.2 2003/09/23 08:03:38 sathish Exp $
 */

package com.integrosys.cms.app.systemparameters.bus;

import com.integrosys.cms.app.systemparameters.Constants;
import com.integrosys.component.commondata.app.bus.BusinessParameterGroupSearchCriteria;
import com.integrosys.component.commondata.app.bus.BusinessParameterSearchCriteria;

/**
 * SystemParametersSearchCriteria
 * @author $Author: sathish $
 * @version $
 * @since Aug 13, 2003 9:02:19 AM$
 */
public class SystemParametersSearchCriteria extends BusinessParameterGroupSearchCriteria {
	public SystemParametersSearchCriteria() {
		// SystemParameters is one of the BusinessParameterGroups with a
		// specific groupCode
		setGroupCode(Constants.SYSTEM_PARAMS_GROUP_CODE);
		setAllGroups(false);

		// include only ACTIVE parameters
		setParameterCritera(new BusinessParameterSearchCriteria());
	}

	public SystemParametersSearchCriteria(String code) {
		// SystemParameters is one of the BusinessParameterGroups with a
		// specific groupCode
		setGroupCode(code);
		setAllGroups(false);

		// include only ACTIVE parameters
		setParameterCritera(new BusinessParameterSearchCriteria());
	}
}
