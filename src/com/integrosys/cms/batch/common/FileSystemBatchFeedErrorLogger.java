package com.integrosys.cms.batch.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * Implementation of {@link BatchFeedErrorLogger} using file system to persist
 * the batch feed error.
 * 
 * <p>
 * This will use output of {@link BatchFeedError#toString()} to persist the info
 * into a log file.
 * 
 * @author Chong Jun Yong
 * 
 */
public final class FileSystemBatchFeedErrorLogger implements BatchFeedErrorLogger {

	private String logFileLocation;

	/**
	 * Indicate whether to append the the output to the log file, need to be set
	 * <code>true</code> if the batch job itself loading multiple files.
	 */
	private boolean appendToLogFile = false;

	public String getLogFileLocation() {
		return logFileLocation;
	}

	public boolean isAppendToLogFile() {
		return appendToLogFile;
	}

	public void setLogFileLocation(String logFileLocation) {
		this.logFileLocation = logFileLocation;
	}

	public void setAppendToLogFile(boolean appendToLogFile) {
		this.appendToLogFile = appendToLogFile;
	}

	public void log(final BatchFeedError error) {
		executePrintStreamAction(new PrintStreamAction() {
			public void doInPrintStream(PrintStream ps) {
				ps.println(error.toString());
			}
		});
	}

	public void log(final Collection batchFeedErrors) {
		executePrintStreamAction(new PrintStreamAction() {
			public void doInPrintStream(PrintStream ps) {
				for (Iterator itr = batchFeedErrors.iterator(); itr.hasNext();) {
					BatchFeedError error = (BatchFeedError) itr.next();
					ps.println(error.toString());
				}
			}
		});
	}

	/**
	 * To execute the {@link PrintStreamAction} call back interface, prepare the
	 * file output stream with the logFile provided. Then finally create a new
	 * PrintStream object and passed to call back interface to work on it.
	 * 
	 * @param action a instance of print stream call back interface.
	 */
	private void executePrintStreamAction(PrintStreamAction action) {
		File logFile = new File(getLogFileLocation());
		try {
			FileOutputStream fos = new FileOutputStream(logFile, isAppendToLogFile());
			PrintStream ps = new PrintStream(fos);

			action.doInPrintStream(ps);

			fos.close();
		}
		catch (FileNotFoundException e) {
			throw new IllegalStateException("cannot find the file [" + logFileLocation + "] in the file system.");
		}
		catch (IOException e) {
			IllegalStateException ise = new IllegalStateException("failed to close the stream");
			ise.initCause(e);
			throw ise;
		}
	}

	/**
	 * Call back interface to work on prepared PrintStream object. Caller not
	 * need to care about the exception raised.
	 * 
	 */
	private interface PrintStreamAction {
		/**
		 * @param ps the print stream object
		 */
		public void doInPrintStream(PrintStream ps);
	}

}
