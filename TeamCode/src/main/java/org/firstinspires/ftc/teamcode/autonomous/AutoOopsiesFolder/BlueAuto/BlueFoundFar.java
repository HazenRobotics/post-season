package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class BlueFoundFar extends LinearOpMode {
	DcMotorEx frontLeft;
	DcMotorEx backLeft;
	DcMotorEx frontRight;
	DcMotorEx backRight;
	final static double ppr = 1440; // number of pulses/ticks in one revolution of the motor
	final static double gearRatio = 60; //60:1 gear ratio
	final static double encoderRadius = 4; //inches
	boolean drive;
	boolean strafe;
	boolean rotate;
	public void runOpMode( ) {
		//Initializes motors
		vroomvroom( );
		waitForStart( );

		frontLeft.setMode( DcMotorEx.RunMode.RUN_TO_POSITION );
		backLeft.setMode( DcMotorEx.RunMode.RUN_TO_POSITION );
		frontRight.setMode( DcMotorEx.RunMode.RUN_TO_POSITION );
		backRight.setMode( DcMotorEx.RunMode.RUN_TO_POSITION );
		
	}

	public void vroomvroom( ) {
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );

		frontRight.setDirection( DcMotor.Direction.REVERSE );
		backRight.setDirection( DcMotor.Direction.REVERSE );
	}

	public void setMotorTargets ( int distance ) {
		frontLeft.setTargetPosition( convertDistTicks ( distance ) );
		backLeft.setTargetPosition( convertDistTicks ( distance ) );
		frontRight.setTargetPosition( convertDistTicks ( distance ) );
		backRight.setTargetPosition( convertDistTicks ( distance ) );

		if(drive)
			move( 1, 0, 0 );
		else if(strafe)
			move( 0, 1, 0 );
		else if(rotate)
			move( 0, 0, 1 );
		
	}

	public static int convertDistTicks( double distance, double wheelRadius, double pulsesPerRevolution, double gearRatio ) {
		double revolutions = distance / Math.PI * 2 * wheelRadius;
		return (int) Math.round( (revolutions * pulsesPerRevolution) / gearRatio );
	}

	public int convertDistTicks( double distance ) {
		return convertDistTicks( distance, encoderRadius, ppr, gearRatio );
	}

	public static double convertTicksDist( int ticks, double wheelRadius, double pulsesPerRevolution, double gearRatio ) {
		double circumference = 2 * Math.PI * wheelRadius;
		return (ticks * circumference * gearRatio) / pulsesPerRevolution;
	}

	public double convertTicksDist( int ticks ) {
		return convertTicksDist( ticks, encoderRadius, ppr, gearRatio );
	}

	public void setPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeft.setPower( frontLeftPower * 0.54 );
		backLeft.setPower( backLeftPower * 0.54 );
		frontRight.setPower( frontRightPower );
		backRight.setPower( backRightPower * 0.54 );
	}

	public void drive( double power ) {
		setPower( power, power, power, power );
	}

	public void strafe( double power ) {
		setPower( power, -power, -power, power );
	}

	public void rotate( double power ) {
		setPower( power, power, -power, -power);
	}

	public void stopRobot( ) {
		frontLeft.setPower( 0 );
		backLeft.setPower( 0 );
		frontRight.setPower( 0 );
		backRight.setPower( 0 );
	}

	public void move( double drive, double strafe, double rotate ) {
		double frontLeftPower = drive + rotate - strafe;
		double backLeftPower = drive + rotate + strafe;
		double frontRightPower = drive - rotate + strafe;
		double backRightPower = drive - rotate - strafe;
		setPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}
}