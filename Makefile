ll: run

clean:
	rm -f src/*.class out/Algo.jar

Algo.jar: out/parcs.jar src/*.java
	@javac -cp out/parcs.jar src/*.java
	@jar cf out/Algo.jar -C src .
	@rm -f src/*.class

run: Algo.jar
	@cd out && java -cp 'parcs.jar:Algo.jar' Algo
