package com.hdfcbank.wsdl.ecbf.counterparty;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

@WebServiceClient(name = "ClimsBulkImportDetailServiceImplService", targetNamespace = "http://serviceImpl.hdfcAPI.com", wsdlLocation = "https://osbsesoauat.hdfcbank.com:5142/eCBF/ProxyService/PS_CLIMSeCBF?wsdl")
public class ClimsBulkImportDetailServiceImplService extends Service{
	
    private final static URL SERVICE_WSDL_LOCATION;
    private final static WebServiceException SERVICE_EXCEPTION;
    private final static QName SERVICE_QNAME = new QName("http://serviceImpl.hdfcAPI.com", "ClimsBulkImportDetailServiceImplService");

	static {
		URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(PropertyManager.getValue("ecbf.counterparty.ws.url") +"?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVICE_WSDL_LOCATION = url;
        SERVICE_EXCEPTION = e;
	}
    
	
    public ClimsBulkImportDetailServiceImplService() {
        super(__getWsdlLocation(), SERVICE_QNAME);
    }

    public ClimsBulkImportDetailServiceImplService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
    private static URL __getWsdlLocation() {
        if (SERVICE_EXCEPTION!= null) {
            throw SERVICE_EXCEPTION;
        }
        return SERVICE_WSDL_LOCATION;
    }
    
    @WebEndpoint(name = "ClimsBulkImportDetailServiceImpl")
    public ClimsBulkImportDetailServiceImpl getClimsBulkImportDetailService() {
        return (ClimsBulkImportDetailServiceImpl)super.getPort(new QName("http://serviceImpl.hdfcAPI.com", "ClimsBulkImportDetailServiceImpl"), ClimsBulkImportDetailServiceImpl.class);
    }


    @WebEndpoint(name = "ClimsBulkImportDetailServiceImpl")
    public ClimsBulkImportDetailServiceImpl getClimsBulkImportDetailService(WebServiceFeature... features) {
        return (ClimsBulkImportDetailServiceImpl)super.getPort(new QName("http://serviceImpl.hdfcAPI.com", "ClimsBulkImportDetailServiceImpl"), ClimsBulkImportDetailServiceImpl.class, features);
    }

}