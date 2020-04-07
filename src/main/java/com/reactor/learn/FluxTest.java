package com.reactor.learn;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FluxTest {

    public static void main(String[] args) {

        Flux.fromIterable(getSomeLongList())
                .mergeWith(Flux.interval(Duration.ofMillis(100)))
                .map(d -> d * 2)
                .take(3)
                .subscribe(System.out::println);

        Flux.create(sink -> {
                    sink.next("3");
                },
                // Overflow (backpressure) handling, default is BUFFER
                FluxSink.OverflowStrategy.BUFFER)
                .timeout(Duration.ofMillis(300))
                .doOnComplete(() -> System.out.println("completed!"))
                .subscribe(System.out::println);
    }

    private static List<Long> getSomeLongList() {
        List<Long> someLongList = new ArrayList<>();
        someLongList.add(1L);
        someLongList.add(2L);
        someLongList.add(3L);
        someLongList.add(4L);
        return someLongList;
    }


}
