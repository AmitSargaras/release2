package com.integrosys.cms.app.maintaintat.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jun 11, 2010
 * Time: 6:35:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTatLimitTrackStage {
    private long tatTrackingStageId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    private long tatParamItemId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    private Date startDate;
    private Date actualDate;
	private Date endDate;
	private String reasonExceeding;
    private String tatApplicable;
    private String stageActive;
    private String status = ICMSConstant.STATE_ACTIVE;
	private boolean isTatApplicable;
    private OBTatParamItem tatParamItem;

    public long getTatTrackingStageId() {
        return tatTrackingStageId;
    }

    public void setTatTrackingStageId(long tatTrackingStageId) {
        this.tatTrackingStageId = tatTrackingStageId;
    }

    public long getTatParamItemId() {
        return tatParamItemId;
    }

    public void setTatParamItemId(long tatParamStageId) {
        this.tatParamItemId = tatParamStageId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReasonExceeding() {
        return reasonExceeding;
    }

    public void setReasonExceeding(String reasonExceeding) {
        this.reasonExceeding = reasonExceeding;
    }

    public void setTatApplicable(String tatApplicable) {
        this.tatApplicable = tatApplicable;
    }

    public String getTatApplicable() {
        return tatApplicable;
    }

    public boolean isStageActive() {
        if ("Y".equals(getStageActive()) || "1".equals(getStageActive()))
            return true;
        else
            return false;
    }

    public void setStageActive(String stageActive) {
        this.stageActive = stageActive;
    }

    public String getStageActive() {
        return stageActive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTatApplicable() {
        if ("Y".equals(getTatApplicable()) || "1".equals(getTatApplicable()))
            return true;
        else
            return false;
    }

    public void setIsTatApplicable(boolean isTatApplicable) {
        this.isTatApplicable = isTatApplicable;
    }

    public boolean getIsTatApplicable() {
        return isTatApplicable();
    }

    public OBTatParamItem getTatParamItem() {
		return tatParamItem;
	}

	public void setTatParamItem(OBTatParamItem tatParamItem) {
		this.tatParamItem = tatParamItem;
	}
}
