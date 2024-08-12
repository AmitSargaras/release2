package com.integrosys.cms.ui.partycamupload.bus;

import java.util.ArrayList;

import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;

/**
 * @author  Abhijit R. 
 */
public interface IPartyCamUploadBusManager {
	


	IPartyCamErrorLog insertPartyCamUpload( ArrayList resultList,String fileName,String uploadId,IPartyCamErrDetLog[] obPartyCamErrDetLog);

}
