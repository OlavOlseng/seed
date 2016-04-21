package olseng.ea.fitness.ranking;

import olseng.ea.core.Population;
import olseng.ea.fitness.AscendingSingleFitnessComparator;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Olav on 25.01.2016.
 */
public class FastNonDominatedSort implements RankingModule {

    public boolean allowFitnessDuplicates = false;

    @Override
    public void rankPopulation(Population population) {

        if (!allowFitnessDuplicates) {
            eliminateFitnessDuplicates(population);
        }
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
        calculateCrowdingDistances(currentFront);

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
            calculateCrowdingDistances(currentFront);
            frontCounter++;
            currentFront = nextFront;
        }
    }

    public void calculateCrowdingDistances(List<Phenotype> front) {
        if (front.size() < 3) {
            return;
        }
        int objectiveCount = front.get(0).getFitnessCount();
        AscendingSingleFitnessComparator comparator = new AscendingSingleFitnessComparator(0);
        for (Phenotype p : front) {
            p.crowdingDistance = 0;
        }
        for (int objective = 0; objective < objectiveCount; objective++) {
            float min = Float.MAX_VALUE;
            float max = Float.MIN_VALUE;

            for (Phenotype p : front) {
                float fitness = p.getFitnessValue(objective);
                if (fitness < min) {
                    min = fitness;
                }
                if (fitness > max) {
                    max = fitness;
                }
            }
            float range = max - min;
            if (range == 0) {
                continue;
            }
            comparator.setFitnessIndex(objective);
            Collections.sort(front, comparator);
            front.get(0).crowdingDistance = 1000000;
            front.get(front.size() - 1).crowdingDistance = 1000000;

            for (int i = 1; i < front.size() - 1; i++) {
                front.get(i).crowdingDistance += (front.get(i + 1).getFitnessValue(objective) - front.get(i - 1).getFitnessValue(objective)) / range;
            }
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

    public void eliminateFitnessDuplicates(Population population) {
        if (population.getPopulationSize() < 2) {
            return;
        }

        ArrayList<Phenotype> removalFlagged = new ArrayList<>();
        for (int i = 0; i < population.getPopulationSize() - 1; i++) {
            Phenotype p = population.getIndividual(i);

            for (int j = i + 1; j < population.getPopulationSize(); j++) {
                if (i == j) {
                    continue;
                }
                Phenotype q = population.getIndividual(j);
                if (isFitnessEqual(p, q)) {
                    removalFlagged.add(q);
                    break;
                }
            }
        }
        for (int i = 0; i < removalFlagged.size(); i++) {
            population.removeIndividual(removalFlagged.get(i));
        }
    }

    public boolean isFitnessEqual(Phenotype p1, Phenotype p2) {
        for (int i = 0; i < p1.getFitnessCount(); i++) {
            if (p1.getFitnessValue(i) != p2.getFitnessValue(i)) {
                return false;
            }
        }
        return true;
    }
}
