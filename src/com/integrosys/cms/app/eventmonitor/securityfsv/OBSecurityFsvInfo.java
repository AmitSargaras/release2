/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/securityfsv/OBSecurityFsvInfo.java,v 1.3 2006/03/06 12:37:56 hshii Exp $
 */
package com.integrosys.cms.app.eventmonitor.securityfsv;

import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/06 12:37:56 $ Tag: $Name: $
 */
public class OBSecurityFsvInfo extends OBEventInfo {

	private String securityType;

	private String securitySubtype;

	private Amount securityFsv;

	private Amount securityMinFsv;

	private String armName;

	private HashMap facilityMap = new HashMap();

	private HashMap sourceIDMap = new HashMap();

	public HashMap getSourceIDMap() {
		return sourceIDMap;
	}

	public void setSourceIDMap(HashMap sourceIDMap) {
		this.sourceIDMap = sourceIDMap;
	}

	public HashMap getFacilityMap() {
		return facilityMap;
	}

	public void setFacilityMap(HashMap facilityMap) {
		this.facilityMap = facilityMap;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubtype() {
		return securitySubtype;
	}

	public void setSecuritySubtype(String securitySubtype) {
		this.securitySubtype = securitySubtype;
	}

	public Amount getSecurityFsv() {
		return securityFsv;
	}

	public void setSecurityFsv(Amount securityFsv) {
		this.securityFsv = securityFsv;
	}

	public Amount getSecurityMinFsv() {
		return securityMinFsv;
	}

	public void setSecurityMinFsv(Amount securityMinFsv) {
		this.securityMinFsv = securityMinFsv;
	}

	public String getArmName() {
		return armName;
	}

	public void setArmName(String armName) {
		this.armName = armName;
	}
}
