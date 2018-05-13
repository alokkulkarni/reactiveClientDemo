package com.example.client.reactiveClientDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ReactiveClientDemoApplication {

	@Bean
	WebClient webClient() {
		return WebClient.builder().build();
	}
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ReactiveClientDemoApplication.class, args);
		final Flux<ServerSentEvent> stream = WebClient
				.create("http://localhost:8080")
				.get().uri("/transaction")
				.retrieve()
				.bodyToFlux(ServerSentEvent.class);

		stream.subscribe(sse -> System.out.println("Received: {}" +  sse));

		TimeUnit.MINUTES.sleep(10);
	}
}


@RestController
@RequestMapping("/client")
class ClientController {

	private WebClient webClient;

	ClientController( WebClient webClient ) {
		this.webClient = webClient;
	}

//	@GetMapping(value = "/{id}")
//	Mono<String> getCustomerData(@PathVariable("id") String id) {
//
//		return this.webClient.get().uri( uriBuilder -> URI.create( "http://localhost:8080/customers/id" ) ).header( "id", id ).retrieve().bodyToMono( String.class );
//	}

//	@GetMapping(value = "/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//	Flux<ServerSentEvent> getCustomerData() {
//
//		return this.webClient.get().uri( uriBuilder -> URI.create( "http://localhost:8080/transaction" ) ).retrieve().bodyToFlux( ServerSentEvent.class );
//	}


}