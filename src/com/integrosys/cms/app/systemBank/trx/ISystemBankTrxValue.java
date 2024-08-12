package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface ISystemBankTrxValue  extends ICMSTrxValue {

    public ISystemBank getSystemBank();

    public ISystemBank getStagingSystemBank();

    public void setSystemBank(ISystemBank value);

    public void setStagingSystemBank(ISystemBank value);
}
