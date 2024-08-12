/**
 * FacilityValueDateDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityValueDateDetails  implements java.io.Serializable {
    private java.lang.String bookdate;

    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal limitamount;

    private java.math.BigDecimal modno;

    private java.lang.String valuedate;

    public FacilityValueDateDetails() {
    }

    public FacilityValueDateDetails(
           java.lang.String bookdate,
           java.math.BigDecimal facilityid,
           java.math.BigDecimal limitamount,
           java.math.BigDecimal modno,
           java.lang.String valuedate) {
           this.bookdate = bookdate;
           this.facilityid = facilityid;
           this.limitamount = limitamount;
           this.modno = modno;
           this.valuedate = valuedate;
    }


    /**
     * Gets the bookdate value for this FacilityValueDateDetails.
     * 
     * @return bookdate
     */
    public java.lang.String getBookdate() {
        return bookdate;
    }


    /**
     * Sets the bookdate value for this FacilityValueDateDetails.
     * 
     * @param bookdate
     */
    public void setBookdate(java.lang.String bookdate) {
        this.bookdate = bookdate;
    }


    /**
     * Gets the facilityid value for this FacilityValueDateDetails.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityValueDateDetails.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the limitamount value for this FacilityValueDateDetails.
     * 
     * @return limitamount
     */
    public java.math.BigDecimal getLimitamount() {
        return limitamount;
    }


    /**
     * Sets the limitamount value for this FacilityValueDateDetails.
     * 
     * @param limitamount
     */
    public void setLimitamount(java.math.BigDecimal limitamount) {
        this.limitamount = limitamount;
    }


    /**
     * Gets the modno value for this FacilityValueDateDetails.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this FacilityValueDateDetails.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }


    /**
     * Gets the valuedate value for this FacilityValueDateDetails.
     * 
     * @return valuedate
     */
    public java.lang.String getValuedate() {
        return valuedate;
    }


    /**
     * Sets the valuedate value for this FacilityValueDateDetails.
     * 
     * @param valuedate
     */
    public void setValuedate(java.lang.String valuedate) {
        this.valuedate = valuedate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityValueDateDetails)) return false;
        FacilityValueDateDetails other = (FacilityValueDateDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bookdate==null && other.getBookdate()==null) || 
             (this.bookdate!=null &&
              this.bookdate.equals(other.getBookdate()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.limitamount==null && other.getLimitamount()==null) || 
             (this.limitamount!=null &&
              this.limitamount.equals(other.getLimitamount()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno()))) &&
            ((this.valuedate==null && other.getValuedate()==null) || 
             (this.valuedate!=null &&
              this.valuedate.equals(other.getValuedate())));
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
        if (getBookdate() != null) {
            _hashCode += getBookdate().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getLimitamount() != null) {
            _hashCode += getLimitamount().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        if (getValuedate() != null) {
            _hashCode += getValuedate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityValueDateDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityValueDateDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bookdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
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
        elemField.setFieldName("limitamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitamount"));
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
        elemField.setFieldName("valuedate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valuedate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
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
