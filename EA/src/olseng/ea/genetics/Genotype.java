package olseng.ea.genetics;

/**
 * Created by olavo on 2016-01-11.
 */
public abstract class Genotype<T> {

    private T data = null;

    /**
     * this method must return a deep copy of the data. Returning a shallow copy will break the implementation!
     * @return A deep copy of the genotype data.
     */
    public abstract T getDeepCopy();

    public void setData(T data) {
        this.data = data;
        parseData(this.data);
    }

    /**
     * Any additional calculations or setup when the data is set for the genotype should be changed here.
     * @param data
     */
    public abstract void parseData(T data);

    public T getData() {
        return this.data;
    }

}
