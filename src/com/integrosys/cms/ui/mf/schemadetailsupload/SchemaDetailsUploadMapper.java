package com.integrosys.cms.ui.mf.schemadetailsupload;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

public class SchemaDetailsUploadMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		SchemaDetailsUploadForm form = (SchemaDetailsUploadForm) cForm;
		
		ISchemaDetailsUpload obj = new OBSchemaDetailsUpload();
		obj.setFileUpload(form.getFileUpload());
		
		return obj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		SchemaDetailsUploadForm form = (SchemaDetailsUploadForm) cForm;
		return form;
	}

}
