package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Teleoop extends OpMode {
	DcMotor frontLeft, backLeft, frontRight, backRight;




	@Override
	public void init( ) {

		frontLeft = hardwareMap.get( DcMotorEx.class, "frontleft" );
		frontRight = hardwareMap.get( DcMotorEx.class, "frontright" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backleft" );
		backRight = hardwareMap.get( DcMotorEx.class, "backright" );

	}
	public void transport(double drive, double strafe, double rotate, double power){
		double frontLeftPower = (drive + strafe + rotate) * power;
		double backLeftPower = (drive - strafe + rotate) * power;
		double frontRightPower = (drive - strafe - rotate) * power;
		double backRightPower = (drive + strafe - rotate) * power;

		frontLeft.setPower( frontLeftPower );
		frontRight.setPower( frontRightPower );
		backLeft.setPower( backLeftPower );
		backRight.setPower( backRightPower );
	}
	@Override
	public void loop( ) {
		transport(  -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.99211233344412231 );
	}
}
