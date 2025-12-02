import java.util.Scanner;

public class FCFS_ArrivalSort {

    static class Process {
        String id;
        int at;
        int bt;
        int ct;
        int tat;
        int wt;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.print("  Enter Arrival Time: ");
            p[i].at = sc.nextInt();
            System.out.print("  Enter Burst Time:   ");
            p[i].bt = sc.nextInt();
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (p[j].at > p[j + 1].at) {
                    Process temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;
                }
            }
        }

        int currentTime = 0;

        for (int i = 0; i < n; i++) {
            if (p[i].at > currentTime) {
                currentTime = p[i].at;
            }

            p[i].ct = currentTime + p[i].bt;
            currentTime = p[i].ct;
            p[i].tat = p[i].ct - p[i].at;
            p[i].wt = p[i].tat - p[i].bt;
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", 
                          "Process", "AT", "BT", "CT", "TAT", "WT");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-10d %-10d %-10d %-10d %-10d\n", 
                              p[i].id, p[i].at, p[i].bt, p[i].ct, p[i].tat, p[i].wt);
        }
        
        sc.close();
    }
}
