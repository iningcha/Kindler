package Database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListTypeConverter {

    Gson gson = new Gson();

    @TypeConverter
    public ArrayList<Integer> stringToIntegerList(String data) {
        if (data == null) {
            ArrayList<Integer> a = new ArrayList<>();
            return a;
        }

        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String integerListToString(ArrayList<Integer> a) {
        return gson.toJson(a);
    }
}
