package sample.adt

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.net.MalformedURLException
import java.net.URL

class ResultTypeTest {
    @Test
    fun `parse a url`() {
        val urlResult: Result<URL> = parseUrl("htt://somewrongurl")
        assertThat(urlResult.getOrNull()).isNull()
        assertThat(urlResult.getOrDefault(URL("http://somedefault"))).isEqualTo(URL("http://somedefault"))
        assertThat(urlResult.isFailure).isTrue()
        assertThat(urlResult.isSuccess).isFalse()

        assertThatThrownBy { urlResult.getOrThrow() }.isInstanceOf(MalformedURLException::class.java)
    }

    @Test
    fun `get a host`() {
        val urlResult: Result<URL> = parseUrl("http://someurl")
        val hostResult: Result<String> = urlResult.map { url -> url.host }
        assertThat(hostResult.getOrNull()).isEqualTo("someurl")
    }

    @Test
    fun `get from a url`() {
        val urlResult: Result<URL> = parseUrl("http://someurl")
        val getResult: Result<String> = urlResult.mapCatching { url -> getFromARemoteUrl(url).getOrThrow() }
        assertThat(getResult.getOrThrow()).isEqualTo("a result")
    }

    @Test
    fun `get from a url with failure`() {
        val urlResult: Result<URL> = parseUrl("http://someurl")
        val getResult: Result<String> = urlResult.mapCatching { url -> throw RuntimeException("something failed!") }
        assertThat(getResult.isFailure).isTrue()
    }

    @Test
    fun `get from a url with failure and flatMap`() {
        val urlResult: Result<URL> = parseUrl("http://someurl")
        val getResult: Result<String> = urlResult.flatMap { url -> getFromARemoteUrl(url) }
        assertThat(getResult.getOrThrow()).isEqualTo("a result")
    }

    @Test
    fun `get host`() {
        val urlResult: Result<URL> = parseUrl("http://myhost")
        assertThat(urlResult.getOrThrow().host).isEqualTo("myhost")
    }

    private fun <T, R> Result<T>.flatMap(block: (T) -> (Result<R>)): Result<R> {
        return this.mapCatching {
            block(it).getOrThrow()
        }
    }

    private fun getFromARemoteUrl(url: URL): Result<String> {
        return kotlin.runCatching { "a result" }
    }

    private fun parseUrl(url: String): Result<URL> =
        kotlin.runCatching { URL(url) }
}
