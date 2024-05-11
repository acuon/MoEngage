package com.acuon.moengage.common

object Constants {
    const val URL =
        "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

    object Arguments {
        const val ARTICLE_URL = "article_url"
        const val ARTICLE_TITLE = "article_title"
    }

    object DateFormats {
        const val SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val APP_DATE_FORMAT = "MM/dd/yyyy"
    }

    object Notification {
        const val CHANNEL_NAME = "mo_engage_channel"
        const val CHANNEL_ID = "mo_engage_channel_id"
    }
}

