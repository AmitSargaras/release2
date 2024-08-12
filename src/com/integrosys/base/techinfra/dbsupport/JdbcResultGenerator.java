package com.integrosys.base.techinfra.dbsupport;

/**
 * <p>
 * Jdbc Result generator to be injected to any bean that doesn't want to care
 * about Jdbc query, and make sure the results returned are in the correct
 * format that the bean wanted.
 * <p>
 * For implementation, may consider to return rows records always, so it doesn't
 * need to care about in what domain type the caller needs.
 * @author Chong Jun Yong
 * 
 */
public interface JdbcResultGenerator {

	/**
	 * <p>
	 * Return the result for this generator by passing any arguments for the
	 * bind variable. the arguments can be null, provided there is no required
	 * for the bind variables in the SQL query.
	 * <p>
	 * <b>Note: </b> This method not suppose to get carried to multiple line in
	 * a same place. Caller should invoke once and carry it result instead.s
	 * @param args arguments for the bind variables, can be null if not requred
	 * @return the result from running the SQL query.
	 */
	public Object getResult(Object[] args);
}
