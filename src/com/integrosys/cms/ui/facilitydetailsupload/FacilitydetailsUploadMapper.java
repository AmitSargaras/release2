/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.facilitydetailsupload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.facilitydetailsupload.FacilitydetailsUploadForm;
import com.integrosys.cms.ui.facilitydetailsupload.IFacilitydetailsUpload;
import com.integrosys.cms.ui.facilitydetailsupload.OBFacilitydetailsUpload;

/**
 *@author $Author: Abhijit J$
 *Mapper for Holiday
 */

public class FacilitydetailsUploadMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		FacilitydetailsUploadForm form = (FacilitydetailsUploadForm) cForm;

		IFacilitydetailsUpload obItem = null;
		try {
			
				obItem = new OBFacilitydetailsUpload();

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

		FacilitydetailsUploadForm form = (FacilitydetailsUploadForm) cForm;

		return form;
	}


}

