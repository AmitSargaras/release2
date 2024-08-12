/*
 * Created on Jun 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.integrosys.base.techinfra.util.PropertyUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ExcelFeedReader implements IFeedReader {
	private PropertyUtil prop;

	private String CSV_DELIM_STR = ",";

	private String FEED_DATE_FORMAT = "yyyyMMdd";

	private FeedConfiguration configuration;

	private FileInputStream fileIn;

	private HSSFSheet sheet;

	private int currentRowInd = 0;

	public ExcelFeedReader() {
		init();
	}

	protected void init() {
		prop = PropertyUtil.getInstance(FeedConstant.feedLayoutPropFileName);
		CSV_DELIM_STR = prop.getProperty(FeedConstant.PROPNAME_CSV_DELIM);
		FEED_DATE_FORMAT = prop.getProperty(FeedConstant.PROPNAME_DATE_FORMAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedReader#setFeedConfiguration(
	 * com.integrosys.cms.batch.reuterfeed.FeedConfiguration)
	 */
	public void setFeedConfiguration(FeedConfiguration config) {
		configuration = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedReader#getFeedConfiguration()
	 */
	public FeedConfiguration getFeedConfiguration() {
		return configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedReader#initialize()
	 */
	public void initialize() throws Exception {
		fileIn = new FileInputStream(configuration.getFeedFileName());
		HSSFWorkbook wb = new HSSFWorkbook(fileIn);
		sheet = wb.getSheetAt(0);
		if (configuration.getIncludeHeaderCol()) {
			currentRowInd = 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedReader#hasMoreRows()
	 */
	public boolean hasMoreRows() throws Exception {
		// check whether the first cell is empty, may not be an accurate
		// condition
		HSSFRow currentRow = sheet.getRow(currentRowInd);
		HSSFCell firstCell = currentRow.getCell((short) 0);
		if ((firstCell == null) || (firstCell.getStringCellValue() == null)
				|| "".equals(firstCell.getStringCellValue().trim())) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedReader#processNextLineFromFeed()
	 */
	public void processNextLineFromFeed() throws Exception {
		// TODO Auto-generated method stub
		List l = getFieldList();
		HSSFRow currentRow = sheet.getRow(currentRowInd);
		for (int i = 0; i < l.size(); i++) {
			HSSFCell nextCell = currentRow.getCell((short) i);
			if (nextCell != null) {
				FeedFieldDef nextField = (FeedFieldDef) (l.get(i));
				if ((nextCell.getCellType() == HSSFCell.CELL_TYPE_STRING)
						|| (nextCell.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
					nextField.setFieldValue(nextCell.getStringCellValue());
				}
				else if (nextCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					nextField.setFieldValue(String.valueOf(nextCell.getNumericCellValue()));
				}
			}
		}
		currentRowInd = currentRowInd + 1;
	}

	private List getFieldList() {
		Map fieldDef = configuration.getFieldsMapping();
		List l = new ArrayList();
		l.addAll(fieldDef.values());
		Collections.sort(l);
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.batch.reuterfeed.IFeedReader#clearup()
	 */
	public void clearup() throws Exception {
		try {
			if (fileIn != null) {
				fileIn.close();
			}
		}
		catch (Exception ex) {
		}
	}

}
