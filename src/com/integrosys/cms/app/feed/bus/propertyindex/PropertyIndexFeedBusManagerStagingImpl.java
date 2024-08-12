/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/PropertyIndexFeedBusManagerStagingImpl.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 */
public class PropertyIndexFeedBusManagerStagingImpl extends PropertyIndexFeedBusManagerImpl {

	/**
	 * Helper method to get the staging bus manager remote interface.
	 * @return The bus manager remote interface.
	 */
	protected SBPropertyIndexFeedBusManager getSbPropertyIndexFeedBusManager() {
		return (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI_STAGING,
				SBPropertyIndexFeedBusManagerHome.class.getName());
	}
}
