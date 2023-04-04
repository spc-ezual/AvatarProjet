package application

import library._

object HTMLToString {
  def traduire(html: Html): String = {
    html match {
      case Tag(name, attributes, children) =>
        val attrString =
          attributes.map { case (key, value) => s"$key=$value" }.mkString(" ")
        val childrenString = children.map(traduire).mkString
        s"<$name $attrString>$childrenString \n </$name>"
      case Texte(content) =>
        content
    }
  }
}
