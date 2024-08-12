package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author bharat waghela Trx controller factory
 */
public class PartyGroupTrxControllerFactory implements ITrxControllerFactory {

	private ITrxController PartyGroupReadController;

	private ITrxController PartyGroupTrxController;

	public ITrxController getPartyGroupReadController() {
		return PartyGroupReadController;
	}

	public void setPartyGroupReadController(
			ITrxController PartyGroupReadController) {
		this.PartyGroupReadController = PartyGroupReadController;
	}

	public ITrxController getPartyGroupTrxController() {
		return PartyGroupTrxController;
	}

	public void setPartyGroupTrxController(
			ITrxController PartyGroupTrxController) {
		this.PartyGroupTrxController = PartyGroupTrxController;
	}

	public PartyGroupTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)
			throws TrxParameterException {
		if (isReadOperation(param.getAction()))

		{
			return getPartyGroupReadController();
		}
		return getPartyGroupTrxController();
	}

	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_PARTY_GROUP))
				|| (anAction.equals(ICMSConstant.ACTION_READ_PARTY_GROUP_ID))) {
			return true;
		}
		return false;
	}
}
