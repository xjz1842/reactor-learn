package com.reactor.learn;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class SchedulersTest {


    public static void main(String[] args) {

        Mono.fromCallable( () -> System.currentTimeMillis() )
                .repeat()
                .publishOn(Schedulers.single())
                .log("foo.bar")
                .flatMap(time ->
                                Mono.fromCallable(() -> { Thread.sleep(1000); return time; })
                                        .subscribeOn(Schedulers.parallel())
                        , 8) //maxConcurrency 8
                .subscribe(System.out::println);

//        Mono.fromCallable( () -> System.currentTimeMillis() )
//                .repeat()
//                .parallel(8) //parallelism
//                .runOn(Schedulers.parallel())
//                .doOnNext( d -> System.out.println("I'm on thread "+d+Thread.currentThread()) )
//                .subscribe();

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
