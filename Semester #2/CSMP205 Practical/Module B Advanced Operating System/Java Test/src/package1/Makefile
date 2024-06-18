# Makefile

# Define the Java compiler
JC = javac

# Define the source and output directories
SRC_DIR = src
BIN_DIR = bin

# Define the source file
SRC_FILE = $(SRC_DIR)/Main.java

# Define the target class file
TARGET = $(BIN_DIR)/Main.class

# Rule to compile the Java file
$(TARGET): $(SRC_FILE)
	$(JC) -d $(BIN_DIR) $(SRC_FILE)

# Clean rule
clean:
	rm -rf $(BIN_DIR)/*.class