package hristostefanov.creditscoredemo.core


object Config {
    // an alternative would be keeping it in shared preferences where end-to-end
    // tests can change it
    var SERVICE_BASE_URL = BuildConfig.SERVICE_BASE_URL
}