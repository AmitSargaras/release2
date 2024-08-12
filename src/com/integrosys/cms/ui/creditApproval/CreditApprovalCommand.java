package com.integrosys.cms.ui.creditApproval;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;

public abstract class CreditApprovalCommand extends AbstractCommand {
	private ICreditApprovalProxy creditApprovalProxy;

	/**
	 * @return the creditApprovalProxy
	 */
	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}


	

}
