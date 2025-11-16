package net.ptidej;

import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.*;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static void main() throws IOException {
        final String path = "../DeMIMA/target/test-classes/ptidej/example/composite1/";
//        final String path = "../Deliverable2/target/test-classes/simple/";

        final ICodeLevelModel codeLevelModel =
                Factory.getInstance().createCodeLevelModel(path);
        try {
            // Instantiating the creator
            CompleteClassFileCreator ccfCreator = new CompleteClassFileCreator(new String[] { path });
            // Creating (or populating) the model
            codeLevelModel.create(ccfCreator);
        } catch (CreationException e) {
            throw new RuntimeException(e);
        }


        // Create base printer
        SimplePrinter printer = new SimplePrinter();

        // Apply BFS traversal
        BFSTraversalDecorator bfsPrinter = new BFSTraversalDecorator(printer);
        String bfsResult = bfsPrinter.traverse(codeLevelModel);
        System.out.println("=== BFS Output ===");
        System.out.println(bfsResult);

        // Apply DFS traversal
        printer.reset();
        DFSTraversalDecorator dfsPrinter = new DFSTraversalDecorator(printer);
        String dfsResult = dfsPrinter.traverse(codeLevelModel);
        System.out.println("=== DFS Output ===");
        System.out.println(dfsResult);

        printer.reset();
        Set<Class<?>> notAllowed = new HashSet<>();
        notAllowed.add(IConstructor.class);
        notAllowed.add(IGhost.class);
        notAllowed.add(IField.class);

        SelectiveTraversalDecorator selective =
                new SelectiveTraversalDecorator(printer, notAllowed);

        String result = selective.traverse(codeLevelModel);

        System.out.println("=== Selective Output ===");
        System.out.println(result);

        // ===== UML TESTS =====

        // Original UML - Fresh generator
        PlantUMLGenerator originalGenerator = new PlantUMLGenerator();
        String originalUML = codeLevelModel.generate(originalGenerator);
        System.out.println("=== Original UML Output ===");
        writeToFile("../Deliverable2/src/main/resources/originalUML.txt", originalUML);

        // BFS UML - Fresh generator
        PlantUMLGenerator bfsGenerator = new PlantUMLGenerator();
        BFSTraversalDecorator bfsUMLTraversal = new BFSTraversalDecorator(bfsGenerator);
        String bfsUML = bfsUMLTraversal.traverse(codeLevelModel);
        System.out.println("=== BFS UML Output ===");
        writeToFile("../Deliverable2/src/main/resources/bfsUML.txt", bfsUML);

        // DFS UML - Fresh generator
        PlantUMLGenerator dfsGenerator = new PlantUMLGenerator();
        DFSTraversalDecorator dfsUMLTraversal = new DFSTraversalDecorator(dfsGenerator);
        String dfsUML = dfsUMLTraversal.traverse(codeLevelModel);
        System.out.println("=== DFS UML Output ===");
        writeToFile("../Deliverable2/src/main/resources/dfsUML.txt", dfsUML);

        // Selective UML - Fresh generator
        PlantUMLGenerator selectiveGenerator = new PlantUMLGenerator();
        Set<Class<?>> notAllowedUML = new HashSet<>();
        notAllowedUML.add(IConstructor.class);
        notAllowedUML.add(IInterface.class);

        SelectiveTraversalDecorator selectiveUMLTraversal =
                new SelectiveTraversalDecorator(selectiveGenerator, notAllowedUML);
        String selectiveUML = selectiveUMLTraversal.traverse(codeLevelModel);
        System.out.println("=== Selective UML Output ===");
        writeToFile("../Deliverable2/src/main/resources/selectiveUML.txt", selectiveUML);


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
