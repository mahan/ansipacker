/*-------------------------------------------------------------------------------

  AnsiPacker packs ansi pictures created by jp2a
  (http://sourceforge.net/projects/jp2a/)

  License: BSD  

  Warning - low code quality: My first groovy hack, just for learning.

  Example how to use jp2a (using ImageMagik for png to jpg conversion first): 
  
  $ convert ~/Downloads/aoki/aoki_241.png jpg:- | jp2a --width=100 --color - > aoki_241.ansi

  usage: groovy ansipacker <filename>

  usage example:
  
  $ groovy ansipacker aoki_254.ansi

  /Mattias Hansson

-------------------------------------------------------------------------------*/


class s {
  static esc = (char)27
}

def compress(line, colorcodes) {
  if (line.join().trim() == "") {
    return
  }

  def currentColor = -1
  line.eachWithIndex {chr, idx ->
    if (currentColor != colorcodes[idx]) {
      print "${s.esc}[${colorcodes[idx]}m$chr"
      currentColor = colorcodes[idx]
    } else {
      print chr
    }
  }
  println ""
}

def destructure(line) {
  def chars = []
  def colorcodes = []

  for (chr in line) {
    if (chr != ' ') {break}
    chars << chr
    colorcodes << 0
  }


  (line =~ /\e\[([\d]+)m([^\e]+)/).each { _, colorcode, chr -> 
    for (c in chr) {
      chars << c
      colorcodes << colorcode
    }
  }

  compress(chars, colorcodes)
}

def static main(args) {

  if (args.length == 0) {
    println "usage: groovy ansipacker <filename>"
    System.exit(-1)
  }

  def lines = []
  new File(args[0]).eachLine { lines << it }

  def packedLines = []
  lines.each {destructure it}
}