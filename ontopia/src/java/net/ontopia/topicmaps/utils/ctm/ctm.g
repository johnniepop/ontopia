
// $Id: ctm.g,v 1.4 2009/04/27 11:04:38 lars.garshol Exp $

header { package net.ontopia.topicmaps.utils.ctm; }

{
  import java.io.File;
  import java.io.Reader;
  import java.io.IOException;
  
  import java.net.MalformedURLException;
  
  import java.util.Set;
  import java.util.Map;
  import java.util.List;
  import java.util.HashMap;
  import java.util.Iterator;
  import java.util.ArrayList;
  import java.util.Collection;
  
  import net.ontopia.utils.CompactHashSet;
  import net.ontopia.utils.OntopiaRuntimeException;
  import net.ontopia.infoset.core.LocatorIF;
  import net.ontopia.infoset.impl.basic.URILocator;
  import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
  import net.ontopia.topicmaps.core.*;
  import net.ontopia.topicmaps.xml.XTMTopicMapReader;
  import net.ontopia.topicmaps.xml.XTMContentHandler;
  import net.ontopia.topicmaps.xml.InvalidTopicMapException;
  import net.ontopia.topicmaps.utils.PSI;
  import net.ontopia.topicmaps.utils.MergeUtils;
  import net.ontopia.topicmaps.utils.ltm.AntlrWrapException;
  import org.xml.sax.InputSource;
  import antlr.TokenStreamException;
  import antlr.SemanticException;
}

/**
 * INTERNAL: Parser for the CTM syntax.
 */
class CTMParser extends Parser;

options {
  defaultErrorHandler = false;
  k = 5;
}

{
  private LocatorIF document;
  private TopicMapIF topicmap;
  private TopicMapBuilderIF builder;
  private Set alreadyLoaded;// the set of locators whose resources are loaded
  private boolean subordinate;//whether we were created via #MERGEMAP or #INC
  private ParseContextIF context;
  private ParseEventHandlerIF handler;
  private ParseEventHandlerIF real_handler;

  private ValueGeneratorIF topic_ref;    // topic referenced by topic_ref
  private ValueGeneratorIF type;      // used for role types
  private ValueGeneratorIF literal;    // last parsed literal
  private ValueGenerator basic_literal; // reused generator
  private ValueGenerator datatype_literal; // reused generator
  private ValueGenerator tmp;              // used for swapping
  private ValueGeneratorIF wildcard;           // reused generator
  private String id;                     // prefix name (used in declarations)
  private Template template;             // template to be invoked (only invoc)

  private String template_name;  // definition only
  private String template_name2; // invocation only
  private List parameters;
  private Map wildcards;          // for top-level named wildcards
  private Map template_wildcards; // for named wildcards in templates
  private Template current_template; // this template inside template definition

  // --- configuration interface

  public void setTopicMap(TopicMapIF topicmap) {
    this.topicmap = topicmap;
    this.builder = topicmap.getBuilder();
    this.context = new GlobalParseContext(topicmap, document);
    this.handler = new BuilderEventHandler(builder, context);
    this.real_handler = handler;
    this.basic_literal = new ValueGenerator();
    this.datatype_literal = new ValueGenerator();
    this.wildcard = new WildcardTopicGenerator(context);
    this.wildcards = new HashMap();
    this.template_wildcards = new HashMap();
  }

  // only used for tolog INSERT
  public void setHandler(ParseEventHandlerIF handler,
                         ParseContextIF context) {
    this.handler = handler;
    this.context = context;
  }

  public ParseContextIF getContext() {
    return context;
  }

  public void setBase(LocatorIF base) {
    this.document = base;
  }

  public void init() {
    alreadyLoaded = new CompactHashSet();
    alreadyLoaded.add(document); // don't want to read top document again
  }

  private ValueGeneratorIF getWildcard(String name) {
    Map map;

    if (current_template == null) 
      map = wildcards;
    else
      map = template_wildcards;
        
    ValueGeneratorIF gen = (ValueGeneratorIF) map.get(name);
    if (gen == null) {
      gen = new NamedWildcardTopicGenerator(context, name);
      map.put(name, gen);
    }

    if (current_template != null)
      current_template.registerWildcard(name, gen);

    return gen;
  }

  private LocatorIF getAbsoluteLocator() throws antlr.TokenStreamException {
    try {
      return new URILocator(LT(0).getText());
    } catch (MalformedURLException e) {
      throw new OntopiaRuntimeException(e); // FIXME!          
    }
  }

  private LocatorIF getRelativeLocator() throws antlr.TokenStreamException {
    return document.resolveAbsolute(LT(0).getText());
  }
}

topicmap :
  (encoding_decl)? (version)?
  /* (directive)* */ (tm_reifier)?
  (directive | 
   topic | 
   template_def | 
   ((topic_ref LEFTPAREN topic_ref COLON) => association |
    template_invocation))*
  EOF!
    ;

encoding_decl :
  ENCODING string;

version :
  VERSION ONEOH;

directive :
  prefix_decl | include | mergemap;

prefix_decl :
  PREFIX IDENTIFIER
    { id = LT(0).getText(); }
  iri_ref
    { context.addPrefix(id, literal.getLocator()); } ;

include :
  INCLUDE iri_ref {
    LocatorIF docuri = literal.getLocator();
    InputSource source = new InputSource(docuri.getExternalForm());
    ParseContextIF othercontext;
    Reader reader = null;
    try {
       reader = CTMTopicMapReader.makeReader(source, new CTMEncodingSniffer());
      CTMLexer lexer = new CTMLexer(reader);
      lexer.setDocuri(docuri.getExternalForm());
      CTMParser parser = new CTMParser(lexer);
      parser.setBase(docuri);
      parser.setTopicMap(topicmap);
      parser.init();
      othercontext = parser.getContext();
      Iterator it = context.getIncludeUris().iterator();
      while (it.hasNext())
        othercontext.addIncludeUri((LocatorIF) it.next());
      othercontext.addIncludeUri(document);
      parser.topicmap();
      reader.close();
    } catch (IOException e) {
      throw new AntlrWrapException(e);
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (IOException e) {
        throw new AntlrWrapException(e);
      }
    }

    // pull over template definitions
    Iterator it = othercontext.getTemplates().values().iterator();
    while (it.hasNext()) {
      Template template = (Template) it.next();
      context.registerTemplate(template.getName(), template);
    }
  };

mergemap :
  MERGEMAP iri_ref {
    try {
      LocatorIF docuri = literal.getLocator();   
      TopicMapIF tm = new CTMTopicMapReader(docuri).read();
      MergeUtils.mergeInto(topicmap, tm);
    } catch (IOException e) {
      throw new AntlrWrapException(e);
    }
  };

topic :
  topic_identity 
  (property (SEMICOLON property)* (SEMICOLON)?)?
  STOP { handler.endTopic(); };

topic_identity :
  (IDENTIFIER { 
     handler.startTopic(context.getTopicById(LT(0).getText()));
   } |
   VARIABLE           
     { if (current_template == null)
         throw new InvalidTopicMapException("Variable " + LT(0).getText() + 
                                            " referenced outside template");
       topic_ref = current_template.getGenerator(LT(0).getText());
       handler.startTopic(topic_ref); } |
   item_identifier   { handler.startTopicItemIdentifier(literal);     } |
   subject_locator   { handler.startTopicSubjectLocator(literal);     } |
   subject_identifier { handler.startTopicSubjectIdentifier(literal); } |
   WILDCARD
     { handler.startTopic(wildcard);  } |
   NAMED_WILDCARD 
     { handler.startTopic(getWildcard(LT(0).getText())); } )
  ;

item_identifier    : HAT iri_ref;
subject_locator    : EQUALS iri_ref;
subject_identifier : iri_ref;

property :
  name | occurrence | instance_of | item_identifier_add | subject_locator_add |
  subject_identifier_add | a_kind_of | template_invocation ;

name :
    { topic_ref = null; } // in case no type
  HYPHEN (topic_ref COLON)? 
    { if (topic_ref == null)
        topic_ref = context.getTopicBySubjectIdentifier(PSI.getSAMNameType()); }
  ( string 
    { basic_literal.setLiteral(LT(0).getText());
      handler.startName(topic_ref, basic_literal); }
  | VARIABLE
    { literal = current_template.getGenerator(LT(0).getText());
      handler.startName(topic_ref, literal); } )
  (scope)? (reifier)? (variant)* 
    { handler.endName(); } ;

variant :
  LEFTPAREN literal
    { handler.startVariant(literal); }
  scope (reifier)? RIGHTPAREN ;

occurrence :
 topic_ref COLON literal 
    { handler.startOccurrence(topic_ref, literal); }
  (scope)? (reifier)? ;

instance_of :
  ISA topic_ref { handler.addTopicType(topic_ref); };

a_kind_of :
  AKO topic_ref { handler.addSubtype(topic_ref); };

item_identifier_add    : HAT iri_ref 
  { handler.addItemIdentifier(literal); } ;
subject_locator_add    : EQUALS iri_ref 
  { handler.addSubjectLocator(literal); } ;
subject_identifier_add : iri_ref 
  { handler.addSubjectIdentifier(literal); } ;

association :
  topic_ref LEFTPAREN
    { handler.startAssociation(topic_ref); }
    role (COMMA role)*
  RIGHTPAREN (scope)?
    { handler.endRoles(); }
  (reifier)?
    { handler.endAssociation(); } ;

role :
  topic_ref 
    { type = topic_ref.copy(); }
  COLON topic_ref 
    { handler.addRole(type, topic_ref); }
  (reifier)? ;

scope : 
  AT topic_ref     { handler.addScopingTopic(topic_ref);  }
  (COMMA topic_ref { handler.addScopingTopic(topic_ref); } )*;

reifier :
  TILDE topic_ref  { handler.addReifier(topic_ref); };

tm_reifier :
  TILDE topic_ref  { 
    // if this is an included TM, don't set reifier
    if (context.getIncludeUris().isEmpty())
      handler.addReifier(topic_ref);
    else {
      // the topic is referenced, and so must be created somehow...
      handler.startTopic(topic_ref);
      handler.endTopic();
    }
  };

embedded_topic :
  LEFTBRACKET
    { handler.startEmbeddedTopic(); }
  property (SEMICOLON property)* (SEMICOLON)?
  RIGHTBRACKET
    { topic_ref = handler.endEmbeddedTopic(); };

topic_ref :
  IDENTIFIER { 
    topic_ref = context.getTopicById(LT(0).getText());
  } |
  embedded_topic                          | // sets topic_ref itself
  WILDCARD { topic_ref = wildcard; }     |
  NAMED_WILDCARD { topic_ref = getWildcard(LT(0).getText()); } |
  subject_locator {
    topic_ref = context.getTopicBySubjectLocator(literal.getLocator());
  } |
  HAT iri_ref {
    topic_ref = context.getTopicByItemIdentifier(literal.getLocator());
  } |
  VARIABLE {
    topic_ref = current_template.getGenerator(LT(0).getText());
  } |
  iri_ref {
    topic_ref = context.getTopicBySubjectIdentifier(literal.getLocator());
  } |
  QNAME { 
    topic_ref = context.getTopicByQname(LT(0).getText());
  }
  ;

literal :
  (string   
    { literal = basic_literal;
      basic_literal.setLiteral(LT(0).getText());
      basic_literal.setDatatype(PSI.getXSDString()); } 
  ( HATHAT 
    { tmp = (ValueGenerator) basic_literal.copy(); }
    iri_ref
    { tmp.setDatatype(literal.getLocator()); 
      literal = tmp; } )?
  | 
  IRI 
    { literal = basic_literal;
      basic_literal.setLocator(getAbsoluteLocator()); } |
  WRAPPED_IRI
    { literal = basic_literal;
      basic_literal.setLocator(getRelativeLocator()); } |
  INTEGER  { literal = basic_literal;
             basic_literal.setLiteral(LT(0).getText());
             basic_literal.setDatatype(PSI.getXSDInteger()); } |
  DECIMAL  { literal = basic_literal;
             basic_literal.setLiteral(LT(0).getText());
             basic_literal.setDatatype(PSI.getXSDDecimal()); } |
  DATE     { literal = basic_literal;
             basic_literal.setLiteral(LT(0).getText());
             basic_literal.setDatatype(PSI.getXSDDate());    } |
  DATETIME { literal = basic_literal;
             basic_literal.setLiteral(LT(0).getText());
             basic_literal.setDatatype(PSI.getXSDDatetime());} |
  VARIABLE { 
    if (current_template == null)
      throw new InvalidTopicMapException("Variable " + LT(0).getText() +
                                         " referenced outside template");
    literal = current_template.getGenerator(LT(0).getText()); 
  });

iri_ref :
  ( QNAME
    { literal = basic_literal;
      basic_literal.setLocator(context.resolveQname(LT(0).getText())); } )
  | IRI
    { literal = basic_literal;
      basic_literal.setLocator(getAbsoluteLocator()); }
  | WRAPPED_IRI
    { literal = basic_literal;
      basic_literal.setLocator(getRelativeLocator()); } 
  | VARIABLE
    { literal = current_template.getGenerator(LT(0).getText()); }
    ;

template_def :
  DEF IDENTIFIER 
    { template_name = LT(0).getText(); 
      System.out.println("TEMPLATE: " + template_name);
      parameters = new ArrayList(); }
  LEFTPAREN (VARIABLE 
    { parameters.add(LT(0).getText()); }
  (COMMA VARIABLE
    { parameters.add(LT(0).getText()); }
  )*)? RIGHTPAREN
    { handler = new TemplateEventHandler(template_name, parameters, real_handler);
      current_template = ((TemplateEventHandler) handler).getTemplate(); }

  template_body

    { context.registerTemplate(template_name, current_template);
      handler = real_handler;
      current_template = null; }
  END ;

// this is separated out so we can share it between template_def and tolog_insert
template_body:
  (topic | 
   ((topic_ref LEFTPAREN topic_ref COLON) => association |
    template_invocation))*;

tolog_insert:
  // the END is there only to force antlr to actually parse the
  // contents of the template_body. without it, antlr is just as happy
  // to simply say "I didn't find anything" and go home, since
  // template_body is not required to match anything. we insert a fake
  // "END" at the end of the CTM part to make this work.
  { current_template = ((TemplateEventHandler) handler).getTemplate(); }
  template_body END; 

template_invocation :
  IDENTIFIER 
    { template_name2 = LT(0).getText();
      parameters = new ArrayList(); }
  LEFTPAREN (argument (COMMA argument)*)? RIGHTPAREN
    { handler.templateInvocation(template_name2, parameters); }
  ;

argument :
  (IRI { 
     parameters.add(new IRIAsArgumentGenerator(context, getAbsoluteLocator()));
   } |
   WRAPPED_IRI {
     parameters.add(new IRIAsArgumentGenerator(context, getRelativeLocator()));
   } |
   QNAME {
     parameters.add(new IRIAsArgumentGenerator(context, context.resolveQname(LT(0).getText())));
   } |
   topic_ref { parameters.add(topic_ref.copy());     } | 
   literal   { parameters.add(literal.copy()); } );

string : SINGLE_QUOTED_STRING | TRIPLE_QUOTED_STRING ;
