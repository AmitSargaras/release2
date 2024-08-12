package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 2, 2007 Time: 6:39:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBValuationFromLOS extends OBValuation implements IValuationFromLOS {

	private String refId;

	// private String valuerCode ;
	// private String valuerName ;
	// private Amount valuationCMV;
	// private Date valuationDate;
	// private Date dateRecievedFromLOS;
	// private Date evaluationDateFSV;
	// private String refId ;

	public OBValuationFromLOS() {

	}

	public OBValuationFromLOS(IValuation iValuation) {
		this.setSourceId(iValuation.getSourceId());
		this.setValuationID(iValuation.getValuationID());
		this.setCurrencyCode(iValuation.getCurrencyCode());
		this.setRefId(iValuation.getValuationID() + "");
		this.setValuerName(iValuation.getValuerName());
		this.setValuationDate(iValuation.getValuationDate());
		this.setCMV(iValuation.getCMV());
		this.setFSV(iValuation.getFSV());
		this.setEvaluationDateFSV(iValuation.getEvaluationDateFSV());
		this.setValuationType(iValuation.getValuationType());
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String toString() {
		return "OBValuationFromLOS{" + "refId='" + refId + '\'' + '}';
	}
}
