package com.integrosys.cms.batch.common.item;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 16:31:21 To
 * change this template use File | Settings | File Templates.
 */
public class OBBatchFile {
	private OBFileHeader obFileHeader;

	private OBFileBody obFileBody;

	private OBFileFooter obFileFooter;

	public OBBatchFile(String fileName, OBRecordKey key) {
		try {

			BufferedReader br = new BufferedReader(new FileReader(fileName));

			String currentLine = br.readLine();
			int lineNo = 1;

			obFileHeader = new OBFileHeader(currentLine, key.getFileType());

			obFileBody = new OBFileBody(key);

			while (currentLine != null) {
				if (lineNo == 1) {
					currentLine = br.readLine();
					lineNo++;
					continue;

				}
				if ("TTTTT".equals(currentLine.substring(0, 5))) {
					obFileFooter = new OBFileFooter(currentLine, key.getFileType());
				}
				else {
					// tracs400 start special handling for CI002F
					if ("AA_START_TOKEN".equals(currentLine.trim())) {
						OBRecordKey obKey = new OBRecordKey("csv", "AANumber", 4);
						obFileBody.setObKey(obKey);
						continue;
					}
					if ("LIMIT_START_TOKEN".equals(currentLine.trim())) {
						OBRecordKey obKey = new OBRecordKey("csv", "LimitId", 4);
						obFileBody.setObKey(obKey);
						continue;
					}
					if ("LIMIT_SECURITY_MAP_START_TOKEN".equals(currentLine.trim())) {
						OBRecordKey obKey = new OBRecordKey("csv", "AANumber", 3);
						obFileBody.setObKey(obKey);
						continue;
					}
					// tracs400 end
					obFileBody.addLine(currentLine);
				}

				currentLine = br.readLine();
				lineNo++;

			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public OBFileHeader getObFileHeader() {
		return obFileHeader;
	}

	public void setObFileHeader(OBFileHeader obFileHeader) {
		this.obFileHeader = obFileHeader;
	}

	public OBFileBody getObFileBody() {
		return obFileBody;
	}

	public void setObFileBody(OBFileBody obFileBody) {
		this.obFileBody = obFileBody;
	}

	public OBFileFooter getObFileFooter() {
		return obFileFooter;
	}

	public void setObFileFooter(OBFileFooter obFileFooter) {
		this.obFileFooter = obFileFooter;
	}
}
