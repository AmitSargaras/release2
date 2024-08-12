package com.integrosys.cms.ui.ubsupload.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

/**
 * @author  Abhijit R. 
 */
public interface IUbsUploadBusManager {
	


	IUbsErrorLog insertUbsUpload( ArrayList resultList,String fileName,String uploadId,IUbsErrDetLog[] obUbsErrDetLog);

}
