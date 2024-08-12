package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 9:05:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostCustomerLimit extends ISynchronizer {
    public String getBankNumber();

    public void setBankNumber(String bankNumber);

    public Date getLastMaintenanceDate();

    public void setLastMaintenanceDate(Date lastMaintenanceDate);

    public String getLastMaintenanceDateStr();

    public void setLastMaintenanceDateStr(String lastMaintenanceDateStr);    

//    public BigDecimal getIsclSecuredLoan();
//
//    public void setIsclSecuredLoan(BigDecimal isclSecuredLoan);
//
//    public BigDecimal getIsclUnsecuredLoan();
//
//    public void setIsclUnsecuredLoan(BigDecimal isclUnsecuredLoan);
//
//    public BigDecimal getSecuredIndividual();
//
//    public void setSecuredIndividual(BigDecimal securedIndividual);
//
//    public BigDecimal getUnsecuredIndividual();
//
//    public void setUnsecuredIndividual(BigDecimal unsecuredIndividual);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);
}
