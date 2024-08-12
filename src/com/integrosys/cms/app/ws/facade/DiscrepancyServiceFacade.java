package com.integrosys.cms.app.ws.facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.util.MakerCheckerUserUtil;
import com.integrosys.cms.app.creditApproval.bus.ICreditApprovalDao;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadDetailResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyReadResponseDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyRequestDTO;
import com.integrosys.cms.app.ws.dto.DiscrepancyResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.discrepency.DiscrepencyForm;
import com.integrosys.cms.ui.discrepency.DiscrepencyValidator;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * @author Bharat Waghela
 *
 */

public class DiscrepancyServiceFacade {

	@Autowired
	private DiscrepancyDetailsDTOMapper discrepancyDetailsDTOMapper;

	private IDiscrepencyProxyManager discrepencyProxy;

	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	private ILimitJdbc limitJdbc;

	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}
	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}
	
	@Autowired
	private ICreditApprovalDao creditApprovalDao;
	
	public ICreditApprovalDao getCreditApprovalDao() {
		return creditApprovalDao;
	}
	public void setCreditApprovalDao(ICreditApprovalDao creditApprovalDao) {
		this.creditApprovalDao = creditApprovalDao;
	}
	
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

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}
	public void setDiscrepancyDetailsDTOMapper(DiscrepancyDetailsDTOMapper discrepancyDetailsDTOMapper) {
		this.discrepancyDetailsDTOMapper = discrepancyDetailsDTOMapper;
	}
	@CLIMSWebServiceMethod
	public DiscrepancyResponseDTO  addDiscrepancyDetails(DiscrepancyRequestDTO  discrepancyRequest) throws CMSValidationFault, CMSFault{
		try {	

			String partyId = "";
			if(discrepancyRequest.getPartyId()!=null && !discrepancyRequest.getPartyId().trim().isEmpty()){
				partyId = discrepancyRequest.getPartyId().trim();
			}
			String status = getLimitJdbc().getPartyStatus(partyId);
			if(status!=null && status.equals("INACTIVE")){
				throw new CMSException("Party is Inactive in system");
			}
			else if(status!=null && status.equals("NOTEXIST")){

				throw new CMSException("Party does not exists in system");
			}
			String camId = getLimitJdbc().getCamByLlpLeId(partyId);
			if(camId==null){
				throw new CMSException("CAM for Party does not exists in system");
			}
			
			if(discrepancyRequest.getWsConsumer()==null || discrepancyRequest.getWsConsumer().trim().isEmpty()){
				throw new CMSException("wsConsumer is mandatory");
			}
			
			OBTrxContext context = new OBTrxContext();
			OBDiscrepencyTrxValue discTrxValue = new OBDiscrepencyTrxValue();
			ILimitProfile profile;
			OBCommonUser user = null;
			ITeam[] teams = null;
			ITeamMembership[] memberships;
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			profile = limitProxy.getLimitProfile(Long.parseLong(camId));
			user = (OBCommonUser) getUserProxy().getUser("CPUADM_A");
			teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
//			IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
			IDiscrepencyTrxValue trxValueOut = null;

			HashMap mp = new HashMap();
			HashMap map = new HashMap();

			if(discrepancyRequest.getDiscrepancyList()!=null && !discrepancyRequest.getDiscrepancyList().isEmpty()
					&& discrepancyRequest.getDiscrepancyList().size()>0){
				
				for (int i = 0;i<discrepancyRequest.getDiscrepancyList().size();i++){
//					ActionErrors discrepancyErrorList = new ActionErrors();
					DiscrepancyDetailRequestDTO discrepancyDto = discrepancyDetailsDTOMapper.getRequestDTOWithActualValues(discrepancyRequest.getDiscrepancyList().get(i),String.valueOf(cust.getCustomerID()));
					mp.put(discrepancyDto.getCpsDiscrepancyId(), discrepancyDto.getErrors());
					
					ActionErrors discrepancyErrorList = new ActionErrors();
					DiscrepencyForm discrepancyForm = discrepancyDetailsDTOMapper.getFormFromDTO(discrepancyDto);
					discrepancyErrorList = DiscrepencyValidator.validateInput(discrepancyForm, Locale.getDefault()); 
					map.put(discrepancyForm.getCpsDiscrepancyId(), discrepancyErrorList);
				}
					
				ValidationUtility.handleError(mp, CLIMSWebService.DESCRIPENCY);
				ValidationUtility.handleError(map, CLIMSWebService.DESCRIPENCY);
			}
			
			
			/*if(discrepancyRequest.getDiscrepancyList()!=null && !discrepancyRequest.getDiscrepancyList().isEmpty()
					&& discrepancyRequest.getDiscrepancyList().size()>0){
				
				for (int i = 0;i<discrepancyRequest.getDiscrepancyList().size();i++){
				}
			}*/
			discTrxValue.setCustomerID(cust.getCustomerID());
			discTrxValue.setCustomerName(cust.getCustomerName());
			discTrxValue.setLegalID(profile.getLEReference());
			discTrxValue.setLegalName(cust.getCustomerName());
			discTrxValue.setTeamID(teams[0].getTeamID());
			discTrxValue.setLimitProfileID(Long.parseLong(camId));
			discTrxValue.setLimitProfileReferenceNumber(profile.getBCAReference());
			context.setLimitProfile(profile);
			context.setCustomer(cust);
			List responseList = new ArrayList();
			DiscrepancyResponseDTO discrepancyResponse = new DiscrepancyResponseDTO();
			MakerCheckerUserUtil mcUtil = (MakerCheckerUserUtil) BeanHouse.get("makerCheckerUserUtil");
			
			if(discrepancyRequest.getDiscrepancyList()!=null && !discrepancyRequest.getDiscrepancyList().isEmpty() 
					&& discrepancyRequest.getDiscrepancyList().size()>0){
				
				for (int i = 0;i<discrepancyRequest.getDiscrepancyList().size();i++){
					trxValueOut = new OBDiscrepencyTrxValue();
					DiscrepancyDetailRequestDTO dto = discrepancyRequest.getDiscrepancyList().get(i);
					context = mcUtil.setContextForMaker();
					IDiscrepency discrepancy = discrepancyDetailsDTOMapper.getActualFromDTO(dto);
					trxValueOut = getDiscrepencyProxy().makerCreateDiscrepency(context, trxValueOut,discrepancy);
					trxValueOut = getDiscrepencyProxy().getDiscrepencyByTrxID(trxValueOut.getTransactionID());
					context = mcUtil.setContextForChecker();
					IDiscrepencyTrxValue trxValue = getDiscrepencyProxy().checkerApproveDiscrepency(context,trxValueOut);
					DiscrepancyDetailResponseDTO response = new DiscrepancyDetailResponseDTO();
					
					if(dto.getCpsDiscrepancyId()!=null && !dto.getCpsDiscrepancyId().trim().isEmpty()){
						response.setCpsDiscrepancyId(dto.getCpsDiscrepancyId().trim());
					}else{
						response.setCpsDiscrepancyId("");
					}
					
					response.setDiscrepancyId(String.valueOf(trxValue.getActualDiscrepency().getId()));
					responseList.add(response);
				}
			}
			discrepancyResponse.setDiscrepancyResponseList(responseList);
			discrepancyResponse.setResponseStatus("DISCREPANCY_CREATED_SUCCESSFULLY");
			return discrepancyResponse;
		}catch (CMSValidationFault e) {
			throw e;
		}catch (Exception e) {
			throw new CMSException(e.getMessage(),e);
		}
	}

	@CLIMSWebServiceMethod
	public DiscrepancyReadResponseDTO  readDiscrepancyDetails(DiscrepancyReadRequestDTO readRequestDTO) throws CMSValidationFault, CMSFault{

		DiscrepancyReadResponseDTO dto = new DiscrepancyReadResponseDTO();
		ArrayList discrepancyTrxList= new ArrayList();
		ArrayList discrepancyObjList= new ArrayList();
		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		String partyId = "";
		
		if(readRequestDTO.getPartyId()!=null && !readRequestDTO.getPartyId().trim().isEmpty()){
			partyId = readRequestDTO.getPartyId().trim();
			String status = getLimitJdbc().getPartyStatus(partyId);
			if(status!=null && status.equals("INACTIVE")){
				throw new CMSException("Party is Inactive in system");
			}
			else if(status!=null && status.equals("NOTEXIST")){
	
				throw new CMSException("Party does not exists in system");
			}
		}
		
		String camId = getLimitJdbc().getCamByLlpLeId(partyId);
		if(camId==null){
			throw new CMSException("CAM for Party does not exists in system");
		}
		
		if(readRequestDTO.getWsConsumer()==null || readRequestDTO.getWsConsumer().trim().isEmpty()){
			throw new CMSException("wsConsumer is mandatory");
		}
		
		ILimitProfile profile = null;
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			profile = limitProxy.getLimitProfile(Long.parseLong(camId));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (LimitException e1) {
			e1.printStackTrace();
		}
			
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomerByCIFSource(profile.getLEReference(),null);
		
		discrepancyTrxList = discrepencyJdbc.listDiscrepancy(cust.getCustomerID());
			
		if(discrepancyTrxList!=null && !discrepancyTrxList.isEmpty() && discrepancyTrxList.size()>0){
			for(int i=0;i<discrepancyTrxList.size();i++){
				String trxId=(String) discrepancyTrxList.get(i);
				IDiscrepencyTrxValue discrepencyTrxValue;
				try {
					discrepencyTrxValue = getDiscrepencyProxy().getDiscrepencyByTrxID(trxId);
					OBDiscrepency obj = (OBDiscrepency)discrepencyTrxValue.getActualDiscrepency();
					DiscrepancyReadDetailResponseDTO discrepancy = new DiscrepancyReadDetailResponseDTO();
					discrepancy.setDiscrepancyId(String.valueOf(obj.getId()));
					
					if(obj.getStatus()!=null && !obj.getStatus().isEmpty()){
						discrepancy.setStatus(obj.getStatus());
					}else{
						discrepancy.setStatus("-");					
					}
					
					if(obj.getCreationDate()!=null){
						discrepancy.setCreationDate(sdf.format(obj.getCreationDate()));
					}else{
						discrepancy.setCreationDate("-");
					}
					
					if(obj.getNextDueDate()!=null){
						discrepancy.setNextDueDate(sdf.format(obj.getNextDueDate())) ;
					}else{
						discrepancy.setNextDueDate("-");
					}
					
					if(obj.getRecDate()!=null){
						discrepancy.setCloseDate(sdf.format(obj.getRecDate()));
					}else{
						discrepancy.setCloseDate("-");
					}
					
					discrepancy.setDiscrepancyType(obj.getDiscrepencyType());
				
					if(obj.getAcceptedDate()!=null){
						discrepancy.setApprovedDate(sdf.format(obj.getAcceptedDate()));
					}else{
						discrepancy.setApprovedDate("-");
					}
					
					if(obj.getOriginalTargetDate()!=null){
						discrepancy.setOriginalTargetDate(sdf.format(obj.getOriginalTargetDate()));
					}else{
						discrepancy.setOriginalTargetDate("-");
					}
					
					if(obj.getCreditApprover()!=null && !obj.getCreditApprover().isEmpty()){
						String cpsId = getCreditApprovalDao().getCPSIdByApprovalCode("actualCreditApproval", obj.getCreditApprover());
						if(cpsId!=null){
							discrepancy.setCreditApprover(cpsId);
						}else{
							discrepancy.setCreditApprover("0") ;
						}
					}else{
						discrepancy.setCreditApprover("0") ;
					}
					discrepancy.setRemarks(obj.getDiscrepencyRemark());
					discrepancyObjList.add(discrepancy);
				} catch (NoSuchDiscrepencyException e) {
					e.printStackTrace();
				} catch (CommandProcessingException e) {
					e.printStackTrace();
				} catch (TransactionException e) {
					e.printStackTrace();
				}
			}
		}
		dto.setDiscrepancyList(discrepancyObjList);
		dto.setResponseStatus("DISCREPANCY_READ_SUCCESSFULLY");
		return dto;
	}
}
