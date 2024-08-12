package com.integrosys.cms.app.json.command;

import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.json.dao.IJsInterfaceLogDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.LineRootResponse;
import com.integrosys.cms.app.json.line.dto.ResponseString;
import com.integrosys.cms.app.json.line.dto.Status;
import com.integrosys.cms.app.json.ws.ILineWebserviceClient;

public class PrepareSendReceiveLineCommand {
	
	public void scmWebServiceCall (String xrefId,String limitProfileId,String limitId,String custId,IJsInterfaceLog log) {
		ILineWebserviceClient clientImpl = (ILineWebserviceClient) BeanHouse.get("lineWebServiceClient") ;
		IJsInterfaceLogDao logDao = (IJsInterfaceLogDao) BeanHouse.get("loggingDao");
		LineRootResponse response;
		try{
			DefaultLogger.debug(this, "SCM webservice for xrefId "+xrefId);
			DefaultLogger.debug(this, "SCM webservice for limitProfileId "+limitProfileId +" "+limitId);
			response = clientImpl.sendRetrieveRequest(xrefId,limitProfileId,limitId,custId,log);
//			DefaultLogger.debug(this, "SCM webservice response "+response);
			ResponseString responseString  = response.getResponseString(); 
			Status statusString = response.getStatus();
			if(responseString!=null) {
			log.setResponseDateTime(DateUtil.now().getTime());
			log.setErrorCode(String.valueOf(responseString.getStatusCode()));
			List errorMsgList = responseString.getResponseMessage();
			StringBuilder errorMsg = new StringBuilder();
			for(int i=0;i<errorMsgList.size();i++) {
				errorMsg.append(errorMsgList.get(i)); 
			}
			log.setErrorMessage(errorMsg.toString());
			log.setStatus(responseString.getStatusCode()==0 ?"Success":"Fail");
			log = logDao.createOrUpdateInterfaceLog(log);
			System.out.println("After if => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
			 DefaultLogger.debug(this,"After if => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
			}else {
				log.setResponseDateTime(DateUtil.now().getTime());
				log.setErrorCode(String.valueOf(statusString.getErrorCode()));
				log.setErrorMessage(statusString.getReplyText());
				log.setStatus("Error");
				log = logDao.createOrUpdateInterfaceLog(log);
				System.out.println("After else => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
				 DefaultLogger.debug(this,"After else => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
			}
		}catch(Exception ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			log.setErrorCode("9");
			log.setErrorMessage(ex.getMessage());
			log.setStatus("Error");
			ex.printStackTrace();
			try {
			log = logDao.createOrUpdateInterfaceLog(log);
			System.out.println("Exception Inside => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
			 DefaultLogger.debug(this,"Exception Inside => logDao.createOrUpdateInterfaceLog(log) ..log.getId()=>"+log.getId());
			}catch(Exception e) {
				DefaultLogger.debug(this, "got exception in update of log table" + e);
				e.printStackTrace();
			}
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
}
}
