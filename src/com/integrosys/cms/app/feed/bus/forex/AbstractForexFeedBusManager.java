/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/AbstractForexFeedBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.forex.OBForex;

/**
 * <p>
 * Abstract business manager to interface with forex feed group or entry.
 * 
 * <p>
 * Sub class to provide entity name of group and entry, to achieve actual and
 * staging copy are stored in correct place in persistent storage.
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/11 04:08:19
 */
public abstract class AbstractForexFeedBusManager implements IForexFeedBusManager {

	private IForexDao forexDao;

	/**
	 * @return the forexDao
	 */
	public IForexDao getForexDao() {
		return forexDao;
	}

	/**
	 * @param forexDao the forexDao to set
	 */
	public void setForexDao(IForexDao forexDao) {
		this.forexDao = forexDao;
	}

	/**
	 * Gets all the entries in the forex group.
	 * @param id Identifies the forex group.
	 * @return The group containing all the entries.
	 * @throws ForexFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IForexFeedGroup getForexFeedGroup(long id) throws ForexFeedGroupException {
		return getForexDao().getForexFeedGroupByPrimaryKey(getForexFeedGroupEntityName(), new Long(id));
	}

	public IForexFeedGroup getForexFeedGroup(String groupType) throws ForexFeedGroupException {
		return getForexDao().getForexFeedGroupByGroupType(getForexFeedGroupEntityName(), groupType);
	}

	/**
	 * Creates the forex feed group with all the entries.
	 * @param group The forex feed group to be created.
	 * @return The created forex feed group.
	 * @throws ForexFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IForexFeedGroup createForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException {
		return getForexDao().createForexFeedGroup(getForexFeedGroupEntityName(), group);
	}

	/**
	 * Updates the forex feed group with the entries. This is a replacement
	 * action.
	 * @param group The forex feed group to update with.
	 * @return The updated forex feed group.
	 * @throws ForexFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IForexFeedGroup updateForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException {
		return getForexDao().updateForexFeedGroup(getForexFeedGroupEntityName(), group);
	}

	/**
	 * Deletes the forex feed group and all its entries.
	 * @param group The forex feed group to delete with all its entries.
	 * @return The deleted forex feed group.
	 * @throws ForexFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IForexFeedGroup deleteForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException {
		getForexDao().deleteForexFeedGroup(getForexFeedGroupEntityName(), group);

		return group;
	}

	/*
	 * protected IForexDao getForexDao() { return (IForexDao)
	 * BeanHouse.get("forexDao"); }
	 */
	
	
	
	/*****************File Upload ***********************/
	/**
	*File Upload Methods
	**/
	
	public boolean isPrevFileUploadPending()
	throws ForexFeedEntryException {
		try {
			return getForexDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ForexFeedEntryException("File is not in proper format");
		}
	}

	public int insertForexFeedEntry(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws ForexFeedEntryException {
		try {
			return getForexDao().insertForexFeedEntry(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ForexFeedEntryException("File is not in proper format");
		}
	}
	
	public HashMap insertForexFeedEntryAuto(IFileMapperMaster trans_Id, String userName, ArrayList result)
			throws ForexFeedEntryException {
				try {
					return getForexDao().insertForexFeedEntryAuto(trans_Id, userName, result);
				} catch (HibernateOptimisticLockingFailureException ex) {
					throw new ForexFeedEntryException("File is not in proper format");
				}
			}

	public IFileMapperId insertForexFeedEntry(
			IFileMapperId fileId, IForexFeedGroupTrxValue trxValue)
			throws ForexFeedEntryException {
		if (!(fileId == null)) {
			return getForexDao().insertForexFeedEntry(getForexFeedEntryFileMapperName(), fileId, trxValue);
		} else {
			throw new ForexFeedEntryException(
					"ERROR- ForexFeedEntry object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws ForexFeedEntryException {
		if (!(fileId == null)) {
			return getForexDao().createFileId(getForexFeedEntryFileMapperName(), fileId);
		} else {
			throw new ForexFeedEntryException(
					"ERROR- ForexFeedEntry object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws ForexFeedEntryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getForexDao().getInsertFileList(
					getForexFeedEntryFileMapperName(), new Long(id));
		} else {
			throw new ForexFeedEntryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageForexFeedEntry(String searchBy, String login)throws ForexFeedEntryException,TrxParameterException,TransactionException {

		return getForexDao().getAllStageForexFeedEntry(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws ForexFeedEntryException,TrxParameterException,TransactionException {

		return getForexDao().getFileMasterList(searchBy);
	}
	
	
	public List insertActualForexFeedEntry(String sysId)
	throws ForexFeedEntryException {
		try {
			return getForexDao().insertActualForexFeedEntry(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ForexFeedEntryException("File is not in proper format");
		}
	}
	
	public IForexFeedGroup insertForexFeedEntry(
			IForexFeedGroup forexFeedGroup)
			throws ForexFeedEntryException {
		if (!(forexFeedGroup == null)) {
			return getForexDao().insertForexFeedEntry("actualForexFeedEntry", forexFeedGroup);
		} else {
			throw new ForexFeedEntryException(
					"ERROR- Credit Approval object is null. ");
		}
	}
	
	public void updateForexFeedEntryExchangeRate(List forexFeedEntryList) throws ForexFeedGroupException
	{
		getForexDao().updateForexFeedEntryExchangeRate(getForexFeedEntryEntityName(), forexFeedEntryList);
	}
	
	public void updateForexFeedEntryExchangeRateAuto(List forexFeedEntryList) throws ForexFeedGroupException
	{
		getForexDao().updateForexFeedEntryExchangeRateAuto(getForexFeedEntryEntityName(), forexFeedEntryList);
	}
	
	public boolean isCurrencyCodeExist(List currencyCodeList) throws ForexFeedGroupException
	{
		return getForexDao().isCurrencyCodeExist(getForexFeedEntryEntityName(), currencyCodeList);
	}
	/***********End File Upload********************/
	
	/**
	 * This Method get ExchangeRate With INR
	 * Input parameter Currency Code
	 */
	public BigDecimal getExchangeRateWithINR (String currencyCode) throws ForexFeedGroupException

	{
		return getForexDao().getExchangeRateWithINR(getForexFeedEntryEntityName(), currencyCode.trim());
	}
	
	public OBForex retriveCurrency(String currencyCode) throws ForexFeedGroupException

	{
		return getForexDao().retriveCurrency(currencyCode.trim());
	}
	/**
	 * to be implemented by sub class, to provide the entity name of forex feed
	 * entry. Different for actual and staging copy
	 * 
	 * @return the entity name of forex feed entry
	 */
	public abstract String getForexFeedEntryEntityName();

	/**
	 * to be implemented by sub class, to provide the entity name of forex feed
	 * group. Different for actual and staging copy
	 * 
	 * @return the entity name of forex feed group
	 */
	public abstract String getForexFeedGroupEntityName();
	
	
	public abstract String getForexFeedEntryFileMapperName();
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ForexFeedGroupException, TrxParameterException, TransactionException {
		getForexDao().deleteTransaction(obFileMapperMaster);		
	}

}
