package com.truckpad.androidcase

import com.truckpad.androidcase.util.asReais
import com.truckpad.androidcase.util.format2Dec
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `Parse Double with 2 decimal cases to Reais format`() {
        val value = 5473.98
        val expected = "R$ 5473,98"

        val result = value.asReais()
        assertThat(result, equalTo(expected))
    }

    @Test
    fun `Parse Double with more than 2 decimal cases to Reais format`() {
        val value = 5473.9834
        val expected = "R$ 5473,98"

        val result = value.asReais()
        assertThat(result, equalTo(expected))
    }

    @Test
    fun `Format Double with more than 2 decimal cases to keep at max 2 dec cases`() {
        val value = 5473.984312
        val expected = "5473,98"

        val result = value.format2Dec()
        assertThat(result, equalTo(expected))
    }
}