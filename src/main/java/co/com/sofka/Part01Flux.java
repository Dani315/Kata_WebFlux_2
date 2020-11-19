package co.com.sofka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 *
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01Flux {

	// Return empty Flux
	public Flux<String> emptyFlux() {
		//empty: crea un flux que se completa sin emitir ningún elemento
		return Flux.empty();
	}

	//Devuelve un Flux que contiene 2 valores "foo" y "bar" sin usar una matriz o una colección
	public Flux<String> fooBarFluxFromValues() {
		//just: crea un Flux que emitirá solo un elemento
		//merge: se encarga funcionar ambos tipos mono
		//delayElement: retrasa el elemento del Flux por una determinada duración
		Mono<String> foo = Mono.just("foo").delayElement(Duration.ofSeconds(3));
		Mono<String> bar = Mono.just("bar").delayElement(Duration.ofSeconds(3));

		return Flux.merge(foo,bar);
	}

	//Creacion de un flujo a partir de una lista que contenga 2 valores "foo" y "bar"
	public Flux<String> fooBarFluxFromList() {
		//fromStream: crea un Flux que emite los elementos contenidos en el Stream
		//stream: los streams son una secuencia de elementos que soportan operaciones de agregación secuencial y paralela
		//delayElements: retrasa cada uno de los elementos del Flux por una determinada duración
		var valores = List.of(
				"foo",
				"bar"
		);
		return  Flux.fromStream(valores.stream()).delayElements(Duration.ofSeconds(3));
	}

	//Creación de un flujo que emite una IllegalStateException
	public Flux<String> errorFlux() {
		//error: Crea un Flux que termina con el error especificado inmediata/ despues de suscribirse
		return Flux.error(new IllegalStateException());
	}

	//Creación de un flujo que emite valores crecientes de 0 a 9 cada 100 ms
	public Flux<Long> counter() {
		//interval: este método crea un nuevo Flux que emite incrementos Long comenzando con 0 cada periodo de tiempo
		//take: toma solo los primeros N valores de este Flux, si estan disponibles
		var flujo = Flux.interval(Duration.ofMillis(100)).take(10);
		return flujo;
	}
}
