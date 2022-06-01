package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.Robot;

@Autonomous(name = "LeanAuto", group = "Autonomous")
public class LeanAuto extends LinearOpMode {

	boolean limboMode = false;
	boolean clawMode = false;
	DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, liftMotor;
	Servo liftBase, claw, liftToggle;
	long startTime;

	@Override
	public void runOpMode( ) {
		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );

		frontLeftMotor = hardwareMap.get( DcMotorEx.class, "frontLeft" );
		backLeftMotor = hardwareMap.get( DcMotorEx.class, "backLeft" );
		frontRightMotor = hardwareMap.get( DcMotorEx.class, "frontRight" );
		backRightMotor = hardwareMap.get( DcMotorEx.class, "backRight" );
		liftMotor = hardwareMap.get( DcMotorEx.class, "Lift" );
		liftBase = hardwareMap.servo.get( "liftBase" );
		liftToggle = hardwareMap.servo.get( "liftToggle" );
		claw = hardwareMap.servo.get( "claw" );


		frontLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );
		backLeftMotor.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
		waitForStart( );
		startTime = System.currentTimeMillis( );
		telemetry.addData( "Mode", "TimeLeft: " + TimeLeft( ) );
		telemetry.update( );
		claw( );
		claw( );
		/**
		 //start at the edge
		 drive(1);
		 waitRobot( 650 );
		 //pick up block

		 int i;
		 for(i=0; opModeIsActive()&& i<5; i+=1)
		 {

		 for(int j=0; opModeIsActive()&&j<2; j++)
		 {
		 //grabs block and lowers lift
		 claw( );
		 limbo();
		 //moves over line
		 strafe( 1 );
		 waitRobot( 500+(i*100) );
		 //raise and drop
		 limbo();
		 claw();
		 //limbo and back over
		 limbo();
		 strafe( -1 );
		 waitRobot( 500+((i+1)*100) );
		 }

		 }
		 if(opModeIsActive())
		 {
		 limbo();
		 strafe( 1 );
		 waitRobot( 500+(i*100) );
		 }
		 while(opModeIsActive())
		 {
		 rotate( 360 ,1 );

		 }
		 **/
	}


	public void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( (startTime + mills) > System.currentTimeMillis( ) && opModeIsActive( ) ) {
			telemetry.update( );
		}

	}

	public void drive( double drive ) {

		setMotorPower( drive, drive, drive, drive );
	}

	public void strafe( double strafe ) {
		double backLeftPower = -strafe;
		double frontRightPower = -strafe;

		setMotorPower( strafe, backLeftPower, frontRightPower, strafe );
	}

	public void rotate( int amount, int drive ) {
		double frontRightPower = -drive;
		double backRightPower = -drive;

		setMotorPower( drive, drive, frontRightPower, backRightPower );
		waitRobot( (int) (amount * 3.5) );
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

	public void limbo( ) {
		if( !limboMode ) {
			liftBase.setPosition( 0.4 );
		} else {
			liftBase.setPosition( 0 );
		}
		waitRobot( 1000 );
		limboMode = !limboMode;
	}

	public void claw( ) {
		if( !clawMode ) {
			claw.setPosition( 0.58 );
		} else {
			claw.setPosition( 0.4 );
		}
		waitRobot( 1000 );
		clawMode = !clawMode;
	}


}


