/*
 * Copyright (c) 2011-2017 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.com.sofka;


import co.com.sofka.domain.User;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

public class Part07Errors {

	//Devuelve un <User> Mono que contenga User.SAUL cuando se produzca un error en la entrada Mono; de lo contrario, no cambie la entrada Mono.
	public Mono<User> betterCallSaulForBogusMono(Mono<User> error) {
		//onErrorReturn: simplemente emite un valor de reserva capturado cuando se observe un error del tipo
		// especificado en este Mono.

		return error.onErrorReturn(User.SAUL);
	}

	//Devuelve un Flux <User> que contenga User.SAUL y User.JESSE cuando ocurra un error en el Flux de entrada; de lo contrario, no cambie el Flux de entrada.
	public Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> error) {
		//onErrorResume: suscribe a un editor de respaldo devuelto cuando se produzca un error,
		// utilizando una función para elegir el respaldo según el error.
		var users = error.onErrorResume(IllegalStateException.class, ex ->Flux.just(User.SAUL, User.JESSE));

		users.subscribe(u -> System.out.println(u));

		return users;
	}

	//Implementa un método que capitalice a cada usuario del flujo entrante utilizando el
	// #capitalizeUser y emite un error que contiene un error GetOutOfHereException
	public Flux<User> capitalizeMany(Flux<User> just) {

		return just.map(u -> {
			try {
				return capitalizeUser(u);
			}catch (GetOutOfHereException e) {
				throw Exceptions.propagate(e);
			}
		});
	}

	User capitalizeUser(User user) throws GetOutOfHereException {
		if (user.equals(User.SAUL)) {
			throw new GetOutOfHereException();
		}
		return new User(user.getUsername(), user.getFirstname(), user.getLastname());
	}

	protected final class GetOutOfHereException extends Exception {
		private static final long serialVersionUID = 0L;
	}

}
