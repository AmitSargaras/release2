/**
 * SessionContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class SessionContext  implements java.io.Serializable {
    private com.iflex.fcr.xface.td.inq.AdditionalAuthorizationDetails additionalAuthorizationDetails;

    private int bankCode;

    private java.lang.String brnPostingDateText;

    private java.lang.String channel;

    private java.lang.String electronicJournalOperationType;

    private long externalBatchNumber;

    private java.lang.String externalReferenceNo;

    private long externalSystemAuditTrailNumber;

    private java.lang.String localDateTimeText;

    private java.lang.String originalReferenceNo;

    private java.lang.String overridenWarnings;

    private java.lang.String postingDateText;

    private com.iflex.fcr.xface.td.inq.AuthorizationReason reason;

    private java.lang.String serviceCode;

    private java.lang.String sessionTicket;

    private com.iflex.fcr.xface.td.inq.UserContext supervisorContext;

    private java.lang.String terminalId;

    private int transactionBranch;

    private java.lang.String userId;

    private java.lang.String userReferenceNumber;

    private java.lang.String valueDateText;

    public SessionContext() {
    }

    public SessionContext(
           com.iflex.fcr.xface.td.inq.AdditionalAuthorizationDetails additionalAuthorizationDetails,
           int bankCode,
           java.lang.String brnPostingDateText,
           java.lang.String channel,
           java.lang.String electronicJournalOperationType,
           long externalBatchNumber,
           java.lang.String externalReferenceNo,
           long externalSystemAuditTrailNumber,
           java.lang.String localDateTimeText,
           java.lang.String originalReferenceNo,
           java.lang.String overridenWarnings,
           java.lang.String postingDateText,
           com.iflex.fcr.xface.td.inq.AuthorizationReason reason,
           java.lang.String serviceCode,
           java.lang.String sessionTicket,
           com.iflex.fcr.xface.td.inq.UserContext supervisorContext,
           java.lang.String terminalId,
           int transactionBranch,
           java.lang.String userId,
           java.lang.String userReferenceNumber,
           java.lang.String valueDateText) {
           this.additionalAuthorizationDetails = additionalAuthorizationDetails;
           this.bankCode = bankCode;
           this.brnPostingDateText = brnPostingDateText;
           this.channel = channel;
           this.electronicJournalOperationType = electronicJournalOperationType;
           this.externalBatchNumber = externalBatchNumber;
           this.externalReferenceNo = externalReferenceNo;
           this.externalSystemAuditTrailNumber = externalSystemAuditTrailNumber;
           this.localDateTimeText = localDateTimeText;
           this.originalReferenceNo = originalReferenceNo;
           this.overridenWarnings = overridenWarnings;
           this.postingDateText = postingDateText;
           this.reason = reason;
           this.serviceCode = serviceCode;
           this.sessionTicket = sessionTicket;
           this.supervisorContext = supervisorContext;
           this.terminalId = terminalId;
           this.transactionBranch = transactionBranch;
           this.userId = userId;
           this.userReferenceNumber = userReferenceNumber;
           this.valueDateText = valueDateText;
    }


    /**
     * Gets the additionalAuthorizationDetails value for this SessionContext.
     * 
     * @return additionalAuthorizationDetails
     */
    public com.iflex.fcr.xface.td.inq.AdditionalAuthorizationDetails getAdditionalAuthorizationDetails() {
        return additionalAuthorizationDetails;
    }


    /**
     * Sets the additionalAuthorizationDetails value for this SessionContext.
     * 
     * @param additionalAuthorizationDetails
     */
    public void setAdditionalAuthorizationDetails(com.iflex.fcr.xface.td.inq.AdditionalAuthorizationDetails additionalAuthorizationDetails) {
        this.additionalAuthorizationDetails = additionalAuthorizationDetails;
    }


    /**
     * Gets the bankCode value for this SessionContext.
     * 
     * @return bankCode
     */
    public int getBankCode() {
        return bankCode;
    }


    /**
     * Sets the bankCode value for this SessionContext.
     * 
     * @param bankCode
     */
    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }


    /**
     * Gets the brnPostingDateText value for this SessionContext.
     * 
     * @return brnPostingDateText
     */
    public java.lang.String getBrnPostingDateText() {
        return brnPostingDateText;
    }


    /**
     * Sets the brnPostingDateText value for this SessionContext.
     * 
     * @param brnPostingDateText
     */
    public void setBrnPostingDateText(java.lang.String brnPostingDateText) {
        this.brnPostingDateText = brnPostingDateText;
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
     * Gets the electronicJournalOperationType value for this SessionContext.
     * 
     * @return electronicJournalOperationType
     */
    public java.lang.String getElectronicJournalOperationType() {
        return electronicJournalOperationType;
    }


    /**
     * Sets the electronicJournalOperationType value for this SessionContext.
     * 
     * @param electronicJournalOperationType
     */
    public void setElectronicJournalOperationType(java.lang.String electronicJournalOperationType) {
        this.electronicJournalOperationType = electronicJournalOperationType;
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
     * Gets the reason value for this SessionContext.
     * 
     * @return reason
     */
    public com.iflex.fcr.xface.td.inq.AuthorizationReason getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this SessionContext.
     * 
     * @param reason
     */
    public void setReason(com.iflex.fcr.xface.td.inq.AuthorizationReason reason) {
        this.reason = reason;
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
     * Gets the sessionTicket value for this SessionContext.
     * 
     * @return sessionTicket
     */
    public java.lang.String getSessionTicket() {
        return sessionTicket;
    }


    /**
     * Sets the sessionTicket value for this SessionContext.
     * 
     * @param sessionTicket
     */
    public void setSessionTicket(java.lang.String sessionTicket) {
        this.sessionTicket = sessionTicket;
    }


    /**
     * Gets the supervisorContext value for this SessionContext.
     * 
     * @return supervisorContext
     */
    public com.iflex.fcr.xface.td.inq.UserContext getSupervisorContext() {
        return supervisorContext;
    }


    /**
     * Sets the supervisorContext value for this SessionContext.
     * 
     * @param supervisorContext
     */
    public void setSupervisorContext(com.iflex.fcr.xface.td.inq.UserContext supervisorContext) {
        this.supervisorContext = supervisorContext;
    }


    /**
     * Gets the terminalId value for this SessionContext.
     * 
     * @return terminalId
     */
    public java.lang.String getTerminalId() {
        return terminalId;
    }


    /**
     * Sets the terminalId value for this SessionContext.
     * 
     * @param terminalId
     */
    public void setTerminalId(java.lang.String terminalId) {
        this.terminalId = terminalId;
    }


    /**
     * Gets the transactionBranch value for this SessionContext.
     * 
     * @return transactionBranch
     */
    public int getTransactionBranch() {
        return transactionBranch;
    }


    /**
     * Sets the transactionBranch value for this SessionContext.
     * 
     * @param transactionBranch
     */
    public void setTransactionBranch(int transactionBranch) {
        this.transactionBranch = transactionBranch;
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
            ((this.additionalAuthorizationDetails==null && other.getAdditionalAuthorizationDetails()==null) || 
             (this.additionalAuthorizationDetails!=null &&
              this.additionalAuthorizationDetails.equals(other.getAdditionalAuthorizationDetails()))) &&
            this.bankCode == other.getBankCode() &&
            ((this.brnPostingDateText==null && other.getBrnPostingDateText()==null) || 
             (this.brnPostingDateText!=null &&
              this.brnPostingDateText.equals(other.getBrnPostingDateText()))) &&
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.electronicJournalOperationType==null && other.getElectronicJournalOperationType()==null) || 
             (this.electronicJournalOperationType!=null &&
              this.electronicJournalOperationType.equals(other.getElectronicJournalOperationType()))) &&
            this.externalBatchNumber == other.getExternalBatchNumber() &&
            ((this.externalReferenceNo==null && other.getExternalReferenceNo()==null) || 
             (this.externalReferenceNo!=null &&
              this.externalReferenceNo.equals(other.getExternalReferenceNo()))) &&
            this.externalSystemAuditTrailNumber == other.getExternalSystemAuditTrailNumber() &&
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
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.sessionTicket==null && other.getSessionTicket()==null) || 
             (this.sessionTicket!=null &&
              this.sessionTicket.equals(other.getSessionTicket()))) &&
            ((this.supervisorContext==null && other.getSupervisorContext()==null) || 
             (this.supervisorContext!=null &&
              this.supervisorContext.equals(other.getSupervisorContext()))) &&
            ((this.terminalId==null && other.getTerminalId()==null) || 
             (this.terminalId!=null &&
              this.terminalId.equals(other.getTerminalId()))) &&
            this.transactionBranch == other.getTransactionBranch() &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
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
        if (getAdditionalAuthorizationDetails() != null) {
            _hashCode += getAdditionalAuthorizationDetails().hashCode();
        }
        _hashCode += getBankCode();
        if (getBrnPostingDateText() != null) {
            _hashCode += getBrnPostingDateText().hashCode();
        }
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getElectronicJournalOperationType() != null) {
            _hashCode += getElectronicJournalOperationType().hashCode();
        }
        _hashCode += new Long(getExternalBatchNumber()).hashCode();
        if (getExternalReferenceNo() != null) {
            _hashCode += getExternalReferenceNo().hashCode();
        }
        _hashCode += new Long(getExternalSystemAuditTrailNumber()).hashCode();
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
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getSessionTicket() != null) {
            _hashCode += getSessionTicket().hashCode();
        }
        if (getSupervisorContext() != null) {
            _hashCode += getSupervisorContext().hashCode();
        }
        if (getTerminalId() != null) {
            _hashCode += getTerminalId().hashCode();
        }
        _hashCode += getTransactionBranch();
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
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
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "sessionContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalAuthorizationDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "additionalAuthorizationDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "additionalAuthorizationDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brnPostingDateText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brnPostingDateText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("electronicJournalOperationType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "electronicJournalOperationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalBatchNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalBatchNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystemAuditTrailNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystemAuditTrailNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localDateTimeText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localDateTimeText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalReferenceNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originalReferenceNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overridenWarnings");
        elemField.setXmlName(new javax.xml.namespace.QName("", "overridenWarnings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postingDateText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postingDateText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "authorizationReason"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionTicket");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionTicket"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supervisorContext");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supervisorContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "userContext"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "terminalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionBranch");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transactionBranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userReferenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userReferenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueDateText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valueDateText"));
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
