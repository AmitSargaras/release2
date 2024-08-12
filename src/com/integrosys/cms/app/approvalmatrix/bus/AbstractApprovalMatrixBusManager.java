package com.integrosys.cms.app.approvalmatrix.bus;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public abstract class AbstractApprovalMatrixBusManager implements IApprovalMatrixBusManager {
	public IApprovalMatrixDao approvalMatrixDao;

	/**
	 * @return the approvalMatrixDao
	 */
	public IApprovalMatrixDao getApprovalMatrixDao() {
		return approvalMatrixDao;
	}

	/**
	 * @param approvalMatrixDao the approvalMatrixDao to set
	 */
	public void setApprovalMatrixDao(IApprovalMatrixDao approvalMatrixDao) {
		this.approvalMatrixDao = approvalMatrixDao;
	}

	public IApprovalMatrixGroup getApprovalMatrixGroup(long id) throws ApprovalMatrixException {
		return getApprovalMatrixDao().getApprovalMatrixGroupByPrimaryKey(getApprovalMatrixGroupEntityName(), new Long(id));
	}

	public IApprovalMatrixGroup getApprovalMatrixGroup(String groupType) throws ApprovalMatrixException {
		return getApprovalMatrixDao().getApprovalMatrixGroupByGroupType(getApprovalMatrixGroupEntityName(), groupType);
	}

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws ApprovalMatrixException when there are errors in creating the
	 *         group.
	 */
	public IApprovalMatrixGroup createApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException {
		return getApprovalMatrixDao().createApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);
	}

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws ApprovalMatrixException when there are errors in updating the
	 *         group.
	 */
	public IApprovalMatrixGroup updateApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException {
		return getApprovalMatrixDao().updateApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);
	}

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws ApprovalMatrixException when there are errors in deleting the
	 *         group.
	 */
	public IApprovalMatrixGroup deleteApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException {
		getApprovalMatrixDao().deleteApprovalMatrixGroup(getApprovalMatrixGroupEntityName(), group);

		return group;
	}

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws ApprovalMatrixEntryException on errors.
	 */
	public IApprovalMatrixEntry getApprovalMatrixEntryByRic(String ric) throws ApprovalMatrixEntryException {
		return getApprovalMatrixDao().getApprovalMatrixEntryByRic(getApprovalMatrixEntryEntityName(), ric);
	}

	public abstract String getApprovalMatrixGroupEntityName();

	public abstract String getApprovalMatrixEntryEntityName();

}
