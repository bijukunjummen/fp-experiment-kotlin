package sample.adt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class TryTypeTest {
    @Test
    fun `parse a url`() {
        val urlResult: Try<URL> = parseUrl("htt://somewrongurl")
        assertThat(urlResult.isFailure()).isTrue()
        assertThat(urlResult.isSuccess()).isFalse()
    }

    @Test
    fun `get from a url`() {
        val urlResult: Try<URL> = parseUrl("http://someurl")
        val getResult: Try<String> = getFromARemoteUrl(urlResult.get())
        assertThat(getResult.get()).isEqualTo("a result")
    }

    @Test
    fun `get host`() {
        val urlResult: Try<URL> = parseUrl("http://myhost")
        assertThat(urlResult.get().host).isEqualTo("myhost")
    }

    @Test
    fun `get host with success`() {
        val urlResult: Try<URL> = parseUrl("http://myhost")
        val hostResult: Try<String> = urlResult.map { url -> url.host }
        assertThat(hostResult).isEqualTo(Try.success("myhost"))
    }

    @Test
    fun `map failure`() {
        val urlResult: Try<URL> = parseUrl("http://myhost")
        val hostResult: Try<String> = urlResult.map { throw RuntimeException("something failed") }
        assertThat(hostResult.isSuccess()).isEqualTo(false)
    }

    @Test
    fun `get host with failure`() {
        val urlResult: Try<URL> = parseUrl("htt://myhost")
        val hostResult: Try<String> = urlResult.map { url -> url.host }
        assertThat(hostResult.isFailure()).isTrue()
    }

    @Test
    fun `get content with flatMap`() {
        val urlResult: Try<URL> = parseUrl("http://someurl")
        val getResult: Try<String> = urlResult.flatMap { url -> getFromARemoteUrl(url) }
        assertThat(getResult).isEqualTo(Try.success("a result"))
    }

    @Test
    fun `get content with flatMap and when`() {
        val urlResult: Try<URL> = parseUrl("http://someurl")
        val getResult: Try<String> = urlResult.flatMap { url -> getFromARemoteUrl(url) }
        when (getResult) {
            is Try.Success -> {
                val (s) = getResult
                println("Got a clean result: $s")
            }
            is Try.Failure -> {
                val (e) = getResult
                println("An exception: $e")
            }
        }
    }

    @Test
    fun `get content with flatMap with error`() {
        val urlResult: Try<URL> = parseUrl("http://someurl")
        val getResult: Try<String> = urlResult.flatMap { throw RuntimeException("Something failed!!") }
        assertThat(getResult.isFailure()).isTrue()
    }

    fun getFromARemoteUrl(url: URL): Try<String> {
        return Try.success("a result")
    }

    fun parseUrl(url: String): Try<URL> {
        return Try.of {
            URL(url)
        }
    }

    fun get(endpoint: URI): Try<String> {
        val httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(30))
            .build()
        val httpRequestBuilder = HttpRequest.newBuilder(endpoint).GET()
        return Try.of {
            val response: HttpResponse<String> =
                httpClient.send(httpRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
            response.body()
        }
    }
}