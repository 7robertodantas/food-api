package com.food.core.sales;

import com.food.core.facade.Discount;
import com.food.core.facade.Item;
import com.food.core.model.ItemContextImpl;
import com.food.core.model.ItemImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static com.food.core.utils.CollectionUtils.asSet;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class CompositionSaleStrategyTest {

    private Map<String, Double> pricePerIngredient;

    @BeforeEach
    private void setUp() {
        pricePerIngredient = new HashMap<>();
        pricePerIngredient.put("Bacon", 2.0);
    }

    @DisplayName("Should get the correct discount")
    @ParameterizedTest(name = "{index} => For an item having {1} and not having {2}, given {0} with net price of {3} should expect discount of {5}")
    @MethodSource("ingredientCompositionProvider")
    public void testApply(final Item item,
                          final Set<String> shouldHave,
                          final Set<String> shouldNotHave,
                          final double itemCostValue,
                          final double percentage,
                          final double expectedDiscount) {


        final CompositionSaleStrategy strategy = new CompositionSaleStrategy("test", "sale strategy", percentage, shouldHave, shouldNotHave);
        final Optional<Discount> discount = strategy.apply(new ItemContextImpl(item, itemCostValue, pricePerIngredient));

        if (expectedDiscount > 0.0) {
            assertThat(discount).isPresent();
            assertThat(discount.get().getValue()).isEqualTo(expectedDiscount);
        } else {
            assertThat(discount).isEmpty();
        }
    }

    private static Stream<Arguments> ingredientCompositionProvider() {
        final double percentage = 0.1;
        return Stream.of(
                // order items, should have, should not have,
                Arguments.of(new ItemImpl("X-Bacon", singletonList("Bacon"), asSet("test")), emptySet(), emptySet(), 10, percentage, 0.0), // no elements expected should return no discount
                Arguments.of(new ItemImpl("X-Bacon", singletonList("Bacon"), asSet("test")), setOf("Bacon"), emptySet(), 10, percentage, 10 * percentage), // should have one element
                Arguments.of(new ItemImpl("X-Bacon", singletonList("Bacon"), asSet("test")), emptySet(), setOf("Bacon"), 10, percentage, 0.0), // should not have one element
                Arguments.of(new ItemImpl("X-Bacon", singletonList("Bread"), asSet("test")), emptySet(), setOf("Bacon"), 10, percentage, 10 * percentage) // should not have one element
        );
    }

    private static Set<String> setOf(final String... values) {
        return Arrays.stream(values).collect(toSet());
    }

}