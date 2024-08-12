package com.integrosys.cms.host.stp;

import java.io.IOException;

/**
 * Exception to be raised when there is fatal file system access, such as file
 * not found, file not able to read, etc.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public class FileSystemAccessException extends STPMessageException {

	private static final long serialVersionUID = 210883552583839197L;

	private static final String FILE_SYSTEM_ACCESS_ERROR_CODE = "IO_ERROR";

	/**
	 * Default Constructor to provide error message
	 *
	 * @param msg error message for this exception
	 */
	public FileSystemAccessException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 *
	 * @param msg error message for this exception
	 * @param cause the IO exception that cause this exception to be thrown
	 */
	public FileSystemAccessException(String msg, IOException cause) {
		super(msg, cause);
	}

	public String getErrorCode() {
		return FILE_SYSTEM_ACCESS_ERROR_CODE;
	}

}