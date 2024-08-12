package com.integrosys.cms.app.propertyparameters.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplateHome;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 31, 2007 Time: 3:32:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBStgPropertyParametersBean extends SBPropertyParametersBean {

	public EBPropertyParametersHome getEBPropertyParametersHome() {
		EBPropertyParametersHome home = (EBPropertyParametersHome) BeanController.getEJBHome(
				"EBStgPropertyParametersHome", EBPropertyParametersHome.class.getName());
		return home;
	}

	/**
	 * Get home interface of EBMFTemplate.
	 * 
	 * @return EBMFTemplate home interface
	 * @throws PropertyParametersException on errors encountered
	 */
	protected EBMFTemplateHome getEBMFTemplateHome() throws PropertyParametersException {
		EBMFTemplateHome ejbHome = (EBMFTemplateHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_MF_TEMPLATE_STAGING_JNDI, EBMFTemplateHome.class.getName());

		if (ejbHome == null) {
			throw new PropertyParametersException("EBMFTemplateHome for staging is null!");
		}

		return ejbHome;
	}
}
