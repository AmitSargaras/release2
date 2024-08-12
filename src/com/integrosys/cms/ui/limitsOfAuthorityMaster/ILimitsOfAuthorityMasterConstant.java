package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import com.integrosys.cms.ui.common.TrxContextMapper;

public interface ILimitsOfAuthorityMasterConstant {

	String MAIN_MAPPER = LimitsOfAuthorityMasterMapper.class.getName();
	String TRX_MAPPER = TrxContextMapper.class.getName();
	String LIMITS_OF_AUTHORITY_OBJ = "limitsOfAuthorityMasterObj";
	String TRX_CONTEXT = "theOBTrxContext";
	String LIMITS_OF_AUTHORITY_TRX_VAL = "limitsOfAuthorityMasterTrxValue";
	String NA = "NA";
	
	String SESSION_RANKING_SEQUENCE_LIST= "loaRankingSequenceList";
	String SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE= "sortedloaRankingSequenceList";
	
    String LOA_MASTER_FIELD_LIMIT_RELEASE_AMT = "loaLimitReleaseAmt";
    String LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT = "loaTotalSanctionedLimit";
    String LOA_MASTER_FIELD_PROPERTY_AMT = "loaPropertyAmt";
    String LOA_MASTER_FIELD_DRAWING_POWER = "loaDrawingPower";
    String LOA_MASTER_FIELD_FD_AMT = "loaFdAmt";
    String LOA_MASTER_FIELD_SBLC_SECURITY_OMV = "loaSblcOmv";
    String LOA_MASTER_FIELD_FACILITY_CAM_COVENANT = "loaFacCamCovenant";
    String LOA_MASTER_CUSTOMER_SEGMENT = "customerSegment";
}
