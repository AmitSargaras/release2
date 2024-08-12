package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface EBCoBorrowerDetailsHome extends EJBHome {

	public EBCoBorrowerDetailsLocal create(ICoBorrowerDetails stock) throws CreateException;

	public EBCoBorrowerDetailsLocal findByPrimaryKey(Long id) throws FinderException;

}
