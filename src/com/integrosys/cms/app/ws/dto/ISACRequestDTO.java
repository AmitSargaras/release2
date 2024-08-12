/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


/**

 * @author $Author: Bhushan$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ISACInfo")
public class ISACRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	@XmlElement(name = "RefNumber",required=true)
	private String refNumber;
	
	@XmlElement(name = "ACTION",required=true)
	private String action;
	
	@XmlElement(name = "USER_ID",required=true)
	private String user_id;

	
	@XmlElement(name = "TELEPHONE_NO",required=true)
	private String telephone_no;
	
	@XmlElement(name = "USER_NAME",required=true)
	private String user_name;
	
	@XmlElement(name = "EMAIL",required=true)
	private String email;
	
	@XmlElement(name = "EMPLOYEE_CODE",required=true)
	private String employee_code;
	
	@XmlElement(name = "ADDRESS",required=true)
	private String address;
	
	@XmlElement(name = "BRANCH_CODE",required=true)
	private String branch_code;
	
	@XmlElement(name = "COUNTRY",required=true)
	private String country;
	
	@XmlElement(name = "REGION",required=true)
	private String region;
	
	
	@XmlElement(name = "STATE",required=true)
	private String state;
	
	
	@XmlElement(name = "CITY",required=true)
	private String city;
	
	
	@XmlElement(name = "USER_STATUS",required=true)
	private String user_status;
	
	@XmlElement(name = "DISABLEMENT_REASON",required=true)
	private String disablement_reason;
	
	@XmlElement(name = "USER_ROLE",required=true)
	private String user_role;
	
	@XmlElement(name = "DEPARTMENT",required=true)
	private String department;
	
	@XmlElement(name = "EMPLOYEE_GRADE",required=true)
	private String employee_grade;
	
	@XmlElement(name = "OVERRIGHT_EXC_LOA",required=true)
	private String overright_exc_loa;
	
	@XmlElement(name = "MAKER_ID",required=true)
	private String maker_id;
	
	@XmlElement(name = "CHECKER_ID",required=true)
	private String checker_id;
	
	@XmlElement(name = "MAKER_DATE",required=true)
	private String maker_date;
	
	@XmlElement(name = "CHECKER_DATE",required=true)
	private String checker_date;
	
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String subFacilityName;
	
	@XmlTransient
	private String event;
	
	@XmlTransient
	private String totalPartySanctionedAmount;
	
	
	

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}



	public String getTotalPartySanctionedAmount() {
		return totalPartySanctionedAmount;
	}

	public void setTotalPartySanctionedAmount(String totalPartySanctionedAmount) {
		this.totalPartySanctionedAmount = totalPartySanctionedAmount;
	}

	public String getSubFacilityName() {
		return subFacilityName;
	}

	public void setSubFacilityName(String subFacilityName) {
		this.subFacilityName = subFacilityName;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTelephone_no() {
		return telephone_no;
	}

	public void setTelephone_no(String telephone_no) {
		this.telephone_no = telephone_no;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployee_code() {
		return employee_code;
	}

	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public String getDisablement_reason() {
		return disablement_reason;
	}

	public void setDisablement_reason(String disablement_reason) {
		this.disablement_reason = disablement_reason;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployee_grade() {
		return employee_grade;
	}

	public void setEmployee_grade(String employee_grade) {
		this.employee_grade = employee_grade;
	}

	public String getOverright_exc_loa() {
		return overright_exc_loa;
	}

	public void setOverright_exc_loa(String overright_exc_loa) {
		this.overright_exc_loa = overright_exc_loa;
	}

	public String getMaker_id() {
		return maker_id;
	}

	public void setMaker_id(String maker_id) {
		this.maker_id = maker_id;
	}

	public String getChecker_id() {
		return checker_id;
	}

	public void setChecker_id(String checker_id) {
		this.checker_id = checker_id;
	}

	public String getMaker_date() {
		return maker_date;
	}

	public void setMaker_date(String maker_date) {
		this.maker_date = maker_date;
	}

	public String getChecker_date() {
		return checker_date;
	}

	public void setChecker_date(String checker_date) {
		this.checker_date = checker_date;
	}

	

}