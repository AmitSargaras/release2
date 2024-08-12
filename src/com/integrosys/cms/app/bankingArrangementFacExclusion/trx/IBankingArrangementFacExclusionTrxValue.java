package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IBankingArrangementFacExclusionTrxValue extends ICMSTrxValue{

	public IBankingArrangementFacExclusion getActual();

	public IBankingArrangementFacExclusion getStaging();

	public void setActual(IBankingArrangementFacExclusion value);

	public void setStaging(IBankingArrangementFacExclusion value);
}
