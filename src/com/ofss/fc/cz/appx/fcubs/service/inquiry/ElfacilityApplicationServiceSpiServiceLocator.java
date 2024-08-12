/**
 * ElfacilityApplicationServiceSpiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class ElfacilityApplicationServiceSpiServiceLocator extends org.apache.axis.client.Service implements com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiService {

    public ElfacilityApplicationServiceSpiServiceLocator() {
    }


    public ElfacilityApplicationServiceSpiServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ElfacilityApplicationServiceSpiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ElfacilityApplicationServiceSpiPort
    private java.lang.String ElfacilityApplicationServiceSpiPort_address = "https://10.226.163.7:9443/com.ofss.fc.cz.module.fcubs.elcm.inquiry.webservice/Elfacility";

    public java.lang.String getElfacilityApplicationServiceSpiPortAddress() {
        return ElfacilityApplicationServiceSpiPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ElfacilityApplicationServiceSpiPortWSDDServiceName = "ElfacilityApplicationServiceSpiPort";

    public java.lang.String getElfacilityApplicationServiceSpiPortWSDDServiceName() {
        return ElfacilityApplicationServiceSpiPortWSDDServiceName;
    }

    public void setElfacilityApplicationServiceSpiPortWSDDServiceName(java.lang.String name) {
        ElfacilityApplicationServiceSpiPortWSDDServiceName = name;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi getElfacilityApplicationServiceSpiPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ElfacilityApplicationServiceSpiPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getElfacilityApplicationServiceSpiPort(endpoint);
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi getElfacilityApplicationServiceSpiPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingStub _stub = new com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingStub(portAddress, this);
            _stub.setPortName(getElfacilityApplicationServiceSpiPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setElfacilityApplicationServiceSpiPortEndpointAddress(java.lang.String address) {
        ElfacilityApplicationServiceSpiPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingStub _stub = new com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpiPortBindingStub(new java.net.URL(ElfacilityApplicationServiceSpiPort_address), this);
                _stub.setPortName(getElfacilityApplicationServiceSpiPortWSDDServiceName());
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
        if ("ElfacilityApplicationServiceSpiPort".equals(inputPortName)) {
            return getElfacilityApplicationServiceSpiPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "ElfacilityApplicationServiceSpiService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "ElfacilityApplicationServiceSpiPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ElfacilityApplicationServiceSpiPort".equals(portName)) {
            setElfacilityApplicationServiceSpiPortEndpointAddress(address);
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
