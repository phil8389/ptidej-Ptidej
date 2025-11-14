package net.ptidej;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws IOException {
        final String path = "../DeMIMA/target/test-classes/ptidej/example/composite1/";
        final ICodeLevelModel codeLevelModel =
                Factory.getInstance().createCodeLevelModel(path);
        try {
            // Building the program representation.
            codeLevelModel.create(new CompleteClassFileCreator(
                    new String[] { path }));
        } catch (CreationException e) {
            throw new RuntimeException(e);
        }

        // ------ Generate using DFS ------
        PlantUMLGenerator PlantUMLGeneratorNewDFS = new PlantUMLGenerator();

        codeLevelModel.generate(PlantUMLGeneratorNewDFS);
        String umlContentDFS = (String) PlantUMLGeneratorNewDFS.getCode();

        // Write to file
        writeToFile("src/main/resources/uml_DFS.txt", umlContentDFS);
    }

    public static void writeToFile(String filename, String content) throws IOException {
        Path path = Path.of(filename);

        // Ensure all parent directories exist
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        // Write to file, creating it if it doesn't exist, and truncating (overwriting) if it does
        Files.writeString(path, content);

        System.out.println("Successfully wrote to " + filename);
    }
}
