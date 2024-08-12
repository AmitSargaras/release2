package com.hdfcbank.wsdl.ecbf.counterparty;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.hdfcbank.xsd.isd.panval.ObjectFactory;

@WebService(name = "ClimsBulkImportDetailServiceImpl", targetNamespace = "http://serviceImpl.hdfcAPI.com")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ClimsBulkImportDetailServiceImpl {

    @WebMethod(operationName = "updateBorrowerDetail")
    @WebResult(name = "updateBorrowerDetailReturn", targetNamespace = "http://serviceImpl.hdfcAPI.com", partName = "parameters")
    public ClimesBorrowerResponseVo updateBorrowerDetail(
        @WebParam(name = "request", targetNamespace = "http://serviceImpl.hdfcAPI.com", partName = "parameters")
        ClimesBorrowerVo parameters);
    
}