/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.user;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.user.bus.OBCMSUser;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.ICommonUserRegion;
import com.integrosys.component.user.app.bus.ICommonUserSegment;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserRegion;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.OBCommonUserSegment;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: ravi $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/23 09:36:28 $ Tag: $Name: $
 */
public class MaintainUserMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public MaintainUserMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "fromMenu", "java.lang.String", REQUEST_SCOPE },
				{ "return", "java.lang.String", REQUEST_SCOPE },
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
					SERVICE_SCOPE },
					{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
						FORM_SCOPE },	
				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		MaintainUserForm aForm = (MaintainUserForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (event.equals("maker_search_user")) {
			ITeam userTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			CommonUserSearchCriteria sc = MaintainUserUitl.createEmptySearchCriteria(user.getLoginID(), userTeam);
			OBCommonUserSearchCriteria obsc = sc.getCriteria();
			DefaultLogger.debug(this, "SearchBy: " + aForm.getSearchBy());
			if ("loginId".equals(aForm.getSearchBy())) {
				if ((aForm.getLoginId() != null) && !"".equals(aForm.getLoginId().trim())) {
					obsc.setLoginId(aForm.getLoginId().toUpperCase());
				}
			}else if ("name".equals(aForm.getSearchBy())) {
				if ((aForm.getName() != null) && !"".equals(aForm.getName().trim())) {
					obsc.setUserName(aForm.getName());
				}
			}else if ("branch".equals(aForm.getSearchBy())) {
				if ((aForm.getBranchCode() != null) && !"".equals(aForm.getBranchCode().trim())) {
					obsc.setBranchCode(aForm.getBranchCode());
				}
			}else if ("status".equals(aForm.getSearchBy())) {
				if ((aForm.getStatus() != null) && !"".equals(aForm.getStatus().trim())) {
					obsc.setStatus(aForm.getStatus());
				}
			}
			
			sc.setCriteria(obsc);
			return sc;
		}
		else if (event.equals("redirect_list_user")) {
			CommonUserSearchCriteria sc = new CommonUserSearchCriteria();
			sc.setStartIndex(aForm.getStartIndex());
			sc.setNItems(10);
			OBCommonUserSearchCriteria obsc = new OBCommonUserSearchCriteria();
			sc.setCriteria(obsc);
			return sc;
		}
		else if (event.equals("maker_edit_user") || event.equals("maker_edit_reject_edit")) {
			ICommonUserTrxValue iUserTrxVal = (ICommonUserTrxValue) map.get("CommonUserTrxValue");
			OBCommonUser obUser = new OBCMSUser();
			AccessorUtil.copyValue(iUserTrxVal.getUser(), obUser);
			obUser.setUserName(aForm.getName());
			if ((aForm.getPassword() != null) && (aForm.getPassword().length() > 0)) {
				obUser.setPassword(aForm.getPassword());
			}
			else {
				obUser.setPassword(null);
			}
			if(aForm.getDepartment()!=null && !(aForm.getDepartment().trim().equals(""))){
			obUser.setDepartment(aForm.getDepartment());
			}
			obUser.setEmployeeID(aForm.getEmpId().toUpperCase());
			obUser.setPhoneNumber(aForm.getContactNo());
			obUser.setEmail(aForm.getEmail());
			obUser.setCountry(aForm.getCountryCode());
			obUser.setStatus(aForm.getStatus());
			obUser.setEjbBranchCode(aForm.getBranchCode());
			/*if( aForm.getCityTown() != null && !(aForm.getCityTown().trim().equals("")) ){
			obUser.setEjbCityId(Long.valueOf(aForm.getCityTown()));
			}
			if( aForm.getCountry() != null && !(aForm.getCountry().trim().equals("")) ){
			obUser.setEjbCountryId(Long.valueOf(aForm.getCountry()));
			}
			if( aForm.getState() != null && !(aForm.getState().trim().equals("")) ){
			obUser.setEjbStateId(Long.valueOf(aForm.getState()));
			}
			obUser.setEjbAddress(aForm.getAddress());
			if( aForm.getRegion() != null && !(aForm.getRegion().trim().equals("")) ){
			obUser.setEjbRegion(Long.valueOf(aForm.getRegion()));
			}*/
			if(aForm.getAppendSegmentList()!=null && !(aForm.getAppendSegmentList().trim().equals(""))){
			String[] stringSegment=commaSeparatedStringToStringArray(aForm.getAppendSegmentList());
			ICommonUserSegment[] userSegment= new ICommonUserSegment[100];
			for(int segmentIndex=0;segmentIndex<stringSegment.length;segmentIndex++){
				ICommonUserSegment segment= new OBCommonUserSegment();
				segment.setUserID(obUser.getUserID());
				segment.setSegmentCode(stringSegment[segmentIndex]);
				 userSegment[segmentIndex]=segment;
			}
			obUser.setUserSegment(userSegment);
			}
			if(aForm.getAppendUserRegionList()!=null && !(aForm.getAppendUserRegionList().trim().equals(""))){
			String[] stringRegion=commaSeparatedStringToStringArray(aForm.getAppendUserRegionList());
			ICommonUserRegion[] userRegion=new ICommonUserRegion[100];
			for(int regionIndex=0;regionIndex<stringRegion.length;regionIndex++){
				ICommonUserRegion  region = new OBCommonUserRegion();
				region.setUserID(obUser.getUserID());
				region.setRegionCode(stringRegion[regionIndex]);
				userRegion[regionIndex]=region; 
				 
			}
			obUser.setUserRegion(userRegion);
			}
			if ((aForm.getValidFromDate() != null) && !("").equals(aForm.getValidFromDate().trim())) {
				obUser.setValidFromDate(DateUtil.convertDate(locale, aForm.getValidFromDate()));
			}
			else {
				obUser.setValidFromDate(null);
			}
			if ((aForm.getValidToDate() != null) && !("").equals(aForm.getValidToDate().trim())) {
				obUser.setValidToDate(DateUtil.convertDate(locale, aForm.getValidToDate()));
			}
			else {
				obUser.setValidToDate(null);
			}
			if(aForm.getDisableReason()!=null && !("").equals(aForm.getDisableReason())){
				obUser.setPosition(aForm.getDisableReason());
			}
			obUser.setEmployeeGrade(aForm.getEmployeeGrade());
			obUser.setOverrideExceptionForLoa(ICMSConstant.YES.equals(aForm.getOverrideExceptionForLoa())? ICMSConstant.YES :ICMSConstant.NO);
			//obUser.setIsacRefNumber("");
			return obUser;
		}
		else if (event.equals("maker_add_user") || event.equals("maker_edit_reject_add")) {
			OBCommonUser obUser = new OBCMSUser();
			if (event.equals("maker_edit_reject_add")) {
				ICommonUserTrxValue iUserTrxVal = (ICommonUserTrxValue) map.get("CommonUserTrxValue");
				AccessorUtil.copyValue(iUserTrxVal.getStagingUser(), obUser);
			}
			obUser.setLoginID(aForm.getLoginId().toUpperCase());
			obUser.setPassword(aForm.getPassword());
			obUser.setUserName(aForm.getName());
			obUser.setEmployeeID(aForm.getEmpId().toUpperCase());
			obUser.setPhoneNumber(aForm.getContactNo());
			obUser.setEmail(aForm.getEmail());
			obUser.setEjbAddress(aForm.getAddress());
			/*if( aForm.getRegion() != null && !(aForm.getRegion().trim().equals("")) ){
			obUser.setEjbRegion(Long.valueOf(aForm.getRegion()));
			}*/
			obUser.setCountry(aForm.getCountryCode());
			obUser.setEjbBranchCode(aForm.getBranchCode());
			/*if( aForm.getCityTown() != null && !(aForm.getCityTown().trim().equals("")) ){
			obUser.setEjbCityId(Long.valueOf(aForm.getCityTown()));
			}
			if( aForm.getCountry() != null && !(aForm.getCountry().trim().equals("")) ){
			obUser.setEjbCountryId(Long.valueOf(aForm.getCountry()));
			}
			if( aForm.getState() != null && !(aForm.getState().trim().equals("")) ){
			obUser.setEjbStateId(Long.valueOf(aForm.getState()));
			}*/
			if(aForm.getDepartment()!=null && !(aForm.getDepartment().trim().equals(""))){
				obUser.setDepartment(aForm.getDepartment());
				}
			
			if(aForm.getAppendSegmentList()!=null && !(aForm.getAppendSegmentList().trim().equals(""))){
			String[] stringSegment=commaSeparatedStringToStringArray(aForm.getAppendSegmentList());
			ICommonUserSegment[] userSegment= new ICommonUserSegment[stringSegment.length];
			for(int segmentIndex=0;segmentIndex<stringSegment.length;segmentIndex++){
				ICommonUserSegment segment= new OBCommonUserSegment();
//				segment.setId(new Long(segmentIndex+100));
				segment.setSegmentCode(stringSegment[segmentIndex]);
				 userSegment[segmentIndex]=segment;
			}
			obUser.setUserSegment(userSegment);
			}
			if(aForm.getAppendUserRegionList()!=null && !(aForm.getAppendUserRegionList().trim().equals(""))){
			String[] stringRegion=commaSeparatedStringToStringArray(aForm.getAppendUserRegionList());
			ICommonUserRegion[] userRegion=new ICommonUserRegion[stringRegion.length];
			for(int regionIndex=0;regionIndex<stringRegion.length;regionIndex++){
				ICommonUserRegion  region = new OBCommonUserRegion();
//				region.setId(new Long((regionIndex+100)));
				
				region.setRegionCode(stringRegion[regionIndex]);
				userRegion[regionIndex]=region; 
				 
			}
			obUser.setUserRegion(userRegion);
			}
//			 obUser.setStatus(UserConstant.STATUS_INACTIVE);
			// GCMS - done in the AddUser command instead
			// obUser.setStatus(UserConstant.STATUS_NEW);
			if ((aForm.getValidFromDate() != null) && !("").equals(aForm.getValidFromDate().trim())) {
				obUser.setValidFromDate(DateUtil.convertDate(locale, aForm.getValidFromDate()));
			}
			if ((aForm.getValidToDate() != null) && !("").equals(aForm.getValidToDate().trim())) {
				obUser.setValidToDate(DateUtil.convertDate(locale, aForm.getValidToDate()));
			}
			if(aForm.getDisableReason()!=null && !("").equals(aForm.getDisableReason())){
				obUser.setPosition(aForm.getDisableReason());
			}
			obUser.setEmployeeGrade(aForm.getEmployeeGrade());
			//obUser.setIsacRefNumber("");
			obUser.setOverrideExceptionForLoa(ICMSConstant.YES.equals(aForm.getOverrideExceptionForLoa())? ICMSConstant.YES :ICMSConstant.NO);
			DefaultLogger.debug(this, " >>user in mapper mapFormToOB<< ");
			return obUser;
		}//File Upload
		else if(event.equals("maker_event_upload_user")){
			if(aForm.getFileUpload()!=null && (!aForm.getFileUpload().equals("")))
	        {
				OBCMSUser obUser = new OBCMSUser();
				obUser.setFileUpload(aForm.getFileUpload());
				return obUser;
	        }
	        }
		return null;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		// Addesd By Abhijit R for Hdfc start
		MaintainUserForm aForm = (MaintainUserForm) cForm;
		OBCommonUser obUser =  (OBCommonUser)obj;
		String event = (String) map.get(ICommonEventConstant.EVENT);
		if (event.equals("maker_edit_reject_add")) {
			ICommonUserTrxValue iUserTrxVal = (ICommonUserTrxValue) map.get("CommonUserTrxValue");
			AccessorUtil.copyValue(iUserTrxVal.getStagingUser(), obUser);
		}
		aForm.setLoginId(obUser.getLoginID().toUpperCase());
		aForm.setPassword(obUser.getPassword());
		aForm.setName(obUser.getUserName());
		aForm.setEmpId(obUser.getEmployeeID().toUpperCase());
		aForm.setContactNo(obUser.getPhoneNumber());
		aForm.setEmail(obUser.getEmail());
		aForm.setAddress(obUser.getEjbAddress());
		if(obUser!=null){
		if(obUser.getTeamTypeMembership()!=null)
		{
			aForm.setTeamName(String.valueOf(obUser.getTeamTypeMembership().getMembershipID()));
		}
		}
//		aForm.setRegion(String.valueOf(obUser.getEjbRegion()));
		aForm.setCountryCode(obUser.getCountry());
		aForm.setBranchCode(obUser.getEjbBranchCode());
	/*	aForm.setCityTown(String.valueOf(obUser.getEjbCityId()));
		aForm.setCountry(String.valueOf(obUser.getEjbCountryId()));
		aForm.setState(String.valueOf(obUser.getEjbStateId()));*/
		if(obUser.getDepartment()!=null && !(obUser.getDepartment().trim().equals(""))){
			aForm.setDepartment(obUser.getDepartment());
			}
		if(obUser.getPosition()!=null && !("").equals(obUser.getPosition())){
			aForm.setDisableReason(obUser.getPosition());
		}
//		 obUser.setStatus(UserConstant.STATUS_INACTIVE);
		// GCMS - done in the AddUser command instead
		// obUser.setStatus(UserConstant.STATUS_NEW);
//		if ((obUser.getValidFromDate() != null) && !("").equals(obUser.getValidFromDate().trim())) {
//			aForm.setValidFromDate(DateUtil.convertDate(locale, obUser.getValidFromDate()));
//		}
//		if ((obUser.getValidToDate() != null) && !("").equals(obUser.getValidToDate().trim())) {
//			aForm.setValidToDate(DateUtil.convertDate(locale, obUser.getValidToDate()));
//		}
		// Addesd By Abhijit R for Hdfc end
		aForm.setEmployeeGrade(obUser.getEmployeeGrade());
		aForm.setOverrideExceptionForLoa(obUser.getOverrideExceptionForLoa());
		//aForm.setIsacRefNumber("");
		DefaultLogger.debug(this, " >>user in mapper mapFormToOB<< " + aForm);
		return aForm;
	
	}
	public static String[] commaSeparatedStringToStringArray(String aString) {
		String[] splittArray = null;
		if (aString != null && !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("\\-");
		}
		return splittArray;
	}
}
