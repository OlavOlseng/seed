package olseng.ea;

import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * Helper class for building an instance of the EA class.
 * Created by olavo on 2016-01-11.
 */
public class EAFactory<G extends Genotype, P extends Phenotype> {

    public OperatorPool operatorPool = null;
    public DevelopmentalMethod developmentalMethod = null;

    public EA<G, P> build() throws InvalidStateException, NullPointerException {

        //Null checks to ensure proper initialization.
        if (operatorPool == null) {
            throw new NullPointerException("OperatorPool is null");
        }
        if (developmentalMethod == null) {
            throw new NullPointerException("DevelopmentalMethod is null");
        }

        EA product = new EA();

        //Assemble the pieces, should ensure proper compatibility between modules.

        product.operatorPool = this.operatorPool;
        product.developmentalMethod = this.developmentalMethod;

        return null;
    }
}
