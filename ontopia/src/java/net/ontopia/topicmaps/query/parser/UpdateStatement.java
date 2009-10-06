
// $Id$

package net.ontopia.topicmaps.query.parser;

import java.util.List;
import java.util.ArrayList;

import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.impl.basic.QueryMatches;

/**
 * INTERNAL: Common abstract superclass for all update statements.
 */
public abstract class UpdateStatement extends TologStatement {
  protected List litlist;     // always exactly two
  protected TologQuery query; // the FROM ... part, if any

  public UpdateStatement() {
    super();
    litlist = new ArrayList();
  }

  public void addLit(Object lit) {
    litlist.add(lit);
  }

  public List getLitList() {
    return litlist;
  }

  public void setClauseList(List clauses, TologOptions options)
    throws AntlrWrapException {
    // this is only called if there was a FROM clause, so we create a subquery
    query = new TologQuery();
    query.setClauseList(clauses);
    query.setOptions(options);

    // add vars in litlist to select list of subquery so that we get projection
    for (int ix = 0; ix < litlist.size(); ix++) {
      Object lit = litlist.get(ix);
      if (lit instanceof Variable)
        query.addVariable((Variable) lit);
    }
  }

  public TologQuery getEmbeddedQuery() {
    return query;
  }

  public void close() throws InvalidQueryException {
    if (query != null)
      query.close();

    // verify that if we have variables in the litlist we also have a FROM
    // part
    if (query == null)
      for (int ix = 0; ix < litlist.size(); ix++)
        if (litlist.get(ix) instanceof Variable)
          throw new InvalidQueryException("Cannot have variables in select " +
                                          "part if no from part");
  }

  public abstract int doStaticUpdates() throws InvalidQueryException;

  public abstract int doUpdates(QueryMatches matches)
    throws InvalidQueryException;
}