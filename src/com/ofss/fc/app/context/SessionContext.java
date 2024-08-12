/**
 * SessionContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.app.context;

public class SessionContext  implements java.io.Serializable {
	
    private java.lang.String[] accessibleTargetUnits;

    private boolean allModeSelected;

    private com.ofss.fc.app.context.ApprovalContext approvalContext;

    private java.lang.String bankCode;

    private java.lang.String channel;

    private long externalBatchNumber;

    private java.lang.String externalReferenceNo;

    private long externalSystemAuditTrailNumber;

    private java.lang.String internalReferenceNo;

    private java.lang.String localDateTimeText;

    private java.lang.String originalReferenceNo;

    private java.lang.String overridenWarnings;

    private java.lang.String postingDateText;

    private com.ofss.fc.enumeration.ServiceCallContextType serviceCallContextType;

    private java.lang.String serviceCode;

    private com.ofss.fc.app.context.UserContext supervisorContext;

    private java.lang.String targetUnit;

    private java.lang.String transactingPartyCode;

    private java.lang.String transactionBranch;

    private com.ofss.fc.datatype.Date transactionStartTime;

    private java.lang.String userId;

    private java.lang.String userLocale;

    private java.lang.String userReferenceNumber;

    private java.lang.String valueDateText;

    public SessionContext() {
    }

    public SessionContext(
           java.lang.String[] accessibleTargetUnits,
           boolean allModeSelected,
           com.ofss.fc.app.context.ApprovalContext approvalContext,
           java.lang.String bankCode,
           java.lang.String channel,
           long externalBatchNumber,
           java.lang.String externalReferenceNo,
           long externalSystemAuditTrailNumber,
           java.lang.String internalReferenceNo,
           java.lang.String localDateTimeText,
           java.lang.String originalReferenceNo,
           java.lang.String overridenWarnings,
           java.lang.String postingDateText,
           com.ofss.fc.enumeration.ServiceCallContextType serviceCallContextType,
           java.lang.String serviceCode,
           com.ofss.fc.app.context.UserContext supervisorContext,
           java.lang.String targetUnit,
           java.lang.String transactingPartyCode,
           java.lang.String transactionBranch,
           com.ofss.fc.datatype.Date transactionStartTime,
           java.lang.String userId,
           java.lang.String userLocale,
           java.lang.String userReferenceNumber,
           java.lang.String valueDateText) {
           this.accessibleTargetUnits = accessibleTargetUnits;
           this.allModeSelected = allModeSelected;
           this.approvalContext = approvalContext;
           this.bankCode = bankCode;
           this.channel = channel;
           this.externalBatchNumber = externalBatchNumber;
           this.externalReferenceNo = externalReferenceNo;
           this.externalSystemAuditTrailNumber = externalSystemAuditTrailNumber;
           this.internalReferenceNo = internalReferenceNo;
           this.localDateTimeText = localDateTimeText;
           this.originalReferenceNo = originalReferenceNo;
           this.overridenWarnings = overridenWarnings;
           this.postingDateText = postingDateText;
           this.serviceCallContextType = serviceCallContextType;
           this.serviceCode = serviceCode;
           this.supervisorContext = supervisorContext;
           this.targetUnit = targetUnit;
           this.transactingPartyCode = transactingPartyCode;
           this.transactionBranch = transactionBranch;
           this.transactionStartTime = transactionStartTime;
           this.userId = userId;
           this.userLocale = userLocale;
           this.userReferenceNumber = userReferenceNumber;
           this.valueDateText = valueDateText;
    }


    /**
     * Gets the accessibleTargetUnits value for this SessionContext.
     * 
     * @return accessibleTargetUnits
     */
    public java.lang.String[] getAccessibleTargetUnits() {
        return accessibleTargetUnits;
    }


    /**
     * Sets the accessibleTargetUnits value for this SessionContext.
     * 
     * @param accessibleTargetUnits
     */
    public void setAccessibleTargetUnits(java.lang.String[] accessibleTargetUnits) {
        this.accessibleTargetUnits = accessibleTargetUnits;
    }

    public java.lang.String getAccessibleTargetUnits(int i) {
        return this.accessibleTargetUnits[i];
    }

    public void setAccessibleTargetUnits(int i, java.lang.String _value) {
        this.accessibleTargetUnits[i] = _value;
    }


    /**
     * Gets the allModeSelected value for this SessionContext.
     * 
     * @return allModeSelected
     */
    public boolean isAllModeSelected() {
        return allModeSelected;
    }


    /**
     * Sets the allModeSelected value for this SessionContext.
     * 
     * @param allModeSelected
     */
    public void setAllModeSelected(boolean allModeSelected) {
        this.allModeSelected = allModeSelected;
    }


    /**
     * Gets the approvalContext value for this SessionContext.
     * 
     * @return approvalContext
     */
    public com.ofss.fc.app.context.ApprovalContext getApprovalContext() {
        return approvalContext;
    }


    /**
     * Sets the approvalContext value for this SessionContext.
     * 
     * @param approvalContext
     */
    public void setApprovalContext(com.ofss.fc.app.context.ApprovalContext approvalContext) {
        this.approvalContext = approvalContext;
    }


    /**
     * Gets the bankCode value for this SessionContext.
     * 
     * @return bankCode
     */
    public java.lang.String getBankCode() {
        return bankCode;
    }


    /**
     * Sets the bankCode value for this SessionContext.
     * 
     * @param bankCode
     */
    public void setBankCode(java.lang.String bankCode) {
        this.bankCode = bankCode;
    }


    /**
     * Gets the channel value for this SessionContext.
     * 
     * @return channel
     */
    public java.lang.String getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this SessionContext.
     * 
     * @param channel
     */
    public void setChannel(java.lang.String channel) {
        this.channel = channel;
    }


    /**
     * Gets the externalBatchNumber value for this SessionContext.
     * 
     * @return externalBatchNumber
     */
    public long getExternalBatchNumber() {
        return externalBatchNumber;
    }


    /**
     * Sets the externalBatchNumber value for this SessionContext.
     * 
     * @param externalBatchNumber
     */
    public void setExternalBatchNumber(long externalBatchNumber) {
        this.externalBatchNumber = externalBatchNumber;
    }


    /**
     * Gets the externalReferenceNo value for this SessionContext.
     * 
     * @return externalReferenceNo
     */
    public java.lang.String getExternalReferenceNo() {
        return externalReferenceNo;
    }


    /**
     * Sets the externalReferenceNo value for this SessionContext.
     * 
     * @param externalReferenceNo
     */
    public void setExternalReferenceNo(java.lang.String externalReferenceNo) {
        this.externalReferenceNo = externalReferenceNo;
    }


    /**
     * Gets the externalSystemAuditTrailNumber value for this SessionContext.
     * 
     * @return externalSystemAuditTrailNumber
     */
    public long getExternalSystemAuditTrailNumber() {
        return externalSystemAuditTrailNumber;
    }


    /**
     * Sets the externalSystemAuditTrailNumber value for this SessionContext.
     * 
     * @param externalSystemAuditTrailNumber
     */
    public void setExternalSystemAuditTrailNumber(long externalSystemAuditTrailNumber) {
        this.externalSystemAuditTrailNumber = externalSystemAuditTrailNumber;
    }


    /**
     * Gets the internalReferenceNo value for this SessionContext.
     * 
     * @return internalReferenceNo
     */
    public java.lang.String getInternalReferenceNo() {
        return internalReferenceNo;
    }


    /**
     * Sets the internalReferenceNo value for this SessionContext.
     * 
     * @param internalReferenceNo
     */
    public void setInternalReferenceNo(java.lang.String internalReferenceNo) {
        this.internalReferenceNo = internalReferenceNo;
    }


    /**
     * Gets the localDateTimeText value for this SessionContext.
     * 
     * @return localDateTimeText
     */
    public java.lang.String getLocalDateTimeText() {
        return localDateTimeText;
    }


    /**
     * Sets the localDateTimeText value for this SessionContext.
     * 
     * @param localDateTimeText
     */
    public void setLocalDateTimeText(java.lang.String localDateTimeText) {
        this.localDateTimeText = localDateTimeText;
    }


    /**
     * Gets the originalReferenceNo value for this SessionContext.
     * 
     * @return originalReferenceNo
     */
    public java.lang.String getOriginalReferenceNo() {
        return originalReferenceNo;
    }


    /**
     * Sets the originalReferenceNo value for this SessionContext.
     * 
     * @param originalReferenceNo
     */
    public void setOriginalReferenceNo(java.lang.String originalReferenceNo) {
        this.originalReferenceNo = originalReferenceNo;
    }


    /**
     * Gets the overridenWarnings value for this SessionContext.
     * 
     * @return overridenWarnings
     */
    public java.lang.String getOverridenWarnings() {
        return overridenWarnings;
    }


    /**
     * Sets the overridenWarnings value for this SessionContext.
     * 
     * @param overridenWarnings
     */
    public void setOverridenWarnings(java.lang.String overridenWarnings) {
        this.overridenWarnings = overridenWarnings;
    }


    /**
     * Gets the postingDateText value for this SessionContext.
     * 
     * @return postingDateText
     */
    public java.lang.String getPostingDateText() {
        return postingDateText;
    }


    /**
     * Sets the postingDateText value for this SessionContext.
     * 
     * @param postingDateText
     */
    public void setPostingDateText(java.lang.String postingDateText) {
        this.postingDateText = postingDateText;
    }


    /**
     * Gets the serviceCallContextType value for this SessionContext.
     * 
     * @return serviceCallContextType
     */
    public com.ofss.fc.enumeration.ServiceCallContextType getServiceCallContextType() {
        return serviceCallContextType;
    }


    /**
     * Sets the serviceCallContextType value for this SessionContext.
     * 
     * @param serviceCallContextType
     */
    public void setServiceCallContextType(com.ofss.fc.enumeration.ServiceCallContextType serviceCallContextType) {
        this.serviceCallContextType = serviceCallContextType;
    }


    /**
     * Gets the serviceCode value for this SessionContext.
     * 
     * @return serviceCode
     */
    public java.lang.String getServiceCode() {
        return serviceCode;
    }


    /**
     * Sets the serviceCode value for this SessionContext.
     * 
     * @param serviceCode
     */
    public void setServiceCode(java.lang.String serviceCode) {
        this.serviceCode = serviceCode;
    }


    /**
     * Gets the supervisorContext value for this SessionContext.
     * 
     * @return supervisorContext
     */
    public com.ofss.fc.app.context.UserContext getSupervisorContext() {
        return supervisorContext;
    }


    /**
     * Sets the supervisorContext value for this SessionContext.
     * 
     * @param supervisorContext
     */
    public void setSupervisorContext(com.ofss.fc.app.context.UserContext supervisorContext) {
        this.supervisorContext = supervisorContext;
    }


    /**
     * Gets the targetUnit value for this SessionContext.
     * 
     * @return targetUnit
     */
    public java.lang.String getTargetUnit() {
        return targetUnit;
    }


    /**
     * Sets the targetUnit value for this SessionContext.
     * 
     * @param targetUnit
     */
    public void setTargetUnit(java.lang.String targetUnit) {
        this.targetUnit = targetUnit;
    }


    /**
     * Gets the transactingPartyCode value for this SessionContext.
     * 
     * @return transactingPartyCode
     */
    public java.lang.String getTransactingPartyCode() {
        return transactingPartyCode;
    }


    /**
     * Sets the transactingPartyCode value for this SessionContext.
     * 
     * @param transactingPartyCode
     */
    public void setTransactingPartyCode(java.lang.String transactingPartyCode) {
        this.transactingPartyCode = transactingPartyCode;
    }


    /**
     * Gets the transactionBranch value for this SessionContext.
     * 
     * @return transactionBranch
     */
    public java.lang.String getTransactionBranch() {
        return transactionBranch;
    }


    /**
     * Sets the transactionBranch value for this SessionContext.
     * 
     * @param transactionBranch
     */
    public void setTransactionBranch(java.lang.String transactionBranch) {
        this.transactionBranch = transactionBranch;
    }


    /**
     * Gets the transactionStartTime value for this SessionContext.
     * 
     * @return transactionStartTime
     */
    public com.ofss.fc.datatype.Date getTransactionStartTime() {
        return transactionStartTime;
    }


    /**
     * Sets the transactionStartTime value for this SessionContext.
     * 
     * @param transactionStartTime
     */
    public void setTransactionStartTime(com.ofss.fc.datatype.Date transactionStartTime) {
        this.transactionStartTime = transactionStartTime;
    }


    /**
     * Gets the userId value for this SessionContext.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this SessionContext.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the userLocale value for this SessionContext.
     * 
     * @return userLocale
     */
    public java.lang.String getUserLocale() {
        return userLocale;
    }


    /**
     * Sets the userLocale value for this SessionContext.
     * 
     * @param userLocale
     */
    public void setUserLocale(java.lang.String userLocale) {
        this.userLocale = userLocale;
    }


    /**
     * Gets the userReferenceNumber value for this SessionContext.
     * 
     * @return userReferenceNumber
     */
    public java.lang.String getUserReferenceNumber() {
        return userReferenceNumber;
    }


    /**
     * Sets the userReferenceNumber value for this SessionContext.
     * 
     * @param userReferenceNumber
     */
    public void setUserReferenceNumber(java.lang.String userReferenceNumber) {
        this.userReferenceNumber = userReferenceNumber;
    }


    /**
     * Gets the valueDateText value for this SessionContext.
     * 
     * @return valueDateText
     */
    public java.lang.String getValueDateText() {
        return valueDateText;
    }


    /**
     * Sets the valueDateText value for this SessionContext.
     * 
     * @param valueDateText
     */
    public void setValueDateText(java.lang.String valueDateText) {
        this.valueDateText = valueDateText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SessionContext)) return false;
        SessionContext other = (SessionContext) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessibleTargetUnits==null && other.getAccessibleTargetUnits()==null) || 
             (this.accessibleTargetUnits!=null &&
              java.util.Arrays.equals(this.accessibleTargetUnits, other.getAccessibleTargetUnits()))) &&
            this.allModeSelected == other.isAllModeSelected() &&
            ((this.approvalContext==null && other.getApprovalContext()==null) || 
             (this.approvalContext!=null &&
              this.approvalContext.equals(other.getApprovalContext()))) &&
            ((this.bankCode==null && other.getBankCode()==null) || 
             (this.bankCode!=null &&
              this.bankCode.equals(other.getBankCode()))) &&
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            this.externalBatchNumber == other.getExternalBatchNumber() &&
            ((this.externalReferenceNo==null && other.getExternalReferenceNo()==null) || 
             (this.externalReferenceNo!=null &&
              this.externalReferenceNo.equals(other.getExternalReferenceNo()))) &&
            this.externalSystemAuditTrailNumber == other.getExternalSystemAuditTrailNumber() &&
            ((this.internalReferenceNo==null && other.getInternalReferenceNo()==null) || 
             (this.internalReferenceNo!=null &&
              this.internalReferenceNo.equals(other.getInternalReferenceNo()))) &&
            ((this.localDateTimeText==null && other.getLocalDateTimeText()==null) || 
             (this.localDateTimeText!=null &&
              this.localDateTimeText.equals(other.getLocalDateTimeText()))) &&
            ((this.originalReferenceNo==null && other.getOriginalReferenceNo()==null) || 
             (this.originalReferenceNo!=null &&
              this.originalReferenceNo.equals(other.getOriginalReferenceNo()))) &&
            ((this.overridenWarnings==null && other.getOverridenWarnings()==null) || 
             (this.overridenWarnings!=null &&
              this.overridenWarnings.equals(other.getOverridenWarnings()))) &&
            ((this.postingDateText==null && other.getPostingDateText()==null) || 
             (this.postingDateText!=null &&
              this.postingDateText.equals(other.getPostingDateText()))) &&
            ((this.serviceCallContextType==null && other.getServiceCallContextType()==null) || 
             (this.serviceCallContextType!=null &&
              this.serviceCallContextType.equals(other.getServiceCallContextType()))) &&
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.supervisorContext==null && other.getSupervisorContext()==null) || 
             (this.supervisorContext!=null &&
              this.supervisorContext.equals(other.getSupervisorContext()))) &&
            ((this.targetUnit==null && other.getTargetUnit()==null) || 
             (this.targetUnit!=null &&
              this.targetUnit.equals(other.getTargetUnit()))) &&
            ((this.transactingPartyCode==null && other.getTransactingPartyCode()==null) || 
             (this.transactingPartyCode!=null &&
              this.transactingPartyCode.equals(other.getTransactingPartyCode()))) &&
            ((this.transactionBranch==null && other.getTransactionBranch()==null) || 
             (this.transactionBranch!=null &&
              this.transactionBranch.equals(other.getTransactionBranch()))) &&
            ((this.transactionStartTime==null && other.getTransactionStartTime()==null) || 
             (this.transactionStartTime!=null &&
              this.transactionStartTime.equals(other.getTransactionStartTime()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.userLocale==null && other.getUserLocale()==null) || 
             (this.userLocale!=null &&
              this.userLocale.equals(other.getUserLocale()))) &&
            ((this.userReferenceNumber==null && other.getUserReferenceNumber()==null) || 
             (this.userReferenceNumber!=null &&
              this.userReferenceNumber.equals(other.getUserReferenceNumber()))) &&
            ((this.valueDateText==null && other.getValueDateText()==null) || 
             (this.valueDateText!=null &&
              this.valueDateText.equals(other.getValueDateText())));
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
        if (getAccessibleTargetUnits() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccessibleTargetUnits());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccessibleTargetUnits(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isAllModeSelected() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getApprovalContext() != null) {
            _hashCode += getApprovalContext().hashCode();
        }
        if (getBankCode() != null) {
            _hashCode += getBankCode().hashCode();
        }
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        _hashCode += new Long(getExternalBatchNumber()).hashCode();
        if (getExternalReferenceNo() != null) {
            _hashCode += getExternalReferenceNo().hashCode();
        }
        _hashCode += new Long(getExternalSystemAuditTrailNumber()).hashCode();
        if (getInternalReferenceNo() != null) {
            _hashCode += getInternalReferenceNo().hashCode();
        }
        if (getLocalDateTimeText() != null) {
            _hashCode += getLocalDateTimeText().hashCode();
        }
        if (getOriginalReferenceNo() != null) {
            _hashCode += getOriginalReferenceNo().hashCode();
        }
        if (getOverridenWarnings() != null) {
            _hashCode += getOverridenWarnings().hashCode();
        }
        if (getPostingDateText() != null) {
            _hashCode += getPostingDateText().hashCode();
        }
        if (getServiceCallContextType() != null) {
            _hashCode += getServiceCallContextType().hashCode();
        }
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getSupervisorContext() != null) {
            _hashCode += getSupervisorContext().hashCode();
        }
        if (getTargetUnit() != null) {
            _hashCode += getTargetUnit().hashCode();
        }
        if (getTransactingPartyCode() != null) {
            _hashCode += getTransactingPartyCode().hashCode();
        }
        if (getTransactionBranch() != null) {
            _hashCode += getTransactionBranch().hashCode();
        }
        if (getTransactionStartTime() != null) {
            _hashCode += getTransactionStartTime().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getUserLocale() != null) {
            _hashCode += getUserLocale().hashCode();
        }
        if (getUserReferenceNumber() != null) {
            _hashCode += getUserReferenceNumber().hashCode();
        }
        if (getValueDateText() != null) {
            _hashCode += getValueDateText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SessionContext.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "sessionContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessibleTargetUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "accessibleTargetUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allModeSelected");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "allModeSelected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvalContext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "approvalContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "approvalContext"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "bankCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalBatchNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "externalBatchNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "externalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystemAuditTrailNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "externalSystemAuditTrailNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "internalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localDateTimeText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "localDateTimeText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "originalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overridenWarnings");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "overridenWarnings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postingDateText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "postingDateText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCallContextType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "serviceCallContextType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "serviceCallContextType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "serviceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supervisorContext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "supervisorContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "userContext"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "targetUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactingPartyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "transactingPartyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionBranch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "transactionBranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionStartTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "transactionStartTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://datatype.fc.ofss.com", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userLocale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "userLocale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userReferenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "userReferenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueDateText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "valueDateText"));
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
