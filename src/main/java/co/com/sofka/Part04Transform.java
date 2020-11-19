package co.com.sofka;


import co.com.sofka.domain.User;
import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Part04Transform {

    //Escribe con mayúscula el nombre de usuario, el nombre y el apellido del usuario
    Mono<User> capitalizeOne(Mono<User> mono) {
        //flatMap: transforma el elemento emitido por este Mono de forma asincrónica,
        // devolviendo el valor emitido por otro Mono.
        //subscribe: este método cuando solo me recibe el consumidor, me permite realizar alguna acción
        //con cada valor producido, en este caso me permite tarer el valor producido (User) del mono, para mostrar sus
        //propiedades.

       var newUser = mono.flatMap(user -> asyncCapitalizeUser(user));

       newUser.subscribe(user -> System.out.println(user));

        return newUser;
    }

    //Escribe con mayúscula el nombre de usuario, el nombre y el apellido de los usuarios
    Flux<User> capitalizeMany(Flux<User> flux) {
        //map: Transforma los elementos emitidos por este Flux aplicando una función síncrona a cada elemento

        Flux<User> newUsers = flux.map(userFlux -> new User(
                userFlux.getUsername().toUpperCase(),
                userFlux.getFirstname().toUpperCase(),
                userFlux.getLastname().toUpperCase()));

        newUsers.subscribe(user -> System.out.println(user));

        return newUsers ;
    }

    //Escribe en mayúscula el nombre de usuario, el nombre y el apellido de los usuarios con #asyncCapitalizeUser
    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        //El método flatMap me transforma los elementos emitidos por el flux, y luego aplana estos elementos en un
        //solo flux mediante la combinación
        //El método asyncCapitalizeUser(user) me transfroma cada elemento del Flux en Mono<User>.

        var newUsers = flux.flatMap(user -> asyncCapitalizeUser(user));

        newUsers.subscribe(user -> System.out.println(user));

        return newUsers;
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
    }
}
