package com.integrosys.cms.app.propertyparameters.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 10:20:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPropertyParameters extends IValueObject, Serializable {

	public long getParameterId();

	public void setParameterId(long parameterId);

	public String getPropertyType();

	public void setPropertyType(String propertyType);

	public String getCollateralSubType();

	public void setCollateralSubType(String collateralSubType);

	public String getCountryCode();

	public void setCountryCode(String countryCode);

	public String getStateCode();

	public void setStateCode(String stateCode);

	public String getDistrictCode();

	public void setDistrictCode(String districtCode);

	public String getMukimCode();

	public void setMukimCode(String mukimCode);

	public String getPostcode();

	public void setPostcode(String postcode);

	public long getLandAreaValueFrom();

	public void setLandAreaValueFrom(long landAreaValueFrom);

	public String getLandAreaUnitFrom();

	public void setLandAreaUnitFrom(String landAreaUnitFrom);

	public long getLandAreaValueTo();

	public void setLandAreaValueTo(long landareaValueTo);

	public String getLandAreaUnitTo();

	public void setLandAreaUnitTo(String landareaUnitTo);

	public long getBuildupAreaValueFrom();

	public void setBuildupAreaValueFrom(long buildupareaValueFrom);

	public String getBuildupAreaUnitFrom();

	public void setBuildupAreaUnitFrom(String buildupareaUnitFrom);

	public long getBuildupAreaValueTo();

	public void setBuildupAreaValueTo(long buildupAreaValueTo);

	public String getBuildupAreaUnitTo();

	public void setBuildupAreaUnitTo(String buildupAreaUnitTo);

	public double getMinimumCurrentOmv();

	public void setMinimumCurrentOmv(double minimumCurrentOmv);

	public String getOmvType();

	public void setOmvType(String omvType);

	public long getVariationOMV();

	public void setVariationOMV(long variationOMV);

	public String getValuationDescription();

	public void setValuationDescription(String valuationDescription);

}
