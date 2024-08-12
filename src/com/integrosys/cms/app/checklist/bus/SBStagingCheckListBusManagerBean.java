/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/SBStagingCheckListBusManagerBean.java,v 1.10 2005/01/24 02:16:23 ckchua Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the checklist bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: ckchua $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/01/24 02:16:23 $ Tag: $Name: $
 */
public class SBStagingCheckListBusManagerBean extends SBCheckListBusManagerBean  {

    /**
	 * To get the home handler for the staging checklist Entity Bean
	 * @return EBCheckListHome - the home handler for the checklist entity bean
	 */
	protected EBCheckListHome getEBCheckListHome() {
		EBCheckListHome ejbHome = (EBCheckListHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_CHECKLIST_JNDI, EBCheckListHome.class.getName());
		return ejbHome;
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBCheckListItemLocalHome getEBCheckListItemLocalHome() throws CheckListException {
		EBCheckListItemLocalHome home = (EBCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CHECKLIST_ITEM_LOCAL_JNDI, EBCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItemLocal is null!");
	}
}
