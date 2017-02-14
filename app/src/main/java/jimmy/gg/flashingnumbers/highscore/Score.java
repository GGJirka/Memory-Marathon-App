package jimmy.gg.flashingnumbers.highscore;


public class Score  {
    private String level;
    private String highScore;
    private String text = "";

    public Score(String level, String highScore){
        this.level = level;
        this.highScore = highScore;
    }
    public Score(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setHighScore(String highScore){
        this.highScore = highScore;
    }
    public String getHighScore(){
        return highScore;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
