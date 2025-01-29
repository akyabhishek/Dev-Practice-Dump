public class HealthMonitor {
    int heartRate;
    public HealthMonitor(){
        return heartRate();
    }
    public void setHeartRate(int hr){
        this.heartRate=hr;
    }
    public int getHeartRate(){
        return heartRate;
    }
}
