import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class ValleyBikeController {

    // initiate input of type scanner
    private static Scanner input = new Scanner(System.in);

    /**
     * Basic option menu that shows at start of program and when no one is logged in
     */
    static void initialMenu() throws IOException, ParseException {
        //TODO back menu on all menus
        //TODO exit option on all menus
        System.out.print("\n Welcome to ValleyBike Share! \n"
                + "1. Create User Account\n"
                + "2. Log In\n"
                + "0. Exit program\n");
        System.out.println("Please enter your selection (0-2):");

        // if input is not a integer
        if (!input.hasNextInt()){
            //keep asking for input until valid
            System.out.println("Not a valid input");
            initialMenu();
        }

        Integer num = input.nextInt();
        input.nextLine();

        switch(num) {
            case 1:
                createAccount();
                break;
            case 2:
                logIn();
                break;
            case 0:
                input.close();

                // save bike and station data
                ValleyBikeSim.saveBikeList();
                ValleyBikeSim.saveStationList();

                System.exit(0);
                break;
        }
    }

    //Do we want a separate method for creating user vs internal accounts? This one is User
    /**
     * Method for a customer to create an account
     */
    public static void createAccount() throws IOException, ParseException {
        //TODO membership types
        //TODO talk to Annika about the flow from model to controller
        String username = enterUsername();
        String password = enterPassword();
        String emailAddress = enterEmail();
        String creditCard = enterCreditCard();
        String membership = enterMembership();

        //create new customer account
        CustomerAccount customerAccount = new CustomerAccount(username, password, emailAddress, creditCard, membership);
        //add customer account to customer account map
        ValleyBikeSim.addCustomerAccount(customerAccount);

        System.out.println("Customer account successfully created!");
        //go to account menu
        String user = customerAccount.getUsername();
        userAccountHome(user);
    }

    private static String enterUsername(){
        System.out.println("Enter username (must be between 6-14 characters):");
        String username = input.nextLine();
        if (!isValidUsername(username)){
            System.out.println("Username is not valid.");
            enterUsername();
        }
        return username;
    }

    private static String enterPassword(){
        System.out.println("Enter password (must be between 6-14 characters):");
        String password = input.nextLine();
        if (!isValidPassword(password)){
            System.out.println("Password is not valid.");
            enterPassword();
        }
        return password;
    }

    private static String enterEmail(){
        // TODO let user know how to make valid email address
        System.out.println("Enter email address:");
        String emailAddress = input.nextLine();
        if (!isValidEmail(emailAddress)){
            System.out.println("Email address is not valid.");
            enterEmail();
        }
        return emailAddress;
    }

    private static String enterCreditCard(){
        System.out.println("Enter credit card number:");
        String creditCard = input.nextLine();
        if (!isValidCreditCard(creditCard)){
            System.out.println("Credit card is not valid.");
            enterCreditCard();
        }
        return creditCard;
    }

    private static String enterMembership(){
        System.out.println("Enter membership type:");
        String membership = input.nextLine();
        return membership;
    }

    private static boolean isValidUsername(String username){
        if (username.length() >= 6 && username.length() <= 14){
            return true;
        }
        return false;
    }

    private static boolean isValidPassword(String password){
        if (password.length() >= 6 && password.length() <= 14){
            return true;
        }
        return false;
    }

    private static boolean isValidCreditCard(String creditCard){
        if(Math.random() <= 0.95) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if the email address input by the user is valid
     * @param emailAddress is the email address input by the user
     * @return returns boolean true if email address is valid and false otherwise
     */
    private static boolean isValidEmail(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (emailAddress == null) return false;
        return pat.matcher(emailAddress).matches();
    }


    public static void logIn() throws IOException, ParseException {
        System.out.println("Press 1 to log in to customer account. \nPress 2 to log in to internal account.");
        int logIn = input.nextInt();
        input.nextLine();
        System.out.println("Enter username:");
        String username = input.nextLine();
        System.out.println("Enter password:");
        String password = input.nextLine();
        switch (logIn){
            case 1:
                ValleyBikeSim.customerLogIn(username, password);
                break;
            case 2:
                ValleyBikeSim.internalLogIn(username, password);
                break;
            default:
                System.out.println("Invalid option chosen.");
                logIn();
        }
    }


    /**
     * Standard menu page for a user after logging in
     * @param username: integer representing unique userID of account
     */
    public static void userAccountHome(String username) throws IOException, ParseException {
        System.out.println("Please choose from one of the following menu options: \n"
                + "1. Edit account info\n"
                + "2. View account balance\n"
                + "3. View station list\n"
                + "4. Rent a bike\n"
                + "6. Log out \n");

        System.out.println("Please enter your selection (1-6):");

        // if input is not a integer
        if (!input.hasNextInt()){
            //keep asking for input until valid
            System.out.println("Not a valid input");
            userAccountHome(username);
        }
        Integer num = input.nextInt();

        switch(num) {
            case 1:
                //edit account info- return to create account or have separate method?
                editAccount(username);
                break;
            case 2:
                //view account balance
                ValleyBikeSim.viewAccountBalance(username);
                break;
            case 3:
                //view station list
                ValleyBikeSim.viewStationList();
                break;
            case 4:
                // helps user rent a bike
                rentBike(username);
                break;
            case 5:
                //log out, return to homepage
                initialMenu();
                break;
        }

        //if function call finished and returned to this page, keep calling menu again until log out/exit
        userAccountHome(username);
    }

    private static void editAccount(String username) throws IOException, ParseException {
        //TODO save edited fields
        System.out.println("Press 1 to edit username.\nPress 2 to edit password." +
                "\nPress 3 to edit email address. \nPress 4 to edit credit card number. \nPress 5 to edit membership.");
        int edit = input.nextInt();
        input.nextLine();
        switch (edit){
            case 1:
                enterUsername();
                break;
            case 2:
                enterPassword();
                break;
            case 3:
                enterEmail();
                break;
            case 4:
                enterCreditCard();
                break;
            case 5:
                enterMembership();
                break;
        }
    }

    /**
     * @param: userID- the unique id associated with the user
     * View the account balance associated with a user's account
     */
    private static void viewAccountBalance(String username) {
        ValleyBikeSim.viewAccountBalance(username);
    }

    /**
     * Can be used for both renting and returning bike
     * Prompts the user for info as to achieve those tasks
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void rentBike(String username) throws IOException, ParseException{
        // View stations
        System.out.println("Here's a list of station IDs and their names");
        System.out.format("%-10s%-10s\n", "ID", "Name");

        // initiate iterator
        Iterator<Integer> keyIterator = ValleyBikeSim.createIterator(false);

        // while it has a next value
        while(keyIterator.hasNext()){
            Integer key = (Integer) keyIterator.next();
            Station station = ValleyBikeSim.getStationObj(key);
            System.out.format("%-10d%-10s\n", key, station.getStationName());
        }

        // choose station to rent from
        int statId = getResponse("Please pick a station from list shown above" +
                "to rent a bike from");

        // designated station, whether bike returned to or bike rented from
        Station stationFrom = ValleyBikeSim.getStationObj(statId);

        // keep prompting user until the station obj is not null
        while(stationFrom == null) {
            System.out.println("The station entered does not exist in our system.");
            statId = getResponse("Please pick a station from list shown above" +
                    "to rent a bike from");
            stationFrom = ValleyBikeSim.getStationObj(statId);
        }

        // if there's more than one bike at station
        if (stationFrom.getBikes() > 1){
            // station now has one less bike
            stationFrom.setBikes(stationFrom.getBikes()+1);
            // and one more available dock
            stationFrom.setAvailableDocks(stationFrom.getAvailableDocks()+1);
        } else {
            // if there's less, notify maintenance worker to resolve data
            System.out.println("Station is almost empty!");
            System.out.println("Notifying maintenance worker to resolve this...");
            ValleyBikeSim.equalizeStations();
            System.out.println("All done!");
        }

        // view available bike ids at station
        System.out.println("Here's a list of bike IDs at this station");
        System.out.format("%-10s%-10s\n", "Station", "Bike ID");

        // initiate iterator
        Iterator<Integer> keyIterator2 = ValleyBikeSim.createIterator(true);;

        // keep looping until there is no next value
        while(keyIterator2.hasNext()){
            Integer key = (Integer) keyIterator2.next();
            Bike bike = ValleyBikeSim.getBikeObj(key);
            if(statId == bike.getStation()) {
                System.out.format("%-10s%-10d\n", statId, key);
            }

        }

        // choose bike to rent
        int b = getResponse("Please enter the id of the bike you" +
                " would like to rent.");
        Bike someBike = ValleyBikeSim.getBikeObj(b);

        while(someBike == null) {
            System.out.println("The bike ID entered does not exist in our system.");
            b = getResponse("bike id");
            someBike = ValleyBikeSim.getBikeObj(b);
        }

        // change bike location to live with customer
        someBike.setBikeLocation(2);

        // TODO add timestamp to ride data

        // time stamp recorded
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);

        // now bike is fully rented
        bikeRented(username, b);
    }

    /**
     * User has bike checked out and can either return bike or report a problem with the bike
     * @param: int userID- the unique id associated with the user
     * @param: bikeID- unique ID associated with the bike that the user has checked out
     */
    private static void bikeRented(String username, int bikeID) throws IOException, ParseException {
        System.out.print("\n Hope you enjoyed your bike ride! \n"
                + "1. Return bike"
                + "2. Report a problem");
        System.out.println("Please enter your selection (1-2):");

        if (!input.hasNextInt()){
            //keep asking for input until valid
            System.out.println("Not a valid input \n");
            bikeRented(username, bikeID);
        }

        Integer num = input.nextInt();
        switch(num) {
            case 1:
                //return bike
                returnBike(username);
                break;
            case 2:
                //report a problem
                reportProblem(username, bikeID);
                break;
            case 0:
                input.close();
                //save all files before exiting
                System.exit(0);
                break;
        }
    }

    /**
     * Can be used for both renting and returning bike
     * Prompts the user for info as to achieve those tasks
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void returnBike(String username) throws IOException, ParseException{
        // choose station to rent from
        int statId = getResponse("Please enter station you're returning the " +
                "bike to");

        // designated station, whether bike returned to or bike rented from
        Station stationTo = ValleyBikeSim.getStationObj(statId);

        // keep prompting user until the station obj is not null
        while(stationTo == null) {
            System.out.println("The station entered does not exist in our system.");
            statId = getResponse("Please enter station you're returning the " +
                    "bike to");
            stationTo = ValleyBikeSim.getStationObj(statId);
        }

        // if there's more than one bike at station
        if (stationTo.getBikes() > 1){
            // station now has one less bike
            stationTo.setBikes(stationTo.getBikes()+1);
            // and one more available dock
            stationTo.setAvailableDocks(stationTo.getAvailableDocks()+1);
        } else {
            // if there's less, notify maintenance worker to resolve data
            System.out.println("Station is almost empty!");
            System.out.println("Notifying maintenance worker to resolve this...");
            ValleyBikeSim.equalizeStations();
            System.out.println("All done!");
        }

        // choose bike to rent
        int b = getResponse("bike id");
        Bike someBike = ValleyBikeSim.getBikeObj(b);

        // if the bike doesn't exist
        while(someBike == null) {
            System.out.println("The bike ID entered does not exist in our system.");
            b = getResponse("bike id");
            someBike = ValleyBikeSim.getBikeObj(b);
        }

        // time stamp recorded
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);

        // if returned, then bike location is available and docked
        someBike.setBikeLocation(0);

        // set bike's station id to the station it's returned to
        someBike.setStation(statId);

        // take user back to their account home
        userAccountHome(username);
    }


    /**
     * Report problem for regular user by adding bike's id to
     * maintenance request list and setting its fields to requiring
     * maintenance.
     *
     * @param username for user
     * user reports a problem with the bike they checked out
     */
    private static void reportProblem(String username, int bikeId) throws IOException, ParseException {
        // add to maintenance requests
        ValleyBikeSim.addToMntRqs(bikeId);

        // prompt user for maintenance report
        System.out.println("Please enter maintenance report.");
        input.nextLine();
        String mntReport = input.nextLine();

        // get bike object
        Bike bike = ValleyBikeSim.getBikeObj(bikeId);

        // set bike's maintenance report and maintenance to true
        bike.setMnt(true);
        bike.setMntReport(mntReport);

        // bike is now out of commission until fixed
        bike.setBikeLocation(1);

        // increase maintenance requests for the station
        Station statObj = ValleyBikeSim.getStationObj(bike.getStation());
        statObj.setMaintenanceRequest(statObj.getMaintenanceRequest()+1);

        // let user know the process is done
        System.out.println("Your report has been submitted successfully!");
        System.out.println("Now let's help you return your bike!");

        // now return bike
        returnBike(username);
    }

    /*
     * Homescreen for internal company employees
     *
     */
    static void internalAccountHome() throws IOException, ParseException {
        // TODO give user option to view station and bike list
        System.out.print("\n Choose from the following: \n"
                + "1. View customer balances \n"
                + "2. View customer activity \n"
                + "3. Add new station \n"
                + "4. Add new bike \n"
                + "5. Edit/Resolve maintenance requests \n"
                + "6. Equalize stations \n"
                + "7. Log out \n");
        System.out.println("Please enter your selection (1-5):");

        if (!input.hasNextInt()){
            //keep asking for input until valid
            System.out.println("Not a valid input \n");
            internalAccountHome();
        }
        Integer num = input.nextInt();
        switch(num) {
            case 1:
                //TODO view customer balances
                break;
            case 2:
                //TODO view customer activity
                break;
            case 3:
                addStation();
                break;
            case 4:
                addBike();
                break;
            case 5:
                ValleyBikeSim.resolveMntReqs();

                break;
            case 6:
                //equalize stations
                ValleyBikeSim.equalizeStations();
                break;
            case 7:
                //log out
                initialMenu();
                break;
        }
        //if function call finishes and returns to internal account menu
        //call account menu again
        internalAccountHome();
    }

    /**
     * Prompts user for all station data and then creates a new station
     * object which is added to the stationMap
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void addStation() throws IOException, ParseException{
        // use helper function to check input is valid and save it
        Integer id = getResponse("Please enter the ID for this station:");

        Station station = ValleyBikeSim.getStationObj(id);

        // handle if the station already exists
        while(station != null){
            // let user know
            System.out.println("Station with this ID already exists.");

            // re-prompt user for station id
            id = getResponse("Please re-enter the ID for this station:");
            station = ValleyBikeSim.getStationObj(id);
        }

        // prompt user for station name
        System.out.println("Please enter station name: ");
        input.nextLine();
        String name = input.nextLine();

        // prompt user for number of bikes
        Integer bikes = getResponse("How many bikes?");

        // prompt for number of available docks at station
        Integer availableDocks = getResponse("How many available docks?");

        // number of maintenance requests
        Integer maintenanceRequest = getResponse("How many maintenance requests?");

        // prompt capacity for station
        Integer capacity = getResponse("What is the station's capacity?");

        // number of kiosks
        Integer kiosk = getResponse("How many kiosks?");

        // prompt for the station's address
        System.out.print("Please enter station address: ");
        input.nextLine();
        String address = input.nextLine();

        // create new station object with received data from user
        Station stationOb = new Station(
                name,
                bikes,
                availableDocks,
                maintenanceRequest,
                capacity,
                kiosk,
                address);

        // add to the station tree
        ValleyBikeSim.addNewStation(id, stationOb);
    }

    /**
     * This method enables maintenance workers to add new bikes
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void addBike() throws IOException, ParseException{
        // get new bike's id
        Integer id = getResponse("Please enter the bike's ID");

        // if the bike already exists
        while(ValleyBikeSim.getBikeObj(id) != null){
            // ask if user wants to overwrite bike
            System.out.println("Bike with this ID already exists.");

            // prompt user to re-enter bike id
            id = getResponse("Please re-enter the bike's ID");
        }

        // prompt for the station id bike will be located in
        Integer stationId = getResponse("Please enter the ID for the station the bike is located at:");

        // get station object and save it
        Station station = ValleyBikeSim.getStationObj(stationId);

        // check if station doesn't exist
        while(station == null){
            // let user know and prompt them to reenter the id
            System.out.println("Station with this ID doesn't exist");
            stationId = getResponse("Please re-enter the ID for this station:");
            station = ValleyBikeSim.getStationObj(id);
        }

        // prompt user if it requires maintenance
        System.out.println("Does it require maintenance? (y/n): ");
        input.nextLine();
        String mnt = input.nextLine();

        // initiate maintenance report string
        String mntReport = "";

        // if it does require maintenance
        if(mnt.toLowerCase().equalsIgnoreCase("y")){
            // prompt user to enter maintenance report
            System.out.println("Please enter maintenance report:");
            input.nextLine();
            mntReport = input.nextLine();

            // this increases the bike's station maintenance requests
            station.setMaintenanceRequest(station.getMaintenanceRequest()+1);

        }

        // give appropriate choices for bike's location
        System.out.println("Please pick one of the following choices for the " +
                "status of the bike:\n" +
                "0: Docked/available at station\n" +
                "2: Docked/out of commission\n");

        // prompt user to select an option
        Integer bikeLocation = getResponse("Please enter one of the above options:");

        // while the answer is not between the options, keep prompting user
        while(!(bikeLocation == 0 | bikeLocation == 2)){
            System.out.println("Your answer has to be either 0 or 2");

            // give appropriate choices for bike's location
            System.out.println("Please pick one of the following choices for the " +
                    "status of the bike:\n" +
                    "0: Docked/available at station\n" +
                    "1: Docked/out of commission\n");
            bikeLocation = getResponse("Please enter one of the above options:");
        }

        // increase number of bikes and available docks
        // if location of bike is available and docked
        if(bikeLocation == 0){
            // increase number of bikes for station
            station.setBikes(station.getBikes()+1);
            station.setAvailableDocks(station.getAvailableDocks()+1);
        }

        // create new bike object based on user's inputs
        Bike bikeOb = new Bike(
                id,
                bikeLocation,
                stationId,
                mnt,
                mntReport
        );

        // add to bike tree structure
        ValleyBikeSim.addNewBike(id, bikeOb);
    }

    /**
     * Helper method to validate user integer input
     *
     * @param request - the input being requested
     * @return the validated integer inputted by user
     */
    public static Integer getResponse(String request){
        System.out.println(request);
        while (!input.hasNextInt()){
            System.out.println("That is not a valid number");
            System.out.println(request);
            input.next();
        }
        Integer value = input.nextInt();
        return value;
    }

    /**
     * Calls ValleyBikeSim's resolveData method to resolve ride data
     *
     * @throws IOException
     * @throws ParseException
     */
    public void resolveData() throws IOException, ParseException {
        String dataFile = input.next();
        ValleyBikeSim.resolveData(dataFile);
    }
}

