/**
 * FixedDepositInquiryRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class FixedDepositInquiryRequestDTO  extends com.iflex.fcr.xface.td.inq.XfaceGenericRequestDTO  implements java.io.Serializable {
    private java.lang.Integer depositNo;

    private java.lang.String fdAccountNo;

    public FixedDepositInquiryRequestDTO() {
    }

    public FixedDepositInquiryRequestDTO(
           java.lang.String validationFlag,
           java.lang.Integer depositNo,
           java.lang.String fdAccountNo) {
        super(
            validationFlag);
        this.depositNo = depositNo;
        this.fdAccountNo = fdAccountNo;
    }


    /**
     * Gets the depositNo value for this FixedDepositInquiryRequestDTO.
     * 
     * @return depositNo
     */
    public java.lang.Integer getDepositNo() {
        return depositNo;
    }


    /**
     * Sets the depositNo value for this FixedDepositInquiryRequestDTO.
     * 
     * @param depositNo
     */
    public void setDepositNo(java.lang.Integer depositNo) {
        this.depositNo = depositNo;
    }


    /**
     * Gets the fdAccountNo value for this FixedDepositInquiryRequestDTO.
     * 
     * @return fdAccountNo
     */
    public java.lang.String getFdAccountNo() {
        return fdAccountNo;
    }


    /**
     * Sets the fdAccountNo value for this FixedDepositInquiryRequestDTO.
     * 
     * @param fdAccountNo
     */
    public void setFdAccountNo(java.lang.String fdAccountNo) {
        this.fdAccountNo = fdAccountNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FixedDepositInquiryRequestDTO)) return false;
        FixedDepositInquiryRequestDTO other = (FixedDepositInquiryRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.depositNo==null && other.getDepositNo()==null) || 
             (this.depositNo!=null &&
              this.depositNo.equals(other.getDepositNo()))) &&
            ((this.fdAccountNo==null && other.getFdAccountNo()==null) || 
             (this.fdAccountNo!=null &&
              this.fdAccountNo.equals(other.getFdAccountNo())));
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
        if (getDepositNo() != null) {
            _hashCode += getDepositNo().hashCode();
        }
        if (getFdAccountNo() != null) {
            _hashCode += getFdAccountNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FixedDepositInquiryRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "fixedDepositInquiryRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fdAccountNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fdAccountNo"));
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
