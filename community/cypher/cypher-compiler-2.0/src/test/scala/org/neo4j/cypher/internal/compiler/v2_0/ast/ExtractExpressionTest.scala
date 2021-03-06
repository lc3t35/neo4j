/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_0.ast

import org.neo4j.cypher.internal.compiler.v2_0._
import symbols._
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions

class ExtractExpressionTest extends Assertions {

  val dummyExpression = DummyExpression(
    TypeSet(CTCollection(CTNode), CTBoolean, CTCollection(CTString)),
    DummyToken(2,3))

  val extractExpression = new Expression with SimpleTypedExpression {
    def token: InputToken = DummyToken(2,3)
    protected def possibleTypes: TypeSet = Set(CTNode, CTNumber)

    def toCommand = ???
  }

  @Test
  def shouldHaveCollectionWithInnerTypesOfExtractExpression() {
    val extract = ExtractExpression(Identifier("x", DummyToken(5,6)), dummyExpression, None, Some(extractExpression), DummyToken(0, 10))
    val result = extract.semanticCheck(Expression.SemanticContext.Simple)(SemanticState.clean)
    assertEquals(Seq(), result.errors)
    assertEquals(Set(CTCollection(CTNode), CTCollection(CTNumber)), extract.types(result.state))
  }

  @Test
  def shouldRaiseSemanticErrorIfPredicateSpecified() {
    val extract = ExtractExpression(Identifier("x", DummyToken(5, 6)), dummyExpression, Some(True(DummyToken(5,6))), Some(extractExpression), DummyToken(0, 10))
    val result = extract.semanticCheck(Expression.SemanticContext.Simple)(SemanticState.clean)
    assertEquals(Seq(SemanticError("extract(...) should not contain a WHERE predicate", DummyToken(0, 10))), result.errors)
  }

  @Test
  def shouldRaiseSemanticErrorIfMissingExtractExpression() {
    val extract = ExtractExpression(Identifier("x", DummyToken(5, 6)), dummyExpression, None, None, DummyToken(0, 10))
    val result = extract.semanticCheck(Expression.SemanticContext.Simple)(SemanticState.clean)
    assertEquals(Seq(SemanticError("extract(...) requires '| expression' (an extract expression)", DummyToken(0, 10))), result.errors)
  }
}
