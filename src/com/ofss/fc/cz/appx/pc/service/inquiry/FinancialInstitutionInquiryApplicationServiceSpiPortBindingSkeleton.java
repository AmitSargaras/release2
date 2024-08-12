/**
 * FinancialInstitutionInquiryApplicationServiceSpiPortBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.pc.service.inquiry;

public class FinancialInstitutionInquiryApplicationServiceSpiPortBindingSkeleton implements com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi, org.apache.axis.wsdl.Skeleton {
    private com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryDTO"), com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryDTO.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "workItemViewObjectDTO"), com.ofss.fc.framework.domain.WorkItemViewObjectDTO[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("fetchAllFinancialInstitutions", _params, new javax.xml.namespace.QName("", "return"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "financialInstitutionInquiryResDTO"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "fetchAllFinancialInstitutions"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("fetchAllFinancialInstitutions") == null) {
            _myOperations.put("fetchAllFinancialInstitutions", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("fetchAllFinancialInstitutions")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("FatalException");
        _fault.setQName(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "FatalException"));
        _fault.setClassName("com.ofss.fc.infra.exception.FatalExceptionBean");
        _fault.setXmlType(new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "fatalExceptionBean"));
        _oper.addFault(_fault);
    }

    public FinancialInstitutionInquiryApplicationServiceSpiPortBindingSkeleton() {
        this.impl = new com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingImpl();
    }

    public FinancialInstitutionInquiryApplicationServiceSpiPortBindingSkeleton(com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi impl) {
        this.impl = impl;
    }
    public com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryResDTO fetchAllFinancialInstitutions(com.ofss.fc.app.context.SessionContext arg0, com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryDTO arg1, com.ofss.fc.framework.domain.WorkItemViewObjectDTO[] arg2) throws java.rmi.RemoteException, com.ofss.fc.infra.exception.FatalExceptionBean
    {
        com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryResDTO ret = impl.fetchAllFinancialInstitutions(arg0, arg1, arg2);
        return ret;
    }

}
