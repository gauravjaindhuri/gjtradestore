package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.dao.model.TradeEntity;
import com.demo.exception.LowerVersionException;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TradeDataTest {

	@Autowired
	MarketPlace marketPlace;

	@Test
	public void aaaatestImmutableCollectionsee() {

		marketPlace.start();

	}

	@Test
	public void testMaturityDate() throws LowerVersionException {

		String sDate1 = "31/12/1998";
		Date maturityDate = new Date();
		try {
			maturityDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<TradeEntity> tt = marketPlace.showAll();

		TradeEntity entity1 = new TradeEntity("T1", 1, "CP-1", "B1", maturityDate, new Date(), false);

		marketPlace.addTrade(entity1);

		Set<TradeEntity> tt2 = marketPlace.showAll();

		assertEquals(tt.size(), tt2.size());

	}

	@Test
	public void testMaturityDateFuture() throws LowerVersionException {

		String sDate1 = "31/12/2024";
		Date maturityDate = new Date();
		try {
			maturityDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<TradeEntity> tt = marketPlace.showAll();

		TradeEntity entity1 = new TradeEntity("T4", 2, "CP-1", "B6", maturityDate, new Date(), false);

		marketPlace.addTrade(entity1);

		Set<TradeEntity> tt2 = marketPlace.showAll();

		assertEquals(tt.size() + 1, tt2.size());

	}
	
	@Test
	public void testSameVersion() throws LowerVersionException {
		
		String sDate1 = "31/12/2024";
		Date maturityDate = new Date();
		try {
			maturityDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TradeEntity entity1 = new TradeEntity("T2", 2, "CP-1", "B6", maturityDate, new Date(), false);

		marketPlace.addTrade(entity1);

		Set<TradeEntity> tt2 = marketPlace.showTrade("T2");

		Set<TradeEntity> tt3 = tt2.stream().filter(ll -> ll.equals(entity1)).collect(Collectors.toSet());

		for (TradeEntity tt : tt3) {
			assertEquals(entity1.getBookId(), tt.getBookId());

		}

	}

}
