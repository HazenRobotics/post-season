package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robots.Robot;

@TeleOp(name = "SimpleTeleOp", group = "TeleOp")
//@Disabled
public class SimpleTeleOp extends OpMode {

	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;

	@Override
	public void init( ) {

		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );

		frontLeftMotor = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeftMotor = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRightMotor = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "backRight" );

		frontLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
	}

	@Override
	public void loop( ) {

		move( -gamepad1.left_stick_y * 2 / 3, gamepad1.left_stick_x, gamepad1.right_stick_x );

		telemetry.update( );
	}

	/**
	 * set directional power
	 * @param drive power
	 * @param strafe strafe power
	 * @param rotate power
	 */
	public void move( double drive, double strafe, double rotate ) {
		double frontLeftPower = drive + strafe + rotate;
		double backLeftPower = drive - strafe + rotate;
		double frontRightPower = drive - strafe - rotate;
		double backRightPower = drive + strafe - rotate;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	/**
	 * set individual power
	 * @param frontLeftPower power
	 * @param backLeftPower power
	 * @param frontRightPower power
	 * @param backRightPower power
	 */
	public void setMotorPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeftMotor.setPower( frontLeftPower );
		backLeftMotor.setPower( backLeftPower );
		frontRightMotor.setPower( frontRightPower );
		backRightMotor.setPower( backRightPower );
	}
}