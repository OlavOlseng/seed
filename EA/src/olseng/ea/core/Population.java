package olseng.ea.core;

import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by olavo on 2016-01-11.
 */
public class Population {

    private List<Phenotype> pool;

    /**
     * @param populationSize - Optimizes initial memory allocation.
     */
    public Population(int populationSize) {
        this.pool = new ArrayList<>(populationSize);
    }

    public int getPopulationSize() {
        return pool.size();
    }

    /**
     * Culls all individuals with index > maxSize.
     * @param maxSize - All entries greater than this will be removed from the list.
     */
    public void cullPopulation(int maxSize) {
        if (getPopulationSize() > maxSize) {
            pool.subList(maxSize, pool.size()).clear();
        }
    }

    /**
     * Replaces all phenotypes in the current popultaion with the provided ones.
     * @param phenotypes - All phenotypes to inhabit the population.
     */
    public void setPopulation(List<Phenotype> phenotypes) {
        this.pool = phenotypes;
    }

    public Phenotype getIndividual(int index) {
        return pool.get(index);
    }

    public void removeIndividual(Phenotype p) {
        pool.remove(p);
    }

    public void merge(List<Phenotype> newIndividuals) {
        pool.addAll(newIndividuals);
    }

    public void sort(Comparator<Phenotype> comparator) {
        Collections.sort(pool, comparator);
    }
}
