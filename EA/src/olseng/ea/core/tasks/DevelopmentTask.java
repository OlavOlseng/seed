package olseng.ea.core.tasks;

import olseng.ea.core.EA;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 12.01.2016.
 */
public class DevelopmentTask extends BaseTask<List<Phenotype>>{

    private final List<Genotype> genotypes;
    private final int start;
    private final int end;

    public DevelopmentTask(EA ea, int start, int end, List<Genotype> genotypes) {
        super(ea);
        this.genotypes = genotypes;
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Phenotype> call() throws Exception {
        List<Phenotype> phenotypes = new ArrayList<>(end - start);

        for (int i = start; i < end; i++) {
            Phenotype p = ea.developmentalMethod.develop(genotypes.get(i));
            phenotypes.add(p);
        }
        return phenotypes;
    }
}
