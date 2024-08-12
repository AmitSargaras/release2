package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS
 * Description: Interface for Property Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 15, 2008
 */
public interface IPropertyIdx extends Serializable {

    public long getPropertyIdxId();

    public void setPropertyIdxId(long propertyIdxId);

    public java.lang.String getValDescr();

    public void setValDescr(java.lang.String valDescr);

    public java.lang.String getCountryCode();

    public void setCountryCode(String countryCode);

    public long getVersionTime();

    public void setVersionTime(long versionTime);

    public Set getPropertyIdxSecSubTypeList();

    public void setPropertyIdxSecSubTypeList(Set propertyIdxSecSubTypeList);

    public Set getPropertyIdxItemList();

    public void setPropertyIdxItemList(Set propertyIdxItemList);
    
    public String getStatus();
    
    public void setStatus(String status) ;
}
