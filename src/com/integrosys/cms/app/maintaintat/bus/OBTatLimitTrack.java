package com.integrosys.cms.app.maintaintat.bus;

import java.util.Date;
import java.util.Set;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:46:28 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * @author Kwok
 *
 */
public class OBTatLimitTrack
{
	private long tatTrackingId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	//public long tatParamItemId;
	private long limitProfileId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    //public String stageType;
    //public String stageCode;
    //public String preStage;
    //public String postStage;
    //public long duration = 0;
    //public String durationType;

    //public String status = ICMSConstant.STATE_ACTIVE;
    private long versionTime = 0;
    
	//private Date actualDate;
	//private Date startDate;
	//private Date endDate;
	//private String reasonExceeding;
	//private boolean isTatApplicable;
	//private String tatApplicable;
	//private String stageActive;
	
//	private OBTatParamItem tatParamItem;

    private String preDisbursementRemarks;
    private String disbursementRemarks;
    private String postDisbursementRemarks;
    private Set stageListSet;

//    public long getTatParamItemId() {
//		return tatParamItemId;
//	}
//
//	public void setTatParamItemId(long tatParamStageId) {
//		this.tatParamItemId = tatParamStageId;
//	}
//
//	public String getStageType() {
//		return stageType;
//	}
//
//	public void setStageType(String stageType) {
//		this.stageType = stageType;
//	}
//
//	public String getStageCode() {
//		return stageCode;
//	}
//
//	public void setStageCode(String stageCode) {
//		this.stageCode = stageCode;
//	}
//
//	public String getPreStage() {
//		return preStage;
//	}
//
//	public void setPreStage(String preStage) {
//		this.preStage = preStage;
//	}
//
//	public String getPostStage() {
//		return postStage;
//	}
//
//	public void setPostStage(String postStage) {
//		this.postStage = postStage;
//	}
//
//	public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public long getDuration() {
//		return duration;
//	}
//
//	public void setDuration(long duration) {
//		this.duration = duration;
//	}
//
//	public String getDurationType() {
//		return durationType;
//	}
//
//	public void setDurationType(String durationType) {
//		this.durationType = durationType;
//	}

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }


//	public String getReasonExceeding() {
//		return reasonExceeding;
//	}
//
//	public void setReasonExceeding(String reasonExceeding) {
//		this.reasonExceeding = reasonExceeding;
//	}

	public long getTatTrackingId() {
		return tatTrackingId;
	}

	public void setTatTrackingId(long tatTrackingId) {
		this.tatTrackingId = tatTrackingId;
	}

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

//	public OBTatParamItem getTatParamItem() {
//		return tatParamItem;
//	}
//
//	public void setTatParamItem(OBTatParamItem tatParamItem) {
//		this.tatParamItem = tatParamItem;
//	}

//	public Date getActualDate() {
//		return actualDate;
//	}
//
//	public void setActualDate(Date actualDate) {
//		this.actualDate = actualDate;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//
//	public void setTatApplicable(String tatApplicable) {
//		this.tatApplicable = tatApplicable;
//	}
//
//	public String getTatApplicable()
//	{
//		return tatApplicable;
//	}

//	public boolean isStageActive()
//	{
//		if("Y".equals(getStageActive()) || "1".equals(getStageActive()))
//			return true;
//		else
//			return false;
//	}
//
//	public void setStageActive(String stageActive)
//	{
//		this.stageActive = stageActive;
//	}
//
//	public String getStageActive()
//	{
//		return stageActive;
//	}
//
//	public boolean isTatApplicable()
//	{
//		if("Y".equals(getTatApplicable()) || "1".equals(getTatApplicable()))
//			return true;
//		else
//			return false;
//	}
//
//	public void setIsTatApplicable(boolean isTatApplicable)
//	{
//		this.isTatApplicable = isTatApplicable;
//	}
//
//	public boolean getIsTatApplicable()
//	{
//		return isTatApplicable();
//	}


    public String getPreDisbursementRemarks() {
        return preDisbursementRemarks;
    }

    public void setPreDisbursementRemarks(String preDisbursementRemarks) {
        this.preDisbursementRemarks = preDisbursementRemarks;
    }

    public String getDisbursementRemarks() {
        return disbursementRemarks;
    }

    public void setDisbursementRemarks(String disbursementRemarks) {
        this.disbursementRemarks = disbursementRemarks;
    }

    public String getPostDisbursementRemarks() {
        return postDisbursementRemarks;
    }

    public void setPostDisbursementRemarks(String postDisbursementRemarks) {
        this.postDisbursementRemarks = postDisbursementRemarks;
    }

    public Set getStageListSet() {
        return stageListSet;
    }

    public void setStageListSet(Set stageListSet) {
        this.stageListSet = stageListSet;
    }
}
