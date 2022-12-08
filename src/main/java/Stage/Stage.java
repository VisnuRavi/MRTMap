package main.java.Stage;

public abstract class Stage {
    Stage nextStage;

    public abstract void execute();

    public void setNextStage(Stage nextStage) {
        this.nextStage = nextStage;
    }

    public void proceed() {
        nextStage.execute();
    }
}
