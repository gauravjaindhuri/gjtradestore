package com.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.demo.dao.model.TradeEntity;
import com.demo.exception.LowerVersionException;

@Service
public class MainApp {

	private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
	

	public static void main(String[] args) {
		
		try
		{
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	
		
		
		

		if (args != null && args.length > 0) {

			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-beans.xml");

			MarketPlace main = (MarketPlace) applicationContext.getBean("marketPlace");

			if (args.length == 1 && args[0].equals("start")) {

				main.start();

			}
			if (args.length == 1 && args[0].equals("stop")) {}

			if (args.length == 1 && args[0].equals("show")) {

				Set<TradeEntity> tradeList = main.showAll();
				
				

				for (TradeEntity rr : tradeList) {
					System.out.println(
							rr.getTradeId() + "|" + rr.getVersion() + "|" + rr.getCounterParty() + "|" + rr.getBookId()
									+ "|" + dateFormat.format(rr.getMaturityDate()) + "|" + dateFormat.format(rr.getCreatedDate()) + "|" + rr.isExpired());
				}

			}

			if (args.length == 2 && args[0].equals("show")) {

				main.showTrade(args[1]);
			}

			if (args.length > 2 && args[0].equals("add")) {

				try {

					String tradeId = args[1];

					int version = Integer.parseInt(args[2]);

					String counterParty = args[3];
					String bookId = args[4];
					boolean expire = false;

					Date maturity = new SimpleDateFormat("dd/MM/yyyy").parse(args[5]);

					Date createdDate = new Date();

					TradeEntity tentity = new TradeEntity(tradeId, version, counterParty, bookId, maturity, createdDate,
							expire);

					main.addTrade(tentity);

				} catch (ArrayIndexOutOfBoundsException e) {
					logger.error(Constants.INVALID_INPUT + e);
					System.out.println(Constants.INVALID_INPUT + e);
				} catch (NumberFormatException e1) {
					logger.error(Constants.INVALID_INPUT + e1);
					System.out.println(Constants.INVALID_INPUT + e1);
				} catch (ParseException e) {
					logger.error(Constants.INVALID_INPUT + e);
					System.out.println(Constants.INVALID_INPUT + e);
				} catch (LowerVersionException e) {
					logger.error(Constants.OLDER_VERSION + e);
					System.out.println(Constants.OLDER_VERSION + e);
				}

			}

			((ClassPathXmlApplicationContext) applicationContext).close();

		}

		else {
			logger.error(Constants.INVALID_INPUT);
			System.out.println(Constants.INVALID_INPUT);
		}
		
		}
		catch(Exception e)
		{
			
		}
	}

}
