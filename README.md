The Arra programming language is an experimental language for the JVM. 
It has got an **very** easy to parse syntax, which is always
<pre><code>COMMAND ARGUMENTS
</code></pre>

Arra has also got with a kind of _"Stack"_. The elements in this stack cannot be moddifed or removed, but they can be used in computations, method calls and variable assigments.
It is a goal to make the syntax as simple and minimalistic as possible, even more minimalistic than normal BASIC dialects.

#### "Hello, World" example
This is an classic "Hello, World!" version:

<pre><code>PRT "Hello, World!" 
</code></pre>

An Object-Oriented version:

<pre><code>FUN PrintHelloWorld
PRT "Hello, World!"
END PrintHelloWorld
</code></pre>

which can be called by executing:
<pre><code>PrintHelloWorld
</code></pre>

#### Variables
You can set variables with the _SET_ command:
<pre><code>SET VariableName Value 
</code></pre>

If you want to call a method with a variable, you must surround the variable name with _[ ]_ brackets
<pre><code>SET Var "Hi there"
PRT [Var]
</code></pre>

There are _"special"_ variables too:
- SYS_OUT 
- SYS_IN
- SYS_ERR
- SYS_ARGS
- STACK_SIZE
- MAP_SIZE
- FUNC_ARGS
