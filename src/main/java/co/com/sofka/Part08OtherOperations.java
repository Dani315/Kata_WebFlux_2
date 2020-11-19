package co.com.sofka;

import co.com.sofka.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Part08OtherOperations {
    Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux, Flux<String> firstnameFlux, Flux<String> lastnameFlux) {
        //uno diferentes flujos, me mapea
        return Flux.zip(usernameFlux, firstnameFlux, lastnameFlux).map(u -> new User(u.getT1(), u.getT2(), u.getT3()));
    }


    //Return the mono which returns its value faster
    Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {
        //Me retorna el elemento más rápido
        return Mono.first(mono1, mono2);
    }


    //Return the flux which returns its values faster
    Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
        //Me retorna el flujo más rápido
        return Flux.first(flux1, flux2);
    }



    Mono<Void> fluxCompletion(Flux<User> flux) {
        /*return flux.doOnNext((user) -> System.out.println(user)).map(u -> {
            System.out.println(u);
           return  new User("", "", "");
        }).then();*/
        return flux.then();
    }



    Mono<User> nullAwareUserToMono(User user) {
        //Si el objeto esta nulo retorno un Mono vacio, si no retorno el valor - control de valores nulos
        return Mono.justOrEmpty(user);
    }



    Mono<User> emptyToSkyler(Mono<User> mono) {
        //Si el mono está vacio, retorno un valor por defecto
        return mono.defaultIfEmpty(User.SKYLER);
    }



    Mono<List<User>> fluxCollection(Flux<User> flux) {
        //Me transforma un flujo a una lista
        //return flux.flatMap(u -> Mono.just(u)).collect(Collectors.toList());
        return flux.collectList() ;
    }
}
