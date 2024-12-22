package com.pradeep.reboot.executer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pradeep.reboot.configuration.AppConfigProperties;
import com.pradeep.reboot.entity.RebootData;
import com.pradeep.reboot.service.rest.DBService;
import com.pradeep.reboot.service.rest.ExternalService;

@Service
public class RebootExecutor {

	@Autowired
	AppConfigProperties appConfigProperties;

	@Autowired
	ExternalService externalService;
	
    @Autowired
    private DBService dbService;
	
	public void rebootAll() {
		ScheduledExecutorService scheduler = Executors
				.newScheduledThreadPool(Integer.parseInt(appConfigProperties.getThreadPoolSize()));
		int activeThreads = Thread.activeCount();
		System.out.println("Active threads at Start (estimate): " + activeThreads);

		List<RebootData> rebootDataList= dbService.getAllRebootData();
		// Get Eligible data from DB

		// Schedule task1 to run after a fixed delay of 2 seconds
		for (int i = 0; i <= rebootDataList.size(); i++) {
			//ExternalService service = new ExternalService();
			RebootData rebootData = rebootDataList.get(i);
			
			String serialNo = rebootData.getSerialNo();
			String oui = rebootData.getOui();
			String productClass = rebootData.getProductClass();

			scheduler.schedule(new Runnable() {
				@Override
				public void run() {
					externalService.getDeviceDetails(serialNo, oui, productClass);
					externalService.checkCDA(serialNo, oui, productClass);
					externalService.getGPVDetails(serialNo, oui, productClass);
					externalService.performRebootOne("deviceId");
					RebootData rebootdata = new RebootData();
					rebootdata.setStatus("Status");
					dbService.updateRebootData(rebootdata);
				}
			}, 0, TimeUnit.SECONDS);

		}

		// Optionally, stop the scheduler after 10 seconds to demonstrate graceful
		// shutdown
		scheduler.schedule(() -> {
			System.out.println("Shutting down scheduler...");
			int activeThreads2 = Thread.activeCount();
			System.out.println("Active threads at End (estimate): " + activeThreads2);
			scheduler.shutdown();
		}, 10, TimeUnit.SECONDS);

	}

	public static void main(String[] args) {
		new RebootExecutor().rebootAll();

	}
	
	@Scheduled(cron = "0 0 4 * * ?") // Every day at 4:00 AM
    public void runDailyTask() {
		rebootAll();
    }

}
