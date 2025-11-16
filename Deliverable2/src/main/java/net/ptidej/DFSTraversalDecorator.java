package net.ptidej;

import padl.kernel.*;
import padl.visitor.IGenerator;

import java.util.Iterator;

/**
 * DFS Traversal Decorator
 */
public class DFSTraversalDecorator extends TraversalDecorator {

    public DFSTraversalDecorator(IGenerator generator) {
        super(generator);
    }
    @Override
    public String traverse(IAbstractModel model) {
        System.out.println("Using DFS (Decorator)");

        decoratedGenerator.open(model);

        // accept() handles recursion
        final Iterator iterator = model.getIteratorOnConstituents();
        while (iterator.hasNext()) {
            IConstituent constituent = (IConstituent) iterator.next();
            constituent.accept(decoratedGenerator);
        }

        decoratedGenerator.close(model);
        return decoratedGenerator.getCode();
    }

    @Override
    public String getName() {
        return "DFS-" + decoratedGenerator.getName();
    }
}