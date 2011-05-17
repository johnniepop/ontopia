
// $Id: ClusteredNamedLockManagerTest.java,v 1.2 2007/11/06 14:25:08 geir.gronmo Exp $

package net.ontopia.topicmaps.webed.utils;

import java.util.*;
import net.ontopia.utils.*;
import EDU.oswego.cs.dl.util.concurrent.SynchronizedInt;
import net.ontopia.topicmaps.webed.impl.utils.*;
import net.ontopia.utils.ontojsp.FakeHttpSession;
import net.ontopia.utils.ontojsp.FakeServletContext;
import net.ontopia.topicmaps.webed.impl.utils.SessionListener;
import net.ontopia.topicmaps.nav2.core.UserIF;
import net.ontopia.topicmaps.nav2.impl.basic.NavigatorConfiguration;
import net.ontopia.topicmaps.nav2.impl.framework.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Ignore;

/**
 * INTERNAL: Test class for testing distributed NamedLockManager locks.
 *
 * Instructions:<br>
 *
 * build: ant dist.jar.oks.enterprise.test
 * distribute: ant -Dhostname=oks01 distribute.cluster; ant -Dhostname=oks02 distribute.cluster
 * start terracotta (also failover): cd ~/terracotta/bin; ./start-tc-server.sh -f /home/oks/oks-terracotta.xml
 * run master: java $TC_OPTS net.ontopia.topicmaps.webed.utils.test.ClusteredNamedLockManagerTest true
 * run client: java $TC_OPTS net.ontopia.topicmaps.webed.utils.test.ClusteredNamedLockManagerTest false
 * 
 */  
@Ignore
public class ClusteredNamedLockManagerTest {

  // Define a logging category.
  static Logger log = LoggerFactory.getLogger(ClusteredNamedLockManagerTest.class.getName());

  private boolean master;
  private int participantCount;

  private SynchronizedInt lock;

  public ClusteredNamedLockManagerTest(boolean master, int participantCount) {
    this.master = master;
    this.participantCount = participantCount;
    this.lock = new SynchronizedInt(0);
  }
  
  public void setUp() {
  }

  public void tearDown() {
  }

  public void run() {
    try {

      FakeHttpSession session = new FakeHttpSession(new FakeServletContext());
      session.addSessionListener(new SessionListener());

      NamedLockManager lockMan = TagUtils.getNamedLockManager(session.getServletContext());
      NavigatorConfiguration navConf = new NavigatorConfiguration();
  
      if (master)
        masterTest(session, lockMan, navConf);
      else
        slaveTest(session, lockMan, navConf);

      System.out.println("Success.");
      
    } catch (Exception e) {
      throw new OntopiaRuntimeException(e);
    }
  }

  private void await(int state) throws InterruptedException {
    System.out.println("Waiting for state " + state + " (now " + lock.get() + ")");
    while (true) {
      int val = lock.get();
      if (val == state)
        break;
      Thread.sleep(1000);
    }    
  }
  
  private void masterTest(FakeHttpSession session, NamedLockManager lockMan, NavigatorConfiguration navConf) throws InterruptedException {
    // master sets counter to 0
    lock.set(0);
    
    ResourceBundle resBundle = null;
    UserIF user1 = new User("user1", new NavigatorConfiguration());
    Collection objs = new HashSet();
    objs.add("first");
    objs.add("second");
    
    String lockName1 = "lock1";
    
    // User 1 locks all the objects.
    LockResult res1 = lockMan.attemptToLock(user1, objs, lockName1, session,
                                            resBundle);
    assertNoUnlockables(res1);
    System.out.println("OK: m1");

    lock.increment();    
    await(2);
    
    // User 1 attempts to lock the objects, but fails.
    assertEquals(objs, lockMan.attemptToLock(user1, objs, lockName1, session,
                                             resBundle)
        .getUnlockable());
    System.out.println("OK: m2");
    
    // User 1 unlocks the objects and then locks them
    lockMan.unlock(user1, lockName1, true);
    LockResult res3 = lockMan.attemptToLock(user1, objs, lockName1, session,
                                            resBundle);
    assertNoUnlockables(res3);
    System.out.println("OK: m3");

    lock.increment();
    
  }
  
  private void slaveTest(FakeHttpSession session, NamedLockManager lockMan, NavigatorConfiguration navConf) throws InterruptedException {
    ResourceBundle resBundle = null;
    UserIF user2 = new User("user2", navConf);
    Collection objs = new HashSet();
    objs.add("first");
    objs.add("second");
    
    String lockName1 = "lock1";

    await(1);
    
    // User 2 attempts to lock the objects, but fails.
    assertEquals(objs, lockMan.attemptToLock(user2, objs, lockName1, session, 
                                             resBundle)
        .getUnlockable());
    System.out.println("OK: s1");
    
    // User 2 unlocks the objects and then locks them
    lockMan.unlock(user2, lockName1, true);
    LockResult res2 = lockMan.attemptToLock(user2, objs, lockName1, session,
                                            resBundle);
    assertNoUnlockables(res2);
    System.out.println("OK: s2");

    lock.increment();    
    await(3);
    
    // User 2 attempts to lock the objects twice, but fails.
    assertEquals(objs, lockMan.attemptToLock(user2, objs, lockName1, session,
                                             resBundle)
        .getUnlockable());
    System.out.println("OK: s3");
    assertEquals(objs, lockMan.attemptToLock(user2, objs, lockName1, session,
                                             resBundle)
        .getUnlockable());
    System.out.println("OK: s4");

    lockMan.unlock(user2, lockName1, true);

    lock.increment();    
  }

  protected void assertEquals(Object o1, Object o2) {
    if (!o1.equals(o2))
      throw new OntopiaRuntimeException("Object " + o1 + " is not equal " + o2);
  }
  
  protected void assertNoUnlockables(LockResult result) {
    assertNoUnlockables(result.getUnlockable());
  }

  protected void assertNoUnlockables(Collection coll) {
    if (!coll.isEmpty())
      throw new OntopiaRuntimeException("There were objects that could not be locked: " + coll);
  }
  
  // -----------------------------------------------------------------------------
  // Main
  // -----------------------------------------------------------------------------

  public static void main(String[] args) throws Exception {

    // initialize logging
    CmdlineUtils.initializeLogging();
      
    // register logging options
    CmdlineOptions options = new CmdlineOptions("ClusteredNamedLockManagerTest", args);
    CmdlineUtils.registerLoggingOptions(options);

    boolean master = Boolean.valueOf(args[0]).booleanValue();
    int participants = 2;
    
    ClusteredNamedLockManagerTest tester = new ClusteredNamedLockManagerTest(master, participants);
    try {
      tester.setUp();
      tester.run();    
    } finally {
      tester.tearDown();
    }
  }
  
}