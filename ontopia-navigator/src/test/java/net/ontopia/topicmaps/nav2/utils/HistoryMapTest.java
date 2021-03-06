/*
 * #!
 * Ontopia Engine
 * #-
 * Copyright (C) 2001 - 2013 The Ontopia Project
 * #-
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * !#
 */

package net.ontopia.topicmaps.nav2.utils;

import junit.framework.TestCase;

public class HistoryMapTest extends TestCase {

  private static final String s1 = "String1";
  private static final String s2 = "zwo";
  private static final String s3 = "Nummer tres";
  private static final String s4 = "viere";
  private static final String s5 = "zwo";

  public HistoryMapTest(String name) {
    super(name);
  }
  
  protected HistoryMap makeHistoryMap() {
    HistoryMap hm = new HistoryMap(3, true);
    hm.add(s1);
    hm.add(s2);
    hm.add(s3);
    return hm;
  }
  
  public void testAdd() {
    HistoryMap hm = makeHistoryMap();
    assertTrue("Expected other HistoryMap result, got " + hm,
               (hm.size() == 3) &&
               hm.containsValue(s1) && hm.containsValue(s2) && hm.containsValue(s3));
    hm.add(s4);
    assertTrue("First should be gone, but got" + hm,
               (hm.size() == 3) &&
               hm.containsValue(s2) && hm.containsValue(s3) && hm.containsValue(s4));
  }
  
  public void testGet() {
    HistoryMap hm = makeHistoryMap();
    assertTrue("1-Expected to get second element, but got " + hm.getEntry(2),
               (hm.size() == 3) && hm.getEntry(2).equals(s2));
    hm.add(s4);
    assertTrue("2-Expected to get second element, but got " + hm.getEntry(2),
               (hm.size() == 3) && hm.getEntry(2).equals(s3));
  }

  public void testSuppressDuplicates() {
    HistoryMap hm = makeHistoryMap();
    assertTrue("Expected to get second element, but got " + hm.getEntry(2),
               (hm.size() == 3) && hm.getEntry(2).equals(s2));
    hm.add(s5);
    assertTrue("Duplicate suppression did not work, got  " + hm.getEntry(2),
               (hm.size() == 3) && hm.getEntry(2).equals(s2));
  }
  
}




