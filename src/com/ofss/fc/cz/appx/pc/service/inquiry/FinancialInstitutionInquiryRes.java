/**
 * FinancialInstitutionInquiryRes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.pc.service.inquiry;

public class FinancialInstitutionInquiryRes  implements java.io.Serializable {
    private java.lang.String bankName;

    private java.lang.String beneType;

    private java.lang.String branchAddress;

    private java.lang.String branchName;

    private java.lang.String ifscCode;

    public FinancialInstitutionInquiryRes() {
    }

    public FinancialInstitutionInquiryRes(
           java.lang.String bankName,
           java.lang.String beneType,
           java.lang.String branchAddress,
           java.lang.String branchName,
           java.lang.String ifscCode) {
           this.bankName = bankName;
           this.beneType = beneType;
           this.branchAddress = branchAddress;
           this.branchName = branchName;
           this.ifscCode = ifscCode;
    }


    /**
     * Gets the bankName value for this FinancialInstitutionInquiryRes.
     * 
     * @return bankName
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this FinancialInstitutionInquiryRes.
     * 
     * @param bankName
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the beneType value for this FinancialInstitutionInquiryRes.
     * 
     * @return beneType
     */
    public java.lang.String getBeneType() {
        return beneType;
    }


    /**
     * Sets the beneType value for this FinancialInstitutionInquiryRes.
     * 
     * @param beneType
     */
    public void setBeneType(java.lang.String beneType) {
        this.beneType = beneType;
    }


    /**
     * Gets the branchAddress value for this FinancialInstitutionInquiryRes.
     * 
     * @return branchAddress
     */
    public java.lang.String getBranchAddress() {
        return branchAddress;
    }


    /**
     * Sets the branchAddress value for this FinancialInstitutionInquiryRes.
     * 
     * @param branchAddress
     */
    public void setBranchAddress(java.lang.String branchAddress) {
        this.branchAddress = branchAddress;
    }


    /**
     * Gets the branchName value for this FinancialInstitutionInquiryRes.
     * 
     * @return branchName
     */
    public java.lang.String getBranchName() {
        return branchName;
    }


    /**
     * Sets the branchName value for this FinancialInstitutionInquiryRes.
     * 
     * @param branchName
     */
    public void setBranchName(java.lang.String branchName) {
        this.branchName = branchName;
    }


    /**
     * Gets the ifscCode value for this FinancialInstitutionInquiryRes.
     * 
     * @return ifscCode
     */
    public java.lang.String getIfscCode() {
        return ifscCode;
    }


    /**
     * Sets the ifscCode value for this FinancialInstitutionInquiryRes.
     * 
     * @param ifscCode
     */
    public void setIfscCode(java.lang.String ifscCode) {
        this.ifscCode = ifscCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FinancialInstitutionInquiryRes)) return false;
        FinancialInstitutionInquiryRes other = (FinancialInstitutionInquiryRes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.beneType==null && other.getBeneType()==null) || 
             (this.beneType!=null &&
              this.beneType.equals(other.getBeneType()))) &&
            ((this.branchAddress==null && other.getBranchAddress()==null) || 
             (this.branchAddress!=null &&
              this.branchAddress.equals(other.getBranchAddress()))) &&
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
        int _hashCode = 1;
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getBeneType() != null) {
            _hashCode += getBeneType().hashCode();
        }
        if (getBranchAddress() != null) {
            _hashCode += getBranchAddress().hashCode();
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
        new org.apache.axis.description.TypeDesc(FinancialInstitutionInquiryRes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryRes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beneType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beneType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchAddress"));
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
