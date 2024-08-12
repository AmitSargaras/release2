/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedGroupStagingBean.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.util.Collection;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 * @ejb.bean name="EBPropertyIndexFeedGroupStaging"
 *           jndi-name="EBPropertyIndexFeedGroupStagingHome"
 *           local-jndi-name="EBPropertyIndexFeedGroupStagingLocalHome"
 *           view-type="both" type="CMP" reentrant="false" cmp-version="2.x"
 *           schema="EBPropertyIndexFeedGroupStaging"
 *           primkey-field="cmpPropertyIndexFeedGroupID"
 * @ejb.ejb-ref ejb-name="EBPropertyIndexFeedEntryStaging" view-type="local"
 *              ref-name="EBPropertyIndexFeedEntryStaging"
 * @ejb.persistence table-name="CMS_STAGE_FEED_GROUP"
 * @ejb.transaction type="Required"
 */
public abstract class EBPropertyIndexFeedGroupStagingBean extends EBPropertyIndexFeedGroupBean {

	/**
	 * 
	 * @return
	 * @ejb.relation 
	 *               name="PropertyIndexFeedGroup-PropertyIndexFeedEntries-Staging"
	 *               role-name=
	 *               "PropertyIndexFeedGroup-Has-PropertyIndexFeedEntries"
	 *               target-ejb="EBPropertyIndexFeedEntryStaging"
	 *               target-role-name
	 *               ="PropertyIndexFeedEntry-BelongsTo-PropertyIndexFeedGroup"
	 *               target-cascade-delete="yes"
	 * @weblogic.target-column-map foreign-key-column="FEED_GROUP_ID"
	 *                             key-column="FEED_GROUP_ID"
	 */
	public abstract Collection getCmrPropertyIndexFeedEntry();

	/**
	 * Helper method to get EB Local Home for PropertyIndexFeedEntry. Overrides
	 * the superclass method.
	 */
	protected EBPropertyIndexFeedEntryLocalHome getEBPropertyIndexFeedEntryLocalHome()
			throws PropertyIndexFeedGroupException {
		EBPropertyIndexFeedEntryLocalHome home = (EBPropertyIndexFeedEntryLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_ENTRY_LOCAL_JNDI_STAGING,
				EBPropertyIndexFeedEntryLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new PropertyIndexFeedGroupException("EBPropertyIndexFeedEntryItemLocal is null!");
	}
}
