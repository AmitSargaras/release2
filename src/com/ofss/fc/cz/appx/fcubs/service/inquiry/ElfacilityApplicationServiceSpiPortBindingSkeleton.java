/**
 * ElfacilityApplicationServiceSpiPortBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class ElfacilityApplicationServiceSpiPortBindingSkeleton implements com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi, org.apache.axis.wsdl.Skeleton {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "sessionContext"), com.ofss.fc.app.context.SessionContext.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSRequestDTO"), com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "workItemViewObjectDTO"), com.ofss.fc.framework.domain.WorkItemViewObjectDTO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("doQueryFacilityIO", _params, new javax.xml.namespace.QName("", "return"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSResponseDTO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "doQueryFacilityIO"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("doQueryFacilityIO") == null) {
            _myOperations.put("doQueryFacilityIO", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("doQueryFacilityIO")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("FatalException");
        _fault.setQName(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "FatalException"));
        _fault.setClassName("com.ofss.fc.infra.exception.FatalExceptionBean");
        _fault.setXmlType(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "fatalExceptionBean"));
        _oper.addFault(_fault);
    }

    public ElfacilityApplicationServiceSpiPortBindingSkeleton() {
        this.impl = new com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingImpl();
    }

    public ElfacilityApplicationServiceSpiPortBindingSkeleton(com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi impl) {
        this.impl = impl;
    }
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO doQueryFacilityIO(com.ofss.fc.app.context.SessionContext arg0, com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO arg1, com.ofss.fc.framework.domain.WorkItemViewObjectDTO[] arg2) throws java.rmi.RemoteException, com.ofss.fc.infra.exception.FatalExceptionBean
    {
        com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO ret = impl.doQueryFacilityIO(arg0, arg1, arg2);
        return ret;
    }

}
