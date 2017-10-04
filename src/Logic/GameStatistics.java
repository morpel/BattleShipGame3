package Logic;


public class GameStatistics {

    private long totalElapsedTimeMS;
    private String totalElapsedTimeStr;
    private int movesCount;
    private StopWatch stopWatch;

    public GameStatistics() {
        totalElapsedTimeStr = null;
        movesCount = 0;
        stopWatch = new StopWatch();
    }

    public long getTotalElapsedTimeMS() {
        totalElapsedTimeMS = stopWatch.getElapsedTimeInMS();
        return totalElapsedTimeMS;
    }

    public String getTotalElapsedTimeStr() {
        totalElapsedTimeStr = stopWatch.getTimeElapsedAsString(stopWatch.getElapsedTimeInMS());
        return totalElapsedTimeStr;
    }

    public void startWatch() {
        stopWatch.initWatch();
    }

    public void updateMoveCounter() {
        movesCount++;
    }

    public int getMovesCounter() {
        return movesCount;
    }
}
