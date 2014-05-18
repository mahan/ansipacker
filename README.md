AnsiPacker packs ansi pictures created by jp2a
(http://sourceforge.net/projects/jp2a/)

License: BSD  

Warning - low code quality: My first groovy hack, just for learning.

Example how to use jp2a (using ImageMagik for png to jpg conversion first): 

$ convert ~/Downloads/aoki/aoki_241.png jpg:- | jp2a --width=100 --color - > aoki_241.ansi

usage: groovy ansipacker < filename >

usage example:

$ groovy ansipacker aoki_254.ansi

/Mattias Hansson