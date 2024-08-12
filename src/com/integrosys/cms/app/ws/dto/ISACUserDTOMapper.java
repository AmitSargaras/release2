package com.integrosys.cms.app.ws.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.user.bus.OBCMSUser;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.ui.user.MaintainUserForm;
import com.integrosys.component.bizstructure.app.bus.OBTeamTypeMembership;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

@Service

public class ISACUserDTOMapper {
	public OBCommonUser  getActualFromDTO( ISACRequestDTO dto, ICommonUserTrxValue trxValue ) throws ParseException {

	
		OBCommonUser obUser = new OBCMSUser();
		
	//	long teamTypeMembershipId = Long.parseLong(tteamTypeMembershipId);
		
		
		if(dto.getRefNumber()!=null && !dto.getRefNumber().trim().isEmpty()){
			obUser.setIsacRefNumber(dto.getRefNumber());
		}
		
		if(dto.getUser_id()!=null && !dto.getUser_id().trim().isEmpty()){
			obUser.setLoginID(dto.getUser_id().trim().toUpperCase());		
		}
				
		if(dto.getTelephone_no()!=null && !dto.getTelephone_no().trim().isEmpty()){
			obUser.setPhoneNumber(dto.getTelephone_no().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setPhoneNumber(trxValue.getUser().getPhoneNumber());
		}else {
			obUser.setPhoneNumber("");
		}
		
		if(dto.getUser_name()!=null && !dto.getUser_name().trim().isEmpty()){
			obUser.setUserName(dto.getUser_name().trim());		
		}else if("U".equals(dto.getAction())){
			obUser.setUserName(trxValue.getUser().getUserName());		
		}else {
			obUser.setUserName("");
		}
		
		if(dto.getEmail()!=null && !dto.getEmail().trim().isEmpty()){
			obUser.setEmail(dto.getEmail().trim());		
		}else if("U".equals(dto.getAction())){
			obUser.setEmail(trxValue.getUser().getEmail());		
		}else {
			obUser.setEmail("");
		}
			
		if(null != dto.getEmployee_code() && !dto.getEmployee_code().trim().isEmpty()){
			obUser.setEmployeeID(dto.getEmployee_code().trim().toUpperCase());
		}else if("U".equals(dto.getAction())){
			obUser.setEmployeeID(trxValue.getUser().getEmployeeID().toUpperCase());
		}else {
			obUser.setEmployeeID("");
			}
	
		if(dto.getAddress()!=null && !dto.getAddress().trim().isEmpty()){
			obUser.setEjbAddress(dto.getAddress().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setEjbAddress(trxValue.getUser().getEjbAddress());
		}else {
			obUser.setEjbAddress("");
			}
		
		System.out.println("ISACUserDTOMapper.java=>dto.getBranch_code()=>"+dto.getBranch_code()+"***");
		
		if(null != dto.getBranch_code()  && !"".equals(dto.getBranch_code().trim())){
			String branchCode="";
			if (dto.getBranch_code().trim().length() > 4) {
				branchCode = dto.getBranch_code().trim().substring(dto.getBranch_code().trim().length() - 4);
				System.out.println("dto.getBranch_code()=>branchCode===>"+branchCode+"**");
				obUser.setEjbBranchCode(branchCode);
			} else {
				obUser.setEjbBranchCode(dto.getBranch_code().trim());
			}
			
		}else if("U".equals(dto.getAction())){
			obUser.setEjbBranchCode(trxValue.getUser().getEjbBranchCode());
		}else {
			System.out.println("ISACUserDTOMapper.java=>obUser.setEjbBranchCode()=>Inside set blank");
			obUser.setEjbBranchCode("");
			}
		
System.out.println("ISACUserDTOMapper.java=>obUser.getEjbBranchCode()=>"+obUser.getEjbBranchCode()+"***dto.getAction()=>"+dto.getAction()+"**");
	
		if(dto.getUser_status()!=null && !dto.getUser_status().trim().isEmpty()){
			obUser.setStatus(dto.getUser_status().trim());
			if ("A".equals(dto.getAction())){
				if("".equals(dto.getUser_status().trim())){
					obUser.setStatus("A");
				}
			}
			if("R".equalsIgnoreCase(dto.getUser_status().trim())){
				obUser.setStatus("A");
			}
		}else if("U".equals(dto.getAction())){
			obUser.setStatus(trxValue.getUser().getStatus());
		}
		else if((dto.getUser_status()==null || "".equals(dto.getUser_status().trim())) && "A".equals(dto.getAction().trim())){
			obUser.setStatus("A");
		}
		else {
			obUser.setStatus("");
			}
	
		if(dto.getDisablement_reason()!=null && !dto.getDisablement_reason().trim().isEmpty()){
			obUser.setPosition(dto.getDisablement_reason().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setPosition(trxValue.getUser().getPosition());
		}else {
			obUser.setPosition("");
			}
		OBTeamTypeMembership teamTypeMembership = new OBTeamTypeMembership();
		long teamTypeMembershipId = 0L;
		if(null != dto.getUser_role().trim()  && !dto.getUser_role().trim().isEmpty()){
			 teamTypeMembershipId = Long.parseLong(dto.getUser_role().trim());
		}else {
			teamTypeMembershipId= trxValue.getUser().getTeamTypeMembership().getMembershipID();
		}
		
		
		teamTypeMembership.setMembershipID(teamTypeMembershipId);
		
		obUser.setTeamTypeMembership(teamTypeMembership);
		
		System.out.println("ISACUserDTOMapper.java=>dto.getDepartment()=>"+dto.getDepartment()+"**");
		if(dto.getDepartment()!=null && !dto.getDepartment().trim().isEmpty()){
			obUser.setDepartment(dto.getDepartment().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setDepartment(trxValue.getUser().getDepartment());
		}else {
			System.out.println("ISACUserDTOMapper.java=>obUser.setDepartment()=>Inside set blank");
			obUser.setDepartment("");
			}

		System.out.println("ISACUserDTOMapper.java=>obUser.getDepartment()=>"+obUser.getDepartment()+"...dto.getAction()=>"+dto.getAction());
		
		if(dto.getEmployee_grade()!=null && !dto.getEmployee_grade().trim().isEmpty()){
			obUser.setEmployeeGrade(dto.getEmployee_grade().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setEmployeeGrade(trxValue.getUser().getEmployeeGrade());
		}else {
			obUser.setEmployeeGrade("");
			}
		
		
		if(dto.getOverright_exc_loa()!=null && !dto.getOverright_exc_loa().trim().isEmpty()){
			obUser.setOverrideExceptionForLoa(dto.getOverright_exc_loa().trim());
		}else if("U".equals(dto.getAction())){
			obUser.setOverrideExceptionForLoa(trxValue.getUser().getOverrideExceptionForLoa());
		}else {
			obUser.setOverrideExceptionForLoa("");
			}
		
		
		if(dto.getMaker_date()!=null && !dto.getMaker_date().trim().isEmpty()){
			obUser.setMakerDt(dto.getMaker_date());
		}
		
		if(dto.getChecker_date()!=null && !dto.getChecker_date().trim().isEmpty()){
			obUser.setCheckerDt(dto.getChecker_date());
		}
		
	
	/*	if(dto.getMaker_date()!=null && !dto.getMaker_date().trim().isEmpty()){
			
			String makreDt=dto.getMaker_date();
			 System.out.println("String makerDt======================"+makreDt);
			    Date mkrDt=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(makreDt);  
			    
			obUser.setMakerDt(mkrDt);
		}else{
			obUser.setMakerDt(null);
		}
		
		
		if(dto.getChecker_date()!=null && !dto.getChecker_date().trim().isEmpty()){
			String checkerDt=dto.getChecker_date();
			 System.out.println("String checkerDt======================"+checkerDt);
			    Date chkrDt=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(checkerDt);  
			    
			    obUser.setCheckerDt(chkrDt);
		}else{
			obUser.setCheckerDt(null);
		}*/
		
     /* if(dto.getMaker_id()!=null && !dto.getMaker_id().trim().isEmpty()){
			obUser.setMakerId(dto.getMaker_id().trim());
		}else{
			obUser.setMakerId("");
		}
		
		if(dto.getChecker_id()!=null && !dto.getChecker_id().trim().isEmpty()){
			obUser.setCheckerId(dto.getChecker_id().trim());
		}else{
			obUser.setCheckerId("");
		}*/
		
		
		/* 	if(dto.getMaker_date()!=null && !dto.getMaker_date().trim().isEmpty()){
			obUser.setMakerDate(dto.getMaker_date().trim());
		}else{
			obUser.setMakerDate("");
		}
			if(dto.getChecker_date()!=null && !dto.getChecker_date().trim().isEmpty()){
			obUser.setCheckerDate(dto.getChecker_date().trim());
		}else{
			obUser.setCheckerDate("");
		}
		*/
		
		return obUser;
	}

	public MaintainUserForm  getFormFromDTO( ISACRequestDTO dto ) {

		//LmtDetailForm lmtDetailForm = new LmtDetailForm();	
		MaintainUserForm aForm = new MaintainUserForm();
		//String camId = "";

		try {
			    /*ILimitProxy limitProxy = LimitProxyFactory.getProxy();*/
			   /* if(dto.getCamId()!=null && !dto.getCamId().trim().isEmpty()){
			    	camId = dto.getCamId().trim();
			    }
			    */
		
				if(dto.getRefNumber()!=null && !dto.getRefNumber().trim().isEmpty()){
					aForm.setIsacRefNumber(dto.getRefNumber());
				}
				if(dto.getMaker_date()!=null && !dto.getMaker_date().trim().isEmpty()){
					String makreDt=dto.getMaker_date();
					 System.out.println("String makerDt======================"+makreDt);
					    Date mkrDt=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(makreDt);  
					    
					aForm.setMakerDt(makreDt);
				}
				
				if(dto.getChecker_date()!=null && !dto.getChecker_date().trim().isEmpty()){
					String checkerDt=dto.getChecker_date();
					 System.out.println("String checkerDt======================"+checkerDt);
					    Date chkrDt=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(checkerDt);  
					    
					aForm.setCheckerDt(checkerDt);
				}
				if(dto.getAction()!=null && !dto.getAction().trim().isEmpty()){
					//aForm.setAction(dto.getAction());
				}
				if(null != dto.getUser_id() && !dto.getUser_id().trim().isEmpty()){
					aForm.setLoginId(dto.getUser_id().trim().toUpperCase());		
				}
				

				
				if(dto.getTelephone_no()!=null && !dto.getTelephone_no().trim().isEmpty()){
					aForm.setContactNo(dto.getTelephone_no().trim());
				}
				
				if(dto.getUser_name()!=null && !dto.getUser_name().trim().isEmpty()){
					aForm.setName(dto.getUser_name().trim());		
				}
				
				if(dto.getEmail()!=null && !dto.getEmail().trim().isEmpty()){
					aForm.setEmail(dto.getEmail().trim());		
				}
				
				if(null != dto.getEmployee_code() && !dto.getEmployee_code().trim().isEmpty()){
					aForm.setEmpId(dto.getEmployee_code().trim().toUpperCase());
				}
			
				if(dto.getAddress()!=null && !dto.getAddress().trim().isEmpty()){
					aForm.setAddress(dto.getAddress().trim());
				}
				
				if(null != dto.getBranch_code()  && !dto.getBranch_code().isEmpty()){
					String branchCode="";
					if (dto.getBranch_code().length() > 4) {
						branchCode = dto.getBranch_code().substring(dto.getBranch_code().length() - 4);
						System.out.println("branchCode==="+branchCode);
						aForm.setBranchCode(branchCode);
					} else {
						aForm.setBranchCode(dto.getBranch_code());
					}
					
					
				}
			
				
				if(dto.getUser_status()!=null && !dto.getUser_status().trim().isEmpty()){
					aForm.setStatus(dto.getUser_status().trim());
					if("R".equalsIgnoreCase(dto.getUser_status().trim())){
						aForm.setStatus("A");
					}
					
					if ("A".equals(dto.getAction())){
						if("".equals(dto.getUser_status().trim())){
							aForm.setStatus("A");
						}
					}
				}
				else if((dto.getUser_status()==null || "".equals(dto.getUser_status().trim())) && "A".equals(dto.getAction().trim())){
					aForm.setStatus("A");
				}
			
				if(dto.getDisablement_reason()!=null && !dto.getDisablement_reason().trim().isEmpty()){
					aForm.setDisableReason(dto.getDisablement_reason().trim());
				}
				
				if(dto.getUser_role()!=null && !dto.getUser_role().trim().isEmpty()){
				//	obUser.setRoleType(dto.getUser_role().trim());
				}
				
				
			/*	OBTeamTypeMembership teamTypeMembership = new OBTeamTypeMembership();
				long teamTypeMembershipId = Long.parseLong(dto.getUser_role().trim());
				teamTypeMembership.setMembershipID(teamTypeMembershipId);*/
				
				if(dto.getUser_role()!=null && !dto.getUser_role().trim().isEmpty()){
					aForm.setTeamTypeMembership(dto.getUser_role().trim());
				}else{
					aForm.setTeamTypeMembership(null);
				}
				
				
				if(dto.getDepartment()!=null && !dto.getDepartment().trim().isEmpty()){
					aForm.setDepartment(dto.getDepartment().trim());
				}

				if(dto.getEmployee_grade()!=null && !dto.getEmployee_grade().trim().isEmpty()){
					aForm.setEmployeeGrade(dto.getEmployee_grade().trim());
				}
				
				if(dto.getOverright_exc_loa()!=null && !dto.getOverright_exc_loa().trim().isEmpty()){
					aForm.setOverrideExceptionForLoa(dto.getOverright_exc_loa().trim());
				}
				
				if(dto.getMaker_date()!=null && !dto.getMaker_date().trim().isEmpty()){
					aForm.setMakerDt(dto.getMaker_date().trim());
				}
								
				if(dto.getChecker_date()!=null && !dto.getChecker_date().trim().isEmpty()){
					aForm.setCheckerDt(dto.getChecker_date().trim());
				}
				
		}catch(Exception ee) {
			
		}
		return aForm;
	}

	
	public ISACRequestDTO getRequestDTOWithActualValues(ISACRequestDTO requestDTO) {
		String errorCode = null;
		MasterAccessUtility masterObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(this, "Inside getRequestDTOWithActualValues of ISACDTOMapper================:::::");

		
		if(requestDTO.getRefNumber()!=null && !requestDTO.getRefNumber().trim().isEmpty()){
			
				requestDTO.setRefNumber(requestDTO.getRefNumber());
			
		}else{
		//	errors.add("RefNumber",new ActionMessage("error.facilityCategory.mandatory"));
		}
		
		
		requestDTO.setErrors(errors);
		return requestDTO;
		}
	

	
	
}
