/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/stock/StockFeedProxyImpl.java,v 1.8 2005/07/29 03:30:32 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.stock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedBusManager;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.OBStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class StockFeedProxyImpl implements IStockFeedProxy {

	private IStockFeedBusManager stockFeedBusManager;

	private IStockFeedBusManager stagingStockFeedBusManager;

	private ITrxControllerFactory trxControllerFactory;

	//Add For File Upload
    private IStockFeedBusManager stagingStockFeedFileMapperIdBusManager;
	
	private IStockFeedBusManager stockFeedFileMapperIdBusManager;

	/**
	 * @return the stagingStockFeedFileMapperIdBusManager
	 */
	public IStockFeedBusManager getStagingStockFeedFileMapperIdBusManager() {
		return stagingStockFeedFileMapperIdBusManager;
	}



	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IStockFeedBusManager getStockFeedBusManager() {
		return stockFeedBusManager;
	}

	public void setStockFeedBusManager(IStockFeedBusManager stockFeedBusManager) {
		this.stockFeedBusManager = stockFeedBusManager;
	}

	public IStockFeedBusManager getStagingStockFeedBusManager() {
		return stagingStockFeedBusManager;
	}

	public void setStagingStockFeedBusManager(IStockFeedBusManager stagingStockFeedBusManager) {
		this.stagingStockFeedBusManager = stagingStockFeedBusManager;
	}

	/**
	 * Get actual stock feed price given the stock exchange.
	 * 
	 * @param subType stock exchange
	 * @return stock feed group
	 * @throws StockFeedGroupException on any errors encountered
	 */
	public IStockFeedGroup getActualStockFeedGroup(String subType) throws StockFeedGroupException {
		return getStockFeedBusManager().getStockFeedGroup(ICMSConstant.STOCK_FEED_GROUP_TYPE, subType);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	public IStockFeedGroupTrxValue checkerApproveStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */

	public IStockFeedGroupTrxValue checkerRejectStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IStockFeedGroupTrxValue getStockFeedGroup(String subType, String stockType) throws StockFeedGroupException {
		DefaultLogger.debug(this, "subType = " + subType);

		IStockFeedBusManager mgr = getStockFeedBusManager();

		IStockFeedGroup group = mgr.getStockFeedGroup(ICMSConstant.STOCK_FEED_GROUP_TYPE, subType, stockType);

		if (group == null) {
			// Cannot find the stock index feed group.
			StockFeedGroupException e = new StockFeedGroupException("Cannot find the stock index feed group.");
			e.setErrorCode(IStockFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IStockFeedGroupTrxValue vv = new OBStockFeedGroupTrxValue();
		vv.setReferenceID(String.valueOf(group.getStockFeedGroupID()));
		vv.setStockFeedGroup(group); // important to set!

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_STOCK_FEED_GROUP);

		return operate(vv, param);
	}

	/**
	 * Get the transaction value containing StockFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IStockFeedGroupTrxValue
	 */
	public IStockFeedGroupTrxValue getStockFeedGroupByTrxID(long trxID) throws StockFeedGroupException {
		IStockFeedGroupTrxValue trxValue = new OBStockFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_STOCK_FEED_GROUP);

		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the StockFeedGroup
	 *        object
	 * @param aFeedGroup - IStockFeedGroup, this could have been passed in the
	 *        trx value, but the intention is that the caller should not have
	 *        modified the trxValue, as the caller does not need to know about
	 *        staging settings et al.
	 * @return IStockFeedGroupTrxValue the saved trxValue
	 */
	public IStockFeedGroupTrxValue makerUpdateStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aFeedGroupTrxValue, IStockFeedGroup aFeedGroup) throws StockFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_STOCK_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the StockFeedGroup
	 *        object
	 * @param aFeedGroup - IStockFeedGroup, this could have been passed in the
	 *        trx value, but the intention is that the caller should not have
	 *        modified the trxValue, as the caller does not need to know about
	 *        staging settings et al.
	 */
	public IStockFeedGroupTrxValue makerSubmitStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aFeedGroupTrxValue, IStockFeedGroup aFeedGroup) throws StockFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_STOCK_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws StockFeedGroupException
	 */
	public IStockFeedGroupTrxValue makerSubmitRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateStockFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	public IStockFeedGroupTrxValue makerUpdateRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a StockFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	public IStockFeedGroupTrxValue makerCloseRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a StockFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	public IStockFeedGroupTrxValue makerCloseDraftStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_STOCK_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws StockFeedEntryException
	 */
	public IStockFeedEntry getStockFeedEntry(String condition, int type) throws StockFeedEntryException {
		return getStockFeedBusManager().getStockFeedEntry(condition, type);
	}

	public IStockFeedGroupTrxValue getStockFeedGroup(long groupID) throws StockFeedGroupException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Helper method to perform the document item transactions.
	 * @param aTrxValue - IDocumentItemTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return IStockFeedGroupTrxValue - the trx interface
	 */
	protected IStockFeedGroupTrxValue operate(IStockFeedGroupTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws StockFeedGroupException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IStockFeedGroupTrxValue) result.getTrxValue();
	}

	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws StockFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new StockFeedGroupException(e);
		}
		catch (Exception ex) {
			throw new StockFeedGroupException(ex.toString());
		}
	}

	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param aTrxValue -
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	protected IStockFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_STOCK_FEED_GROUP);

		return aTrxValue;
	}

	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @return IStockFeedGroupTrxValue - the checklist trx interface formulated
	 */
	protected IStockFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IStockFeedGroup aFeedGroup) {

		IStockFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBStockFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBStockFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IStockFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingStockFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}
	
	//govind
	public IStockFeedEntry getStockFeedEntryStockExc(String stockExchange, String scriptCode) throws StockFeedEntryException
	{
		return getStockFeedBusManager().getStockFeedEntryStockExc(stockExchange, scriptCode);
	}
	
	//------------------------------------ File Upload-----------------------------------------------------
	
	 private IStockFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID, String stockType) {
		 IStockFeedGroupTrxValue ccStockFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccStockFeedGroupTrxValue = new OBStockFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccStockFeedGroupTrxValue = new OBStockFeedGroupTrxValue();
	        }
	        ccStockFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IStockFeedGroupTrxValue) ccStockFeedGroupTrxValue, stockType);
	        ccStockFeedGroupTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccStockFeedGroupTrxValue;
	    }
	 
	 private IStockFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId, String stockType) {
		 IStockFeedGroupTrxValue ccStockFeedGroupTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccStockFeedGroupTrxValue = new OBStockFeedGroupTrxValue(anICMSTrxValue);
	        } else {
	            ccStockFeedGroupTrxValue = new OBStockFeedGroupTrxValue();
	        }
	        ccStockFeedGroupTrxValue = formulateTrxValueID(anITrxContext, (IStockFeedGroupTrxValue) ccStockFeedGroupTrxValue, stockType);
	        ccStockFeedGroupTrxValue.setStagingFileMapperID(fileId);
	        return ccStockFeedGroupTrxValue;
	    }
	 private IStockFeedGroupTrxValue formulateTrxValueID(ITrxContext anITrxContext, IStockFeedGroupTrxValue anIStockFeedGroupTrxValue, String stockType) {
	        anIStockFeedGroupTrxValue.setTrxContext(anITrxContext);
	        if(stockType.equals("001")){
	        anIStockFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_STOCK_ITEM);
	        }
	        else if(stockType.equals("002")){
	        	anIStockFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_STOCK_NSE_ITEM);
	        }
	        else if(stockType.equals("003")){
	        	anIStockFeedGroupTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_STOCK_OTHERS_ITEM);
	        }
	        return anIStockFeedGroupTrxValue;
	    }

	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public IStockFeedGroupTrxValue makerInsertMapperStockFeedEntry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID ,String stockType)throws StockFeedGroupException, TrxParameterException,
				TransactionException {
			// TODO Auto-generated method stub
			if (anITrxContext == null) {
	            throw new StockFeedGroupException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new StockFeedGroupException("The OBFileMapperID to be updated is null !!!");
	        }

	        IStockFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID, stockType);
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending(String stockType) throws StockFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getStockFeedBusManager().isPrevFileUploadPending(stockType);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new StockFeedGroupException("ERROR-- Due to null StockFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertStockFeedEntry(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList ,String stockType) throws StockFeedGroupException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getStockFeedBusManager().insertStockFeedEntry(fileMapperMaster, userName, resultlList ,stockType);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new StockFeedGroupException("ERROR-- Due to null StockFeedEntry object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public IStockFeedGroupTrxValue getInsertFileByTrxID(String trxID)
		throws StockFeedGroupException, TransactionException,
		CommandProcessingException {
		 	IStockFeedGroupTrxValue trxValue = new OBStockFeedGroupTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in StockFeedEntry.
		 */
	 public List getAllStage(String searchBy, String login)throws StockFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getStockFeedBusManager().getAllStageStockFeedEntry( searchBy, login);
		}else{
			throw new StockFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public IStockFeedGroupTrxValue checkerApproveInsertStockFeedEntry(
	 		ITrxContext anITrxContext,
	 		IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
	 		throws StockFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new StockFeedGroupException("The ITrxContext is null!!!");
	    }
	    if (anIStockFeedGroupTrxValue == null) {
	        throw new StockFeedGroupException
	                ("The IStockFeedGroupTrxValue to be updated is null!!!");
	    }
	    anIStockFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIStockFeedGroupTrxValue ,"");
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anIStockFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of StockFeedEntry.
		 */
	 public List getFileMasterList(String searchBy)throws StockFeedGroupException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getStockFeedBusManager().getFileMasterList( searchBy);
		}else{
			throw new StockFeedGroupException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public List insertActualStockFeedEntry(String sysId) throws StockFeedGroupException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getStockFeedBusManager().insertActualStockFeedEntry(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new StockFeedGroupException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new StockFeedGroupException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in StockFeedEntry.
		 */
	 
	// public IStockFeedGroupTrxValue checkerCreateStockFeedEntry(ITrxContext anITrxContext, IStockFeedEntry anICCStockFeedEntry, String refStage) throws StockFeedGroupException,TrxParameterException,
	 public IStockFeedGroupTrxValue checkerCreateStockFeedEntry(ITrxContext anITrxContext, IStockFeedGroup anICCStockFeedEntry, String refStage) throws StockFeedGroupException,TrxParameterException,
	 
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new StockFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anICCStockFeedEntry == null) {
	         throw new StockFeedGroupException("The ICCStockFeedEntry to be updated is null !!!");
	     }

	     IStockFeedGroupTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCStockFeedEntry);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCStockFeedEntry.getStockFeedGroupID()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files StockFeedEntry.
		 */

	 public IStockFeedGroupTrxValue checkerRejectInsertStockFeedEntry(
	 	ITrxContext anITrxContext,
	 	IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
	 	throws StockFeedGroupException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new StockFeedGroupException("The ITrxContext is null!!!");
	 	}
	 	if (anIStockFeedGroupTrxValue == null) {
	 	  throw new StockFeedGroupException
	 	          ("The IStockFeedGroupTrxValue to be updated is null!!!");
	 	}
	 		anIStockFeedGroupTrxValue = formulateTrxValueID(anITrxContext, anIStockFeedGroupTrxValue ,"");
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anIStockFeedGroupTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files StockFeedEntry.
		 */

	 public IStockFeedGroupTrxValue makerInsertCloseRejectedStockFeedEntry(
	 		ITrxContext anITrxContext,
	 		IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
	 		throws StockFeedGroupException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new StockFeedGroupException("The ITrxContext is null!!!");
	     }
	     if (anIStockFeedGroupTrxValue == null) {
	         throw new StockFeedGroupException("The IStockFeedGroupTrxValue to be updated is null!!!");
	     }
	     anIStockFeedGroupTrxValue = formulateTrxValue(anITrxContext, anIStockFeedGroupTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anIStockFeedGroupTrxValue, param);
	 }
	 public void updateStockFeedEntryItem(List stockFeedEntryList) throws StockFeedGroupException,TrxParameterException,TransactionException
	 {
		 getStockFeedBusManager().updateStockFeedEntryItem(stockFeedEntryList);
		 
	 }
	 
	 /*Return true if Currency Code not Exist*/
	 public boolean isStockCodeExist(List scriptCodeList, String stockType) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException
	 {	
		return  getStockFeedBusManager().isStockCodeExist(scriptCodeList, stockType);
	 }
	 /**
		 * @return Maker deleteTransaction.  //A govind 270811
		 */
	 public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,
		TransactionException  {
		 
			try {
				getStockFeedBusManager().deleteTransaction(obFileMapperMaster);
			} catch (Exception e) {
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- Due to null City object cannot update.");
			}
		}
	//------------------------------------End File Upload-----------------------------------------------------



	/**
	 * @return the stockFeedFileMapperIdBusManager
	 */
	public IStockFeedBusManager getStockFeedFileMapperIdBusManager() {
		return stockFeedFileMapperIdBusManager;
	}



	/**
	 * @param stockFeedFileMapperIdBusManager the stockFeedFileMapperIdBusManager to set
	 */
	public void setStockFeedFileMapperIdBusManager(
			IStockFeedBusManager stockFeedFileMapperIdBusManager) {
		this.stockFeedFileMapperIdBusManager = stockFeedFileMapperIdBusManager;
	}



	/**
	 * @param stagingStockFeedFileMapperIdBusManager the stagingStockFeedFileMapperIdBusManager to set
	 */
	public void setStagingStockFeedFileMapperIdBusManager(
			IStockFeedBusManager stagingStockFeedFileMapperIdBusManager) {
		this.stagingStockFeedFileMapperIdBusManager = stagingStockFeedFileMapperIdBusManager;
	}

	public boolean isExistScriptCode(String stockCode)
			throws StockFeedGroupException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getStockFeedBusManager().isExistScriptCode(stockCode);
	}
}