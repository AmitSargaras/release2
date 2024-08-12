/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/PropertyIndexFeedBusManagerFactory.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 */
public class PropertyIndexFeedBusManagerFactory {

	public static IPropertyIndexFeedBusManager getActualPropertyIndexFeedBusManager() {
		return new PropertyIndexFeedBusManagerImpl();
	}

	public static IPropertyIndexFeedBusManager getStagingPropertyIndexFeedBusManager() {
		return new PropertyIndexFeedBusManagerStagingImpl();
	}
}
