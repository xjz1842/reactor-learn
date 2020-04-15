package com.reactor.learn;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FluxTest {

    public static void main(String[] args) {

//        Flux.fromIterable(getSomeLongList())
//                .map(d -> d * 2)
//                .take(3)
//                .subscribe(System.out::println);

        Flux.create(sink -> {
                    for (int i = 0; i < 10; i++)
                        sink.next(i);
                },
                // Overflow (backpressure) handling, default is BUFFER
                FluxSink.OverflowStrategy.BUFFER)
//                .timeout(Duration.ofMillis(300))
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
