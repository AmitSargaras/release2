/**
 * XfaceGenericResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class XfaceGenericResponseDTO  implements java.io.Serializable {
    private java.lang.String postingDate;

    private com.iflex.fcr.xface.td.inq.TransactionStatus transactionStatus;

    public XfaceGenericResponseDTO() {
    }

    public XfaceGenericResponseDTO(
           java.lang.String postingDate,
           com.iflex.fcr.xface.td.inq.TransactionStatus transactionStatus) {
           this.postingDate = postingDate;
           this.transactionStatus = transactionStatus;
    }


    /**
     * Gets the postingDate value for this XfaceGenericResponseDTO.
     * 
     * @return postingDate
     */
    public java.lang.String getPostingDate() {
        return postingDate;
    }


    /**
     * Sets the postingDate value for this XfaceGenericResponseDTO.
     * 
     * @param postingDate
     */
    public void setPostingDate(java.lang.String postingDate) {
        this.postingDate = postingDate;
    }


    /**
     * Gets the transactionStatus value for this XfaceGenericResponseDTO.
     * 
     * @return transactionStatus
     */
    public com.iflex.fcr.xface.td.inq.TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }


    /**
     * Sets the transactionStatus value for this XfaceGenericResponseDTO.
     * 
     * @param transactionStatus
     */
    public void setTransactionStatus(com.iflex.fcr.xface.td.inq.TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XfaceGenericResponseDTO)) return false;
        XfaceGenericResponseDTO other = (XfaceGenericResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.postingDate==null && other.getPostingDate()==null) || 
             (this.postingDate!=null &&
              this.postingDate.equals(other.getPostingDate()))) &&
            ((this.transactionStatus==null && other.getTransactionStatus()==null) || 
             (this.transactionStatus!=null &&
              this.transactionStatus.equals(other.getTransactionStatus())));
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
        if (getPostingDate() != null) {
            _hashCode += getPostingDate().hashCode();
        }
        if (getTransactionStatus() != null) {
            _hashCode += getTransactionStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XfaceGenericResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "xfaceGenericResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transactionStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "transactionStatus"));
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
