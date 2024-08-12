package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLimitXRefUdfLocalHome2 extends EJBLocalHome{

	public EBLimitXRefUdfLocal2 create(ILimitXRefUdf2 value) throws CreateException;
	public EBLimitXRefUdfLocal2 findByPrimaryKey(Long pk) throws FinderException;
}
