package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 5:18:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostLawyer implements IHostLawyer {

    private static final String[] MATCHING_PROPERTIES = new String[] { "lawyerCode" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "lawyerCode", "lastUpdatedDate" };

    private String lawyerCode;
    private String lawyerType;
    private String lawyerReferenceNumber;
    private String lawyerName;
    private String status;
    private String remarks;
    private Date lastUpdatedDate;


    public String getLawyerCode() {
        return lawyerCode;
    }

    public void setLawyerCode(String lawyerCode) {
        this.lawyerCode = lawyerCode;
    }

    public String getLawyerType() {
        return lawyerType;
    }

    public void setLawyerType(String lawyerType) {
        this.lawyerType = lawyerType;
    }

    public String getLawyerReferenceNumber() {
        return lawyerReferenceNumber;
    }

    public void setLawyerReferenceNumber(String lawyerReferenceNumber) {
        this.lawyerReferenceNumber = lawyerReferenceNumber;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        setLastUpdatedDate(new Date());       //not required to update status since status field is from remote host
        
        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        setStatus(IHostLawyer.STATUS_DELETED);
        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getLawyerCode() != null){
            setLawyerCode(getLawyerCode().trim());
        }
        if(getLawyerType() != null){
            setLawyerType(getLawyerType().trim());
        }
        if(getLawyerReferenceNumber() != null){
            setLawyerReferenceNumber(getLawyerReferenceNumber().trim());
        }
        if(getLawyerName() != null){
            setLawyerName(getLawyerName().trim());
        }
        if(getStatus() != null){
            setStatus(getStatus().trim());
        }
        if(getRemarks() != null){
            setRemarks(getRemarks().trim());
        }
    }

}
