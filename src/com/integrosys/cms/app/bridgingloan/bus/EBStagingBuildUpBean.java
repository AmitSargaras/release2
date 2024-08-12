package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 27, 2007 Time: 3:28:13 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class EBStagingBuildUpBean extends EBBuildUpBean {
	/**
	 * Method to get EB Local Home for the Sales Proceeds
	 */
	protected EBSalesProceedsLocalHome getEBSalesProceedsLocalHome() throws BridgingLoanException {
		EBSalesProceedsLocalHome home = (EBSalesProceedsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_SALES_PROCEEDS_LOCAL_JNDI, EBSalesProceedsLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBBLStagingSalesProceedsLocalHome is null!");
	}
}