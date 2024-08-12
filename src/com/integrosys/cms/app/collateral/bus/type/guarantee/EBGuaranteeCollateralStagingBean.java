package com.integrosys.cms.app.collateral.bus.type.guarantee;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.EBLineDetailLocalHome;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.EBFeeDetailsLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBGuaranteeCollateralStagingBean extends EBGuaranteeCollateralBean {

	protected EBFeeDetailsLocalHome getEBFeeDetailsLocalHome() {
		EBFeeDetailsLocalHome ejbHome = (EBFeeDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FEEDETAILS_STAGE_LOCAL_JNDI, EBFeeDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFeeDetailsLocalHome is Null!");
		}

		return ejbHome;
	}
	
	protected EBLineDetailLocalHome getEBLocalLineDetail() throws CollateralException {
		EBLineDetailLocalHome home = (EBLineDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LINE_DETAIL_LOCAL_JNDI_STAGING, EBLineDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBLineDetailLocalHome is null!");
		}
	}
}
