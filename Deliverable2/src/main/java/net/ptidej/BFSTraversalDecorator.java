package net.ptidej;

import padl.kernel.*;
import padl.kernel.impl.Constituent;
import padl.visitor.IGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * BFS Traversal Decorator
 * Wraps any generator and provides breadth-first traversal
 */
public class BFSTraversalDecorator extends TraversalDecorator {
    
    public BFSTraversalDecorator(IGenerator generator) {
        super(generator);
    }
    
    @Override
    public String traverse(IAbstractModel model) {
        System.out.println("Using BFS (Decorator)");
        List<IConstituent> currentLevel = new ArrayList<>();
        
        // Add root level constituents
        final Iterator rootIterator = model.getConcurrentIteratorOnConstituents();
        while (rootIterator.hasNext()) {
            Object next = rootIterator.next();
            if (next instanceof IConstituent) {
                currentLevel.add((IConstituent) next);
            }
        }
        
        // Process level by level
        while (!currentLevel.isEmpty()) {
            List<IConstituent> nextLevel = new ArrayList<>();
            
            for (IConstituent c : currentLevel) {
                Constituent constituent = (Constituent) c;
                constituent.acceptVisit(decoratedGenerator);
                
                // Get children for next level
                if (constituent instanceof IContainer) {
                    final Iterator childIterator = 
                            ((IContainer) constituent).getConcurrentIteratorOnConstituents();
                    while (childIterator.hasNext()) {
                        Object child = childIterator.next();
                        if (child instanceof IConstituent) {
                            nextLevel.add((IConstituent) child);
                        }
                    }
                }
            }
            
            // Move to next level
            currentLevel = nextLevel;
        }
        return decoratedGenerator.getCode();
    }
    
    @Override
    public String getName() {
        return "BFS-" + decoratedGenerator.getName();
    }
}