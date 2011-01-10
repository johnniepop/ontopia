package ontopoly.rest.editor.spi.impl.couchdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ontopoly.rest.editor.spi.PrestoChangeSet;
import ontopoly.rest.editor.spi.PrestoDataProvider;
import ontopoly.rest.editor.spi.PrestoField;
import ontopoly.rest.editor.spi.PrestoTopic;
import ontopoly.rest.editor.spi.PrestoType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

public class CouchDataProvider implements PrestoDataProvider {

  private HttpClient httpClient;
  private CouchDbInstance dbInstance;
  private CouchDbConnector db;
  
  private final ObjectMapper mapper = new ObjectMapper();

  public CouchDataProvider(String host, int port, String databaseName) {
    httpClient = new StdHttpClient.Builder()
    .host(host)
    .port(port)
    .build();

    dbInstance = new StdCouchDbInstance(httpClient);
    db = new StdCouchDbConnector(databaseName, dbInstance);

    db.createDatabaseIfNotExists();  
  }
  
  CouchDbConnector getCouchConnector() {
    return db;
  }
  
  ObjectMapper getObjectMapper() {
    return mapper;
  }
  
  public PrestoTopic getTopicById(String id) {
    ObjectNode doc = db.get(ObjectNode.class, id);
    if (doc == null) {
      throw new RuntimeException("Unknown topic: " + id);
    }
    return CouchTopic.existing(this, doc);
  }

  public Collection<PrestoTopic> getAvailableFieldValues(PrestoField field) {
    List<PrestoTopic> result = new ArrayList<PrestoTopic>();
    for (PrestoType type : field.getAvailableFieldValueTypes()) {
      ViewQuery query = new ViewQuery()
      .designDocId("_design/schema")
      .viewName("by-type").includeDocs(true).key(type.getId());
      ViewResult viewResult = db.queryView(query);
      for (Row row : viewResult.getRows()) {
        ObjectNode doc = (ObjectNode)row.getDocAsNode();
        
        result.add(CouchTopic.existing(this, doc));
      }
    }    
    return result;
  }

  public PrestoChangeSet createTopic(PrestoType type) {
    return new CouchChangeSet(this, type);
  }

  public PrestoChangeSet updateTopic(PrestoTopic topic) {
    return new CouchChangeSet(this, (CouchTopic)topic);
  }

  public boolean removeTopic(PrestoTopic topic) {
    CouchTopic couchTopic = (CouchTopic)topic;
    db.delete(couchTopic.getData());
    return true;
  }

  public void close() {
  }

}