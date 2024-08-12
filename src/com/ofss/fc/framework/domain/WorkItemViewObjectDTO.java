/**
 * WorkItemViewObjectDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.framework.domain;

public class WorkItemViewObjectDTO  implements java.io.Serializable {
    private com.ofss.fc.framework.domain.ComponentIdentityDTO[] componentsIdentity;

    private java.lang.String pageDefinitionXml;

    private java.lang.String taskCode;

    private com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] voObjects;

    private java.lang.String voOwnerId;

    public WorkItemViewObjectDTO() {
    }

    public WorkItemViewObjectDTO(
           com.ofss.fc.framework.domain.ComponentIdentityDTO[] componentsIdentity,
           java.lang.String pageDefinitionXml,
           java.lang.String taskCode,
           com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] voObjects,
           java.lang.String voOwnerId) {
           this.componentsIdentity = componentsIdentity;
           this.pageDefinitionXml = pageDefinitionXml;
           this.taskCode = taskCode;
           this.voObjects = voObjects;
           this.voOwnerId = voOwnerId;
    }


    /**
     * Gets the componentsIdentity value for this WorkItemViewObjectDTO.
     * 
     * @return componentsIdentity
     */
    public com.ofss.fc.framework.domain.ComponentIdentityDTO[] getComponentsIdentity() {
        return componentsIdentity;
    }


    /**
     * Sets the componentsIdentity value for this WorkItemViewObjectDTO.
     * 
     * @param componentsIdentity
     */
    public void setComponentsIdentity(com.ofss.fc.framework.domain.ComponentIdentityDTO[] componentsIdentity) {
        this.componentsIdentity = componentsIdentity;
    }

    public com.ofss.fc.framework.domain.ComponentIdentityDTO getComponentsIdentity(int i) {
        return this.componentsIdentity[i];
    }

    public void setComponentsIdentity(int i, com.ofss.fc.framework.domain.ComponentIdentityDTO _value) {
        this.componentsIdentity[i] = _value;
    }


    /**
     * Gets the pageDefinitionXml value for this WorkItemViewObjectDTO.
     * 
     * @return pageDefinitionXml
     */
    public java.lang.String getPageDefinitionXml() {
        return pageDefinitionXml;
    }


    /**
     * Sets the pageDefinitionXml value for this WorkItemViewObjectDTO.
     * 
     * @param pageDefinitionXml
     */
    public void setPageDefinitionXml(java.lang.String pageDefinitionXml) {
        this.pageDefinitionXml = pageDefinitionXml;
    }


    /**
     * Gets the taskCode value for this WorkItemViewObjectDTO.
     * 
     * @return taskCode
     */
    public java.lang.String getTaskCode() {
        return taskCode;
    }


    /**
     * Sets the taskCode value for this WorkItemViewObjectDTO.
     * 
     * @param taskCode
     */
    public void setTaskCode(java.lang.String taskCode) {
        this.taskCode = taskCode;
    }


    /**
     * Gets the voObjects value for this WorkItemViewObjectDTO.
     * 
     * @return voObjects
     */
    public com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] getVoObjects() {
        return voObjects;
    }


    /**
     * Sets the voObjects value for this WorkItemViewObjectDTO.
     * 
     * @param voObjects
     */
    public void setVoObjects(com.ofss.fc.framework.domain.common.dto.NameValuePairDTO[] voObjects) {
        this.voObjects = voObjects;
    }

    public com.ofss.fc.framework.domain.common.dto.NameValuePairDTO getVoObjects(int i) {
        return this.voObjects[i];
    }

    public void setVoObjects(int i, com.ofss.fc.framework.domain.common.dto.NameValuePairDTO _value) {
        this.voObjects[i] = _value;
    }


    /**
     * Gets the voOwnerId value for this WorkItemViewObjectDTO.
     * 
     * @return voOwnerId
     */
    public java.lang.String getVoOwnerId() {
        return voOwnerId;
    }


    /**
     * Sets the voOwnerId value for this WorkItemViewObjectDTO.
     * 
     * @param voOwnerId
     */
    public void setVoOwnerId(java.lang.String voOwnerId) {
        this.voOwnerId = voOwnerId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkItemViewObjectDTO)) return false;
        WorkItemViewObjectDTO other = (WorkItemViewObjectDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.componentsIdentity==null && other.getComponentsIdentity()==null) || 
             (this.componentsIdentity!=null &&
              java.util.Arrays.equals(this.componentsIdentity, other.getComponentsIdentity()))) &&
            ((this.pageDefinitionXml==null && other.getPageDefinitionXml()==null) || 
             (this.pageDefinitionXml!=null &&
              this.pageDefinitionXml.equals(other.getPageDefinitionXml()))) &&
            ((this.taskCode==null && other.getTaskCode()==null) || 
             (this.taskCode!=null &&
              this.taskCode.equals(other.getTaskCode()))) &&
            ((this.voObjects==null && other.getVoObjects()==null) || 
             (this.voObjects!=null &&
              java.util.Arrays.equals(this.voObjects, other.getVoObjects()))) &&
            ((this.voOwnerId==null && other.getVoOwnerId()==null) || 
             (this.voOwnerId!=null &&
              this.voOwnerId.equals(other.getVoOwnerId())));
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
        if (getComponentsIdentity() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComponentsIdentity());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComponentsIdentity(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPageDefinitionXml() != null) {
            _hashCode += getPageDefinitionXml().hashCode();
        }
        if (getTaskCode() != null) {
            _hashCode += getTaskCode().hashCode();
        }
        if (getVoObjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVoObjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVoObjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVoOwnerId() != null) {
            _hashCode += getVoOwnerId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkItemViewObjectDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "workItemViewObjectDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentsIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentsIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentIdentityDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageDefinitionXml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "pageDefinitionXml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "taskCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voObjects");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "voObjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "nameValuePairDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voOwnerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "voOwnerId"));
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
