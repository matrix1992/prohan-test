/**
 * 
 */
package com.prohan.test;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author prohan
 *
 */
public class ZookeeperTest {

	private ZookeeperFactory zookeeperFactory;

	// @Before
	// public void setup() throws IOException {
	// zookeeperFactory = new ZookeeperFactory();
	// zookeeperFactory.connect("localhost", 2181);
	// }

	// api - https://zookeeper.apache.org/doc/r3.2.2/api/index.html
	// https://ihong5.wordpress.com/tag/zookeeper/

	@Test
	public void zkTest() throws KeeperException, InterruptedException, IOException {
		zookeeperFactory = new ZookeeperFactory();
		zookeeperFactory.connect("localhost", 2181);
		ZooKeeper zk = zookeeperFactory.getZooKeeper();
		zk.create("/newZkNode", "just another node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		byte[] b = zk.getData("/newZkNode", false, null);
		Assert.assertEquals("just another node", new String(b));
		Stat znodeStat = zk.exists("/newZkNode", new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println("Called on event on newZkNode");
			}

		});
		zk.setData("/newZkNode", "a different data".getBytes(), znodeStat.getVersion());
		b = zk.getData("/newZkNode", false, null);
		Assert.assertFalse("just another node".equals(new String(b)));
		znodeStat = zk.exists("/newZkNode", new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println("Called again on event on newZkNode");
			}

		});
		zk.delete("/newZkNode", znodeStat.getVersion());
	}

	@After
	public void tearApart() throws InterruptedException {
		zookeeperFactory.closeConnection();
	}

}
