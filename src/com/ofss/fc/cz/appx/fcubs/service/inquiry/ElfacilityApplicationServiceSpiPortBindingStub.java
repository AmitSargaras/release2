/**
 * ElfacilityApplicationServiceSpiPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class ElfacilityApplicationServiceSpiPortBindingStub extends org.apache.axis.client.Stub implements com.ofss.fc.cz.appx.fcubs.service.inquiry.ElfacilityApplicationServiceSpi {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("doQueryFacilityIO");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "sessionContext"), com.ofss.fc.app.context.SessionContext.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSRequestDTO"), com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "workItemViewObjectDTO"), com.ofss.fc.framework.domain.WorkItemViewObjectDTO[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSResponseDTO"));
        oper.setReturnClass(com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "FatalException"),
                      "com.ofss.fc.infra.exception.FatalExceptionBean",
                      new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "fatalExceptionBean"), 
                      true
                     ));
        _operations[0] = oper;

    }

    public ElfacilityApplicationServiceSpiPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ElfacilityApplicationServiceSpiPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ElfacilityApplicationServiceSpiPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "approvalContext");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.app.context.ApprovalContext.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "sessionContext");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.app.context.SessionContext.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://context.app.fc.ofss.com", "userContext");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.app.context.UserContext.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://datatype.fc.ofss.com", "date");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.datatype.Date.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentIdentityDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.ComponentIdentityDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "componentStateDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.ComponentStateDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://domain.framework.fc.ofss.com", "workItemViewObjectDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.WorkItemViewObjectDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dataTransferObject");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.common.dto.DataTransferObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "dictionary");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.common.dto.Dictionary.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dto.common.domain.framework.fc.ofss.com", "nameValuePairDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.framework.domain.common.dto.NameValuePairDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "maintenanceType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.enumeration.MaintenanceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "serviceCallContextType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.enumeration.ServiceCallContextType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://enumeration.infra.fc.ofss.com", "replyMessageType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.enumeration.ReplyMessageType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://enumeration.infra.fc.ofss.com", "responseCodeType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.enumeration.ResponseCodeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://error.validation.infra.fc.ofss.com", "validationError");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.validation.error.ValidationError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "extendedReply");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.exception.ReplyMessage[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "replyMessage");
            qName2 = new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "messages");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "fatalExceptionBean");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.exception.FatalExceptionBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://exception.infra.fc.ofss.com", "replyMessage");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.infra.exception.ReplyMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">>fcubsHeaderType>addl>param");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">fcubsHeaderType>addl");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderTypeAddlParam[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">>fcubsHeaderType>addl>param");
            qName2 = new javax.xml.namespace.QName("", "param");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "errorDetailsType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "errorType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.ErrorDetailsType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "errorDetailsType");
            qName2 = new javax.xml.namespace.QName("", "error");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityBranchRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCharge");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCharge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCovenant");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCurrencyRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCustomerRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityDescriptionFullTypeResponseDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionFullTypeResponseDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityDescriptionPKTypeDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityDescriptionPKTypeDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityExposure");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityExposureRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityMandate");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityPoolLink");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityProductRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySchedules");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySystemRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityTenorRestriction");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityUDEValues");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityValueDateDetails");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsbody");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbody.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsbodyrequest");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsHeaderType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "msgStatType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.MsgStatType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSRequestDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSResponseDTO");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "ubscompType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.UbscompType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "udfDetailsType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "warningType");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.cz.appx.fcubs.service.inquiry.WarningType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "baseResponse");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.service.response.BaseResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://response.service.fc.ofss.com", "transactionStatus");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.service.response.TransactionStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://validation.dto.app.fc.ofss.com", "validatable");
            cachedSerQNames.add(qName);
            cls = com.ofss.fc.app.dto.validation.Validatable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO doQueryFacilityIO(com.ofss.fc.app.context.SessionContext arg0, com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSRequestDTO arg1, com.ofss.fc.framework.domain.WorkItemViewObjectDTO[] arg2) throws java.rmi.RemoteException, com.ofss.fc.infra.exception.FatalExceptionBean {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "doQueryFacilityIO"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1, arg2});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO) org.apache.axis.utils.JavaUtils.convert(_resp, com.ofss.fc.cz.appx.fcubs.service.inquiry.QueryFacilityIOFSResponseDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.ofss.fc.infra.exception.FatalExceptionBean) {
              throw (com.ofss.fc.infra.exception.FatalExceptionBean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
