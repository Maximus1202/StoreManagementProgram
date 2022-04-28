import java.util.Scanner;

public class Worker extends Customer {

    private WorkerType workerType;



    public Worker(String firstName, String lastName, String userName, String password, int userType,  boolean isVIP, Integer rank) {
        super(firstName, lastName, userName, password, userType, isVIP);
        this.workerType = initRank(rank);
    }

    private WorkerType initRank(Integer rank) {
        switch (rank) {
            case 1:
                return WorkerType.REGULAR_WORKER;
            case 2:
                return WorkerType.MANAGER;
            case 3:
                return WorkerType.MANAGEMENT_TEAM;
        }
        return null;
    }

    public String admit() {
        StringBuilder message = new StringBuilder();
        message.append("Hello ").append(this.getFirstName()).append(" ").append(this.getLastName()).
                append(" ").append(this.getWorkerType()).append(" ! ");
                return message.toString();
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}
