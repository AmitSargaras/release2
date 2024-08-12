/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/DigitalLibraryBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/03
 */
public class DigitalLibraryBusManagerImpl extends AbstractDigitalLibraryBusManager {

	public String getDigitalLibraryEntryEntityName() {
		return IDigitalLibraryDao.ACTUAL_DIGITAL_LIBRARY_ENTRY_ENTITY_NAME;
	}

	public String getDigitalLibraryGroupEntityName() {
		return IDigitalLibraryDao.ACTUAL_DIGITAL_LIBRARY_GROUP_ENTITY_NAME;
	}

	public IDigitalLibraryGroup updateToWorkingCopy(IDigitalLibraryGroup workingCopy, IDigitalLibraryGroup imageCopy)
			throws DigitalLibraryException {
		Set replicatedStageDigitalLibraryEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "digitalLibraryEntryID" });

		Set mergedDigitalLibraryEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageDigitalLibraryEntriesSet, new String[] { "digitalLibraryEntryID" },
				new String[] { "digitalLibraryEntryRef", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedDigitalLibraryEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedDigitalLibraryEntriesSet);
		}

		return updateDigitalLibraryGroup(workingCopy);
	}
}
