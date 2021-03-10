package com.demo.dao.model;

public class BookEntity {
	
	String id ;
	String ownerCompnay ;
	Long price ;
	String position ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerCompnay() {
		return ownerCompnay;
	}
	public void setOwnerCompnay(String ownerCompnay) {
		this.ownerCompnay = ownerCompnay;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public BookEntity(String id, String ownerCompnay, Long price, String position) {
		super();
		this.id = id;
		this.ownerCompnay = ownerCompnay;
		this.price = price;
		this.position = position;
	}
	
	
	

}
