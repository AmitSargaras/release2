/**
 * ElfacilityApplicationServiceSpi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public interface ElfacilityApplicationServiceSpi extends java.rmi.Remote {
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO doQueryFacilityIO(com.ofss.fc.app.context.SessionContext arg0, com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO arg1, com.ofss.fc.framework.domain.WorkItemViewObjectDTO[] arg2) throws java.rmi.RemoteException, com.ofss.fc.infra.exception.FatalExceptionBean;
}
