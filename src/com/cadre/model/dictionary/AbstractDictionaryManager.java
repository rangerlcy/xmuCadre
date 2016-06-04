package com.cadre.model.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cadre.model.utils.StringUtil;


public class AbstractDictionaryManager {

	protected final Map<String,Dictionary> dictionarys = new HashMap<String,Dictionary>();

	/**
	 * 返回一个字典
	 * @param name
	 * @return
	 */
	public Dictionary getDictionary(String name) {
		if(StringUtils.isBlank(name)){
			throw new RuntimeException(" 字典name不能为空");
		}
		return dictionarys.get(StringUtils.trimToEmpty(name).toLowerCase());
	}

	public Map<String,Dictionary> getDictionarys() {
		return dictionarys;
	}

	/**
	 * 得到字典项的值
	 * @param dictionaryName 字段名称
	 * @param key 字典项
	 * @return
	 */
	public String getValue(String dictionaryName, String key) {
		return getDictionary(dictionaryName).getValue(key);
	}

	/**
	 * 得到名称对应的字典项
	 * @param dictionaryName
	 * @param value
	 * @return
	 */
	public String getKey(String dictionaryName, String value) {
		return getDictionary(dictionaryName).getCode(value);
	}
	/**
	 * 得到名称对应的字典项
	 * @param dictionaryName
	 * @param value
	 * @param defaultItemIndex 默认项
	 * @return
	 */
	public String getKey(String dictionaryName, String value,int defaultItemIndex) {
		String key = getDictionary(dictionaryName).getCode(value);
		if(StringUtils.isBlank(key)){
			key = getDictionary(dictionaryName).getItems().get(defaultItemIndex).getKey();
		}
		return key;
	}
	/**
	 * 得到名称对应的字典项
	 * @param dictionaryName
	 * @param value
	 * @param defaultValue 默认值
	 * @param defaultItemIndex 如果默认值为空，取默认项的值
	 * @return
	 */
	public String getKey(String dictionaryName, String value,String defaultValue,int defaultItemIndex) {
		String key = getDictionary(dictionaryName).getCode(value);
		if(StringUtils.isBlank(key)){
			key = defaultValue;
		}
		if(StringUtils.isBlank(key)){
			key = getDictionary(dictionaryName).getItems().get(defaultItemIndex).getKey();
		}
		return key;
	}
	/**
	 * 得到一字典的所有字典项
	 * @param dictionaryName
	 * @return
	 */
	public List<DictionaryItem> getItems(String dictionaryName) {
		return getDictionary(dictionaryName).getItems();
	}

}