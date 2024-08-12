package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;

import java.util.Collection;

/**
 * Title: CLIMS 
 * Description: Bank Entity Branch Param Transaction Interface
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: May 30, 2008
 */
public interface IBankEntityBranchTrxValue  extends ICMSTrxValue {

    public Collection getBankEntityBranchParam();

    public Collection getStagingBankEntityBranchParam();

    public void setBankEntityBranchParam(Collection value);

    public void setStagingBankEntityBranchParam(Collection value);
}
