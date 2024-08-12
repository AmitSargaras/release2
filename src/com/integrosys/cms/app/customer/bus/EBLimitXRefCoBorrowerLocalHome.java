package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLimitXRefCoBorrowerLocalHome extends EJBLocalHome{

	public EBLimitXRefCoBorrowerLocal create(ILimitXRefCoBorrower value) throws CreateException;
	public EBLimitXRefCoBorrowerLocal findByPrimaryKey(Long pk) throws FinderException;
}
