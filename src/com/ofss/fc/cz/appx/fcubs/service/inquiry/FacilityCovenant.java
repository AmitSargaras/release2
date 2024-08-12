/**
 * FacilityCovenant.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityCovenant  implements java.io.Serializable {
    private java.math.BigDecimal covenantid;

    private java.lang.String covenantrefno;

    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal gracedays;

    private java.lang.String mandatory;

    private java.math.BigDecimal modno;

    private java.math.BigDecimal noticedays;

    private java.lang.String period;

    private java.lang.String periodcity;

    private java.lang.String remarks;

    private java.lang.String revisiondate;

    private java.lang.String startdate;

    public FacilityCovenant() {
    }

    public FacilityCovenant(
           java.math.BigDecimal covenantid,
           java.lang.String covenantrefno,
           java.math.BigDecimal facilityid,
           java.math.BigDecimal gracedays,
           java.lang.String mandatory,
           java.math.BigDecimal modno,
           java.math.BigDecimal noticedays,
           java.lang.String period,
           java.lang.String periodcity,
           java.lang.String remarks,
           java.lang.String revisiondate,
           java.lang.String startdate) {
           this.covenantid = covenantid;
           this.covenantrefno = covenantrefno;
           this.facilityid = facilityid;
           this.gracedays = gracedays;
           this.mandatory = mandatory;
           this.modno = modno;
           this.noticedays = noticedays;
           this.period = period;
           this.periodcity = periodcity;
           this.remarks = remarks;
           this.revisiondate = revisiondate;
           this.startdate = startdate;
    }


    /**
     * Gets the covenantid value for this FacilityCovenant.
     * 
     * @return covenantid
     */
    public java.math.BigDecimal getCovenantid() {
        return covenantid;
    }


    /**
     * Sets the covenantid value for this FacilityCovenant.
     * 
     * @param covenantid
     */
    public void setCovenantid(java.math.BigDecimal covenantid) {
        this.covenantid = covenantid;
    }


    /**
     * Gets the covenantrefno value for this FacilityCovenant.
     * 
     * @return covenantrefno
     */
    public java.lang.String getCovenantrefno() {
        return covenantrefno;
    }


    /**
     * Sets the covenantrefno value for this FacilityCovenant.
     * 
     * @param covenantrefno
     */
    public void setCovenantrefno(java.lang.String covenantrefno) {
        this.covenantrefno = covenantrefno;
    }


    /**
     * Gets the facilityid value for this FacilityCovenant.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityCovenant.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the gracedays value for this FacilityCovenant.
     * 
     * @return gracedays
     */
    public java.math.BigDecimal getGracedays() {
        return gracedays;
    }


    /**
     * Sets the gracedays value for this FacilityCovenant.
     * 
     * @param gracedays
     */
    public void setGracedays(java.math.BigDecimal gracedays) {
        this.gracedays = gracedays;
    }


    /**
     * Gets the mandatory value for this FacilityCovenant.
     * 
     * @return mandatory
     */
    public java.lang.String getMandatory() {
        return mandatory;
    }


    /**
     * Sets the mandatory value for this FacilityCovenant.
     * 
     * @param mandatory
     */
    public void setMandatory(java.lang.String mandatory) {
        this.mandatory = mandatory;
    }


    /**
     * Gets the modno value for this FacilityCovenant.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this FacilityCovenant.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }


    /**
     * Gets the noticedays value for this FacilityCovenant.
     * 
     * @return noticedays
     */
    public java.math.BigDecimal getNoticedays() {
        return noticedays;
    }


    /**
     * Sets the noticedays value for this FacilityCovenant.
     * 
     * @param noticedays
     */
    public void setNoticedays(java.math.BigDecimal noticedays) {
        this.noticedays = noticedays;
    }


    /**
     * Gets the period value for this FacilityCovenant.
     * 
     * @return period
     */
    public java.lang.String getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this FacilityCovenant.
     * 
     * @param period
     */
    public void setPeriod(java.lang.String period) {
        this.period = period;
    }


    /**
     * Gets the periodcity value for this FacilityCovenant.
     * 
     * @return periodcity
     */
    public java.lang.String getPeriodcity() {
        return periodcity;
    }


    /**
     * Sets the periodcity value for this FacilityCovenant.
     * 
     * @param periodcity
     */
    public void setPeriodcity(java.lang.String periodcity) {
        this.periodcity = periodcity;
    }


    /**
     * Gets the remarks value for this FacilityCovenant.
     * 
     * @return remarks
     */
    public java.lang.String getRemarks() {
        return remarks;
    }


    /**
     * Sets the remarks value for this FacilityCovenant.
     * 
     * @param remarks
     */
    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }


    /**
     * Gets the revisiondate value for this FacilityCovenant.
     * 
     * @return revisiondate
     */
    public java.lang.String getRevisiondate() {
        return revisiondate;
    }


    /**
     * Sets the revisiondate value for this FacilityCovenant.
     * 
     * @param revisiondate
     */
    public void setRevisiondate(java.lang.String revisiondate) {
        this.revisiondate = revisiondate;
    }


    /**
     * Gets the startdate value for this FacilityCovenant.
     * 
     * @return startdate
     */
    public java.lang.String getStartdate() {
        return startdate;
    }


    /**
     * Sets the startdate value for this FacilityCovenant.
     * 
     * @param startdate
     */
    public void setStartdate(java.lang.String startdate) {
        this.startdate = startdate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityCovenant)) return false;
        FacilityCovenant other = (FacilityCovenant) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.covenantid==null && other.getCovenantid()==null) || 
             (this.covenantid!=null &&
              this.covenantid.equals(other.getCovenantid()))) &&
            ((this.covenantrefno==null && other.getCovenantrefno()==null) || 
             (this.covenantrefno!=null &&
              this.covenantrefno.equals(other.getCovenantrefno()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.gracedays==null && other.getGracedays()==null) || 
             (this.gracedays!=null &&
              this.gracedays.equals(other.getGracedays()))) &&
            ((this.mandatory==null && other.getMandatory()==null) || 
             (this.mandatory!=null &&
              this.mandatory.equals(other.getMandatory()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno()))) &&
            ((this.noticedays==null && other.getNoticedays()==null) || 
             (this.noticedays!=null &&
              this.noticedays.equals(other.getNoticedays()))) &&
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod()))) &&
            ((this.periodcity==null && other.getPeriodcity()==null) || 
             (this.periodcity!=null &&
              this.periodcity.equals(other.getPeriodcity()))) &&
            ((this.remarks==null && other.getRemarks()==null) || 
             (this.remarks!=null &&
              this.remarks.equals(other.getRemarks()))) &&
            ((this.revisiondate==null && other.getRevisiondate()==null) || 
             (this.revisiondate!=null &&
              this.revisiondate.equals(other.getRevisiondate()))) &&
            ((this.startdate==null && other.getStartdate()==null) || 
             (this.startdate!=null &&
              this.startdate.equals(other.getStartdate())));
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
        if (getCovenantid() != null) {
            _hashCode += getCovenantid().hashCode();
        }
        if (getCovenantrefno() != null) {
            _hashCode += getCovenantrefno().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getGracedays() != null) {
            _hashCode += getGracedays().hashCode();
        }
        if (getMandatory() != null) {
            _hashCode += getMandatory().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        if (getNoticedays() != null) {
            _hashCode += getNoticedays().hashCode();
        }
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        if (getPeriodcity() != null) {
            _hashCode += getPeriodcity().hashCode();
        }
        if (getRemarks() != null) {
            _hashCode += getRemarks().hashCode();
        }
        if (getRevisiondate() != null) {
            _hashCode += getRevisiondate().hashCode();
        }
        if (getStartdate() != null) {
            _hashCode += getStartdate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityCovenant.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCovenant"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("covenantid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "covenantid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("covenantrefno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "covenantrefno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gracedays");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gracedays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noticedays");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noticedays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("period");
        elemField.setXmlName(new javax.xml.namespace.QName("", "period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodcity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodcity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "remarks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revisiondate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revisiondate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
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
