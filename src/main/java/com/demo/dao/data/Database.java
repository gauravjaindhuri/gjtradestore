package com.demo.dao.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.demo.dao.model.BookEntity;
import com.demo.dao.model.CounterParty;
import com.demo.dao.model.TradeEntity;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Database {

	public Set<TradeEntity> tradeList = new HashSet<TradeEntity>();

	public Set<BookEntity> bookList = new HashSet<BookEntity>();

	public Set<CounterParty> cpList = new HashSet<CounterParty>();

	public static final String TRADE_TABLE = "c:\\Projects\\TRADE_TABLE.csv";

	public Database() {

		CounterParty cp1 = new CounterParty("CP-1", "ABC ORG");
		CounterParty cp2 = new CounterParty("CP-2", "XYZ ORG");

		cpList.add(cp1);
		cpList.add(cp2);

		BookEntity b1 = new BookEntity("B1", "PQR ORG", 100L, "A");
		BookEntity b2 = new BookEntity("B2", "PQR ORG", 100L, "D");

		bookList.add(b1);
		bookList.add(b2);

	}

	public void start() {

		TradeEntity entity1 = new TradeEntity("T1", 1, "CP-1", "B1", new Date(), new Date(), false);
		TradeEntity entity2 = new TradeEntity("T2", 1, "CP-1", "B1", new Date(), new Date(), false);
		TradeEntity entity3 = new TradeEntity("T2", 2, "CP-1", "B1", new Date(), new Date(), false);
		

		tradeList.add(entity1);
		tradeList.add(entity2);
		tradeList.add(entity3);

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(TRADE_TABLE));

			for (TradeEntity trad : tradeList) {

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

				// Create record
				String[] record = new String[] { trad.getTradeId(), "" + trad.getVersion(), trad.getCounterParty(),
						trad.getBookId(), dateFormat.format(trad.getMaturityDate()),
						dateFormat.format(trad.getCreatedDate()), trad.isExpired() + "" };
				// Write the record to file
				writer.writeNext(record);
			}

			// close the writer
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Set<TradeEntity> getTradeList() {

		Set<TradeEntity> tradeListLocal = new HashSet<TradeEntity>();

		try {

			CSVReader reader = new CSVReader(new FileReader(TRADE_TABLE), ',', '"', 0);

			// Read all rows at once
			List<String[]> allRows = reader.readAll();

			// Read CSV line by line and use the string array as you want
			for (String[] row : allRows) {

				String tradeId = row[0];

				int version = Integer.parseInt(row[1]);

				String counterParty = row[2];
				String bookId = row[3];

				Date maturity = new SimpleDateFormat("dd/MM/yyyy").parse(row[4]);

				Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(row[5]);

				boolean expire = Boolean.parseBoolean(row[6]);

				TradeEntity tentity = new TradeEntity(tradeId, version, counterParty, bookId, maturity, createdDate,
						expire);

				tradeListLocal.add(tentity);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tradeListLocal;
	}

	public Set<TradeEntity> getTrade(String tradeId) {

		Set<TradeEntity> result = (Set<TradeEntity>) this.getTradeList().stream() // convert list to stream
				.filter(line -> tradeId.equals(line.getTradeId())).collect(Collectors.toSet());

		return result;
	}

	public void addTrade(TradeEntity trade) {

		Set<TradeEntity> tradeListLocal = getTradeList();
		tradeListLocal.add(trade);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(TRADE_TABLE,true));

			for (TradeEntity trad : tradeListLocal) {

				// Create record
				String[] record = new String[] { trad.getTradeId(), "" + trad.getVersion(), trad.getCounterParty(),
						trad.getBookId(), dateFormat.format(trad.getMaturityDate()),
						dateFormat.format(trad.getCreatedDate()), trad.isExpired() + "" };
				// Write the record to file
				writer.writeNext(record);
			}

			// close the writer
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addTradeUpdate(Set<TradeEntity> allTrades) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(TRADE_TABLE));

			for (TradeEntity trad : allTrades) {

				// Create record
				String[] record = new String[] { trad.getTradeId(), "" + trad.getVersion(), trad.getCounterParty(),
						trad.getBookId(), dateFormat.format(trad.getMaturityDate()),
						dateFormat.format(trad.getCreatedDate()), trad.isExpired() + "" };
				// Write the record to file
				writer.writeNext(record);
			}

			// close the writer
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
