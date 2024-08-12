package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface EBFacilityCoBorrowerDetailsHome extends EJBHome {

	public EBFacilityCoBorrowerDetailsLocal create(IFacilityCoBorrowerDetails stock) throws CreateException;

	public EBFacilityCoBorrowerDetailsLocal findByPrimaryKey(Long id) throws FinderException;

}
