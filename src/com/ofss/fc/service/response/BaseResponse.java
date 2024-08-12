/**
 * BaseResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.service.response;

public abstract class BaseResponse  implements java.io.Serializable {
	
    private java.lang.String configVersionId;

    private com.ofss.fc.enumeration.MaintenanceType maintenanceType;

    private com.ofss.fc.service.response.TransactionStatus status;

    public BaseResponse() {
    }

    public BaseResponse(
           java.lang.String configVersionId,
           com.ofss.fc.enumeration.MaintenanceType maintenanceType,
           com.ofss.fc.service.response.TransactionStatus status) {
           this.configVersionId = configVersionId;
           this.maintenanceType = maintenanceType;
           this.status = status;
    }


    /**
     * Gets the configVersionId value for this BaseResponse.
     * 
     * @return configVersionId
     */
    public java.lang.String getConfigVersionId() {
        return configVersionId;
    }


    /**
     * Sets the configVersionId value for this BaseResponse.
     * 
     * @param configVersionId
     */
    public void setConfigVersionId(java.lang.String configVersionId) {
        this.configVersionId = configVersionId;
    }


    /**
     * Gets the maintenanceType value for this BaseResponse.
     * 
     * @return maintenanceType
     */
    public com.ofss.fc.enumeration.MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }


    /**
     * Sets the maintenanceType value for this BaseResponse.
     * 
     * @param maintenanceType
     */
    public void setMaintenanceType(com.ofss.fc.enumeration.MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }


    /**
     * Gets the status value for this BaseResponse.
     * 
     * @return status
     */
    public com.ofss.fc.service.response.TransactionStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this BaseResponse.
     * 
     * @param status
     */
    public void setStatus(com.ofss.fc.service.response.TransactionStatus status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseResponse)) return false;
        BaseResponse other = (BaseResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.configVersionId==null && other.getConfigVersionId()==null) || 
             (this.configVersionId!=null &&
              this.configVersionId.equals(other.getConfigVersionId()))) &&
            ((this.maintenanceType==null && other.getMaintenanceType()==null) || 
             (this.maintenanceType!=null &&
              this.maintenanceType.equals(other.getMaintenanceType()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getConfigVersionId() != null) {
            _hashCode += getConfigVersionId().hashCode();
        }
        if (getMaintenanceType() != null) {
            _hashCode += getMaintenanceType().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "baseResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configVersionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "configVersionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maintenanceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "maintenanceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "maintenanceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "transactionStatus"));
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
