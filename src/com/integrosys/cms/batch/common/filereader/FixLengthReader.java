/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.batch.common.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Reader for fixed length file.
 * 
 * @author $Author: kltan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class FixLengthReader {

	public void readFixLength(ProcessDataFile dd) {
		try {

			BufferedReader in = new BufferedReader(new FileReader(dd.dataFilePath));
			String str;
			String strHeader = null;

			int count = 0;
			while ((str = in.readLine()) != null) {
				if (count == dd.rowHeaderIndex) {
					strHeader = str;
					count++;
					continue;
				}
				else if (count < dd.rowStartIndex) {
					count++;
					continue;
				}
				else if ((count > dd.rowEndIndex) && (dd.rowEndIndex != 0)) {
					break;
				}

				if (str.equals("")) {
					count++;
					continue;
				}

				HashMap hm = new HashMap();

				if (dd.columnsIndex == null) {
					for (int i = 0; i < dd.totalColumn; i++) {
						String header = strHeader.substring(dd.columnLength * i, dd.columnLength * (i + 1)).trim();
						String value = str.substring(dd.columnLength * i, dd.columnLength * (i + 1)).trim();
						hm.put(header, value);
					}
				}
				else {
					StringTokenizer st = new StringTokenizer(dd.columnsIndex, ",");
					while (st.hasMoreTokens()) {
						int i = Integer.parseInt(st.nextToken());
						String header = strHeader.substring(dd.columnLength * i, dd.columnLength * (i + 1)).trim();
						String value = str.substring(dd.columnLength * i, dd.columnLength * (i + 1)).trim();
						hm.put(header, value);
					}
				}
				dd.rowArray.add(hm);
				count++;
			}
			in.close();

			// System.out.println(
			// "successful insert fix length data into array...");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
	}

}