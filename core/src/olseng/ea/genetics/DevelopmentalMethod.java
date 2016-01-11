package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public interface DevelopmentalMethod<G extends Genotype, P extends Phenotype> {

    /**
     * This method should take a genotype and develop it to the corresponding phenotype.
     * @param genotype - The genotype to be developed.
     * @return - The phenotype developed from the genotype.
     */
    public P develop(G genotype);

}
