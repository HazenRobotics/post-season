package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.Robot;

@Autonomous(name = "SomethingAuto", group = "Autonomous")
public class SomethingAuto extends LinearOpMode {


	DcMotor  backLeft,backRight;
	long startTime;

	@Override
	public void runOpMode( ) {
		Robot.createMatchLogFile( getClass( ).getSimpleName( ) );

		telemetry.addData( "Mode", "Initiating robot..." );
		telemetry.update( );


		backLeft = hardwareMap.get( DcMotorEx.class, "backLeft" );
		backRight = hardwareMap.get( DcMotorEx.class, "backRight" );


		backRight.setDirection( DcMotorSimple.Direction.REVERSE );

		telemetry.addData( "Mode", "waiting for start" );
		telemetry.update( );
		waitForStart( );
		startTime = System.currentTimeMillis( );
		telemetry.addData( "Mode", "TimeLeft: " + TimeLeft( ) );
		telemetry.update( );
		//start at the edge
		drive(1);
		waitRobot( 1146);
		drive(0);
	}

	public void waitRobot( int mills ) {
		long startTime = System.currentTimeMillis( );
		while( (startTime + mills) > System.currentTimeMillis( ) && opModeIsActive())
		{
			telemetry.update( );
		}

	}

	public void drive( double drive ) {

		setMotorPower( drive, drive);

	}

	public void rotate( int amount,int drive ) {
		double frontRightPower = -drive;
		double backRightPower = -drive;

		setMotorPower(frontRightPower, backRightPower );
		waitRobot( (int)(amount*3.5) );
	}

	public long TimeLeft( ) {
		return System.currentTimeMillis( ) - startTime + 30;
	}

	public void setMotorPower(double brp, double blp){
		backRight.setPower(brp);
		backLeft.setPower(blp);
	}
}


