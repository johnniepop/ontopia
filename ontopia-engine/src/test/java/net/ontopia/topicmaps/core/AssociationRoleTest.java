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

package net.ontopia.topicmaps.core;

public abstract class AssociationRoleTest extends AbstractTypedTest {
  protected AssociationRoleIF role;
  
  public AssociationRoleTest(String name) {
    super(name);
  }
    
  // --- Test cases

  public void testReification() {
    TopicIF reifier = builder.makeTopic();
    ReifiableIF reifiable = role;
    
    assertTrue("Object reified by the reifying topic was found",
               reifier.getReified() == null);
    assertTrue("Topic reifying the reifiable was found",
               reifiable.getReifier() == null);
    
    reifiable.setReifier(reifier);
    assertTrue("No topic reifying the reifiable was found",
               reifiable.getReifier() == reifier);
    assertTrue("No object reified by the reifying topic was found",
               reifier.getReified() == reifiable);
    
    reifiable.setReifier(null);
    assertTrue("Object reified by the reifying topic was found",
               reifier.getReified() == null);
    assertTrue("Topic reifying the first reifiable was found",
               reifiable.getReifier() == null);
  }

  public void testPlayer() {
    assertTrue("player null initially", role.getPlayer() != null);

    TopicIF player = builder.makeTopic();
    role.setPlayer(player);
    assertTrue("player not set properly", role.getPlayer().equals(player));

    try {
      role.setPlayer(null);
      fail("player could be set to null");
    } catch (NullPointerException e) {
    }
    assertTrue("player not retained", role.getPlayer().equals(player));
  }

  public void testParentAssociation() {
    assertTrue("parent not set to right object",
               role.getAssociation().equals(parent));
  }

  // --- Internal methods

  @Override
  public void setUp() throws Exception {
    super.setUp();
    AssociationIF assoc = builder.makeAssociation(builder.makeTopic());
    parent = assoc;
    role = builder.makeAssociationRole(assoc, builder.makeTopic(), builder.makeTopic());
    object = role;
    typed = role;
  }

  @Override
  protected TMObjectIF makeObject() {
    AssociationIF assoc = builder.makeAssociation(builder.makeTopic());
    return builder.makeAssociationRole(assoc, builder.makeTopic(), builder.makeTopic());
  }
    
}
