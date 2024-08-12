package com.integrosys.cms.batch.ecbf.counterparty;

import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.ecbf.counterparty.IECBFCustomerInterfaceLog;
import com.integrosys.cms.app.ecbf.counterparty.IECBFCustomerInterfaceLogDAO;
import com.integrosys.cms.app.ecbf.counterparty.IECBFCustomerInterfaceLogJDBC;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;

public class SendPartyToECBFJob {

	private final static Logger logger = LoggerFactory.getLogger(SendPartyToECBFJob.class);
	
	public static void main(String[] args) {
		new SendPartyToECBFJob().execute();
	}
	
	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String serverName = bundle.getString("send.party.to.ecbf.server.name");
		String udfName=bundle.getString("psl.upload.ecbf.udf");
		
		if(null!= serverName && serverName.equalsIgnoreCase("app1")) {
			printOut("::: Starting SendPartyToECBFJob :::", "info", null);
			IECBFCustomerInterfaceLogJDBC ecbfCustomerInterfaceLogJDBC = (IECBFCustomerInterfaceLogJDBC) BeanHouse.get("ecbfCustomerInterfaceLogJDBC");
			
			printOut("Starting batch job to resent all failed request to ECBF party webservice at :" + DateUtil.getDate(), "info", null);
	
			Map<String, Long> failedMap = null;
			try {
				failedMap = ecbfCustomerInterfaceLogJDBC.findAllFailedRequest(CommonUtil.getCurrentDate()); 
			} catch (Exception e) {
				e.printStackTrace();
				printOut("Exception while fetching request which failed on  "+ CommonUtil.getCurrentDate() +" with error: " + e.getMessage(), "error", e);
			}
			
			if(failedMap != null && failedMap.size() > 0) {
				long successCount = 0;
				IECBFCustomerInterfaceLogDAO ecbfCustomerInterfaceLogDAO = (IECBFCustomerInterfaceLogDAO) BeanHouse.get("ecbfCustomerInterfaceLogDAO");
				printOut("Total of "+ failedMap.size() + " request(s) to be resent to ECBF party webservice", "info", null);
				for(String partyId : failedMap.keySet()) {
					Long logId = failedMap.get(partyId);
					printOut("Resending latest failed request for partyId: " + partyId + " with transactionrefNo: " + logId, "info", null);
					try {
						IECBFCustomerInterfaceLog log = ecbfCustomerInterfaceLogDAO.getInterfaceLog(logId);
						boolean success = ClimesToECBFHelper.sendRequest(log);
						if(success) {
							successCount++;
							printOut("Successfully resent failed request for partyId: " + partyId + " with transactionrefNo: " + logId, "info", null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						printOut("Exception caught inside sendRequest with error: " + e.getMessage(), "error", e);
					}
				}
				printOut(successCount +" request(s) to ECBF party webservice resent successfully", "info", null);
			}
			
			printOut("Batch job completed at " + DateUtil.getDate(), "info", null);
			printOut("::: End SendPartyToECBFJob :::", "info", null);
		}
	}
	
	private static void printOut(String value, String type, Exception ex) {
		if(type.equals("info"))
			logger.info(value);
		if(type.equals("debug"))
			logger.debug(value);
		if(type.equals("error"))
			logger.error(value, ex);
	}
	
}