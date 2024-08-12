/**
 * FacilityTenorRestriction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityTenorRestriction  implements java.io.Serializable {
    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal fromprv;

    private java.math.BigDecimal id;

    private java.lang.String liabbranh;

    private java.math.BigDecimal limit;

    private java.math.BigDecimal tenor;

    private java.lang.String tenormnemonic;

    private java.math.BigDecimal tonext;

    private java.math.BigDecimal utilisation;

    public FacilityTenorRestriction() {
    }

    public FacilityTenorRestriction(
           java.math.BigDecimal facilityid,
           java.math.BigDecimal fromprv,
           java.math.BigDecimal id,
           java.lang.String liabbranh,
           java.math.BigDecimal limit,
           java.math.BigDecimal tenor,
           java.lang.String tenormnemonic,
           java.math.BigDecimal tonext,
           java.math.BigDecimal utilisation) {
           this.facilityid = facilityid;
           this.fromprv = fromprv;
           this.id = id;
           this.liabbranh = liabbranh;
           this.limit = limit;
           this.tenor = tenor;
           this.tenormnemonic = tenormnemonic;
           this.tonext = tonext;
           this.utilisation = utilisation;
    }


    /**
     * Gets the facilityid value for this FacilityTenorRestriction.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityTenorRestriction.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the fromprv value for this FacilityTenorRestriction.
     * 
     * @return fromprv
     */
    public java.math.BigDecimal getFromprv() {
        return fromprv;
    }


    /**
     * Sets the fromprv value for this FacilityTenorRestriction.
     * 
     * @param fromprv
     */
    public void setFromprv(java.math.BigDecimal fromprv) {
        this.fromprv = fromprv;
    }


    /**
     * Gets the id value for this FacilityTenorRestriction.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this FacilityTenorRestriction.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the liabbranh value for this FacilityTenorRestriction.
     * 
     * @return liabbranh
     */
    public java.lang.String getLiabbranh() {
        return liabbranh;
    }


    /**
     * Sets the liabbranh value for this FacilityTenorRestriction.
     * 
     * @param liabbranh
     */
    public void setLiabbranh(java.lang.String liabbranh) {
        this.liabbranh = liabbranh;
    }


    /**
     * Gets the limit value for this FacilityTenorRestriction.
     * 
     * @return limit
     */
    public java.math.BigDecimal getLimit() {
        return limit;
    }


    /**
     * Sets the limit value for this FacilityTenorRestriction.
     * 
     * @param limit
     */
    public void setLimit(java.math.BigDecimal limit) {
        this.limit = limit;
    }


    /**
     * Gets the tenor value for this FacilityTenorRestriction.
     * 
     * @return tenor
     */
    public java.math.BigDecimal getTenor() {
        return tenor;
    }


    /**
     * Sets the tenor value for this FacilityTenorRestriction.
     * 
     * @param tenor
     */
    public void setTenor(java.math.BigDecimal tenor) {
        this.tenor = tenor;
    }


    /**
     * Gets the tenormnemonic value for this FacilityTenorRestriction.
     * 
     * @return tenormnemonic
     */
    public java.lang.String getTenormnemonic() {
        return tenormnemonic;
    }


    /**
     * Sets the tenormnemonic value for this FacilityTenorRestriction.
     * 
     * @param tenormnemonic
     */
    public void setTenormnemonic(java.lang.String tenormnemonic) {
        this.tenormnemonic = tenormnemonic;
    }


    /**
     * Gets the tonext value for this FacilityTenorRestriction.
     * 
     * @return tonext
     */
    public java.math.BigDecimal getTonext() {
        return tonext;
    }


    /**
     * Sets the tonext value for this FacilityTenorRestriction.
     * 
     * @param tonext
     */
    public void setTonext(java.math.BigDecimal tonext) {
        this.tonext = tonext;
    }


    /**
     * Gets the utilisation value for this FacilityTenorRestriction.
     * 
     * @return utilisation
     */
    public java.math.BigDecimal getUtilisation() {
        return utilisation;
    }


    /**
     * Sets the utilisation value for this FacilityTenorRestriction.
     * 
     * @param utilisation
     */
    public void setUtilisation(java.math.BigDecimal utilisation) {
        this.utilisation = utilisation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityTenorRestriction)) return false;
        FacilityTenorRestriction other = (FacilityTenorRestriction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.fromprv==null && other.getFromprv()==null) || 
             (this.fromprv!=null &&
              this.fromprv.equals(other.getFromprv()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.liabbranh==null && other.getLiabbranh()==null) || 
             (this.liabbranh!=null &&
              this.liabbranh.equals(other.getLiabbranh()))) &&
            ((this.limit==null && other.getLimit()==null) || 
             (this.limit!=null &&
              this.limit.equals(other.getLimit()))) &&
            ((this.tenor==null && other.getTenor()==null) || 
             (this.tenor!=null &&
              this.tenor.equals(other.getTenor()))) &&
            ((this.tenormnemonic==null && other.getTenormnemonic()==null) || 
             (this.tenormnemonic!=null &&
              this.tenormnemonic.equals(other.getTenormnemonic()))) &&
            ((this.tonext==null && other.getTonext()==null) || 
             (this.tonext!=null &&
              this.tonext.equals(other.getTonext()))) &&
            ((this.utilisation==null && other.getUtilisation()==null) || 
             (this.utilisation!=null &&
              this.utilisation.equals(other.getUtilisation())));
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
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getFromprv() != null) {
            _hashCode += getFromprv().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLiabbranh() != null) {
            _hashCode += getLiabbranh().hashCode();
        }
        if (getLimit() != null) {
            _hashCode += getLimit().hashCode();
        }
        if (getTenor() != null) {
            _hashCode += getTenor().hashCode();
        }
        if (getTenormnemonic() != null) {
            _hashCode += getTenormnemonic().hashCode();
        }
        if (getTonext() != null) {
            _hashCode += getTonext().hashCode();
        }
        if (getUtilisation() != null) {
            _hashCode += getUtilisation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityTenorRestriction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityTenorRestriction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromprv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fromprv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabbranh");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabbranh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tenor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenormnemonic");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tenormnemonic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tonext");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tonext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilisation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilisation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
