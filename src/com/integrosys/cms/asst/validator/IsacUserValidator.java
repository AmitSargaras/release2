/**
 * 
 */
package com.integrosys.cms.asst.validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.ws.dto.ISACRequestDTO;
import com.integrosys.cms.app.ws.dto.ISACResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * @author janki.mandalia
 * 
 */
public class IsacUserValidator {
	
	public ISACResponseDTO  isacValidateAdd(ISACRequestDTO isacReqDto,ISACResponseDTO isacResDto ,ICommonUserTrxValue trxValue) throws CMSValidationFault,CMSFault{
		String errorCode = "";
		        
		if(null != isacReqDto) {
			System.out.println("IsacUserValidator.java=>isacValidateAdd()=>isacReqDto=>"+isacReqDto.toString());
		}else {
			System.out.println("IsacUserValidator.java=>isacValidateAdd()=>isacReqDto is not null.");
		}
		
        if (null == isacReqDto.getUser_name() || "".equals(isacReqDto.getUser_name().trim() )  ) {
			
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Username can not be blank");
			return isacResDto;
		}
        
        System.out.println("IsacUserValidator.java=>isacValidateAdd()=>Line37=>isacReqDto.getDepartment()=>"+isacReqDto.getDepartment()+"******");
		 if (null == isacReqDto.getDepartment() || "".equals(isacReqDto.getDepartment().trim() )  ) {
			
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Department");
			return isacResDto;
		}
		 System.out.println("IsacUserValidator.java=>isacValidateAdd=>Line45=>isacReqDto.getDepartment()=>"+isacReqDto.getDepartment()+"******isacReqDto=>"+isacReqDto);
		 if (null == isacReqDto.getUser_role() || "".equals(isacReqDto.getUser_role().trim()) || isacReqDto.getUser_role().trim().length() > 200   ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid User Role");
				return isacResDto;
			}
		 if (null == isacReqDto.getBranch_code() || "".equals(isacReqDto.getBranch_code().trim() )  ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid Branch Code");
				return isacResDto;
			}
		 
		 if (null == isacReqDto.getEmployee_code() || "".equals(isacReqDto.getEmployee_code().trim() )  ) {
			
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Employee Code can not be blank");
				return isacResDto;
		 }
		 if ( isacReqDto.getUser_status().trim().length()>30   ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid User Status");
				return isacResDto;
		 }
		 if (!isacReqDto.getUser_id().trim().equals(isacReqDto.getEmployee_code().trim() )  ) {
			 
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Employee Code should be same as User Id");
				return isacResDto;
			}
	
			if (!(errorCode = Validator.checkString(isacReqDto.getUser_name().trim(), true, 1, 50)).equals(Validator.ERROR_NONE)  ) {
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid User Name");
				return isacResDto;
			}
			
			
		
		return isacResDto;
	
	}

	public ISACResponseDTO  isacValidateUpdate(ISACRequestDTO isacReqDto,ISACResponseDTO isacResDto ,long userId,ICommonUserTrxValue trxValue) throws CMSValidationFault,CMSFault{
		String errorCode = "";
		
		if(null != isacReqDto) {
			System.out.println("IsacUserValidator.java=>isacValidateUpdate()=>isacReqDto=>"+isacReqDto.toString());
		}else {
			System.out.println("IsacUserValidator.java=>isacValidateUpdate()=>isacReqDto is not null.");
		}
		IsacUserDaoImpl isacDao = new IsacUserDaoImpl();
		String userCurrStatus= isacDao.getUserStatus(userId);
		
		System.out.println("IsacUserValidator.java=>isacValidateUpdate()=>userCurrStatus=>"+userCurrStatus+"****");
		
		//Unlock(U) 
		if("U".equals(isacReqDto.getUser_status().trim())  && !"O".equals(userCurrStatus)          ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID IS NOT LOCKED");
			return isacResDto;
		}
		
		/*else if("A".equals(isacReqDto.getUser_status().trim())  &&  "A".equals(userCurrStatus)){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("RECORD WITH SAME STATUS EXIST");
			return isacResDto;
		}*/
		
		
		//Enable(A)
		else if("A".equals(isacReqDto.getUser_status().trim())  && (!"I".equals(userCurrStatus) && !"D".equals(userCurrStatus)) && !"U".equals(userCurrStatus )  && !"R".equals(userCurrStatus) && !"NR".equals(userCurrStatus) && !"A".equals(userCurrStatus)  ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID NOT ALLOW TO ENABLE");
			return isacResDto;
		}
		
		//Disable(I) 
		else if("I".equals(isacReqDto.getUser_status().trim())  && (!"A".equals(userCurrStatus) && !"R".equals(userCurrStatus) && !"NR".equals(userCurrStatus))  ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID NOT ALLOW TO DISABLE");

			return isacResDto;
		}
		
	/*	//Terminate(T) 
		else if("T".equals(isacReqDto.getUser_status())  && (!"A".equals(userCurrStatus) && !"I".equals(userCurrStatus) && !"O".equals(userCurrStatus)) && !"U".equals(userCurrStatus )   ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID NOT ALLOWED TO TERMINATE");
			return isacResDto;
		}*/
				
		//Locked(O) 	
		else if("O".equals(isacReqDto.getUser_status().trim())  && (!"A".equals(userCurrStatus) )  ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID IS NOT IN ENABLE STATUS TO LOCKED");
			return isacResDto;
		}
		
		//Revoke(R) 
		else if("R".equals(isacReqDto.getUser_status().trim())  && (!"T".equals(userCurrStatus) )  ){
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USERID IS NOT TERMINATED");
			return isacResDto;
		}
			
		
		return isacResDto;
		
	}
	
	
	public ISACResponseDTO  isacCommonValidations(ISACRequestDTO isacReqDto,ISACResponseDTO isacResDto ,ICommonUserTrxValue trxValue) throws CMSValidationFault,CMSFault, SQLException{
		String errorCode = "";
		
		if(null != isacReqDto) {
			System.out.println("IsacUserValidator.java=>isacCommonValidations()=>isacReqDto=>"+isacReqDto.toString());
		}else {
			System.out.println("IsacUserValidator.java=>isacCommonValidations()=>isacReqDto is not null.");
		}
		
		boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(isacReqDto.getUser_id().trim());
		
		if( codeFlag == true ||  null == isacReqDto.getUser_id() || "".equals(isacReqDto.getUser_id().trim()) ||  isacReqDto.getUser_id().trim().length() > 12 )
		{
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid User ID");
			return isacResDto;
		}
	
		boolean validRefNo= isDuplicateRefNumber(isacReqDto.getRefNumber().trim());
		
		 /*if ( !validRefNo   ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Duplicate Reference Number");
				return isacResDto;
			}*/
		if ( null == isacReqDto.getRefNumber() || "".equals(isacReqDto.getRefNumber().trim()) ||  isacReqDto.getRefNumber().trim().length() > 30 ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid Reference Number");
				return isacResDto;
			}
		
		
		if (  "U".equals(isacReqDto.getAction().trim()) &&  (null == isacReqDto.getUser_status() || "".equals(isacReqDto.getUser_status().trim()))&&   !"A".equals(trxValue.getUser().getStatus().trim()) ) {
			
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("USER STATUS IS NOT ACTIVE");
			return isacResDto;
		}
	        	
		 if ( !(null == isacReqDto.getBranch_code() || "".equals(isacReqDto.getBranch_code().trim()))){
			 String branchCode=isacReqDto.getBranch_code().trim();
				if (isacReqDto.getBranch_code().trim().length() > 4) {
					branchCode = isacReqDto.getBranch_code().trim().substring(isacReqDto.getBranch_code().trim().length() - 4);
				}
		 
		boolean isValidBranchCode= isValidBranchCode(branchCode);
		
		 if ( !isValidBranchCode   ) {
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid Branch Code");
				return isacResDto;
			}
		 }
		 
		 boolean numericUserRole = ASSTValidator.isNumeric(isacReqDto.getUser_role().trim());
		 boolean isValidUserRole=false;
		 if(numericUserRole) {
		  isValidUserRole= isValidUserRole(isacReqDto.getUser_role().trim());
		 }
			 if (  !(null == isacReqDto.getUser_role() || "".equals(isacReqDto.getUser_role().trim()))
					&& (!isValidUserRole  ||  isacReqDto.getUser_role().trim().length() >200) ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid User Role");
				return isacResDto;
			}
		 
	    if (!(errorCode = Validator.checkEmail(isacReqDto.getEmail().trim(), false)).equals(Validator.ERROR_NONE)) {
			
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Email Id.");
			return isacResDto;
		}
	    if(!(null == isacReqDto.getEmail() || "".equals(isacReqDto.getEmail().trim())) && isacReqDto.getEmail().trim().length() >50 )
		{
	
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Email Id");
			return isacResDto;
		}
				    
		//addess
		if(!(null == isacReqDto.getAddress() || "".equals(isacReqDto.getAddress().trim())) )
		{
		if ( !(errorCode = Validator.checkString(isacReqDto.getAddress().trim(), false, 1, 199))
				.equals(Validator.ERROR_NONE)) {
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Address");
			return isacResDto;
		}
		}
		
		//country
		boolean validCounty = ASSTValidator.isValidAlphaNumStringWithoutSpace(isacReqDto.getCountry().trim());

		if(!(null == isacReqDto.getCountry() || "".equals(isacReqDto.getCountry().trim())) && (isacReqDto.getCountry().trim().length() >50 || validCounty == true ))
		{

			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid County");
			return isacResDto;
		}
		
		boolean validRegion= ASSTValidator.isValidAlphaNumStringWithoutSpace(isacReqDto.getRegion().trim());

		if(!(null == isacReqDto.getRegion() || "".equals(isacReqDto.getRegion().trim())) && (isacReqDto.getRegion().trim().length() >30 || validRegion == true ))
		{
		
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Region");
			return isacResDto;
		}
		
		boolean validState= ASSTValidator.isValidAlphaNumStringWithoutSpace(isacReqDto.getState().trim());

		if(!(null == isacReqDto.getState() || "".equals(isacReqDto.getState().trim())) && (isacReqDto.getState().trim().length() >30 || validState == true) )
		{
		
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid State");
			return isacResDto;
		}
		
		boolean validCity= ASSTValidator.isValidAlphaNumStringWithoutSpace(isacReqDto.getCity().trim());
		System.out.println("validCity=============="+validCity);

		if(!(null == isacReqDto.getCity() || "".equals(isacReqDto.getCity().trim())) && (isacReqDto.getCity().trim().length() >30 || validCity == true ))
		{
		
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid City");
			return isacResDto;
		}
	
		boolean isValidDepartment= isValidDepartment(isacReqDto.getDepartment().trim(),isacReqDto);
		
		System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 295=>isValidDepartment=="+isValidDepartment+"**isacReqDto.getDepartment()=>"+isacReqDto.getDepartment()+"******");
		System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 296=>isacReqDto=>"+isacReqDto);
		if ( ( null != isacReqDto.getDepartment() && !"".equals(isacReqDto.getDepartment().trim()))
			&& (!isValidDepartment || !(errorCode = Validator.checkString(isacReqDto.getDepartment(), true, 1, 40)).equals(Validator.ERROR_NONE)  )) {
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			System.out.println("IsacUserValidator.java=>isacCommonValidations()=>Line 300=>isacReqDto.getDepartment()=>"+isacReqDto.getDepartment()+"******isacReqDto.getRefNumber()=>"+isacReqDto.getRefNumber()+"****");
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Department");
			return isacResDto;
		}
		System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 305=>isacReqDto=>"+isacReqDto);
		 boolean isValidEmpGrade= isValidEmpGrade(isacReqDto.getEmployee_grade().trim());
			 if (  !(null == isacReqDto.getEmployee_grade() || "".equals(isacReqDto.getEmployee_grade().trim()))
					 &&  !isValidEmpGrade  || isacReqDto.getEmployee_grade().trim().length() > 40  ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid Employee Grade");
				return isacResDto;
			}
		 
			 
		/*if(!(null == isacReqDto.getEmployee_grade() || isacReqDto.getEmployee_grade().trim().equals(""))   && isacReqDto.getEmployee_grade().length() >40)
		{
		
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Employee Grade");
			return isacResDto;
		}*/
		
		if("I".equals(isacReqDto.getUser_status().trim())) {
		if (!(errorCode = Validator.checkString(isacReqDto.getDisablement_reason().trim(), true, 1, 40)).equals(Validator.ERROR_NONE)  ) {
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid DisableMent reason");
			return isacResDto;
		}
		}
		if(!(null == isacReqDto.getDisablement_reason() || "".equals(isacReqDto.getDisablement_reason().trim())) && isacReqDto.getDisablement_reason().trim().length() > 40  )
		{
		
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid DisableMent reason");
				return isacResDto;
		}
		System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 342=>isacReqDto.getUser_status()=>"+isacReqDto.getUser_status()+"**");
		 if( !"U".equals(isacReqDto.getUser_status().trim())  && !"A".equals(isacReqDto.getUser_status().trim())      
				&&	!"I".equals(isacReqDto.getUser_status().trim())  && !"T".equals(isacReqDto.getUser_status().trim())  
				&&  !"O".equals(isacReqDto.getUser_status().trim())  && !"R".equals(isacReqDto.getUser_status().trim())  	
		 	    && !"".equals(isacReqDto.getUser_status().trim())
		)	{
			 System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 348=>isacReqDto.getUser_status()=>"+isacReqDto.getUser_status()+"***isacReqDto.getRefNumber()=>"+isacReqDto.getRefNumber());
			 System.out.println("IsacUserValidator.java=>isacCommonValidations=>Line 349=>isacReqDto=>"+isacReqDto);
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid User Status");
			return isacResDto;
		}
		 
		 /*if("U".equals(isacReqDto.getAction().trim()) && (null == isacReqDto.getUser_status() || "".equals(isacReqDto.getUser_status().trim())) ){
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("User Status can not be blank");
				return isacResDto;
			}*/
	 
	    if (null == isacReqDto.getRefNumber() || "".equals(isacReqDto.getRefNumber().trim()) ||  isacReqDto.getRefNumber().trim().length() > 30 ) {
			
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Reference Number");
			return isacResDto;
		}
	    
		if(!(isacReqDto.getTelephone_no()==null|| "".equals(isacReqDto.getTelephone_no().trim())))
		{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(isacReqDto.getTelephone_no().toString().trim(),false,15,999999999999999.D)))
		{
			isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
			isacResDto.setErrorCode("1");
			isacResDto.setErrorMessage("Invalid Telephone Number");
			return isacResDto;
		}
		}
		
		 if (null == isacReqDto.getMaker_id() || "".equals(isacReqDto.getMaker_id().trim() )  ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Maker ID can not be blank");
				return isacResDto;
			}
		 
		 if (null == isacReqDto.getChecker_id() || "".equals(isacReqDto.getChecker_id().trim() )  ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Checker ID can not be blank");
				return isacResDto;
			}
		 
		 if (null != isacReqDto.getOverright_exc_loa() && !"".equals(isacReqDto.getOverright_exc_loa().trim()) &&  isacReqDto.getOverright_exc_loa().trim().length() > 1 ) {
				
				isacResDto.setRefNumber(isacReqDto.getRefNumber().trim());
				isacResDto.setErrorCode("1");
				isacResDto.setErrorMessage("Invalid OVERRIGHT_EXC_LOA Value");
				return isacResDto;
			}
		 
		 
			return isacResDto;
		
	}	

	public boolean isDuplicateRefNumber(String refNum) throws SQLException {
		boolean valid= false;	
		
		String sql="select count(*) as count from CMS_USER WHERE  REF_NUMBER = '"+refNum+"' ";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;
	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	
	            	String count = rs.getString("count");
	              if(count.equals("0")){
	            	   valid = true;
	               }
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }
	        finally {
	        	rs.close();
	        	dbUtil.close();
			}
		return valid;
	}

//isValidBranchCode
	public boolean isValidBranchCode(String branchCode) throws SQLException {
		boolean valid= false;	
		
		
		String sql="select count(*) as count from CMS_SYSTEM_BANK_BRANCH "
				+ "where  STATUS!='INACTIVE' AND  deprecated='N' and  SYSTEM_BANK_BRANCH_CODE = '"+branchCode+"' ";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;
	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	
	            	String count = rs.getString("count");
	              if(count.equals("1")){
	            	   valid = true;
	               }
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }
	        finally {
	        	rs.close();
	        	dbUtil.close();
			}
		return valid;
	}
	
	

	//isValidUserRole
		public boolean isValidUserRole(String userRole) throws SQLException {
			boolean valid= false;	
			
			String sql="select count(*) as count from  CMS_TEAM_TYPE_MEMBERSHIP "
					+ "where TEAM_TYPE_MEMBERSHIP_ID = '"+userRole+"' ";
			
		        SQLParameter params = SQLParameter.getInstance();
		        DBUtil dbUtil = null;
		        ResultSet rs = null;
		        try {
		            dbUtil = new DBUtil();
		            dbUtil.setSQL(sql);
		            rs = dbUtil.executeQuery();
		           
		            while (rs.next()) {	
		            	String count = rs.getString("count");
		              if(count.equals("1")){
		            	   valid = true;
		               }
		            }
		        } catch (SQLException ex) {
		            throw new SearchDAOException("SQLException in isValidUserRole():", ex);
		        } catch (Exception ex) {
		            throw new SearchDAOException("Exception in isValidUserRole():", ex);
		        }
		        finally {
		        	rs.close();
		        	dbUtil.close();
				}
			return valid;
		}
		
		//isValidEmpGrade
				public boolean isValidEmpGrade(String empGrade) throws SQLException {
					boolean valid= false;	
					
					String sql="select count(*) as count from  COMMON_CODE_CATEGORY_ENTRY "
							+ "where CATEGORY_CODE ='EMPLOYEE_GRADE' AND ENTRY_CODE = '"+empGrade+"' ";
					
				        SQLParameter params = SQLParameter.getInstance();
				        DBUtil dbUtil = null;
				        ResultSet rs = null;
				        try {
				            dbUtil = new DBUtil();
				            dbUtil.setSQL(sql);
				            rs = dbUtil.executeQuery();
				           
				            while (rs.next()) {	
				            	String count = rs.getString("count");
				              if(count.equals("1")){
				            	   valid = true;
				               }
				            }
				        } catch (SQLException ex) {
				            throw new SearchDAOException("SQLException in isValidEmpGrade():", ex);
				        } catch (Exception ex) {
				            throw new SearchDAOException("Exception in isValidEmpGrade():", ex);
				        }
				        finally {
				        	rs.close();
				        	dbUtil.close();
						}
					return valid;
				}
				
				//isValidDepartment
				public boolean isValidDepartment(String deptCode,ISACRequestDTO isacReqDto) throws SQLException {
					boolean valid= false;	
					int count=0;
					List<String> DeptList = new ArrayList<String>();
					String sql="select ENTRY_CODE as DEPT_CODE from  COMMON_CODE_CATEGORY_ENTRY "
							+ "where CATEGORY_CODE ='HDFC_DEPARTMENT' AND  ACTIVE_STATUS='1'";
					
				        SQLParameter params = SQLParameter.getInstance();
				        DBUtil dbUtil = null;
				        ResultSet rs = null;
				        try {
				            dbUtil = new DBUtil();
				            dbUtil.setSQL(sql);
				            rs = dbUtil.executeQuery();
				           
				            while (rs.next()) {	
				            	DeptList.add(rs.getString("DEPT_CODE"));
				            	
				            }
				          for(int i=0;i<DeptList.size(); i++) {
				          String climsDeptCode=DeptList.get(i);
				  //        System.out.println("climsDeptCode::"+climsDeptCode);
				          String tempdeptCode="";
				          
				           tempdeptCode = climsDeptCode;
				           tempdeptCode = "00000".substring(0, (5-tempdeptCode.length())) + tempdeptCode;
				          
				        	if(deptCode.equals(tempdeptCode) ) {
				        		  count++;
				            	valid=true;
				            	isacReqDto.setDepartment(climsDeptCode);
				            }
				          }
				          if(count>1) {
				        	 valid=false;
				          }
				            
				        } catch (SQLException ex) {
				            throw new SearchDAOException("SQLException in isValidDepartment():", ex);
				        } catch (Exception ex) {
				            throw new SearchDAOException("Exception in isValidDepartment():", ex);
				        }
				        finally {
				        	rs.close();
				        	dbUtil.close();
						}
					return valid;
				}
				
				//forDuplicateRefNumber
				public ISACResponseDTO forDuplicateRefNumber(String refNumber,ISACResponseDTO isacResDto) throws SQLException {
					boolean valid= false;	
					int count=0;
					List<String> DeptList = new ArrayList<String>();
					String sql="select ISAC_ERROR_CODE, ISAC_ERROR_MESSAGE from  CMS_INTERFACE_LOG "
							+ "where ISAC_REFERENCE_NO ='"+refNumber+"'";
					
				        SQLParameter params = SQLParameter.getInstance();
				        DBUtil dbUtil = null;
				        ResultSet rs = null;
				        try {
				            dbUtil = new DBUtil();
				            dbUtil.setSQL(sql);
				            rs = dbUtil.executeQuery();
				           
				            while (rs.next()) {	
				            	
				            	isacResDto.setRefNumber(refNumber);
								isacResDto.setErrorCode(rs.getString("ISAC_ERROR_CODE"));
								isacResDto.setErrorMessage(rs.getString("ISAC_ERROR_MESSAGE"));
				            }
				         
				            
				        } catch (SQLException ex) {
				            throw new SearchDAOException("SQLException in forDuplicateRefNumber():", ex);
				        } catch (Exception ex) {
				            throw new SearchDAOException("Exception in forDuplicateRefNumber():", ex);
				        }
				        finally {
				        	rs.close();
				        	dbUtil.close();
						}
				        return isacResDto;
				}
				
				
}
