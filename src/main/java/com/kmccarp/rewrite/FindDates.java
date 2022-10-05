package com.kmccarp.rewrite;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Option;
import org.openrewrite.Recipe;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.xml.XmlIsoVisitor;
import org.openrewrite.xml.tree.Xml;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

@Value
@EqualsAndHashCode(callSuper = false)
public class FindDates extends Recipe {
    @Override
    public String getDisplayName() {
        return "Renames variables with name `x` to `awesome`. Yeah!";
    }

    @Option(displayName = "Date",
            description = "The date to search for. Accepts both `yyyy-mm-dd` and epoch second formats.",
            example = "2020-01-01")
    String date;

    @Nullable
    private LocalDate getParsedDate() {
        LocalDate maybeDate = null;
        Instant maybeInstant = null;
        try {
            maybeInstant = Instant.ofEpochSecond(Long.parseLong(date));
        } catch (DateTimeParseException | NumberFormatException ignored) {}
        try {
            maybeDate = LocalDate.parse(date);
        } catch (DateTimeParseException ignored) {}
        return maybeDate != null ? maybeDate : maybeInstant != null ? maybeInstant.atOffset(ZoneOffset.UTC).toLocalDate() : null;
    }

    @Override
    protected XmlIsoVisitor<ExecutionContext> getVisitor() {
        return new XmlIsoVisitor<>() {
            @Override
            public Xml.Attribute visitAttribute(Xml.Attribute attribute, ExecutionContext ctx) {
                Xml.Attribute a = super.visitAttribute(attribute, ctx);
                LocalDate parsedDate = getParsedDate();
                if (parsedDate == null) {
                    return a;
                }
                // if attribute value matches date, add search marker
                String valueAsString = a.getValueAsString();
                if (valueAsString.equals(parsedDate.toString()) || valueAsString.equals(Long.toString(parsedDate.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC)))) {
                    return a.withMarkers(a.getMarkers().searchResult("Found date"));
                }
                return a;
            }
        };
    }
}
