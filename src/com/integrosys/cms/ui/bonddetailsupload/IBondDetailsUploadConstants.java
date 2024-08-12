package com.integrosys.cms.ui.bonddetailsupload;

import com.integrosys.cms.ui.common.TrxContextMapper;

public interface IBondDetailsUploadConstants {

	String BOND_DETAILS_UPLOAD_FORM = "form.bondDetailsUploadObj";
	String BOND_DETAILS_UPLOAD_MAPPER = BondDetailsUploadMapper.class.getName();
	
	String TRX_MAPPER = TrxContextMapper.class.getName();
	String SESSION_TRX_OBJ = "theOBTrxContext";
	String BOND_DETAILS_UPLOAD = "BondDetailsUpload";

	String BOND = "Bond";
	
	String SESSION_TRX_VALUE_OUT = "trxValueOut";
	String SESSION_TOTAL_UPLOADED_LIST = "totalUploadedList";
	String SESSION_TOTAL_FAILED_LIST = "totalFailedList";
	
}
