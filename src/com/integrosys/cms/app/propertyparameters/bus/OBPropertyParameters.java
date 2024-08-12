package com.integrosys.cms.app.propertyparameters.bus;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 10:20:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBPropertyParameters implements IPropertyParameters {

	private long parameterId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String propertyType;

	private String collateralSubType;

	private String countryCode;

	private String stateCode;

	private String districtCode;

	private String mukimCode;

	private String postcode;

	private long landAreaValueFrom = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;;

	private String landAreaUnitFrom;

	private long landAreaValueTo = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;;

	private String landAreaUnitTo;

	private long buildupAreaValueFrom = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;;

	private String buildupAreaUnitFrom;

	private long buildupAreaValueTo = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;;

	private String buildupAreaUnitTo;

	private double minimumCurrentOmv = com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;;

	private String omvType;

	private long VariationOMV = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;;

	private String valuationDescription;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ArrayList stateList;

	private ArrayList districtList;

	private ArrayList MukimList;

	private ArrayList securitySubTypeList;

	public long getParameterId() {
		return parameterId;
	}

	public void setParameterId(long parameterId) {
		this.parameterId = parameterId;
	}

	public String getPropertyType() {
		this.propertyType = "Property";
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getCollateralSubType() {
		return collateralSubType;
	}

	public void setCollateralSubType(String collateralSubType) {
		this.collateralSubType = collateralSubType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {

		return stateCode;
	}

	public void setStateCode(String stateCode) {

		this.stateCode = stateCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getMukimCode() {
		return mukimCode;
	}

	public void setMukimCode(String mukimCode) {
		this.mukimCode = mukimCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public long getLandAreaValueFrom() {
		return landAreaValueFrom;
	}

	public void setLandAreaValueFrom(long landAreaValueFrom) {
		this.landAreaValueFrom = landAreaValueFrom;
	}

	public String getLandAreaUnitFrom() {
		return landAreaUnitFrom;
	}

	public void setLandAreaUnitFrom(String landAreaUnitFrom) {
		this.landAreaUnitFrom = landAreaUnitFrom;
	}

	public long getLandAreaValueTo() {
		return landAreaValueTo;
	}

	public void setLandAreaValueTo(long landareaValueTo) {
		this.landAreaValueTo = landareaValueTo;
	}

	public String getLandAreaUnitTo() {
		return landAreaUnitTo;
	}

	public void setLandAreaUnitTo(String landareaUnitTo) {
		this.landAreaUnitTo = landareaUnitTo;
	}

	public long getBuildupAreaValueFrom() {
		return buildupAreaValueFrom;
	}

	public void setBuildupAreaValueFrom(long buildupareaValueFrom) {
		this.buildupAreaValueFrom = buildupareaValueFrom;
	}

	public String getBuildupAreaUnitFrom() {
		return buildupAreaUnitFrom;
	}

	public void setBuildupAreaUnitFrom(String buildupareaUnitFrom) {
		this.buildupAreaUnitFrom = buildupareaUnitFrom;
	}

	public long getBuildupAreaValueTo() {
		return buildupAreaValueTo;
	}

	public void setBuildupAreaValueTo(long buildupAreaValueTo) {
		this.buildupAreaValueTo = buildupAreaValueTo;
	}

	public String getBuildupAreaUnitTo() {
		return buildupAreaUnitTo;
	}

	public void setBuildupAreaUnitTo(String buildupAreaUnitTo) {
		this.buildupAreaUnitTo = buildupAreaUnitTo;
	}

	public double getMinimumCurrentOmv() {
		return minimumCurrentOmv;
	}

	public void setMinimumCurrentOmv(double minimumCurrentOmv) {
		this.minimumCurrentOmv = minimumCurrentOmv;
	}

	public String getOmvType() {
		return omvType;
	}

	public void setOmvType(String omvType) {
		this.omvType = omvType;
	}

	public long getVariationOMV() {
		return VariationOMV;
	}

	public void setVariationOMV(long variationOMV) {
		VariationOMV = variationOMV;
	}

	public String getValuationDescription() {
		return valuationDescription;
	}

	public void setValuationDescription(String valuationDescription) {
		this.valuationDescription = valuationDescription;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public ArrayList getStateList() {
		// covert String to list, set list fields
		StringTokenizer stToken = new StringTokenizer(getStateCode(), ";");
		ArrayList newList = new ArrayList();

		while (stToken.hasMoreTokens()) {
			String stateCode = (String) stToken.nextToken();
			newList.add(stateCode);
		}
		this.stateList = newList;

		return stateList;
	}

	public void setStateList(ArrayList stateList) {
		// convert list to String
		// then call setStateCode()
		String mergedString = "";
		for (int i = 0; i < stateList.size(); i++) {
			String stateCode = (String) stateList.get(i);
			mergedString += stateCode + ";";
		}
		setStateCode(mergedString);

		this.stateList = stateList;
	}

	public ArrayList getDistrictList() {
		// covert String to list, set list fields
		StringTokenizer stToken = new StringTokenizer(getDistrictCode(), ";");
		ArrayList newList = new ArrayList();

		while (stToken.hasMoreTokens()) {
			String districtCode = (String) stToken.nextToken();
			newList.add(districtCode);
		}
		this.districtList = newList;

		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		// covert list to String,set string fields
		String mergedString = "";
		for (int i = 0; i < districtList.size(); i++) {
			String districtCode = (String) districtList.get(i);
			mergedString += districtCode + ";";
		}
		setDistrictCode(mergedString);
		this.districtList = districtList;
	}

	public ArrayList getMukimList() {
		// covert String to list, set list fields
		StringTokenizer stToken = new StringTokenizer(getMukimCode(), ";");
		ArrayList newList = new ArrayList();

		while (stToken.hasMoreTokens()) {
			String mukimCode = (String) stToken.nextToken();
			newList.add(mukimCode);
		}
		this.MukimList = newList;

		return MukimList;
	}

	public void setMukimList(ArrayList mukimList) {
		// covert list to String,set string fields
		String mergedString = "";
		for (int i = 0; i < mukimList.size(); i++) {
			String mukimCode = (String) mukimList.get(i);
			mergedString += mukimCode + ";";
		}
		setMukimCode(mergedString);
		this.MukimList = mukimList;
	}

	public ArrayList getSecuritySubTypeList() {
		// covert String to list, set list fields
		StringTokenizer stToken = new StringTokenizer(getCollateralSubType(), ";");
		ArrayList newList = new ArrayList();

		while (stToken.hasMoreTokens()) {
			String colSubType = (String) stToken.nextToken();
			newList.add(colSubType);
		}
		this.securitySubTypeList = newList;

		return securitySubTypeList;
	}

	public void setSecuritySubTypeList(ArrayList securitySubTypeList) {
		// covert list to String,set string fields
		String mergedString = "";
		for (int i = 0; i < securitySubTypeList.size(); i++) {
			String colSubType = (String) securitySubTypeList.get(i);
			mergedString += colSubType + ";";
		}
		setCollateralSubType(mergedString);
		this.securitySubTypeList = securitySubTypeList;
	}

}
