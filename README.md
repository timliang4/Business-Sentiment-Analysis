# Business Sentiment Analysis

>A RESTful API for performing sentiment analysis on recent business reviews, with a companion React app

## Motivation

The online ratings and reviews for businesses are often outdated and, as a result, inaccurate.

The Business Sentiment Analysis RESTful API performs sentiment analysis on the most recent reviews of any business supported by the [Google Places API](https://developers.google.com/maps/documentation/places/web-service/overview), providing users with up-to-date and accurate information about the business.

The API and app are not publicly deployed because the Google Places API is not free and neither is deploying apps.

### Example

The API supports CRUD operations. This example will go through each operation.

**CREATING:** Enter the place ID of the desired business and click "Search".

<img src="images/creating.png" width=360>

---

**READING:** After loading, the new entry should be posted to the database and loaded on the app.

<img src="images/reading.png" width=360>

---

**UPDATING:** Click the "Update" under the entry. The most recent reviews will be fetched and the sentiment and variance will be recalculated.

Alternatively, you could manually update the sentiment using the "Edit" button. Then click "Save" to post the modified data to the database.

<img src="images/editing.png" width=360>

---

**DELETING:** Click the "Delete" button and the entry will be deleted from the database.

## Support

Encounter an issue?

If you find a bug or have a feature request, please file an issue or email me at timliang4@gmail.com. Or, if you'd like to contribute, feel free to submit a pull request.
