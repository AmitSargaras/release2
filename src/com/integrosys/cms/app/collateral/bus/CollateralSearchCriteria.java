package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

public class CollateralSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 4240738900592162689L;

	private String aaNumber;

	private boolean advanceSearch = false;

	private boolean requiredPagination = true;

	private Integer totalCountForCurrentTotalPages;

	private String advanceSearchType = "";

	private String aircraftType;

	private String assetType;

	private String branchCode;

	private String builtYear;

	private String chassissNo;

	private String engineNo;

	private String customerName = null;

	private String districtCD;

	private String flagRegistered;

	private Date fromExpDate;

	private String goldGrade;

	private String idNO = null;

	private String issuer;

	private String itemType;

	private String legalID = null;

	private String leIDType = null;

	private String modelNo;

	private String mukimCD;

	private String purchaseReceiptNo;

	private String regN0;

	private String securityID;

	private String securityLoc = "";

	private String securitySubType = "";

	private String securityType = "";

	private String serialNo;

	private String standbyLCNo;

	private String stateCD;

	private String titleNo;

	private String titleNoPrefix;

	private String titleTypeCD;

	private Date toExpDate;

	private ITrxContext trxContext;

	private String vehType;

	private String yearOfManufacture;

	private String aaSearchType;

	private String securitySearchType;

	public CollateralSearchCriteria() {
	}

	public String getAaNumber() {
		return aaNumber;
	}

	public String getAaSearchType() {
		return aaSearchType;
	}

	public boolean getAdvanceSearch() {
		return advanceSearch;
	}

	public String getAdvanceSearchType() {
		return advanceSearchType;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public String getAssetType() {
		return assetType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getBuiltYear() {
		return builtYear;
	}

	public String getChassissNo() {
		return chassissNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getDistrictCD() {
		return districtCD;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public String getFlagRegistered() {
		return flagRegistered;
	}

	public Date getFromExpDate() {
		return fromExpDate;
	}

	public String getGoldGrade() {
		return goldGrade;
	}

	public String getIdNO() {
		return idNO;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getItemType() {
		return itemType;
	}

	public String getLegalID() {
		return legalID;
	}

	public String getLeIDType() {
		return leIDType;
	}

	public String getModelNo() {
		return modelNo;
	}

	public String getMukimCD() {
		return mukimCD;
	}

	public String getPurchaseReceiptNo() {
		return purchaseReceiptNo;
	}

	public String getRegN0() {
		return regN0;
	}

	public String getSecurityID() {
		return securityID;
	}

	public String getSecurityLoc() {
		return securityLoc;
	}

	public String getSecuritySearchType() {
		return securitySearchType;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public String getSecurityType() {
		return securityType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public String getStandbyLCNo() {
		return standbyLCNo;
	}

	public String getStateCD() {
		return stateCD;
	}

	public String getTitleNo() {
		return titleNo;
	}

	public String getTitleNoPrefix() {
		return titleNoPrefix;
	}

	public String getTitleTypeCD() {
		return titleTypeCD;
	}

	public Date getToExpDate() {
		return toExpDate;
	}

	public Integer getTotalCountForCurrentTotalPages() {
		return totalCountForCurrentTotalPages;
	}

	public ITrxContext getTrxContext() {
		return trxContext;
	}

	public String getVehType() {
		return vehType;
	}

	public String getYearOfManufacture() {
		return yearOfManufacture;
	}

	public boolean isRequiredPagination() {
		return requiredPagination;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public void setAaSearchType(String aaSearchType) {
		this.aaSearchType = aaSearchType;
	}

	public void setAdvanceSearch(boolean advanceSearch) {
		this.advanceSearch = advanceSearch;
	}

	public void setAdvanceSearchType(String advanceSearchType) {
		this.advanceSearchType = advanceSearchType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setBuiltYear(String builtYear) {
		this.builtYear = builtYear;
	}

	public void setChassissNo(String chassissNo) {
		this.chassissNo = chassissNo;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setDistrictCD(String districtCD) {
		this.districtCD = districtCD;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public void setFlagRegistered(String flagRegistered) {
		this.flagRegistered = flagRegistered;
	}

	public void setFromExpDate(Date fromExpDate) {
		this.fromExpDate = fromExpDate;
	}

	public void setGoldGrade(String goldGrade) {
		this.goldGrade = goldGrade;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public void setMukimCD(String mukimCD) {
		this.mukimCD = mukimCD;
	}

	public void setPurchaseReceiptNo(String purchaseReceiptNo) {
		this.purchaseReceiptNo = purchaseReceiptNo;
	}

	public void setRegN0(String regN0) {
		this.regN0 = regN0;
	}

	public void setRequiredPagination(boolean requiredPagination) {
		this.requiredPagination = requiredPagination;
	}

	public void setSecurityID(String sCISecurityID) {
		this.securityID = sCISecurityID;
	}

	public void setSecurityLoc(String securityLoc) {
		this.securityLoc = securityLoc;
	}

	public void setSecuritySearchType(String securitySearchType) {
		this.securitySearchType = securitySearchType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public void setStandbyLCNo(String standbyLCNo) {
		this.standbyLCNo = standbyLCNo;
	}

	public void setStateCD(String stateCD) {
		this.stateCD = stateCD;
	}

	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}

	public void setTitleNoPrefix(String titleNoPrefix) {
		this.titleNoPrefix = titleNoPrefix;
	}

	public void setTitleTypeCD(String titleTypeCD) {
		this.titleTypeCD = titleTypeCD;
	}

	public void setToExpDate(Date toExpDate) {
		this.toExpDate = toExpDate;
	}

	public void setTotalCountForCurrentTotalPages(Integer totalCountForCurrentTotalPages) {
		this.totalCountForCurrentTotalPages = totalCountForCurrentTotalPages;
	}

	public void setTrxContext(ITrxContext trxContext) {
		this.trxContext = trxContext;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	/**
	 * To display full representation of the search criteria in detailed level,
	 * for earch subtype or type will only show it's info only.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("CollateralSearchCriteria -");
		buf.append("AvancedSearch ? [").append(advanceSearch ? "YES" : "NO").append("], ");
		buf.append("Security Type [").append(securityType).append("], ");
		buf.append("Security Sub Type [").append(securitySubType).append("], ");

		if (!advanceSearch) {
			buf.append("Security Source [").append(securitySearchType).append("]");
			buf.append("Security Id [").append(securityID).append("]");
		}
		else {
			buf.append("Customer Name [").append(customerName).append("], ");
			buf.append("LE Id Type [").append(leIDType).append("], ");
			buf.append("Legal Id [").append(legalID).append("], ");
			buf.append("Id Number [").append(customerName).append("], ");
			buf.append("AA Number [").append(customerName).append("], ");
			buf.append("Branch Code [").append(branchCode).append("]");

			if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(securitySubType)) {
				buf.append(" 'Vehicle'; Vehicle Type [").append(assetType).append("], ");
				buf.append("Registration No [").append(regN0).append("], ");
				buf.append("Chassis No [").append(chassissNo).append("], ");
				buf.append("Engine No [").append(engineNo).append("], ");
			}
			else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD.equals(securitySubType)) {
				buf.append(" 'Gold'; Item Type [").append(itemType).append("], ");
				buf.append("Purchase Receipt No [").append(purchaseReceiptNo).append("], ");
				buf.append("Gold Grade [").append(goldGrade).append("]");
			}
			else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(securitySubType)) {
				buf.append(" 'Plant & Equipment'; Asset Type [").append(assetType).append("], ");
				buf.append("Serial No [").append(serialNo).append("], ");
				buf.append("Manufactured Year [").append(yearOfManufacture).append("], ");
				buf.append("Model No [").append(modelNo).append("]");
			}
			else if (ICMSConstant.COLTYPE_ASSET_VESSEL.equals(securitySubType)) {
				buf.append(" 'Vessel'; Flag Registered [").append(flagRegistered).append("], ");
				buf.append("Built Year [").append(builtYear).append("], ");
				buf.append(" Asset Type [").append(assetType).append("]");
			}
			else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT.equals(securitySubType)) {
				buf.append(" 'Aircraft'; Aircraft Type [").append(aircraftType).append("]");
				buf.append(" Asset Type [").append(assetType).append("]");
			}
			else if (ICMSConstant.SECURITY_TYPE_GUARANTEE.equals(securityType)) {
				buf.append(" 'Guarantee'; Issuer [").append(issuer).append("], ");
				buf.append("Standby LC No [").append(standbyLCNo).append("], ");
				buf.append("Expire Date (from) [").append(fromExpDate).append("], ");
				buf.append("Expire Date (to) [").append(toExpDate).append("]");
			}
			else if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(securityType)) {
				buf.append(" 'Property'; Title No [").append(titleNoPrefix).append(titleNo).append("], ");
				buf.append("Title Type [").append(titleTypeCD).append("], ");
				buf.append("State [").append(stateCD).append("], ");
				buf.append("District [").append(districtCD).append("], ");
				buf.append("Mukim [").append(mukimCD).append("]");
			}
		}

		return buf.toString();
	}
}
