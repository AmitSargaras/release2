package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public interface EBGroupMemberLocalHome extends EJBLocalHome {

    EBGroupMemberLocal create(IGroupMember obj) throws CreateException;

    EBGroupMemberLocal findByPrimaryKey(Long pk) throws FinderException;

}
