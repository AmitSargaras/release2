package com.integrosys.cms.app.securityenvelope.bus;

/**
 * Created by IntelliJ IDEA.
 * User: Erene Wong
 * Date: Jan 11, 2010
 * Time: 3:11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SecEnvelopeBusManagerStagingImpl extends AbstractSecEnvelopeBusManager {

    public String getSecEnvelopeEntityName() {
        return ISecEnvelopeDao.STAGE_SECURITY_ENVELOPE_ENTITY_NAME;
    }

    public ISecEnvelope updateToWorkingCopy(ISecEnvelope workingCopy, ISecEnvelope imageCopy)
            throws SecEnvelopeException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

}