package com.demo;

import java.util.Set;
import java.util.stream.Collectors;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.data.Database;
import com.demo.dao.model.TradeEntity;
import com.demo.exception.LowerVersionException;

@Service
public class MarketPlace {

	@Autowired
	Database database;

	private static final Logger logger = LoggerFactory.getLogger(MarketPlace.class);
	

	
	Scheduler scheduler =  null;
	

	public Set<TradeEntity> showAll() {

		Set<TradeEntity> tradeList = database.getTradeList();

		return tradeList;
	}

	public Set<TradeEntity> showTrade(String tradeId) {
		
		Set<TradeEntity> tradeList = database.getTrade(tradeId);

		for (TradeEntity rr : tradeList) {
			System.out.println(rr.getTradeId() + "|" + rr.getVersion() + "|" + rr.getCounterParty() + "|"
					+ rr.getBookId() + "|" + rr.getMaturityDate() + "|" + rr.getCreatedDate() + "|" + rr.isExpired());
		}
return tradeList ;
	}

	public void addTrade(TradeEntity tentity) throws LowerVersionException {
		
		Set<TradeEntity> allTrades = this.showAll();

		Set<TradeEntity> sameTrade = allTrades.stream() // convert list to stream
				.filter(line -> tentity.getTradeId().equals(line.getTradeId())).collect(Collectors.toSet());
		
		Set<TradeEntity> greaterVersion = sameTrade.stream() // convert list to stream
				.filter(line -> tentity.getVersion() <line.getVersion()).collect(Collectors.toSet());
		
		if(greaterVersion.size()==0)
		{
	
		if (tentity.getMaturityDate().after(tentity.getCreatedDate())) {

			Set<TradeEntity> result = allTrades.stream() // convert list to stream
					.filter(line -> tentity.equals(line)).collect(Collectors.toSet());

			if (result.size() > 0) {
				allTrades.removeAll(result);

				logger.info(Constants.SAME_DATE);
				System.out.println(Constants.SAME_DATE);
				
				allTrades.add(tentity);
				database.addTradeUpdate(allTrades);

			} else {
				database.addTrade(tentity);

			}

		} else {
			logger.error(Constants.MATURITY_DATE);
			System.out.println(Constants.MATURITY_DATE);
		}
		}
		else
		{
			System.out.println(Constants.OLDER_VERSION);
			logger.error(Constants.OLDER_VERSION);
			throw new LowerVersionException(Constants.OLDER_VERSION);
			
		}

	}

	public void start() {
		logger.info("Application Starting ....");

		try {
			database.start();
			logger.info("Application Started and Data added succfully ....");
			
			scheduler = new StdSchedulerFactory().getScheduler();
			
			JobDataMap data = new JobDataMap();
			
			data.put("database",this.database);
			
			JobDetail job1 = JobBuilder.newJob(UpdateExpiryDateJob.class).usingJobData(data)
					.withIdentity("job1", "group1").build();
			
		

			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * *"))
					.build();
			
			try
			{
	
			if(scheduler.isStarted())
			{
			scheduler.shutdown();
			}
			scheduler.start();
			scheduler.scheduleJob(job1, trigger1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			
			
		} catch (Exception e) {
			logger.error("Some error occured in application" + e);
		}

	}

}
