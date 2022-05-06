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
	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, liftMotor;
	Servo liftBase, claw;
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
		liftBase = hardwareMap.servo.get( "lift servo" );
		//claw = hardwareMap.servo.get( "claw" );


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
		if( gamepad1.a ) {
			while( gamepad1.a );
			limbo( limboMode );
			limboMode = !limboMode;
		}
		if( gamepad1.left_bumper ) {
			//claw( clawMode );
			clawMode = !clawMode;
		}

	}

	/**
	 * set directional power
	 *
	 * @param drive  power
	 * @param strafe strafe power
	 * @param rotate power
	 */
	public void move( double drive, double strafe, double rotate, double power ) {
		double frontLeftPower = (drive + strafe + rotate) * power;
		double backLeftPower = (drive - strafe + rotate) * power;
		double frontRightPower = (drive - strafe - rotate) * power;
		double backRightPower = (drive + strafe - rotate) * power;

		frontLeftMotor.setPower( frontLeftPower );
		backLeftMotor.setPower( backLeftPower );
		frontRightMotor.setPower( frontRightPower );
		backRightMotor.setPower( backRightPower );
	}


	public static void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( startTime + mills < System.currentTimeMillis( ) ) ;
	}

	public void limbo( boolean mode ) {
		if( mode ) {
			liftBase.setPosition( 90 );
		} else {
			liftBase.setPosition( 0 );
		}
	}

	/*public void claw( boolean mode ) {
		if( mode ) {
			claw.setPosition( 90 );
		} else {
			claw.setPosition( 0 );
		}
	}*/

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