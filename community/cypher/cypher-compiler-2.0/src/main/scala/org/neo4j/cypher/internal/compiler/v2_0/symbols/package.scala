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
package org.neo4j.cypher.internal.compiler.v2_0

package object symbols {
  implicit def cypherTypeSet[T <: CypherType](set: Set[T]) : TypeSet = TypeSet(set)

  val CTAny = AnyType.instance
  val CTBoolean = BooleanType.instance
  val CTString = StringType.instance
  val CTNumber = NumberType.instance
  val CTDouble = DoubleType.instance
  val CTInteger = IntegerType.instance
  val CTLong = LongType.instance
  val CTMap = MapType.instance
  val CTNode = NodeType.instance
  val CTRelationship = RelationshipType.instance
  val CTPath = PathType.instance
  def CTCollection(inner: CypherType) = CollectionType(inner)
  val CTCollectionAny = CTCollection(CTAny)
}
