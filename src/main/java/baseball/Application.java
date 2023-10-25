package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;
public class Application {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame() {
        List<Integer> computerNumbers = generateComputerNumbers();

        System.out.println("숫자 야구 게임을 시작합니다. 1부터 9까지 서로 다른 수로 이루어진 3자리의 수를 맞춰보세요.");

        while (true) {
            System.out.print("숫자를 입력하세요: ");
            String input = Console.readLine();

            try {
                List<Integer> userNumbers = parseUserInput(input);

                if (isMatch(computerNumbers, userNumbers)) {
                    System.out.println("정답입니다! 게임 종료!");
                    break;
                } else {
                    String hint = getHint(computerNumbers, userNumbers);
                    System.out.println("결과: " + hint);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("잘못된 입력입니다. 1부터 9까지 서로 다른 수로 이루어진 3자리 수를 입력하세요.");
            }
        }

        System.out.println("게임을 다시 시작하려면 'playGame()'를 호출하거나 애플리케이션을 종료합니다.");
    }

    public static List<Integer> generateComputerNumbers() {
        List<Integer> computerNumbers = new ArrayList<>();
        while (computerNumbers.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computerNumbers.contains(randomNumber)) {
                computerNumbers.add(randomNumber);
            }
        }
        return computerNumbers;
    }

    public static List<Integer> parseUserInput(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException();
        }

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int num = Character.getNumericValue(input.charAt(i));
            if (num < 1 || num > 9 || numbers.contains(num)) {
                throw new IllegalArgumentException();
            }
            numbers.add(num);
        }
        return numbers;
    }

    public static boolean isMatch(List<Integer> computerNumbers, List<Integer> userNumbers) {
        for (int i = 0; i < computerNumbers.size(); i++) {
            if (!computerNumbers.get(i).equals(userNumbers.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getHint(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < computerNumbers.size(); i++) {
            for (int j = 0; j < userNumbers.size(); j++) {
                if (computerNumbers.get(i).equals(userNumbers.get(j))) {
                    if (i == j) {
                        strikes++;
                    } else {
                        balls++;
                    }
                }
            }
        }

        if (strikes == 0 && balls == 0) {
            return "낫싱";
        }

        StringBuilder hint = new StringBuilder();
        if (strikes > 0) {
            hint.append(strikes).append("스트라이크 ");
        }
        if (balls > 0) {
            hint.append(balls).append("볼");
        }

        return hint.toString();
    }
}
