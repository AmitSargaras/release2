package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRefHome;

public abstract class EBLimitSysXRefStagingBean extends EBLimitSysXRefBean {
	public EBLimitSysXRefStagingBean() {
	}

	protected EBCustomerSysXRefHome getEBHomeCustomerSysXRef() throws LimitException {
		EBCustomerSysXRefHome home = (EBCustomerSysXRefHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_JNDI_STAGING, EBCustomerSysXRefHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCustomerSysXRefHome is null!");
		}
	}

}