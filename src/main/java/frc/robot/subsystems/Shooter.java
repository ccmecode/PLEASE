package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    CANSparkMax shooter1 = new CANSparkMax(32, MotorType.kBrushless);
    CANSparkMax shooter2 = new CANSparkMax(33, MotorType.kBrushless);
    NetworkTable table;
    DoubleTopic shooter1lowertopic;
    DoubleTopic shooter1midtopic;
    DoubleTopic shooter1uppertopic;
    DoubleTopic shooter2lowertopic;
    DoubleTopic shooter2midtopic;
    DoubleTopic shooter2uppertopic;

    public Shooter(NetworkTable table){
        this.table = table;
        shooter1.setInverted(true);
        shooter2.setInverted(true);
        shooter1lowertopic = table.getDoubleTopic("/datatable/shooter1lower");
        shooter1midtopic = table.getDoubleTopic("/datatable/shooter1mid");
        shooter1uppertopic = table.getDoubleTopic("/datatable/shooter1upper");
        shooter2lowertopic = table.getDoubleTopic("/datatable/shooter2lower");
        shooter2midtopic = table.getDoubleTopic("/datatable/shooter2mid");
        shooter2uppertopic = table.getDoubleTopic("/datatable/shooter2upper");
        shooter1lowertopic.getEntry(0).set(0);
        shooter1midtopic.getEntry(0).set(0);
        shooter1uppertopic.getEntry(0).set(0);
        shooter2lowertopic.getEntry(0).set(0);
        shooter2midtopic.getEntry(0).set(0);
        shooter2uppertopic.getEntry(0).set(0);
    }
    
    public void shoot() {
        shooter1.set(shooter1midtopic.getEntry(Constants.ShooterConstants.WHEEL_1_MID_SPEED).get());
        shooter2.set(shooter2midtopic.getEntry(Constants.ShooterConstants.WHEEL_2_MID_SPEED).get()); //will need to test

    }
    
    public void stopShoot() {
        shooter1.set(0.0);
        shooter2.set(0.0);
    }

    public void shootlow() {
        shooter1.set(shooter1lowertopic.getEntry(Constants.ShooterConstants.WHEEL_1_LOWER_SPEED).get());//will need to test
        shooter2.set(shooter2lowertopic.getEntry(Constants.ShooterConstants.WHEEL_2_LOWER_SPEED).get());//will need to test
    }

    public void shoottrap() {
        shooter1.set(shooter1uppertopic.getEntry(Constants.ShooterConstants.WHEEL_1_UPPER_SPEED).get());
        shooter2.set(shooter2midtopic.getEntry(Constants.ShooterConstants.WHEEL_2_UPPER_SPEED).get());//will need to test
    }
}