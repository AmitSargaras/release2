package com.integrosys.cms.app.ws.endpoint;

import java.util.Iterator;
import java.util.Locale;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.dto.CamDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.AADetailRequestDTO;
import com.integrosys.cms.app.ws.dto.AADetailResponseDTO;
import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.InterfaceHelper;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.integrosys.cms.ui.manualinput.aa.AADetailValidator;

@WebService(serviceName = "HelloWorldWs", portName = "HelloWorldPort")
@HandlerChain(file="handler-chain.xml")
public class HelloWorld{
	@WebMethod(operationName="getHelloWorld")
	public AADetailResponseDTO  getHelloWorld(@WebParam (name = "CAMRequest") AADetailRequestDTO  CAMRequest) {
		Locale defaultLocale = Locale.getDefault();
		CamDetailsDTOMapper dtoMapper = new CamDetailsDTOMapper();
		InterfaceHelper helper = new InterfaceHelper();
		AADetailForm form = dtoMapper.getFormFromDTO(CAMRequest);
		ActionErrors errorList = AADetailValidator.validateInput(form, defaultLocale); 
		AADetailResponseDTO CAMresponse = new AADetailResponseDTO();
		Iterator entries = errorList.get();
		String errorCode = "";
		//List list = new ArrayList();
		String errorValue = "";
		while (entries.hasNext()) {
			ActionMessage thisEntry = (ActionMessage) entries.next();
			Object key = thisEntry.getKey();
			Object[] value = thisEntry.getValues();
			errorCode = errorCode+" || "+key.toString();
			if(value != null){
				errorValue = errorValue+" || "+value[0].toString();
			}
		}
		if(errorList.size()>0)
		{
			//CAMresponse.setResponseStatus("FAILURE");
			ErrorDetailDTO error = new ErrorDetailDTO();
			error.setErrorCode(errorCode);
			error.setErrorDescription(errorValue);
//			CAMresponse.setErrorCode(errorCode);
//			CAMresponse.setErrorDetail(errorValue);
			//listCAMresponse.add(error);
			//CAMresponse.setErrorDetail(list);
			//helper.interfaceLoggingActivity(CAMRequest,CAMresponse,error);
			return CAMresponse;
		}
		else{
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		OBTrxContext trxContext = new OBTrxContext();
		ILimitProfileTrxValue limitProfileTrxVal = null;
		ICMSTrxResult trxResult = null;
		OBCMSTrxValue transaction = new OBCMSTrxValue();
		ILimitProfile newLimitProfile = dtoMapper.getActualFromDTO(CAMRequest,null);
		try {
			trxResult = proxy.makerCreateLimitProfile(trxContext, limitProfileTrxVal, newLimitProfile);
			if(trxResult!=null){
				transaction = (OBCMSTrxValue)trxResult.getTrxValue();
			}
			limitProfileTrxVal = proxy.getTrxLimitProfile(transaction.getTransactionID());
			trxResult = proxy.checkerApproveUpdateLimitProfile(trxContext, limitProfileTrxVal);
			if(trxResult != null && trxResult.getTrxValue() != null){
				//CAMresponse.setTransactionID(trxResult.getTrxValue().getTransactionID());
				//CAMresponse.setResponseStatus("CAM_CREATED_SUCCESSFULLY");
			}
		} catch (LimitException e1) {
			DefaultLogger.error(this, "############# error in HelloWorld ######## ", e1);
			e1.printStackTrace();
			//CAMresponse.setResponseStatus("CAM_CREATION_FAILURE");
			ErrorDetailDTO error = new ErrorDetailDTO();
			error.setErrorCode(e1.getErrorCode());
			error.setErrorDescription(e1.getMessage());
//			CAMresponse.setErrorCode(e1.getErrorCode());
//			CAMresponse.setErrorDetail(e1.getMessage());
			//list.add(error);
			//CAMresponse.setErrorDetail(list);
		//	helper.interfaceLoggingActivity(CAMRequest,CAMresponse,error);
			return CAMresponse;
			
		}
	//	helper.interfaceLoggingActivity(CAMRequest,CAMresponse,new ErrorDetailDTO());
		return CAMresponse;
		}
	}

	
} 





