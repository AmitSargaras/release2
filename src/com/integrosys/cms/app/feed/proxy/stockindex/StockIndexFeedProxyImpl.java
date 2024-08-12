/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/stockindex/StockIndexFeedProxyImpl.java,v 1.5 2005/01/12 08:43:48 hshii Exp $
 */
package com.integrosys.cms.app.feed.proxy.stockindex;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedBusManager;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedEntryException;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stockindex.OBStockIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Implementation of {@link IStockIndexFeedProxy}
 * 
 * @author Chong Jun Yong
 * 
 */
public class StockIndexFeedProxyImpl implements IStockIndexFeedProxy {

	private IStockIndexFeedBusManager stockIndexFeedBusManager;

	private IStockIndexFeedBusManager stagingStockIndexFeedBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public IStockIndexFeedBusManager getStagingStockIndexFeedBusManager() {
		return stagingStockIndexFeedBusManager;
	}

	public void setStagingStockIndexFeedBusManager(IStockIndexFeedBusManager stagingStockIndexFeedBusManager) {
		this.stagingStockIndexFeedBusManager = stagingStockIndexFeedBusManager;
	}

	public IStockIndexFeedBusManager getStockIndexFeedBusManager() {
		return stockIndexFeedBusManager;
	}

	public void setStockIndexFeedBusManager(IStockIndexFeedBusManager stockIndexFeedBusManager) {
		this.stockIndexFeedBusManager = stockIndexFeedBusManager;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return this.trxControllerFactory;
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	public IStockIndexFeedGroupTrxValue checkerApproveStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */

	public IStockIndexFeedGroupTrxValue checkerRejectStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	public IStockIndexFeedGroupTrxValue getStockIndexFeedGroup(String subType) throws StockIndexFeedGroupException {
		IStockIndexFeedGroup group = getStockIndexFeedBusManager().getStockIndexFeedGroup(
				ICMSConstant.STOCK_INDEX_FEED_GROUP_TYPE, subType);

		if (group == null) {
			StockIndexFeedGroupException e = new StockIndexFeedGroupException("Cannot find the stock index feed group.");
			e.setErrorCode(IStockIndexFeedProxy.NO_FEED_GROUP);
			throw e;
		}

		IStockIndexFeedGroupTrxValue vv = new OBStockIndexFeedGroupTrxValue();
		vv.setReferenceID(String.valueOf(group.getStockIndexFeedGroupID()));
		vv.setStockIndexFeedGroup(group); // important to set!

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_STOCK_INDEX_FEED_GROUP);

		return operate(vv, param);
	}

	/**
	 * Get the transaction value containing StockIndexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IStockIndexFeedGroupTrxValue
	 */
	public IStockIndexFeedGroupTrxValue getStockIndexFeedGroupByTrxID(long trxID) throws StockIndexFeedGroupException {
		IStockIndexFeedGroupTrxValue trxValue = new OBStockIndexFeedGroupTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_STOCK_INDEX_FEED_GROUP);
		return operate(trxValue, param);
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the StockIndexFeedGroup
	 *        object
	 * @param aFeedGroup - IStockIndexFeedGroup, this could have been passed in
	 *        the trx value, but the intention is that the caller should not
	 *        have modified the trxValue, as the caller does not need to know
	 *        about staging settings et al.
	 * @return IStockIndexFeedGroupTrxValue the saved trxValue
	 */
	public IStockIndexFeedGroupTrxValue makerUpdateStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aFeedGroupTrxValue, IStockIndexFeedGroup aFeedGroup)
			throws StockIndexFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_STOCK_INDEX_FEED_GROUP);
		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aFeedGroupTrxValue transaction wrapper for the StockIndexFeedGroup
	 *        object
	 * @param aFeedGroup - IStockIndexFeedGroup, this could have been passed in
	 *        the trx value, but the intention is that the caller should not
	 *        have modified the trxValue, as the caller does not need to know
	 *        about staging settings et al.
	 */
	public IStockIndexFeedGroupTrxValue makerSubmitStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aFeedGroupTrxValue, IStockIndexFeedGroup aFeedGroup)
			throws StockIndexFeedGroupException {
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");

		aFeedGroupTrxValue = formulateTrxValue(anITrxContext, aFeedGroupTrxValue, aFeedGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_STOCK_INDEX_FEED_GROUP);

		return operate(aFeedGroupTrxValue, param);
	}

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws StockIndexFeedGroupException
	 */
	public IStockIndexFeedGroupTrxValue makerSubmitRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	// --- below are 2nd priority to be implemented
	/**
	 * This is essentially the same as makerUpdateStockIndexFeedGroup except
	 * that it triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	public IStockIndexFeedGroupTrxValue makerUpdateRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a StockIndexFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	public IStockIndexFeedGroupTrxValue makerCloseRejectedStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Cancels an initiated transaction on a StockIndexFeedGroup to return it to
	 * last 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockIndexFeedGroup object
	 */
	public IStockIndexFeedGroupTrxValue makerCloseDraftStockIndexFeedGroup(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) throws StockIndexFeedGroupException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_STOCK_INDEX_FEED_GROUP);
		return operate(aTrxValue, param);
	}

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws StockIndexFeedEntryException
	 */
	public IStockIndexFeedEntry getStockIndexFeedEntryByRic(String ric) throws StockIndexFeedEntryException {
		return getStockIndexFeedBusManager().getStockIndexFeedEntryByRic(ric);
	}

	public IStockIndexFeedGroupTrxValue getStockIndexFeedGroup(long groupID) throws StockIndexFeedGroupException {
		// TODO Auto-generated method stub
		return null;
	}

	protected IStockIndexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IStockIndexFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_STOCK_INDEX_FEED_GROUP);

		return aTrxValue;
	}

	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @return IStockIndexFeedGroupTrxValue - the checklist trx interface
	 *         formulated
	 */
	protected IStockIndexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IStockIndexFeedGroup aFeedGroup) {

		IStockIndexFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBStockIndexFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBStockIndexFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IStockIndexFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingStockIndexFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	protected IStockIndexFeedGroupTrxValue operate(IStockIndexFeedGroupTrxValue aTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws StockIndexFeedGroupException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (IStockIndexFeedGroupTrxValue) result.getTrxValue();
	}

	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws StockIndexFeedGroupException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);

			Validate.notNull(controller, "'controller' must not be null, please check the trx controller factory.");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new StockIndexFeedGroupException(e);
		}
		catch (Exception ex) {
			throw new StockIndexFeedGroupException(ex.toString());
		}
	}

}