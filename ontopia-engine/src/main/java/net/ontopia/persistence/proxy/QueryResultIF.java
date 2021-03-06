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

package net.ontopia.persistence.proxy;

  
/**
 * INTERNAL: Interface for representing two-dimensional (or
 * potentially even N-dimensional) query results.<p>
 *
 * Note: it is a goal that this interface is aligned with
 * net.ontopia.topicmaps.query.core.QueryResultIF.
 */

public interface QueryResultIF {
  
  /**
   * INTERNAL: Skip to the next row in the query result set. The
   * method returns false if the skip was not valid, i.e. we're at the
   * end of the result set.
   */
  boolean next();

  /**
   * INTERNAL: Returns the number of fields that each row in the query
   * result set have.
   */
  int getWidth();

  //! /**
  //!  * PUBLIC: Returns the index of the named column. Returns -1 if the
  //!  * column does not exist. The column index is zero-based.
  //!  */
  //! public int getIndex(String colname);

  /**
   * PUBLIC: Returns the names of the columns.
   */
  String[] getColumnNames();

  /**
   * PUBLIC: Returns the name of the given column.  The column index
   * is zero-based.
   *
   * @throws IndexOutOfBoundsException if there is no such column.
   */
  String getColumnName(int ix);
  
  /**
   * INTERNAL: Get the value of the field with the specified index
   * from the current result row. The index is zero-based.
   */
  Object getValue(int index);
  
  //! /**
  //!  * PUBLIC: Returns the value in the given column in the current
  //!  * match.  Requires <code>next()</code> to have been called first.
  //!  * @throws IllegalArgumentException if there is no such column.
  //!  */
  //! public Object getValue(String colname);
  
  /**
   * INTERNAL: Get the values of all fields from the current result
   * row.
   */
  Object[] getValues();
  
  /**
   * INTERNAL: Reads the values of all fields from the current result
   * row into the specified array.
   */
  Object[] getValues(Object[] values);
  
  /**
   * INTERNAL: Closes the query result, which allows it to free its
   * resources.
   */
  void close();
  
}
