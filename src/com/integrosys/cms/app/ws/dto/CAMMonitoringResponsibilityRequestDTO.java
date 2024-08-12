package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class CAMMonitoringResponsibilityRequestDTO {

	@XmlElement(name = "MonitoringResponsibiltyValue",required=true)
	private String MonitoringResponsibiltyValue;

	public String getMonitoringResponsibiltyValue() {
		return MonitoringResponsibiltyValue;
	}

	public void setMonitoringResponsibiltyValue(String monitoringResponsibiltyValue) {
		MonitoringResponsibiltyValue = monitoringResponsibiltyValue;
	}
	
}