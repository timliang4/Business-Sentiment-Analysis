package com.tim_liang.business_sentiment_analysis;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

public class BasicPipelineExample {

    public static String text = "Terrible service. all reviews are fake and irrelevant. This is about reviewing google as business not the building/staff etc.";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // annnotate the document
        Annotation annotation = pipeline.process(text);
        List<Integer> scores = new ArrayList<>();
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            scores.add(RNNCoreAnnotations.getPredictedClass(tree));
        }
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(scores.get(i));
        }
    }
}