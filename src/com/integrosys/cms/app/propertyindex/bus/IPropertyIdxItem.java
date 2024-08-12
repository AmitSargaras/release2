package com.integrosys.cms.app.propertyindex.bus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS 
 * Description: Interface for Property Index Item
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 17, 2008
 */

public interface IPropertyIdxItem extends Serializable
{
   /**
    * <!-- begin-user-doc --> CMP Field propertyIdxItemId Returns the propertyIdxItemId
    * @return the propertyIdxItemId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getPropertyIdxItemId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the propertyIdxItemId
    * @param long the new propertyIdxItemId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setPropertyIdxItemId(long propertyIdxItemId ) ;

   /**
    * <!-- begin-user-doc --> CMP Field propertyIdxId Returns the propertyIdxId
    * @return the propertyIdxId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getPropertyIdxId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the propertyIdxId
    * @param long the new propertyIdxId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setPropertyIdxId(long propertyIdxId ) ;

   /**
    * <!-- begin-user-doc --> CMP Field idxYear Returns the idxYear
    * @return the idxYear <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.math.BigDecimal getIdxYear(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the idxYear
    * @param java.math.BigDecimal the new idxYear value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setIdxYear( java.math.BigDecimal idxYear ) ;

   /**
    * <!-- begin-user-doc --> CMP Field idxType Returns the idxType
    * @return the idxType <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getIdxType(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the idxType
    * @param java.lang.String the new idxType value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setIdxType( java.lang.String idxType ) ;

   /**
    * <!-- begin-user-doc --> CMP Field idxValue Returns the idxValue
    * @return the idxValue <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.math.BigDecimal getIdxValue(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the idxValue
    * @param java.math.BigDecimal the new idxValue value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setIdxValue( java.math.BigDecimal idxValue ) ;

    public java.lang.String getStateCode() ;

    public void setStateCode( java.lang.String stateCode ) ;

   /**
    * <!-- begin-user-doc --> CMP Field status Returns the status
    * @return the status <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getStatus(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the status
    * @param java.lang.String the new status value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setStatus( java.lang.String status ) ;

   /**
    * <!-- begin-user-doc --> CMP Field cmsRefId Returns the cmsRefId
    * @return the cmsRefId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getCmsRefId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the cmsRefId
    * @param long the new cmsRefId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setCmsRefId( long cmsRefId );

   public Set getPropertyTypeList();
   public void setPropertyTypeList(Set propertyTypeList);
   public Set getMukimList();
   public void setMukimList(Set mukimList);
   public Set getDistrictList();
   public void setDistrictList(Set districtList);
}