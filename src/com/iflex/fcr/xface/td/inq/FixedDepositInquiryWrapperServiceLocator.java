/**
 * FixedDepositInquiryWrapperServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class FixedDepositInquiryWrapperServiceLocator extends org.apache.axis.client.Service implements com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperService {

/**
 * OSB Service
 */

    public FixedDepositInquiryWrapperServiceLocator() {
    }


    public FixedDepositInquiryWrapperServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FixedDepositInquiryWrapperServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FixedDepositInquiryWrapperPort
    private java.lang.String FixedDepositInquiryWrapperPort_address = "https://osbsesoauat.hdfcbank.com:5142/CLIMS_Fc/ProxyService/PS_FixedDepositInquiry";

    public java.lang.String getFixedDepositInquiryWrapperPortAddress() {
        return FixedDepositInquiryWrapperPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FixedDepositInquiryWrapperPortWSDDServiceName = "FixedDepositInquiryWrapperPort";

    public java.lang.String getFixedDepositInquiryWrapperPortWSDDServiceName() {
        return FixedDepositInquiryWrapperPortWSDDServiceName;
    }

    public void setFixedDepositInquiryWrapperPortWSDDServiceName(java.lang.String name) {
        FixedDepositInquiryWrapperPortWSDDServiceName = name;
    }

    public com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper getFixedDepositInquiryWrapperPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FixedDepositInquiryWrapperPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFixedDepositInquiryWrapperPort(endpoint);
    }

    public com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper getFixedDepositInquiryWrapperPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingStub _stub = new com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingStub(portAddress, this);
            _stub.setPortName(getFixedDepositInquiryWrapperPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFixedDepositInquiryWrapperPortEndpointAddress(java.lang.String address) {
        FixedDepositInquiryWrapperPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper.class.isAssignableFrom(serviceEndpointInterface)) {
                com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingStub _stub = new com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingStub(new java.net.URL(FixedDepositInquiryWrapperPort_address), this);
                _stub.setPortName(getFixedDepositInquiryWrapperPortWSDDServiceName());
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
        if ("FixedDepositInquiryWrapperPort".equals(inputPortName)) {
            return getFixedDepositInquiryWrapperPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "FixedDepositInquiryWrapperService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "FixedDepositInquiryWrapperPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FixedDepositInquiryWrapperPort".equals(portName)) {
            setFixedDepositInquiryWrapperPortEndpointAddress(address);
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
