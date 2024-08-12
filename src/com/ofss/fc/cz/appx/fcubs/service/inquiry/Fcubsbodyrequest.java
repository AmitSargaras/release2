/**
 * Fcubsbodyrequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class Fcubsbodyrequest  implements java.io.Serializable {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO facilityIO;

    public Fcubsbodyrequest() {
    }

    public Fcubsbodyrequest(
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO facilityIO) {
           this.facilityIO = facilityIO;
    }


    /**
     * Gets the facilityIO value for this Fcubsbodyrequest.
     * 
     * @return facilityIO
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO getFacilityIO() {
        return facilityIO;
    }


    /**
     * Sets the facilityIO value for this Fcubsbodyrequest.
     * 
     * @param facilityIO
     */
    public void setFacilityIO(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO facilityIO) {
        this.facilityIO = facilityIO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Fcubsbodyrequest)) return false;
        Fcubsbodyrequest other = (Fcubsbodyrequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.facilityIO==null && other.getFacilityIO()==null) || 
             (this.facilityIO!=null &&
              this.facilityIO.equals(other.getFacilityIO())));
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
        if (getFacilityIO() != null) {
            _hashCode += getFacilityIO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Fcubsbodyrequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsbodyrequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityDescriptionPKTypeDTO"));
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
