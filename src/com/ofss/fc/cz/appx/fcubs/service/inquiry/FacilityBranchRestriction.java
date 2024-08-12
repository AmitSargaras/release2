/**
 * FacilityBranchRestriction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityBranchRestriction  implements java.io.Serializable {
    private java.lang.String branchcode;

    private java.lang.String facilityid;

    public FacilityBranchRestriction() {
    }

    public FacilityBranchRestriction(
           java.lang.String branchcode,
           java.lang.String facilityid) {
           this.branchcode = branchcode;
           this.facilityid = facilityid;
    }


    /**
     * Gets the branchcode value for this FacilityBranchRestriction.
     * 
     * @return branchcode
     */
    public java.lang.String getBranchcode() {
        return branchcode;
    }


    /**
     * Sets the branchcode value for this FacilityBranchRestriction.
     * 
     * @param branchcode
     */
    public void setBranchcode(java.lang.String branchcode) {
        this.branchcode = branchcode;
    }


    /**
     * Gets the facilityid value for this FacilityBranchRestriction.
     * 
     * @return facilityid
     */
    public java.lang.String getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityBranchRestriction.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.lang.String facilityid) {
        this.facilityid = facilityid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityBranchRestriction)) return false;
        FacilityBranchRestriction other = (FacilityBranchRestriction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.branchcode==null && other.getBranchcode()==null) || 
             (this.branchcode!=null &&
              this.branchcode.equals(other.getBranchcode()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid())));
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
        if (getBranchcode() != null) {
            _hashCode += getBranchcode().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityBranchRestriction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityBranchRestriction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
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
