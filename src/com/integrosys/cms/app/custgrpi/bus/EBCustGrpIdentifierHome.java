package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.currency.Amount;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;
import java.util.Collection;


public interface EBCustGrpIdentifierHome extends EJBHome {

    public EBCustGrpIdentifier create(ICustGrpIdentifier obj) throws CreateException, RemoteException;

    public EBCustGrpIdentifier findByPrimaryKey(Long pk) throws FinderException, RemoteException;

    public SearchResult searchEntryDetails(GroupMemberSearchCriteria obj) throws FinderException, SearchDAOException, RemoteException;

    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria obj) throws FinderException, SearchDAOException, RemoteException;

    public Map getGroupAccountMgrCodes(Map inputMap) throws FinderException,SearchDAOException, RemoteException;

    public Amount getGroupLimit(String intLmt, String rating) throws FinderException,SearchDAOException, RemoteException;

    public List setEntityDetails(List list) throws FinderException,SearchDAOException, RemoteException;
	
	/**
	     * Find the EB of Customer Group ejb object by internal limit type.
	     *
	     * @param internalLimitType internal limit type of type String
	     * @return Collection of EBCustGrpIdentifier
	     * @throws FinderException on error while finding the ejb
	     * @throws RemoteException on error during remote method call
	     */
	public Collection findByInternalLimitType (String internalLimitType)
        throws FinderException, RemoteException;
}
