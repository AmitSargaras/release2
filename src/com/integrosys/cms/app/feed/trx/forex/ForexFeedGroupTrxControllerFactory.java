/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ForexFeedGroupTrxControllerFactory.java,v 1.9 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.forex;

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
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 10:15:18 $ Tag: $Name: $
 */
public class ForexFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	private ITrxController forexFeedGroupReadController;
	
	private ITrxController forexFeedGroupTrxController;
	
	//Add by govind for upload
    private ITrxController forexFeedGroupInsertFileTrxController;

    private ITrxController readForexFeedGroupInsertFileTrxController;
	
	/**
	 * @return the forexFeedGroupReadController
	 */
	public ITrxController getForexFeedGroupReadController() {
		return forexFeedGroupReadController;
	}

	/**
	 * @param forexFeedGroupReadController the forexFeedGroupReadController to set
	 */
	public void setForexFeedGroupReadController(
			ITrxController forexFeedGroupReadController) {
		this.forexFeedGroupReadController = forexFeedGroupReadController;
	}

	/**
	 * @return the forexFeedGroupTrxController
	 */
	public ITrxController getForexFeedGroupTrxController() {
		return forexFeedGroupTrxController;
	}

	/**
	 * @param forexFeedGroupTrxController the forexFeedGroupTrxController to set
	 */
	public void setForexFeedGroupTrxController(
			ITrxController forexFeedGroupTrxController) {
		this.forexFeedGroupTrxController = forexFeedGroupTrxController;
	}
	
	/**
	 * @return the forexFeedGroupInsertFileTrxController
	 */
	public ITrxController getForexFeedGroupInsertFileTrxController() {
		return forexFeedGroupInsertFileTrxController;
	}

	/**
	 * @param forexFeedGroupInsertFileTrxController the forexFeedGroupInsertFileTrxController to set
	 */
	public void setForexFeedGroupInsertFileTrxController(
			ITrxController forexFeedGroupInsertFileTrxController) {
		this.forexFeedGroupInsertFileTrxController = forexFeedGroupInsertFileTrxController;
	}



	/**
	 * @return the readForexFeedGroupInsertFileTrxController
	 */
	public ITrxController getReadForexFeedGroupInsertFileTrxController() {
		return readForexFeedGroupInsertFileTrxController;
	}

	/**
	 * @param readForexFeedGroupInsertFileTrxController the readForexFeedGroupInsertFileTrxController to set
	 */
	public void setReadForexFeedGroupInsertFileTrxController(
			ITrxController readForexFeedGroupInsertFileTrxController) {
		this.readForexFeedGroupInsertFileTrxController = readForexFeedGroupInsertFileTrxController;
	}

	/**
	 * Default Constructor
	 */
	public ForexFeedGroupTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_FOREX_FEED_GROUP)) {
			return getForexFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_FOREX_FEED_GROUP)) {
			return getForexFeedGroupTrxController();
		}
		//For File Upload Add by Govind
		else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
        	return getForexFeedGroupInsertFileTrxController();
        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
        	return getForexFeedGroupInsertFileTrxController();
    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
        	return getReadForexFeedGroupInsertFileTrxController();
        }
		

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	
}
