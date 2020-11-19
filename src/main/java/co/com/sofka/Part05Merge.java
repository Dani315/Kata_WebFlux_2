package co.com.sofka;


import co.com.sofka.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to merge flux.
 *
 */
public class Part05Merge {

	//Fusiona valores de flux1 y flux2 con intercalación
	public Flux<User> mergeFluxWithInterleave(Flux<User> all, Flux<User> all1) {
		//merge: fusiona los elementos emitidos de ambos flujos, pero de forma intercalada(Multiple)
		//mergeWidth: fusiona los elementos emitidos de ambos flujos, pero de forma intercalada(Por referencia)

		var merge = all.mergeWith(all1);

		merge.subscribe(user -> System.out.println(user));

		return merge;
	}

	//Fusiona valores de flux1 y flux2 sin intercalación (valores de flujo1 y luego valores de flujo2)
	public Flux<User> mergeFluxWithNoInterleave(Flux<User> all, Flux<User> all1) {
		//concat: el primer flujo se termina y luego el otro flujo se concatena con el(Multiple)
		//concatWidth: el primer flujo se termina y luego el otro flujo se concatena con el(Por referencia)

		var concat = all.concatWith(all1);

		concat.subscribe(user -> System.out.println(user));

		return concat;
	}

	//Crea un flujo que contenga el valor de mono1 y luego el valor de mono2
	public Flux<User> createFluxFromMultipleMono(Mono<User> skylerMono, Mono<User> marieMono) {
		//mergeSecuencial: fusiona datos de Publisher en una secuencia combinada ordenada.
		var merge = Flux.mergeSequential(skylerMono, marieMono);

		merge.subscribe(user -> System.out.println(user));

		return merge;
	}
}
