package library


/** La case classe des expression */
sealed trait Expression
case class Mot(w: String) extends Expression
case class Et(e1: Expression, e2: Expression) extends Expression
case class Ou(e1: Expression, e2: Expression) extends Expression

/** Le parseur d'expressions */
object ParserExpression {

}