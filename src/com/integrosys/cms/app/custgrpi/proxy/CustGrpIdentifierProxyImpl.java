package com.integrosys.cms.app.custgrpi.proxy;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;

public class CustGrpIdentifierProxyImpl implements ICustGrpIdentifierProxy {


      public ICustGrpIdentifierTrxValue maker2CancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException {
        try {
            return getProxy().maker2CancelUpdateCustGrpIdentifier(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

     public ICustGrpIdentifierTrxValue checker2RejectUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException {
        try {
            return getProxy().checker2RejectUpdateCustGrpIdentifier(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

     public ICustGrpIdentifierTrxValue checker2ApproveUpdateCustGrp
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        try {
            return getProxy().checker2ApproveUpdateCustGrp(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

     public ICustGrpIdentifierTrxValue maker2SaveCustGrpIdentifier(
               ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
               ICustGrpIdentifier stagingOBJ) throws CustGrpIdentifierException {
           try {
               return getProxy().maker2SaveCustGrpIdentifier(anITrxContext, aTrxValue, stagingOBJ);
           } catch (RemoteException e) {
               DefaultLogger.error(this, "", e);
               throw new CustGrpIdentifierException("RemoteException", e);
           }
       }

    public ICustGrpIdentifierTrxValue maker2SubmitCustGrpIdentifier(
               ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
               ICustGrpIdentifier stagingOBJ) throws CustGrpIdentifierException {
           try {
               return getProxy().maker2SubmitCustGrpIdentifier(anITrxContext, aTrxValue, stagingOBJ);
           } catch (RemoteException e) {
               DefaultLogger.error(this, "", e);
               throw new CustGrpIdentifierException("RemoteException", e);
           }
       }


     public ICustGrpIdentifierTrxValue makerDeleteCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier stagingOBJ) throws CustGrpIdentifierException {
        try {
            return getProxy().makerDeleteCustGrpIdentifier(anITrxContext, aTrxValue, stagingOBJ);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }




     public ICustGrpIdentifierTrxValue getCustGrpIdentifierByGrpID
            (ITrxContext ctx, String grpID) throws CustGrpIdentifierException {
        try {
            return getProxy().getCustGrpIdentifierByGrpID(ctx, grpID);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("Exception caught at getCustGrpIdentifierByGrpID: " + e.toString());
        }

    }

    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByTrxID
            (ITrxContext ctx, String trxID) throws CustGrpIdentifierException {
        try {
            return getProxy().getCustGrpIdentifierByTrxID(ctx, trxID);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("Exception caught at getCustGrpIdentifierByTrxID: " + e.toString());
        }

    }

    public ICustGrpIdentifierTrxValue checkerApproveUpdateCustGrp
            (ITrxContext anITrxContext,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        try {
            return getProxy().checkerApproveUpdateCustGrp(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    public ICustGrpIdentifierTrxValue makerSubmitCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier stagingOBJ) throws CustGrpIdentifierException {
        try {
            return getProxy().makerSubmitCustGrpIdentifier(anITrxContext, aTrxValue, stagingOBJ);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }


    public ICustGrpIdentifierTrxValue checkerRejectUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException {
        try {
            return getProxy().checkerRejectUpdateCustGrpIdentifier(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }



     public ICustGrpIdentifierTrxValue makerCancelUpdateCustGrpIdentifier(
            ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException {
        try {
            return getProxy().makerCancelUpdateCustGrpIdentifier(anITrxContext, aTrxValue);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

     public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria) throws CustGrpIdentifierException {
        try {
            return getProxy().searchEntryDetails(criteria);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    public List setEntityDetails(List list)  throws CustGrpIdentifierException{
        try {
            return getProxy().setEntityDetails(list);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }


    public ICustGrpIdentifierTrxValue makerSaveCustGrpIdentifier(
               ITrxContext anITrxContext, ICustGrpIdentifierTrxValue aTrxValue,
               ICustGrpIdentifier stagingOBJ) throws CustGrpIdentifierException {
           try {
               return getProxy().makerSaveCustGrpIdentifier(anITrxContext, aTrxValue, stagingOBJ);
           } catch (RemoteException e) {
               DefaultLogger.error(this, "", e);
               throw new CustGrpIdentifierException("RemoteException", e);
           }
       }


   public Map getGroupAccountMgrCodes(Map inputMap) throws CustGrpIdentifierException {
        try {
            return getProxy().getGroupAccountMgrCodes(inputMap);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    public Amount getGroupLimit(String intLmt, String rating) throws CustGrpIdentifierException {
        try {
            return getProxy().getGroupLimit(intLmt, rating);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

   public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria) throws CustGrpIdentifierException {
        try {
            return getProxy().searchGroup(criteria);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws CustGrpIdentifierException {
        try {
            return getProxy().retrieveMasterGroupBySubGroupID(subgroupIDList);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    public boolean groupHasLimitBooking(List grpIDList) throws CustGrpIdentifierException {
        try {
            return getProxy().groupHasLimitBooking(grpIDList);
        } catch (RemoteException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    private SBCustGrpIdentifierProxy getProxy() throws CustGrpIdentifierException {
        SBCustGrpIdentifierProxy home = (SBCustGrpIdentifierProxy) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_PROXY_JNDI,
                SBCustGrpIdentifierProxyHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustGrpIdentifierException("SBCustGrpIdentifierProxy is null!");
        }
    }


}
