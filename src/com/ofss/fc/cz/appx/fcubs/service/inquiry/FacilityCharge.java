/**
 * FacilityCharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityCharge  implements java.io.Serializable {
    private java.math.BigDecimal adminchargeamt;

    private java.math.BigDecimal adminchargeperct;

    private java.lang.String adminchargetype;

    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal modno;

    private java.math.BigDecimal unutlchargeamt;

    private java.math.BigDecimal unutlchargeperct;

    private java.lang.String unutlchargetype;

    private java.math.BigDecimal utlchargeamt;

    private java.math.BigDecimal utlchargeperct;

    private java.lang.String utlchargetype;

    public FacilityCharge() {
    }

    public FacilityCharge(
           java.math.BigDecimal adminchargeamt,
           java.math.BigDecimal adminchargeperct,
           java.lang.String adminchargetype,
           java.math.BigDecimal facilityid,
           java.math.BigDecimal modno,
           java.math.BigDecimal unutlchargeamt,
           java.math.BigDecimal unutlchargeperct,
           java.lang.String unutlchargetype,
           java.math.BigDecimal utlchargeamt,
           java.math.BigDecimal utlchargeperct,
           java.lang.String utlchargetype) {
           this.adminchargeamt = adminchargeamt;
           this.adminchargeperct = adminchargeperct;
           this.adminchargetype = adminchargetype;
           this.facilityid = facilityid;
           this.modno = modno;
           this.unutlchargeamt = unutlchargeamt;
           this.unutlchargeperct = unutlchargeperct;
           this.unutlchargetype = unutlchargetype;
           this.utlchargeamt = utlchargeamt;
           this.utlchargeperct = utlchargeperct;
           this.utlchargetype = utlchargetype;
    }


    /**
     * Gets the adminchargeamt value for this FacilityCharge.
     * 
     * @return adminchargeamt
     */
    public java.math.BigDecimal getAdminchargeamt() {
        return adminchargeamt;
    }


    /**
     * Sets the adminchargeamt value for this FacilityCharge.
     * 
     * @param adminchargeamt
     */
    public void setAdminchargeamt(java.math.BigDecimal adminchargeamt) {
        this.adminchargeamt = adminchargeamt;
    }


    /**
     * Gets the adminchargeperct value for this FacilityCharge.
     * 
     * @return adminchargeperct
     */
    public java.math.BigDecimal getAdminchargeperct() {
        return adminchargeperct;
    }


    /**
     * Sets the adminchargeperct value for this FacilityCharge.
     * 
     * @param adminchargeperct
     */
    public void setAdminchargeperct(java.math.BigDecimal adminchargeperct) {
        this.adminchargeperct = adminchargeperct;
    }


    /**
     * Gets the adminchargetype value for this FacilityCharge.
     * 
     * @return adminchargetype
     */
    public java.lang.String getAdminchargetype() {
        return adminchargetype;
    }


    /**
     * Sets the adminchargetype value for this FacilityCharge.
     * 
     * @param adminchargetype
     */
    public void setAdminchargetype(java.lang.String adminchargetype) {
        this.adminchargetype = adminchargetype;
    }


    /**
     * Gets the facilityid value for this FacilityCharge.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityCharge.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the modno value for this FacilityCharge.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this FacilityCharge.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }


    /**
     * Gets the unutlchargeamt value for this FacilityCharge.
     * 
     * @return unutlchargeamt
     */
    public java.math.BigDecimal getUnutlchargeamt() {
        return unutlchargeamt;
    }


    /**
     * Sets the unutlchargeamt value for this FacilityCharge.
     * 
     * @param unutlchargeamt
     */
    public void setUnutlchargeamt(java.math.BigDecimal unutlchargeamt) {
        this.unutlchargeamt = unutlchargeamt;
    }


    /**
     * Gets the unutlchargeperct value for this FacilityCharge.
     * 
     * @return unutlchargeperct
     */
    public java.math.BigDecimal getUnutlchargeperct() {
        return unutlchargeperct;
    }


    /**
     * Sets the unutlchargeperct value for this FacilityCharge.
     * 
     * @param unutlchargeperct
     */
    public void setUnutlchargeperct(java.math.BigDecimal unutlchargeperct) {
        this.unutlchargeperct = unutlchargeperct;
    }


    /**
     * Gets the unutlchargetype value for this FacilityCharge.
     * 
     * @return unutlchargetype
     */
    public java.lang.String getUnutlchargetype() {
        return unutlchargetype;
    }


    /**
     * Sets the unutlchargetype value for this FacilityCharge.
     * 
     * @param unutlchargetype
     */
    public void setUnutlchargetype(java.lang.String unutlchargetype) {
        this.unutlchargetype = unutlchargetype;
    }


    /**
     * Gets the utlchargeamt value for this FacilityCharge.
     * 
     * @return utlchargeamt
     */
    public java.math.BigDecimal getUtlchargeamt() {
        return utlchargeamt;
    }


    /**
     * Sets the utlchargeamt value for this FacilityCharge.
     * 
     * @param utlchargeamt
     */
    public void setUtlchargeamt(java.math.BigDecimal utlchargeamt) {
        this.utlchargeamt = utlchargeamt;
    }


    /**
     * Gets the utlchargeperct value for this FacilityCharge.
     * 
     * @return utlchargeperct
     */
    public java.math.BigDecimal getUtlchargeperct() {
        return utlchargeperct;
    }


    /**
     * Sets the utlchargeperct value for this FacilityCharge.
     * 
     * @param utlchargeperct
     */
    public void setUtlchargeperct(java.math.BigDecimal utlchargeperct) {
        this.utlchargeperct = utlchargeperct;
    }


    /**
     * Gets the utlchargetype value for this FacilityCharge.
     * 
     * @return utlchargetype
     */
    public java.lang.String getUtlchargetype() {
        return utlchargetype;
    }


    /**
     * Sets the utlchargetype value for this FacilityCharge.
     * 
     * @param utlchargetype
     */
    public void setUtlchargetype(java.lang.String utlchargetype) {
        this.utlchargetype = utlchargetype;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityCharge)) return false;
        FacilityCharge other = (FacilityCharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adminchargeamt==null && other.getAdminchargeamt()==null) || 
             (this.adminchargeamt!=null &&
              this.adminchargeamt.equals(other.getAdminchargeamt()))) &&
            ((this.adminchargeperct==null && other.getAdminchargeperct()==null) || 
             (this.adminchargeperct!=null &&
              this.adminchargeperct.equals(other.getAdminchargeperct()))) &&
            ((this.adminchargetype==null && other.getAdminchargetype()==null) || 
             (this.adminchargetype!=null &&
              this.adminchargetype.equals(other.getAdminchargetype()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno()))) &&
            ((this.unutlchargeamt==null && other.getUnutlchargeamt()==null) || 
             (this.unutlchargeamt!=null &&
              this.unutlchargeamt.equals(other.getUnutlchargeamt()))) &&
            ((this.unutlchargeperct==null && other.getUnutlchargeperct()==null) || 
             (this.unutlchargeperct!=null &&
              this.unutlchargeperct.equals(other.getUnutlchargeperct()))) &&
            ((this.unutlchargetype==null && other.getUnutlchargetype()==null) || 
             (this.unutlchargetype!=null &&
              this.unutlchargetype.equals(other.getUnutlchargetype()))) &&
            ((this.utlchargeamt==null && other.getUtlchargeamt()==null) || 
             (this.utlchargeamt!=null &&
              this.utlchargeamt.equals(other.getUtlchargeamt()))) &&
            ((this.utlchargeperct==null && other.getUtlchargeperct()==null) || 
             (this.utlchargeperct!=null &&
              this.utlchargeperct.equals(other.getUtlchargeperct()))) &&
            ((this.utlchargetype==null && other.getUtlchargetype()==null) || 
             (this.utlchargetype!=null &&
              this.utlchargetype.equals(other.getUtlchargetype())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAdminchargeamt() != null) {
            _hashCode += getAdminchargeamt().hashCode();
        }
        if (getAdminchargeperct() != null) {
            _hashCode += getAdminchargeperct().hashCode();
        }
        if (getAdminchargetype() != null) {
            _hashCode += getAdminchargetype().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        if (getUnutlchargeamt() != null) {
            _hashCode += getUnutlchargeamt().hashCode();
        }
        if (getUnutlchargeperct() != null) {
            _hashCode += getUnutlchargeperct().hashCode();
        }
        if (getUnutlchargetype() != null) {
            _hashCode += getUnutlchargetype().hashCode();
        }
        if (getUtlchargeamt() != null) {
            _hashCode += getUtlchargeamt().hashCode();
        }
        if (getUtlchargeperct() != null) {
            _hashCode += getUtlchargeperct().hashCode();
        }
        if (getUtlchargetype() != null) {
            _hashCode += getUtlchargetype().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityCharge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCharge"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminchargeamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adminchargeamt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminchargeperct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adminchargeperct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminchargetype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adminchargetype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unutlchargeamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unutlchargeamt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unutlchargeperct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unutlchargeperct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unutlchargetype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unutlchargetype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utlchargeamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utlchargeamt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utlchargeperct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utlchargeperct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utlchargetype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utlchargetype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
