package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;

/**
 * Title: CLIMS
 * Description: Interface for Property Index Security SubType
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */

public interface IPropertyIdxSecSubType extends Serializable {
    /**
     * <!-- begin-user-doc --> CMP Field propertyIdxSecSubTypeId Returns the propertyIdxSecSubTypeId
     *
     * @return the propertyIdxSecSubTypeId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public long getPropertyIdxSecSubTypeId();

    /**
     * <!-- begin-user-doc --> Sets the propertyIdxSecSubTypeId
     *
     * @param long the new propertyIdxSecSubTypeId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setPropertyIdxSecSubTypeId(long propertyIdxSecSubTypeId);


    /**
     * <!-- begin-user-doc --> CMP Field securitySubTypeId Returns the securitySubTypeId
     *
     * @return the securitySubTypeId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public java.lang.String getSecuritySubTypeId();

    /**
     * <!-- begin-user-doc --> Sets the securitySubTypeId
     *
     * @param java.lang.String the new securitySubTypeId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setSecuritySubTypeId(java.lang.String securitySubTypeId);

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

    public long getPropertyIdxId();

    public void setPropertyIdxId(long propertyIdxId);

}