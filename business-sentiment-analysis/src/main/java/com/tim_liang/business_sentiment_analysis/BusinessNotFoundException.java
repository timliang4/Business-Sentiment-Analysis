package com.tim_liang.business_sentiment_analysis;

class BusinessNotFoundException extends RuntimeException {

    BusinessNotFoundException(String id) {
        super("Could not find employee " + id);
    }

}
