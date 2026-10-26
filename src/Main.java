import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        // pick Dog or Cat first -- every menu option below only shows that type
        String type = askForType(input);

        while(running)
        {
            System.out.println();
            System.out.println("===== " + type + " Menu =====");
            System.out.println("1. List all " + type + " names");
            System.out.println("2. Find " + type + "s by color");
            System.out.println("3. Find spayed/neutered " + type + "s");
            System.out.println("4. Find " + type + "s with a birthday in a month");
            System.out.println("5. Switch pet type");
            System.out.println("0. Quit");
            System.out.print("Choose an option: ");

            // read the whole line so leftover newlines don't cause problems
            String choice = input.nextLine().trim();

            if(choice.equals("1"))
            {
                printNames(petAPI.namesOfType(type));
            }
            else if(choice.equals("2"))
            {
                System.out.print("Enter a color (e.g. Black, White, Brown): ");
                String color = input.nextLine().trim();
                printNames(petAPI.namesOfColors(color, type));
            }
            else if(choice.equals("3"))
            {
                System.out.print("Spayed/neutered? (yes or no): ");
                String answer = input.nextLine().trim();
                // the database stores true/false as 1/0
                String spayedNeutered;
                if(answer.equalsIgnoreCase("yes"))
                {
                    spayedNeutered = "1";
                }
                else
                {
                    spayedNeutered = "0";
                }
                printNames(petAPI.spayedNeuteredByAnimal(spayedNeutered, type));
            }
            else if(choice.equals("4"))
            {
                System.out.print("Enter a month (1-12): ");
                try
                {
                    int month = Integer.parseInt(input.nextLine().trim());
                    if(month < 1 || month > 12)
                    {
                        System.out.println("Month must be between 1 and 12.");
                    }
                    else
                    {
                        printNames(petAPI.namesWithBirthdayMonth(month, type));
                    }
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Please enter a number.");
                }
            }
            else if(choice.equals("5"))
            {
                type = askForType(input);
            }
            else if(choice.equals("0"))
            {
                running = false;
                System.out.println("Goodbye!");
            }
            else
            {
                System.out.println("Not a valid option, try again.");
            }
        }
        input.close();
    }

    /**
     * Keep asking the user for Dog or Cat until they enter one
     * @param input the Scanner to read from
     * @return "Dog" or "Cat", capitalized the way the database stores it
     */
    public static String askForType(Scanner input) {
        while(true)
        {
            System.out.print("Are you looking for a Dog or a Cat? ");
            String answer = input.nextLine().trim();
            if(answer.equalsIgnoreCase("dog"))
            {
                return "Dog";
            }
            else if(answer.equalsIgnoreCase("cat"))
            {
                return "Cat";
            }
            else
            {
                System.out.println("Please type Dog or Cat.");
            }
        }
    }

    /**
     * Print each name in the list on its own line, or a message if the list is empty
     * @param names the list of names to print
     */
    public static void printNames(ArrayList<String> names) {
        if(names.isEmpty())
        {
            System.out.println("No pets found.");
        }
        else
        {
            for(String name : names)
            {
                System.out.println(name);
            }
            System.out.println("(" + names.size() + " pets)");
        }
    }
}
