package com.raul.androidapps.softwaretestrevolut

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raul.androidapps.softwaretestrevolut.ui.selector.SelectorFragment
import com.raul.androidapps.softwaretestrevolut.ui.selector.SelectorFragmentDirections
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class NavigationTest {


    @Test
    fun testNavigationToConversionUsingCoroutines() {
        // Create a mock NavController
        val mockNavController = mock(NavController::class.java)

        // Create a graphical FragmentScenario for the TitleScreen
        val selectorScenario = launchFragmentInContainer<SelectorFragment>(null, R.style.AppTheme)

        // Set the NavController property on the fragment
        Scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        // Verify that performing a click prompts the correct Navigation action
        onView(ViewMatchers.withId(R.id.coroutines_button)).perform(ViewActions.click())
        Mockito.verify(mockNavController)
            .navigate(SelectorFragmentDirections.actionSelectorFragmentToConversionFragment(true))
    }

    @Test
    fun testNavigationToConversionUsingRxJava() {
        // Create a mock NavController
        val mockNavController = mock(NavController::class.java)

        // Create a graphical FragmentScenario for the TitleScreen
        val selectorScenario = launchFragmentInContainer<SelectorFragment>(null, R.style.AppTheme)

        // Set the NavController property on the fragment
        selectorScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        // Verify that performing a click prompts the correct Navigation action
        onView(ViewMatchers.withId(R.id.rx_java_button)).perform(ViewActions.click())
        Mockito.verify(mockNavController)
            .navigate(SelectorFragmentDirections.actionSelectorFragmentToConversionFragment(false))
    }
}
