package com.integrosys.cms.app.ws.facade;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.checklist.bus.CheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBTemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateDAO;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.DocumentsDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.DocumentsReadDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DocumentsRequestDTO;
import com.integrosys.cms.app.ws.dto.DocumentsResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.batch.forex.ForexDAO;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
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
public class DocumentsServiceFacade {

	@Autowired
	private DocumentsDetailsDTOMapper documentsDetailsDTOMapper;

	private ICommonUserProxy userProxy;
	private ICMSTeamProxy cmsTeamProxy;
	private ILimitJdbc limitJdbc;
	private ICheckListProxyManager checklistProxyManager;
	private boolean isMaintainChecklistWithoutApproval = true;
	private ISecurityDao securityDao;
	
	private SimpleDateFormat formatedDate = new SimpleDateFormat("dd-MMM-yyyy");

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

	public ICheckListProxyManager getChecklistProxyManager() {
		return checklistProxyManager;
	}
	public void setChecklistProxyManager(
			ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}
	public boolean isMaintainChecklistWithoutApproval() {
		return isMaintainChecklistWithoutApproval;
	}
	public void setMaintainChecklistWithoutApproval(
			boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}
	public void setDocumentsDetailsDTOMapper(DocumentsDetailsDTOMapper documentsDetailsDTOMapper) {
		this.documentsDetailsDTOMapper = documentsDetailsDTOMapper;
	}

	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}
	
	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}
	
	@CLIMSWebServiceMethod
	public DocumentsResponseDTO  addDocumentsDetails(DocumentsRequestDTO  documentsRequestDTO) throws CMSValidationFault, CMSFault{
	
		DocumentsResponseDTO documentResponseDTO = new DocumentsResponseDTO();
	
//		ICommonUser user = new OBCommonUser();
		OBTrxContext context = new OBTrxContext();
		MakerCheckerUserUtil makercheckUtil = (MakerCheckerUserUtil)BeanHouse.get("makerCheckerUserUtil");
		ITemplateTrxValue templateTrxVal = null;

		//FacilityCode - To get from Mapping Id
		//Check Facility is available on Cms_Document_Masterlist table or not?
		
		// If yes then get the template list and add document item to template list array
		//Else create a new entry
			
			try {
				// To Set Document MasterList Record ; Tables impacted : Cms_Document_Masterlist,cms_document_item 
				ICheckListTemplateProxyManager checkListTemplateProxy = CheckListTemplateProxyManagerFactory
									.getCheckListTemplateProxyManager();
				
				String partyId = "";
				if(documentsRequestDTO.getPartyId()!=null && !documentsRequestDTO.getPartyId().trim().isEmpty()){
					partyId = documentsRequestDTO.getPartyId().trim();
					String status = getLimitJdbc().getPartyStatus(partyId);
					
					if(status!=null && status.equals("INACTIVE")){
						throw new CMSException("Party is Inactive in system");
					}
				}
				
				ActionErrors errors  = documentsDetailsDTOMapper.validateData(documentsRequestDTO,getLimitJdbc(),getSecurityDao());
				
				if(errors == null || errors.isEmpty() || errors.size()==0){
					
					//Get Proxy
					ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
					
					//To Set Customer Value to Context
					if(documentsRequestDTO.getPartyId()!=null && !documentsRequestDTO.getPartyId().trim().isEmpty()){
						ICMSCustomer cust = custProxy.getCustomerByCIFSource(documentsRequestDTO.getPartyId().trim(),null);
						context.setCustomer(cust);
					}
					
					//Get CAM Id using Party Id
					String camId = null;
					try{
						camId = getLimitJdbc().getCamByLlpLeId(documentsRequestDTO.getPartyId().trim());
					}catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
					}
					
					ITemplate template = new OBTemplate();
					List<DocumentsDetailResponseDTO> documentResponseList = new LinkedList<DocumentsDetailResponseDTO>();
					
					if(documentsRequestDTO.getDocumentList()!=null && documentsRequestDTO.getDocumentList().size()>0){
						List<DocumentsDetailRequestDTO> documentsDetailRequestDTOList = documentsRequestDTO.getDocumentList();
						
						for(DocumentsDetailRequestDTO docDetailReqDTO : documentsDetailRequestDTOList){
							
							ICheckListTrxValue checkListTrxVal = null;
								//-------------------------- INSERT/UPDATE TO MASTER LIST : START ------------------------------
								
								try{
									if(docDetailReqDTO.getDocumentCategory()!=null && !docDetailReqDTO.getDocumentCategory().trim().isEmpty()
											&& "F".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
									
										Map<String,Object> valuesMap = documentsDetailsDTOMapper.createMasterListRecord(docDetailReqDTO,getLimitJdbc());
						
										if(valuesMap!=null && !valuesMap.isEmpty()){
											 
											 if(valuesMap.get("obTemplate")!=null){
												 template = (ITemplate)valuesMap.get("obTemplate");
											 }
											 
											 if(valuesMap.get("templateTrxVal")!=null){
												 templateTrxVal = (ITemplateTrxValue)valuesMap.get("templateTrxVal"); 
												 template = templateTrxVal.getTemplate();
											 }
											 ITemplateTrxValue modifiedTrxValue = null;
											 if(template!=null && template.getTemplateItemList()!=null && template.getTemplateItemList().length>0 ){
												 
												 context = makercheckUtil.setContextForMaker();
												 if(templateTrxVal == null){
													 templateTrxVal = checkListTemplateProxy.makerCreateTemplate(context, template);
												 }else{
													 templateTrxVal = checkListTemplateProxy.makerUpdateTemplate(context, templateTrxVal, template);
												 }
												 modifiedTrxValue = checkListTemplateProxy.getTemplateByTrxID(templateTrxVal.getTransactionID());

												 context = makercheckUtil.setContextForChecker();
												 checkListTemplateProxy.checkerApproveTemplate(context, modifiedTrxValue);
											 }
										}
									}
								}catch (CheckListTemplateException e) {
									e.printStackTrace();
									DefaultLogger.error(this, e.getMessage());
								}
								
								//-------------------------- END ----------------------------------
								
								// ------------------------ INSERT/UPDATE TO CHECKLIST & RESPONSE : START  ----------------------
								if(templateTrxVal!=null || (docDetailReqDTO.getDocumentCategory()!=null && !docDetailReqDTO.getDocumentCategory().trim().isEmpty() && !"F".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim()))){
		
									// Get Mapping ID i.e. Security/Facility ID
									String mappingId = null;
									String securitySubType =null;
									
									if("CAM".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())
											|| "O".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())
											|| "REC".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
										securitySubType = docDetailReqDTO.getDocumentCategory().trim();
									}else{
										if(docDetailReqDTO.getMappingId()!=null && !docDetailReqDTO.getMappingId().trim().isEmpty()){
											mappingId = docDetailReqDTO.getMappingId().trim();
											if("S".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
												try{
													securitySubType = getSecurityDao().findSecuritySubTypeIdByCmsSecurityId(Long.parseLong(mappingId));
												}catch (Exception e) {
													DefaultLogger.error(this, e.getMessage());
													throw new CMSException("No Security found with given Mapping ID");
												}
											}else if("F".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
												securitySubType = docDetailReqDTO.getDocumentCategory().trim();
											}
										}
									}/*else{
										if("CAM".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())
												|| "O".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())
												|| "REC".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
											securitySubType = docDetailReqDTO.getDocumentCategory().trim();
										}
									}*/
									
											
									try{
										CheckListDAO checkListDAO = new CheckListDAO();
										TemplateDAO templateDAO = new TemplateDAO();
		
										//Get CheckList ID based on Mapping ID and CAM ID
										String category = null;
										if("S".equalsIgnoreCase(docDetailReqDTO.getDocumentCategory().trim())){
											category = "S";
										}else{
											category = securitySubType;
										}
										
										String chkListID = checkListDAO.getCheckListIdUsingMappingIdAndCamId(camId,mappingId,category);
										if(chkListID!=null && !chkListID.isEmpty()){
											//Get Transaction using CheckList ID = Reference ID in TRANSACTION TABLE
											String trxId = templateDAO.getTransactionIdByMasterListId(chkListID);
											if(trxId!=null && !trxId.isEmpty()){
												//Get CheckListProxy Manager to get transaction using Transaction ID
												ICheckListProxyManager chkListPrxyMgr = CheckListProxyManagerFactory.getCheckListProxyManager();
												checkListTrxVal = chkListPrxyMgr.getCheckListByTrxID(trxId);
											}
										}
									}catch (Exception e) {
										DefaultLogger.error(this, e.getMessage());
									}
											
											
									ICheckList existingCheckList = null;
									if (checkListTrxVal != null) {
										if (ICMSConstant.STATE_REJECTED.equals(checkListTrxVal.getStatus())) {
											existingCheckList = checkListTrxVal.getStagingCheckList();
										}
										else {
											existingCheckList = checkListTrxVal.getCheckList();
										}
									}
									
									ICheckList obCheckList = documentsDetailsDTOMapper.getActualFromDTO(docDetailReqDTO,getLimitJdbc(),camId,existingCheckList,securitySubType);
									
									if (checkListTrxVal == null) {
										if (this.isMaintainChecklistWithoutApproval) {
											checkListTrxVal = this.checklistProxyManager.makerCreateCheckListWithoutApproval(context, obCheckList);
										}
										else {
											checkListTrxVal = this.checklistProxyManager.makerCreateCheckList(context, obCheckList);
										}
									}
									else {
										ICheckListItem temp1[] = obCheckList.getCheckListItemList();
										if (this.isMaintainChecklistWithoutApproval) {
											checkListTrxVal = this.checklistProxyManager.makerUpdateCheckListWithoutApproval(context,
													checkListTrxVal, obCheckList);
										}
										else {
											checkListTrxVal = this.checklistProxyManager.makerUpdateCheckList(context, checkListTrxVal, obCheckList);
										}
									}
											
									//Set Response Values
									
									if(checkListTrxVal != null){
										DocumentsDetailResponseDTO documentDetailResponseDTO = new DocumentsDetailResponseDTO();
										
										if(checkListTrxVal.getCheckList()!=null 
												&& checkListTrxVal.getCheckList().getCheckListItemList()!=null 
												&& checkListTrxVal.getCheckList().getCheckListItemList().length>0){
											
											ICheckListItem[] checkListItemList = checkListTrxVal.getCheckList().getCheckListItemList();
											
											for(ICheckListItem checkListItem : checkListItemList){
												
												// Set CPS ID
												if(checkListItem.getItemCode()!=null && !checkListItem.getItemCode().trim().isEmpty() 
														&& docDetailReqDTO.getDocumentCode().trim().equalsIgnoreCase(checkListItem.getItemCode().trim())){
												
													if(docDetailReqDTO.getCpsDocumentId()!=null && !docDetailReqDTO.getCpsDocumentId().trim().isEmpty()){
														documentDetailResponseDTO.setCpsDocumentId(docDetailReqDTO.getCpsDocumentId().trim());
													}else{
														documentDetailResponseDTO.setCpsDocumentId("");
													}
												
													// Set Document Item ID : Generated By CLIMS
													if(0l != checkListItem.getCheckListItemID()){
														documentDetailResponseDTO.setDocumentItemId(Long.toString(checkListItem.getCheckListItemID()));//To Do : CLIMS Document ID - Check List Item Table
													}
													
													documentResponseList.add(documentDetailResponseDTO);
													break;
												}
											}
											documentResponseDTO.setDocumentResponseList(documentResponseList);
											documentResponseDTO.setResponseStatus("DOCUMENT_ADDED_SUCCESSFULLY");
										}
									}else{
										DefaultLogger.error(this, "no value found in checkListTrxVal: "+checkListTrxVal);
										throw new CMSException("Server side error");
									}
								}
	//						}//If Condition for Data validation -- End 
						}
					}
				}else{
					HashMap map = new HashMap();
					map.put("1", errors);
					ValidationUtility.handleError(map, CLIMSWebService.DOCUMENTS);
				}
			}catch (CMSValidationFault e) {
				throw e; 
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error during document create ######## ", e);
				throw new CMSException(e.getMessage(),e); 
			}
		/*}catch (NumberFormatException e) {
			e.printStackTrace();
		}*/
		
		return documentResponseDTO;
	}

	@CLIMSWebServiceMethod
	public DocumentsReadResponseDTO readDocumentsDetails(DocumentsReadRequestDTO docReadReqDTO) throws CMSValidationFault, CMSFault{

	//	System.out.println("Party ID : "+docReadReqDTO.getPartyId());
		DocumentsReadResponseDTO documentReadResponseDTO = new DocumentsReadResponseDTO();
		
		//Validate WSDL data - START
		ActionErrors errors = documentsDetailsDTOMapper.readDocumentVaidations(docReadReqDTO, getLimitJdbc());
		//Validate WSDL data - END
		
		String camId = null;
		
		if(errors == null || errors.isEmpty() || errors.size()==0){
			try{
				if(docReadReqDTO.getPartyId()!=null && !docReadReqDTO.getPartyId().trim().isEmpty()){
					camId = getLimitJdbc().getCamByLlpLeId(docReadReqDTO.getPartyId().trim());
				}
			}catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
			}
			
			if(camId!=null && !camId.trim().isEmpty()){
				CheckListDAO checkListDAO = new CheckListDAO();
				ForexDAO forexDAO = new ForexDAO(); 
				List<String> checkListResultList = checkListDAO.getCheckListId(camId);
				
				if(checkListResultList!=null && checkListResultList.size()>0){
					List<DocumentsReadDetailResponseDTO> readResponseDTOList = new LinkedList<DocumentsReadDetailResponseDTO>();
	
					for(String checkListId : checkListResultList){
						if(checkListId!=null && !checkListId.isEmpty()){
							List<OBCheckListItem> checkItemList = checkListDAO.getCheckListItemByCheckListId(checkListId);
							if(checkItemList!=null && checkItemList.size() >0){
								DecimalFormat decimalFormat = new DecimalFormat("#.00");

								for(OBCheckListItem item : checkItemList){
									
									DocumentsReadDetailResponseDTO readDetailDTO = new DocumentsReadDetailResponseDTO();
									
									if(item.getDocumentStatus()!=null && !item.getDocumentStatus().isEmpty()){
										if(item.getDocumentStatus().equalsIgnoreCase("AWAITING")){
											readDetailDTO.setDocumentStatus("PENDING");
										}else{
											readDetailDTO.setDocumentStatus(item.getDocumentStatus());
										}
									}else{
										readDetailDTO.setDocumentStatus("-");
									}
									
									readDetailDTO.setDocumentCode(item.getItemCode()!=null?item.getItemCode():"-");
									readDetailDTO.setDocumentDescription(item.getItemDesc()!=null?item.getItemDesc():"-");
									
									if(item.getDocAmt()!=null && !item.getDocAmt().isEmpty()){
										readDetailDTO.setDocumentAmount(decimalFormat.format(Double.parseDouble(item.getDocAmt().replace(",", ""))));
									}else{
										readDetailDTO.setDocumentAmount("0.00");
									}
									
									if(item.getHdfcAmt()!=null && !item.getHdfcAmt().isEmpty()){
										readDetailDTO.setHdfcAmount(decimalFormat.format(Double.parseDouble(item.getHdfcAmt().replace(",", ""))));
									}else{
										readDetailDTO.setHdfcAmount("0.00");
									}
									
									if(item.getCurrency()!=null && !item.getCurrency().isEmpty()){
										String currency_cps_id = null;
										try {
											currency_cps_id = forexDAO.getCPSId(item.getCurrency());
											if(currency_cps_id!=null && !currency_cps_id.isEmpty()){
												readDetailDTO.setDocumentCurrency(currency_cps_id);
											}else{
												readDetailDTO.setDocumentCurrency("0");
											}
										} catch (FinderException e) {
											e.printStackTrace();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}else{
										readDetailDTO.setDocumentCurrency("0");
									}
									readDetailDTO.setDocumentDate(item.getDocDate()!=null?formatedDate.format(item.getDocDate()):"-");
									readDetailDTO.setDocumentOriginaltargeDate(item.getOriginalTargetDate()!=null ?formatedDate.format(item.getOriginalTargetDate()):"-");
									readDetailDTO.setDocumentExpiryDate(item.getExpiryDate()!=null ? formatedDate.format(item.getExpiryDate()):"-");
									readDetailDTO.setDocumentRemarks(item.getRemarks()!=null?item.getRemarks():"-");
									readDetailDTO.setDocumentItemId(item.getCheckListItemID()!=0L?Long.toString(item.getCheckListItemID()):"0");
									readDetailDTO.setDocumentDeferredDate(item.getDeferExpiryDate()!=null ? formatedDate.format(item.getDeferExpiryDate()):"-");
									readDetailDTO.setDocumentWaiveDate(item.getWaivedDate()!=null ?formatedDate.format(item.getWaivedDate()):"-");
									readDetailDTO.setDocumentReceiveDate(item.getReceivedDate()!=null?formatedDate.format(item.getReceivedDate()):"-");
									
									readResponseDTOList.add(readDetailDTO);
								}
							}
						}
					}
					documentReadResponseDTO.setDocumentList(readResponseDTOList);
					documentReadResponseDTO.setResponseStatus("DOCUMENT_READ_SUCCESSFULLY");
				}
			}
		}else{
			HashMap map = new HashMap();
			map.put("1", errors);
			ValidationUtility.handleError(map, CLIMSWebService.DOCUMENTS);
		}
		
		return documentReadResponseDTO;

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


			user = (OBCommonUser) getUserProxy().getUser("CPUADM_C");
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
	
}