package olseng.ea.fitness;

import olseng.ea.core.Population;

/**
 * Created by Olav on 11.01.2016.
 */
public interface RankingModule {

    /**
     * This function should assign a rank to every phenotype in the population.
     */
    void rankPopulation(Population population);

}
