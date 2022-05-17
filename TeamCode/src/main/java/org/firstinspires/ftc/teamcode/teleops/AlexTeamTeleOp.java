package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "AlexTeamTeleOp", group = "TeleOp")
public class AlexTeamTeleOP extends OpMode {
	DcMotor frontLeft, backRight, frontRight, backLeft;
	Servo clearance, cleared, lift, claw;

	// adding more chagnes to file

	@Override
	public void init( ) {
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		clearance = hardwareMap.servo.get( "" );
		cleared = hardwareMap.servo.get( "liftLeft" );
		lift = hardwareMap.servo.get( "" );
		claw = hardwareMap.servo.get( "" );
		frontLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
	}

	@Override
	public void loop( ) {
		drive( -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x );
		if( gamepad1.a ) {
			clearance.setPosition( 0 );
			cleared.setPosition( 0 );
		}
		if( gamepad1.b ) {
			cleared.setPosition( 0 );
			clearance.setPosition( 0 );
		}
		if( gamepad1.right_bumper ) {
			lift.setPosition( 0 );
		}
		if( gamepad1.left_bumper ) {
			lift.setPosition( 0 );
		}

	}

	public void drive( double drive, double strafe, double rotate ) {
		double frontLeftPower = drive + strafe + rotate;
		double backLeftPower = drive - strafe + rotate;
		double frontRightPower = drive - strafe - rotate;
		double backRightPower = drive + strafe - rotate;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public void setMotorPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeft.setPower( frontLeftPower );
		backLeft.setPower( backLeftPower );
		frontRight.setPower( frontRightPower );
		backRight.setPower( backRightPower );
	}

}
