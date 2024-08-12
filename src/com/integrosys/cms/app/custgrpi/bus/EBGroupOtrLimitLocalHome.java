package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface EBGroupOtrLimitLocalHome extends EJBLocalHome {

    EBGroupOtrLimitLocal create(IGroupOtrLimit obj) throws CreateException;

    EBGroupOtrLimitLocal findByPrimaryKey(Long pk) throws FinderException;

}