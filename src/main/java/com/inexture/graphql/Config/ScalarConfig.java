package com.inexture.graphql.Config;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.regex.Pattern;


@Configuration
public class ScalarConfig {

    @Bean
    public RuntimeWiringConfigurer date() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
    }

    @Bean
    public RuntimeWiringConfigurer dateTime() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }

    @Bean
    public RuntimeWiringConfigurer localTime() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.LocalTime);
    }

    @Bean
    public RuntimeWiringConfigurer email() {
        return wiringBuilder -> wiringBuilder.scalar(EmailScalar.email);
    }

    public static class EmailScalar {

        public static final GraphQLScalarType email = GraphQLScalarType.newScalar()
                .name("email")
                .description("A custom scalar that handles emails")
                .coercing(new Coercing() {
                    @Override
                    public Object serialize(Object dataFetcherResult) {
                        return serializeEmail(dataFetcherResult);
                    }

                    @Override
                    public Object parseValue(Object input) {
                        return parseEmailFromVariable(input);
                    }

                    @Override
                    public Object parseLiteral(Object input) {
                        return parseEmailFromAstLiteral(input);
                    }
                })
                .build();

        private static boolean looksLikeAnEmailAddress(String possibleEmailValue) {
            // ps.  I am not trying to replicate RFC-3696 clearly
            return Pattern.matches("^(.+)@(.+)$", possibleEmailValue);
        }

        private static Object serializeEmail(Object dataFetcherResult) {
            String possibleEmailValue = String.valueOf(dataFetcherResult);
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            } else {
                throw new CoercingSerializeException("Unable to serialize " + possibleEmailValue + " as an email address");
            }
        }

        private static Object parseEmailFromVariable(Object input) {
            if (input instanceof String) {
                String possibleEmailValue = input.toString();
                if (looksLikeAnEmailAddress(possibleEmailValue)) {
                    return possibleEmailValue;
                }
            }
            throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
        }

        private static Object parseEmailFromAstLiteral(Object input) {
            if (input instanceof StringValue) {
                String possibleEmailValue = ((StringValue) input).getValue();
                if (looksLikeAnEmailAddress(possibleEmailValue)) {
                    return possibleEmailValue;
                }
            }
            throw new CoercingParseLiteralException(
                    "Value is not any email address : '" + String.valueOf(input) + "'"
            );
        }
    }
}
