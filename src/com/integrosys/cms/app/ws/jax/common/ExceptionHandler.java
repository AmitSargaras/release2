package com.integrosys.cms.app.ws.jax.common;

import java.util.ArrayList;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ThrowsAdvice;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.ValidationErrorDetailsDTO;

@Aspect
public class ExceptionHandler implements ThrowsAdvice {

	@AfterThrowing(pointcut = "@annotation(com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod)", throwing = "ex")
	public void handleException(Throwable ex) throws Throwable {
		DefaultLogger.debug(this,"Handle Exception : Throw SOAP Fault!");
		if (ex instanceof CMSValidationFault) {
			throw ex;
		}
		
		ErrorDetailDTO error = getServerErrorDetailDTO(ex);
		throw new CMSFault(WebServiceStatusCode.FAIL.name(), error);
	}
	
	public static ErrorDetailDTO getServerErrorDetailDTO(Throwable ex){
		ErrorDetailDTO error = new ErrorDetailDTO();
		error.setErrorCode(WebServiceStatusCode.SERVER_ERROR.name());
		error.setErrorDescription(ex.getMessage()!=null?ex.getMessage():WebServiceStatusCode.SERVER_ERROR.message);
		error.setValidationErrorDetails(new ArrayList<ValidationErrorDetailsDTO>());
		return error;
	}

}
