/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/WarehouseTrxControllerFactory.java,v 1.4 2004/08/13 07:31:29 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

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
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/13 07:31:29 $ Tag: $Name: $
 */
public class WarehouseTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public WarehouseTrxControllerFactory() {
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
			return new WarehouseReadController();
		}
		return new WarehouseTrxController();
	}

	private boolean isReadOperation(String operation) {
		return (operation.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID)
				|| operation.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID) || operation
				.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_COUNTRY));
	}

}