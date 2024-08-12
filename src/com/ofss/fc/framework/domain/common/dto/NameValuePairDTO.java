/**
 * NameValuePairDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain.common.dto;

public class NameValuePairDTO  implements java.io.Serializable {
	
    private java.lang.String datatype;

    private java.lang.String genericName;

    private java.lang.String name;

    private java.lang.String value;

    public NameValuePairDTO() {
    }

    public NameValuePairDTO(
           java.lang.String datatype,
           java.lang.String genericName,
           java.lang.String name,
           java.lang.String value) {
           this.datatype = datatype;
           this.genericName = genericName;
           this.name = name;
           this.value = value;
    }


    /**
     * Gets the datatype value for this NameValuePairDTO.
     * 
     * @return datatype
     */
    public java.lang.String getDatatype() {
        return datatype;
    }


    /**
     * Sets the datatype value for this NameValuePairDTO.
     * 
     * @param datatype
     */
    public void setDatatype(java.lang.String datatype) {
        this.datatype = datatype;
    }


    /**
     * Gets the genericName value for this NameValuePairDTO.
     * 
     * @return genericName
     */
    public java.lang.String getGenericName() {
        return genericName;
    }


    /**
     * Sets the genericName value for this NameValuePairDTO.
     * 
     * @param genericName
     */
    public void setGenericName(java.lang.String genericName) {
        this.genericName = genericName;
    }


    /**
     * Gets the name value for this NameValuePairDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this NameValuePairDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the value value for this NameValuePairDTO.
     * 
     * @return value
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this NameValuePairDTO.
     * 
     * @param value
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NameValuePairDTO)) return false;
        NameValuePairDTO other = (NameValuePairDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datatype==null && other.getDatatype()==null) || 
             (this.datatype!=null &&
              this.datatype.equals(other.getDatatype()))) &&
            ((this.genericName==null && other.getGenericName()==null) || 
             (this.genericName!=null &&
              this.genericName.equals(other.getGenericName()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        if (getDatatype() != null) {
            _hashCode += getDatatype().hashCode();
        }
        if (getGenericName() != null) {
            _hashCode += getGenericName().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NameValuePairDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "nameValuePairDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datatype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "datatype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("genericName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "genericName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "value"));
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
