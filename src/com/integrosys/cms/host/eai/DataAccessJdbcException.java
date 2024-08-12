package com.integrosys.cms.host.eai;

import java.sql.SQLException;

/**
 * Exception to be raised when there is JDBC error, useful when the SQLException
 * is wrapped inside another exception, and nested message is not shown, instead
 * need to drill down to the cause and it's message.
 * 
 * @author Chong Jun Yong
 * 
 */
public class DataAccessJdbcException extends EAIMessageException {

	private static final long serialVersionUID = -476065184989863208L;

	private static final String DATA_ACCESS_JDBC_ERROR_CODE = "JDBC_ERROR";

	private SQLException cause;

	/**
	 * Constructor to only accept SQLException as cause
	 * 
	 * @param message detailed message
	 * @param cause the cause which is the sql exception
	 */
	public DataAccessJdbcException(String message, SQLException cause) {
		super(message + "; caused by " + cause.getClass().getName() + ", error code [" + cause.getErrorCode()
				+ "], SQL state [" + cause.getSQLState() + "], reason [" + cause.getMessage() + "]");
		this.cause = cause;
	}

	public SQLException getSQLException() {
		return this.cause;
	}

	public String getErrorCode() {
		return DATA_ACCESS_JDBC_ERROR_CODE;
	}

}
