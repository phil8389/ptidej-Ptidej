package net.ptidej.bfs;

import java.util.ArrayList;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IContainer;
import padl.kernel.IConstituent;

public final class BreadthFirstTraversal {
    private BreadthFirstTraversal() {
    }

    public static String generateBF(final IAbstractModel model, final IVisitorBF visitor) {
        List<IConstituent> current = new ArrayList<>();
        final var rootIt = model.getConcurrentIteratorOnConstituents();
        while (rootIt.hasNext()) {
            current.add((IConstituent) rootIt.next());
        }

        while (!current.isEmpty()) {
            List<IConstituent> next = new ArrayList<>();
            for (IConstituent node : current) {
                visitor.visit(node); 
                if (node instanceof IContainer container) {
                    final var childIt = container.getConcurrentIteratorOnConstituents();
                    while (childIt.hasNext()) {
                        next.add((IConstituent) childIt.next());
                    }
                }
            }
            current = next;
        }
        return visitor.getCode();
    }
}
