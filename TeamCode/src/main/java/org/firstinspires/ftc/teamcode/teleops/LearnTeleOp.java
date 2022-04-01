package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Test", group = "TeleOp")
//@Disabled
public class LearnTeleOp extends OpMode {

	DcMotor frontLeft, backRight, frontRight, backLeft;

	@Override
	public void init( ) {
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
	}

	@Override
	public void loop( ) {
		cock( -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x );
		if( gamepad1.a ) {

		}
	}

	public void cock( double drive, double strafe, double rotate ) {
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