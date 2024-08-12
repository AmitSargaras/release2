package com.integrosys.cms.host.stp.common;

import com.integrosys.cms.host.stp.bus.IStpTransBusManager;
import com.integrosys.cms.host.stp.bus.IStpTransDao;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTransJdbc;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Dec 14, 2008
 * Time: 1:33:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpErrorMessageFetcherImpl implements IStpErrorMessageFetcher,IStpTransType {

    private IStpTransJdbc stpTransJdbc;

    public IStpTransJdbc getStpTransJdbc() {
        return stpTransJdbc;
    }

    public void setStpTransJdbc(IStpTransJdbc stpTransJdbc) {
        this.stpTransJdbc = stpTransJdbc;
    }

    public List getErrorMessage(String transactionId){
        List errMsg = getStpTransJdbc().getErrorMessage(transactionId);
        if(errMsg != null && errMsg.size() > 0){
            ArrayList iMsg = (ArrayList)errMsg.get(0);
            ArrayList aMsg = new ArrayList();
            if(iMsg != null && iMsg.size() > 0){
                if(iMsg.get(0).equals(TRX_TYPE_FAC_MASTER_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_MASTER_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_MASTER_DELETE)){
                    aMsg.add("SIBS Facility Master Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_BMN_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_BMN_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_BMN_DELETE)){
                    aMsg.add("SIBS Facility BNM Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_OFFICER_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_OFFICER_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_OFFICER_DELETE)){
                    aMsg.add("SIBS Facility Officer Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_RELATION_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_RELATION_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_RELATION_DELETE)){
                    aMsg.add("SIBS Facility Relationship Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_INSURE_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_INSURE_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_INSURE_DELETE)){
                    aMsg.add("SIBS Facility Insurance Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_MASTER_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_MASTER_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_MASTER_DELETE)){
                    aMsg.add("SIBS Collateral Master Information Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_INSURE_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_INSURE_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_INSURE_DELETE)){
                    aMsg.add("SIBS Collateral Insurance Policy Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_TRADE_IN_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_TRADE_IN_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_TRADE_IN_DELETE)){
                    aMsg.add("SIBS Collateral Trade In Information Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_CHARGOR_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_CHARGOR_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_CHARGOR_DELETE)){
                    aMsg.add("SIBS Collateral Pledgor Information Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_LINK_FAC_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_LINK_FAC_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_LINK_FAC_DELETE)){
                    aMsg.add("SIBS Collateral Pledge Information Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_COL_FAC_CHARGE_CREATE) || iMsg.get(0).equals(TRX_TYPE_COL_FAC_CHARGE_UPDATE) || iMsg.get(0).equals(TRX_TYPE_COL_FAC_CHARGE_DELETE)){
                    aMsg.add("SIBS Collateral Charge Information Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_MASTER_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_MASTER_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_MASTER_DELETE)){
                    aMsg.add("SIBS Facility Islamic Master Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_BBA_VARI_PACK_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_BBA_VARI_PACK_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_BBA_VARI_PACK_DELETE)){
                    aMsg.add("SIBS Facility BBA Vari Package Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_MULTI_TIER_FINANCE_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_MULTI_TIER_FINANCE_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_MULTI_TIER_FINANCE_DELETE)){
                    aMsg.add("SIBS Facility Multi Tier Financing Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_INCREMENT_REDUCT_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_INCREMENT_REDUCT_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_INCREMENT_REDUCT_DELETE)){
                    aMsg.add("SIBS Facility Incremental Reduction Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEW_DELETE)){
                    aMsg.add("SIBS Facility Islamic Rental Renew Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_UPDATE) || iMsg.get(0).equals(TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_DELETE)){
                    aMsg.add("SIBS Facility Islamic Security Deposit Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }else if(iMsg.get(0).equals(TRX_TYPE_FAC_BASEL_2_CREATE) || iMsg.get(0).equals(TRX_TYPE_FAC_BASEL_2_UPDATE)){
                    aMsg.add("SIBS Facility BASEL 2 Error: [ " + iMsg.get(1) + " ] " + iMsg.get(2));
                }

                return aMsg;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

}
