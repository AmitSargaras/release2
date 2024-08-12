package com.integrosys.cms.app.recurrent.bus;

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
public class SBStagingRecurrentBusManagerBean extends SBRecurrentBusManagerBean {

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListHome getEBRecurrentCheckListHome() {
		EBRecurrentCheckListHome ejbHome = (EBRecurrentCheckListHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_RECURRENT_CHECKLIST_JNDI, EBRecurrentCheckListHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListItemLocalHome getEBRecurrentCheckListItemHome() throws RecurrentException {
		EBRecurrentCheckListItemLocalHome home = (EBRecurrentCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI,
				EBRecurrentCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListItemLocal is null!");
	}

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBRecurrentCheckListSubItemLocalHome getEBRecurrentCheckListSubItemHome() {
		EBRecurrentCheckListSubItemLocalHome ejbHome = (EBRecurrentCheckListSubItemLocalHome) BeanController
				.getEJBHome(ICMSJNDIConstant.EB_STAGING_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI,
						EBRecurrentCheckListSubItemLocalHome.class.getName());
		return ejbHome;
	}

	// cr 26
	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBConvenantLocalHome getEBConvenantHome() throws RecurrentException {
		EBConvenantLocalHome home = (EBConvenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CONVENANT_LOCAL_JNDI, EBConvenantLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListItemLocal is null!");
	}

	/**
	 * To get the home handler for the recurrent checklist Entity Bean
	 * @return EBRecurrentCheckListHome - the home handler for the checklist
	 *         entity bean
	 */
	protected EBConvenantSubItemLocalHome getEBConvenantSubItemHome() {
		EBConvenantSubItemLocalHome ejbHome = (EBConvenantSubItemLocalHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_CONVENANT_SUB_ITEM_LOCAL_JNDI, EBConvenantSubItemLocalHome.class.getName());
		return ejbHome;
	}


}
