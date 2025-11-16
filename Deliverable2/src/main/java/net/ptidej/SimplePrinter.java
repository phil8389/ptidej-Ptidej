package net.ptidej;

import padl.kernel.*;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;

/**
 * Simple printer that was generated with the help of Claude.ai
 */
public class SimplePrinter implements IGenerator {
    protected final StringBuffer output = new StringBuffer();
    private IFirstClassEntity currentEntity;
    private int indentLevel = 0;

    public SimplePrinter() {
    }

    // Helper method to print with indentation
    private void print(String message) {
        String indent = "  ".repeat(indentLevel);
        output.append(indent).append(message).append("\n");
    }

    // Open/Close methods - can be used by DFS

    @Override
    public void open(IAbstractModel anAbstractModel) {
        print("=== MODEL: " + anAbstractModel.getDisplayName() + " ===");
        indentLevel++;
    }

    @Override
    public void close(IAbstractModel anAbstractModel) {
        indentLevel--;
        print("=== END MODEL ===");
    }

    @Override
    public void open(IClass aClass) {
        currentEntity = aClass;
        String className = String.valueOf(aClass.getName());
        if (aClass.isAbstract()) {
            print("ABSTRACT CLASS: " + className);
        } else {
            print("CLASS: " + className);
        }
        indentLevel++;
    }

    @Override
    public void close(IClass aClass) {
        indentLevel--;
        currentEntity = null;
        print("END CLASS: " + aClass.getDisplayName());
    }

    @Override
    public void open(IInterface anInterface) {
        print("INTERFACE: " + anInterface.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IInterface anInterface) {
        indentLevel--;
        print("END INTERFACE: " + anInterface.getDisplayName());
    }

    @Override
    public void open(IConstructor aConstructor) {
        print("CONSTRUCTOR: " + aConstructor.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IConstructor aConstructor) {
        indentLevel--;
    }

    @Override
    public void open(IDelegatingMethod aDelegatingMethod) {
        print("DELEGATING METHOD: " + aDelegatingMethod.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IDelegatingMethod aDelegatingMethod) {
        indentLevel--;
    }

    @Override
    public void open(IGetter aGetter) {
        print("GETTER: " + aGetter.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IGetter aGetter) {
        indentLevel--;
    }

    @Override
    public void open(IGhost aGhost) {
        print("GHOST: " + aGhost.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IGhost aGhost) {
        indentLevel--;
    }

    @Override
    public void open(IMemberClass aMemberClass) {
        print("MEMBER CLASS: " + aMemberClass.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IMemberClass aMemberClass) {
        indentLevel--;
    }

    @Override
    public void open(IMemberGhost aMemberGhost) {
        print("MEMBER GHOST: " + aMemberGhost.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IMemberGhost aMemberGhost) {
        indentLevel--;
    }

    @Override
    public void open(IMemberInterface aMemberInterface) {
        print("MEMBER INTERFACE: " + aMemberInterface.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IMemberInterface aMemberInterface) {
        indentLevel--;
    }

    @Override
    public void open(IMethod aMethod) {
        print("METHOD: " + aMethod.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IMethod aMethod) {
        indentLevel--;
    }

    @Override
    public void open(IPackage aPackage) {
        print("PACKAGE: " + aPackage.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IPackage aPackage) {
        indentLevel--;
    }

    @Override
    public void open(IPackageDefault aPackage) {
        print("PACKAGE (DEFAULT): " + aPackage.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(IPackageDefault aPackage) {
        indentLevel--;
    }

    @Override
    public void open(ISetter aSetter) {
        print("SETTER: " + aSetter.getDisplayName());
        indentLevel++;
    }

    @Override
    public void close(ISetter aSetter) {
        indentLevel--;
    }

    // Visit methods - used by BFS and Selective

    @Override
    public void visit(IField aField) {
        String visibility = getVisibilitySymbol(aField.getVisibility());
        String fieldName = String.valueOf(aField.getDisplayName());
        String fieldType = String.valueOf(aField.getType());
        print("FIELD: " + visibility + fieldName + " : " + fieldType);
    }

    private String getVisibilitySymbol(int visibility) {
        switch (visibility) {
            case 1: return "+"; // public
            case 2: return "-"; // private
            case 4: return "#"; // protected
            default: return "~"; // package/default
        }
    }

    @Override
    public void visit(IAggregation anAggregation) {
        if (currentEntity != null) {
            print("AGGREGATION: " + currentEntity.getDisplayName() +
                    " o-- " + anAggregation.getTargetEntity().getDisplayName());
        }
    }

    @Override
    public void visit(IAssociation anAssociation) {
        if (currentEntity != null) {
            print("ASSOCIATION: " + currentEntity.getDisplayName() +
                    " -- " + anAssociation.getTargetEntity().getDisplayName());
        }
    }

    @Override
    public void visit(IComposition aComposition) {
        if (currentEntity != null) {
            print("COMPOSITION: " + currentEntity.getDisplayName() +
                    " *-- " + aComposition.getTargetEntity().getDisplayName());
        }
    }

    @Override
    public void visit(IContainerAggregation aContainerAggregation) {
        print("CONTAINER AGGREGATION: " + aContainerAggregation.getDisplayName());
    }

    @Override
    public void visit(IContainerComposition aContainerComposition) {
        print("CONTAINER COMPOSITION: " + aContainerComposition.getDisplayName());
    }

    @Override
    public void visit(ICreation aCreation) {
        print("CREATION: " + aCreation.getDisplayName());
    }

    @Override
    public void visit(IMethodInvocation aMethodInvocation) {
        print("METHOD INVOCATION: " + aMethodInvocation.getDisplayName());
    }

    @Override
    public void visit(IParameter aParameter) {
        print("PARAMETER: " + aParameter.getDisplayName());
    }

    @Override
    public void visit(IPrimitiveEntity aPrimitiveEntity) {
        print("PRIMITIVE ENTITY: " + aPrimitiveEntity.getDisplayName());
    }

    @Override
    public void visit(IUseRelationship aUse) {
        print("USE RELATIONSHIP: " + aUse.getDisplayName());
    }

    // Additional visit methods (not in IGenerator interface)

    public void visit(IPackageGhost aPackageGhost) {
        print("PACKAGE GHOST: " + aPackageGhost.getDisplayName());
    }

    public void visit(IPackage aPackage) {
        print("PACKAGE: " + aPackage.getDisplayName());
    }

    public void visit(IGhost aGhost) {
        print("GHOST: " + aGhost.getDisplayName());
    }

    public void visit(IConstructor aConstructor) {
        print("CONSTRUCTOR: " + aConstructor.getDisplayName());
    }

    public void visit(IMethod aMethod) {
        print("METHOD: " + aMethod.getDisplayName());
    }

    public void visit(IInterface anInterface) {
        print("INTERFACE: " + anInterface.getDisplayName());
    }

    public void visit(IClass aClass) {
        String className = String.valueOf(aClass.getName());
        if (aClass.isAbstract()) {
            print("ABSTRACT CLASS: " + className);
        } else {
            print("CLASS: " + className);
        }
    }

    public void visit(IMemberClass aMemberClass) {
        print("MEMBER CLASS: " + aMemberClass.getDisplayName());
    }

    public void visit(IMemberGhost aMemberGhost) {
        print("MEMBER GHOST: " + aMemberGhost.getDisplayName());
    }

    public void visit(IMemberInterface aMemberInterface) {
        print("MEMBER INTERFACE: " + aMemberInterface.getDisplayName());
    }

    public void visit(IGetter aGetter) {
        print("GETTER: " + aGetter.getDisplayName());
    }

    public void visit(ISetter aSetter) {
        print("SETTER: " + aSetter.getDisplayName());
    }

    public void visit(IDelegatingMethod aDelegatingMethod) {
        print("DELEGATING METHOD: " + aDelegatingMethod.getDisplayName());
    }

    public void visit(IPackageDefault aPackageDefault) {
        print("PACKAGE (DEFAULT): " + aPackageDefault.getDisplayName());
    }

    @Override
    public String getName() {
        return "SimplePrinter";
    }

    @Override
    public void reset() {
        output.setLength(0);
        currentEntity = null;
        indentLevel = 0;
    }

    @Override
    public void unknownConstituentHandler(final String aCalledMethodName,
                                          final IConstituent aConstituent) {
        ProxyConsole.getInstance().debugOutput()
                .print(this.getClass().getName());
        ProxyConsole.getInstance().debugOutput()
                .print(" does not know what to do for \"");
        ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
        ProxyConsole.getInstance().debugOutput().print("\" (");
        ProxyConsole.getInstance().debugOutput()
                .print(aConstituent.getDisplayID());
        ProxyConsole.getInstance().debugOutput().println(')');
    }

    @Override
    public String getCode() {
        return output.toString();
    }
}