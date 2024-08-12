package com.integrosys.cms.batch.sibs.parameter.obj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 4:10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostProductPackage extends ISynchronizer {


    public String getPackageCode();

    public void setPackageCode(String packageCode);

    public String getDescription();

    public void setDescription(String description);

    public Date getExpiryDate();

    public void setExpiryDate(Date expiryDate);

    public String getExpiryDateStr();

    public void setExpiryDateStr(String expiryDateStr);

    public Date getEffectiveDate();

    public void setEffectiveDate(Date effectiveDate);

    public String getEffectiveDateStr();

    public void setEffectiveDateStr(String effectiveDateStr);

    public BigDecimal getFundAllocated();

    public void setFundAllocated(BigDecimal fundAllocated);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);
}