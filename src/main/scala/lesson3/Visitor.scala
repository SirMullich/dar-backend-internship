package lesson3

import java.util.Date

// best practice use 'def' instead of 'val' in traits
sealed trait Visitor { //sealed => inherited only in this file
  def id: String
  // Unique id assigned to each user
  def createdAt: Date // Date this user first visited the site
  // How long has this visitor been around?
  def age: Long = new Date().getTime - createdAt.getTime
}

case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

// A has b and c and d and e... PRODUCT type
case class User(
                 id: String,
                 email: String,
                 createdAt: Date = new Date()
               ) extends Visitor


// Visitor can be Anonymous or User
// A can be B or C or D or E ....          SUM type
