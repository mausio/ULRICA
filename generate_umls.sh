#!/bin/bash

# Create UMLs-imgs directory if it doesn't exist
mkdir -p presentation/assets/UMLs-imgs

# Check if PlantUML is installed
plantuml_jar="plantuml.jar"
if [ ! -f "$plantuml_jar" ]; then
  echo "Downloading PlantUML..."
  curl -L -o $plantuml_jar https://sourceforge.net/projects/plantuml/files/plantuml.jar/download
fi

# Generate PNG files from PlantUML code
for file in presentation/assets/UMLs-Code/*.puml; do
  filename=$(basename "$file" .puml)
  echo "Generating PNG for $filename..."
  java -jar $plantuml_jar "$file" -o ../UMLs-imgs
done

echo "UML generation complete!" 