package com.integrosys.cms.app.securityenvelope.bus;


import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Erene
 * Date: Jan 25, 2010
 * Time: 7:41:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISecEnvelopeBusManager {
    ISecEnvelopeDao getSecEnvelopeDao();

    void setSecEnvelopeDao(ISecEnvelopeDao secEnvelopeDao);

    ISecEnvelopeJdbc getSecEnvelopeJdbc();

    void setSecEnvelopeJdbc(ISecEnvelopeJdbc secEnvelopeJdbc);

    ISecEnvelope createSecEnvelope(ISecEnvelope secEnvelope);

    ISecEnvelope getSecEnvelope(long key);

    ISecEnvelope updateSecEnvelope(ISecEnvelope secEnvelope);

    List getAllSecEnvelope ();

    List getAllSecEnvelopeItemStaging();

    int getNumDocItemInEnvelope(String envBarcode);

    List getActualSecEnvelope(long lspLmtProfileId);

    ISecEnvelope updateToWorkingCopy(ISecEnvelope workingCopy, ISecEnvelope imageCopy) throws SecEnvelopeException;
    
    List getEnvelopeItemByLimitProfileId(long lspLmtProfileId);

}
