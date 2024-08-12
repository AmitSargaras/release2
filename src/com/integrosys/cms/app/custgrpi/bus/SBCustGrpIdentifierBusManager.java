package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;
import java.util.List;


public interface SBCustGrpIdentifierBusManager extends EJBObject {

    public ICustGrpIdentifier deleteCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException, RemoteException;

    public ICustGrpIdentifier updateCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException, RemoteException;

    public ICustGrpIdentifier getCustGrpIdentifierByGrpID(ICustGrpIdentifier details)
            throws CustGrpIdentifierException, RemoteException;

    public ICustGrpIdentifier createCustGrpIdentifier(ICustGrpIdentifier details)
            throws CustGrpIdentifierException, RemoteException;

    public ICustGrpIdentifier getCustGrpIdentifierByTrxIDRef(long trxIDRef)
            throws CustGrpIdentifierException, RemoteException;

    public SearchResult searchEntryDetails(GroupMemberSearchCriteria obj)
            throws CustGrpIdentifierException, RemoteException;

    public Map getGroupAccountMgrCodes(Map inputMap)
            throws CustGrpIdentifierException, RemoteException;

    public Amount getGroupLimit(String intLmt, String rating)
            throws CustGrpIdentifierException, RemoteException;

    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria)
            throws CustGrpIdentifierException, RemoteException;

    public List setEntityDetails(List list)  throws CustGrpIdentifierException, RemoteException;
	
	/**
	     * Gets Customer Group by internal limit type.
	     *
	     * @param internalLimitType internal limit type
	     * @return Array of ICustGrpIdentifier
	     * @throws CustGrpIdentifierException on errors encountered
	     */
	public ICustGrpIdentifier[] getCustGrpByInternalLimitType(String internalLimitType) throws CustGrpIdentifierException, RemoteException;
	
	/**
	     * Updates the BGEL limit amount of the specific array of Customer Group.
	     *
	     * @param grpObjList array of ICustGrpIdentifier to be updated	   
	     * @throws CustGrpIdentifierException on errors encountered
	     */
	public void updateCustGrpLimitAmount(ICustGrpIdentifier[] grpObjList) throws CustGrpIdentifierException, RemoteException;
	
	/**
	     * Updates the BGEL limit amount of the specific Customer Group.
	     *
	     * @param grpIDList list of group ID to be updated	   
	     * @param lmtAmt the new BGEL amount to be updated to
	     * @throws CustGrpIdentifierException on errors encountered
	     */
	public void updateCustGrpLimitAmount(List grpIDList, Amount lmtAmt) throws CustGrpIdentifierException, RemoteException;
	
	/**
	     * Gets Customer Group by group ID.
	     *
	     * @param grpID group ID
	     * @return ICustGrpIdentifier
	     * @throws CustGrpIdentifierException on errors encountered
	     */
    public ICustGrpIdentifier getCustGrpIdentifierByGrpID(Long grpID) throws CustGrpIdentifierException, RemoteException;
}
