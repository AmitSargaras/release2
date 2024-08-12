/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/BondFeedGroupTrxControllerFactory.java,v 1.3 2003/09/12 10:15:18 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.bond;

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
public class BondFeedGroupTrxControllerFactory implements ITrxControllerFactory {

	/**
	 * Default Constructor
	 */
	
	private ITrxController bondFeedGroupReadController;

	private ITrxController bondFeedGroupTrxContoller;
	
	/*Add by govind for upload*/
    private ITrxController bondFeedGroupInsertFileTrxController;

    private ITrxController readBondFeedGroupInsertFileTrxController;
	/*End by Govind S:*/
	
	public ITrxController getBondFeedGroupReadController() {
		return bondFeedGroupReadController;
	}

	public void setBondFeedGroupReadController(ITrxController bondFeedGroupReadController) {
		this.bondFeedGroupReadController = bondFeedGroupReadController;
	}

	public ITrxController getBondFeedGroupTrxContoller() {
		return bondFeedGroupTrxContoller;
	}

	public void setBondFeedGroupTrxContoller(ITrxController bondFeedGroupTrxContoller) {
		this.bondFeedGroupTrxContoller = bondFeedGroupTrxContoller;
	}
	
	public BondFeedGroupTrxControllerFactory() {
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

		if (param.getAction().equals(ICMSConstant.ACTION_READ_BOND_FEED_GROUP)) {
			return getBondFeedGroupReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_BOND_FEED_GROUP)) {
			return getBondFeedGroupTrxContoller();
		}

		//For File Upload Add by Govind
		if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
	    		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
	    	return getBondFeedGroupInsertFileTrxController();
	    }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
	    	return getBondFeedGroupInsertFileTrxController();
		}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
	    	return getReadBondFeedGroupInsertFileTrxController();
	    }
		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}

	/**
	 * @return the bondFeedGroupInsertFileTrxController
	 */
	public ITrxController getBondFeedGroupInsertFileTrxController() {
		return bondFeedGroupInsertFileTrxController;
	}

	/**
	 * @param bondFeedGroupInsertFileTrxController the bondFeedGroupInsertFileTrxController to set
	 */
	public void setBondFeedGroupInsertFileTrxController(
			ITrxController bondFeedGroupInsertFileTrxController) {
		this.bondFeedGroupInsertFileTrxController = bondFeedGroupInsertFileTrxController;
	}

	/**
	 * @return the readBondFeedGroupInsertFileTrxController
	 */
	public ITrxController getReadBondFeedGroupInsertFileTrxController() {
		return readBondFeedGroupInsertFileTrxController;
	}

	/**
	 * @param readBondFeedGroupInsertFileTrxController the readBondFeedGroupInsertFileTrxController to set
	 */
	public void setReadBondFeedGroupInsertFileTrxController(
			ITrxController readBondFeedGroupInsertFileTrxController) {
		this.readBondFeedGroupInsertFileTrxController = readBondFeedGroupInsertFileTrxController;
	}
	
	



	
}
