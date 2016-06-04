/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.springmvc.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * @Type: com.cadre.springmvc.controller.StringEscapeEditor.java
 * @ClassName: StringEscapeEditor
 * @Description: <br/>
 * 
 */
public class StringEscapeEditor extends PropertyEditorSupport {

	private boolean escapeHTML;
    private boolean escapeJavaScript;
    @SuppressWarnings("unused")
	private boolean escapeSQL;

    public StringEscapeEditor() {
        super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript/*, boolean escapeSQL*/) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        /*this.escapeSQL = escapeSQL;*/
    }

    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = StringEscapeUtils.escapeHtml3(value);
            }
            if (escapeJavaScript) {
                value = StringEscapeUtils.unescapeEcmaScript(value);
            }
            /*if (escapeSQL) {
                value = StringEscapeUtils.escapeSql(value);
            }*/
            setValue(value);
        }
    }

    public String getAsText() {
        Object value = getValue();
        return (value != null ? value.toString() : "");
    }
}
