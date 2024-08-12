package com.integrosys.cms.batch.common;

import java.util.List;

/**
 * DAO which responsible for doing persist list of records (each record in a
 * list format, in order).
 * @author Chong Jun Yong
 * 
 */
public interface ListRecordsDao {
	/**
	 * <p>
	 * Persist list of records which in turn each of object is a List as well.
	 * <p>
	 * The record list for each of records supplied, <i>must</i> be in order, so
	 * that persistent framework not need to care about the ordering.
	 * @param records list of records (each record in a list format)
	 */
	public void persist(List records);
}
