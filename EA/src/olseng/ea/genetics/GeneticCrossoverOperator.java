package olseng.ea.genetics;

import java.util.Random;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class GeneticCrossoverOperator<G extends Genotype> extends GeneticOperator {

    public GeneticCrossoverOperator(double weight) {
        super(weight);
    }

    /**
     * This method should combine the genotype of parent1 and parent2, and return a child.
     * PS: REMEMBER TO TAKE A DEEP COPY OF THE PARENT DATA!
     * @param parent1 - First genotype to crossover
     * @param parent2 - Second genotype to crossover
     * @param rand - Random object that is native to the thread. Use this object when doing random calculations to optimize threading performance.
     */
    public abstract G crossover(G parent1, G parent2, Random rand);
}
