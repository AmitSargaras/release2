package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBLimitProfileUdfLocalHome extends EJBLocalHome {

	public EBLimitProfileUdfLocal create(ILimitProfileUdf value) throws CreateException;
	public EBLimitProfileUdfLocal findByPrimaryKey(Long pk) throws FinderException;
}
