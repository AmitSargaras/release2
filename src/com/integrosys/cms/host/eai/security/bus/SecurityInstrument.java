package com.integrosys.cms.host.eai.security.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents security instrument.
 * 
 * @author $Author: shpoon $<br>
 * @version $Revision:$
 * @since $Date $ Tag: $Name $
 */
public class SecurityInstrument implements java.io.Serializable {
	private long cMSSecurityInstrumentId = ICMSConstant.LONG_INVALID_VALUE;

	private long cMSSecurityId = ICMSConstant.LONG_INVALID_VALUE;

	private String instrumentCode;

	public long getCMSSecurityInstrumentId() {
		return cMSSecurityInstrumentId;
	}

	public void setCMSSecurityInstrumentId(long cMSSecurityInstrumentId) {
		this.cMSSecurityInstrumentId = cMSSecurityInstrumentId;
	}

	public long getCMSSecurityId() {
		return cMSSecurityId;
	}

	public void setCMSSecurityId(long cMSSecurityId) {
		this.cMSSecurityId = cMSSecurityId;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}

}
