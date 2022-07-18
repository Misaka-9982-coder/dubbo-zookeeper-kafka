package com.misaka.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CuratorTest {

    CuratorFramework build;

    @Before
    public void testConnect() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
//
//        // first method
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
//                60 * 1000, 15 * 1000, retryPolicy);
//        curatorFramework.start();


        // second method
        build = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("misaka")
                .build();

        build.start();
    }

    @Test
    public void testCreate1() throws Exception {
        String path = build.create().forPath("/app1");
        System.out.println(path);
    }

    @Test
    public void testCreate2() throws Exception {
        String path = build.create().forPath("/app2", "hello".getBytes());
        System.out.println(path);
    }

    @Test
    public void testCreate3() throws Exception {
        String path = build.create().withMode(CreateMode.EPHEMERAL).forPath("/app3");
        System.out.println(path);
    }

    @Test
    public void testCreate4() throws Exception {
        String path = build.create().creatingParentContainersIfNeeded() .forPath("/app4/p1");
        System.out.println(path);
    }

    @After
    public void testClose() {
        if(build != null) {
            build.close();
        }
    }
}
