/**
 * FacilityDescriptionPKTypeDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityDescriptionPKTypeDTO  extends com.ofss.fc.framework.domain.common.dto.DataTransferObject  implements java.io.Serializable {
    private java.lang.String liabid;

    private java.lang.String liabno;

    private java.lang.String linecode;

    private java.math.BigDecimal lineserial;

    public FacilityDescriptionPKTypeDTO() {
    }

    public FacilityDescriptionPKTypeDTO(
           com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray,
           java.lang.String liabid,
           java.lang.String liabno,
           java.lang.String linecode,
           java.math.BigDecimal lineserial) {
        super(
            dictionaryArray);
        this.liabid = liabid;
        this.liabno = liabno;
        this.linecode = linecode;
        this.lineserial = lineserial;
    }


    /**
     * Gets the liabid value for this FacilityDescriptionPKTypeDTO.
     * 
     * @return liabid
     */
    public java.lang.String getLiabid() {
        return liabid;
    }


    /**
     * Sets the liabid value for this FacilityDescriptionPKTypeDTO.
     * 
     * @param liabid
     */
    public void setLiabid(java.lang.String liabid) {
        this.liabid = liabid;
    }


    /**
     * Gets the liabno value for this FacilityDescriptionPKTypeDTO.
     * 
     * @return liabno
     */
    public java.lang.String getLiabno() {
        return liabno;
    }


    /**
     * Sets the liabno value for this FacilityDescriptionPKTypeDTO.
     * 
     * @param liabno
     */
    public void setLiabno(java.lang.String liabno) {
        this.liabno = liabno;
    }


    /**
     * Gets the linecode value for this FacilityDescriptionPKTypeDTO.
     * 
     * @return linecode
     */
    public java.lang.String getLinecode() {
        return linecode;
    }


    /**
     * Sets the linecode value for this FacilityDescriptionPKTypeDTO.
     * 
     * @param linecode
     */
    public void setLinecode(java.lang.String linecode) {
        this.linecode = linecode;
    }


    /**
     * Gets the lineserial value for this FacilityDescriptionPKTypeDTO.
     * 
     * @return lineserial
     */
    public java.math.BigDecimal getLineserial() {
        return lineserial;
    }


    /**
     * Sets the lineserial value for this FacilityDescriptionPKTypeDTO.
     * 
     * @param lineserial
     */
    public void setLineserial(java.math.BigDecimal lineserial) {
        this.lineserial = lineserial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityDescriptionPKTypeDTO)) return false;
        FacilityDescriptionPKTypeDTO other = (FacilityDescriptionPKTypeDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.liabid==null && other.getLiabid()==null) || 
             (this.liabid!=null &&
              this.liabid.equals(other.getLiabid()))) &&
            ((this.liabno==null && other.getLiabno()==null) || 
             (this.liabno!=null &&
              this.liabno.equals(other.getLiabno()))) &&
            ((this.linecode==null && other.getLinecode()==null) || 
             (this.linecode!=null &&
              this.linecode.equals(other.getLinecode()))) &&
            ((this.lineserial==null && other.getLineserial()==null) || 
             (this.lineserial!=null &&
              this.lineserial.equals(other.getLineserial())));
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
        if (getLiabid() != null) {
            _hashCode += getLiabid().hashCode();
        }
        if (getLiabno() != null) {
            _hashCode += getLiabno().hashCode();
        }
        if (getLinecode() != null) {
            _hashCode += getLinecode().hashCode();
        }
        if (getLineserial() != null) {
            _hashCode += getLineserial().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityDescriptionPKTypeDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityDescriptionPKTypeDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linecode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineserial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineserial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
