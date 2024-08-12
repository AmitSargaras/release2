/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerStagingImpl.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class GeneralParamBusManagerStagingImpl extends AbstractGeneralParamBusManager {

	public IGeneralParamGroup createGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException {
		group = getGeneralParamDao().createGeneralParamGroup(getGeneralParamGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getGeneralParamDao().updateGeneralParamGroup(getGeneralParamGroupEntityName(), group);
	}

	public IGeneralParamGroup updateGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException {
		group = getGeneralParamDao().updateGeneralParamGroup(getGeneralParamGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getGeneralParamDao().updateGeneralParamGroup(getGeneralParamGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IGeneralParamGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "generalParamEntryRef", Long.class, "paramID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getGeneralParamEntryEntityName() {
		return IGeneralParamDao.STAGE_GENERAL_PARAM_ENTRY_ENTITY_NAME;
	}

	public String getGeneralParamGroupEntityName() {
		return IGeneralParamDao.STAGE_GENERAL_PARAM_GROUP_ENTITY_NAME;
	}

	public IGeneralParamGroup updateToWorkingCopy(IGeneralParamGroup workingCopy, IGeneralParamGroup imageCopy)
			throws GeneralParamGroupException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
}
