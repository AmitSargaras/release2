package com.integrosys.cms.batch.leiInterface.schedular;

import java.util.List;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.lei.bus.ILeiJdbc;
import com.integrosys.cms.app.lei.json.ws.ICCILWebserviceClient;

public class LeiInterfaceJob {
	
	public static void main(String[] args) {

		new LeiInterfaceJob().execute();
	}
	
	public LeiInterfaceJob() {}
	
	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String leiInterfaceServerName = bundle.getString("lei.interface.server.name");
		System.out.println("<<<<....In execute() LeiInterfaceJob Starting....>>>>" + leiInterfaceServerName);
		if (null != leiInterfaceServerName && leiInterfaceServerName.equalsIgnoreCase("app1")) {
			processLeiValidationService();
		}
	}	
												
	public void processLeiValidationService() {
		ILeiJdbc leiJdbc = (ILeiJdbc)BeanHouse.get("leiJdbc");		
		ICCILWebserviceClient clientCcilImpl = (ICCILWebserviceClient)BeanHouse.get("leiWebServiceClient");					
		try {
			int successCount=0;
			System.out.println("Starting LeiInterfaceJob for Validate LEI....");
			List obLeiDetailsFileList =  leiJdbc.getUploadDetailsListForLeiValidation();
			for(int i=0;i<=obLeiDetailsFileList.size();i++) {
				System.out.println("Inside the loop to sent requests for validate LEI...");
				OBLeiDetailsFile obLeiDetailsFile = (OBLeiDetailsFile) obLeiDetailsFileList.get(i);
			    boolean success = false;
				success = clientCcilImpl.processUploadLeiRequests(obLeiDetailsFile);
				if(success) {
					leiJdbc.updateLeiValidationFlag(obLeiDetailsFile);
					successCount++;
					System.out.println("Successfully sent request for party with Id: " + obLeiDetailsFile.getPartyId());
				}else {
					System.out.println("No record available to send CCIL for LEI Validation(s) through scheduler....");
					break;
				}
			}
			System.out.println(successCount +" request(s) to CCIL for LEI Validation Successfully completed");			
		}catch (Exception e) {
			System.out.println("LeiInterfaceJob in catch Exception......" + e.getMessage());
			e.printStackTrace();
		}
	}	
}
