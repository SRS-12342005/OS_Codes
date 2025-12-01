import java.util.*;

/*
 * Program: Round Robin Scheduling
 * Logic:
 * 1. Use a Queue (Ready Queue) to manage execution order.
 * 2. Keep track of 'remainingTime' for every process.
 * 3. Run process for 'Time Quantum' (e.g., 2 units) or until finished.
 * 4. IMPORTANT: Add newly arrived processes to the queue BEFORE adding the 
 * current unfinished process back.
 */

public class RoundRobin_Scheduling {

    static class Process {
        String id;
        int at;             // Arrival Time
        int bt;             // Burst Time
        int remainingTime;  // Time left to run
        int ct;             // Completion Time
        int tat;            // Turnaround Time
        int wt;             // Waiting Time
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. INPUT
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

        // 2. SORT BY ARRIVAL TIME FIRST
        // (Just to ensure we add them to the queue in correct initial order)
        Arrays.sort(p, (p1, p2) -> p1.at - p2.at);

        // 3. LOGIC (The Ready Queue Simulation)
        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completedCount = 0;
        
        // This index helps us track which processes from the list 
        // have already joined the ready queue.
        int arrivalIndex = 0; 

        // If the first process doesn't arrive at 0, fast forward time
        if (n > 0 && p[0].at > 0) {
            currentTime = p[0].at;
        }

        // Add the first process(es) that have arrived to the queue
        while(arrivalIndex < n && p[arrivalIndex].at <= currentTime) {
            readyQueue.add(p[arrivalIndex]);
            arrivalIndex++;
        }

        System.out.println("\n--- Gantt Chart Execution ---");

        while (completedCount < n) {
            if (readyQueue.isEmpty()) {
                // If queue is empty but not everyone is done, wait for next arrival
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
            
            // Determine how long to run: Quantum or Remaining Time?
            int timeToRun = Math.min(quantum, current.remainingTime);

            // Execute
            current.remainingTime -= timeToRun;
            currentTime += timeToRun;
            
            System.out.print("| " + current.id + " (" + currentTime + ") ");

            // CHECK FOR NEW ARRIVALS
            // While this process was running, did anyone else arrive?
            // Add them to queue BEFORE adding the current process back.
            while (arrivalIndex < n && p[arrivalIndex].at <= currentTime) {
                readyQueue.add(p[arrivalIndex]);
                arrivalIndex++;
            }

            // If current process is not done, put it back in queue
            if (current.remainingTime > 0) {
                readyQueue.add(current);
            } else {
                // Process is finished
                completedCount++;
                current.ct = currentTime;
                current.tat = current.ct - current.at;
                current.wt = current.tat - current.bt;
            }
        }
        System.out.println("|");

        // 4. OUTPUT
        System.out.println("\n---------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s\n", 
                          "Process", "AT", "BT", "CT", "TAT", "WT");
        System.out.println("---------------------------------------------------------");

        // Note: The array 'p' is sorted by Arrival Time, so output will be in that order
        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-10d %-10d %-10d %-10d %-10d\n", 
                              p[i].id, p[i].at, p[i].bt, p[i].ct, p[i].tat, p[i].wt);
        }
        
        sc.close();
    }
}