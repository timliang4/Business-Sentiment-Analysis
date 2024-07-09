package com.tim_liang.business_sentiment_analysis;

import org.springframework.data.annotation.Id;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

public class Business {
    @Id
    private String id;

    private String name;
    private String averageSentiment;
    private String sentimentVariance;

    public Business(String id) {
        this.id = id;
        this.name = "";
        this.averageSentiment = "";
        this.sentimentVariance = "";
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAverageSentiment(String avgSent) {
        this.averageSentiment = avgSent;
    }

    public String getAverageSentiment() {
        return this.averageSentiment;
    }

    public void setSentimentVariance(String sentVar) {
        this.sentimentVariance = sentVar;
    }

    public String getSentimentVariance() {
        return this.sentimentVariance;
    }

    public boolean findSentimentAndName() {
        PlacesAPIHandler pah = Business.fetchAPIData(this.id);
        if (pah.name.isEmpty() && pah.reviews.isEmpty()) {
            return false;
        }
        this.name = pah.name;
        List<Integer> reviews = Business.getSentimentScores(pah.reviews);
        double mean = Business.calculateAverageSentiment(reviews);
        this.setAverageSentiment(Business.getAverageSentimentString(mean));
        this.setSentimentVariance(
                Business.getSentimentVarianceString(Business.calculateSentimentVariance(reviews, mean)));
        return true;
    }

    @Override
    public String toString() {
        return String.format("Business(placeID=%s, name='%s', averageSentiment=%s, sentimentVariance=%s)", this.id,
                this.name, this.averageSentiment, this.sentimentVariance);
    }

    public static PlacesAPIHandler fetchAPIData(String placeID) {
        PlacesAPIHandler pah = new PlacesAPIHandler();
        pah.getAPIData(placeID);
        return pah;
    }

    public static List<Integer> getSentimentScores(List<String> reviewsText) {
        List<Integer> result = new ArrayList<>();
        List<String> reviews = reviewsText;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        for (int i = 0; i < reviews.size(); i++) {
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            Annotation annotation = pipeline.process(reviews.get(i));
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                result.add(RNNCoreAnnotations.getPredictedClass(tree));
            }
        }
        return result;
    }

    public static double calculateAverageSentiment(List<Integer> lst) {
        double sum = 0;
        for (int i = 0; i < lst.size(); i++) {
            sum += lst.get(i);
        }
        return sum / lst.size();
    }

    public static double calculateSentimentVariance(List<Integer> lst, double mean) {
        double variance = 0;
        for (int i = 0; i < lst.size(); i++) {
            variance += Math.pow((double) lst.get(i) - mean, 2.0);
        }
        return variance / ((double) lst.size() - 1);
    }

    public static String getAverageSentimentString(double meanSentiment) {
        switch ((int) Math.round(meanSentiment)) {
            case 0:
                return "very negative";
            case 1:
                return "negative";
            case 2:
                return "neutral";
            case 3:
                return "positive";
            case 4:
                return "very positive";
        }
        return "";
    }

    public static String getSentimentVarianceString(double sentimentVar) {
        return String.format("%.2f", sentimentVar);
    }

}
