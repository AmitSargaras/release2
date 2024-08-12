package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class PincodeMappingTrxControllerFactory implements ITrxControllerFactory  {

	public PincodeMappingTrxControllerFactory() {
		super();
	}
	private ITrxController PincodeMappingReadController;

	private ITrxController PincodeMappingTrxController;
	
	public ITrxController getPincodeMappingReadController() {
		return PincodeMappingReadController;
	}

	public void setPincodeMappingReadController(ITrxController pincodeMappingReadController) {
		PincodeMappingReadController = pincodeMappingReadController;
	}
	public ITrxController getPincodeMappingTrxController() {
		return PincodeMappingTrxController;
	}
	public void setPincodeMappingTrxController(ITrxController pincodeMappingTrxController) {
		PincodeMappingTrxController = pincodeMappingTrxController;
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)
			throws TrxParameterException {
		if (isReadOperation(param.getAction()))

		{
			return getPincodeMappingReadController();
		}
		return getPincodeMappingTrxController();
	}
	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_PINCODE_MAPPING))
				|| (anAction.equals(ICMSConstant.ACTION_READ_PINCODE_MAPPING_ID))) {
			return true;
		}
		return false;
	}
}
