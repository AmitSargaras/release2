package com.integrosys.cms.app.lei.json.command;


import java.util.Calendar;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.*;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLogDao;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse;
import com.integrosys.cms.app.lei.json.ws.ICCILWebserviceClient;


public class PrepareSendReceiveCCILCommand {
	
	
	public RetrieveLEICCILResponse ccilWebServiceCall (String loginId,String leiCode,ICMSCustomer obCustomer,ILEIValidationLog log, String customerCifId) {
		ICCILWebserviceClient clientImpl = (ICCILWebserviceClient) BeanHouse.get("leiWebServiceClient") ;
		ILEIValidationLogDao logDao = (ILEIValidationLogDao) BeanHouse.get("logLeiDao");
		RetrieveLEICCILResponse response = null;
		try{
			response = clientImpl.sendRetrieveRequest(leiCode,obCustomer,log,customerCifId);
			DefaultLogger.debug(this, "LEI webservice response "+response);
			if(response!=null) {
				log.setResponseDateTime(DateUtil.now().getTime());
				log.setErrorCode(response.getErrorCode());
				log.setErrorMessage(response.getErrorMessage());
				String code = response.getErrorCode();
				if(PropertyManager.getValue("ccil.success.code").equals(code)) {
					log.setIsLEICodeValidated('Y');
					log.setStatus("Success");
					log.setLastValidatedBy(loginId);
					log.setLastValidatedDate(Calendar.getInstance().getTime());	
				}else {
					log.setIsLEICodeValidated('N');
					log.setStatus("Fail");
				}
				log = logDao.createOrUpdateInterfaceLog(log);
			}else {
				log.setResponseDateTime(DateUtil.now().getTime());
//				log.setErrorCode("LAE014");
//				log.setErrorMessage("Authentication Failed.");
				log.setStatus("Fail");
				log.setIsLEICodeValidated('N');
				log = logDao.createOrUpdateInterfaceLog(log);
			}
//			obCustomer.setIsLeiValidated(log.getIsLEICodeValidated());

			return response;
			
		}catch(Exception ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			log.setResponseDateTime(DateUtil.now().getTime());
			log.setErrorCode("");
			log.setErrorMessage("Exception occurred during request");
			log.setStatus("Fail");
			log.setIsLEICodeValidated('N');
			try {
				log = logDao.createOrUpdateInterfaceLog(log);
//				obCustomer.setIsLeiValidated(log.getIsLEICodeValidated());
			}catch(Exception e) {
				DefaultLogger.debug(this, "got exception in update of log table" + e);
				e.printStackTrace();
			}
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
	}
}

