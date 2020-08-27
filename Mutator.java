package xxx;

public interface Mutator {

	void set(String aKey, Object aValue);

	void setIfNull(String aKey, Object aValue);

	long increment(String aKey);

}
