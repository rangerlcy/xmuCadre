package com.cadre.controller.pojo;

import java.util.ArrayList;
import java.util.List;

public class PositionPaperParam {
	List<PositionPaperItem> positionPaperItems = new ArrayList<PositionPaperItem>();

	public List<PositionPaperItem> getPositionPaperItems() {
		return positionPaperItems;
	}

	public void setPositionPaperItems(List<PositionPaperItem> positionPaperItems) {
		this.positionPaperItems = positionPaperItems;
	}
	
	
}
