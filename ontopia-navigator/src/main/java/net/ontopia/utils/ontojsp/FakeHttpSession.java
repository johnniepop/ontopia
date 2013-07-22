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

package net.ontopia.utils.ontojsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.ontopia.utils.NullObject;

/**
 * INTERNAL: Fake the ServletContext, needed for execution of servlets
 */
public class FakeHttpSession implements HttpSession {
  private Hashtable attrs;
  private ServletContext context;
  private int maxInactiveInterval;
  private Collection listeners = new ArrayList();

  public FakeHttpSession(ServletContext context) {
    this(context, new Hashtable());
  }
  
  public FakeHttpSession(ServletContext context, Hashtable attrs) {
    this.context = context;
    this.attrs = attrs;
    maxInactiveInterval = -1;
  }
  
  public Object getAttribute(String name) {
    Object result = attrs.get(name);
    if (result == NullObject.INSTANCE) 
      return null;
    else
      return result;
  }
  
  public Enumeration getAttributeNames() {
    return attrs.keys();
  }
  
  public void removeAttribute(String name) {
    attrs.remove(name);
  }
  
  public void setAttribute(String name, Object value) {
    if (value == null) 
      attrs.put(name, NullObject.INSTANCE);
    else 
      attrs.put(name, value);
  }
  
  public Object getValue(String name) {
    // Note: deprecated
    return getAttribute(name);
  }
  
  public String[] getValueNames() {
    // Note: deprecated
    Collection keys = attrs.keySet();
    String[] names = new String[keys.size()];
    keys.toArray(names);
    return names;
  }
  
  public void putValue(String name, Object value) {
    // Note: deprecated
    setAttribute(name, value);
  }
  
  public void removeValue(String name) {
    // Note: deprecated
    removeAttribute(name);
  }
  
  public long getCreationTime() {
    // TODO
    return -1;
  }
  
  public String getId() {
    // TODO
    return null;
  }
  
  public long getLastAccessedTime() {
    // TODO
    return -1;
  }
  
  public int getMaxInactiveInterval() {
    return maxInactiveInterval;
  }
  
  public HttpSessionContext getSessionContext() {
    // Note: deprecated
    return null;
  }

  public ServletContext getServletContext() {
    return context;
  }
  
  public void addSessionListener(HttpSessionListener listener) {
    listeners.add(listener);
}
  
  public void expire() {
     invalidate();
  }
  public void invalidate() {
    for (Iterator iter = listeners.iterator(); iter.hasNext();) {
      HttpSessionListener listener = (HttpSessionListener) iter.next();
      listener.sessionDestroyed(new HttpSessionEvent(this));
    }
  }
  
  public boolean isNew() {
    // TODO
    return true;
  }
  
  public void setMaxInactiveInterval(int interval) {
    maxInactiveInterval = interval;
  }
            
}
