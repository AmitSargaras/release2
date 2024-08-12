/**
 * ApprovalContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.app.context;

public class ApprovalContext  implements java.io.Serializable {
	
    private com.ofss.fc.infra.exception.ReplyMessage[] approvedOverrides;

    public ApprovalContext() {
    }

    public ApprovalContext(
           com.ofss.fc.infra.exception.ReplyMessage[] approvedOverrides) {
           this.approvedOverrides = approvedOverrides;
    }


    /**
     * Gets the approvedOverrides value for this ApprovalContext.
     * 
     * @return approvedOverrides
     */
    public com.ofss.fc.infra.exception.ReplyMessage[] getApprovedOverrides() {
        return approvedOverrides;
    }


    /**
     * Sets the approvedOverrides value for this ApprovalContext.
     * 
     * @param approvedOverrides
     */
    public void setApprovedOverrides(com.ofss.fc.infra.exception.ReplyMessage[] approvedOverrides) {
        this.approvedOverrides = approvedOverrides;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApprovalContext)) return false;
        ApprovalContext other = (ApprovalContext) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.approvedOverrides==null && other.getApprovedOverrides()==null) || 
             (this.approvedOverrides!=null &&
              java.util.Arrays.equals(this.approvedOverrides, other.getApprovedOverrides())));
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
        if (getApprovedOverrides() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getApprovedOverrides());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getApprovedOverrides(), i);
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
        new org.apache.axis.description.TypeDesc(ApprovalContext.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "approvalContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvedOverrides");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "approvedOverrides"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "replyMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "messages"));
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
