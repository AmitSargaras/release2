/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/BondFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController mutualFundsFeedGroupReadController;

	private ITrxController mutualFundsFeedGroupTrxContoller;
	
	/*Add by govind for upload*/
    private ITrxController mutualfundsFeedGroupInsertFileTrxController;

    private ITrxController readMutualFundsFeedGroupInsertFileTrxController;
	/*End by Govind S:*/
	public ITrxController getMutualFundsFeedGroupReadController() {
		return mutualFundsFeedGroupReadController;
	}

	public void setMutualFundsFeedGroupReadController(ITrxController mutualFundsFeedGroupReadController) {
		this.mutualFundsFeedGroupReadController = mutualFundsFeedGroupReadController;
	}

	public ITrxController getMutualFundsFeedGroupTrxContoller() {
		return mutualFundsFeedGroupTrxContoller;
	}

	public void setMutualFundsFeedGroupTrxContoller(ITrxController mutualFundsFeedGroupTrxContoller) {
		this.mutualFundsFeedGroupTrxContoller = mutualFundsFeedGroupTrxContoller;
	}
	
	public MutualFundsFeedGroupTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return getMutualFundsFeedGroupTrxContoller();
		}
		//For File Upload Add by Govind
		if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
	    		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
	    	return getMutualfundsFeedGroupInsertFileTrxController();
	    }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
	    	return getMutualfundsFeedGroupInsertFileTrxController();
		}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
	    	return getReadMutualFundsFeedGroupInsertFileTrxController();
	    }
		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	/**
	 * @return the mutualfundsFeedGroupInsertFileTrxController
	 */
	public ITrxController getMutualfundsFeedGroupInsertFileTrxController() {
		return mutualfundsFeedGroupInsertFileTrxController;
	}

	/**
	 * @param mutualfundsFeedGroupInsertFileTrxController the mutualfundsFeedGroupInsertFileTrxController to set
	 */
	public void setMutualfundsFeedGroupInsertFileTrxController(
			ITrxController mutualfundsFeedGroupInsertFileTrxController) {
		this.mutualfundsFeedGroupInsertFileTrxController = mutualfundsFeedGroupInsertFileTrxController;
	}

	/**
	 * @return the readMutualFundsFeedGroupInsertFileTrxController
	 */
	public ITrxController getReadMutualFundsFeedGroupInsertFileTrxController() {
		return readMutualFundsFeedGroupInsertFileTrxController;
	}

	/**
	 * @param readMutualFundsFeedGroupInsertFileTrxController the readMutualFundsFeedGroupInsertFileTrxController to set
	 */
	public void setReadMutualFundsFeedGroupInsertFileTrxController(
			ITrxController readMutualFundsFeedGroupInsertFileTrxController) {
		this.readMutualFundsFeedGroupInsertFileTrxController = readMutualFundsFeedGroupInsertFileTrxController;
	}

	
}
