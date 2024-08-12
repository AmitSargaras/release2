package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:38:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostFacilityType implements IHostFacilityType {

    private static final String[] MATCHING_PROPERTIES = new String[] { "facilityCode" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "facilityCode", "status" , "lastUpdatedDate" };

    private String facilityCode;
    private String loanType;
    private String currency;
    private String revolvingIndicator;
    private String revOsBalOrgamt;
    private String status;
    private Date lastUpdatedDate;
    private String description;
    private String accountType;


    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRevolvingIndicator() {
        return revolvingIndicator;
    }

    public void setRevolvingIndicator(String revolvingIndicator) {
        this.revolvingIndicator = revolvingIndicator;
    }

    public String getRevOsBalOrgamt() {
        return revOsBalOrgamt;
    }

    public void setRevOsBalOrgamt(String revOsBalOrgamt) {
        this.revOsBalOrgamt = revOsBalOrgamt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setStatus(ICMSConstant.STATE_ACTIVE);
        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        setStatus(ICMSConstant.STATE_DELETED);
        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getFacilityCode() != null){
            setFacilityCode(getFacilityCode().trim());
        }
        if(getLoanType() != null){
            setLoanType(getLoanType().trim());
        }
        if(getCurrency() != null){
            setCurrency(getCurrency().trim());
        }
        if(getRevolvingIndicator() != null){
            setRevolvingIndicator(getRevolvingIndicator().trim());
        }
        if(getRevOsBalOrgamt() != null){
            setRevOsBalOrgamt(getRevOsBalOrgamt().trim());
        }
        if(getDescription() != null){
            setDescription(getDescription().trim());
        }
        if(getAccountType() != null){
            setAccountType(getAccountType().trim());
        }
    }

}
