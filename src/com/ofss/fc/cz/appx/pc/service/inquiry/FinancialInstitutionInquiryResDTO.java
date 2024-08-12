/**
 * FinancialInstitutionInquiryResDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.pc.service.inquiry;

public class FinancialInstitutionInquiryResDTO  extends com.ofss.fc.service.response.BaseResponse  implements java.io.Serializable {
    private com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray;

    private com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes[] financialInstitutionApplicationResponse;

    public FinancialInstitutionInquiryResDTO() {
    }

    public FinancialInstitutionInquiryResDTO(
           java.lang.String configVersionId,
           com.ofss.fc.enumeration.MaintenanceType maintenanceType,
           com.ofss.fc.service.response.TransactionStatus status,
           com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray,
           com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes[] financialInstitutionApplicationResponse) {
        super(
            configVersionId,
            maintenanceType,
            status);
        this.dictionaryArray = dictionaryArray;
        this.financialInstitutionApplicationResponse = financialInstitutionApplicationResponse;
    }


    /**
     * Gets the dictionaryArray value for this FinancialInstitutionInquiryResDTO.
     * 
     * @return dictionaryArray
     */
    public com.ofss.fc.framework.domain.common.dto.Dictionary[] getDictionaryArray() {
        return dictionaryArray;
    }


    /**
     * Sets the dictionaryArray value for this FinancialInstitutionInquiryResDTO.
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


    /**
     * Gets the financialInstitutionApplicationResponse value for this FinancialInstitutionInquiryResDTO.
     * 
     * @return financialInstitutionApplicationResponse
     */
    public com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes[] getFinancialInstitutionApplicationResponse() {
        return financialInstitutionApplicationResponse;
    }


    /**
     * Sets the financialInstitutionApplicationResponse value for this FinancialInstitutionInquiryResDTO.
     * 
     * @param financialInstitutionApplicationResponse
     */
    public void setFinancialInstitutionApplicationResponse(com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes[] financialInstitutionApplicationResponse) {
        this.financialInstitutionApplicationResponse = financialInstitutionApplicationResponse;
    }

    public com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes getFinancialInstitutionApplicationResponse(int i) {
        return this.financialInstitutionApplicationResponse[i];
    }

    public void setFinancialInstitutionApplicationResponse(int i, com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryRes _value) {
        this.financialInstitutionApplicationResponse[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FinancialInstitutionInquiryResDTO)) return false;
        FinancialInstitutionInquiryResDTO other = (FinancialInstitutionInquiryResDTO) obj;
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
              java.util.Arrays.equals(this.dictionaryArray, other.getDictionaryArray()))) &&
            ((this.financialInstitutionApplicationResponse==null && other.getFinancialInstitutionApplicationResponse()==null) || 
             (this.financialInstitutionApplicationResponse!=null &&
              java.util.Arrays.equals(this.financialInstitutionApplicationResponse, other.getFinancialInstitutionApplicationResponse())));
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
        if (getFinancialInstitutionApplicationResponse() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFinancialInstitutionApplicationResponse());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFinancialInstitutionApplicationResponse(), i);
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
        new org.apache.axis.description.TypeDesc(FinancialInstitutionInquiryResDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryResDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dictionaryArray");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dictionaryArray"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dictionary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("financialInstitutionApplicationResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "financialInstitutionApplicationResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryRes"));
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
