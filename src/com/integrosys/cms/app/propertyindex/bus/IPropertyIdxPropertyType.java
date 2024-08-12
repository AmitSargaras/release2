package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;

/**
 * Title: CLIMS
 * Description: Interface for Property Index Type Of Property
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */

public interface IPropertyIdxPropertyType extends Serializable {

    public long getPropertyIdxPropertyTypeId();

    public void setPropertyIdxPropertyTypeId(long propertyIdxPropertyTypeId);

    public String getPropertyTypeId();

    public void setPropertyTypeId(String propertyTypeId);

    /**
     * <!-- begin-user-doc --> CMP Field status Returns the status
     *
     * @return the status <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     */
    public String getStatus();

    /**
     * <!-- begin-user-doc --> Sets the status
     *
     * @param java.lang.String the new status value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     */
    public void setStatus(String status);

    public long getPropertyIdxItemId();

    public void setPropertyIdxItemId(long propertyIdxItemId);
}
