package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AlexTeamAuto", group = "Autonomous")
public class AlexTeamAuto extends LinearOpMode {

	DcMotor frontLeft, backRight, frontRight, backLeft;
	Servo clearance, cleared, lift, claw;

	@Override
	public void runOpMode( ) {
		frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
		frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		clearance = hardwareMap.servo.get( "" );
		cleared = hardwareMap.servo.get( "liftLeft" );
		lift = hardwareMap.servo.get( "" );
		frontLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeft.setDirection( DcMotorSimple.Direction.REVERSE );
		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );

		waitForStart( );


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
