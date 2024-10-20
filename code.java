import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Splitwise {
    // Store balances for each user
    private Map<String, Double> balanceSheet;

    public Splitwise() {
        balanceSheet = new HashMap<>();
    }

    // Add an expense and split among participants
    public void addExpense(String paidBy, double amount, String[] participants) {
        double splitAmount = amount / participants.length;

        for (String participant : participants) {
            // If the participant is the one who paid, adjust their balance accordingly
            if (participant.equals(paidBy)) {
                balanceSheet.put(paidBy, balanceSheet.getOrDefault(paidBy, 0.0) + (amount - splitAmount));
            } else {
                // Deduct the split amount from each participant's balance
                balanceSheet.put(participant, balanceSheet.getOrDefault(participant, 0.0) - splitAmount);
            }
        }
    }

    // Display balances
    public void showBalances() {
        boolean noDebts = true;

        for (Map.Entry<String, Double> entry : balanceSheet.entrySet()) {
            if (entry.getValue() != 0) {
                noDebts = false;
                if (entry.getValue() > 0) {
                    System.out.println(entry.getKey() + " should receive " + entry.getValue());
                } else {
                    System.out.println(entry.getKey() + " owes " + Math.abs(entry.getValue()));
                }
            }
        }

        if (noDebts) {
            System.out.println("No balances to show. Everyone is settled up!");
        }
    }

    public static void main(String[] args) {
        Splitwise splitwise = new Splitwise();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense\n2. Show Balances\n3. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter the name of the person who paid: ");
                String paidBy = scanner.next();
                System.out.print("Enter the total amount: ");
                double amount = scanner.nextDouble();
                System.out.print("Enter the number of participants: ");
                int numParticipants = scanner.nextInt();
                String[] participants = new String[numParticipants];
                System.out.println("Enter the names of the participants: ");
                for (int i = 0; i < numParticipants; i++) {
                    participants[i] = scanner.next();
                }
                splitwise.addExpense(paidBy, amount, participants);
            } else if (choice == 2) {
                splitwise.showBalances();
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
