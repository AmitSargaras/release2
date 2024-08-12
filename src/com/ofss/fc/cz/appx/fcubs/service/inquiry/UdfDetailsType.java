/**
 * UdfDetailsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class UdfDetailsType  implements java.io.Serializable {
    private java.lang.String datatype;

    private java.lang.String fieldname;

    private java.lang.String fieldvalue;

    private java.lang.String functionid;

    private java.lang.String valtype;

    public UdfDetailsType() {
    }

    public UdfDetailsType(
           java.lang.String datatype,
           java.lang.String fieldname,
           java.lang.String fieldvalue,
           java.lang.String functionid,
           java.lang.String valtype) {
           this.datatype = datatype;
           this.fieldname = fieldname;
           this.fieldvalue = fieldvalue;
           this.functionid = functionid;
           this.valtype = valtype;
    }


    /**
     * Gets the datatype value for this UdfDetailsType.
     * 
     * @return datatype
     */
    public java.lang.String getDatatype() {
        return datatype;
    }


    /**
     * Sets the datatype value for this UdfDetailsType.
     * 
     * @param datatype
     */
    public void setDatatype(java.lang.String datatype) {
        this.datatype = datatype;
    }


    /**
     * Gets the fieldname value for this UdfDetailsType.
     * 
     * @return fieldname
     */
    public java.lang.String getFieldname() {
        return fieldname;
    }


    /**
     * Sets the fieldname value for this UdfDetailsType.
     * 
     * @param fieldname
     */
    public void setFieldname(java.lang.String fieldname) {
        this.fieldname = fieldname;
    }


    /**
     * Gets the fieldvalue value for this UdfDetailsType.
     * 
     * @return fieldvalue
     */
    public java.lang.String getFieldvalue() {
        return fieldvalue;
    }


    /**
     * Sets the fieldvalue value for this UdfDetailsType.
     * 
     * @param fieldvalue
     */
    public void setFieldvalue(java.lang.String fieldvalue) {
        this.fieldvalue = fieldvalue;
    }


    /**
     * Gets the functionid value for this UdfDetailsType.
     * 
     * @return functionid
     */
    public java.lang.String getFunctionid() {
        return functionid;
    }


    /**
     * Sets the functionid value for this UdfDetailsType.
     * 
     * @param functionid
     */
    public void setFunctionid(java.lang.String functionid) {
        this.functionid = functionid;
    }


    /**
     * Gets the valtype value for this UdfDetailsType.
     * 
     * @return valtype
     */
    public java.lang.String getValtype() {
        return valtype;
    }


    /**
     * Sets the valtype value for this UdfDetailsType.
     * 
     * @param valtype
     */
    public void setValtype(java.lang.String valtype) {
        this.valtype = valtype;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UdfDetailsType)) return false;
        UdfDetailsType other = (UdfDetailsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datatype==null && other.getDatatype()==null) || 
             (this.datatype!=null &&
              this.datatype.equals(other.getDatatype()))) &&
            ((this.fieldname==null && other.getFieldname()==null) || 
             (this.fieldname!=null &&
              this.fieldname.equals(other.getFieldname()))) &&
            ((this.fieldvalue==null && other.getFieldvalue()==null) || 
             (this.fieldvalue!=null &&
              this.fieldvalue.equals(other.getFieldvalue()))) &&
            ((this.functionid==null && other.getFunctionid()==null) || 
             (this.functionid!=null &&
              this.functionid.equals(other.getFunctionid()))) &&
            ((this.valtype==null && other.getValtype()==null) || 
             (this.valtype!=null &&
              this.valtype.equals(other.getValtype())));
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
        if (getDatatype() != null) {
            _hashCode += getDatatype().hashCode();
        }
        if (getFieldname() != null) {
            _hashCode += getFieldname().hashCode();
        }
        if (getFieldvalue() != null) {
            _hashCode += getFieldvalue().hashCode();
        }
        if (getFunctionid() != null) {
            _hashCode += getFunctionid().hashCode();
        }
        if (getValtype() != null) {
            _hashCode += getValtype().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UdfDetailsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "udfDetailsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datatype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datatype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldvalue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldvalue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("functionid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "functionid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valtype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valtype"));
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
