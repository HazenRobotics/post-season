package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.Robot;

@Autonomous(name = "LeanAuto", group = "Autonomous")
public class LeanAuto extends LinearOpMode {

	boolean limboMode = false;
	boolean clawMode = false;
	boolean aWasPressed = false;
	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, liftMotor;
	Servo liftBase, claw;
	double power = 1;
	long startTime;

	@Override
	public void runOpMode( ) throws InterruptedException {
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
		telemetry.update( );
		waitForStart( );
		startTime = System.currentTimeMillis( );
		telemetry.addData( "Mode", "TimeLeft: " + TimeLeft( ) );
		telemetry.update( );

		drive( 1);
		waitRobot( 700 );
		rotate( 1 );
		waitRobot( 500 );
		drive( 1 );
		waitRobot( 1000 );
		strafe( 0.2 );
		waitRobot( 200 );
		drive(0);


	}

	public void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( (startTime + mills) > System.currentTimeMillis( ) );

	}

	public void drive( double drive ) {
		double frontLeftPower = drive;
		double backLeftPower = drive;
		double frontRightPower = drive;
		double backRightPower = drive;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public void strafe( double strafe ) {
		double frontLeftPower = strafe;
		double backLeftPower = -strafe;
		double frontRightPower = -strafe;
		double backRightPower = strafe;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public void rotate( double rotate ) {
		double frontLeftPower = rotate/1.5;
		double backLeftPower = rotate/1.5;
		double frontRightPower = -rotate/1.5;
		double backRightPower = -rotate/1.5;

		setMotorPower( frontLeftPower, backLeftPower, frontRightPower, backRightPower );
	}

	public long TimeLeft( ) {
		return System.currentTimeMillis( ) - startTime + 30;
	}

	public void setMotorPower( double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower ) {
		frontLeftMotor.setPower( frontLeftPower );
		backLeftMotor.setPower( backLeftPower );
		frontRightMotor.setPower( frontRightPower );
		backRightMotor.setPower( backRightPower );
	}
	public void limbo( boolean mode ) {
		if( !mode ) {
			liftBase.setPosition( 0.4 );
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


