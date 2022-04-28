
public enum WorkerType {
    REGULAR_WORKER(10),
    MANAGER(20),
    MANAGEMENT_TEAM(30);

    int discount;
    WorkerType(int discount) {
        this.discount = discount;
    }

}
