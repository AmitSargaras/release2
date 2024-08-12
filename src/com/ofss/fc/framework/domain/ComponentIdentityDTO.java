/**
 * ComponentIdentityDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain;

public class ComponentIdentityDTO  implements java.io.Serializable {
	
    private java.lang.String componentKey;

    private com.ofss.fc.framework.domain.ComponentStateDTO componentState;

    public ComponentIdentityDTO() {
    }

    public ComponentIdentityDTO(
           java.lang.String componentKey,
           com.ofss.fc.framework.domain.ComponentStateDTO componentState) {
           this.componentKey = componentKey;
           this.componentState = componentState;
    }


    /**
     * Gets the componentKey value for this ComponentIdentityDTO.
     * 
     * @return componentKey
     */
    public java.lang.String getComponentKey() {
        return componentKey;
    }


    /**
     * Sets the componentKey value for this ComponentIdentityDTO.
     * 
     * @param componentKey
     */
    public void setComponentKey(java.lang.String componentKey) {
        this.componentKey = componentKey;
    }


    /**
     * Gets the componentState value for this ComponentIdentityDTO.
     * 
     * @return componentState
     */
    public com.ofss.fc.framework.domain.ComponentStateDTO getComponentState() {
        return componentState;
    }


    /**
     * Sets the componentState value for this ComponentIdentityDTO.
     * 
     * @param componentState
     */
    public void setComponentState(com.ofss.fc.framework.domain.ComponentStateDTO componentState) {
        this.componentState = componentState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComponentIdentityDTO)) return false;
        ComponentIdentityDTO other = (ComponentIdentityDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.componentKey==null && other.getComponentKey()==null) || 
             (this.componentKey!=null &&
              this.componentKey.equals(other.getComponentKey()))) &&
            ((this.componentState==null && other.getComponentState()==null) || 
             (this.componentState!=null &&
              this.componentState.equals(other.getComponentState())));
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
        if (getComponentKey() != null) {
            _hashCode += getComponentKey().hashCode();
        }
        if (getComponentState() != null) {
            _hashCode += getComponentState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComponentIdentityDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentIdentityDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentStateDTO"));
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
