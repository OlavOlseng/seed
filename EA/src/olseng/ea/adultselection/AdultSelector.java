package olseng.ea.adultselection;

import olseng.ea.core.Population;
import olseng.ea.genetics.Phenotype;

/**
 * Created by Olav on 11.01.2016.
 */
public abstract class AdultSelector<P extends Phenotype> {

    protected Population population;

    public void setPopulation(Population p) {
        this.population = p;
    }

    /**
     * This method should return an individual from the population set by the selector.
     * @return
     */
    public abstract Phenotype getIndividual();

}
