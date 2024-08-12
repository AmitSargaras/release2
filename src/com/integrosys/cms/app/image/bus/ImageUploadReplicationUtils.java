package com.integrosys.cms.app.image.bus;

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * Replication utility used for replicating stock to interact with persistent
 *
 * @author Govind.Sahu
 */
public abstract class ImageUploadReplicationUtils {

	/**
	 * <p>
	 * Replicate Property Index which is ready to create a entity into
	 * persistent storage. Normally to create a staging copy will use this.
	 *
	 * <p>
	 * <b>note</b> must know which field(s) need to be filtered when doing
	 * replication
	 *
	 */
	public static IImageUploadDetails replicateImageUploadForCreateStagingCopy(IImageUploadDetails staging) {
		
		IImageUploadDetails replicatedIdx = (IImageUploadDetails) ReplicateUtils
		.replicateObject(staging, new String[] { "" });
        return replicatedIdx;
	}
	
	
public static IImageUploadAdd replicateImageUploadForCreateStagingCopy(IImageUploadAdd staging) {
		
		OBImageUploadAdd replicatedImageUpload = new OBImageUploadAdd();
		replicatedImageUpload.setImgId(staging.getImgId());
		replicatedImageUpload.setImgFileName(staging.getImgFileName());
        replicatedImageUpload.setImgSize((staging.getImgSize()));
        replicatedImageUpload.setCustId(staging.getCustId());
        replicatedImageUpload.setCustName(staging.getCustName());
        replicatedImageUpload.setImgDepricated(staging.getImgDepricated());
        replicatedImageUpload.setImageFilePath(staging.getImageFilePath());

        return replicatedImageUpload;
	}
}