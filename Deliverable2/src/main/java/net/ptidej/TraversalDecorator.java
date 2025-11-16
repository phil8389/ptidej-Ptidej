package net.ptidej;

import padl.kernel.*;
import padl.visitor.IGenerator;

import java.util.Iterator;

/**
 * Base decorator for traversal strategies
 * Wraps an IGenerator and adds traversal behavior
 * Method creation were made by the IDE and populated by an AI agent
 */
public class TraversalDecorator implements IGenerator {
    protected final IGenerator decoratedGenerator;

    protected TraversalDecorator(IGenerator generator) {
        this.decoratedGenerator = generator;
    }

    // Default implementation - can be overridden
    public String traverse(IAbstractModel model) {
        // Default DFS behavior
        decoratedGenerator.open(model);
        final Iterator iterator = model.getIteratorOnConstituents();
        while (iterator.hasNext()) {
            ((IConstituent) iterator.next()).accept(decoratedGenerator);
        }
        decoratedGenerator.close(model);
        return decoratedGenerator.getCode();
    }

    // Delegate all IGenerator methods to decorated generator

    @Override
    public void open(IAbstractModel anAbstractModel) {
        decoratedGenerator.open(anAbstractModel);
    }

    @Override
    public void close(IAbstractModel anAbstractModel) {
        decoratedGenerator.close(anAbstractModel);
    }

    @Override
    public void open(IClass aClass) {
        decoratedGenerator.open(aClass);
    }

    @Override
    public void close(IClass aClass) {
        decoratedGenerator.close(aClass);
    }

    @Override
    public void open(IInterface anInterface) {
        decoratedGenerator.open(anInterface);
    }

    @Override
    public void close(IInterface anInterface) {
        decoratedGenerator.close(anInterface);
    }

    @Override
    public void open(IConstructor aConstructor) {
        decoratedGenerator.open(aConstructor);
    }

    @Override
    public void close(IConstructor aConstructor) {
        decoratedGenerator.close(aConstructor);
    }

    @Override
    public void open(IMethod aMethod) {
        decoratedGenerator.open(aMethod);
    }

    @Override
    public void close(IMethod aMethod) {
        decoratedGenerator.close(aMethod);
    }

    @Override
    public void open(IPackage aPackage) {
        decoratedGenerator.open(aPackage);
    }

    @Override
    public void close(IPackage aPackage) {
        decoratedGenerator.close(aPackage);
    }

    @Override
    public void open(IPackageDefault aPackageDefault) {
        decoratedGenerator.open(aPackageDefault);
    }

    @Override
    public void close(IPackageDefault aPackageDefault) {
        decoratedGenerator.close(aPackageDefault);
    }

    @Override
    public void open(IGhost aGhost) {
        decoratedGenerator.open(aGhost);
    }

    @Override
    public void close(IGhost aGhost) {
        decoratedGenerator.close(aGhost);
    }

    @Override
    public void open(IMemberClass aMemberClass) {
        decoratedGenerator.open(aMemberClass);
    }

    @Override
    public void close(IMemberClass aMemberClass) {
        decoratedGenerator.close(aMemberClass);
    }

    @Override
    public void open(IMemberInterface aMemberInterface) {
        decoratedGenerator.open(aMemberInterface);
    }

    @Override
    public void close(IMemberInterface aMemberInterface) {
        decoratedGenerator.close(aMemberInterface);
    }

    @Override
    public void open(IMemberGhost aMemberGhost) {
        decoratedGenerator.open(aMemberGhost);
    }

    @Override
    public void close(IMemberGhost aMemberGhost) {
        decoratedGenerator.close(aMemberGhost);
    }

    @Override
    public void open(IGetter aGetter) {
        decoratedGenerator.open(aGetter);
    }

    @Override
    public void close(IGetter aGetter) {
        decoratedGenerator.close(aGetter);
    }

    @Override
    public void open(ISetter aSetter) {
        decoratedGenerator.open(aSetter);
    }

    @Override
    public void close(ISetter aSetter) {
        decoratedGenerator.close(aSetter);
    }

    @Override
    public void open(IDelegatingMethod aDelegatingMethod) {
        decoratedGenerator.open(aDelegatingMethod);
    }

    @Override
    public void close(IDelegatingMethod aDelegatingMethod) {
        decoratedGenerator.close(aDelegatingMethod);
    }

    @Override
    public void visit(IField aField) {
        decoratedGenerator.visit(aField);
    }

    @Override
    public void visit(IMethodInvocation aMethodInvocation) {
        decoratedGenerator.visit(aMethodInvocation);
    }

    @Override
    public void visit(IParameter aParameter) {
        decoratedGenerator.visit(aParameter);
    }

    @Override
    public void visit(IAggregation anAggregation) {
        decoratedGenerator.visit(anAggregation);
    }

    @Override
    public void visit(IAssociation anAssociation) {
        decoratedGenerator.visit(anAssociation);
    }

    @Override
    public void visit(IComposition aComposition) {
        decoratedGenerator.visit(aComposition);
    }

    @Override
    public void visit(IContainerAggregation aContainerAggregation) {
        decoratedGenerator.visit(aContainerAggregation);
    }

    @Override
    public void visit(IContainerComposition aContainerComposition) {
        decoratedGenerator.visit(aContainerComposition);
    }

    @Override
    public void visit(ICreation aCreation) {
        decoratedGenerator.visit(aCreation);
    }

    @Override
    public void visit(IPrimitiveEntity aPrimitiveEntity) {
        decoratedGenerator.visit(aPrimitiveEntity);
    }

    @Override
    public void visit(IUseRelationship aUse) {
        decoratedGenerator.visit(aUse);
    }

    @Override
    public String getCode() {
        return decoratedGenerator.getCode();
    }

    @Override
    public String getName() {
        return decoratedGenerator.getName();
    }

    @Override
    public void reset() {
        decoratedGenerator.reset();
    }

    @Override
    public void unknownConstituentHandler(String aCalledMethodName, IConstituent aConstituent) {
        decoratedGenerator.unknownConstituentHandler(aCalledMethodName, aConstituent);
    }
}