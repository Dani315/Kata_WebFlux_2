package co.com.sofka;

import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 *
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02Mono {

	//Return empty Mono
	public Mono<String> emptyMono() {
		//empty: Crea un Mono que se completa sin emitir ningún elemento
		return Mono.empty();
	}

	//Devuelve un Mono que nunca emite ninguna señal.
	public Mono<String> monoWithNoSignal() {
		//never: devuelve un Mono que nunca señalará ningún dato, error o señal de finalización
		//esencialmente ejecutandose indefinidamente
		return Mono.never();
	}

	//Devuelve un Mono que contiene un valor "foo"
	public Mono<String> fooMono() {
		//just: crea un nuevo Mono que emite el elemento especificado
		//que se captura en el momento de la instanciación
		//delayElement: retrasa este Mono elemento por una duración determinada
		return Mono.just("foo").delayElement(Duration.ofSeconds(3));
	}

	//Crea un Mono que emita una IllegalStateException
	public Mono<String> errorMono() {
		//error: Crea un Mono que termina con el error especificado inmediatamente despues de suscribirse
		return Mono.error(new IllegalStateException());
	}
}
