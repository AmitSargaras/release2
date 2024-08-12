package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLimitXRefUdfLocalHome extends EJBLocalHome{

	public EBLimitXRefUdfLocal create(ILimitXRefUdf value) throws CreateException;
	public EBLimitXRefUdfLocal findByPrimaryKey(Long pk) throws FinderException;
}
