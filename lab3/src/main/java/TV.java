public class TV {
    private boolean power = false;
    private int channel = 0;
    private final int minChannel = 0, maxChannel = 100;
    private final String enableMode = "TV is on. Current channel is ";
    private final String disableMode = "TV is off.";

    public void setPower(boolean power) {
        this.power = power;
        if (channel == 0) {
            channel++;
        }
        if (!power) {
            setChannel(minChannel);
        } else {
            setChannel(channel);
        }
    }

    public boolean getPower() {
        return power;
    }

    public void setChannel(int channel) {
        if (getPower() && (minChannel < channel && channel < maxChannel)) {
            this.channel = channel;
        }
    }

    public int getChannel() {
        if (getPower()) {
            return channel;
        }
        return 0;
    }

    public String getInfo() {
        if (getPower()) {
            return enableMode + getChannel() + ".";
        }
        return disableMode;
    }
}
