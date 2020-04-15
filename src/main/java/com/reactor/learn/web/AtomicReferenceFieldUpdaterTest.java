package com.reactor.learn.web;

import com.reactor.learn.AtomicReferenceBean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterTest {

    static final AtomicIntegerFieldUpdater<AtomicReferenceBean> AtomicReferenceBean =
            AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceBean.class, "count");

    static AtomicReferenceFieldUpdater<AtomicReferenceBean, String> S =
            AtomicReferenceFieldUpdater.newUpdater(AtomicReferenceBean.class, String.class, "name");

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) {

        AtomicReferenceBean referenceBean = new AtomicReferenceBean();
        for (long i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(() -> {
                referenceBean.count1++;
                AtomicReferenceBean.getAndIncrement(referenceBean);
                System.out.println(S.getAndUpdate(referenceBean, (prev)->{return prev+1;}));
            });
        }

        System.out.println(S.getAndSet(referenceBean, "NAME"));
        System.out.println(S.getAndSet(referenceBean, "NEWNAME"));
        System.out.println(S.getAndSet(referenceBean, "END"));

        System.out.println(referenceBean.count);

        System.out.println(referenceBean.count1);
    }

}
