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

package net.ontopia.utils;

import net.ontopia.utils.LexicalComparator;

public class LexicalComparatorTest extends AbstractComparatorTest {

  public LexicalComparatorTest(String name) {
    super(name);
  }

  public void testIdentical() {
    testComparator(LexicalComparator.CASE_SENSITIVE.compare("FOOBAR", "FOOBAR"), 0, 1);
  }

  public void testCaseDifference() {
    testComparator(LexicalComparator.CASE_SENSITIVE.compare("FOOBAR", "FOoBAR"), 1, 0);
  }

  public void testSmallerThan() {
    testComparator(LexicalComparator.CASE_SENSITIVE.compare("FOOBAR", "FOOAR"), 1, 0);
  }

  public void testGreaterThan() {
    testComparator(LexicalComparator.CASE_SENSITIVE.compare("FOOAR", "FOOBAR"), -1, 0);
  }

  public void testIgnoreCase() {
    testComparator(LexicalComparator.CASE_INSENSITIVE.compare("FOOBAR", "FOoBAR"), 0, 1);
  }

  public void testGreaterThanIgnore() {
    testComparator(LexicalComparator.CASE_INSENSITIVE.compare("FOOaR", "FOOBAR"), -1, 0);
  }

  public void testSmallerThanIgnore() {
    testComparator(LexicalComparator.CASE_INSENSITIVE.compare("FOOBAR", "FOOBAR"), 0, 1);
  }

}




