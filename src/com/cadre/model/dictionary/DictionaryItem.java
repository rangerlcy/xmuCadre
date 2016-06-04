
package com.cadre.model.dictionary;

import java.io.Serializable;

/**
 * 
 */
public class DictionaryItem implements Serializable, Cloneable {

	private static final long serialVersionUID = 4884450869635166322L;
	private String key;
	private String value;

	public DictionaryItem(String key,String value) {
		this.key = key;
		this.value = value;
	}

	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//***************以下代码是为了兼容之前其它对像中的getCode,getName方法*******************/
	public String getCode(){
		return key;
	}
	
	public String getName(){
		return value;
	}
	//**********************************/
}
