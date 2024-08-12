/**
 * FinancialInstitutionInquiryDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.pc.service.inquiry;

public class FinancialInstitutionInquiryDTO  extends com.ofss.fc.framework.domain.common.dto.DataTransferObject  implements java.io.Serializable {
    private java.lang.String bankName;

    private java.lang.String branchName;

    private java.lang.String ifscCode;

    public FinancialInstitutionInquiryDTO() {
    }

    public FinancialInstitutionInquiryDTO(
           com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray,
           java.lang.String organizationName,
           java.lang.String bankName,
           java.lang.String branchName,
           java.lang.String ifscCode) {
        super(
            dictionaryArray,
            organizationName);
        this.bankName = bankName;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
    }


    /**
     * Gets the bankName value for this FinancialInstitutionInquiryDTO.
     * 
     * @return bankName
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this FinancialInstitutionInquiryDTO.
     * 
     * @param bankName
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the branchName value for this FinancialInstitutionInquiryDTO.
     * 
     * @return branchName
     */
    public java.lang.String getBranchName() {
        return branchName;
    }


    /**
     * Sets the branchName value for this FinancialInstitutionInquiryDTO.
     * 
     * @param branchName
     */
    public void setBranchName(java.lang.String branchName) {
        this.branchName = branchName;
    }


    /**
     * Gets the ifscCode value for this FinancialInstitutionInquiryDTO.
     * 
     * @return ifscCode
     */
    public java.lang.String getIfscCode() {
        return ifscCode;
    }


    /**
     * Sets the ifscCode value for this FinancialInstitutionInquiryDTO.
     * 
     * @param ifscCode
     */
    public void setIfscCode(java.lang.String ifscCode) {
        this.ifscCode = ifscCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FinancialInstitutionInquiryDTO)) return false;
        FinancialInstitutionInquiryDTO other = (FinancialInstitutionInquiryDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.branchName==null && other.getBranchName()==null) || 
             (this.branchName!=null &&
              this.branchName.equals(other.getBranchName()))) &&
            ((this.ifscCode==null && other.getIfscCode()==null) || 
             (this.ifscCode!=null &&
              this.ifscCode.equals(other.getIfscCode())));
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
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getBranchName() != null) {
            _hashCode += getBranchName().hashCode();
        }
        if (getIfscCode() != null) {
            _hashCode += getIfscCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FinancialInstitutionInquiryDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ifscCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ifscCode"));
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
