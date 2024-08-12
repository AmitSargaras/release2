/**
 * FinancialInstitutionInquiryApplicationServiceSpiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.pc.service.inquiry;

public class FinancialInstitutionInquiryApplicationServiceSpiServiceLocator extends org.apache.axis.client.Service implements com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiService {

    public FinancialInstitutionInquiryApplicationServiceSpiServiceLocator() {
    }


    public FinancialInstitutionInquiryApplicationServiceSpiServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FinancialInstitutionInquiryApplicationServiceSpiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FinancialInstitutionInquiryApplicationServiceSpiPort
    private java.lang.String FinancialInstitutionInquiryApplicationServiceSpiPort_address = "http://10.226.163.7:8002/com.ofss.fc.cz.hdfc.obp.webservice/FinancialInstitutionInquiry";

    public java.lang.String getFinancialInstitutionInquiryApplicationServiceSpiPortAddress() {
        return FinancialInstitutionInquiryApplicationServiceSpiPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName = "FinancialInstitutionInquiryApplicationServiceSpiPort";

    public java.lang.String getFinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName() {
        return FinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName;
    }

    public void setFinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName(java.lang.String name) {
        FinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName = name;
    }

    public com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi getFinancialInstitutionInquiryApplicationServiceSpiPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FinancialInstitutionInquiryApplicationServiceSpiPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFinancialInstitutionInquiryApplicationServiceSpiPort(endpoint);
    }

    public com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi getFinancialInstitutionInquiryApplicationServiceSpiPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub _stub = new com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub(portAddress, this);
            _stub.setPortName(getFinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFinancialInstitutionInquiryApplicationServiceSpiPortEndpointAddress(java.lang.String address) {
        FinancialInstitutionInquiryApplicationServiceSpiPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpi.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub _stub = new com.ofss.fc.cz.appx.pc.service.inquiry.FinancialInstitutionInquiryApplicationServiceSpiPortBindingStub(new java.net.URL(FinancialInstitutionInquiryApplicationServiceSpiPort_address), this);
                _stub.setPortName(getFinancialInstitutionInquiryApplicationServiceSpiPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FinancialInstitutionInquiryApplicationServiceSpiPort".equals(inputPortName)) {
            return getFinancialInstitutionInquiryApplicationServiceSpiPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "FinancialInstitutionInquiryApplicationServiceSpiService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://inquiry.service.pc.appx.cz.fc.ofss.com/", "FinancialInstitutionInquiryApplicationServiceSpiPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FinancialInstitutionInquiryApplicationServiceSpiPort".equals(portName)) {
            setFinancialInstitutionInquiryApplicationServiceSpiPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
