package lesson8.repository

import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.connectors.{ContactPoints, KeySpace}
import com.typesafe.config.ConfigFactory
import com.outworkers.phantom.dsl._

object CassandraConnector {
  private val config   = ConfigFactory.load()
//  private val hosts    = config.getString("cassandra.hosts").split(',')
  private val hosts    = Array("10.8.5.108")
  private val keySpace = config.getString("cassandra.keyspace")
  private val replicas = config.getInt("cassandra.replication")

  val cassandraConnection: CassandraConnection =
    ContactPoints(hosts)
      .withClusterBuilder(
        _.withSocketOptions(new SocketOptions().setConnectTimeoutMillis(20000).setReadTimeoutMillis(20000))
      )
      .keySpace(KeySpace(keySpace).ifNotExists().`with`(replication.eqs(SimpleStrategy.replication_factor(replicas))))
}