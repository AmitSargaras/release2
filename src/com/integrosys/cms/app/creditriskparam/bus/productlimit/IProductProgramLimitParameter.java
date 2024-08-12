package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface IProductProgramLimitParameter extends Serializable, IValueObject {

    public Long getId();
    public void setId(Long id);

    public String getProductProgramDesc();
    public void setProductProgramDesc(String productProgramDesc);
	
	public String getReferenceCode();
    public void setReferenceCode(String referenceCode);

    public Amount getLimitAmount();
    public void setLimitAmount(Amount limitAmount);

    public String getStatus();
    public void setStatus(String status);
    
    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public long getCmsRefId();
	public void setCmsRefId(long cmsRefId);
    
    public Collection getProductTypeList();
	public void setProductTypeList(Collection productTypeList);

    public Set getProductTypeSet();
    public void setProductTypeSet(Set productTypeSet);
}
