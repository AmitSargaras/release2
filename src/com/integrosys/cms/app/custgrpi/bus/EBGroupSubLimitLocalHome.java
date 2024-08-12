package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface EBGroupSubLimitLocalHome extends EJBLocalHome {

    EBGroupSubLimitLocal create(IGroupSubLimit obj) throws CreateException;

    EBGroupSubLimitLocal findByPrimaryKey(Long pk) throws FinderException;

}
