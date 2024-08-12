/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICustomerSearchResult.java,v 1.7 2005/10/14 09:30:02 lyng Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

import java.util.Date;

/**
 * This interface represents a customer search result data.
 *
 * @author $Author: lyng $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/14 09:30:02 $ Tag: $Name: $
 */
/**
 * Add new property for Stp customer inquiry
 *
 * @author Andy Wong
 * @since 20 Oct 2008
 */
public interface ICustomerSearchResult extends java.io.Serializable {
    // Getters

    /**
     * Get the legal name
     *
     * @return String
     */
    public String getLegalName();

    /**
     * Get the legal ID
     *
     * @return long
     */
    public long getLegalID();

    /**
     * Get the legal reference
     *
     * @return String
     */
    public String getLegalReference();

    /**
     * Get the customer name
     *
     * @return String
     */
    public String getCustomerName();

    /**
     * Get the sub-profile ID
     *
     * @return long
     */
    public long getSubProfileID();

    /**
     * Get the sub-profile reference
     *
     * @return String
     */
    public String getSubProfileReference();

    /**
     * Get the instruction ID
     *
     * @return long
     */
    public long getInstructionID();

    /**
     * Get the Instruction Ref No
     *
     * @return String
     */
    public String getInstructionRefNo();

    /**
     * Get the Instruction Approved Date
     *
     * @return Date
     */
    public Date getInstructionApprovedDate();

    /**
     * Get the transaction ID
     *
     * @return String
     */
    public String getTransactionID();

    /**
     * Get cms inner limit id.
     *
     * @return long
     */
    public long getInnerLimitID();

    /**
     * Get cms outer limit id.
     *
     * @return long
     */
    public long getOuterLimitID();

    /**
     * Get BCA originating location country.
     *
     * @return String
     */
    public String getOrigLocCntry();

    /**
     * Get limit profile.
     *
     * @return ILimitProfile
     */
    public ILimitProfile getLimitProfile();

    /**
     * Get customer info.
     *
     * @return ICMSCustomer
     */
    public ICMSCustomer getCustomer();

    /**
     * Check if the customer search encounters DAP Error.
     *
     * @return boolean
     */
    public boolean getIsDAPError();

    /**
     * Get bca originating location.
     *
     * @return IBookingLocation
     */
    public IBookingLocation getBcaOrigLocation();

    /**
     * Get instruction originating location.
     *
     * @return IBookingLocation
     */
    public IBookingLocation getInstrOrigLocation();

    // Setters
    /**
     * Set the legal name
     *
     * @param value is of type String
     */
    public void setLegalName(String value);

    /**
     * Set the legal ID
     *
     * @param value is of type long
     */
    public void setLegalID(long value);

    /**
     * Set the legal reference
     *
     * @param value is of type String
     */
    public void setLegalReference(String value);

    /**
     * Set the customer name
     *
     * @param value is of type String
     */
    public void setCustomerName(String value);

    /**
     * Set the sub-profile ID
     *
     * @param value is of type long
     */
    public void setSubProfileID(long value);

    /**
     * Set the sub-profile reference
     *
     * @param value is of type String
     */
    public void setSubProfileReference(String value);

    /**
     * Set the instruction ID
     *
     * @param value is of type long
     */
    public void setInstructionID(long value);

    /**
     * Set the Instruction Ref No
     *
     * @param value is of type String
     */
    public void setInstructionRefNo(String value);

    /**
     * Set the Instruction Approved Date
     *
     * @param value is of type Date
     */
    public void setInstructionApprovedDate(Date value);

    /**
     * Set the transaction ID
     *
     * @param value is of type String
     */
    public void setTransactionID(String value);

    /**
     * Set cms inner limit id.
     *
     * @param innerLimitID of type long
     */
    public void setInnerLimitID(long innerLimitID);

    /**
     * Set cms outer limit id.
     *
     * @param outerLimitID of type long
     */
    public void setOuterLimitID(long outerLimitID);

    /**
     * Set BCA originating location country.
     *
     * @param origLocCntry of type String
     */
    public void setOrigLocCntry(String origLocCntry);

    /**
     * Set limit profile.
     *
     * @param limitProfile of type ILimitProfile
     */
    public void setLimitProfile(ILimitProfile limitProfile);

    /**
     * Set customer info.
     *
     * @param customer of type ICMSCustomer
     */
    public void setCustomer(ICMSCustomer customer);

    /**
     * Set indicator to check if there's DAP Exception for the customer
     * searched.
     *
     * @param isDAPError of type boolean
     */
    public void setIsDAPError(boolean isDAPError);

    /**
     * Set BCA originating location.
     *
     * @param bcaOrigLocation of type IBookingLocation
     */
    public void setBcaOrigLocation(IBookingLocation bcaOrigLocation);

    /**
     * Set instruction originationg location.
     *
     * @param instrOrigLocation of type IBookingLocation
     */
    public void setInstrOrigLocation(IBookingLocation instrOrigLocation);

    /**
     * Get limit profile type.
     *
     * @return String
     */
    public String getLmtProfileType();

    /**
     * Set limit profile type.
     *
     * @param lmtProfileType of type String
     */
    public void setLmtProfileType(String lmtProfileType);

    /**
     * Get agreement ID.
     *
     * @return long
     */
    public long getAgreementID();

    /**
     * Set agreement ID.
     *
     * @param agreementID of type long
     */
    public void setAgreementID(long agreementID);

    /**
     * Get agreement type.
     *
     * @return String
     */
    public String getAgreementType();

    /**
     * Set agreement type.
     *
     * @param agreementType of type String
     */
    public void setAgreementType(String agreementType);

    /**
     * Get if the customer has any contract financing facilities
     *
     * @return true if customer has at least one contract financing facility,
     *         false otherwise
     */
    boolean getHasContractFinance();

    /**
     * Set if the customer has any contract financing facilities
     *
     * @param hasContractFinance - true if customer has at least one contract
     *                           financing facility, false otherwise
     */
    void setHasContractFinance(boolean hasContractFinance);

    /**
     * Get if the customer has any bridging loan facilities
     *
     * @return true if customer has at least one bridging loan facility, false
     *         otherwise
     */
    boolean getHasBridgingLoan();

    /**
     * Set if the customer has any bridging loan facilities
     *
     * @param hasBridgingLoan - true if customer has at least one bridging loan
     *                        facility, false otherwise
     */
    void setHasBridgingLoan(boolean hasBridgingLoan);

    /**
     * Get CMS create indicator
     *
     * @return boolean
     */
    public boolean getCMSCreateInd();

	/**
     * Set CMS create indicator
     *
     * @param value is of type boolean
     */
	public void setCMSCreateInd(boolean value);

	/**
     * Get can delete limit profile indicator
     *
     * @return boolean
     */
	public boolean getCanDeleteLmtProfile();

	/**
     * Set can delete limit profile indicator
     *
     * @param value is of type boolean
     */
	public void setCanDeleteLmtProfile(boolean value);

    /**
     * Get customer ID type
     * @return
     */
    public String getIdType();

    /**
     * Set customer ID type
     * @param idType
     */
    public void setIdType(String idType);

    /**
     * Get customer ID Number
     * @return
     */
    public String getIdNo();

    /**
     * Set customer ID Number
     * @param idNo
     */
    public void setIdNo(String idNo);

}