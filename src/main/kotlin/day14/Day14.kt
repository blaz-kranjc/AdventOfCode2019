package day14

import util.sequence
import kotlin.math.sign

private const val ORE_INGREDIENT = "ORE"
internal const val FUEL_INGREDIENT = "FUEL"
internal const val AVAILABLE_ORE = 1000000000000L

fun parseIngredient(str: String): Pair<String, Long>? {
    val components = str.trim().split(' ')
    if (components.size != 2) return null
    val quantity = components[0].toLongOrNull() ?: return null
    return Pair(components[1], quantity)
}

fun parseRecipe(str: String): Pair<String, Pair<Long, List<Pair<String, Long>>>>? {
    val components = str.trim().split("=>")
    if (components.size != 2) return null
    val requirements = components[0].split(',').map { parseIngredient(it) }.sequence() ?: return null
    val product = parseIngredient(components[1]) ?: return null
    return product.first to Pair(product.second, requirements.toList())
}

fun parseRecipes(str: String): Map<String, Pair<Long, List<Pair<String, Long>>>>? =
    str.split('\n').map { parseRecipe(it) }.sequence()?.toMap()

fun requiredOre(
    ingredient: String,
    quantity: Long,
    recipes: Map<String, Pair<Long, List<Pair<String, Long>>>>,
    leftovers: Map<String, Long> = emptyMap()
): Pair<Long, Map<String, Long>> {
    if (ingredient == ORE_INGREDIENT)
        return Pair(quantity, leftovers)
    if (leftovers.getOrDefault(ingredient, 0) >= quantity)
        return Pair(0, leftovers + (ingredient to (leftovers.getOrDefault(ingredient, 0) - quantity)))

    val recipe = recipes[ingredient] ?: error("Unable to create this")
    val newRequired = quantity - leftovers.getOrDefault(ingredient, 0)
    val nRecipes = newRequired / recipe.first + (newRequired % recipe.first).sign
    val leftover = nRecipes * recipe.first - newRequired
    return recipe.second.fold(Pair(0L, leftovers + (ingredient to leftover))) { (ore, surplus), req ->
        val (newOre, newSurplus) = requiredOre(req.first, req.second * nRecipes, recipes, surplus)
        Pair(ore + newOre, newSurplus)
    }
}

tailrec fun findMaxTrue(nMin: Long, nMax: Long, op: (Long) -> Boolean): Long {
    val delta = nMax - nMin
    if (delta == 1L) return nMin
    val curr = (delta / 2L) + nMin
    return if (op(curr)) findMaxTrue(curr, nMax, op) else return findMaxTrue(nMin, curr, op)
}

fun maxFuelWithOre(ore: Long, recipes: Map<String, Pair<Long, List<Pair<String, Long>>>>): Long =
    findMaxTrue(0, ore) { requiredOre(FUEL_INGREDIENT, it, recipes).first < ore }

fun main() {
    val inp = object {}.javaClass.getResource("/day14/input.txt").readText().trim()
    val recipes = parseRecipes(inp) ?: return
    val fuelForOne = requiredOre(FUEL_INGREDIENT, 1, recipes).first
    println(fuelForOne)
    println(maxFuelWithOre(AVAILABLE_ORE, recipes))
}
