import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load native OpenCV library
        System.out.println(Core.VERSION);
        System.out.println("Java version: " + System.getProperty("java.version"));
    }
}