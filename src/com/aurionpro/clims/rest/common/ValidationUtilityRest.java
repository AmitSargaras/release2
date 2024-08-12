package com.aurionpro.clims.rest.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.jax.common.Messages;
import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;

public class ValidationUtilityRest {

	public static List<ValidationErrorDetailsDTO> handleError(HashMap map, CLIMSWebService serviceName) {
		Messages messages = Messages.getInstance(serviceName);
		List<ValidationErrorDetailsDTO> validationErrorDetailsList = new ArrayList<ValidationErrorDetailsDTO>();
		ActionErrors errorList = new ActionErrors();
		Set mapSet = (Set) map.entrySet();
		Iterator mapIterator = mapSet.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) mapIterator.next();
			errorList = (ActionErrors) mapEntry.getValue();
			Iterator<String> fieldArray = errorList.properties();
			while (fieldArray.hasNext()) {
				ValidationErrorDetailsDTO validationErrorDetailsDTO = new ValidationErrorDetailsDTO();
				String validationKey = fieldArray.next();

				ActionMessage thisEntry = (ActionMessage) errorList.get(validationKey).next();
				String messageKey = thisEntry.getKey();
				Object[] value = thisEntry.getValues();
				if (serviceName.equals(CLIMSWebService.PARTY)) {
					validationKey = validationKey.replace("Error", "");
					if ("customerNameShort".equalsIgnoreCase(validationKey)) {
						validationKey = "partyName";
					}
					if ("invalidPan".equalsIgnoreCase(validationKey)) {
						validationKey = "PAN";
					}

					if (messageKey.toUpperCase().contains("Mandatory".toUpperCase())
							|| messageKey.toUpperCase().contains("Empty".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("PTY0001");
						validationErrorDetailsDTO.setField(validationKey + " is mandatory");
					} else if (messageKey.toUpperCase().contains("invalid".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("PTY0002");
						validationErrorDetailsDTO.setField(validationKey + " is invalid");
					} else {
						validationErrorDetailsDTO.setErrorCode("PTY0002");
						validationErrorDetailsDTO.setField(validationKey + "  " + messages.getString(messageKey, value));
					}
				} else if (serviceName.equals(CLIMSWebService.CAM)) {
					if (messageKey.toUpperCase().contains("Mandatory".toUpperCase())
							|| messageKey.toUpperCase().contains("Empty".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("CAM0001");
						validationErrorDetailsDTO.setField(validationKey + " is mandatory");
					} else if (messageKey.toUpperCase().contains("invalid".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("CAM0002");
						validationErrorDetailsDTO.setField(validationKey + " is invalid");
					} else {
						validationErrorDetailsDTO.setErrorCode("CAM0002");
						validationErrorDetailsDTO.setField(validationKey + "  " + messages.getString(messageKey, value));
					}
				} else if (serviceName.equals(CLIMSWebService.FACILITY)) {
					if (messageKey.toUpperCase().contains("Mandatory".toUpperCase())
							|| messageKey.toUpperCase().contains("Empty".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("FAC0001");
						validationErrorDetailsDTO.setField(validationKey + " is mandatory");
					} else if (messageKey.toUpperCase().contains("invalid".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("FAC0002");
						validationErrorDetailsDTO.setField(validationKey + " is invalid");
					} else {
						validationErrorDetailsDTO.setErrorCode("FAC0002");
						validationErrorDetailsDTO.setField(validationKey + "  " + messages.getString(messageKey, value));
					}
				} else if (serviceName.equals(CLIMSWebService.UPDATE_SECURITY)) {
					if (messageKey.toUpperCase().contains("Mandatory".toUpperCase())
							|| messageKey.toUpperCase().contains("Empty".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("SEC001");
						validationErrorDetailsDTO.setField(validationKey + " is mandatory");
					} else if (messageKey.toUpperCase().contains("invalid".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("SEC002");
						validationErrorDetailsDTO.setField(validationKey + " is invalid");
					} else {
						validationErrorDetailsDTO.setErrorCode("SEC002");
						validationErrorDetailsDTO.setField(validationKey + "  " + messages.getString(messageKey, value));
					}
				} else if (serviceName.equals(CLIMSWebService.UDF)) {
					if (messageKey.toUpperCase().contains("Mandatory".toUpperCase())
							|| messageKey.toUpperCase().contains("Empty".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("UDF0001");
						validationErrorDetailsDTO.setField(validationKey + " is mandatory");
					} else if (messageKey.toUpperCase().contains("invalid".toUpperCase())) {
						validationErrorDetailsDTO.setErrorCode("UDF0002");
						validationErrorDetailsDTO.setField(validationKey + " is invalid");
					} else {
						validationErrorDetailsDTO.setErrorCode("UDF0002");
						validationErrorDetailsDTO.setField(validationKey + "  " + messages.getString(messageKey, value));
					}
				}

				validationErrorDetailsList.add(validationErrorDetailsDTO);
			}
		}
		return validationErrorDetailsList;
	}

	public boolean isDuplicateRefNumber(String logsTable, String refNum, String channelCode) throws SQLException {
		boolean valid = false;
		System.out.println("In isDuplicateRefNumber--> refNum======" + refNum);
		String sql = "select count(*) as count from " + logsTable + " WHERE  REQUEST_ID = '" + refNum
				+ "' and CHANNEL_CODE = '" + channelCode + "' ";

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				String count = rs.getString("count");
				if (count.equals("0")) {
					valid = true;
				}
			}
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
		} finally {
			rs.close();
			dbUtil.close();
		}
		return valid;
	}
}