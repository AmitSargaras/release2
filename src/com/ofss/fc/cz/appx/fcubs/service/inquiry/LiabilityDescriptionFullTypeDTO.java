/**
 * LiabilityDescriptionFullTypeDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class LiabilityDescriptionFullTypeDTO  implements java.io.Serializable {
    private java.lang.String authstat;

    private java.lang.String category;

    private java.lang.String checkerdtstamp;

    private java.lang.String checkerid;

    private java.lang.String conversiondate;

    private java.lang.String creditrating;

    private java.math.BigDecimal fxcleanrisklimit;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating[] liabilityCreditRating;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore[] liabilityScore;

    private java.lang.String liabilitybranch;

    private java.lang.String liabilityccy;

    private java.lang.String liabilityname;

    private java.lang.String liabilityno;

    private java.math.BigDecimal mainliabno;

    private java.lang.String makerdtstamp;

    private java.lang.String makerid;

    private java.math.BigDecimal modno;

    private java.lang.String nettingrequired;

    private java.lang.String onceauth;

    private java.math.BigDecimal overalllimit;

    private java.math.BigDecimal overallscore;

    private java.math.BigDecimal processno;

    private java.lang.String recstat;

    private java.lang.String revisiondate;

    private java.math.BigDecimal seccleanrisklimit;

    private java.math.BigDecimal secpstlrisklimit;

    private java.lang.String source;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] udfdetails;

    private java.lang.String unadvised;

    private java.lang.String userdefinestatus;

    private java.lang.String userreferenceno;

    private java.math.BigDecimal utilisationamt;

    public LiabilityDescriptionFullTypeDTO() {
    }

    public LiabilityDescriptionFullTypeDTO(
           java.lang.String authstat,
           java.lang.String category,
           java.lang.String checkerdtstamp,
           java.lang.String checkerid,
           java.lang.String conversiondate,
           java.lang.String creditrating,
           java.math.BigDecimal fxcleanrisklimit,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating[] liabilityCreditRating,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore[] liabilityScore,
           java.lang.String liabilitybranch,
           java.lang.String liabilityccy,
           java.lang.String liabilityname,
           java.lang.String liabilityno,
           java.math.BigDecimal mainliabno,
           java.lang.String makerdtstamp,
           java.lang.String makerid,
           java.math.BigDecimal modno,
           java.lang.String nettingrequired,
           java.lang.String onceauth,
           java.math.BigDecimal overalllimit,
           java.math.BigDecimal overallscore,
           java.math.BigDecimal processno,
           java.lang.String recstat,
           java.lang.String revisiondate,
           java.math.BigDecimal seccleanrisklimit,
           java.math.BigDecimal secpstlrisklimit,
           java.lang.String source,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] udfdetails,
           java.lang.String unadvised,
           java.lang.String userdefinestatus,
           java.lang.String userreferenceno,
           java.math.BigDecimal utilisationamt) {
           this.authstat = authstat;
           this.category = category;
           this.checkerdtstamp = checkerdtstamp;
           this.checkerid = checkerid;
           this.conversiondate = conversiondate;
           this.creditrating = creditrating;
           this.fxcleanrisklimit = fxcleanrisklimit;
           this.liabilityCreditRating = liabilityCreditRating;
           this.liabilityScore = liabilityScore;
           this.liabilitybranch = liabilitybranch;
           this.liabilityccy = liabilityccy;
           this.liabilityname = liabilityname;
           this.liabilityno = liabilityno;
           this.mainliabno = mainliabno;
           this.makerdtstamp = makerdtstamp;
           this.makerid = makerid;
           this.modno = modno;
           this.nettingrequired = nettingrequired;
           this.onceauth = onceauth;
           this.overalllimit = overalllimit;
           this.overallscore = overallscore;
           this.processno = processno;
           this.recstat = recstat;
           this.revisiondate = revisiondate;
           this.seccleanrisklimit = seccleanrisklimit;
           this.secpstlrisklimit = secpstlrisklimit;
           this.source = source;
           this.udfdetails = udfdetails;
           this.unadvised = unadvised;
           this.userdefinestatus = userdefinestatus;
           this.userreferenceno = userreferenceno;
           this.utilisationamt = utilisationamt;
    }


    /**
     * Gets the authstat value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return authstat
     */
    public java.lang.String getAuthstat() {
        return authstat;
    }


    /**
     * Sets the authstat value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param authstat
     */
    public void setAuthstat(java.lang.String authstat) {
        this.authstat = authstat;
    }


    /**
     * Gets the category value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the checkerdtstamp value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return checkerdtstamp
     */
    public java.lang.String getCheckerdtstamp() {
        return checkerdtstamp;
    }


    /**
     * Sets the checkerdtstamp value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param checkerdtstamp
     */
    public void setCheckerdtstamp(java.lang.String checkerdtstamp) {
        this.checkerdtstamp = checkerdtstamp;
    }


    /**
     * Gets the checkerid value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return checkerid
     */
    public java.lang.String getCheckerid() {
        return checkerid;
    }


    /**
     * Sets the checkerid value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param checkerid
     */
    public void setCheckerid(java.lang.String checkerid) {
        this.checkerid = checkerid;
    }


    /**
     * Gets the conversiondate value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return conversiondate
     */
    public java.lang.String getConversiondate() {
        return conversiondate;
    }


    /**
     * Sets the conversiondate value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param conversiondate
     */
    public void setConversiondate(java.lang.String conversiondate) {
        this.conversiondate = conversiondate;
    }


    /**
     * Gets the creditrating value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return creditrating
     */
    public java.lang.String getCreditrating() {
        return creditrating;
    }


    /**
     * Sets the creditrating value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param creditrating
     */
    public void setCreditrating(java.lang.String creditrating) {
        this.creditrating = creditrating;
    }


    /**
     * Gets the fxcleanrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return fxcleanrisklimit
     */
    public java.math.BigDecimal getFxcleanrisklimit() {
        return fxcleanrisklimit;
    }


    /**
     * Sets the fxcleanrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param fxcleanrisklimit
     */
    public void setFxcleanrisklimit(java.math.BigDecimal fxcleanrisklimit) {
        this.fxcleanrisklimit = fxcleanrisklimit;
    }


    /**
     * Gets the liabilityCreditRating value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilityCreditRating
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating[] getLiabilityCreditRating() {
        return liabilityCreditRating;
    }


    /**
     * Sets the liabilityCreditRating value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilityCreditRating
     */
    public void setLiabilityCreditRating(com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating[] liabilityCreditRating) {
        this.liabilityCreditRating = liabilityCreditRating;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating getLiabilityCreditRating(int i) {
        return this.liabilityCreditRating[i];
    }

    public void setLiabilityCreditRating(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityCreditRating _value) {
        this.liabilityCreditRating[i] = _value;
    }


    /**
     * Gets the liabilityScore value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilityScore
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore[] getLiabilityScore() {
        return liabilityScore;
    }


    /**
     * Sets the liabilityScore value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilityScore
     */
    public void setLiabilityScore(com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore[] liabilityScore) {
        this.liabilityScore = liabilityScore;
    }

    public com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore getLiabilityScore(int i) {
        return this.liabilityScore[i];
    }

    public void setLiabilityScore(int i, com.ofss.fc.cz.appx.fcubs.service.inquiry.LiabilityScore _value) {
        this.liabilityScore[i] = _value;
    }


    /**
     * Gets the liabilitybranch value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilitybranch
     */
    public java.lang.String getLiabilitybranch() {
        return liabilitybranch;
    }


    /**
     * Sets the liabilitybranch value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilitybranch
     */
    public void setLiabilitybranch(java.lang.String liabilitybranch) {
        this.liabilitybranch = liabilitybranch;
    }


    /**
     * Gets the liabilityccy value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilityccy
     */
    public java.lang.String getLiabilityccy() {
        return liabilityccy;
    }


    /**
     * Sets the liabilityccy value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilityccy
     */
    public void setLiabilityccy(java.lang.String liabilityccy) {
        this.liabilityccy = liabilityccy;
    }


    /**
     * Gets the liabilityname value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilityname
     */
    public java.lang.String getLiabilityname() {
        return liabilityname;
    }


    /**
     * Sets the liabilityname value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilityname
     */
    public void setLiabilityname(java.lang.String liabilityname) {
        this.liabilityname = liabilityname;
    }


    /**
     * Gets the liabilityno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return liabilityno
     */
    public java.lang.String getLiabilityno() {
        return liabilityno;
    }


    /**
     * Sets the liabilityno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param liabilityno
     */
    public void setLiabilityno(java.lang.String liabilityno) {
        this.liabilityno = liabilityno;
    }


    /**
     * Gets the mainliabno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return mainliabno
     */
    public java.math.BigDecimal getMainliabno() {
        return mainliabno;
    }


    /**
     * Sets the mainliabno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param mainliabno
     */
    public void setMainliabno(java.math.BigDecimal mainliabno) {
        this.mainliabno = mainliabno;
    }


    /**
     * Gets the makerdtstamp value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return makerdtstamp
     */
    public java.lang.String getMakerdtstamp() {
        return makerdtstamp;
    }


    /**
     * Sets the makerdtstamp value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param makerdtstamp
     */
    public void setMakerdtstamp(java.lang.String makerdtstamp) {
        this.makerdtstamp = makerdtstamp;
    }


    /**
     * Gets the makerid value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return makerid
     */
    public java.lang.String getMakerid() {
        return makerid;
    }


    /**
     * Sets the makerid value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param makerid
     */
    public void setMakerid(java.lang.String makerid) {
        this.makerid = makerid;
    }


    /**
     * Gets the modno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }


    /**
     * Gets the nettingrequired value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return nettingrequired
     */
    public java.lang.String getNettingrequired() {
        return nettingrequired;
    }


    /**
     * Sets the nettingrequired value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param nettingrequired
     */
    public void setNettingrequired(java.lang.String nettingrequired) {
        this.nettingrequired = nettingrequired;
    }


    /**
     * Gets the onceauth value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return onceauth
     */
    public java.lang.String getOnceauth() {
        return onceauth;
    }


    /**
     * Sets the onceauth value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param onceauth
     */
    public void setOnceauth(java.lang.String onceauth) {
        this.onceauth = onceauth;
    }


    /**
     * Gets the overalllimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return overalllimit
     */
    public java.math.BigDecimal getOveralllimit() {
        return overalllimit;
    }


    /**
     * Sets the overalllimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param overalllimit
     */
    public void setOveralllimit(java.math.BigDecimal overalllimit) {
        this.overalllimit = overalllimit;
    }


    /**
     * Gets the overallscore value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return overallscore
     */
    public java.math.BigDecimal getOverallscore() {
        return overallscore;
    }


    /**
     * Sets the overallscore value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param overallscore
     */
    public void setOverallscore(java.math.BigDecimal overallscore) {
        this.overallscore = overallscore;
    }


    /**
     * Gets the processno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return processno
     */
    public java.math.BigDecimal getProcessno() {
        return processno;
    }


    /**
     * Sets the processno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param processno
     */
    public void setProcessno(java.math.BigDecimal processno) {
        this.processno = processno;
    }


    /**
     * Gets the recstat value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return recstat
     */
    public java.lang.String getRecstat() {
        return recstat;
    }


    /**
     * Sets the recstat value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param recstat
     */
    public void setRecstat(java.lang.String recstat) {
        this.recstat = recstat;
    }


    /**
     * Gets the revisiondate value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return revisiondate
     */
    public java.lang.String getRevisiondate() {
        return revisiondate;
    }


    /**
     * Sets the revisiondate value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param revisiondate
     */
    public void setRevisiondate(java.lang.String revisiondate) {
        this.revisiondate = revisiondate;
    }


    /**
     * Gets the seccleanrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return seccleanrisklimit
     */
    public java.math.BigDecimal getSeccleanrisklimit() {
        return seccleanrisklimit;
    }


    /**
     * Sets the seccleanrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param seccleanrisklimit
     */
    public void setSeccleanrisklimit(java.math.BigDecimal seccleanrisklimit) {
        this.seccleanrisklimit = seccleanrisklimit;
    }


    /**
     * Gets the secpstlrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return secpstlrisklimit
     */
    public java.math.BigDecimal getSecpstlrisklimit() {
        return secpstlrisklimit;
    }


    /**
     * Sets the secpstlrisklimit value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param secpstlrisklimit
     */
    public void setSecpstlrisklimit(java.math.BigDecimal secpstlrisklimit) {
        this.secpstlrisklimit = secpstlrisklimit;
    }


    /**
     * Gets the source value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the udfdetails value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return udfdetails
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.UdfDetailsType[] getUdfdetails() {
        return udfdetails;
    }


    /**
     * Sets the udfdetails value for this LiabilityDescriptionFullTypeDTO.
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
     * Gets the unadvised value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return unadvised
     */
    public java.lang.String getUnadvised() {
        return unadvised;
    }


    /**
     * Sets the unadvised value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param unadvised
     */
    public void setUnadvised(java.lang.String unadvised) {
        this.unadvised = unadvised;
    }


    /**
     * Gets the userdefinestatus value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return userdefinestatus
     */
    public java.lang.String getUserdefinestatus() {
        return userdefinestatus;
    }


    /**
     * Sets the userdefinestatus value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param userdefinestatus
     */
    public void setUserdefinestatus(java.lang.String userdefinestatus) {
        this.userdefinestatus = userdefinestatus;
    }


    /**
     * Gets the userreferenceno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return userreferenceno
     */
    public java.lang.String getUserreferenceno() {
        return userreferenceno;
    }


    /**
     * Sets the userreferenceno value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param userreferenceno
     */
    public void setUserreferenceno(java.lang.String userreferenceno) {
        this.userreferenceno = userreferenceno;
    }


    /**
     * Gets the utilisationamt value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @return utilisationamt
     */
    public java.math.BigDecimal getUtilisationamt() {
        return utilisationamt;
    }


    /**
     * Sets the utilisationamt value for this LiabilityDescriptionFullTypeDTO.
     * 
     * @param utilisationamt
     */
    public void setUtilisationamt(java.math.BigDecimal utilisationamt) {
        this.utilisationamt = utilisationamt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiabilityDescriptionFullTypeDTO)) return false;
        LiabilityDescriptionFullTypeDTO other = (LiabilityDescriptionFullTypeDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authstat==null && other.getAuthstat()==null) || 
             (this.authstat!=null &&
              this.authstat.equals(other.getAuthstat()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.checkerdtstamp==null && other.getCheckerdtstamp()==null) || 
             (this.checkerdtstamp!=null &&
              this.checkerdtstamp.equals(other.getCheckerdtstamp()))) &&
            ((this.checkerid==null && other.getCheckerid()==null) || 
             (this.checkerid!=null &&
              this.checkerid.equals(other.getCheckerid()))) &&
            ((this.conversiondate==null && other.getConversiondate()==null) || 
             (this.conversiondate!=null &&
              this.conversiondate.equals(other.getConversiondate()))) &&
            ((this.creditrating==null && other.getCreditrating()==null) || 
             (this.creditrating!=null &&
              this.creditrating.equals(other.getCreditrating()))) &&
            ((this.fxcleanrisklimit==null && other.getFxcleanrisklimit()==null) || 
             (this.fxcleanrisklimit!=null &&
              this.fxcleanrisklimit.equals(other.getFxcleanrisklimit()))) &&
            ((this.liabilityCreditRating==null && other.getLiabilityCreditRating()==null) || 
             (this.liabilityCreditRating!=null &&
              java.util.Arrays.equals(this.liabilityCreditRating, other.getLiabilityCreditRating()))) &&
            ((this.liabilityScore==null && other.getLiabilityScore()==null) || 
             (this.liabilityScore!=null &&
              java.util.Arrays.equals(this.liabilityScore, other.getLiabilityScore()))) &&
            ((this.liabilitybranch==null && other.getLiabilitybranch()==null) || 
             (this.liabilitybranch!=null &&
              this.liabilitybranch.equals(other.getLiabilitybranch()))) &&
            ((this.liabilityccy==null && other.getLiabilityccy()==null) || 
             (this.liabilityccy!=null &&
              this.liabilityccy.equals(other.getLiabilityccy()))) &&
            ((this.liabilityname==null && other.getLiabilityname()==null) || 
             (this.liabilityname!=null &&
              this.liabilityname.equals(other.getLiabilityname()))) &&
            ((this.liabilityno==null && other.getLiabilityno()==null) || 
             (this.liabilityno!=null &&
              this.liabilityno.equals(other.getLiabilityno()))) &&
            ((this.mainliabno==null && other.getMainliabno()==null) || 
             (this.mainliabno!=null &&
              this.mainliabno.equals(other.getMainliabno()))) &&
            ((this.makerdtstamp==null && other.getMakerdtstamp()==null) || 
             (this.makerdtstamp!=null &&
              this.makerdtstamp.equals(other.getMakerdtstamp()))) &&
            ((this.makerid==null && other.getMakerid()==null) || 
             (this.makerid!=null &&
              this.makerid.equals(other.getMakerid()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno()))) &&
            ((this.nettingrequired==null && other.getNettingrequired()==null) || 
             (this.nettingrequired!=null &&
              this.nettingrequired.equals(other.getNettingrequired()))) &&
            ((this.onceauth==null && other.getOnceauth()==null) || 
             (this.onceauth!=null &&
              this.onceauth.equals(other.getOnceauth()))) &&
            ((this.overalllimit==null && other.getOveralllimit()==null) || 
             (this.overalllimit!=null &&
              this.overalllimit.equals(other.getOveralllimit()))) &&
            ((this.overallscore==null && other.getOverallscore()==null) || 
             (this.overallscore!=null &&
              this.overallscore.equals(other.getOverallscore()))) &&
            ((this.processno==null && other.getProcessno()==null) || 
             (this.processno!=null &&
              this.processno.equals(other.getProcessno()))) &&
            ((this.recstat==null && other.getRecstat()==null) || 
             (this.recstat!=null &&
              this.recstat.equals(other.getRecstat()))) &&
            ((this.revisiondate==null && other.getRevisiondate()==null) || 
             (this.revisiondate!=null &&
              this.revisiondate.equals(other.getRevisiondate()))) &&
            ((this.seccleanrisklimit==null && other.getSeccleanrisklimit()==null) || 
             (this.seccleanrisklimit!=null &&
              this.seccleanrisklimit.equals(other.getSeccleanrisklimit()))) &&
            ((this.secpstlrisklimit==null && other.getSecpstlrisklimit()==null) || 
             (this.secpstlrisklimit!=null &&
              this.secpstlrisklimit.equals(other.getSecpstlrisklimit()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.udfdetails==null && other.getUdfdetails()==null) || 
             (this.udfdetails!=null &&
              java.util.Arrays.equals(this.udfdetails, other.getUdfdetails()))) &&
            ((this.unadvised==null && other.getUnadvised()==null) || 
             (this.unadvised!=null &&
              this.unadvised.equals(other.getUnadvised()))) &&
            ((this.userdefinestatus==null && other.getUserdefinestatus()==null) || 
             (this.userdefinestatus!=null &&
              this.userdefinestatus.equals(other.getUserdefinestatus()))) &&
            ((this.userreferenceno==null && other.getUserreferenceno()==null) || 
             (this.userreferenceno!=null &&
              this.userreferenceno.equals(other.getUserreferenceno()))) &&
            ((this.utilisationamt==null && other.getUtilisationamt()==null) || 
             (this.utilisationamt!=null &&
              this.utilisationamt.equals(other.getUtilisationamt())));
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
        if (getAuthstat() != null) {
            _hashCode += getAuthstat().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getCheckerdtstamp() != null) {
            _hashCode += getCheckerdtstamp().hashCode();
        }
        if (getCheckerid() != null) {
            _hashCode += getCheckerid().hashCode();
        }
        if (getConversiondate() != null) {
            _hashCode += getConversiondate().hashCode();
        }
        if (getCreditrating() != null) {
            _hashCode += getCreditrating().hashCode();
        }
        if (getFxcleanrisklimit() != null) {
            _hashCode += getFxcleanrisklimit().hashCode();
        }
        if (getLiabilityCreditRating() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLiabilityCreditRating());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLiabilityCreditRating(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLiabilityScore() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLiabilityScore());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLiabilityScore(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLiabilitybranch() != null) {
            _hashCode += getLiabilitybranch().hashCode();
        }
        if (getLiabilityccy() != null) {
            _hashCode += getLiabilityccy().hashCode();
        }
        if (getLiabilityname() != null) {
            _hashCode += getLiabilityname().hashCode();
        }
        if (getLiabilityno() != null) {
            _hashCode += getLiabilityno().hashCode();
        }
        if (getMainliabno() != null) {
            _hashCode += getMainliabno().hashCode();
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
        if (getNettingrequired() != null) {
            _hashCode += getNettingrequired().hashCode();
        }
        if (getOnceauth() != null) {
            _hashCode += getOnceauth().hashCode();
        }
        if (getOveralllimit() != null) {
            _hashCode += getOveralllimit().hashCode();
        }
        if (getOverallscore() != null) {
            _hashCode += getOverallscore().hashCode();
        }
        if (getProcessno() != null) {
            _hashCode += getProcessno().hashCode();
        }
        if (getRecstat() != null) {
            _hashCode += getRecstat().hashCode();
        }
        if (getRevisiondate() != null) {
            _hashCode += getRevisiondate().hashCode();
        }
        if (getSeccleanrisklimit() != null) {
            _hashCode += getSeccleanrisklimit().hashCode();
        }
        if (getSecpstlrisklimit() != null) {
            _hashCode += getSecpstlrisklimit().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
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
        if (getUserdefinestatus() != null) {
            _hashCode += getUserdefinestatus().hashCode();
        }
        if (getUserreferenceno() != null) {
            _hashCode += getUserreferenceno().hashCode();
        }
        if (getUtilisationamt() != null) {
            _hashCode += getUtilisationamt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiabilityDescriptionFullTypeDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityDescriptionFullTypeDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authstat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authstat"));
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
        elemField.setFieldName("conversiondate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conversiondate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditrating");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditrating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fxcleanrisklimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fxcleanrisklimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityCreditRating");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityCreditRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityCreditRating"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityScore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityScore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilitybranch");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilitybranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityccy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityccy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainliabno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mainliabno"));
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
        elemField.setFieldName("overalllimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "overalllimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overallscore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "overallscore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
        elemField.setFieldName("recstat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recstat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revisiondate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "revisiondate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seccleanrisklimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seccleanrisklimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secpstlrisklimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secpstlrisklimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("", "source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("userdefinestatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userdefinestatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userreferenceno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userreferenceno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilisationamt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilisationamt"));
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
