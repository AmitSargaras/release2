package com.integrosys.cms.app.securityenvelope.bus;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Erene Wong
 * Date: Jan 25, 2010
 * Time: 11:51:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISecEnvelopeJdbc {
    List getAllSecEnvelope ();
    List getAllSecEnvelopeItemStaging();
    int getNumDocItemInEnvelope(String envBarcode);
    List getActualSecEnvelope (long lspLmtProfId);

}
