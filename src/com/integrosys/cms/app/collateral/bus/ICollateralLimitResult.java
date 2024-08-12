package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import com.integrosys.cms.app.common.bus.IBookingLocation;

public interface ICollateralLimitResult extends java.io.Serializable {
	/**
	 * Get legal name.
	 * 
	 * @return String
	 */
	public String getLegalName();

	/**
	 * Set legal name.
	 * 
	 * @param legalName of type String
	 */
	public void setLegalName(String legalName);

	/**
	 * Get customer name.
	 * 
	 * @return String
	 */
	public String getCustomerName();

	/**
	 * Set customer name.
	 * 
	 * @param customerName of type String
	 */
	public void setCustomerName(String customerName);

	/**
	 * Get sub profile id.
	 * 
	 * @return long
	 */
	public long getSubProfileID();

	/**
	 * Set sub profile id.
	 * 
	 * @param subProfileID of type long
	 */
	public void setSubProfileID(long subProfileID);

	/**
	 * Get legal id.
	 * 
	 * @return String
	 */
	public String getLegalID();

	/**
	 * Set legal id.
	 * 
	 * @param legalID of type String
	 */
	public void setLegalID(String legalID);

	/**
	 * Get instruction reference no.
	 * 
	 * @return String
	 */
	public String getInstructionRefNo();

	/**
	 * Set instruction reference no.
	 * 
	 * @param instructionRefNo of type String
	 */
	public void setInstructionRefNo(String instructionRefNo);

	/**
	 * Get instruction approved date.
	 * 
	 * @return Date
	 */
	public Date getInstructionApprovedDate();

	/**
	 * Set instruction approved date.
	 * 
	 * @param instructionApprovedDate of type Date
	 */
	public void setInstructionApprovedDate(Date instructionApprovedDate);

	/**
	 * Get limit id.
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Set limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID);

	/**
	 * Get security id in SCI.
	 * 
	 * @return String
	 */
	public String getSecurityID();

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Get product description.
	 * 
	 * @return String
	 */
	public String getProductDesc();

	/**
	 * Set product description.
	 * 
	 * @param productDesc of type String
	 */
	public void setProductDesc(String productDesc);

	/**
	 * Get collateral type name.
	 * 
	 * @return String
	 */
	public String getTypeName();

	/**
	 * Set collateral sub type name.
	 * 
	 * @param typeName of type String
	 */
	public void setTypeName(String typeName);

	/**
	 * Get collateral sub type name.
	 * 
	 * @return String
	 */
	public String getSubTypeName();

	/**
	 * Set collateral sub type name.
	 * 
	 * @param subTypeName of type String
	 */
	public void setSubTypeName(String subTypeName);

	/**
	 * Get security subtype code.
	 * 
	 * @return String
	 */
	public String getSubTypeCode();

	/**
	 * Set security subtype code.
	 * 
	 * @param subTypeCode of type String
	 */
	public void setSubTypeCode(String subTypeCode);

	/**
	 * Get security type code.
	 * 
	 * @return String
	 */
	public String getTypeCode();

	/**
	 * Set security type code.
	 * 
	 * @param typeCode of type String
	 */
	public void setTypeCode(String typeCode);

	/**
	 * Get limit ID from SCI.
	 * 
	 * @return String
	 */
	public String getSCILimitID();

	/**
	 * Set limit ID from SCI.
	 * 
	 * @param sciLimitID of type String
	 */
	public void setSCILimitID(String sciLimitID);

	/**
	 * Get if the security is perfected.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralPerfected();

	/**
	 * Set if the security is perfected.
	 * 
	 * @param isCollateralPerfected of type boolean
	 */
	public void setIsCollateralPerfected(boolean isCollateralPerfected);

	/**
	 * Get limit map update status indicator.
	 * 
	 * @return String
	 */
	public String getSCILimitMapStatus();

	/**
	 * Set limit map update status indicator.
	 * 
	 * @param sciLimitMapStatus of type String
	 */
	public void setSCILimitMapStatus(String sciLimitMapStatus);

	/**
	 * Get collateral business status. It's either ICMSConstant.STATE_ACTIVE or
	 * ICMSConstant.STATE_DELETED.
	 * 
	 * @return String
	 */
	public String getCollateralStatus();

	/**
	 * Set collateral business status. The value is either
	 * ICMSConstant.STATE_ACTIVE or ICMSConstant.STATE_DELETED
	 * 
	 * @param collateralStatus of type String
	 */
	public void setCollateralStatus(String collateralStatus);

	/**
	 * Get limit security map id.
	 * 
	 * @return long
	 */
	public long getLimitSecMapID();

	/**
	 * Set limit security map id.
	 * 
	 * @param limitSecMapID of type long
	 */
	public void setLimitSecMapID(long limitSecMapID);

	/**
	 * Get limit security map reference id.
	 * 
	 * @return long
	 */
	public long getSCILimitSecMapID();

	/**
	 * Set limit security map reference id.
	 * 
	 * @param sciLimitSecMapID of type long
	 */
	public void setSCILimitSecMapID(long sciLimitSecMapID);

	/**
	 * Get sci outer limit id.
	 * 
	 * @return String
	 */
	public String getSCIOuterLimitID();

	/**
	 * Set sci outer limit id.
	 * 
	 * @param sciOuterLimitID of type long
	 */
	public void setSCIOuterLimitID(String sciOuterLimitID);

	/**
	 * Get outer limit LE ID.
	 * 
	 * @return String
	 */
	public String getSCIOuterLegalID();

	/**
	 * Set outer limit LE ID.
	 * 
	 * @param sciOuterLegalID of type String
	 */
	public void setSCIOuterLegalID(String sciOuterLegalID);

	/**
	 * Get outer customer name.
	 * 
	 * @return String
	 */
	public String getOuterCustomerName();

	/**
	 * Set outer customer name.
	 * 
	 * @param outerCustomerName of type String
	 */
	public void setOuterCustomerName(String outerCustomerName);

	/**
	 * Get outer BCA instruction reference no.
	 * 
	 * @return String
	 */
	public String getOuterInstructionRefNo();

	/**
	 * Set outer BCA instruction reference no.
	 * 
	 * @param outerInstructionRefNo of type String
	 */
	public void setOuterInstructionRefNo(String outerInstructionRefNo);

	/**
	 * Get outer originating location country.
	 * 
	 * @return String
	 */
	public String getOuterOrigLocCntry();

	/**
	 * Set outer originating location country.
	 * 
	 * @param outerOrigLocCntry of type String
	 */
	public void setOuterOrigLocCntry(String outerOrigLocCntry);

	/**
	 * Check if inner and outer limit are of the same limit profile.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA();

	/**
	 * Set an indicator to check if inner and outer limit are of the same limit
	 * profile.
	 * 
	 * @param isInnerOuterSameBCA of type boolean
	 */
	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA);

	/**
	 * Get security booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getSecurityLocation();

	/**
	 * Set security booking location.
	 * 
	 * @param securityLocation of type IBookingLocation
	 */
	public void setSecurityLocation(IBookingLocation securityLocation);

	public String getSecurityOrganization();

	public void setSecurityOrganization(String org);

	public String getSourceSystemName();

	public void setSourceSystemName(String sourceSystemName);
}
