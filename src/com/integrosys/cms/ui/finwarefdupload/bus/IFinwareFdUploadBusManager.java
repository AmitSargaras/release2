package com.integrosys.cms.ui.finwarefdupload.bus;

import java.util.ArrayList;

import com.integrosys.cms.batch.ubs.IUbsErrDetLog;
import com.integrosys.cms.batch.ubs.IUbsErrorLog;

/**
 * @author  Abhijit R. 
 */
public interface IFinwareFdUploadBusManager {
	


	IUbsErrorLog compareFinwareFdfile(ArrayList result,String fileName,String uploadId,IUbsErrDetLog obUbsErrDetLog[]);
}
