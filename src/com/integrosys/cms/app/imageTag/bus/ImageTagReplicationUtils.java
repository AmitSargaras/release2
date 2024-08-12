package com.integrosys.cms.app.imageTag.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

/**
 * Replication utility used for replicating stock to interact with persistent
 * 
 * @author abhijit.rudrakshawar
 */
public abstract class ImageTagReplicationUtils {

	/**
	 * <p>
	 * Replicate Image Tag which is ready to create a entity into persistent
	 * storage. Normally to create a staging copy will use this.
	 * 
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 * 
	 */
	public static IImageTagDetails replicateImageTagForCreateStagingCopy(
			IImageTagDetails imageTag) {

		IImageTagDetails replicatedIdx = (IImageTagDetails) ReplicateUtils
		.replicateObject(imageTag, new String[] { "" });

		return replicatedIdx;
	}
}