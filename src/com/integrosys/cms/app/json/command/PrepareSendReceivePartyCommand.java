package com.integrosys.cms.app.json.command;


import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.*;
import com.integrosys.cms.app.json.dao.IJsInterfaceLogDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.party.dto.Status;
import com.integrosys.cms.app.json.party.dto.PartyRootResponse;
import com.integrosys.cms.app.json.party.dto.ResponseString;
import com.integrosys.cms.app.json.ws.IPartyWebserviceClient;


public class PrepareSendReceivePartyCommand {
	
		public void scmWebServiceCall (String custId,String action,String scmFlag,IJsInterfaceLog log) {
			IPartyWebserviceClient clientImpl = (IPartyWebserviceClient) BeanHouse.get("partyWebServiceClient") ;
			IJsInterfaceLogDao logDao = (IJsInterfaceLogDao) BeanHouse.get("loggingDao");
			PartyRootResponse response = null;
			try{
				DefaultLogger.debug(this, "SCM webservice lspLeId "+custId);
				response = clientImpl.sendRetrieveRequest(custId,action,scmFlag,log);
//				DefaultLogger.debug(this, "SCM webservice response "+response);
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
				}else {
					log.setResponseDateTime(DateUtil.now().getTime());
					log.setErrorCode(statusString.getErrorCode());
					log.setErrorMessage(statusString.getReplyText());
					log.setStatus("Error");
					log = logDao.createOrUpdateInterfaceLog(log);
				}
			}catch(Exception ex){
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				log.setErrorCode("9");
				log.setErrorMessage(ex.getMessage());
				log.setStatus("Error");
				try {
				log = logDao.createOrUpdateInterfaceLog(log);
				}catch(Exception e) {
					DefaultLogger.debug(this, "got exception in update of log table" + e);
					e.printStackTrace();
				}
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
	}
}

