/**
 * TransactionStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.service.response;

public class TransactionStatus  implements java.io.Serializable {
	
    private java.lang.String errorCode;

    private com.ofss.fc.infra.exception.ReplyMessage[] extendedReply;

    private java.lang.String externalReferenceNo;

    private java.lang.String internalReferenceNumber;

    private boolean isOverriden;

    private java.lang.String memo;

    private com.ofss.fc.datatype.Date postingDate;

    private long replyCode;

    private java.lang.String replyText;

    private java.lang.String userReferenceNumber;

    private com.ofss.fc.infra.validation.error.ValidationError[] validationErrors;

    public TransactionStatus() {
    }

    public TransactionStatus(
           java.lang.String errorCode,
           com.ofss.fc.infra.exception.ReplyMessage[] extendedReply,
           java.lang.String externalReferenceNo,
           java.lang.String internalReferenceNumber,
           boolean isOverriden,
           java.lang.String memo,
           com.ofss.fc.datatype.Date postingDate,
           long replyCode,
           java.lang.String replyText,
           java.lang.String userReferenceNumber,
           com.ofss.fc.infra.validation.error.ValidationError[] validationErrors) {
           this.errorCode = errorCode;
           this.extendedReply = extendedReply;
           this.externalReferenceNo = externalReferenceNo;
           this.internalReferenceNumber = internalReferenceNumber;
           this.isOverriden = isOverriden;
           this.memo = memo;
           this.postingDate = postingDate;
           this.replyCode = replyCode;
           this.replyText = replyText;
           this.userReferenceNumber = userReferenceNumber;
           this.validationErrors = validationErrors;
    }


    /**
     * Gets the errorCode value for this TransactionStatus.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this TransactionStatus.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the extendedReply value for this TransactionStatus.
     * 
     * @return extendedReply
     */
    public com.ofss.fc.infra.exception.ReplyMessage[] getExtendedReply() {
        return extendedReply;
    }


    /**
     * Sets the extendedReply value for this TransactionStatus.
     * 
     * @param extendedReply
     */
    public void setExtendedReply(com.ofss.fc.infra.exception.ReplyMessage[] extendedReply) {
        this.extendedReply = extendedReply;
    }


    /**
     * Gets the externalReferenceNo value for this TransactionStatus.
     * 
     * @return externalReferenceNo
     */
    public java.lang.String getExternalReferenceNo() {
        return externalReferenceNo;
    }


    /**
     * Sets the externalReferenceNo value for this TransactionStatus.
     * 
     * @param externalReferenceNo
     */
    public void setExternalReferenceNo(java.lang.String externalReferenceNo) {
        this.externalReferenceNo = externalReferenceNo;
    }


    /**
     * Gets the internalReferenceNumber value for this TransactionStatus.
     * 
     * @return internalReferenceNumber
     */
    public java.lang.String getInternalReferenceNumber() {
        return internalReferenceNumber;
    }


    /**
     * Sets the internalReferenceNumber value for this TransactionStatus.
     * 
     * @param internalReferenceNumber
     */
    public void setInternalReferenceNumber(java.lang.String internalReferenceNumber) {
        this.internalReferenceNumber = internalReferenceNumber;
    }


    /**
     * Gets the isOverriden value for this TransactionStatus.
     * 
     * @return isOverriden
     */
    public boolean isIsOverriden() {
        return isOverriden;
    }


    /**
     * Sets the isOverriden value for this TransactionStatus.
     * 
     * @param isOverriden
     */
    public void setIsOverriden(boolean isOverriden) {
        this.isOverriden = isOverriden;
    }


    /**
     * Gets the memo value for this TransactionStatus.
     * 
     * @return memo
     */
    public java.lang.String getMemo() {
        return memo;
    }


    /**
     * Sets the memo value for this TransactionStatus.
     * 
     * @param memo
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }


    /**
     * Gets the postingDate value for this TransactionStatus.
     * 
     * @return postingDate
     */
    public com.ofss.fc.datatype.Date getPostingDate() {
        return postingDate;
    }


    /**
     * Sets the postingDate value for this TransactionStatus.
     * 
     * @param postingDate
     */
    public void setPostingDate(com.ofss.fc.datatype.Date postingDate) {
        this.postingDate = postingDate;
    }


    /**
     * Gets the replyCode value for this TransactionStatus.
     * 
     * @return replyCode
     */
    public long getReplyCode() {
        return replyCode;
    }


    /**
     * Sets the replyCode value for this TransactionStatus.
     * 
     * @param replyCode
     */
    public void setReplyCode(long replyCode) {
        this.replyCode = replyCode;
    }


    /**
     * Gets the replyText value for this TransactionStatus.
     * 
     * @return replyText
     */
    public java.lang.String getReplyText() {
        return replyText;
    }


    /**
     * Sets the replyText value for this TransactionStatus.
     * 
     * @param replyText
     */
    public void setReplyText(java.lang.String replyText) {
        this.replyText = replyText;
    }


    /**
     * Gets the userReferenceNumber value for this TransactionStatus.
     * 
     * @return userReferenceNumber
     */
    public java.lang.String getUserReferenceNumber() {
        return userReferenceNumber;
    }


    /**
     * Sets the userReferenceNumber value for this TransactionStatus.
     * 
     * @param userReferenceNumber
     */
    public void setUserReferenceNumber(java.lang.String userReferenceNumber) {
        this.userReferenceNumber = userReferenceNumber;
    }


    /**
     * Gets the validationErrors value for this TransactionStatus.
     * 
     * @return validationErrors
     */
    public com.ofss.fc.infra.validation.error.ValidationError[] getValidationErrors() {
        return validationErrors;
    }


    /**
     * Sets the validationErrors value for this TransactionStatus.
     * 
     * @param validationErrors
     */
    public void setValidationErrors(com.ofss.fc.infra.validation.error.ValidationError[] validationErrors) {
        this.validationErrors = validationErrors;
    }

    public com.ofss.fc.infra.validation.error.ValidationError getValidationErrors(int i) {
        return this.validationErrors[i];
    }

    public void setValidationErrors(int i, com.ofss.fc.infra.validation.error.ValidationError _value) {
        this.validationErrors[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionStatus)) return false;
        TransactionStatus other = (TransactionStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.extendedReply==null && other.getExtendedReply()==null) || 
             (this.extendedReply!=null &&
              java.util.Arrays.equals(this.extendedReply, other.getExtendedReply()))) &&
            ((this.externalReferenceNo==null && other.getExternalReferenceNo()==null) || 
             (this.externalReferenceNo!=null &&
              this.externalReferenceNo.equals(other.getExternalReferenceNo()))) &&
            ((this.internalReferenceNumber==null && other.getInternalReferenceNumber()==null) || 
             (this.internalReferenceNumber!=null &&
              this.internalReferenceNumber.equals(other.getInternalReferenceNumber()))) &&
            this.isOverriden == other.isIsOverriden() &&
            ((this.memo==null && other.getMemo()==null) || 
             (this.memo!=null &&
              this.memo.equals(other.getMemo()))) &&
            ((this.postingDate==null && other.getPostingDate()==null) || 
             (this.postingDate!=null &&
              this.postingDate.equals(other.getPostingDate()))) &&
            this.replyCode == other.getReplyCode() &&
            ((this.replyText==null && other.getReplyText()==null) || 
             (this.replyText!=null &&
              this.replyText.equals(other.getReplyText()))) &&
            ((this.userReferenceNumber==null && other.getUserReferenceNumber()==null) || 
             (this.userReferenceNumber!=null &&
              this.userReferenceNumber.equals(other.getUserReferenceNumber()))) &&
            ((this.validationErrors==null && other.getValidationErrors()==null) || 
             (this.validationErrors!=null &&
              java.util.Arrays.equals(this.validationErrors, other.getValidationErrors())));
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
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getExtendedReply() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExtendedReply());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExtendedReply(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExternalReferenceNo() != null) {
            _hashCode += getExternalReferenceNo().hashCode();
        }
        if (getInternalReferenceNumber() != null) {
            _hashCode += getInternalReferenceNumber().hashCode();
        }
        _hashCode += (isIsOverriden() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMemo() != null) {
            _hashCode += getMemo().hashCode();
        }
        if (getPostingDate() != null) {
            _hashCode += getPostingDate().hashCode();
        }
        _hashCode += new Long(getReplyCode()).hashCode();
        if (getReplyText() != null) {
            _hashCode += getReplyText().hashCode();
        }
        if (getUserReferenceNumber() != null) {
            _hashCode += getUserReferenceNumber().hashCode();
        }
        if (getValidationErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValidationErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValidationErrors(), i);
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
        new org.apache.axis.description.TypeDesc(TransactionStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "transactionStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extendedReply");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "extendedReply"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "replyMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "messages"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "externalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internalReferenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "internalReferenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOverriden");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "isOverriden"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "memo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "postingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datatype.fc.ofss.com", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "replyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replyText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "replyText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userReferenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "userReferenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validationErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "validationErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "validationError"));
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
