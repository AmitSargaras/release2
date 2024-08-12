package com.integrosys.cms.app.documentlocation.bus;

/**
 * This interface defines the UI operations that are provided by a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/07 11:54:09 $ Tag: $Name: $
 */
public interface IDocumentAppTypeItem extends java.io.Serializable {

	public Long getDocumentLoanId();

	public void setDocumentLoanId(Long documentLoanId);
	
	public Long getDocumentId();

	public void setDocumentId(Long documentId);

	public String getAppType();

	public void setAppType(String appType);
	
	public long getRefId();
	
	public void setRefId(long refId);

	public String getStatus();

	public void setStatus(String status);
	
	

}
