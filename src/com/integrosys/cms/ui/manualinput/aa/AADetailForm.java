/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.io.Serializable;
import java.util.List;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.udf.IUserExtendable;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class AADetailForm extends TrxContextForm implements Serializable, IUserExtendable {
	private String aaNum;

	private String aaSource;

	private String aaSourceDesc;

	private String allFaciltyList;
	
	private String facilityNamesList;
	
	private String monitoringResponsibilityList1;

	private String monitoringResponsibilityList2;

	private String monitoringResponsibility;
	
	private String monitoringResponsibility2;
	
	private String finalmonitoringResponsibility;
	
	private String faciltyNameList1;
	
	private String faciltyNameList2;
	
	private String finalFaciltyName;
	
	public String getMonitoringResponsibility2() {
		return monitoringResponsibility2;
	}
	public void setMonitoringResponsibility2(String monitoringResponsibility2) {
		this.monitoringResponsibility2 = monitoringResponsibility2;
	}
	public String getFinalmonitoringResponsibility() {
		return finalmonitoringResponsibility;
	}
	public void setFinalmonitoringResponsibility(
			String finalmonitoringResponsibility) {
		this.finalmonitoringResponsibility = finalmonitoringResponsibility;
	}
	public String getMonitoringResponsibility() {
		return monitoringResponsibility;
	}
	public void setMonitoringResponsibility(String monitoringResponsibility) {
		this.monitoringResponsibility = monitoringResponsibility;
	}
	public String getCovenant_Condition() {
		return covenant_Condition;
	}
	public void setCovenant_Condition(String covenant_Condition) {
		this.covenant_Condition = covenant_Condition;
	}
	public String getMonitoringResponsibilityList2() {
		return monitoringResponsibilityList2;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public void setMonitoringResponsibilityList2(
			String monitoringResponsibilityList2) {
		this.monitoringResponsibilityList2 = monitoringResponsibilityList2;
	}

	private String targetDate;
	private String aaApprovalDate;

	private String bookingCty;

	private String bookingCtyDesc;

	public String getMonitoringResponsibilityList1() {
		return monitoringResponsibilityList1;
	}
	public void setMonitoringResponsibilityList1(String monitoringResponsibilityList1) {
		this.monitoringResponsibilityList1 = monitoringResponsibilityList1;
	}
	public String getCovenantremarks() {
		return covenantremarks;
	}
	public void setCovenantremarks(String covenantremarks) {
		this.covenantremarks = covenantremarks;
	}

	private String bookingOrg;

	private String bookingOrgDesc;

	private String interimReviewDate;

	private String annualReviewDate;

	private String aaType;

	private String leReference;

	private String leID;

	private String aaID;

	private String[] deletedAgreement;
	
	private String camType;
	
	private String camLoginDate;
	
	//private String approvingOfficerGrade = null;
	
	private String extendedNextReviewDate = null;
	
	private String noOfTimesExtended;
	
	private String totalSactionedAmount="0";
	
	private String relationshipManager;
	
	private String controllingBranch;
	
	private String committeApproval;
	
	private String boardApproval;
	
	private String creditApproval1;
	
	private String creditApproval2;
	
	private String creditApproval3;
	
	private String creditApproval4;
	
	private String creditApproval5;
	
	private String creditApproval6;
	
	private String assetClassification;
	
	private String docRemarks;
	
	private String rbiAssetClassification;
	private String covenantType;
	
	private String covenant_Condition;	
	
	private String monitoringResponsibilityProp;
	
	public String getMonitoringResponsibilityProp() {
		return monitoringResponsibilityProp;
	}
	public void setMonitoringResponsibilityProp(
			String monitoringResponsibilityProp) {
		this.monitoringResponsibilityProp = monitoringResponsibilityProp;
	}

	private String compiled;
	
	public String getAllFaciltyList() {
		return allFaciltyList;
	}
	public void setAllFaciltyList(String allFaciltyList) {
		this.allFaciltyList = allFaciltyList;
	}
	public String getCovenantCondition() {
		return covenant_Condition;
	}
	public void setCovenantCondition(String covenant_Condition) {
		this.covenant_Condition = covenant_Condition;
	}
	public String getCompiled() {
		return compiled;
	}
	public void setCompiled(String compiled) {
		this.compiled = compiled;
	}
	public String getAdvised() {
		return advised;
	}
	public void setAdvised(String advised) {
		this.advised = advised;
	}
	public String getMonitoringResponsibilty() {
		return monitoringResponsibilty;
	}
	public void setMonitoringResponsibilty(String monitoringResponsibilty) {
		this.monitoringResponsibilty = monitoringResponsibilty;
	}
	public String getCovenantDescription() {
		return covenantDescription;
	}
	public void setCovenantDescription(String covenantDescription) {
		this.covenantDescription = covenantDescription;
	}
	public String getcovenantRemarks() {
		return covenantremarks;
	}
	public void SetcovenantRemarks(String covenantremarks) {
		this.covenantremarks = covenantremarks;
	}

	private String advised;
	
	private String monitoringResponsibilty;
	
	private String covenantDescription;
	
	private String covenantremarks;
	
	private String CovenantCategory;
	
	public String getCovenantType() {
		return covenantType;
	}
	public void setCovenantType(String covenantType) {
		this.covenantType = covenantType;
	}

	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	private String ramRating;
	private String ramRatingYear;
	private String ramRatingType;
	//Start :Code added for Fully Cash Collateral
	private String fullyCashCollateral;
	
	 //Start:Uma Khot:CRI Field addition enhancement
	private  String isSpecialApprGridForCustBelowHDB8;
	private  String isSingleBorrowerPrudCeiling;
	private  String detailsOfRbiApprovalForSingle;
	private  String isGroupBorrowerPrudCeiling;
	private  String detailsOfRbiApprovalForGroup;
	private  String isNonCooperativeBorrowers;
	private  String isDirectorAsNonCooperativeForOther;
	private  String nameOfDirectorsAndCompany;
	 //End:Uma Khot:CRI Field addition enhancement
	
	public String getRamRating() {
		return ramRating;
	}
	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}
	public String getRamRatingYear() {
		return ramRatingYear;
	}
	public void setRamRatingYear(String ramRatingYear) {
		this.ramRatingYear = ramRatingYear;
	}
	public String getRamRatingType() {
		return ramRatingType;
	}
	
	//End by Pramod Katkar
	public void setRamRatingType(String ramRatingType) {
		this.ramRatingType = ramRatingType;
	}

	public String getFullyCashCollateral() {
		return fullyCashCollateral;
	}

	public void setFullyCashCollateral(String fullyCashCollateral) {
		this.fullyCashCollateral = fullyCashCollateral;
	}
	
	//End   :Code added for Fully Cash Collateral

	
	//Start:Code added for Total Sanctioned Amount Facility Level
	private String totalSanctionedAmountFacLevel="0";

	public String getTotalSanctionedAmountFacLevel() {
		return totalSanctionedAmountFacLevel;
	}

	public void setTotalSanctionedAmountFacLevel(
			String totalSanctionedAmountFacLevel) {
		this.totalSanctionedAmountFacLevel = totalSanctionedAmountFacLevel;
	}
	//End  :Code added for Total Sanctioned Amount Facility Level
	
	public String getDocRemarks() {
		return docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}

	public String getCamLoginDate() {
		return camLoginDate;
	}

	public void setCamLoginDate(String camLoginDate) {
		this.camLoginDate = camLoginDate;
	}
	
/*public String getApprovingOfficerGrade() {
		return approvingOfficerGrade;
	}

	public void setApprovingOfficerGrade(String approvingOfficerGrade) {
		this.approvingOfficerGrade = approvingOfficerGrade;
	}*/

	

	public String getExtendedNextReviewDate() {
		return extendedNextReviewDate;
	}

	public void setExtendedNextReviewDate(String extendedNextReviewDate) {
		this.extendedNextReviewDate = extendedNextReviewDate;
	}

	public String getNoOfTimesExtended() {
		return noOfTimesExtended;
	}

	public void setNoOfTimesExtended(String noOfTimesExtended) {
		this.noOfTimesExtended = noOfTimesExtended;
	}

	public String getTotalSactionedAmount() {
		return totalSactionedAmount;
	}

	public void setTotalSactionedAmount(String totalSactionedAmount) {
		this.totalSactionedAmount = totalSactionedAmount;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getControllingBranch() {
		return controllingBranch;
	}

	public void setControllingBranch(String controllingBranch) {
		this.controllingBranch = controllingBranch;
	}

	public String getCommitteApproval() {
		return committeApproval;
	}

	public void setCommitteApproval(String committeApproval) {
		this.committeApproval = committeApproval;
	}

	public String getBoardApproval() {
		return boardApproval;
	}

	public void setBoardApproval(String boardApproval) {
		this.boardApproval = boardApproval;
	}

	public String getCreditApproval1() {
		return creditApproval1;
	}

	public void setCreditApproval1(String creditApproval1) {
		this.creditApproval1 = creditApproval1;
	}

	public String getCreditApproval2() {
		return creditApproval2;
	}

	public void setCreditApproval2(String creditApproval2) {
		this.creditApproval2 = creditApproval2;
	}

	public String getCreditApproval3() {
		return creditApproval3;
	}

	public void setCreditApproval3(String creditApproval3) {
		this.creditApproval3 = creditApproval3;
	}

	public String getCreditApproval4() {
		return creditApproval4;
	}

	public void setCreditApproval4(String creditApproval4) {
		this.creditApproval4 = creditApproval4;
	}
	
	public String getCreditApproval5() {
		return creditApproval5;
	}

	public void setCreditApproval5(String creditApproval5) {
		this.creditApproval5 = creditApproval5;
	}
	
	public String getCreditApproval6() {
		return creditApproval6;
	}

	public void setCreditApproval6(String creditApproval6) {
		this.creditApproval6 = creditApproval6;
	}

	public String getAssetClassification() {
		return assetClassification;
	}

	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
	}

	public String getRbiAssetClassification() {
		return rbiAssetClassification;
	}

	public void setRbiAssetClassification(String rbiAssetClassification) {
		this.rbiAssetClassification = rbiAssetClassification;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	/**
	 * Description : get method for form to get the AA approval date
	 * 
	 * @return aaApprovalDate
	 */

	public String getAaApprovalDate() {
		return aaApprovalDate;
	}

	/**
	 * Description : set the AA approval date
	 * 
	 * @param aaApprovalDate is the AA approval date
	 */

	public void setAaApprovalDate(String aaApprovalDate) {
		this.aaApprovalDate = aaApprovalDate;
	}

	/**
	 * Description : get method for form to get the AA number
	 * 
	 * @return aaNum
	 */

	public String getAaNum() {
		return aaNum;
	}

	/**
	 * Description : set the AA number
	 * 
	 * @param aaNum is the AA number
	 */

	public void setAaNum(String aaNum) {
		this.aaNum = aaNum;
	}

	/**
	 * Description : get method for form to get the AA source
	 * 
	 * @return aaSource
	 */

	public String getAaSource() {
		return aaSource;
	}

	/**
	 * Description : set the AA source
	 * 
	 * @param aaSource is the AA source
	 */

	public void setAaSource(String aaSource) {
		this.aaSource = aaSource;
	}

	/**
	 * Description : get method for form to get the AA source Description
	 * 
	 * @return aaSourceDesc
	 */

	public String getAaSourceDesc() {
		return aaSourceDesc;
	}

	/**
	 * Description : set the AA source Description
	 * 
	 * @param aaSourceDesc is the AA source Description
	 */

	public void setAaSourceDesc(String aaSourceDesc) {
		this.aaSourceDesc = aaSourceDesc;
	}

	/**
	 * Description : get method for form to get the AA Type
	 * 
	 * @return aaType
	 */

	public String getAaType() {
		return aaType;
	}

	/**
	 * Description : set the AA Type
	 * 
	 * @param aaType is the AA Type
	 */

	public void setAaType(String aaType) {
		this.aaType = aaType;
	}

	/**
	 * Description : get method for form to get the annual review date
	 * 
	 * @return annualReviewDate
	 */

	public String getAnnualReviewDate() {
		return annualReviewDate;
	}

	/**
	 * Description : set the annual review date
	 * 
	 * @param annualReviewDate is the annual review date
	 */

	public void setAnnualReviewDate(String annualReviewDate) {
		this.annualReviewDate = annualReviewDate;
	}

	/**
	 * Description : get method for form to get the booking country code
	 * 
	 * @return bookingCty
	 */

	public String getBookingCty() {
		return bookingCty;
	}

	/**
	 * Description : set the booking country code
	 * 
	 * @param bookingCty is the booking country code
	 */

	public void setBookingCty(String bookingCty) {
		this.bookingCty = bookingCty;
	}

	/**
	 * Description : get method for form to get the booking country code
	 * description
	 * 
	 * @return bookingCtyDesc
	 */

	public String getBookingCtyDesc() {
		return bookingCtyDesc;
	}

	/**
	 * Description : set the booking country code description
	 * 
	 * @param bookingCtyDesc is the booking country code description
	 */

	public void setBookingCtyDesc(String bookingCtyDesc) {
		this.bookingCtyDesc = bookingCtyDesc;
	}

	/**
	 * Description : get method for form to get the booking organisation
	 * 
	 * @return bookingOrg
	 */

	public String getBookingOrg() {
		return bookingOrg;
	}

	/**
	 * Description : set the booking organisation
	 * 
	 * @param bookingOrg is the booking organisation
	 */

	public void setBookingOrg(String bookingOrg) {
		this.bookingOrg = bookingOrg;
	}

	/**
	 * Description : get method for form to get the booking organisation
	 * description
	 * 
	 * @return bookingOrgDesc
	 */

	public String getBookingOrgDesc() {
		return bookingOrgDesc;
	}

	/**
	 * Description : set the booking organisation description
	 * 
	 * @param bookingOrgDesc is the booking organisation description
	 */

	public void setBookingOrgDesc(String bookingOrgDesc) {
		this.bookingOrgDesc = bookingOrgDesc;
	}

	/**
	 * Description : get method for form to get the interim review date
	 * 
	 * @return interimReviewDate
	 */

	public String getInterimReviewDate() {
		return interimReviewDate;
	}

	/**
	 * Description : set the interim review date
	 * 
	 * @param interimReviewDate is the interim review date
	 */

	public void setInterimReviewDate(String interimReviewDate) {
		this.interimReviewDate = interimReviewDate;
	}

	/**
	 * Description : get method for form to get the limit profile ID
	 * 
	 * @return aaID
	 */

	public String getAaID() {
		return aaID;
	}

	/**
	 * Description : set the limit profile ID No
	 * 
	 * @param aaID is the limit profile ID
	 */

	public void setAaID(String aaID) {
		this.aaID = aaID;
	}

	/**
	 * Description : get method for form to get the customer ID
	 * 
	 * @return leReference
	 */

	public String getLeReference() {
		return leReference;
	}

	/**
	 * Description : set the customer ID No
	 * 
	 * @param customerID is the customer ID
	 */

	public void setLeReference(String leReference) {
		this.leReference = leReference;
	}

	/**
	 * Description : get method for form to get the legal ID
	 * 
	 * @return leID
	 */

	public String getLeID() {
		return leID;
	}

	/**
	 * Description : set the legal ID No
	 * 
	 * @param leID is the legal ID
	 */

	public void setLeID(String leID) {
		this.leID = leID;
	}

	/**
	 * Description : get method for form to get the deleted agreement
	 * 
	 * @return deletedAgreement
	 */

	public String[] getDeletedAgreement() {
		return deletedAgreement;
	}

	/**
	 * Description : set the deleted agreement
	 * 
	 * @param deletedAgreement is the deleted agreement
	 */

	public void setDeletedAgreement(String[] deletedAgreement) {
		this.deletedAgreement = deletedAgreement;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {
		String[][] input = { { "InitialLimitProfile", "com.integrosys.cms.ui.manualinput.aa.AADetailMapper" },
				{ "viewImageProfile", "com.integrosys.cms.ui.manualinput.aa.AADetailMapper" },

		{ "LimitProfile", "com.integrosys.cms.ui.manualinput.aa.AADetailMapper" },

		{ "limitProfileTrxVal", "com.integrosys.cms.ui.manualinput.aa.AADetailMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.manualinput.aa.AADetailMapper" }

		};
		return input;
	}

	
	// ---------------- UDF Fields --------------------------------
	// Added by Pradeep on 11th Oct 2011
	// Currently 50 user defined fields (udfs) are supported.
	
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private String udf6;
	private String udf7;
	private String udf8;
	private String udf9;
	private String udf10;
	private String udf11;
	private String udf12;
	private String udf13;
	private String udf14;
	private String udf15;
	private String udf16;
	private String udf17;
	private String udf18;
	private String udf19;
	private String udf20;
	private String udf21;
	private String udf22;
	private String udf23;
	private String udf24;
	private String udf25;
	private String udf26;
	private String udf27;
	private String udf28;
	private String udf29;
	private String udf30;
	private String udf31;
	private String udf32;
	private String udf33;
	private String udf34;
	private String udf35;
	private String udf36;
	private String udf37;
	private String udf38;
	private String udf39;
	private String udf40;
	private String udf41;
	private String udf42;
	private String udf43;
	private String udf44;
	private String udf45;
	private String udf46;
	private String udf47;
	private String udf48;
	private String udf49;
	private String udf50;
	private String udf51;
	private String udf52;
	private String udf53;
	private String udf54;
	private String udf55;
	private String udf56;
	private String udf57;
	private String udf58;
	private String udf59;
	private String udf60;
	private String udf61;
	private String udf62;
	private String udf63;
	private String udf64;
	private String udf65;
	private String udf66;
	private String udf67;
	private String udf68;
	private String udf69;
	private String udf70;
	private String udf71;
	private String udf72;
	private String udf73;
	private String udf74;
	private String udf75;
	private String udf76;
	private String udf77;
	private String udf78;
	private String udf79;
	private String udf80;
	private String udf81;
	private String udf82;
	private String udf83;
	private String udf84;
	private String udf85;
	private String udf86;
	private String udf87;
	private String udf88;
	private String udf89;
	private String udf90;
	private String udf91;
	private String udf92;
	private String udf93;
	private String udf94;
	private String udf95;
	private String udf96;
	private String udf97;
	private String udf98;
	private String udf99;
	private String udf100;
	private String udf101;
		private String udf102;
		private String udf103;
		private String udf104;
		private String udf105;
		private String udf106;
	    private String udf107;
	    private String udf108;
	    private String udf109;
	    private String udf110;
	    private String udf111;
	    private String udf112;
	    private String udf113;
	    private String udf114;
	    private String udf115;
	   
	    private String udf116;
	    private String udf117;
	    private String udf118;
	    private String udf119;
	    private String udf120;
	    private String udf121;
	    private String udf122;
	    private String udf123;
	    private String udf124;
	    private String udf125;
	    private String udf126;
	    private String udf127;
	    private String udf128;
	    private String udf129;
	    private String udf130;
	    private String udf131;
	    private String udf132;
	    private String udf133;
	    private String udf134;
	    private String udf135;
	    private String udf136;
	    private String udf137;
	    private String udf138;
	    private String udf139;
	    private String udf140;
	    private String udf141;
	    private String udf142;
	    private String udf143;
	    private String udf144;
	    private String udf145;
	    private String udf146;
	    private String udf147;
	    private String udf148;
	    private String udf149;
	    private String udf150;

	    private String udf151;
	    private String udf152;
	    private String udf153;
	    private String udf154;
	    private String udf155;
	    private String udf156;
	    private String udf157;
	    private String udf158;
	    private String udf159;
	    private String udf160;
	    private String udf161;
	    private String udf162;
	    private String udf163;
	    private String udf164;
	    private String udf165;
	    private String udf166;
	    private String udf167;
	    private String udf168;
	    private String udf169;
	    private String udf170;
	    private String udf171;
	    private String udf172;
	    private String udf173;
	    private String udf174;
	    private String udf175;
	    private String udf176;
	    private String udf177;
	    private String udf178;
	    private String udf179;
	    private String udf180;
	    private String udf181;
	    private String udf182;
	    private String udf183;
	    private String udf184;
	    private String udf185;
	    private String udf186;
	    private String udf187;
	    private String udf188;
	    private String udf189;
	    private String udf190;
	    private String udf191;
	    private String udf192;
	    private String udf193;
	    private String udf194;
	    private String udf195;
	    private String udf196;
	    private String udf197;
	    private String udf198;
	    private String udf199;
	    private String udf200;
	    private String udf201;
		private String udf202;
		private String udf203;
		private String udf204;
		private String udf205;
		private String udf206;
	    private String udf207;
	    private String udf208;
	    private String udf209;
	    private String udf210;
	    private String udf211;
	    private String udf212;
	    private String udf213;
	    private String udf214;
	    private String udf215;


	
	private long udfId;
	
	
	public long getUdfId() {
		return udfId;
	}

	public void setUdfId(long id) {
		this.udfId = id;
	}

	public String getUdf1() {
		return udf1;
	}

	public String getUdf10() {
		return udf10;
	}

	public String getUdf11() {
		return udf11;
	}

	public String getUdf12() {
		return udf12;
	}

	public String getUdf13() {
		return udf13;
	}

	public String getUdf14() {
		return udf14;
	}

	public String getUdf15() {
		return udf15;
	}

	public String getUdf16() {
		return udf16;
	}

	public String getUdf17() {
		return udf17;
	}

	public String getUdf18() {
		return udf18;
	}

	public String getUdf19() {
		return udf19;
	}

	public String getUdf2() {
		return udf2;
	}

	public String getUdf20() {
		return udf20;
	}

	public String getUdf21() {
		return udf21;
	}

	public String getUdf22() {
		return udf22;
	}

	public String getUdf23() {
		return udf23;
	}

	public String getUdf24() {
		return udf24;
	}

	public String getUdf25() {
		return udf25;
	}

	public String getUdf26() {
		return udf26;
	}

	public String getUdf27() {
		return udf27;
	}

	public String getUdf28() {
		return udf28;
	}

	public String getUdf29() {
		return udf29;
	}

	public String getUdf3() {
		return udf3;
	}

	public String getUdf30() {
		return udf30;
	}

	public String getUdf31() {
		return udf31;
	}

	public String getUdf32() {
		return udf32;
	}

	public String getUdf33() {
		return udf33;
	}

	public String getUdf34() {
		return udf34;
	}

	public String getUdf35() {
		return udf35;
	}

	public String getUdf36() {
		return udf36;
	}

	public String getUdf37() {
		return udf37;
	}

	public String getUdf38() {
		return udf38;
	}

	public String getUdf39() {
		return udf39;
	}

	public String getUdf4() {
		return udf4;
	}

	public String getUdf40() {
		return udf40;
	}

	public String getUdf41() {
		return udf41;
	}

	public String getUdf42() {
		return udf42;
	}

	public String getUdf43() {
		return udf43;
	}

	public String getUdf44() {
		return udf44;
	}

	public String getUdf45() {
		return udf45;
	}

	public String getUdf46() {
		return udf46;
	}

	public String getUdf47() {
		return udf47;
	}

	public String getUdf48() {
		return udf48;
	}

	public String getUdf49() {
		return udf49;
	}

	public String getUdf5() {
		return udf5;
	}

	public String getUdf50() {
		return udf50;
	}

	public String getUdf6() {
		return udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

	public void setUdf11(String udf11) {
		this.udf11 = udf11;
	}

	public void setUdf12(String udf12) {
		this.udf12 = udf12;
	}

	public void setUdf13(String udf13) {
		this.udf13 = udf13;
	}

	public void setUdf14(String udf14) {
		this.udf14 = udf14;
	}

	public void setUdf15(String udf15) {
		this.udf15 = udf15;
	}

	public void setUdf16(String udf16) {
		this.udf16 = udf16;
	}

	public void setUdf17(String udf17) {
		this.udf17 = udf17;
	}

	public void setUdf18(String udf18) {
		this.udf18 = udf18;
	}

	public void setUdf19(String udf19) {
		this.udf19 = udf19;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public void setUdf20(String udf20) {
		this.udf20 = udf20;
	}

	public void setUdf21(String udf21) {
		this.udf21 = udf21;
	}

	public void setUdf22(String udf22) {
		this.udf22 = udf22;
	}

	public void setUdf23(String udf23) {
		this.udf23 = udf23;
	}

	public void setUdf24(String udf24) {
		this.udf24 = udf24;
	}

	public void setUdf25(String udf25) {
		this.udf25 = udf25;
	}

	public void setUdf26(String udf26) {
		this.udf26 = udf26;
	}

	public void setUdf27(String udf27) {
		this.udf27 = udf27;
	}

	public void setUdf28(String udf28) {
		this.udf28 = udf28;
	}

	public void setUdf29(String udf29) {
		this.udf29 = udf29;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public void setUdf30(String udf30) {
		this.udf30 = udf30;
	}

	public void setUdf31(String udf31) {
		this.udf31 = udf31;
	}

	public void setUdf32(String udf32) {
		this.udf32 = udf32;
	}

	public void setUdf33(String udf33) {
		this.udf33 = udf33;
	}

	public void setUdf34(String udf34) {
		this.udf34 = udf34;
	}

	public void setUdf35(String udf35) {
		this.udf35 = udf35;
	}

	public void setUdf36(String udf36) {
		this.udf36 = udf36;
	}

	public void setUdf37(String udf37) {
		this.udf37 = udf37;
	}

	public void setUdf38(String udf38) {
		this.udf38 = udf38;
	}

	public void setUdf39(String udf39) {
		this.udf39 = udf39;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public void setUdf40(String udf40) {
		this.udf40 = udf40;
	}

	public void setUdf41(String udf41) {
		this.udf41 = udf41;
	}

	public void setUdf42(String udf42) {
		this.udf42 = udf42;
	}

	public void setUdf43(String udf43) {
		this.udf43 = udf43;
	}

	public void setUdf44(String udf44) {
		this.udf44 = udf44;
	}

	public void setUdf45(String udf45) {
		this.udf45 = udf45;
	}

	public void setUdf46(String udf46) {
		this.udf46 = udf46;
	}

	public void setUdf47(String udf47) {
		this.udf47 = udf47;
	}

	public void setUdf48(String udf48) {
		this.udf48 = udf48;
	}

	public void setUdf49(String udf49) {
		this.udf49 = udf49;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public void setUdf50(String udf50) {
		this.udf50 = udf50;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf51() {
		return udf51;
	}

	public void setUdf51(String udf51) {
		this.udf51 = udf51;
	}

	public String getUdf52() {
		return udf52;
	}

	public void setUdf52(String udf52) {
		this.udf52 = udf52;
	}

	public String getUdf53() {
		return udf53;
	}

	public void setUdf53(String udf53) {
		this.udf53 = udf53;
	}

	public String getUdf54() {
		return udf54;
	}

	public void setUdf54(String udf54) {
		this.udf54 = udf54;
	}

	public String getUdf55() {
		return udf55;
	}

	public void setUdf55(String udf55) {
		this.udf55 = udf55;
	}

	public String getUdf56() {
		return udf56;
	}

	public void setUdf56(String udf56) {
		this.udf56 = udf56;
	}

	public String getUdf57() {
		return udf57;
	}

	public void setUdf57(String udf57) {
		this.udf57 = udf57;
	}

	public String getUdf58() {
		return udf58;
	}

	public void setUdf58(String udf58) {
		this.udf58 = udf58;
	}

	public String getUdf59() {
		return udf59;
	}

	public void setUdf59(String udf59) {
		this.udf59 = udf59;
	}

	public String getUdf60() {
		return udf60;
	}

	public void setUdf60(String udf60) {
		this.udf60 = udf60;
	}

	public String getUdf61() {
		return udf61;
	}

	public void setUdf61(String udf61) {
		this.udf61 = udf61;
	}

	public String getUdf62() {
		return udf62;
	}

	public void setUdf62(String udf62) {
		this.udf62 = udf62;
	}

	public String getUdf63() {
		return udf63;
	}

	public void setUdf63(String udf63) {
		this.udf63 = udf63;
	}

	public String getUdf64() {
		return udf64;
	}

	public void setUdf64(String udf64) {
		this.udf64 = udf64;
	}

	public String getUdf65() {
		return udf65;
	}

	public void setUdf65(String udf65) {
		this.udf65 = udf65;
	}

	public String getUdf66() {
		return udf66;
	}

	public void setUdf66(String udf66) {
		this.udf66 = udf66;
	}

	public String getUdf67() {
		return udf67;
	}

	public void setUdf67(String udf67) {
		this.udf67 = udf67;
	}

	public String getUdf68() {
		return udf68;
	}

	public void setUdf68(String udf68) {
		this.udf68 = udf68;
	}

	public String getUdf69() {
		return udf69;
	}

	public void setUdf69(String udf69) {
		this.udf69 = udf69;
	}

	public String getUdf70() {
		return udf70;
	}

	public void setUdf70(String udf70) {
		this.udf70 = udf70;
	}

	public String getUdf71() {
		return udf71;
	}

	public void setUdf71(String udf71) {
		this.udf71 = udf71;
	}

	public String getUdf72() {
		return udf72;
	}

	public void setUdf72(String udf72) {
		this.udf72 = udf72;
	}

	public String getUdf73() {
		return udf73;
	}

	public void setUdf73(String udf73) {
		this.udf73 = udf73;
	}

	public String getUdf74() {
		return udf74;
	}

	public void setUdf74(String udf74) {
		this.udf74 = udf74;
	}

	public String getUdf75() {
		return udf75;
	}

	public void setUdf75(String udf75) {
		this.udf75 = udf75;
	}

	public String getUdf76() {
		return udf76;
	}

	public void setUdf76(String udf76) {
		this.udf76 = udf76;
	}

	public String getUdf77() {
		return udf77;
	}

	public void setUdf77(String udf77) {
		this.udf77 = udf77;
	}

	public String getUdf78() {
		return udf78;
	}

	public void setUdf78(String udf78) {
		this.udf78 = udf78;
	}

	public String getUdf79() {
		return udf79;
	}

	public void setUdf79(String udf79) {
		this.udf79 = udf79;
	}

	public String getUdf80() {
		return udf80;
	}

	public void setUdf80(String udf80) {
		this.udf80 = udf80;
	}

	public String getUdf81() {
		return udf81;
	}

	public void setUdf81(String udf81) {
		this.udf81 = udf81;
	}

	public String getUdf82() {
		return udf82;
	}

	public void setUdf82(String udf82) {
		this.udf82 = udf82;
	}

	public String getUdf83() {
		return udf83;
	}

	public void setUdf83(String udf83) {
		this.udf83 = udf83;
	}

	public String getUdf84() {
		return udf84;
	}

	public void setUdf84(String udf84) {
		this.udf84 = udf84;
	}

	public String getUdf85() {
		return udf85;
	}

	public void setUdf85(String udf85) {
		this.udf85 = udf85;
	}

	public String getUdf86() {
		return udf86;
	}

	public void setUdf86(String udf86) {
		this.udf86 = udf86;
	}

	public String getUdf87() {
		return udf87;
	}

	public void setUdf87(String udf87) {
		this.udf87 = udf87;
	}

	public String getUdf88() {
		return udf88;
	}

	public void setUdf88(String udf88) {
		this.udf88 = udf88;
	}

	public String getUdf89() {
		return udf89;
	}

	public void setUdf89(String udf89) {
		this.udf89 = udf89;
	}

	public String getUdf90() {
		return udf90;
	}

	public void setUdf90(String udf90) {
		this.udf90 = udf90;
	}

	public String getUdf91() {
		return udf91;
	}

	public void setUdf91(String udf91) {
		this.udf91 = udf91;
	}

	public String getUdf92() {
		return udf92;
	}

	public void setUdf92(String udf92) {
		this.udf92 = udf92;
	}

	public String getUdf93() {
		return udf93;
	}

	public void setUdf93(String udf93) {
		this.udf93 = udf93;
	}

	public String getUdf94() {
		return udf94;
	}

	public void setUdf94(String udf94) {
		this.udf94 = udf94;
	}

	public String getUdf95() {
		return udf95;
	}

	public void setUdf95(String udf95) {
		this.udf95 = udf95;
	}

	public String getUdf96() {
		return udf96;
	}

	public void setUdf96(String udf96) {
		this.udf96 = udf96;
	}

	public String getUdf97() {
		return udf97;
	}

	public void setUdf97(String udf97) {
		this.udf97 = udf97;
	}

	public String getUdf98() {
		return udf98;
	}

	public void setUdf98(String udf98) {
		this.udf98 = udf98;
	}

	public String getUdf99() {
		return udf99;
	}

	public void setUdf99(String udf99) {
		this.udf99 = udf99;
	}

	public String getUdf100() {
		return udf100;
	}

	public void setUdf100(String udf100) {
		this.udf100 = udf100;
	}

	//Start:Uma Khot:Added for Valid Rating CR
	private String ramRatingFinalizationDate = null;
	public String getRamRatingFinalizationDate(){
		return ramRatingFinalizationDate;
		
	}
	public void setRamRatingFinalizationDate(String ramRatingFinalizationDate){
		this.ramRatingFinalizationDate=ramRatingFinalizationDate;
	}
	//End:Uma Khot:Added for Valid Rating CR
	 //Start:Uma Khot:CRI Field addition enhancement
	public String getIsSpecialApprGridForCustBelowHDB8() {
		return isSpecialApprGridForCustBelowHDB8;
	}
	public void setIsSpecialApprGridForCustBelowHDB8(
			String isSpecialApprGridForCustBelowHDB8) {
		this.isSpecialApprGridForCustBelowHDB8 = isSpecialApprGridForCustBelowHDB8;
	}
	public String getIsSingleBorrowerPrudCeiling() {
		return isSingleBorrowerPrudCeiling;
	}
	public void setIsSingleBorrowerPrudCeiling(String isSingleBorrowerPrudCeiling) {
		this.isSingleBorrowerPrudCeiling = isSingleBorrowerPrudCeiling;
	}
	public String getDetailsOfRbiApprovalForSingle() {
		return detailsOfRbiApprovalForSingle;
	}
	public void setDetailsOfRbiApprovalForSingle(String detailsOfRbiApprovalForSingle) {
		this.detailsOfRbiApprovalForSingle = detailsOfRbiApprovalForSingle;
	}
	public String getIsGroupBorrowerPrudCeiling() {
		return isGroupBorrowerPrudCeiling;
	}
	public void setIsGroupBorrowerPrudCeiling(String isGroupBorrowerPrudCeiling) {
		this.isGroupBorrowerPrudCeiling = isGroupBorrowerPrudCeiling;
	}
	public String getDetailsOfRbiApprovalForGroup() {
		return detailsOfRbiApprovalForGroup;
	}
	public void setDetailsOfRbiApprovalForGroup(String detailsOfRbiApprovalForGroup) {
		this.detailsOfRbiApprovalForGroup = detailsOfRbiApprovalForGroup;
	}
	public String getIsNonCooperativeBorrowers() {
		return isNonCooperativeBorrowers;
	}
	public void setIsNonCooperativeBorrowers(String isNonCooperativeBorrowers) {
		this.isNonCooperativeBorrowers = isNonCooperativeBorrowers;
	}
	public String getIsDirectorAsNonCooperativeForOther() {
		return isDirectorAsNonCooperativeForOther;
	}
	public void setIsDirectorAsNonCooperativeForOther(
			String isDirectorAsNonCooperativeForOther) {
		this.isDirectorAsNonCooperativeForOther = isDirectorAsNonCooperativeForOther;
	}
	public String getNameOfDirectorsAndCompany() {
		return nameOfDirectorsAndCompany;
	}
	public void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany) {
		this.nameOfDirectorsAndCompany = nameOfDirectorsAndCompany;
	}

	//End:Uma Khot:CRI Field addition enhancement
	
	private  String rbiApprovalForSingle;
	private  String rbiApprovalForGroup;
	
	public String getRbiApprovalForSingle() {
		return rbiApprovalForSingle;
	}
	public void setRbiApprovalForSingle(String rbiApprovalForSingle) {
		this.rbiApprovalForSingle = rbiApprovalForSingle;
	}

	public String getRbiApprovalForGroup() {
		return rbiApprovalForGroup;
	}
	public void setRbiApprovalForGroup(String rbiApprovalForGroup) {
		this.rbiApprovalForGroup = rbiApprovalForGroup;
	}
	public String getFacilityNamesList() {
		return facilityNamesList;
	}
	public void setFacilityNamesList(String facilityNamesList) {
		this.facilityNamesList = facilityNamesList;
	}
	public String getFinalFaciltyName() {
		return finalFaciltyName;
	}
	public void setFinalFaciltyName(String finalFaciltyName) {
		this.finalFaciltyName = finalFaciltyName;
	}
	public String getFaciltyNameList1() {
		return faciltyNameList1;
	}
	public void setFaciltyNameList1(String faciltyNameList1) {
		this.faciltyNameList1 = faciltyNameList1;
	}
	public String getFaciltyNameList2() {
		return faciltyNameList2;
	}
	public void setFaciltyNameList2(String faciltyNameList2) {
		this.faciltyNameList2 = faciltyNameList2;
	}
	
	
public String getUdf101() {
		return udf101;
	}
	public void setUdf101(String udf101) {
		this.udf101 = udf101;
	}
	public String getUdf102() {
		return udf102;
	}
	public void setUdf102(String udf102) {
		this.udf102 = udf102;
	}
	public String getUdf103() {
		return udf103;
	}
	public void setUdf103(String udf103) {
		this.udf103 = udf103;
	}
	public String getUdf104() {
		return udf104;
	}
	public void setUdf104(String udf104) {
		this.udf104 = udf104;
	}
	public String getUdf105() {
		return udf105;
	}
	public void setUdf105(String udf105) {
		this.udf105 = udf105;
	}
	  public String getUdf106() {
		return udf106;
	}
	public void setUdf106(String udf106) {
		this.udf106 = udf106;
	}
	public String getUdf107() {
		return udf107;
	}
	public void setUdf107(String udf107) {
		this.udf107 = udf107;
	}
	public String getUdf108() {
		return udf108;
	}
	public void setUdf108(String udf108) {
		this.udf108 = udf108;
	}
	public String getUdf109() {
		return udf109;
	}
	public void setUdf109(String udf109) {
		this.udf109 = udf109;
	}
	public String getUdf110() {
		return udf110;
	}
	public void setUdf110(String udf110) {
		this.udf110 = udf110;
	}
	public String getUdf111() {
		return udf111;
	}
	public void setUdf111(String udf111) {
		this.udf111 = udf111;
	}
	public String getUdf112() {
		return udf112;
	}
	public void setUdf112(String udf112) {
		this.udf112 = udf112;
	}
	public String getUdf113() {
		return udf113;
	}
	public void setUdf113(String udf113) {
		this.udf113 = udf113;
	}
	public String getUdf114() {
		return udf114;
	}
	public void setUdf114(String udf114) {
		this.udf114 = udf114;
	}
	public String getUdf115() {
		return udf115;
	}
	public void setUdf115(String udf115) {
		this.udf115 = udf115;
	}
	public String getUdf116() {
		return udf116;
	}
	public void setUdf116(String udf116) {
		this.udf116 = udf116;
	}
	public String getUdf117() {
		return udf117;
	}
	public void setUdf117(String udf117) {
		this.udf117 = udf117;
	}
	public String getUdf118() {
		return udf118;
	}
	public void setUdf118(String udf118) {
		this.udf118 = udf118;
	}
	public String getUdf119() {
		return udf119;
	}
	public void setUdf119(String udf119) {
		this.udf119 = udf119;
	}
	public String getUdf120() {
		return udf120;
	}
	public void setUdf120(String udf120) {
		this.udf120 = udf120;
	}
	public String getUdf121() {
		return udf121;
	}
	public void setUdf121(String udf121) {
		this.udf121 = udf121;
	}
	public String getUdf122() {
		return udf122;
	}
	public void setUdf122(String udf122) {
		this.udf122 = udf122;
	}
	public String getUdf123() {
		return udf123;
	}
	public void setUdf123(String udf123) {
		this.udf123 = udf123;
	}
	public String getUdf124() {
		return udf124;
	}
	public void setUdf124(String udf124) {
		this.udf124 = udf124;
	}
	public String getUdf125() {
		return udf125;
	}
	public void setUdf125(String udf125) {
		this.udf125 = udf125;
	}
	public String getUdf126() {
		return udf126;
	}
	public void setUdf126(String udf126) {
		this.udf126 = udf126;
	}
	public String getUdf127() {
		return udf127;
	}
	public void setUdf127(String udf127) {
		this.udf127 = udf127;
	}
	public String getUdf128() {
		return udf128;
	}
	public void setUdf128(String udf128) {
		this.udf128 = udf128;
	}
	public String getUdf129() {
		return udf129;
	}
	public void setUdf129(String udf129) {
		this.udf129 = udf129;
	}
	public String getUdf130() {
		return udf130;
	}
	public void setUdf130(String udf130) {
		this.udf130 = udf130;
	}
	public String getUdf131() {
		return udf131;
	}
	public void setUdf131(String udf131) {
		this.udf131 = udf131;
	}
	public String getUdf132() {
		return udf132;
	}
	public void setUdf132(String udf132) {
		this.udf132 = udf132;
	}
	public String getUdf133() {
		return udf133;
	}
	public void setUdf133(String udf133) {
		this.udf133 = udf133;
	}
	public String getUdf134() {
		return udf134;
	}
	public void setUdf134(String udf134) {
		this.udf134 = udf134;
	}
	public String getUdf135() {
		return udf135;
	}
	public void setUdf135(String udf135) {
		this.udf135 = udf135;
	}
	public String getUdf136() {
		return udf136;
	}
	public void setUdf136(String udf136) {
		this.udf136 = udf136;
	}
	public String getUdf137() {
		return udf137;
	}
	public void setUdf137(String udf137) {
		this.udf137 = udf137;
	}
	public String getUdf138() {
		return udf138;
	}
	public void setUdf138(String udf138) {
		this.udf138 = udf138;
	}
	public String getUdf139() {
		return udf139;
	}
	public void setUdf139(String udf139) {
		this.udf139 = udf139;
	}
	public String getUdf140() {
		return udf140;
	}
	public void setUdf140(String udf140) {
		this.udf140 = udf140;
	}
	public String getUdf141() {
		return udf141;
	}
	public void setUdf141(String udf141) {
		this.udf141 = udf141;
	}
	public String getUdf142() {
		return udf142;
	}
	public void setUdf142(String udf142) {
		this.udf142 = udf142;
	}
	public String getUdf143() {
		return udf143;
	}
	public void setUdf143(String udf143) {
		this.udf143 = udf143;
	}
	public String getUdf144() {
		return udf144;
	}
	public void setUdf144(String udf144) {
		this.udf144 = udf144;
	}
	public String getUdf145() {
		return udf145;
	}
	public void setUdf145(String udf145) {
		this.udf145 = udf145;
	}
	public String getUdf146() {
		return udf146;
	}
	public void setUdf146(String udf146) {
		this.udf146 = udf146;
	}
	public String getUdf147() {
		return udf147;
	}
	public void setUdf147(String udf147) {
		this.udf147 = udf147;
	}
	public String getUdf148() {
		return udf148;
	}
	public void setUdf148(String udf148) {
		this.udf148 = udf148;
	}
	public String getUdf149() {
		return udf149;
	}
	public void setUdf149(String udf149) {
		this.udf149 = udf149;
	}
	public String getUdf150() {
		return udf150;
	}
	public void setUdf150(String udf150) {
		this.udf150 = udf150;
	}
	public String getUdf151() {
		return udf151;
	}
	public void setUdf151(String udf151) {
		this.udf151 = udf151;
	}
	public String getUdf152() {
		return udf152;
	}
	public void setUdf152(String udf152) {
		this.udf152 = udf152;
	}
	public String getUdf153() {
		return udf153;
	}
	public void setUdf153(String udf153) {
		this.udf153 = udf153;
	}
	public String getUdf154() {
		return udf154;
	}
	public void setUdf154(String udf154) {
		this.udf154 = udf154;
	}
	public String getUdf155() {
		return udf155;
	}
	public void setUdf155(String udf155) {
		this.udf155 = udf155;
	}
	public String getUdf156() {
		return udf156;
	}
	public void setUdf156(String udf156) {
		this.udf156 = udf156;
	}
	public String getUdf157() {
		return udf157;
	}
	public void setUdf157(String udf157) {
		this.udf157 = udf157;
	}
	public String getUdf158() {
		return udf158;
	}
	public void setUdf158(String udf158) {
		this.udf158 = udf158;
	}
	public String getUdf159() {
		return udf159;
	}
	public void setUdf159(String udf159) {
		this.udf159 = udf159;
	}
	public String getUdf160() {
		return udf160;
	}
	public void setUdf160(String udf160) {
		this.udf160 = udf160;
	}
	public String getUdf161() {
		return udf161;
	}
	public void setUdf161(String udf161) {
		this.udf161 = udf161;
	}
	public String getUdf162() {
		return udf162;
	}
	public void setUdf162(String udf162) {
		this.udf162 = udf162;
	}
	public String getUdf163() {
		return udf163;
	}
	public void setUdf163(String udf163) {
		this.udf163 = udf163;
	}
	public String getUdf164() {
		return udf164;
	}
	public void setUdf164(String udf164) {
		this.udf164 = udf164;
	}
	public String getUdf165() {
		return udf165;
	}
	public void setUdf165(String udf165) {
		this.udf165 = udf165;
	}
	public String getUdf166() {
		return udf166;
	}
	public void setUdf166(String udf166) {
		this.udf166 = udf166;
	}
	public String getUdf167() {
		return udf167;
	}
	public void setUdf167(String udf167) {
		this.udf167 = udf167;
	}
	public String getUdf168() {
		return udf168;
	}
	public void setUdf168(String udf168) {
		this.udf168 = udf168;
	}
	public String getUdf169() {
		return udf169;
	}
	public void setUdf169(String udf169) {
		this.udf169 = udf169;
	}
	public String getUdf170() {
		return udf170;
	}
	public void setUdf170(String udf170) {
		this.udf170 = udf170;
	}
	public String getUdf171() {
		return udf171;
	}
	public void setUdf171(String udf171) {
		this.udf171 = udf171;
	}
	public String getUdf172() {
		return udf172;
	}
	public void setUdf172(String udf172) {
		this.udf172 = udf172;
	}
	public String getUdf173() {
		return udf173;
	}
	public void setUdf173(String udf173) {
		this.udf173 = udf173;
	}
	public String getUdf174() {
		return udf174;
	}
	public void setUdf174(String udf174) {
		this.udf174 = udf174;
	}
	public String getUdf175() {
		return udf175;
	}
	public void setUdf175(String udf175) {
		this.udf175 = udf175;
	}
	public String getUdf176() {
		return udf176;
	}
	public void setUdf176(String udf176) {
		this.udf176 = udf176;
	}
	public String getUdf177() {
		return udf177;
	}
	public void setUdf177(String udf177) {
		this.udf177 = udf177;
	}
	public String getUdf178() {
		return udf178;
	}
	public void setUdf178(String udf178) {
		this.udf178 = udf178;
	}
	public String getUdf179() {
		return udf179;
	}
	public void setUdf179(String udf179) {
		this.udf179 = udf179;
	}
	public String getUdf180() {
		return udf180;
	}
	public void setUdf180(String udf180) {
		this.udf180 = udf180;
	}
	public String getUdf181() {
		return udf181;
	}
	public void setUdf181(String udf181) {
		this.udf181 = udf181;
	}
	public String getUdf182() {
		return udf182;
	}
	public void setUdf182(String udf182) {
		this.udf182 = udf182;
	}
	public String getUdf183() {
		return udf183;
	}
	public void setUdf183(String udf183) {
		this.udf183 = udf183;
	}
	public String getUdf184() {
		return udf184;
	}
	public void setUdf184(String udf184) {
		this.udf184 = udf184;
	}
	public String getUdf185() {
		return udf185;
	}
	public void setUdf185(String udf185) {
		this.udf185 = udf185;
	}
	public String getUdf186() {
		return udf186;
	}
	public void setUdf186(String udf186) {
		this.udf186 = udf186;
	}
	public String getUdf187() {
		return udf187;
	}
	public void setUdf187(String udf187) {
		this.udf187 = udf187;
	}
	public String getUdf188() {
		return udf188;
	}
	public void setUdf188(String udf188) {
		this.udf188 = udf188;
	}
	public String getUdf189() {
		return udf189;
	}
	public void setUdf189(String udf189) {
		this.udf189 = udf189;
	}
	public String getUdf190() {
		return udf190;
	}
	public void setUdf190(String udf190) {
		this.udf190 = udf190;
	}
	public String getUdf191() {
		return udf191;
	}
	public void setUdf191(String udf191) {
		this.udf191 = udf191;
	}
	public String getUdf192() {
		return udf192;
	}
	public void setUdf192(String udf192) {
		this.udf192 = udf192;
	}
	public String getUdf193() {
		return udf193;
	}
	public void setUdf193(String udf193) {
		this.udf193 = udf193;
	}
	public String getUdf194() {
		return udf194;
	}
	public void setUdf194(String udf194) {
		this.udf194 = udf194;
	}
	public String getUdf195() {
		return udf195;
	}
	public void setUdf195(String udf195) {
		this.udf195 = udf195;
	}
	public String getUdf196() {
		return udf196;
	}
	public void setUdf196(String udf196) {
		this.udf196 = udf196;
	}
	public String getUdf197() {
		return udf197;
	}
	public void setUdf197(String udf197) {
		this.udf197 = udf197;
	}
	public String getUdf198() {
		return udf198;
	}
	public void setUdf198(String udf198) {
		this.udf198 = udf198;
	}
	public String getUdf199() {
		return udf199;
	}
	public void setUdf199(String udf199) {
		this.udf199 = udf199;
	}
	public String getUdf200() {
		return udf200;
	}
	public void setUdf200(String udf200) {
		this.udf200 = udf200;
	}
	public String getUdf201() {
		return udf201;
	}
	public void setUdf201(String udf201) {
		this.udf201 = udf201;
	}
	public String getUdf202() {
		return udf202;
	}
	public void setUdf202(String udf202) {
		this.udf202 = udf202;
	}
	public String getUdf203() {
		return udf203;
	}
	public void setUdf203(String udf203) {
		this.udf203 = udf203;
	}
	public String getUdf204() {
		return udf204;
	}
	public void setUdf204(String udf204) {
		this.udf204 = udf204;
	}
	public String getUdf205() {
		return udf205;
	}
	public void setUdf205(String udf205) {
		this.udf205 = udf205;
	}
	public String getUdf206() {
		return udf206;
	}
	public void setUdf206(String udf206) {
		this.udf206 = udf206;
	}
	public String getUdf207() {
		return udf207;
	}
	public void setUdf207(String udf207) {
		this.udf207 = udf207;
	}
	public String getUdf208() {
		return udf208;
	}
	public void setUdf208(String udf208) {
		this.udf208 = udf208;
	}
	public String getUdf209() {
		return udf209;
	}
	public void setUdf209(String udf209) {
		this.udf209 = udf209;
	}
	public String getUdf210() {
		return udf210;
	}
	public void setUdf210(String udf210) {
		this.udf210 = udf210;
	}
	public String getUdf211() {
		return udf211;
	}
	public void setUdf211(String udf211) {
		this.udf211 = udf211;
	}
	public String getUdf212() {
		return udf212;
	}
	public void setUdf212(String udf212) {
		this.udf212 = udf212;
	}
	public String getUdf213() {
		return udf213;
	}
	public void setUdf213(String udf213) {
		this.udf213 = udf213;
	}
	public String getUdf214() {
		return udf214;
	}
	public void setUdf214(String udf214) {
		this.udf214 = udf214;
	}
	public String getUdf215() {
		return udf215;
	}
	public void setUdf215(String udf215) {
		this.udf215 = udf215;
	}
	
	private String coBorrowerId;
	private String coBorrowerName;



	public String getCoBorrowerId() {
		return coBorrowerId;
	}

	public void setCoBorrowerId(String coBorrowerId) {
		this.coBorrowerId = coBorrowerId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
	public String getCovenantCategory() {
		return CovenantCategory;
	}
	public void setCovenantCategory(String covenantCategory) {
		CovenantCategory = covenantCategory;
	}
	

	
	
}