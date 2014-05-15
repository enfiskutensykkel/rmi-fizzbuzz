SRC = $(wildcard *.java)
AUX = $(wildcard *.policy)

.PHONY: all clean
all:
	javac $(SRC)
	rmic FizzBuzzServer

clean:
	rm *.class
