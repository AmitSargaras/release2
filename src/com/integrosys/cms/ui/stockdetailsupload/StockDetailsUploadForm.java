package com.integrosys.cms.ui.stockdetailsupload;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.common.TrxContextMapper;

public class StockDetailsUploadForm extends TrxContextForm implements Serializable, IStockDetailsUploadConstants{
	
	private FormFile fileUpload;
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String[][] getMapper() {
		String[][] input = { 
				{ STOCK_DETAILS_UPLOAD_FORM, StockDetailsUploadMapper.class.getName() },
				{ SESSION_TRX_OBJ, TrxContextMapper.class.getName()} };
		return input;
	}

}
