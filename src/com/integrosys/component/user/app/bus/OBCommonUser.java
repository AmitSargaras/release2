package com.integrosys.component.user.app.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import java.util.Date;

public class OBCommonUser implements ICommonUser {
	private long userID;
	private String loginID;
	private String userName;
	private String employeeID;
	private String department;
	private String position;
	private String phoneNumber;
	private String email;
	private String password;
	private IRoleType roleType;
	private long versionTime;
	private String status;
	private Long cityId;
	private Long stateId;
	private Long countryId;
	private String address;
	private Long region;
	private String branchCode;
	ICommonUserSegment[] userSegment;
	ICommonUserRegion[] userRegion;
	private String _country;
	private String _organisation;
	private Date validFromDate;
	private Date validToDate;
	private ITeamTypeMembership teamTypeMembership;

	private String employeeGrade;
	private String overrideExceptionForLoa;
	
	public ITeamTypeMembership getTeamTypeMembership() {
		return this.teamTypeMembership;
	}

	public void setTeamTypeMembership(ITeamTypeMembership teamTypeMembership) {
		this.teamTypeMembership = teamTypeMembership;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OBCommonUser() {
		this.userSegment = null;
		this.userRegion = null;
		this._country = null;
		this._organisation = null;
	}

	public OBCommonUser(ICommonUser user) {
		this();
		AccessorUtil.copyValue(user, this);
	}

	public long getUserID() {
		return this.userID;
	}

	public void setUserID(long aUserID) {
		this.userID = aUserID;
	}

	public String getLoginID() {
		return this.loginID;
	}

	public void setLoginID(String aLoginID) {
		this.loginID = aLoginID;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String aUserName) {
		this.userName = aUserName;
	}

	public String getEmployeeID() {
		return this.employeeID;
	}

	public void setEmployeeID(String aEmployeeID) {
		this.employeeID = aEmployeeID;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String aDepartment) {
		this.department = aDepartment;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String aPosition) {
		this.position = aPosition;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String aPhoneNumber) {
		this.phoneNumber = aPhoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String aEmail) {
		this.email = aEmail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoleType(IRoleType roleType) {
		this.roleType = roleType;
	}

	public IRoleType getRoleType() {
		return this.roleType;
	}

	public String getCountry() {
		return this._country;
	}

	public String getOrganisation() {
		return this._organisation;
	}

	public void setCountry(String value) {
		this._country = value;
	}

	public void setOrganisation(String value) {
		this._organisation = value;
	}

	public Date getValidFromDate() {
		return this.validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidToDate() {
		return this.validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public Long getEjbCityId() {
		return this.cityId;
	}

	public void setEjbCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getEjbStateId() {
		return this.stateId;
	}

	public void setEjbStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getEjbCountryId() {
		return this.countryId;
	}

	public void setEjbCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getEjbAddress() {
		return this.address;
	}

	public void setEjbAddress(String address) {
		this.address = address;
	}

	public Long getEjbRegion() {
		return this.region;
	}

	public void setEjbRegion(Long region) {
		this.region = region;
	}

	public String getEjbBranchCode() {
		return this.branchCode;
	}

	public void setEjbBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public ICommonUserSegment[] getUserSegment() {
		return this.userSegment;
	}

	public void setUserSegment(ICommonUserSegment[] userSegment) {
		this.userSegment = userSegment;
	}

	public ICommonUserRegion[] getUserRegion() {
		return this.userRegion;
	}

	public void setUserRegion(ICommonUserRegion[] userRegion) {
		this.userRegion = userRegion;
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