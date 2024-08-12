package com.integrosys.cms.app.securityenvelope.bus;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Erene Wong
 * @since 04 Feb 2010
 */
public abstract class SecEnvelopeReplicationUtils {

	/**
	 * <p>
	 * Replicate Security Envelope which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static ISecEnvelope replicateSecEnvelopeForCreateStagingCopy(ISecEnvelope secEnvelope) {
        Set tempItemSet = new HashSet();

        for (Iterator iterator = secEnvelope.getSecEnvelopeItemList().iterator(); iterator.hasNext();) {
            OBSecEnvelopeItem secEnvelopeItem = (OBSecEnvelopeItem) iterator.next();
            secEnvelopeItem.setSecEnvelopeItemId(0);
            tempItemSet.add(secEnvelopeItem);
        }
        ISecEnvelope replicatedEnv = (ISecEnvelope) ReplicateUtils.replicateObject(secEnvelope,
				new String[] { "secEnvelopeId"});

		replicatedEnv.setSecEnvelopeItemList(tempItemSet);
        return replicatedEnv;
	}
}