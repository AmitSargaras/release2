package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ILimitsOfAuthorityMasterTrxValue extends ICMSTrxValue{

	public ILimitsOfAuthorityMaster getActual();

	public ILimitsOfAuthorityMaster getStaging();

	public void setActual(ILimitsOfAuthorityMaster value);

	public void setStaging(ILimitsOfAuthorityMaster value);
}
