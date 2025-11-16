package net.ptidej;

import net.ptidej.TraversalDecorator;
import padl.kernel.*;
import padl.kernel.impl.Constituent;
import padl.kernel.impl.FirstClassEntity;
import padl.visitor.IGenerator;

import java.util.*;

/**
 * Selective/Filtering Traversal Decorator
 * Wraps any generator and filters out specified constituent types
 */
public class SelectiveTraversalDecorator extends TraversalDecorator {
    private final Set<Class<?>> notAllowedTypes;

    public SelectiveTraversalDecorator(IGenerator generator, Set<Class<?>> notAllowedTypes) {
        super(generator);
        this.notAllowedTypes = notAllowedTypes;
    }

    @Override
    public String traverse(IAbstractModel model) {
        System.out.println("Using Selective DFS (Decorator)");

        // Print filtered types
        if (notAllowedTypes != null && !notAllowedTypes.isEmpty()) {
            System.out.println("Filtering out the following types:");
            notAllowedTypes.forEach(type -> System.out.println("  - " + type.getSimpleName()));
        } else {
            System.out.println("No types filtered (notAllowedTypes is empty)");
        }

        decoratedGenerator.open(model);

        // Use DFS with filtering
        final Iterator iterator = model.getConcurrentIteratorOnConstituents();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof IConstituent) {
                traverseSelectivelyDFS((IConstituent) next);
            }
        }

        decoratedGenerator.close(model);
        return decoratedGenerator.getCode();
    }

    private void traverseSelectivelyDFS(IConstituent constituent) {
        // Check if constituent should be filtered out
        if (!shouldVisit(constituent)) {
            return; // Skip entire subtree
        }

        if (constituent instanceof IContainer) {
            // Open
            if (constituent instanceof Constituent) {
                ((Constituent) constituent).acceptOpen(decoratedGenerator);
            }

            // Recursively traverse children with filtering
            final Iterator childIterator =
                    ((IContainer) constituent).getConcurrentIteratorOnConstituents();
            while (childIterator.hasNext()) {
                Object child = childIterator.next();
                if (child instanceof IConstituent) {
                    traverseSelectivelyDFS((IConstituent) child);
                }
            }
            // Close
            if (constituent instanceof Constituent) {
                ((Constituent) constituent).acceptClose(decoratedGenerator);
            }
        } else {
            // For leaf nodes (fields, methods, etc.) - just visit
            if (constituent instanceof Constituent) {
                ((Constituent) constituent).acceptVisit(decoratedGenerator);
            }
        }
    }

    private boolean shouldVisit(IConstituent constituent) {
        if (notAllowedTypes == null || notAllowedTypes.isEmpty()) {
            return true;
        }

        return notAllowedTypes.stream()
                .noneMatch(type -> type.isInstance(constituent));
    }

    @Override
    public String getName() {
        return "Selective-" + decoratedGenerator.getName();
    }
}