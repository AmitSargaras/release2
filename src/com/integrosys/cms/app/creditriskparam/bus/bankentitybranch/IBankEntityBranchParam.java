package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * Interface for BankEntityBranchParam.
 *
 * @author Andy Wong
 * @generated
 * @wtp generated
 */
public interface IBankEntityBranchParam extends Serializable, IValueObject {

    public Long getBankEntityId();

    public void setBankEntityId(Long bankEntityId);

    public Long getGroupId();

    public void setGroupId(Long groupId);

    public String getEntityType();

    public void setEntityType(String entityType);

    public String getBranchCode();

    public void setBranchCode(String branchCode);

    public String getBranchCodeSrc();

    public void setBranchCodeSrc(String branchCodeSrc);

    public String getStatus();

    public void setStatus(String status);

    public long getVersionTime();

    public void setVersionTime(long versionTime);

    public long getCmsRefId();

    public void setCmsRefId(long cmsRefId);

    public String getEntityTypeDesc();

    public void setEntityTypeDesc(String entityType);
}
