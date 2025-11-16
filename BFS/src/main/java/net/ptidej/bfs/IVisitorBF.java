package net.ptidej.bfs;

import padl.kernel.IConstituent;

public interface IVisitorBF {
    void visit(IConstituent node);
    String getCode();
}
