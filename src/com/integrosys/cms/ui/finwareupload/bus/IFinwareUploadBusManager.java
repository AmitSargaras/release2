package com.integrosys.cms.ui.finwareupload.bus;

import java.util.ArrayList;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

/**
 * @author  Abhijit R. 
 */
public interface IFinwareUploadBusManager {
	


	IUbsErrorLog insertFinwareUpload(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]);
}
