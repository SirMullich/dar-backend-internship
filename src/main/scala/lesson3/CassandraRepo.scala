package lesson3



case class CassandraRepo() {
    // 2 operations per 1 second
    // 1 operation per 500 microsecond
    val cassandraRepo: String = new String("Cassandra")
}
