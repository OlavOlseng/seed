package olseng.ea.genetics;

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
     */
    public abstract G crossover(G parent1, G parent2);
}
