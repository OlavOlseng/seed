package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class Genotype<T> {

    private T data = null;
    private int geneCount = 0;

    /**
     * This method should calculate the genes in the genome.
     * @param data
     */
    public abstract int calculateGeneCount(T data);

    /**
     * this method must return a deep copy of the data. Returning a shallow copy will break the implementation!
     * @return A deep copy of the genotype data.
     */
    public abstract Genotype<T> getDeepCopy();

    public void setData(T data) {
        this.data = data;
        this.geneCount = calculateGeneCount(data);
    }

    public T getData() {
        return this.data;
    }

    public int getGeneCount() {
        return this.geneCount;
    }
}
