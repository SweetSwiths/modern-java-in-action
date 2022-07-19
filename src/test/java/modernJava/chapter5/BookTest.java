package modernJava.chapter5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    Trader pss = new Trader("성수", "Busan");
    Trader smh = new Trader("명하", "Seoul");
    Trader kyj = new Trader("영진", "Seoul");
    Trader kjs = new Trader("준수", "Incheon");
    Trader lsy = new Trader("수연", "Bucheon");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(pss, 2011, 300),
            new Transaction(pss, 2012, 500),
            new Transaction(smh, 2011, 700),
            new Transaction(smh, 2012, 800),
            new Transaction(kjs, 2011, 1000),
            new Transaction(lsy, 2014, 900),
            new Transaction(kyj, 2013, 2000)
    );


    @Test
    @DisplayName("2011년에 일어난 모든 트랜잭션을 찾아서 값을 오른차순으로 정렬")
    void exam1() {
        List<Transaction> transaction2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println("transaction2011 = " + transaction2011);
        assertThat(transaction2011).filteredOn(t -> t.getYear() != 2011);
    }

    @Test
    @DisplayName("거래자가 근무하는 모든 도시를 중복 없이 나열하시오.")
    void exam2() {
        List<String> cityList = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("cityList = " + cityList);
        assertThat(cityList).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("서울에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.")
    void exam3() {
        List<Trader> traderInSeoul = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Seoul"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println("traderInSeoul = " + traderInSeoul);

        assertThat(traderInSeoul.iterator().next().getCity()).contains("Seoul");
    }

    @Test
    @DisplayName("모든 거래자의 이름을 자음순으로 정렬해서 반환하시오.")
    void exam4() {
        String traderStr1 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + "," + n2);
        System.out.println("reduce 사용");
        System.out.println("traderStr1 = " + traderStr1);


        String traderStr2 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println("joining 사용");
        System.out.println("traderStr2 = " + traderStr2);
    }

    @Test
    @DisplayName("부산에 거래자가 있는가?")
    void exam5() {
        boolean isBusanTrader = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Busan"));

        System.out.println("isBusanTrader = " + isBusanTrader);
        assertThat(isBusanTrader).isTrue();
    }

    @Test
    @DisplayName(" 서울에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.")
    void exam6() {
        List<Integer> seoulValueList = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Seoul"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println("seoulValueList = " + seoulValueList);

    }

    @Test
    @DisplayName("전체 트랜잭션 중 최댓값은 얼마인가?")
    void exam7() {
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .max(Comparator.comparing(Integer::intValue));
        // max
        System.out.println("maxValue = " + maxValue);

        Optional<Integer> reduceMaxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        // reduce max
        System.out.println("reduceMaxValue = " + reduceMaxValue);
        assertThat(maxValue).hasValue(2000);
    }

    @Test
    @DisplayName("전체 트랜잭션 중 최솟값은 얼마인가?")
    void exam8() {
        Optional<Integer> minValue = transactions.stream()
                .map(Transaction::getValue)
                .min(Comparator.comparing(Integer::intValue));
        // min
        System.out.println("minValue = " + minValue);

        Optional<Integer> reduceMinValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        // reduce min
        System.out.println("reduceMinValue = " + reduceMinValue);
        assertThat(minValue).hasValue(300);
    }

    @Test
    @DisplayName("전체 트랜잭션 값 합계는 ?")
    void exam9() {
        Optional<Integer> reduceSum = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println("reduceSum = " + reduceSum);


        int sum = transactions.stream()
                .mapToInt(Transaction::getValue).sum();
        System.out.println("sum = " + sum);

        assertThat(sum).isEqualTo(6200);
    }
}
