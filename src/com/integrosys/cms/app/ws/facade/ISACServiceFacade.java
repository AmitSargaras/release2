/**
 * 
 */
package com.integrosys.cms.app.ws.facade;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.crystaldecisions.sdk.occa.report.definition.DayFormat;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.dto.ISACUserDTOMapper;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.asst.validator.IsacUserValidator;
import com.integrosys.cms.ui.user.MaintainUserForm;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.OBSearchCommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * @author Ankit
 *
 */

public class ISACServiceFacade{
	
	@Autowired
	private ISACUserDTOMapper isacUserDTOMapper;
	
	public void setIsacUserDTOMapper(ISACUserDTOMapper isacUserDTOMapper) {
		this.isacUserDTOMapper = isacUserDTOMapper;
	}
	@CLIMSWebServiceMethod
	public ISACResponseDTO  AddIsacUser(ISACRequestDTO  isacRequestDTO) throws CMSValidationFault, CMSFault{
	ISACResponseDTO isacResDto = new ISACResponseDTO();
	isacResDto.setErrorCode("");
		try {
			IsacUserValidator obj = new IsacUserValidator();
			boolean validRefNo= obj.isDuplicateRefNumber(isacRequestDTO.getRefNumber());
			//System.out.println("AddIsacUser:: validRefNo"+validRefNo);
			if(!validRefNo) {
				obj.forDuplicateRefNumber(isacRequestDTO.getRefNumber(),isacResDto);
				return isacResDto;
			}
			
			ISACRequestDTO isacUserRequestDTO = isacUserDTOMapper.getRequestDTOWithActualValues(isacRequestDTO);
		
			MaintainUserForm form = isacUserDTOMapper.getFormFromDTO(isacUserRequestDTO);
			form.setEvent(isacUserRequestDTO.getEvent());
			CMSUserProxy proxy = new CMSUserProxy();
			ICommonUserTrxValue trxValue = new OBUserTrxValue();
			ICompTrxResult trxResult = null;
			OBTrxContext trxContext = new OBTrxContext(); 
			try {
			
			((ICMSTrxValue) trxValue).setTrxContext(trxContext); // -------- ??????
			}
			catch(Exception ee) {
				isacResDto.setRefNumber(isacUserRequestDTO.getRefNumber());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid User ID");
				return isacResDto;
				
			}
			ICommonUser newObCommonUser = new OBCommonUser();
			
			if(isacUserRequestDTO.getEvent()!=null){
				
				DefaultLogger.info(this,"isacUserRequestDTO.getEvent():::"+isacUserRequestDTO.getEvent());
				boolean existingUser=true;
				if("WS_ISACUSER_CREATE".equalsIgnoreCase(isacUserRequestDTO.getEvent())){
 		
					if(null != isacUserRequestDTO.getUser_id() && !("".equals(isacUserRequestDTO.getUser_id()) )){
				try {
					CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
					StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria(); 
					criteriaOB.setEmployeeId(isacUserRequestDTO.getUser_id().trim());
					criteriaOB.setLoginId(isacUserRequestDTO.getUser_id().trim());
					criteria.setCriteria(criteriaOB);
					criteria.setNItems(10);
					SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
					
					if(searchResult !=null){
						if(searchResult.getNItems()==0){
							existingUser=false;
						}
					}else{
						existingUser=false;
					}
					 //commonUser= proxy.getUser(user.getLoginID());
				} catch (EntityNotFoundException e) {
					DefaultLogger.debug(this, "Duplicate user not found");
				} catch (RemoteException e) {
					DefaultLogger.debug(this, "Duplicate user not found");
				}
			}else {
				existingUser=false;
			}
					if(!existingUser) {
					IsacUserValidator validate= new IsacUserValidator();
					
			    	validate.isacCommonValidations(isacRequestDTO,isacResDto,trxValue);
					validate.isacValidateAdd(isacRequestDTO,isacResDto,trxValue);
					
					if(null==isacResDto.getErrorCode() ||  "".equals(isacResDto.getErrorCode())) {
	
					newObCommonUser = isacUserDTOMapper.getActualFromDTO(isacUserRequestDTO,trxValue);
					newObCommonUser.setIsacRefNumber(isacUserRequestDTO.getRefNumber());
					
					newObCommonUser.setDepartment(isacRequestDTO.getDepartment());
					trxValue.setStagingUser(newObCommonUser);
					trxValue.setTransactionDate(new Date());
					trxValue.setTransactionType("USER");
				
					trxResult =   proxy.makerCreateUser(trxContext,trxValue,newObCommonUser);
					trxValue=(ICommonUserTrxValue) trxResult.getTrxValue();
					trxValue.setStagingReferenceID(trxValue.getStagingReferenceID());
				
					newObCommonUser.setUserID(trxValue.getStagingUser().getUserID());
				
					trxValue.setInstanceName(trxResult.getTrxValue().getInstanceName());	
					trxValue.setStatus(trxResult.getTrxValue().getStatus());
					trxValue.setVersionTime(trxResult.getTrxValue().getVersionTime());
					trxValue.setTransactionID(trxResult.getTrxValue().getTransactionID());
				//	trxContext.setUser(newObCommonUser);
					trxValue.setStagingUser(trxValue.getStagingUser());
					trxValue.setUser(trxValue.getUser());
					trxContext.setUser(trxValue.getUser());
					trxResult =  proxy.checkerApproveCreateUser(trxContext, trxValue);
					
					OBUserTrxValue trxVal = (OBUserTrxValue) trxResult.getTrxValue();
					long teamTypeMembershipId = trxVal.getUser().getTeamTypeMembership().getMembershipID();
					int loopCount = 1;
					long[] teamTypeMembershipIdArr = new long[2];
					long tempTeamTypeMembershipId = 0;
					if(teamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER ){
						tempTeamTypeMembershipId = teamTypeMembershipId;
						loopCount = 2;
						teamTypeMembershipIdArr[0]= ICMSConstant.CPU_MAKER;
						teamTypeMembershipIdArr[1]= ICMSConstant.CPU_CHECKER;
					}else if(teamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER){
						loopCount = 2;
						tempTeamTypeMembershipId = teamTypeMembershipId;
						teamTypeMembershipIdArr[0]= ICMSConstant.CPU_ADMIN_MAKER;
						teamTypeMembershipIdArr[1]= ICMSConstant.CPU_ADMIN_CHECKER;
					}
					for(int k = 0;k<loopCount;k++){
						if(tempTeamTypeMembershipId == ICMSConstant.CPU_MAKER_CHECKER || tempTeamTypeMembershipId == ICMSConstant.CPU_ADMIN_MAKER_CHECKER ){
							teamTypeMembershipId = teamTypeMembershipIdArr[k];
						}
						
						StdUserDAO  stdUserDAO = new StdUserDAO();
						long teamMembershipId= stdUserDAO.getTeamMembershipIdByTeamTypeMembershipId(teamTypeMembershipId);
						stdUserDAO.createTeamMember(trxVal.getUser().getUserID(), teamMembershipId);
						
					}
				}
			
			if(trxResult != null){
				isacResDto.setRefNumber(isacUserRequestDTO.getRefNumber().trim());
				isacResDto.setErrorCode("0");
				isacResDto.setErrorMessage("SUCCESS");
			}
			}else {
				isacResDto.setRefNumber(isacUserRequestDTO.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("User Already Exist");
			}
			}
			}
			else{
				isacResDto.setRefNumber(isacUserRequestDTO.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid Event/Action");
			//	throw new CMSException("Server side error");
			}
		}catch (CMSValidationFault e) {
			System.out.println("Exception catch1:"+e.getMessage());
			isacResDto.setRefNumber(isacRequestDTO.getRefNumber().trim()); //Temp hardcode
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage(e.getMessage());
	//		throw e; 
		}catch (Exception e) {
			System.out.println("Exception catch2:"+e.getMessage());

			isacResDto.setRefNumber(isacRequestDTO.getRefNumber().trim()); //Temp hardcode
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage(e.getMessage());
			DefaultLogger.error(this, "############# error during isac user create ######## ", e);
		//	throw new CMSException(e.getMessage(),e); 
		}
		return isacResDto;
	}
	
	@CLIMSWebServiceMethod
	public ISACResponseDTO  UpdateIsacUser(ISACRequestDTO isacReqDto) throws CMSValidationFault,CMSFault{
		
		ISACResponseDTO isacResDto = new ISACResponseDTO();
		isacResDto.setErrorCode("");
		isacReqDto.setEmployee_code(isacReqDto.getUser_id() );
		try {
			
			IsacUserValidator obj = new IsacUserValidator();
			boolean validRefNo= obj.isDuplicateRefNumber(isacReqDto.getRefNumber());
		    //System.out.println("AddIsacUser:: validRefNo"+validRefNo);
			if(!validRefNo) {
				obj.forDuplicateRefNumber(isacReqDto.getRefNumber(),isacResDto);
				return isacResDto;
			} 
			ISACRequestDTO isacRequestDTOInstance = isacUserDTOMapper.getRequestDTOWithActualValues(isacReqDto);
			ActionErrors cpsIdErrors = isacRequestDTOInstance.getErrors();
			
			MaintainUserForm form = isacUserDTOMapper.getFormFromDTO(isacRequestDTOInstance);
			form.setEvent(isacRequestDTOInstance.getEvent());
				
			CMSUserProxy proxy = new CMSUserProxy();
			ICommonUserTrxValue trxValue = new OBUserTrxValue();
			ICompTrxResult trxResult = null;
			OBTrxContext trxContext = new OBTrxContext(); 
			
			((ICMSTrxValue) trxValue).setTrxContext(trxContext); // -------- ??????
			
			ICommonUser newObCommonUser = new OBCommonUser();
			
				DefaultLogger.info(this,"isacRequestDTOInstance.getEvent():::"+isacRequestDTOInstance.getEvent());
				
				if("WS_ISACUSER_UPDATE".equalsIgnoreCase(isacRequestDTOInstance.getEvent())){
					
					CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
					StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
					if(null != isacRequestDTOInstance.getUser_id() && !"".equalsIgnoreCase(isacRequestDTOInstance.getUser_id()))
					{
					isacRequestDTOInstance.setUser_id(isacRequestDTOInstance.getUser_id().toUpperCase());
					criteriaOB.setEmployeeId(( isacRequestDTOInstance.getUser_id()));
					}
					criteria.setCriteria(criteriaOB);
					criteria.setNItems(10);
					System.out.println("criteriaOB.getEmployeeId()=====:"+criteriaOB.getEmployeeId());
					SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
					java.util.Vector vector=(java.util.Vector)searchResult.getResultList();
					OBSearchCommonUser userVal2=new OBSearchCommonUser();
					try {
					 userVal2=(OBSearchCommonUser) vector.get(0);
					}catch(Exception e) {
						isacResDto.setRefNumber(isacRequestDTOInstance.getRefNumber());
						isacResDto.setErrorCode("1");
						isacResDto.setErrorMessage("Invalid User/Employee ID");
						return isacResDto;
					}
				//	ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
					//userTrxVal = userProxy.getUserByPK(String.valueOf(userVal2.getUserID()));
					trxValue = proxy.getUserByPK(String.valueOf(userVal2.getUserID()));
					
					if(!"A".equals(trxValue.getUser().getStatus().trim())  && "U".equals(isacReqDto.getAction().trim()) && !"".equals(isacReqDto.getUser_status().trim()) && null != isacReqDto.getUser_status()) {
						isacRequestDTOInstance.setBranch_code("");
						isacRequestDTOInstance.setEmail("");
						isacRequestDTOInstance.setUser_role("");
						isacRequestDTOInstance.setAddress("");
						isacRequestDTOInstance.setCountry("");
						isacRequestDTOInstance.setRegion("");
						isacRequestDTOInstance.setState("");
						isacRequestDTOInstance.setCity("");
						isacRequestDTOInstance.setDepartment("");
						isacRequestDTOInstance.setEmployee_grade("");
						
						if(!"I".equals(isacReqDto.getUser_status().trim())){
							isacRequestDTOInstance.setDisablement_reason("");
							isacReqDto.setDisablement_reason("");
						}
						
						isacRequestDTOInstance.setTelephone_no("");
						isacRequestDTOInstance.setOverright_exc_loa("");
						
						isacReqDto.setBranch_code("");
			    		isacReqDto.setEmail("");
			    		isacReqDto.setUser_role("");
			    		isacReqDto.setAddress("");
			    		isacReqDto.setCountry("");
			    		isacReqDto.setRegion("");
			    		isacReqDto.setState("");
			    		isacReqDto.setCity("");
			    		isacReqDto.setDepartment("");
			    		isacReqDto.setEmployee_grade("");
			    		isacReqDto.setTelephone_no("");
			    		isacReqDto.setOverright_exc_loa("");
			    	}
					else if("A".equals(trxValue.getUser().getStatus().trim())  && "U".equals(isacReqDto.getAction().trim()) &&  !"A".equals(isacReqDto.getUser_status().trim()) &&  !"".equals(isacReqDto.getUser_status().trim()) && null != isacReqDto.getUser_status() ) {
						isacRequestDTOInstance.setBranch_code("");
						isacRequestDTOInstance.setEmail("");
						isacRequestDTOInstance.setUser_role("");
						isacRequestDTOInstance.setAddress("");
						isacRequestDTOInstance.setCountry("");
						isacRequestDTOInstance.setRegion("");
						isacRequestDTOInstance.setState("");
						isacRequestDTOInstance.setCity("");
						isacRequestDTOInstance.setDepartment("");
						isacRequestDTOInstance.setEmployee_grade("");
						
						if(!"I".equals(isacReqDto.getUser_status().trim())){
							isacRequestDTOInstance.setDisablement_reason("");
							isacReqDto.setDisablement_reason("");
						}
						
						isacRequestDTOInstance.setTelephone_no("");
						isacRequestDTOInstance.setOverright_exc_loa("");
						
						isacReqDto.setBranch_code("");
			    		isacReqDto.setEmail("");
			    		isacReqDto.setUser_role("");
			    		isacReqDto.setAddress("");
			    		isacReqDto.setCountry("");
			    		isacReqDto.setRegion("");
			    		isacReqDto.setState("");
			    		isacReqDto.setCity("");
			    		isacReqDto.setDepartment("");
			    		isacReqDto.setEmployee_grade("");
			    		isacReqDto.setTelephone_no("");
			    		isacReqDto.setOverright_exc_loa("");
			    	}
					
					newObCommonUser = isacUserDTOMapper.getActualFromDTO(isacRequestDTOInstance,trxValue);
			    	IsacUserValidator validate= new IsacUserValidator();
			    	
			    	
			    	
			    	validate.isacCommonValidations(isacReqDto,isacResDto,trxValue);
					validate.isacValidateUpdate(isacReqDto,isacResDto,userVal2.getUserID(),trxValue);
					
					System.out.println("ISACServiceFacade.java=>Line 292=>isacReqDto.getDepartment()=>"+isacReqDto.getDepartment()+"**");
					
					if(null != isacReqDto.getDepartment() && !"".equals(isacReqDto.getDepartment().trim())) {					
					 newObCommonUser.setDepartment(isacReqDto.getDepartment());
					}
					
					if(null==isacResDto.getErrorCode() ||  "".equals(isacResDto.getErrorCode())) {
				
					newObCommonUser.setVersionTime(trxValue.getVersionTime());
					newObCommonUser.setUserID(userVal2.getUserID());
					String makerId="";
					if(null != isacRequestDTOInstance.getMaker_id() && !"".equals(isacRequestDTOInstance.getMaker_id())) {
						makerId = isacRequestDTOInstance.getMaker_id() ;
					}else {
						makerId= trxValue.getUser().getLoginID();
					}
			
				//	newObCommonUser.setLoginID(makerId);
				   newObCommonUser.setIsacRefNumber(isacRequestDTOInstance.getRefNumber());
				//	newObCommonUser.setMakerDt(makerDt);
					 String mkrDate=isacRequestDTOInstance.getMaker_date();
					/* System.out.println("String mkrDate======================"+mkrDate);
					Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(mkrDate);  
					
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
					    String strDate1 = dateFormat1.format(date1);  
					    System.out.println("Date strDate1======================"+strDate1);*/

					newObCommonUser.setMakerDt(mkrDate);
					String ckrDate=isacRequestDTOInstance.getChecker_date();
					/* System.out.println("String ckrDate======================"+ckrDate);
					    Date date2=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(ckrDate);  
					
					    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
					    String strDate2 = dateFormat2.format(date2);  
					    System.out.println("Date strDate2======================"+strDate2);*/
					    newObCommonUser.setCheckerDt(ckrDate);
					System.out.println("newObCommonUser.getEjbBranchCode()=>"+newObCommonUser.getEjbBranchCode()+"**");
					System.out.println("newObCommonUser.getDepartment()=>"+newObCommonUser.getDepartment()+"**");
					trxContext.setUser(newObCommonUser);
					trxValue.setStagingUser(newObCommonUser);
					trxValue.setTransactionDate(new Date());
				//	trxValue.setTransactionType("USER");
				//	trxValue.setip(newObCommonUser);"ISAC interface";
					trxResult = proxy.makerUpdateUser(trxContext,trxValue,newObCommonUser);
					OBUserTrxValue obTrx=	(OBUserTrxValue) trxResult.getTrxValue();
					trxValue.setStagingReferenceID(obTrx.getStagingReferenceID());
					
					String checkerId="";
					if(null != isacRequestDTOInstance.getChecker_id() && !"".equals(isacRequestDTOInstance.getChecker_id())) {
						checkerId = isacRequestDTOInstance.getChecker_id() ;
					}else {
						checkerId= trxValue.getUser().getLoginID();
					}
				//	newObCommonUser.setLoginID(checkerId);
				//	newObCommonUser.setLoginID(checkerId);
					trxContext.setUser(newObCommonUser);
					obTrx.getStagingUser().setMakerDt(mkrDate);
					obTrx.getStagingUser().setCheckerDt(ckrDate);
					obTrx.setTransactionDate(new Date());
				
					trxValue.setStagingUser(obTrx.getStagingUser());
					trxValue.setFromState(trxResult.getTrxValue().getFromState());
					trxValue.setStatus(trxResult.getTrxValue().getStatus());
					trxValue.setToState(trxResult.getTrxValue().getToState());
					trxValue.setTransactionID(trxResult.getTrxValue().getTransactionID());
					trxValue.setVersionTime(trxResult.getTrxValue().getVersionTime());
				
			//		newObCommonUser.setVersionTime(trxResult.getTrxValue().getVersionTime());
				//	trxValue.setUser(newObCommonUser);
				//	trxValue.setOpDesc(trxResult.getTrxValue().getOp);
					trxResult =  proxy.checkerApproveUpdateUser(trxContext, trxValue);
					
					if(trxResult != null && "".equals(isacResDto.getErrorCode() ) ){
						isacResDto.setRefNumber(isacRequestDTOInstance.getRefNumber());
						isacResDto.setErrorCode("0");
						isacResDto.setErrorMessage("USER_UPDATED_SUCCESSFULLY");
					}
				}
			}
		}catch (CMSValidationFault e) {
			isacResDto.setRefNumber(isacReqDto.getRefNumber());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage(e.getMessage());
		//	throw e; 
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error during user update ######## ", e);
			isacResDto.setRefNumber(isacReqDto.getRefNumber());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage(e.getMessage());
		//	throw new CMSException(e.getMessage(),e); 
		}
		return isacResDto;
	}
	
}
