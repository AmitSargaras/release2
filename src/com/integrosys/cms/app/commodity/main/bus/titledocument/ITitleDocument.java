/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/ITitleDocument.java,v 1.3 2004/07/22 07:33:15 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import java.io.Serializable;

import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 10:00:52 AM To change this template use File | Settings | File Templates.
 */
public interface ITitleDocument extends ICommodityMainInfo, Serializable {

	public static final String NEGOTIABLE = "neg";

	public static final String NON_NEGOTIABLE = "non_neg";

	/**
	 * Get title document type id.
	 * 
	 * @return long
	 */
	public long getTitleDocumentID();

	/**
	 * Set title document type id.
	 * 
	 * @param titleDocumentID of type long
	 */
	public void setTitleDocumentID(long titleDocumentID);

	/**
	 * Get documentation description.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Set documentation description
	 * 
	 * @param name of type String
	 */
	public void setName(String name);

	/**
	 * Get title document type, ITitleDocument.NEGOTIABLE or
	 * ITitleDocument.NON_NEGOTIABLE.
	 * 
	 * @return String
	 */
	public String getType();

	/**
	 * Set title document type, ITitleDocument.NEGOTIABLE or
	 * ITitleDocument.NON_NEGOTIABLE.
	 * 
	 * @param type of type String
	 */
	public void setType(String type);

	/**
	 * Get cms group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set cms group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get cms common reference id across actual and staging tables.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set cms common reference id across actual and staging tables.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
