package by.kolbun;

import by.kolbun.randomizer.ChanceMap;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		ChanceMap<Entity> chances = new ChanceMap<>();

		Entity rareEnt = new Entity("Rare");
		Entity middleEnt = new Entity("Middle");
		Entity oftenEnt = new Entity("Often");

		Map<Entity, Integer> results = new HashMap<>() {{
			put(rareEnt, 0);
			put(middleEnt, 0);
			put(oftenEnt, 0);
		}};

		chances.setChance(rareEnt, 10);
		chances.setChance(middleEnt, 50);
		chances.setChance(oftenEnt, 100);

		for (int i = 0; i < 100; i++) {

			Entity roll = chances.roll();
			results.computeIfPresent(roll, (k, v) -> v += 1);
		}

		results.forEach((key, value) -> System.out.printf("%7s : %d\n", key.getTitle(), value));
	}

}
