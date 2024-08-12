package com.integrosys.cms.app.tatduration.bus;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:57:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatParamItem extends Serializable {
	
	public long getTatParamItemId();

	public void setTatParamItemId(long tatParamStageId); 

	public long getTatParamId();

	public void setTatParamId(long tatParamId);
	
	public String getStageType();

	public void setStageType(String stageType) ;

	public String getStageCode();

	public void setStageCode(String stageCode);

	public String getPreStage();

	public void setPreStage(String preStage);

	public String getPostStage();

	public void setPostStage(String postStage) ;

	public String getStatus();

	public void setStatus(String status) ;
	
	public double getDuration();
	public void setDuration(double duration);
	public String getDurationType();
	public void setDurationType(String durationType);
	public long getCmsRefID();
	public void setCmsRefID(long cmsRefID);
	public long getVersionTime() ;
	public void setVersionTime(long versionTime) ;
	
	public long getSequenceOrder() ;
	public void setSequenceOrder(long sequenceOrder);
	
}
