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
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		lift = hardwareMap.servo.get( "liftServo" );
		claw = hardwareMap.servo.get( "clawServo" );

		frontRight.setDirection( DcMotor.Direction.REVERSE );
		backRight.setDirection( DcMotor.Direction.REVERSE );

		waitForStart( );

		// run until  the end of the match (driver presses STOP)
		while ( opModeIsActive( ) ) {
			move( gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x );
			buttonOps( );
		}
	}

	pubic void buttonOps( ) {
		if( gamepad.right_trigger ) 
			lift.setPower( 1.0 );
		if( gamepad.left_trigger  && claw.getPosition( ) == 0.0 ) 
			claw.setPosition( 0.6 );
		if( gamepad.left_trigger  && claw.getPosition( ) == 0.6 )
			claw.setPosition( 0.0 );
	}


	public void setPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeft.setPower( frontLeftPower * 0.54 );
		backLeft.setPower( backLeftPower * 0.54 );
		frontRight.setPower( frontRightPower * 0.54);
		backRight.setPower( backRightPower * 0.54 );
	}

	public void drive( double power ) {
		setPower( power, power, power, power );
	}

	public void strafe( double power ) {
		setPower( power, -power, -power, power );
	}

	public void rotate( double power ) {
		setPower( power, power, -power, -power );
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

	public void 


}
