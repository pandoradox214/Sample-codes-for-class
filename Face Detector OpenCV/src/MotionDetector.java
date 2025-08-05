import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

public class MotionDetector {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        VideoCapture capture = new VideoCapture(0);

        if (!capture.isOpened()) {
            System.out.println("Cannot open camera.");
            return;
        }

        Mat frame1 = new Mat();
        Mat frame2 = new Mat();
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();
        Mat diff = new Mat();
        Mat thresh = new Mat();

        // Capture initial frames
        capture.read(frame1);
        capture.read(frame2);

        while (true) {
            // Convert both frames to grayscale
            Imgproc.cvtColor(frame1, gray1, Imgproc.COLOR_BGR2GRAY);
            Imgproc.cvtColor(frame2, gray2, Imgproc.COLOR_BGR2GRAY);

            // Blur to reduce noise and improve accuracy
            Imgproc.GaussianBlur(gray1, gray1, new Size(21, 21), 0);
            Imgproc.GaussianBlur(gray2, gray2, new Size(21, 21), 0);

            // Compute difference between frames
            Core.absdiff(gray1, gray2, diff);

            // Apply threshold to highlight significant differences
            Imgproc.threshold(diff, thresh, 25, 255, Imgproc.THRESH_BINARY);

            // Display the result
            HighGui.imshow("Motion Detection", thresh);

            // Press 'q' to quit
            if (HighGui.waitKey(30) == 'q') {
                break;
            }

            // Move to next frame
            frame1 = frame2.clone();
            capture.read(frame2);
        }

        capture.release();
        HighGui.destroyAllWindows();
    }
}
