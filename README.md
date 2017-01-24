# Arra
*Project Dead for now.*
The Arra programming language is an experimental language for the JVM. 
It has got an **very** easy to parse syntax, which is always

`COMMAND ARGUMENTS`

Arra has also got with a kind of _"Stack"_. The elements in this stack cannot be moddifed or removed, but they can be used in computations, method calls and variable assigments.
It is a goal to make the syntax as simple and minimalistic as possible, even more minimalistic than normal BASIC dialects.

#### "Hello, World" example
This is an classic "Hello, World!" version:

`PRT "Hello, World!" `

An Object-Oriented version:

`
FUN PrintHelloWorld
PRT "Hello, World!"
END PrintHelloWorld
`

which can be called by executing:
`PrintHelloWorld`

#### Comments
If you have started a comment, the whole rest of the line will be a comment.
You can start a comment with `#`:

`PRT "Information..." # Prints some information`

#### Variables
You can set variables with the _SET_ command:

`SET VariableName Value `

If you want to call a method with a variable, you must surround the variable name with _[ ]_ brackets

`SET Var "Hi there"
PRT [Var]`

There are _"special"_ variables too:
- SYS_OUT 
- SYS_IN
- SYS_ERR
- SYS_ARGS
- STACK_SIZE
- MAP_SIZE
- FUNC_ARGS

#### The Stack
The stack holds the return values from all variables, so you do not have assign the value to a variable every time.
You can reference an element in the stack by putting the elements _ID_ in _[ ]_ brackets.
So lets say at the start of your application you first pushed something in the Stack, the you can get it with `[0]`. 
Note that every index in Arra begins with zero.
