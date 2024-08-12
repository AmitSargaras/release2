/**
 * Fcubsbodyreq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class Fcubsbodyreq  implements java.io.Serializable {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO;

    public Fcubsbodyreq() {
    }

    public Fcubsbodyreq(
           com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO) {
           this.liabilityIO = liabilityIO;
    }


    /**
     * Gets the liabilityIO value for this Fcubsbodyreq.
     * 
     * @return liabilityIO
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType getLiabilityIO() {
        return liabilityIO;
    }


    /**
     * Sets the liabilityIO value for this Fcubsbodyreq.
     * 
     * @param liabilityIO
     */
    public void setLiabilityIO(com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO) {
        this.liabilityIO = liabilityIO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Fcubsbodyreq)) return false;
        Fcubsbodyreq other = (Fcubsbodyreq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.liabilityIO==null && other.getLiabilityIO()==null) || 
             (this.liabilityIO!=null &&
              this.liabilityIO.equals(other.getLiabilityIO())));
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
        if (getLiabilityIO() != null) {
            _hashCode += getLiabilityIO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Fcubsbodyreq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsbodyreq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "LiabilityDescription-PK-Type"));
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
