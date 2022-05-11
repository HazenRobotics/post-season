package org.firstinspires.ftc.teamcode.teleops;

import static com.qualcomm.robotcore.hardware.Servo.MAX_POSITION;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Something", group = "TeleOp")
public class Something extends OpMode {

	DcMotor backRight, backLeft;
	Servo plateR, plateL;
	CRServo claw, lift;

	boolean xWasPressed = false;
	boolean plateMode = false;

	@Override
	public void init( ) {
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		lift = hardwareMap.crservo.get( "lift" );
		claw = hardwareMap.crservo.get( "claw" );
		plateR = hardwareMap.servo.get( "plateR" );
		plateL = hardwareMap.servo.get( "plateL" );
		backRight.setDirection( DcMotorSimple.Direction.REVERSE );
		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
	}

	@Override
	public void loop( ) {
		move( -gamepad1.left_stick_y, gamepad1.left_stick_x );
		scoring( gamepad1.right_stick_y, gamepad1.right_stick_x );
		if( gamepad1.x && !xWasPressed ) {
			plateMode = !plateMode;
			plateToggle( plateMode );
		}
		xWasPressed = gamepad1.x;
	}

	public void move( double drive, double rotate ) {
		double backLeftPower = drive + rotate;
		double backRightPower = drive - rotate;

		setMotorPower( backLeftPower, backRightPower );
	}

	public void scoring( double liftPower, double clawPower ) {
		lift.setPower( liftPower );
		claw.setPower( clawPower );
	}

	public void setMotorPower( double backLeftPower, double backRightPower ) {
		backLeft.setPower( backLeftPower );
		backRight.setPower( backRightPower );
	}

	public void plateToggle( boolean mode ) {
		if( mode ) {
			plateL.setPosition( 1.0 );
			plateR.setPosition( 1.0 );
		} else {
			plateL.setPosition( 0.0 );
			plateR.setPosition( 0.0 );
		}
	}

}
