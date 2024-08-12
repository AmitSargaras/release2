/*
 * Created on Nov 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.common.bus;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginationUtilFactory {
	public static final int DBTYPE_ORACLE = 1;

	public static final int DBTYPE_DB2 = 2;

	public PaginationUtil getPaginationUtil(int dbType) {
		if (dbType == DBTYPE_ORACLE) {
			return new PaginationUtilOracle();
		}
		else if (dbType == DBTYPE_DB2) {
			return new PaginationUtilDB2();
		}
		return new PaginationUtilOracle();
	}

}
