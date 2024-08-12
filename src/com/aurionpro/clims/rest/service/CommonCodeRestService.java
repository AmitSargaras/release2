package com.aurionpro.clims.rest.service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.dto.BodyRestResponseDTO;
import com.aurionpro.clims.rest.dto.CommonCodeDTOMapper;
import com.aurionpro.clims.rest.dto.CommonCodeDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.CommonCodeRestRequestDTO;
import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.HeaderDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.aurionpro.clims.rest.interfaceLog.OBRestInterfaceLog;
import com.google.gson.Gson;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class CommonCodeRestService {

	
	 CommonCodeDTOMapper commonCodeDTOMapper = new CommonCodeDTOMapper();

	private ICommonUserProxy userProxy;
	private ICMSTeamProxy cmsTeamProxy;
	public void setUserProxy(ICommonUserProxy userProxy) {
		this.userProxy = userProxy;
	}
	public ICommonUserProxy getUserProxy() {
		return userProxy;
	}
	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}
	public void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}

	

	public ILimit deleteSecurityDetails(ILimit lmtObj, String DelIndex)
			throws CMSValidationFault, CMSFault {

		List icollAllo = new ArrayList();
		ICollateralAllocation[] array = lmtObj.getCollateralAllocations();
		if (lmtObj.getCollateralAllocations() != null
				&& lmtObj.getCollateralAllocations().length > 0) {
			for (int i = 0; i < array.length; i++) {
				ICollateralAllocation obj = array[i];

				if (obj.getHostStatus() != null
						&& "I".equals(obj.getHostStatus())) {
					String strColID = String.valueOf(obj.getCpsSecurityId());
					ICollateralAllocation icollAlloObj = new OBCollateralAllocation();
					if (strColID != null && !DelIndex.equals(strColID)) {
						icollAlloObj = lmtObj.getCollateralAllocations()[i];
						icollAllo.add(icollAlloObj);
						break;
					}
				}
			}
		}
		lmtObj.setCollateralAllocations(null);
		ICollateralAllocation[] icollAlloFinal = new ICollateralAllocation[icollAllo
				.size()];
		if (icollAllo != null && icollAllo.size() > 0) {
			for (int i = 0; i < icollAllo.size(); i++) {
				icollAlloFinal[i] = (ICollateralAllocation) icollAllo.get(i);
			}
		}
		lmtObj.setCollateralAllocations(icollAlloFinal);
		return lmtObj;
	}

	public ITeamMembership validateMakerCheckerSelection(
			ITeamMembership[] teamMemberShips, String membershipID)
			throws LoginProcessException {
		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			// the user has maker checker role
			if ((membershipID == null) || (membershipID.trim().length() == 0)) {
				lpexp.setErrorCode(
						CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long membershipIDlong = teamMemberShips[i]
						.getTeamTypeMembership().getMembershipType()
						.getMembershipTypeID();
				String membershipIDString = new Long(membershipIDlong)
						.toString();
				if (membershipID.equals(membershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	public OBTrxContext setContextAsPerUserId(OBTrxContext context,
			String userID) throws NumberFormatException, LimitException {

		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		try {

			user = (OBCommonUser) getUserProxy().getUser(userID);
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
			context = new OBTrxContext(user, teams[0]);

			// teams =
			// getCmsTeamProxy().getTeamsByUserID(Long.parseLong("20110805000000060"));

			memberships = getCmsTeamProxy()
					.getTeamMembershipListByUserID(user.getUserID());
			boolean makerCheckerSameUserChk = Boolean
					.valueOf(PropertyManager
							.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER))
					.booleanValue();
			ITeamMembership membershipChk = null;
			if (makerCheckerSameUserChk) {
				membershipChk = validateMakerCheckerSelection(memberships, "");
			} else {
				membershipChk = validateTeamTypeMembershipRequested(memberships,
						null);
			}
			String teamMembershipIDChk = String.valueOf(
					membershipChk.getTeamTypeMembership().getMembershipID());
			context.setTeamMembershipID(getTeamMembershipIDFromTeam(
					Long.parseLong(teamMembershipIDChk), teams[0]));

			return context;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BizStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ITeamMembership validateTeamTypeMembershipRequested(
			ITeamMembership[] teamMemberShips, String teamMembershipReq)
			throws LoginProcessException {

		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			if ((teamMembershipReq == null)
					|| (teamMembershipReq.trim().length() == 0)) { // default
				lpexp.setErrorCode(
						CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long teamMembershipIDlong = teamMemberShips[i]
						.getTeamTypeMembership().getMembershipID();
				String teamMembershipIDString = new Long(teamMembershipIDlong)
						.toString();
				if (teamMembershipReq.equals(teamMembershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	private long getTeamMembershipIDFromTeam(long teamTypeID, ITeam team) {
		if (team == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		ITeamMembership[] memberships = team.getTeamMemberships();
		if (memberships != null) {
			for (int i = 0; i < memberships.length; i++) {
				if (memberships[i].getTeamTypeMembership()
						.getMembershipID() == teamTypeID) {
					return memberships[i].getTeamMembershipID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public CommonRestResponseDTO getCommonCodeDetails(
			CommonCodeRestRequestDTO requestDto, Gson gson)
			throws CMSValidationFault, CMSFault {
		
		OBRestInterfaceLog restinterfacelog = new OBRestInterfaceLog(); 
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	   String dt=  formatter.format(date);
	   System.out.println("dt===="+dt);
	   
	   String jsonRequest = gson.toJson(requestDto);
		System.out.println("jsonRequest ====>"+jsonRequest);
		
		restinterfacelog.setRequestDate(date);
		CommonRestResponseDTO obj = new CommonRestResponseDTO();
		
		
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		
		
		System.out.println("Inside getCommonCodeDetails");
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat(
				"dd/MMM/yyyy");
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse
				.get("makerCheckerUserUtil");
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		List<BodyRestResponseDTO> BodyRestList = new LinkedList<BodyRestResponseDTO>();

		if(requestDto.getBodyDetails().get(0).getCategoryCode() != null) {
		if (requestDto.getBodyDetails().get(0).getCategoryCode().trim().isEmpty()) {
			ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_CATEGORY_CODE_REQ_MESSAGE);
			responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_CATEGORY_CODE_REQ);
			responseMessageList.add(responseMessageDetailDTO);
			
			headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
			headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
			ccHeaderResponse.add(headerObj);
			
			
	
			
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageList);
			
			BodyRestList.add(bodyObj);
			obj.setHeaderDetails(ccHeaderResponse);
			obj.setBodyDetails(BodyRestList);
			
			return obj;
		}
		}
		if(requestDto.getHeaderDetails().get(0).getRequestId() != null)
		{
			if(requestDto.getHeaderDetails().get(0).getRequestId().trim().isEmpty()) {

				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_REQ_MESSAGE);
				responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_REQ);
				responseMessageList.add(responseMessageDetailDTO);
				
				headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);
				
				BodyRestList.add(bodyObj);
				
				
				obj.setHeaderDetails(ccHeaderResponse);
				obj.setBodyDetails(BodyRestList);
				
				return obj;
			
			}
		}
		if(requestDto.getHeaderDetails().get(0).getRequestId() != null)
		{
			if( !ASSTValidator.isNumeric(requestDto.getHeaderDetails().get(0).getRequestId())) {

				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.REQUEST_ID_NUM_MESSAGE);
				responseMessageDetailDTO.setResponseCode(ResponseConstants.REQUEST_ID_NUM);
				responseMessageList.add(responseMessageDetailDTO);
				
				headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);
				
				BodyRestList.add(bodyObj);
				
				
				obj.setHeaderDetails(ccHeaderResponse);
				obj.setBodyDetails(BodyRestList);
				
				return obj;
			
			}
		}
		
		obj = commonCodeDTOMapper.getCommonCodeRestResponseDTOWithActualValues(requestDto);
		if (null == obj.getBodyDetails() || !obj.getBodyDetails().get(0).getResponseStatus().equals("FAILED")) {
		
		try {
			System.out.println("Going for common code List");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			if(requestDto.getBodyDetails().get(0).getCategoryCode() != null) {
				if (requestDto.getBodyDetails().get(0).getCategoryCode().trim().isEmpty()) {
					ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
					responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_CATEGORY_CODE_REQ_MESSAGE);
					responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_CATEGORY_CODE_REQ);
					responseMessageList.add(responseMessageDetailDTO);
					
					headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
					headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
					ccHeaderResponse.add(headerObj);
					
			
					bodyObj.setResponseStatus("FAILED");
					bodyObj.setResponseMessageList(responseMessageList);
								
					BodyRestList.add(bodyObj);
					
					
					obj.setHeaderDetails(ccHeaderResponse);
					obj.setBodyDetails(BodyRestList);
					
					return obj;
				}
				
			List<OBCommonCodeEntry> obcommoncodeentryList = limitDAO
					.getCommonCodeList(requestDto);
			if (obcommoncodeentryList != null
					&& obcommoncodeentryList.size() > 0) {
				List<CommonCodeDetailRestResponseDTO> ccResponseDTOList = new LinkedList<CommonCodeDetailRestResponseDTO>();
			
				
				
				for (OBCommonCodeEntry item : obcommoncodeentryList) {
					CommonCodeDetailRestResponseDTO ccDetailDTO = new CommonCodeDetailRestResponseDTO();
					if (item.getCategoryCode() != null
							&& !item.getCategoryCode().isEmpty()) {
						ccDetailDTO.setCategoryCode(item.getCategoryCode());
					} else {
						ccDetailDTO.setCategoryCode("-");
					}
					String EntryId = String.valueOf(item.getEntryId());
					if (EntryId != null && !EntryId.isEmpty()) {
						ccDetailDTO.setEntryId(EntryId);
					} else {
						ccDetailDTO.setEntryId("-");
					}
					if (item.getEntryName() != null
							&& !item.getEntryName().isEmpty()) {
						ccDetailDTO.setEntryName(item.getEntryName());
					} else {
						ccDetailDTO.setEntryName("-");
					}
					if (item.getEntryCode() != null
							&& !item.getEntryCode().isEmpty()) {
						ccDetailDTO.setEntryCode(item.getEntryCode());
					} else {
						ccDetailDTO.setEntryCode("-");
					}
//					if (item.getRefEntryCode() != null
//							&& !item.getRefEntryCode().isEmpty()) {
//						ccDetailDTO.setRefEntryCode(item.getRefEntryCode());
//					}
					if (item.getActiveStatusStr() != null
							&& !item.getActiveStatusStr().isEmpty()) {

						if (item.getActiveStatusStr().equals("0"))
							ccDetailDTO.setStatus("INACTIVE");
						if (item.getActiveStatusStr().equals("1"))
							ccDetailDTO.setStatus("ACTIVE");
					} else {
						ccDetailDTO.setStatus("-");
					}

					ccResponseDTOList.add(ccDetailDTO);
				}
				
				headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
					
				
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.SUCCESS_RESPONSE_CODE);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.COMMONCODE_SUCCESS_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);
		
				bodyObj.setResponseStatus("SUCCESS");
				bodyObj.setResponseMessageList(responseMessageList);
				
				bodyObj.setCommonCodeDetailRestList(ccResponseDTOList);
				BodyRestList.add(bodyObj);
				
				
				obj.setHeaderDetails(ccHeaderResponse);
				obj.setBodyDetails(BodyRestList);
				
			}
			else
			{
				headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
					
				
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.NO_DATA_FOUND);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.NO_DATA_FOUND_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);
		
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageList);
				BodyRestList.add(bodyObj);
				
				
				obj.setHeaderDetails(ccHeaderResponse);
				obj.setBodyDetails(BodyRestList);
			}
		}else {
			headerObj.setRequestId(requestDto.getHeaderDetails().get(0).getRequestId());
			headerObj.setChannelCode(requestDto.getHeaderDetails().get(0).getChannelCode());
			ccHeaderResponse.add(headerObj);
				
			
			ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
			responseMessageDetailDTO.setResponseCode(ResponseConstants.CC_CATEGORY_CODE_REQ);
			responseMessageDetailDTO.setResponseMessage(ResponseConstants.CC_CATEGORY_CODE_REQ_MESSAGE);
			responseMessageList.add(responseMessageDetailDTO);
	
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageList);
			BodyRestList.add(bodyObj);
			
			
			obj.setHeaderDetails(ccHeaderResponse);
			obj.setBodyDetails(BodyRestList);
		}
			
		}
		catch (Exception e) {
			System.out.println("Exception in  common code Webservice e=" + e);
			throw new CMSException(e.getMessage(), e);
		}
		
		}
		/*if(bodyObj.getResponseStatus().equalsIgnoreCase("SUCCESS"))
			restinterfacelog.setInterfaceStatus(bodyObj.getResponseStatus());
		if(bodyObj.getResponseStatus().equalsIgnoreCase("FAILED"))
		{
			restinterfacelog.setInterfaceStatus(bodyObj.getResponseStatus());
		}
		restinterfacelog.setRequestId(headerObj.getRequestId());
		restinterfacelog.setChannelCode(headerObj.getChannelCode());
	//	restinterfacelog.setInterfaceFailedReason("");
		restinterfacelog.setInterfaceFailedReason("");
		restinterfacelog.setRequestText(jsonRequest.toString());
		
		String jsonResponse = gson.toJson(obj);
		System.out.println("jsonResponse ====>"+jsonResponse);
		restinterfacelog.setResponseText(jsonResponse.toString());
	    Date date1 = new Date();  
		restinterfacelog.setResponseDate(date1);
		
		IRestInterfaceLogDAO restInterfaceLogDAO = (IRestInterfaceLogDAO)BeanHouse.get("restInterfaceLogDAO");
		
		try {
			restInterfaceLogDAO.insertCommonCodeLog(restinterfacelog);
		} catch (Exception e) {
			System.out.println("Exception while entering Interface entry for CommonCode Enquiry service"+e.getMessage());
			e.printStackTrace();
		}*/
		return obj;
	}

}
