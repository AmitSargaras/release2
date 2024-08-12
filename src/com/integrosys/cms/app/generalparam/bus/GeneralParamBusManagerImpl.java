/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
* @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class GeneralParamBusManagerImpl extends AbstractGeneralParamBusManager {

	public String getGeneralParamEntryEntityName() {
		return IGeneralParamDao.ACTUAL_GENERAL_PARAM_GROUP_ENTITY_NAME;
	}

	public String getGeneralParamGroupEntityName() {
		return IGeneralParamDao.ACTUAL_GENERAL_PARAM_GROUP_ENTITY_NAME;
	}

	public IGeneralParamGroup updateToWorkingCopy(IGeneralParamGroup workingCopy, IGeneralParamGroup imageCopy)
			throws GeneralParamGroupException {
		Set replicatedStageGeneralParamFeedEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "paramID" });

		Set mergedGeneralParamFeedEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageGeneralParamFeedEntriesSet, new String[] { "generalParamEntryRef" },
				new String[] { "paramID", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedGeneralParamFeedEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedGeneralParamFeedEntriesSet);
		}

		return updateGeneralParamGroup(workingCopy);
	}
}
