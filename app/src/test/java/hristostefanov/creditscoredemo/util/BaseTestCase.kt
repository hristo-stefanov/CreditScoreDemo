package hristostefanov.creditscoredemo.util

import org.mockito.MockitoAnnotations

open class BaseTestCase {
    private var autoCloseable: AutoCloseable? = null

    @org.junit.Before
    fun beforeEach() {
        autoCloseable = MockitoAnnotations.openMocks(this)
    }

    @org.junit.After
    fun afterEach() {
        autoCloseable?.close()
    }

}
