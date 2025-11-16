package net.ptidej.bfs;

import padl.kernel.IAbstractModel;
import padl.kernel.IContainer;
import padl.kernel.IConstituent;

// visit only, for comparison

public final class DepthFirstTraversal {
    private DepthFirstTraversal() {
    }

    public static String generateDFS(final IAbstractModel model, final IVisitorBF visitor) {
        final var it = model.getConcurrentIteratorOnConstituents();
        while (it.hasNext()) {
            visitNode((IConstituent) it.next(), visitor);
        }
        return visitor.getCode();
    }

    private static void visitNode(final IConstituent node, final IVisitorBF visitor) {
        if (node == null) {
            return;
        }
        visitor.visit(node);
        if (node instanceof IContainer container) {
            final var childIt = container.getConcurrentIteratorOnConstituents();
            while (childIt.hasNext()) {
                visitNode((IConstituent) childIt.next(), visitor);
            }
        }
    }
}
