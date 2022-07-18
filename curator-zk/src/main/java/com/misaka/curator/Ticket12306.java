package com.misaka.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Ticket12306 implements Runnable {
    private int tickets = 10;

    InterProcessMutex mutex;

    public Ticket12306() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
        CuratorFramework build = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .build();

        build.start();
        mutex = new InterProcessMutex(build, "/lock");
    }

    @Override
    public void run() {
        while(true) {
            // get the lock first
            try {
                mutex.acquire(3, TimeUnit.SECONDS);

                if(tickets > 0) {
                    System.out.println(Thread.currentThread() + " 售票：" + tickets);
                    Thread.sleep(100);
                    tickets -- ;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // release the lock
                try {
                    mutex.release();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
