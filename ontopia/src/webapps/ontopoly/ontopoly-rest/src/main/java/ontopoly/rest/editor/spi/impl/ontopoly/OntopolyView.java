package ontopoly.rest.editor.spi.impl.ontopoly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ontopoly.model.FieldsView;
import ontopoly.rest.editor.spi.PrestoView;

public class OntopolyView implements PrestoView {

  private final OntopolySession session;
  private final FieldsView fieldsView;

  OntopolyView(OntopolySession session, FieldsView fieldsView) {
    this.session = session;
    this.fieldsView = fieldsView;    
  }
  
  static FieldsView getWrapped(PrestoView view) {
    return ((OntopolyView)view).fieldsView;
  }

  public String getId() {
    return fieldsView.getId();
  }

  public String getDatabaseId() {
    return session.getDatabaseId();
  }
  
  public String getName() {
    return fieldsView.getName();
  }

  static Collection<PrestoView> wrap(OntopolySession session, Collection<FieldsView> fieldViews) {
    List<PrestoView> result = new ArrayList<PrestoView>(fieldViews.size());
    for (FieldsView fieldView : fieldViews) {
      result.add(new OntopolyView(session, fieldView));
    }
    return result;
  }
 
}
