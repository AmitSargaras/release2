/**
 * 
 */
package com.aurionpro.clims.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aurionpro.clims.rest.service.UdfEnquiryRestService;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.constants.WholeSaleApi_Constant;
import com.aurionpro.clims.rest.dto.*;
import com.aurionpro.clims.rest.helper.IAccessChannelForRestAPI;
import com.aurionpro.clims.rest.interfaceLog.IRestCamInterfaceLogDAO;
import com.aurionpro.clims.rest.interfaceLog.IRestFacilityInterfaceLogDAO;
import com.aurionpro.clims.rest.interfaceLog.IUdfEnqRestInterfaceLogDAO;
import com.aurionpro.clims.rest.interfaceLog.IRestInterfaceLogDAO;
import com.aurionpro.clims.rest.interfaceLog.IRestPartyInterfaceLogDAO;
import com.aurionpro.clims.rest.interfaceLog.OBRestCamInterfaceLog;
import com.aurionpro.clims.rest.interfaceLog.OBRestFacilityInterfaceLog;
import com.aurionpro.clims.rest.interfaceLog.OBRestInterfaceLog;
import com.aurionpro.clims.rest.interfaceLog.OBUdfEnqRestInterfaceLog;
import com.aurionpro.clims.rest.interfaceLog.OBRestPartyInterfaceLog;
import com.aurionpro.clims.rest.service.CamDetailsRestService;
import com.aurionpro.clims.rest.service.CommonCodeRestService;
import com.aurionpro.clims.rest.service.FacilityDetailsRestService;
import com.aurionpro.clims.rest.service.PartyDetailsRestService;
import com.aurionpro.clims.rest.service.SecurityDetailsRestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 * @author anil.pandey
 *
 */
public class RestApiRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gson _gson = null;
	private IAccessChannelForRestAPI accessChannelForRestAPI;

	public RestApiRequestServlet() {
		super();
		_gson = new Gson();
		// TODO Auto-generated constructor stub
	}

	private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("application/json");

		String res = _gson.toJson(obj);
		res = res.replace("\\u003d\\u003e", "=>");
		PrintWriter out = response.getWriter();

		out.print(res);
		out.flush();
	}

	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		String jsonRequest = "";
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();

		PartyDetailsRestService partyDetailsRestService = new PartyDetailsRestService();
		ResourceBundle bundle = ResourceBundle.getBundle("rest_api");
				
		String securityOnOffFlag = bundle.getString("Security_Update_on_off");
		String securityDeleteOnOffFlag = bundle.getString("Security_Delete_on_off");
		SecurityDetailsRestService securityDetailsRestService;
		securityDetailsRestService = (SecurityDetailsRestService) BeanHouse.get("securityDetailsRestService");
		List bodyRestList = new LinkedList();
		BodyRestResponseDTO bodyObj = new BodyRestResponseDTO();
		HeaderDetailRestResponseDTO headerObj = new HeaderDetailRestResponseDTO();
		List<HeaderDetailRestResponseDTO> commonHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();
		CommonRestResponseDTO commonRestResponseDTO = new CommonRestResponseDTO();
		ValidationUtilityRest utilObj = new ValidationUtilityRest();
		String refNum = "";
		if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_COMMON_CODE)) {

			HeaderDetailRestResponseDTO commonHeaderResponseDTO = new HeaderDetailRestResponseDTO();

			CommonCodeRestService commonCodeRestService;
			commonCodeRestService = (CommonCodeRestService) BeanHouse.get("commonCodeRestService");
			BufferedReader reader = request.getReader();

			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			CommonCodeRestRequestDTO commonCodeRestRequestDTO = null;
			Date date = new Date();
			OBRestInterfaceLog restinterfacelog = new OBRestInterfaceLog();

			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				commonCodeRestRequestDTO = gson.fromJson(reader, CommonCodeRestRequestDTO.class);

				jsonRequest = gson.toJson(commonCodeRestRequestDTO);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String commonChannel = commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
				String commonPass = commonCodeRestRequestDTO.getHeaderDetails().get(0).getPassCode();
				String commonReqId = commonCodeRestRequestDTO.getHeaderDetails().get(0).getRequestId();
				String logsTable = "CC_REST_INTERFACE_LOG";
				boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, commonReqId, commonChannel);
				System.out.println("Req num validRefNo======" + validRefNo);
				if (validRefNo) {
					bodyDetailsList = validateHeaderFields(commonChannel, commonPass, commonReqId);
					if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
						headerObj.setRequestId(commonReqId);
						headerObj.setChannelCode(commonChannel);
						commonHeaderResponse.add(headerObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyDetailsList);
					} else {

						if (accessChannelForRestAPI.accessToChannel(
								commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),
								"api.rest.access.common.code.view")) {
							commonRestResponseDTO = commonCodeRestService
									.getCommonCodeDetails(commonCodeRestRequestDTO, gson);
						} else {

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
							restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
						}
					}
				} else {

					headerObj.setRequestId(commonCodeRestRequestDTO.getHeaderDetails().get(0).getRequestId());
					headerObj.setChannelCode(commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
					commonHeaderResponse.add(headerObj);

					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);

					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyRestList);
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				commonHeaderResponseDTO
						.setRequestId(commonCodeRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				commonHeaderResponseDTO
						.setChannelCode(commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(commonCodeRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(commonCodeRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(commonCodeRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestInterfaceLogDAO restInterfaceLogDAO = (IRestInterfaceLogDAO) BeanHouse.get("restInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertCommonCodeLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for CommonCode Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		} else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.CREATE_PARTY)) {

			BufferedReader reader = request.getReader();

			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			PartyDetailsRestRequestDTO partyDetailsRequest = new PartyDetailsRestRequestDTO();
			Date date = new Date();
			OBRestPartyInterfaceLog restinterfacelog = new OBRestPartyInterfaceLog();

			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");

				partyDetailsRequest = gson.fromJson(reader, PartyDetailsRestRequestDTO.class);

				jsonRequest = gson.toJson(partyDetailsRequest);
				restinterfacelog.setRequestText(jsonRequest.toString());
				List<BodyRestResponseDTO> bodyDetailsList;
				String channelCode = partyDetailsRequest.getHeaderDetails().get(0).getChannelCode();
				String passsCode = partyDetailsRequest.getHeaderDetails().get(0).getPassCode();
				String requestId = partyDetailsRequest.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(requestId);
					headerObj.setChannelCode(channelCode);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {
					if (accessChannelForRestAPI.accessToChannel(
							partyDetailsRequest.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.party.create")) {
						refNum = partyDetailsRequest.getHeaderDetails().get(0).getRequestId();
						String chnl = partyDetailsRequest.getHeaderDetails().get(0).getChannelCode();
						String logsTable = "PARTY_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = partyDetailsRestService
									.createPartyDetailsRest(partyDetailsRequest, gson);
						} else {

							headerObj.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}
					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);
						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);
						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason(ResponseConstants.ACCESS_DENIED_MESSAGE);
					}
				}
			} catch (JsonSyntaxException e1) {
				System.out.println("e1 Exception createPartyDetailsRest()" + e1.getMessage());

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e2) {
				System.out.println("e2 BException createPartyDetailsRest()" + e2.getMessage());

				headerObj.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e2.printStackTrace();
			} finally {
				jsonRequest = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + jsonRequest);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestPartyInterfaceLogDAO restInterfaceLogDAO = (IRestPartyInterfaceLogDAO) BeanHouse
						.get("restPartyInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertPartyDetailsInterfaceLog(restinterfacelog);

				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for CommonCode Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		} else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_PARTY)) {
			BufferedReader reader = request.getReader();

			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			PartyDetailsRestRequestDTO partyDetailsRequest = new PartyDetailsRestRequestDTO();
			Date date = new Date();
			OBRestPartyInterfaceLog restinterfacelog = new OBRestPartyInterfaceLog();

			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");

				partyDetailsRequest = gson.fromJson(reader, PartyDetailsRestRequestDTO.class);

				jsonRequest = gson.toJson(partyDetailsRequest);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String channelCode = partyDetailsRequest.getHeaderDetails().get(0).getChannelCode();
				String passsCode = partyDetailsRequest.getHeaderDetails().get(0).getPassCode();
				String requestId = partyDetailsRequest.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(requestId);
					headerObj.setChannelCode(channelCode);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {
					if (accessChannelForRestAPI.accessToChannel(
							partyDetailsRequest.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.party.update")) {
						refNum = partyDetailsRequest.getHeaderDetails().get(0).getRequestId();
						String chnl = partyDetailsRequest.getHeaderDetails().get(0).getChannelCode();
						String logsTable = "PARTY_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = partyDetailsRestService
									.updatePartyDetailsRest(partyDetailsRequest, gson);

						} else {

							headerObj.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}
					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason(ResponseConstants.ACCESS_DENIED_MESSAGE);
					}
				}
			} catch (JsonSyntaxException e1) {
				System.out.println("e1 Exception updatePartyDetailsRest()" + e1.getMessage());

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e2) {
				System.out.println("e2 BException updatePartyDetailsRest()" + e2.getMessage());

				headerObj.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e2.printStackTrace();
			} finally {
				jsonRequest = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + jsonRequest);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(partyDetailsRequest.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(partyDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();

				restinterfacelog.setResponseDate(date1);

				IRestPartyInterfaceLogDAO restInterfaceLogDAO = (IRestPartyInterfaceLogDAO) BeanHouse
						.get("restPartyInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertPartyDetailsInterfaceLog(restinterfacelog);

				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for CommonCode Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		}

		else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.CREATE_CAM)) {

			HeaderDetailRestResponseDTO camHeaderResponseDTO = new HeaderDetailRestResponseDTO();

			CamDetailsRestService camDetailsRestService;
			camDetailsRestService = (CamDetailsRestService) BeanHouse.get("camDetailsRestService");
			BufferedReader reader = request.getReader();
			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			CamBodyRestRequestDTO camBodyRestRequestDTO = null;
			Date date = new Date();
			OBRestCamInterfaceLog restinterfacelog = new OBRestCamInterfaceLog();

			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				camBodyRestRequestDTO = gson.fromJson(reader, CamBodyRestRequestDTO.class);

				jsonRequest = gson.toJson(camBodyRestRequestDTO);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String channelCode = camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
				String passsCode = camBodyRestRequestDTO.getHeaderDetails().get(0).getPassCode();
				String requestId = camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(requestId);
					headerObj.setChannelCode(channelCode);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {

					if (accessChannelForRestAPI.accessToChannel(
							camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.cam.create")) {
						refNum = camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId();
						String chnl = camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
						String logsTable = "CAM_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = camDetailsRestService.addCamDetailsRest(camBodyRestRequestDTO);
						} else {

							headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);
							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}

					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
					}
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				camHeaderResponseDTO.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				camHeaderResponseDTO.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestCamInterfaceLogDAO restInterfaceLogDAO = (IRestCamInterfaceLogDAO) BeanHouse
						.get("restCamInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertCamDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println("Exception while entering Interface entry for CAM Service " + e.getMessage());
					e.printStackTrace();
				}
			}

		} else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_CAM)) {

			HeaderDetailRestResponseDTO camHeaderResponseDTO = new HeaderDetailRestResponseDTO();

			CamDetailsRestService camDetailsRestService;
			camDetailsRestService = (CamDetailsRestService) BeanHouse.get("camDetailsRestService");
			BufferedReader reader = request.getReader();

			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			CamBodyRestRequestDTO camBodyRestRequestDTO = null;
			Date date = new Date();
			OBRestCamInterfaceLog restinterfacelog = new OBRestCamInterfaceLog();
			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				camBodyRestRequestDTO = gson.fromJson(reader, CamBodyRestRequestDTO.class);

				jsonRequest = gson.toJson(camBodyRestRequestDTO);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String channelCode = camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
				String passsCode = camBodyRestRequestDTO.getHeaderDetails().get(0).getPassCode();
				String requestId = camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(requestId);
					headerObj.setChannelCode(channelCode);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {

					if (accessChannelForRestAPI.accessToChannel(
							camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.cam.update")) {
						refNum = camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId();
						String chnl = camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
						String logsTable = "CAM_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = camDetailsRestService
									.updateCamDetailsRest(camBodyRestRequestDTO);
						} else {

							headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);
							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}

					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
					}
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				camHeaderResponseDTO.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				camHeaderResponseDTO.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(camBodyRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(camBodyRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestCamInterfaceLogDAO restInterfaceLogDAO = (IRestCamInterfaceLogDAO) BeanHouse
						.get("restCamInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertCamDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println("Exception while entering Interface entry for CAM Service " + e.getMessage());
					e.printStackTrace();
				}
			}

		} else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.CREATE_FACILITY)) {
			System.out.println("CREATE_FACILITY path======" + pathInfo);
			System.out.println("CREATE_FACILITY path======" + pathInfo);
			HeaderDetailRestResponseDTO commonHeaderResponseDTO = new HeaderDetailRestResponseDTO();
			FacilityDetailRestResponseDTO facilityDetailsResponse = null;
			FacilityDetailsRestService FacilityDetailsRestSrervice = null;
			facilityDetailsResponse = new FacilityDetailRestResponseDTO();
			FacilityDetailsRestSrervice = new FacilityDetailsRestService();
			BufferedReader reader = request.getReader();
			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			FacilityDetlRestRequestDTO FacilityDetailsRequest = null;

			Date date = new Date();
			OBRestFacilityInterfaceLog restinterfacelog = new OBRestFacilityInterfaceLog();

			restinterfacelog.setRequestDate(date);
			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				FacilityDetailsRequest = gson.fromJson(reader, FacilityDetlRestRequestDTO.class);

				jsonRequest = gson.toJson(FacilityDetailsRequest);
				restinterfacelog.setRequestText(jsonRequest.toString());
				List<BodyRestResponseDTO> bodyDetailsList;
				String commonChannel = FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode();
				String commonPass = FacilityDetailsRequest.getHeaderDetails().get(0).getPassCode();
				String commonReqId = FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(commonChannel, commonPass, commonReqId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(commonReqId);
					headerObj.setChannelCode(commonChannel);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {
					if (accessChannelForRestAPI.accessToChannel(
							FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.fac.create")) {

						refNum = FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId();
						String chnl = FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode(); 
						String logsTable = "FACILITY_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = FacilityDetailsRestSrervice
									.addFacilityDetailsRest(FacilityDetailsRequest, gson);
						} else {

							headerObj.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}
					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
					}
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE + "\n"+e.getMessage() );
				responseMessageList.add(responseMessageDetailDTO);

				commonHeaderResponseDTO
						.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				commonHeaderResponseDTO
						.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestFacilityInterfaceLogDAO restInterfaceLogDAO = (IRestFacilityInterfaceLogDAO) BeanHouse
						.get("restFacilityInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertFacilityDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for Facility Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		} else if (null != pathInfo && pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_FACILITY)) {

			System.out.println("UPDATE_FACILITY path======" + pathInfo);

			HeaderDetailRestResponseDTO commonHeaderResponseDTO = new HeaderDetailRestResponseDTO();
			FacilityDetailRestResponseDTO facilityDetailsResponse = null;
			FacilityDetailsRestService FacilityDetailsRestSrervice = null;
			facilityDetailsResponse = new FacilityDetailRestResponseDTO();
			FacilityDetailsRestSrervice = new FacilityDetailsRestService();
			BufferedReader reader = request.getReader();
			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			FacilityDetlRestRequestDTO FacilityDetailsRequest = null;

			Date date = new Date();
			OBRestFacilityInterfaceLog restinterfacelog = new OBRestFacilityInterfaceLog();

			restinterfacelog.setRequestDate(date);
			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				FacilityDetailsRequest = gson.fromJson(reader, FacilityDetlRestRequestDTO.class);

				jsonRequest = gson.toJson(FacilityDetailsRequest);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String commonChannel = FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode();
				String commonPass = FacilityDetailsRequest.getHeaderDetails().get(0).getPassCode();
				String commonReqId = FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(commonChannel, commonPass, commonReqId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(commonReqId);
					headerObj.setChannelCode(commonChannel);
					commonHeaderResponse.add(headerObj);
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyDetailsList);

				} else {

					if (accessChannelForRestAPI.accessToChannel(
							FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.fac.update")) {

						refNum = FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId();
						String chnl = FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode(); 
						String logsTable = "FACILITY_REST_INTERFACE_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							commonRestResponseDTO = FacilityDetailsRestSrervice
									.updateFacilityDetailsRest(FacilityDetailsRequest, gson);
						} else {

							headerObj.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
							commonRestResponseDTO.setBodyDetails(bodyRestList);
						}

					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
					}
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE + "\n"+e.getMessage());
				responseMessageList.add(responseMessageDetailDTO);

				commonHeaderResponseDTO
						.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				commonHeaderResponseDTO
						.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(FacilityDetailsRequest.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(FacilityDetailsRequest.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IRestFacilityInterfaceLogDAO restInterfaceLogDAO = (IRestFacilityInterfaceLogDAO) BeanHouse
						.get("restFacilityInterfaceLogDAO");
				try {
					restInterfaceLogDAO.insertFacilityDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for CommonCode Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		}
		else if (null != pathInfo && "on".equals(securityOnOffFlag) 
				&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_AB_SPECIFIC_ASSET_SECURITY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_PROPERTY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_CORPORATE)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_INDIVIDUAL)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_MARKETABLE_SECURITY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.UPDATE_GUARANTEE_GOVERNMENT))) {

			   BufferedReader reader = request.getReader();
			
				Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
				
				Date date = new Date();
				OBRestFacilityInterfaceLog restinterfacelog = new OBRestFacilityInterfaceLog();

				restinterfacelog.setRequestDate(date);
				
				CollateralRestRequestDTO collateralRestRequest = new CollateralRestRequestDTO();
				
				CollateralRestResponseDTO collateralRestResponse = new CollateralRestResponseDTO();
				
			   try {
				   
					accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");

					collateralRestRequest = gson.fromJson(reader,CollateralRestRequestDTO.class);

					jsonRequest = gson.toJson(collateralRestRequest);
					restinterfacelog.setRequestText(jsonRequest.toString());

					List<BodyRestResponseDTO> bodyDetailsList;
					String channelCode = collateralRestRequest.getHeaderDetails().get(0).getChannelCode();
					String passsCode = collateralRestRequest.getHeaderDetails().get(0).getPassCode();
					String requestId = collateralRestRequest.getHeaderDetails().get(0).getRequestId();
					bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

					if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
						headerObj.setRequestId(requestId);
						headerObj.setChannelCode(channelCode);
						commonHeaderResponse.add(headerObj);
						collateralRestResponse.setHeaderDetails(commonHeaderResponse);
						collateralRestResponse.setBodyDetails(bodyDetailsList);

					} else {
						if (accessChannelForRestAPI.accessToChannel(
								collateralRestRequest.getHeaderDetails().get(0).getChannelCode(),
								"api.rest.access.sec.update")) {
							refNum = collateralRestRequest.getHeaderDetails().get(0).getRequestId();
							String chnl = collateralRestRequest.getHeaderDetails().get(0).getChannelCode();
							String logsTable = "SECURITY_REST_INTERFACE_LOG";
							boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
							System.out.println("refNum validRefNo======" + validRefNo);
							if (validRefNo) {
								collateralRestResponse = securityDetailsRestService.updateSecurityDetails(collateralRestRequest,pathInfo);
							} else {

								headerObj.setRequestId(collateralRestRequest.getHeaderDetails().get(0).getRequestId());
								headerObj.setChannelCode(collateralRestRequest.getHeaderDetails().get(0).getChannelCode());
								commonHeaderResponse.add(headerObj);

								ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
								responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
								responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
								responseMessageList.add(responseMessageDetailDTO);

								bodyObj.setResponseStatus("FAILED");
								bodyObj.setResponseMessageList(responseMessageList);

								bodyRestList.add(bodyObj);

								restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
								collateralRestResponse.setHeaderDetails(commonHeaderResponse);
								collateralRestResponse.setBodyDetails(bodyRestList);
							}
						} else {

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);
							collateralRestResponse.setHeaderDetails(commonHeaderResponse);
							collateralRestResponse.setBodyDetails(bodyRestList);
							restinterfacelog.setInterfaceFailedReason(ResponseConstants.ACCESS_DENIED_MESSAGE);
						}
					}
				} catch (JsonSyntaxException e1) {
					System.out.println("e1 Exception updateSecurityDetailsRest()" + e1.getMessage());

					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);
					collateralRestResponse.setHeaderDetails(commonHeaderResponse);
					collateralRestResponse.setBodyDetails(bodyRestList);
					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					e1.printStackTrace();
				} catch (Exception e2) {
					System.out.println("e2 BException updateSecurityDetailsRest()" + e2.getMessage());

					headerObj.setRequestId(collateralRestRequest.getHeaderDetails().get(0).getRequestId());
					headerObj.setChannelCode(collateralRestRequest.getHeaderDetails().get(0).getChannelCode());
					commonHeaderResponse.add(headerObj);
					
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE+" Message"+e2.getLocalizedMessage());
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);

					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					collateralRestResponse.setHeaderDetails(commonHeaderResponse);
					collateralRestResponse.setBodyDetails(bodyRestList);
					e2.printStackTrace();
				} finally {
					String json = gson.toJson(collateralRestResponse);
					System.out.println("json ====>" + json);
					if (collateralRestResponse.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
						restinterfacelog.setInterfaceStatus("SUCCESS");
					if (collateralRestResponse.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
						restinterfacelog.setInterfaceStatus("FAILED");
					}
					sendAsJson(response, collateralRestResponse);

					restinterfacelog.setRequestId(collateralRestRequest.getHeaderDetails().get(0).getRequestId());
					restinterfacelog.setChannelCode(collateralRestRequest.getHeaderDetails().get(0).getChannelCode());
					restinterfacelog.setInterfaceFailedReason("");

					String jsonResponse = gson.toJson(collateralRestResponse);
					restinterfacelog.setResponseText(jsonResponse.toString());
					Date date1 = new Date();

					restinterfacelog.setResponseDate(date1);
					
					IRestFacilityInterfaceLogDAO restInterfaceLogDAO = (IRestFacilityInterfaceLogDAO) BeanHouse
							.get("restFacilityInterfaceLogDAO");

					try {
						restInterfaceLogDAO.insertSecurityDetailsInterfaceLog(restinterfacelog);
					} catch (Exception e) {
						System.out.println(
								"Exception while entering Interface entry for Facility Enquiry service" + e.getMessage());
						e.printStackTrace();
					}
				
				}
		}
		
		else if (null != pathInfo && "on".equals(securityDeleteOnOffFlag) 
				&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_AB_SPECIFIC_ASSET_SECURITY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_PROPERTY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_GUARANTEE_CORPORATE)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_GUARANTEE_INDIVIDUAL)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_MARKETABLE_SECURITY)
						||pathInfo.startsWith("/" + WholeSaleApi_Constant.DELETE_GUARANTEE_GOVERNMENT))) {

			   BufferedReader reader = request.getReader();
			
				Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
				
				Date date = new Date();
				OBRestFacilityInterfaceLog restinterfacelog = new OBRestFacilityInterfaceLog();

				restinterfacelog.setRequestDate(date);
				
				CollateralDeleteEnquiryRestRequestDTO ColDelRestRequestDTO = new CollateralDeleteEnquiryRestRequestDTO();
				
				CollateralRestResponseDTO collateralRestResponse = new CollateralRestResponseDTO();
				
			   try {
				   
					accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");

					ColDelRestRequestDTO = gson.fromJson(reader,CollateralDeleteEnquiryRestRequestDTO.class);

					jsonRequest = gson.toJson(ColDelRestRequestDTO);
					restinterfacelog.setRequestText(jsonRequest.toString());

					List<BodyRestResponseDTO> bodyDetailsList;
					String channelCode = ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
					String passsCode = ColDelRestRequestDTO.getHeaderDetails().get(0).getPassCode();
					String requestId = ColDelRestRequestDTO.getHeaderDetails().get(0).getRequestId();
					bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

					if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
						headerObj.setRequestId(requestId);
						headerObj.setChannelCode(channelCode);
						commonHeaderResponse.add(headerObj);
						collateralRestResponse.setHeaderDetails(commonHeaderResponse);
						collateralRestResponse.setBodyDetails(bodyDetailsList);

					} else {
						if (accessChannelForRestAPI.accessToChannel(
								ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),
								"api.rest.access.sec.delete")) {
							refNum = ColDelRestRequestDTO.getHeaderDetails().get(0).getRequestId();
							String chnl = ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
							String logsTable = "DEL_SEC_REST_INF_LOG";
							boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
							System.out.println("refNum validRefNo======" + validRefNo);
							if (validRefNo) {
								collateralRestResponse = securityDetailsRestService.deleteSecurityDetails(ColDelRestRequestDTO,pathInfo);
							} else {

								headerObj.setRequestId(ColDelRestRequestDTO.getHeaderDetails().get(0).getRequestId());
								headerObj.setChannelCode(ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
								commonHeaderResponse.add(headerObj);

								ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
								responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
								responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
								responseMessageList.add(responseMessageDetailDTO);

								bodyObj.setResponseStatus("FAILED");
								bodyObj.setResponseMessageList(responseMessageList);

								bodyRestList.add(bodyObj);

								restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
								collateralRestResponse.setHeaderDetails(commonHeaderResponse);
								collateralRestResponse.setBodyDetails(bodyRestList);
							}
						} else {

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);
							collateralRestResponse.setHeaderDetails(commonHeaderResponse);
							collateralRestResponse.setBodyDetails(bodyRestList);
							restinterfacelog.setInterfaceFailedReason(ResponseConstants.ACCESS_DENIED_MESSAGE);
						}
					}
				} catch (JsonSyntaxException e1) {
					System.out.println("e1 Exception deleteSecurityDetails()" + e1.getMessage());

					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);
					collateralRestResponse.setHeaderDetails(commonHeaderResponse);
					collateralRestResponse.setBodyDetails(bodyRestList);
					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					e1.printStackTrace();
				} catch (Exception e2) {
					System.out.println("e2 BException deleteSecurityDetails()" + e2.getMessage());

					headerObj.setRequestId(ColDelRestRequestDTO.getHeaderDetails().get(0).getRequestId());
					headerObj.setChannelCode(ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
					commonHeaderResponse.add(headerObj);
					
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE+" Message"+e2.getLocalizedMessage());
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);

					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					collateralRestResponse.setHeaderDetails(commonHeaderResponse);
					collateralRestResponse.setBodyDetails(bodyRestList);
					e2.printStackTrace();
				} finally {
					String json = gson.toJson(collateralRestResponse);
					System.out.println("json ====>" + json);
					if (collateralRestResponse.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
						restinterfacelog.setInterfaceStatus("SUCCESS");
					if (collateralRestResponse.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
						restinterfacelog.setInterfaceStatus("FAILED");
					}
					sendAsJson(response, collateralRestResponse);

					restinterfacelog.setRequestId(ColDelRestRequestDTO.getHeaderDetails().get(0).getRequestId());
					restinterfacelog.setChannelCode(ColDelRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
					restinterfacelog.setInterfaceFailedReason("");

					String jsonResponse = gson.toJson(collateralRestResponse);
					restinterfacelog.setResponseText(jsonResponse.toString());
					Date date1 = new Date();

					restinterfacelog.setResponseDate(date1);
					
					IRestFacilityInterfaceLogDAO restInterfaceLogDAO = (IRestFacilityInterfaceLogDAO) BeanHouse
							.get("restFacilityInterfaceLogDAO");

					try {
						restInterfaceLogDAO.insertSecurityDetailsInterfaceLog(restinterfacelog);
					} catch (Exception e) {
						System.out.println(
								"Exception while entering Interface entry for Facility Enquiry service" + e.getMessage());
						e.printStackTrace();
					}
				
				}
		} else if (null != pathInfo && ( pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_PARTY_UDF)
				|| pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_CAM_UDF)
				|| pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT1_UDF)
				|| pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT2_UDF) )) {

			HeaderDetailRestResponseDTO commonHeaderResponseDTO = new HeaderDetailRestResponseDTO();

			UdfEnquiryRestService udfEnquiryRestService = new UdfEnquiryRestService();

			BufferedReader reader = request.getReader();
			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			UdfEnquiryRestRequestDTO udfEnquiryRestRequestDTO = null;
			Date date = new Date();
			OBUdfEnqRestInterfaceLog restinterfacelog = new OBUdfEnqRestInterfaceLog();

			restinterfacelog.setRequestDate(date);

			try {
				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");
				udfEnquiryRestRequestDTO = gson.fromJson(reader, UdfEnquiryRestRequestDTO.class);

				jsonRequest = gson.toJson(udfEnquiryRestRequestDTO);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List<BodyRestResponseDTO> bodyDetailsList;
				String commonChannel = udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
				String commonPass = udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getPassCode();
				String commonReqId = udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getRequestId();
				String logsTable = "UDF_ENQUIRY_REST_INTERFACE_LOG";
				boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, commonReqId, commonChannel);
				if (validRefNo) {
					bodyDetailsList = validateHeaderFields(commonChannel, commonPass, commonReqId);
					if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
						headerObj.setRequestId(commonReqId);
						headerObj.setChannelCode(commonChannel);
						commonHeaderResponse.add(headerObj);
						commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
						commonRestResponseDTO.setBodyDetails(bodyDetailsList);
					} else {

						if (   (accessChannelForRestAPI.accessToChannel(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),"api.rest.access.party.udf.view"))
								&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_PARTY_UDF))
							|| (accessChannelForRestAPI.accessToChannel(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),"api.rest.access.cam.udf.view"))
								&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_CAM_UDF))
							|| (accessChannelForRestAPI.accessToChannel(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),"api.rest.access.limit1.udf.view"))
								&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT1_UDF))
							|| (accessChannelForRestAPI.accessToChannel(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),"api.rest.access.limit2.udf.view"))
								&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.GET_LIMIT2_UDF))
							) 
						{
								commonRestResponseDTO = udfEnquiryRestService.getUdfDetails(udfEnquiryRestRequestDTO, pathInfo);							
						}else{
								
								ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
								responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
								responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
								responseMessageList.add(responseMessageDetailDTO);

								bodyObj.setResponseStatus("FAILED");
								bodyObj.setResponseMessageList(responseMessageList);

								bodyRestList.add(bodyObj);
								commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
								commonRestResponseDTO.setBodyDetails(bodyRestList);
								restinterfacelog.setInterfaceFailedReason("NO ACCESS FOR GIVEN CHANNEL CODE");
							}
						}					
				} else {

					headerObj.setRequestId(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getRequestId());
					headerObj.setChannelCode(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
					commonHeaderResponse.add(headerObj);

					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
					responseMessageList.add(responseMessageDetailDTO);

					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);

					bodyRestList.add(bodyObj);

					restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
					commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
					commonRestResponseDTO.setBodyDetails(bodyRestList);
				}
			} catch (JsonSyntaxException e1) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				commonHeaderResponseDTO.setRequestId(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				commonHeaderResponseDTO.setChannelCode(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode());

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				headerObj.setRequestId(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				commonRestResponseDTO.setHeaderDetails(commonHeaderResponse);
				commonRestResponseDTO.setBodyDetails(bodyRestList);
				e.printStackTrace();
			} finally {
				String json = gson.toJson(commonRestResponseDTO);
				System.out.println("json ====>" + json);

				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (commonRestResponseDTO.getBodyDetails().get(0).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, commonRestResponseDTO);

				restinterfacelog.setRequestId(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(udfEnquiryRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(commonRestResponseDTO);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();
				restinterfacelog.setResponseDate(date1);

				IUdfEnqRestInterfaceLogDAO restInterfaceLogDAO = (IUdfEnqRestInterfaceLogDAO) BeanHouse.get("udfRestInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertUdfEnuiryDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println("Exception while entering Interface entry for Party Udf Enquiry service" + e.getMessage());
					e.printStackTrace();
				}
			}

		}else if (null != pathInfo && "on".equals(securityDeleteOnOffFlag) 
				&& (pathInfo.startsWith("/" + WholeSaleApi_Constant.ENQUIRY_AB_SPECIFIC_ASSET_SECURITY))) {

			BufferedReader reader = request.getReader();

			Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy hh24:mm:ss").create();
			ABEnquiryResponseDTO aBSARes = new ABEnquiryResponseDTO();
			Date date = new Date();
			OBRestFacilityInterfaceLog restinterfacelog = new OBRestFacilityInterfaceLog();

			restinterfacelog.setRequestDate(date);

			CollateralDeleteEnquiryRestRequestDTO colEnqRestRequestDTO = new CollateralDeleteEnquiryRestRequestDTO();

			//CollateralRestResponseDTO collateralRestResponse = new CollateralRestResponseDTO();

			try {

				accessChannelForRestAPI = (IAccessChannelForRestAPI) BeanHouse.get("accessChannelForRestAPI");

				colEnqRestRequestDTO = gson.fromJson(reader,CollateralDeleteEnquiryRestRequestDTO.class);

				jsonRequest = gson.toJson(colEnqRestRequestDTO);
				restinterfacelog.setRequestText(jsonRequest.toString());

				List bodyDetailsList;
				String channelCode = colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
				String passsCode = colEnqRestRequestDTO.getHeaderDetails().get(0).getPassCode();
				String requestId = colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId();
				bodyDetailsList = validateHeaderFields(channelCode, passsCode, requestId);

				if (null != bodyDetailsList && !bodyDetailsList.isEmpty()) {
					headerObj.setRequestId(requestId);
					headerObj.setChannelCode(channelCode);
					commonHeaderResponse.add(headerObj);
					aBSARes.setHeaderDetails(commonHeaderResponse);
					aBSARes.setBodyDetails(bodyDetailsList);

				} else {
					if (accessChannelForRestAPI.accessToChannel(
							colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode(),
							"api.rest.access.sec.view")) {
						refNum = colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId();
						String chnl = colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode();
						String logsTable = "ENQUIRY_SEC_INF_LOG";
						boolean validRefNo = utilObj.isDuplicateRefNumber(logsTable, refNum, chnl);
						System.out.println("refNum validRefNo======" + validRefNo);
						if (validRefNo) {
							aBSARes =(ABEnquiryResponseDTO) securityDetailsRestService.enqirySecurityDetails(colEnqRestRequestDTO,pathInfo);
						} else {

							headerObj.setRequestId(colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId());
							headerObj.setChannelCode(colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
							commonHeaderResponse.add(headerObj);

							ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
							responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_DUP);
							responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_DUP_MSG);
							responseMessageList.add(responseMessageDetailDTO);

							bodyObj.setResponseStatus("FAILED");
							bodyObj.setResponseMessageList(responseMessageList);

							bodyRestList.add(bodyObj);

							restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
							aBSARes.setHeaderDetails(commonHeaderResponse);
							aBSARes.setBodyDetails(bodyRestList);
						}
					} else {

						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseCode(ResponseConstants.ACCESS_DENIED);
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.ACCESS_DENIED_MESSAGE);
						responseMessageList.add(responseMessageDetailDTO);

						bodyObj.setResponseStatus("FAILED");
						bodyObj.setResponseMessageList(responseMessageList);

						bodyRestList.add(bodyObj);
						aBSARes.setHeaderDetails(commonHeaderResponse);
						aBSARes.setBodyDetails(bodyRestList);
						restinterfacelog.setInterfaceFailedReason(ResponseConstants.ACCESS_DENIED_MESSAGE);
					}
				}
			} catch (JsonSyntaxException e1) {
				System.out.println("e1 Exception deleteSecurityDetails()" + e1.getMessage());

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.INVALID_JSON);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.INVALID_JSON_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);
				aBSARes.setHeaderDetails(commonHeaderResponse);
				aBSARes.setBodyDetails(bodyRestList);
				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				e1.printStackTrace();
			} catch (Exception e2) {
				System.out.println("e2 BException deleteSecurityDetails()" + e2.getMessage());

				headerObj.setRequestId(colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				commonHeaderResponse.add(headerObj);

				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.EXCEPTION);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.EXCEPTION_MESSAGE+" Message"+e2.getLocalizedMessage());
				responseMessageList.add(responseMessageDetailDTO);

				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);

				bodyRestList.add(bodyObj);

				restinterfacelog.setInterfaceFailedReason(responseMessageDetailDTO.getResponseMessage());
				aBSARes.setHeaderDetails(commonHeaderResponse);
				aBSARes.setBodyDetails(bodyRestList);
				e2.printStackTrace();
			} finally {
				String json = gson.toJson(aBSARes);
				System.out.println("json ====>" + json);
				if (((BodyRestResponseDTO) aBSARes.getBodyDetails().get(0)).getResponseStatus().equalsIgnoreCase("SUCCESS"))
					restinterfacelog.setInterfaceStatus("SUCCESS");
				if (((BodyRestResponseDTO) aBSARes.getBodyDetails().get(0)).getResponseStatus().equalsIgnoreCase("FAILED")) {
					restinterfacelog.setInterfaceStatus("FAILED");
				}
				sendAsJson(response, aBSARes);

				restinterfacelog.setRequestId(colEnqRestRequestDTO.getHeaderDetails().get(0).getRequestId());
				restinterfacelog.setChannelCode(colEnqRestRequestDTO.getHeaderDetails().get(0).getChannelCode());
				restinterfacelog.setInterfaceFailedReason("");

				String jsonResponse = gson.toJson(aBSARes);
				restinterfacelog.setResponseText(jsonResponse.toString());
				Date date1 = new Date();

				restinterfacelog.setResponseDate(date1);

				IRestFacilityInterfaceLogDAO restInterfaceLogDAO = (IRestFacilityInterfaceLogDAO) BeanHouse
						.get("restFacilityInterfaceLogDAO");

				try {
					restInterfaceLogDAO.insertSecurityDetailsInterfaceLog(restinterfacelog);
				} catch (Exception e) {
					System.out.println(
							"Exception while entering Interface entry for Facility Enquiry service" + e.getMessage());
					e.printStackTrace();
				}

			}
		}
			
	}

	@SuppressWarnings("null")
	public List<BodyRestResponseDTO> validateHeaderFields(String channelCode, String passCode, String requestId) {

		BodyRestResponseDTO bodyObj = new BodyRestResponseDTO();

		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		List<BodyRestResponseDTO> bodyRestList = new LinkedList<BodyRestResponseDTO>();

		if (null == channelCode || channelCode.isEmpty()) {
			ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.CHANNEL_CODE_REQ_MESSAGE);
			responseMessageDetailDTO.setResponseCode(ResponseConstants.CHANNEL_CODE_REQ);
			responseMessageList.add(responseMessageDetailDTO);
		}

		if (null == passCode || passCode.isEmpty()) {

			ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.PASSCODE_REQ_MESSAGE);
			responseMessageDetailDTO.setResponseCode(ResponseConstants.PASSCODE_REQ);
			responseMessageList.add(responseMessageDetailDTO);

		}
		if (null == requestId || requestId.isEmpty()) {
			ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_REQ_MESSAGE);
			responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_REQ);
			responseMessageList.add(responseMessageDetailDTO);
		}

		if (null != channelCode && !channelCode.isEmpty()) {
			if (channelCode.length() > 40) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.CHANNEL_CODE_LENGTH_MESSAGE);
				responseMessageDetailDTO.setResponseCode(ResponseConstants.CHANNEL_CODE_LENGTH);
				responseMessageList.add(responseMessageDetailDTO);
			}
		}

		if (null != passCode && !passCode.isEmpty()) {
			ResourceBundle bundle = ResourceBundle.getBundle("rest_api");
			if (channelCode != null && !channelCode.isEmpty()) {
				String channelName = bundle.getString("channel.code.for.verification");
				String[] channelCodes = channelName.split(",");
				List channelCodeList = Arrays.asList(channelCodes);
				if (channelCodeList.contains(channelCode)) {
					String passVerify = bundle.getString("api.rest.passcode.verification.".concat(channelCode));
					if (!passVerify.equals(passCode)) {
						ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
						responseMessageDetailDTO.setResponseMessage(ResponseConstants.PASSCODE_WRONG_MESSAGE);
						responseMessageDetailDTO.setResponseCode(ResponseConstants.PASSCODE_WRONG);
						responseMessageList.add(responseMessageDetailDTO);
					}
				}	
			}
		}

		if (null != requestId && !requestId.isEmpty() && ASSTValidator.isNumeric(requestId)) {

			if (null != requestId && !requestId.isEmpty()) {
				if (requestId.length() > 22) {
					ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_LEN_MESSAGE);
					responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_LEN);
					responseMessageList.add(responseMessageDetailDTO);

				}
			}

		} else if (null != requestId && !requestId.isEmpty() && !ASSTValidator.isNumeric(requestId)) {
			// validation for numeric

			ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_NUM_MESSAGE);
			responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_NUM);
			responseMessageList.add(responseMessageDetailDTO);

		}

		if (null != channelCode && !channelCode.isEmpty()) {
			ResourceBundle bundle = ResourceBundle.getBundle("rest_api");
			String channelName = bundle.getString("channel.code.for.verification");
			String[] channelCodes = channelName.split(",");

			List channelCodeList = Arrays.asList(channelCodes);

			if (!channelCodeList.contains(channelCode)) {
				ResponseMessageDetailDTO responseMessageDetailDTO = new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.CHANNEL_CODE_INVALID_MESSAGE);
				responseMessageDetailDTO.setResponseCode(ResponseConstants.CHANNEL_CODE_INVALID);
				responseMessageList.add(responseMessageDetailDTO);

			}
		}

		if (null != responseMessageList && !responseMessageList.isEmpty()) {
			bodyObj.setResponseMessageList(responseMessageList);
			bodyRestList.add(bodyObj);
			bodyObj.setResponseStatus("FAILED");

		}

		return bodyRestList;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();
		System.out.println("doGet GET path======" + path);

	}

}