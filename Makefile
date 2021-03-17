all: run

clean:
	rm -f out/Main.jar out/Algo.jar

out/Main.jar: out/parcs.jar src/Main.java
	@javac -cp out/parcs.jar src/Main.java
	@jar cf out/Main.jar -C src Main.class
	@rm -f src/Main.class 

out/Algo.jar: out/parcs.jar src/Algo.java
	@javac -cp out/parcs.jar src/Algo.java
	@jar cf out/Algo.jar -C src Algo.class
	@rm -f src/Algo.class

build: out/Main.jar out/Algo.jar

run: out/Main.jar 
	@cd out && java -cp 'parcs.jar:Main.jar' Main
