/**
 * LiabilityScore.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class LiabilityScore  implements java.io.Serializable {
    private java.math.BigDecimal liability;

    private java.math.BigDecimal score;

    private java.math.BigDecimal scorecode;

    public LiabilityScore() {
    }

    public LiabilityScore(
           java.math.BigDecimal liability,
           java.math.BigDecimal score,
           java.math.BigDecimal scorecode) {
           this.liability = liability;
           this.score = score;
           this.scorecode = scorecode;
    }


    /**
     * Gets the liability value for this LiabilityScore.
     * 
     * @return liability
     */
    public java.math.BigDecimal getLiability() {
        return liability;
    }


    /**
     * Sets the liability value for this LiabilityScore.
     * 
     * @param liability
     */
    public void setLiability(java.math.BigDecimal liability) {
        this.liability = liability;
    }


    /**
     * Gets the score value for this LiabilityScore.
     * 
     * @return score
     */
    public java.math.BigDecimal getScore() {
        return score;
    }


    /**
     * Sets the score value for this LiabilityScore.
     * 
     * @param score
     */
    public void setScore(java.math.BigDecimal score) {
        this.score = score;
    }


    /**
     * Gets the scorecode value for this LiabilityScore.
     * 
     * @return scorecode
     */
    public java.math.BigDecimal getScorecode() {
        return scorecode;
    }


    /**
     * Sets the scorecode value for this LiabilityScore.
     * 
     * @param scorecode
     */
    public void setScorecode(java.math.BigDecimal scorecode) {
        this.scorecode = scorecode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiabilityScore)) return false;
        LiabilityScore other = (LiabilityScore) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.liability==null && other.getLiability()==null) || 
             (this.liability!=null &&
              this.liability.equals(other.getLiability()))) &&
            ((this.score==null && other.getScore()==null) || 
             (this.score!=null &&
              this.score.equals(other.getScore()))) &&
            ((this.scorecode==null && other.getScorecode()==null) || 
             (this.scorecode!=null &&
              this.scorecode.equals(other.getScorecode())));
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
        if (getLiability() != null) {
            _hashCode += getLiability().hashCode();
        }
        if (getScore() != null) {
            _hashCode += getScore().hashCode();
        }
        if (getScorecode() != null) {
            _hashCode += getScorecode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiabilityScore.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityScore"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liability");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liability"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("score");
        elemField.setXmlName(new javax.xml.namespace.QName("", "score"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scorecode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scorecode"));
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
