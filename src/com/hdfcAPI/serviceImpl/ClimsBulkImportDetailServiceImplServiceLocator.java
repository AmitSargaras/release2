/**
 * ClimsBulkImportDetailServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hdfcAPI.serviceImpl;

public class ClimsBulkImportDetailServiceImplServiceLocator extends org.apache.axis.client.Service implements com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplService {

    public ClimsBulkImportDetailServiceImplServiceLocator() {
    }


    public ClimsBulkImportDetailServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClimsBulkImportDetailServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClimsBulkImportDetailServiceImpl
    private java.lang.String ClimsBulkImportDetailServiceImpl_address = "https://10.226.206.71:9443/ecbflr/services/ClimsBulkImportDetailServiceImpl";

    public java.lang.String getClimsBulkImportDetailServiceImplAddress() {
        return ClimsBulkImportDetailServiceImpl_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClimsBulkImportDetailServiceImplWSDDServiceName = "ClimsBulkImportDetailServiceImpl";

    public java.lang.String getClimsBulkImportDetailServiceImplWSDDServiceName() {
        return ClimsBulkImportDetailServiceImplWSDDServiceName;
    }

    public void setClimsBulkImportDetailServiceImplWSDDServiceName(java.lang.String name) {
        ClimsBulkImportDetailServiceImplWSDDServiceName = name;
    }

    public com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl getClimsBulkImportDetailServiceImpl() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClimsBulkImportDetailServiceImpl_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClimsBulkImportDetailServiceImpl(endpoint);
    }

    public com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl getClimsBulkImportDetailServiceImpl(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingStub _stub = new com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingStub(portAddress, this);
            _stub.setPortName(getClimsBulkImportDetailServiceImplWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClimsBulkImportDetailServiceImplEndpointAddress(java.lang.String address) {
        ClimsBulkImportDetailServiceImpl_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingStub _stub = new com.hdfcAPI.serviceImpl.ClimsBulkImportDetailServiceImplSoapBindingStub(new java.net.URL(ClimsBulkImportDetailServiceImpl_address), this);
                _stub.setPortName(getClimsBulkImportDetailServiceImplWSDDServiceName());
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
        if ("ClimsBulkImportDetailServiceImpl".equals(inputPortName)) {
            return getClimsBulkImportDetailServiceImpl();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://serviceImpl.hdfcAPI.com", "ClimsBulkImportDetailServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://serviceImpl.hdfcAPI.com", "ClimsBulkImportDetailServiceImpl"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClimsBulkImportDetailServiceImpl".equals(portName)) {
            setClimsBulkImportDetailServiceImplEndpointAddress(address);
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
