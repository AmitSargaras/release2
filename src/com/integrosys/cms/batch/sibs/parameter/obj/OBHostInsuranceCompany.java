package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 5:25:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostInsuranceCompany implements IHostInsuranceCompany {

    private static final String[] MATCHING_PROPERTIES = new String[] { "insNumber" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "insNumber", "status", "lastUpdatedDate" };

    private String insNumber;
    private String insName;
    private String insAddress;
    private String insCityState;
    private String insPhone;
    private BigDecimal reservePercent;
    private String panelIndicator;
    private String status;
    private Date lastUpdatedDate;


    public String getInsNumber() {
        return insNumber;
    }

    public void setInsNumber(String insNumber) {
        this.insNumber = insNumber;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getInsAddress() {
        return insAddress;
    }

    public void setInsAddress(String insAddress) {
        this.insAddress = insAddress;
    }

    public String getInsCityState() {
        return insCityState;
    }

    public void setInsCityState(String insCityState) {
        this.insCityState = insCityState;
    }

    public String getInsPhone() {
        return insPhone;
    }

    public void setInsPhone(String insPhone) {
        this.insPhone = insPhone;
    }

    public BigDecimal getReservePercent() {
        return reservePercent;
    }

    public void setReservePercent(BigDecimal reservePercent) {
        this.reservePercent = reservePercent;
    }

    public String getPanelIndicator() {
        return panelIndicator;
    }

    public void setPanelIndicator(String panelIndicator) {
        this.panelIndicator = panelIndicator;
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
        if(getInsNumber() != null){
            setInsNumber(getInsNumber().trim());
        }
        if(getInsName() != null){
            setInsName(getInsName().trim());
        }
        if(getInsAddress() != null){
            setInsAddress(getInsAddress().trim());
        }
        if(getInsCityState() != null){
            setInsCityState(getInsCityState().trim());
        }
        if(getInsPhone() != null){
            setInsPhone(getInsPhone().trim());
        }
        if(getPanelIndicator() != null){
            setPanelIndicator(getPanelIndicator().trim());
        }
    }

}
