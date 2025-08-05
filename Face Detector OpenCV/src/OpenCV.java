import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

public class OpenCV {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load native OpenCV library
    }

    public static void main(String[] args) {
        // Load face detector
        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
        if (faceDetector.empty()) {
            System.out.println("Failed to load Haar Cascade.");
            return;
        }

        // Open webcam (device 0)
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.out.println("Cannot open the camera.");
            return;
        }

        Mat frame = new Mat();

        System.out.println("Starting live camera. Press ESC to quit.");

        while (true) {
            if (camera.read(frame)) {
                MatOfRect faces = new MatOfRect();
                faceDetector.detectMultiScale(frame, faces);

                for (Rect rect : faces.toArray()) {
                    Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 0), 2);
                }

                HighGui.imshow("Live Face Detection", frame);

                // Exit if ESC is pressed
                if (HighGui.waitKey(1) == 27) {
                    break;
                }
            }
        }
        camera.release();
        HighGui.destroyAllWindows();
    }
}
