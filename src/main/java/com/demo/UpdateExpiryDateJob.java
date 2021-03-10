package com.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.demo.dao.data.Database;
import com.demo.dao.model.TradeEntity;


public class UpdateExpiryDateJob implements Job {
	
	
	private Database database;
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		System.out.println("Job started..........");

		JobDetail jobDetail = context.getJobDetail();

		database = (Database) jobDetail.getJobDataMap().get("database");

		Set<TradeEntity> trades = database.getTradeList();
		Set<TradeEntity> updatedtrades = new HashSet<TradeEntity>();

		for (TradeEntity te : trades) {
			
			
			String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
			
			Date todayDate = null;
			try {
				todayDate = new SimpleDateFormat("dd/MM/yyyy").parse(today);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (te.getMaturityDate().before(todayDate)) {
				te.setExpired(true);
			}
			updatedtrades.add(te);

		}

		database.addTradeUpdate(updatedtrades);

	}

}
