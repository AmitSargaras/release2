/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/AbstractUnitTrustFeedBusManager.java,v 1.1 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 */
public abstract class AbstractUnitTrustFeedBusManager implements IUnitTrustFeedBusManager {

	public IUnitTrustDao unitTrustDao;

	public void setUnitTrustDao(IUnitTrustDao unitTrustDao) {
		this.unitTrustDao = unitTrustDao;
	}

	protected IUnitTrustDao getUnitTrustDao() {
		return unitTrustDao;
	}

	public IUnitTrustFeedGroup getUnitTrustFeedGroup(long id) throws UnitTrustFeedGroupException {
		return getUnitTrustDao().getUnitTrustFeedGroupByPrimaryKey(getUnitTrustFeedGroupEntityName(), new Long(id));
	}

	public IUnitTrustFeedGroup getUnitTrustFeedGroup(String groupType, String subType)
			throws UnitTrustFeedGroupException {
		return getUnitTrustDao().getUnitTrustFeedGroupByTypeAndSubType(getUnitTrustFeedGroupEntityName(), groupType,
				subType);
	}

	public IUnitTrustFeedGroup createUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException {
		return getUnitTrustDao().createUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);
	}

	public IUnitTrustFeedGroup updateUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException {
		return getUnitTrustDao().updateUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);
	}

	public IUnitTrustFeedGroup deleteUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException {
		getUnitTrustDao().deleteUnitTrustFeedGroup(getUnitTrustFeedGroupEntityName(), group);

		return group;
	}

	public IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String ric) throws UnitTrustFeedEntryException {
		return getUnitTrustDao().getUnitTrustFeedEntryByRic(getUnitTrustFeedEntryEntityName(), ric);
	}

	public abstract String getUnitTrustFeedGroupEntityName();

	public abstract String getUnitTrustFeedEntryEntityName();

}
