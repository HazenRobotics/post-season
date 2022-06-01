package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "AlexTeamTeleOp", group = "TeleOp")
public class AlexTeamTeleOP {

	public class LearnTeleOp extends OpMode {

		DcMotor frontLeft, backRight, frontRight, backLeft;
		CRServo clearance, lift, cleared, claw;

		//clearance is pulling lift back
		//cleared is pulling lift forward
		//lift pulls lift up/down
		//claw controls claw
		@Override
		public void init( ) {
			frontRight = hardwareMap.get( DcMotorEx.class, "frontRight" );
			backRight = hardwareMap.get( DcMotorEx.class, "backRight" );
			frontLeft = hardwareMap.get( DcMotorEx.class, "frontLeft" );
			backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
			clearance = hardwareMap.crservo.get( "clearance" );
			lift = hardwareMap.crservo.get( "lift" );
			cleared = hardwareMap.crservo.get( "cleared" );
			claw = hardwareMap.crservo.get( "claw" );

			frontLeft.setDirection( DcMotorSimple.Direction.REVERSE );
			backLeft.setDirection( DcMotorSimple.Direction.REVERSE );
			telemetry.addData( "Mode", "waiting for start" );
			telemetry.update( );
		}

		@Override
		public void loop( ) {
			drive( -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x );

			claw( gamepad1.right_trigger, gamepad1.left_trigger );

			if( gamepad1.b ) {
				clearance.setPower( 1 );
				cleared.setPower( -1 );
			} else if( gamepad1.a ) {
				clearance.setPower( -1 );
				cleared.setPower( 1 );
			} else {
				cleared.setPower( 0 );
				clearance.setPower( 0 );
			}

			if( gamepad1.right_bumper ) {
				lift.setPower( 1 );
			} else if( gamepad1.left_bumper ) {
				lift.setPower( -1 );
			} else {
				lift.setPower( 0 );
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

		public void claw( double grab, double release ) {
			double clawPower = grab - release;
			claw.setPower( clawPower );
		}
	}
}
