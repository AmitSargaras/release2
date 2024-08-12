package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:43:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostFacilityType extends ISynchronizer {

    public String getFacilityCode();

    public void setFacilityCode(String facilityCode);

    public String getLoanType();

    public void setLoanType(String loanType);

    public String getCurrency();

    public void setCurrency(String currency);

    public String getRevolvingIndicator();

    public void setRevolvingIndicator(String revolvingIndicator);

    public String getRevOsBalOrgamt();

    public void setRevOsBalOrgamt(String revOsBalOrgamt);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);

    public String getDescription();

    public void setDescription(String description);

    public String getAccountType();

    public void setAccountType(String accountType);
}
