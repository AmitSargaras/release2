/**
 * Dictionary.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain.common.dto;

public class Dictionary  implements java.io.Serializable {
    private java.lang.String fullyQualifiedClassName;

    private com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] nameValuePairDTOArray;

    public Dictionary() {
    }

    public Dictionary(
           java.lang.String fullyQualifiedClassName,
           com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] nameValuePairDTOArray) {
           this.fullyQualifiedClassName = fullyQualifiedClassName;
           this.nameValuePairDTOArray = nameValuePairDTOArray;
    }


    /**
     * Gets the fullyQualifiedClassName value for this Dictionary.
     * 
     * @return fullyQualifiedClassName
     */
    public java.lang.String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }


    /**
     * Sets the fullyQualifiedClassName value for this Dictionary.
     * 
     * @param fullyQualifiedClassName
     */
    public void setFullyQualifiedClassName(java.lang.String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }


    /**
     * Gets the nameValuePairDTOArray value for this Dictionary.
     * 
     * @return nameValuePairDTOArray
     */
    public com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] getNameValuePairDTOArray() {
        return nameValuePairDTOArray;
    }


    /**
     * Sets the nameValuePairDTOArray value for this Dictionary.
     * 
     * @param nameValuePairDTOArray
     */
    public void setNameValuePairDTOArray(com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] nameValuePairDTOArray) {
        this.nameValuePairDTOArray = nameValuePairDTOArray;
    }

    public com.ofss.fc.framework.domain.common.dto.NameValuePairDTO getNameValuePairDTOArray(int i) {
        return this.nameValuePairDTOArray[i];
    }

    public void setNameValuePairDTOArray(int i, com.ofss.fc.framework.domain.common.dto.NameValuePairDTO _value) {
        this.nameValuePairDTOArray[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Dictionary)) return false;
        Dictionary other = (Dictionary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fullyQualifiedClassName==null && other.getFullyQualifiedClassName()==null) || 
             (this.fullyQualifiedClassName!=null &&
              this.fullyQualifiedClassName.equals(other.getFullyQualifiedClassName()))) &&
            ((this.nameValuePairDTOArray==null && other.getNameValuePairDTOArray()==null) || 
             (this.nameValuePairDTOArray!=null &&
              java.util.Arrays.equals(this.nameValuePairDTOArray, other.getNameValuePairDTOArray())));
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
        if (getFullyQualifiedClassName() != null) {
            _hashCode += getFullyQualifiedClassName().hashCode();
        }
        if (getNameValuePairDTOArray() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNameValuePairDTOArray());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNameValuePairDTOArray(), i);
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
        new org.apache.axis.description.TypeDesc(Dictionary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dictionary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullyQualifiedClassName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "fullyQualifiedClassName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameValuePairDTOArray");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "nameValuePairDTOArray"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "nameValuePairDTO"));
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
