package olseng.ea;

import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;

/**
 * Created by olavo on 2016-01-11.
 */
public class Population<P extends Phenotype> {

    private ArrayList<P> pool;

    /**
     * @param populationSize - Optimizes initial memory allocation.
     */
    public Population(int populationSize) {
        this.pool = new ArrayList<P>(populationSize);
    }

    public int getPopulationSize() {
        return pool.size();
    }


}
