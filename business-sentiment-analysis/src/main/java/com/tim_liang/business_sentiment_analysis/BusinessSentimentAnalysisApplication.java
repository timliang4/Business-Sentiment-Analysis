package com.tim_liang.business_sentiment_analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinessSentimentAnalysisApplication implements CommandLineRunner {

	@Autowired
	private BusinessRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(BusinessSentimentAnalysisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Business b1 = new Business("ChIJZ0-OtALhw4kRR23dl-sjDYk");
		b1.setName("Hot Wok Cafe");
		b1.setAverageSentiment("positive");
		b1.setSentimentVariance("0.29");

		Business b2 = new Business("ChIJx3Nrt55ZwokRP3Xiw0TGnKE");
		b2.setName("Han Dynasty");
		b2.setAverageSentiment("positive");
		b2.setSentimentVariance("0.62");

		Business b3 = new Business("ChIJ41r0ucHmw4kRal9zizLvEfY");
		b3.setName("Chuck's Spring Street Cafe");
		b3.setAverageSentiment("neutral");
		b3.setSentimentVariance("0.94");

		Business b4 = new Business("ChIJRxuuKQ5fwYkRkRc7E745Zck");
		b4.setName("Gaetano's Cheesesteaks");
		b4.setAverageSentiment("neutral");
		b4.setSentimentVariance("0.80");

		repo.save(b1);
		repo.save(b2);
		repo.save(b3);
		repo.save(b4);
	}
}
