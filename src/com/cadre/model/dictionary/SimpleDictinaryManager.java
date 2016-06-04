package com.cadre.model.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cadre.common.WebApplication;
import com.cadre.model.dictionary.parser.DictionaryParser;
import com.cadre.model.dictionary.parser.ParserException;
import com.cadre.model.dictionary.parser.XmlDictionaryParser;
import com.cadre.pojo.DictionaryDb;

/**
 * 字典管理类
 * @author qjp
 *
 */
public class SimpleDictinaryManager extends AbstractDictionaryManager{
	
	/**
	 * 可以传入多个字典解析器
	 * @param parserList
	 */
	private SimpleDictinaryManager(){
		try {
			List<DictionaryDb> dictionaryDbs = WebApplication.getCurrUser().getDictionarys();
			Dictionary d = null;
			Map<String, DictionaryDb> dicMap = new HashMap<String, DictionaryDb>();
			for (DictionaryDb dictionaryDb : dictionaryDbs){//加载字典表中字典
				d  = new Dictionary();
				if (dictionaryDb.getParentCode() != null && dictionaryDb.getParentCode().equals("init")){
					if (null == dictionaryDb.getName() || null == dictionaryDb.getRemark()){
						continue;
					}
					d.setDesc(dictionaryDb.getRemark());
					d.setName(dictionaryDb.getName());
					dicMap.put(dictionaryDb.getCode(), dictionaryDb);
					dictionarys.put(dictionaryDb.getName().toLowerCase(), d);
				}
			}
			d = null;
			//加载字典项
			for (DictionaryDb dictionaryDb : dictionaryDbs){
				if (dictionaryDb.getParentCode() != null && null != dictionaryDb.getCode() && dictionaryDb.getCode().length() > 4 ){
					if (null == dictionaryDb.getKey() || null == dictionaryDb.getValue()){
						continue;
					}
					d = dictionarys.get(dicMap.get(dictionaryDb.getParentCode()).getName().toLowerCase());//获取这个字典
					if (null == d)
						continue;
					// TODO 向字典中加入字典项并写回
					d.add(new DictionaryItem(dictionaryDb.getKey(), dictionaryDb.getValue()));
					dictionarys.put(d.getName().toLowerCase(), d);
					d = null;
				}
			}
//			
//			DictionaryParser parser = new XmlDictionaryParser(SimpleDictinaryManager.class.getResourceAsStream("xml/dictionary.xml"));
//			Set<Dictionary> dictionaries =parser.parse();
//			for(Dictionary d : dictionaries){
//				dictionarys.put(d.getName().toLowerCase(), d);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 默认字典管理器单例
	 * @author qjp
	 *
	 */
	static class InstanceHolder {
		static final SimpleDictinaryManager instance = new SimpleDictinaryManager();
	}
	
	/**
	 * 返回默认默认字典管理器单例
	 * @return
	 */
	public static SimpleDictinaryManager get(){
		return InstanceHolder.instance;
	}
}
