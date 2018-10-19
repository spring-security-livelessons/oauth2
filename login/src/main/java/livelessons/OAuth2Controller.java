package livelessons;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class OAuth2Controller {

	private final WebClient webClient;

	public OAuth2Controller(WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/message")
	Mono<String> message() {
		return this.webClient.get().uri("http://localhost:9090/").retrieve()
				.bodyToMono(String.class);
	}

	@GetMapping("/message/client-id")
	Mono<String> messageClientId() {
		return this.webClient.get().uri("http://localhost:9090/")
				.attributes(clientRegistrationId("keycloak")).retrieve()
				.bodyToMono(String.class);
	}

	// resolving via annotation

	@GetMapping("/message/annotation")
	Mono<String> message(
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		return this.webClient.get().uri("http://localhost:9090/")
				.attributes(oauth2AuthorizedClient(authorizedClient)).retrieve()
				.bodyToMono(String.class);
	}

	@GetMapping("/message/annotation/client-id")
	Mono<String> messageClientId(
			@RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient) {
		return this.webClient.get().uri("http://localhost:9090/")
				.attributes(oauth2AuthorizedClient(authorizedClient)).retrieve()
				.bodyToMono(String.class);
	}

}
