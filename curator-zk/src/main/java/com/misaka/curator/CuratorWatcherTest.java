package com.misaka.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CuratorWatcherTest {

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

    @After
    public void testClose() {
        if(build != null) {
            build.close();
        }
    }

    @Test
    public void testNodeCache() throws Exception {
        NodeCache nodeCache = new NodeCache(build, "/app1");

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("node has changed");

                byte[] data = nodeCache.getCurrentData().getData();
                String path = nodeCache.getCurrentData().getPath();
                System.out.println(path + ":" + new String(data));
            }
        });

        nodeCache.start(true);

        while(true) {
            Thread.sleep(1000);
        }
    }

    @Test
    public void testPathChildrenCache() throws Exception {
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(build, "/app2", true);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("pathChildrenCacheEvent:" + pathChildrenCacheEvent.getType());
                String data = new String(pathChildrenCacheEvent.getData().getData());
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED");

                        System.out.println("pathChildrenCacheEvent:"
                                + pathChildrenCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED");

                        System.out.println("pathChildrenCacheEvent:"
                                + pathChildrenCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED");

                        System.out.println("pathChildrenCacheEvent:"
                                + pathChildrenCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    default:
                        break;
                }
            }
        });

        pathChildrenCache.start();

        while(true) {
            Thread.sleep(1000);
        }
    }

    @Test
    public void testTreeCache() throws Exception {
        final TreeCache treeCache = new TreeCache(build, "/app2");

        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("treeCacheEvent:" + treeCacheEvent.getType());
                String data = new String(treeCacheEvent.getData().getData());
                switch (treeCacheEvent.getType()) {
                    case NODE_ADDED:
                        System.out.println("NODE_ADDED");

                        System.out.println("treeCacheEvent:"
                                + treeCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    case NODE_REMOVED:
                        System.out.println("NODE_REMOVED");

                        System.out.println("treeCacheEvent:"
                                + treeCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    case NODE_UPDATED:
                        System.out.println("NODE_UPDATED");

                        System.out.println("treeCacheEvent:"
                                + treeCacheEvent.getData().getPath() + " : "
                                + data);
                        break;
                    default:
                        break;
                }
            }
        });

        treeCache.start();

        while(true) {
            Thread.sleep(1000);
        }
    }
}
