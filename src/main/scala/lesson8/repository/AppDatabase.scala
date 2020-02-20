package lesson8.repository

import com.outworkers.phantom.connectors.{CassandraConnection, Connector}
import com.outworkers.phantom.database.{Database, DatabaseProvider}

class AppDatabase(override val connector: CassandraConnection) extends Database[AppDatabase](connector) {
  object jobsTable extends JobsTable with Connector
}