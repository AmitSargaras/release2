package com.integrosys.cms.app.generateli.bus;

import java.util.Date;

/**
 * This interface defines the UI operations that are provided by a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/07 11:54:09 $ Tag: $Name: $
 */
public interface ILiDoc extends java.io.Serializable {

	public Date getFirstGenDate();



	public void setFirstGenDate(Date firstGenDate);



	public Date getLastGenDate();



	public void setLastGenDate(Date lastGenDate);


	public String getGeneratedBy();



	public void setGeneratedBy(String generatedBy);


	public String getLiTemplateName();



	public void setLiTemplateName(String liTemplateName);


	public Long getId();


	public void setId(Long id);
	

}
