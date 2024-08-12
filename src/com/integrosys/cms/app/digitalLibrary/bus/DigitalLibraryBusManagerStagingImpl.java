/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/DigitalLibraryBusManagerStagingImpl.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public class DigitalLibraryBusManagerStagingImpl extends AbstractDigitalLibraryBusManager {

	public IDigitalLibraryGroup createDigitalLibraryGroup(IDigitalLibraryGroup group) throws DigitalLibraryException {
		group = getDigitalLibraryDao().createDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getDigitalLibraryDao().updateDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);
	}

	public IDigitalLibraryGroup updateDigitalLibraryGroup(IDigitalLibraryGroup group) throws DigitalLibraryException {
		group = getDigitalLibraryDao().updateDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getDigitalLibraryDao().updateDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IDigitalLibraryGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "digitalLibraryEntryRef", Long.class, "digitalLibraryEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getDigitalLibraryEntryEntityName() {
		return IDigitalLibraryDao.STAGE_DIGITAL_LIBRARY_ENTRY_ENTITY_NAME;
	}

	public String getDigitalLibraryGroupEntityName() {
		return IDigitalLibraryDao.STAGE_DIGITAL_LIBRARY_GROUP_ENTITY_NAME;
	}

	public IDigitalLibraryGroup updateToWorkingCopy(IDigitalLibraryGroup workingCopy, IDigitalLibraryGroup imageCopy)
			throws DigitalLibraryException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
}
