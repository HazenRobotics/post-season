package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Something", group = "TeleOp")
public class Something extends OpMode{

	DcMotor  backRight, backLeft;
	Servo lift, claw, plateR, plateL;

	boolean liftMode = false;
	boolean clawMode = false;
	boolean plateMode = false;

	@Override
	public void init( ) {
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		lift = hardwareMap.servo.get( "lift" );
		claw = hardwareMap.servo.get( "claw" );
		plateR = hardwareMap.servo.get("plateR");
		plateL = hardwareMap.servo.get("plateL");
		backRight.setDirection( DcMotorSimple.Direction.REVERSE );
		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
	}

	@Override
	public void loop( ) {
		move( -gamepad1.left_stick_y, gamepad1.left_stick_x);
		if( gamepad1.a ) {
			clawMode = !clawMode;
			moveServo( clawMode, claw );
		}
		if( gamepad1.b ) {
			liftMode = !liftMode;
			moveServo( liftMode, lift );
		}
		if( gamepad1.x ) {
			plateMode = !plateMode;
			moveServo( plateMode, plateR );
			moveServo( plateMode, plateL );
		}
	}

	public void move( double drive, double rotate ) {
		double backLeftPower = drive + rotate;
		double backRightPower = drive - rotate;

		setMotorPower(backLeftPower, backRightPower );
	}

	public void setMotorPower( double backLeftPower, double backRightPower ) {
		backLeft.setPower( backLeftPower );
		backRight.setPower( backRightPower );
	}

	public void moveServo(boolean mode, Servo servo)
	{
		if( mode ) {
			servo.setPosition( 90 );
		} else {
			servo.setPosition( 0 );
		}
	}

}
