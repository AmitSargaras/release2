package com.integrosys.cms.ui.partycamupload.dao;

import java.util.ArrayList;

import com.integrosys.cms.batch.partycam.IPartyCamErrDetLog;
import com.integrosys.cms.batch.partycam.IPartyCamErrorLog;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 14-04-2011
	 */

public interface IPartyCamUploadDAO {
	
	public IPartyCamErrorLog insertPartyCamUpload( ArrayList result,String fileName,String uploadId,IPartyCamErrDetLog[] obPartyCamErrDetLog);
}
