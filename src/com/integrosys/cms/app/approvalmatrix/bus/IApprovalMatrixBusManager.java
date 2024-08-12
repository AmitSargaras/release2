/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IApprovalMatrixBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

/**
 * <p>
 * Interface of business manager for bond feed
 * 
 * <p>
 * <b>NOTE</b> All calling to persistence storage should come through here, be
 * it JDBC, ORM, or even Entity Bean.
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/09/03
 */
public interface IApprovalMatrixBusManager {

	/**
	 * Gets all the entries in the bond group.
	 * @param id Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws ApprovalMatrixException when there are errors in getting the
	 *         entries.
	 */
	public IApprovalMatrixGroup getApprovalMatrixGroup(long id) throws ApprovalMatrixException;

	/**
	 * Gets all the entries in the bond group.
	 * @param groupType Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws ApprovalMatrixException when there are errors in getting the
	 *         entries.
	 */
	public IApprovalMatrixGroup getApprovalMatrixGroup(String groupType) throws ApprovalMatrixException;

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws ApprovalMatrixException when there are errors in creating the
	 *         group.
	 */
	public IApprovalMatrixGroup createApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException;

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws ApprovalMatrixException when there are errors in updating the
	 *         group.
	 */
	public IApprovalMatrixGroup updateApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException;

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws ApprovalMatrixException when there are errors in deleting the
	 *         group.
	 */
	public IApprovalMatrixGroup deleteApprovalMatrixGroup(IApprovalMatrixGroup group) throws ApprovalMatrixException;

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws ApprovalMatrixEntryException on errors.
	 */
	public IApprovalMatrixEntry getApprovalMatrixEntryByRic(String ric) throws ApprovalMatrixEntryException;

	/**
	 * <p>
	 * Update from a image copy to a working copy, will be used for updating
	 * actual copy using staging copy.
	 * 
	 * <p>
	 * This normally will get called from transaction engine, operation which is
	 * checker approve update.
	 * 
	 * @param workingCopy working copy, which in cms context is actual copy
	 * @param imageCopy image copy, which in cms context is staging copy
	 * @return the updated working copy
	 * @throws ApprovalMatrixException
	 */
	public IApprovalMatrixGroup updateToWorkingCopy(IApprovalMatrixGroup workingCopy, IApprovalMatrixGroup imageCopy)
			throws ApprovalMatrixException;

}
