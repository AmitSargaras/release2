package com.integrosys.cms.ui.genlad;
import java.util.ArrayList;
import java.util.List;


public class DocObj {
	
	public String header = null;
	public String content = null;
	public String midContent = null;
	public String footer = null;
	public String footerName = null;
	public List detilsList= null;
	public String projectManager = null;
	public String projectLead = null;
	public String teamLead = null;
	public String SSE = null;
	public String SE = null;
	

	
	public String getMidContent() {
		return midContent;
	}
	public void setMidContent(String midContent) {
		this.midContent = midContent;
	}
	public String getProjectLead() {
		return projectLead;
	}
	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}
	public String getTeamLead() {
		return teamLead;
	}
	public void setTeamLead(String teamLead) {
		this.teamLead = teamLead;
	}
	public String getSSE() {
		return SSE;
	}
	public void setSSE(String sSE) {
		SSE = sSE;
	}
	public String getSE() {
		return SE;
	}
	public void setSE(String sE) {
		SE = sE;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public List getDetilsList() {
		return detilsList;
	}
	public void setDetilsList(List detilsList) {
		this.detilsList = detilsList;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getFooterName() {
		return footerName;
	}
	public void setFooterName(String footerName) {
		this.footerName = footerName;
	}
	
	

}
