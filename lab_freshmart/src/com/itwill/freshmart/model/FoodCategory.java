package com.itwill.freshmart.model;

public class FoodCategory {
	
	   public static final class Entity {
	        public static final String TBL_FOODCATEGORY = "FOOD_CATEGORY";
	        public static final String COL_ID = "ID";
	        public static final String COL_CATEGORY = "CATEGORY";
	    }

	private Integer id;
	private String category;

	
	
	public FoodCategory() {

	}

	public FoodCategory(Integer id, String category) {
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
		return "FoodCategory [" + " id = " + id + ", category = " + category + " ]";
	}
	
	  public static CategoryBuilder builder() {
	        return new CategoryBuilder();
	    }
	
	public static class CategoryBuilder {
        private Integer id;
        private String category;
        
        private CategoryBuilder() {
        }

        public CategoryBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder category(String category) {
            this.category = category;
            return this;
        }

        public FoodCategory build() {
            return new FoodCategory(id, category);
        }
    }

}