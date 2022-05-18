package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp( name = "TeleOopsies - team Pancit", group = "Linear Opmode" )

//Make sure that disabled is commented out if you want to use this
//@Disabled

public class TeleOopsies extends LinearOpMode {
	DcMotorEx frontLeft;
	DcMotorEx backLeft;
	DcMotorEx frontRight;
	DcMotorEx backRight;
	CRServo lift;
	Servo claw;

	@Override
	public void runOpMode( ) {
		//2 servos on this bad boy
		lift = hardwareMap.servo.get( "liftServo" );
		claw = hardwareMap.servo.get( "clawServo" );

		//Motors getting set up
		vroomvroom( );
		waitForStart( );

		// run until  the end of the match (until driver presses STOP)
		while ( opModeIsActive( ) ) {
			//move using controls
			//d-pad for drive and strafe (forward/backward, left/right ), right joystick for rotate
			move( gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x );
			buttonOps( );
			printInfo( );
		}
	}

	//Prints all the info during teleOp to allow user to be aware of power settings and servo positions
	public void printInfo( ) {
		telemetry.addLine( "gamepad1.left_stick_y: " + gamepad.left_stick_y );
		telemetry.addLine( "gamepad1.left_stick_x: " + gamepad1.left_stick_x );
		telemetry.addLine( "- gamepad1.right_stick_x: " + -gamepad1.right_stick_x );
		telemetry.addLine( "lift power: " + lift.getPosition( ) );
		telemetry.addLine( "claw power: " + claw.getPower( ) );
		telemetry.update( );
	}

	//Button setup method
	//right trigger is for the lift, press and hold
	//left trigger is for the claw, 2 positions - released and clutch
	public void buttonOps( ) {
		if( gamepad.right_trigger ) 
			lift.setPower( 1.0 );
		if( gamepad.left_trigger  && claw.getPosition( ) == 0.0 ) 
			claw.setPosition( 0.6 );
		if( gamepad.left_trigger  && claw.getPosition( ) == 0.6 )
			claw.setPosition( 0.0 );
	}

	//Motor setup method
	public void vroomvroom( ) {
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );

		frontRight.setDirection( DcMotor.Direction.REVERSE );
		backRight.setDirection( DcMotor.Direction.REVERSE );
	}

	//sets power to the motors, but frontRight doesn't move as fast. 
	//as a result, everything except frontRight gets 0.54 norm power
	public void setPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeft.setPower( frontLeftPower * 0.55 );
		backLeft.setPower( backLeftPower * 0.55 );
		frontRight.setPower( frontRightPower );
		backRight.setPower( backRightPower * 0.55 );
	}

	//puts power to wheels to move forward and backward
	public void drive( double power ) {
		setPower( power, power, power, power );
	}

	//puts power to wheels to move left and right
	public void strafe( double power ) {
		setPower( power, -power, -power, power );
	}

	//puts power to rotate wheels
	public void rotate( double power ) {
		setPower( power, power, -power, -power );
	}

	//STOPS ROBOT
	public void stopRobot( ) {
		frontLeft.setPower( 0 );
		backLeft.setPower( 0 );
		frontRight.setPower( 0 );
		backRight.setPower( 0 );
	}

	//combines the values of drive, strafe, and rotate together
	public void move( double drive, double strafe, double rotate ) {
		double frontLeftPower = drive + rotate - strafe;
		double backLeftPower = drive + rotate + strafe;
		double frontRightPower = drive - rotate + strafe;
		double backRightPower = drive - rotate - strafe;
		setPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}


}
