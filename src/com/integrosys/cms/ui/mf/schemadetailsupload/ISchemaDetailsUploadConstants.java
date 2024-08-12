package com.integrosys.cms.ui.mf.schemadetailsupload;

import com.integrosys.cms.ui.common.TrxContextMapper;

public interface ISchemaDetailsUploadConstants {

	String SCHEMA_DETAILS_UPLOAD_FORM = "form.schemaDetailsUploadObj";
	String SCHEMA_DETAILS_UPLOAD_MAPPER = SchemaDetailsUploadMapper.class.getName();
	
	String TRX_MAPPER = TrxContextMapper.class.getName();
	String SESSION_TRX_OBJ = "theOBTrxContext";
	String MFSCHEMADETAILS_UPLOAD = "MFSchemaDetailsUpload";
	String MUTUAL_FUND = "MF";

	String SESSION_TRX_VALUE_OUT = "trxValueOut";
	String SESSION_TOTAL_UPLOADED_LIST = "totalUploadedList";
	String SESSION_TOTAL_FAILED_LIST = "totalFailedList";
	
}
