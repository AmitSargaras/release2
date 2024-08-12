/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/SBPropertyIndexFeedBusManagerStagingBean.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 * @ejb.bean name="SBPropertyIndexFeedBusManagerStaging"
 *           jndi-name="SBPropertyIndexFeedBusManagerHomeStaging"
 *           local-jndi-name="SBPropertyIndexFeedBusManagerLocalHomeStaging"
 *           view-type="remote" type="Stateless"
 */
public class SBPropertyIndexFeedBusManagerStagingBean extends SBPropertyIndexFeedBusManagerBean {

	protected EBPropertyIndexFeedEntryHome getEbPropertyIndexFeedEntryHome() {
		return (EBPropertyIndexFeedEntryHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_ENTRY_JNDI_STAGING, EBPropertyIndexFeedEntryHome.class
						.getName());
	}

	protected EBPropertyIndexFeedGroupHome getEbPropertyIndexFeedGroupHome() {
		return (EBPropertyIndexFeedGroupHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_GROUP_JNDI_STAGING, EBPropertyIndexFeedGroupHome.class
						.getName());
	}
}
