package by.kolbun.randomizer;

class Modifier {

	private final ModifierType type;

	private final double mod;

	Modifier(ModifierType type, double mod) {

		this.type = type;
		this.mod = mod;
	}

	double applyToValue(double v) {

		return type.apply(mod, v);
	}
}
