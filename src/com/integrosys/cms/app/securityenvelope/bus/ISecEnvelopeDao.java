package com.integrosys.cms.app.securityenvelope.bus;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Erene Wong
 * Date: Jan 25, 2010
 * Time: 4:17:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISecEnvelopeDao {
    // entity name for actual security envelope
    public static final String ACTUAL_SECURITY_ENVELOPE_ENTITY_NAME = "actualSecEnvelope";
    // entity name for stage security envelope
    public static final String STAGE_SECURITY_ENVELOPE_ENTITY_NAME = "stageSecEnvelope";
    // entity name for actual security envelope item
    public static final String ACTUAL_SECURITY_ENVELOPE_ITEM_ENTITY_NAME = "actualSecEnvelopeItem";
    // entity name for stage security envelope item
    public static final String STAGE_SECURITY_ENVELOPE_ITEM_ENTITY_NAME = "stageSecEnvelopeItem";
    
    ISecEnvelope createSecEnvelope(String entityName, ISecEnvelope secEnvelope);

    ISecEnvelope getSecEnvelope(String entityName, long key);

    ISecEnvelope updateSecEnvelope(String entityName, ISecEnvelope secEnvelope);
    
    public List getEnvelopeItemByLimitProfileId(final long limitProfileid);
}
