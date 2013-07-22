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

import java.util.*;

import net.ontopia.utils.*;
import net.ontopia.topicmaps.utils.*;
import net.ontopia.topicmaps.core.*;

/**
 * INTERNAL: A comparator for ordering AssociationRoleIFs alphabetically
 * by role type and role player.
 */
public class AssociationRoleTypeComparator implements Comparator {

  // constants
  private static final StringifierIF DEF_TOPIC_STRINGIFIER = TopicStringifiers
    .getSortNameStringifier();
  private static final Comparator DEF_TOPIC_COMPARATOR = TopicComparators
    .getCaseInsensitiveComparator(DEF_TOPIC_STRINGIFIER);
  
  protected Comparator tc;

  public AssociationRoleTypeComparator() {
    // Empty constructor, used on application startup to initialise a
    // "fast" comparator which will compare association roles using no
    // context.
    tc = DEF_TOPIC_COMPARATOR;
  }

  /**
   * Constructor used to make a comparator which will compare
   * Association Roles using the context provided.
   *
   * @param context The context to select topics in.
   * @param sortTopic The topic representing sort names.
   */
  public AssociationRoleTypeComparator(Collection context, TopicIF sortTopic) {
    if (context == null)
      context = Collections.EMPTY_SET;

    List sortContext = new ArrayList(context);
    if (sortTopic != null)
      sortContext.add(sortTopic);
    tc = new TopicComparator(context, sortContext);
  }
  
  /**
   * Compares two AssociationRoleIFs.
   */
  public int compare (Object o1, Object o2){
    AssociationRoleIF ar1, ar2;
    try {
      ar1 = (AssociationRoleIF) o1;
      ar2 = (AssociationRoleIF) o2;
    } catch (ClassCastException e) {
      throw new OntopiaRuntimeException ("AssociationRoleTypeComparator Error: This comparator only compares AssociationRoleIFs.");
    }
    // Compare role types
    int result = tc.compare(ar1.getType(), ar2.getType());
    if (result == 0)
      // Compare role players
      return tc.compare(ar1.getPlayer(), ar2.getPlayer());
    else
      return result;
  }
  
}
