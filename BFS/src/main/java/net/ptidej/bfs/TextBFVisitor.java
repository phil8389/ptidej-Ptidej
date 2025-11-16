package net.ptidej.bfs;

import padl.kernel.IConstituent;

public class TextBFVisitor implements IVisitorBF {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void visit(final IConstituent node) {
        if (node == null) {
            return;
        }
        final String typeName = node.getClass().getSimpleName();
        final String displayName;
        try {
            displayName = node.getDisplayName();
        }
        catch (final Exception e) {
            sb.append(typeName).append(" : <error>").append('\n');
            return;
        }
        sb.append(typeName)
          .append(" : ")
          .append(displayName)
          .append('\n');
    }

    @Override
    public String getCode() {
        return sb.toString();
    }
}
