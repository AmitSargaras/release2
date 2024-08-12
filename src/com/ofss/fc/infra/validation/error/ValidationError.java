/**
 * ValidationError.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.infra.validation.error;

public class ValidationError  implements java.io.Serializable {
	
    private java.lang.String applicableAttributes;

    private long associatedSeverity;

    private java.lang.String attributeName;

    private java.lang.String attributeValue;

    private java.lang.String errorCode;

    private java.lang.String errorMessage;

    private java.lang.String[] errorMessageParams;

    private java.lang.String methodName;

    private java.lang.String objectName;

    public ValidationError() {
    }

    public ValidationError(
           java.lang.String applicableAttributes,
           long associatedSeverity,
           java.lang.String attributeName,
           java.lang.String attributeValue,
           java.lang.String errorCode,
           java.lang.String errorMessage,
           java.lang.String[] errorMessageParams,
           java.lang.String methodName,
           java.lang.String objectName) {
           this.applicableAttributes = applicableAttributes;
           this.associatedSeverity = associatedSeverity;
           this.attributeName = attributeName;
           this.attributeValue = attributeValue;
           this.errorCode = errorCode;
           this.errorMessage = errorMessage;
           this.errorMessageParams = errorMessageParams;
           this.methodName = methodName;
           this.objectName = objectName;
    }


    /**
     * Gets the applicableAttributes value for this ValidationError.
     * 
     * @return applicableAttributes
     */
    public java.lang.String getApplicableAttributes() {
        return applicableAttributes;
    }


    /**
     * Sets the applicableAttributes value for this ValidationError.
     * 
     * @param applicableAttributes
     */
    public void setApplicableAttributes(java.lang.String applicableAttributes) {
        this.applicableAttributes = applicableAttributes;
    }


    /**
     * Gets the associatedSeverity value for this ValidationError.
     * 
     * @return associatedSeverity
     */
    public long getAssociatedSeverity() {
        return associatedSeverity;
    }


    /**
     * Sets the associatedSeverity value for this ValidationError.
     * 
     * @param associatedSeverity
     */
    public void setAssociatedSeverity(long associatedSeverity) {
        this.associatedSeverity = associatedSeverity;
    }


    /**
     * Gets the attributeName value for this ValidationError.
     * 
     * @return attributeName
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this ValidationError.
     * 
     * @param attributeName
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Gets the attributeValue value for this ValidationError.
     * 
     * @return attributeValue
     */
    public java.lang.String getAttributeValue() {
        return attributeValue;
    }


    /**
     * Sets the attributeValue value for this ValidationError.
     * 
     * @param attributeValue
     */
    public void setAttributeValue(java.lang.String attributeValue) {
        this.attributeValue = attributeValue;
    }


    /**
     * Gets the errorCode value for this ValidationError.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this ValidationError.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the errorMessage value for this ValidationError.
     * 
     * @return errorMessage
     */
    public java.lang.String getErrorMessage() {
        return errorMessage;
    }


    /**
     * Sets the errorMessage value for this ValidationError.
     * 
     * @param errorMessage
     */
    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * Gets the errorMessageParams value for this ValidationError.
     * 
     * @return errorMessageParams
     */
    public java.lang.String[] getErrorMessageParams() {
        return errorMessageParams;
    }


    /**
     * Sets the errorMessageParams value for this ValidationError.
     * 
     * @param errorMessageParams
     */
    public void setErrorMessageParams(java.lang.String[] errorMessageParams) {
        this.errorMessageParams = errorMessageParams;
    }

    public java.lang.String getErrorMessageParams(int i) {
        return this.errorMessageParams[i];
    }

    public void setErrorMessageParams(int i, java.lang.String _value) {
        this.errorMessageParams[i] = _value;
    }


    /**
     * Gets the methodName value for this ValidationError.
     * 
     * @return methodName
     */
    public java.lang.String getMethodName() {
        return methodName;
    }


    /**
     * Sets the methodName value for this ValidationError.
     * 
     * @param methodName
     */
    public void setMethodName(java.lang.String methodName) {
        this.methodName = methodName;
    }


    /**
     * Gets the objectName value for this ValidationError.
     * 
     * @return objectName
     */
    public java.lang.String getObjectName() {
        return objectName;
    }


    /**
     * Sets the objectName value for this ValidationError.
     * 
     * @param objectName
     */
    public void setObjectName(java.lang.String objectName) {
        this.objectName = objectName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValidationError)) return false;
        ValidationError other = (ValidationError) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicableAttributes==null && other.getApplicableAttributes()==null) || 
             (this.applicableAttributes!=null &&
              this.applicableAttributes.equals(other.getApplicableAttributes()))) &&
            this.associatedSeverity == other.getAssociatedSeverity() &&
            ((this.attributeName==null && other.getAttributeName()==null) || 
             (this.attributeName!=null &&
              this.attributeName.equals(other.getAttributeName()))) &&
            ((this.attributeValue==null && other.getAttributeValue()==null) || 
             (this.attributeValue!=null &&
              this.attributeValue.equals(other.getAttributeValue()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.errorMessage==null && other.getErrorMessage()==null) || 
             (this.errorMessage!=null &&
              this.errorMessage.equals(other.getErrorMessage()))) &&
            ((this.errorMessageParams==null && other.getErrorMessageParams()==null) || 
             (this.errorMessageParams!=null &&
              java.util.Arrays.equals(this.errorMessageParams, other.getErrorMessageParams()))) &&
            ((this.methodName==null && other.getMethodName()==null) || 
             (this.methodName!=null &&
              this.methodName.equals(other.getMethodName()))) &&
            ((this.objectName==null && other.getObjectName()==null) || 
             (this.objectName!=null &&
              this.objectName.equals(other.getObjectName())));
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
        if (getApplicableAttributes() != null) {
            _hashCode += getApplicableAttributes().hashCode();
        }
        _hashCode += new Long(getAssociatedSeverity()).hashCode();
        if (getAttributeName() != null) {
            _hashCode += getAttributeName().hashCode();
        }
        if (getAttributeValue() != null) {
            _hashCode += getAttributeValue().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getErrorMessage() != null) {
            _hashCode += getErrorMessage().hashCode();
        }
        if (getErrorMessageParams() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrorMessageParams());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrorMessageParams(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMethodName() != null) {
            _hashCode += getMethodName().hashCode();
        }
        if (getObjectName() != null) {
            _hashCode += getObjectName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidationError.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "validationError"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicableAttributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "applicableAttributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("associatedSeverity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "associatedSeverity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "attributeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "attributeValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "errorMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessageParams");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "errorMessageParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("methodName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "methodName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "objectName"));
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
