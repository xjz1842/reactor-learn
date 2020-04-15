package com.reactor.learn;

import reactor.core.publisher.Mono;

public class MonoTest {

    public static void main(String[] args) {

        Mono.fromCallable(()->"hello")
                .flatMap(time -> Mono.just(time+" world"))
                .doOnSuccess(r -> System.out.println("success"))
                .subscribe(System.out::println);

    }

}
