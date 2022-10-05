package com.kmccarp.rewrite;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.xml.Assertions.xml;

class FindDatesTest implements RewriteTest {

    @ParameterizedTest
    @CsvSource({"2020-01-01, 2020-01-01", "2020-01-01, 1577836800","1577836800, 2020-01-01", "1577836800, 1577836800"})
    void findsDatesWithYyyyMmDd(String inputDate, String xmlDate) {
        rewriteRun(
                spec -> spec.recipe(new FindDates(inputDate)),
                xml("" +
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                "<abc date=\"" + xmlDate + "\">blah</abc>",
                        "" +
                                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                "<abc <!--~~(Found date)~~>-->date=\"" + xmlDate + "\">blah</abc>"
                )
        );
    }


}
