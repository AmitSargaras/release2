package com.hdfcbank.wsdl.ecbf.counterparty;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _ClimsBorrowerResponse_QNAME = new QName("http://serviceImpl.hdfcAPI.com", "updateBorrowerDetailReturn");
    private final static QName _ClimsBorrowerRequest_QNAME = new QName("http://serviceImpl.hdfcAPI.com", "request");

    public ObjectFactory() {
    }

    public ClimesBorrowerVo createClimsBorrowerRequest() {
        return new ClimesBorrowerVo();
    }

    public ClimesBorrowerResponseVo createClimsBorrowerResponse() {
        return new ClimesBorrowerResponseVo();
    }

    @XmlElementDecl(namespace = "http://serviceImpl.hdfcAPI.com", name = "updateBorrowerDetailReturn")
    public JAXBElement<ClimesBorrowerResponseVo> createClimsBorrowerResponse(ClimesBorrowerResponseVo value) {
        return new JAXBElement<ClimesBorrowerResponseVo>(_ClimsBorrowerResponse_QNAME, ClimesBorrowerResponseVo.class, null, value);
    }

    @XmlElementDecl(namespace = "http://serviceImpl.hdfcAPI.com", name = "request")
    public JAXBElement<ClimesBorrowerVo> createClimsBorrowerRequest(ClimesBorrowerVo value) {
        return new JAXBElement<ClimesBorrowerVo>(_ClimsBorrowerRequest_QNAME, ClimesBorrowerVo.class, null, value);
    }

}