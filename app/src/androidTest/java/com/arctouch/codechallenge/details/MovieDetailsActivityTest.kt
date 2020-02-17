package com.arctouch.codechallenge.details

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsActivityTest {

    private lateinit var activityScenario: ActivityScenario<MovieDetailsActivity>

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun whenTheActivityResumesShouldShowTheMovieDetails() {

        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("ID",1)
        activityScenario = ActivityScenario.launch(MovieDetailsActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        activityScenario.use { scenario ->
            scenario.moveToState(Lifecycle.State.RESUMED)    // Moves the activity state to State.RESUMED.
            Espresso.onView(ViewMatchers.withId(com.arctouch.codechallenge.R.id.tv_movie_title)).check(ViewAssertions.matches(ViewMatchers.withText(FakeData.movie.title)))
            Espresso.onView(ViewMatchers.withId(com.arctouch.codechallenge.R.id.tv_movie_overview)).check(ViewAssertions.matches(ViewMatchers.withText(FakeData.movie.overview)))
        }

    }
}