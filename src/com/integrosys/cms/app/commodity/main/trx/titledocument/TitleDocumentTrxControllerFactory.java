/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/TitleDocumentTrxControllerFactory.java,v 1.3 2004/08/13 04:16:22 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This controller factory is used to create doc item related controllers.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/13 04:16:22 $ Tag: $Name: $
 */
public class TitleDocumentTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public TitleDocumentTrxControllerFactory() {
		super();
	}

	/**
	 * Returns an ITrxController given the ITrxValue and ITrxParameter objects
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return ITrxController
	 * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
	 *         any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new TitleDocumentReadController();
		}
		return new TitleDocumentTrxController();
	}

	private boolean isReadOperation(String operation) {
		return (operation.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID) || operation
				.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID));
	}
}