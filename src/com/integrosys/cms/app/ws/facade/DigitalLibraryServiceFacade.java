package com.integrosys.cms.app.ws.facade;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.digitalLibrary.bus.DigiLibDocExportLogDao;
import com.integrosys.cms.app.digitalLibrary.bus.DigiLibDocExportLogDaoFactory;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.DigitalLibraryDTOMapper;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTOForV2;
import com.integrosys.cms.app.ws.dto.DigitalLibraryResponseDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryResponseDTOForV2;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class DigitalLibraryServiceFacade {

	@Autowired
	private DigitalLibraryDTOMapper digitalLibraryDTOMapper;

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

	public DigitalLibraryDTOMapper getDigitalLibraryDTOMapper() {
		return digitalLibraryDTOMapper;
	}

	public void setDigitalLibraryDTOMapper(DigitalLibraryDTOMapper digitalLibraryDTOMapper) {
		this.digitalLibraryDTOMapper = digitalLibraryDTOMapper;
	}

	public ILimit deleteSecurityDetails(ILimit lmtObj, String DelIndex) throws CMSValidationFault, CMSFault {

		List icollAllo = new ArrayList();
		ICollateralAllocation[] array = lmtObj.getCollateralAllocations();
		if (lmtObj.getCollateralAllocations() != null && lmtObj.getCollateralAllocations().length > 0) {
			for (int i = 0; i < array.length; i++) {
				ICollateralAllocation obj = array[i];

				if (obj.getHostStatus() != null && "I".equals(obj.getHostStatus())) {
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
		ICollateralAllocation[] icollAlloFinal = new ICollateralAllocation[icollAllo.size()];
		if (icollAllo != null && icollAllo.size() > 0) {
			for (int i = 0; i < icollAllo.size(); i++) {
				icollAlloFinal[i] = (ICollateralAllocation) icollAllo.get(i);
			}
		}
		lmtObj.setCollateralAllocations(icollAlloFinal);
		return lmtObj;
	}

	public ITeamMembership validateMakerCheckerSelection(ITeamMembership[] teamMemberShips, String membershipID)
			throws LoginProcessException {
		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			// the user has maker checker role
			if ((membershipID == null) || (membershipID.trim().length() == 0)) {
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long membershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipType()
						.getMembershipTypeID();
				String membershipIDString = new Long(membershipIDlong).toString();
				if (membershipID.equals(membershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	public OBTrxContext setContextAsPerUserId(OBTrxContext context, String userID)
			throws NumberFormatException, LimitException {

		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		try {

			user = (OBCommonUser) getUserProxy().getUser(userID);
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
			context = new OBTrxContext(user, teams[0]);

			// teams =
			// getCmsTeamProxy().getTeamsByUserID(Long.parseLong("20110805000000060"));

			memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
			boolean makerCheckerSameUserChk = Boolean
					.valueOf(PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
			ITeamMembership membershipChk = null;
			if (makerCheckerSameUserChk) {
				membershipChk = validateMakerCheckerSelection(memberships, "");
			} else {
				membershipChk = validateTeamTypeMembershipRequested(memberships, null);
			}
			String teamMembershipIDChk = String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
			context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk), teams[0]));

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

	public ITeamMembership validateTeamTypeMembershipRequested(ITeamMembership[] teamMemberShips,
			String teamMembershipReq) throws LoginProcessException {

		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			if ((teamMembershipReq == null) || (teamMembershipReq.trim().length() == 0)) { // default
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long teamMembershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipID();
				String teamMembershipIDString = new Long(teamMembershipIDlong).toString();
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
				if (memberships[i].getTeamTypeMembership().getMembershipID() == teamTypeID) {
					return memberships[i].getTeamMembershipID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	@CLIMSWebServiceMethod
	public DigitalLibraryResponseDTO getDigitalLibraryDetails(DigitalLibraryRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		ICMSTrxResult response = null;
		List newAlloc = new ArrayList();
		HashMap facMap = new HashMap();
		System.out.println("Inside getDigitalLibraryDetails");
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		if (requestDto.getPartyId().trim().isEmpty() && requestDto.getPanNo().trim().isEmpty()
				&& requestDto.getSystemId().trim().isEmpty()) {
			System.out.println("one of the field is Mandatory Exception");
			throw new CMSException("one of the field is Mandatory");
		}

		DigitalLibraryRequestDTO digitalLibraryRequestDTO = digitalLibraryDTOMapper
				.getDigitalLibraryRequestDTOWithActualValues(requestDto);
		ActionErrors cpsIdErrors = digitalLibraryRequestDTO.getErrors();
		System.out.println("After errors checked== Validator");
		HashMap map = new HashMap();
		if (cpsIdErrors.size() > 0) {
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.DIGITALLIBRARY);
		}

		DigitalLibraryResponseDTO obj = new DigitalLibraryResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			System.out.println("Going for image List");

			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			List<String> imageList = new ArrayList<String>();
			imageList = limitDAO.getImageIdList(digitalLibraryRequestDTO.getPartyId(), digitalLibraryRequestDTO);
			String imageListStirng = new String();
			for (int i = 0; i < imageList.size(); i++) {
				if (null != imageList.get(i)) {
					imageListStirng = imageListStirng.concat(imageList.get(i));
				}
				if (imageList.size() > 1) {
					if ((i + 1) < imageList.size()) {
						imageListStirng = imageListStirng.concat(",");
					}
				}
			}

			String partyIDs = "";

			if (null != digitalLibraryRequestDTO.getPanNo() && null == digitalLibraryRequestDTO.getPartyId()
					&& null == digitalLibraryRequestDTO.getSystemId()) {
				partyIDs = limitDAO.getPartyIdByPanNo(digitalLibraryRequestDTO.getPanNo());
			} else if (null == digitalLibraryRequestDTO.getPanNo() && null == digitalLibraryRequestDTO.getPartyId()
					&& null != digitalLibraryRequestDTO.getSystemId()) {
				partyIDs = limitDAO.getPartyIdBySystemId(digitalLibraryRequestDTO.getSystemId());
			} else if (null != digitalLibraryRequestDTO.getPanNo() && null == digitalLibraryRequestDTO.getPartyId()
					&& null != digitalLibraryRequestDTO.getSystemId()) {
				partyIDs = limitDAO.getPartyIdByPanNo(digitalLibraryRequestDTO.getPanNo());
			} else {
				partyIDs = digitalLibraryRequestDTO.getPartyId();
			}

			System.out.println("set response to SUCCESS");
			obj.setResponseStatus("SUCCESS");
			obj.setImageId(imageListStirng);
			if(!imageListStirng.isEmpty())
			obj.setRemarks("Image id(s) shared for Party ID:" + partyIDs);
			else
				obj.setRemarks("No Records Found");

		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException in digital Lib Webservice e=" + e);
			throw new CMSException(e.getMessage(), e);
		} catch (Exception e) {
			System.out.println("Exception in digital Lib Webservice e=" + e);
			throw new CMSException(e.getMessage(), e);
		}
		return obj;
	}

	@CLIMSWebServiceMethod
	public DigitalLibraryResponseDTO getDigitalLibraryDetailsForV2(DigitalLibraryRequestDTO requestDto)
			throws CMSValidationFault, CMSFault {
		
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String digiLibNoOfRecords = bundle.getString("digital.library.no.of.records.allowed");
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		DigiLibDocExportLogDao digiLibDocExportLogDao = DigiLibDocExportLogDaoFactory.getDigiLibDocExportLogDao();

		String partyIDs = "";

		if (null != requestDto.getPanNo() && null == requestDto.getPartyId() && null == requestDto.getSystemId()) {
			partyIDs = limitDAO.getPartyIdByPanNo(requestDto.getPanNo());
		} else if (null == requestDto.getPanNo() && null == requestDto.getPartyId()
				&& null != requestDto.getSystemId()) {
			partyIDs = limitDAO.getPartyIdBySystemId(requestDto.getSystemId());
		} else if (null != requestDto.getPanNo() && null == requestDto.getPartyId()
				&& null != requestDto.getSystemId()) {
			partyIDs = limitDAO.getPartyIdByPanNo(requestDto.getPanNo());
		} else {
			partyIDs = requestDto.getPartyId();
		}

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("REFERENCE_NO", Long.toString(System.currentTimeMillis()));
		parameterMap.put("PARTY_ID", requestDto.getPartyId());
		parameterMap.put("PAN_NO", requestDto.getPanNo());
		parameterMap.put("CLIMS_SYSTEM_ID", requestDto.getSystemId());
		parameterMap.put("PARTY_NAME", requestDto.getPartyName());
		parameterMap.put("INTERFACE_STATUS", "FAILED");		
		parameterMap.put("PAN_NO", requestDto.getPanNo());
		parameterMap.put("PAN_NO", requestDto.getPanNo());
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(DigitalLibraryRequestDTO.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(requestDto, sw);
			String requestXMLString = sw.toString();
			parameterMap.put("REQUEST_TEXT", requestXMLString);
			Timestamp requestTimestamp = new Timestamp(System.currentTimeMillis());
			parameterMap.put("REQUEST_DATE", requestTimestamp);
			
		} catch (JAXBException e1) {
			e1.printStackTrace();
			parameterMap.put("INTERFACE_FAILED_REASON", e1.getMessage());
			digiLibDocExportLogDao.addExportLog(parameterMap);
			throw new CMSException(e1.getMessage());
		}		

		System.out.println("Inside getDigitalLibraryDetails");
		if (requestDto.getPartyId().trim().isEmpty() && requestDto.getPanNo().trim().isEmpty()
				&& requestDto.getSystemId().trim().isEmpty()) {
			System.out.println("one of the field is Mandatory Exception");
			parameterMap.put("INTERFACE_FAILED_REASON", "party id or pan number or system id is required");
			digiLibDocExportLogDao.addExportLog(parameterMap);
			throw new CMSException("PartyId or PAN Number or SystemID either of one field is mandatory in Request");
		}

		DigitalLibraryRequestDTO digitalLibraryRequestDTO = digitalLibraryDTOMapper
				.getDigitalLibraryRequestDTOWithActualValuesForV2(requestDto);
		ActionErrors cpsIdErrors = digitalLibraryRequestDTO.getErrors();
		System.out.println("After errors checked== Validator");
		HashMap map = new HashMap();
		if (cpsIdErrors.size() > 0) {
			map.put("1", cpsIdErrors);
			String handleErrorList = ValidationUtility.handleErrorList(map,CLIMSWebService.DIGITALLIBRARY);
			parameterMap.put("INTERFACE_FAILED_REASON", handleErrorList);
			digiLibDocExportLogDao.addExportLog(parameterMap);
			ValidationUtility.handleError(map, CLIMSWebService.DIGITALLIBRARY);
		}

		DigitalLibraryResponseDTO obj = new DigitalLibraryResponseDTO();
		String statusMessage = "FAILED";
		String tagIds;
		StringBuilder tagId =null;
		try {
			System.out.println("Going for image List");

			List<String> imageList = new ArrayList<String>();
			
			List<String> imageIDList= new ArrayList<String>();
			
			//String camDetails = limitDAO.getCamDetails(digitalLibraryRequestDTO.getPartyId(),digitalLibraryRequestDTO.getPanNo(),digitalLibraryRequestDTO.getSystemId());
			tagId = limitDAO.getTagIdwithCamDetails(digitalLibraryRequestDTO.getPartyId(),digitalLibraryRequestDTO.getPanNo(),digitalLibraryRequestDTO.getSystemId());
			if(tagId!=null && !tagId.toString().isEmpty())
			{
				tagIds=tagId.toString();
				tagIds=tagIds.replaceAll(",$", "");
				imageIDList = limitDAO.getImageDetails(tagIds);
			}
				String imageIdStr = "";
			if(imageIDList != null && !imageIDList.isEmpty())
			{
				
				StringBuilder docCodeValue = new StringBuilder("");
				for (String s : imageIDList) {
					docCodeValue.append(s + ",");
				}
				imageIdStr = docCodeValue.toString();
				imageIdStr = imageIdStr.replaceAll(",$", "");
			}
			else
			{
				imageIdStr = "''";
			}
			if(digitalLibraryRequestDTO.getDocCode() == null || digitalLibraryRequestDTO.getDocCode().isEmpty())
				imageList = limitDAO.getImageIdListForV2(digitalLibraryRequestDTO.getPartyId(), digitalLibraryRequestDTO,imageIdStr);
			else
				imageList = limitDAO.getImageIdListForV2(digitalLibraryRequestDTO,digitalLibraryRequestDTO.getPartyId(),imageIdStr);
			String imageListStirng = new String();
			int digiLibNoOfRecordsInt = Integer.parseInt(digiLibNoOfRecords);  
					
			if(imageList.size() < digiLibNoOfRecordsInt)
			{
			System.out.println("SIZE OF RESPONSE---------------------------------------->>>>>>>>>>"+imageList.size());
			for (int i = 0; i < imageList.size(); i++) {
				if (null != imageList.get(i)) {
					imageListStirng = imageListStirng.concat(imageList.get(i));
				}
				if (imageList.size() > 1) {
					if ((i + 1) < imageList.size()) {
						imageListStirng = imageListStirng.concat("|");
					}
				}
			}
			statusMessage = "SUCCESS";
			obj.setResponseStatus(statusMessage);
			obj.setImageId(imageListStirng);
			if(!imageListStirng.isEmpty())
				obj.setRemarks("Image id(s) shared for Party ID:" + partyIDs);
				else
					obj.setRemarks("No Records Found");
			parameterMap.put("INTERFACE_STATUS", statusMessage);
			
			}
			else
			{
				obj.setResponseStatus(statusMessage);
				obj.setRemarks("Request has been made for Image details of "+imageList.size()+" documents which exceeds the capacity of "+digiLibNoOfRecords+".");
				
			}
			obj.setImageId(imageListStirng);
			/*if(!imageListStirng.isEmpty())
				obj.setRemarks("Image id(s) shared for Party ID:" + partyIDs);
				else
					obj.setRemarks("No Records Found");*/
			parameterMap.put("INTERFACE_STATUS", statusMessage);
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(DigitalLibraryResponseDTO.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				StringWriter sw = new StringWriter();
				jaxbMarshaller.marshal(obj, sw);
				String responseXMLString = sw.toString();
				parameterMap.put("RESPONSE_TEXT", responseXMLString);
				Timestamp responseTimestamp = new Timestamp(System.currentTimeMillis());
				parameterMap.put("RESPONSE_DATE", responseTimestamp);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException in digital Lib Webservice e=" + e);
			parameterMap.put("INTERFACE_STATUS", "FAILED");
			parameterMap.put("INTERFACE_FAILED_REASON", e.getMessage());
			throw new CMSException(e.getMessage(), e);
		} catch (Exception e) {
			System.out.println("Exception in digital Lib Webservice e=" + e);
			parameterMap.put("INTERFACE_STATUS", "FAILED");
			parameterMap.put("INTERFACE_FAILED_REASON", e.getMessage());
			throw new CMSException(e.getMessage(), e);
		} finally {
			digiLibDocExportLogDao.addExportLog(parameterMap);
		}
		return obj;
	}

}
