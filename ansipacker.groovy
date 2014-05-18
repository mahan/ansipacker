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
  static int c = 0
  static Map codes = [:]
  static esc = (char)27
}


def compress(line, colorcodes) {
  if (line.join().trim() == "") {
    //println "skipping empty line"
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


  //("foo1 bar30 foo27 baz9 foo600" =~ /foo(\d+)/).each { match, digit -> println "+$digit" }
  def esc = (char) 27
  //(line =~ /\e\[([\d]+)m([^\e])/).each { _, colorcode, chr -> println "colorcode: $colorcode, char: $chr, ascii: ${(int) chr}" }
  //(line =~ /\e\[([\d]+)m([^\e]+)/).each { _, colorcode, chr -> print "$esc[${colorcode}m$chr$esc[0m" }

  chars = []
  colorcodes = []

  for (chr in line) {
    if (chr != ' ') {break}
    chars << chr
    colorcodes << 0
    //print chr
  }


  (line =~ /\e\[([\d]+)m([^\e]+)/).each { _, colorcode, chr -> 
    //print "$esc[${colorcode}m$chr"
    for (c in chr) {
      chars << c
      colorcodes << colorcode
    }
    s.codes[colorcode] = 1
  }

  s.c++
  //print "!X! ${s.c}"
  //println ""

  //println "chars.size = ${chars.size}"
  //println "colorcodes.size = ${colorcodes.size}"

  compress(chars, colorcodes)
}

def static main(args) {

	//args.eachWithIndex {arg, ix -> println "index: $ix, arg: $arg"}

  //("KAAWW BBXX KCCYY KDDZZ" =~ /K([A-D]+)([V-Z]+)/).each { match, thing, thing2 -> println "1:$thing 2:$thing2" }
  //System.exit(-1)

	if (args.length == 0) {
		println "usage: groovy ansipacker <filename>"
		System.exit(-1)
	}

	def lines = []
	new File(args[0]).eachLine { lines << it }

  packedLines = []
	lines.each {destructure it}

  //println ""
  //println s.codes
}