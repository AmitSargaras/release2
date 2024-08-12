/**
 * FixedDepositInquiryWrapperPortBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class FixedDepositInquiryWrapperPortBindingSkeleton implements com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper, org.apache.axis.wsdl.Skeleton {
    private com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sessionContext"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "sessionContext"), com.iflex.fcr.xface.td.inq.SessionContext.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "param"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "fixedDepositInquiryRequestDTO"), com.iflex.fcr.xface.td.inq.FixedDepositInquiryRequestDTO.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getFixedDepositDetails", _params, new javax.xml.namespace.QName("", "return"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "fixedDepositInquiryResponseDTO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "getFixedDepositDetails"));
        _myOperationsList.add(_oper);
        if (_myOperations.get("getFixedDepositDetails") == null) {
            _myOperations.put("getFixedDepositDetails", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getFixedDepositDetails")).add(_oper);
    }

    public FixedDepositInquiryWrapperPortBindingSkeleton() {
        this.impl = new com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingImpl();
    }

    public FixedDepositInquiryWrapperPortBindingSkeleton(com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper impl) {
        this.impl = impl;
    }
    public com.iflex.fcr.xface.td.inq.FixedDepositInquiryResponseDTO getFixedDepositDetails(com.iflex.fcr.xface.td.inq.SessionContext sessionContext, com.iflex.fcr.xface.td.inq.FixedDepositInquiryRequestDTO param) throws java.rmi.RemoteException
    {
        com.iflex.fcr.xface.td.inq.FixedDepositInquiryResponseDTO ret = impl.getFixedDepositDetails(sessionContext, param);
        return ret;
    }

}
