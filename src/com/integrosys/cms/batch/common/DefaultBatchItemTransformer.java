package com.integrosys.cms.batch.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.file.mapping.DefaultFieldSet;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.transform.ItemTransformer;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class DefaultBatchItemTransformer implements ItemTransformer {
	private LineAggregator headerAggregator;
	private LineAggregator bodyAggregator;
	private LineAggregator footerAggregator;
    
	public LineAggregator getHeaderAggregator() {
		return headerAggregator;
	}

	public LineAggregator getBodyAggregator() {
		return bodyAggregator;
	}

	public LineAggregator getFooterAggregator() {
		return footerAggregator;
	}

	public void setHeaderAggregator(LineAggregator headerAggregator) {
		this.headerAggregator = headerAggregator;
	}

	public void setBodyAggregator(LineAggregator bodyAggregator) {
		this.bodyAggregator = bodyAggregator;
	}

	public void setFooterAggregator(LineAggregator footerAggregator) {
		this.footerAggregator = footerAggregator;
	}

	public Object transform(Object arg0, int fixLengthSize) throws Exception {
//		System.out.println( "<<<<<<<<<<<< fixLengthSize: "+fixLengthSize);
		if (fixLengthSize > 0) {
			if (headerAggregator instanceof EnhancedFixedLengthLineAggregator) {
				headerAggregator = addLastRange((EnhancedFixedLengthLineAggregator)headerAggregator, fixLengthSize);
				List input = (List)arg0;
				List data = (ArrayList)input.get(0);
				data.add(null);
				input.set(0, data);
			}
			if (footerAggregator instanceof EnhancedFixedLengthLineAggregator) {
				footerAggregator = addLastRange((EnhancedFixedLengthLineAggregator)footerAggregator, fixLengthSize);
				List input = (List)arg0;
				List data = (ArrayList)input.get(input.size() - 1);
				data.add(null);		
				input.set(input.size() - 1, data);
			}
		}
		return transform(arg0);
	}
	
	public Object transform(Object arg0) throws Exception {
		List input = (List)arg0;
		List result = new ArrayList();
		
		int count = 0;
		int size = input.size();
		for (Iterator iterator = input.iterator(); iterator.hasNext();) {
			FieldSet fs = new DefaultFieldSet((String[]) ((List)iterator.next()).toArray(new String[0]));
			if (count == 0) {
				if (headerAggregator == null) {
					result.add(bodyAggregator.aggregate(fs));
				} else {
					DefaultLogger.debug(this,"<<<< header aggregator field set count: "+fs.getFieldCount());					
					result.add(headerAggregator.aggregate(fs));
				}
			} else if (count == size - 1) {
				if (footerAggregator == null) {
					result.add(bodyAggregator.aggregate(fs));
				} else {
					DefaultLogger.debug(this,"<<<< footer aggregator field set count: "+fs.getFieldCount());
					result.add(footerAggregator.aggregate(fs));
				}
			} else {
				result.add(bodyAggregator.aggregate(fs));
			}
			count++;
		}
		
		return result;
	}

	private EnhancedFixedLengthLineAggregator addLastRange
		(EnhancedFixedLengthLineAggregator aggregator, int fixLengthSize) {
		if (!aggregator.isLastPosition(fixLengthSize)) {
			aggregator.addLastRange(fixLengthSize);
		}
		return aggregator;
	}
}
