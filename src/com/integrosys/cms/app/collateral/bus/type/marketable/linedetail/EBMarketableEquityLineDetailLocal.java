package com.integrosys.cms.app.collateral.bus.type.marketable.linedetail;

import javax.ejb.EJBLocalObject;

public interface EBMarketableEquityLineDetailLocal extends EJBLocalObject {
	
	public long getLineDetailId();

	public IMarketableEquityLineDetail getValue();

	public void setValue(IMarketableEquityLineDetail value);


}
