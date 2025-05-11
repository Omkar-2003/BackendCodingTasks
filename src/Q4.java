import java.util.*;
import java.util.concurrent.*;

public class Q4 {

    // Please refer to line 62 after the detailed explanation of the questions.

    public static class RateLimiterDemo {

        private static final int MAX_CALLS_PER_MINUTE = 15;
        private static final int PENALTY_DURATION_SECONDS = 60;

        private Deque<Long> callTimestamps = new ArrayDeque<>();
        private boolean inPenalty = false;
        private long penaltyStartTime = 0;

        // Simulated API
        public String call_me(String input) {
            return "Response for: " + input;
        }

        public String safeCall(String input) {
            long now = System.currentTimeMillis();

           if (inPenalty) {
                if (now - penaltyStartTime >= PENALTY_DURATION_SECONDS * 1000) {
                    inPenalty = false;
                    System.out.println("Penalty period ended.");
                } else {
                    throw new RuntimeException("You are in penalty mode! Wait before making another call.");
                }
            }

            while (!callTimestamps.isEmpty() && now - callTimestamps.peekFirst() > 60_000) {
                callTimestamps.pollFirst();
            }

            if (callTimestamps.size() >= MAX_CALLS_PER_MINUTE) {
                inPenalty = true;
                penaltyStartTime = now;
                throw new RuntimeException("Rate limit exceeded! Entering penalty mode.");
            }
            callTimestamps.addLast(now);
            return call_me(input);
        }

        public static  void main(String[] args) throws InterruptedException {
            RateLimiterDemo api = new RateLimiterDemo();

            for (int i = 1; i <= 20; i++) {
                try {
                    String response = api.safeCall("input-" + i);
                    System.out.println("Call " + i + ": " + response);
                } catch (RuntimeException ex) {
                    System.out.println("Call " + i + " failed: " + ex.getMessage());
                    Thread.sleep(61_000);
                }
                Thread.sleep(1000);
            }
        }
    }

}
// 1. You would be able to use the API within the safe limit.
// Answer : Maintain a queue of timestamps (Deque<Long>) of previous calls.Before each new call:
//    i)Remove timestamps older than 60 seconds.
//    ii)If the queue size is less than 15, allow the call.
//    iii)If the queue has 15 entries, block or delay the call. This ensures that only 15 calls are made within any rolling 60-second window.


// 2. What happens if you are supposed to call the API 20 times per minute? Is there any way to accomplish this?
// Answer :Previously, I worked with an AWS Application Load Balancer that intelligently distributes traffic across multiple EC2 instances or containers.
//    In this API rate-limiting scenario, here's how I would improve the system:"

//    i)Distribute Calls Using AWS Load Balancer (ALB):
//    1)Spin up multiple client instances behind an Application Load Balancer,The ALB distributes the load among instances.
//    2)If rate limit is enforced per IP or API key, this won’t help. But if not, you can:Run 2+ instances → each can safely make 15 calls → 30 total

//    ii)Queue Calls:
//    1)Calls exceeding 15/min are queued and retried next minute.
//    2)Use a producer-consumer pattern.
//    3)Java example: use ScheduledExecutorService or Kafka topic


// 3. If you were the API designer, what would you do to implement this behavior?
//  Answer:
//  1)Backend Monitoring & Auto-scaling:
//    If too many clients are hitting limits, consider auto-scaling server groups behind AWS Load Balancer to improve availability
//  2)Penalty Enforcement:
//    If client exceeds quota → block their API key/IP for 60 seconds
//  3)Token Bucket Algorithm:
//    Each client gets 15 tokens per 60 seconds and Each request consumes one.//
//    If no token left → return 429 Too Many Requests.
