/**
 * 
 */
package com.integrosys.cms.app.ws.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.ValidationErrorDetailsDTO;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.Messages;
import com.integrosys.cms.app.ws.jax.common.Messages.CLIMSResourceBundle;

/**
 * @author bhushan.malankar
 *
 */
public class ValidationUtility {

	public static  void handleError(HashMap map, CLIMSWebService serviceName) throws CMSValidationFault {
		Messages fieldResourceBundal = Messages.getInstance(CLIMSResourceBundle.WEBSERVICE_RESOURCE_BUNDLE);
		Messages messages = Messages.getInstance(serviceName);
		List<ValidationErrorDetailsDTO> validationErrorDetailsList = new ArrayList<ValidationErrorDetailsDTO>();
		ActionErrors errorList = new ActionErrors();
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) 
		{
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			String keyValue = (String) mapEntry.getKey();
			errorList = (ActionErrors) mapEntry.getValue();

			Iterator<String> fieldArray = errorList.properties();
			while (fieldArray.hasNext()) {
				ValidationErrorDetailsDTO validationErrorDetailsDTO = new ValidationErrorDetailsDTO();
				String validationKey = fieldArray.next();
				ActionMessage thisEntry = (ActionMessage) errorList.get(validationKey).next();

				Object messageKey = thisEntry.getKey();
				Object[] value = thisEntry.getValues();
				if(serviceName.equals(CLIMSWebService.SECURITY)){
					validationErrorDetailsDTO.setField(fieldResourceBundal.getString(serviceName.fieldPrefix() + validationKey) + " of security: "+keyValue);
				}else if(serviceName.equals(CLIMSWebService.DOCUMENTS)){
					if(validationKey!=null){
						if(validationKey.contains("::")){
							String validKey = validationKey.split("::")[0];
							validationErrorDetailsDTO.setField(fieldResourceBundal.getString(serviceName.fieldPrefix() + validKey) + "");
						}else{
							validationErrorDetailsDTO.setField(fieldResourceBundal.getString(serviceName.fieldPrefix() + validationKey) + "");
						}
					}
				}
				else{
					validationErrorDetailsDTO.setField(fieldResourceBundal.getString(serviceName.fieldPrefix() + validationKey) + "");
				}
				if (value != null) {
					validationErrorDetailsDTO.setValidationMessage(messages.getString(messageKey.toString(), value));
				}
				else{
					validationErrorDetailsDTO.setValidationMessage(messages.getString(messageKey.toString(), ""));
				}
				validationErrorDetailsDTO.setReferenceId("");
				
				validationErrorDetailsList.add(validationErrorDetailsDTO);
			}


		}

		if (validationErrorDetailsList.size()>0) {

			ErrorDetailDTO error = new ErrorDetailDTO();
			error.setErrorCode(WebServiceStatusCode.VALIDATION_ERROR.name());
			error.setErrorDescription(WebServiceStatusCode.VALIDATION_ERROR.message);


			error.setValidationErrorDetails(validationErrorDetailsList);
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), error);

		}
	}
	
	public static String handleErrorList(HashMap map, CLIMSWebService serviceName) throws CMSValidationFault {
		StringBuilder str = new StringBuilder();
		String errorMessage="",key ="",message = "";
		ActionErrors errorList = new ActionErrors();
		Messages messages = Messages.getInstance(serviceName);
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) 
		{

			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			String keyValue = (String) mapEntry.getKey();
			errorList = (ActionErrors) mapEntry.getValue();

			Iterator<String> fieldArray = errorList.properties();
			while (fieldArray.hasNext()) {
				ValidationErrorDetailsDTO validationErrorDetailsDTO = new ValidationErrorDetailsDTO();
				String validationKey = fieldArray.next();
				ActionMessage thisEntry = (ActionMessage) errorList.get(validationKey).next();

				Object messageKey = thisEntry.getKey();
				Object[] value = thisEntry.getValues();
				
				if (value != null) {
					message=(messages.getString(messageKey.toString(), value));
				}
				else{
					message=(messages.getString(messageKey.toString(), ""));
				}
				str.append(validationKey + " - " + message + ",");
			}
		}
		return str.toString();
	}
}
