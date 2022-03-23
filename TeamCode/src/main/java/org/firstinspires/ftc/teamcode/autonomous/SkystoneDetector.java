package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SkystoneDetector extends OpenCvPipeline {
	Scalar skyLowHSV = new Scalar( 220,10,0 );
	Scalar skyHighHSV = new Scalar( 255,40,15);
	Scalar normalLowHSV = new Scalar( 25,40,40 );
	Scalar normalHighHSV = new Scalar(  45,100,67 );

	Telemetry telemetry;
	Mat mat = new Mat();

	public enum StoneType {
		STONE,
		SKYSTONE,
		NOTHING
	}
	static double COLOR_THRESHOLD = 0.5;
	private StoneType stoneType;

	@Override
	public Mat processFrame( Mat input ) {
		Imgproc.cvtColor( input,mat,Imgproc.COLOR_RGB2HSV);
		Core.inRange(mat, skyLowHSV, skyHighHSV, mat);
		double value = Core.sumElems( mat ).val[0] / (1280 * 720) / 255;
		boolean skyInFrame = value > COLOR_THRESHOLD;
		stoneType = skyInFrame ? StoneType.SKYSTONE : StoneType.NOTHING;
		Imgproc.cvtColor( mat, mat, Imgproc.COLOR_GRAY2RGB );
		return mat;
	}

	public StoneType getStoneType() {
		return stoneType;
	}
}
