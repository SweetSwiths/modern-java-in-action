package modernJava.chapter6;

public class Beintech {
    private String name;
    private String gender;
    private int years;
    private Position position;


    public enum Position {
        이사, 부장, 차장, 과장, 대리, 사원
    }

    public Beintech(String name, String gender, int years, Position position) {
        this.name = name;
        this.gender = gender;
        this.years = years;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getYears() {
        return years;
    }

    public Position getPosition() {
        return position;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "name= " + name;
    }
}

