import java.time.LocalDate;

public abstract class Membership {

    private int totalRidesLeft;

    private LocalDate lastPayment;
    private LocalDate memberSince;

    Membership() {
        this.totalRidesLeft = 0;
        this.lastPayment = LocalDate.now();
        this.memberSince = LocalDate.now();
    }

    Membership(int totalRidesLeft, LocalDate lastPayment, LocalDate memberSince){
        this.totalRidesLeft = totalRidesLeft;
        this.lastPayment = lastPayment;
        this.memberSince = memberSince;
    }

    int getTotalRidesLeft() {
        return totalRidesLeft;
    }

    void setTotalRidesLeft(int totalRidesLeft) {
        this.totalRidesLeft = totalRidesLeft;
    }

    LocalDate getLastPayment() {
        return lastPayment;
    }

    void setLastPayment(LocalDate lastPayment) {
        this.lastPayment = lastPayment;
    }

    LocalDate getMemberSince() {
        return memberSince;
    }

    void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    public int getMembershipInt(){
        return 0;
    }

    public boolean checkPaymentDue(){
        return false;
    }

    public boolean checkMaxRidesExceeded() {
        return false;
    }

    public String getMembershipString(){
        return null;
    }

}
