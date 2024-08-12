/**
 * DataTransferObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain.common.dto;

public abstract class DataTransferObject  extends com.ofss.fc.app.dto.validation.Validatable  implements java.io.Serializable {
	
    private com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray;
    private java.lang.String organizationName;

    public DataTransferObject() {
    }

    public DataTransferObject(
           com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray) {
        this.dictionaryArray = dictionaryArray;
    }


    public DataTransferObject(com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray2,
    		java.lang.String organizationName) {
    	this.dictionaryArray = dictionaryArray2;
    	this.organizationName=organizationName;
	}

	/**
     * Gets the dictionaryArray value for this DataTransferObject.
     * 
     * @return dictionaryArray
     */
    public com.ofss.fc.framework.domain.common.dto.Dictionary[] getDictionaryArray() {
        return dictionaryArray;
    }


    /**
     * Sets the dictionaryArray value for this DataTransferObject.
     * 
     * @param dictionaryArray
     */
    public void setDictionaryArray(com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray) {
        this.dictionaryArray = dictionaryArray;
    }

    public com.ofss.fc.framework.domain.common.dto.Dictionary getDictionaryArray(int i) {
        return this.dictionaryArray[i];
    }

    public void setDictionaryArray(int i, com.ofss.fc.framework.domain.common.dto.Dictionary _value) {
        this.dictionaryArray[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataTransferObject)) return false;
        DataTransferObject other = (DataTransferObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dictionaryArray==null && other.getDictionaryArray()==null) || 
             (this.dictionaryArray!=null &&
              java.util.Arrays.equals(this.dictionaryArray, other.getDictionaryArray())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDictionaryArray() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDictionaryArray());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDictionaryArray(), i);
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
        new org.apache.axis.description.TypeDesc(DataTransferObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dataTransferObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dictionaryArray");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dictionaryArray"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dictionary"));
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
