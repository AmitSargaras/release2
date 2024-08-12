package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

public class StockDetailsUploadMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		
		StockDetailsUploadForm form = (StockDetailsUploadForm)cForm;
		
		IStockDetailsUpload obItem = new OBStockDetailsUpload();
		
		if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
        {
			obItem.setFileUpload(form.getFileUpload());
        }
		
		return obItem;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		
		StockDetailsUploadForm form = (StockDetailsUploadForm)cForm;
		
		return form;
	}

}
