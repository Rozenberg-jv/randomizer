package by.kolbun.randomizer;

import java.util.*;
import java.util.function.BiFunction;

public class ChanceMap<T> {

	private final Map<T, Integer> weights = new HashMap<>();
	private final Map<T, List<Modifier>> modifiers = new HashMap<>();
	private final Random R = new Random();
	private final Chance chance = new Chance();

	public void setChance(T obj, int weight) {

		weights.put(obj, weight);

		chance.recalculate();
	}

	public void setModifier(T obj, ModifierType type, double value) {

		if (modifiers.containsKey(obj)) // TODO: 11/11/2019 не разрешена коллизия и вопрос удаления, а также очередность использования
			modifiers.get(obj).add(new Modifier(type, value));
		else
			modifiers.put(obj, new ArrayList<>() {{
				new Modifier(type, value);
			}});

		chance.recalculate();
	}

	public T roll() {

		if (weights.isEmpty())
			return null;

		double rand = R.nextDouble();

		int i = 0;
		for (; i < chance.chances.size(); i++) {
			if (rand < chance.chances.get(i)) {
				break;
			}
		}

		return chance.objects.get(i);
	}

	private class Chance {

		private final List<T> objects = new ArrayList<>();
		private final List<Double> chances = new ArrayList<>();

		public void recalculate() {

			objects.clear();
			chances.clear();

			//			double sum = weights.values().stream().mapToInt(x -> x).sum();
			double sum = calcSum();

			// переделать применение модификаторов шанса
			/*for (Map.Entry<T, Integer> e : entries) {
				T key = e.getKey();

				if (!modifiers.containsKey(key))
					sum += e.getValue();
				else {
					sum = e.getValue();
					for (Modifier m : modifiers.get(key))
						sum += m.applyToValue(sum);
				}
			}*///end

			double prev = 0;
			double delta;

			for (Map.Entry<T, Integer> entry : weights.entrySet()) {

				delta = entry.getValue() / sum;

				objects.add(entry.getKey());
				chances.add(prev + delta);

				prev += delta;
			}
		}

		private double calcSum() {

			double sum = 0;

			for (Map.Entry<T, Integer> e : weights.entrySet()) {
				T key = e.getKey();

				if (!modifiers.containsKey(key))
					sum += e.getValue();
				else {
					sum = e.getValue();
					for (Modifier m : modifiers.get(key))
						sum += m.applyToValue(sum);
				}
			}

			return sum;
		}
	}

}

