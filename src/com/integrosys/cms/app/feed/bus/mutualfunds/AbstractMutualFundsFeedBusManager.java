package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public abstract class AbstractMutualFundsFeedBusManager implements IMutualFundsFeedBusManager {
	
	public IMutualFundsDao mutualFundsDao;

	public IMutualFundsDao getMutualFundsDao() {
		return mutualFundsDao;
	}

	public void setMutualFundsDao(IMutualFundsDao mutualFundsDao) {
		this.mutualFundsDao = mutualFundsDao;
	}

	public IMutualFundsFeedGroup getMutualFundsFeedGroup(long id) throws MutualFundsFeedGroupException {
		return getMutualFundsDao().getMutualFundsFeedGroupByPrimaryKey(getMutualFundsFeedGroupEntityName(), new Long(id));
	}

	public IMutualFundsFeedGroup getMutualFundsFeedGroup(String groupType) throws MutualFundsFeedGroupException {
		return getMutualFundsDao().getMutualFundsFeedGroupByGroupType(getMutualFundsFeedGroupEntityName(), groupType);
	}

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IMutualFundsFeedGroup createMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException {
		return getMutualFundsDao().createMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);
	}

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IMutualFundsFeedGroup updateMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException {
		return getMutualFundsDao().updateMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);
	}

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws MutualFundsFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IMutualFundsFeedGroup deleteMutualFundsFeedGroup(IMutualFundsFeedGroup group) throws MutualFundsFeedGroupException {
		getMutualFundsDao().deleteMutualFundsFeedGroup(getMutualFundsFeedGroupEntityName(), group);

		return group;
	}

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws MutualFundsFeedEntryException on errors.
	 */
	public IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String ric) throws MutualFundsFeedEntryException {
		return getMutualFundsDao().getMutualFundsFeedEntryByRic(getMutualFundsFeedEntryEntityName(), ric);
	}

	public abstract String getMutualFundsFeedGroupEntityName();

	public abstract String getMutualFundsFeedEntryEntityName();
	
	public boolean isValidSchemeCode(String schemeCode){
		return  getMutualFundsDao().isValidSchemeCode(schemeCode);
	}

	public IMutualFundsFeedEntry getIMutualFundsFeed (String schemeCode) throws MutualFundsFeedEntryException

	{
		return getMutualFundsDao().getIMutualFundsFeed(getMutualFundsFeedEntryEntityName(), schemeCode);
	}

	/*****************File Upload ***********************/
	/**
	*File Upload Methods
	**/
	
	public boolean isPrevFileUploadPending()
	throws MutualFundsFeedEntryException {
		try {
			return getMutualFundsDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new MutualFundsFeedEntryException("File is not in proper format");
		}
	}

	public int insertMutualFundsFeedEntry(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws MutualFundsFeedEntryException {
		try {
			return getMutualFundsDao().insertMutualFundsFeedEntry(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new MutualFundsFeedEntryException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertMutualFundsFeedEntry(
			IFileMapperId fileId, IMutualFundsFeedGroupTrxValue trxValue)
			throws MutualFundsFeedEntryException {
		if (!(fileId == null)) {
			return getMutualFundsDao().insertMutualFundsFeedEntry(getMutualFundsFeedEntryFileMapperName(), fileId, trxValue);
		} else {
			throw new MutualFundsFeedEntryException(
					"ERROR- MutualFundsFeedEntry object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws MutualFundsFeedEntryException {
		if (!(fileId == null)) {
			return getMutualFundsDao().createFileId(getMutualFundsFeedEntryFileMapperName(), fileId);
		} else {
			throw new MutualFundsFeedEntryException(
					"ERROR- MutualFundsFeedEntry object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws MutualFundsFeedEntryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getMutualFundsDao().getInsertFileList(
					getMutualFundsFeedEntryFileMapperName(), new Long(id));
		} else {
			throw new MutualFundsFeedEntryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageMutualFundsFeedEntry(String searchBy, String login)throws MutualFundsFeedEntryException,TrxParameterException,TransactionException {

		return getMutualFundsDao().getAllStageMutualFundsFeedEntry(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws MutualFundsFeedEntryException,TrxParameterException,TransactionException {

		return getMutualFundsDao().getFileMasterList(searchBy);
	}
	
	
	public List insertActualMutualFundsFeedEntry(String sysId)
	throws MutualFundsFeedEntryException {
		try {
			return getMutualFundsDao().insertActualMutualFundsFeedEntry(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new MutualFundsFeedEntryException("File is not in proper format");
		}
	}
	
	public IMutualFundsFeedGroup insertMutualFundsFeedEntry(
			IMutualFundsFeedGroup mutualfundsFeedGroup)
			throws MutualFundsFeedEntryException {
		if (!(mutualfundsFeedGroup == null)) {
			return getMutualFundsDao().insertMutualFundsFeedEntry("actualMutualFundsFeedEntry", mutualfundsFeedGroup);
		} else {
			throw new MutualFundsFeedEntryException(
					"ERROR- Credit Approval object is null. ");
		}
	}
	
	public void updateMutualFundsFeedEntryItem(List mutualfundsFeedEntryList) throws MutualFundsFeedGroupException
	{
		getMutualFundsDao().updateMutualFundsFeedEntryItem(getMutualFundsFeedEntryEntityName(), mutualfundsFeedEntryList);
	}
	
	public boolean isMutualFundsCodeExist(List schemeCodeList) throws MutualFundsFeedGroupException
	{
		return getMutualFundsDao().isMutualFundsCodeExist(getMutualFundsFeedEntryEntityName(), schemeCodeList);
	}
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)    //A govind 300811
	throws NoSuchGeographyException {
		try {
			getMutualFundsDao().deleteTransaction(obFileMapperMaster);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	public abstract String getMutualFundsFeedEntryFileMapperName();
	/***********End File Upload********************/
}
