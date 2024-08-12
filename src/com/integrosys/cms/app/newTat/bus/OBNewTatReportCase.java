
package com.integrosys.cms.app.newTat.bus;


import java.util.Date;

import com.integrosys.cms.app.component.bus.OBComponent;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBNewTatReportCase   {
	
	/**
	 * constructor
	 */
	public OBNewTatReportCase() {
		
	}
	private long id;
	private long versionTime;
	private String event;
	private String category;
	private String party_Region;
	private String rM_Region;
	private String line_no;
	private String limit_released_amount;
	private String serial_No;
	private String case_RM;
	private String party_RM;
	private String case_No;
	private String party_Name;
	private String party_ID;
	private String customer_Segment;
	private String rM_Document_submitted_date;
	private String rM_Document_Submitted_Time;
	private String rM_Actual_Document_Submitted_Datetime;
	private String limit_Document_received_time;
	private String limit_document_received_date;
	private String actual_limit_document_received_datetime;
	private String limit_document_Scanned_Date;
	private String limit_Document_Scanned_Time;
	private String actual_limit_Document_Scanned_datetime;
	private String deferral_Raised_Date;
	private String deferral_Raised_Time;
	private String actual_deferral_Raised_datetime;
	private String deferral_Approved_Date;
	private String deferral_Approved_Time;
	private String actual_deferral_Approved_datetime;
	private String cPU_limit_set_CLIMS_date;
	private String cPU_Limit_set_CLIMS_Time;
	private String actual_cPU_Limit_set_CLIMS_dateime;
	private String total_Time;
	private String turn_Around_Time;
	private String delay_reason;
	
	
	private String  delayReasonText         ;
	
	public String getDelayReasonText() {
		return delayReasonText;
	}
	public void setDelayReasonText(String delayReasonText) {
		this.delayReasonText = delayReasonText;
	}
	
	public String getDelay_reason() {
		return delay_reason;
	}
	public void setDelay_reason(String delayReason) {
		delay_reason = delayReason;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getParty_Region() {
		return party_Region;
	}
	public void setParty_Region(String partyRegion) {
		party_Region = partyRegion;
	}
	public String getrM_Region() {
		return rM_Region;
	}
	public void setrM_Region(String rMRegion) {
		rM_Region = rMRegion;
	}
	public String getLine_no() {
		return line_no;
	}
	public void setLine_no(String lineNo) {
		line_no = lineNo;
	}
	public String getLimit_released_amount() {
		return limit_released_amount;
	}
	public void setLimit_released_amount(String limitReleasedAmount) {
		limit_released_amount = limitReleasedAmount;
	}
	public String getSerial_No() {
		return serial_No;
	}
	public void setSerial_No(String serialNo) {
		serial_No = serialNo;
	}
	public String getCase_RM() {
		return case_RM;
	}
	public void setCase_RM(String caseRM) {
		case_RM = caseRM;
	}
	public String getParty_RM() {
		return party_RM;
	}
	public void setParty_RM(String partyRM) {
		party_RM = partyRM;
	}
	public String getCase_No() {
		return case_No;
	}
	public void setCase_No(String caseNo) {
		case_No = caseNo;
	}
	public String getParty_Name() {
		return party_Name;
	}
	public void setParty_Name(String partyName) {
		party_Name = partyName;
	}
	public String getParty_ID() {
		return party_ID;
	}
	public void setParty_ID(String partyID) {
		party_ID = partyID;
	}
	public String getCustomer_Segment() {
		return customer_Segment;
	}
	public void setCustomer_Segment(String customerSegment) {
		customer_Segment = customerSegment;
	}
	public String getrM_Document_submitted_date() {
		return rM_Document_submitted_date;
	}
	public void setrM_Document_submitted_date(String rMDocumentSubmittedDate) {
		rM_Document_submitted_date = rMDocumentSubmittedDate;
	}
	public String getrM_Document_Submitted_Time() {
		return rM_Document_Submitted_Time;
	}
	public void setrM_Document_Submitted_Time(String rMDocumentSubmittedTime) {
		rM_Document_Submitted_Time = rMDocumentSubmittedTime;
	}
	public String getLimit_Document_received_time() {
		return limit_Document_received_time;
	}
	public void setLimit_Document_received_time(String limitDocumentReceivedTime) {
		limit_Document_received_time = limitDocumentReceivedTime;
	}
	public String getLimit_document_received_date() {
		return limit_document_received_date;
	}
	public void setLimit_document_received_date(String limitDocumentReceivedDate) {
		limit_document_received_date = limitDocumentReceivedDate;
	}
	public String getLimit_document_Scanned_Date() {
		return limit_document_Scanned_Date;
	}
	public void setLimit_document_Scanned_Date(String limitDocumentScannedDate) {
		limit_document_Scanned_Date = limitDocumentScannedDate;
	}
	public String getLimit_Document_Scanned_Time() {
		return limit_Document_Scanned_Time;
	}
	public void setLimit_Document_Scanned_Time(String limitDocumentScannedTime) {
		limit_Document_Scanned_Time = limitDocumentScannedTime;
	}
	public String getDeferral_Raised_Date() {
		return deferral_Raised_Date;
	}
	public void setDeferral_Raised_Date(String deferralRaisedDate) {
		deferral_Raised_Date = deferralRaisedDate;
	}
	public String getDeferral_Raised_Time() {
		return deferral_Raised_Time;
	}
	public void setDeferral_Raised_Time(String deferralRaisedTime) {
		deferral_Raised_Time = deferralRaisedTime;
	}
	public String getDeferral_Approved_Date() {
		return deferral_Approved_Date;
	}
	public void setDeferral_Approved_Date(String deferralApprovedDate) {
		deferral_Approved_Date = deferralApprovedDate;
	}
	public String getDeferral_Approved_Time() {
		return deferral_Approved_Time;
	}
	public void setDeferral_Approved_Time(String deferralApprovedTime) {
		deferral_Approved_Time = deferralApprovedTime;
	}
	public String getcPU_limit_set_CLIMS_date() {
		return cPU_limit_set_CLIMS_date;
	}
	public void setcPU_limit_set_CLIMS_date(String cPULimitSetCLIMSDate) {
		cPU_limit_set_CLIMS_date = cPULimitSetCLIMSDate;
	}
	public String getcPU_Limit_set_CLIMS_Time() {
		return cPU_Limit_set_CLIMS_Time;
	}
	public void setcPU_Limit_set_CLIMS_Time(String cPULimitSetCLIMSTime) {
		cPU_Limit_set_CLIMS_Time = cPULimitSetCLIMSTime;
	}
	public String getTotal_Time() {
		return total_Time;
	}
	public void setTotal_Time(String totalTime) {
		total_Time = totalTime;
	}
	public String getTurn_Around_Time() {
		return turn_Around_Time;
	}
	public void setTurn_Around_Time(String turnAroundTime) {
		turn_Around_Time = turnAroundTime;
	}
	public String getrM_Actual_Document_Submitted_Datetime() {
		return rM_Actual_Document_Submitted_Datetime;
	}
	public void setrM_Actual_Document_Submitted_Datetime(
			String rMActualDocumentSubmittedDatetime) {
		rM_Actual_Document_Submitted_Datetime = rMActualDocumentSubmittedDatetime;
	}
	public String getActual_limit_document_received_datetime() {
		return actual_limit_document_received_datetime;
	}
	public void setActual_limit_document_received_datetime(
			String actualLimitDocumentReceivedDatetime) {
		actual_limit_document_received_datetime = actualLimitDocumentReceivedDatetime;
	}
	public String getActual_limit_Document_Scanned_datetime() {
		return actual_limit_Document_Scanned_datetime;
	}
	public void setActual_limit_Document_Scanned_datetime(
			String actualLimitDocumentScannedDatetime) {
		actual_limit_Document_Scanned_datetime = actualLimitDocumentScannedDatetime;
	}
	public String getActual_deferral_Raised_datetime() {
		return actual_deferral_Raised_datetime;
	}
	public void setActual_deferral_Raised_datetime(
			String actualDeferralRaisedDatetime) {
		actual_deferral_Raised_datetime = actualDeferralRaisedDatetime;
	}
	public String getActual_deferral_Approved_datetime() {
		return actual_deferral_Approved_datetime;
	}
	public void setActual_deferral_Approved_datetime(
			String actualDeferralApprovedDatetime) {
		actual_deferral_Approved_datetime = actualDeferralApprovedDatetime;
	}
	public String getActual_cPU_Limit_set_CLIMS_dateime() {
		return actual_cPU_Limit_set_CLIMS_dateime;
	}
	public void setActual_cPU_Limit_set_CLIMS_dateime(
			String actualCPULimitSetCLIMSDateime) {
		actual_cPU_Limit_set_CLIMS_dateime = actualCPULimitSetCLIMSDateime;
	}
	
	
	
	
}