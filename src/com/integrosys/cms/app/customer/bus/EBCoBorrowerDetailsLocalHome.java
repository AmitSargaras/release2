package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBCoBorrowerDetailsLocalHome extends EJBLocalHome {

	public EBCoBorrowerDetailsLocal create(ICoBorrowerDetails stock) throws CreateException;

	public EBCoBorrowerDetailsLocal findByPrimaryKey(Long id) throws FinderException;

}
