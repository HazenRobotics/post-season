package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.Robot;

@TeleOp(name = "LeanTeleop", group = "TeleOp")
//@Disabled

public class LeanTeleOp extends OpMode {

	boolean limboMode = false;
	boolean clawMode = false;
	boolean aWasPressed = false;
	boolean bWasPressed = false;
	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, liftMotor;
	Servo liftBase, claw,liftToogle;
	double power = 1;

	@Override
	public void init( ) {

		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );

		frontLeftMotor = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeftMotor = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRightMotor = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "backRight" );
		liftMotor = hardwareMap.get( DcMotorEx.class, "Lift" );
		liftBase = hardwareMap.servo.get( "liftBase" );
		liftToogle = hardwareMap.servo.get( "liftTooggle" );
		claw = hardwareMap.servo.get( "claw" );


		frontLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		if( gamepad1.dpad_up && power < 1 ) {
			power -= 0.1;
		}
		if( gamepad1.dpad_up && power > 0 ) {
			power += 0.1;
		}
		telemetry.update( );

	}

	@Override
	public void loop( ) {

		Up( );
		if( gamepad1.a ) {
			move( -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power / 2 );
		} else {
			move( -gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power );

		}
		if( gamepad1.dpad_up && power < 1 ) {
			power -= 0.1;
		}
		if( gamepad1.dpad_up && power > 0 ) {
			power += 0.1;
		}
		if( aWasPressed && !gamepad1.a ) {
			limbo(  );
		}
		if( bWasPressed && !gamepad1.b) {
			claw(  );
		}
		aWasPressed = gamepad1.a;
		bWasPressed = gamepad1.b;


	}

	/**
	 * set directional power
	 *
	 * @param drive  power
	 * @param strafe strafe power
	 * @param rotate power
	 */
	public void move( double drive, double strafe, double rotate, double power ) {
		double frontLeftPower = (drive + strafe + rotate/2) * power;
		double backLeftPower = (drive - strafe + rotate/2) * power;
		double frontRightPower = (drive - strafe - rotate/2) * power;
		double backRightPower = (drive + strafe - rotate/2) * power;

		frontLeftMotor.setPower( frontLeftPower );
		backLeftMotor.setPower( backLeftPower );
		frontRightMotor.setPower( frontRightPower );
		backRightMotor.setPower( backRightPower );
	}


	public void limbo(   ) {
		if( !limboMode ) {
			liftBase.setPosition( 0.4 );
		} else {
			liftBase.setPosition( 0 );
		}
		limboMode = !limboMode;
	}

	public void claw(  ) {
		if( !clawMode ) {
			claw.setPosition( 0.58 );
		} else {
			claw.setPosition( 0.4 );
		}
		clawMode = !clawMode;
	}

	public void Up( ) {
		if( gamepad1.right_trigger > 0 ) {
			liftMotor.setPower( 1 );
		} else if( gamepad1.left_trigger > 0 ) {
			liftMotor.setPower( -1 );
		} else {
			liftMotor.setPower( 0 );
		}

	}
}