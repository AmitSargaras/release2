package com.integrosys.cms.app.custgrpi.proxy;


import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;


public interface SBCustGrpIdentifierProxy extends EJBObject {


    public ICustGrpIdentifierTrxValue maker2CancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue checker2RejectUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue checker2ApproveUpdateCustGrp
            (ITrxContext ctx, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue maker2SaveCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws RemoteException, CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue maker2SubmitCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws RemoteException, CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue makerDeleteCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByGrpID
            (ITrxContext ctx, String grpID)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue checkerApproveUpdateCustGrp
            (ITrxContext ctx, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByTrxID
            (ITrxContext ctx, String trxRefID)
            throws RemoteException, CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue makerSubmitCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws RemoteException, CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue checkerRejectUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue makerCancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue)
            throws RemoteException, CustGrpIdentifierException;


    public SearchResult searchEntryDetails(GroupMemberSearchCriteria obj)
            throws CustGrpIdentifierException, RemoteException;


    public List setEntityDetails(List list) throws CustGrpIdentifierException, RemoteException;


    public ICustGrpIdentifierTrxValue makerSaveCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws RemoteException, CustGrpIdentifierException;


    public Map getGroupAccountMgrCodes(Map inputMap)
            throws CustGrpIdentifierException, RemoteException;

    public Amount getGroupLimit(String intLmt, String rating)
            throws CustGrpIdentifierException, RemoteException;

    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria)
            throws CustGrpIdentifierException, RemoteException;

    List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws CustGrpIdentifierException, RemoteException;

    boolean groupHasLimitBooking(List grpIDList) throws CustGrpIdentifierException, RemoteException;
}
