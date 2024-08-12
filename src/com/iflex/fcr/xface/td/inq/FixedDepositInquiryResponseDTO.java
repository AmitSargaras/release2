/**
 * FixedDepositInquiryResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class FixedDepositInquiryResponseDTO  extends com.iflex.fcr.xface.td.inq.XfaceGenericResponseDTO  implements java.io.Serializable {
    private java.math.BigDecimal depositAmount;

    private java.lang.String depositDate;

    private java.lang.Integer depositNo;

    private java.lang.String depositorName;

    private java.lang.String fdAccountNo;

    private java.math.BigDecimal interestRate;

    private java.lang.String maturityDate;

    public FixedDepositInquiryResponseDTO() {
    }

    public FixedDepositInquiryResponseDTO(
           java.lang.String postingDate,
           com.iflex.fcr.xface.td.inq.TransactionStatus transactionStatus,
           java.math.BigDecimal depositAmount,
           java.lang.String depositDate,
           java.lang.Integer depositNo,
           java.lang.String depositorName,
           java.lang.String fdAccountNo,
           java.math.BigDecimal interestRate,
           java.lang.String maturityDate) {
        super(
            postingDate,
            transactionStatus);
        this.depositAmount = depositAmount;
        this.depositDate = depositDate;
        this.depositNo = depositNo;
        this.depositorName = depositorName;
        this.fdAccountNo = fdAccountNo;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
    }


    /**
     * Gets the depositAmount value for this FixedDepositInquiryResponseDTO.
     * 
     * @return depositAmount
     */
    public java.math.BigDecimal getDepositAmount() {
        return depositAmount;
    }


    /**
     * Sets the depositAmount value for this FixedDepositInquiryResponseDTO.
     * 
     * @param depositAmount
     */
    public void setDepositAmount(java.math.BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }


    /**
     * Gets the depositDate value for this FixedDepositInquiryResponseDTO.
     * 
     * @return depositDate
     */
    public java.lang.String getDepositDate() {
        return depositDate;
    }


    /**
     * Sets the depositDate value for this FixedDepositInquiryResponseDTO.
     * 
     * @param depositDate
     */
    public void setDepositDate(java.lang.String depositDate) {
        this.depositDate = depositDate;
    }


    /**
     * Gets the depositNo value for this FixedDepositInquiryResponseDTO.
     * 
     * @return depositNo
     */
    public java.lang.Integer getDepositNo() {
        return depositNo;
    }


    /**
     * Sets the depositNo value for this FixedDepositInquiryResponseDTO.
     * 
     * @param depositNo
     */
    public void setDepositNo(java.lang.Integer depositNo) {
        this.depositNo = depositNo;
    }


    /**
     * Gets the depositorName value for this FixedDepositInquiryResponseDTO.
     * 
     * @return depositorName
     */
    public java.lang.String getDepositorName() {
        return depositorName;
    }


    /**
     * Sets the depositorName value for this FixedDepositInquiryResponseDTO.
     * 
     * @param depositorName
     */
    public void setDepositorName(java.lang.String depositorName) {
        this.depositorName = depositorName;
    }


    /**
     * Gets the fdAccountNo value for this FixedDepositInquiryResponseDTO.
     * 
     * @return fdAccountNo
     */
    public java.lang.String getFdAccountNo() {
        return fdAccountNo;
    }


    /**
     * Sets the fdAccountNo value for this FixedDepositInquiryResponseDTO.
     * 
     * @param fdAccountNo
     */
    public void setFdAccountNo(java.lang.String fdAccountNo) {
        this.fdAccountNo = fdAccountNo;
    }


    /**
     * Gets the interestRate value for this FixedDepositInquiryResponseDTO.
     * 
     * @return interestRate
     */
    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }


    /**
     * Sets the interestRate value for this FixedDepositInquiryResponseDTO.
     * 
     * @param interestRate
     */
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }


    /**
     * Gets the maturityDate value for this FixedDepositInquiryResponseDTO.
     * 
     * @return maturityDate
     */
    public java.lang.String getMaturityDate() {
        return maturityDate;
    }


    /**
     * Sets the maturityDate value for this FixedDepositInquiryResponseDTO.
     * 
     * @param maturityDate
     */
    public void setMaturityDate(java.lang.String maturityDate) {
        this.maturityDate = maturityDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FixedDepositInquiryResponseDTO)) return false;
        FixedDepositInquiryResponseDTO other = (FixedDepositInquiryResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.depositAmount==null && other.getDepositAmount()==null) || 
             (this.depositAmount!=null &&
              this.depositAmount.equals(other.getDepositAmount()))) &&
            ((this.depositDate==null && other.getDepositDate()==null) || 
             (this.depositDate!=null &&
              this.depositDate.equals(other.getDepositDate()))) &&
            ((this.depositNo==null && other.getDepositNo()==null) || 
             (this.depositNo!=null &&
              this.depositNo.equals(other.getDepositNo()))) &&
            ((this.depositorName==null && other.getDepositorName()==null) || 
             (this.depositorName!=null &&
              this.depositorName.equals(other.getDepositorName()))) &&
            ((this.fdAccountNo==null && other.getFdAccountNo()==null) || 
             (this.fdAccountNo!=null &&
              this.fdAccountNo.equals(other.getFdAccountNo()))) &&
            ((this.interestRate==null && other.getInterestRate()==null) || 
             (this.interestRate!=null &&
              this.interestRate.equals(other.getInterestRate()))) &&
            ((this.maturityDate==null && other.getMaturityDate()==null) || 
             (this.maturityDate!=null &&
              this.maturityDate.equals(other.getMaturityDate())));
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
        if (getDepositAmount() != null) {
            _hashCode += getDepositAmount().hashCode();
        }
        if (getDepositDate() != null) {
            _hashCode += getDepositDate().hashCode();
        }
        if (getDepositNo() != null) {
            _hashCode += getDepositNo().hashCode();
        }
        if (getDepositorName() != null) {
            _hashCode += getDepositorName().hashCode();
        }
        if (getFdAccountNo() != null) {
            _hashCode += getFdAccountNo().hashCode();
        }
        if (getInterestRate() != null) {
            _hashCode += getInterestRate().hashCode();
        }
        if (getMaturityDate() != null) {
            _hashCode += getMaturityDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FixedDepositInquiryResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "fixedDepositInquiryResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositorName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depositorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interestRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maturityDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maturityDate"));
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
