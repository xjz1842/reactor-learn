package com.reactor.learn;


import reactor.core.publisher.Mono;


public class MonoTest {

    public static void main(String[] args) {

        Mono.fromCallable(System::currentTimeMillis)
                .flatMap(time -> Mono.just(time+1))
                .doOnSuccess(r -> System.out.println("success"))
                .subscribe(System.out::println);

    }

}
