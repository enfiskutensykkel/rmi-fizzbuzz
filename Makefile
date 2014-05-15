range = $(if $(filter $1,$(lastword $3)),$3,$(call range,$1,$2,$3 $(words $3)))  
make_range = $(foreach i,$(call range,$1),$(call range,$2))
equal = $(if $(filter-out $1,$2),,$1)

limit := 101
numbers := $(wordlist 2,$(limit),$(call range,$(limit)))
 
threes := $(wordlist 2,$(limit),$(call make_range,$(limit),2))
fives := $(wordlist 2,$(limit),$(call make_range,$(limit),4))

fizzbuzz := $(foreach v,$(numbers),\
           $(if $(and $(call equal,0,$(word $(v),$(threes))),$(call equal,0,$(word $(v),$(fives)))),FizzBuzz,\
           $(if $(call equal,0,$(word $(v),$(threes))),Fizz,\
           $(if $(call equal,0,$(word $(v),$(fives))),Buzz,$(v)))))

.PHONY: all clean fizzbuzz
all:
	javac $(wildcard *.java)
	rmic FizzBuzzServer

clean:
	rm *.class

fizzbuzz: ; $(info $(fizzbuzz))
