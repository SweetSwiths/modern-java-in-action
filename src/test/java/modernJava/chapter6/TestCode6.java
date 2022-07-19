package modernJava.chapter6;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class TestCode6 {

    List<Beintech> beintechList = Arrays.asList(
            new Beintech("이추앙", "여자", 30, Beintech.Position.이사),
            new Beintech("정선일", "남자", 20, Beintech.Position.부장),
            new Beintech("박종록", "남자", 19, Beintech.Position.부장),
            new Beintech("송명하", "남자", 4, Beintech.Position.대리),
            new Beintech("장하니", "여자", 7, Beintech.Position.과장),
            new Beintech("임수연", "여자", 1, Beintech.Position.사원),
            new Beintech("김준수", "남자", 100, Beintech.Position.사원),
            new Beintech("박성수", "남자", 2, Beintech.Position.사원),
            new Beintech("서건욱", "남자", 8, Beintech.Position.과장),
            new Beintech("심진우", "남자", 12, Beintech.Position.차장),
            new Beintech("강산", "남자", 4, Beintech.Position.대리)
    );

    @Test
    @DisplayName("타입에 따른 분류")
    public void getPositionGroup() {
        Map<Beintech.Position, List<Beintech>> positionGroup = beintechList.stream()
                .collect(groupingBy(Beintech::getPosition));

//        Assertions.assertThat(positionGroup.keySet()).
//        System.out.println(positionGroup);
    }



}