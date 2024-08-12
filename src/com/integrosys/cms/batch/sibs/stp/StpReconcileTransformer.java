package com.integrosys.cms.batch.sibs.stp;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.file.mapping.DefaultFieldSet;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.transform.ItemTransformer;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 3, 2008
 * Time: 1:34:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpReconcileTransformer implements ItemTransformer {

    /**
     * Aggregators for all types of lines in the output file
     */
    private Map aggregators;

    /**
     * Converts information from an object to a collection of Strings for
     * output.
     */
    public Object transform(Object data) {
        List input = (List) data;
        List result = new ArrayList();

        for (Iterator iterator = input.iterator(); iterator.hasNext();) {
            OBStpReconcile obStpReconcile = (OBStpReconcile) iterator.next();
            if (StringUtils.equals(obStpReconcile.getRecordType(), "H")) {
                result.add(getAggregator("header").aggregate(StpReconcileFormatterUtils.headerArgs()));
            } else if (StringUtils.equals(obStpReconcile.getRecordType(), "D")) {
                result.add(getAggregator("body").aggregate(StpReconcileFormatterUtils.lineItemArgs(obStpReconcile)));
            } else if (StringUtils.equals(obStpReconcile.getRecordType(), "T")) {
                result.add(getAggregator("footer").aggregate(StpReconcileFormatterUtils.footerArgs(obStpReconcile.getFiller())));
            }
        }

        return result;
    }

    public void setAggregators(Map aggregators) {
        this.aggregators = aggregators;
    }

    private LineAggregator getAggregator(String name) {
        return (LineAggregator) aggregators.get(name);
    }

    /**
     * Utility class encapsulating formatting of <code>Stp Reconcile</code> and its
     * nested objects.
     */
    private static class StpReconcileFormatterUtils {

        private static SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

        static FieldSet headerArgs() {
            return new DefaultFieldSet(new String[]{"H", dateFormat.format(new Date()),
                    "   ", "BR001", "   ", "SIBS", "T"});
        }

        static FieldSet footerArgs(String count) {
            return new DefaultFieldSet(new String[]{"T", count, "             T"});
        }

        static FieldSet lineItemArgs(OBStpReconcile item) {
            return new DefaultFieldSet(new String[]{item.getRecordType(), item.getTrxUID(),
                    item.getFiller(), "T"});
        }
    }
}
