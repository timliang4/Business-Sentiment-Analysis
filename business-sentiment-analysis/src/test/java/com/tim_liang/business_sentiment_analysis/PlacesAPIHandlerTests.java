package com.tim_liang.business_sentiment_analysis;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlacesAPIHandlerTests {

    @Test
    void testConstrustor() {
        PlacesAPIHandler pah = new PlacesAPIHandler();
        assertEquals(new ArrayList<String>(), pah.reviews);
        assertEquals("", pah.name);
    }

    // invalid place ids should do nothing

    @Test
    void testNullPlaceID() {
        PlacesAPIHandler pah = new PlacesAPIHandler();
        pah.getAPIData(null);
        assertEquals(new ArrayList<String>(), pah.reviews);
        assertEquals("", pah.name);
    }

    @Test
    void testInvalidPlaceID() {
        PlacesAPIHandler pah = new PlacesAPIHandler();
        pah.getAPIData("1");
        assertEquals(new ArrayList<String>(), pah.reviews);
        assertEquals("", pah.name);
    }

}
