package olseng.ea;

import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

/**
 * Created by olavo on 2016-01-11.
 */
public class EA<G extends Genotype, P extends Phenotype> {

    public OperatorPool<G> operatorPool;
    public DevelopmentalMethod<G, P> developmentalMethod;

    public Population<P> population;

}
