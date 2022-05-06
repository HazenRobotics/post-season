package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.SkystoneDetector;
import org.firstinspires.ftc.teamcode.robots.Robot;

@TeleOp(name = "LeanTeleOp", group = "TeleOp")
//@Disabled
public class LeanTeleOp extends OpMode {

	boolean limboMode = false;
	boolean clawMode = false;
	DcMotorEx frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, liftMotor;
	Servo liftBase, claw;

	@Override
	public void init( ) {

		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );

		frontLeftMotor = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeftMotor = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRightMotor = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "backRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "Lift" );
		liftBase = hardwareMap.servo.get( "liftBase" );
//		claw = hardwareMap.servo.get( "servo" );


		frontLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
		liftBase.setPosition( 0.5 );
//		liftBase.setPosition( 0 );
	}

	@Override
	public void loop( ) {
		SkystoneDetector stone = new SkystoneDetector( );
		move( -gamepad1.left_stick_y * 2 / 3, gamepad1.left_stick_x, gamepad1.right_stick_x );
		if( gamepad1.left_bumper ) {
			while( gamepad1.a ) ;
			limboMode = !limboMode;
			limbo( limboMode );
		}


		telemetry.update( );
	}

	/**
	 * set directional power
	 *
	 * @param drive  power
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
	 *
	 * @param frontLeftPower  power
	 * @param backLeftPower   power
	 * @param frontRightPower power
	 * @param backRightPower  power
	 */
	public void setMotorPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
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
			liftBase.setPosition( 1 );
		} else {
			liftBase.setPosition( 0 );
		}
	}



	public void Up( double percent ) {
		liftMotor.setPower( 0.5 );
		int time = (int) (1000 * percent);
		waitRobot( time );
		liftMotor.setPower( 0 );
	}
}