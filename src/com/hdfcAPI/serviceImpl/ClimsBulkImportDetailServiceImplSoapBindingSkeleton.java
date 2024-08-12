/**
 * ClimsBulkImportDetailServiceImplSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hdfcAPI.serviceImpl;

public class ClimsBulkImportDetailServiceImplSoapBindingSkeleton implements com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl, org.apache.axis.wsdl.Skeleton {
    private com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://serviceImpl.hdfcAPI.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://bean.hdfcAPI.com", "ClimesBorrowerVo"), com.hdfcAPI.bean.ClimesBorrowerVo.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("updateBorrowerDetail", _params, new javax.xml.namespace.QName("http://serviceImpl.hdfcAPI.com", "updateBorrowerDetailReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://bean.hdfcAPI.com", "ClimesBorrowerResponseVo"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://serviceImpl.hdfcAPI.com", "updateBorrowerDetail"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("updateBorrowerDetail") == null) {
            _myOperations.put("updateBorrowerDetail", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("updateBorrowerDetail")).add(_oper);
    }

    public ClimsBulkImportDetailServiceImplSoapBindingSkeleton() {
        this.impl = new com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingImpl();
    }

    public ClimsBulkImportDetailServiceImplSoapBindingSkeleton(com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl impl) {
        this.impl = impl;
    }
    public com.hdfcAPI.bean.ClimesBorrowerResponseVo updateBorrowerDetail(com.hdfcAPI.bean.ClimesBorrowerVo request) throws java.rmi.RemoteException
    {
        com.hdfcAPI.bean.ClimesBorrowerResponseVo ret = impl.updateBorrowerDetail(request);
        return ret;
    }

}
