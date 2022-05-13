package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class VPTeleOp extends OpMode {

	DcMotor leftMotor, rightMotor;

	Servo foundLeft, foundRight, clawTilter, blockGrabber;


	boolean foundTogB = false;
	boolean blockGrabB = false;
	boolean clawTiltB = false;

	@Override
	public void init( ) {

		leftMotor = hardwareMap.get( DcMotorEx.class, "leftMotor" );
		rightMotor = hardwareMap.get( DcMotorEx.class, "rightMotor" );

		foundLeft = hardwareMap.servo.get( "foundLeft" );
		foundRight = hardwareMap.servo.get( "foundRight" );
		clawTilter = hardwareMap.servo.get( "clawTilter" );
		blockGrabber = hardwareMap.servo.get( "blockGrabber" );

		foundLeft.setPosition( 0.0 );
		foundRight.setPosition( 1.0 );
		clawTilter.setPosition( 1.0 );
		blockGrabber.setPosition( 0.5 );

		leftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
	}


	@Override
	public void loop( ) {

		if( gamepad1.y ) {
			foundTog( );
		}

		if( gamepad1.x ) {
			clawTiltTog( );
		}

		if( gamepad1.a ) {
			blockGrabTog( );
		}

		move( -gamepad1.left_stick_y, gamepad1.right_stick_x );
	}
	public static void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( startTime + mills < System.currentTimeMillis( ) ) ;
	}

	public void move( double drive, double rotate ) {

		double leftPower = drive + rotate;
		double rightPower = drive - rotate;

		leftMotor.setPower( leftPower );
		rightMotor.setPower( rightPower );
	}

	public void foundTog( ) {

		if( foundTogB ) {

			foundLeft.setPosition( 0.5 );
			foundRight.setPosition( 0.5 );
		} else {

			foundLeft.setPosition( 0.0 );
			foundRight.setPosition( 1.0 );

		}

		foundTogB = !foundTogB;
	}


	public void clawTiltTog( ) {

		if( clawTiltB ) {

			clawTilter.setPosition( 0.75 );

		} else {

			clawTilter.setPosition( 1.0 );
		}

		clawTiltB = !clawTiltB;
	}

	public void blockGrabTog( ) {

		if( blockGrabB ) {

			blockGrabber.setPosition( 0.0 );
		} else {

			blockGrabber.setPosition( 0.5 );
		}

		blockGrabB = !blockGrabB;
	}
}

