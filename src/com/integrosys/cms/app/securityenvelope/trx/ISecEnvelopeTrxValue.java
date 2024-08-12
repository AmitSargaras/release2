package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Jan 27, 2010
 */
public interface ISecEnvelopeTrxValue  extends ICMSTrxValue {

    public ISecEnvelope getSecEnvelope();

    public ISecEnvelope getStagingSecEnvelope();

    public void setSecEnvelope(ISecEnvelope value);

    public void setStagingSecEnvelope(ISecEnvelope value);
}
