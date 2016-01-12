package olseng.ea.core;

import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olavo on 2016-01-11.
 */
public class Population<P extends Phenotype> {

    private List<P> pool;

    /**
     * @param populationSize - Optimizes initial memory allocation.
     */
    public Population(int populationSize) {
        this.pool = new ArrayList<P>(populationSize);
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
            pool = pool.subList(0 ,maxSize);
        }
    }

    /**
     * Replaces all phenotypes in the current popultaion with the provided ones.
     * @param phenotypes - All phenotypes to inhabit the population.
     */
    public void setPopulation(List<P> phenotypes) {
        this.pool = phenotypes;
    }

    public Phenotype getIndividual(int index) {
        return pool.get(index);
    }
}
