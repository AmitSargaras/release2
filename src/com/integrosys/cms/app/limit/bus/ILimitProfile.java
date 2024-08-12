/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ILimitProfile.java,v 1.25 2006/10/25 08:05:48 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;
import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface represents an Limit Profile.
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.25 $
 * @since $Date: 2006/10/25 08:05:48 $ Tag: $Name: $
 */
public interface ILimitProfile extends java.io.Serializable, IValueObject {
	// Getters

	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	public String getRamRating(); 
	
	public void setRamRating(String ramRating); 
	
	public String getRamRatingYear(); 
	
	public void setRamRatingYear(String ramRatingYear);
	
	public String getRamRatingType();
	
	public void setRamRatingType(String ramRatingType);
	
	//End by Pramod Katkar
	/**
	 * Get limit profile type
	 * 
	 * @return String
	 */
	public String getAAType();

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public double getActualSecurityCoverage();

	/**
	 * Gets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @return application type of AA
	 */
	public String getApplicationType();

	/**
	 * Get Approval Date
	 * 
	 * @return Date
	 */
	public Date getApprovalDate();

	// public Date getDDNDate();
	/*
	 * Get SCC Date
	 * 
	 * @return Date
	 */
	// public Date getSCCDate();
	/*
	 * Get Waiver Approved date
	 * 
	 * @return Date
	 */
	// public Date getWaiverApprovedDate();
	/*
	 * Get Security Compliance Type
	 * 
	 * @return String
	 */
	// public String getSecurityComplianceType();
	/**
	 * Get First approver employee ID
	 * 
	 * @return String
	 */
	public String getApproverEmployeeID1();

	/**
	 * Get Second approver employee ID
	 * 
	 * @return String
	 */
	public String getApproverEmployeeID2();

	/**
	 * Get first approver employee name
	 * 
	 * @return String
	 */
	public String getApproverEmployeeName1();

	/**
	 * Get second approver employee name
	 * 
	 * @return String
	 */
	public String getApproverEmployeeName2();

	/**
	 * Get the approving officer grade
	 * 
	 * @return String
	 */
//	public String getApprovingOfficerGrade();

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * @return boolean
	 */
	public boolean getBCACompleteInd();

	/**
	 * Get the BCA Create Date in CMS. This is the date the the BCA is created
	 * in CMS.
	 * 
	 * @return Date
	 */
	public Date getBCACreateDate();

	/**
	 * if the BCA is local or Foreign
	 * 
	 * @return boolean
	 */
	public boolean getBCALocalInd();

	/**
	 * Get the BCA Reference
	 * 
	 * @return String
	 */
	public String getBCAReference();

	/**
	 * Get the BCA Status
	 * 
	 * @return String
	 */
	public String getBCAStatus();

	/*
	 * Get BFL Indicator Update Date
	 * 
	 * @return Date
	 */
	public Date getBflIndUpdateDate();

	/**
	 * Get BFL Required indicator
	 * 
	 * @return boolean
	 */
	public boolean getBFLRequiredInd();

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getCCCCompleteInd();

	/**
	 * Get this limit profile's latest clean type/ special clean type BFL
	 * issuance Date
	 * 
	 * @return Date
	 */
	public Date getCleanSpecialBFLIssuanceDate();

	/**
	 * Get CMS create indicator
	 * 
	 * @return boolean
	 */
	public boolean getCMSCreateInd();

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @return boolean
	 */
	public boolean getCRApprovalRequiredInd();

	/**
	 * Get credit policy compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getCreditPolicyCompliantInd();

	/**
	 * Get the customer ID
	 * 
	 * @return long
	 */
	public long getCustomerID();

	/**
	 * Get customer reference
	 * 
	 * @return long
	 */
	public long getCustRef();

	/**
	 * Get expected loss
	 * 
	 * @return double
	 */
	public double getExpectedLoss();

	/**
	 * Get this limit profile's extended date for customer acceptance of BFL
	 * 
	 * @return Date
	 */
	public Date getExtendedBFLIssuanceDate();

	/**
	 * Get Extended next review date
	 * 
	 * @return Date
	 */
	public Date getExtendedNextReviewDate();

	/**
	 * Get full doc review indicator
	 * 
	 * @return boolean
	 */
	public boolean getFullDocReviewInd();

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus();

	/**
	 * Get this limit profile's LE reference (SCI LEID).
	 * 
	 * @return String
	 */
	public String getLEReference();

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Get Limit Profile Reference
	 * 
	 * @return String
	 */
	public String getLimitProfileRef();

	/*
	 * Get First Approver Booking Location
	 * 
	 * @return String
	 */
	// public String getApproverBookingLocation1();
	/*
	 * Get First Approver employee type
	 * 
	 * @return String
	 */
	// public String getApproverEmployeeType1();
	/*
	 * Get First Approver employee code
	 * 
	 * @return String
	 */
	// public String getApproverEmployeeCode1();
	/*
	 * Get Second Approver Booking Location
	 * 
	 * @return String
	 */
	// public String getApproverBookingLocation2();
	/*
	 * Get Second Approver employee type
	 * 
	 * @return String
	 */
	// public String getApproverEmployeeType2();
	/*
	 * Get Second Approver employee code
	 * 
	 * @return String
	 */
	// public String getApproverEmployeeCode2();
	/*
	 * Get Employee booking location
	 * 
	 * @return String
	 */
	// public String getEmployeeBookingLocation();
	/*
	 * Get Employee Type
	 * 
	 * @return String
	 */
	// public String getEmployeeType();
	/*
	 * Get Employee Code
	 * 
	 * @return String
	 */
	// public String getEmployeeCode();
	/**
	 * Get All limits associated to this profile
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getLimits();

	public Set getLimitsSet();

	/**
	 * <p>
	 * Get the LOS AA/BCA/LimitProfile/CA Reference Number. Not supposed to be
	 * Internal Key of other system.
	 * <p>
	 * And also should differ from {@link #getBCAReference()} which is the
	 * Reference number of Legacy System, such as Backend System.
	 * 
	 * @return los reference number for this Limit Profile
	 */
	public String getLosLimitProfileReference();

	/**
	 * Get next annual review date
	 * 
	 * @return Date
	 */
	public Date getNextAnnualReviewDate();

	/**
	 * Get next interim review date
	 * 
	 * @return Date
	 */
	public Date getNextInterimReviewDate();

	/**
	 * Get All limits associated to this profile which are not DELETED in status
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getNonDeletedLimits();

	/**
	 * Get Originating Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOriginatingLocation();

	/**
	 * Get product program compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getProductProgCompliantInd();

	/**
	 * Get projected economic profit
	 * 
	 * @return double
	 */
	public double getProjectedProfit();

	/**
	 *Identify is the BCA is a renewed BCA
	 * 
	 * @return boolean
	 */
	public boolean getRenewalInd();

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public double getRequiredSecurityCoverage();

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getSCCCompleteInd();

	// Setters

	/**
	 * Get limit profile segment
	 * 
	 * @return String
	 */
	public String getSegment();

	/**
	 * Get source ID
	 * 
	 * @return String
	 */
	public String getSourceID();

	/**
	 * Get the TAT create date.
	 * 
	 * @return Date
	 */
	public Date getTATCreateDate();

	/**
	 * Get the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null.
	 * 
	 * @return ITATEntry[]
	 */
	public ITATEntry[] getTATEntries();

	public Set getTATEntriesSet();

	/**
	 * Get trading agreement associated to this profile
	 * 
	 * @return ITradingAgreement
	 */
	public ITradingAgreement getTradingAgreement();

	/**
	 * Get under-write standard compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getUnderwriteStandardCompliantInd();

	/**
	 * Set limit profile type
	 * 
	 * @param value is of type String
	 */
	public void setAAType(String value);

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(double value);

	/**
	 * Sets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @param applicationType - Application Type
	 */
	public void setApplicationType(String applicationType);

	/**
	 * Set Approval Date
	 * 
	 * @param value is of type Date
	 */
	public void setApprovalDate(Date value);

	// public void setDDNDate(Date value);
	/*
	 * Set SCC Date
	 * 
	 * @param value is of type Date
	 */
	// public void setSCCDate(Date value);
	/*
	 * Set Waiver Approved date
	 * 
	 * @param value is of type Date
	 */
	// public void setWaiverApprovedDate(Date value);
	/*
	 * Set Security Compliance Type
	 * 
	 * @param value is of type String
	 */
	// public void setSecurityComplianceType(String value);
	/**
	 * Set First Approver Employee ID
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeID1(String value);

	/**
	 * Set Second Approver Employee ID
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeID2(String value);

	/**
	 * Set first approver employee name
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeName1(String value);

	/**
	 * Set second approver employee name
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeName2(String value);

	/**
	 * Set the approving officer grade
	 * 
	 * @param value is of type String
	 */
//public void setApprovingOfficerGrade(String value);

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * (As of R1.5 CR146, CCC has already been removed thus no longer required
	 * to check if CCC has been generated)
	 * @param value is of type boolean
	 */
	public void setBCACompleteInd(boolean value);

	/**
	 * Set the BCA Create Date in CMS. This is the date the the BCA is created
	 * in CMS.
	 * 
	 * @param value is of type Date
	 */
	public void setBCACreateDate(Date value);

	/**
	 * If the BCA is local or Foreign
	 * 
	 * @param value of type boolean
	 */
	public void setBCALocalInd(boolean value);

	/**
	 * Set the BCA Reference
	 * 
	 * @param value is of type String
	 */
	public void setBCAReference(String value);

	/**
	 * Set the BCA Status
	 * 
	 * @param value is of type String
	 */
	public void setBCAStatus(String value);

	/*
	 * Set BFL Indicator Update Date
	 * 
	 * @param value is of type Date
	 */
	public void setBflIndUpdateDate(Date value);

	/**
	 * Set BFL Required indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setBFLRequiredInd(boolean value);

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setCCCCompleteInd(boolean value);

	/**
	 * Set CMS create indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCMSCreateInd(boolean value);

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCRApprovalRequiredInd(boolean value);

	/**
	 * Set credit policy compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCreditPolicyCompliantInd(boolean value);

	/**
	 * Set the customer ID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value);

	/**
	 * Set customer reference
	 * 
	 * @param value is of type long
	 */
	public void setCustRef(long value);

	/**
	 * Set expected loss
	 * 
	 * @param value is of type double
	 */
	public void setExpectedLoss(double value);

	/**
	 * Get this limit profile's extended date for customer acceptance of BFL
	 * 
	 * @param extendedBFLIssuanceDate of type Date
	 */
	public void setExtendedBFLIssuanceDate(Date extendedBFLIssuanceDate);

	/**
	 * Set Extended next review date
	 * 
	 * @param value is of type Date
	 */
	public void setExtendedNextReviewDate(Date value);

	/**
	 * Set full doc review indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setFullDocReviewInd(boolean value);

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value);

	/**
	 * Get this limit profile's LE reference (SCI LEID).
	 * 
	 * @param lEReference legal entity reference
	 */
	public void setLEReference(String lEReference);

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	/**
	 * Set Limit Profile Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitProfileRef(String value);

	public void setLimits(ILimit[] value);

	public void setLimitsSet(Set limitsSet);

	/**
	 * Set the LOS AA/BCA/LimitProfile/CA Referece Number. Not supposed to be
	 * Internal Key of other system.
	 * <p>
	 * And also should differ from {@link #setBCAReference()} which is the
	 * Reference number of Legacy System, such as Backend System.
	 * 
	 * @param losLimitProfileReference the los reference number to be for this
	 *        Limit Profile
	 */
	public void setLosLimitProfileReference(String losLimitProfileReference);

	/**
	 * Set next annual review date
	 * 
	 * @param value is of type Date
	 */
	public void setNextAnnualReviewDate(Date value);

	/**
	 * Set next interim review date
	 * 
	 * @param value is of type Date
	 */
	public void setNextInterimReviewDate(Date value);

	/**
	 * Set Originating Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setOriginatingLocation(IBookingLocation value);

	/**
	 * Set product program compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setProductProgCompliantInd(boolean value);

	/**
	 * Set projected economic profit
	 * 
	 * @param value is of type double
	 */
	public void setProjectedProfit(double value);

	/**
	 * Set the BCA renewal indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setRenewalInd(boolean value);

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(double value);

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setSCCCompleteInd(boolean value);

	/**
	 * Set limit profile segment
	 * 
	 * @segment of type String
	 */
	public void setSegment(String segment);

	/**
	 * Set source ID
	 * 
	 * @param value is of type String
	 */
	public void setSourceID(String value);

	/**
	 * Set the TAT create date.
	 * 
	 * @param value is of type Date
	 */
	public void setTATCreateDate(Date value);

	/**
	 * Set the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null.
	 * 
	 * @param value is of type ITATEntry[]
	 */
	public void setTATEntries(ITATEntry[] value);

	public void setTATEntriesSet(Set tATEntriesSet);

	/**
	 * Set trading agreement associated to this profile
	 * 
	 * @param value is of type ITradingAgreement
	 */
	public void setTradingAgreement(ITradingAgreement value);

	/**
	 * Set under-write standard compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setUnderwriteStandardCompliantInd(boolean value);

    /**
     * Gets the application law type of the AA. E.g. CON - conventional, ISL - islamic
     * @return application law type of AA
     */
    public String getApplicationLawType();

    /**
     * Sets the application law type of the AA. E.g. CON - conventional, ISL - islamic
     * @param applicationLawType - Application Law Type
     */
    public void setApplicationLawType(String applicationLawType);
    
    /**
     * get the is migrated indicator
     * @return String
     */
    public String getMigratedInd();
    
    /**
     * set is migrated indicator
     * @param value is of type String
     */
    public void setMigratedInd(String migratedInd);
    
    
    //Shiv Cam
    public String getCamType();
    public void setCamType(String value);
	
	public Date getCamLoginDate();
    public void setCamLoginDate(Date value);
    
    public double getTotalSactionedAmount();
    public void setTotalSactionedAmount(double value);
    
    public long getNoOfTimesExtended();
    public void setNoOfTimesExtended(long value);
    
    public String getRelationshipManager();
    public void setRelationshipManager(String value);
    
    public String getControllingBranch();
    public void setControllingBranch(String value);
    
    public String getCommitteApproval();
    public void setCommitteApproval(String value);
    
    public String getBoardApproval();
    public void setBoardApproval(String value);
       
    public String getApproverEmployeeName3();
    public void setApproverEmployeeName3(String value);
    
    public String getApproverEmployeeName4();
    public void setApproverEmployeeName4(String value);
    
    public String getApproverEmployeeName5();
    public void setApproverEmployeeName5(String value);
    
    public String getAssetClassification();
    public void setAssetClassification(String value);
    
    public String getRbiAssetClassification();
    public void setRbiAssetClassification(String value);
    
    public  String getDocRemarks();
	public  void setDocRemarks(String docRemarks);
	
	public String getFullyCashCollateral();

	public void setFullyCashCollateral(String fullyCashCollateral) ;
	//Start:Code added for Total Sanctioned Amount For Facility
	public String getTotalSanctionedAmountFacLevel() ;

	public void setTotalSanctionedAmountFacLevel(String totalSanctionedAmountFacLevel) ;
	//End  :Code added for Total Sanctioned Amount For Facility
	public ILimitProfileUdf[] getUdfData();
	public void setUdfData(ILimitProfileUdf[] udfData);
	
	//Start:Uma Khot:Added for Valid Rating CR
	public Date getRamRatingFinalizationDate();
	public void setRamRatingFinalizationDate(Date ramRatingFinalizationDate);
	//End:Uma Khot:Added for Valid Rating CR
	
	 //Start:Uma Khot:CRI Field addition enhancement
	public String getIsSpecialApprGridForCustBelowHDB8();
	public void setIsSpecialApprGridForCustBelowHDB8(String isSpecialApprGridForCustBelowHDB8) ;
	public String getIsSingleBorrowerPrudCeiling();
	public void setIsSingleBorrowerPrudCeiling(String isSingleBorrowerPrudCeiling);
	public String getDetailsOfRbiApprovalForSingle();
	public void setDetailsOfRbiApprovalForSingle(String detailsOfRbiApprovalForSingle);
	public String getIsGroupBorrowerPrudCeiling();
	public void setIsGroupBorrowerPrudCeiling(String isGroupBorrowerPrudCeiling);
	public String getDetailsOfRbiApprovalForGroup();
	public void setDetailsOfRbiApprovalForGroup(String detailsOfRbiApprovalForGroup);
	public String getIsNonCooperativeBorrowers();
	public void setIsNonCooperativeBorrowers(String isNonCooperativeBorrowers);
	public String getIsDirectorAsNonCooperativeForOther();
	public void setIsDirectorAsNonCooperativeForOther(String isDirectorAsNonCooperativeForOther);
	public String getNameOfDirectorsAndCompany();
	public void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany);
	 //End:Uma Khot:CRI Field addition enhancement
	
	
	public String getRbiApprovalForSingle();
	
	public void setRbiApprovalForSingle(String rbiApprovalForSingle);
	
	public String getRbiApprovalForGroup();
	
	public void setRbiApprovalForGroup(String rbiApprovalForGroup);
	
	public IOtherCovenant getObOtherCovenant();
	
	public void setObOtherCovenant(IOtherCovenant obOtherCovenant);

}