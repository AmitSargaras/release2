package com.integrosys.cms.app.limit.bus;

import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;



public abstract class EBLimitCovenantStagingBean extends EBLimitCovenantBean {
	public EBLimitCovenantStagingBean() {
	}

	protected EntityContext _context = null;
}