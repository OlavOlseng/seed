package olseng.ea;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.core.EA;
import olseng.ea.fitness.*;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for building an instance of the EA class.
 * Created by olavo on 2016-01-11.
 */
public class EAFactory<G extends Genotype, P extends Phenotype> {

    public OperatorPool operatorPool = null;
    public DevelopmentalMethod developmentalMethod = null;
    public List<FitnessObjective> objectives = null;
    public RankingModule rankingModule = null;
    public AdultSelector adultSelector = null;


    public EAFactory() {
        this.objectives = new ArrayList<>();
    }

    public void addFitnessObjective(FitnessObjective<P> objective) {
        this.objectives.add(objective);
    }

    public EA<G, P> build() {

        //Null checks to ensure proper initialization.
        if (operatorPool == null) {
            throw new NullPointerException("OperatorPool is null!");
        }
        if (developmentalMethod == null) {
            throw new NullPointerException("DevelopmentalMethod is null!");
        }
        if (objectives.size() < 1) {
            throw new InvalidStateException("No fitness objectives were added! At least one is needed for the algorithm to run.");
        }
        if (adultSelector == null) {
            throw new NullPointerException("No adult selector set!");
        }

        EA product = new EA();


        //Assemble the pieces, should ensure proper compatibility between modules.
        product.operatorPool = this.operatorPool;
        product.developmentalMethod = this.developmentalMethod;

        FitnessEvaluator evaluator = new StandardFitnessEvaluator();
        evaluator.setObjectives(objectives);
        product.fitnessEvaluator = evaluator;

        if(rankingModule == null) {
            product.rankingMode = false;
            product.sortingModule = new SingleFitnessComparator();
        }
        else {
            product.rankingMode = true;
            product.rankingModule = rankingModule;
        }

        product.adultSelector = adultSelector;

        return product;
    }
}
