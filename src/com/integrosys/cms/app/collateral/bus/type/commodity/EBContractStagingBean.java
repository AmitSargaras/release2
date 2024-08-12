/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBContractStagingBean.java,v 1.5 2004/10/08 12:09:15 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: May 3, 2004 Time: 4:19:26
 * PM To change this template use File | Settings | File Templates.
 */
public abstract class EBContractStagingBean extends EBContractBean {
	public EBContractStagingBean() {
		isStaging = true;
	}
}
