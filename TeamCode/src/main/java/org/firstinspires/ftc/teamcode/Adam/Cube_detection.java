package org.firstinspires.ftc.teamcode.Adam;

import android.graphics.Canvas;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;

public class Cube_detection implements VisionProcessor {

    /*
     * These are our variables that will be
     * modifiable from the variable tuner.
     *
     * Scalars in OpenCV are generally used to
     * represent color. So our values in the
     * lower and upper Scalars here represent
     * the Y, Cr and Cb values respectively.
     *
     * YCbCr, like most color spaces, range
     * from 0-255, so we default to those
     * min and max values here for now, meaning
     * that all pixels will be shown.
     */
    public int color = 0;

    Scalar lower = new Scalar(0, 0, 75);
    Scalar upper = new Scalar(47, 150, 255);

    public int x = 0;
    public int x2 = 0;

    /**
     * This will allow us to choose the color
     * space we want to use on the live field
     * tuner instead of hardcoding it
     */
    public ColorSpace colorSpace = ColorSpace.RGB;

    /*
     * A good practice when typing EOCV pipelines is
     * declaring the Mats you will use here at the top
     * of your pipeline, to reuse the same buffers every
     * time. This removes the need to call mat.release()
     * with every Mat you create on the processFrame method,
     * and therefore, reducing the possibility of getting a
     * memory leak and causing the app to crash due to an
     * "Out of Memory" error.
     */
    private Mat ycrcbMat       = new Mat();
    private Mat binaryMat      = new Mat();
    private Mat maskedInputMat = new Mat();

    private Telemetry telemetry = null;

    /**
     * Enum to choose which color space to choose
     * with the live variable tuner isntead of
     * hardcoding it.
     */
    enum ColorSpace {
        /*
         * Define our "conversion codes" in the enum
         * so that we don't have to do a switch
         * statement in the processFrame method.
         */
        RGB(Imgproc.COLOR_RGBA2RGB),
        HSV(Imgproc.COLOR_RGB2HSV),
        YCrCb(Imgproc.COLOR_RGB2YCrCb),
        Lab(Imgproc.COLOR_RGB2Lab);

        //store cvtCode in a public var
        public int cvtCode = 0;

        //constructor to be used by enum declarations above
        ColorSpace(int cvtCode) {
            this.cvtCode = cvtCode;
        }
    }

    public Cube_detection(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        if(color == 0) {
            lower = new Scalar(0, 0, 75);
            upper = new Scalar(47, 150, 255);
        }
        else {
            lower = new Scalar(72, 0, 0);
            upper = new Scalar(255, 150, 48);
        }

        /*
         * Converts our input mat from RGB to
         * specified color space by the enum.
         * EOCV ALWAYS returns RGB mats, so you'd
         * always convert from RGB to the color
         * space you want to use.
         *
         * Takes our "input" mat as an input, and outputs
         * to a separate Mat buffer "ycrcbMat"
         */
        Imgproc.cvtColor(frame, ycrcbMat, colorSpace.cvtCode);

        /*
         * This is where our thresholding actually happens.
         * Takes our "ycrcbMat" as input and outputs a "binary"
         * Mat to "binaryMat" of the same size as our input.
         * "Discards" all the pixels outside the bounds specified
         * by the scalars above (and modifiable with EOCV-Sim's
         * live variable tuner.)
         *
         * Binary meaning that we have either a 0 or 255 value
         * for every pixel.
         *
         * 0 represents our pixels that were outside the bounds
         * 255 represents our pixels that are inside the bounds
         */


        Core.inRange(ycrcbMat, lower, upper, binaryMat);

        /*
         * Release the reusable Mat so that old data doesn't
         * affect the next step in the current processing
         */
        maskedInputMat.release();

        /*
         * Now, with our binary Mat, we perform a "bitwise and"
         * to our input image, meaning that we will perform a mask
         * which will include the pixels from our input Mat which
         * are "255" in our binary Mat (meaning that they're inside
         * the range) and will discard any other pixel outside the
         * range (RGB 0, 0, 0. All discarded pixels will be black)
         */

        Core.bitwise_and(frame, frame, maskedInputMat, binaryMat);

        /**
         * Add some nice and informative telemetry messages
         */

        /*
         * Different from OpenCvPipeline, you cannot return
         * a Mat from processFrame. Therefore, we will take
         * advantage of the fact that anything drawn onto the
         * passed `frame` object will be displayed on the
         * viewport. We will just return null here.
         */
        maskedInputMat.copyTo(frame);

        Point p1 = new Point(0, 0);
        Point p2 = new Point(x, frame.height());
        Point p3 = new Point(x, 0);
        Point p4 = new Point(x2, frame.height());
        Point p5 = new Point(x2, 0);
        Point p6 = new Point(frame.width(), frame.height());

        Scalar col = new Scalar(0, 255, 0);

        Imgproc.rectangle(frame, p1, p2, col);
        Imgproc.rectangle(frame, p3, p4, col);
        Imgproc.rectangle(frame, p5, p6, col);


        Mat r1 = frame.submat(new Rect(p1, p2));
        Mat r2 = frame.submat(new Rect(p3, p4));
        Mat r3 = frame.submat(new Rect(p5, p6));

        double r1i = Core.sumElems(r1).val[0];
        double r2i = Core.sumElems(r2).val[0];
        double r3i = Core.sumElems(r3).val[0];

        String high = "r1";
        if (r1i < r2i) {
            high = "r2";
        }
        if (r2i < r3i) {
            high = "r3";
        }
        if (r3i < r1i) {
            high = "r1";
        }

        telemetry.addData("Highest", high);
        telemetry.addData("r1", r1i);
        telemetry.addData("r2", r2i);
        telemetry.addData("r3", r3i);
        telemetry.addData("[>]", "Change these values in tuner menu");
        telemetry.addData("[Color Space]", colorSpace.name());
        telemetry.addData("[Lower Scalar]", lower);
        telemetry.addData("[Upper Scalar]", upper);
        telemetry.update();

        return null;
    }

    public String getHighValue(Mat frame) {
        if (color == 0) {
            lower = new Scalar(0, 0, 75);
            upper = new Scalar(47, 150, 255);
        } else {
            lower = new Scalar(72, 0, 0);
            upper = new Scalar(255, 150, 48);
        }

        Imgproc.cvtColor(frame, ycrcbMat, colorSpace.cvtCode);

        Core.inRange(ycrcbMat, lower, upper, binaryMat);

        Core.bitwise_and(frame, frame, maskedInputMat, binaryMat);

        Point p1 = new Point(0, 0);
        Point p2 = new Point(x, frame.height());
        Point p3 = new Point(x, 0);
        Point p4 = new Point(x2, frame.height());
        Point p5 = new Point(x2, 0);
        Point p6 = new Point(frame.width(), frame.height());

        Scalar col = new Scalar(0, 255, 0);

        Imgproc.rectangle(frame, p1, p2, col);
        Imgproc.rectangle(frame, p3, p4, col);
        Imgproc.rectangle(frame, p5, p6, col);

        Mat r1 = frame.submat(new Rect(p1, p2));
        Mat r2 = frame.submat(new Rect(p3, p4));
        Mat r3 = frame.submat(new Rect(p5, p6));

        if (!r1.empty() && !r2.empty() && !r3.empty()) {
            double r1i = Core.sumElems(r1).val[0];
            double r2i = Core.sumElems(r2).val[0];
            double r3i = Core.sumElems(r3).val[0];

            String high = "r1";
            if (r1i < r2i) {
                high = "r2";
            }
            if (r2i < r3i) {
                high = "r3";
            }
            if (r3i < r1i) {
                high = "r1";
            }

            return high;
        }
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    }
}
