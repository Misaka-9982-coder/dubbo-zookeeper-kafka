package com.misaka.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LockTest {
    public static void main(String[] args) {
        Ticket12306 ticket = new Ticket12306();

        Thread t1 = new Thread(ticket, "携程");
        Thread t2 = new Thread(ticket, "飞猪");

        t1.start();
        t2.start();
    }
}
