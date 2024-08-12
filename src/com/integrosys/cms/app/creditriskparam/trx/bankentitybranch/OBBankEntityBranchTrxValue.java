package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.bankentitybranch.IBankEntityBranchTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;

import java.util.Collection;

/**
 * Title: CLIMS 
 * Description: Bank Entity Branch Param Transaction data object
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: May 30, 2008
 */
public class OBBankEntityBranchTrxValue extends OBCMSTrxValue implements IBankEntityBranchTrxValue {

    public  OBBankEntityBranchTrxValue() {}

    Collection actual;
    Collection staging ;

    public OBBankEntityBranchTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

    public Collection getBankEntityBranchParam() {
        return actual;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection getStagingBankEntityBranchParam() {
        return staging;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBankEntityBranchParam(Collection value) {
        this.actual = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStagingBankEntityBranchParam(Collection value) {
        this.staging = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
