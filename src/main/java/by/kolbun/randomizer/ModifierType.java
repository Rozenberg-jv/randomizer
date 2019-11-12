package by.kolbun.randomizer;

import java.util.function.BiFunction;

enum ModifierType {
	PERCENT((mod, value) -> mod * value),
	ABS_SUBT((mod, value) -> value - mod),
	ABS_PLUS((mod, value) -> value + mod);

	private final BiFunction<Double, Double, Double> func;

	ModifierType(BiFunction<Double, Double, Double> func) {

		this.func = func;
	}

	public double apply(double mod, double val) {

		return func.apply(mod, val);
	}
}