package com.integrosys.cms.app.custgrpi.proxy;

import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.currency.Amount;

import java.util.Map;
import java.util.List;

public interface ICustGrpIdentifierProxy extends java.io.Serializable {


    public ICustGrpIdentifierTrxValue maker2CancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext,
            ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException;

     public ICustGrpIdentifierTrxValue checker2RejectUpdateCustGrpIdentifier
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException;

     public ICustGrpIdentifierTrxValue checker2ApproveUpdateCustGrp
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException;

     public ICustGrpIdentifierTrxValue maker2SaveCustGrpIdentifier(
           ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
           ICustGrpIdentifier stagingICustGrpIdentifier)
           throws CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue maker2SubmitCustGrpIdentifier(
           ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
           ICustGrpIdentifier stagingICustGrpIdentifier)
           throws CustGrpIdentifierException;


     public ICustGrpIdentifierTrxValue makerDeleteCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier stagingICustGrpIdentifier)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByGrpID
            (ITrxContext anITrxContext,
             String trxID)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue checkerApproveUpdateCustGrp
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException;




    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByTrxID
            (ITrxContext anITrxContext,
             String trxID)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue makerSubmitCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier stagingICustGrpIdentifier)
            throws CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue checkerRejectUpdateCustGrpIdentifier
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException;


    public ICustGrpIdentifierTrxValue makerCancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext,
            ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException;

    public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria)
            throws CustGrpIdentifierException;

    public List setEntityDetails(List list)  throws CustGrpIdentifierException;

    public ICustGrpIdentifierTrxValue makerSaveCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier stagingICustGrpIdentifier)
            throws CustGrpIdentifierException;


    public Map getGroupAccountMgrCodes(Map inputMap)
            throws CustGrpIdentifierException;

    public Amount getGroupLimit(String intLmt, String rating)
            throws CustGrpIdentifierException;

    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria)
            throws CustGrpIdentifierException;

    List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws CustGrpIdentifierException;
    
    boolean groupHasLimitBooking(List grpIDList) throws CustGrpIdentifierException;
}
