package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBSystemBankTrxValue extends OBCMSTrxValue implements ISystemBankTrxValue{

    public  OBSystemBankTrxValue(){}

    ISystemBank systemBank ;
    ISystemBank stagingSystemBank ;

    public OBSystemBankTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }
    
    public ISystemBank getSystemBank() {
        return systemBank;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ISystemBank getStagingSystemBank() {
        return stagingSystemBank;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSystemBank(ISystemBank value) {
        this.systemBank = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStagingSystemBank(ISystemBank value) {
        this.stagingSystemBank = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
