package com.integrosys.cms.ui.manualinput.limit;

import com.integrosys.base.uiinfra.common.AbstractCommand;

public class FetchCoBorrowerName_HKCmd extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "coBorrowerDropDownId", "java.lang.String", REQUEST_SCOPE },
				{ "partyId", "java.lang.String", REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		// TODO Auto-generated method stub
		return (new String[][] { { "fcubsCoBorrowerName", "java.lang.String", REQUEST_SCOPE },
				{ "fcubsCoBorrowerResponce", "java.lang.String", REQUEST_SCOPE }, });
	}

	/*public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String coBorrowerDropDownId = (String) map.get("coBorrowerDropDownId");
		String partyId = (String) map.get("partyId");
		DefaultLogger.debug(this, "Co Borrower FCUBS webservice: coBorrowerDropDownId:" + coBorrowerDropDownId + " partyId:" + partyId);

	
		CoBorrower_WsLog CoBorrower_WsLog = new CoBorrower_WsLog();

		DefaultLogger.debug(this, "CoBorrower_WsLog:" + CoBorrower_WsLog);
		CoBorrower_WsLog.setCoBorrowerDropDownId(coBorrowerDropDownId);
		CoBorrower_WsLog.setPartyId(partyId);

		try {
			
			String coBorroer_FCUBS_Webservice=PropertyManager.getValue("fcubs.facility.webservice.CoBorroewr_HK.url");
			DefaultLogger.debug(this, "liability webservice::::"+coBorroer_FCUBS_Webservice);
			if(null!=coBorroer_FCUBS_Webservice){
				coBorroer_FCUBS_Webservice = coBorroer_FCUBS_Webservice.trim(); 
				DefaultLogger.debug(this, "liabilityWebservice.trim()::::"+coBorroer_FCUBS_Webservice);
			}
			
			String source = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.source");
			String userid = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.userid");
			String branch = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.branch");
			String coBorrower_FCUBS_Service = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.service");
			String coBorrower_FCUBS_Operation = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.operation");
			
			String bankCode = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.bankCode");
			String channel = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.channel");
			String serviceCode = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.serviceCode");
			String transactionBranch = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.transactionBranch");
			String userId = PropertyManager.getValue("fcubs.webservice.CoBorroewr_FCUBS.userId");
			String transactingPartyCode = coBorrowerDropDownId;
			
			DefaultLogger.debug(this, " source:" + source + " userid:" + userid + " branch:" + branch
					+ " coBorrower_FCUBS_Service:" + coBorrower_FCUBS_Service + " liabilityOperation:" + coBorrower_FCUBS_Operation);
			DefaultLogger.debug(this,
					" bankCode:" + bankCode + " channel:" + channel + " serviceCode:" + serviceCode
							+ " transactingPartyCode:" + transactingPartyCode + " transactionBranch:"
							+ transactionBranch + " userId:" + userId);

			DefaultLogger.debug(this, "creating FCUBSFetchLiabilityHkApplicationServiceSpiServiceLocator");
			FCUBSFetchLiabilityHkApplicationServiceSpiServiceLocator coBorrowerFCUBSClient = new FCUBSFetchLiabilityHkApplicationServiceSpiServiceLocator();
			DefaultLogger.debug(this, "coBorrowerFCUBSClient:"+coBorrowerFCUBSClient);
			
			DefaultLogger.debug(this, "setting liability endpoint");
			coBorrowerFCUBSClient.setFCUBSFetchLiabilityHkApplicationServiceSpiPortEndpointAddress(coBorroer_FCUBS_Webservice);
			
			DefaultLogger.debug(this, "getting CoBorrower_FCUBS port");
			FCUBSFetchLiabilityHkApplicationServiceSpi coBorrower_FCUBS_ApplicationServiceSpiPort = coBorrowerFCUBSClient.getFCUBSFetchLiabilityHkApplicationServiceSpiPort();

			DefaultLogger.debug(this, "coBorrower_FCUBS_ApplicationServiceSpiPort:"+coBorrower_FCUBS_ApplicationServiceSpiPort);
			Calendar c = Calendar.getInstance();
			String externalReferenceNo = "CLIMSLIABILITY" + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH)
					+ c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND)
					+ c.get(Calendar.MILLISECOND);
			DefaultLogger.debug(this, "externalReferenceNo:" + externalReferenceNo);

			SessionContext sessionContext = new SessionContext();
			sessionContext.setBankCode(bankCode);
			sessionContext.setChannel(channel);
		//	sessionContext.setServiceCode(serviceCode);
			sessionContext.setExternalReferenceNo(externalReferenceNo);
			sessionContext.setTransactingPartyCode(transactingPartyCode);
			sessionContext.setTransactionBranch(transactionBranch);
			sessionContext.setUserId(userId);
			sessionContext.setUserReferenceNumber(externalReferenceNo);
			
			DefaultLogger.debug(this, "sessionContext:" + sessionContext);
			
			FcubsFetchLiabilityHkRequestDTO coBorrower_FCUBS_IOFSRequest = new FcubsFetchLiabilityHkRequestDTO();
			FcubsheaderTypeHk fcubsheader = new FcubsheaderTypeHk();
			FcubsbodyHk fcubsbody = new FcubsbodyHk();

			FetchLiabQueryIOTypeHk liabIO = new FetchLiabQueryIOTypeHk();

			DefaultLogger.debug(this, "fcubsheader:" + fcubsheader + " fcubsbody:" + fcubsbody);
			UbscompTypeHk ubscomp2 = fcubsheader.getUbscomp();
			DefaultLogger.debug(this, "ubscomp2.FCUBS:" + ubscomp2.FCUBS);

			fcubsheader.setSource(source);
			fcubsheader.setUbscomp(ubscomp2.FCUBS);
			fcubsheader.setUserid(userid);
			fcubsheader.setBranch(branch);
			fcubsheader.setService(coBorrower_FCUBS_Service);
			fcubsheader.setOperation(coBorrower_FCUBS_Operation);
		
			DefaultLogger.debug(this, "liabIO before:" + liabIO);
			liabIO.setLiabNo(coBorrowerDropDownId);
			DefaultLogger.debug(this, "liabIO after:" + liabIO);

			fcubsbody.setLiabilityIO(liabIO);

			coBorrower_FCUBS_IOFSRequest.setFcubsheader(fcubsheader);
			coBorrower_FCUBS_IOFSRequest.setFcubsbody(fcubsbody);
			
			WorkItemViewObjectDTO workItemViewObjectDTO = new WorkItemViewObjectDTO();
			WorkItemViewObjectDTO[] arg2 = new WorkItemViewObjectDTO[1];
			arg2[0] = workItemViewObjectDTO;
			DefaultLogger.debug(this, "workItemViewObjectDTO:" + workItemViewObjectDTO + " arg2:" + arg2);
		
			CoBorrower_WsLog.setRequestDateTime(Calendar.getInstance().getTime());
			
			DefaultLogger.debug(this, "calling doQueryLiabilityIO");
			FcubsFetchLiabilityHkResponseDTO response = coBorrower_FCUBS_ApplicationServiceSpiPort.fcubsFetchLiabilityHk(sessionContext,
					coBorrower_FCUBS_IOFSRequest, arg2);
			DefaultLogger.debug(this, "DoQueryLiabilityIOResponseReturn:" + response);

			 DefaultLogger.debug(this,"casting port to stub");
			 FCUBSFetchLiabilityHkApplicationServiceSpiPortBindingStub stub=(FCUBSFetchLiabilityHkApplicationServiceSpiPortBindingStub)coBorrower_FCUBS_ApplicationServiceSpiPort;
			 DefaultLogger.debug(this,"stub:"+stub);
			 String request = stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
			 CoBorrower_WsLog.setRequestMessage(request);
			 DefaultLogger.debug(this,"request:"+request);
			
			 System.out.println("sss request ===================================:"+request);
			 
			String liabresponse = stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
			CoBorrower_WsLog.setResponseMessage(liabresponse);
			DefaultLogger.debug(this,"response:"+liabresponse);
			
			TransactionStatus status = response.getStatus();
			DefaultLogger.debug(this, "status:" + status);
			 System.out.println("sss response ===================================:"+response);
			CoBorrower_WsLog.setResponseDateTime(Calendar.getInstance().getTime());
			if (null != status) {
				CoBorrower_WsLog.setResponseCode(String.valueOf(status.getReplyCode()));
				CoBorrower_WsLog.setErrorMessage(status.getReplyText());

				if (0 == status.getReplyCode()) {

					if (null != response.getFcubsbody() && null != response.getFcubsbody().getLiabilityFull()) {
						FcubsbodyResHk fcubsbody2 = response.getFcubsbody();
						FetchLiabFullTypeHk liabilityFull = fcubsbody2.getLiabilityFull();
						DefaultLogger.debug(this, "liabilityFull:" + liabilityFull);

						String coBorrowerName = liabilityFull.getLiabname();
						DefaultLogger.debug(this, "liabBranch:" + coBorrowerName);

						DefaultLogger.debug(this, "calling getLiabBranch");
						String fcubsLiabResponce = "";
						
						DefaultLogger.debug(this, "coBorrowerName after:" + coBorrowerName);
						DefaultLogger.debug(this, "call FetchCoBorrower Name completed successfully.");

						result.put("fcubsCoBorrowerName", coBorrowerName);
						result.put("fcubsCoBorrowerResponce", fcubsLiabResponce);

					} else {
						DefaultLogger.debug(this, "status.getReplyText():" + status.getReplyText());

						result.put("fcubsCoBorrowerResponce", "Either getFCUBSBODY or getLiabilityFull are returing null.");

					}
				} else {
					DefaultLogger.debug(this, "status.getReplyText():" + status.getReplyText());

					LimitDAO ldao = new LimitDAO();
					String facLiabErrorDesc = ldao.getFacLiabErrorDesc(status.getErrorCode());
					facLiabErrorDesc = status.getErrorCode() + ":" + facLiabErrorDesc;
					DefaultLogger.debug(this, "facLiabErrorDesc:" + facLiabErrorDesc);
					CoBorrower_WsLog.setErrorMessage(facLiabErrorDesc);
					result.put("fcubsCoBorrowerResponce", facLiabErrorDesc);
				}

			} else {
				CoBorrower_WsLog.setErrorMessage("TransactionStatus is null");
				result.put("fcubsCoBorrowerResponce", "TransactionStatus is null");
			}
		} catch (Exception e) {
			DefaultLogger.debug(this, "exception:" + e.getMessage());
			e.printStackTrace();
			if (null != CoBorrower_WsLog)
				CoBorrower_WsLog.setErrorMessage(e.getMessage());
			result.put("fcubsCoBorrowerResponce", e.getMessage());

		}

		if (null != CoBorrower_WsLog) {
			ICoBorrower_FCUBS_WsLogDao coBorrower_FCUBS_WsLogDao = (ICoBorrower_FCUBS_WsLogDao) BeanHouse.get("coBorrower_FCUBS_WsLogDao");
			ICoBorrower_WsLog createLiabilityWsLog = coBorrower_FCUBS_WsLogDao.createCoBorrowerWsLog(CoBorrower_WsLog);
			DefaultLogger.debug(this, "createLiabilityWsLog id:" + createLiabilityWsLog.getId());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}*/
}
