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
import java.util.ArrayList;

import net.ontopia.utils.OntopiaRuntimeException;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.core.VariantNameIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TMObjectIF;
import net.ontopia.topicmaps.utils.PSI;
import net.ontopia.topicmaps.utils.VariantNameGrabber;
import net.ontopia.infoset.impl.basic.URILocator;

/**
 * INTERNAL: A Comparator for ordering TopicNameIFs and VariantNameIFs
 * alphabetically (case-independent).
 */
public class NameComparator implements Comparator {

  protected Collection scopes;
  protected VariantNameGrabber vnGrabber;
  protected VariantNameGrabber sortNameGrabber;
  
  /**
   * Empty constructor, used on application startup to initialise a
   * "fast" comparator which will compare TopicNameIFs and
   * VariantNameIFs using no context.
   */
  public NameComparator() {
    this.scopes = Collections.EMPTY_SET;
    this.vnGrabber = null;
    this.sortNameGrabber = null;
  }

  /**
   * Constructor used to make a comparator which will compare
   * TopicNameIFs and VariantNameIFs using the context provided.
   */
  public NameComparator(Collection context) {
    this.scopes = context;
    this.vnGrabber = new VariantNameGrabber(context);
    this.sortNameGrabber = null;
  }


  protected static final URILocator SORT_LOCATOR = PSI.getXTMSort();

  /**
   * INTERNAL: setup variant sort name grabber.
   * Attention: reuse same sort name grabber for this NameComparator
   * object instance, so be sure all objects are in the same topicmap.
   */
  protected final void initSortNameGrabber(TMObjectIF tmObj) {
    if (sortNameGrabber == null) {
      TopicIF sortTopic = tmObj.getTopicMap().getTopicBySubjectIdentifier(SORT_LOCATOR);
      Collection sortScope = new ArrayList(1);
      sortScope.add( sortTopic );
      sortNameGrabber = new VariantNameGrabber(sortScope);
    }
  }


  /**
   * INTERNAL: helper method which gets out of the object the
   * base name or variant name value. If it's a basename try to
   * retrieve the sort variant name for it.
   */
  protected String getName(Object obj) throws ClassCastException {
    String value = null;
    
    // --- first try if it's a base name
    try {
      TopicNameIF basename = (TopicNameIF) obj;

      // try to get sort variant name for this base name
      initSortNameGrabber( basename );
      VariantNameIF sortVariant = (VariantNameIF) sortNameGrabber.grab( basename );
      if (sortVariant != null) {
        if (sortVariant.getValue() != null)
          value = sortVariant.getValue();
        else
          value = sortVariant.getLocator().getAddress();
      }
      else
        value = basename.getValue();

    } catch (ClassCastException e) {
      // --- ...second try if it's a variant name
      VariantNameIF variant = (VariantNameIF) obj;
      if (variant.getValue() != null)
        value = variant.getValue();
      else
        value = variant.getLocator().getAddress();
    }

    return value;
  }
  
  /**
   * Compares two TopicNameIFs / VariantNameIFs.
   */
  public int compare(Object o1, Object o2) {
    String value1, value2;

    try {
      value1 = getName(o1);
      value2 = getName(o2);      
    } catch (ClassCastException e) {
      throw new OntopiaRuntimeException("NameComparator Error: This comparator only compares " +
                                        "TopicNameIFs and VariantNameIFs.", e);
    }
    
    if (value1 == null) return 1;
    if (value2 == null) return -1;
    
    return value1.compareToIgnoreCase(value2);
  }
  
}





