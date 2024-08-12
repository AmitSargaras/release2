/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/ApprovalMatrixBusManagerStagingImpl.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public class ApprovalMatrixBusManagerStagingImpl extends AbstractApprovalMatrixBusManager {

	public IApprovalMatrixGroup createApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException {
		group = getApprovalMatrixDao().createApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getApprovalMatrixDao().updateApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);
	}

	public IApprovalMatrixGroup updateApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException {
		group = getApprovalMatrixDao().updateApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);

		copyPrimaryKeyToReferenceId(group);

		return getApprovalMatrixDao().updateApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);
	}

	protected void copyPrimaryKeyToReferenceId(IApprovalMatrixGroup group) {
		Set updateGroupWithFeedEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(group
				.getFeedEntriesSet(), "approvalMatrixEntryRef", Long.class, "approvalMatrixEntryID");
		group.getFeedEntriesSet().clear();
		if (updateGroupWithFeedEntryRefSet != null) {
			group.getFeedEntriesSet().addAll(updateGroupWithFeedEntryRefSet);
		}
	}

	public String getApprovalMatrixEntryEntityName() {
		return IApprovalMatrixDao.STAGE_APPROVAL_MATRIX_ENTRY_ENTITY_NAME;
	}

	public String getApprovalMatrixGroupEntityName() {
		return IApprovalMatrixDao.STAGE_APPROVAL_MATRIX_GROUP_ENTITY_NAME;
	}

	public IApprovalMatrixGroup updateToWorkingCopy(IApprovalMatrixGroup workingCopy, IApprovalMatrixGroup imageCopy)
			throws ApprovalMatrixException {
		throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
	}
}
