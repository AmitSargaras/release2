/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/CMSNotificationMapper.java,v 1.2 2005/11/11 07:16:30 whuang Exp $
 */
package com.integrosys.cms.ui.notification;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.notification.bus.OBCMSNotification;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/11/11 07:16:30 $ Tag: $Name: $
 */

public class CMSNotificationMapper extends AbstractCommonMapper {

	/**
	 * Default Construtor
	 */
	public CMSNotificationMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser",
				GLOBAL_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		DefaultLogger.debug(this, "userID: ------------------------ " + user.getUserID());
		NotificationForm nForm = (NotificationForm) cForm;
		OBCMSNotification notification = new OBCMSNotification();
		notification.setUserID(user.getUserID());
		notification.setNotificationIDs(nForm.getNotificationIDs());
		return notification;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		return null;
	}
}
