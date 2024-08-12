package com.integrosys.cms.app.tatduration.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:46:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTatParamItem implements ITatParamItem
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long tatParamItemId;
    public long tatParamId;
    public String stageType = null;
    public String stageCode = null;
    public String preStage = null;
    public String postStage = null;
    public double duration = 0;
    public String durationType = null;
    public long sequenceOrder = 0;
    public long cmsRefID;
    public String status = ICMSConstant.STATE_ACTIVE;
    public long versionTime = 0;
    

    public long getTatParamItemId() {
		return tatParamItemId;
	}

	public void setTatParamItemId(long tatParamStageId) {
		this.tatParamItemId = tatParamStageId;
	}

	public long getTatParamId() {
		return tatParamId;
	}

	public void setTatParamId(long tatParamId) {
		this.tatParamId = tatParamId;
	}

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public String getPreStage() {
		return preStage;
	}

	public void setPreStage(String preStage) {
		this.preStage = preStage;
	}

	public String getPostStage() {
		return postStage;
	}

	public void setPostStage(String postStage) {
		this.postStage = postStage;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public long getCmsRefID() {
		return cmsRefID;
	}

	public void setCmsRefID(long cmsRefID) {
		this.cmsRefID = cmsRefID;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getSequenceOrder() {
		return sequenceOrder;
	}

	public void setSequenceOrder(long sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}

}
