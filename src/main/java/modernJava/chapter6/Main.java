package modernJava.chapter6;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) {
        List<Beintech> beintechList = Arrays.asList(
                new Beintech("이추앙", "여자", 30, Beintech.Position.이사),
                new Beintech("정선일","남자", 20, Beintech.Position.부장),
                new Beintech("박종록","남자", 19, Beintech.Position.부장),
                new Beintech("송명하","남자", 4, Beintech.Position.대리),
                new Beintech("장하니","여자", 7, Beintech.Position.과장),
                new Beintech("임수연","여자", 1, Beintech.Position.사원),
                new Beintech("김준수","남자", 100, Beintech.Position.사원),
                new Beintech("박성수","남자", 2, Beintech.Position.사원),
                new Beintech("서건욱","남자", 8, Beintech.Position.과장),
                new Beintech("심진우","남자", 12, Beintech.Position.차장),
                new Beintech("강산","남자", 4, Beintech.Position.대리)
        );

        // 그룹화
        getPositionGroup(beintechList);
        getFilterPositionGroup(beintechList);
        getFiltersPositionGroup(beintechList);
        countPositionGroup(beintechList);
        maxByPositionGroup(beintechList);

        // 분할
        partitioning(beintechList);
        partitioning2(beintechList);

        // 리듀싱
        reducingMax();
        reducingMaxBeintech(beintechList);

    }

    // 타입에 따른 분류
    private static void getPositionGroup(List<Beintech> beintechList) {
        Map<Beintech.Position, List<Beintech>> positionGroup = beintechList.stream()
                .collect(groupingBy(Beintech::getPosition));

        System.out.println(positionGroup);
    }

    // 조건에 따른 분류
    private static void getFilterPositionGroup(List<Beintech> beintechList) {
//        Map<Beintech.Position, List<Beintech>> positionFilterGroup = beintechList.stream()
//                .filter(beintech -> beintech.getYears() >= 100) // 필터로 걸러진 것들은 그룹화가 되지않음.
//                .collect(groupingBy(Beintech::getPosition));

        Map<Beintech.Position, List<Beintech>> positionFilterGroup = beintechList.stream()
                // groupingBy 메서드 오버로드
                .collect(groupingBy(Beintech::getPosition,
                        filtering(beintech -> beintech.getYears() >= 100,
                                toList())));
        System.out.println(positionFilterGroup);
    }

    // 여러개의 조건
    private static void getFiltersPositionGroup(List<Beintech> beintechList) {
        Map<Beintech.Position, Map<String, List<Beintech>>> positionFilterGroup = beintechList.stream()
                .collect(groupingBy(Beintech::getPosition,
                        groupingBy(beintech ->
                                {
                                    if (beintech.getYears() >= 10) {
                                        return "고수";
                                    } else {
                                        return "초보";
                                    }
                                }
                        )));
        System.out.println(positionFilterGroup);
    }

    // counting -- 그룹별로 인원수
    private static void countPositionGroup(List<Beintech> beintechList) {
        Map<Beintech.Position, Long> groups = beintechList.stream()
                .collect(groupingBy(Beintech::getPosition, counting()));
        System.out.println(groups);
    }

    // maxBy  -- position 그룹별로 제일 연차가 많은 사람
    private static void maxByPositionGroup(List<Beintech> beintechList) {
//        Map<Beintech.Position, Optional<Beintech>> groups = beintechList.stream()
//                .collect(groupingBy(Beintech::getPosition,
//                        maxBy(Comparator.comparingInt(Beintech::getYears))));

        //Optional을 제거해주려면 collectingAndThen 사용
        Map<Beintech.Position, Beintech> groups = beintechList.stream()
                .collect(groupingBy(Beintech::getPosition,
                        collectingAndThen(maxBy(comparingInt(Beintech::getYears)),
                        Optional::get)));

        System.out.println(groups);
    }

    //분할
    private static void partitioning(List<Beintech> beintechList) {
        Map<Boolean, List<Beintech>> partition = beintechList.stream()
                .collect(partitioningBy(be -> be.getGender().equals("남자")));
        System.out.println(partition);
    }

    private static void partitioning2(List<Beintech> beintechList) {
        Map<Boolean, Beintech> partition = beintechList.stream()
                .collect(partitioningBy(be -> be.getGender().equals("남자"),
                        collectingAndThen(maxBy(comparingInt(Beintech::getYears)),
                                Optional::get)));
        System.out.println(partition);
    }

    // 리듀싱
    private static void reducingMax() {
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");

        // Comparator 구현체
        Comparator<String> stringComparator = comparingInt(Integer::parseInt);

        Optional<String> maxNumber = numbers.stream()
                .collect(maxBy(stringComparator));
        System.out.println(maxNumber);
    }

    // 응용
    private static void reducingMaxBeintech(List<Beintech> beintechList) {

        // Comparator 구현체
        Comparator<Beintech> beintechComparator = comparingInt(Beintech::getYears);

        Optional<Beintech> beintechMax = beintechList.stream()
                .collect(maxBy(beintechComparator));

        System.out.println(beintechMax);
    }



}
