package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.io.Serializable;
import java.util.List;


public interface ISegmentWiseEmailDao {
	
	static final String ACTUAL_SEGMENT_WISE_EMAIL_NAME = "actualSegmentWiseEmail";
	static final String STAGE_SEGMENT_WISE_EMAIL_NAME = "stageCreateSegmentWiseEmail";
	
	
	ISegmentWiseEmail createSegmentWiseEmail(String entityName, ISegmentWiseEmail excludedFacility)throws SegmentWiseEmailException;
	ISegmentWiseEmail getSegmentWiseEmail(String entityName, Serializable key)throws SegmentWiseEmailException;
	
	ISegmentWiseEmail updateSegmentWiseEmail(String entityName, ISegmentWiseEmail item)throws SegmentWiseEmailException;
	
	ISegmentWiseEmail deleteSegmentWiseEmail(String entityName, ISegmentWiseEmail excludedFacility)throws SegmentWiseEmailException;

}
