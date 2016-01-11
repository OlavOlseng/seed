package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public interface GeneticCrossoverOperator<G extends Genotype> {

    /**
     * This method should combine the genotype of parent1 and parent2, and store it in the child container.
     * @param parent1 - First genotype to crossover
     * @param parent2 - Second genotype to crossover
     * @param child - The target container for the new genotype.
     */
    public void crossover(G parent1, G parent2, G child);

}
