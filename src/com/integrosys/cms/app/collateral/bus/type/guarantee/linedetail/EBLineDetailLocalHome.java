package com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLineDetailLocalHome extends EJBLocalHome {

	public EBLineDetailLocal create(ILineDetail value) throws CreateException;
	
	public EBLineDetailLocal findByPrimaryKey(Long pk) throws FinderException;
}
