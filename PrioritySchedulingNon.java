import java.util.Scanner;

public class PrioritySchedulingNon {

    static class Process {
        String id;
        int at;
        int bt;
        int priority;
        int ct;
        int tat;
        int wt;
        boolean isCompleted;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.println("\nProcess " + (i + 1) + ":");
            System.out.print("  Enter ID: "); 
            p[i].id = sc.next();
            System.out.print("  Enter AT: "); 
            p[i].at = sc.nextInt();
            System.out.print("  Enter BT: "); 
            p[i].bt = sc.nextInt();
            System.out.print("  Enter Priority (Lower # = Higher Priority): "); 
            p[i].priority = sc.nextInt();
            p[i].isCompleted = false;
        }

        int currentTime = 0;
        int completedCount = 0;

        while (completedCount < n) {
            int idx = -1;
            int bestPriority = 999999;

            for (int i = 0; i < n; i++) {
                if (p[i].at <= currentTime && !p[i].isCompleted) {
                    if (p[i].priority < bestPriority) {
                        bestPriority = p[i].priority;
                        idx = i;
                    } else if (p[i].priority == bestPriority) {
                        if (p[i].at < p[idx].at) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                p[idx].ct = currentTime + p[idx].bt;
                p[idx].tat = p[idx].ct - p[idx].at;
                p[idx].wt = p[idx].tat - p[idx].bt;
                p[idx].isCompleted = true;
                completedCount++;
                currentTime = p[idx].ct;
            } else {
                currentTime++;
            }
        }

        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", 
                          "Process", "AT", "BT", "Priority", "CT", "TAT", "WT");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-10d %-10d %-10d %-10d %-10d %-10d\n", 
                              p[i].id, p[i].at, p[i].bt, p[i].priority, p[i].ct, p[i].tat, p[i].wt);
        }
        
        sc.close();
    }
}
