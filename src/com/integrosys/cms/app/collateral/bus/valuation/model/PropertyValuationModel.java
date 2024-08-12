package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.valuation.support.LandAreaMeasure;

import java.util.Date;

/**
 * Modified with IntelliJ IDEA.
 * User: Andy Wong
 * Date: Dec 3, 2008
 * Time: 10:31:05 AM
 *
 * Amendment: add new category land use field for EONCMS req
 */
public class PropertyValuationModel extends GenericValuationModel {
	private String postCode;

	private String stateCode;

	private String districtCode;

	private String mukimCode;

	private LandAreaMeasure landArea;

	private LandAreaMeasure buildupArea;

	private Amount currentOMV;

    //used in property index
	private Amount spValue;
	private Date spDate;
	private String propertyType;
    //Andy Wong, 3 Dec 2008: required in EONCMS for showing types of property as land use category
    private String categoryOfLandUse;

    //this is not used in the calculation, but its a pre-condition that needs to be fulfilled
    private String propertyCompletionStatus;


    public PropertyValuationModel() {
		super();
	}

	/**
	 * @return Returns the buildupArea.
	 */
	public LandAreaMeasure getBuildupArea() {
		return buildupArea;
	}

	/**
	 * @param buildupArea The buildupArea to set.
	 */
	public void setBuildupArea(LandAreaMeasure buildupArea) {
		this.buildupArea = buildupArea;
	}

	/**
	 * @return Returns the currentOMV.
	 */
	public Amount getCurrentOMV() {
		return currentOMV;
	}

	/**
	 * @param currentOMV The currentOMV to set.
	 */
	public void setCurrentOMV(Amount currentOMV) {
		this.currentOMV = currentOMV;
	}

	/**
	 * @return Returns the districtCode.
	 */
	public String getDistrictCode() {
		return districtCode;
	}

	/**
	 * @param districtCode The districtCode to set.
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * @return Returns the landArea.
	 */
	public LandAreaMeasure getLandArea() {
		return landArea;
	}

	/**
	 * @param landArea The landArea to set.
	 */
	public void setLandArea(LandAreaMeasure landArea) {
		this.landArea = landArea;
	}

	/**
	 * @return Returns the mukimCode.
	 */
	public String getMukimCode() {
		return mukimCode;
	}

	/**
	 * @param mukimCode The mukimCode to set.
	 */
	public void setMukimCode(String mukimCode) {
		this.mukimCode = mukimCode;
	}

	/**
	 * @return Returns the postCode.
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode The postCode to set.
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return Returns the stateCode.
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode The stateCode to set.
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

    public Amount getSpValue() {
        return spValue;
    }

    public void setSpValue(Amount spValue) {
        this.spValue = spValue;
    }

    public Date getSpDate() {
        return spDate;
    }

    public void setSpDate(Date spDate) {
        this.spDate = spDate;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }


    public String getPropertyCompletionStatus() {
        return propertyCompletionStatus;
    }

    public void setPropertyCompletionStatus(String propertyCompletionStatus) {
        this.propertyCompletionStatus = propertyCompletionStatus;
    }

    public String getCategoryOfLandUse() {
        return categoryOfLandUse;
    }

    public void setCategoryOfLandUse(String categoryOfLandUse) {
        this.categoryOfLandUse = categoryOfLandUse;
    }
}
