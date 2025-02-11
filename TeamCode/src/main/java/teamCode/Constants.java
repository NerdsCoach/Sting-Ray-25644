package teamCode;

public class Constants
{

    public static final class DriveTrainConstants
    {
        public static final int kRandomValue = 0;
    }
    //Call values with
//    Constants.DriveTrainConstants.kRandomValue

    public static final class LiftArmConstants
    {
        public static final int kLiftArmCloseSample = 2;
        public static final int kLiftArmFarSample = 450;
        public static final int kLiftArmHighBasket = 2400;
        public static final int kLiftArmHighChamber = 2400;
        public static final int kLiftArmHome = 0;
        public static final int kLiftArmLowBasket = 2400;
        public static final int kLiftArmLowChamber = 675;
        public static final int kLiftArmScoreSpecimen = 200;
        public static final int kLiftArmFudgeFactorUp = 50;
        public static final int kLiftArmFudgeFactorDown = -50;
        public static final int kLiftArmIntakeReset = 550;
        public static final int kLiftarmReleaseClimbArm = 2400;
        public static final int kLiftArmClimb = 0;
    }

    public static final class SlideArmConstants
    {
        public static final int kSlideArmCloseSample = 7;
        public static final int kSlideArmFarSample = 434; //1970
        public static final int kSlideArmHighBasket = 602;
        public static final int kSlideArmHighChamber = 149; //600
        public static final int kSlideArmHome = 7;
        public static final int kSlideArmLowBasket = 68; //200
        public static final int kSlideArmLowChamber = 7;
        public static final int kSlideFudgeIn = -25;
        public static final int kSlideFudgeOut = 25;
        public static final int kSlideFudgeOutMax = 549;
        public static final int kSlideSpecimenScore = 54;
    }

    public static final class PivotIntakeConstants
    {
        public static final double kIntakePivotScore = 0.65;
        public static final double kIntakePivotPickUp = 0.54;
        public static final double kIntakePivotFarSample= 0.45;
    }
    public static final class ClimbArmConstants
    {
        public static final int kClimberArmUp = 11300;
        public static final int kClimbArmAscentTwo = 7500;
        public static final int kClimberArmFudgeUp = 250;
        public static final int kClimberArmFudgeDown = -250;
    }
}
