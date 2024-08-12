package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBFacilityCoBorrowerDetailsLocalHome extends EJBLocalHome {

	public EBFacilityCoBorrowerDetailsLocal create(IFacilityCoBorrowerDetails stock) throws CreateException;

	public EBFacilityCoBorrowerDetailsLocal findByPrimaryKey(Long id) throws FinderException;

}
