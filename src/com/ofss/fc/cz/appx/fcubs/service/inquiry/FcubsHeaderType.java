/**
 * FcubsHeaderType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FcubsHeaderType  implements java.io.Serializable {
    private java.lang.String action;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam[] addl;

    private java.lang.String branch;

    private java.lang.String correlid;

    private java.lang.String destination;

    private java.lang.String functionid;

    private java.lang.String moduleid;

    private java.lang.String msgid;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.MsgStatType msgstat;

    private java.lang.String multitripid;

    private java.lang.String operation;

    private java.lang.String service;

    private java.lang.String source;

    private java.lang.String sourceoperation;

    private java.lang.String sourceuserid;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType ubscomp;

    private java.lang.String userid;

    public FcubsHeaderType() {
    }

    public FcubsHeaderType(
           java.lang.String action,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam[] addl,
           java.lang.String branch,
           java.lang.String correlid,
           java.lang.String destination,
           java.lang.String functionid,
           java.lang.String moduleid,
           java.lang.String msgid,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.MsgStatType msgstat,
           java.lang.String multitripid,
           java.lang.String operation,
           java.lang.String service,
           java.lang.String source,
           java.lang.String sourceoperation,
           java.lang.String sourceuserid,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType ubscomp,
           java.lang.String userid) {
           this.action = action;
           this.addl = addl;
           this.branch = branch;
           this.correlid = correlid;
           this.destination = destination;
           this.functionid = functionid;
           this.moduleid = moduleid;
           this.msgid = msgid;
           this.msgstat = msgstat;
           this.multitripid = multitripid;
           this.operation = operation;
           this.service = service;
           this.source = source;
           this.sourceoperation = sourceoperation;
           this.sourceuserid = sourceuserid;
           this.ubscomp = ubscomp;
           this.userid = userid;
    }


    /**
     * Gets the action value for this FcubsHeaderType.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this FcubsHeaderType.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the addl value for this FcubsHeaderType.
     * 
     * @return addl
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam[] getAddl() {
        return addl;
    }


    /**
     * Sets the addl value for this FcubsHeaderType.
     * 
     * @param addl
     */
    public void setAddl(com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam[] addl) {
        this.addl = addl;
    }


    /**
     * Gets the branch value for this FcubsHeaderType.
     * 
     * @return branch
     */
    public java.lang.String getBranch() {
        return branch;
    }


    /**
     * Sets the branch value for this FcubsHeaderType.
     * 
     * @param branch
     */
    public void setBranch(java.lang.String branch) {
        this.branch = branch;
    }


    /**
     * Gets the correlid value for this FcubsHeaderType.
     * 
     * @return correlid
     */
    public java.lang.String getCorrelid() {
        return correlid;
    }


    /**
     * Sets the correlid value for this FcubsHeaderType.
     * 
     * @param correlid
     */
    public void setCorrelid(java.lang.String correlid) {
        this.correlid = correlid;
    }


    /**
     * Gets the destination value for this FcubsHeaderType.
     * 
     * @return destination
     */
    public java.lang.String getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this FcubsHeaderType.
     * 
     * @param destination
     */
    public void setDestination(java.lang.String destination) {
        this.destination = destination;
    }


    /**
     * Gets the functionid value for this FcubsHeaderType.
     * 
     * @return functionid
     */
    public java.lang.String getFunctionid() {
        return functionid;
    }


    /**
     * Sets the functionid value for this FcubsHeaderType.
     * 
     * @param functionid
     */
    public void setFunctionid(java.lang.String functionid) {
        this.functionid = functionid;
    }


    /**
     * Gets the moduleid value for this FcubsHeaderType.
     * 
     * @return moduleid
     */
    public java.lang.String getModuleid() {
        return moduleid;
    }


    /**
     * Sets the moduleid value for this FcubsHeaderType.
     * 
     * @param moduleid
     */
    public void setModuleid(java.lang.String moduleid) {
        this.moduleid = moduleid;
    }


    /**
     * Gets the msgid value for this FcubsHeaderType.
     * 
     * @return msgid
     */
    public java.lang.String getMsgid() {
        return msgid;
    }


    /**
     * Sets the msgid value for this FcubsHeaderType.
     * 
     * @param msgid
     */
    public void setMsgid(java.lang.String msgid) {
        this.msgid = msgid;
    }


    /**
     * Gets the msgstat value for this FcubsHeaderType.
     * 
     * @return msgstat
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.MsgStatType getMsgstat() {
        return msgstat;
    }


    /**
     * Sets the msgstat value for this FcubsHeaderType.
     * 
     * @param msgstat
     */
    public void setMsgstat(com.ofss.fc.cz.appx.fcubs.service.inquiry.MsgStatType msgstat) {
        this.msgstat = msgstat;
    }


    /**
     * Gets the multitripid value for this FcubsHeaderType.
     * 
     * @return multitripid
     */
    public java.lang.String getMultitripid() {
        return multitripid;
    }


    /**
     * Sets the multitripid value for this FcubsHeaderType.
     * 
     * @param multitripid
     */
    public void setMultitripid(java.lang.String multitripid) {
        this.multitripid = multitripid;
    }


    /**
     * Gets the operation value for this FcubsHeaderType.
     * 
     * @return operation
     */
    public java.lang.String getOperation() {
        return operation;
    }


    /**
     * Sets the operation value for this FcubsHeaderType.
     * 
     * @param operation
     */
    public void setOperation(java.lang.String operation) {
        this.operation = operation;
    }


    /**
     * Gets the service value for this FcubsHeaderType.
     * 
     * @return service
     */
    public java.lang.String getService() {
        return service;
    }


    /**
     * Sets the service value for this FcubsHeaderType.
     * 
     * @param service
     */
    public void setService(java.lang.String service) {
        this.service = service;
    }


    /**
     * Gets the source value for this FcubsHeaderType.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this FcubsHeaderType.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the sourceoperation value for this FcubsHeaderType.
     * 
     * @return sourceoperation
     */
    public java.lang.String getSourceoperation() {
        return sourceoperation;
    }


    /**
     * Sets the sourceoperation value for this FcubsHeaderType.
     * 
     * @param sourceoperation
     */
    public void setSourceoperation(java.lang.String sourceoperation) {
        this.sourceoperation = sourceoperation;
    }


    /**
     * Gets the sourceuserid value for this FcubsHeaderType.
     * 
     * @return sourceuserid
     */
    public java.lang.String getSourceuserid() {
        return sourceuserid;
    }


    /**
     * Sets the sourceuserid value for this FcubsHeaderType.
     * 
     * @param sourceuserid
     */
    public void setSourceuserid(java.lang.String sourceuserid) {
        this.sourceuserid = sourceuserid;
    }


    /**
     * Gets the ubscomp value for this FcubsHeaderType.
     * 
     * @return ubscomp
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType getUbscomp() {
        return ubscomp;
    }


    /**
     * Sets the ubscomp value for this FcubsHeaderType.
     * 
     * @param ubscomp
     */
    public void setUbscomp(com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType ubscomp) {
        this.ubscomp = ubscomp;
    }


    /**
     * Gets the userid value for this FcubsHeaderType.
     * 
     * @return userid
     */
    public java.lang.String getUserid() {
        return userid;
    }


    /**
     * Sets the userid value for this FcubsHeaderType.
     * 
     * @param userid
     */
    public void setUserid(java.lang.String userid) {
        this.userid = userid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FcubsHeaderType)) return false;
        FcubsHeaderType other = (FcubsHeaderType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.addl==null && other.getAddl()==null) || 
             (this.addl!=null &&
              java.util.Arrays.equals(this.addl, other.getAddl()))) &&
            ((this.branch==null && other.getBranch()==null) || 
             (this.branch!=null &&
              this.branch.equals(other.getBranch()))) &&
            ((this.correlid==null && other.getCorrelid()==null) || 
             (this.correlid!=null &&
              this.correlid.equals(other.getCorrelid()))) &&
            ((this.destination==null && other.getDestination()==null) || 
             (this.destination!=null &&
              this.destination.equals(other.getDestination()))) &&
            ((this.functionid==null && other.getFunctionid()==null) || 
             (this.functionid!=null &&
              this.functionid.equals(other.getFunctionid()))) &&
            ((this.moduleid==null && other.getModuleid()==null) || 
             (this.moduleid!=null &&
              this.moduleid.equals(other.getModuleid()))) &&
            ((this.msgid==null && other.getMsgid()==null) || 
             (this.msgid!=null &&
              this.msgid.equals(other.getMsgid()))) &&
            ((this.msgstat==null && other.getMsgstat()==null) || 
             (this.msgstat!=null &&
              this.msgstat.equals(other.getMsgstat()))) &&
            ((this.multitripid==null && other.getMultitripid()==null) || 
             (this.multitripid!=null &&
              this.multitripid.equals(other.getMultitripid()))) &&
            ((this.operation==null && other.getOperation()==null) || 
             (this.operation!=null &&
              this.operation.equals(other.getOperation()))) &&
            ((this.service==null && other.getService()==null) || 
             (this.service!=null &&
              this.service.equals(other.getService()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.sourceoperation==null && other.getSourceoperation()==null) || 
             (this.sourceoperation!=null &&
              this.sourceoperation.equals(other.getSourceoperation()))) &&
            ((this.sourceuserid==null && other.getSourceuserid()==null) || 
             (this.sourceuserid!=null &&
              this.sourceuserid.equals(other.getSourceuserid()))) &&
            ((this.ubscomp==null && other.getUbscomp()==null) || 
             (this.ubscomp!=null &&
              this.ubscomp.equals(other.getUbscomp()))) &&
            ((this.userid==null && other.getUserid()==null) || 
             (this.userid!=null &&
              this.userid.equals(other.getUserid())));
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
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getAddl() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddl());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddl(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBranch() != null) {
            _hashCode += getBranch().hashCode();
        }
        if (getCorrelid() != null) {
            _hashCode += getCorrelid().hashCode();
        }
        if (getDestination() != null) {
            _hashCode += getDestination().hashCode();
        }
        if (getFunctionid() != null) {
            _hashCode += getFunctionid().hashCode();
        }
        if (getModuleid() != null) {
            _hashCode += getModuleid().hashCode();
        }
        if (getMsgid() != null) {
            _hashCode += getMsgid().hashCode();
        }
        if (getMsgstat() != null) {
            _hashCode += getMsgstat().hashCode();
        }
        if (getMultitripid() != null) {
            _hashCode += getMultitripid().hashCode();
        }
        if (getOperation() != null) {
            _hashCode += getOperation().hashCode();
        }
        if (getService() != null) {
            _hashCode += getService().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getSourceoperation() != null) {
            _hashCode += getSourceoperation().hashCode();
        }
        if (getSourceuserid() != null) {
            _hashCode += getSourceuserid().hashCode();
        }
        if (getUbscomp() != null) {
            _hashCode += getUbscomp().hashCode();
        }
        if (getUserid() != null) {
            _hashCode += getUserid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FcubsHeaderType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsHeaderType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">>fcubsHeaderType>addl>param"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "param"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branch");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correlid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correlid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destination");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destination"));
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
        elemField.setFieldName("moduleid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "moduleid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msgid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgstat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msgstat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "msgStatType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multitripid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multitripid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("", "source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceoperation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sourceoperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceuserid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sourceuserid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ubscomp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ubscomp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "ubscompType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userid"));
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
