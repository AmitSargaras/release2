package com.integrosys.cms.batch.common;

import java.util.Arrays;

import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.Alignment;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;
import org.springframework.util.Assert;

public class EnhancedFixedLengthLineAggregator implements LineAggregator {
	private Range[] ranges;

	private int lastColumn;

	private Alignment align = Alignment.LEFT;

	private char padding = ' ';

	/**
	 * Set column ranges. Used in conjunction with the
	 * {@link RangeArrayPropertyEditor} this property can be set in the form of
	 * a String describing the range boundaries, e.g. "1,4,7" or "1-3,4-6,7" or
	 * "1-2,4-5,7-10".
	 * 
	 * @param columns array of Range objects which specify column start and end
	 * position
	 */
	public void setColumns(Range[] columns) {
		Assert.notNull(columns);
		lastColumn = findLastColumn(columns);
		this.ranges = columns;
	}

	/**
	 * Aggregate provided strings into single line using specified column
	 * ranges.
	 * 
	 * @param fieldSet arrays of strings representing data to be aggregated
	 * @return aggregated strings
	 */
	public String aggregate(FieldSet fieldSet) {

		Assert.notNull(fieldSet);
		Assert.notNull(ranges);

		String[] args = fieldSet.getValues();
//		System.out.println("~~~~~~~~~~~~ args length: "+args.length+"\tranges length: "+ranges.length);		
		Assert.isTrue(args.length <= ranges.length, "Number of arguments must match number of fields in a record");

		// calculate line length
		int lineLength = ranges[lastColumn].hasMaxValue() ? ranges[lastColumn].getMax() : ranges[lastColumn].getMin()
				+ args[lastColumn].length() - 1;

		// create stringBuffer with length of line filled with padding
		// characters
		char[] emptyLine = new char[lineLength];
		Arrays.fill(emptyLine, padding);

		StringBuffer stringBuffer = new StringBuffer(lineLength);
		stringBuffer.append(emptyLine);

		// aggregate all strings
		for (int i = 0; i < args.length; i++) {

			// offset where text will be inserted
			int start = ranges[i].getMin() - 1;

			// calculate column length
			int columnLength;
			if ((i == lastColumn) && (!ranges[lastColumn].hasMaxValue())) {
				columnLength = args[lastColumn].length();
			}
			else {
				columnLength = ranges[i].getMax() - ranges[i].getMin() + 1;
			}

			String textToInsert = (args[i] == null) ? "" : args[i];

			Assert.isTrue(columnLength >= textToInsert.length(), "Supplied text: " + textToInsert
					+ " is longer than defined length: " + columnLength);

			if (align == Alignment.RIGHT) {
				start += (columnLength - textToInsert.length());
			}
			else if (align == Alignment.CENTER) {
				start += ((columnLength - textToInsert.length()) / 2);
			}

			stringBuffer.replace(start, start + textToInsert.length(), textToInsert);
		}

		return stringBuffer.toString();
	}

	/**
	 * Recognized alignments are <code>CENTER, RIGHT, LEFT</code>. An
	 * IllegalArgumentException is thrown in case the argument does not match
	 * any of the recognized values.
	 * 
	 * @param alignment the alignment to be used
	 */
	public void setAlignment(Alignment alignment) {
		this.align = alignment;
	}

	/**
	 * Setter for padding (default is space).
	 * 
	 * @param padding the padding character
	 */
	public void setPadding(char padding) {
		this.padding = padding;
	}

	/**
	 * Get last position of the row from the range provided
	 * @return
	 */
	public int getLastPosition() {
		int lastColumnIndex = findLastColumn(this.ranges);
		return this.ranges[lastColumnIndex].getMax();
	}
	
	public boolean isLastPosition(int lastPosition) {
		return (getLastPosition() == lastPosition);			
	}
	
	public void addLastRange(int max) {
		addLastRange(getLastPosition() + 1, max);
	}
	
	public void addLastRange(int min, int max) {
//		System.out.println(" <<<<<<<<<< min: "+min+"\tmax: "+max+"\tranges length: "+this.ranges.length);
		Range lastRange = new Range(min, max);
		Range[] newColumns = new Range[this.ranges.length + 1];
		System.arraycopy(this.ranges, 0, newColumns, 0, this.ranges.length);
		newColumns[this.ranges.length] = lastRange;
		
		lastColumn = findLastColumn(newColumns);
		this.ranges = newColumns;
	}
	
	/*
	 * Find last column. Columns are not sorted. Returns index of last column
	 * (column with highest offset).
	 */
	private int findLastColumn(Range[] columns) {

		int lastOffset = 1;
		int lastIndex = 0;

		for (int i = 0; i < columns.length; i++) {
			if (columns[i].getMin() > lastOffset) {
				lastOffset = columns[i].getMin();
				lastIndex = i;
			}
		}

		return lastIndex;
	}	

}
