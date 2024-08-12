package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author priya
 */
public class OBInternalCreditRating implements IInternalCreditRating {

    private static final long serialVersionUID = 1L;

    private Long intCredRatId = new Long(ICMSConstant.LONG_INVALID_VALUE);
    private long versionTime = ICMSConstant.LONG_INVALID_VALUE;
    private String intCredRatCode;
    private String intCredRatLmtAmtCurCode;
    private double intCredRatLmtAmt = ICMSConstant.DOUBLE_INVALID_VALUE;
    private long groupId = ICMSConstant.LONG_INVALID_VALUE;
    private long refId = ICMSConstant.LONG_INVALID_VALUE;
    private String status;

    public OBInternalCreditRating() {
        super();
    }

    public OBInternalCreditRating(IInternalCreditRating obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBInternalCreditRating))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

    public Long getIntCredRatId() {
        return intCredRatId;
    }

    public void setIntCredRatId(Long intCredRatId) {
        this.intCredRatId = intCredRatId;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }

    public String getIntCredRatCode() {
        return intCredRatCode;
    }

    public void setIntCredRatCode(String intCredRatCode) {
        this.intCredRatCode = intCredRatCode;
    }

    public String getIntCredRatLmtAmtCurCode() {
        return intCredRatLmtAmtCurCode;
    }

    public void setIntCredRatLmtAmtCurCode(String intCredRatLmtAmtCurCode) {
        this.intCredRatLmtAmtCurCode = intCredRatLmtAmtCurCode;
    }

    public double getIntCredRatLmtAmt() {
        return intCredRatLmtAmt;
    }

    public void setIntCredRatLmtAmt(double intCredRatLmtAmt) {
        this.intCredRatLmtAmt = intCredRatLmtAmt;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
