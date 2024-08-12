/**
 * AdditionalAuthorizationDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class AdditionalAuthorizationDetails  implements java.io.Serializable {
    private java.lang.String accountNumber;

    private java.lang.String approvalMatrixDetails;

    private long batchNumber;

    private int branchCode;

    private java.lang.String employeeCode;

    private java.lang.String employeeName;

    private java.lang.String flgLogTODAuthDetails;

    private java.lang.String referenceNumber;

    private long systemTrailAuditNumber;

    private java.lang.String tellerId;

    private java.lang.String todReason;

    public AdditionalAuthorizationDetails() {
    }

    public AdditionalAuthorizationDetails(
           java.lang.String accountNumber,
           java.lang.String approvalMatrixDetails,
           long batchNumber,
           int branchCode,
           java.lang.String employeeCode,
           java.lang.String employeeName,
           java.lang.String flgLogTODAuthDetails,
           java.lang.String referenceNumber,
           long systemTrailAuditNumber,
           java.lang.String tellerId,
           java.lang.String todReason) {
           this.accountNumber = accountNumber;
           this.approvalMatrixDetails = approvalMatrixDetails;
           this.batchNumber = batchNumber;
           this.branchCode = branchCode;
           this.employeeCode = employeeCode;
           this.employeeName = employeeName;
           this.flgLogTODAuthDetails = flgLogTODAuthDetails;
           this.referenceNumber = referenceNumber;
           this.systemTrailAuditNumber = systemTrailAuditNumber;
           this.tellerId = tellerId;
           this.todReason = todReason;
    }


    /**
     * Gets the accountNumber value for this AdditionalAuthorizationDetails.
     * 
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this AdditionalAuthorizationDetails.
     * 
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the approvalMatrixDetails value for this AdditionalAuthorizationDetails.
     * 
     * @return approvalMatrixDetails
     */
    public java.lang.String getApprovalMatrixDetails() {
        return approvalMatrixDetails;
    }


    /**
     * Sets the approvalMatrixDetails value for this AdditionalAuthorizationDetails.
     * 
     * @param approvalMatrixDetails
     */
    public void setApprovalMatrixDetails(java.lang.String approvalMatrixDetails) {
        this.approvalMatrixDetails = approvalMatrixDetails;
    }


    /**
     * Gets the batchNumber value for this AdditionalAuthorizationDetails.
     * 
     * @return batchNumber
     */
    public long getBatchNumber() {
        return batchNumber;
    }


    /**
     * Sets the batchNumber value for this AdditionalAuthorizationDetails.
     * 
     * @param batchNumber
     */
    public void setBatchNumber(long batchNumber) {
        this.batchNumber = batchNumber;
    }


    /**
     * Gets the branchCode value for this AdditionalAuthorizationDetails.
     * 
     * @return branchCode
     */
    public int getBranchCode() {
        return branchCode;
    }


    /**
     * Sets the branchCode value for this AdditionalAuthorizationDetails.
     * 
     * @param branchCode
     */
    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }


    /**
     * Gets the employeeCode value for this AdditionalAuthorizationDetails.
     * 
     * @return employeeCode
     */
    public java.lang.String getEmployeeCode() {
        return employeeCode;
    }


    /**
     * Sets the employeeCode value for this AdditionalAuthorizationDetails.
     * 
     * @param employeeCode
     */
    public void setEmployeeCode(java.lang.String employeeCode) {
        this.employeeCode = employeeCode;
    }


    /**
     * Gets the employeeName value for this AdditionalAuthorizationDetails.
     * 
     * @return employeeName
     */
    public java.lang.String getEmployeeName() {
        return employeeName;
    }


    /**
     * Sets the employeeName value for this AdditionalAuthorizationDetails.
     * 
     * @param employeeName
     */
    public void setEmployeeName(java.lang.String employeeName) {
        this.employeeName = employeeName;
    }


    /**
     * Gets the flgLogTODAuthDetails value for this AdditionalAuthorizationDetails.
     * 
     * @return flgLogTODAuthDetails
     */
    public java.lang.String getFlgLogTODAuthDetails() {
        return flgLogTODAuthDetails;
    }


    /**
     * Sets the flgLogTODAuthDetails value for this AdditionalAuthorizationDetails.
     * 
     * @param flgLogTODAuthDetails
     */
    public void setFlgLogTODAuthDetails(java.lang.String flgLogTODAuthDetails) {
        this.flgLogTODAuthDetails = flgLogTODAuthDetails;
    }


    /**
     * Gets the referenceNumber value for this AdditionalAuthorizationDetails.
     * 
     * @return referenceNumber
     */
    public java.lang.String getReferenceNumber() {
        return referenceNumber;
    }


    /**
     * Sets the referenceNumber value for this AdditionalAuthorizationDetails.
     * 
     * @param referenceNumber
     */
    public void setReferenceNumber(java.lang.String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }


    /**
     * Gets the systemTrailAuditNumber value for this AdditionalAuthorizationDetails.
     * 
     * @return systemTrailAuditNumber
     */
    public long getSystemTrailAuditNumber() {
        return systemTrailAuditNumber;
    }


    /**
     * Sets the systemTrailAuditNumber value for this AdditionalAuthorizationDetails.
     * 
     * @param systemTrailAuditNumber
     */
    public void setSystemTrailAuditNumber(long systemTrailAuditNumber) {
        this.systemTrailAuditNumber = systemTrailAuditNumber;
    }


    /**
     * Gets the tellerId value for this AdditionalAuthorizationDetails.
     * 
     * @return tellerId
     */
    public java.lang.String getTellerId() {
        return tellerId;
    }


    /**
     * Sets the tellerId value for this AdditionalAuthorizationDetails.
     * 
     * @param tellerId
     */
    public void setTellerId(java.lang.String tellerId) {
        this.tellerId = tellerId;
    }


    /**
     * Gets the todReason value for this AdditionalAuthorizationDetails.
     * 
     * @return todReason
     */
    public java.lang.String getTodReason() {
        return todReason;
    }


    /**
     * Sets the todReason value for this AdditionalAuthorizationDetails.
     * 
     * @param todReason
     */
    public void setTodReason(java.lang.String todReason) {
        this.todReason = todReason;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdditionalAuthorizationDetails)) return false;
        AdditionalAuthorizationDetails other = (AdditionalAuthorizationDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountNumber==null && other.getAccountNumber()==null) || 
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.approvalMatrixDetails==null && other.getApprovalMatrixDetails()==null) || 
             (this.approvalMatrixDetails!=null &&
              this.approvalMatrixDetails.equals(other.getApprovalMatrixDetails()))) &&
            this.batchNumber == other.getBatchNumber() &&
            this.branchCode == other.getBranchCode() &&
            ((this.employeeCode==null && other.getEmployeeCode()==null) || 
             (this.employeeCode!=null &&
              this.employeeCode.equals(other.getEmployeeCode()))) &&
            ((this.employeeName==null && other.getEmployeeName()==null) || 
             (this.employeeName!=null &&
              this.employeeName.equals(other.getEmployeeName()))) &&
            ((this.flgLogTODAuthDetails==null && other.getFlgLogTODAuthDetails()==null) || 
             (this.flgLogTODAuthDetails!=null &&
              this.flgLogTODAuthDetails.equals(other.getFlgLogTODAuthDetails()))) &&
            ((this.referenceNumber==null && other.getReferenceNumber()==null) || 
             (this.referenceNumber!=null &&
              this.referenceNumber.equals(other.getReferenceNumber()))) &&
            this.systemTrailAuditNumber == other.getSystemTrailAuditNumber() &&
            ((this.tellerId==null && other.getTellerId()==null) || 
             (this.tellerId!=null &&
              this.tellerId.equals(other.getTellerId()))) &&
            ((this.todReason==null && other.getTodReason()==null) || 
             (this.todReason!=null &&
              this.todReason.equals(other.getTodReason())));
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
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getApprovalMatrixDetails() != null) {
            _hashCode += getApprovalMatrixDetails().hashCode();
        }
        _hashCode += new Long(getBatchNumber()).hashCode();
        _hashCode += getBranchCode();
        if (getEmployeeCode() != null) {
            _hashCode += getEmployeeCode().hashCode();
        }
        if (getEmployeeName() != null) {
            _hashCode += getEmployeeName().hashCode();
        }
        if (getFlgLogTODAuthDetails() != null) {
            _hashCode += getFlgLogTODAuthDetails().hashCode();
        }
        if (getReferenceNumber() != null) {
            _hashCode += getReferenceNumber().hashCode();
        }
        _hashCode += new Long(getSystemTrailAuditNumber()).hashCode();
        if (getTellerId() != null) {
            _hashCode += getTellerId().hashCode();
        }
        if (getTodReason() != null) {
            _hashCode += getTodReason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdditionalAuthorizationDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "additionalAuthorizationDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvalMatrixDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvalMatrixDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "batchNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employeeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "employeeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employeeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "employeeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flgLogTODAuthDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flgLogTODAuthDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "referenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemTrailAuditNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemTrailAuditNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tellerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tellerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("todReason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "todReason"));
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
