package com.tim_liang.business_sentiment_analysis;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessRepository extends MongoRepository<Business, String> {

}
