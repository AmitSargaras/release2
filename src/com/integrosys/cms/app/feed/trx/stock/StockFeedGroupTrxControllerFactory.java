/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stock/StockFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.stock;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/12 10:15:18 $ Tag: $Name: $
 */
public class StockFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	private ITrxController stockFeedGroupReadController;

	private ITrxController stockFeedGroupTrxContoller;
	
	/*Add by govind for upload*/
    private ITrxController stockFeedGroupInsertFileTrxController;

    private ITrxController readStockFeedGroupInsertFileTrxController;
	/*End by Govind S:*/

	public ITrxController getStockFeedGroupReadController() {
		return stockFeedGroupReadController;
	}

	public void setStockFeedGroupReadController(ITrxController stockFeedGroupReadController) {
		this.stockFeedGroupReadController = stockFeedGroupReadController;
	}

	public ITrxController getStockFeedGroupTrxContoller() {
		return stockFeedGroupTrxContoller;
	}

	public void setStockFeedGroupTrxContoller(ITrxController stockFeedGroupTrxContoller) {
		this.stockFeedGroupTrxContoller = stockFeedGroupTrxContoller;
	}

	/**
	 * @return the stockFeedGroupInsertFileTrxController
	 */
	public ITrxController getStockFeedGroupInsertFileTrxController() {
		return stockFeedGroupInsertFileTrxController;
	}

	/**
	 * @param stockFeedGroupInsertFileTrxController the stockFeedGroupInsertFileTrxController to set
	 */
	public void setStockFeedGroupInsertFileTrxController(
			ITrxController stockFeedGroupInsertFileTrxController) {
		this.stockFeedGroupInsertFileTrxController = stockFeedGroupInsertFileTrxController;
	}

	/**
	 * @return the readStockFeedGroupInsertFileTrxController
	 */
	public ITrxController getReadStockFeedGroupInsertFileTrxController() {
		return readStockFeedGroupInsertFileTrxController;
	}

	/**
	 * @param readStockFeedGroupInsertFileTrxController the readStockFeedGroupInsertFileTrxController to set
	 */
	public void setReadStockFeedGroupInsertFileTrxController(
			ITrxController readStockFeedGroupInsertFileTrxController) {
		this.readStockFeedGroupInsertFileTrxController = readStockFeedGroupInsertFileTrxController;
	}

	/**
	 * Default Constructor
	 */
	public StockFeedGroupTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (param.getAction().equals(ICMSConstant.ACTION_READ_STOCK_FEED_GROUP)) {
			return getStockFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_STOCK_FEED_GROUP)) {
			return getStockFeedGroupTrxContoller();
		}
		//For File Upload Add by Govind
		if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
	    		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
	    	return getStockFeedGroupInsertFileTrxController();
	    }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
	    	return getStockFeedGroupInsertFileTrxController();
		}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
	    	return getReadStockFeedGroupInsertFileTrxController();
	    }

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

}
