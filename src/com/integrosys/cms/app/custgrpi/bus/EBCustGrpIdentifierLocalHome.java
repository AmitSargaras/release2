package com.integrosys.cms.app.custgrpi.bus;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;


public interface EBCustGrpIdentifierLocalHome extends EJBLocalHome {

    EBCustGrpIdentifierLocal create(ICustGrpIdentifier aCustGrpIdentifier) throws CreateException;

    EBCustGrpIdentifierLocal findByPrimaryKey(Long pk) throws FinderException;

}
