/**
 * ComponentStateDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain;

public class ComponentStateDTO  implements java.io.Serializable {
    private boolean rendered;

    private java.lang.String value;

    private java.lang.String visible;

    public ComponentStateDTO() {
    }

    public ComponentStateDTO(
           boolean rendered,
           java.lang.String value,
           java.lang.String visible) {
           this.rendered = rendered;
           this.value = value;
           this.visible = visible;
    }


    /**
     * Gets the rendered value for this ComponentStateDTO.
     * 
     * @return rendered
     */
    public boolean isRendered() {
        return rendered;
    }


    /**
     * Sets the rendered value for this ComponentStateDTO.
     * 
     * @param rendered
     */
    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }


    /**
     * Gets the value value for this ComponentStateDTO.
     * 
     * @return value
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this ComponentStateDTO.
     * 
     * @param value
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }


    /**
     * Gets the visible value for this ComponentStateDTO.
     * 
     * @return visible
     */
    public java.lang.String getVisible() {
        return visible;
    }


    /**
     * Sets the visible value for this ComponentStateDTO.
     * 
     * @param visible
     */
    public void setVisible(java.lang.String visible) {
        this.visible = visible;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComponentStateDTO)) return false;
        ComponentStateDTO other = (ComponentStateDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.rendered == other.isRendered() &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue()))) &&
            ((this.visible==null && other.getVisible()==null) || 
             (this.visible!=null &&
              this.visible.equals(other.getVisible())));
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
        _hashCode += (isRendered() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getVisible() != null) {
            _hashCode += getVisible().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComponentStateDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentStateDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rendered");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "rendered"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "visible"));
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
