package android.example.com.codechallenge003.Utils;


import android.net.Uri;
import java.util.Locale;

public final class Utils {

    public static String buildThumbnailPath(String path) {
        return buildImagePath("w92", path);
    }

    public static String buildPosterPath(String path) {
        return buildImagePath("w154", path);
    }

    private static String buildImagePath(String size, String path) {
        return new Uri.Builder()
                .scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath(size)
                .appendEncodedPath(path)
                .build()
                .toString();
    }

    public static String DoubleToString(Double number) {
        return String.format(Locale.US, "%.2f", number);
    }
}
