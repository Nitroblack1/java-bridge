package bridge.DomainTest;

import bridge.Constants.StandardTools;
import bridge.Constants.StandardTools.retry;
import bridge.Domain.BridgeGame;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

public class BridgeGameTest {

    @Mock
    BridgeGame bridgeGame = new BridgeGame();

    @Nested
    @DisplayName("데이터 저장 테스트")
    class BridgeDataTest {

        @DisplayName("게임 시작시 다리 설계도 저장 여부")
        @Test
        void isBridgeDesignStored() {
            String bridgeLength = "3";
            InputStream in = new ByteArrayInputStream(bridgeLength.getBytes());
            System.setIn(in);

            bridgeGame.gameStart();
            assertThat(bridgeGame.bridgeData.getBridge()).hasSize(Integer.parseInt(bridgeLength));
        }

        @DisplayName("게임 시작시 다리 길이 입력 후 총 시도횟수 초기화 여부")
        @Test
        void isTotalAttemptInitialized() {
            String bridgeLength = "3";
            InputStream in = new ByteArrayInputStream(bridgeLength.getBytes());
            System.setIn(in);

            bridgeGame.gameStart();
            assertThat(bridgeGame.bridgeData.getTotalAttempt()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("다리 이동 테스트")
    class moveTest {

        @DisplayName("다 건넜을 경우 성공을 선언한다.")
        @Test
        void finishCrossingBridgeTest() {
            bridgeGame.bridgeData.setBridge(Arrays.asList("U", "D", "D"));
            List<String> nextSteps = new ArrayList<>(Arrays.asList("U", "D", "D"));
            bridgeGame.bridgeData.updateBridgeDesignByUser(nextSteps);

            bridgeGame.validateGameSuccessfullyFinished();

            assertThat(bridgeGame.getIsGameSucceed()).isTrue();
        }
    }

    @Nested
    @DisplayName("재시도 여부 테스트")
    class retryOrQuitTest {

        @DisplayName("R을 받을 경우 RETRY를 선언, 시도 횟수 증가, 유저의 인풋값을 초기화한다.")
        @Test
        void retryTest() {
            String retryInput = "R";
            retry retry = StandardTools.retry.RETRY;
            InputStream in = new ByteArrayInputStream(retryInput.getBytes());
            System.setIn(in);

            bridgeGame.retry();
            assertThat(bridgeGame.getRetryOrQuit()).isEqualTo(retry);
            assertThat(bridgeGame.bridgeData.getTotalAttempt()).isEqualTo(1);
            assertThat(bridgeGame.bridgeData.getBridgeDesignByUser()).isEqualTo(
                    Collections.emptyList());
        }

        @DisplayName("Q를 받을 경우, QUIT 상태로 선언한다.")
        @Test
        void quitTest() {
            String quitInput = "Q";
            retry quit = retry.QUIT;
            InputStream in = new ByteArrayInputStream(quitInput.getBytes());
            System.setIn(in);

            bridgeGame.retry();
            assertThat(bridgeGame.getRetryOrQuit()).isEqualTo(quit);
        }
    }
}
