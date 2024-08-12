package com.integrosys.cms.app.chktemplate.bus;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 7, 2008
 * Time: 6:17:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDynamicProperty extends Serializable {

    public long getPropertyID();

    public void setPropertyID(long propertyID);

    public long getPropertySetupID();

    public void setPropertySetupID(long propertySetupID);

    public String getPropertyCategory();

    public void setPropertyCategory(String category);

    public String getPropertyValue();

    public void setPropertyValue(String propertyValue);

    public long getReferenceID();

    public void setReferenceID(long referenceID);

    public String getStatus();

    public void setStatus(String status);

}
