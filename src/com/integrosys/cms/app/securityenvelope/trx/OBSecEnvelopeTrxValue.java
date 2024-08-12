package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Title: CLIMS 
 * Description: Property Index Transaction data object
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Jan 30, 2010
 */
public class OBSecEnvelopeTrxValue extends OBCMSTrxValue implements ISecEnvelopeTrxValue{

    public  OBSecEnvelopeTrxValue(){}

    ISecEnvelope secEnvelope ;
    ISecEnvelope stagingSecEnvelope ;

    public OBSecEnvelopeTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }
    
    public ISecEnvelope getSecEnvelope() {
        return secEnvelope;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ISecEnvelope getStagingSecEnvelope() {
        return stagingSecEnvelope;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSecEnvelope(ISecEnvelope value) {
        this.secEnvelope = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStagingSecEnvelope(ISecEnvelope value) {
        this.stagingSecEnvelope = value;
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
