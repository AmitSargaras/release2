/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBLimitProfile.java,v 1.30 2006/10/16 06:53:40 jzhan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents an Limit Profile.
 * 
 * @author $Author: jzhan $
 * @version $Revision: 1.30 $
 * @since $Date: 2006/10/16 06:53:40 $ Tag: $Name: $
 */
public class OBLimitProfile implements ILimitProfile {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String bCAStatus = null;

	private String bCAReference = null;
	
	private String lOSBCAReference = null;

	private String losLimitProfileReference = null;

	private String limitProfileRef = null;

	private IBookingLocation originatingLocation = null;

	private Date approvalDate = null;

	private Date nextInterimReviewDate = null;

	private Date nextAnnualReviewDate = null;

	private Date extendedNextReviewDate = null;

	private double projectedProfit = 0;

	private double expectedLoss = 0;

	private boolean productProgCompliantInd = false;

	private boolean creditPolicyCompliantInd = false;

	private boolean underwriteStandardCompliantInd = false;

	private boolean cRApprovalRequiredInd = false;

	private Date ddnDate = null;

	private Date sccDate = null;

	private Date waiverApprovedDate = null;

	private String securityComplianceType = null;

	private String approverBookingLocation1 = null;

	private String approverEmployeeType1 = null;

	private String approverEmployeeCode1 = null;

	private String approverEmployeeID1 = null;

	private String approverEmployeeName1 = null;

	private String approverBookingLocation2 = null;

	private String approverEmployeeType2 = null;

	private String approverEmployeeCode2 = null;

	private String approverEmployeeID2 = null;

	private String approverEmployeeName2 = null;

	private String employeeBookingLocation = null;

	private String employeeType = null;

	private String employeeCode = null;

	private long versionTime = 0;

	private ILimit[] limits = null;

	private Set limitsSet;

	private double requiredSecurityCoverage = ICMSConstant.DOUBLE_INVALID_VALUE;

	private double actualSecurityCoverage = ICMSConstant.DOUBLE_INVALID_VALUE;

	private String approvingOfficerGrade = null;

	private Date bCACreateDate = null;

	private Date tATCreateDate = null;

	private ITATEntry[] tATEntries = null;

	private Set tATEntriesSet;

	private boolean bFLRequiredInd = true; // requirement default is yes

	private boolean renewalInd = false;

	private boolean fullDocReviewInd = false;

	private String hostStatus = null;

	private boolean bCACompleteInd = false;

	private boolean sCCCompleteInd = false;

	private boolean cCCCompleteInd = false;

	private boolean bCALocalInd = true;

	private Date bflIndUpdateDate = null;

	private String lEReference;

	private Date extendedBFLIssuanceDate = null;

	private Date cleanSpecialBFLIssuanceDate = null;

	private ITradingAgreement tradeAgreement = null;

	private String aAType;

	private String sourceID;

	private long custRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private boolean cMSCreateInd = true;

	private String segment = null;

	private String applicationType = null;

    private String applicationLawType = null;
    
    private String migratedInd = ICMSConstant.FALSE_VALUE;
    
    //786001
    private Date camLoginDate;
    
	private long noOfTimesExtended;
	
	private double totalSactionedAmount;
	
	private String relationshipManager;

	private String controllingBranch;
	
	private String committeApproval;
	
	private String boardApproval;
	
	private String approverEmployeeName3;
	
	private String approverEmployeeName4;
	
	private String approverEmployeeName5;
	
	private String assetClassification;
	
	private String docRemarks;
	
	private String rbiAssetClassification;
    
	private String camType;
	
	private Date ramRatingFinalizationDate = null;
	
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
	
	private IOtherCovenant obOtherCovenant;
	
	private List otherCovenantDetails;
	
	public List getOtherCovenantDetails() {
		return otherCovenantDetails;
	}
	public void setOtherCovenantDetails(List otherCovenantDetails) {
		this.otherCovenantDetails = otherCovenantDetails;
	}
 //Added by Pramod Katkar for New Filed CR on 20-08-2013
	private String ramRating;
	private String ramRatingYear;
	private String ramRatingType;
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
	public void setRamRatingType(String ramRatingType) {
		this.ramRatingType = ramRatingType;
	}
	//End by Pramod Katkar
	//Start:Code added for Fully Cash Collateral
	private String fullyCashCollateral;
	
	

	public String getFullyCashCollateral() {
		return fullyCashCollateral;
	}

	public void setFullyCashCollateral(String fullyCashCollateral) {
		this.fullyCashCollateral = fullyCashCollateral;
	}
	//End  :Code added for Fully Cash Collateral
	
	//Start:Code added for Total Sanctioned Amount For Facility
	private String totalSanctionedAmountFacLevel;
	public String getTotalSanctionedAmountFacLevel() {
		return totalSanctionedAmountFacLevel;
	}

	public void setTotalSanctionedAmountFacLevel(String totalSanctionedAmountFacLevel) {
		this.totalSanctionedAmountFacLevel = totalSanctionedAmountFacLevel;
	}
	//End  :Code added for Total Sanctioned Amount For Facility
	
	public String getDocRemarks() {
		return docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}

	public String getCamType() {
			return camType;
		}

		public void setCamType(String camType) {
			this.camType = camType;
		}
	
	public Date getCamLoginDate() {
		return camLoginDate;
	}

	public void setCamLoginDate(Date camLoginDate) {
		this.camLoginDate = camLoginDate;
	}

	public long getNoOfTimesExtended() {
		return noOfTimesExtended;
	}

	public void setNoOfTimesExtended(long noOfTimesExtended) {
		this.noOfTimesExtended = noOfTimesExtended;
	}

	

	public double getTotalSactionedAmount() {
		return totalSactionedAmount;
	}

	public void setTotalSactionedAmount(double totalSactionedAmount) {
		this.totalSactionedAmount = totalSactionedAmount;
	}

	public String getBoardApproval() {
		return boardApproval;
	}

	public void setBoardApproval(String boardApproval) {
		this.boardApproval = boardApproval;
	}

	public String getApproverEmployeeName3() {
		return approverEmployeeName3;
	}

	public void setApproverEmployeeName3(String approverEmployeeName3) {
		this.approverEmployeeName3 = approverEmployeeName3;
	}

	public String getApproverEmployeeName4() {
		return approverEmployeeName4;
	}

	public void setApproverEmployeeName4(String approverEmployeeName4) {
		this.approverEmployeeName4 = approverEmployeeName4;
	}

	public String getApproverEmployeeName5() {
		return approverEmployeeName5;
	}

	public void setApproverEmployeeName5(String approverEmployeeName5) {
		this.approverEmployeeName5 = approverEmployeeName5;
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
    /**
	 * Default Constructor
	 */
	public OBLimitProfile() {
	}

	
	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#getAAType
	 */
	public String getAAType() {
		return this.aAType;
	}

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public double getActualSecurityCoverage() {
		return actualSecurityCoverage;
	}

	/**
	 * Gets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @return application type of AA
	 */
	public String getApplicationType() {
		return applicationType;
	}

    /**
     * Gets the application law type of the AA. E.g. CON - conventional, ISL - islamic
     * @return application law type of AA
     */
    public String getApplicationLawType() {
        return applicationLawType;
    }


	/**
	 * Get Approval Date
	 * 
	 * @return Date
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}

	// Getters

	/*
	 * Get Document Deferral Note Date
	 * 
	 * @return Date
	 * 
	 * public Date getDDNDate() { return _ddnDate; }
	 */
	/*
	 * Get SCC Date
	 * 
	 * @return Date
	 * 
	 * public Date getSCCDate() { return _sccDate; }
	 */
	/*
	 * Get Waiver Approved date
	 * 
	 * @return Date
	 * 
	 * public Date getWaiverApprovedDate() { return _waiverApprovedDate; }
	 */
	/*
	 * Get Security Compliance Type
	 * 
	 * @return String
	 * 
	 * public String getSecurityComplianceType() { return
	 * _securityComplianceType; }
	 */
	/**
	 * Get First approver employee ID
	 * 
	 * @return String
	 */
	public String getApproverEmployeeID1() {
		return approverEmployeeID1;
	}

	/**
	 * Get Second approver employee ID
	 * 
	 * @return String
	 */
	public String getApproverEmployeeID2() {
		return approverEmployeeID2;
	}

	/**
	 * Get first approver employee name
	 * 
	 * @return String
	 */
	public String getApproverEmployeeName1() {
		return approverEmployeeName1;
	}

	/**
	 * Get second approver employee name
	 * 
	 * @return String
	 */
	public String getApproverEmployeeName2() {
		return approverEmployeeName2;
	}

	/**
	 * Get the approving officer grade
	 * 
	 * @return String
	 */
	/*public String getApprovingOfficerGrade() {
		return approvingOfficerGrade;
	}*/

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * @return boolean
	 */
	public boolean getBCACompleteInd() {
		return bCACompleteInd;
	}

	/**
	 * Get the BCA Create Date in CMS. This is the date the the BCA is created
	 * in CMS.
	 * 
	 * @return Date
	 */
	public Date getBCACreateDate() {
		return bCACreateDate;
	}

	/**
	 * If the bca is local ? true means local, false means foreign.
	 * 
	 * @return boolean
	 */
	public boolean getBCALocalInd() {
		return bCALocalInd;
	}

	/**
	 * Get the BCA Reference
	 * 
	 * @return String
	 */
	public String getBCAReference() {
		return bCAReference;
	}

	public String getLOSBCAReference() {
		return lOSBCAReference;
	}

	public void setLOSBCAReference(String reference) {
		lOSBCAReference = reference;
	}

	/**
	 * Get the BCA Status
	 * 
	 * @return String
	 */
	public String getBCAStatus() {
		return bCAStatus;
	}

	public Date getBflIndUpdateDate() {
		return bflIndUpdateDate;
	}

	/**
	 * Get BFL Required indicator
	 * 
	 * @return boolean
	 */
	public boolean getBFLRequiredInd() {
		return bFLRequiredInd;
	}

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getCCCCompleteInd() {
		return cCCCompleteInd;
	}

	/**
	 * Get this limit profile's latest clean type/ special clean type BFL
	 * issuance Date
	 * 
	 * @return Date
	 */
	public Date getCleanSpecialBFLIssuanceDate() {
		if (cleanSpecialBFLIssuanceDate == null) {
			ITATEntry[] entries = getTATEntries();
			int len = entries == null ? 0 : entries.length;
			if (len != 0) {
				Arrays.sort(entries, new Comparator() {
					public int compare(Object o1, Object o2) {
						Date t1 = ((ITATEntry) o1).getTATStamp();
						Date t2 = ((ITATEntry) o2).getTATStamp();

						if ((t1 == null) && (t2 != null)) {
							return -1;
						}

						if ((t1 != null) && (t2 == null)) {
							return 1;
						}

						if ((t1 == null) && (t2 == null)) {
							return 0;
						}

						return t1.compareTo(t2);
					}
				});

				String code = entries[len - 1].getTATServiceCode();
				if (code.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)
						|| code.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)) {
					cleanSpecialBFLIssuanceDate = entries[len - 1].getTATStamp();
				}
			}
		}
		return cleanSpecialBFLIssuanceDate;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#getCMSCreateInd
	 */
	public boolean getCMSCreateInd() {
		return this.cMSCreateInd;
	}

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @return boolean
	 */
	public boolean getCRApprovalRequiredInd() {
		return cRApprovalRequiredInd;
	}

	/**
	 * Get credit policy compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getCreditPolicyCompliantInd() {
		return creditPolicyCompliantInd;
	}

	/*
	 * Get BFL Indicator Update Date
	 * 
	 * @return Date
	 */

	/**
	 * Get the customer ID
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerID;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#getCustRef
	 */
	public long getCustRef() {
		return this.custRef;
	}

	/**
	 * Get expected loss
	 * 
	 * @return double
	 */
	public double getExpectedLoss() {
		return expectedLoss;
	}

	/**
	 * Get this limit profile's extended date for customer acceptance of BFL
	 * 
	 * @return Date
	 */
	public Date getExtendedBFLIssuanceDate() {
		return extendedBFLIssuanceDate;
	}

	/**
	 * Get Extended next review date
	 * 
	 * @return Date
	 */
	public Date getExtendedNextReviewDate() {
		return extendedNextReviewDate;
	}

	/**
	 * Get full doc review indicator
	 * 
	 * @return boolean
	 */
	public boolean getFullDocReviewInd() {
		return fullDocReviewInd;
	}

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus() {
		return hostStatus;
	}

	/**
	 * Get this limit profile's LE reference (SCI LEID).
	 * 
	 * @return String
	 */
	public String getLEReference() {
		return lEReference;
	}

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Get Limit Profile Reference
	 * 
	 * @return String
	 */
	public String getLimitProfileRef() {
		return limitProfileRef;
	}

	/*
	 * Get First Approver Booking Location
	 * 
	 * @return String
	 * 
	 * public String getApproverBookingLocation1() { return
	 * _approverBookingLocation1; }
	 */
	/*
	 * Get First Approver employee type
	 * 
	 * @return String
	 * 
	 * public String getApproverEmployeeType1() { return _approverEmployeeType1;
	 * }
	 */
	/*
	 * Get First Approver employee code
	 * 
	 * @return String
	 * 
	 * public String getApproverEmployeeCode1() { return _approverEmployeeCode1;
	 * }
	 */
	/*
	 * Get Second Approver Booking Location
	 * 
	 * @return String
	 * 
	 * public String getApproverBookingLocation2() { return
	 * _approverBookingLocation2; }
	 */
	/*
	 * Get Second Approver employee type
	 * 
	 * @return String
	 * 
	 * public String getApproverEmployeeType2() { return _approverEmployeeType2;
	 * }
	 */
	/*
	 * Get Second Approver employee code
	 * 
	 * @return String
	 * 
	 * public String getApproverEmployeeCode2() { return _approverEmployeeCode2;
	 * }
	 */
	/*
	 * Get Employee booking location
	 * 
	 * @return String
	 * 
	 * public String getEmployeeBookingLocation() { return
	 * _employeeBookingLocation; }
	 */
	/*
	 * Get Employee Type
	 * 
	 * @return String
	 * 
	 * public String getEmployeeType() { return _employeeType; }
	 */
	/*
	 * Get Employee Code
	 * 
	 * @return String
	 * 
	 * public String getEmployeeCode() { return _employeeCode; }
	 */
	/**
	 * Get All limits associated to this profile
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getLimits() {
		return limits;
	}

	public Set getLimitsSet() {
		return limitsSet;
	}

	public String getLosLimitProfileReference() {
		return this.losLimitProfileReference;
	}

	/**
	 * Get next annual review date
	 * 
	 * @return Date
	 */
	public Date getNextAnnualReviewDate() {
		return nextAnnualReviewDate;
	}

	/**
	 * Get next interim review date
	 * 
	 * @return Date
	 */
	public Date getNextInterimReviewDate() {
		return nextInterimReviewDate;
	}

	/**
	 * Get All limits associated to this profile which are not DELETED in status
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getNonDeletedLimits() {
		if ((null == limits) || (limits.length == 0)) {
			return limits;
		}
		else {
			ArrayList aList = new ArrayList(limits.length);
			for (int i = 0; i < limits.length; i++) {
				if (ICMSConstant.STATE_DELETED.equals(limits[i].getLimitStatus())) {
					// don't include into arraylist
				}
				else {
					aList.add(limits[i]);
				}
			}
			return (ILimit[]) aList.toArray(new ILimit[0]);
		}
	}

	/**
	 * Get Originating Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOriginatingLocation() {
		return originatingLocation;
	}

	/**
	 * Get product program compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getProductProgCompliantInd() {
		return productProgCompliantInd;
	}

	/**
	 * Get projected economic profit
	 * 
	 * @return double
	 */
	public double getProjectedProfit() {
		return projectedProfit;
	}

	/**
	 *Identify is the BCA is a renewed BCA
	 * 
	 * @return boolean
	 */
	public boolean getRenewalInd() {
		return renewalInd;
	}

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public double getRequiredSecurityCoverage() {
		return requiredSecurityCoverage;
	}

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getSCCCompleteInd() {
		return sCCCompleteInd;
	}

	/**
	 * Get limit profile segment
	 * 
	 * @return String
	 */
	public String getSegment() {
		return segment;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#getSourceID
	 */
	public String getSourceID() {
		return this.sourceID;
	}

	/**
	 * Get the TAT create date.
	 * 
	 * @return Date
	 */
	public Date getTATCreateDate() {
		return tATCreateDate;
	}

	// Setters

	/**
	 * Get the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null.
	 * 
	 * @return ITATEntry[]
	 */
	public ITATEntry[] getTATEntries() {
		return tATEntries;
	}

	public Set getTATEntriesSet() {
		return tATEntriesSet;
	}

	/**
	 * Get trading agreement associated to this profile
	 * 
	 * @return ITradingAgreement
	 */
	public ITradingAgreement getTradingAgreement() {
		return tradeAgreement;
	}

	/**
	 * Get under-write standard compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getUnderwriteStandardCompliantInd() {
		return underwriteStandardCompliantInd;
	}

	/**
	 * Get Version Time
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#setAAType
	 */
	public void setAAType(String value) {
		this.aAType = value;
	}

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(double value) {
		actualSecurityCoverage = value;
	}

	/**
	 * Sets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @param applicationType - Application Type
	 */
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}


    /**
     * Sets the application law type of the AA. E.g. CON - conventional, ISL - islamic
     * @param applicationLawType - Application Law Type
     */
    public void setApplicationLawType(String applicationLawType) {
        this.applicationLawType = applicationLawType;
    }

    /**
	 * Set Approval Date
	 * 
	 * @param value is of type Date
	 */
	public void setApprovalDate(Date value) {
		approvalDate = value;
	}

	/*
	 * Set SCC Date
	 * 
	 * @param value is of type Date
	 * 
	 * public void setSCCDate(Date value) { _sccDate = value; }
	 */
	/*
	 * Set Waiver Approved date
	 * 
	 * @param value is of type Date
	 * 
	 * public void setWaiverApprovedDate(Date value) { _waiverApprovedDate =
	 * value; }
	 */
	/*
	 * Set Security Compliance Type
	 * 
	 * @param value is of type String
	 * 
	 * public void setSecurityComplianceType(String value) {
	 * _securityComplianceType = value; }
	 */
	/**
	 * Set First approver employee ID
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeID1(String value) {
		approverEmployeeID1 = value;
	}

	/**
	 * Set Second approver employee ID
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeID2(String value) {
		approverEmployeeID2 = value;
	}

	/**
	 * Set first approver employee name
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeName1(String value) {
		approverEmployeeName1 = value;
	}

	/**
	 * Set second approver employee name
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeName2(String value) {
		approverEmployeeName2 = value;
	}

	/**
	 * Set the approving officer grade
	 * 
	 * @param value is of type String
	 */
	/*public void setApprovingOfficerGrade(String value) {
		approvingOfficerGrade = value;
	}*/
	
	public void setCommitteApproval(String committeApproval) {
		this.committeApproval = committeApproval;
	}

	public String getCommitteApproval() {
		return committeApproval;
	}

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * @param value is of type boolean
	 */
	public void setBCACompleteInd(boolean value) {
		bCACompleteInd = value;
	}

	/**
	 * Set the BCA Create Date in CMS. This is the date the the BCA is created
	 * in CMS.
	 * 
	 * @param value is of type Date
	 */
	public void setBCACreateDate(Date value) {
		bCACreateDate = value;
	}

	/**
	 * If the bca is local ? true means local, false means foreign.
	 * 
	 * @param value if of type boolean
	 */
	public void setBCALocalInd(boolean value) {
		bCALocalInd = value;
	}

	/**
	 * Set the BCA Reference
	 * 
	 * @param value is of type String
	 */
	public void setBCAReference(String value) {
		bCAReference = value;
	}

	/**
	 * Set the BCA Status
	 * 
	 * @param value is of type String
	 */
	public void setBCAStatus(String value) {
		bCAStatus = value;
	}

	/*
	 * Set BFL Indicator Update Date
	 * 
	 * @param value is of type Date
	 */
	public void setBflIndUpdateDate(Date value) {
		bflIndUpdateDate = value;
	}

	/**
	 * Set BFL Required indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setBFLRequiredInd(boolean value) {
		bFLRequiredInd = value;
	}

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setCCCCompleteInd(boolean value) {
		cCCCompleteInd = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#setCMSCreateInd
	 */
	public void setCMSCreateInd(boolean value) {
		this.cMSCreateInd = value;
	}

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCRApprovalRequiredInd(boolean value) {
		cRApprovalRequiredInd = value;
	}

	/**
	 * Set credit policy compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCreditPolicyCompliantInd(boolean value) {
		creditPolicyCompliantInd = value;
	}

	/**
	 * Set customer ID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		customerID = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#setCustRef
	 */
	public void setCustRef(long value) {
		this.custRef = value;
	}

	/**
	 * Set expected loss
	 * 
	 * @param value is of type double
	 */
	public void setExpectedLoss(double value) {
		expectedLoss = value;
	}

	/**
	 * Get this limit profile's extended date for customer acceptance of BFL
	 * 
	 * @param extendedBFLIssuanceDate of type Date
	 */
	public void setExtendedBFLIssuanceDate(Date extendedBFLIssuanceDate) {
		this.extendedBFLIssuanceDate = extendedBFLIssuanceDate;
	}

	/**
	 * Set Extended next review date
	 * 
	 * @param value is of type Date
	 */
	public void setExtendedNextReviewDate(Date value) {
		extendedNextReviewDate = value;
	}

	/**
	 * Set full doc review indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setFullDocReviewInd(boolean value) {
		fullDocReviewInd = value;
	}

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value) {
		hostStatus = value;
	}

	/**
	 * Get this limit profile's LE reference (SCI LEID).
	 * 
	 * @param lEReference legal entity reference
	 */
	public void setLEReference(String lEReference) {
		this.lEReference = lEReference;
	}

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		limitProfileID = value;
	}

	/**
	 * Set Limit Profile Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitProfileRef(String value) {
		limitProfileRef = value;
	}

	/*
	 * Set First Approver Booking Location
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverBookingLocation1(String value) {
	 * _approverBookingLocation1 = value; }
	 */
	/*
	 * Set First Approver employee type
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverEmployeeType1(String value) {
	 * _approverEmployeeType1 = value; }
	 */
	/*
	 * Set First Approver employee code
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverEmployeeCode1(String value) {
	 * _approverEmployeeCode1 = value; }
	 */
	/*
	 * Set Second Approver Booking Location
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverBookingLocation2(String value) {
	 * _approverBookingLocation2 = value; }
	 */
	/*
	 * Set Second Approver employee type
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverEmployeeType2(String value) {
	 * _approverEmployeeType2 = value; }
	 */
	/*
	 * Set Second Approver employee code
	 * 
	 * @param value is of type String
	 * 
	 * public void setApproverEmployeeCode2(String value) {
	 * _approverEmployeeCode2 = value; }
	 */
	/*
	 * Set Employee booking location
	 * 
	 * @param value is of type String
	 * 
	 * public void setEmployeeBookingLocation(String value) {
	 * _employeeBookingLocation = value; }
	 */
	/*
	 * Set Employee Type
	 * 
	 * @param value is of type String
	 * 
	 * public void setEmployeeType(String value) { _employeeType = value; }
	 */
	/*
	 * Set Employee Code
	 * 
	 * @param value is of type String
	 * 
	 * public void setEmployeeCode(String value) { _employeeCode = value; }
	 */
	/**
	 * Set All limits associated to this profile
	 * 
	 * @param value is of type ILimit[]
	 */
	public void setLimits(ILimit[] value) {
		limits = value;

		this.limitsSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setLimitsSet(Set limitsSet) {
		this.limitsSet = limitsSet;

		this.limits = (limitsSet == null) ? null : (ILimit[]) limitsSet.toArray(new ILimit[0]);
	}

	public void setLosLimitProfileReference(String losLimitProfileReference) {
		this.losLimitProfileReference = losLimitProfileReference;
	}

	/**
	 * Set next annual review date
	 * 
	 * @param value is of type Date
	 */
	public void setNextAnnualReviewDate(Date value) {
		nextAnnualReviewDate = value;
	}

	/**
	 * Set next interim review date
	 * 
	 * @param value is of type Date
	 */
	public void setNextInterimReviewDate(Date value) {
		nextInterimReviewDate = value;
	}

	/**
	 * Set Originating Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setOriginatingLocation(IBookingLocation value) {
		originatingLocation = value;
	}

	/**
	 * Set product program compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setProductProgCompliantInd(boolean value) {
		productProgCompliantInd = value;
	}

	/**
	 * Set projected economic profit
	 * 
	 * @param value is of type double
	 */
	public void setProjectedProfit(double value) {
		projectedProfit = value;
	}

	/**
	 * Set the BCA renewal indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setRenewalInd(boolean value) {
		renewalInd = value;
	}

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(double value) {
		requiredSecurityCoverage = value;
	}

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setSCCCompleteInd(boolean value) {
		sCCCompleteInd = value;
	}

	/**
	 * Set limit profile segment
	 * 
	 * @segment of type String
	 */
	public void setSegment(String segment) {
		this.segment = segment;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimitProfile#setSourceID
	 */
	public void setSourceID(String value) {
		this.sourceID = value;
	}

	/**
	 * Set the TAT create date.
	 * 
	 * @param value is of type Date
	 */
	public void setTATCreateDate(Date value) {
		tATCreateDate = value;
	}

	/**
	 * Set the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null.
	 * 
	 * @param value is of type ITATEntry[]
	 */
	public void setTATEntries(ITATEntry[] value) {
		tATEntries = value;

		this.tATEntriesSet = (value == null) ? new HashSet(0) : new HashSet(Arrays.asList(value));
	}

	public void setTATEntriesSet(Set tATEntriesSet) {
		this.tATEntriesSet = tATEntriesSet;

		this.tATEntries = (tATEntriesSet == null) ? null : (ITATEntry[]) tATEntriesSet.toArray(new ITATEntry[0]);
	}

	/**
	 * Set trading agreement associated to this profile
	 * 
	 * @param value is of type ITradingAgreement
	 */
	public void setTradingAgreement(ITradingAgreement value) {
		this.tradeAgreement = value;
	}

	/**
	 * Set under-write standard compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setUnderwriteStandardCompliantInd(boolean value) {
		underwriteStandardCompliantInd = value;
	}

	public String getMigratedInd() {
		return migratedInd;
	}

	public void setMigratedInd(String migratedInd) {
		this.migratedInd = migratedInd;
	}

	/**
	 * Set Version Time
	 * 
	 * @param version is of type long
	 */
	public void setVersionTime(long version) {
		versionTime = version;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName()).append("#");
		buf.append("lEReference [").append(lEReference).append("] ");
		buf.append("bcaReference [").append(bCAReference).append("] ");
		buf.append("losLimitProfileReference [").append(losLimitProfileReference).append("] ");
		buf.append("applicationType [").append(applicationType).append("] ");
		buf.append("approvalDate [").append(approvalDate).append("] ");
		buf.append("bCAStatus [").append(bCAStatus).append("] ");
		buf.append("sourceID [").append(sourceID).append("] ");

		return buf.toString();
	}


	private ILimitProfileUdf[] udfData = null;

	public ILimitProfileUdf[] getUdfData() {
		return udfData;
	}

	public void setUdfData(ILimitProfileUdf[] udfData) {
		this.udfData = udfData;
	}  

	//Start:Uma Khot:Added for Valid Rating CR
	public Date getRamRatingFinalizationDate(){
		return ramRatingFinalizationDate;
		
	}
	public void setRamRatingFinalizationDate(Date ramRatingFinalizationDate){
		this.ramRatingFinalizationDate=ramRatingFinalizationDate;
	}
	//End:Uma Khot:Added for Valid Rating CR

	 //Start:Uma Khot:CRI Field addition enhancement
	
	public String getIsSpecialApprGridForCustBelowHDB8() {
		return isSpecialApprGridForCustBelowHDB8;
	}
	public void setIsSpecialApprGridForCustBelowHDB8(String isSpecialApprGridForCustBelowHDB8) {
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
	public IOtherCovenant getObOtherCovenant() {
		return obOtherCovenant;
	}
	public void setObOtherCovenant(IOtherCovenant obOtherCovenant) {
		this.obOtherCovenant = obOtherCovenant;
	}
	
}