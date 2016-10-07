package com.activegrid.model

import com.activegrid.model.Graph.Neo4jRep
import org.neo4j.graphdb.Node

/**
  * Created by shareefn on 7/10/16.
  */

case class InstanceUser(override val id: Option[Long],userName: String, publicKeys: List[String])  extends BaseEntity

object InstanceUser{
  implicit class InstanceUserImpl(instanceUser: InstanceUser) extends Neo4jRep[InstanceUser]{

    override def toNeo4jGraph(entity: InstanceUser): Option[Node] = {

      val label: String = "InstanceUser"

      val mapPrimitives : Map[String, Any] = Map("userName" -> entity.userName, "publicKeys" -> entity.publicKeys.toArray)

      val node: Option[Node] = GraphDBExecutor.createGraphNodeWithPrimitives[TestImplicit](label, mapPrimitives)

      node
    }

    override def fromNeo4jGraph(nodeId: Long): Option[InstanceUser] = {

      val listOfKeys: List[String] = List("userName","publicKeys")

      val propertyValues: Map[String,Any] = GraphDBExecutor.getGraphProperties(nodeId,listOfKeys)
      val userName: String = propertyValues.get("userName").get.toString
      val publicKeys: List[String] = propertyValues.get("publicKeys").get.asInstanceOf[Array[String]].toList

      Some(InstanceUser(Some(nodeId), userName,publicKeys))
    }

  }


}

