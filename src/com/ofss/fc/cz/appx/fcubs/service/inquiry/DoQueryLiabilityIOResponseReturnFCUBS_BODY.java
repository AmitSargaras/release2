/**
 * DoQueryLiabilityIOResponseReturnFCUBS_BODY.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class DoQueryLiabilityIOResponseReturnFCUBS_BODY  implements java.io.Serializable {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionFullTypeDTO liabilityFull;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[][] fcubserrorresp;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType[] fcubswarningresp;

    public DoQueryLiabilityIOResponseReturnFCUBS_BODY() {
    }

    public DoQueryLiabilityIOResponseReturnFCUBS_BODY(
           com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionFullTypeDTO liabilityFull,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[][] fcubserrorresp,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType[] fcubswarningresp) {
           this.liabilityIO = liabilityIO;
           this.liabilityFull = liabilityFull;
           this.fcubserrorresp = fcubserrorresp;
           this.fcubswarningresp = fcubswarningresp;
    }


    /**
     * Gets the liabilityIO value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @return liabilityIO
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType getLiabilityIO() {
        return liabilityIO;
    }


    /**
     * Sets the liabilityIO value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @param liabilityIO
     */
    public void setLiabilityIO(com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionPKType liabilityIO) {
        this.liabilityIO = liabilityIO;
    }


    /**
     * Gets the liabilityFull value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @return liabilityFull
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionFullTypeDTO getLiabilityFull() {
        return liabilityFull;
    }


    /**
     * Sets the liabilityFull value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @param liabilityFull
     */
    public void setLiabilityFull(com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityDescriptionFullTypeDTO liabilityFull) {
        this.liabilityFull = liabilityFull;
    }


    /**
     * Gets the fcubserrorresp value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @return fcubserrorresp
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[][] getFcubserrorresp() {
        return fcubserrorresp;
    }


    /**
     * Sets the fcubserrorresp value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @param fcubserrorresp
     */
    public void setFcubserrorresp(com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[][] fcubserrorresp) {
        this.fcubserrorresp = fcubserrorresp;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[] getFcubserrorresp(int i) {
        return this.fcubserrorresp[i];
    }

    public void setFcubserrorresp(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[] _value) {
        this.fcubserrorresp[i] = _value;
    }


    /**
     * Gets the fcubswarningresp value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @return fcubswarningresp
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType[] getFcubswarningresp() {
        return fcubswarningresp;
    }


    /**
     * Sets the fcubswarningresp value for this DoQueryLiabilityIOResponseReturnFCUBS_BODY.
     * 
     * @param fcubswarningresp
     */
    public void setFcubswarningresp(com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType[] fcubswarningresp) {
        this.fcubswarningresp = fcubswarningresp;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType getFcubswarningresp(int i) {
        return this.fcubswarningresp[i];
    }

    public void setFcubswarningresp(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType _value) {
        this.fcubswarningresp[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DoQueryLiabilityIOResponseReturnFCUBS_BODY)) return false;
        DoQueryLiabilityIOResponseReturnFCUBS_BODY other = (DoQueryLiabilityIOResponseReturnFCUBS_BODY) obj;
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
              this.liabilityIO.equals(other.getLiabilityIO()))) &&
            ((this.liabilityFull==null && other.getLiabilityFull()==null) || 
             (this.liabilityFull!=null &&
              this.liabilityFull.equals(other.getLiabilityFull()))) &&
            ((this.fcubserrorresp==null && other.getFcubserrorresp()==null) || 
             (this.fcubserrorresp!=null &&
              java.util.Arrays.equals(this.fcubserrorresp, other.getFcubserrorresp()))) &&
            ((this.fcubswarningresp==null && other.getFcubswarningresp()==null) || 
             (this.fcubswarningresp!=null &&
              java.util.Arrays.equals(this.fcubswarningresp, other.getFcubswarningresp())));
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
        if (getLiabilityFull() != null) {
            _hashCode += getLiabilityFull().hashCode();
        }
        if (getFcubserrorresp() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFcubserrorresp());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFcubserrorresp(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFcubswarningresp() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFcubswarningresp());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFcubswarningresp(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DoQueryLiabilityIOResponseReturnFCUBS_BODY.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">>doQueryLiabilityIOResponse>return>FCUBS_BODY"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityIO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityIO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "LiabilityDescription-PK-Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityFull");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityFull"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityDescriptionFullTypeDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fcubserrorresp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fcubserrorresp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "errorType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fcubswarningresp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fcubswarningresp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "warningType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
