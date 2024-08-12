/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/ApprovalMatrixBusManagerImpl.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/03
 */
public class ApprovalMatrixBusManagerImpl extends AbstractApprovalMatrixBusManager {

	public String getApprovalMatrixEntryEntityName() {
		return IApprovalMatrixDao.ACTUAL_APPROVAL_MATRIX_ENTRY_ENTITY_NAME;
	}

	public String getApprovalMatrixGroupEntityName() {
		return IApprovalMatrixDao.ACTUAL_APPROVAL_MATRIX_GROUP_ENTITY_NAME;
	}

	public IApprovalMatrixGroup updateToWorkingCopy(IApprovalMatrixGroup workingCopy, IApprovalMatrixGroup imageCopy)
			throws ApprovalMatrixException {
		Set replicatedStageApprovalMatrixEntriesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFeedEntriesSet(), new String[] { "approvalMatrixEntryID" });

		Set mergedApprovalMatrixEntriesSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
				.getFeedEntriesSet(), replicatedStageApprovalMatrixEntriesSet, new String[] { "approvalMatrixEntryID" },
				new String[] { "approvalMatrixEntryRef", "versionTime" });

		workingCopy.getFeedEntriesSet().clear();
		if (mergedApprovalMatrixEntriesSet != null) {
			workingCopy.getFeedEntriesSet().addAll(mergedApprovalMatrixEntriesSet);
		}

		return updateApprovalMatrixGroup(workingCopy);
	}
}
