/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/util/SQLParameter.java,v 1.2 2005/04/22 09:16:28 jtan Exp $
 */

package com.integrosys.cms.app.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is the container for the SQL parameters used for JDBC calls Acts as a
 * type checker before setting in the parameters Assumes that user will input
 * the binded objects in sequence by constructing the SQL in top-down fashion
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/22 09:16:28 $ Tag: $Name: $
 */
public class SQLParameter {

	private List params; // stores all the SQL objects for binding in sequential
							// order

	private SQLParameter() {
		this.params = new ArrayList();
	}

	public static SQLParameter getInstance() {
		return new SQLParameter();
	}

	public void addString(String s) {
		this.params.add(s);
	}

	public void addLong(Long l) {
		this.params.add(l);
	}

	public void addDate(java.util.Date d) {
		this.params.add(d);
	}

	public void addDate(java.sql.Date d) {
		this.params.add(d);
	}

	public void addInteger(Integer i) {
		this.params.add(i);
	}

	/**
	 * Ordered iterator using ArrayList
	 * @return iterator
	 */
	public Iterator iterator() {
		return this.params.iterator();
	}

	/**
	 * number of collected parameters
	 * @return int
	 */
	public int size() {
		return this.params.size();
	}

	/**
	 * returns a List interface for this object
	 * @return List
	 */
	public List asList() {
		return this.params;

	}

	/**
	 * adds a list of params to the existing parameters
	 * @param params
	 */
	public void addList(List params) {
		this.params.addAll(params);
	}

}
