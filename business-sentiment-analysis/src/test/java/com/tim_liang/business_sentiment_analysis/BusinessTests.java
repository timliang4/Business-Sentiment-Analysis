package com.tim_liang.business_sentiment_analysis;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusinessTests {

    @Test
    void testConstructor() {
        var business = new Business("1");
        assertEquals("1", business.getId());
        assertEquals("", business.getAverageSentiment());
        assertEquals("", business.getSentimentVariance());
    }

    @Test
    void testToString() {
        var business = new Business("1");
        business.setName("Whole Foods");
        business.setAverageSentiment("Positive");
        business.setSentimentVariance("0.20");
        assertEquals(business.toString(),
                "Business(placeID=1, name='Whole Foods', averageSentiment=Positive, sentimentVariance=0.20)");
    }

    @Test
    void testSentimentAnalysis() {
        String text = "Terrible service. all reviews are fake and irrelevant. This is about reviewing google as business not the building/staff etc.";
        List<String> reviews = new ArrayList<>();
        reviews.add(text);
        List<Integer> expectedScores = new ArrayList<>();
        expectedScores.add(0);
        expectedScores.add(1);
        expectedScores.add(1);
        assertEquals(expectedScores, Business.getSentimentScores(reviews));
    }

    @Test
    void testAverageCalculation() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(1, 3, 2, 4, 0, 1, 2, 3));
        assertEquals(2, Business.calculateAverageSentiment(arr));
        assertEquals("very negative", Business.getAverageSentimentString(0.0));
        assertEquals("negative", Business.getAverageSentimentString(1.0));
        assertEquals("neutral", Business.getAverageSentimentString(2.0));
        assertEquals("positive", Business.getAverageSentimentString(3.0));
        assertEquals("very positive", Business.getAverageSentimentString(4.0));
    }

    @Test
    void testVarianceCalculation() {
        List<Integer> arr = new ArrayList<>(Arrays.asList(1, 0, 4, 3, 2, 4, 2, 1, 0, 2, 1, 4, 3));
        double mean = Business.calculateAverageSentiment(arr);
        double variance = Business.calculateSentimentVariance(arr, mean);

        // should be close enough
        assertTrue(Math.abs(variance - 2.0769231) < 0.001);
        assertEquals("2.08", Business.getSentimentVarianceString(variance));
    }
}
