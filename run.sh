#!/usr/bin/env bash
set -euo pipefail

CSV_PATH="${1:-cars.csv}"

# Compile all Java files
if javac *.java; then
  echo "Compilation successful. Running program..."
  java Main "$CSV_PATH"
else
  echo "Compilation failed."
  exit 1
fi

