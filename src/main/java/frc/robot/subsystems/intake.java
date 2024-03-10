package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//I'm writing a commitale change
public class intake extends SubsystemBase {
    CANSparkMax intake1 = new CANSparkMax(55, MotorType.kBrushless);
    CANSparkMax intake2 = new CANSparkMax(34, MotorType.kBrushless);
    NetworkTable table;
    DoubleTopic intakeforwardtopic;
    DoubleTopic intakebackwardtopic;
    DoubleTopic intakepushtopic;
    public intake(NetworkTable table){
        this.table = table;
        intakeforwardtopic = table.getDoubleTopic("/datatable/intakefoward");
        intakebackwardtopic = table.getDoubleTopic("/datatable/intakebackward");
        intakepushtopic = table.getDoubleTopic("/datatable/intakepush");
        intakeforwardtopic.getEntry(0).set(0);
        intakebackwardtopic.getEntry(0).set(0);
        intakepushtopic.getEntry(0).set(0);
        intake1.setInverted(true);
        intake2.setInverted(true);
    }
    
    public void intakeup() {
        intake1.set(intakeforwardtopic.getEntry(Constants.IntakeConstants.INTAKE_FORWARD_SPEED).get());
        intake2.set(intakeforwardtopic.getEntry(Constants.IntakeConstants.INTAKE_FORWARD_SPEED).get());//will need to test
    }
    
    public void intakepush() {

        intake1.set(intakebackwardtopic.getEntry(Constants.IntakeConstants.INTAKE_BACKWARD_SPEED).get());
        intake2.set(intakebackwardtopic.getEntry(Constants.IntakeConstants.INTAKE_BACKWARD_SPEED).get());
    }

    public void intakereverse() {
        intake1.set(-intakepushtopic.getEntry(Constants.IntakeConstants.INTAKE_PUSH_SPEED).get());
        intake2.set(-intakepushtopic.getEntry(Constants.IntakeConstants.INTAKE_PUSH_SPEED).get());
    }

    public void stopIntake() {
        intake1.set(0);
        intake2.set(0);
    }
}