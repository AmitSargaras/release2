package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLineCovenantLocalHome extends EJBLocalHome{

	public EBLineCovenantLocal create(ILineCovenant value) throws CreateException;
	public EBLineCovenantLocal findByPrimaryKey(Long pk) throws FinderException;
}
