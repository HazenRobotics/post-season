package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class AutoOopsies extends LinearOpMode {
	DcMotorEx frontLeft;
	DcMotorEx backLeft;
	DcMotorEx frontRight;
	DcMotorEx backRight;
	final static int ppr = 1440; // number of pulses/ticks in one revolution of the motor

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