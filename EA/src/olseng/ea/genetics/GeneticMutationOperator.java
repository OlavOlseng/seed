package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class GeneticMutationOperator<G extends Genotype> extends GeneticOperator {

    public GeneticMutationOperator(double weight) {
        super(weight);
    }

    /**
     * The method should store mutate a parent and return a new child.
     * PS: REMEMBER TO TAKE A DEEP COPY OF THE PARENT DATA!
     * @param parent
     */
    public abstract G mutate(G parent);

}
