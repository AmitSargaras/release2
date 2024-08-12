package com.integrosys.cms.app.collateral.bus.type.marketable.linedetail;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBMarketableEquityLineDetailLocalHome extends EJBLocalHome {

	public EBMarketableEquityLineDetailLocal create(IMarketableEquityLineDetail value) throws CreateException;
	
	public EBMarketableEquityLineDetailLocal findByPrimaryKey(Long pk) throws FinderException;
}
