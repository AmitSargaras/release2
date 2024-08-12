package com.integrosys.cms.ui.approvalmatrix;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * This class implements FormBean
 */
public class ApprovalMatrixForm extends TrxContextForm implements java.io.Serializable {

	public static final String MAPPER = "com.integrosys.cms.ui.approvalmatrix.ApprovalMatrixMapper";

	private String[] riskGrade;

	private String[] level1;

	private String[] level2;

	private String[] level3;
	
	private String[] level4;

	private String targetOffset = "-1";
	
	/**
	 * @return the riskGrade
	 */
	public String[] getRiskGrade() {
		return riskGrade;
	}

	/**
	 * @param riskGrade the riskGrade to set
	 */
	public void setRiskGrade(String[] riskGrade) {
		this.riskGrade = riskGrade;
	}

	/**
	 * @return the level1
	 */
	public String[] getLevel1() {
		return level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String[] level1) {
		this.level1 = level1;
	}

	/**
	 * @return the level2
	 */
	public String[] getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String[] level2) {
		this.level2 = level2;
	}

	/**
	 * @return the level3
	 */
	public String[] getLevel3() {
		return level3;
	}

	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3(String[] level3) {
		this.level3 = level3;
	}
	
	/**
	 * @return the level4
	 */
	public String[] getLevel4() {
		return level4;
	}

	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4(String[] level4) {
		this.level4 = level4;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String getTargetOffset() {
		return targetOffset;
	}

	public void setTargetOffset(String targetOffset) {
		this.targetOffset = targetOffset;
	}
}
