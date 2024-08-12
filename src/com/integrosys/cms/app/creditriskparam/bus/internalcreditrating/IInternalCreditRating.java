package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author priya
 */
public interface IInternalCreditRating extends Serializable, IValueObject {

    public Long getIntCredRatId();

    public void setIntCredRatId(Long intCredRatId);

    public long getVersionTime();

    public void setVersionTime(long versionTime);

    public String getIntCredRatCode();

    public void setIntCredRatCode(String intCredRatCode);

    public String getIntCredRatLmtAmtCurCode();

    public void setIntCredRatLmtAmtCurCode(String intCredRatLmtAmtCurCode);

    public double getIntCredRatLmtAmt();

    public void setIntCredRatLmtAmt(double intCredRatLmtAmt);

    public long getGroupId();

    public void setGroupId(long groupId);

    public long getRefId();

    public void setRefId(long refId);

    public String getStatus();

    public void setStatus(String status);

}
