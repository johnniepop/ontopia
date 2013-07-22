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

package net.ontopia.topicmaps.nav2.impl.framework;

import java.io.File;
import java.util.Map;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * INTERNAL: Abstract class for handling a test case related to the
 * navigator taglib framework.
 */
public abstract class AbstractTaglibTestCase {

  static Logger log = LoggerFactory.getLogger(AbstractTaglibTestCase.class.getName());
  protected String jspfile;
  protected String topicmapId;
  protected Hashtable reqParams;

  /** Use this character sequence to separate attribute values */
  protected final static String SEPARATOR = "|";
  
  /**
   * Default constructor.
   */
  public AbstractTaglibTestCase(String jspfile, 
                                String topicmapId) {
    this.jspfile = jspfile;
    this.topicmapId = topicmapId;
  }

  public String toString() {
    return this.getClass().getName() + "  file: " + jspfile +
      " with topicmap: " + topicmapId;
  }

  /**
   * Sets the parameters of the Request in the fake servlet
   * environment from the map containing parameter key-value pairs.
   */
  protected void setRequestParameters(Map params) {
    reqParams = new Hashtable();
    Iterator it = params.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      String val = (String) params.get(key);
      // figure out if this is a single value or a multi value field
      if (val.indexOf(SEPARATOR) < 0) {
        reqParams.put(key, val);
      } else {
        StringTokenizer strtok = new StringTokenizer(val, SEPARATOR);
        List values = new ArrayList();
        while (strtok.hasMoreTokens()) {
          String sVal = strtok.nextToken();
          values.add(sVal);
        }
        reqParams.put(key, values.toArray(new String[values.size()]));
      }
    } // while
  }

  /**
   * Gets the request parameters used by the JSP as input.
   */
  protected Hashtable getRequestParameters() {
    return reqParams;
  }

  protected String getTopicMapId() {
    return topicmapId;
  }

  protected String getJspFileName() {
    return jspfile;
  }

}
