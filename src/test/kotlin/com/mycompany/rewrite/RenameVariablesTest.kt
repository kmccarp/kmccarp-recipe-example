/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany.rewrite

import org.junit.jupiter.api.Test
import org.openrewrite.Recipe
import org.openrewrite.java.JavaRecipeTest

class RenameVariablesTest : JavaRecipeTest {
    override val recipe: Recipe
        get() = RenameVariables()

    @Test
    fun renamex() = assertChanged(
            before = """
            class Test {
                void test() {
                    int x = 0;
                }
            }
        """,
            after = """
            class Test {
                void test() {
                    int awesome = 0;
                }
            }
        """,
        cycles = 1, expectedCyclesThatMakeChanges = 1
    )
}