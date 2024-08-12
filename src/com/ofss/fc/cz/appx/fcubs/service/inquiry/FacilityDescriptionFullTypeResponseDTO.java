/**
 * FacilityDescriptionFullTypeResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityDescriptionFullTypeResponseDTO  implements java.io.Serializable {
    private java.math.BigDecimal amountreinstatedtoday;

    private java.math.BigDecimal amountutilisedtoday;

    private java.math.BigDecimal approvedamt;

    private java.lang.String authstat;

    private java.lang.String availabilityflag;

    private java.math.BigDecimal availableamount;

    private java.lang.String availmentdate;

    private java.math.BigDecimal blockamount;

    private java.lang.String branchresttype;

    private java.lang.String brn;

    private java.lang.String bulkpmtreqd;

    private java.lang.String category;

    private java.lang.String ccyrest;

    private java.lang.String ccyresttype;

    private java.lang.String checkerdtstamp;

    private java.lang.String checkerid;

    private java.math.BigDecimal collateralcontribution;

    private java.math.BigDecimal collateralpct;

    private java.lang.String commitmentproduct;

    private java.lang.String commitmentsettlacc;

    private java.lang.String commitmentsettlbrn;

    private java.lang.String conversiondate;

    private java.lang.String customerresttype;

    private java.lang.String dateoffirstod;

    private java.lang.String dateoflastod;

    private java.math.BigDecimal daylightlimit;

    private java.math.BigDecimal daylightodlimit;

    private java.lang.String description;

    private java.math.BigDecimal dspefflineamount;

    private java.math.BigDecimal excepbreach;

    private java.math.BigDecimal exceptxnamt;

    private java.math.BigDecimal excesstenor;

    private java.lang.String expresttype;

    private java.lang.String externalrefno;

    private java.lang.String extsystemresttype;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction[] facilityBranchRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCharge facilityCharge;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant[] facilityCovenant;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction[] facilityCurrencyRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction[] facilityCustomerRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure[] facilityExposure;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction[] facilityExposureRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate[] facilityMandate;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink[] facilityPoolLink;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction[] facilityProductRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules[] facilitySchedules;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction[] facilitySystemRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction[] facilityTenorRestriction;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues[] facilityUDEValues;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails[] facilityValueDateDetails;

    private java.lang.String funded;

    private java.lang.String internalremarks;

    private java.lang.String lastnewutildate;

    private java.lang.String liabbr;

    private java.lang.String liabid;

    private java.lang.String liabno;

    private java.math.BigDecimal limitamount;

    private java.lang.String lineccy;

    private java.lang.String linecode;

    private java.lang.String lineexpirydate;

    private java.math.BigDecimal lineserial;

    private java.lang.String linestartdate;

    private java.lang.String lmtamtbasis;

    private java.math.BigDecimal mainlineid;

    private java.lang.String makerdtstamp;

    private java.lang.String makerid;

    private java.math.BigDecimal modno;

    private java.math.BigDecimal nettingamount;

    private java.lang.String nettingrequired;

    private java.lang.String onceauth;

    private java.lang.String ppcprojectid;

    private java.lang.String ppcrefno;

    private java.math.BigDecimal processno;

    private java.lang.String productresttype;

    private java.lang.String recstat;

    private java.math.BigDecimal reportingamount;

    private java.lang.String revolvingline;

    private java.lang.String shadowlimit;

    private java.math.BigDecimal tankedutil;

    private java.lang.String tenorresttype;

    private java.math.BigDecimal transferamount;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] udfdetails;

    private java.lang.String unadvised;

    private java.math.BigDecimal uncollectedamount;

    private java.math.BigDecimal uncollectedfundslimit;

    private java.lang.String userdefinestatus;

    private java.lang.String userdefstatchangeddt;

    private java.lang.String userrefno;

    private java.math.BigDecimal utilisation;

    public FacilityDescriptionFullTypeResponseDTO() {
    }

    public FacilityDescriptionFullTypeResponseDTO(
           java.math.BigDecimal amountreinstatedtoday,
           java.math.BigDecimal amountutilisedtoday,
           java.math.BigDecimal approvedamt,
           java.lang.String authstat,
           java.lang.String availabilityflag,
           java.math.BigDecimal availableamount,
           java.lang.String availmentdate,
           java.math.BigDecimal blockamount,
           java.lang.String branchresttype,
           java.lang.String brn,
           java.lang.String bulkpmtreqd,
           java.lang.String category,
           java.lang.String ccyrest,
           java.lang.String ccyresttype,
           java.lang.String checkerdtstamp,
           java.lang.String checkerid,
           java.math.BigDecimal collateralcontribution,
           java.math.BigDecimal collateralpct,
           java.lang.String commitmentproduct,
           java.lang.String commitmentsettlacc,
           java.lang.String commitmentsettlbrn,
           java.lang.String conversiondate,
           java.lang.String customerresttype,
           java.lang.String dateoffirstod,
           java.lang.String dateoflastod,
           java.math.BigDecimal daylightlimit,
           java.math.BigDecimal daylightodlimit,
           java.lang.String description,
           java.math.BigDecimal dspefflineamount,
           java.math.BigDecimal excepbreach,
           java.math.BigDecimal exceptxnamt,
           java.math.BigDecimal excesstenor,
           java.lang.String expresttype,
           java.lang.String externalrefno,
           java.lang.String extsystemresttype,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction[] facilityBranchRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCharge facilityCharge,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant[] facilityCovenant,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction[] facilityCurrencyRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction[] facilityCustomerRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure[] facilityExposure,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction[] facilityExposureRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate[] facilityMandate,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink[] facilityPoolLink,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction[] facilityProductRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules[] facilitySchedules,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction[] facilitySystemRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction[] facilityTenorRestriction,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues[] facilityUDEValues,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails[] facilityValueDateDetails,
           java.lang.String funded,
           java.lang.String internalremarks,
           java.lang.String lastnewutildate,
           java.lang.String liabbr,
           java.lang.String liabid,
           java.lang.String liabno,
           java.math.BigDecimal limitamount,
           java.lang.String lineccy,
           java.lang.String linecode,
           java.lang.String lineexpirydate,
           java.math.BigDecimal lineserial,
           java.lang.String linestartdate,
           java.lang.String lmtamtbasis,
           java.math.BigDecimal mainlineid,
           java.lang.String makerdtstamp,
           java.lang.String makerid,
           java.math.BigDecimal modno,
           java.math.BigDecimal nettingamount,
           java.lang.String nettingrequired,
           java.lang.String onceauth,
           java.lang.String ppcprojectid,
           java.lang.String ppcrefno,
           java.math.BigDecimal processno,
           java.lang.String productresttype,
           java.lang.String recstat,
           java.math.BigDecimal reportingamount,
           java.lang.String revolvingline,
           java.lang.String shadowlimit,
           java.math.BigDecimal tankedutil,
           java.lang.String tenorresttype,
           java.math.BigDecimal transferamount,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] udfdetails,
           java.lang.String unadvised,
           java.math.BigDecimal uncollectedamount,
           java.math.BigDecimal uncollectedfundslimit,
           java.lang.String userdefinestatus,
           java.lang.String userdefstatchangeddt,
           java.lang.String userrefno,
           java.math.BigDecimal utilisation) {
           this.amountreinstatedtoday = amountreinstatedtoday;
           this.amountutilisedtoday = amountutilisedtoday;
           this.approvedamt = approvedamt;
           this.authstat = authstat;
           this.availabilityflag = availabilityflag;
           this.availableamount = availableamount;
           this.availmentdate = availmentdate;
           this.blockamount = blockamount;
           this.branchresttype = branchresttype;
           this.brn = brn;
           this.bulkpmtreqd = bulkpmtreqd;
           this.category = category;
           this.ccyrest = ccyrest;
           this.ccyresttype = ccyresttype;
           this.checkerdtstamp = checkerdtstamp;
           this.checkerid = checkerid;
           this.collateralcontribution = collateralcontribution;
           this.collateralpct = collateralpct;
           this.commitmentproduct = commitmentproduct;
           this.commitmentsettlacc = commitmentsettlacc;
           this.commitmentsettlbrn = commitmentsettlbrn;
           this.conversiondate = conversiondate;
           this.customerresttype = customerresttype;
           this.dateoffirstod = dateoffirstod;
           this.dateoflastod = dateoflastod;
           this.daylightlimit = daylightlimit;
           this.daylightodlimit = daylightodlimit;
           this.description = description;
           this.dspefflineamount = dspefflineamount;
           this.excepbreach = excepbreach;
           this.exceptxnamt = exceptxnamt;
           this.excesstenor = excesstenor;
           this.expresttype = expresttype;
           this.externalrefno = externalrefno;
           this.extsystemresttype = extsystemresttype;
           this.facilityBranchRestriction = facilityBranchRestriction;
           this.facilityCharge = facilityCharge;
           this.facilityCovenant = facilityCovenant;
           this.facilityCurrencyRestriction = facilityCurrencyRestriction;
           this.facilityCustomerRestriction = facilityCustomerRestriction;
           this.facilityExposure = facilityExposure;
           this.facilityExposureRestriction = facilityExposureRestriction;
           this.facilityMandate = facilityMandate;
           this.facilityPoolLink = facilityPoolLink;
           this.facilityProductRestriction = facilityProductRestriction;
           this.facilitySchedules = facilitySchedules;
           this.facilitySystemRestriction = facilitySystemRestriction;
           this.facilityTenorRestriction = facilityTenorRestriction;
           this.facilityUDEValues = facilityUDEValues;
           this.facilityValueDateDetails = facilityValueDateDetails;
           this.funded = funded;
           this.internalremarks = internalremarks;
           this.lastnewutildate = lastnewutildate;
           this.liabbr = liabbr;
           this.liabid = liabid;
           this.liabno = liabno;
           this.limitamount = limitamount;
           this.lineccy = lineccy;
           this.linecode = linecode;
           this.lineexpirydate = lineexpirydate;
           this.lineserial = lineserial;
           this.linestartdate = linestartdate;
           this.lmtamtbasis = lmtamtbasis;
           this.mainlineid = mainlineid;
           this.makerdtstamp = makerdtstamp;
           this.makerid = makerid;
           this.modno = modno;
           this.nettingamount = nettingamount;
           this.nettingrequired = nettingrequired;
           this.onceauth = onceauth;
           this.ppcprojectid = ppcprojectid;
           this.ppcrefno = ppcrefno;
           this.processno = processno;
           this.productresttype = productresttype;
           this.recstat = recstat;
           this.reportingamount = reportingamount;
           this.revolvingline = revolvingline;
           this.shadowlimit = shadowlimit;
           this.tankedutil = tankedutil;
           this.tenorresttype = tenorresttype;
           this.transferamount = transferamount;
           this.udfdetails = udfdetails;
           this.unadvised = unadvised;
           this.uncollectedamount = uncollectedamount;
           this.uncollectedfundslimit = uncollectedfundslimit;
           this.userdefinestatus = userdefinestatus;
           this.userdefstatchangeddt = userdefstatchangeddt;
           this.userrefno = userrefno;
           this.utilisation = utilisation;
    }


    /**
     * Gets the amountreinstatedtoday value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return amountreinstatedtoday
     */
    public java.math.BigDecimal getAmountreinstatedtoday() {
        return amountreinstatedtoday;
    }


    /**
     * Sets the amountreinstatedtoday value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param amountreinstatedtoday
     */
    public void setAmountreinstatedtoday(java.math.BigDecimal amountreinstatedtoday) {
        this.amountreinstatedtoday = amountreinstatedtoday;
    }


    /**
     * Gets the amountutilisedtoday value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return amountutilisedtoday
     */
    public java.math.BigDecimal getAmountutilisedtoday() {
        return amountutilisedtoday;
    }


    /**
     * Sets the amountutilisedtoday value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param amountutilisedtoday
     */
    public void setAmountutilisedtoday(java.math.BigDecimal amountutilisedtoday) {
        this.amountutilisedtoday = amountutilisedtoday;
    }


    /**
     * Gets the approvedamt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return approvedamt
     */
    public java.math.BigDecimal getApprovedamt() {
        return approvedamt;
    }


    /**
     * Sets the approvedamt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param approvedamt
     */
    public void setApprovedamt(java.math.BigDecimal approvedamt) {
        this.approvedamt = approvedamt;
    }


    /**
     * Gets the authstat value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return authstat
     */
    public java.lang.String getAuthstat() {
        return authstat;
    }


    /**
     * Sets the authstat value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param authstat
     */
    public void setAuthstat(java.lang.String authstat) {
        this.authstat = authstat;
    }


    /**
     * Gets the availabilityflag value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return availabilityflag
     */
    public java.lang.String getAvailabilityflag() {
        return availabilityflag;
    }


    /**
     * Sets the availabilityflag value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param availabilityflag
     */
    public void setAvailabilityflag(java.lang.String availabilityflag) {
        this.availabilityflag = availabilityflag;
    }


    /**
     * Gets the availableamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return availableamount
     */
    public java.math.BigDecimal getAvailableamount() {
        return availableamount;
    }


    /**
     * Sets the availableamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param availableamount
     */
    public void setAvailableamount(java.math.BigDecimal availableamount) {
        this.availableamount = availableamount;
    }


    /**
     * Gets the availmentdate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return availmentdate
     */
    public java.lang.String getAvailmentdate() {
        return availmentdate;
    }


    /**
     * Sets the availmentdate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param availmentdate
     */
    public void setAvailmentdate(java.lang.String availmentdate) {
        this.availmentdate = availmentdate;
    }


    /**
     * Gets the blockamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return blockamount
     */
    public java.math.BigDecimal getBlockamount() {
        return blockamount;
    }


    /**
     * Sets the blockamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param blockamount
     */
    public void setBlockamount(java.math.BigDecimal blockamount) {
        this.blockamount = blockamount;
    }


    /**
     * Gets the branchresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return branchresttype
     */
    public java.lang.String getBranchresttype() {
        return branchresttype;
    }


    /**
     * Sets the branchresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param branchresttype
     */
    public void setBranchresttype(java.lang.String branchresttype) {
        this.branchresttype = branchresttype;
    }


    /**
     * Gets the brn value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return brn
     */
    public java.lang.String getBrn() {
        return brn;
    }


    /**
     * Sets the brn value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param brn
     */
    public void setBrn(java.lang.String brn) {
        this.brn = brn;
    }


    /**
     * Gets the bulkpmtreqd value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return bulkpmtreqd
     */
    public java.lang.String getBulkpmtreqd() {
        return bulkpmtreqd;
    }


    /**
     * Sets the bulkpmtreqd value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param bulkpmtreqd
     */
    public void setBulkpmtreqd(java.lang.String bulkpmtreqd) {
        this.bulkpmtreqd = bulkpmtreqd;
    }


    /**
     * Gets the category value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the ccyrest value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return ccyrest
     */
    public java.lang.String getCcyrest() {
        return ccyrest;
    }


    /**
     * Sets the ccyrest value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param ccyrest
     */
    public void setCcyrest(java.lang.String ccyrest) {
        this.ccyrest = ccyrest;
    }


    /**
     * Gets the ccyresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return ccyresttype
     */
    public java.lang.String getCcyresttype() {
        return ccyresttype;
    }


    /**
     * Sets the ccyresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param ccyresttype
     */
    public void setCcyresttype(java.lang.String ccyresttype) {
        this.ccyresttype = ccyresttype;
    }


    /**
     * Gets the checkerdtstamp value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return checkerdtstamp
     */
    public java.lang.String getCheckerdtstamp() {
        return checkerdtstamp;
    }


    /**
     * Sets the checkerdtstamp value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param checkerdtstamp
     */
    public void setCheckerdtstamp(java.lang.String checkerdtstamp) {
        this.checkerdtstamp = checkerdtstamp;
    }


    /**
     * Gets the checkerid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return checkerid
     */
    public java.lang.String getCheckerid() {
        return checkerid;
    }


    /**
     * Sets the checkerid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param checkerid
     */
    public void setCheckerid(java.lang.String checkerid) {
        this.checkerid = checkerid;
    }


    /**
     * Gets the collateralcontribution value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return collateralcontribution
     */
    public java.math.BigDecimal getCollateralcontribution() {
        return collateralcontribution;
    }


    /**
     * Sets the collateralcontribution value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param collateralcontribution
     */
    public void setCollateralcontribution(java.math.BigDecimal collateralcontribution) {
        this.collateralcontribution = collateralcontribution;
    }


    /**
     * Gets the collateralpct value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return collateralpct
     */
    public java.math.BigDecimal getCollateralpct() {
        return collateralpct;
    }


    /**
     * Sets the collateralpct value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param collateralpct
     */
    public void setCollateralpct(java.math.BigDecimal collateralpct) {
        this.collateralpct = collateralpct;
    }


    /**
     * Gets the commitmentproduct value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return commitmentproduct
     */
    public java.lang.String getCommitmentproduct() {
        return commitmentproduct;
    }


    /**
     * Sets the commitmentproduct value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param commitmentproduct
     */
    public void setCommitmentproduct(java.lang.String commitmentproduct) {
        this.commitmentproduct = commitmentproduct;
    }


    /**
     * Gets the commitmentsettlacc value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return commitmentsettlacc
     */
    public java.lang.String getCommitmentsettlacc() {
        return commitmentsettlacc;
    }


    /**
     * Sets the commitmentsettlacc value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param commitmentsettlacc
     */
    public void setCommitmentsettlacc(java.lang.String commitmentsettlacc) {
        this.commitmentsettlacc = commitmentsettlacc;
    }


    /**
     * Gets the commitmentsettlbrn value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return commitmentsettlbrn
     */
    public java.lang.String getCommitmentsettlbrn() {
        return commitmentsettlbrn;
    }


    /**
     * Sets the commitmentsettlbrn value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param commitmentsettlbrn
     */
    public void setCommitmentsettlbrn(java.lang.String commitmentsettlbrn) {
        this.commitmentsettlbrn = commitmentsettlbrn;
    }


    /**
     * Gets the conversiondate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return conversiondate
     */
    public java.lang.String getConversiondate() {
        return conversiondate;
    }


    /**
     * Sets the conversiondate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param conversiondate
     */
    public void setConversiondate(java.lang.String conversiondate) {
        this.conversiondate = conversiondate;
    }


    /**
     * Gets the customerresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return customerresttype
     */
    public java.lang.String getCustomerresttype() {
        return customerresttype;
    }


    /**
     * Sets the customerresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param customerresttype
     */
    public void setCustomerresttype(java.lang.String customerresttype) {
        this.customerresttype = customerresttype;
    }


    /**
     * Gets the dateoffirstod value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return dateoffirstod
     */
    public java.lang.String getDateoffirstod() {
        return dateoffirstod;
    }


    /**
     * Sets the dateoffirstod value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param dateoffirstod
     */
    public void setDateoffirstod(java.lang.String dateoffirstod) {
        this.dateoffirstod = dateoffirstod;
    }


    /**
     * Gets the dateoflastod value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return dateoflastod
     */
    public java.lang.String getDateoflastod() {
        return dateoflastod;
    }


    /**
     * Sets the dateoflastod value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param dateoflastod
     */
    public void setDateoflastod(java.lang.String dateoflastod) {
        this.dateoflastod = dateoflastod;
    }


    /**
     * Gets the daylightlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return daylightlimit
     */
    public java.math.BigDecimal getDaylightlimit() {
        return daylightlimit;
    }


    /**
     * Sets the daylightlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param daylightlimit
     */
    public void setDaylightlimit(java.math.BigDecimal daylightlimit) {
        this.daylightlimit = daylightlimit;
    }


    /**
     * Gets the daylightodlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return daylightodlimit
     */
    public java.math.BigDecimal getDaylightodlimit() {
        return daylightodlimit;
    }


    /**
     * Sets the daylightodlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param daylightodlimit
     */
    public void setDaylightodlimit(java.math.BigDecimal daylightodlimit) {
        this.daylightodlimit = daylightodlimit;
    }


    /**
     * Gets the description value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the dspefflineamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return dspefflineamount
     */
    public java.math.BigDecimal getDspefflineamount() {
        return dspefflineamount;
    }


    /**
     * Sets the dspefflineamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param dspefflineamount
     */
    public void setDspefflineamount(java.math.BigDecimal dspefflineamount) {
        this.dspefflineamount = dspefflineamount;
    }


    /**
     * Gets the excepbreach value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return excepbreach
     */
    public java.math.BigDecimal getExcepbreach() {
        return excepbreach;
    }


    /**
     * Sets the excepbreach value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param excepbreach
     */
    public void setExcepbreach(java.math.BigDecimal excepbreach) {
        this.excepbreach = excepbreach;
    }


    /**
     * Gets the exceptxnamt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return exceptxnamt
     */
    public java.math.BigDecimal getExceptxnamt() {
        return exceptxnamt;
    }


    /**
     * Sets the exceptxnamt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param exceptxnamt
     */
    public void setExceptxnamt(java.math.BigDecimal exceptxnamt) {
        this.exceptxnamt = exceptxnamt;
    }


    /**
     * Gets the excesstenor value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return excesstenor
     */
    public java.math.BigDecimal getExcesstenor() {
        return excesstenor;
    }


    /**
     * Sets the excesstenor value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param excesstenor
     */
    public void setExcesstenor(java.math.BigDecimal excesstenor) {
        this.excesstenor = excesstenor;
    }


    /**
     * Gets the expresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return expresttype
     */
    public java.lang.String getExpresttype() {
        return expresttype;
    }


    /**
     * Sets the expresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param expresttype
     */
    public void setExpresttype(java.lang.String expresttype) {
        this.expresttype = expresttype;
    }


    /**
     * Gets the externalrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return externalrefno
     */
    public java.lang.String getExternalrefno() {
        return externalrefno;
    }


    /**
     * Sets the externalrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param externalrefno
     */
    public void setExternalrefno(java.lang.String externalrefno) {
        this.externalrefno = externalrefno;
    }


    /**
     * Gets the extsystemresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return extsystemresttype
     */
    public java.lang.String getExtsystemresttype() {
        return extsystemresttype;
    }


    /**
     * Sets the extsystemresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param extsystemresttype
     */
    public void setExtsystemresttype(java.lang.String extsystemresttype) {
        this.extsystemresttype = extsystemresttype;
    }


    /**
     * Gets the facilityBranchRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityBranchRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction[] getFacilityBranchRestriction() {
        return facilityBranchRestriction;
    }


    /**
     * Sets the facilityBranchRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityBranchRestriction
     */
    public void setFacilityBranchRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction[] facilityBranchRestriction) {
        this.facilityBranchRestriction = facilityBranchRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction getFacilityBranchRestriction(int i) {
        return this.facilityBranchRestriction[i];
    }

    public void setFacilityBranchRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityBranchRestriction _value) {
        this.facilityBranchRestriction[i] = _value;
    }


    /**
     * Gets the facilityCharge value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityCharge
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCharge getFacilityCharge() {
        return facilityCharge;
    }


    /**
     * Sets the facilityCharge value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityCharge
     */
    public void setFacilityCharge(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCharge facilityCharge) {
        this.facilityCharge = facilityCharge;
    }


    /**
     * Gets the facilityCovenant value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityCovenant
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant[] getFacilityCovenant() {
        return facilityCovenant;
    }


    /**
     * Sets the facilityCovenant value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityCovenant
     */
    public void setFacilityCovenant(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant[] facilityCovenant) {
        this.facilityCovenant = facilityCovenant;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant getFacilityCovenant(int i) {
        return this.facilityCovenant[i];
    }

    public void setFacilityCovenant(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCovenant _value) {
        this.facilityCovenant[i] = _value;
    }


    /**
     * Gets the facilityCurrencyRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityCurrencyRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction[] getFacilityCurrencyRestriction() {
        return facilityCurrencyRestriction;
    }


    /**
     * Sets the facilityCurrencyRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityCurrencyRestriction
     */
    public void setFacilityCurrencyRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction[] facilityCurrencyRestriction) {
        this.facilityCurrencyRestriction = facilityCurrencyRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction getFacilityCurrencyRestriction(int i) {
        return this.facilityCurrencyRestriction[i];
    }

    public void setFacilityCurrencyRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCurrencyRestriction _value) {
        this.facilityCurrencyRestriction[i] = _value;
    }


    /**
     * Gets the facilityCustomerRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityCustomerRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction[] getFacilityCustomerRestriction() {
        return facilityCustomerRestriction;
    }


    /**
     * Sets the facilityCustomerRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityCustomerRestriction
     */
    public void setFacilityCustomerRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction[] facilityCustomerRestriction) {
        this.facilityCustomerRestriction = facilityCustomerRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction getFacilityCustomerRestriction(int i) {
        return this.facilityCustomerRestriction[i];
    }

    public void setFacilityCustomerRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityCustomerRestriction _value) {
        this.facilityCustomerRestriction[i] = _value;
    }


    /**
     * Gets the facilityExposure value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityExposure
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure[] getFacilityExposure() {
        return facilityExposure;
    }


    /**
     * Sets the facilityExposure value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityExposure
     */
    public void setFacilityExposure(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure[] facilityExposure) {
        this.facilityExposure = facilityExposure;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure getFacilityExposure(int i) {
        return this.facilityExposure[i];
    }

    public void setFacilityExposure(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposure _value) {
        this.facilityExposure[i] = _value;
    }


    /**
     * Gets the facilityExposureRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityExposureRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction[] getFacilityExposureRestriction() {
        return facilityExposureRestriction;
    }


    /**
     * Sets the facilityExposureRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityExposureRestriction
     */
    public void setFacilityExposureRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction[] facilityExposureRestriction) {
        this.facilityExposureRestriction = facilityExposureRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction getFacilityExposureRestriction(int i) {
        return this.facilityExposureRestriction[i];
    }

    public void setFacilityExposureRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityExposureRestriction _value) {
        this.facilityExposureRestriction[i] = _value;
    }


    /**
     * Gets the facilityMandate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityMandate
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate[] getFacilityMandate() {
        return facilityMandate;
    }


    /**
     * Sets the facilityMandate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityMandate
     */
    public void setFacilityMandate(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate[] facilityMandate) {
        this.facilityMandate = facilityMandate;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate getFacilityMandate(int i) {
        return this.facilityMandate[i];
    }

    public void setFacilityMandate(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityMandate _value) {
        this.facilityMandate[i] = _value;
    }


    /**
     * Gets the facilityPoolLink value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityPoolLink
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink[] getFacilityPoolLink() {
        return facilityPoolLink;
    }


    /**
     * Sets the facilityPoolLink value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityPoolLink
     */
    public void setFacilityPoolLink(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink[] facilityPoolLink) {
        this.facilityPoolLink = facilityPoolLink;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink getFacilityPoolLink(int i) {
        return this.facilityPoolLink[i];
    }

    public void setFacilityPoolLink(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityPoolLink _value) {
        this.facilityPoolLink[i] = _value;
    }


    /**
     * Gets the facilityProductRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityProductRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction[] getFacilityProductRestriction() {
        return facilityProductRestriction;
    }


    /**
     * Sets the facilityProductRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityProductRestriction
     */
    public void setFacilityProductRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction[] facilityProductRestriction) {
        this.facilityProductRestriction = facilityProductRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction getFacilityProductRestriction(int i) {
        return this.facilityProductRestriction[i];
    }

    public void setFacilityProductRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityProductRestriction _value) {
        this.facilityProductRestriction[i] = _value;
    }


    /**
     * Gets the facilitySchedules value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilitySchedules
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules[] getFacilitySchedules() {
        return facilitySchedules;
    }


    /**
     * Sets the facilitySchedules value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilitySchedules
     */
    public void setFacilitySchedules(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules[] facilitySchedules) {
        this.facilitySchedules = facilitySchedules;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules getFacilitySchedules(int i) {
        return this.facilitySchedules[i];
    }

    public void setFacilitySchedules(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySchedules _value) {
        this.facilitySchedules[i] = _value;
    }


    /**
     * Gets the facilitySystemRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilitySystemRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction[] getFacilitySystemRestriction() {
        return facilitySystemRestriction;
    }


    /**
     * Sets the facilitySystemRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilitySystemRestriction
     */
    public void setFacilitySystemRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction[] facilitySystemRestriction) {
        this.facilitySystemRestriction = facilitySystemRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction getFacilitySystemRestriction(int i) {
        return this.facilitySystemRestriction[i];
    }

    public void setFacilitySystemRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilitySystemRestriction _value) {
        this.facilitySystemRestriction[i] = _value;
    }


    /**
     * Gets the facilityTenorRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityTenorRestriction
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction[] getFacilityTenorRestriction() {
        return facilityTenorRestriction;
    }


    /**
     * Sets the facilityTenorRestriction value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityTenorRestriction
     */
    public void setFacilityTenorRestriction(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction[] facilityTenorRestriction) {
        this.facilityTenorRestriction = facilityTenorRestriction;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction getFacilityTenorRestriction(int i) {
        return this.facilityTenorRestriction[i];
    }

    public void setFacilityTenorRestriction(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityTenorRestriction _value) {
        this.facilityTenorRestriction[i] = _value;
    }


    /**
     * Gets the facilityUDEValues value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityUDEValues
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues[] getFacilityUDEValues() {
        return facilityUDEValues;
    }


    /**
     * Sets the facilityUDEValues value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityUDEValues
     */
    public void setFacilityUDEValues(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues[] facilityUDEValues) {
        this.facilityUDEValues = facilityUDEValues;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues getFacilityUDEValues(int i) {
        return this.facilityUDEValues[i];
    }

    public void setFacilityUDEValues(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityUDEValues _value) {
        this.facilityUDEValues[i] = _value;
    }


    /**
     * Gets the facilityValueDateDetails value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return facilityValueDateDetails
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails[] getFacilityValueDateDetails() {
        return facilityValueDateDetails;
    }


    /**
     * Sets the facilityValueDateDetails value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param facilityValueDateDetails
     */
    public void setFacilityValueDateDetails(com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails[] facilityValueDateDetails) {
        this.facilityValueDateDetails = facilityValueDateDetails;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails getFacilityValueDateDetails(int i) {
        return this.facilityValueDateDetails[i];
    }

    public void setFacilityValueDateDetails(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.FacilityValueDateDetails _value) {
        this.facilityValueDateDetails[i] = _value;
    }


    /**
     * Gets the funded value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return funded
     */
    public java.lang.String getFunded() {
        return funded;
    }


    /**
     * Sets the funded value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param funded
     */
    public void setFunded(java.lang.String funded) {
        this.funded = funded;
    }


    /**
     * Gets the internalremarks value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return internalremarks
     */
    public java.lang.String getInternalremarks() {
        return internalremarks;
    }


    /**
     * Sets the internalremarks value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param internalremarks
     */
    public void setInternalremarks(java.lang.String internalremarks) {
        this.internalremarks = internalremarks;
    }


    /**
     * Gets the lastnewutildate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return lastnewutildate
     */
    public java.lang.String getLastnewutildate() {
        return lastnewutildate;
    }


    /**
     * Sets the lastnewutildate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param lastnewutildate
     */
    public void setLastnewutildate(java.lang.String lastnewutildate) {
        this.lastnewutildate = lastnewutildate;
    }


    /**
     * Gets the liabbr value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return liabbr
     */
    public java.lang.String getLiabbr() {
        return liabbr;
    }


    /**
     * Sets the liabbr value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param liabbr
     */
    public void setLiabbr(java.lang.String liabbr) {
        this.liabbr = liabbr;
    }


    /**
     * Gets the liabid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return liabid
     */
    public java.lang.String getLiabid() {
        return liabid;
    }


    /**
     * Sets the liabid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param liabid
     */
    public void setLiabid(java.lang.String liabid) {
        this.liabid = liabid;
    }


    /**
     * Gets the liabno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return liabno
     */
    public java.lang.String getLiabno() {
        return liabno;
    }


    /**
     * Sets the liabno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param liabno
     */
    public void setLiabno(java.lang.String liabno) {
        this.liabno = liabno;
    }


    /**
     * Gets the limitamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return limitamount
     */
    public java.math.BigDecimal getLimitamount() {
        return limitamount;
    }


    /**
     * Sets the limitamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param limitamount
     */
    public void setLimitamount(java.math.BigDecimal limitamount) {
        this.limitamount = limitamount;
    }


    /**
     * Gets the lineccy value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return lineccy
     */
    public java.lang.String getLineccy() {
        return lineccy;
    }


    /**
     * Sets the lineccy value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param lineccy
     */
    public void setLineccy(java.lang.String lineccy) {
        this.lineccy = lineccy;
    }


    /**
     * Gets the linecode value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return linecode
     */
    public java.lang.String getLinecode() {
        return linecode;
    }


    /**
     * Sets the linecode value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param linecode
     */
    public void setLinecode(java.lang.String linecode) {
        this.linecode = linecode;
    }


    /**
     * Gets the lineexpirydate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return lineexpirydate
     */
    public java.lang.String getLineexpirydate() {
        return lineexpirydate;
    }


    /**
     * Sets the lineexpirydate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param lineexpirydate
     */
    public void setLineexpirydate(java.lang.String lineexpirydate) {
        this.lineexpirydate = lineexpirydate;
    }


    /**
     * Gets the lineserial value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return lineserial
     */
    public java.math.BigDecimal getLineserial() {
        return lineserial;
    }


    /**
     * Sets the lineserial value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param lineserial
     */
    public void setLineserial(java.math.BigDecimal lineserial) {
        this.lineserial = lineserial;
    }


    /**
     * Gets the linestartdate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return linestartdate
     */
    public java.lang.String getLinestartdate() {
        return linestartdate;
    }


    /**
     * Sets the linestartdate value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param linestartdate
     */
    public void setLinestartdate(java.lang.String linestartdate) {
        this.linestartdate = linestartdate;
    }


    /**
     * Gets the lmtamtbasis value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return lmtamtbasis
     */
    public java.lang.String getLmtamtbasis() {
        return lmtamtbasis;
    }


    /**
     * Sets the lmtamtbasis value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param lmtamtbasis
     */
    public void setLmtamtbasis(java.lang.String lmtamtbasis) {
        this.lmtamtbasis = lmtamtbasis;
    }


    /**
     * Gets the mainlineid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return mainlineid
     */
    public java.math.BigDecimal getMainlineid() {
        return mainlineid;
    }


    /**
     * Sets the mainlineid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param mainlineid
     */
    public void setMainlineid(java.math.BigDecimal mainlineid) {
        this.mainlineid = mainlineid;
    }


    /**
     * Gets the makerdtstamp value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return makerdtstamp
     */
    public java.lang.String getMakerdtstamp() {
        return makerdtstamp;
    }


    /**
     * Sets the makerdtstamp value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param makerdtstamp
     */
    public void setMakerdtstamp(java.lang.String makerdtstamp) {
        this.makerdtstamp = makerdtstamp;
    }


    /**
     * Gets the makerid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return makerid
     */
    public java.lang.String getMakerid() {
        return makerid;
    }


    /**
     * Sets the makerid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param makerid
     */
    public void setMakerid(java.lang.String makerid) {
        this.makerid = makerid;
    }


    /**
     * Gets the modno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }


    /**
     * Gets the nettingamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return nettingamount
     */
    public java.math.BigDecimal getNettingamount() {
        return nettingamount;
    }


    /**
     * Sets the nettingamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param nettingamount
     */
    public void setNettingamount(java.math.BigDecimal nettingamount) {
        this.nettingamount = nettingamount;
    }


    /**
     * Gets the nettingrequired value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return nettingrequired
     */
    public java.lang.String getNettingrequired() {
        return nettingrequired;
    }


    /**
     * Sets the nettingrequired value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param nettingrequired
     */
    public void setNettingrequired(java.lang.String nettingrequired) {
        this.nettingrequired = nettingrequired;
    }


    /**
     * Gets the onceauth value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return onceauth
     */
    public java.lang.String getOnceauth() {
        return onceauth;
    }


    /**
     * Sets the onceauth value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param onceauth
     */
    public void setOnceauth(java.lang.String onceauth) {
        this.onceauth = onceauth;
    }


    /**
     * Gets the ppcprojectid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return ppcprojectid
     */
    public java.lang.String getPpcprojectid() {
        return ppcprojectid;
    }


    /**
     * Sets the ppcprojectid value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param ppcprojectid
     */
    public void setPpcprojectid(java.lang.String ppcprojectid) {
        this.ppcprojectid = ppcprojectid;
    }


    /**
     * Gets the ppcrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return ppcrefno
     */
    public java.lang.String getPpcrefno() {
        return ppcrefno;
    }


    /**
     * Sets the ppcrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param ppcrefno
     */
    public void setPpcrefno(java.lang.String ppcrefno) {
        this.ppcrefno = ppcrefno;
    }


    /**
     * Gets the processno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return processno
     */
    public java.math.BigDecimal getProcessno() {
        return processno;
    }


    /**
     * Sets the processno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param processno
     */
    public void setProcessno(java.math.BigDecimal processno) {
        this.processno = processno;
    }


    /**
     * Gets the productresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return productresttype
     */
    public java.lang.String getProductresttype() {
        return productresttype;
    }


    /**
     * Sets the productresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param productresttype
     */
    public void setProductresttype(java.lang.String productresttype) {
        this.productresttype = productresttype;
    }


    /**
     * Gets the recstat value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return recstat
     */
    public java.lang.String getRecstat() {
        return recstat;
    }


    /**
     * Sets the recstat value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param recstat
     */
    public void setRecstat(java.lang.String recstat) {
        this.recstat = recstat;
    }


    /**
     * Gets the reportingamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return reportingamount
     */
    public java.math.BigDecimal getReportingamount() {
        return reportingamount;
    }


    /**
     * Sets the reportingamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param reportingamount
     */
    public void setReportingamount(java.math.BigDecimal reportingamount) {
        this.reportingamount = reportingamount;
    }


    /**
     * Gets the revolvingline value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return revolvingline
     */
    public java.lang.String getRevolvingline() {
        return revolvingline;
    }


    /**
     * Sets the revolvingline value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param revolvingline
     */
    public void setRevolvingline(java.lang.String revolvingline) {
        this.revolvingline = revolvingline;
    }


    /**
     * Gets the shadowlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return shadowlimit
     */
    public java.lang.String getShadowlimit() {
        return shadowlimit;
    }


    /**
     * Sets the shadowlimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param shadowlimit
     */
    public void setShadowlimit(java.lang.String shadowlimit) {
        this.shadowlimit = shadowlimit;
    }


    /**
     * Gets the tankedutil value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return tankedutil
     */
    public java.math.BigDecimal getTankedutil() {
        return tankedutil;
    }


    /**
     * Sets the tankedutil value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param tankedutil
     */
    public void setTankedutil(java.math.BigDecimal tankedutil) {
        this.tankedutil = tankedutil;
    }


    /**
     * Gets the tenorresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return tenorresttype
     */
    public java.lang.String getTenorresttype() {
        return tenorresttype;
    }


    /**
     * Sets the tenorresttype value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param tenorresttype
     */
    public void setTenorresttype(java.lang.String tenorresttype) {
        this.tenorresttype = tenorresttype;
    }


    /**
     * Gets the transferamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return transferamount
     */
    public java.math.BigDecimal getTransferamount() {
        return transferamount;
    }


    /**
     * Sets the transferamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param transferamount
     */
    public void setTransferamount(java.math.BigDecimal transferamount) {
        this.transferamount = transferamount;
    }


    /**
     * Gets the udfdetails value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return udfdetails
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] getUdfdetails() {
        return udfdetails;
    }


    /**
     * Sets the udfdetails value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param udfdetails
     */
    public void setUdfdetails(com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] udfdetails) {
        this.udfdetails = udfdetails;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType getUdfdetails(int i) {
        return this.udfdetails[i];
    }

    public void setUdfdetails(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType _value) {
        this.udfdetails[i] = _value;
    }


    /**
     * Gets the unadvised value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return unadvised
     */
    public java.lang.String getUnadvised() {
        return unadvised;
    }


    /**
     * Sets the unadvised value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param unadvised
     */
    public void setUnadvised(java.lang.String unadvised) {
        this.unadvised = unadvised;
    }


    /**
     * Gets the uncollectedamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return uncollectedamount
     */
    public java.math.BigDecimal getUncollectedamount() {
        return uncollectedamount;
    }


    /**
     * Sets the uncollectedamount value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param uncollectedamount
     */
    public void setUncollectedamount(java.math.BigDecimal uncollectedamount) {
        this.uncollectedamount = uncollectedamount;
    }


    /**
     * Gets the uncollectedfundslimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return uncollectedfundslimit
     */
    public java.math.BigDecimal getUncollectedfundslimit() {
        return uncollectedfundslimit;
    }


    /**
     * Sets the uncollectedfundslimit value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param uncollectedfundslimit
     */
    public void setUncollectedfundslimit(java.math.BigDecimal uncollectedfundslimit) {
        this.uncollectedfundslimit = uncollectedfundslimit;
    }


    /**
     * Gets the userdefinestatus value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return userdefinestatus
     */
    public java.lang.String getUserdefinestatus() {
        return userdefinestatus;
    }


    /**
     * Sets the userdefinestatus value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param userdefinestatus
     */
    public void setUserdefinestatus(java.lang.String userdefinestatus) {
        this.userdefinestatus = userdefinestatus;
    }


    /**
     * Gets the userdefstatchangeddt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return userdefstatchangeddt
     */
    public java.lang.String getUserdefstatchangeddt() {
        return userdefstatchangeddt;
    }


    /**
     * Sets the userdefstatchangeddt value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param userdefstatchangeddt
     */
    public void setUserdefstatchangeddt(java.lang.String userdefstatchangeddt) {
        this.userdefstatchangeddt = userdefstatchangeddt;
    }


    /**
     * Gets the userrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return userrefno
     */
    public java.lang.String getUserrefno() {
        return userrefno;
    }


    /**
     * Sets the userrefno value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param userrefno
     */
    public void setUserrefno(java.lang.String userrefno) {
        this.userrefno = userrefno;
    }


    /**
     * Gets the utilisation value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @return utilisation
     */
    public java.math.BigDecimal getUtilisation() {
        return utilisation;
    }


    /**
     * Sets the utilisation value for this FacilityDescriptionFullTypeResponseDTO.
     * 
     * @param utilisation
     */
    public void setUtilisation(java.math.BigDecimal utilisation) {
        this.utilisation = utilisation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityDescriptionFullTypeResponseDTO)) return false;
        FacilityDescriptionFullTypeResponseDTO other = (FacilityDescriptionFullTypeResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amountreinstatedtoday==null && other.getAmountreinstatedtoday()==null) || 
             (this.amountreinstatedtoday!=null &&
              this.amountreinstatedtoday.equals(other.getAmountreinstatedtoday()))) &&
            ((this.amountutilisedtoday==null && other.getAmountutilisedtoday()==null) || 
             (this.amountutilisedtoday!=null &&
              this.amountutilisedtoday.equals(other.getAmountutilisedtoday()))) &&
            ((this.approvedamt==null && other.getApprovedamt()==null) || 
             (this.approvedamt!=null &&
              this.approvedamt.equals(other.getApprovedamt()))) &&
            ((this.authstat==null && other.getAuthstat()==null) || 
             (this.authstat!=null &&
              this.authstat.equals(other.getAuthstat()))) &&
            ((this.availabilityflag==null && other.getAvailabilityflag()==null) || 
             (this.availabilityflag!=null &&
              this.availabilityflag.equals(other.getAvailabilityflag()))) &&
            ((this.availableamount==null && other.getAvailableamount()==null) || 
             (this.availableamount!=null &&
              this.availableamount.equals(other.getAvailableamount()))) &&
            ((this.availmentdate==null && other.getAvailmentdate()==null) || 
             (this.availmentdate!=null &&
              this.availmentdate.equals(other.getAvailmentdate()))) &&
            ((this.blockamount==null && other.getBlockamount()==null) || 
             (this.blockamount!=null &&
              this.blockamount.equals(other.getBlockamount()))) &&
            ((this.branchresttype==null && other.getBranchresttype()==null) || 
             (this.branchresttype!=null &&
              this.branchresttype.equals(other.getBranchresttype()))) &&
            ((this.brn==null && other.getBrn()==null) || 
             (this.brn!=null &&
              this.brn.equals(other.getBrn()))) &&
            ((this.bulkpmtreqd==null && other.getBulkpmtreqd()==null) || 
             (this.bulkpmtreqd!=null &&
              this.bulkpmtreqd.equals(other.getBulkpmtreqd()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.ccyrest==null && other.getCcyrest()==null) || 
             (this.ccyrest!=null &&
              this.ccyrest.equals(other.getCcyrest()))) &&
            ((this.ccyresttype==null && other.getCcyresttype()==null) || 
             (this.ccyresttype!=null &&
              this.ccyresttype.equals(other.getCcyresttype()))) &&
            ((this.checkerdtstamp==null && other.getCheckerdtstamp()==null) || 
             (this.checkerdtstamp!=null &&
              this.checkerdtstamp.equals(other.getCheckerdtstamp()))) &&
            ((this.checkerid==null && other.getCheckerid()==null) || 
             (this.checkerid!=null &&
              this.checkerid.equals(other.getCheckerid()))) &&
            ((this.collateralcontribution==null && other.getCollateralcontribution()==null) || 
             (this.collateralcontribution!=null &&
              this.collateralcontribution.equals(other.getCollateralcontribution()))) &&
            ((this.collateralpct==null && other.getCollateralpct()==null) || 
             (this.collateralpct!=null &&
              this.collateralpct.equals(other.getCollateralpct()))) &&
            ((this.commitmentproduct==null && other.getCommitmentproduct()==null) || 
             (this.commitmentproduct!=null &&
              this.commitmentproduct.equals(other.getCommitmentproduct()))) &&
            ((this.commitmentsettlacc==null && other.getCommitmentsettlacc()==null) || 
             (this.commitmentsettlacc!=null &&
              this.commitmentsettlacc.equals(other.getCommitmentsettlacc()))) &&
            ((this.commitmentsettlbrn==null && other.getCommitmentsettlbrn()==null) || 
             (this.commitmentsettlbrn!=null &&
              this.commitmentsettlbrn.equals(other.getCommitmentsettlbrn()))) &&
            ((this.conversiondate==null && other.getConversiondate()==null) || 
             (this.conversiondate!=null &&
              this.conversiondate.equals(other.getConversiondate()))) &&
            ((this.customerresttype==null && other.getCustomerresttype()==null) || 
             (this.customerresttype!=null &&
              this.customerresttype.equals(other.getCustomerresttype()))) &&
            ((this.dateoffirstod==null && other.getDateoffirstod()==null) || 
             (this.dateoffirstod!=null &&
              this.dateoffirstod.equals(other.getDateoffirstod()))) &&
            ((this.dateoflastod==null && other.getDateoflastod()==null) || 
             (this.dateoflastod!=null &&
              this.dateoflastod.equals(other.getDateoflastod()))) &&
            ((this.daylightlimit==null && other.getDaylightlimit()==null) || 
             (this.daylightlimit!=null &&
              this.daylightlimit.equals(other.getDaylightlimit()))) &&
            ((this.daylightodlimit==null && other.getDaylightodlimit()==null) || 
             (this.daylightodlimit!=null &&
              this.daylightodlimit.equals(other.getDaylightodlimit()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.dspefflineamount==null && other.getDspefflineamount()==null) || 
             (this.dspefflineamount!=null &&
              this.dspefflineamount.equals(other.getDspefflineamount()))) &&
            ((this.excepbreach==null && other.getExcepbreach()==null) || 
             (this.excepbreach!=null &&
              this.excepbreach.equals(other.getExcepbreach()))) &&
            ((this.exceptxnamt==null && other.getExceptxnamt()==null) || 
             (this.exceptxnamt!=null &&
              this.exceptxnamt.equals(other.getExceptxnamt()))) &&
            ((this.excesstenor==null && other.getExcesstenor()==null) || 
             (this.excesstenor!=null &&
              this.excesstenor.equals(other.getExcesstenor()))) &&
            ((this.expresttype==null && other.getExpresttype()==null) || 
             (this.expresttype!=null &&
              this.expresttype.equals(other.getExpresttype()))) &&
            ((this.externalrefno==null && other.getExternalrefno()==null) || 
             (this.externalrefno!=null &&
              this.externalrefno.equals(other.getExternalrefno()))) &&
            ((this.extsystemresttype==null && other.getExtsystemresttype()==null) || 
             (this.extsystemresttype!=null &&
              this.extsystemresttype.equals(other.getExtsystemresttype()))) &&
            ((this.facilityBranchRestriction==null && other.getFacilityBranchRestriction()==null) || 
             (this.facilityBranchRestriction!=null &&
              java.util.Arrays.equals(this.facilityBranchRestriction, other.getFacilityBranchRestriction()))) &&
            ((this.facilityCharge==null && other.getFacilityCharge()==null) || 
             (this.facilityCharge!=null &&
              this.facilityCharge.equals(other.getFacilityCharge()))) &&
            ((this.facilityCovenant==null && other.getFacilityCovenant()==null) || 
             (this.facilityCovenant!=null &&
              java.util.Arrays.equals(this.facilityCovenant, other.getFacilityCovenant()))) &&
            ((this.facilityCurrencyRestriction==null && other.getFacilityCurrencyRestriction()==null) || 
             (this.facilityCurrencyRestriction!=null &&
              java.util.Arrays.equals(this.facilityCurrencyRestriction, other.getFacilityCurrencyRestriction()))) &&
            ((this.facilityCustomerRestriction==null && other.getFacilityCustomerRestriction()==null) || 
             (this.facilityCustomerRestriction!=null &&
              java.util.Arrays.equals(this.facilityCustomerRestriction, other.getFacilityCustomerRestriction()))) &&
            ((this.facilityExposure==null && other.getFacilityExposure()==null) || 
             (this.facilityExposure!=null &&
              java.util.Arrays.equals(this.facilityExposure, other.getFacilityExposure()))) &&
            ((this.facilityExposureRestriction==null && other.getFacilityExposureRestriction()==null) || 
             (this.facilityExposureRestriction!=null &&
              java.util.Arrays.equals(this.facilityExposureRestriction, other.getFacilityExposureRestriction()))) &&
            ((this.facilityMandate==null && other.getFacilityMandate()==null) || 
             (this.facilityMandate!=null &&
              java.util.Arrays.equals(this.facilityMandate, other.getFacilityMandate()))) &&
            ((this.facilityPoolLink==null && other.getFacilityPoolLink()==null) || 
             (this.facilityPoolLink!=null &&
              java.util.Arrays.equals(this.facilityPoolLink, other.getFacilityPoolLink()))) &&
            ((this.facilityProductRestriction==null && other.getFacilityProductRestriction()==null) || 
             (this.facilityProductRestriction!=null &&
              java.util.Arrays.equals(this.facilityProductRestriction, other.getFacilityProductRestriction()))) &&
            ((this.facilitySchedules==null && other.getFacilitySchedules()==null) || 
             (this.facilitySchedules!=null &&
              java.util.Arrays.equals(this.facilitySchedules, other.getFacilitySchedules()))) &&
            ((this.facilitySystemRestriction==null && other.getFacilitySystemRestriction()==null) || 
             (this.facilitySystemRestriction!=null &&
              java.util.Arrays.equals(this.facilitySystemRestriction, other.getFacilitySystemRestriction()))) &&
            ((this.facilityTenorRestriction==null && other.getFacilityTenorRestriction()==null) || 
             (this.facilityTenorRestriction!=null &&
              java.util.Arrays.equals(this.facilityTenorRestriction, other.getFacilityTenorRestriction()))) &&
            ((this.facilityUDEValues==null && other.getFacilityUDEValues()==null) || 
             (this.facilityUDEValues!=null &&
              java.util.Arrays.equals(this.facilityUDEValues, other.getFacilityUDEValues()))) &&
            ((this.facilityValueDateDetails==null && other.getFacilityValueDateDetails()==null) || 
             (this.facilityValueDateDetails!=null &&
              java.util.Arrays.equals(this.facilityValueDateDetails, other.getFacilityValueDateDetails()))) &&
            ((this.funded==null && other.getFunded()==null) || 
             (this.funded!=null &&
              this.funded.equals(other.getFunded()))) &&
            ((this.internalremarks==null && other.getInternalremarks()==null) || 
             (this.internalremarks!=null &&
              this.internalremarks.equals(other.getInternalremarks()))) &&
            ((this.lastnewutildate==null && other.getLastnewutildate()==null) || 
             (this.lastnewutildate!=null &&
              this.lastnewutildate.equals(other.getLastnewutildate()))) &&
            ((this.liabbr==null && other.getLiabbr()==null) || 
             (this.liabbr!=null &&
              this.liabbr.equals(other.getLiabbr()))) &&
            ((this.liabid==null && other.getLiabid()==null) || 
             (this.liabid!=null &&
              this.liabid.equals(other.getLiabid()))) &&
            ((this.liabno==null && other.getLiabno()==null) || 
             (this.liabno!=null &&
              this.liabno.equals(other.getLiabno()))) &&
            ((this.limitamount==null && other.getLimitamount()==null) || 
             (this.limitamount!=null &&
              this.limitamount.equals(other.getLimitamount()))) &&
            ((this.lineccy==null && other.getLineccy()==null) || 
             (this.lineccy!=null &&
              this.lineccy.equals(other.getLineccy()))) &&
            ((this.linecode==null && other.getLinecode()==null) || 
             (this.linecode!=null &&
              this.linecode.equals(other.getLinecode()))) &&
            ((this.lineexpirydate==null && other.getLineexpirydate()==null) || 
             (this.lineexpirydate!=null &&
              this.lineexpirydate.equals(other.getLineexpirydate()))) &&
            ((this.lineserial==null && other.getLineserial()==null) || 
             (this.lineserial!=null &&
              this.lineserial.equals(other.getLineserial()))) &&
            ((this.linestartdate==null && other.getLinestartdate()==null) || 
             (this.linestartdate!=null &&
              this.linestartdate.equals(other.getLinestartdate()))) &&
            ((this.lmtamtbasis==null && other.getLmtamtbasis()==null) || 
             (this.lmtamtbasis!=null &&
              this.lmtamtbasis.equals(other.getLmtamtbasis()))) &&
            ((this.mainlineid==null && other.getMainlineid()==null) || 
             (this.mainlineid!=null &&
              this.mainlineid.equals(other.getMainlineid()))) &&
            ((this.makerdtstamp==null && other.getMakerdtstamp()==null) || 
             (this.makerdtstamp!=null &&
              this.makerdtstamp.equals(other.getMakerdtstamp()))) &&
            ((this.makerid==null && other.getMakerid()==null) || 
             (this.makerid!=null &&
              this.makerid.equals(other.getMakerid()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno()))) &&
            ((this.nettingamount==null && other.getNettingamount()==null) || 
             (this.nettingamount!=null &&
              this.nettingamount.equals(other.getNettingamount()))) &&
            ((this.nettingrequired==null && other.getNettingrequired()==null) || 
             (this.nettingrequired!=null &&
              this.nettingrequired.equals(other.getNettingrequired()))) &&
            ((this.onceauth==null && other.getOnceauth()==null) || 
             (this.onceauth!=null &&
              this.onceauth.equals(other.getOnceauth()))) &&
            ((this.ppcprojectid==null && other.getPpcprojectid()==null) || 
             (this.ppcprojectid!=null &&
              this.ppcprojectid.equals(other.getPpcprojectid()))) &&
            ((this.ppcrefno==null && other.getPpcrefno()==null) || 
             (this.ppcrefno!=null &&
              this.ppcrefno.equals(other.getPpcrefno()))) &&
            ((this.processno==null && other.getProcessno()==null) || 
             (this.processno!=null &&
              this.processno.equals(other.getProcessno()))) &&
            ((this.productresttype==null && other.getProductresttype()==null) || 
             (this.productresttype!=null &&
              this.productresttype.equals(other.getProductresttype()))) &&
            ((this.recstat==null && other.getRecstat()==null) || 
             (this.recstat!=null &&
              this.recstat.equals(other.getRecstat()))) &&
            ((this.reportingamount==null && other.getReportingamount()==null) || 
             (this.reportingamount!=null &&
              this.reportingamount.equals(other.getReportingamount()))) &&
            ((this.revolvingline==null && other.getRevolvingline()==null) || 
             (this.revolvingline!=null &&
              this.revolvingline.equals(other.getRevolvingline()))) &&
            ((this.shadowlimit==null && other.getShadowlimit()==null) || 
             (this.shadowlimit!=null &&
              this.shadowlimit.equals(other.getShadowlimit()))) &&
            ((this.tankedutil==null && other.getTankedutil()==null) || 
             (this.tankedutil!=null &&
              this.tankedutil.equals(other.getTankedutil()))) &&
            ((this.tenorresttype==null && other.getTenorresttype()==null) || 
             (this.tenorresttype!=null &&
              this.tenorresttype.equals(other.getTenorresttype()))) &&
            ((this.transferamount==null && other.getTransferamount()==null) || 
             (this.transferamount!=null &&
              this.transferamount.equals(other.getTransferamount()))) &&
            ((this.udfdetails==null && other.getUdfdetails()==null) || 
             (this.udfdetails!=null &&
              java.util.Arrays.equals(this.udfdetails, other.getUdfdetails()))) &&
            ((this.unadvised==null && other.getUnadvised()==null) || 
             (this.unadvised!=null &&
              this.unadvised.equals(other.getUnadvised()))) &&
            ((this.uncollectedamount==null && other.getUncollectedamount()==null) || 
             (this.uncollectedamount!=null &&
              this.uncollectedamount.equals(other.getUncollectedamount()))) &&
            ((this.uncollectedfundslimit==null && other.getUncollectedfundslimit()==null) || 
             (this.uncollectedfundslimit!=null &&
              this.uncollectedfundslimit.equals(other.getUncollectedfundslimit()))) &&
            ((this.userdefinestatus==null && other.getUserdefinestatus()==null) || 
             (this.userdefinestatus!=null &&
              this.userdefinestatus.equals(other.getUserdefinestatus()))) &&
            ((this.userdefstatchangeddt==null && other.getUserdefstatchangeddt()==null) || 
             (this.userdefstatchangeddt!=null &&
              this.userdefstatchangeddt.equals(other.getUserdefstatchangeddt()))) &&
            ((this.userrefno==null && other.getUserrefno()==null) || 
             (this.userrefno!=null &&
              this.userrefno.equals(other.getUserrefno()))) &&
            ((this.utilisation==null && other.getUtilisation()==null) || 
             (this.utilisation!=null &&
              this.utilisation.equals(other.getUtilisation())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAmountreinstatedtoday() != null) {
            _hashCode += getAmountreinstatedtoday().hashCode();
        }
        if (getAmountutilisedtoday() != null) {
            _hashCode += getAmountutilisedtoday().hashCode();
        }
        if (getApprovedamt() != null) {
            _hashCode += getApprovedamt().hashCode();
        }
        if (getAuthstat() != null) {
            _hashCode += getAuthstat().hashCode();
        }
        if (getAvailabilityflag() != null) {
            _hashCode += getAvailabilityflag().hashCode();
        }
        if (getAvailableamount() != null) {
            _hashCode += getAvailableamount().hashCode();
        }
        if (getAvailmentdate() != null) {
            _hashCode += getAvailmentdate().hashCode();
        }
        if (getBlockamount() != null) {
            _hashCode += getBlockamount().hashCode();
        }
        if (getBranchresttype() != null) {
            _hashCode += getBranchresttype().hashCode();
        }
        if (getBrn() != null) {
            _hashCode += getBrn().hashCode();
        }
        if (getBulkpmtreqd() != null) {
            _hashCode += getBulkpmtreqd().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getCcyrest() != null) {
            _hashCode += getCcyrest().hashCode();
        }
        if (getCcyresttype() != null) {
            _hashCode += getCcyresttype().hashCode();
        }
        if (getCheckerdtstamp() != null) {
            _hashCode += getCheckerdtstamp().hashCode();
        }
        if (getCheckerid() != null) {
            _hashCode += getCheckerid().hashCode();
        }
        if (getCollateralcontribution() != null) {
            _hashCode += getCollateralcontribution().hashCode();
        }
        if (getCollateralpct() != null) {
            _hashCode += getCollateralpct().hashCode();
        }
        if (getCommitmentproduct() != null) {
            _hashCode += getCommitmentproduct().hashCode();
        }
        if (getCommitmentsettlacc() != null) {
            _hashCode += getCommitmentsettlacc().hashCode();
        }
        if (getCommitmentsettlbrn() != null) {
            _hashCode += getCommitmentsettlbrn().hashCode();
        }
        if (getConversiondate() != null) {
            _hashCode += getConversiondate().hashCode();
        }
        if (getCustomerresttype() != null) {
            _hashCode += getCustomerresttype().hashCode();
        }
        if (getDateoffirstod() != null) {
            _hashCode += getDateoffirstod().hashCode();
        }
        if (getDateoflastod() != null) {
            _hashCode += getDateoflastod().hashCode();
        }
        if (getDaylightlimit() != null) {
            _hashCode += getDaylightlimit().hashCode();
        }
        if (getDaylightodlimit() != null) {
            _hashCode += getDaylightodlimit().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDspefflineamount() != null) {
            _hashCode += getDspefflineamount().hashCode();
        }
        if (getExcepbreach() != null) {
            _hashCode += getExcepbreach().hashCode();
        }
        if (getExceptxnamt() != null) {
            _hashCode += getExceptxnamt().hashCode();
        }
        if (getExcesstenor() != null) {
            _hashCode += getExcesstenor().hashCode();
        }
        if (getExpresttype() != null) {
            _hashCode += getExpresttype().hashCode();
        }
        if (getExternalrefno() != null) {
            _hashCode += getExternalrefno().hashCode();
        }
        if (getExtsystemresttype() != null) {
            _hashCode += getExtsystemresttype().hashCode();
        }
        if (getFacilityBranchRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityBranchRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityBranchRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityCharge() != null) {
            _hashCode += getFacilityCharge().hashCode();
        }
        if (getFacilityCovenant() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityCovenant());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityCovenant(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityCurrencyRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityCurrencyRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityCurrencyRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityCustomerRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityCustomerRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityCustomerRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityExposure() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityExposure());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityExposure(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityExposureRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityExposureRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityExposureRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityMandate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityMandate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityMandate(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityPoolLink() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityPoolLink());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityPoolLink(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityProductRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityProductRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityProductRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilitySchedules() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilitySchedules());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilitySchedules(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilitySystemRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilitySystemRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilitySystemRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityTenorRestriction() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityTenorRestriction());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityTenorRestriction(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityUDEValues() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityUDEValues());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityUDEValues(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFacilityValueDateDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFacilityValueDateDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFacilityValueDateDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFunded() != null) {
            _hashCode += getFunded().hashCode();
        }
        if (getInternalremarks() != null) {
            _hashCode += getInternalremarks().hashCode();
        }
        if (getLastnewutildate() != null) {
            _hashCode += getLastnewutildate().hashCode();
        }
        if (getLiabbr() != null) {
            _hashCode += getLiabbr().hashCode();
        }
        if (getLiabid() != null) {
            _hashCode += getLiabid().hashCode();
        }
        if (getLiabno() != null) {
            _hashCode += getLiabno().hashCode();
        }
        if (getLimitamount() != null) {
            _hashCode += getLimitamount().hashCode();
        }
        if (getLineccy() != null) {
            _hashCode += getLineccy().hashCode();
        }
        if (getLinecode() != null) {
            _hashCode += getLinecode().hashCode();
        }
        if (getLineexpirydate() != null) {
            _hashCode += getLineexpirydate().hashCode();
        }
        if (getLineserial() != null) {
            _hashCode += getLineserial().hashCode();
        }
        if (getLinestartdate() != null) {
            _hashCode += getLinestartdate().hashCode();
        }
        if (getLmtamtbasis() != null) {
            _hashCode += getLmtamtbasis().hashCode();
        }
        if (getMainlineid() != null) {
            _hashCode += getMainlineid().hashCode();
        }
        if (getMakerdtstamp() != null) {
            _hashCode += getMakerdtstamp().hashCode();
        }
        if (getMakerid() != null) {
            _hashCode += getMakerid().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        if (getNettingamount() != null) {
            _hashCode += getNettingamount().hashCode();
        }
        if (getNettingrequired() != null) {
            _hashCode += getNettingrequired().hashCode();
        }
        if (getOnceauth() != null) {
            _hashCode += getOnceauth().hashCode();
        }
        if (getPpcprojectid() != null) {
            _hashCode += getPpcprojectid().hashCode();
        }
        if (getPpcrefno() != null) {
            _hashCode += getPpcrefno().hashCode();
        }
        if (getProcessno() != null) {
            _hashCode += getProcessno().hashCode();
        }
        if (getProductresttype() != null) {
            _hashCode += getProductresttype().hashCode();
        }
        if (getRecstat() != null) {
            _hashCode += getRecstat().hashCode();
        }
        if (getReportingamount() != null) {
            _hashCode += getReportingamount().hashCode();
        }
        if (getRevolvingline() != null) {
            _hashCode += getRevolvingline().hashCode();
        }
        if (getShadowlimit() != null) {
            _hashCode += getShadowlimit().hashCode();
        }
        if (getTankedutil() != null) {
            _hashCode += getTankedutil().hashCode();
        }
        if (getTenorresttype() != null) {
            _hashCode += getTenorresttype().hashCode();
        }
        if (getTransferamount() != null) {
            _hashCode += getTransferamount().hashCode();
        }
        if (getUdfdetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUdfdetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUdfdetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUnadvised() != null) {
            _hashCode += getUnadvised().hashCode();
        }
        if (getUncollectedamount() != null) {
            _hashCode += getUncollectedamount().hashCode();
        }
        if (getUncollectedfundslimit() != null) {
            _hashCode += getUncollectedfundslimit().hashCode();
        }
        if (getUserdefinestatus() != null) {
            _hashCode += getUserdefinestatus().hashCode();
        }
        if (getUserdefstatchangeddt() != null) {
            _hashCode += getUserdefstatchangeddt().hashCode();
        }
        if (getUserrefno() != null) {
            _hashCode += getUserrefno().hashCode();
        }
        if (getUtilisation() != null) {
            _hashCode += getUtilisation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityDescriptionFullTypeResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityDescriptionFullTypeResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountreinstatedtoday");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amountreinstatedtoday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountutilisedtoday");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amountutilisedtoday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvedamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvedamt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authstat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authstat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availabilityflag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availabilityflag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availableamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availmentdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availmentdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blockamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bulkpmtreqd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bulkpmtreqd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccyrest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccyrest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccyresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccyresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkerdtstamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checkerdtstamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkerid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checkerid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collateralcontribution");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collateralcontribution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collateralpct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collateralpct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitmentproduct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commitmentproduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitmentsettlacc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commitmentsettlacc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitmentsettlbrn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "commitmentsettlbrn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conversiondate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conversiondate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateoffirstod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateoffirstod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateoflastod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateoflastod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("daylightlimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "daylightlimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("daylightodlimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "daylightodlimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dspefflineamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dspefflineamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excepbreach");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excepbreach"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exceptxnamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "exceptxnamt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excesstenor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excesstenor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalrefno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalrefno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extsystemresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extsystemresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityBranchRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityBranchRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityBranchRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCharge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityCovenant");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityCovenant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCovenant"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityCurrencyRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityCurrencyRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCurrencyRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityCustomerRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityCustomerRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCustomerRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityExposure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityExposure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityExposure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityExposureRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityExposureRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityExposureRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityMandate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityMandate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityMandate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityPoolLink");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityPoolLink"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityPoolLink"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityProductRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityProductRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityProductRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilitySchedules");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilitySchedules"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySchedules"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilitySystemRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilitySystemRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySystemRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityTenorRestriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityTenorRestriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityTenorRestriction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityUDEValues");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityUDEValues"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityUDEValues"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityValueDateDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityValueDateDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityValueDateDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funded");
        elemField.setXmlName(new javax.xml.namespace.QName("", "funded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("internalremarks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "internalremarks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastnewutildate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastnewutildate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineccy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineccy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linecode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineexpirydate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineexpirydate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineserial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineserial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linestartdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linestartdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lmtamtbasis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lmtamtbasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainlineid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mainlineid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("makerdtstamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "makerdtstamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("makerid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "makerid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nettingamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nettingamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nettingrequired");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nettingrequired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onceauth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onceauth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ppcprojectid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ppcprojectid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ppcrefno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ppcrefno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "processno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recstat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recstat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportingamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportingamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revolvingline");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revolvingline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shadowlimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shadowlimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tankedutil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tankedutil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenorresttype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tenorresttype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "transferamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udfdetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "udfdetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "udfDetailsType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unadvised");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unadvised"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uncollectedamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uncollectedamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uncollectedfundslimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uncollectedfundslimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userdefinestatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userdefinestatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userdefstatchangeddt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userdefstatchangeddt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userrefno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userrefno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilisation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilisation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
