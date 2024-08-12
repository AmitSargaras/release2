package com.integrosys.cms.batch.feeds.property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.AbstractDomainAwareBatchFeedLoader;

public class PropertyFileLoader extends AbstractDomainAwareBatchFeedLoader {

	public final String VALUATION_ENTITY_NAME = "actualValuation";

	public IPropertyFileDAO propertyFileDao;

	public FlatFileItemReader propertyFileReader;

	public FlatFileItemReader getPropertyFileReader() {
		return propertyFileReader;
	}

	public void setPropertyFileReader(FlatFileItemReader propertyFileReader) {
		this.propertyFileReader = propertyFileReader;
	}

	public IPropertyFileDAO getPropertyFileDao() {
		return propertyFileDao;
	}

	public void setPropertyFileDao(IPropertyFileDAO propertyFileDao) {
		this.propertyFileDao = propertyFileDao;
	}

    protected void preprocess(Object feed) {
        OBPropertyFile propertyFeed = (OBPropertyFile) feed;

        propertyFeed.setCompositeID(propertyFeed.getCifID().trim() + String.valueOf(propertyFeed.getCollateralID()).trim());
    }

    protected void doExecute(Map context) throws BatchJobException {
		doReadFeeds(context);
        doRunStoredProcedure();
    }

	protected void doPersistFeedList(List feedList) {
        List newList = new ArrayList();
        Iterator itr = feedList.iterator();

        while (itr.hasNext()) {
            IPropertyFile obp = (IPropertyFile) itr.next();
            if ( !("-999999999").equals(obp.getCompositeID()) ) {
                newList.add(obp);
            }
        }

        propertyFileDao.saveOrUpdatePropertyFeeds(newList);
//        propertyFileDao.createPropertyItems(VALUATION_ENTITY_NAME, feedList);
	}

	protected ResourceAwareItemReaderItemStream getFlatFileItemReader() {
		return propertyFileReader;
	}

}
