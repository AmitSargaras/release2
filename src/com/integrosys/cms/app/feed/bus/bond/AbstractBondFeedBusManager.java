package com.integrosys.cms.app.feed.bus.bond;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public abstract class AbstractBondFeedBusManager implements IBondFeedBusManager {
	public IBondDao bondDao;

	public IBondDao getBondDao() {
		return bondDao;
	}

	public void setBondDao(IBondDao bondDao) {
		this.bondDao = bondDao;
	}

	public IBondFeedGroup getBondFeedGroup(long id) throws BondFeedGroupException {
		return getBondDao().getBondFeedGroupByPrimaryKey(getBondFeedGroupEntityName(), new Long(id));
	}

	public IBondFeedGroup getBondFeedGroup(String groupType) throws BondFeedGroupException {
		return getBondDao().getBondFeedGroupByGroupType(getBondFeedGroupEntityName(), groupType);
	}

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws BondFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IBondFeedGroup createBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException {
		return getBondDao().createBondFeedGroup(getBondFeedGroupEntityName(), group);
	}

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws BondFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IBondFeedGroup updateBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException {
		return getBondDao().updateBondFeedGroup(getBondFeedGroupEntityName(), group);
	}

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws BondFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IBondFeedGroup deleteBondFeedGroup(IBondFeedGroup group) throws BondFeedGroupException {
		getBondDao().deleteBondFeedGroup(getBondFeedGroupEntityName(), group);

		return group;
	}

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws BondFeedEntryException on errors.
	 */
	public IBondFeedEntry getBondFeedEntryByRic(String ric) throws BondFeedEntryException {
		return getBondDao().getBondFeedEntryByRic(getBondFeedEntryEntityName(), ric);
	}

	/*****************File Upload ***********************/
	/**
	*File Upload Methods
	**/
	
	public boolean isPrevFileUploadPending()
	throws BondFeedEntryException {
		try {
			return getBondDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BondFeedEntryException("File is not in proper format");
		}
	}

	public int insertBondFeedEntry(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws BondFeedEntryException {
		try {
			return getBondDao().insertBondFeedEntry(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BondFeedEntryException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertBondFeedEntry(
			IFileMapperId fileId, IBondFeedGroupTrxValue trxValue)
			throws BondFeedEntryException {
		if (!(fileId == null)) {
			return getBondDao().insertBondFeedEntry(getBondFeedEntryFileMapperName(), fileId, trxValue);
		} else {
			throw new BondFeedEntryException(
					"ERROR- BondFeedEntry object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws BondFeedEntryException {
		if (!(fileId == null)) {
			return getBondDao().createFileId(getBondFeedEntryFileMapperName(), fileId);
		} else {
			throw new BondFeedEntryException(
					"ERROR- BondFeedEntry object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws BondFeedEntryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getBondDao().getInsertFileList(
					getBondFeedEntryFileMapperName(), new Long(id));
		} else {
			throw new BondFeedEntryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageBondFeedEntry(String searchBy, String login)throws BondFeedEntryException,TrxParameterException,TransactionException {

		return getBondDao().getAllStageBondFeedEntry(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws BondFeedEntryException,TrxParameterException,TransactionException {

		return getBondDao().getFileMasterList(searchBy);
	}
	
	
	public List insertActualBondFeedEntry(String sysId)
	throws BondFeedEntryException {
		try {
			return getBondDao().insertActualBondFeedEntry(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new BondFeedEntryException("File is not in proper format");
		}
	}
	
	public IBondFeedGroup insertBondFeedEntry(
			IBondFeedGroup bondFeedGroup)
			throws BondFeedEntryException {
		if (!(bondFeedGroup == null)) {
			return getBondDao().insertBondFeedEntry("actualBondFeedEntry", bondFeedGroup);
		} else {
			throw new BondFeedEntryException(
					"ERROR- Credit Approval object is null. ");
		}
	}
	
	public void updateBondFeedEntryItem(List bondFeedEntryList) throws BondFeedGroupException
	{
		getBondDao().updateBondFeedEntryItem(getBondFeedEntryEntityName(), bondFeedEntryList);
	}
	
	public boolean isBondCodeExist(List bondCodeList) throws BondFeedGroupException
	{
		return getBondDao().isBondCodeExist(getBondFeedEntryEntityName(), bondCodeList);
	}
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws BondFeedEntryException, TrxParameterException, TransactionException {
		getBondDao().deleteTransaction(obFileMapperMaster);		
	}
	public abstract String getBondFeedEntryFileMapperName();
	/***********End File Upload********************/
	
	
	public abstract String getBondFeedGroupEntityName();

	public abstract String getBondFeedEntryEntityName();
	
	
	public IBondFeedEntry getBondFeedEntry(String bondCode) throws BondFeedEntryException {
		return getBondDao().getBondFeedEntry(bondCode);
	}
	
	public boolean isExistBondCode(String bondCode)
		throws BondFeedGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getBondDao().isExistBondCode(IBondDao.ACTUAL_BOND_FEED_ENTRY_ENTITY_NAME,bondCode);
	}
	
}
