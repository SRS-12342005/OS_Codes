import java.util.Scanner;

public class SRTF_Scheduling {

    static class Process {
        String id;
        int at;
        int bt;
        int remainingTime;
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
            System.out.println("\nProcess " + (i + 1) + ":");
            System.out.print("  Enter Process ID (e.g. P1): ");
            p[i].id = sc.next();
            System.out.print("  Enter Arrival Time: ");
            p[i].at = sc.nextInt();
            System.out.print("  Enter Burst Time:   ");
            p[i].bt = sc.nextInt();
            p[i].remainingTime = p[i].bt;
        }

        int currentTime = 0;
        int completedCount = 0;
        
        while (completedCount < n) {
            int shortestIndex = -1;
            int minRemaining = 999999;

            for (int i = 0; i < n; i++) {
                if (p[i].at <= currentTime && p[i].remainingTime > 0) {
                    if (p[i].remainingTime < minRemaining) {
                        minRemaining = p[i].remainingTime;
                        shortestIndex = i;
                    } else if (p[i].remainingTime == minRemaining) {
                        if (p[i].at < p[shortestIndex].at) {
                            shortestIndex = i;
                        }
                    }
                }
            }

            if (shortestIndex != -1) {
                p[shortestIndex].remainingTime--;
                currentTime++;

                if (p[shortestIndex].remainingTime == 0) {
                    completedCount++;
                    p[shortestIndex].ct = currentTime;
                    p[shortestIndex].tat = p[shortestIndex].ct - p[shortestIndex].at;
                    p[shortestIndex].wt = p[shortestIndex].tat - p[shortestIndex].bt;
                }
            } else {
                currentTime++;
            }
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
