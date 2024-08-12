/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/AbstractStockFeedBusManager.java,v 1.1 2003/08/07 08:34:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/07
 */
public abstract class AbstractStockFeedBusManager implements IStockFeedBusManager {

	private IStockDao stockDao;
	
	private IStockExchangeJdbc stockExchangeJdbc;

	public IStockDao getStockDao() {
		return stockDao;
	}

	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}

	public IStockExchangeJdbc getStockExchangeJdbc() {
		return stockExchangeJdbc;
	}

	public void setStockExchangeJdbc(IStockExchangeJdbc stockExchangeJdbc) {
		this.stockExchangeJdbc = stockExchangeJdbc;
	}

	public IStockFeedGroup getStockFeedGroup(long id) throws StockFeedGroupException {
		return getStockDao().getStockFeedGroupByPrimaryKey(getStockFeedGroupEntityName(), new Long(id));
	}

	public IStockFeedGroup getStockFeedGroup(String groupType, String subType, String stockType)
			throws StockFeedGroupException {
		return getStockDao().getStockFeedGroupByTypeAndSubTypeAndStockType(getStockFeedGroupEntityName(), groupType,
				subType, stockType);
	}

	public IStockFeedGroup getStockFeedGroup(String groupType, String subType) throws StockFeedGroupException {
		return getStockDao().getStockFeedGroupByTypeAndSubType(getStockFeedGroupEntityName(), groupType, groupType);
	}

	public IStockFeedGroup createStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException {
		return getStockDao().createStockFeedGroup(getStockFeedGroupEntityName(), group);
	}

	public IStockFeedGroup updateStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException {
		return getStockDao().updateStockFeedGroup(getStockFeedGroupEntityName(), group);
	}

	public IStockFeedGroup deleteStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException {
		getStockDao().deleteStockFeedGroup(getStockFeedGroupEntityName(), group);

		return group;
	}

	public IStockFeedEntry getStockFeedEntry(String condition, int type) throws StockFeedEntryException {
		switch (type) {
		case 1:
			return getStockDao().getStockFeedEntryByTicker(getStockFeedEntryEntityName(), condition);
		case 2:
			return getStockDao().getStockFeedEntryByIsinCode(getStockFeedEntryEntityName(), condition);
		case 3:
			return getStockDao().getStockFeedEntryByRic(getStockFeedEntryEntityName(), condition);
		default:
			return null;
		}
	}

	public IStockExchange[] getAllStockExchanges() throws OFAException {
		return getStockExchangeJdbc().getAllStockExchanges();
	}
	//govind
	public IStockFeedEntry getStockFeedEntryStockExc(String stockExchange, String scriptCode) throws StockFeedEntryException
	{
		return getStockDao().getStockFeedEntryByStockExcScri(getStockFeedEntryEntityName(), stockExchange,scriptCode);
	}

	public abstract String getStockFeedEntryEntityName();

	public abstract String getStockFeedGroupEntityName();
	
	
	
	/*****************File Upload ***********************/
	/**
	*File Upload Methods
	**/
	
	public boolean isPrevFileUploadPending(String stockType)
	throws StockFeedEntryException {
		try {
			return getStockDao().isPrevFileUploadPending(stockType);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new StockFeedEntryException("File is not in proper format");
		}
	}

	public int insertStockFeedEntry(IFileMapperMaster trans_Id, String userName, ArrayList result ,String stockType)
	throws StockFeedEntryException {
		try {
			return getStockDao().insertStockFeedEntry(trans_Id, userName, result ,stockType);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new StockFeedEntryException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertStockFeedEntry(
			IFileMapperId fileId, IStockFeedGroupTrxValue trxValue)
			throws StockFeedEntryException {
		if (!(fileId == null)) {
			return getStockDao().insertStockFeedEntry(getStockFeedEntryFileMapperName(), fileId, trxValue);
		} else {
			throw new StockFeedEntryException(
					"ERROR- StockFeedEntry object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws StockFeedEntryException {
		if (!(fileId == null)) {
			return getStockDao().createFileId(getStockFeedEntryFileMapperName(), fileId);
		} else {
			throw new StockFeedEntryException(
					"ERROR- StockFeedEntry object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws StockFeedEntryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getStockDao().getInsertFileList(
					getStockFeedEntryFileMapperName(), new Long(id));
		} else {
			throw new StockFeedEntryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageStockFeedEntry(String searchBy, String login)throws StockFeedEntryException,TrxParameterException,TransactionException {

		return getStockDao().getAllStageStockFeedEntry(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws StockFeedEntryException,TrxParameterException,TransactionException {

		return getStockDao().getFileMasterList(searchBy);
	}
	
	
	public List insertActualStockFeedEntry(String sysId)
	throws StockFeedEntryException {
		try {
			return getStockDao().insertActualStockFeedEntry(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new StockFeedEntryException("File is not in proper format");
		}
	}
	
	public IStockFeedGroup insertStockFeedEntry(
			IStockFeedGroup stockFeedGroup)
			throws StockFeedEntryException {
		if (!(stockFeedGroup == null)) {
			return getStockDao().insertStockFeedEntry("actualStockFeedEntry", stockFeedGroup);
		} else {
			throw new StockFeedEntryException(
					"ERROR- Credit Approval object is null. ");
		}
	}
	
	public void updateStockFeedEntryItem(List stockFeedEntryList) throws StockFeedGroupException
	{
		getStockDao().updateStockFeedEntryItem(getStockFeedEntryEntityName(), stockFeedEntryList);
	}
	
	public boolean isStockCodeExist(List scriptCodeList, String stockType) throws StockFeedGroupException
	{
		return getStockDao().isStockCodeExist(getStockFeedEntryEntityName(), scriptCodeList, stockType);
	}
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)    //A govind 270811
	throws NoSuchGeographyException {
		try {
			getStockDao().deleteTransaction(obFileMapperMaster);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	public abstract String getStockFeedEntryFileMapperName();
	/***********End File Upload********************/

	public boolean isExistScriptCode(String stockCode)throws StockFeedGroupException, TrxParameterException,
		TransactionException, ConcurrentUpdateException {
		return getStockDao().isExistScriptCode(IStockDao.ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME,stockCode);
	}
	
}
