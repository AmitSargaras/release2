package com.integrosys.cms.app.diary.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IDiaryDao {

	public static final String ACTUAL_DIARY_ITEM_NAME = "actualOBDiaryItem";

	public IDiaryItemUI getDiaryItem(String entityName, Serializable key);

	public IDiaryItemUI createDiaryItem(String entityName, IDiaryItemUI item);

	public IDiaryItemUI updateDiaryItem(String entityName, IDiaryItemUI item);

	public SearchResult getAllDiaryItemList(String entityName) throws DiaryItemException;
	
	public int getSegmentGenericCount(String segment,Long teamId) throws Exception;
	
	public int getSegmentDroplineODCount(String segment,Long teamId) throws Exception;
	
	public SearchResult getGenericListWithSegment(String entityName,String segmentName) throws DiaryItemException ;
	
	public SearchResult getGenericListWithoutSegment(String entityName) throws DiaryItemException;
	
	public SearchResult getDropLineListWithSegment(String entityName,String segmentName) throws DiaryItemException ;
	
	public SearchResult getDropLineListWithoutSegment(String entityName) throws DiaryItemException;
	
	public SearchResult getTotalListWithSegment(String entityName,String segmentName) throws DiaryItemException;
	
	public int getGenericTodayCount(String applicationDate,Long teamId) throws Exception;
	
	public int getDroplineTodayCount(String applicationDate,Long teamId) throws Exception;
	
	public int getOverdueGenericTodayCount(String applicationDate,Long teamId) throws Exception;
	
	public int getOverdueDroplineTodayCount(String applicationDate,Long teamId) throws Exception;
	
	public int getSegmentGenericTodayCount(String segment,String applicationDate,Long teamId) throws Exception;
	
	public int getSegmentDroplineTodayCount(String segment,String applicationDate,Long teamId) throws Exception;
	
	public int getOverdueSegmentGenericTodayCount(String segment,String applicationDate,Long teamId) throws Exception;
	
	public int getOverdueSegmentDroplineTodayCount(String segment,String applicationDate,Long teamId) throws Exception;
	//public ArrayList getRegionAndSegment(String legalReference);

}
