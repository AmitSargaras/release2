/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCustomerSearchResult.java,v 1.10 2005/10/14 09:30:02 lyng Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.cci.bus.OBCustomerAddress;

/**
 * This class represents a customer search result data.
 * 
 * @author lyng
 * @author Andy Wong
 * @author Chong Jun Yong
 * @since 2005/10/14
 */
public class OBCustomerSearchResult implements ICustomerSearchResult {
	private String legalName = null;

	private long legalID = ICMSConstant.LONG_INVALID_VALUE;

	private String legalReference = null;

	private String customerName = null;

	private long subID = ICMSConstant.LONG_INVALID_VALUE;

	private String subRef = null;

	private long instrID = ICMSConstant.LONG_INVALID_VALUE;

	/** Limit Profile Reference Number from host */
	private String instrRef = null;

	private String losLimitProfileRef = null;

	private Date instrApprDate = null;

	private String trxID = null;

	private boolean isNoLimits = false;

	private String bcaStatus = null;

	private IBookingLocation bcaOrigLocation;

	private IBookingLocation instrOrigLocation;

	private long innerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long outerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private String origLocCntry;

	private ILimitProfile limitProfile;

	private ICMSCustomer customer;

	private boolean isDAPError = false;

	private String lmtProfileType = null;

	private long agreementID = ICMSConstant.LONG_INVALID_VALUE;

	private String agreementType;

	private boolean hasContractFinance = false;

	private boolean hasBridgingLoan = false;

	private boolean cmsCreateInd = false;

	private boolean canDeleteLmtProfile = true;

	private String idType;

	private String idNo;

	private String searchMethod;

	private String mainId;
	
	private String lmpLeID;
	
	private String camType;  //Shiv 190911

    public String getLmpLeID() {
		return lmpLeID;
	}

	public void setLmpLeID(String lmpLeID) {
		this.lmpLeID = lmpLeID;
	}

	private String groupCCINo ;
    private String groupCCIMapID ;
    private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private String grpNo  ;
    private String grpID  ;
    private String  groupName ="";
    private String sourceID ;
    private OBCustomerAddress address ;
    private Date dob ;
    private Date incorporationDate;

	/**
	 * Default Constructor
	 */
	public OBCustomerSearchResult() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICustomerSearchResult
	 */
	public OBCustomerSearchResult(ICustomerSearchResult value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#getAgreementID
	 */
	public long getAgreementID() {
		return this.agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#getAgreementType
	 */
	public String getAgreementType() {
		return this.agreementType;
	}

	/**
	 * Get bca originating location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBcaOrigLocation() {
		return bcaOrigLocation;
	}

	/**
	 * Get the bcaStatus
	 * 
	 * @return String
	 */
	public String getBcaStatus() {
		return bcaStatus;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#getCanDeleteLmtProfile
	 */
	public boolean getCanDeleteLmtProfile() {
		return this.canDeleteLmtProfile;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#getCMSCreateInd
	 */
	public boolean getCMSCreateInd() {
		return this.cmsCreateInd;
	}

	/**
	 * Get customer info.
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getCustomer() {
		return customer;
	}

	/**
	 * Get the customer name
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Get if the customer has any bridging loan facilities
	 * @return true if customer has at least one bridging loan facility, false
	 *         otherwise
	 */
	public boolean getHasBridgingLoan() {
		return hasBridgingLoan;
	}

	/**
	 * Get if the customer has any contract financing facilities
	 * @return true if customer has at least one contract financing facility,
	 *         false otherwise
	 */
	public boolean getHasContractFinance() {
		return hasContractFinance;
	}

	public String getIdNo() {
		return idNo;
	}

	public String getIdType() {
		return idType;
	}

	/**
	 * Get cms inner limit id.
	 * 
	 * @return long
	 */
	public long getInnerLimitID() {
		return innerLimitID;
	}

	/**
	 * Get instruction originating location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getInstrOrigLocation() {
		return instrOrigLocation;
	}

	/**
	 * Get the Instruction Approved Date
	 * 
	 * @return Date
	 */
	public Date getInstructionApprovedDate() {
		return instrApprDate;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	/**
	 * Get the instruction ID
	 * 
	 * @return long
	 */
	public long getInstructionID() {
		return instrID;
	}

	/*
	 * Get the Instruction Ref No
	 * 
	 * @return String
	 */
	public String getInstructionRefNo() {
		return instrRef;
	}

	/**
	 * Check if the customer search encounters DAP Error.
	 * 
	 * @return boolean
	 */
	public boolean getIsDAPError() {
		return isDAPError;
	}

	/**
	 * Get the legal ID
	 * 
	 * @return long
	 */
	public long getLegalID() {
		return legalID;
	}

	/**
	 * Get the legal name
	 * 
	 * @return String
	 */
	public String getLegalName() {
		return legalName;
	}

	// Setters

	/**
	 * Get the legal reference
	 * 
	 * @return String
	 */
	public String getLegalReference() {
		return legalReference;
	}

	/**
	 * Get limit profile.
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getLimitProfile() {
		return limitProfile;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#getLmtProfileType
	 */
	public String getLmtProfileType() {
		return this.lmtProfileType;
	}

	public String getLosLimitProfileRef() {
		return losLimitProfileRef;
	}

	public String getMainId() {
		return mainId;
	}

	/**
	 * Get the is no limits indicator
	 * 
	 * @return boolean
	 */
	public boolean getNoLimitsInd() {
		return isNoLimits;
	}

	/**
	 * Get BCA originating location country.
	 * 
	 * @return String
	 */
	public String getOrigLocCntry() {
		return origLocCntry;
	}

	/**
	 * Get cms outer limit id.
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return outerLimitID;
	}

	public String getSearchMethod() {
		return searchMethod;
	}

	/**
	 * Get the sub-profile ID
	 * 
	 * @return long
	 */
	public long getSubProfileID() {
		return subID;
	}

	/**
	 * Get the sub-profile reference
	 * 
	 * @return String
	 */
	public String getSubProfileReference() {
		return subRef;
	}

	/**
	 * Get the transaction ID
	 * 
	 * @return String
	 */
	public String getTransactionID() {
		return trxID;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#setAgreementID
	 */
	public void setAgreementID(long agreementID) {
		this.agreementID = agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#setAgreementType
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * Set BCA originating location.
	 * 
	 * @param bcaOrigLocation of type IBookingLocation
	 */
	public void setBcaOrigLocation(IBookingLocation bcaOrigLocation) {
		this.bcaOrigLocation = bcaOrigLocation;
	}

	/**
	 * Set the bcaStatus
	 * 
	 * @param _bcaStatus is of type String
	 */
	public void setBcaStatus(String _bcaStatus) {
		this.bcaStatus = _bcaStatus;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#setCanDeleteLmtProfile
	 */
	public void setCanDeleteLmtProfile(boolean value) {
		this.canDeleteLmtProfile = value;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#setCMSCreateInd
	 */
	public void setCMSCreateInd(boolean value) {
		this.cmsCreateInd = value;
	}

	/**
	 * Set customer info.
	 * 
	 * @param customer of type ICMSCustomer
	 */
	public void setCustomer(ICMSCustomer customer) {
		this.customer = customer;
	}

	/**
	 * Set the customer name
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value) {
		customerName = value;
	}

	/**
	 * Set if the customer has any bridging loan facilities
	 * @param hasBridgingLoan - true if customer has at least one bridging loan
	 *        facility, false otherwise
	 */
	public void setHasBridgingLoan(boolean hasBridgingLoan) {
		this.hasBridgingLoan = hasBridgingLoan;
	}

	/**
	 * Set if the customer has any contract financing facilities
	 * @param hasContractFinance - true if customer has at least one contract
	 *        financing facility, false otherwise
	 */
	public void setHasContractFinance(boolean hasContractFinance) {
		hasContractFinance = hasContractFinance;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * Set cms inner limit id.
	 * 
	 * @param innerLimitID of type long
	 */
	public void setInnerLimitID(long innerLimitID) {
		this.innerLimitID = innerLimitID;
	}

	/**
	 * Set instruction originationg location.
	 * 
	 * @param instrOrigLocation of type IBookingLocation
	 */
	public void setInstrOrigLocation(IBookingLocation instrOrigLocation) {
		this.instrOrigLocation = instrOrigLocation;
	}

	/**
	 * Set the Instruction Approved Date
	 * 
	 * @param value is of type Date
	 */
	public void setInstructionApprovedDate(Date value) {
		instrApprDate = value;
	}

	/**
	 * Set the instruction ID
	 * 
	 * @param value is of type long
	 */
	public void setInstructionID(long value) {
		instrID = value;
	}

	/**
	 * Set the Instruction Ref No
	 * 
	 * @param value is of type String
	 */
	public void setInstructionRefNo(String value) {
		instrRef = value;
	}

	/**
	 * Set indicator to check if there's DAP Exception for the customer
	 * searched.
	 * 
	 * @param isDAPError of type boolean
	 */
	public void setIsDAPError(boolean isDAPError) {
		this.isDAPError = isDAPError;
	}

	/**
	 * Set the legal ID
	 * 
	 * @param value is of type long
	 */
	public void setLegalID(long value) {
		legalID = value;
	}

	/**
	 * Set the legal name
	 * 
	 * @param value is of type String
	 */
	public void setLegalName(String value) {
		legalName = value;
	}

	/**
	 * Set the legal reference
	 * 
	 * @param value is of type String
	 */
	public void setLegalReference(String value) {
		legalReference = value;
	}

	/**
	 * Set limit profile.
	 * 
	 * @param limitProfile of type ILimitProfile
	 */
	public void setLimitProfile(ILimitProfile limitProfile) {
		this.limitProfile = limitProfile;
	}

	/**
	 * @see com.integrosys.cms.app.customer.bus.ICustomerSearchResult#setLmtProfileType
	 */
	public void setLmtProfileType(String lmtProfileType) {
		this.lmtProfileType = lmtProfileType;
	}

	public void setLosLimitProfileRef(String losLimitProfileRef) {
		this.losLimitProfileRef = losLimitProfileRef;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	/**
	 * Set the is no limits indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setNoLimitsInd(boolean value) {
		isNoLimits = value;
	}

	/**
	 * Set BCA originating location country.
	 * 
	 * @param origLocCntry of type String
	 */
	public void setOrigLocCntry(String origLocCntry) {
		this.origLocCntry = origLocCntry;
	}

	/**
	 * Set cms outer limit id.
	 * 
	 * @param outerLimitID of type long
	 */
	public void setOuterLimitID(long outerLimitID) {
		this.outerLimitID = outerLimitID;
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}

	/**
	 * Set the sub-profile ID
	 * 
	 * @param value is of type long
	 */
	public void setSubProfileID(long value) {
		subID = value;
	}

	/**
	 * Set the sub-profile reference
	 * 
	 * @param value is of type String
	 */
	public void setSubProfileReference(String value) {
		subRef = value;
	}

	/**
	 * Set the transaction ID
	 * 
	 * @param value is of type String
	 */
	public void setTransactionID(String value) {
		trxID = value;
	}

    public String getGroupCCINo() {
        return groupCCINo;
    }

    public void setGroupCCINo(String groupCCINo) {
        this.groupCCINo = groupCCINo;
    }

    public String getGroupCCIMapID() {
        return groupCCIMapID;
    }

    public void setGroupCCIMapID(String groupCCIMapID) {
        this.groupCCIMapID = groupCCIMapID;
    }

    public long getLimitProfileID() {
        return limitProfileID;
    }

    public void setLimitProfileID(long limitProfileID) {
        this.limitProfileID = limitProfileID;
    }

    public String getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public OBCustomerAddress getAddress() {
        return address;
    }

    public void setAddress(OBCustomerAddress address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(Date incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    /**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("#").append(System.identityHashCode(this)).append(" ");
		buf.append("Customer Name [").append(customerName).append("] ");
		buf.append("CIF Number [").append(legalReference).append("] ");
		buf.append("Host AA Number [").append(instrRef).append("] ");
		buf.append("LOS AA Number [").append(losLimitProfileRef).append("] ");
		buf.append("Approve Date[").append(instrApprDate).append("] ");

		return buf.toString();
	}
}