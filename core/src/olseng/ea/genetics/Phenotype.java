package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class Phenotype<T, G extends Genotype> implements Comparable {

    private G genotype = null;
    private T representation = null;

    private float[] fitnessValues;

    public Phenotype(int fitnessValueCount, G genotype) {
        this.fitnessValues = new float[fitnessValueCount];
        this.genotype = genotype;
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

    public void setFitnessValue(int ID, float value) {
        fitnessValues[ID] = value;
    }

    public float getFitnessValue(int ID) {
        return fitnessValues[ID];
    }
}
