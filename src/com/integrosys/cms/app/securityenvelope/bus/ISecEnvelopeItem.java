package com.integrosys.cms.app.securityenvelope.bus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Title: CLIMS 
 * Description: Interface for Security Envelope Item
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong 
 * Date: Jan 25, 2010
 */

public interface ISecEnvelopeItem extends Serializable
{
   /**
    * <!-- begin-user-doc --> CMP Field secEnvelopeItemId Returns the secEnvelopeItemId
    * @return the secEnvelopeItemId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getSecEnvelopeItemId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeItemId
    * @param long the new secEnvelopeItemId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeItemId(long secEnvelopeItemId ) ;

   /**
    * <!-- begin-user-doc --> CMP Field secEnvelopeId Returns the secEnvelopeId
    * @return the secEnvelopeId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getSecEnvelopeId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeId
    * @param long the new secEnvelopeId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeId(long secEnvelopeId ) ;

   /**
    * <!-- begin-user-doc --> CMP Field secEnvelopeItemAddr Returns the secEnvelopeItemAddr
    * @return the secEnvelopeItemAddr <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getSecEnvelopeItemAddr(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeItemAddr
    * @param java.lang.String the new secEnvelopeItemAddr value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeItemAddr( java.lang.String secEnvelopeItemAddr ) ;

	/**
    * <!-- begin-user-doc --> CMP Field secEnvelopeItemCab Returns the secEnvelopeItemCab
    * @return the secEnvelopeItemCab <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getSecEnvelopeItemCab(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeItemCab
    * @param java.lang.String the new secEnvelopeItemCab value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeItemCab( java.lang.String secEnvelopeItemCab ) ;

	 /**
    * <!-- begin-user-doc --> CMP Field secEnvelopeItemDrw Returns the secEnvelopeItemDrw
    * @return the secEnvelopeItemDrw <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getSecEnvelopeItemDrw(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeItemDrw
    * @param java.lang.String the new secEnvelopeItemDrw value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeItemDrw( java.lang.String secEnvelopeItemDrw ) ;

  /**
    * <!-- begin-user-doc --> CMP Field secEnvelopeItemBarcode Returns the secEnvelopeItemBarcode
    * @return the secEnvelopeItemBarcode <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public java.lang.String getSecEnvelopeItemBarcode(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeItemBarcode
    * @param java.lang.String the new secEnvelopeItemBarcode value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeItemBarcode( java.lang.String secEnvelopeItemBarcode ) ;

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
    * <!-- begin-user-doc --> CMP Field secEnvelopeRefId Returns the secEnvelopeRefId
    * @return the secEnvelopeRefId <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public long getSecEnvelopeRefId(  ) ;

   /**
    * <!-- begin-user-doc --> Sets the secEnvelopeRefId
    * @param long the new secEnvelopeRefId value <!-- end-user-doc --> <!-- begin-xdoclet-definition -->
    * @generated     */
   public void setSecEnvelopeRefId( long secEnvelopeRefId );

}