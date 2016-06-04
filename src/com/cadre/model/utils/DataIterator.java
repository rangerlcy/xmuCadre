/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.model.utils;

/**
 *  此类比较适合写出数据量比较大的,比如导出100万的数据就没办法一次查出到一个列表中，但可以通过迭代到下一条。
 * 
 */
public interface DataIterator<T> {
	public String[] getTitleColumns();

	public T next();

	public boolean hasNext();
}
