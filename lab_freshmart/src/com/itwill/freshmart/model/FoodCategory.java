package com.itwill.freshmart.model;

public class FoodCategory {

	private Integer id;
	private String category;

	public FoodCategory() {

	}

	public FoodCategory(Integer id, String category) {
		super();
		this.id = id;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "FoodCategory [" + " id = " + id + ", category = " + category + " / " + "]";
	}
	
	

}
