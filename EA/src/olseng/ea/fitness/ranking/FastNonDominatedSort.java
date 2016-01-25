package olseng.ea.fitness.ranking;

import olseng.ea.core.Population;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 25.01.2016.
 */
public class FastNonDominatedSort implements RankingModule {

    @Override
    public void rankPopulation(Population population) {
        List<Phenotype> currentFront = new ArrayList<>();
        for (int i = 0; i < population.getPopulationSize(); i++) {
            Phenotype p = population.getIndividual(i);
            p.dominatedByCount = 0;
            p.dominatedSet = new ArrayList<Phenotype>();

            for (int j = 0; j < population.getPopulationSize(); j++) {
                if (i == j) {
                    continue;
                }
                Phenotype q = population.getIndividual(j);
                if (dominates(p, q)) {
                    p.dominatedSet.add(q);
                }
                else if (dominates(q, p)) {
                    p.dominatedByCount++;
                }
            }
            if (p.dominatedByCount == 0) {
                currentFront.add(p);
                p.setRank(1);
            }
        }

        int frontCounter = 1;
        while (currentFront.size() > 0) {
            List<Phenotype> nextFront = new ArrayList<>();

            for (Phenotype p : currentFront) {

                for (int i = 0; i < p.dominatedSet.size(); i++) {
                    Phenotype q = p.getDominatedPhenotype(i);
                    q.dominatedByCount--;

                    if (q.dominatedByCount == 0) {
                        nextFront.add(q);
                        q.setRank(frontCounter + 1);
                    }
                }
            }
            frontCounter++;
            currentFront = nextFront;
        }
    }

    public boolean dominates(Phenotype p1, Phenotype p2) {
        for (int i = 0; i < p1.getFitnessCount(); i++) {
            if (p1.getFitnessValue(i) < p2.getFitnessValue(i)) {
                return false;
            }
        }
        return true;
    }
}
