/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * <p>
 * Interface of business manager for bond feed
 * 
 * <p>
 * <b>NOTE</b> All calling to persistence storage should come through here, be
 * it JDBC, ORM, or even Entity Bean.
 * 
* @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsFeedBusManager {

	/**
	 * Gets all the entries in the bond group.
	 * @param id Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws MutualFundsFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IMutualFundsFeedGroup getMutualFundsFeedGroup(long id) throws MutualFundsFeedGroupException;

	/**
	 * Gets all the entries in the bond group.
	 * @param groupType Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws MutualFundsFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IMutualFundsFeedGroup getMutualFundsFeedGroup(String groupType) throws MutualFundsFeedGroupException;

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IMutualFundsFeedGroup createMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException;

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IMutualFundsFeedGroup updateMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException;

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IMutualFundsFeedGroup deleteMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException;

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws MutualFundsFeedEntryException on errors.
	 */
	public IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String ric) throws MutualFundsFeedEntryException;

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
	 * @throws MutualFundsFeedGroupException
	 */
	public IMutualFundsFeedGroup updateToWorkingCopy(IMutualFundsFeedGroup workingCopy, IMutualFundsFeedGroup imageCopy)
			throws MutualFundsFeedGroupException;

	public IMutualFundsFeedEntry getIMutualFundsFeed (String schemeCode) throws MutualFundsFeedEntryException;

	
	public boolean isValidSchemeCode(String schemeCode);
	
	//For File Upload add by govind on 30-Aug-2011	
	boolean isPrevFileUploadPending() throws MutualFundsFeedEntryException;
	int insertMutualFundsFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws MutualFundsFeedEntryException;
	IFileMapperId insertMutualFundsFeedEntry(IFileMapperId fileId, IMutualFundsFeedGroupTrxValue idxTrxValue)throws MutualFundsFeedEntryException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws MutualFundsFeedEntryException;
	IFileMapperId getInsertFileById(long id) throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	List getAllStageMutualFundsFeedEntry(String searchBy, String login)throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	List insertActualMutualFundsFeedEntry(String sysId)throws MutualFundsFeedEntryException;
	IMutualFundsFeedGroup insertMutualFundsFeedEntry(IMutualFundsFeedGroup mutualfundsFeedEntry)throws MutualFundsFeedEntryException;
	void updateMutualFundsFeedEntryItem(List bondFeedEntryList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	boolean isMutualFundsCodeExist(List schemeCodeList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException; //A govind 300811
	//end File Upload add by govind on 30-Aug-2011	
}
