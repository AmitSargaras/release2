/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.user;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * @author $Author: sathish $<br>
 * 
 * @version $Revision: 1.4 $
 * 
 * @since $Date: 2003/08/21 08:37:47 $
 * 
 *        Tag: $Name: $
 */

public class MaintainUserForm extends TrxContextForm implements Serializable {

	private String loginId = "";

	private String password = "";

	private String confirmPassword = "";

	private boolean creation;// added by gtm

	private String name = "";

	private String empId = "";

	private String contactNo = "";

	private String email = "";

	private String teamName = "";

	private String countryCode = "";

	private String status = "";

	private String validFromDate = "";

	private String validToDate = "";
	
	private String disableReason = "";
	
	private String teamTypeMembership="";
	
	private String address;
	private String cityTown;
	private String state;
	private String country;
	private String region;
	private String segment;
	private String allSegment;
	private String userRegion;
	private String allUserRegion;
	
	private String department;
	
	private String appendSegmentList;
	
	private String appendUserRegionList;

	private String isUnlock = "";
	
	private String employeeGrade;
	private String overrideExceptionForLoa;
	
	
	public String getIsUnlock() {
		return isUnlock;
	}

	public void setIsUnlock(String isUnlock) {
		this.isUnlock = isUnlock;
	}

	public String getRegion() {
		return region;
	}

	public String getTeamTypeMembership() {
		return teamTypeMembership;
	}

	public void setTeamTypeMembership(String teamTypeMembership) {
		this.teamTypeMembership = teamTypeMembership;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	private String branchCode;
	private String branchName;
	
//	private String departmentCode;
	private String event;

	private String searchBy;
	
	/*Added by archana - start*/
	private FormFile fileUpload;
	

	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	/*Added by archana - start*/

	public String getDisableReason() {
		return disableReason;
	}

	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	public String getLoginId() {

		return loginId;

	}

	public void setLoginId(String loginId) {

		this.loginId = loginId;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String pwd) {

		this.password = pwd;

	}

	public boolean getCreation() {

		return creation;

	}

	public void setCreation(boolean flag) {

		this.creation = flag;

	}

	public String getConfirmPassword() {

		return confirmPassword;

	}

	public void setConfirmPassword(String confPwd) {

		this.confirmPassword = confPwd;

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getEmpId() {

		return empId;

	}

	public void setEmpId(String empId) {

		this.empId = empId;

	}

	public String getContactNo() {

		return contactNo;

	}

	public void setContactNo(String contactNo) {

		this.contactNo = contactNo;

	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public String getTeamName() {

		return teamName;

	}

	public void setTeamName(String teamName) {

		this.teamName = teamName;

	}

	public String getCountryCode() {

		return countryCode;

	}

	public void setCountryCode(String countryCode) {

		this.countryCode = countryCode;

	}

	public String getStatus() {

		return status;

	}

	public void setStatus(String status) {

		this.status = status;

	}

	public String getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}

	public String getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

	public void reset() {

	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String[][] getMapper() {

		String[][] input = {

		{ "CommonUserSearchCriteria", "com.integrosys.cms.ui.user.MaintainUserMapper" },

		{ "CommonUser", "com.integrosys.cms.ui.user.MaintainUserMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityTown() {
		return cityTown;
	}

	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

//	public String getDepartmentCode() {
//		return departmentCode;
//	}
//
//	public void setDepartmentCode(String departmentCode) {
//		this.departmentCode = departmentCode;
//	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getAllSegment() {
		return allSegment;
	}

	public void setAllSegment(String allSegment) {
		this.allSegment = allSegment;
	}

	public String getUserRegion() {
		return userRegion;
	}

	public void setUserRegion(String userRegion) {
		this.userRegion = userRegion;
	}

	public String getAllUserRegion() {
		return allUserRegion;
	}

	public void setAllUserRegion(String allUserRegion) {
		this.allUserRegion = allUserRegion;
	}

	public String getAppendSegmentList() {
		return appendSegmentList;
	}

	public void setAppendSegmentList(String appendSegmentList) {
		this.appendSegmentList = appendSegmentList;
	}

	public String getAppendUserRegionList() {
		return appendUserRegionList;
	}

	public void setAppendUserRegionList(String appendUserRegionList) {
		this.appendUserRegionList = appendUserRegionList;
	}
	
	public String getEmployeeGrade() {
		return employeeGrade;
	}

	public void setEmployeeGrade(String employeeGrade) {
		this.employeeGrade = employeeGrade;
	}

	public String getOverrideExceptionForLoa() {
		return overrideExceptionForLoa;
	}

	public void setOverrideExceptionForLoa(String overrideExceptionForLoa) {
		this.overrideExceptionForLoa = overrideExceptionForLoa;
	}
	
	
	private String isacRefNumber;
	private String makerDt;
	private String checkerDt;


	public String getIsacRefNumber() {
		return isacRefNumber;
	}

	public void setIsacRefNumber(String isacRefNumber) {
		this.isacRefNumber = isacRefNumber;
	}

	public String getMakerDt() {
		return makerDt;
	}

	public void setMakerDt(String makerDt) {
		this.makerDt = makerDt;
	}

	public String getCheckerDt() {
		return checkerDt;
	}

	public void setCheckerDt(String checkerDt) {
		this.checkerDt = checkerDt;
	}

	
	

}
