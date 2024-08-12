package com.integrosys.cms.ui.manualinput.customer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;
import java.util.ResourceBundle;
import com.integrosys.cms.app.lei.json.command.PrepareSendReceiveCCILCommand; 
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.lei.json.dto.LEIDetailsResponseDto;
import com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;
import com.integrosys.cms.app.lei.json.dao.OBLEIValidationLog;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class ValidateLEICodeWithCCILCommand extends AbstractCommand{

	protected static ResourceBundle resourceBundle;
	
	static{
		resourceBundle = ResourceBundle.getBundle("ofa",Locale.getDefault());
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "leiCode", "java.lang.String", REQUEST_SCOPE },
				{ "customerCifId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "customerId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
			});
	}


	public String[][] getResultDescriptor() {

		return (new String[][] {	
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE },
				{ "leiExpDate", "java.lang.String", REQUEST_SCOPE },
				{ "leiValLogOB", "com.integrosys.cms.app.lei.json.dto.OBLEIValidationLog", REQUEST_SCOPE },
				{ "leiCCILResponse", "com.integrosys.cms.app.lei.json.dto.RetrieveLEICCILResponse", REQUEST_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "OBCMSCustomerNew","com.integrosys.cms.app.customer.bus.ICMSCustomer",SERVICE_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
			});
	}

	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		String leiCode = (String) map.get("leiCode");
		String customerCifId = (String) map.get("customerCifId");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MMM/yyyy");
		
		DefaultLogger.info(this, "Inside doExecute() ValidateLEICodeWithCCILCommand "+event);
		
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBTrxContext obTrxContext =(OBTrxContext)map.get("theOBTrxContext");
		DefaultLogger.debug(this,obTrxContext.getUser().getLoginID());
		String loginId = obTrxContext.getUser().getLoginID();
		RetrieveLEICCILResponse response = null;
		Date leiDate = null; 
		String leiExpDate = null;
		ILEIValidationLog leiValLogOB = new OBLEIValidationLog();
		PrepareSendReceiveCCILCommand leiWsCall = new PrepareSendReceiveCCILCommand();
		if(null != leiCode && !leiCode.isEmpty()){
			try{				
				response = leiWsCall.ccilWebServiceCall(loginId, leiCode, obCustomer, leiValLogOB, customerCifId);				
				if(response!=null) {
					ObjectMapper mapper = new ObjectMapper();	
					LEIDetailsResponseDto myLeiObject = null;			
					try {
						myLeiObject = mapper.readValue(response.getLeiDetails(), LEIDetailsResponseDto.class);
					} catch (IOException e) {
						DefaultLogger.info(this, e.getMessage(), e);
						throw new Exception("Error Parsing response ", e);
					}
					leiDate = dateFormat.parse(myLeiObject.getNextRenewalDate().substring(0,10));
					leiExpDate = targetFormat.format(leiDate); 
					String code = response.getErrorCode();
					if(PropertyManager.getValue("ccil.success.code").equals(code)) {
						if(obCustomer!=null) {
							obCustomer.setIsLeiValidated('Y');
						}else {
							obCustomer = new OBCMSCustomer();
							obCustomer.setIsLeiValidated('Y');
						}
					}
				}
			}catch(Exception e){
				System.err.println("Error encountered while communicating to CCIL Application:::"+e.getCause());
				DefaultLogger.error(this,"Error encountered while communicating to CCIL Application:::"+e.getCause());
				response= new RetrieveLEICCILResponse();
			}
		}
		
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("OBCMSCustomerNew", obCustomer);
		resultMap.put("event",event);	
		resultMap.put("leiValLogOB", leiValLogOB);
		resultMap.put("leiExpDate", leiExpDate);
		resultMap.put("leiCCILResponse", response);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}

}