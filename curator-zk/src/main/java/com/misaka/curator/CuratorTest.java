package com.misaka.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class CuratorTest {

    @Test
    public void testConnect() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);

        // first method
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
                60 * 1000, 15 * 1000, retryPolicy);
        curatorFramework.start();


        // second method
        CuratorFramework build = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("misaka")
                .build();

        build.start();
    }
}
