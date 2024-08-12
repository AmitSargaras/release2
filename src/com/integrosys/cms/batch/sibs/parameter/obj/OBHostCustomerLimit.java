package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.base.techinfra.util.DateUtil;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 9:01:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostCustomerLimit implements IHostCustomerLimit {

    private static final String[] MATCHING_PROPERTIES = new String[] { "bankNumber" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "bankNumber", "status", "lastUpdatedDate" };

    private String bankNumber;
    private BigDecimal capitalFund;
    private Date lastMaintenanceDate;
    private String lastMaintenanceDateStr;
//    private BigDecimal isclSecuredLoan;
//    private BigDecimal isclUnsecuredLoan;
//    private BigDecimal securedIndividual;
//    private BigDecimal unsecuredIndividual;
    private String status;
    private Date lastUpdatedDate;


    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public BigDecimal getCapitalFund() {
        return capitalFund;
    }

    public void setCapitalFund(BigDecimal capitalFund) {
        this.capitalFund = capitalFund;
    }

    public Date getLastMaintenanceDate() {
        if (lastMaintenanceDate == null) {
            lastMaintenanceDate = parseJulianDate(getLastMaintenanceDateStr());
        }
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getLastMaintenanceDateStr() {
        return lastMaintenanceDateStr;
    }

    public void setLastMaintenanceDateStr(String lastMaintenanceDateStr) {
        if (lastMaintenanceDateStr != null) {
            lastMaintenanceDateStr = lastMaintenanceDateStr.trim();
        }
        this.lastMaintenanceDateStr = lastMaintenanceDateStr;
    }

//    public BigDecimal getIsclSecuredLoan() {
//        return isclSecuredLoan;
//    }
//
//    public void setIsclSecuredLoan(BigDecimal isclSecuredLoan) {
//        this.isclSecuredLoan = isclSecuredLoan;
//    }
//
//    public BigDecimal getIsclUnsecuredLoan() {
//        return isclUnsecuredLoan;
//    }
//
//    public void setIsclUnsecuredLoan(BigDecimal isclUnsecuredLoan) {
//        this.isclUnsecuredLoan = isclUnsecuredLoan;
//    }
//
//    public BigDecimal getSecuredIndividual() {
//        return securedIndividual;
//    }
//
//    public void setSecuredIndividual(BigDecimal securedIndividual) {
//        this.securedIndividual = securedIndividual;
//    }
//
//    public BigDecimal getUnsecuredIndividual() {
//        return unsecuredIndividual;
//    }
//
//    public void setUnsecuredIndividual(BigDecimal unsecuredIndividual) {
//        this.unsecuredIndividual = unsecuredIndividual;
//    }

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
        if(getBankNumber() != null){
            setBankNumber(getBankNumber().trim());
        }
    }

    private Date parseJulianDate (String dateStr) {
        Date parsedDate = null;
        if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
            parsedDate = DateUtil.parseDate("yyyyDDD",dateStr);
        }

        return parsedDate;
    }
    
}
