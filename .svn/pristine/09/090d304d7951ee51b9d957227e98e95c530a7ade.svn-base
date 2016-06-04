package com.cadre.model.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 字典-包含字典名及字典项
 * @author qjp
 *
 */
public class Dictionary {
	private String name;
	private String desc;
	private final List<DictionaryItem> items = new ArrayList<DictionaryItem>();
	private final Map<String, String> keyValueMap = new HashMap<String, String>();
	private final Map<String, String> valueKeyMap = new HashMap<String, String>();
	
	public String getName() {
		return StringUtils.trimToEmpty(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return StringUtils.trimToEmpty(desc);
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void add(DictionaryItem kv) {
		if (kv == null)
			return;
		items.add(kv);
		keyValueMap.put(kv.getKey(), kv.getValue());
		valueKeyMap.put(kv.getValue(), kv.getKey());
	}

	public List<DictionaryItem> getItems() {
		return items;
	}

	public Map<String, String> getMap() {
		return keyValueMap;
	}

	public String getValue(String key) {
		return keyValueMap.get(key);
	}

	public String getCode(String value) {
		return valueKeyMap.get(StringUtils.trimToEmpty(value));
	}
}


