/*
 * #!
 * Ontopia Navigator
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

package net.ontopia.topicmaps.nav.utils.comparators;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import net.ontopia.topicmaps.core.NameIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.utils.GrabberIF;
import net.ontopia.utils.StringifierIF;

import net.ontopia.topicmaps.nav.utils.stringifiers.ComparatorNameStringifier;

/**
 * INTERNAL: A Comparator for ordering topics alphabetically. Note that
 * it does not look up the 'sort' topic for you, but that this must be
 * provided explicitly to the constructors.
 */
public class TopicComparator implements Comparator<TopicIF> {

  protected GrabberIF<TopicIF, NameIF> nameGrabber;
  protected StringifierIF<NameIF> nameStringifier;

  /**
   * Empty constructor, used on application startup to initialise a
   * "fast" comparator which will compare Topics using no context.
   */
  public TopicComparator() {
    this(null, null);
  }
  
  /**
   * Constructor used to make a comparator which will compare Topics using the 
   * contexts provided. 
   */
  public TopicComparator(Collection<TopicIF> baseNameContext) {
    this(baseNameContext, null);
  } 
  
  
  /**
   * Constructor used to make a comparator which will compare Topics
   * using the contexts provided. The variantNameContext will
   * generally be a Sort topic if is available. This is the default
   * applied by the application.
   */
  public TopicComparator(Collection<TopicIF> baseNameContext, Collection<TopicIF> variantNameContext) {
    if (baseNameContext == null) { baseNameContext = Collections.emptySet(); }
    if (variantNameContext == null) { variantNameContext = Collections.emptySet(); }
    nameGrabber = new ContextNameGrabber(baseNameContext, variantNameContext);
    nameStringifier = new ComparatorNameStringifier();
  }

  /**
   * implementing method which is required for Comparator interface.
   */
  @Override
  public int compare(TopicIF o1, TopicIF o2) {

    // this method is time-critical, since it is called n*log(n) times
    // for every list of topics. could probably do more to make it
    // faster.

    if (o1 == null)
      return 1;
    if (o2 == null)
      return -1;

    String n1 = nameStringifier.toString(nameGrabber.grab(o1));
    String n2 = nameStringifier.toString(nameGrabber.grab(o2));

    if (n1 == null)
      return 1;
    if (n2 == null)
      return -1;
    
    return n1.compareToIgnoreCase(n2);
  }
  
}
