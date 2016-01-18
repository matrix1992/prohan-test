/**
 * 
 */
package com.prohan.test;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * @author prohan
 *
 */
public class ZookeeperFactory {

	private ZooKeeper zooKeeper;

	public void connect(String host, int port) throws IOException {
		zooKeeper = new ZooKeeper(host, port, new Watcher() {
			public void process(WatchedEvent arg0) {
				System.out.println("Connected succesfully!!!");
			}
		});
	}

	public void closeConnection() throws InterruptedException {
		zooKeeper.close();
	}

	public ZooKeeper getZooKeeper() {
		if (zooKeeper == null || !zooKeeper.getState().equals(States.CONNECTED)) {
			throw new IllegalStateException("ZooKeeper is not connected.");
		}
		return zooKeeper;
	}

}
