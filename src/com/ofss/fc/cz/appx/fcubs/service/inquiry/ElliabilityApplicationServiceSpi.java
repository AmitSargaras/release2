/**
 * ElliabilityApplicationServiceSpi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public interface ElliabilityApplicationServiceSpi extends java.rmi.Remote {
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.DoQueryLiabilityIOResponseReturn doQueryLiabilityIO(com.ofss.fc.app.context.SessionContext arg0, com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryLiablityIOFSRequest arg1, com.ofss.fc.framework.domain.WorkItemViewObjectDTO[] arg2) throws java.rmi.RemoteException, com.ofss.fc.infra.exception.FatalExceptionBean;
}
