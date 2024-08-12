package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;

/**
 * Title: CLIMS
 * Description: Interface for Property Index Mukim
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */

public interface IPropertyIdxMukim extends Serializable {
    /**
     * <!-- begin-user-doc --> CMP Field propertyIdxMukimCodeId Returns the propertyIdxMukimCodeId
     *
     * @return the propertyIdxMukimCodeId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public long getPropertyIdxMukimCodeId();

    /**
     * <!-- begin-user-doc --> Sets the propertyIdxMukimCodeId
     *
     * @param long the new propertyIdxMukimCodeId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setPropertyIdxMukimCodeId(long propertyIdxMukimCodeId);

    /**
     * <!-- begin-user-doc --> CMP Field mukim_code Returns the mukim_code
     *
     * @return the mukim_code <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public java.lang.String getMukimCode();

    /**
     * <!-- begin-user-doc --> Sets the mukim_code
     *
     * @param java.lang.String the new mukim_code value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
     * @generated
     */
    public void setMukimCode(java.lang.String mukim_code);

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

    public long getPropertyItemIdxId();

    public void setPropertyItemIdxId(long propertyItemIdxId);
}
