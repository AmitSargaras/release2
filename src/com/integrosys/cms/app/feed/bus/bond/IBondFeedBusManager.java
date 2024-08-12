/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;

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
public interface IBondFeedBusManager {

	/**
	 * Gets all the entries in the bond group.
	 * @param id Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws BondFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IBondFeedGroup getBondFeedGroup(long id) throws BondFeedGroupException;

	/**
	 * Gets all the entries in the bond group.
	 * @param groupType Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws BondFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IBondFeedGroup getBondFeedGroup(String groupType) throws BondFeedGroupException;

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws BondFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IBondFeedGroup createBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException;

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws BondFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IBondFeedGroup updateBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException;

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws BondFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IBondFeedGroup deleteBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException;

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws BondFeedEntryException on errors.
	 */
	public IBondFeedEntry getBondFeedEntryByRic(String ric) throws BondFeedEntryException;

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
	 * @throws BondFeedGroupException
	 */
	public IBondFeedGroup updateToWorkingCopy(IBondFeedGroup workingCopy, IBondFeedGroup imageCopy)
			throws BondFeedGroupException;
	
	
	/**
	 * Method added for Marketable Securities(Bond)
	 * 
	 */
	IBondFeedEntry getBondFeedEntry(String bondCode) throws BondFeedEntryException;
	
	//For File Upload add by govind on 24-Aug-2011	
	boolean isPrevFileUploadPending() throws BondFeedEntryException;
	int insertBondFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws BondFeedEntryException;
	IFileMapperId insertBondFeedEntry(IFileMapperId fileId, IBondFeedGroupTrxValue idxTrxValue)throws BondFeedEntryException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws BondFeedEntryException;
	IFileMapperId getInsertFileById(long id) throws BondFeedEntryException,TrxParameterException,TransactionException;
	List getAllStageBondFeedEntry(String searchBy, String login)throws BondFeedEntryException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws BondFeedEntryException,TrxParameterException,TransactionException;
	List insertActualBondFeedEntry(String sysId)throws BondFeedEntryException;
	IBondFeedGroup insertBondFeedEntry(IBondFeedGroup bondFeedEntry)throws BondFeedEntryException;
	void updateBondFeedEntryItem(List bondFeedEntryList) throws BondFeedGroupException,TrxParameterException,TransactionException;
	boolean isBondCodeExist(List bondCodeList) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws BondFeedGroupException, TrxParameterException, TransactionException;
	//end File Upload add by govind on 24-Aug-2011
	
	public boolean isExistBondCode(String bondCode) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
