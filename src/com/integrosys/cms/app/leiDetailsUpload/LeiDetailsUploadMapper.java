package com.integrosys.cms.app.leiDetailsUpload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 *@author $Author:  Mukesh Mohapatra$
 *Mapper for CERSAI Acknowledgment Upload
 */

public class LeiDetailsUploadMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		LeiDetailsUploadForm form = (LeiDetailsUploadForm) cForm;

		ILeiDetailsUpload obItem = null;
		try {
			
				obItem = new OBLeiDetailsUpload();

				//File Upload
				if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	            {
					obItem.setFileUpload(form.getFileUpload());
	            }
				

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of File upload", ex);
		}
}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		LeiDetailsUploadForm form = (LeiDetailsUploadForm) cForm;

		return form;
	}


}

