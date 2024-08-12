package com.integrosys.cms.ui.bonddetailsupload;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

public class BondDetailsUploadMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		BondDetailsUploadForm form = (BondDetailsUploadForm) cForm;
		
		IBondDetailsUpload obj = new OBBondDetailsUpload();
		obj.setFileUpload(form.getFileUpload());
		
		return obj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		BondDetailsUploadForm form = (BondDetailsUploadForm) cForm;
		return form;
	}

}
