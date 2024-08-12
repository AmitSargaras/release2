package com.integrosys.cms.batch.sibs.stp;

import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.transform.ItemTransformerItemWriter;
import org.springframework.batch.item.transform.ItemTransformer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.repeat.ExitStatus;

import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 2, 2008
 * Time: 4:30:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpReconcileFileGen implements BatchJob {
    private JdbcCursorItemReader itemReader;
    private FlatFileItemWriter itemWriter;
    private StpReconcileTransformer itemTransformer;

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

    public StpReconcileTransformer getItemTransformer() {
        return itemTransformer;
    }

    public void setItemTransformer(StpReconcileTransformer itemTransformer) {
        this.itemTransformer = itemTransformer;
    }

    public ExitStatus execute() throws Exception {
        OBStpReconcile tmp;
        int recordCount = 0;
        ExecutionContext ctx = new ExecutionContext();
        itemReader.open(ctx);
        itemWriter.open(ctx);
        ArrayList data = new ArrayList();

        // write file header
        tmp = new OBStpReconcile();
        tmp.setRecordType("H");
        data.add(tmp);

        while ((tmp=(OBStpReconcile) itemReader.read()) != null) {
            data.add(tmp);
            recordCount++;
        }

        // write file footer
        tmp = new OBStpReconcile();
        tmp.setRecordType("T");
        // reuse filler field to pass in record count
        tmp.setFiller(Integer.toString(recordCount));
        data.add(tmp);

        ArrayList transformed = (ArrayList) itemTransformer.transform(data);
        for (Iterator iterator = transformed.iterator(); iterator.hasNext();) {
            String output = (String) iterator.next();
            itemWriter.write(output);
        }

        itemWriter.flush();
        itemWriter.close(ctx);
        itemReader.close(ctx);
        return ExitStatus.FINISHED;
    }

    public void execute(Map context) throws BatchJobException {
        try {
            execute();
        } catch (Exception e) {
            throw new IncompleteBatchJobException("BR001-Stp Reconcile failed to complete", e);
        }
    }
}
