package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class Phenotype<T, G extends Genotype> {

    private G genotype = null;
    private T representation = null;

    private float[] fitnessValues;
    private int rank = Integer.MAX_VALUE;

    public Phenotype(G genotype) {
        this.genotype = genotype;
    }

    public void initFitnessValues(int valueCount) {
        this.fitnessValues = new float[valueCount];
    }

    public void setRepresentation(T representation) {
        this.representation = representation;
    }

    public T getRepresentation() {
        return representation;
    }

    public G getGenotype() {
        return this.genotype;
    }

    public int getFitnessCount() {
        return fitnessValues.length;
    }

    public void setFitnessValue(int index, float value) {
        fitnessValues[index] = value;
    }

    public float getFitnessValue(int index) {
        return fitnessValues[index];
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }
}
