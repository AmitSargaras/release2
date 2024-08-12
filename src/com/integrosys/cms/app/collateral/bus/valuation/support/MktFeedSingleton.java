package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IMarketableValuationDAO;

public class MktFeedSingleton implements ValuationProfileSingleton, IMarketableProfileSingleton {

	private static MktFeedSingleton instance;

	private IMarketableValuationDAO marketableValuationDao;

	private boolean initialized;

	private HashMap feedMap;

	private List boardList;

	public IMarketableValuationDAO getMarketableValuationDao() {
		return marketableValuationDao;
	}

	public void setMarketableValuationDao(IMarketableValuationDAO marketableValuationDao) {
		this.marketableValuationDao = marketableValuationDao;
	}

	public MktFeedSingleton() {
	}

	public void init() {
		if (!initialized) {
			feedMap = new HashMap();
			boardList = new ArrayList();
			try {
				marketableValuationDao.retrieveFeedPriceInfo(feedMap);
				marketableValuationDao.retrieveBoardType(boardList);
				initialized = true;
			}
			catch (Exception ex) {
			}
		}
	}

	public static MktFeedSingleton getInstance() {
		if (instance == null) {
			synchronized (MktFeedSingleton.class) {
				if (instance == null) {
					instance = new MktFeedSingleton();
				}
			}
		}
		return instance;
	}

	public HashMap getFeedMap() {
		return feedMap;
	}

	public void setFeedMap(HashMap feedMap) {
		this.feedMap = feedMap;
	}

	public List getBoardList() {
		return boardList;
	}

	public void setBoardList(List boardList) {
		this.boardList = boardList;
	}

	public void reloadData() {
		clearData();
		marketableValuationDao.retrieveFeedPriceInfo(feedMap);
		marketableValuationDao.retrieveBoardType(boardList);
	}

	public void clearData() {
		feedMap.clear();
		boardList.clear();
	}

	public Object getData(String key) {
		return feedMap.get(key);
	}

	public Map getData() {
		return getFeedMap();
	}

	public void reloadProfiles() {
		reloadData();
	}

}
