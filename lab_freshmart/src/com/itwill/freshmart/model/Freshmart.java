package com.itwill.freshmart.model;

import java.sql.Date;
import java.time.LocalDate;

public class Freshmart {

	public static final class Entity {
		public static final String TBL_FRESHMART = "FRESHMART";
		public static final String COL_ID = "ID";
		public static final String COL_TYPE_ID = "TYPE_ID";
		public static final String COL_FOOD_NAME = "FOOD_NAME";
		public static final String COL_EXPIRATION_DATE = "EXPIRATION_DATE";
		public static final String COL_STORAGE = "STORAGE";
		public static final String COL_FOOD_QUANTITY = "FOOD_QUANTITY";
		public static final String COL_IMG = "IMG";
	}

	private Integer id; 
	private Integer typeid; 
	private String foodname;
	private LocalDate expirationdate;
	private String storage;
	private Integer foodquantity;
	private String IMG;

	public Freshmart(Integer id, Integer typeid, String foodname, LocalDate expirationdate, String storage,
			Integer foodquantity, String iMG) {
		this.id = id;
		this.typeid = typeid;
		this.foodname = foodname;
		this.expirationdate = expirationdate;
		this.storage = storage;
		this.foodquantity = foodquantity;
		this.IMG = iMG;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public LocalDate getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(LocalDate expirationdate) {
		this.expirationdate = expirationdate;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public Integer getFoodquantity() {
		return foodquantity;
	}

	public void setFoodquantity(Integer foodquantity) {
		this.foodquantity = foodquantity;
	}

	public String getIMG() {
		return IMG;
	}

	public void setIMG(String iMG) {
		IMG = iMG;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Freshmart [ id= " + id + ", typeid= " + typeid + ", foodname= " + foodname + ", expirationdate= "
				+ expirationdate + ", storage= " + storage + ", foodquantity= " + foodquantity + ", IMG= " + IMG + " ]";
	}
	
	public static FreshmartBuilder builder() {
		return new FreshmartBuilder();
	}
	
	public static class FreshmartBuilder {
		private Integer id; // freshmart 테이블의 PK
		private Integer typeid; // freshmart 테이블의 FK
		private String foodname;
		private LocalDate expirationdate;
		private String storage;
		private Integer foodquantity;
		private String IMG;

		private FreshmartBuilder() {
		}

		public FreshmartBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public FreshmartBuilder typeid(Integer typeid) {
			this.typeid = typeid;
			return this;
		}

		public FreshmartBuilder foodname(String foodname) {
			this.foodname = foodname;
			return this;
		}

		public FreshmartBuilder expirationdate(LocalDate expirationdate) {
			this.expirationdate = expirationdate;
			return this;
		}
		
		public FreshmartBuilder expirationdate(Date expirationdate) {
			
			if (expirationdate != null) { 
				this.expirationdate = expirationdate.toLocalDate();
			}
			return this;
		}

		public FreshmartBuilder storage(String storage) {

			this.storage = storage;

			return this;
		}
		
		public FreshmartBuilder foodquantity(Integer foodquantity) {
			this.foodquantity = foodquantity;
			return this;
		}
		
		public FreshmartBuilder IMG(String IMG) {
			this.IMG = IMG;
			return this;
		}

		// (6) 외부 클래스 타입을 리턴하는 메서드
		public Freshmart build() {
			return new Freshmart(id, typeid, foodname, expirationdate, storage, foodquantity, IMG);
		}

	}

	

}
