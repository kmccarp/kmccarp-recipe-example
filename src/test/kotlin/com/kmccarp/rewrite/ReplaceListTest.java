package com.kmccarp.rewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class ReplaceListTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new ReplaceList());
    }

    @Test
    void replacesListWithList1() {
        rewriteRun(
                java("""
                                package com.kmccarp.rewrite;
                                
                                import java.util.List;
                                                        
                                public class ReplaceListTest {
                                    public void test() {
                                        List<String> list = List.of("a", "b", "c");
                                    }
                                }
                                """,
                        """
                                package com.kmccarp.rewrite;
                                
                                import java.util.List1;
                                                        
                                public class ReplaceListTest {
                                    public void test() {
                                        List1<String> list = List1.of("a", "b", "c");
                                    }
                                }
                                        """)

        );
    }
}
