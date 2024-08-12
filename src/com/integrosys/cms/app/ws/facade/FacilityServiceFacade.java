package com.integrosys.cms.app.ws.facade;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.aurionpro.clims.rest.common.ValidationErrorDetailsDTO;
import com.aurionpro.clims.rest.common.ValidationUtilityRest;
import com.aurionpro.clims.rest.constants.ResponseConstants;
import com.aurionpro.clims.rest.dto.BodyRestResponseDTO;
import com.aurionpro.clims.rest.dto.CoBorrowerDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.CommonRestResponseDTO;
import com.aurionpro.clims.rest.dto.FacilityBodyRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.FacilityDetlRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityLineDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilitySCODDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilitySCODRestResponseDTO;
import com.aurionpro.clims.rest.dto.HeaderDetailRestResponseDTO;
import com.aurionpro.clims.rest.dto.ResponseMessageDetailDTO;
import com.aurionpro.clims.rest.dto.SecurityDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.SecurityDetailRestResponseDTO;
import com.aurionpro.clims.rest.interfaceLog.OBRestInterfaceLog;
import com.google.gson.Gson;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.LmtColSearchCriteria;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.bus.OBFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.manualinput.security.proxy.SBMISecProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.dto.AdhocFacilityRequestDTO;
import com.integrosys.cms.app.ws.dto.AdhocFacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityDeleteRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.FacilityNewFieldsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityLineDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.FacilityReadRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilityReadResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilityResponseDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.FacilitySCODResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityNewFieldsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailsDTOMapper;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.batch.eod.EndOfDaySyncMastersServiceImpl;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
import com.integrosys.cms.ui.manualinput.limit.GenerateDeferralsForSCOD;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.limit.MILimitValidator;
import com.integrosys.cms.ui.manualinput.limit.MILimitValidatorRest;
import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;
import com.integrosys.cms.ui.manualinput.security.MISecValidator;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.manualinput.security.SecDetailForm;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * @author Bharat Waghela
 *
 */

public class FacilityServiceFacade {

	@Autowired
	private FacilityDetailsDTOMapper facilityDetailsDTOMapper;

	@Autowired
	private SecurityDetailsDTOMapper securityDetailsDTOMapper;

	private EndOfDaySyncMastersServiceImpl EndOfDaySyncMastersServiceImpl;

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
	public void setFacilityDetailsDTOMapper(FacilityDetailsDTOMapper facilityDetailsDTOMapper) {
		this.facilityDetailsDTOMapper = facilityDetailsDTOMapper;
	}

	public EndOfDaySyncMastersServiceImpl getEndOfDaySyncMastersServiceImpl() {
		return EndOfDaySyncMastersServiceImpl;
	}
	public void setEndOfDaySyncMastersServiceImpl(
			EndOfDaySyncMastersServiceImpl endOfDaySyncMastersServiceImpl) {
		EndOfDaySyncMastersServiceImpl = endOfDaySyncMastersServiceImpl;
	}
	@CLIMSWebServiceMethod
	public FacilityResponseDTO  addFacilityDetails(FacilityDetailRequestDTO  facilityRequestDTO) throws CMSValidationFault, CMSFault{
		try {
			//getEndOfDaySyncMastersServiceImpl().performEODSync();
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			ILimitProfile profile;
			List secList = new ArrayList();
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();	
			HashMap facMap = new HashMap();
			//Fetching CAM Details and set to the context 
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			
			if(facilityRequestDTO.getWsConsumer()==null || facilityRequestDTO.getWsConsumer().trim().isEmpty()){
				throw new CMSException("wsConsumer is mandatory");
			}

			FacilityDetailRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getRequestDTOWithActualValues(facilityRequestDTO);
			ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();

			HashMap map = new HashMap();
			if(cpsIdErrors.size()>0){
				map.put("1", cpsIdErrors);
				ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
			}

			String camId = "";
			if(facilityRequestDTO.getCamId()!=null && !facilityRequestDTO.getCamId().trim().isEmpty()){
				camId = facilityRequestDTO.getCamId().trim();
			}
			
			profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			//Fetching Party Details and set to the context 
			ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
			
			LmtDetailForm facilityForm = facilityDetailsDTOMapper.getFormFromDTO(facilityDetailRequestDTO,cust);
			ActionErrors facilityErrorList = MILimitValidator.validateMILimit(facilityForm, Locale.getDefault()); 
			//code for facility field validation
			if(facilityErrorList.size()>0){
				facMap.put("1", facilityErrorList);
				ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
			}
			
			if(facilityRequestDTO.getSecurityList()!=null && !facilityRequestDTO.getSecurityList().isEmpty() 
					&& facilityRequestDTO.getSecurityList().size()>0 ){
				HashMap secMap = new HashMap();
				for (int i = 0;i<facilityRequestDTO.getSecurityList().size();i++){
					ActionErrors securityErrorListCps = new ActionErrors();
					SecurityDetailRequestDTO securityDetailRequestDTO = securityDetailsDTOMapper.getRequestDTOWithActualValues(facilityRequestDTO.getSecurityList().get(i),"WS_FAC_CREATE");
					securityErrorListCps = securityDetailRequestDTO.getErrors();
					String cpsSecurityId = ""; 
					if(facilityRequestDTO.getSecurityList().get(i).getCpsSecurityId()!=null && !facilityRequestDTO.getSecurityList().get(i).getCpsSecurityId().trim().isEmpty()){
						cpsSecurityId = facilityRequestDTO.getSecurityList().get(i).getCpsSecurityId().trim();
					}
					secMap.put(cpsSecurityId, securityErrorListCps);
				}

				ValidationUtility.handleError(secMap, CLIMSWebService.SECURITY);
	
				HashMap securityMap = new HashMap();
				for (int i = 0;i<facilityRequestDTO.getSecurityList().size();i++){
					ActionErrors securityErrorList = new ActionErrors();
					SecDetailForm securityForm = securityDetailsDTOMapper.getFormFromDTO(facilityRequestDTO.getSecurityList().get(i));
					securityForm.setEvent("WS_FAC_CREATE");
					securityErrorList = MISecValidator.validateMISecurity(securityForm, Locale.getDefault());
					String cpsSecurityId = "";
					if(securityForm.getCpsSecurityId()!=null && !securityForm.getCpsSecurityId().trim().isEmpty()){
						cpsSecurityId = securityForm.getCpsSecurityId().trim();
					}
					
					securityMap.put(cpsSecurityId, securityErrorList);
				}
				//code for security field validation
				ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
			}
			
			FacilityResponseDTO facilityResponse = new FacilityResponseDTO();
			OBLimit newLimit = facilityDetailsDTOMapper.getActualFromDTO(facilityDetailRequestDTO);
			OBTrxContext context = null;
			context = mcUtil.setContextForMaker();
			
			//context = setContextAsPerUserId(context,"CPUADM_A");

			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			limitTrxValue.setStagingLimit(newLimit);

			/*OBCommonUser user = null;
			ITeam[] teams = null;
			ITeamMembership[] memberships;

			user = (OBCommonUser) getUserProxy().getUser("CPUADM_C");
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
*/
			
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			helper.setTrxLocation(context, limitTrxValue.getStagingLimit());
			limitTrxValue.setCustomerID(cust.getCustomerID());
			limitTrxValue.setCustomerName(cust.getCustomerName());
			limitTrxValue.setLegalID(profile.getLEReference());
			limitTrxValue.setLegalName(cust.getCustomerName());
//			limitTrxValue.setTeamID(teams[0].getTeamID());
			limitTrxValue.setLimitProfileID(Long.parseLong(camId));
			limitTrxValue.setLimitProfileReferenceNumber(profile.getBCAReference());

			MISecurityUIHelper secHelper = new MISecurityUIHelper();
			SBMISecProxy secProxy = secHelper.getSBMISecProxy();
			ICMSTrxResult res = null;
			ICMSTrxResult response = null;
			ICMSTrxResult secRes = null;
			
			if(facilityRequestDTO.getSecurityList()!=null && !facilityRequestDTO.getSecurityList().isEmpty()
					&& facilityRequestDTO.getSecurityList().size()>0){
				ICollateralAllocation[] newAlloc = new ICollateralAllocation[facilityRequestDTO.getSecurityList().size()];
				List<SecurityDetailRequestDTO> securityList= facilityRequestDTO.getSecurityList();
				for(int i = 0;i<securityList.size();i++){
					ICollateralTrxValue secTrxObj = new OBCollateralTrxValue();
					SecurityDetailRequestDTO securityDTO = (SecurityDetailRequestDTO)securityList.get(i);
					OBCollateral collateral = new OBCollateral();
					
					collateral.setCollateralLocation("IN");
					collateral.setCurrencyCode("INR");
					
					if(securityDTO.getSecurityPriority()!=null && !securityDTO.getSecurityPriority().trim().isEmpty()){
						collateral.setSecPriority(securityDTO.getSecurityPriority().trim());
					}else{
						collateral.setSecPriority("");
					}
					
					if(securityDTO.getCollateralCodeName()!=null && !securityDTO.getCollateralCodeName().trim().isEmpty()){
						collateral.setCollateralCode(securityDTO.getCollateralCodeName().trim());
					}else{
						collateral.setCollateralCode("");
					}
					
					collateral.setLmtSecurityCoverage("100");
					
					collateral.setStatus("ACTIVE");
					ICollateralDAO dao = CollateralDAOFactory.getDAO();
					if(securityDTO.getSecuritySubType()!=null && !securityDTO.getSecuritySubType().trim().isEmpty()){
						OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(securityDTO.getSecuritySubType().trim());
						collateral.setCollateralSubType(obSubType);
					}
					
					//Cam Online Format Begin					
					if(securityDTO.getPrimarySecurityAddress()!=null && !securityDTO.getPrimarySecurityAddress().trim().isEmpty()){
						collateral.setPrimarySecurityAddress(securityDTO.getPrimarySecurityAddress().trim());
					}else{
						collateral.setPrimarySecurityAddress("");
					}
					if(securityDTO.getSecondarySecurityAddress()!=null && !securityDTO.getSecondarySecurityAddress().trim().isEmpty()){
						collateral.setSecondarySecurityAddress(securityDTO.getSecondarySecurityAddress().trim());
					}else{
						collateral.setSecondarySecurityAddress("");
					}
					if(securityDTO.getSecurityValueAsPerCAM()!=null && !securityDTO.getSecurityValueAsPerCAM().trim().isEmpty()){
						collateral.setSecurityValueAsPerCAM(DateUtil.convertDate(securityDTO.getSecurityValueAsPerCAM()));
					}else{
						collateral.setSecurityValueAsPerCAM(null);
					}
					if(securityDTO.getSecurityMargin()!=null && !securityDTO.getSecurityMargin().trim().isEmpty()){
						collateral.setSecurityMargin(securityDTO.getSecurityMargin().trim());
					}else{
						collateral.setSecurityMargin("");
					}
					if(securityDTO.getChargePriority()!=null && !securityDTO.getChargePriority().trim().isEmpty()){
						collateral.setChargePriority(securityDTO.getChargePriority().trim());
					}else{
						collateral.setChargePriority("");
					}
					//Cam Online Format END
					ICollateral stagingCol = collateral;
					secHelper.setTrxLocation(context, stagingCol);
					secHelper.setPledgorLocation(stagingCol);
					secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
					secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
					ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
					ICollateral newcol = colVal.getCollateral();
					OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
					nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
					nextAlloc.setCollateral(newcol);
					nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
					nextAlloc.setLimitProfileID(Long.parseLong(camId));
					nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
					nextAlloc.setLmtSecurityCoverage("100");
					
					if(securityDTO.getCpsSecurityId()!=null && !securityDTO.getCpsSecurityId().trim().isEmpty()){
						nextAlloc.setCpsSecurityId(securityDTO.getCpsSecurityId().trim());
					}else{
						nextAlloc.setCpsSecurityId("");
					}
					
					newAlloc[i] = nextAlloc;
				}
				newLimit.setCollateralAllocations(newAlloc);
			}

	/*
	 * 26-FEB-2016 : When adding/updating facility through CAM online, sanctioned amount of existing facilities (Not created through CAMonline)
	 * should be zeroed - CR raised by bank : CLIMS validation for existing cases for facility/sanctioned amount 
	 * 
	 * Logic: Existing facility's sanctioned amount will be zeroed (i.e. CMS_REQ_SEC_COVERAGE column value) for CAM Id sent through facility request
	 * 
	 * 
	 * */
			
//			proxy.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "Before calling method...limitDAO.updateSanctionedLimitToZero()");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "After calling method...limitDAO.updateSanctionedLimitToZero()");
			
			res = proxy.createSubmitFacility(context, limitTrxValue,false);

			/*
			//maker create process
			res = proxy.createLimitTrx(context, limitTrxValue, false);
			limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
			//Setting user related information in the context
			context = setContextAsPerUserId(context,"CPUADM_C");
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			//Checker approve process
			response = proxy.checkerApproveLmtTrx(context, limitTrxValue);
			 */
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				facilityResponse.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				ICollateralAllocation[] collateralalloc= limitTrxValue.getLimit().getCollateralAllocations();
				
				if(facilityRequestDTO.getSecured()!=null && "Y".equalsIgnoreCase(facilityRequestDTO.getSecured())){
				if(collateralalloc!=null && collateralalloc.length>0){
					for(int i =0;i<collateralalloc.length;i++){
						SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
						ICollateralAllocation alloc = collateralalloc[i];
						if(alloc.getHostStatus()!=null && alloc.getHostStatus().equalsIgnoreCase("I") && alloc.getCpsSecurityId()!=null){
							dto.setCpsSecurityId(alloc.getCpsSecurityId());
							dto.setSecurityId(String.valueOf(alloc.getCollateral().getCollateralID()));
							secList.add(dto);
						}
					}
				}
				}
				else{
					SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
					dto.setCpsSecurityId(" ");
					dto.setSecurityId(" ");
					secList.add(dto);
				}
				facilityResponse.setSecurityResponseList(secList);
				facilityResponse.setResponseStatus("FACILITY_CREATED_SUCCESSFULLY");
			}
			return facilityResponse;
		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (CollateralException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CMSValidationFault e) {
			throw e;
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
	}


	@CLIMSWebServiceMethod
	public FacilityResponseDTO  updateFacilityDetails(FacilityDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		ICMSTrxResult response = null;
		List newAlloc = new ArrayList();
		HashMap facMap = new HashMap();
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		
		if(facilityRequest.getWsConsumer()==null || facilityRequest.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
		}
		
		FacilityDetailRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getRequestDTOWithActualValues(facilityRequest);
		ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();

		HashMap map = new HashMap();
		if(cpsIdErrors.size()>0){
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
		}
		
		String camId = "";
		try {
			limitProxy = LimitProxyFactory.getProxy();
			context = mcUtil.setContextForMaker();//setContextAsPerUserId(context,"CPUADM_A");
			if(facilityRequest.getCamId()!=null && !facilityRequest.getCamId().trim().isEmpty()){
				camId = facilityRequest.getCamId().trim();
				profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			}			
			context.setLimitProfile(profile);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		} 
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);

		context.setCustomer(cust);

		LmtDetailForm facilityForm = facilityDetailsDTOMapper.getFormFromDTO(facilityDetailRequestDTO,cust);
		facilityForm.setEvent("WS_FAC_EDIT");
		ActionErrors facilityErrorList = MILimitValidator.validateMILimit(facilityForm, Locale.getDefault()); 
		//code for facility field validation
		if(facilityErrorList.size()>0){
			facMap.put("1", facilityErrorList);
			ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
		}

		if(facilityRequest.getSecurityList()!=null && !facilityRequest.getSecurityList().isEmpty()
				&& facilityRequest.getSecurityList().size()>0){

			HashMap secMap = new HashMap();
			for (int i = 0;i<facilityRequest.getSecurityList().size();i++){
				ActionErrors securityErrorListCps = new ActionErrors();
				SecurityDetailRequestDTO securityDetailRequestDTO = securityDetailsDTOMapper.getRequestDTOWithActualValues(facilityRequest.getSecurityList().get(i),"WS_FAC_EDIT");
				securityErrorListCps = securityDetailRequestDTO.getErrors();
				String cpsSecurityId = "";
				if(facilityRequest.getSecurityList().get(i).getCpsSecurityId()!=null 
						&& !facilityRequest.getSecurityList().get(i).getCpsSecurityId().trim().isEmpty()){
					cpsSecurityId = facilityRequest.getSecurityList().get(i).getCpsSecurityId().trim();
				}
				secMap.put(cpsSecurityId, securityErrorListCps);
			}
			ValidationUtility.handleError(secMap, CLIMSWebService.SECURITY);
	
			HashMap securityMap = new HashMap();
			for (int i = 0;i<facilityRequest.getSecurityList().size();i++){
				ActionErrors securityErrorList = new ActionErrors();
				SecDetailForm securityForm = securityDetailsDTOMapper.getFormFromDTO(facilityRequest.getSecurityList().get(i));
				securityForm.setEvent("WS_FAC_EDIT");
				securityErrorList = MISecValidator.validateMISecurity(securityForm, Locale.getDefault());
				String cpsSecurityId = "";
				if(securityForm.getCpsSecurityId()!=null && !securityForm.getCpsSecurityId().trim().isEmpty()){
					cpsSecurityId = securityForm.getCpsSecurityId().trim();
				}
				securityMap.put(cpsSecurityId, securityErrorList);
			}
			//code for security field validation
			ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
		}

		FacilityResponseDTO obj = new FacilityResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			//need to add facility id generated by CLIMS in specification
			//lmtTrxObj = proxy.searchLimitByLmtId("20140924000001680");
			try{
				if(facilityRequest.getClimsFacilityId()!=null && !facilityRequest.getClimsFacilityId().trim().isEmpty()){
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			}
			catch(Exception e){
				throw new CMSValidationFault("climsFacilityId","Invalid climsFacilityId");
			}
			
	
			if(lmtTrxObj!=null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_DELETE"))
					||(lmtTrxObj.getStatus().equals("REJECTED"))
					||(lmtTrxObj.getStatus().equals("DRAFT"))
					||(lmtTrxObj.getStatus().equals("DELETED")))
			)
			{
				throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+lmtTrxObj.getStatus());
			}
			
			
			ILimit limit = (ILimit)lmtTrxObj.getLimit();
			
			/*if(facilityRequest.getEvent()!=null && facilityRequest.getEvent().equalsIgnoreCase("WS_FAC_EDIT")){
				if(!limit.getFacilityCode().equalsIgnoreCase(facilityForm.getFacilityCode()))
				{
					throw new CMSValidationFault("FacilityMasterId","FacilityMasterId is non editable field");
				}
			}*/
			
			if(facilityDetailRequestDTO.getRiskType()!=null && !facilityDetailRequestDTO.getRiskType().trim().isEmpty()){
				 limit.setRiskType(facilityDetailRequestDTO.getRiskType().trim());
			 }else{
				 limit.setRiskType("");
			 }
			 
			 if(facilityDetailRequestDTO.getTenor()!=null && !facilityDetailRequestDTO.getTenor().trim().isEmpty()){
				 limit.setTenor(Long.parseLong(facilityDetailRequestDTO.getTenor()));
			 }else {
				 limit.setTenor(null);
			 }
			 if(facilityDetailRequestDTO.getTenorUnit() != null && !facilityDetailRequestDTO.getTenorUnit().trim().isEmpty()) {
				 limit.setTenorUnit(facilityDetailRequestDTO.getTenorUnit().toUpperCase());
			 }else {
				 limit.setTenorUnit("");
			 }
			 if(facilityDetailRequestDTO.getTenorDesc()!=null && !facilityDetailRequestDTO.getTenorDesc().trim().isEmpty()){
				 limit.setTenorDesc(facilityDetailRequestDTO.getTenorDesc().trim());
			 }else{
				 limit.setTenorDesc("");
			 }
			 if(facilityDetailRequestDTO.getMargin()!=null && !facilityDetailRequestDTO.getMargin().trim().isEmpty()){
				 limit.setMargin(Double.parseDouble(facilityDetailRequestDTO.getMargin()));
			 }else {
				 limit.setMargin(null);
			 }
			 if(facilityDetailRequestDTO.getPutCallOption()!=null && !facilityDetailRequestDTO.getPutCallOption().trim().isEmpty()){
				 limit.setPutCallOption(facilityDetailRequestDTO.getPutCallOption().trim());
			 }else{
				 limit.setPutCallOption("");
			 }
			 if(facilityDetailRequestDTO.getOptionDate()!=null && !facilityDetailRequestDTO.getOptionDate().trim().isEmpty()){
				 limit.setOptionDate(DateUtil.convertDate(facilityDetailRequestDTO.getOptionDate()));
			 }else {
				 limit.setOptionDate(null);
			 }
			 
			 if(facilityDetailRequestDTO.getLoanAvailabilityDate()!=null && !facilityDetailRequestDTO.getLoanAvailabilityDate().trim().isEmpty()){
				 limit.setLoanAvailabilityDate(DateUtil.convertDate(facilityDetailRequestDTO.getLoanAvailabilityDate()));
			 }else {
				 limit.setLoanAvailabilityDate(null);
			 }
			 if(facilityDetailRequestDTO.getBankingArrangement()!=null && !facilityDetailRequestDTO.getBankingArrangement().trim().isEmpty()){
				 limit.setBankingArrangement(facilityDetailRequestDTO.getBankingArrangement());
			 }else {
				 limit.setBankingArrangement("");
			 }
			 if(facilityDetailRequestDTO.getClauseAsPerPolicy()!=null && !facilityDetailRequestDTO.getClauseAsPerPolicy().trim().isEmpty()){
				 limit.setClauseAsPerPolicy(facilityDetailRequestDTO.getClauseAsPerPolicy());
			 }else {
				 limit.setClauseAsPerPolicy("");
			 }
			if(facilityDetailRequestDTO.getFacilityCategoryId()!=null && !facilityDetailRequestDTO.getFacilityCategoryId().isEmpty()){
				limit.setFacilityCat(facilityDetailRequestDTO.getFacilityCategoryId().trim());
			}
			
			if(facilityDetailRequestDTO.getFacilityTypeId()!=null && !facilityDetailRequestDTO.getFacilityTypeId().isEmpty()){
				limit.setFacilityType(facilityDetailRequestDTO.getFacilityTypeId().trim());
			}
			
			if(limit!=null && facilityDetailRequestDTO.getSanctionedAmount()!=null && !facilityDetailRequestDTO.getSanctionedAmount().trim().isEmpty()){
				limit.setRequiredSecurityCoverage(facilityDetailRequestDTO.getSanctionedAmount());
			}
			if(limit!=null && facilityDetailRequestDTO.getGrade()!=null && !facilityDetailRequestDTO.getGrade().trim().isEmpty()){
				limit.setGrade(facilityDetailRequestDTO.getGrade());
			}
			
			if(facilityDetailRequestDTO.getSecured()!=null && !facilityDetailRequestDTO.getSecured().trim().isEmpty()){
				limit.setIsSecured(facilityDetailRequestDTO.getSecured().trim());
			}
			
			if(facilityDetailRequestDTO.getSubLimitFlag()!=null && !facilityDetailRequestDTO.getSubLimitFlag().trim().isEmpty()){
				limit.setLimitType(facilityDetailRequestDTO.getSubLimitFlag());
			}
			
			if(facilityDetailRequestDTO.getSubFacilityName()!=null && !facilityDetailRequestDTO.getSubFacilityName().trim().isEmpty()){
				limit.setSubFacilityName(facilityDetailRequestDTO.getSubFacilityName());
			}
			
			if(limit.getGuarantee() == null || limit.getGuarantee().isEmpty()){
				limit.setGuarantee("No");
			}
			
			if(facilityDetailRequestDTO.getCurrency()!=null && !facilityDetailRequestDTO.getCurrency().trim().isEmpty()){
				limit.setCurrencyCode(facilityDetailRequestDTO.getCurrency().trim());		
			}else{
				limit.setCurrencyCode("INR");		
			}			
			
			List currentCpsIds = new ArrayList();
			List newCpsIdsOnly = new ArrayList();
			List newCpsIds = null;
			
			if(facilityRequest.getSecurityList()!=null && !facilityRequest.getSecurityList().isEmpty()
					&& facilityRequest.getSecurityList().size()>0){
				
				newCpsIds = facilityRequest.getSecurityList();
				for(int i =0;i<facilityRequest.getSecurityList().size();i++){
					SecurityDetailRequestDTO dto = facilityRequest.getSecurityList().get(i);
					newCpsIdsOnly.add(dto.getCpsSecurityId()!=null?dto.getCpsSecurityId().trim():"");
				}
				
			}
			
			if(limit.getCollateralAllocations()!=null && limit.getCollateralAllocations().length > 0){

				ICollateralAllocation[] collateralalloc= limit.getCollateralAllocations();
				for(int i=0; i<collateralalloc.length;i++){
					ICollateralAllocation collateral = collateralalloc[i];
					if(collateral.getHostStatus()!=null && "I".equals(collateral.getHostStatus()) && collateral.getCpsSecurityId()!=null){
						currentCpsIds.add(collateral.getCpsSecurityId());
					}
				}

				if(currentCpsIds!=null && !currentCpsIds.isEmpty() && currentCpsIds.size()>0){
					for(int i =0;i<currentCpsIds.size();i++){
						if(newCpsIdsOnly.contains(currentCpsIds.get(i))){
							continue;
						}else{
							limit = deleteSecurityDetails(limit, (currentCpsIds.get(i)).toString());
							lmtTrxObj.setStagingLimit(limit);
						}
					}
				}
				
			}
			
			int j = 0;
			ILimit stagingLimit = lmtTrxObj.getLimit();
			ICollateralAllocation[] colAlloc =stagingLimit.getCollateralAllocations();
		
			if(newCpsIds!=null && !newCpsIds.isEmpty() && newCpsIds.size()>0){
				
				for(int i =0;i<newCpsIds.size();i++){
					SecurityDetailRequestDTO securityDto = (SecurityDetailRequestDTO)newCpsIds.get(i);

					if(currentCpsIds.contains(securityDto.getCpsSecurityId())){
						if(colAlloc!=null && colAlloc.length>0){
							for(int k=0;k<colAlloc.length;k++){
								ICollateralAllocation collateralAlloc = colAlloc[k];
								if(collateralAlloc.getCpsSecurityId()!=null && collateralAlloc.getCpsSecurityId().equals(securityDto.getCpsSecurityId())){
									collateralAlloc.setLmtSecurityCoverage("100");
									colAlloc[k]=collateralAlloc;
								}
							}
						}
					}else{
						ICMSTrxResult secRes = null;
						ICollateralTrxValue secTrxObj = new OBCollateralTrxValue();
						SecurityDetailRequestDTO securityDTO = (SecurityDetailRequestDTO)newCpsIds.get(i);
						OBCollateral collateral = new OBCollateral();
						collateral.setCollateralLocation("IN");
						collateral.setCurrencyCode("INR");
						collateral.setSecPriority(securityDTO.getSecurityPriority());
						collateral.setCollateralCode(securityDTO.getCollateralCodeName());
						collateral.setLmtSecurityCoverage("100");
						collateral.setStatus("ACTIVE");
						
						if(securityDTO.getPrimarySecurityAddress()!=null && !securityDTO.getPrimarySecurityAddress().trim().isEmpty()){
							collateral.setPrimarySecurityAddress(securityDTO.getPrimarySecurityAddress().trim());
						}else{
							collateral.setPrimarySecurityAddress("");
						}
						if(securityDTO.getSecondarySecurityAddress()!=null && !securityDTO.getSecondarySecurityAddress().trim().isEmpty()){
							collateral.setSecondarySecurityAddress(securityDTO.getSecondarySecurityAddress().trim());
						}else{
							collateral.setSecondarySecurityAddress("");
						}
						if(securityDTO.getSecurityValueAsPerCAM()!=null && !securityDTO.getSecurityValueAsPerCAM().trim().isEmpty()){
							collateral.setSecurityValueAsPerCAM(DateUtil.convertDate(securityDTO.getSecurityValueAsPerCAM()));
						}else{
							collateral.setSecurityValueAsPerCAM(null);
						}
						if(securityDTO.getSecurityMargin()!=null && !securityDTO.getSecurityMargin().trim().isEmpty()){
							collateral.setSecurityMargin(securityDTO.getSecurityMargin().trim());
						}else{
							collateral.setSecurityMargin("");
						}
						if(securityDTO.getChargePriority()!=null && !securityDTO.getChargePriority().trim().isEmpty()){
							collateral.setChargePriority(securityDTO.getChargePriority().trim());
						}else{
							collateral.setChargePriority("");
						}						
						
						ICollateralDAO dao = CollateralDAOFactory.getDAO();
						OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(securityDTO.getSecuritySubType());
						collateral.setCollateralSubType(obSubType);
						ICollateral stagingCol = collateral;
						MISecurityUIHelper secHelper = new MISecurityUIHelper();
						SBMISecProxy secProxy = secHelper.getSBMISecProxy();
						secHelper.setTrxLocation(context, stagingCol);
						secHelper.setPledgorLocation(stagingCol);
						secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
						secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
						ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
						ICollateral newcol = colVal.getCollateral();
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(camId));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
						nextAlloc.setCollateral(newcol);
						nextAlloc.setLmtSecurityCoverage("100");
						nextAlloc.setCpsSecurityId(securityDTO.getCpsSecurityId());
						newAlloc.add(nextAlloc);
					}
				}
			}	

			ICollateralAllocation[] cpsAlloc =new ICollateralAllocation[newAlloc.size()];
			for(int i = 0;i<newAlloc.size();i++){
				ICollateralAllocation secAlloc = (ICollateralAllocation)newAlloc.get(i);
				cpsAlloc[i]=secAlloc;
			}
			
			ICollateralAllocation[] finalcolAlloc = null;
			if(colAlloc!=null){
				finalcolAlloc = new ICollateralAllocation[cpsAlloc.length+colAlloc.length];
				for(int i = 0;i<colAlloc.length;i++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)colAlloc[i];
					finalcolAlloc[i]=secAlloc;
				}
				int a =0;
				for(int k = colAlloc.length;k<cpsAlloc.length+colAlloc.length;k++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)cpsAlloc[a++];
					finalcolAlloc[k]=secAlloc;
				}
			}else{
				finalcolAlloc = new ICollateralAllocation[cpsAlloc.length];
				for(int a = 0;a<cpsAlloc.length;a++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)cpsAlloc[a];
					finalcolAlloc[a]=secAlloc;
				}
			}
			stagingLimit.setCollateralAllocations(finalcolAlloc);
			lmtTrxObj.setStagingLimit(stagingLimit);
			
			/*
			 * 26-FEB-2016 : When adding/updating facility through CAM online, sanctioned amount of existing facilities (Not created through CAMonline)
			 * should be zeroed - CR raised by bank : CLIMS validation for existing cases for facility/sanctioned amount 
			 * 
			 * Logic: Existing facility's sanctioned amount will be zeroed (i.e. CMS_REQ_SEC_COVERAGE column value) for CAM Id sent through facility request
			 * 
			 * 
			 * */
					
//			proxy.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "Before calling method...limitDAO.updateSanctionedLimitToZero()");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "After calling method...limitDAO.updateSanctionedLimitToZero()");
			
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj,false);


			/*ICMSTrxResult res = proxy.makerUpdateLmtTrx(context, lmtTrxObj, false);
			lmtTrxObj = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
			//Setting user related information in the context
			context = setContextAsPerUserId(context,"CPUADM_C");
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			//Checker approve process
			response = proxy.checkerApproveLmtTrx(context, lmtTrxObj);*/
			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			List secList = new ArrayList();
			
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				obj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				ICollateralAllocation[] colalloc= limitTrxValue.getLimit().getCollateralAllocations();
				if(facilityRequest.getSecured()!=null && "Y".equalsIgnoreCase(facilityRequest.getSecured())){
				if(colalloc!=null && colalloc.length>0){
					for(int i =0;i<colalloc.length;i++){
						SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
						ICollateralAllocation alloc = colalloc[i];
						if(alloc.getHostStatus()!=null && alloc.getHostStatus().equalsIgnoreCase("I") && alloc.getCpsSecurityId()!=null){
							dto.setCpsSecurityId(alloc.getCpsSecurityId());
							dto.setSecurityId(String.valueOf(alloc.getCollateral().getCollateralID()));
							secList.add(dto);
						}
					}
				}
				}else{
					SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
					dto.setCpsSecurityId(" ");
					dto.setSecurityId(" ");
					secList.add(dto);
				}
				
				obj.setSecurityResponseList(secList);
				obj.setResponseStatus("FACILITY_EDITED_SUCCESSFULLY");
			}
			
		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CollateralException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CMSValidationFault e) {
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
		return obj;
	}


	@CLIMSWebServiceMethod
	public FacilityResponseDTO  deleteFacilityDetails(FacilityDeleteRequestDTO facilityDeleteRequestDTO) throws CMSValidationFault, CMSFault{
		FacilityResponseDTO obj = new FacilityResponseDTO();	
		try{
			OBTrxContext context = null;
			ILimitProfile profile = null;
			ILimitProxy limitProxy = null;
			ILimitTrxValue lmtTrxObj = null;
			ICMSTrxResult response = null;
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try{
				lmtTrxObj = proxy.searchLimitByLmtId(facilityDeleteRequestDTO.getFacilityID()!=null?facilityDeleteRequestDTO.getFacilityID().trim():"0L");
			}
			catch(Exception e){
				throw new CMSValidationFault("climsFacilityId","Invalid climsFacilityId");
			}
			context = setContextAsPerUserId(context,"CPUADM_A");
			limitProxy = LimitProxyFactory.getProxy();
			try{
			profile = limitProxy.getLimitProfile(Long.parseLong(facilityDeleteRequestDTO.getCamID()!=null?facilityDeleteRequestDTO.getCamID().trim():"0L"));
			}
			catch(Exception e){
				throw new CMSValidationFault("camId","Invalid camId");
			}
			context.setLimitProfile(profile);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
			context.setCustomer(cust);

			if(lmtTrxObj!=null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_DELETE"))
					||(lmtTrxObj.getStatus().equals("REJECTED"))||(lmtTrxObj.getStatus().equals("DRAFT"))))
			{
				throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+lmtTrxObj.getStatus());
			}
			else if(lmtTrxObj!=null && (lmtTrxObj.getStatus().equals("DELETED"))){
				
				throw new CMSValidationFault("facilityStatus","Facility is already Deleted in the system");
			}
			
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj ,true);
			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				obj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				obj.setResponseStatus("FACILITY_DELETED_SUCCESSFULLY");
			}
			
			/*ICMSTrxResult res = proxy.makerDeleteLmtTrx(context, lmtTrxObj);
		lmtTrxObj = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
		//Setting user related information in the context
		context = setContextAsPerUserId(context,"CPUADM_C");
		context.setLimitProfile(profile);
		context.setCustomer(cust);
		//Checker approve process
		response = proxy.checkerApproveLmtTrx(context, lmtTrxObj);*/

		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CMSValidationFault e) {
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
		return obj;

	}

	@CLIMSWebServiceMethod
	public FacilityReadResponseDTO  readFacilityDetails(FacilityReadRequestDTO facilityReadRequestDTO) throws CMSValidationFault, CMSFault{
		FacilityReadResponseDTO readResponseDTO = new FacilityReadResponseDTO();	
		try{
			ILimitTrxValue lmtTrxObj = null;
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try{
				lmtTrxObj = proxy.searchLimitByLmtId( (facilityReadRequestDTO.getFacilityID()!=null && !facilityReadRequestDTO.getFacilityID().trim().isEmpty())?facilityReadRequestDTO.getFacilityID().trim():"0L");
			}
			catch(Exception e){
				throw new CMSValidationFault("facilityID","Invalid facilityID");
			}
			
			if(facilityReadRequestDTO.getWsConsumer()==null || facilityReadRequestDTO.getWsConsumer().isEmpty()){
				throw new CMSException("wsConsumer is mandatory");
			}
			
			if(lmtTrxObj!=null){
				ILimit limit = (ILimit)lmtTrxObj.getLimit();
				ILimitSysXRef[] limitSysXRefs = limit.getLimitSysXRefs();
				double utilizedAmount = 0d;
				double devideBy = 1000000d;
				if(limitSysXRefs!=null && limitSysXRefs.length>0){
					for(ILimitSysXRef limitSysXRef : limitSysXRefs){
						ICustomerSysXRef customerSysXRef = limitSysXRef.getCustomerSysXRef();
						utilizedAmount += Double.parseDouble(customerSysXRef.getUtilizedAmount());
	//					System.out.println("utilizedAmount>>>"+new BigDecimal(utilizedAmount).toPlainString());
					}
				}
				readResponseDTO.setFacilityID(facilityReadRequestDTO.getFacilityID());
				if(utilizedAmount==0d){
					readResponseDTO.setUtilizedAmount("0.00");
				}else{
					readResponseDTO.setUtilizedAmount(new DecimalFormat("#0.00").format(utilizedAmount/devideBy));
				}
			}
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
		return readResponseDTO;
	}

	public ILimit deleteSecurityDetails(ILimit lmtObj, String  DelIndex)throws CMSValidationFault,CMSFault{

		List icollAllo = new ArrayList();
		ICollateralAllocation[] array = lmtObj.getCollateralAllocations();
		if (lmtObj.getCollateralAllocations() != null
				&& lmtObj.getCollateralAllocations().length > 0) {
			for (int i = 0; i < array.length; i++) {
				ICollateralAllocation obj = array[i];

				if(obj.getHostStatus()!=null && "I".equals(obj.getHostStatus())){
					String strColID = String.valueOf(obj.getCpsSecurityId());		
					ICollateralAllocation icollAlloObj = new OBCollateralAllocation();
					if(strColID!=null && !DelIndex.equals(strColID)){
						icollAlloObj = lmtObj.getCollateralAllocations()[i];
						icollAllo.add(icollAlloObj);
						break;
					}
				}
			}
		}
		lmtObj.setCollateralAllocations(null);
		ICollateralAllocation[] icollAlloFinal = new ICollateralAllocation[icollAllo.size()];
		if(icollAllo!=null && icollAllo.size()>0){
			for(int i=0; i<icollAllo.size() ; i++){
				icollAlloFinal[i]=(ICollateralAllocation) icollAllo.get(i);
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

	public OBTrxContext setContextAsPerUserId(OBTrxContext context,String userID) throws NumberFormatException, LimitException {

		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		try {


			user = (OBCommonUser) getUserProxy().getUser(userID);
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
			context = new OBTrxContext(user, teams[0]);

			//teams = getCmsTeamProxy().getTeamsByUserID(Long.parseLong("20110805000000060"));

			memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
			boolean makerCheckerSameUserChk = Boolean.valueOf(
					PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
			ITeamMembership membershipChk = null;
			if (makerCheckerSameUserChk) {
				membershipChk = validateMakerCheckerSelection(memberships, "");
			}
			else {
				membershipChk = validateTeamTypeMembershipRequested(memberships, null);
			}
			String teamMembershipIDChk =  String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
			context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk),  teams[0]));

			return context;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (BizStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RemoteException e) {
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
	
	//SCOD CR
	@CLIMSWebServiceMethod
	public FacilitySCODResponseDTO updateSCODFacilityDetails(FacilitySCODDetailRequestDTO facilityRequest)
			throws CMSValidationFault, CMSFault {
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		ICMSTrxResult response = null;
		List newAlloc = new ArrayList();
		HashMap facMap = new HashMap();
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");

		if (facilityRequest.getWsConsumer() == null || facilityRequest.getWsConsumer().trim().isEmpty()) {
			throw new CMSException("wsConsumer is mandatory");
		}

		FacilitySCODDetailRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getSCODRequestDTOWithActualValues(facilityRequest);
		ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();

		HashMap map = new HashMap();
		if (cpsIdErrors.size() > 0) {
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
		}

		String camId = "";
		try {
			limitProxy = LimitProxyFactory.getProxy();
			context = mcUtil.setContextForMaker();// setContextAsPerUserId(context,"CPUADM_A");
			if (facilityRequest.getCamId() != null && !facilityRequest.getCamId().trim().isEmpty()) {
				camId = facilityRequest.getCamId().trim();
				profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			}
			context.setLimitProfile(profile);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		}
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(), null);

		context.setCustomer(cust);

		LmtDetailForm facilityForm = facilityDetailsDTOMapper.getSCODFormFromDTO(facilityDetailRequestDTO, cust);
		if("Initial".equals(facilityDetailRequestDTO.getCamType())) {
			facilityForm.setEvent("WS_SCOD_FAC_EDIT_INITIAL");
		}else {
			facilityForm.setEvent("WS_SCOD_FAC_EDIT");
		}
		
		FacilitySCODResponseDTO obj = new FacilitySCODResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			try {
				if (facilityRequest.getClimsFacilityId() != null
						&& !facilityRequest.getClimsFacilityId().trim().isEmpty()) {
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			} catch (Exception e) {
				throw new CMSValidationFault("climsFacilityId", "Invalid climsFacilityId");
			}

			if (lmtTrxObj != null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_DELETE")) || (lmtTrxObj.getStatus().equals("REJECTED"))
					|| (lmtTrxObj.getStatus().equals("DRAFT")) || (lmtTrxObj.getStatus().equals("DELETED")))) {
				throw new CMSValidationFault("transactionStatus",
						"Unable to update due to invalid transaction Status :" + lmtTrxObj.getStatus());
			}

			ILimit limit = (ILimit) lmtTrxObj.getLimit();
			
			if(!"WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
			if(!"".equals(limit.getScodDate()) && limit.getScodDate() != null) {
			facilityForm.setScodDate(DateUtil.formatDate(Locale.getDefault(),limit.getScodDate()));
			}
			if(!"".equals(limit.getInfaProjectFlag()) && limit.getInfaProjectFlag() != null) {
				facilityForm.setInfaProjectFlag(limit.getInfaProjectFlag());
				}
			if(!"".equals(limit.getProjectFinance()) && limit.getProjectFinance() != null) {
				facilityForm.setProjectFinance(limit.getProjectFinance());
				}
			if(!"".equals(limit.getProjectLoan()) && limit.getProjectLoan() != null) {
				facilityForm.setProjectLoan(limit.getProjectLoan());
				}
			if(!"".equals(limit.getRemarksSCOD()) && limit.getRemarksSCOD() != null) {
				facilityForm.setRemarksSCOD(limit.getRemarksSCOD());
				}
			
			if(!"".equals(limit.getExstAssetClass()) && limit.getExstAssetClass() != null) {
				facilityForm.setExstAssetClass(limit.getExstAssetClass());
				}
			
			if(!"".equals(limit.getExstAssClassDate()) && limit.getExstAssClassDate() != null) {
				facilityForm.setExstAssClassDate(DateUtil.formatDate(Locale.getDefault(),limit.getExstAssClassDate()));
				}
			}
			
			if("WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
				if(limit.getScodDate() != null && limit.getProjectFinance()!=null && limit.getInfaProjectFlag() != null && limit.getProjectLoan() != null && limit.getRemarksSCOD() != null && limit.getExstAssetClass() != null) {
					throw new CMSException("Initial CAM already proceed for this facility.");
				}
			}
			
			if(!"WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
				if(limit.getScodDate() == null && limit.getProjectFinance()==null && limit.getInfaProjectFlag() == null && limit.getProjectLoan() == null ) {
					throw new CMSException("Please proceed Initial CAM for this facility.");
				}
			}
		ActionErrors facilityErrorList = MILimitValidator.validateMILimit(facilityForm, Locale.getDefault() );
		// code for facility field validation
		if (facilityErrorList.size() > 0) {
			facMap.put("1", facilityErrorList);
			ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
		}
		DefaultLogger.info(this, "Error Map: "+facMap);
		/*FacilitySCODResponseDTO obj = new FacilitySCODResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			try {
				if (facilityRequest.getClimsFacilityId() != null
						&& !facilityRequest.getClimsFacilityId().trim().isEmpty()) {
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			} catch (Exception e) {
				throw new CMSValidationFault("climsFacilityId", "Invalid climsFacilityId");
			}

			if (lmtTrxObj != null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_DELETE")) || (lmtTrxObj.getStatus().equals("REJECTED"))
					|| (lmtTrxObj.getStatus().equals("DRAFT")) || (lmtTrxObj.getStatus().equals("DELETED")))) {
				throw new CMSValidationFault("transactionStatus",
						"Unable to update due to invalid transaction Status :" + lmtTrxObj.getStatus());
			}*/

			Date actualDate = (lmtTrxObj.getLimit()!=null) ? lmtTrxObj.getLimit().getScodDate() : null;
			//ILimit limit = (ILimit) lmtTrxObj.getLimit();

			//Initial
			if (facilityDetailRequestDTO.getProjectFinance() != null
					&& !facilityDetailRequestDTO.getProjectFinance().isEmpty()) {
				limit.setProjectFinance(facilityDetailRequestDTO.getProjectFinance().trim());
			}

			if (facilityDetailRequestDTO.getProjectLoan() != null
					&& !facilityDetailRequestDTO.getProjectLoan().isEmpty()) {
				limit.setProjectLoan(facilityDetailRequestDTO.getProjectLoan().trim());
			}

			if (limit != null && facilityDetailRequestDTO.getInfraFlag() != null
					&& !facilityDetailRequestDTO.getInfraFlag().trim().isEmpty()) {
				limit.setInfaProjectFlag(facilityDetailRequestDTO.getInfraFlag());
			}
			
			try {
				if (facilityDetailRequestDTO.getScod()!=null 
						&& !facilityDetailRequestDTO.getScod().trim().isEmpty()) {
					limit.setScodDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getScod().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}

			if (facilityDetailRequestDTO.getScodRemark() != null
					&& !facilityDetailRequestDTO.getScodRemark().trim().isEmpty()) {
				limit.setRemarksSCOD(facilityDetailRequestDTO.getScodRemark());
			}

			if (facilityDetailRequestDTO.getExeAssetClass() != null
					&& !facilityDetailRequestDTO.getExeAssetClass().trim().isEmpty()) {
				limit.setExstAssetClass(facilityDetailRequestDTO.getExeAssetClass().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getExeAssetClassDate()!=null 
						&& !facilityDetailRequestDTO.getExeAssetClassDate().trim().isEmpty()) {
					limit.setExstAssClassDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getExeAssetClassDate().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Interim 
			
			if (facilityDetailRequestDTO.getLimitReleaseFlg() != null
					&& !facilityDetailRequestDTO.getLimitReleaseFlg().isEmpty()) {
				limit.setWhlmreupSCOD(facilityDetailRequestDTO.getLimitReleaseFlg().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSched() != null
					&& !facilityDetailRequestDTO.getRepayChngSched().isEmpty()) {
				limit.setChngInRepaySchedule(facilityDetailRequestDTO.getRepayChngSched().trim());
			}
			
			if (facilityDetailRequestDTO.getRevAssetClass()!= null
					&& !facilityDetailRequestDTO.getRevAssetClass().isEmpty()) {
				limit.setRevisedAssetClass(facilityDetailRequestDTO.getRevAssetClass().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDt()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDt().trim().isEmpty()) {
					limit.setRevsdAssClassDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDt().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			try {
				if (facilityDetailRequestDTO.getAcod()!=null 
						&& !facilityDetailRequestDTO.getAcod().trim().isEmpty()) {
					limit.setActualCommOpsDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getAcod().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 1
			
			if (facilityDetailRequestDTO.getDelayFlagL1() != null
					&& !facilityDetailRequestDTO.getDelayFlagL1().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getInterestFlag() != null
					&& !facilityDetailRequestDTO.getInterestFlag().isEmpty()) {
				limit.setPrincipalInterestSchFlag(facilityDetailRequestDTO.getInterestFlag().trim());
			}
			
			if (facilityDetailRequestDTO.getPriorReqFlag() != null
					&& !facilityDetailRequestDTO.getPriorReqFlag().isEmpty()) {
				limit.setRecvPriorOfSCOD(facilityDetailRequestDTO.getPriorReqFlag().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL1() != null
					&& !facilityDetailRequestDTO.getDefReasonL1().isEmpty()) {
				limit.setReasonLevelOneDelay(facilityDetailRequestDTO.getDefReasonL1().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL1() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL1().isEmpty()) {
				limit.setChngInRepaySchedule(facilityDetailRequestDTO.getRepayChngSchedL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL1()!=null 
						&& !facilityDetailRequestDTO.getEscodL1().trim().isEmpty()) {
					limit.setEscodLevelOneDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL1().isEmpty()) {
				limit.setPromotersCapRunFlag(facilityDetailRequestDTO.getOwnershipQ1FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL1().isEmpty()) {
				limit.setPromotersHoldEquityFlag(facilityDetailRequestDTO.getOwnershipQ2FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL1().isEmpty()) {
				limit.setHasProjectViabReAssFlag(facilityDetailRequestDTO.getOwnershipQ3FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL1().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlag(facilityDetailRequestDTO.getScopeQ1FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL1().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlag(facilityDetailRequestDTO.getScopeQ2FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL1().isEmpty()) {
				limit.setProjectViabReassesedFlag(facilityDetailRequestDTO.getScopeQ3FlagL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL1()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL1().trim().isEmpty()) {
					limit.setRevsedESCODStpDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL1(limit.getExstAssetClass().trim());
			}
			
			//try {
				if (limit.getExstAssClassDate()!=null ) {
					limit.setExstAssClassDateL1(limit.getExstAssClassDate());
				}
				/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL1()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL1().isEmpty()) {
				limit.setRevisedAssetClassL1(facilityDetailRequestDTO.getRevAssetClassL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL1()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL1().trim().isEmpty()) {
					limit.setRevsdAssClassDateL1(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 2
			
			if (facilityDetailRequestDTO.getDelayFlagL2() != null
					&& !facilityDetailRequestDTO.getDelayFlagL2().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL2() != null
					&& !facilityDetailRequestDTO.getDefReasonL2().isEmpty()) {
				limit.setReasonLevelTwoDelay(facilityDetailRequestDTO.getDefReasonL2().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL2() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL2().isEmpty()) {
				limit.setChngInRepayScheduleL2(facilityDetailRequestDTO.getRepayChngSchedL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL2()!=null 
						&& !facilityDetailRequestDTO.getEscodL2().trim().isEmpty()) {
					limit.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ1L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ1L2().isEmpty()) {
				limit.setLegalOrArbitrationLevel2Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ1L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ2L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ2L2().isEmpty()) {
				limit.setReasonBeyondPromoterLevel2Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ2L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ3L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ3L2().isEmpty()) {
				limit.setChngOfOwnPrjFlagInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ3L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ4L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ4L2().isEmpty()) {
				limit.setChngOfProjScopeInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ4L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ5L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ5L2().isEmpty()) {
				limit.setChngOfOwnPrjFlagNonInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ5L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ6L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ6L2().isEmpty()) {
				limit.setChngOfProjScopeNonInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ6L2().trim());
			}
			
			if (facilityDetailRequestDTO.getLegalDetailL2() != null
					&& !facilityDetailRequestDTO.getLegalDetailL2().isEmpty()) {
				limit.setLeaglOrArbiProceed(facilityDetailRequestDTO.getLegalDetailL2().trim());
			}
			
			if (facilityDetailRequestDTO.getBeyondControlL2() != null
					&& !facilityDetailRequestDTO.getBeyondControlL2().isEmpty()) {
				limit.setDetailsRsnByndPro(facilityDetailRequestDTO.getBeyondControlL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL2().isEmpty()) {
				limit.setPromotersCapRunFlagL2(facilityDetailRequestDTO.getOwnershipQ1FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL2().isEmpty()) {
				limit.setPromotersHoldEquityFlagL2(facilityDetailRequestDTO.getOwnershipQ2FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL2().isEmpty()) {
				limit.setHasProjectViabReAssFlagL2(facilityDetailRequestDTO.getOwnershipQ3FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL2().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlagL2(facilityDetailRequestDTO.getScopeQ1FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL2().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlagL2(facilityDetailRequestDTO.getScopeQ2FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL2().isEmpty()) {
				limit.setProjectViabReassesedFlagL2(facilityDetailRequestDTO.getScopeQ3FlagL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL2()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL2().trim().isEmpty()) {
					limit.setRevsedESCODStpDateL2(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL2(limit.getExstAssetClass().trim());
			}
			
			//try {
				if (limit.getExstAssClassDate()!=null) {
					limit.setExstAssClassDateL2(limit.getExstAssClassDate());
				}
			/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL2()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL2().isEmpty()) {
				limit.setRevisedAssetClass_L2(facilityDetailRequestDTO.getRevAssetClassL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL2()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL2().trim().isEmpty()) {
					limit.setRevsdAssClassDate_L2(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 3
			
			if (facilityDetailRequestDTO.getDelayFlagL3() != null
					&& !facilityDetailRequestDTO.getDelayFlagL3().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL3() != null
					&& !facilityDetailRequestDTO.getDefReasonL3().isEmpty()) {
				limit.setReasonLevelThreeDelay(facilityDetailRequestDTO.getDefReasonL3().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL3() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL3().isEmpty()) {
				limit.setChngInRepayScheduleL3(facilityDetailRequestDTO.getRepayChngSchedL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL3()!=null 
						&& !facilityDetailRequestDTO.getEscodL3().trim().isEmpty()) {
					limit.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ1L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ1L3().isEmpty()) {
				limit.setLegalOrArbitrationLevel3Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ1L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ2L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ2L3().isEmpty()) {
				limit.setReasonBeyondPromoterLevel3Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ2L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ3L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ3L3().isEmpty()) {
				limit.setChngOfOwnPrjFlagInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ3L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ4L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ4L3().isEmpty()) {
				limit.setChngOfProjScopeInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ4L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ5L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ5L3().isEmpty()) {
				limit.setChngOfOwnPrjFlagNonInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ5L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ6L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ6L3().isEmpty()) {
				limit.setChngOfProjScopeNonInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ6L3().trim());
			}
			
			if (facilityDetailRequestDTO.getLegalDetailL3() != null
					&& !facilityDetailRequestDTO.getLegalDetailL3().isEmpty()) {
				limit.setLeaglOrArbiProceedLevel3(facilityDetailRequestDTO.getLegalDetailL3().trim());
			}
			
			if (facilityDetailRequestDTO.getBeyondControlL3() != null
					&& !facilityDetailRequestDTO.getBeyondControlL3().isEmpty()) {
				limit.setDetailsRsnByndProLevel3(facilityDetailRequestDTO.getBeyondControlL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL3().isEmpty()) {
				limit.setPromotersCapRunFlagL3(facilityDetailRequestDTO.getOwnershipQ1FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL3().isEmpty()) {
				limit.setPromotersHoldEquityFlagL3(facilityDetailRequestDTO.getOwnershipQ2FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL3().isEmpty()) {
				limit.setHasProjectViabReAssFlagL3(facilityDetailRequestDTO.getOwnershipQ3FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL3().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlagL3(facilityDetailRequestDTO.getScopeQ1FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL3().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlagL3(facilityDetailRequestDTO.getScopeQ2FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL3().isEmpty()) {
				limit.setProjectViabReassesedFlagL3(facilityDetailRequestDTO.getScopeQ3FlagL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL3()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL3().trim().isEmpty()) {
					limit.setRevsedESCODStpDateL3(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL3(limit.getExstAssetClass().trim());
			}
			
			/*try {*/
				if (limit.getExstAssClassDate()!=null) {
					limit.setExstAssClassDateL3(limit.getExstAssClassDate());
				}
			/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL3()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL3().isEmpty()) {
				limit.setRevisedAssetClass_L3(facilityDetailRequestDTO.getRevAssetClassL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL3()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL3().trim().isEmpty()) {
					limit.setRevsdAssClassDate_L3(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			lmtTrxObj.setStagingLimit(limit);
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj, false);
			
			//Start SCOD Deferral
			if(lmtTrxObj.getLimit()!=null && lmtTrxObj.getLimit().getFacilityName()!=null) {
				String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
				DefaultLogger.info(this, "API... scodLineNoList......."+scodLineNoList);
				Date scodStage = lmtTrxObj.getStagingLimit().getScodDate();
				boolean isEquals=true;
				try {
					SimpleDateFormat sdformat = new SimpleDateFormat("dd/MMM/yyyy");
					if(lmtTrxObj.getLimit()!=null && lmtTrxObj.getLimit().getScodDate()!=null) {
						Date actual = sdformat.parse(sdformat.format(actualDate));
						Date stage = sdformat.parse(sdformat.format(lmtTrxObj.getStagingLimit().getScodDate()));	
						if(!(stage.compareTo(actual) > 0)) {
							isEquals=false;
						}
					}
				}catch(Exception ex) {
					DefaultLogger.error(this, "Exception ", ex);
				}
				DefaultLogger.info(this, "API... isEquals......."+isEquals);
			
				if(scodLineNoList != null) {
				if(!(lmtTrxObj.getStagingLimit().getLineNo()==null || lmtTrxObj.getStagingLimit().getLineNo().isEmpty()) 
					&& (scodLineNoList.contains(lmtTrxObj.getStagingLimit().getLineNo()))
					&& isEquals) {
					DefaultLogger.debug(this, "API...Before calling generateDeferralsForSCOD.......");
					//ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal1.setTime(scodStage);
					cal2.setTime(new Date());
				
					if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
						if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
								&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=2 && "Initial".equals(profile.getCamType())) {
							GenerateDeferralsForSCOD.generateDeferralsForSCOD(context, lmtTrxObj, profile, new Date(), res);
						} else if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
								&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=1 
								&& ("Interim".equals(profile.getCamType()) || "Annual".equals(profile.getCamType()))) {
							GenerateDeferralsForSCOD.generateDeferralsForSCOD(context, lmtTrxObj, profile, new Date(), res);
						} 
					} 
					DefaultLogger.debug(this, "API... After calling generateDeferralsForSCOD.......");
				}
				}
			}
			//End SCOD Deferral
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateFlagsFlagForLMTSTP(lmtTrxObj);
			obj.setResponseStatus("FACILITY_UPDATED_SUCCESSFULLY");
			obj.setTransactionID(lmtTrxObj.getTransactionID());
			obj.setFacilityId(String.valueOf(limit.getLimitID()));

		} catch (LimitException e) {
			DefaultLogger.error(this, "LimitException ", e);
			throw new CMSException(e.getMessage(), e);
		} catch (RemoteException e) {
			DefaultLogger.error(this, "RemoteException ", e);
			throw new CMSException(e.getMessage(), e);
		} catch (CMSValidationFault e) {
			DefaultLogger.error(this, "CMSValidationFault ", e);
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		} catch (NumberFormatException e) {
			DefaultLogger.error(this, "NumberFormatException ", e);
			throw new CMSException(e.getMessage(), e);
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception ", e);
			throw new CMSException(e.getMessage(), e);
		}
		return obj;
	}

	@CLIMSWebServiceMethod
	public FacilityResponseDTO  updateNewFacilityDetails(FacilityNewFieldsDetailRequestDTO  facilityRequest) throws CMSValidationFault, CMSFault{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		List newAlloc = new ArrayList();
		HashMap facMap = new HashMap();
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		
		if(facilityRequest.getWsConsumer()==null || facilityRequest.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
		}
		
		FacilityNewFieldsDetailRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getNewFieldsRequestDTOWithActualValues(facilityRequest);
		ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();

		HashMap map = new HashMap();
		if(cpsIdErrors.size()>0){
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
		}
		
		String camId = "";
		try {
			limitProxy = LimitProxyFactory.getProxy();
			context = mcUtil.setContextForMaker();//setContextAsPerUserId(context,"CPUADM_A");
			if(facilityRequest.getCamId()!=null && !facilityRequest.getCamId().trim().isEmpty()){
				camId = facilityRequest.getCamId().trim();
				profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			}			
			context.setLimitProfile(profile);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		} 
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);

		context.setCustomer(cust);

		LmtDetailForm facilityForm = facilityDetailsDTOMapper.getNewFieldsFormFromDTO(facilityDetailRequestDTO,cust);
		facilityForm.setEvent("WS_NEW_FAC_EDIT");
		ActionErrors facilityErrorList = MILimitValidator.validateMILimit(facilityForm, Locale.getDefault()); 
		//code for facility field validation
		if(facilityErrorList.size()>0){
			facMap.put("1", facilityErrorList);
			ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
		}

		if(facilityRequest.getSecurityList()!=null && !facilityRequest.getSecurityList().isEmpty()
				&& facilityRequest.getSecurityList().size()>0){

			HashMap secMap = new HashMap();
			for (int i = 0;i<facilityRequest.getSecurityList().size();i++){
				ActionErrors securityErrorListCps = new ActionErrors();
				SecurityNewFieldsDetailRequestDTO securityDetailRequestDTO = securityDetailsDTOMapper.getNewFieldsRequestDTOWithActualValues(facilityRequest.getSecurityList().get(i),"WS_NEW_FAC_EDIT");
				securityErrorListCps = securityDetailRequestDTO.getErrors();
				String cpsSecurityId = "";
				if(facilityRequest.getSecurityList().get(i).getCpsSecurityId()!=null 
						&& !facilityRequest.getSecurityList().get(i).getCpsSecurityId().trim().isEmpty()){
					cpsSecurityId = facilityRequest.getSecurityList().get(i).getCpsSecurityId().trim();
				}
				secMap.put(cpsSecurityId, securityErrorListCps);
			}
			ValidationUtility.handleError(secMap, CLIMSWebService.SECURITY);
	
			HashMap securityMap = new HashMap();
			for (int i = 0;i<facilityRequest.getSecurityList().size();i++){
				ActionErrors securityErrorList = new ActionErrors();
				SecDetailForm securityForm = securityDetailsDTOMapper.getNewFieldsFormFromDTO(facilityRequest.getSecurityList().get(i));
				securityForm.setEvent("WS_NEW_FAC_EDIT");
				securityErrorList = MISecValidator.validateMISecurity(securityForm, Locale.getDefault());
				String cpsSecurityId = "";
				if(securityForm.getCpsSecurityId()!=null && !securityForm.getCpsSecurityId().trim().isEmpty()){
					cpsSecurityId = securityForm.getCpsSecurityId().trim();
				}
				securityMap.put(cpsSecurityId, securityErrorList);
			}
			//code for security field validation
			ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
		}

		FacilityResponseDTO obj = new FacilityResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			//need to add facility id generated by CLIMS in specification
			//lmtTrxObj = proxy.searchLimitByLmtId("20140924000001680");
			try{
				if(facilityRequest.getClimsFacilityId()!=null && !facilityRequest.getClimsFacilityId().trim().isEmpty()){
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			}
			catch(Exception e){
				throw new CMSValidationFault("climsFacilityId","Invalid climsFacilityId");
			}
			
			if(lmtTrxObj!=null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_DELETE"))
					||(lmtTrxObj.getStatus().equals("REJECTED"))
					||(lmtTrxObj.getStatus().equals("DRAFT"))
					||(lmtTrxObj.getStatus().equals("DELETED")))
			)
			{
				throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+lmtTrxObj.getStatus());
			}
			
			
			ILimit limit = (ILimit)lmtTrxObj.getLimit();
			
			
			//Cam Online Format Begin
			if(facilityDetailRequestDTO.getRiskType()!=null && !facilityDetailRequestDTO.getRiskType().trim().isEmpty()){
				 limit.setRiskType(facilityDetailRequestDTO.getRiskType().trim());
			 }else{
				 limit.setRiskType("");
			 }
			 
			 if(facilityDetailRequestDTO.getTenor()!=null && !facilityDetailRequestDTO.getTenor().trim().isEmpty()){
				 limit.setTenor(Long.parseLong(facilityDetailRequestDTO.getTenor()));
			 }else {
				 limit.setTenor(null);
			 }
			 if(facilityDetailRequestDTO.getTenorUnit() != null && !facilityDetailRequestDTO.getTenorUnit().trim().isEmpty()) {
				 limit.setTenorUnit(facilityDetailRequestDTO.getTenorUnit().toUpperCase());
			 }else {
				 limit.setTenorUnit("");
			 }
			 if(facilityDetailRequestDTO.getTenorDesc()!=null && !facilityDetailRequestDTO.getTenorDesc().trim().isEmpty()){
				 limit.setTenorDesc(facilityDetailRequestDTO.getTenorDesc().trim());
			 }else{
				 limit.setTenorDesc("");
			 }
			 if(facilityDetailRequestDTO.getMargin()!=null && !facilityDetailRequestDTO.getMargin().trim().isEmpty()){
				 limit.setMargin(Double.parseDouble(facilityDetailRequestDTO.getMargin()));
			 }else {
				 limit.setMargin(null);
			 }
			 if(facilityDetailRequestDTO.getPutCallOption()!=null && !facilityDetailRequestDTO.getPutCallOption().trim().isEmpty()){
				 limit.setPutCallOption(facilityDetailRequestDTO.getPutCallOption().trim());
			 }else{
				 limit.setPutCallOption("");
			 }
			 if(facilityDetailRequestDTO.getOptionDate()!=null && !facilityDetailRequestDTO.getOptionDate().trim().isEmpty()){
				 limit.setOptionDate(DateUtil.convertDate(facilityDetailRequestDTO.getOptionDate()));
			 }else {
				 limit.setOptionDate(null);
			 }
			 
			 if(facilityDetailRequestDTO.getLoanAvailabilityDate()!=null && !facilityDetailRequestDTO.getLoanAvailabilityDate().trim().isEmpty()){
				 limit.setLoanAvailabilityDate(DateUtil.convertDate(facilityDetailRequestDTO.getLoanAvailabilityDate()));
			 }else {
				 limit.setLoanAvailabilityDate(null);
			 }
			 if(facilityDetailRequestDTO.getBankingArrangement()!=null && !facilityDetailRequestDTO.getBankingArrangement().trim().isEmpty()){
				 limit.setBankingArrangement(facilityDetailRequestDTO.getBankingArrangement());
			 }else {
				 limit.setBankingArrangement("");
			 }
			 if(facilityDetailRequestDTO.getClauseAsPerPolicy()!=null && !facilityDetailRequestDTO.getClauseAsPerPolicy().trim().isEmpty()){
				 limit.setClauseAsPerPolicy(facilityDetailRequestDTO.getClauseAsPerPolicy());
			 }else {
				 limit.setClauseAsPerPolicy("");
			 }
			//Cam Online Format END
			
			List currentCpsIds = new ArrayList();
			List newCpsIdsOnly = new ArrayList();
			List newCpsIds = null;
			
			if(facilityRequest.getSecurityList()!=null && !facilityRequest.getSecurityList().isEmpty()
					&& facilityRequest.getSecurityList().size()>0){
				
				newCpsIds = facilityRequest.getSecurityList();
				for(int i =0;i<facilityRequest.getSecurityList().size();i++){
					SecurityNewFieldsDetailRequestDTO dto = facilityRequest.getSecurityList().get(i);
					newCpsIdsOnly.add(dto.getCpsSecurityId()!=null?dto.getCpsSecurityId().trim():"");
				}
				
			}
			
			if(limit.getCollateralAllocations()!=null && limit.getCollateralAllocations().length > 0){

				ICollateralAllocation[] collateralalloc= limit.getCollateralAllocations();
				for(int i=0; i<collateralalloc.length;i++){
					ICollateralAllocation collateral = collateralalloc[i];
					if(collateral.getHostStatus()!=null && "I".equals(collateral.getHostStatus()) && collateral.getCpsSecurityId()!=null){
						currentCpsIds.add(collateral.getCpsSecurityId());
					}
				}

				if(currentCpsIds!=null && !currentCpsIds.isEmpty() && currentCpsIds.size()>0){
					for(int i =0;i<currentCpsIds.size();i++){
						if(newCpsIdsOnly.contains(currentCpsIds.get(i))){
							continue;
						}else{
							limit = deleteSecurityDetails(limit, (currentCpsIds.get(i)).toString());
							lmtTrxObj.setStagingLimit(limit);
						}
					}
				}
				
			}
			
			int j = 0;
			ILimit stagingLimit = lmtTrxObj.getLimit();
			ICollateralAllocation[] colAlloc =stagingLimit.getCollateralAllocations();
		
			if(newCpsIds!=null && !newCpsIds.isEmpty() && newCpsIds.size()>0){
				
				for(int i =0;i<newCpsIds.size();i++){
					SecurityNewFieldsDetailRequestDTO securityDto = (SecurityNewFieldsDetailRequestDTO)newCpsIds.get(i);

					if(currentCpsIds.contains(securityDto.getCpsSecurityId())){
						if(colAlloc!=null && colAlloc.length>0){
							for(int k=0;k<colAlloc.length;k++){
								ICollateralAllocation collateralAlloc = colAlloc[k];
								if(collateralAlloc.getCpsSecurityId()!=null && collateralAlloc.getCpsSecurityId().equals(securityDto.getCpsSecurityId())){
									collateralAlloc.setLmtSecurityCoverage("100");
									colAlloc[k]=collateralAlloc;
								}
							}
						}
					}else{
						ICMSTrxResult secRes = null;
						ICollateralTrxValue secTrxObj = new OBCollateralTrxValue();
						SecurityNewFieldsDetailRequestDTO securityDTO = (SecurityNewFieldsDetailRequestDTO)newCpsIds.get(i);
						OBCollateral collateral = new OBCollateral();
						collateral.setCollateralLocation("IN");
						collateral.setCurrencyCode("INR");
						collateral.setLmtSecurityCoverage("100");
						collateral.setStatus("ACTIVE");
						
						if(securityDTO.getPrimarySecurityAddress()!=null && !securityDTO.getPrimarySecurityAddress().trim().isEmpty()){
							collateral.setPrimarySecurityAddress(securityDTO.getPrimarySecurityAddress().trim());
						}else{
							collateral.setPrimarySecurityAddress("");
						}
						if(securityDTO.getSecondarySecurityAddress()!=null && !securityDTO.getSecondarySecurityAddress().trim().isEmpty()){
							collateral.setSecondarySecurityAddress(securityDTO.getSecondarySecurityAddress().trim());
						}else{
							collateral.setSecondarySecurityAddress("");
						}
						if(securityDTO.getSecurityValueAsPerCAM()!=null && !securityDTO.getSecurityValueAsPerCAM().trim().isEmpty()){
							collateral.setSecurityValueAsPerCAM(DateUtil.convertDate(securityDTO.getSecurityValueAsPerCAM()));
						}else{
							collateral.setSecurityValueAsPerCAM(null);
						}
						if(securityDTO.getSecurityMargin()!=null && !securityDTO.getSecurityMargin().trim().isEmpty()){
							collateral.setSecurityMargin(securityDTO.getSecurityMargin().trim());
						}else{
							collateral.setSecurityMargin("");
						}
						if(securityDTO.getChargePriority()!=null && !securityDTO.getChargePriority().trim().isEmpty()){
							collateral.setChargePriority(securityDTO.getChargePriority().trim());
						}else{
							collateral.setChargePriority("");
						}

						ICollateral stagingCol = collateral;
						MISecurityUIHelper secHelper = new MISecurityUIHelper();
						SBMISecProxy secProxy = secHelper.getSBMISecProxy();
						secHelper.setTrxLocation(context, stagingCol);
						secHelper.setPledgorLocation(stagingCol);
						secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
						secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
						ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
						ICollateral newcol = colVal.getCollateral();
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(camId));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
						nextAlloc.setCollateral(newcol);
						nextAlloc.setLmtSecurityCoverage("100");
						nextAlloc.setCpsSecurityId(securityDTO.getCpsSecurityId());
						newAlloc.add(nextAlloc);
					}
				}
			}	

			ICollateralAllocation[] cpsAlloc =new ICollateralAllocation[newAlloc.size()];
			for(int i = 0;i<newAlloc.size();i++){
				ICollateralAllocation secAlloc = (ICollateralAllocation)newAlloc.get(i);
				cpsAlloc[i]=secAlloc;
			}
			
			ICollateralAllocation[] finalcolAlloc = null;
			if(colAlloc!=null){
				finalcolAlloc = new ICollateralAllocation[cpsAlloc.length+colAlloc.length];
				for(int i = 0;i<colAlloc.length;i++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)colAlloc[i];
					finalcolAlloc[i]=secAlloc;
				}
				int a =0;
				for(int k = colAlloc.length;k<cpsAlloc.length+colAlloc.length;k++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)cpsAlloc[a++];
					finalcolAlloc[k]=secAlloc;
				}
			}else{
				finalcolAlloc = new ICollateralAllocation[cpsAlloc.length];
				for(int a = 0;a<cpsAlloc.length;a++){
					ICollateralAllocation secAlloc = (ICollateralAllocation)cpsAlloc[a];
					finalcolAlloc[a]=secAlloc;
				}
			}
			stagingLimit.setCollateralAllocations(finalcolAlloc);
			lmtTrxObj.setStagingLimit(stagingLimit);
			
			DefaultLogger.debug(this, "Before calling method...limitDAO.updateSanctionedLimitToZero()");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "After calling method...limitDAO.updateSanctionedLimitToZero()");
			
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj,false);

			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			List secList = new ArrayList();
			
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				obj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				ICollateralAllocation[] colalloc= limitTrxValue.getLimit().getCollateralAllocations();
				if(colalloc!=null && colalloc.length>0){
					for(int i =0;i<colalloc.length;i++){
						SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
						ICollateralAllocation alloc = colalloc[i];
						if(alloc.getHostStatus()!=null && alloc.getHostStatus().equalsIgnoreCase("I") && alloc.getCpsSecurityId()!=null){
							dto.setCpsSecurityId(alloc.getCpsSecurityId());
							dto.setSecurityId(String.valueOf(alloc.getCollateral().getCollateralID()));
							secList.add(dto);
						}
					}
				}
				
				obj.setSecurityResponseList(secList);
				obj.setResponseStatus("FACILITY_NEW_FIELDS_EDITED_SUCCESSFULLY");
			}
			
		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CollateralException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CMSValidationFault e) {
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
		return obj;
	}
	

public CommonRestResponseDTO  updateFacilityDetailsRest(FacilityDetlRestRequestDTO  facilityDetailsRestRequest, Gson gson) throws CMSValidationFault, CMSFault{
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		ICMSTrxResult response = null;
		List newAlloc = new ArrayList();
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		HashMap facMap = new HashMap();
		
		OBRestInterfaceLog restinterfacelog = new OBRestInterfaceLog(); 
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	   String dt=  formatter.format(date);
	   System.out.println("dt===="+dt);
	   
	   String jsonRequest = gson.toJson(facilityDetailsRestRequest);
		System.out.println("jsonRequest ====>"+jsonRequest);
		
		restinterfacelog.setRequestDate(date);
		//CommonRestResponseDTO obj = new CommonRestResponseDTO();
		
		
		HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
		BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
		List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
	    List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
		FacilityDetailsDTOMapper facilityDetailsDTOMapper = new FacilityDetailsDTOMapper();
		
		CommonRestResponseDTO commonCodeRestResponseDTO = new CommonRestResponseDTO();
		List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
		List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

		List<BodyRestResponseDTO> BodyRestList = new LinkedList<BodyRestResponseDTO>();
		List<FacilityDetailRestResponseDTO> facResponseDTOList = new LinkedList<FacilityDetailRestResponseDTO>();
		
		FacilityDetailRestResponseDTO facilityResponse = new FacilityDetailRestResponseDTO();
		
		
		
	    
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
		
		/*if(facilityRequest.getWsConsumer()==null || facilityRequest.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
			
		}*/
		System.out.println("CREATE_FACILITY Before mapper Recieved======" );

		
		FacilityBodyRestRequestDTO reqObj= facilityDetailsDTOMapper.getActualVAluesFromInput(facilityDetailsRestRequest);
		//BigDecimal totalReleasedAmount =new BigDecimal("0");
		reqObj.setEvent("WS_FAC_EDIT_REST");
		
		
		FacilityBodyRestRequestDTO facilityDetailsRequest = facilityDetailsDTOMapper.getRestRequestDTOWithActualValues(reqObj);
		if ("Y".equals(facilityDetailsRequest.getIsReleased()) && facilityDetailsRequest.getLineList() != null		//smriti
				&& !facilityDetailsRequest.getLineList().isEmpty() && facilityDetailsRequest.getLineList().size() > 0) {
			for (int i = 0; i < facilityDetailsRequest.getLineList().size(); i++) {
				
				if ("Y".equals(facilityDetailsRequest.getLineList().get(i).getNewLine().trim())) {
					if (facilityDetailsRequest.getLineList().get(i).getReleasedAmount() != null
							&& !facilityDetailsRequest.getLineList().get(i).getReleasedAmount().trim().isEmpty()) {
						Locale locale;
						BigDecimal totalReleasedAmount =new BigDecimal("0");
						if (Validator.checkNumber(facilityDetailsRequest.getLineList().get(i).getReleasedAmount(), true,
								0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2).equals(Validator.ERROR_NONE))
							totalReleasedAmount = totalReleasedAmount.add(
									new BigDecimal(facilityDetailsRequest.getLineList().get(i).getReleasedAmount()));
					}
				} 
				
			}
		}	
		
		ActionErrors cpsIdErrors = facilityDetailsRequest.getErrors();
		
			HashMap map = new HashMap();
			if(cpsIdErrors.size()>0){
				map.put("1", cpsIdErrors);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);


				}
				
				headerObj.setRequestId(facilityDetailsRestRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(facilityDetailsRestRequest.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestList.add(bodyObj);
				commonCodeRestResponseDTO.setHeaderDetails(ccHeaderResponse);
				commonCodeRestResponseDTO.setBodyDetails(BodyRestList);	
				
				return commonCodeRestResponseDTO;
			}
			System.out.println("CREATE_FACILITY After mapper Recieved======" );

		String camId = "";
		try {
			limitProxy = LimitProxyFactory.getProxy();
			context = mcUtil.setContextForMaker();//setContextAsPerUserId(context,"CPUADM_A");
			if(facilityDetailsRequest.getCamId()!=null && !facilityDetailsRequest.getCamId().trim().isEmpty()){
				camId = facilityDetailsRequest.getCamId().trim();
				profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			}			
			context.setLimitProfile(profile);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		} 
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);

		context.setCustomer(cust);

		System.out.println("CREATE_FACILITY before getFormFromDTO mapper Recieved======" );

		LmtDetailForm facilityForm = facilityDetailsDTOMapper.getFormFromRestDTO(facilityDetailsRequest,cust);
		try
		{
		List resultlist =proxy.getFacDetailList(facilityDetailsRequest.getFacilityName(), String.valueOf(cust.getCustomerID()));
		OBLimit obLimit=(OBLimit) resultlist.get(0);
		facilityForm.setLineNo(obLimit.getLineNo());
		facilityForm.setPurpose(obLimit.getPurpose());
		facilityForm.setFacilitySystem(obLimit.getFacilitySystem());
		facilityForm.setFacilityType(obLimit.getFacilityType());
		facilityForm.setFacilityCode(obLimit.getFacilityCode());
		//facilityForm.setFacilityCat(obLimit.getFacilityCat());
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("CREATE_FACILITY after getFormFromDTO mapper Recieved======" );

		facilityForm.setEvent("WS_FAC_EDIT_REST");
		ActionErrors facilityErrorList = MILimitValidatorRest.validateMILimit(facilityForm, Locale.getDefault(), facilityDetailsRequest); 
		//code for facility field validation
		if(facilityErrorList.size()>0){
			facMap.put("1", facilityErrorList);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(facMap, CLIMSWebService.FACILITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		System.out.println("UPDATE_FACILITY before getSecurityList mapper Recieved======" );

		
		if(facilityDetailsRequest.getSecured() != null && !(facilityDetailsRequest.getSecured()).trim().isEmpty())
		{
			if("Y".equalsIgnoreCase(facilityDetailsRequest.getSecured()))
			{
			if(facilityDetailsRequest.getSecurityList()!=null && !facilityDetailsRequest.getSecurityList().isEmpty() 
					&& facilityDetailsRequest.getSecurityList().size()>0 ){
				HashMap secMap = new HashMap();
				SecurityDetailsDTOMapper securityDetailsDTOMapper = new SecurityDetailsDTOMapper();
				for (int i = 0;i<facilityDetailsRequest.getSecurityList().size();i++){
					ActionErrors securityErrorListCps = new ActionErrors();
					SecurityDetailRestRequestDTO securityDetailRequestDTO = securityDetailsDTOMapper.getRestRequestDTOWithActualValues(facilityDetailsRequest.getSecurityList().get(i),"WS_FAC_CREATE",profile);
					securityErrorListCps = securityDetailRequestDTO.getErrors();
					String unqSecurityId = ""; 
					if(facilityDetailsRequest.getSecurityList().get(i).getUniqueReqId()!=null && !facilityDetailsRequest.getSecurityList().get(i).getUniqueReqId().trim().isEmpty()){
						unqSecurityId = facilityDetailsRequest.getSecurityList().get(i).getUniqueReqId().trim();
					}
//					if(null != securityErrorListCps)
					secMap.put(i+unqSecurityId, securityErrorListCps);
				}

				//ValidationUtility.handleError(secMap, CLIMSWebService.SECURITY);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(secMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
	
//				HashMap securityMap = new HashMap();
//				for (int i = 0;i<facilityDetailsRequest.getSecurityList().size();i++){
//					ActionErrors securityErrorList = new ActionErrors();
//					SecDetailForm securityForm = securityDetailsDTOMapper.getFormFromRestDTO(facilityDetailsRequest.getSecurityList().get(i));
//					securityForm.setEvent("WS_FAC_CREATE_REST");
//					securityErrorList = MISecValidator.validateMISecurityRest(securityForm, Locale.getDefault());
//					String unqSecurityId = "";
//					if(securityForm.getUniqueReqId()!=null && !securityForm.getUniqueReqId().trim().isEmpty()){
//						unqSecurityId = securityForm.getUniqueReqId().trim();
//					}
//					
//					securityMap.put(unqSecurityId, securityErrorList);
//				}
//				//code for security field validation
//				//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
//				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(securityMap, CLIMSWebService.FACILITY);
//				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
//				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
//				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
//				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
//				    responseMessageDetailDTOList.add(rmd);
//				}
			}
			
			
		}}
		FacilitySCODDetailRestRequestDTO facilityRequestscod=null;
		
		//Add if statement :: this need be called when scod flag is Y
				if(facilityDetailsRequest.getIsScod() != null && !(facilityDetailsRequest.getIsScod()).trim().isEmpty())		//smriti
				{
					String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
					System.out.println("scodLineNoList ====>"+scodLineNoList);

					if("Y".equalsIgnoreCase(facilityDetailsRequest.getIsScod()))
					{

						if(!(null == facilityForm.getLineNo()|| facilityForm.getLineNo().isEmpty()) 
								&& (scodLineNoList.contains(facilityForm.getLineNo())))
						{
							facilityRequestscod = facilityDetailsDTOMapper.getActualVAluesFromInputSCOD(facilityDetailsRestRequest);
							facilityRequestscod.setEvent("WS_FAC_EDIT_SCOD_REST");
							FacilitySCODDetailRestRequestDTO facilityDetailScodRequestDTO = facilityDetailsDTOMapper.getSCODRestRequestDTOWithActualValues(facilityRequestscod);
							ActionErrors cpsIdErrorsscod = facilityDetailScodRequestDTO.getErrors();

							HashMap mapscod = new HashMap();
							if (cpsIdErrorsscod.size() > 0)
							{
								mapscod.put("1", cpsIdErrorsscod);
								validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
								for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
									ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
									rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
									rmd.setResponseMessage(validationErrorDetailsDTO.getField());
									responseMessageDetailDTOList.add(rmd);


								}

							}
							if (responseMessageDetailDTOList.isEmpty()) {
									FacilitySCODDetailRequestDTO facilitySCODdetailRequestDTO = facilityDetailsDTOMapper.getSCODformValidationRest(facilityRequestscod);
									ActionErrors cpsIdErrorsscodform = facilitySCODdetailRequestDTO.getErrors();
									
									HashMap mapscodform = new HashMap();

								mapscodform.put("1", cpsIdErrorsscodform);
								validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscodform, CLIMSWebService.FACILITY);
								for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
									ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
									rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
									rmd.setResponseMessage(validationErrorDetailsDTO.getField());
									responseMessageDetailDTOList.add(rmd);

								}
								

							}
						}
						else
						{
							ActionErrors errors = new ActionErrors();
							errors.add("lineNo", new ActionMessage("error.lineNo.notvalid"));
							HashMap mapscod = new HashMap();
							if (errors.size() > 0)
							{
								mapscod.put("1", errors);
								validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
								for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
									ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
									rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
									rmd.setResponseMessage(validationErrorDetailsDTO.getField());
									responseMessageDetailDTOList.add(rmd);


								}

							}
						}								
					}else if("N".equalsIgnoreCase(facilityDetailsRequest.getIsScod()) && (facilityForm.getLineNo()!=null && scodLineNoList.contains(facilityForm.getLineNo()))) {
						ActionErrors errors = new ActionErrors();
						errors.add("isScod", new ActionMessage("should be 'Y' for SCOD Line"));
						HashMap mapscod = new HashMap();
						if (errors.size() > 0)
						{
							mapscod.put("1", errors);
							validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
							for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
								ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
								rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
								rmd.setResponseMessage(validationErrorDetailsDTO.getField());
								responseMessageDetailDTOList.add(rmd);


							}

						}					
					}
				}
		
		
		if(facilityDetailsRequest.getLineList()!=null && !facilityDetailsRequest.getLineList().isEmpty() 
				&& facilityDetailsRequest.getLineList().size()>0 ){

			FacilityLineDetailsDTOMapper facilityLineDetailsDTOMapper = new FacilityLineDetailsDTOMapper();
			HashMap lineErrorMap = new HashMap();
			for (int i = 0;i<facilityDetailsRequest.getLineList().size();i++){
				ActionErrors lineErrorList = new ActionErrors();					
				FacilityLineDetailRestRequestDTO reqObjline = facilityLineDetailsDTOMapper.getRestRequestDTOWithActualValues(facilityDetailsRequest.getLineList().get(i), "",facilityDetailsRequest,facilityForm);
				
				lineErrorList = reqObjline.getErrors();
				String unqId = ""; 
				if(facilityDetailsRequest.getLineList().get(i).getSystemId()!=null && !facilityDetailsRequest.getLineList().get(i).getSystemId().trim().isEmpty()){
					unqId = facilityDetailsRequest.getLineList().get(i).getSystemId().trim();
				}
				
				lineErrorMap.put(unqId, lineErrorList);
			}
			//code for security field validation
			//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(lineErrorMap, CLIMSWebService.FACILITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}

			HashMap lineMap = new HashMap();
			
			
			
			for (int i = 0;i<facilityDetailsRequest.getLineList().size();i++){
				ActionErrors lineErrorList = new ActionErrors();
				String errorLog="";
				
				XRefDetailForm xrefForm = facilityLineDetailsDTOMapper.getFormFromRestDTO(facilityDetailsRequest.getLineList().get(i));
				xrefForm.setLineNo(facilityForm.getLineNo());
				
				xrefForm.setEvent("WS_FAC_LINE_CREATE");
				xrefForm.setFacilitySystem(facilityForm.getFacilitySystem());
				xrefForm.setCustomerID(facilityForm.getCustomerID());//ss
				lineErrorList = MILimitValidatorRest.validateXRef(xrefForm, Locale.getDefault(),facilityDetailsRequest);
				String cpsSecurityId = "1234";
				
				if("N".equals(facilityDetailsRequest.getLineList().get(i).getNewLine()))
				{
					lineErrorList=MILimitValidatorRest.validateXRefLine(xrefForm, Locale.getDefault(),lineErrorList);
					
				}
				lineMap.put(cpsSecurityId, lineErrorList);
			}
			//code for security field validation
			//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(lineMap, CLIMSWebService.FACILITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
		}
		
		
		if (facilityDetailsRequest.getBorrowerList()!=null && !facilityDetailsRequest.getBorrowerList().isEmpty() 
				&& facilityDetailsRequest.getBorrowerList().size()>0 )
				{

			HashMap borrowerMap = new HashMap();
			List<String>liabid= new ArrayList<String>();
			String facCoBorrowerLiabIds="";
			StringBuilder str1 = new StringBuilder("");		
			ActionErrors errors = new ActionErrors();
			
			/*List<String> CoBorrowerName= new ArrayList<String>();
			String facCoBorrowerNames="";
			StringBuilder str2 = new StringBuilder("");*/

			if (facilityDetailsRequest.getBorrowerList().size() > 5) {

				errors.add("borrowerList", new ActionMessage("error.string.duplicate.coBorrower.size"));
				borrowerMap.put("borrowerList", errors);
			}

			else {
			//List<CoBorrowerDetailsRestRequestDTO> facCoBorrowerListNew = new ArrayList<CoBorrowerDetailsRestRequestDTO>();

			for (int i = 0;i<facilityDetailsRequest.getBorrowerList().size();i++){
				
				//CoBorrowerDetailsRestRequestDTO ob= facilityRequestDTO.getBorrowerList().get(i);
				liabid.add(facilityDetailsRequest.getBorrowerList().get(i).getCoBorrowerLiabId()+"-"+facilityDetailsRequest.getBorrowerList().get(i).getCoBorrowerName());
				str1.append(facilityDetailsRequest.getBorrowerList().get(i).getCoBorrowerLiabId()+"-"+facilityDetailsRequest.getBorrowerList().get(i).getCoBorrowerName()).append(",");
			}
	 
			facCoBorrowerLiabIds = str1.toString();
	 
	        // Condition check to remove the last comma
				if (facCoBorrowerLiabIds.length() > 0) {
				facCoBorrowerLiabIds = facCoBorrowerLiabIds.substring( 0, facCoBorrowerLiabIds.length() - 1);	
	        }
	 
			//facCoBorrowerLiabIds= String.join(",",liabid);
			List<String> selectedCoBorrowerIds = UIUtil.getListFromDelimitedString(facCoBorrowerLiabIds, ",");
			ILimitDAO limit = LimitDAOFactory.getDAO();
			List partyCoBorrowerList = limit.getPartyCoBorrowerDetails(cust.getCustomerID());
			
			boolean flag=false;
			if(null != selectedCoBorrowerIds && !selectedCoBorrowerIds.isEmpty() ) {
				
				for(int i=0; i<selectedCoBorrowerIds.size(); i++) {
					for(int j=0; j<partyCoBorrowerList.size(); j++) {
						//IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
						ICoBorrowerDetails partyCoBorrower = new OBCoBorrowerDetails();

						partyCoBorrower= (ICoBorrowerDetails) partyCoBorrowerList.get(j);
						//String liabId= partyCoBorrower.getCoBorrowerLiabId();
						String borroName= partyCoBorrower.getCoBorrowerName();
						//	String[] borroNm= borroName.split("-");
						//	borroName=borroNm[1];
							if (selectedCoBorrowerIds.get(i).equalsIgnoreCase(borroName)) {
							flag=true;
							break;
						}
						
					}
						if(!flag) {
						 
						String BorrowerId =selectedCoBorrowerIds.get(i);
						errors.add(BorrowerId, new ActionMessage("error.borrowerID")); 
						borrowerMap.put(BorrowerId,errors);
						flag=false;
					}
				}
			}
			
			}
			//code for security field validation
			//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
			validationErrorDetailsDTOList = ValidationUtilityRest.handleError(borrowerMap, CLIMSWebService.FACILITY);
			for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
			    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
			    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
			    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
			    responseMessageDetailDTOList.add(rmd);
			}
				}
		
		
		
		FacilityDetailRestResponseDTO obj = new FacilityDetailRestResponseDTO();
		if (responseMessageDetailDTOList.isEmpty())
		{

		ILimitTrxValue lmtTrxObj = null;
		
		try {
			//need to add facility id generated by CLIMS in specification
			//lmtTrxObj = proxy.searchLimitByLmtId("20140924000001680");
			try{
				if(facilityDetailsRequest.getClimsFacilityId()!=null && !facilityDetailsRequest.getClimsFacilityId().trim().isEmpty()){
					lmtTrxObj = proxy.searchLimitByLmtId(facilityDetailsRequest.getClimsFacilityId().trim());
				}
			}
			catch(Exception e){
				throw new CMSValidationFault("climsFacilityId","Invalid climsFacilityId");
			}
			
	
			if(lmtTrxObj!=null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					||(lmtTrxObj.getStatus().equals("PENDING_DELETE"))
					||(lmtTrxObj.getStatus().equals("REJECTED"))
					||(lmtTrxObj.getStatus().equals("DRAFT"))
					||(lmtTrxObj.getStatus().equals("DELETED")))
			)
			{
				throw new CMSValidationFault("transactionStatus","Unable to update due to invalid transaction Status :"+lmtTrxObj.getStatus());
			}
			
			System.out.println("66666" );

			ILimit limit = (ILimit)lmtTrxObj.getLimit();
			
			/*if(facilityRequest.getEvent()!=null && facilityRequest.getEvent().equalsIgnoreCase("WS_FAC_EDIT")){
				if(!limit.getFacilityCode().equalsIgnoreCase(facilityForm.getFacilityCode()))
				{
					throw new CMSValidationFault("FacilityMasterId","FacilityMasterId is non editable field");
				}
			}*/
			
			limit=facilityDetailsDTOMapper.getActualUpdateFromRestDTO(facilityDetailsRequest,limit);
			ILimitSysXRef[] existLineAlloc = limit.getLimitSysXRefs();
			
			if(facilityDetailsRequest.getLineList()!=null && !facilityDetailsRequest.getLineList().isEmpty()
					&& facilityDetailsRequest.getLineList().size()>0){
				
				ILimitSysXRef[] newLineAlloc = new ILimitSysXRef[facilityDetailsRequest.getLineList().size()];
				ILimitSysXRef limitSysXref;
				OBCustomerSysXRef xrefobj ;
				List<FacilityLineDetailRestRequestDTO> lineList= facilityDetailsRequest.getLineList();
				for(int i = 0;i<lineList.size();i++)
				{
					FacilityLineDetailRestRequestDTO lineDTO = (FacilityLineDetailRestRequestDTO)lineList.get(i);
					if("Y".equals(lineDTO.getNewLine()))
					{
						limitSysXref = new OBLimitSysXRef();
						xrefobj = new OBCustomerSysXRef();						
						xrefobj=facilityDetailsDTOMapper.getActualLineValFromRestDTO(lineDTO,facilityForm, xrefobj);

					}
					else
					{
						limitSysXref =lmtTrxObj.getStagingLimit().getLimitSysXRefs()[0];
						xrefobj= (OBCustomerSysXRef) limitSysXref.getCustomerSysXRef();
						xrefobj=facilityDetailsDTOMapper.getActualLineValFromRestDTO(lineDTO,facilityForm,xrefobj);

					}

					limitSysXref.setCustomerSysXRef(xrefobj);
					limitSysXref.setStatus("ACTIVE");
					newLineAlloc[i]=limitSysXref;
				}
				ILimitSysXRef[] finalLinealloc=null;
                if(existLineAlloc!=null){
                    finalLinealloc = new ILimitSysXRef[newLineAlloc.length+existLineAlloc.length];
                    for(int i = 0;i<existLineAlloc.length;i++){
                        ILimitSysXRef lineAlloc = (ILimitSysXRef)existLineAlloc[i];
                        finalLinealloc[i]=lineAlloc;
                    }
                    int a =0;
                    for(int k = existLineAlloc.length;k<newLineAlloc.length+existLineAlloc.length;k++){
                        ILimitSysXRef lineAlloc = (ILimitSysXRef)newLineAlloc[a++];
                        finalLinealloc[k]=lineAlloc;
                    }
                }else{
                    finalLinealloc = new ILimitSysXRef[newLineAlloc.length];
                    for(int a = 0;a<newLineAlloc.length;a++){
                        ILimitSysXRef lineAlloc = (ILimitSysXRef)newLineAlloc[a];
                        finalLinealloc[a]=lineAlloc;
                    }
                }

                limit.setLimitSysXRefs(finalLinealloc);
			}
			
			List<IFacilityCoBorrowerDetails> exitBorrowers =  limit.getCoBorrowerDetails();
			if(facilityDetailsRequest.getBorrowerList()!=null && !facilityDetailsRequest.getBorrowerList().isEmpty()		//smriti
            && facilityDetailsRequest.getBorrowerList().size()>0)
            {
			List<IFacilityCoBorrowerDetails> facCoBorrowerDetailsList = new ArrayList<IFacilityCoBorrowerDetails>();

			List<CoBorrowerDetailsRestRequestDTO> coborrowerList = facilityDetailsRequest.getBorrowerList();
			
			for(CoBorrowerDetailsRestRequestDTO coBorrowerId : coborrowerList) {
				IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
				facCoBorrower.setCoBorrowerLiabId(coBorrowerId.getCoBorrowerLiabId());
				facCoBorrower.setCoBorrowerName(coBorrowerId.getCoBorrowerLiabId()+"-"+coBorrowerId.getCoBorrowerName());
//				facCoBorrower.setCoBorrowerName(coBorrowerId.getCoBorrowerName());
				facCoBorrower.setCreateBy("CMS");
				facCoBorrower.setCreationDate(UIUtil.getDate());
				facCoBorrower.setMainProfileId(Long.parseLong(facilityDetailsRequest.getCamId()));
				
				facCoBorrowerDetailsList.add(facCoBorrower);
			}
			
                if(null != exitBorrowers && !exitBorrowers.isEmpty() && exitBorrowers.size()>0)
                {
                    if(null != facCoBorrowerDetailsList && !facCoBorrowerDetailsList.isEmpty() && facCoBorrowerDetailsList.size()>0)
                    {
                        exitBorrowers.addAll(facCoBorrowerDetailsList);
                        limit.setCoBorrowerDetails(exitBorrowers);
                    }
                    else
                    {
                        limit.setCoBorrowerDetails(exitBorrowers);
                    }
                }
                else
                {
                    if(null != facCoBorrowerDetailsList && !facCoBorrowerDetailsList.isEmpty() && facCoBorrowerDetailsList.size()>0)
                    {
			limit.setCoBorrowerDetails(facCoBorrowerDetailsList);
			}
                }
            }
			
			
			MISecurityUIHelper secHelper = new MISecurityUIHelper();		//smriti
			SBMISecProxy secProxy = secHelper.getSBMISecProxy();			
			ICMSTrxResult secRes = null;
			
			ICollateralAllocation[] colAlloc= limit.getCollateralAllocations();
			
			if(facilityDetailsRequest.getSecured() != null && !(facilityDetailsRequest.getSecured()).trim().isEmpty())
			{
				if("Y".equalsIgnoreCase(facilityDetailsRequest.getSecured()))
				{
			if(facilityDetailsRequest.getSecurityList()!=null && !facilityDetailsRequest.getSecurityList().isEmpty()
					&& facilityDetailsRequest.getSecurityList().size()>0){
				ICollateralAllocation[] newAllocUpdate = new ICollateralAllocation[facilityDetailsRequest.getSecurityList().size()];
				List<SecurityDetailRestRequestDTO> securityList= facilityDetailsRequest.getSecurityList();
				for(int i = 0;i<securityList.size();i++){
					ICollateralTrxValue secTrxObj = new OBCollateralTrxValue();
					SecurityDetailRestRequestDTO securityDTO = (SecurityDetailRestRequestDTO)securityList.get(i);
					
					
					ICollateralDAO dao = CollateralDAOFactory.getDAO();
					OBCollateral collateral = new OBCollateral();
					if((securityDTO.getPartyId()!=null && !securityDTO.getPartyId().trim().isEmpty() ) ||
							securityDTO.getExistingSecurityId()!=null && !securityDTO.getExistingSecurityId().trim().isEmpty())
					{
						LmtColSearchCriteria crit = new LmtColSearchCriteria();
						crit.setLimitProfId(profile.getLimitProfileID());
			            crit.setSciSecId(securityDTO.getExistingSecurityId());
			            crit.setCustName(securityDTO.getPartyId());
						collateral= dao.searchCollateralByIdSubtypeRest(crit);
						
							
								OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
								nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
								nextAlloc.setCollateral(collateral);
								nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
								nextAlloc.setLimitProfileID(Long.parseLong(camId));
								nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
								if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
									nextAlloc.setLmtSecurityCoverage(securityDTO.getSecurityCoverage());
									System.out.print("====FacilityServiceFacade==2808=====nextAlloc.getLmtSecurityCoverage : "+nextAlloc.getLmtSecurityCoverage());
									}
								else
								{
								nextAlloc.setLmtSecurityCoverage("100");
								}


								if(securityDTO.getUniqueReqId()!=null && !securityDTO.getUniqueReqId().trim().isEmpty()){
									nextAlloc.setCpsSecurityId(securityDTO.getUniqueReqId().trim());
								}else{
									nextAlloc.setCpsSecurityId("");
								}

								newAllocUpdate[i] = nextAlloc;
//							}

						}
					else
					{
						
						if(null != securityDTO.getSecurityCurrency() && !securityDTO.getSecurityCurrency().trim().isEmpty()){
							collateral.setCurrencyCode(securityDTO.getSecurityCurrency().trim());
						}else{
							collateral.setCurrencyCode("INR");
						}
						
						if(null != securityDTO.getSecurityCountry() && !securityDTO.getSecurityCountry().trim().isEmpty()){
							collateral.setCollateralLocation(securityDTO.getSecurityCountry().trim());
						}else{
							collateral.setCollateralLocation("IN");
						}
						

						if(securityDTO.getSecurityPriority()!=null && !securityDTO.getSecurityPriority().trim().isEmpty()){
							collateral.setSecPriority(securityDTO.getSecurityPriority().trim());
						}else{
							collateral.setSecPriority("");
						}

						if(securityDTO.getCollateralCodeName()!=null && !securityDTO.getCollateralCodeName().trim().isEmpty()){
							collateral.setCollateralCode(securityDTO.getCollateralCodeName().trim());
						}else{
							collateral.setCollateralCode("");
						}

						if(securityDTO.getMonitorFrequencyColl()!=null && !securityDTO.getMonitorFrequencyColl().trim().isEmpty())
						{ 
							collateral.setMonitorFrequency(securityDTO.getMonitorFrequencyColl().trim());
						}else
						{
							collateral.setMonitorFrequency("");
						}
						if(securityDTO.getMonitorProcessColl()!=null && !securityDTO.getMonitorProcessColl().trim().isEmpty()){
							collateral.setMonitorProcess(securityDTO.getMonitorProcessColl().trim());
						}
						else{
							collateral.setMonitorProcess("");
						}

						if(securityDTO.getSecurityRefNote()!=null && !securityDTO.getSecurityRefNote().trim().isEmpty()){
							collateral.setSCIReferenceNote(securityDTO.getSecurityRefNote().trim());
						}else{
							collateral.setSCIReferenceNote("");
						}
						
						if(securityDTO.getSecurityBranch()!=null && !securityDTO.getSecurityBranch().trim().isEmpty()){
							collateral.setSecurityOrganization(securityDTO.getSecurityBranch().trim());
						}
						else
						{
							collateral.setSecurityOrganization("");
						}
						if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
							collateral.setLmtSecurityCoverage(securityDTO.getSecurityCoverage().trim());
							System.out.print("====FacilityServiceFacade==2883=====collateral.getLmtSecurityCoverage : "+collateral.getLmtSecurityCoverage());
						}
						else
						{
							collateral.setLmtSecurityCoverage("100");
						}

						collateral.setStatus("ACTIVE");

						if(securityDTO.getSecuritySubType()!=null && !securityDTO.getSecuritySubType().trim().isEmpty()){
							OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(securityDTO.getSecuritySubType().trim());
							collateral.setCollateralSubType(obSubType);
						}
						ICollateral stagingCol = collateral;
						secHelper.setTrxLocation(context, stagingCol);
						secHelper.setPledgorLocation(stagingCol);
						secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
						secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
						ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
						ICollateral newcol = colVal.getCollateral();
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCollateral(newcol);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(camId));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
						if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
							nextAlloc.setLmtSecurityCoverage(securityDTO.getSecurityCoverage());		
							System.out.print("====FacilityServiceFacade==2911=====nextAlloc.getLmtSecurityCoverage : "+nextAlloc.getLmtSecurityCoverage());
							}
						else
						{
						nextAlloc.setLmtSecurityCoverage("100");
						}

						
						if(securityDTO.getUniqueReqId()!=null && !securityDTO.getUniqueReqId().trim().isEmpty()){
							nextAlloc.setCpsSecurityId(securityDTO.getUniqueReqId().trim());
						}else{
							nextAlloc.setCpsSecurityId("");
						}
						
						newAllocUpdate[i] = nextAlloc;
					}
					
				}
				//limit.setCollateralAllocations(newAllocUpdate);
				
				
				
				ICollateralAllocation[] finalcolAlloc = null;
                if(colAlloc!=null){
                    finalcolAlloc = new ICollateralAllocation[newAllocUpdate.length+colAlloc.length];
                    for(int i = 0;i<colAlloc.length;i++){
                        ICollateralAllocation secAlloc = (ICollateralAllocation)colAlloc[i];
                        finalcolAlloc[i]=secAlloc;
                    }
                    int a =0;
                    for(int k = colAlloc.length;k<newAllocUpdate.length+colAlloc.length;k++){
                        ICollateralAllocation secAlloc = (ICollateralAllocation)newAllocUpdate[a++];
                        finalcolAlloc[k]=secAlloc;
                    }
                }else{
                    finalcolAlloc = new ICollateralAllocation[newAllocUpdate.length];
                    for(int a = 0;a<newAllocUpdate.length;a++){
                        ICollateralAllocation secAlloc = (ICollateralAllocation)newAllocUpdate[a];
                        finalcolAlloc[a]=secAlloc;
                    }
                }
                limit.setCollateralAllocations(finalcolAlloc);
			}

			}}			System.out.println("121212");
			
			if(facilityDetailsRequest.getIsScod() != null && !(facilityDetailsRequest.getIsScod()).trim().isEmpty())
			{
				if("Y".equalsIgnoreCase(facilityDetailsRequest.getIsScod()))
				{
					String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
					System.out.println("scodLineNoList ====>"+scodLineNoList);

					if(!(null == facilityForm.getLineNo()|| facilityForm.getLineNo().isEmpty()) 
							&& (scodLineNoList.contains(facilityForm.getLineNo())))
					{
						//FacilitySCODDetailRestRequestDTO facilityRequestscod = facilityDetailsDTOMapper.getActualVAluesFromInputSCOD(facilityDetailsRestRequest);
						
						limit=facilityDetailsDTOMapper.getActualVAluesFromInputSCODToOB(facilityRequestscod, (OBLimit) limit);
					}
					
				}
			}
			
			lmtTrxObj.setStagingLimit(limit);
			
			

			/*
			 * 26-FEB-2016 : When adding/updating facility through CAM online, sanctioned amount of existing facilities (Not created through CAMonline)
			 * should be zeroed - CR raised by bank : CLIMS validation for existing cases for facility/sanctioned amount 
			 * 
			 * Logic: Existing facility's sanctioned amount will be zeroed (i.e. CMS_REQ_SEC_COVERAGE column value) for CAM Id sent through facility request
			 * 
			 * 
			 * */
					
//			proxy.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "Before calling method...limitDAO.updateSanctionedLimitToZero()");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "After calling method...limitDAO.updateSanctionedLimitToZero()");
			
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj,false);

			System.out.println("141414" );

			/*ICMSTrxResult res = proxy.makerUpdateLmtTrx(context, lmtTrxObj, false);
			lmtTrxObj = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
			//Setting user related information in the context
			context = setContextAsPerUserId(context,"CPUADM_C");
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			//Checker approve process
			response = proxy.checkerApproveLmtTrx(context, lmtTrxObj);*/
			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			List secList = new ArrayList();
			
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				bodyObj.setLimitId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				bodyObj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitRef()));
				ICollateralAllocation[] colalloc= limitTrxValue.getLimit().getCollateralAllocations();
				if(facilityDetailsRequest.getSecured()!=null && "Y".equalsIgnoreCase(facilityDetailsRequest.getSecured())){
				if(colalloc!=null && colalloc.length>0){
					for(int i =0;i<colalloc.length;i++){
						SecurityDetailRestResponseDTO dto = new SecurityDetailRestResponseDTO();
						ICollateralAllocation alloc = colalloc[i];
						if(alloc.getHostStatus()!=null && alloc.getHostStatus().equalsIgnoreCase("I") && alloc.getCpsSecurityId()!=null){
							dto.setUniqueReqId(alloc.getCpsSecurityId());
							dto.setSecurityId(String.valueOf(alloc.getCollateral().getCollateralID()));
							secList.add(dto);
						}
					}
				}
				}else{
					SecurityDetailResponseDTO dto = new SecurityDetailResponseDTO();
					dto.setCpsSecurityId(" ");
					dto.setSecurityId(" ");
					secList.add(dto);
				}
				System.out.println("131313" );

				/*obj.setSecurityResponseList(secList);
				obj.setResponseStatus("FACILITY_EDITED_SUCCESSFULLY");
				facResponseDTOList.add(obj);*/
				headerObj.setRequestId(facilityDetailsRestRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(facilityDetailsRestRequest.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
					
				
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.SUCCESS_RESPONSE_CODE);
				responseMessageDetailDTO.setResponseMessage("FACILITY_EDITED_SUCCESSFULLY");
				responseMessageList.add(responseMessageDetailDTO);
		
				bodyObj.setResponseStatus("SUCCESS");
				bodyObj.setResponseMessageList(responseMessageList);
				
				bodyObj.setSecurityResponseList(secList);
				
				BodyRestList.add(bodyObj);
				
				
				commonCodeRestResponseDTO.setHeaderDetails(ccHeaderResponse);
				commonCodeRestResponseDTO.setBodyDetails(BodyRestList);
				
			}
		
			
		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CollateralException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (CMSValidationFault e) {
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			System.out.println("In update "+e);
			throw new CMSException(e.getMessage(),e);
		}
		
		}
		else
		{
			bodyObj.setPartyId("");
			bodyObj.setResponseStatus("FAILED");
			bodyObj.setResponseMessageList(responseMessageDetailDTOList);
			BodyRestList.add(bodyObj);
			commonCodeRestResponseDTO.setBodyDetails(BodyRestList);	
			
		}
		return commonCodeRestResponseDTO;
	}
	

	public CommonRestResponseDTO  addFacilityDetailsRest(FacilityDetlRestRequestDTO  facilityDetailsRestRequest, Gson gson) throws CMSValidationFault, CMSFault{
		try {
			//getEndOfDaySyncMastersServiceImpl().performEODSync();
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			ILimitProfile profile;
			List secList = new ArrayList();
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();	
			HashMap facMap = new HashMap();
			//Fetching CAM Details and set to the context 
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			
			/*if(facilityRequestDTO.getWsConsumer()==null || facilityRequestDTO.getWsConsumer().trim().isEmpty()){
				throw new CMSException("wsConsumer is mandatory");
			}*/
			
			OBRestInterfaceLog restinterfacelog = new OBRestInterfaceLog(); 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		   String dt=  formatter.format(date);
		   System.out.println("dt===="+dt);
		   
		   String jsonRequest = gson.toJson(facilityDetailsRestRequest);
			//System.out.println("jsonRequest ====>"+jsonRequest);
			
			restinterfacelog.setRequestDate(date);
			//CommonRestResponseDTO obj = new CommonRestResponseDTO();
			
			
			HeaderDetailRestResponseDTO headerObj= new  HeaderDetailRestResponseDTO();
			BodyRestResponseDTO bodyObj= new  BodyRestResponseDTO();
			List<ResponseMessageDetailDTO> responseMessageDetailDTOList = new ArrayList<ResponseMessageDetailDTO>();
		    List<ValidationErrorDetailsDTO> validationErrorDetailsDTOList;
			FacilityDetailsDTOMapper facilityDetailsDTOMapper = new FacilityDetailsDTOMapper();
			
			CommonRestResponseDTO commonCodeRestResponseDTO = new CommonRestResponseDTO();
			List<ResponseMessageDetailDTO> responseMessageList = new LinkedList<ResponseMessageDetailDTO>();
			List<HeaderDetailRestResponseDTO> ccHeaderResponse = new LinkedList<HeaderDetailRestResponseDTO>();

			List<BodyRestResponseDTO> BodyRestList = new LinkedList<BodyRestResponseDTO>();
			List<FacilityDetailRestResponseDTO> facResponseDTOList = new LinkedList<FacilityDetailRestResponseDTO>();
			
			FacilityDetailRestResponseDTO facilityResponse = new FacilityDetailRestResponseDTO();
			
			FacilityBodyRestRequestDTO reqObj = facilityDetailsDTOMapper.getActualVAluesFromInput(facilityDetailsRestRequest);
			BigDecimal totalReleasedAmount =new BigDecimal("0");
			reqObj.setEvent("WS_FAC_CREATE_REST");
			FacilityBodyRestRequestDTO facilityRequestDTO  = facilityDetailsDTOMapper.getRestRequestDTOWithActualValues(reqObj);
			if ("Y".equals(facilityRequestDTO.getIsReleased()) && facilityRequestDTO.getLineList() != null
					&& !facilityRequestDTO.getLineList().isEmpty() && facilityRequestDTO.getLineList().size() > 0) {
				for (int i = 0; i < facilityRequestDTO.getLineList().size(); i++) {
					
					if ("Y".equals(facilityRequestDTO.getLineList().get(i).getNewLine().trim())) {
						if (facilityRequestDTO.getLineList().get(i).getReleasedAmount() != null
								&& !facilityRequestDTO.getLineList().get(i).getReleasedAmount().trim().isEmpty()) {
							Locale locale;
							if (Validator.checkNumber(facilityRequestDTO.getLineList().get(i).getReleasedAmount(), true,
									0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_38_2).equals(Validator.ERROR_NONE))
								totalReleasedAmount = totalReleasedAmount.add(
										new BigDecimal(facilityRequestDTO.getLineList().get(i).getReleasedAmount()));
						}
					} else if (null == facilityRequestDTO.getLineList().get(i).getNewLine()
							|| facilityRequestDTO.getLineList().get(i).getNewLine().trim().isEmpty()
							|| "N".equals(facilityRequestDTO.getLineList().get(i).getNewLine().trim())) {
						facilityRequestDTO.getLineList().remove(facilityRequestDTO.getLineList().get(i));
					} 
					else {
						String newLine=facilityRequestDTO.getLineList().get(i).getNewLine().trim();
						ActionErrors errors = new ActionErrors();
						errors.add( newLine + " - newLine", new ActionMessage("error.invalid"));
					} 
				}
			}
			ActionErrors cpsIdErrors = facilityRequestDTO.getErrors();

			
			
			HashMap map = new HashMap();
			if(cpsIdErrors.size()>0){
				map.put("1", cpsIdErrors);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(map, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
					ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
					rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
					rmd.setResponseMessage(validationErrorDetailsDTO.getField());
					responseMessageDetailDTOList.add(rmd);


				}
				
				headerObj.setRequestId(facilityDetailsRestRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(facilityDetailsRestRequest.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestList.add(bodyObj);
				commonCodeRestResponseDTO.setHeaderDetails(ccHeaderResponse);
				commonCodeRestResponseDTO.setBodyDetails(BodyRestList);	
				
				return commonCodeRestResponseDTO;
			}
			String camId = "";
			if(facilityRequestDTO.getCamId()!=null && !facilityRequestDTO.getCamId().trim().isEmpty()){
				camId = facilityRequestDTO.getCamId().trim();
			}
			
			profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			//Fetching Party Details and set to the context 
			ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
			
			LmtDetailForm facilityForm = facilityDetailsDTOMapper.getFormFromRestDTO(facilityRequestDTO,cust);
			try
			{
				//System.out.println("9898 new inside tryyy" );
			List resultlist =proxy.getFacDetailList(facilityRequestDTO.getFacilityName(), String.valueOf(cust.getCustomerID()));
			OBLimit obLimit=(OBLimit) resultlist.get(0);
			facilityForm.setLineNo(obLimit.getLineNo());
			facilityForm.setPurpose(obLimit.getPurpose());
			facilityForm.setFacilitySystem(obLimit.getFacilitySystem());
			facilityForm.setFacilityType(obLimit.getFacilityType());
			facilityForm.setFacilityCode(obLimit.getFacilityCode());
			//facilityForm.setFacilityCat(obLimit.getFacilityCat());
			
			}
			catch(Exception e)
			{
				//System.out.println("exception ====>"+e);
				e.printStackTrace();
			}
			facilityForm.setEvent("WS_FAC_CREATE_REST");
			facilityForm.setTotalReleasedAmount(totalReleasedAmount.toString());
			ActionErrors facilityErrorList = MILimitValidatorRest.validateMILimit(facilityForm, Locale.getDefault(),facilityRequestDTO); 
			//code for facility field validation
		if(facilityErrorList.size()>0){
				facMap.put("1", facilityErrorList);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(facMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
			}
		FacilitySCODDetailRestRequestDTO facilityRequestscod=null;
		//Add if statement :: this need be called when scod flag is Y
		if(facilityRequestDTO.getIsScod() != null && !(facilityRequestDTO.getIsScod()).trim().isEmpty())
		{
			String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
			System.out.println("scodLineNoList ====>"+scodLineNoList);

			if("Y".equalsIgnoreCase(facilityRequestDTO.getIsScod()))
			{

				if(!(null == facilityForm.getLineNo()|| facilityForm.getLineNo().isEmpty()) 
						&& (scodLineNoList.contains(facilityForm.getLineNo())))
				{
					facilityRequestscod = facilityDetailsDTOMapper.getActualVAluesFromInputSCOD(facilityDetailsRestRequest);
					facilityRequestscod.setEvent("WS_FAC_CREATE_SCOD_REST");
					FacilitySCODDetailRestRequestDTO facilityDetailScodRequestDTO = facilityDetailsDTOMapper.getSCODRestRequestDTOWithActualValues(facilityRequestscod);
					ActionErrors cpsIdErrorsscod = facilityDetailScodRequestDTO.getErrors();

					HashMap mapscod = new HashMap();
					if (cpsIdErrorsscod.size() > 0)
					{
						mapscod.put("1", cpsIdErrorsscod);
						validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
						for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
							ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
							rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
							rmd.setResponseMessage(validationErrorDetailsDTO.getField());
							responseMessageDetailDTOList.add(rmd);


						}

					}
				}
				else
				{
					ActionErrors errors = new ActionErrors();
					errors.add("lineNo", new ActionMessage("error.lineNo.notvalid"));
					HashMap mapscod = new HashMap();
					if (errors.size() > 0)
					{
						mapscod.put("1", errors);
						validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
						for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
							ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
							rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
							rmd.setResponseMessage(validationErrorDetailsDTO.getField());
							responseMessageDetailDTOList.add(rmd);


						}

					}
				}
			}else if("N".equalsIgnoreCase(facilityRequestDTO.getIsScod()) && (facilityForm.getLineNo()!=null && scodLineNoList.contains(facilityForm.getLineNo()))) {
				ActionErrors errors = new ActionErrors();
				errors.add("isScod", new ActionMessage("should be 'Y' for SCOD Line"));
				HashMap mapscod = new HashMap();
				if (errors.size() > 0)
				{
					mapscod.put("1", errors);
					validationErrorDetailsDTOList = ValidationUtilityRest.handleError(mapscod, CLIMSWebService.FACILITY);
					for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
						ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
						rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
						rmd.setResponseMessage(validationErrorDetailsDTO.getField());
						responseMessageDetailDTOList.add(rmd);


					}

				}
			}
		}
		//scod validaion code end
		
		if(facilityRequestDTO.getSecured() != null && !(facilityRequestDTO.getSecured()).trim().isEmpty())
		{
			if("Y".equalsIgnoreCase(facilityRequestDTO.getSecured()))
			{
			if(facilityRequestDTO.getSecurityList()!=null && !facilityRequestDTO.getSecurityList().isEmpty() 
					&& facilityRequestDTO.getSecurityList().size()>0 ){
				HashMap secMap = new HashMap();
				SecurityDetailsDTOMapper securityDetailsDTOMapper = new SecurityDetailsDTOMapper();
				for (int i = 0;i<facilityRequestDTO.getSecurityList().size();i++){
					ActionErrors securityErrorListCps = new ActionErrors();
					SecurityDetailRestRequestDTO securityDetailRequestDTO = securityDetailsDTOMapper.getRestRequestDTOWithActualValues(facilityRequestDTO.getSecurityList().get(i),"WS_FAC_CREATE",profile);
					securityErrorListCps = securityDetailRequestDTO.getErrors();
					String unqSecurityId = ""; 
					if(facilityRequestDTO.getSecurityList().get(i).getUniqueReqId()!=null && !facilityRequestDTO.getSecurityList().get(i).getUniqueReqId().trim().isEmpty()){
						unqSecurityId = facilityRequestDTO.getSecurityList().get(i).getUniqueReqId().trim();
					}
//					if(null != securityErrorListCps)
					secMap.put(i+unqSecurityId, securityErrorListCps);
				}

				//ValidationUtility.handleError(secMap, CLIMSWebService.SECURITY);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(secMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
	
//				HashMap securityMap = new HashMap();
//				for (int i = 0;i<facilityRequestDTO.getSecurityList().size();i++){
//					ActionErrors securityErrorList = new ActionErrors();
//					SecDetailForm securityForm = securityDetailsDTOMapper.getFormFromRestDTO(facilityRequestDTO.getSecurityList().get(i));
//					securityForm.setEvent("WS_FAC_CREATE_REST");
//					securityErrorList = MISecValidator.validateMISecurityRest(securityForm, Locale.getDefault());
//					String unqSecurityId = "";
//					if(securityForm.getUniqueReqId()!=null && !securityForm.getUniqueReqId().trim().isEmpty()){
//						unqSecurityId = securityForm.getUniqueReqId().trim();
//					}
//					
//					securityMap.put(unqSecurityId, securityErrorList);
//				}
//				//code for security field validation
//				//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
//				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(securityMap, CLIMSWebService.FACILITY);
//				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
//				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
//				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
//				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
//				    responseMessageDetailDTOList.add(rmd);
//				}
			}
			
			
		}}
			
			if("Y".equals(facilityRequestDTO.getIsReleased()) && facilityRequestDTO.getLineList()!=null && !facilityRequestDTO.getLineList().isEmpty() && facilityRequestDTO.getLineList().size()>0 ){
	
				FacilityLineDetailsDTOMapper facilityLineDetailsDTOMapper = new FacilityLineDetailsDTOMapper();
				HashMap lineErrorMap = new HashMap();
				for (int i = 0;i<facilityRequestDTO.getLineList().size();i++){
					ActionErrors lineErrorList = new ActionErrors();					
					FacilityLineDetailRestRequestDTO reqObjline = facilityLineDetailsDTOMapper.getRestRequestDTOWithActualValues(facilityRequestDTO.getLineList().get(i), "",facilityRequestDTO,facilityForm);
					
					lineErrorList = reqObjline.getErrors();
					String unqId = ""; 
					if(facilityRequestDTO.getLineList().get(i).getSystemId()!=null && !facilityRequestDTO.getLineList().get(i).getSystemId().trim().isEmpty()){
						unqId = facilityRequestDTO.getLineList().get(i).getSystemId().trim();
					}
					
					lineErrorMap.put(unqId, lineErrorList);
				}
				//code for security field validation
				//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(lineErrorMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
				
				
				HashMap lineMap = new HashMap();
				for (int i = 0;i<facilityRequestDTO.getLineList().size();i++){
					ActionErrors lineErrorList = new ActionErrors();
					
					XRefDetailForm xrefForm = facilityLineDetailsDTOMapper.getFormFromRestDTO(facilityRequestDTO.getLineList().get(i));
					xrefForm.setEvent("WS_LINE_CREATE_REST");
					xrefForm.setFacilitySystem(facilityForm.getFacilitySystem());
					xrefForm.setCustomerID(facilityForm.getCustomerID());//ss
					lineErrorList = MILimitValidatorRest.validateXRef(xrefForm, Locale.getDefault(),facilityRequestDTO);
					String cpsSecurityId = "1234";
					
					
					lineMap.put(cpsSecurityId, lineErrorList);
				}
				//code for security field validation
				//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(lineMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
			}
			
			if (facilityRequestDTO.getBorrowerList()!=null && !facilityRequestDTO.getBorrowerList().isEmpty() 
					&& facilityRequestDTO.getBorrowerList().size()>0 ){
	
				HashMap borrowerMap = new HashMap();
				List<String>liabid= new ArrayList<String>();
				String facCoBorrowerLiabIds="";
				StringBuilder str1 = new StringBuilder("");		
				ActionErrors errors = new ActionErrors();
				
				List<String> CoBorrowerName= new ArrayList<String>();
				String facCoBorrowerNames="";
				StringBuilder str2 = new StringBuilder("");

				if (facilityRequestDTO.getBorrowerList().size() > 5) {

					errors.add("borrowerList", new ActionMessage("error.string.duplicate.coBorrower.size"));
					borrowerMap.put("borrowerList", errors);
				}

				else {
				//List<CoBorrowerDetailsRestRequestDTO> facCoBorrowerListNew = new ArrayList<CoBorrowerDetailsRestRequestDTO>();

				for (int i = 0;i<facilityRequestDTO.getBorrowerList().size();i++){
					
					//CoBorrowerDetailsRestRequestDTO ob= facilityRequestDTO.getBorrowerList().get(i);
					liabid.add(facilityRequestDTO.getBorrowerList().get(i).getCoBorrowerLiabId()+"-"+facilityRequestDTO.getBorrowerList().get(i).getCoBorrowerName());
					str1.append(facilityRequestDTO.getBorrowerList().get(i).getCoBorrowerLiabId()+"-"+facilityRequestDTO.getBorrowerList().get(i).getCoBorrowerName()).append(",");
				}
		 
				facCoBorrowerLiabIds = str1.toString();
		 
		        // Condition check to remove the last comma
					if (facCoBorrowerLiabIds.length() > 0) {
					facCoBorrowerLiabIds = facCoBorrowerLiabIds.substring( 0, facCoBorrowerLiabIds.length() - 1);	
		        }
		 
				//facCoBorrowerLiabIds= String.join(",",liabid);
				List<String> selectedCoBorrowerIds = UIUtil.getListFromDelimitedString(facCoBorrowerLiabIds, ",");
				ILimitDAO limit = LimitDAOFactory.getDAO();
				List partyCoBorrowerList = limit.getPartyCoBorrowerDetails(cust.getCustomerID());
				
				boolean flag=false;
				if(null != selectedCoBorrowerIds && !selectedCoBorrowerIds.isEmpty() ) {
					
					for(int i=0; i<selectedCoBorrowerIds.size(); i++) {
						for(int j=0; j<partyCoBorrowerList.size(); j++) {
							IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
							ICoBorrowerDetails partyCoBorrower = new OBCoBorrowerDetails();

							partyCoBorrower= (ICoBorrowerDetails) partyCoBorrowerList.get(j);
							String liabId= partyCoBorrower.getCoBorrowerLiabId();
							String borroName= partyCoBorrower.getCoBorrowerName();
							//	String[] borroNm= borroName.split("-");
							//	borroName=borroNm[1];
								if (selectedCoBorrowerIds.get(i).equalsIgnoreCase(borroName)) {
								flag=true;
								break;
							}
							
						}
							if(!flag) {
							 
							String BorrowerId =selectedCoBorrowerIds.get(i);
							errors.add("coBorrowerLiabId_or_coBorrowerName", new ActionMessage("error.borrowerID")); 
							borrowerMap.put(BorrowerId,errors);
							flag=false;
						}
					}
				}
				
				}
				//code for security field validation
				//ValidationUtility.handleError(securityMap, CLIMSWebService.SECURITY);
				validationErrorDetailsDTOList = ValidationUtilityRest.handleError(borrowerMap, CLIMSWebService.FACILITY);
				for(ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetailsDTOList) {
				    ResponseMessageDetailDTO rmd = new ResponseMessageDetailDTO();
				    rmd.setResponseCode(validationErrorDetailsDTO.getErrorCode());
				    rmd.setResponseMessage(validationErrorDetailsDTO.getField());
				    responseMessageDetailDTOList.add(rmd);
				}
			}
			
			
			
			
			
			
			
			
		//	System.out.println("89898");
			
			
			if (responseMessageDetailDTOList.isEmpty())
			{
				//System.out.println("99898");//DB saving start

			OBLimit newLimit = facilityDetailsDTOMapper.getActualFromRestDTO(facilityRequestDTO);
			newLimit.setLineNo(facilityForm.getLineNo());
			newLimit.setPurpose(facilityForm.getPurpose());	
			newLimit.setFacilitySystem(facilityForm.getFacilitySystem());
			newLimit.setFacilityType(facilityForm.getFacilityType());
			newLimit.setFacilityCode(facilityForm.getFacilityCode());
			
			String risktypelinecode = PropertyManager.getValue("risktype.linecode");
			String risktypeval=PropertyManager.getValue("risktype.value");
			if(risktypelinecode.contains(facilityForm.getLineNo()))
			{
				newLimit.setRiskType(risktypeval);
				}

			OBTrxContext context = null;
			context = mcUtil.setContextForMaker();
			
			//context = setContextAsPerUserId(context,"CPUADM_A");

			OBLimitTrxValue limitTrxValue = new OBLimitTrxValue();
			limitTrxValue.setStagingLimit(newLimit);

			/*OBCommonUser user = null;
			ITeam[] teams = null;
			ITeamMembership[] memberships;

			user = (OBCommonUser) getUserProxy().getUser("CPUADM_C");
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
*/
			
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			helper.setTrxLocation(context, limitTrxValue.getStagingLimit());
			limitTrxValue.setCustomerID(cust.getCustomerID());
			limitTrxValue.setCustomerName(cust.getCustomerName());
			limitTrxValue.setLegalID(profile.getLEReference());
			limitTrxValue.setLegalName(cust.getCustomerName());
//			limitTrxValue.setTeamID(teams[0].getTeamID());
			limitTrxValue.setLimitProfileID(Long.parseLong(camId));
			limitTrxValue.setLimitProfileReferenceNumber(profile.getBCAReference());

			MISecurityUIHelper secHelper = new MISecurityUIHelper();
			SBMISecProxy secProxy = secHelper.getSBMISecProxy();
			ICMSTrxResult res = null;
			ICMSTrxResult response = null;
			ICMSTrxResult secRes = null;
			System.out.println("1212");
			
			
			if("Y".equals(facilityRequestDTO.getIsReleased()) && facilityRequestDTO.getLineList()!=null && !facilityRequestDTO.getLineList().isEmpty()
					&& facilityRequestDTO.getLineList().size()>0){
				ILimitSysXRef[] newLineAlloc = new ILimitSysXRef[facilityRequestDTO.getLineList().size()];
				List<FacilityLineDetailRestRequestDTO> lineList= facilityRequestDTO.getLineList();
				for(int i = 0;i<lineList.size();i++){
					
					FacilityLineDetailRestRequestDTO lineDTO = (FacilityLineDetailRestRequestDTO)lineList.get(i);
					ILimitSysXRef limitSysXref = new OBLimitSysXRef();
					OBCustomerSysXRef xrefobj = new OBCustomerSysXRef();
					
					xrefobj=facilityDetailsDTOMapper.getActualLineValFromRestDTO(lineDTO,facilityForm, xrefobj);

					limitSysXref.setCustomerSysXRef(xrefobj);
					limitSysXref.setStatus("ACTIVE");
					newLineAlloc[i]=limitSysXref;
				}
				newLimit.setLimitSysXRefs(newLineAlloc);
			}
			
			if(facilityRequestDTO.getSecured() != null && !(facilityRequestDTO.getSecured()).trim().isEmpty())
			{
				if("Y".equalsIgnoreCase(facilityRequestDTO.getSecured()))
				{
			if(facilityRequestDTO.getSecurityList()!=null && !facilityRequestDTO.getSecurityList().isEmpty()
					&& facilityRequestDTO.getSecurityList().size()>0){
				ICollateralAllocation[] newAlloc = new ICollateralAllocation[facilityRequestDTO.getSecurityList().size()];
				List<SecurityDetailRestRequestDTO> securityList= facilityRequestDTO.getSecurityList();
				for(int i = 0;i<securityList.size();i++){
					ICollateralTrxValue secTrxObj = new OBCollateralTrxValue();
					SecurityDetailRestRequestDTO securityDTO = (SecurityDetailRestRequestDTO)securityList.get(i);
					
					
					ICollateralDAO dao = CollateralDAOFactory.getDAO();
					OBCollateral collateral = new OBCollateral();
					if((securityDTO.getPartyId()!=null && !securityDTO.getPartyId().trim().isEmpty() ) ||
							securityDTO.getExistingSecurityId()!=null && !securityDTO.getExistingSecurityId().trim().isEmpty())
					{
						LmtColSearchCriteria crit = new LmtColSearchCriteria();
						crit.setLimitProfId(profile.getLimitProfileID());
			            crit.setSciSecId(securityDTO.getExistingSecurityId());
			            crit.setCustName(securityDTO.getPartyId());
						collateral= dao.searchCollateralByIdSubtypeRest(crit);
						
/*						if(null==collateral)
						{
							collateral=new OBCollateral();
							collateral.setCollateralLocation("IN");
							collateral.setCurrencyCode("INR");

							if(securityDTO.getSecurityPriority()!=null && !securityDTO.getSecurityPriority().trim().isEmpty()){
								collateral.setSecPriority(securityDTO.getSecurityPriority().trim());
							}else{
								collateral.setSecPriority("");
							}

							if(securityDTO.getCollateralCodeName()!=null && !securityDTO.getCollateralCodeName().trim().isEmpty()){
								collateral.setCollateralCode(securityDTO.getCollateralCodeName().trim());
							}else{
								collateral.setCollateralCode("");
							}

							if(securityDTO.getMonitorFrequencyColl()!=null && !securityDTO.getMonitorFrequencyColl().trim().isEmpty())
							{ 
								collateral.setMonitorFrequency(securityDTO.getMonitorFrequencyColl().trim());
							}else
							{
								collateral.setMonitorFrequency("");
							}
							if(securityDTO.getMonitorProcessColl()!=null && !securityDTO.getMonitorProcessColl().trim().isEmpty()){
								collateral.setMonitorProcess(securityDTO.getMonitorProcessColl().trim());
							}
							else{
								collateral.setMonitorProcess("");
							}

							if(securityDTO.getSecurityRefNote()!=null && !securityDTO.getSecurityRefNote().trim().isEmpty()){
								collateral.setSCIReferenceNote(securityDTO.getSecurityRefNote().trim());
							}else{
								collateral.setSCIReferenceNote("");
							}
							
							if(securityDTO.getSecurityBranch()!=null && !securityDTO.getSecurityBranch().trim().isEmpty()){
								collateral.setSecurityOrganization(securityDTO.getSecurityBranch().trim());
							}
							else
							{
								collateral.setSecurityOrganization("");
							}
							if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
								collateral.setLmtSecurityCoverage(securityDTO.getSecurityCoverage().trim());
							}
							else
							{
								collateral.setLmtSecurityCoverage("100");
							}
							

							collateral.setStatus("ACTIVE");

							if(securityDTO.getSecuritySubType()!=null && !securityDTO.getSecuritySubType().trim().isEmpty()){
								OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(securityDTO.getSecuritySubType().trim());
								collateral.setCollateralSubType(obSubType);
							}
							ICollateral stagingCol = collateral;
							secHelper.setTrxLocation(context, stagingCol);
							secHelper.setPledgorLocation(stagingCol);
							secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
							secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
							ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
							ICollateral newcol = colVal.getCollateral();
							OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
							nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
							nextAlloc.setCollateral(newcol);
							nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
							nextAlloc.setLimitProfileID(Long.parseLong(camId));
							nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
							if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
								nextAlloc.setLmtSecurityCoverage(securityDTO.getSecurityCoverage());							
								}
							else
							{
							nextAlloc.setLmtSecurityCoverage("100");
							}

							if(securityDTO.getUniqueReqId()!=null && !securityDTO.getUniqueReqId().trim().isEmpty()){
								nextAlloc.setCpsSecurityId(securityDTO.getUniqueReqId().trim());
							}else{
								nextAlloc.setCpsSecurityId("");
							}

							newAlloc[i] = nextAlloc;
						}*/
//							else
//							{
							
								OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
								nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
								nextAlloc.setCollateral(collateral);
								nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
								nextAlloc.setLimitProfileID(Long.parseLong(camId));
								nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
								if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
									nextAlloc.setLmtSecurityCoverage(securityDTO.getSecurityCoverage());
									System.out.print("====FacilityServiceFacade==3706=====nextAlloc.getLmtSecurityCoverage : "+nextAlloc.getLmtSecurityCoverage());
									}
								else
								{
								nextAlloc.setLmtSecurityCoverage("100");
								}


								if(securityDTO.getUniqueReqId()!=null && !securityDTO.getUniqueReqId().trim().isEmpty()){
									nextAlloc.setCpsSecurityId(securityDTO.getUniqueReqId().trim());
								}else{
									nextAlloc.setCpsSecurityId("");
								}

								newAlloc[i] = nextAlloc;
//							}

						}
					else
					{
//						collateral.setCollateralLocation("IN");
//						collateral.setCurrencyCode("INR");
						
						if(null != securityDTO.getSecurityCurrency() && !securityDTO.getSecurityCurrency().trim().isEmpty()){
							collateral.setCurrencyCode(securityDTO.getSecurityCurrency().trim());
						}else{
							collateral.setCurrencyCode("INR");
						}
						
						if(null != securityDTO.getSecurityCountry() && !securityDTO.getSecurityCountry().trim().isEmpty()){
							collateral.setCollateralLocation(securityDTO.getSecurityCountry().trim());
						}else{
							collateral.setCollateralLocation("IN");
						}
						

						if(securityDTO.getSecurityPriority()!=null && !securityDTO.getSecurityPriority().trim().isEmpty()){
							collateral.setSecPriority(securityDTO.getSecurityPriority().trim());
						}else{
							collateral.setSecPriority("");
						}

						if(securityDTO.getCollateralCodeName()!=null && !securityDTO.getCollateralCodeName().trim().isEmpty()){
							collateral.setCollateralCode(securityDTO.getCollateralCodeName().trim());
						}else{
							collateral.setCollateralCode("");
						}

						if(securityDTO.getMonitorFrequencyColl()!=null && !securityDTO.getMonitorFrequencyColl().trim().isEmpty())
						{ 
							collateral.setMonitorFrequency(securityDTO.getMonitorFrequencyColl().trim());
						}else
						{
							collateral.setMonitorFrequency("");
						}
						if(securityDTO.getMonitorProcessColl()!=null && !securityDTO.getMonitorProcessColl().trim().isEmpty()){
							collateral.setMonitorProcess(securityDTO.getMonitorProcessColl().trim());
						}
						else{
							collateral.setMonitorProcess("");
						}

						if(securityDTO.getSecurityRefNote()!=null && !securityDTO.getSecurityRefNote().trim().isEmpty()){
							collateral.setSCIReferenceNote(securityDTO.getSecurityRefNote().trim());
						}else{
							collateral.setSCIReferenceNote("");
						}
						
						if(securityDTO.getSecurityBranch()!=null && !securityDTO.getSecurityBranch().trim().isEmpty()){
							collateral.setSecurityOrganization(securityDTO.getSecurityBranch().trim());
						}
						else
						{
							collateral.setSecurityOrganization("");
						}
						if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
							collateral.setLmtSecurityCoverage(securityDTO.getSecurityCoverage().trim());
							System.out.print("====FacilityServiceFacade==3783=====collateral.getLmtSecurityCoverage : "+collateral.getLmtSecurityCoverage());
						}
						else
						{
							collateral.setLmtSecurityCoverage("100");
						}

						collateral.setStatus("ACTIVE");

						if(securityDTO.getSecuritySubType()!=null && !securityDTO.getSecuritySubType().trim().isEmpty()){
							OBCollateralSubType obSubType = (OBCollateralSubType) dao.getCollateralSubTypesBySubTypeCode(securityDTO.getSecuritySubType().trim());
							collateral.setCollateralSubType(obSubType);
						}
						ICollateral stagingCol = collateral;
						secHelper.setTrxLocation(context, stagingCol);
						secHelper.setPledgorLocation(stagingCol);
						secTrxObj.setStagingCollateral(secHelper.getCollateralBySubtype(stagingCol));
						secRes = secProxy.makerDirectCreateCollateralTrx(context, secTrxObj);
						ICollateralTrxValue colVal = (ICollateralTrxValue) secRes.getTrxValue();
						ICollateral newcol = colVal.getCollateral();
						OBCollateralAllocation nextAlloc = new OBCollateralAllocation();
						nextAlloc.setHostStatus(ICMSConstant.HOST_STATUS_INSERT);
						nextAlloc.setCollateral(newcol);
						nextAlloc.setCustomerCategory(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER);
						nextAlloc.setLimitProfileID(Long.parseLong(camId));
						nextAlloc.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
						if(securityDTO.getSecurityCoverage()!=null && !securityDTO.getSecurityCoverage().trim().isEmpty()){
							nextAlloc.setLmtSecurityCoverage(securityDTO.getSecurityCoverage());	
							System.out.print("====FacilityServiceFacade==3811=====nextAlloc.getLmtSecurityCoverage : "+nextAlloc.getLmtSecurityCoverage());
							}
						else
						{
						nextAlloc.setLmtSecurityCoverage("100");
						}

						
						if(securityDTO.getUniqueReqId()!=null && !securityDTO.getUniqueReqId().trim().isEmpty()){
							nextAlloc.setCpsSecurityId(securityDTO.getUniqueReqId().trim());
						}else{
							nextAlloc.setCpsSecurityId("");
						}
						
						newAlloc[i] = nextAlloc;
					}
					
				}
				newLimit.setCollateralAllocations(newAlloc);
			}

			}}

			if(facilityRequestDTO.getBorrowerList()!=null && !facilityRequestDTO.getBorrowerList().isEmpty()
					&& facilityRequestDTO.getBorrowerList().size()>0){
			List<IFacilityCoBorrowerDetails> facCoBorrowerDetailsList = new ArrayList<IFacilityCoBorrowerDetails>();

			List<CoBorrowerDetailsRestRequestDTO> coborrowerList = facilityRequestDTO.getBorrowerList();
			
			for(CoBorrowerDetailsRestRequestDTO coBorrowerId : coborrowerList) {
				IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
				facCoBorrower.setCoBorrowerLiabId(coBorrowerId.getCoBorrowerLiabId());
				facCoBorrower.setCoBorrowerName(coBorrowerId.getCoBorrowerLiabId()+"-"+coBorrowerId.getCoBorrowerName());
//				facCoBorrower.setCoBorrowerName(coBorrowerId.getCoBorrowerName());
				facCoBorrower.setCreateBy("CMS");
				facCoBorrower.setCreationDate(UIUtil.getDate());
				facCoBorrower.setMainProfileId(Long.parseLong(facilityRequestDTO.getCamId()));
				
				facCoBorrowerDetailsList.add(facCoBorrower);
			}
			
			newLimit.setCoBorrowerDetails(facCoBorrowerDetailsList);
			}
			
			
			
			if(facilityRequestDTO.getIsScod() != null && !(facilityRequestDTO.getIsScod()).trim().isEmpty())
			{
				if("Y".equalsIgnoreCase(facilityRequestDTO.getIsScod()))
				{
					String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
					System.out.println("scodLineNoList ====>"+scodLineNoList);

					if(!(null == facilityForm.getLineNo()|| facilityForm.getLineNo().isEmpty()) 
							&& (scodLineNoList.contains(facilityForm.getLineNo())))
					{
						//FacilitySCODDetailRestRequestDTO facilityRequestscod = facilityDetailsDTOMapper.getActualVAluesFromInputSCOD(facilityDetailsRestRequest);
						
						newLimit=facilityDetailsDTOMapper.getActualVAluesFromInputSCODToOB(facilityRequestscod, newLimit);
					}
					
				}
			}
				
			
			
			
			
			
			
			
			

	/*
	 * 26-FEB-2016 : When adding/updating facility through CAM online, sanctioned amount of existing facilities (Not created through CAMonline)
	 * should be zeroed - CR raised by bank : CLIMS validation for existing cases for facility/sanctioned amount 
	 * 
	 * Logic: Existing facility's sanctioned amount will be zeroed (i.e. CMS_REQ_SEC_COVERAGE column value) for CAM Id sent through facility request
	 * 
	 * 
	 * */
			
//			proxy.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "Before calling method...limitDAO.updateSanctionedLimitToZero()");
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateSanctionedLimitToZero(camId);
			DefaultLogger.debug(this, "After calling method...limitDAO.updateSanctionedLimitToZero()");
			
			
			res = proxy.createSubmitFacility(context, limitTrxValue,false);

			/*
			//maker create process
			res = proxy.createLimitTrx(context, limitTrxValue, false);
			limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
			//Setting user related information in the context
			context = setContextAsPerUserId(context,"CPUADM_C");
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			//Checker approve process
			response = proxy.checkerApproveLmtTrx(context, limitTrxValue);
			 */
			if(res != null && res.getTrxValue() != null){
				limitTrxValue = (OBLimitTrxValue)proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
				bodyObj.setLimitId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				bodyObj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitRef()));
				ICollateralAllocation[] collateralalloc= limitTrxValue.getLimit().getCollateralAllocations();
				System.out.println("121256");
				ILimitSysXRef[] newLineNum = limitTrxValue.getLimit().getLimitSysXRefs();
				
				//code in case of maker only
				
				
				/*bodyObj.setFacilityId(String.valueOf(limitTrxValue.getStagingLimit().getLimitID()));

				//bodyObj.setFacilityId(String.valueOf(limitTrxValue.getLimit().getLimitID()));
				ICollateralAllocation[] collateralalloc= limitTrxValue.getStagingLimit().getCollateralAllocations();
				System.out.println("121256");
				ILimitSysXRef[] newLineNum = limitTrxValue.getStagingLimit().getLimitSysXRefs();*/
				
				if(facilityRequestDTO.getSecured()!=null && "Y".equalsIgnoreCase(facilityRequestDTO.getSecured())){
					System.out.println("190902");
				if(collateralalloc!=null && collateralalloc.length>0){
					for(int i =0;i<collateralalloc.length;i++){
						SecurityDetailRestResponseDTO dto = new SecurityDetailRestResponseDTO();
						ICollateralAllocation alloc = collateralalloc[i];
						if(alloc.getHostStatus()!=null && alloc.getHostStatus().equalsIgnoreCase("I") && alloc.getCpsSecurityId()!=null){
							dto.setUniqueReqId(alloc.getCpsSecurityId());
							dto.setSecurityId(String.valueOf(alloc.getCollateral().getCollateralID()));
							secList.add(dto);
						}
					}
				}
				}
				else{
					System.out.println("00092");
					SecurityDetailRestResponseDTO dto = new SecurityDetailRestResponseDTO();
					dto.setCpsSecurityId(" ");
					dto.setSecurityId(" ");
					secList.add(dto);
				}
				facilityResponse.setSecurityResponseList(secList);
				facilityResponse.setResponseStatus("FACILITY_CREATED_SUCCESSFULLY");
				
				facResponseDTOList.add(facilityResponse);
				headerObj.setRequestId(facilityDetailsRestRequest.getHeaderDetails().get(0).getRequestId());
				headerObj.setChannelCode(facilityDetailsRestRequest.getHeaderDetails().get(0).getChannelCode());
				ccHeaderResponse.add(headerObj);
					
				
				ResponseMessageDetailDTO responseMessageDetailDTO =new ResponseMessageDetailDTO();
				responseMessageDetailDTO.setResponseCode(ResponseConstants.FAC_SUCCESS_RESPONSE_CODE);
				responseMessageDetailDTO.setResponseMessage(ResponseConstants.FAC_ADD_SUCCESS_MESSAGE);
				responseMessageList.add(responseMessageDetailDTO);
		
				bodyObj.setResponseStatus("SUCCESS");
				bodyObj.setResponseMessageList(responseMessageList);
				
				bodyObj.setSecurityResponseList(secList);
				BodyRestList.add(bodyObj);
				
				
				commonCodeRestResponseDTO.setHeaderDetails(ccHeaderResponse);
				commonCodeRestResponseDTO.setBodyDetails(BodyRestList);
			}
			}
			else {
				//bodyObj.setPartyId("");
				bodyObj.setResponseStatus("FAILED");
				bodyObj.setResponseMessageList(responseMessageDetailDTOList);
				BodyRestList.add(bodyObj);
				commonCodeRestResponseDTO.setBodyDetails(BodyRestList);	
			}
			return commonCodeRestResponseDTO;
		} catch (LimitException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new CMSException(e.getMessage(),e);
		} catch (CollateralException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(),e);
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
	}

	public ILimit  updateSCODFacilityDetailsRest(FacilitySCODDetailRestRequestDTO facilityRequest , ILimit limit)
			throws CMSValidationFault, CMSFault {
		OBTrxContext context = null;
		ILimitProfile profile = null;
		ILimitProxy limitProxy = null;
		ICMSTrxResult response = null;
		List newAlloc = new ArrayList();
		HashMap facMap = new HashMap();
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");

		/*if (facilityRequest.getWsConsumer() == null || facilityRequest.getWsConsumer().trim().isEmpty()) {
			throw new CMSException("wsConsumer is mandatory");
		}*/
		FacilityDetailsDTOMapper facilityDetailsDTOMapper = new FacilityDetailsDTOMapper();
		FacilitySCODDetailRestRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getSCODRestRequestDTOWithActualValues(facilityRequest);
		ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();

		HashMap map = new HashMap();
		/*if (cpsIdErrors.size() > 0) {
			map.put("1", cpsIdErrors);
			ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
		}
*/
		String camId = "";
		try {
			limitProxy = LimitProxyFactory.getProxy();
			context = mcUtil.setContextForMaker();// setContextAsPerUserId(context,"CPUADM_A");
			if (facilityRequest.getCamId() != null && !facilityRequest.getCamId().trim().isEmpty()) {
				camId = facilityRequest.getCamId().trim();
				profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			}
			context.setLimitProfile(profile);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		}
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(), null);

		context.setCustomer(cust);

		LmtDetailForm facilityForm = facilityDetailsDTOMapper.getSCODFormFromRestDTO(facilityDetailRequestDTO, cust);
		if("Initial".equals(facilityDetailRequestDTO.getCamType())) {
			facilityForm.setEvent("WS_SCOD_FAC_EDIT_INITIAL");
		}else {
			facilityForm.setEvent("WS_SCOD_FAC_EDIT");
		}
		
		FacilitySCODRestResponseDTO obj = new FacilitySCODRestResponseDTO();
		/*ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();*/
		try {
			/*try {
				if (facilityRequest.getClimsFacilityId() != null
						&& !facilityRequest.getClimsFacilityId().trim().isEmpty()) {
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			} catch (Exception e) {
				throw new CMSValidationFault("climsFacilityId", "Invalid climsFacilityId");
			}

			if (lmtTrxObj != null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_DELETE")) || (lmtTrxObj.getStatus().equals("REJECTED"))
					|| (lmtTrxObj.getStatus().equals("DRAFT")) || (lmtTrxObj.getStatus().equals("DELETED")))) {
				throw new CMSValidationFault("transactionStatus",
						"Unable to update due to invalid transaction Status :" + lmtTrxObj.getStatus());
			}*/

			//ILimit limit = (ILimit) lmtTrxObj.getLimit();
			
			if(!"WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
			if(!"".equals(limit.getScodDate()) && limit.getScodDate() != null) {
			facilityForm.setScodDate(DateUtil.formatDate(Locale.getDefault(),limit.getScodDate()));
			}
			if(!"".equals(limit.getInfaProjectFlag()) && limit.getInfaProjectFlag() != null) {
				facilityForm.setInfaProjectFlag(limit.getInfaProjectFlag());
				}
			if(!"".equals(limit.getProjectFinance()) && limit.getProjectFinance() != null) {
				facilityForm.setProjectFinance(limit.getProjectFinance());
				}
			if(!"".equals(limit.getProjectLoan()) && limit.getProjectLoan() != null) {
				facilityForm.setProjectLoan(limit.getProjectLoan());
				}
			if(!"".equals(limit.getRemarksSCOD()) && limit.getRemarksSCOD() != null) {
				facilityForm.setRemarksSCOD(limit.getRemarksSCOD());
				}
			
			if(!"".equals(limit.getExstAssetClass()) && limit.getExstAssetClass() != null) {
				facilityForm.setExstAssetClass(limit.getExstAssetClass());
				}
			
			if(!"".equals(limit.getExstAssClassDate()) && limit.getExstAssClassDate() != null) {
				facilityForm.setExstAssClassDate(DateUtil.formatDate(Locale.getDefault(),limit.getExstAssClassDate()));
				}
			}
			
			if("WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
				if(limit.getScodDate() != null && limit.getProjectFinance()!=null && limit.getInfaProjectFlag() != null && limit.getProjectLoan() != null && limit.getRemarksSCOD() != null && limit.getExstAssetClass() != null) {
					throw new CMSException("Initial CAM already proceed for this facility.");
				}
			}
			
			if(!"WS_SCOD_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
				
				if(limit.getScodDate() == null && limit.getProjectFinance()==null && limit.getInfaProjectFlag() == null && limit.getProjectLoan() == null ) {
					throw new CMSException("Please proceed Initial CAM for this facility.");
				}
			}
		ActionErrors facilityErrorList = MILimitValidatorRest.validateMILimit(facilityForm, Locale.getDefault(), null );
		// code for facility field validation
		if (facilityErrorList.size() > 0) {
			facMap.put("1", facilityErrorList);
			ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
		}

		/*FacilitySCODResponseDTO obj = new FacilitySCODResponseDTO();
		ILimitTrxValue lmtTrxObj = null;
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			try {
				if (facilityRequest.getClimsFacilityId() != null
						&& !facilityRequest.getClimsFacilityId().trim().isEmpty()) {
					lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
				}
			} catch (Exception e) {
				throw new CMSValidationFault("climsFacilityId", "Invalid climsFacilityId");
			}

			if (lmtTrxObj != null && ((lmtTrxObj.getStatus().equals("PENDING_CREATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_UPDATE"))
					|| (lmtTrxObj.getStatus().equals("PENDING_DELETE")) || (lmtTrxObj.getStatus().equals("REJECTED"))
					|| (lmtTrxObj.getStatus().equals("DRAFT")) || (lmtTrxObj.getStatus().equals("DELETED")))) {
				throw new CMSValidationFault("transactionStatus",
						"Unable to update due to invalid transaction Status :" + lmtTrxObj.getStatus());
			}*/

			//Date actualDate = (lmtTrxObj.getLimit()!=null) ? lmtTrxObj.getLimit().getScodDate() : null;
			//ILimit limit = (ILimit) lmtTrxObj.getLimit();

			//Initial
			if (facilityDetailRequestDTO.getProjectFinance() != null
					&& !facilityDetailRequestDTO.getProjectFinance().isEmpty()) {
				limit.setProjectFinance(facilityDetailRequestDTO.getProjectFinance().trim());
			}

			if (facilityDetailRequestDTO.getProjectLoan() != null
					&& !facilityDetailRequestDTO.getProjectLoan().isEmpty()) {
				limit.setProjectLoan(facilityDetailRequestDTO.getProjectLoan().trim());
			}

			if (limit != null && facilityDetailRequestDTO.getInfraFlag() != null
					&& !facilityDetailRequestDTO.getInfraFlag().trim().isEmpty()) {
				limit.setInfaProjectFlag(facilityDetailRequestDTO.getInfraFlag());
			}
			
			try {
				if (facilityDetailRequestDTO.getScod()!=null 
						&& !facilityDetailRequestDTO.getScod().trim().isEmpty()) {
					limit.setScodDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getScod().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}

			if (facilityDetailRequestDTO.getScodRemark() != null
					&& !facilityDetailRequestDTO.getScodRemark().trim().isEmpty()) {
				limit.setRemarksSCOD(facilityDetailRequestDTO.getScodRemark());
			}

			if (facilityDetailRequestDTO.getExeAssetClass() != null
					&& !facilityDetailRequestDTO.getExeAssetClass().trim().isEmpty()) {
				limit.setExstAssetClass(facilityDetailRequestDTO.getExeAssetClass().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getExeAssetClassDate()!=null 
						&& !facilityDetailRequestDTO.getExeAssetClassDate().trim().isEmpty()) {
					limit.setExstAssClassDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getExeAssetClassDate().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Interim 
			
			if (facilityDetailRequestDTO.getLimitReleaseFlg() != null
					&& !facilityDetailRequestDTO.getLimitReleaseFlg().isEmpty()) {
				limit.setWhlmreupSCOD(facilityDetailRequestDTO.getLimitReleaseFlg().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSched() != null
					&& !facilityDetailRequestDTO.getRepayChngSched().isEmpty()) {
				limit.setChngInRepaySchedule(facilityDetailRequestDTO.getRepayChngSched().trim());
			}
			
			if (facilityDetailRequestDTO.getRevAssetClass()!= null
					&& !facilityDetailRequestDTO.getRevAssetClass().isEmpty()) {
				limit.setRevisedAssetClass(facilityDetailRequestDTO.getRevAssetClass().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDt()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDt().trim().isEmpty()) {
					limit.setRevsdAssClassDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDt().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			try {
				if (facilityDetailRequestDTO.getAcod()!=null 
						&& !facilityDetailRequestDTO.getAcod().trim().isEmpty()) {
					limit.setActualCommOpsDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getAcod().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 1
			
			if (facilityDetailRequestDTO.getDelayFlagL1() != null
					&& !facilityDetailRequestDTO.getDelayFlagL1().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getInterestFlag() != null
					&& !facilityDetailRequestDTO.getInterestFlag().isEmpty()) {
				limit.setPrincipalInterestSchFlag(facilityDetailRequestDTO.getInterestFlag().trim());
			}
			
			if (facilityDetailRequestDTO.getPriorReqFlag() != null
					&& !facilityDetailRequestDTO.getPriorReqFlag().isEmpty()) {
				limit.setRecvPriorOfSCOD(facilityDetailRequestDTO.getPriorReqFlag().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL1() != null
					&& !facilityDetailRequestDTO.getDefReasonL1().isEmpty()) {
				limit.setReasonLevelOneDelay(facilityDetailRequestDTO.getDefReasonL1().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL1() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL1().isEmpty()) {
				limit.setChngInRepaySchedule(facilityDetailRequestDTO.getRepayChngSchedL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL1()!=null 
						&& !facilityDetailRequestDTO.getEscodL1().trim().isEmpty()) {
					limit.setEscodLevelOneDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL1().isEmpty()) {
				limit.setPromotersCapRunFlag(facilityDetailRequestDTO.getOwnershipQ1FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL1().isEmpty()) {
				limit.setPromotersHoldEquityFlag(facilityDetailRequestDTO.getOwnershipQ2FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL1()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL1().isEmpty()) {
				limit.setHasProjectViabReAssFlag(facilityDetailRequestDTO.getOwnershipQ3FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL1().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlag(facilityDetailRequestDTO.getScopeQ1FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL1().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlag(facilityDetailRequestDTO.getScopeQ2FlagL1().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL1()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL1().isEmpty()) {
				limit.setProjectViabReassesedFlag(facilityDetailRequestDTO.getScopeQ3FlagL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL1()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL1().trim().isEmpty()) {
					limit.setRevsedESCODStpDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL1(limit.getExstAssetClass().trim());
			}
			
			//try {
				if (limit.getExstAssClassDate()!=null ) {
					limit.setExstAssClassDateL1(limit.getExstAssClassDate());
				}
				/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL1()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL1().isEmpty()) {
				limit.setRevisedAssetClassL1(facilityDetailRequestDTO.getRevAssetClassL1().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL1()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL1().trim().isEmpty()) {
					limit.setRevsdAssClassDateL1(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL1().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 2
			
			if (facilityDetailRequestDTO.getDelayFlagL2() != null
					&& !facilityDetailRequestDTO.getDelayFlagL2().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL2() != null
					&& !facilityDetailRequestDTO.getDefReasonL2().isEmpty()) {
				limit.setReasonLevelTwoDelay(facilityDetailRequestDTO.getDefReasonL2().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL2() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL2().isEmpty()) {
				limit.setChngInRepayScheduleL2(facilityDetailRequestDTO.getRepayChngSchedL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL2()!=null 
						&& !facilityDetailRequestDTO.getEscodL2().trim().isEmpty()) {
					limit.setEscodLevelSecondDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ1L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ1L2().isEmpty()) {
				limit.setLegalOrArbitrationLevel2Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ1L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ2L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ2L2().isEmpty()) {
				limit.setReasonBeyondPromoterLevel2Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ2L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ3L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ3L2().isEmpty()) {
				limit.setChngOfOwnPrjFlagInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ3L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ4L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ4L2().isEmpty()) {
				limit.setChngOfProjScopeInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ4L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ5L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ5L2().isEmpty()) {
				limit.setChngOfOwnPrjFlagNonInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ5L2().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ6L2() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ6L2().isEmpty()) {
				limit.setChngOfProjScopeNonInfraLevel2(facilityDetailRequestDTO.getEscodRevisionReasonQ6L2().trim());
			}
			
			if (facilityDetailRequestDTO.getLegalDetailL2() != null
					&& !facilityDetailRequestDTO.getLegalDetailL2().isEmpty()) {
				limit.setLeaglOrArbiProceed(facilityDetailRequestDTO.getLegalDetailL2().trim());
			}
			
			if (facilityDetailRequestDTO.getBeyondControlL2() != null
					&& !facilityDetailRequestDTO.getBeyondControlL2().isEmpty()) {
				limit.setDetailsRsnByndPro(facilityDetailRequestDTO.getBeyondControlL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL2().isEmpty()) {
				limit.setPromotersCapRunFlagL2(facilityDetailRequestDTO.getOwnershipQ1FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL2().isEmpty()) {
				limit.setPromotersHoldEquityFlagL2(facilityDetailRequestDTO.getOwnershipQ2FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL2()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL2().isEmpty()) {
				limit.setHasProjectViabReAssFlagL2(facilityDetailRequestDTO.getOwnershipQ3FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL2().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlagL2(facilityDetailRequestDTO.getScopeQ1FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL2().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlagL2(facilityDetailRequestDTO.getScopeQ2FlagL2().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL2()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL2().isEmpty()) {
				limit.setProjectViabReassesedFlagL2(facilityDetailRequestDTO.getScopeQ3FlagL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL2()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL2().trim().isEmpty()) {
					limit.setRevsedESCODStpDateL2(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL2(limit.getExstAssetClass().trim());
			}
			
			//try {
				if (limit.getExstAssClassDate()!=null) {
					limit.setExstAssClassDateL2(limit.getExstAssClassDate());
				}
			/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL2()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL2().isEmpty()) {
				limit.setRevisedAssetClass_L2(facilityDetailRequestDTO.getRevAssetClassL2().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL2()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL2().trim().isEmpty()) {
					limit.setRevsdAssClassDate_L2(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL2().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Level 3
			
			if (facilityDetailRequestDTO.getDelayFlagL3() != null
					&& !facilityDetailRequestDTO.getDelayFlagL3().isEmpty()) {
				limit.setProjectDelayedFlag(facilityDetailRequestDTO.getDelayFlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getDelaylevel() != null
					&& !facilityDetailRequestDTO.getDelaylevel().isEmpty()) {
				limit.setLelvelDelaySCOD(facilityDetailRequestDTO.getDelaylevel().trim());
			}
			
			if (facilityDetailRequestDTO.getDefReasonL3() != null
					&& !facilityDetailRequestDTO.getDefReasonL3().isEmpty()) {
				limit.setReasonLevelThreeDelay(facilityDetailRequestDTO.getDefReasonL3().trim());
			}
			
			if (facilityDetailRequestDTO.getRepayChngSchedL3() != null
					&& !facilityDetailRequestDTO.getRepayChngSchedL3().isEmpty()) {
				limit.setChngInRepayScheduleL3(facilityDetailRequestDTO.getRepayChngSchedL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getEscodL3()!=null 
						&& !facilityDetailRequestDTO.getEscodL3().trim().isEmpty()) {
					limit.setEscodLevelThreeDelayDate(relationshipDateFormat.parse(facilityDetailRequestDTO.getEscodL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ1L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ1L3().isEmpty()) {
				limit.setLegalOrArbitrationLevel3Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ1L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ2L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ2L3().isEmpty()) {
				limit.setReasonBeyondPromoterLevel3Flag(facilityDetailRequestDTO.getEscodRevisionReasonQ2L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ3L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ3L3().isEmpty()) {
				limit.setChngOfOwnPrjFlagInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ3L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ4L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ4L3().isEmpty()) {
				limit.setChngOfProjScopeInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ4L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ5L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ5L3().isEmpty()) {
				limit.setChngOfOwnPrjFlagNonInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ5L3().trim());
			}
			
			if (facilityDetailRequestDTO.getEscodRevisionReasonQ6L3() != null
					&& !facilityDetailRequestDTO.getEscodRevisionReasonQ6L3().isEmpty()) {
				limit.setChngOfProjScopeNonInfraLevel3(facilityDetailRequestDTO.getEscodRevisionReasonQ6L3().trim());
			}
			
			if (facilityDetailRequestDTO.getLegalDetailL3() != null
					&& !facilityDetailRequestDTO.getLegalDetailL3().isEmpty()) {
				limit.setLeaglOrArbiProceedLevel3(facilityDetailRequestDTO.getLegalDetailL3().trim());
			}
			
			if (facilityDetailRequestDTO.getBeyondControlL3() != null
					&& !facilityDetailRequestDTO.getBeyondControlL3().isEmpty()) {
				limit.setDetailsRsnByndProLevel3(facilityDetailRequestDTO.getBeyondControlL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ1FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ1FlagL3().isEmpty()) {
				limit.setPromotersCapRunFlagL3(facilityDetailRequestDTO.getOwnershipQ1FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ2FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ2FlagL3().isEmpty()) {
				limit.setPromotersHoldEquityFlagL3(facilityDetailRequestDTO.getOwnershipQ2FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getOwnershipQ3FlagL3()!= null
					&& !facilityDetailRequestDTO.getOwnershipQ3FlagL3().isEmpty()) {
				limit.setHasProjectViabReAssFlagL3(facilityDetailRequestDTO.getOwnershipQ3FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ1FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ1FlagL3().isEmpty()) {
				limit.setChangeInScopeBeforeSCODFlagL3(facilityDetailRequestDTO.getScopeQ1FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ2FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ2FlagL3().isEmpty()) {
				limit.setCostOverrunOrg25CostViabilityFlagL3(facilityDetailRequestDTO.getScopeQ2FlagL3().trim());
			}
			
			if (facilityDetailRequestDTO.getScopeQ3FlagL3()!= null
					&& !facilityDetailRequestDTO.getScopeQ3FlagL3().isEmpty()) {
				limit.setProjectViabReassesedFlagL3(facilityDetailRequestDTO.getScopeQ3FlagL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevisedEscodL3()!=null 
						&& !facilityDetailRequestDTO.getRevisedEscodL3().trim().isEmpty()) {
					limit.setRevsedESCODStpDateL3(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevisedEscodL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (limit.getExstAssetClass()!= null
					&& !limit.getExstAssetClass().isEmpty()) {
				limit.setExstAssetClassL3(limit.getExstAssetClass().trim());
			}
			
			/*try {*/
				if (limit.getExstAssClassDate()!=null) {
					limit.setExstAssClassDateL3(limit.getExstAssClassDate());
				}
			/*}catch (ParseException e) {
				e.printStackTrace();
			}*/
			
			if (facilityDetailRequestDTO.getRevAssetClassL3()!= null
					&& !facilityDetailRequestDTO.getRevAssetClassL3().isEmpty()) {
				limit.setRevisedAssetClass_L3(facilityDetailRequestDTO.getRevAssetClassL3().trim());
			}
			
			try {
				if (facilityDetailRequestDTO.getRevAssetClassDtL3()!=null 
						&& !facilityDetailRequestDTO.getRevAssetClassDtL3().trim().isEmpty()) {
					limit.setRevsdAssClassDate_L3(relationshipDateFormat.parse(facilityDetailRequestDTO.getRevAssetClassDtL3().trim()));
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			/*
			lmtTrxObj.setStagingLimit(limit);
			ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj, false);*/
			
			//Start SCOD Deferral
			/*if(lmtTrxObj.getLimit()!=null && lmtTrxObj.getLimit().getFacilityName()!=null) {
				String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
				System.out.println("API... scodLineNoList......."+scodLineNoList);
				Date scodStage = lmtTrxObj.getStagingLimit().getScodDate();
				boolean isEquals=true;
				try {
					SimpleDateFormat sdformat = new SimpleDateFormat("dd/MMM/yyyy");
					if(lmtTrxObj.getLimit()!=null && lmtTrxObj.getLimit().getScodDate()!=null) {
						Date actual = sdformat.parse(sdformat.format(actualDate));
						Date stage = sdformat.parse(sdformat.format(lmtTrxObj.getStagingLimit().getScodDate()));	
						if(!(stage.compareTo(actual) > 0)) {
							isEquals=false;
						}
					}
				}catch(Exception ex) {
					System.out.println("API... Exception......."+ex);
				}
				System.out.println("API... isEquals......."+isEquals);
			
				if(scodLineNoList != null) {
				if(!(lmtTrxObj.getStagingLimit().getLineNo()==null || lmtTrxObj.getStagingLimit().getLineNo().isEmpty()) 
					&& (scodLineNoList.contains(lmtTrxObj.getStagingLimit().getLineNo()))
					&& isEquals) {
					System.out.println("API...Before calling generateDeferralsForSCOD.......");
					//ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal1.setTime(scodStage);
					cal2.setTime(new Date());
				
					if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
						if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
								&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=2 && "Initial".equals(profile.getCamType())) {
							GenerateDeferralsForSCOD.generateDeferralsForSCOD(context, lmtTrxObj, profile, new Date(), res);
						} else if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
								&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=1 
								&& ("Interim".equals(profile.getCamType()) || "Annual".equals(profile.getCamType()))) {
							GenerateDeferralsForSCOD.generateDeferralsForSCOD(context, lmtTrxObj, profile, new Date(), res);
						} 
					} 
					System.out.println("API... After calling generateDeferralsForSCOD.......");
				}
				}
			}
			//End SCOD Deferral
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			limitDAO.updateFlagsFlagForLMTSTP(lmtTrxObj);
			obj.setResponseStatus("FACILITY_UPDATED_SUCCESSFULLY");
			obj.setTransactionID(lmtTrxObj.getTransactionID());
			obj.setFacilityId(String.valueOf(limit.getLimitID()));*/

		} catch (CMSValidationFault e) {
			throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
		} catch (NumberFormatException e) {
			throw new CMSException(e.getMessage(), e);
		} catch (Exception e) {
			throw new CMSException(e.getMessage(), e);
		}
		return limit;
	}
	
	@CLIMSWebServiceMethod
public AdhocFacilityResponseDTO updateAdhocFacility(AdhocFacilityRequestDTO facilityRequest)
			throws CMSValidationFault, CMSFault {
	OBTrxContext context = null;
	ILimitProfile profile = null;
	ILimitProxy limitProxy = null;
	ICMSTrxResult response = null;
	List newAlloc = new ArrayList();
	HashMap facMap = new HashMap();
	SimpleDateFormat adhocDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
	MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
 //  System.out.println("facilityRequest.getWsConsumer():: "+facilityRequest.getWsConsumer());
	
   if ( null == facilityRequest.getWsConsumer()  || facilityRequest.getWsConsumer().trim().isEmpty()) {
		throw new CMSException("wsConsumer is mandatory");
	}

	AdhocFacilityRequestDTO facilityDetailRequestDTO = facilityDetailsDTOMapper.getAdhocRequestDTOWithActualValues(facilityRequest);
	ActionErrors cpsIdErrors = facilityDetailRequestDTO.getErrors();
  //System.out.println("cpsIdErrors.size():: "+cpsIdErrors.size());
	HashMap map = new HashMap();
	if (cpsIdErrors.size() > 0) {
		map.put("1", cpsIdErrors);
		ValidationUtility.handleError(map, CLIMSWebService.FACILITY);
	}

	String camId = "";
	try {
		limitProxy = LimitProxyFactory.getProxy();
		context = mcUtil.setContextForMaker();// setContextAsPerUserId(context,"CPUADM_A");
		if ( null != facilityRequest.getCamId()  && !facilityRequest.getCamId().trim().isEmpty()) {
			camId = facilityRequest.getCamId().trim();
		//	System.out.println("####### camId:: "+camId);
			profile = limitProxy.getLimitProfile(Long.parseLong(camId));
		}
		context.setLimitProfile(profile);
	} catch (NumberFormatException e1) {
		e1.printStackTrace();
	//	System.out.println("Exception_1:::: "+e1.getMessage());
	} catch (LimitException e1) {
	//	System.out.println("Exception_1:::: "+e1.getMessage());
		e1.printStackTrace();
	}
	ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
	ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(), null);
	context.setCustomer(cust);

	LmtDetailForm facilityForm = facilityDetailsDTOMapper.getAdhocFormFromDTO(facilityDetailRequestDTO, cust);
	if("Initial".equals(facilityDetailRequestDTO.getCamType())) {
		facilityForm.setEvent("WS_ADHOC_FAC_EDIT_INITIAL");
	}else {
		facilityForm.setEvent("WS_ADHOC_FAC_EDIT");
	}
	AdhocFacilityResponseDTO obj = new AdhocFacilityResponseDTO();
	ILimitTrxValue lmtTrxObj = null;
	MILimitUIHelper helper = new MILimitUIHelper();
	SBMILmtProxy proxy = helper.getSBMILmtProxy();
	try {
		try {
			if (null != facilityRequest.getClimsFacilityId() && !facilityRequest.getClimsFacilityId().trim().isEmpty())
			{
				lmtTrxObj = proxy.searchLimitByLmtId(facilityRequest.getClimsFacilityId().trim());
			}
		} catch (Exception e) {
				e.printStackTrace();
			throw new CMSValidationFault("climsFacilityId", "Invalid climsFacilityId");
		}

		if ( null != lmtTrxObj  && ( ("PENDING_CREATE".equals(lmtTrxObj.getStatus()))
				|| ("PENDING_UPDATE".equals(lmtTrxObj.getStatus())) || ("REJECTED".equals(lmtTrxObj.getStatus()))
				|| ("DRAFT".equals(lmtTrxObj.getStatus())) || ("DELETED".equals(lmtTrxObj.getStatus()))				
				)) {
			throw new CMSValidationFault("transactionStatus",
					"Unable to update due to invalid transaction Status :" + lmtTrxObj.getStatus());
		}

		ILimit limit = (ILimit) lmtTrxObj.getLimit();
		if(!"WS_ADHOC_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
		
		if(!"".equals(limit.getAdhocFacility()) && null != limit.getAdhocFacility()) {
			facilityForm.setAdhocFacility(limit.getAdhocFacility());
			}
		if(!"".equals(limit.getAdhocTenor()) && null !=  limit.getAdhocTenor()) {
			facilityForm.setAdhocTenor(limit.getAdhocTenor());
			}
		
		if(!"".equals(limit.getAdhocFacilityExpDate()) && null !=  limit.getAdhocFacilityExpDate()) {
			facilityForm.setAdhocFacilityExpDate(DateUtil.formatDate(Locale.getDefault(),limit.getAdhocFacilityExpDate()));
			}
		if(!"".equals(limit.getAdhocLastAvailDate()) && null !=  limit.getAdhocLastAvailDate()) {
			facilityForm.setAdhocLastAvailDate(DateUtil.formatDate(Locale.getDefault(),limit.getAdhocLastAvailDate()));
			}
		
		if(!"".equals(limit.getMultiDrawdownAllow()) && null !=  limit.getMultiDrawdownAllow()) {
			facilityForm.setMultiDrawdownAllow(limit.getMultiDrawdownAllow());
			}
			
		if(!"".equals(limit.getGeneralPurposeLoan()) && null !=  limit.getGeneralPurposeLoan()) {
			facilityForm.setGeneralPurposeLoan(limit.getGeneralPurposeLoan());
			}
		
		}
		if("WS_ADHOC_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
     
			if(null !=  limit.getAdhocFacility() && null !=  limit.getGeneralPurposeLoan()) {
				throw new CMSException("Initial CAM already proceed for this facility.");
			}
		}
		
		if(!"WS_ADHOC_FAC_EDIT_INITIAL".equals(facilityForm.getEvent())) {
			
			if(null == limit.getAdhocFacility() && null == limit.getGeneralPurposeLoan() ) {
				throw new CMSException("Please proceed Initial CAM for this facility.");
			}
		}
	
		
	ActionErrors facilityErrorList = MILimitValidator.validateMILimit(facilityForm, Locale.getDefault() );
	// code for facility field validation
	if (facilityErrorList.size() > 0) {
		facMap.put("1", facilityErrorList);
		ValidationUtility.handleError(facMap, CLIMSWebService.FACILITY);
	}

	//Initial
		if (null != facilityDetailRequestDTO.getAdhocFacility() 
				&& !facilityDetailRequestDTO.getAdhocFacility().isEmpty()) {
			limit.setAdhocFacility(facilityDetailRequestDTO.getAdhocFacility().trim());
		}

		if (null != limit  &&  null != facilityDetailRequestDTO.getAdhocTenor()
				&& !facilityDetailRequestDTO.getAdhocTenor().trim().isEmpty()) {
			limit.setAdhocTenor(facilityDetailRequestDTO.getAdhocTenor());
		}
		
		try {
			if (null != facilityDetailRequestDTO.getAdhocFacilityExpDate() 
					&& !facilityDetailRequestDTO.getAdhocFacilityExpDate().trim().isEmpty()) {
				limit.setAdhocFacilityExpDate(adhocDateFormat.parse(facilityDetailRequestDTO.getAdhocFacilityExpDate().trim()));
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			if (null != facilityDetailRequestDTO.getAdhocLastAvailDate()
					&& !facilityDetailRequestDTO.getAdhocLastAvailDate().trim().isEmpty()) {
				limit.setAdhocLastAvailDate(adhocDateFormat.parse(facilityDetailRequestDTO.getAdhocLastAvailDate().trim()));
			}
		}catch (ParseException e) {
			e.printStackTrace();
		}

		if (null != facilityDetailRequestDTO.getMultiDrawDownAllow()
				&& !facilityDetailRequestDTO.getMultiDrawDownAllow().trim().isEmpty()) {
			limit.setMultiDrawdownAllow(facilityDetailRequestDTO.getMultiDrawDownAllow().trim());
		}
		
		if (null !=  facilityDetailRequestDTO.getGeneralPurposeLoan() 
				&& !facilityDetailRequestDTO.getGeneralPurposeLoan().trim().isEmpty()) {
			limit.setGeneralPurposeLoan(facilityDetailRequestDTO.getGeneralPurposeLoan());
		}

		//Interim 
		
		lmtTrxObj.setStagingLimit(limit);
		
		ICMSTrxResult res = proxy.createSubmitFacility(context, lmtTrxObj, false);
		
	           //Deferral
		
		ILimitDAO limitDAO = LimitDAOFactory.getDAO();
		limitDAO.updateFlagsFlagForLMTSTP(lmtTrxObj);
		obj.setResponseStatus("FACILITY_UPDATED_SUCCESSFULLY");
		obj.setTransactionID(lmtTrxObj.getTransactionID());
		obj.setFacilityId(String.valueOf(limit.getLimitID()));
		
	} catch (LimitException e) {
		throw new CMSException(e.getMessage(), e);
	} catch (RemoteException e) {
		throw new CMSException(e.getMessage(), e);
	} catch (CMSValidationFault e) {
		throw new CMSValidationFault(WebServiceStatusCode.FAIL.name(), e.getFaultInfo());
	} catch (NumberFormatException e) {
		throw new CMSException(e.getMessage(), e);
	} catch (Exception e) {
		throw new CMSException(e.getMessage(), e);
	}
	return obj;
	} //End Adhoc
}
