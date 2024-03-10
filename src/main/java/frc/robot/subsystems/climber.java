package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class climber extends SubsystemBase {
    //TODO update id
    CANSparkMax climber1 = new CANSparkMax(21, MotorType.kBrushless);
    NetworkTable table;
    DoubleTopic climberclimbuptopic;
    DoubleTopic climberdeclimbtopic;
    public climber(NetworkTable table){
        this.table = table;
        // climber1.setIdleMode(IdleMode.kBrake);
        climberclimbuptopic = table.getDoubleTopic("/datatable/climberclimbup");
        climberdeclimbtopic = table.getDoubleTopic("/datatable/climberdeclimb");
        climberclimbuptopic.getEntry(0).set(0);
        climberdeclimbtopic.getEntry(0).set(0);
        climber1.setInverted(true);
        climber1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
    
    public void climbup() {
        climber1.set(climberclimbuptopic.getEntry(Constants.ClimberConstants.CLIMBER_CLIMB_SPEED).get());
    }
    
    public void declimb() {
        climber1.set(climberdeclimbtopic.getEntry(Constants.ClimberConstants.CLIMBER_DECLIMB_SPEED).get());
    }
    public void stopclimb() {
        climber1.set(0);
    }

}