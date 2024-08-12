/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateralSearchResult.java,v 1.17 2006/05/08 11:28:19 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This class represents a collateral search result data.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/05/08 11:28:19 $ Tag: $Name: $
 */
public class OBCollateralSearchResult implements ICollateralSearchResult {
	private String legalName;

	private String customerName;

	private long subProfileID;

	private String legalID;

	private String instructionRefNo;

	private Date instructionApprovedDate;

	private long limitID;

	private String sciLimitID;

	private String securityID;

	private long collateralID;

	private String productDesc;

	private String typeName;

	private String subTypeName;

	private String subTypeCode;

	private String typeCode;

	private boolean isCollateralPerfected;

	private String sciLimitMapStatus;

	private String collateralStatus;

	private long limitSecMapID;

	private long sciLimitSecMapID;

	private String sciOuterLimitID;

	private String sciOuterLegalID;

	private String outerCustomerName;

	private String outerInstructionRefNo;

	private String outerOrigLocCntry;

	private boolean isInnerOuterSameBCA = true;

	private IBookingLocation securityLocation;

	private String securityOrganization;

	private String sourceSystemName;

	private List secSystemName;

	private List OBCollateralLimitList;

	/**
	 * Default Constructor
	 */
	public OBCollateralSearchResult() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICollateralSearchResult
	 */
	public OBCollateralSearchResult(ICollateralSearchResult value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	public List getOBCollateralLimitList() {
		return OBCollateralLimitList;
	}

	public void setOBCollateralLimitList(List OBCollateralLimitList) {
		this.OBCollateralLimitList = OBCollateralLimitList;
	}

	/**
	 * Get legal name.
	 * 
	 * @return String
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * Set legal name.
	 * 
	 * @param legalName of type String
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	 * Get customer name.
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Set customer name.
	 * 
	 * @param customerName of type String
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Get sub profile id.
	 * 
	 * @return long
	 */
	public long getSubProfileID() {
		return subProfileID;
	}

	/**
	 * Set sub profile id.
	 * 
	 * @param subProfileID of type long
	 */
	public void setSubProfileID(long subProfileID) {
		this.subProfileID = subProfileID;
	}

	/**
	 * Get legal id.
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalID;
	}

	/**
	 * Set legal id.
	 * 
	 * @param legalID of type long
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	/**
	 * Get instruction reference no.
	 * 
	 * @return String
	 */
	public String getInstructionRefNo() {
		return instructionRefNo;
	}

	/**
	 * Set instruction reference no.
	 * 
	 * @param instructionRefNo of type String
	 */
	public void setInstructionRefNo(String instructionRefNo) {
		this.instructionRefNo = instructionRefNo;
	}

	/**
	 * Get instruction approved date.
	 * 
	 * @return Date
	 */
	public Date getInstructionApprovedDate() {
		return instructionApprovedDate;
	}

	/**
	 * Set instruction approved date.
	 * 
	 * @param instructionApprovedDate of type Date
	 */
	public void setInstructionApprovedDate(Date instructionApprovedDate) {
		this.instructionApprovedDate = instructionApprovedDate;
	}

	/**
	 * Get limit id.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Set limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	/**
	 * Get security id in SCI.
	 * 
	 * @return String
	 */
	public String getSecurityID() {
		return securityID;
	}

	/**
	 * Set security id in SCI.
	 * 
	 * @param securityID of type long
	 */
	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Get product description.
	 * 
	 * @return String
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * Set product description.
	 * 
	 * @param productDesc of type String
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * Get collateral type name.
	 * 
	 * @return String
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set collateral sub type name.
	 * 
	 * @param typeName of type String
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Get collateral sub type name.
	 * 
	 * @return String
	 */
	public String getSubTypeName() {
		return subTypeName;
	}

	/**
	 * Set collateral sub type name.
	 * 
	 * @param subTypeName of type String
	 */
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	/**
	 * Get security subtype code.
	 * 
	 * @return String
	 */
	public String getSubTypeCode() {
		return subTypeCode;
	}

	/**
	 * Set security subtype code.
	 * 
	 * @param subTypeCode of type String
	 */
	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	/**
	 * Get security type code.
	 * 
	 * @return String
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * Set security type code.
	 * 
	 * @param typeCode of type String
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * Get limit ID from SCI.
	 * 
	 * @return String
	 */
	public String getSCILimitID() {
		return sciLimitID;
	}

	/**
	 * Set limit ID from SCI.
	 * 
	 * @param sciLimitID of type String
	 */
	public void setSCILimitID(String sciLimitID) {
		this.sciLimitID = sciLimitID;
	}

	/**
	 * Get if the security is perfected.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralPerfected() {
		return isCollateralPerfected;
	}

	/**
	 * Set if the security is perfected.
	 * 
	 * @param isCollateralPerfected of type boolean
	 */
	public void setIsCollateralPerfected(boolean isCollateralPerfected) {
		this.isCollateralPerfected = isCollateralPerfected;
	}

	/**
	 * Get limit map update status indicator.
	 * 
	 * @return String
	 */
	public String getSCILimitMapStatus() {
		return sciLimitMapStatus;
	}

	/**
	 * Set limit map update status indicator.
	 * 
	 * @param sciLimitMapStatus of type String
	 */
	public void setSCILimitMapStatus(String sciLimitMapStatus) {
		this.sciLimitMapStatus = sciLimitMapStatus;
	}

	/**
	 * Get collateral business status. It's either ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @return String
	 */
	public String getCollateralStatus() {
		return collateralStatus;
	}

	/**
	 * Set collateral business status. The value is either
	 * ICMSConstant.STATE_ACTIVE or ICMSConstant.STATE_DELETED
	 * 
	 * @param collateralStatus of type String
	 */
	public void setCollateralStatus(String collateralStatus) {
		this.collateralStatus = collateralStatus;
	}

	/**
	 * Get limit security map id.
	 * 
	 * @return long
	 */
	public long getLimitSecMapID() {
		return limitSecMapID;
	}

	/**
	 * Set limit security map id.
	 * 
	 * @param limitSecMapID of type long
	 */
	public void setLimitSecMapID(long limitSecMapID) {
		this.limitSecMapID = limitSecMapID;
	}

	/**
	 * Get limit security map reference id.
	 * 
	 * @return long
	 */
	public long getSCILimitSecMapID() {
		return sciLimitSecMapID;
	}

	/**
	 * Set limit security map reference id.
	 * 
	 * @param sciLimitSecMapID of type long
	 */
	public void setSCILimitSecMapID(long sciLimitSecMapID) {
		this.sciLimitSecMapID = sciLimitSecMapID;
	}

	/**
	 * Get sci outer limit id.
	 * 
	 * @return String
	 */
	public String getSCIOuterLimitID() {
		return sciOuterLimitID;
	}

	/**
	 * Set sci outer limit id.
	 * 
	 * @param sciOuterLimitID of type long
	 */
	public void setSCIOuterLimitID(String sciOuterLimitID) {
		this.sciOuterLimitID = sciOuterLimitID;
	}

	/**
	 * Get outer limit LE ID.
	 * 
	 * @return String
	 */
	public String getSCIOuterLegalID() {
		return sciOuterLegalID;
	}

	/**
	 * Set outer limit LE ID.
	 * 
	 * @param sciOuterLegalID of type long
	 */
	public void setSCIOuterLegalID(String sciOuterLegalID) {
		this.sciOuterLegalID = sciOuterLegalID;
	}

	/**
	 * Get outer customer name.
	 * 
	 * @return String
	 */
	public String getOuterCustomerName() {
		return outerCustomerName;
	}

	/**
	 * Set outer customer name.
	 * 
	 * @param outerCustomerName of type String
	 */
	public void setOuterCustomerName(String outerCustomerName) {
		this.outerCustomerName = outerCustomerName;
	}

	/**
	 * Get outer BCA instruction reference no.
	 * 
	 * @return String
	 */
	public String getOuterInstructionRefNo() {
		return outerInstructionRefNo;
	}

	/**
	 * Set outer BCA instruction reference no.
	 * 
	 * @param outerInstructionRefNo of type String
	 */
	public void setOuterInstructionRefNo(String outerInstructionRefNo) {
		this.outerInstructionRefNo = outerInstructionRefNo;
	}

	/**
	 * Get outer originating location country.
	 * 
	 * @return String
	 */
	public String getOuterOrigLocCntry() {
		return outerOrigLocCntry;
	}

	/**
	 * Set outer originating location country.
	 * 
	 * @param outerOrigLocCntry of type String
	 */
	public void setOuterOrigLocCntry(String outerOrigLocCntry) {
		this.outerOrigLocCntry = outerOrigLocCntry;
	}

	/**
	 * Check if inner and outer limit are of the same limit profile.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA() {
		return isInnerOuterSameBCA;
	}

	/**
	 * Set an indicator to check if inner and outer limit are of the same limit
	 * profile.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
		this.isInnerOuterSameBCA = isInnerOuterSameBCA;
	}

	/**
	 * Get security booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getSecurityLocation() {
		return securityLocation;
	}

	/**
	 * Set security booking location.
	 * 
	 * @param securityLocation of type IBookingLocation
	 */
	public void setSecurityLocation(IBookingLocation securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public List getSecSystemName() {
		return secSystemName;
	}

	public void setSecSystemName(List secSystemName) {
		this.secSystemName = secSystemName;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}