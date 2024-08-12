package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Title: CLIMS 
 * Description: Property Index Transaction data object
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 17, 2008
 */
public class OBPropertyIdxTrxValue extends OBCMSTrxValue implements IPropertyIdxTrxValue{

    public  OBPropertyIdxTrxValue(){}

    IPropertyIdx prIdx ;
    IPropertyIdx stagingPrIdx ;

    public OBPropertyIdxTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }
    
    public IPropertyIdx getPrIdx() {
        return prIdx;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IPropertyIdx getStagingPrIdx() {
        return stagingPrIdx;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPrIdx(IPropertyIdx value) {
        this.prIdx = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStagingPrIdx(IPropertyIdx value) {
        this.stagingPrIdx = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
