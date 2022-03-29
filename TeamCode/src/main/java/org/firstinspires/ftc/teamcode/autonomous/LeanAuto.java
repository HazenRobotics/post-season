package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robots.Robot;

public class LeanAuto extends LinearOpMode {
	SkystoneDetector Stone = new SkystoneDetector();
	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
	long startTime;
	@Override
	public void runOpMode( ) throws InterruptedException {
		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );

		frontLeftMotor = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeftMotor = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRightMotor = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "backRight" );

		frontLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update();
		waitForStart();
		startTime = System.currentTimeMillis();
		telemetry.addData( "Mode", "TimeLeft: "+TimeLeft());
		telemetry.update();
		drive(1);
		waitRobot(2000);
		drive(0);
		strafe( 1 );
	}

	public void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( startTime + mills < System.currentTimeMillis( ))
		{
			telemetry.update();
			Stone.processFrame(Stone.mat);
		}

	}

	public void drive( double drive) {
		double frontLeftPower = drive;
		double backLeftPower = drive;
		double frontRightPower = drive;
		double backRightPower = drive;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public void strafe(double strafe) {
		double frontLeftPower = 1 + strafe;
		double backLeftPower =-strafe;
		double frontRightPower =-strafe;
		double backRightPower = 1 + strafe;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public void rotate(double rotate) {
		double frontLeftPower = 1+ rotate;
		double backLeftPower = 1+ rotate;
		double frontRightPower = - rotate;
		double backRightPower = - rotate;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public long TimeLeft()
	{
		return System.currentTimeMillis()-startTime+30;
	}

	public void setMotorPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeftMotor.setPower( frontLeftPower );
		backLeftMotor.setPower( backLeftPower );
		frontRightMotor.setPower( frontRightPower );
		backRightMotor.setPower( backRightPower );
	}
	public void caveManFindRock()
	{
		strafe( 0.5 );
		while(Stone.getStoneType()!= SkystoneDetector.StoneType.SKYSTONE)
		{
			Stone.processFrame(Stone.mat);
			telemetry.update();
		}
		drive(1);
	}
}


