package com.integrosys.cms.app.fileUpload.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;

public abstract class FileUploadReplicationUtils {
	public static IFileUpload replicateFileForCreateStagingCopy(IFileUpload anFile) {

		IFileUpload replicatedIdx = (IFileUpload) ReplicateUtils.replicateObject(anFile,
				new String[] { "id"});

        return replicatedIdx;
	}
}
