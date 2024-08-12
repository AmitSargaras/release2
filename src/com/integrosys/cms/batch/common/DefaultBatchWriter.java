package com.integrosys.cms.batch.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.transform.ItemTransformer;
import org.springframework.batch.repeat.ExitStatus;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.common.mapping.ColumnMetaInfo;
import com.integrosys.cms.batch.factory.BatchJob;

public class DefaultBatchWriter implements BatchJob {
	private JdbcCursorItemReader itemReader;
	
	private FlatFileItemWriter itemWriter;
	
	private ItemTransformer itemTransformer;
	
	private ColumnMetaInfo[] headerMetaInfo;
	private Map headerMap;
	
	private ColumnMetaInfo[] footerMetaInfo;
	private Map footerMap;
	
	private String sql;
	
	private long recordCount;
	
	private int fixLengthSize;
	
	public JdbcCursorItemReader getItemReader() {
		return itemReader;
	}

	public void setItemReader(JdbcCursorItemReader itemReader) {
		this.itemReader = itemReader;
	}

	public FlatFileItemWriter getItemWriter() {
		return itemWriter;
	}

	public void setItemWriter(FlatFileItemWriter itemWriter) {
		this.itemWriter = itemWriter;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	
	public ItemTransformer getItemTransformer() {
		return itemTransformer;
	}

	public void setItemTransformer(ItemTransformer itemTransformer) {
		this.itemTransformer = itemTransformer;
	}

	public ColumnMetaInfo[] getHeaderMetaInfo() {
		return headerMetaInfo;
	}

	public Map getHeaderMap() {
		return headerMap;
	}

	public ColumnMetaInfo[] getFooterMetaInfo() {
		return footerMetaInfo;
	}

	public Map getFooterMap() {
		return footerMap;
	}

	public void setHeaderMetaInfo(ColumnMetaInfo[] headerMetaInfo) {
		this.headerMetaInfo = headerMetaInfo;
	}

	public void setHeaderMap(Map headerMap) {
		this.headerMap = headerMap;
	}

	public void setFooterMetaInfo(ColumnMetaInfo[] footerMetaInfo) {
		this.footerMetaInfo = footerMetaInfo;
	}

	public void setFooterMap(Map footerMap) {
		this.footerMap = footerMap;
	}

	public int getFixLengthSize() {
		return fixLengthSize;
	}

	public void setFixLengthSize(int fixLengthSize) {
		this.fixLengthSize = fixLengthSize;
	}

	protected void preProcess() {
		if (sql != null) {
			itemReader.setSql(sql);
		}
	}
	
	public ExitStatus execute() throws Exception {
        ExecutionContext ctx = new ExecutionContext();
        
		List dataList = new ArrayList();
		
		// set header data		
		if (headerMetaInfo != null && headerMetaInfo.length > 0) {
			dataList.add(mapHeaderFooterData(headerMetaInfo, headerMap));
		}

		// set body        
		List data = new ArrayList();
		recordCount = 0;
		try {
	        itemReader.open(ctx);			
	        while ((data = (List) itemReader.read()) != null) {
	        	dataList.add(data);
	            recordCount++;
	            //System.out.println("<<<< recordCount: "+recordCount);
	        }    
		} catch (Exception e) {
			itemReader.close(ctx);
			throw e;
		}
        itemReader.close(ctx);	
        
		// set footer data
		if (footerMetaInfo != null && footerMetaInfo.length > 0) {
			dataList.add(mapHeaderFooterData(footerMetaInfo, footerMap));
		}		
		
		// then transform		
        itemWriter.open(ctx);
        ArrayList transformed;
        if (fixLengthSize > 0 && itemTransformer instanceof DefaultBatchItemTransformer) {
        	transformed = (ArrayList)((DefaultBatchItemTransformer)itemTransformer).transform(dataList, fixLengthSize);
        } else {
        	transformed = (ArrayList) itemTransformer.transform(dataList);
        }
        for (Iterator iterator = transformed.iterator(); iterator.hasNext();) {
            String output = (String) iterator.next();
            itemWriter.write(output);
        }        
        
        itemWriter.flush();
        itemWriter.close(ctx);
        return ExitStatus.FINISHED;        		
	}
	
    public void execute(Map context) throws BatchJobException {
        try {
            execute();
        } catch (Exception e) {
            throw new IncompleteBatchJobException("Failed to generate output batch file... ", e);
        }
    }	
	
	private final static String TOTAL_COUNT = "total_count";
	private final static String CURRENT_DATE = "current_date";
	
	private List mapHeaderFooterData(ColumnMetaInfo[] headerFooterMetaInfo, Map dataMap) {
		List data = new ArrayList();		
		
		for (int i = 0; i < headerFooterMetaInfo.length; i++) {
			ColumnMetaInfo metaInfo = headerFooterMetaInfo[i];
			
			String dataStr = null;
			if (dataMap != null) {
				dataStr = (String)dataMap.get(String.valueOf(metaInfo.getColumnNumber()));
			}
			
			if (StringUtils.isBlank(dataStr)) {
				data.add(null);
				continue;
			}
			
			if (metaInfo.getClassType() == String.class) {
				data.add(dataStr);
			} else if (metaInfo.getClassType() == Double.class ||
					metaInfo.getClassType() == BigDecimal.class ||
					metaInfo.getClassType() == Long.class ||
					metaInfo.getClassType() == Integer.class) {
				if (TOTAL_COUNT.equals(dataStr)) {
					data.add(String.valueOf(recordCount));
				} else {
					// in the future add more options
					data.add(null);
				}
			} else if (metaInfo.getClassType() == Date.class) {
				if (CURRENT_DATE.equals(dataStr)) {
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat(metaInfo.getDateFormat());
					data.add(formatter.format(date));
				} else {
					data.add(null);
				}
			}  else if (metaInfo.getClassType() == Boolean.class) {
				data.add(null);
			} else {
				throw new IllegalArgumentException("unknown data type [" + metaInfo + "]");
			}
		}
		
		return data;
	}
}
