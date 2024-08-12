package com.integrosys.cms.app.lei.json.ws;


import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;

public interface ICCILWebserviceClient {

	public RetrieveLEICCILResponse sendRetrieveRequest(String leiCode,ICMSCustomer obCustomer,ILEIValidationLog log, String customerCifId) throws Exception;
	public boolean processUploadLeiRequests(OBLeiDetailsFile obLeiDetailsFile);
	
}
