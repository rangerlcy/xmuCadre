package com.cadre.model.dictionary.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cadre.model.dictionary.Dictionary;
import com.cadre.model.dictionary.DictionaryItem;

public class XmlDictionaryParser implements DictionaryParser{

	private InputStream inputStream;
	public XmlDictionaryParser(String filePath) throws FileNotFoundException {
		this(new File(filePath));
	}
	
	public XmlDictionaryParser(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}
	
	public XmlDictionaryParser(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	@Override
	public Set<Dictionary> parse() throws ParserException {
		try {
			SAXReader reader = new SAXReader();  
			Document doc = reader.read(inputStream);
			Element root = doc.getRootElement();
			
			Set<Dictionary> dictsSet = new HashSet<Dictionary>();
			Dictionary dictionary;
			List<Element> dicts = root.elements();
			for(Element e : dicts) {
				dictionary = new Dictionary();
				dictionary.setName(e.attributeValue("name"));
				dictionary.setDesc(e.attributeValue("desc"));
				
				
				List<Element> items = e.elements();
				for(Element item : items){
					dictionary.add(new DictionaryItem(item.attributeValue("key"), item.attributeValue("value")));
				}
				dictsSet.add(dictionary);
			}
			
			return dictsSet;
		} catch(Exception e){
			throw new ParserException(e);
		}
		
	}

}
