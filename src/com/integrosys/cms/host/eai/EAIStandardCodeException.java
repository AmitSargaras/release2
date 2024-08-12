package com.integrosys.cms.host.eai;

/**
 * Base class for exception throw by the EAI validation on Standard Code
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class EAIStandardCodeException extends EAIMessageValidationException {

	private static final long serialVersionUID = 6172165302381489685L;

	private StandardCode standardCode;

	private String source;

	private String categoryType;

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EAIStandardCodeException(String msg) {
		super(msg);
	}

	public final void setStandardCode(StandardCode standardCode) {
		this.standardCode = standardCode;
	}

	public StandardCode getStandardCode() {
		return standardCode;
	}

	public String getMessage() {
		StringBuffer buf = new StringBuffer(super.getMessage());
		if (getCategoryType() != null) {
			buf.append("; Category type required [").append(categoryType).append("]");
		}

		return buf.toString();
	}

}