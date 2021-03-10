package com.demo.dao.model;

import java.util.Comparator;
import java.util.Date;

public class TradeEntity implements Comparable<TradeEntity> {

	private String tradeId;
	private int version;
	private String counterParty;
	private String bookId;
	private Date maturityDate;
	private Date createdDate;
	private boolean expired;

	public TradeEntity(String tradeId, int version, String counterParty, String bookId, Date maturityDate,
			Date createdDate, boolean expired) {
		super();

		this.tradeId = tradeId;
		this.version = version;
		this.counterParty = counterParty;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.expired = expired;

	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public int compareTo(TradeEntity o) {
		
		  return Comparator.comparing(TradeEntity::getTradeId)
			      .thenComparing(TradeEntity::getVersion)
			      .compare(this, o);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TradeEntity trade = (TradeEntity) o;
		return this.tradeId.equals(trade.tradeId) && this.version == trade.version;
	}

	@Override
    public int hashCode() 
    { 
          
   
        return this.tradeId.hashCode()+this.version; 
    }

}
