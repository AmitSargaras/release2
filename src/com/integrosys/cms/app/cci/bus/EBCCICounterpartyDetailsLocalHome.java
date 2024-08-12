package com.integrosys.cms.app.cci.bus;


import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;


public interface EBCCICounterpartyDetailsLocalHome extends EJBLocalHome {

    EBCCICounterpartyDetailsLocal create(ICCICounterparty aICCICounterparty) throws CreateException;

    EBCCICounterpartyDetailsLocal findByPrimaryKey(Long pk) throws FinderException ;


//   public Collection findByGroupCCINo (long groupCCINo) throws FinderException;

}
