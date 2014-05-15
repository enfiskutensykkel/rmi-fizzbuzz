fizzbuzz
========

Overly complex [FizzBuzz](http://en.wikipedia.org/wiki/Bizz_buzz) 
implementation just for the sake of using [Remote Method Invocation](http://en.wikipedia.org/wiki/Java_remote_method_invocation).
I have to point out that this is **NOT** how I usually write code, this code is
deliberately complex, due to it being a joke implementation.



Compile with:

	javac *.java; rmic FizzBuzzServer


	
Run server with: 

	java -Djava.security.policy=server.policy FizzBuzzServer



Run client with:	

	java -Djava.security.policy=client.policy FizzBuzzClient
