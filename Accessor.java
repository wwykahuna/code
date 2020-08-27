package xxx;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Accessor {

	<T> T get(Object aKey);

	boolean containsKey(Object aKey);

	<T> T get(Object aKey, Class<T> aReturnType);

	<T> T get(Object aKey, Class<T> aReturnType, T aDefaultValue);

	String getString(Object aKey);

	<T> T[] getArray(Object aKey, Class<T> aElementType);

	Map<String, Object> getMap(Object aKey);

	Map<String, Object> getMap(Object aKey, Map<String, Object> aDefault);

	String getRequiredString(Object aKey);

	<T> T getRequired(Object aKey);

	<T> T getRequired(Object aKey, Class<T> aValueType);

	String getString(Object aKey, String aDefaultValue);

	<T> List<T> getList(Object aKey, Class<T> aElementType);

	<T> List<T> getList(Object aKey, Class<T> aElementType, List<T> aDefaultValue);

	Long getLong(Object aKey);

	long getLong(Object aKey, long aDefaultValue);

	Double getDouble(Object aKey);

	Double getDouble(Object aKey, double aDefaultValue);

	Float getFloat(Object aKey);

	float getFloat(Object aKey, float aDefaultValue);

	Integer getInteger(Object aKey);

	int getInteger(Object aKey, int aDefaultValue);

	Boolean getBoolean(Object aKey);

	boolean getBoolean(Object aKey, boolean aDefaultValue);

	Date getDate(Object aKey);

	Duration getDuration(Object aKey);

	Duration getDuration(Object aKey, String aDefaultDuration);

	Map<String, Object> asMap();

}
