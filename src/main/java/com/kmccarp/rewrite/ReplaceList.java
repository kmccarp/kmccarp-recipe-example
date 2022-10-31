package com.kmccarp.rewrite;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.ChangeType;

public class ReplaceList extends Recipe {

    @Override
    public String getDisplayName() {
        return "Replace List with List`Version`";
    }

    @Override
    public String getDescription() {
        return "Replaces the class java.util.List with java.util.List`Version`";
    }

    @Override
    protected TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ChangeType("java.util.List", "java.util.List1", true).getVisitor();
    }
}
