package net.ptidej;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.ptidej.bfs.BreadthFirstTraversal;
import net.ptidej.bfs.TextBFVisitor;
import net.ptidej.bfs.DepthFirstTraversal;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IContainer;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

/**
 * runner to compare a visit-only DFS, a visit-only BFS,
 * and PADL's built-in DFS traversal.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        
        final String[] inputs = new String[] {
                "../ptidej-Ptidej/Caffeine/target/classes/"
        };
        final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("jephs-bfs");
        try {
            model.create(new CompleteClassFileCreator(inputs, true));
        }
        catch (final CreationException e) {
            throw new RuntimeException(e);
        }

        // DFS (visit-only)
        final TextBFVisitor dfsVisitor = new TextBFVisitor();
        final String dfsListing =
                DepthFirstTraversal.generateDFS((IAbstractModel) model, dfsVisitor);
        writeToFile("BFS/src/main/resources/DFS.txt", dfsListing);

        // BFS (visit-only)
        final TextBFVisitor bfVisitor = new TextBFVisitor();
        final String bfsListing = BreadthFirstTraversal.generateBF((IAbstractModel) model, bfVisitor);
        writeToFile("BFS/src/main/resources/BFS.txt", bfsListing);

        // final padl.analysis.plantUMLGenerator.PlantUMLGenerator defaultDfs = new padl.analysis.plantUMLGenerator.PlantUMLGenerator();
        // final String defaultListing = model.generate(defaultDfs);
        // writeToFile("BFS/src/main/resources/default_DFS.txt", defaultListing);
    }

    private static void writeToFile(final String filename, final String content) throws IOException {
        final Path path = Path.of(filename);
        final Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(path, content);
    }

}
