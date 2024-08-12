package com.integrosys.cms.batch.common.posidex.templateparser;

public class WhereClause {

	private String mandatoryClause;
	private Param[] param;
	private String orderAndGroupByClause;
	public String getMandatoryClause() {
		return mandatoryClause;
	}
	public void setMandatoryClause(String mandatoryClause) {
		this.mandatoryClause = mandatoryClause;
	}
	public Param[] getParam() {
		return param;
	}
	public void setParam(Param[] param) {
		this.param = param;
	}
	public String getOrderAndGroupByClause() {
		return orderAndGroupByClause;
	}
	public void setOrderAndGroupByClause(String orderAndGroupByClause) {
		this.orderAndGroupByClause = orderAndGroupByClause;
	}
	
}
