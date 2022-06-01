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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

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
	// Servo backClaw;
	boolean buttonPressed;

	@Override
	public void runOpMode( ) {
		//2 servos on this bad boy
		lift = hardwareMap.crservo.get( "lift" );
		claw = hardwareMap.servo.get( "claw" );
		// backClaw = hardwareMap.servo.get( "backClaw" );


		//Motors getting set up
		vroomvroom( );
		waitForStart( );

		// run until  the end of the match (until driver presses STOP)
		while ( opModeIsActive( ) ) {
			//move using controls
			//d-pad for drive and strafe (forward/backward, left/right ), right joystick for rotate
			move( gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x );
			//left bumper to make lift go up, right to make it go down
			//button A for lift (it is a bit scuffed)
			buttonOps( );
			printInfo( );
		}
	}

	//Prints all the info during teleOp to allow user to be aware of power settings and servo positions
	public void printInfo( ) {
		telemetry.addLine( "gamepad1.left_stick_y: " + gamepad1.left_stick_y );
		telemetry.addLine( "gamepad1.left_stick_x: " + gamepad1.left_stick_x );
		telemetry.addLine( "- gamepad1.right_stick_x: " + -gamepad1.right_stick_x );
		telemetry.addLine( "lift power: " + lift.getPower( ) );
		telemetry.addLine( "claw power: " + claw.getPosition( ) );
		telemetry.update( );
	}

	//Button setup method
	//right trigger is for the lift, press and hold
	//left trigger is for the claw, 2 positions - released and clutch
	public void buttonOps( ) {
		if( gamepad1.right_bumper )
			lift.setPower( 1 );
		else if ( !gamepad1.right_bumper )
			lift.setPower( 0 );
		if( gamepad1.left_bumper ) {
			lift.setPower( -1 );
		}

		if( gamepad1.a  && !buttonPressed) {
			claw.setPosition( 0.9 );
			buttonPressed = !buttonPressed;
		}
		else if( gamepad1.a) {
			claw.setPosition( 0.0 );
			buttonPressed = !buttonPressed;
		}
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
	//as a result, everything except frontRight gets 0.55 norm power
	//This is needed because the everything except frontRight is a Torquenado
	public void setPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeft.setPower( frontLeftPower * 0.55 );
		backLeft.setPower( backLeftPower * 0.55 );
		frontRight.setPower( frontRightPower );
		backRight.setPower( backRightPower * 0.55 );
	}

	//puts power to wheels to move forward and backward
	//putting power to all of the wheels will bring the robot forward, negative is backward
	public void drive( double power ) {
		setPower( power, power, power, power );
	}

	//puts power to wheels to move left and right
	//front wheel is opposite of same side back wheel
	//frontLeft forward, while backLeft goes backward
	//frontRight forward, while backRight goes backward
	public void strafe( double power ) {
		setPower( power, -power, -power, power );
	}

	//puts power to ROTATE wheels
	//left goes forward, right goes backward
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
