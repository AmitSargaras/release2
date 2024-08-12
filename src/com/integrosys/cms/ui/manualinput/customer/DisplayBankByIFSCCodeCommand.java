package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.manualinput.party.IIfsccodeWsLogDao;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiServiceLocator;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryDTO;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes;
import com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryResDTO;
import com.ofss.fc.framework.domain.WorkItemViewObjectDTO;
import com.ofss.fc.service.response.TransactionStatus;

public class DisplayBankByIFSCCodeCommand extends AbstractCommand {

	private IOtherBankProxyManager otherBankProxyManager;

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	protected static ResourceBundle resourceBundle;

	static {
		resourceBundle = ResourceBundle.getBundle("ofa", Locale.getDefault());
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "ifscCode", "java.lang.String", REQUEST_SCOPE }, { "bankName", "java.lang.String", REQUEST_SCOPE },
				{ "bankBranch", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "ifscCodeValue", "java.lang.String", REQUEST_SCOPE },
				{ "bankNameValue", "java.lang.String", REQUEST_SCOPE },
				{ "bankBranchNameValue", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
				{ "OtherBankList", "java.util.List", SERVICE_SCOPE },
				{ "ifscWsResponce", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the results
	 * back into the HashMap.Here get data from database for Interest Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		String ifscCodeValue = (String) map.get("ifscCode");
		String bankNameValue = (String) map.get("bankName");
		String bankBranchNameValue = (String) map.get("bankBranch");
		List OtherBankList = new ArrayList();

		String ifscWsResponce = "";
		DefaultLogger.info(this, "Inside doExecute() DisplayBankByIFSCCodeCommand " + event);
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		String partyId = obCustomer.getCifId();
		
		IIfsccodeWsLog ifsccodeWsLog = new IfsccodeWsLog();
		ifsccodeWsLog.setPartyId(partyId);
		ifsccodeWsLog.setBankName(bankNameValue);
		ifsccodeWsLog.setBranchName(bankBranchNameValue);
		ifsccodeWsLog.setIfscCode(ifscCodeValue);
		
		FinancialInstitutionInquiryResDTO fetchFinancialResponse = null;

		try {
			String ifscCodeEndPoint = getFieldValue("IFSC_CODE_BANK_DETAILS_URL");
			DefaultLogger.debug(this, "ifscCodeEndPoint::::" + ifscCodeEndPoint);
			if (ifscCodeEndPoint != null) {
				ifscCodeEndPoint = ifscCodeEndPoint.trim();
				DefaultLogger.debug(this, "ifscCodeEndPoint.trim()::::" + ifscCodeEndPoint);
			}

			String bankCode = PropertyManager.getValue("ifsccode.webservice.bankCode");
			String channel = PropertyManager.getValue("ifsccode.webservice.channel");
			String transactionBranch = PropertyManager.getValue("ifsccode.webservice.transactionBranch");
			String userId = PropertyManager.getValue("ifsccode.webservice.userId");
			String transactingPartyCode = PropertyManager.getValue("ifsccode.webservice.transactingPartyCode");

			DefaultLogger.debug(this, " bankCode:" + bankCode + " channel:" + channel + " transactionBranch:"
					+ transactionBranch + " userId:" + userId + " transactingPartyCode:" + transactingPartyCode);

			DefaultLogger.debug(this, "calling FinancialInstitutionApplicationServiceSpi_Service()...");
			FinancialInstitutionInquiryApplicationServiceSpiServiceLocator service = new FinancialInstitutionInquiryApplicationServiceSpiServiceLocator();

			DefaultLogger.debug(this, "setting IFSC endpoint");
			service.setFinancialInstitutionInquiryApplicationServiceSpiPortEndpointAddress(ifscCodeEndPoint);

			DefaultLogger.debug(this, "FinancialInstitutionApplicationServiceSpi_Service() called..");
			FinancialInstitutionInquiryApplicationServiceSpi financialInstitutionApplicationServiceSpiPort = service
					.getFinancialInstitutionInquiryApplicationServiceSpiPort();
			DefaultLogger.debug(this,
					"service connected through port.." + financialInstitutionApplicationServiceSpiPort);

			Calendar c = Calendar.getInstance();
			String externalReferenceNo = "CLIMSIFSC" + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.MONTH)
					+ c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND)
					+ c.get(Calendar.MILLISECOND);
			DefaultLogger.debug(this, "externalReferenceNo:" + externalReferenceNo);

			SessionContext sessionContext = new SessionContext();
			sessionContext.setBankCode(bankCode);
			sessionContext.setChannel(channel);
			sessionContext.setExternalReferenceNo(externalReferenceNo);
			sessionContext.setTransactingPartyCode(transactingPartyCode);
			sessionContext.setTransactionBranch(transactionBranch);
			sessionContext.setUserId(userId);
			DefaultLogger.debug(this, "sessionContext:" + sessionContext);

			if ((null != ifscCodeValue && !ifscCodeValue.isEmpty())
					||(null != bankNameValue && !bankNameValue.isEmpty())
					||(null != bankBranchNameValue && !bankBranchNameValue.isEmpty())) {
				
				FinancialInstitutionInquiryDTO e = new FinancialInstitutionInquiryDTO();
				e.setBankName(bankNameValue);
				e.setBranchName(bankBranchNameValue);
				e.setIfscCode(ifscCodeValue);

				WorkItemViewObjectDTO workItemViewObjectDTO = new WorkItemViewObjectDTO();
				WorkItemViewObjectDTO[] arg2 = new WorkItemViewObjectDTO[1];
				arg2[0] = workItemViewObjectDTO;
				DefaultLogger.debug(this, "workItemViewObjectDTO:" + workItemViewObjectDTO + " arg2:" + arg2);

				fetchFinancialResponse = financialInstitutionApplicationServiceSpiPort.fetchAllFinancialInstitutions(sessionContext, e, arg2);
				DefaultLogger.info(this, "fetchFinancialResponse::::" + fetchFinancialResponse);
				
				FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub stub = (FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub) financialInstitutionApplicationServiceSpiPort;
				DefaultLogger.debug(this, "stub:" + stub);

				ifsccodeWsLog.setRequestDateTime(Calendar.getInstance().getTime());
				String request = stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
				ifsccodeWsLog.setRequestMessage(request);
				DefaultLogger.debug(this, "request:" + request);

				ifsccodeWsLog.setResponseDateTime(Calendar.getInstance().getTime());
				String response = stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
				ifsccodeWsLog.setResponseMessage(response);
				DefaultLogger.debug(this, "response:" + response);

				TransactionStatus status = fetchFinancialResponse.getStatus();
				DefaultLogger.debug(this, "TransactionStatus:" + status);

				if (null != status) {
					ifsccodeWsLog.setResponseCode(String.valueOf(status.getReplyCode()));
					ifsccodeWsLog.setErrorMessage(status.getReplyText());

					if (status.getReplyCode() == 0) {
						if (fetchFinancialResponse != null) {
							for (FinancialInstitutionInquiryRes details : fetchFinancialResponse
									.getFinancialInstitutionApplicationResponse()) {
								OBOtherBank oBOtherBank = new OBOtherBank();
								oBOtherBank.setiFSCCode(details.getIfscCode());
								oBOtherBank.setOtherBankName(details.getBankName());
								oBOtherBank.setBranchName(details.getBranchName());
								oBOtherBank.setBranchNameAddress(details.getBranchAddress());

								OtherBankList.add(oBOtherBank);

								DefaultLogger.debug(this, "details.getBankName() : " + details.getBankName());
								DefaultLogger.debug(this, "details.getBranchName(): " + details.getBranchName());
								DefaultLogger.debug(this, "Branch Address : " + details.getBranchAddress());
								resultMap.put("ifscWsResponce", "Bank details fetched successfully.");
							}
						}
					} else {
						DefaultLogger.debug(this, "status.getReplyCode():" + status.getReplyCode());
						DefaultLogger.debug(this, "status.getReplyText():" + status.getReplyText());
						ifsccodeWsLog.setErrorMessage(
								"Reply Code:" + status.getReplyCode() + " Reply Text:" + status.getReplyText());
						resultMap.put("ifscWsResponce",
								"Reply Code:" + status.getReplyCode() + " Reply Text:" + status.getReplyText());
					}
				} else {
					ifsccodeWsLog.setErrorMessage("TransactionStatus is null");
					resultMap.put("ifscWsResponce", "TransactionStatus is null");
				}
			}

		} catch (Exception e) {
			DefaultLogger.debug(this, "exception:" + e.getMessage());
			e.printStackTrace();
			if (null != ifsccodeWsLog)
				ifsccodeWsLog.setErrorMessage(e.getMessage());
			resultMap.put("ifscWsResponce","java.net.UnknownHostException: hbentbpuatap.hdfcbank.com");
		}

		if (null != ifsccodeWsLog) {
			IIfsccodeWsLogDao ifsccodeWsLogDao = (IIfsccodeWsLogDao) BeanHouse.get("ifsccodeWsLogDao");
			IIfsccodeWsLog createIfsccodeWsLog = ifsccodeWsLogDao.createIfsccodeWsLog(ifsccodeWsLog);
			DefaultLogger.debug(this, "createIfsccodeWsLog id:" + createIfsccodeWsLog.getId());
		}

		resultMap.put("event", event);
		resultMap.put("ifscCodeValue", ifscCodeValue);
		resultMap.put("bankNameValue", bankNameValue);
		resultMap.put("bankBranchNameValue", bankBranchNameValue);
		resultMap.put("OtherBankList", OtherBankList);
		DefaultLogger.debug(this, "Interface Log BO created....");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	protected String getFieldValue(String name) {
		if (resourceBundle.containsKey(name)) {
			return resourceBundle.getString(name);
		} else {
			return null;
		}
	}

}
