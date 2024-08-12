package com.integrosys.cms.ui.manualinput.limit;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.limit.bus.ILiabilityWsLogDao;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.DoQueryLiabilityIOResponseReturn;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.DoQueryLiabilityIOResponseReturnFCUBS_BODY;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElliabilityApplicationServiceSpi;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElliabilityApplicationServiceSpiPortBindingStub;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.ElliabilityApplicationServiceSpiServiceLocator;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyreq;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionFullTypeDTO;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryLiablityIOFSRequest;
import com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType;
import com.ofss.fc.framework.domain.WorkItemViewObjectDTO;
import com.ofss.fc.service.response.TransactionStatus;

public class FetchLiabBranchCmd extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "sysLiabID", "java.lang.String", REQUEST_SCOPE },
				{ "partyId", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "fcubsLiabBranch", "java.lang.String", REQUEST_SCOPE },
				{ "fcubsLiabResponce", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String sysLiabID = (String) map.get("sysLiabID");
		String partyId = (String) map.get("partyId");
		DefaultLogger.debug(this, "Liability webservice: sysLiabID:" + sysLiabID + " partyId:" + partyId);

	
		ILiabilityWsLog liabilityWsLog = new LiabilityWsLog();

		DefaultLogger.debug(this, "liabilityWsLog:" + liabilityWsLog);
		liabilityWsLog.setFacilitySysId(sysLiabID);
		liabilityWsLog.setPartyId(partyId);

		try {
			
			String liabilityWebservice=PropertyManager.getValue("fcubs.liability.webservice.url");
			DefaultLogger.debug(this, "liability webservice::::"+liabilityWebservice);
			if(null!=liabilityWebservice){
				liabilityWebservice = liabilityWebservice.trim(); 
				DefaultLogger.debug(this, "liabilityWebservice.trim()::::"+liabilityWebservice);
			}
			
			String source = PropertyManager.getValue("fcubs.webservice.liability.source");
			String userid = PropertyManager.getValue("fcubs.webservice.liability.userid");
			String branch = PropertyManager.getValue("fcubs.webservice.liability.branch");
			String liabilityService = PropertyManager.getValue("fcubs.webservice.liability.service");
			String liabilityOperation = PropertyManager.getValue("fcubs.webservice.liability.operation");
			String bankCode = PropertyManager.getValue("fcubs.webservice.liability.bankCode");
			String channel = PropertyManager.getValue("fcubs.webservice.liability.channel");
			String serviceCode = PropertyManager.getValue("fcubs.webservice.liability.serviceCode");
			String transactionBranch = PropertyManager.getValue("fcubs.webservice.liability.transactionBranch");
			String userId = PropertyManager.getValue("fcubs.webservice.liability.userId");
			String transactingPartyCode = sysLiabID;
			
			DefaultLogger.debug(this, " source:" + source + " userid:" + userid + " branch:" + branch
					+ " liabilityService:" + liabilityService + " liabilityOperation:" + liabilityOperation);
			DefaultLogger.debug(this,
					" bankCode:" + bankCode + " channel:" + channel + " serviceCode:" + serviceCode
							+ " transactingPartyCode:" + transactingPartyCode + " transactionBranch:"
							+ transactionBranch + " userId:" + userId);

			DefaultLogger.debug(this, "creating ElliabilityApplicationServiceSpiServiceLocator");
			ElliabilityApplicationServiceSpiServiceLocator liabClient = new ElliabilityApplicationServiceSpiServiceLocator();
			DefaultLogger.debug(this, "liabClient:"+liabClient);
			
			DefaultLogger.debug(this, "setting liability endpoint");
			liabClient.setElliabilityApplicationServiceSpiPortEndpointAddress(liabilityWebservice);
			
			DefaultLogger.debug(this, "getting liability port");
			ElliabilityApplicationServiceSpi elliabilityApplicationServiceSpiPort = liabClient.getElliabilityApplicationServiceSpiPort();
			DefaultLogger.debug(this, "elliabilityApplicationServiceSpiPort:"+elliabilityApplicationServiceSpiPort);
			Calendar c = Calendar.getInstance();
			String externalReferenceNo = "CLIMSLIABILITY" + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH)
					+ c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND)
					+ c.get(Calendar.MILLISECOND);
			DefaultLogger.debug(this, "externalReferenceNo:" + externalReferenceNo);

			SessionContext sessionContext = new SessionContext();
			sessionContext.setBankCode(bankCode);
			sessionContext.setChannel(channel);
			sessionContext.setServiceCode(serviceCode);
			sessionContext.setExternalReferenceNo(externalReferenceNo);
			sessionContext.setTransactingPartyCode(transactingPartyCode);
			sessionContext.setTransactionBranch(transactionBranch);
			sessionContext.setUserId(userId);

			DefaultLogger.debug(this, "sessionContext:" + sessionContext);
			QueryLiablityIOFSRequest liablityIOFSRequest = new QueryLiablityIOFSRequest();
			FcubsHeaderType fcubsheader = new FcubsHeaderType();
			Fcubsbodyreq fcubsbody = new Fcubsbodyreq();

			LiabilityDescriptionPKType liabIO = new LiabilityDescriptionPKType();

			DefaultLogger.debug(this, "fcubsheader:" + fcubsheader + " fcubsbody:" + fcubsbody);
			UbscompType ubscomp2 = fcubsheader.getUbscomp();
			DefaultLogger.debug(this, "ubscomp2.FCUBS:" + ubscomp2.FCUBS);

			fcubsheader.setSource(source);
			fcubsheader.setUbscomp(ubscomp2.FCUBS);
			fcubsheader.setUserid(userid);
			fcubsheader.setBranch(branch);
			fcubsheader.setService(liabilityService);
			fcubsheader.setOperation(liabilityOperation);
		
			DefaultLogger.debug(this, "liabIO before:" + liabIO);
			liabIO.setLiabilityno(sysLiabID);
			DefaultLogger.debug(this, "liabIO after:" + liabIO);

			fcubsbody.setLiabilityIO(liabIO);

			liablityIOFSRequest.setFcubsheader(fcubsheader);
			liablityIOFSRequest.setFcubsbody(fcubsbody);

			WorkItemViewObjectDTO workItemViewObjectDTO = new WorkItemViewObjectDTO();
			WorkItemViewObjectDTO[] arg2 = new WorkItemViewObjectDTO[1];
			arg2[0] = workItemViewObjectDTO;
			DefaultLogger.debug(this, "workItemViewObjectDTO:" + workItemViewObjectDTO + " arg2:" + arg2);
		
			liabilityWsLog.setRequestDateTime(Calendar.getInstance().getTime());
			
			DefaultLogger.debug(this, "calling doQueryLiabilityIO");
			DoQueryLiabilityIOResponseReturn response = elliabilityApplicationServiceSpiPort.doQueryLiabilityIO(sessionContext,
					liablityIOFSRequest, arg2);
			DefaultLogger.debug(this, "DoQueryLiabilityIOResponseReturn:" + response);

			 DefaultLogger.debug(this,"casting port to stub");
			 ElliabilityApplicationServiceSpiPortBindingStub stub=(ElliabilityApplicationServiceSpiPortBindingStub)elliabilityApplicationServiceSpiPort;
			 DefaultLogger.debug(this,"stub:"+stub);
			 String request = stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
			 liabilityWsLog.setRequestMessage(request);
			 DefaultLogger.debug(this,"request:"+request);
				
			String liabresponse = stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
			liabilityWsLog.setResponseMessage(liabresponse);
			DefaultLogger.debug(this,"response:"+liabresponse);
			
			TransactionStatus status = response.getStatus();
			DefaultLogger.debug(this, "status:" + status);

			liabilityWsLog.setResponseDateTime(Calendar.getInstance().getTime());
			if (null != status) {
				liabilityWsLog.setResponseCode(String.valueOf(status.getReplyCode()));
				liabilityWsLog.setErrorMessage(status.getReplyText());

				if (0 == status.getReplyCode()) {

					if (null != response.getFCUBS_BODY() && null != response.getFCUBS_BODY().getLiabilityFull()) {
						DoQueryLiabilityIOResponseReturnFCUBS_BODY fcubsbody2 = response.getFCUBS_BODY();
						LiabilityDescriptionFullTypeDTO liabilityFull = fcubsbody2.getLiabilityFull();
						DefaultLogger.debug(this, "liabilityFull:" + liabilityFull);

						String liabBranch = liabilityFull.getLiabilitybranch();
						DefaultLogger.debug(this, "liabBranch:" + liabBranch);

						DefaultLogger.debug(this, "calling getLiabBranch");
						String fcubsLiabResponce = "";
						IFCCBranchDao fccBranchDao = (IFCCBranchDao) BeanHouse.get("fccBranchDao");
						List fccBranchList = fccBranchDao.getFccBranchList();

						DefaultLogger.debug(this, "liabBranch before:" + liabBranch);
						DefaultLogger.debug(this, "Iterating fccbranchlist");
						for (int i = 0; i < fccBranchList.size(); i++) {
							IFCCBranch fccBranch = (IFCCBranch) fccBranchList.get(i);
							if (fccBranch.getStatus().equals("ACTIVE")) {
								String label = fccBranch.getBranchCode();
								if (null != label && !label.isEmpty() && label.equals(liabBranch)) {
									String value = Long.toString(fccBranch.getId());
									liabBranch = liabBranch + "-" + value;
									break;
								}
							}
						}
						DefaultLogger.debug(this, "liabBranch after:" + liabBranch);
						DefaultLogger.debug(this, "call FetchLiabBranch completed successfully.");

						result.put("fcubsLiabBranch", liabBranch);
						result.put("fcubsLiabResponce", fcubsLiabResponce);

					} else {
						DefaultLogger.debug(this, "status.getReplyText():" + status.getReplyText());

						result.put("fcubsLiabResponce", "Either getFCUBSBODY or getLiabilityFull are returing null.");

					}
				} else {
					DefaultLogger.debug(this, "status.getReplyText():" + status.getReplyText());

					LimitDAO ldao = new LimitDAO();
					String facLiabErrorDesc = ldao.getFacLiabErrorDesc(status.getErrorCode());
					facLiabErrorDesc = status.getErrorCode() + ":" + facLiabErrorDesc;
					DefaultLogger.debug(this, "facLiabErrorDesc:" + facLiabErrorDesc);
					liabilityWsLog.setErrorMessage(facLiabErrorDesc);
					result.put("fcubsLiabResponce", facLiabErrorDesc);
				}

			} else {
				liabilityWsLog.setErrorMessage("TransactionStatus is null");
				result.put("fcubsLiabResponce", "TransactionStatus is null");
			}
		} catch (Exception e) {
			DefaultLogger.debug(this, "exception:" + e.getMessage());
			e.printStackTrace();
			if (null != liabilityWsLog)
				liabilityWsLog.setErrorMessage(e.getMessage());
			result.put("fcubsLiabResponce", e.getMessage());

		}

		if (null != liabilityWsLog) {
			ILiabilityWsLogDao liabilityWsLogDao = (ILiabilityWsLogDao) BeanHouse.get("liabilityWsLogDao");
			ILiabilityWsLog createLiabilityWsLog = liabilityWsLogDao.createLiabilityWsLog(liabilityWsLog);
			DefaultLogger.debug(this, "createLiabilityWsLog id:" + createLiabilityWsLog.getId());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}
}
