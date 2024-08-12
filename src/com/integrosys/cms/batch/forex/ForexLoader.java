package com.integrosys.cms.batch.forex;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/forex/ForexLoader.java,v 1.10 2006/07/07 04:52:03 hmbao Exp $
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;

/**
 * Purpose: A batch program that takes in forex rates data in ASCII format. It
 * parses the text file and prepares the data for uploading into the Forex
 * tables. <p/> Description: Please ensure that there the rates data file is in
 * the current directory java -cp %CLASSPATH%
 * -DPropertyFile=%INTEGRO_HOME%\config\currencydenom.properties
 * ;%INTEGRO_HOME%\config\ofa.properties
 * com.integrosys.cms.batch.forex.ForexLoader filename <p/> Please ensure that
 * the ofa.properties file is updated with this entry
 * dbconfig.manager.batch.class
 * =com.integrosys.cms.batch.forex.SCBJDBCConnectionManager
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 */

// Check with Sathish on exception handling
public class ForexLoader extends AbstractMonitorAdapter {

	public static final int STANDARD_DENOMINATION = 1;

	public static final int CURRENCY_DENOMINATION = 1000000;

	public static final String NUM_OF_CURRENCY = "numberOfCurrency";

	public static final String CURRENCY_CODE = "currencycode";

	public static final String CURRENCY_UNIT = "currencyunit";

	public static final String DELIMITER_SYM = " ";

	public static final String CURRENCY_KEY_PREFIX = "currencycode.denomination.";

	public static final String FOREX_FILE = "batch.forex.dir.file";

	/**
	 * this indicates how the currency is computed. A value of 'T' means the
	 * conversion is done from USD -> X Currency A value of 'O' means the
	 * conversion is done from X Currency -> USD
	 */
	public static final String CONVERSION_DIRECTION_TO_OURS = "O";

	/**
	 * Default Constructor
	 */
	// public ForexLoader() {
	// StartupInit.init();
	// }
	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doWork(context);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new EventMonitorException(e);
		}
	}

	private void doWork(SessionContext context) throws Exception {
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		// check that the parameters entered are valid
		DefaultLogger.debug(this, "reading from property mangaer" + PropertyManager.getValue("batch.forex.dir.file"));
		try {
			String forexFile = PropertyManager.getValue(FOREX_FILE);
			String filename = new String(forexFile);
			File file = new File(filename);
			if (!file.exists()) {
				DefaultLogger.error(this, "File: " + filename + " does not exist");
			}
			if (!file.canRead()) {
				DefaultLogger.error(this, "File: " + filename + " is not readable");
			}

			trxUtil.beginUserTrx();
			int noOfRecUpdated = loadForexData(filename);
			DefaultLogger.debug(this, "Num of update: " + noOfRecUpdated);
			trxUtil.commitUserTrx();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception!", e);
			trxUtil.rollbackUserTrx();
		}
	}

	// public static void main(String[] args) {
	//
	// // check that the parameters entered are valid
	// DefaultLogger.debug(this,"error reading from property mangaer"+
	// PropertyManager.getValue("batch.forex.dir.file"));
	//
	// String forexFile = PropertyManager.getValue(FOREX_FILE);
	// String filename = new String(forexFile);
	// File file = new File(filename);
	// if (!file.exists()) {
	// DefaultLogger.error(ForexLoader.class.getName(), "File: " + filename + "
	// does not exist");
	// }
	//
	// if (!file.canRead()) {
	// DefaultLogger.error(ForexLoader.class.getName(), "File: " + filename + "
	// is not readable");
	// }
	// // loadForexData(filename);
	// }

	public int loadForexData(String filename) throws SearchDAOException {

		ArrayList rateData = readFile(filename);
		ArrayList results;
		int noOfRecUpdated = 0;
		if ((rateData != null) && (rateData.size() != 0)) {
			results = processRateData(rateData);
			if ((results != null) && (results.size() != 0)) {
				ForexDAO dao = new ForexDAO();
				noOfRecUpdated = dao.updateRates(results);
			}
			else {
				DefaultLogger.debug(this, "There are no records to be uploaded");
			}
		}
		else {
			DefaultLogger.debug(this, "File " + filename + " is empty, aborting");
		}
		return noOfRecUpdated;
	}

	// public static void testLoad(ArrayList list) {
	// for (int i = 0; i < list.size(); i++) {
	// DefaultLogger.debug(ForexLoader.class.getName(), list.get(i));
	// }
	// }

	/**
	 * * Rates Data is given in the following format: 1. Header Data Field Name
	 * Length Description Date 8 Today's date in ddmmccyy format Space 1 Space
	 * Base Currency 3 Base currency - hard coded to USD Space 1 Space Number of
	 * Records 4 Number of records including header <p/> Individual CashRate
	 * Data 2. CashRate Field Name Length Description Currency 3 Currency Code
	 * Cash Rate 12 The rate against the USD. The format is 99999999999 Rate is
	 * multiplied by 1 million and padded to the left with zeros. For example,
	 * 1.234 is displayed as 000001234000 <p/> This method removes the preceding
	 * zeros from each rate record and converts it to the base rate. For
	 * Japanese YEN and RUPIAH, the data are given in the following denomination
	 * 1 USD in multiples 100 YEN 1 USD in multiples of 1000 INDONESIAN RUPIAH
	 * 
	 * @param rates
	 * @return an ArrayList of OBForex objects
	 */
	public ArrayList processRateData(ArrayList rates) {

		DefaultLogger.debug(this, "Entering processRateData");
		// break up record into currency, cash rate, and conversion terms
		// parse input header
		if (rates == null) {
			return null;
		}

		String header = (String) rates.get(0);
		StringTokenizer tokens = new StringTokenizer(header, DELIMITER_SYM);
		String effectiveDate = tokens.nextToken(); // data export date
		String fromCurrency = tokens.nextToken(); // base currency
		String strNumRecords = tokens.nextToken(); // number of records

		int numRecords = Integer.parseInt(strNumRecords);
		if (numRecords == 0) {
			return null;
		}
		// create an array to hold all records
		ArrayList forex = new ArrayList(numRecords);

		String record;
		String strVal;
		double rate;
		int unit;
		for (int i = 1; i < rates.size(); i++) {
			record = (String) rates.get(i);
			OBForex data = parseRecord(fromCurrency, effectiveDate, record);
			strVal = PropertyManager.getValue(CURRENCY_KEY_PREFIX + data.getToCurrency());

			if (strVal == null) {
				unit = STANDARD_DENOMINATION;
			}
			else {
				unit = Integer.parseInt(strVal);
			}
			data.setUnit(unit);
			rate = data.getRate() / CURRENCY_DENOMINATION;

			/**
			 * this indicates how the currency is computed. A value of 'T' means
			 * the conversion is done from USD -> X Currency A value of 'O'
			 * means the conversion is done from X Currency -> USD
			 */
			String indicator = data.getIndicator();
			if (indicator.equals(CONVERSION_DIRECTION_TO_OURS)) {
				if (rate != 0) {
					data.setRate(1 / rate);
				}
			}
			else {
				data.setRate(rate);
			}
			forex.add(data);
		}

		return forex;

	}

	/**
	 * Parses each text line and construct it as a Forex value object
	 * 
	 * @param fromCurrency
	 * @param effectiveDate - date where rates were collected
	 * @param record - contains the toCurrency and actual rate
	 * @return a value object representing forex data
	 */
	public OBForex parseRecord(String fromCurrency, String effectiveDate, String record) {

		if ((record == null) || (record.length() == 0)) {
			return null;
		}
		StringTokenizer tokens = new StringTokenizer(record, DELIMITER_SYM);
		String toCurrency = tokens.nextToken();
		String strRate = tokens.nextToken();
		String indicator = tokens.nextToken();
		double rate = Double.parseDouble(removeLeftPaddedZeroes(strRate));
		return new OBForex(fromCurrency, toCurrency, rate, effectiveDate, STANDARD_DENOMINATION, indicator);
	}

	/**
	 * reads a text file into a ArrayList
	 * 
	 * @param filename
	 * @return an ArrayList of text lines
	 */
	public ArrayList readFile(String filename) {

		ArrayList list = new ArrayList();
		BufferedReader br = null;
		String line;

		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		}
		catch (FileNotFoundException FNFE) {
			DefaultLogger.error(this, "Error loading file " + filename + FNFE.getMessage());
		}
		catch (IOException IE) {
			DefaultLogger.error(this, "Error occurred during processing of file " + filename + IE.getMessage());
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (IOException IE) {
				DefaultLogger.debug(this, "Error closing file " + filename);
				System.exit(1);
			}
		}

		return list;

	}

	/**
	 * Helper method to remove the left padded zeroes in rates data
	 * 
	 * @param str - rates left-padded with zeroes
	 * @return scrubbed string
	 */
	public String removeLeftPaddedZeroes(String str) {
		final char PAD_SYMBOL = '0';
		char ch;
		String result = null;

		if ((str == null) || (str.length() == 0)) {
			return result;
		}

		int pos = 0;
		int maxPosition = str.length();
		while (pos < maxPosition) {
			ch = str.charAt(pos);
			if (ch == PAD_SYMBOL) {
				pos++;
			}
			else {
				break;
			}
		}

		if ((pos != 0) && (pos < maxPosition)) {
			result = str.substring(pos, str.length());
		}
		else if (pos == maxPosition) {
			result = PAD_SYMBOL + "";
		}
		else {
			result = str;
		}

		return result;
	}

}
