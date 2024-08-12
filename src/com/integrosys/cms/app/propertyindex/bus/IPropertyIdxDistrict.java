package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;

/**
 * Title: CLIMS
 * Description: Interface for Property Index District
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 15, 2008
 */

public interface IPropertyIdxDistrict extends Serializable {
    /**
     * <!-- begin-user-doc --> CMP Field propertyIdxDistrictCodeId Returns the propertyIdxDistrictCodeId
     *
     * @return the propertyIdxDistrictCodeId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public long getPropertyIdxDistrictCodeId();

    /**
     * <!-- begin-user-doc --> Sets the propertyIdxDistrictCodeId
     *
     * @param java.lang.Long the new propertyIdxDistrictCodeId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setPropertyIdxDistrictCodeId(long propertyIdxDistrictCodeId);

    /**
     * <!-- begin-user-doc --> CMP Field districtCode Returns the districtCode
     *
     * @return the districtCode <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public java.lang.String getDistrictCode();

    /**
     * <!-- begin-user-doc --> Sets the districtCode
     *
     * @param java.lang.String the new districtCode value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setDistrictCode(java.lang.String districtCode);

    /**
     * <!-- begin-user-doc --> CMP Field status Returns the status
     *
     * @return the status <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public java.lang.String getStatus();

    /**
     * <!-- begin-user-doc --> Sets the status
     *
     * @param java.lang.String the new status value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setStatus(java.lang.String status);

    public long getPropertyIdxItemId();

    public void setPropertyIdxItemId(long propertyIdxItemId);

}
