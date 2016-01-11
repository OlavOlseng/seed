package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public interface GeneticMutator<G extends Genotype> {

    /**
     * The method should store the mutation or the parent genotype data in the child genotype.
     * @param parent
     * @param child
     */
    public void mutate(G parent, G child);

}
