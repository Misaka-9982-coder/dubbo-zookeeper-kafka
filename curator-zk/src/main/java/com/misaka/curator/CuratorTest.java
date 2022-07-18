package com.misaka.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testGet1() throws Exception {
        byte[] bytes = build.getData().forPath("/app1");
        System.out.println(new String(bytes));
    }

    @Test
    public void testGet2() throws Exception {
        List<String> strings = build.getChildren().forPath("/app4");
        System.out.println(strings);
    }

    @Test
    public void testGet3() throws Exception {
        Stat stat = new Stat();
        build.getData().storingStatIn(stat).forPath("/app1");
        System.out.println(stat);
    }

    @Test
    public void testModify1() throws Exception {
        byte[] bytes = build.getData().forPath("/app1");
        System.out.println(new String(bytes));
        build.setData().forPath("/app1", "misaka".getBytes());
        bytes = build.getData().forPath("/app1");
        System.out.println(new String(bytes));
    }

    @Test
    public void testModifyByVersion() throws Exception {
        Stat stat = new Stat();
        build.getData().storingStatIn(stat).forPath("/app1");
        int version = stat.getVersion();

        build.setData().withVersion(version).forPath("/app1", "misaka".getBytes());
    }

    @Test
    public void testDelete1() throws Exception {
        build.delete().forPath("/app1");
    }

    @Test
    public void testDelete2() throws Exception {
        build.delete().deletingChildrenIfNeeded().forPath("/app4");
    }

    @Test
    public void testDelete3() throws Exception {
        build.delete().guaranteed().forPath("/app2");
    }

    @Test
    public void testDelete4 () throws Exception {
        build.delete().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println(curatorFramework);
                System.out.println(curatorEvent);
            }
        }).forPath("/app1");
    }

    @After
    public void testClose() {
        if(build != null) {
            build.close();
        }
    }
}
