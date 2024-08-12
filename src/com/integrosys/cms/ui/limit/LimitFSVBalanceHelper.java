/*
 * Created on Apr 4, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.limit;

import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitFSVBalanceHelper {
	private List lastLimitList = null;

	public LimitFSVBalanceHelper() {
	}

	public List getFSVBalForMainborrowLimit(String limitId) {
		try {
			SBLimitManager manager = (SBLimitManager) (BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
					SBLimitManagerHome.class.getName()));
			return manager.getFSVBalForMainborrowLimit(limitId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List getFSVBalForCoborrowLimit(String limitId) {
		try {
			SBLimitManager manager = (SBLimitManager) (BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
					SBLimitManagerHome.class.getName()));
			return manager.getFSVBalForCoborrowLimit(limitId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
