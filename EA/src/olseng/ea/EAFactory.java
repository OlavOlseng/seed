package olseng.ea;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.core.EA;
import olseng.ea.core.MaxGenerationCondition;
import olseng.ea.core.TerminationCondition;
import olseng.ea.fitness.*;
import olseng.ea.fitness.ranking.RankComparator;
import olseng.ea.fitness.ranking.RankingModule;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Helper class for building an instance of the EA class.
 * Created by olavo on 2016-01-11.
 */
public class EAFactory<G extends Genotype, P extends Phenotype> {

    public OperatorPool operatorPool = null;
    public DevelopmentalMethod developmentalMethod = null;
    public List<FitnessObjective> objectives = null;
    public List<TerminationCondition> terminationConditions = null;
    public RankingModule rankingModule = null;
    public AdultSelector adultSelector = null;
    public Comparator<Phenotype> sortingModule = null;


    public EAFactory() {
        this.objectives = new ArrayList<>();
        this.terminationConditions = new ArrayList<>();
    }

    public void addFitnessObjective(FitnessObjective<P> objective) {
        this.objectives.add(objective);
    }

    public void addTerminationCondition(TerminationCondition terminationCondition) {
        this.terminationConditions.add(terminationCondition);
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

        product.terminationConditions.addAll(this.terminationConditions);

        if(rankingModule != null) {
            product.rankingMode = true;
            product.rankingModule = rankingModule;
            product.sortingModule = new RankComparator();
        }
        else if (sortingModule == null){
            throw new InvalidStateException("No ranking or sorting module was provided.");
        }
        else {
            product.rankingMode = false;
            product.sortingModule = sortingModule;
        }

        product.adultSelector = adultSelector;

        return product;
    }
}
