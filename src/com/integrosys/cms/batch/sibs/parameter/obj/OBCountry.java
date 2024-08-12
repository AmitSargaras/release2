package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 2:53:00 PM
 *
 */
public class OBCountry implements ICountry {

    private static final String[] MATCHING_PROPERTIES = new String[] { "countryCode" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "countryID", "updateStatusIndicator" , "updateDate" };

    private long countryID = ICMSConstant.LONG_INVALID_VALUE;
    private String countryCode;
    private String countryName;
    private Date updateDate;
    private String updateStatusIndicator;


    public long getCountryID() {
        return countryID;
    }

    public void setCountryID(long countryID) {
        this.countryID = countryID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateStatusIndicator() {
        return updateStatusIndicator;
    }

    public void setUpdateStatusIndicator(String updateStatusIndicator) {
        this.updateStatusIndicator = updateStatusIndicator;
    }

    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }


    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setUpdateStatusIndicator((updateStatusIndicator == null) ? "I" : "U");
        setUpdateDate(new Date());

        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        setUpdateStatusIndicator("D");

        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getCountryCode() != null){
            setCountryCode(getCountryCode().trim());
        }
        if(getCountryName() != null){
            setCountryName(getCountryName().trim());
        }
    }
}
