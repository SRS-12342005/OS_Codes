import java.util.*;

public class RoundRobin_Scheduling {

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
            System.out.print("  Enter ID: "); p[i].id = sc.next();
            System.out.print("  Enter AT: "); p[i].at = sc.nextInt();
            System.out.print("  Enter BT: "); p[i].bt = sc.nextInt();
            p[i].remainingTime = p[i].bt;
        }

        System.out.print("\nEnter Time Quantum: ");
        int quantum = sc.nextInt();

        Arrays.sort(p, (p1, p2) -> p1.at - p2.at);

        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completedCount = 0;
        
        int arrivalIndex = 0; 

        if (n > 0 && p[0].at > 0) {
            currentTime = p[0].at;
        }

        while(arrivalIndex < n && p[arrivalIndex].at <= currentTime) {
            readyQueue.add(p[arrivalIndex]);
            arrivalIndex++;
        }

        System.out.println("\n--- Gantt Chart Execution ---");

        while (completedCount < n) {
            if (readyQueue.isEmpty()) {
                if (arrivalIndex < n) {
                    currentTime = p[arrivalIndex].at;
                    while(arrivalIndex < n && p[arrivalIndex].at <= currentTime) {
                        readyQueue.add(p[arrivalIndex]);
                        arrivalIndex++;
                    }
                }
                continue;
            }

            Process current = readyQueue.poll();
            
            int timeToRun = Math.min(quantum, current.remainingTime);

            current.remainingTime -= timeToRun;
            currentTime += timeToRun;
            
            System.out.print("| " + current.id + " (" + currentTime + ") ");

            while (arrivalIndex < n && p[arrivalIndex].at <= currentTime) {
                readyQueue.add(p[arrivalIndex]);
                arrivalIndex++;
            }

            if (current.remainingTime > 0) {
                readyQueue.add(current);
            } else {
                completedCount++;
                current.ct = currentTime;
                current.tat = current.ct - current.at;
                current.wt = current.tat - current.bt;
            }
        }
        System.out.println("|");

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
