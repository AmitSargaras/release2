package com.integrosys.component.user.app.bus;

import com.integrosys.base.businfra.user.IUser;
import com.integrosys.base.businfra.workflow.IWorkflowActor;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import java.util.Date;

public interface ICommonUser extends IUser, IWorkflowActor {
	long getUserID();

	String getLoginID();

	String getUserName();

	String getEmployeeID();

	String getDepartment();

	String getPosition();

	String getPhoneNumber();

	String getEmail();

	String getPassword();

	IRoleType getRoleType();

	long getVersionTime();

	String getStatus();

	String getCountry();

	String getOrganisation();

	Date getValidFromDate();

	Date getValidToDate();

	void setUserID(long var1);

	void setLoginID(String var1);

	void setUserName(String var1);

	void setEmployeeID(String var1);

	void setDepartment(String var1);

	void setPosition(String var1);

	void setPhoneNumber(String var1);

	void setEmail(String var1);

	void setPassword(String var1);

	void setRoleType(IRoleType var1);

	void setVersionTime(long var1);

	void setStatus(String var1);

	void setCountry(String var1);

	void setOrganisation(String var1);

	void setValidFromDate(Date var1);

	void setValidToDate(Date var1);

	ITeamTypeMembership getTeamTypeMembership();

	void setTeamTypeMembership(ITeamTypeMembership var1);

	Long getEjbCityId();

	Long getEjbStateId();

	Long getEjbCountryId();

	String getEjbBranchCode();

	String getEjbAddress();

	Long getEjbRegion();

	void setEjbCityId(Long var1);

	void setEjbStateId(Long var1);

	void setEjbCountryId(Long var1);

	void setEjbBranchCode(String var1);

	void setEjbAddress(String var1);

	void setEjbRegion(Long var1);

	ICommonUserSegment[] getUserSegment();

	void setUserSegment(ICommonUserSegment[] var1);

	ICommonUserRegion[] getUserRegion();

	void setUserRegion(ICommonUserRegion[] var1);
	
	String getEmployeeGrade();

	void setEmployeeGrade(String employeeGrade);

	String getOverrideExceptionForLoa();

	void setOverrideExceptionForLoa(String overrideExceptionForLoa);
	
	String getIsacRefNumber();

	void setIsacRefNumber(String isacRefNumber);
	
	String getMakerDt();

	void setMakerDt(String makerDt);
	
	String getCheckerDt();

	void setCheckerDt(String checkerDt);
	

}