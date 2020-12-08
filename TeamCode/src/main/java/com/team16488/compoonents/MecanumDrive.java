package com.team16488.compoonents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.team16488.common.NUMERICAL_CONSTANTS;
import com.team16488.common.RobotMap;

/**
 * This is the class for the mecanum drive. like all components,
 * it can also be used for autonomous code
 */
public class MecanumDrive {

    //hardware(local)
    RobotMap robot; // this is all you need to do here, in the init method of the OpMode you just run the mapHardware method to initialize the hardware


    public MecanumDrive(RobotMap opModeRobot) {
        // initialize the hardware(pass in the opMode Robot map(instance) to this class in order to use it here)
        this.robot = opModeRobot;
        // This is all you need to do to initialize your hardware now when you have to call a motor just use robot.objectName.method
    }
    // above is all you need to do to initialize a component and its RobotMap

    // create various drive methods using methods

    /**
     * This method uses the inputs the x y and rotational axis and uses those values to set power to
     * the motors based on the calculations required for the mechanum drive
     * all axis take joystick values or any double between -1 and 1
     *
     * @param xAxisValue    Movement on the x axis(left right)
     * @param yAxiValue     Movement on the y(forward, backward)
     * @param rotationValue Movement on the rotational axis(turning on the spot)
     */
    public void operatorMecanumDrive(double xAxisValue, double yAxiValue, double rotationValue) {
        double r = Math.hypot(xAxisValue, yAxiValue);
        double robotAngle = Math.atan2(yAxiValue, xAxisValue) - Math.PI / 4;

        final double v1 = r * Math.cos(robotAngle) + rotationValue;
        final double v2 = r * Math.sin(robotAngle) - rotationValue;
        final double v3 = r * Math.sin(robotAngle) + rotationValue;
        final double v4 = r * Math.cos(robotAngle) - rotationValue;

        this.robot.FrontLeftMotor.setPower(v1);
        this.robot.FrontRightMotor.setPower(v2);
        this.robot.RearLeftMotor.setPower(v3);
        this.robot.RearRightMotor.setPower(v4);
    }

    public void operatorDriveInit() {
        this.robot.FrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.robot.RearLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.robot.FrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.robot.RearRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }


    public void encoderDriveInit() {
        this.robot.FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.RearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.RearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.robot.FrontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.RearLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.FrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.robot.RearRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Moves the robot to a specified position based on inputs
     *
     * @param speed     positive double, speed at which the robot will move
     * @param inches    the number of inches to move the robot
     * @param direction string the direction of movement(forward, backward, left, right) <-- use these parameters
     * @param turn      string the direction to turn(left, right) leave blank if no turn
     */
    public void encoderDrive(double speed,
                             double inches, String direction,
                             String turn) {

        if (direction.equals("forward")) {
            int newLeftTarget;
            int newRightTarget;

            newLeftTarget = robot.FrontLeftMotor.getCurrentPosition() + (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);
            newRightTarget = robot.FrontRightMotor.getCurrentPosition() + (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newLeftTarget != robot.FrontLeftMotor.getCurrentPosition() && newRightTarget != robot.FrontRightMotor.getCurrentPosition()) {
                operatorMecanumDrive(0, speed, 0);
            } else {
                stopDrive();
            }

        }

        if (direction.equals("backward")) {
            int newLeftTarget;
            int newRightTarget;

            newLeftTarget = robot.FrontLeftMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);
            newRightTarget = robot.FrontRightMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newLeftTarget != robot.FrontLeftMotor.getCurrentPosition() && newRightTarget != robot.FrontRightMotor.getCurrentPosition()) {
                operatorMecanumDrive(0, -speed, 0);
            } else {
                stopDrive();
            }

        }

        if (direction.equals("left")) {
            // define target locations
            int newLeftTarget;
            newLeftTarget = robot.FrontLeftMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newLeftTarget != robot.FrontLeftMotor.getCurrentPosition()) {
                operatorMecanumDrive(-speed, 0, 0);
            } else {
                stopDrive();
            }
        }

        if (direction.equals("right")) {
            // define target locations
            int newRightTarget;
            newRightTarget = robot.FrontRightMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newRightTarget != robot.FrontRightMotor.getCurrentPosition()) {
                operatorMecanumDrive(speed, 0, 0);
            } else {
                stopDrive();
            }
        }

        if (turn.equals("left")) {
            int newLeftTarget;
            int newRightTarget;

            newLeftTarget = robot.FrontLeftMotor.getCurrentPosition() + (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);
            newRightTarget = robot.FrontRightMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newLeftTarget != robot.FrontLeftMotor.getCurrentPosition() && newRightTarget != robot.FrontRightMotor.getCurrentPosition()) {
                operatorMecanumDrive(0, 0, -speed);
            } else {
                stopDrive();
            }
        }

        if (turn.equals("right")) {
            int newLeftTarget;
            int newRightTarget;

            newLeftTarget = robot.FrontLeftMotor.getCurrentPosition() - (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);
            newRightTarget = robot.FrontRightMotor.getCurrentPosition() + (int) (inches * NUMERICAL_CONSTANTS.TICKS_PER_INCH);

            if (newLeftTarget != robot.FrontLeftMotor.getCurrentPosition() && newRightTarget != robot.FrontRightMotor.getCurrentPosition()) {
                operatorMecanumDrive(0, 0, speed);
            } else {
                stopDrive();
            }
        }
    }

    public void stopDrive() {
        this.robot.FrontLeftMotor.setPower(0);
        this.robot.FrontRightMotor.setPower(0);
        this.robot.RearLeftMotor.setPower(0);
        this.robot.RearRightMotor.setPower(0);
    }


}
