package modernJava.chapter5;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

public class TestCode {

    /**
     * reduce 에는 박싱, 언박싱 비용이 들어간다.
     * 내부적으로 합계를 계산하기 위해 Integer를 int로 언박싱 후 다시 int -> Integer로 박싱하는 과정때문에 시간이 더 오래걸린다.
     * 성능상 IntStream에서 제공하는 메서드가 있는 경우에는 해당 메서드를 이용하는게 바람직하다.
     */

    List<Transaction> transactions = new ArrayList<>();

    @Before
    public void init() {
        for (int i = 0; i < 1000000; i++) {
            transactions.add(i, new Transaction(new Trader(i + "", i + ""), i, i));
        }
    }

    @Test
    @DisplayName("for-loop 시간측정")
    public void forLoopTest() {
        long start1 = System.currentTimeMillis();
        int sum1 = 0;
        for (int i = 0; i < 1000000; i++) {
            sum1 += transactions.get(i).getValue();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("for-loop : " + (end1 - start1));
    }

    @Test
    @DisplayName("mapToInt 시간 측정")
    public void mapToIntTest() {
        long start2 = System.currentTimeMillis();
        int mapToIntSum2 = transactions.stream().mapToInt(Transaction::getValue).sum();
        long end2 = System.currentTimeMillis();
        System.out.println("MapToIntSum : " + (end2 - start2));
    }

    @Test
    @DisplayName("stream reduce 시간 측정")
    public void reduceTest() {
        long start3 = System.currentTimeMillis();
        int reduceSum = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        long end3 = System.currentTimeMillis();
        System.out.println("reduceSum : " + (end3 - start3));
    }


}
