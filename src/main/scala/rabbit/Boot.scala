package rabbit

import com.rabbitmq.client._

object Boot extends App {

  val QUEUE_NAME = "test-rabbit-queue"

  def receive(connection: Connection, workerNum: Int) = {
    val channel = connection.createChannel()
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback: DeliverCallback = (_, delivery) => {
      val message = new String(delivery.getBody, "UTF-8")
      println(s" [x] I'm woker: ${workerNum} Received '" + message + "'")
    }
    val shutdownSignalCallback: ConsumerShutdownSignalCallback = new ConsumerShutdownSignalCallback {
      override def handleShutdownSignal(consumerTag: String, sig: ShutdownSignalException): Unit = ???
    }
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, shutdownSignalCallback)
  }


  val factory = new ConnectionFactory()
  factory.setHost("10.8.5.108")
  factory.setUsername("dev-crediton");
  factory.setPassword("test12345");
  factory.setVirtualHost("dev-id");

  val connection = factory.newConnection()



  val channel = connection.createChannel()
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  val message: String = "Hello World!"

  // ASCII 256 symbols

  receive(connection, 1)
  receive(connection, 2)
  receive(connection, 3)

  (0 to 3000).foreach { i =>
    channel.basicPublish("", QUEUE_NAME, null, (message + i).getBytes("UTF-8"))
  }



  println(" [x] Sent '" + message + "'")







//  channel.close()
//  connection.close()
}
