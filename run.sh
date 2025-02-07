#!/bin/bash

# Diretórios
SRC_DIR="src"
BIN_DIR="bin"
LIB_DIR="lib"
JAR_LIB="$LIB_DIR/postgresql-42.2.18.jar"

# Classe principal (substitua pelo nome correto da classe principal)
MAIN_CLASS="app.Program"

# Criar diretório bin se não existir
mkdir -p "$BIN_DIR"

# Compilar os arquivos .java
find "$SRC_DIR" -name "*.java" >sources.txt
javac -d "$BIN_DIR" -cp "$JAR_LIB" @sources.txt
rm sources.txt

# Executar o programa
java -cp "$BIN_DIR:$JAR_LIB" "$MAIN_CLASS"
